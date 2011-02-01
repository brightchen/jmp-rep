/*
 * $Id: FtpDocumentTask.java,v 1.12 2008/08/19 14:14:46 brightc Exp $
 * 
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 */

package cg.publish.task.ftpTask;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

import java.util.TimerTask;
import java.io.File;

import org.apache.commons.net.ftp.FTPFile;

import cg.common.email.EmailService;
import cg.common.logging.Logger;
import cg.publish.service.FtpConfigService;
import cg.publish.service.FtpFileService;
import cg.publish.util.DocNameUtil;
import cg.publish.xmlbinding.ftpConfig.FtpConnectionInfo;

/**
 * Run as a background process to retrieve the document from the ftp-server This class original from FtpTimerTask, I
 * copy it instead of extend from it as most of methods need slight modification.
 * 
 * The logic is a little bit different too, new features: 1. supports multi-ftp-servers 2. all the information related
 * to the parsing is inside file name
 * 
 * The logic of processing document in this part is same as the one in document-action. we can provide a service to this
 * logic later.
 * 
 * The expected post condition: 1. for each file in the ftp server, copy this file to the
 * local_dir/publication_month_day_year/success fold if success. copy this file to the
 * local_dir/publication_month_day_year/failed fold if failed. treat the first part of ftp file name as the publication.
 * 
 * 2. delete( maybe changed in the future ) the file from ftp server if download success.
 * 
 * The process of this task as following: 1. get all ftp file information from ftp server. 2. download the file to
 * success fold if file name is valid; download it the the failed fold if file name is invalid 3. delete the file from
 * ftp server 4. process the document( delegate to DocumentProcessService ), move the file to the failed fold if failed
 * 
 * @author Bright Chen
 */
public class FtpDocumentTask extends TimerTask
{
  private static final Logger log = Logger.getLogger( FtpDocumentTask.class );

  // fail codes;
  private static final int FC_INVALIDFILE = 1;
  private static final int FC_PROCESSFAILED = 2;
  private static final String FD_INVALIDFILE = "The file or file name format is invalid";
  private static final String FD_PROCESSFAILED = "Process file failed";

  private static final String DEF_EMAIL_NOTIFICATION_TITLE = "FTP file processing failed";

  private EmailService emailService;
  private FtpConfigService ftpConfigService;
  private FtpFileService ftpFileService;

  private String localDownloadDir;
  private String emailNotificationTitle = DEF_EMAIL_NOTIFICATION_TITLE;
  private boolean shouldRun;
  private int count = 0;

  protected Logger getStatusLogger( String loggerName )
  {
    return FtpStatusLoggerFactory.getInstance().getLogger( loggerName );
  }

  @Override
  public void run()
  {
    if( !shouldRun )
      return;
    
    int thisCount = count++;
    log.info( "FtpDocumentTask::run(), count: " + thisCount );
    List< FtpConnectionInfo > connectionInfoList = null;
    try
    {
      connectionInfoList = ftpConfigService.getFtpConnectionInfoList();
    }
    catch ( Exception e )
    {
      log.error( "FtpDocumentTask::run()", e );
    }

    if ( connectionInfoList == null || connectionInfoList.size() == 0 )
    {
      log.info( "No connection defined in configure file." );
      return;
    }

    for ( FtpConnectionInfo connectionInfo : connectionInfoList )
    {
      log.debug( "dealing ftp connection: " + connectionInfo.toString() );
      try
      {
        final String ftpServer = connectionInfo.getServer();
        final String ftpUser = connectionInfo.getUser();
        if ( ftpServer == null || ftpServer.length() == 0 || ftpUser == null || ftpUser.length() == 0 )
        {
          log.warn( "Ignore the invalid ftp connection: server=" + ftpServer + "; ftpUser=" + ftpUser );
          continue;
        }

        ftpFileService.startFtpConnection( ftpServer, ftpUser, connectionInfo.getPassword() );

        FTPFile[] ftpFiles = ftpFileService.listFtpFiles();
        if ( ftpFiles == null || ftpFiles.length == 0 )
        {
          log.info( "No file found on FTP server. server = " + ftpServer + "; user = " + ftpUser );
          continue;
        }

        // we should deal with the pdf file first
        ftpFiles = sequenceFtpFiles( ftpFiles );

        // deal with each file
        for ( FTPFile ftpFile : ftpFiles )
        {
          processFtpFile( ftpFile, connectionInfo );
        }
      }

      catch ( Exception e )
      {
        e.printStackTrace();
      }
      finally
      {
        ftpFileService.closeFtpConnection();
      }
    }
    log.info( "end of FtpDocumentTask::run(), count: " + thisCount );
  }

  protected FTPFile[] sequenceFtpFiles( FTPFile[] ftpFiles )
  {
    if ( ftpFiles == null || ftpFiles.length == 0 )
      return null;

    FTPFile[] sequencedFtpFiles = new FTPFile[ftpFiles.length];

    // put the pdf files at the beginning;
    int pdfIndex = 0;
    int otherIndex = ftpFiles.length - 1;
    for ( FTPFile ftpFile : ftpFiles )
    {
      final String fileName = ftpFile.getName();
      if ( fileName.length() >= 4 && fileName.toLowerCase().endsWith( ".pdf" ) )
      {
        sequencedFtpFiles[pdfIndex++] = ftpFile;
      }
      else
      {
        sequencedFtpFiles[otherIndex--] = ftpFile;
      }
    }
    return sequencedFtpFiles;
  }

  protected void processFtpFile( FTPFile ftpFile, FtpConnectionInfo connectionInfo )
  {
    if ( ftpFile == null )
    {
      log.warn( "ftpFile is null." );
      return;
    }

    // first check the file name, ignore the files which name are invalid;
    final String fileName = ftpFile.getName();
    log.info( "processing file: " + fileName );

    try
    {
      FtpFileNameInfo fileNameInfo = FtpFileNameInfo.parseFtpFileName( fileName );
      if ( fileNameInfo == null )
      {
        onInvalidFile( ftpFile, connectionInfo );
      }
      else
      {
        String fileDir = createSuccessDirectory( fileNameInfo );
        File file = ftpFileService.downloadFile( fileDir, ftpFile );
        if ( file == null )
        {
          log.warn( "download file abort. The file maybe creating." );
          return;
        }
        log.info( "downloaded file: " + file.getAbsolutePath() );

        // String onSiteDir = createOnSiteDirectory( fileNameInfo );
        // File onSiteFile = new File( onSiteDir + "/" + fileName );
        // FileUtilities.copyFile( file, onSiteFile );

        // go on process the file
        if ( !processFile( file, fileNameInfo ) )
        {
          onProcessFileFailed( connectionInfo, file, fileNameInfo );
        }
        else
        {
          onProcessFileSuccess( file, fileNameInfo );
        }

        // delete the onSite file if it still exists
        // onSiteFile.delete();
      }
    }
    catch ( Exception e )
    {
      log.warn( "processFtpFile()", e );
    }
  }

  protected boolean processFile( File file, FtpFileNameInfo fileNameInfo )
  {
    if ( fileNameInfo == null || file == null )
    {
      log.warn( "processFtpFiles(): Invalid parameters: file = " + file + "; fileNameInfo = " + fileNameInfo );
      throw new IllegalArgumentException( "Invalid parameters: file = " + file + "; fileNameInfo = " + fileNameInfo );
    }

    FtpFileNameInfo.FILE_TYPES enumFileType = fileNameInfo.getEnumFileType();
    if ( enumFileType == null )
    {
      log.warn( "processFtpFiles(): Invalid file type." );
      throw new IllegalArgumentException( "processFtpFiles(): Invalid file type." );
    }

    if ( enumFileType == FtpFileNameInfo.FILE_TYPES.PDF )
      return processPdfFile( file, fileNameInfo );

    if ( enumFileType == FtpFileNameInfo.FILE_TYPES.CSV )
      return processCsvFile( file, fileNameInfo );

    // don't support other file type;
    return false;

  }

  protected boolean processPdfFile( File file, FtpFileNameInfo fileNameInfo )
  {
    if ( fileNameInfo == null || file == null )
    {
      log.warn( "processPdfFile(): Invalid parameters: file = " + file + "; fileNameInfo = " + fileNameInfo );
      throw new IllegalArgumentException( "Invalid parameters: file = " + file + "; fileNameInfo = " + fileNameInfo );
    }

//    final String editionFileName = getEditionDocumentName( fileNameInfo );

    // delegate to the documentProcessService
//    if ( !documentProcessService.processDocument( fileNameInfo.getPublicationName(), fileNameInfo.getEditionDate(),
//                                                  fileNameInfo.getSectionName(), file, fileNameInfo.getStyle(),
//                                                  editionFileName ) )
//    {
//      String errDir = createFailedDirectory( fileNameInfo );
//      log.warn( documentProcessService.getErrorReason() );
//      getStatusLogger( errDir + "/errors" ).error( documentProcessService.getErrorReason() );
//      return false;
//    }
    return true;
  }

  protected boolean processCsvFile( File file, FtpFileNameInfo fileNameInfo )
  {
    return true;
  }

  protected String createFailedDirectory( String fileName )
  {
    String firstPart = getFirstPart( fileName );
    String dirString = localDownloadDir + "/" + firstPart + "_" + getDateString() + "/" + "Failed";
    createDirectory( dirString );
    return dirString;
  }

  protected String createFailedDirectory( FtpFileNameInfo fileNameInfo )
  {
    String dirString = getFailedDirectory( fileNameInfo );
    createDirectory( dirString );
    return dirString;
  }

  protected String getFailedDirectory( FtpFileNameInfo fileNameInfo )
  {
    return ( localDownloadDir + "/" + fileNameInfo.getPublicationName() + "_" + getDateString() + "/" + "Failed" );
  }

  protected String createSuccessDirectory( FtpFileNameInfo fileNameInfo )
  {
    String dirString = localDownloadDir + "/" + fileNameInfo.getPublicationName() + "_" + getDateString() + "/"
                       + "Success";
    createDirectory( dirString );
    return dirString;
  }

  protected String createOnSiteDirectory( FtpFileNameInfo fileNameInfo )
  {
    String dirString = localDownloadDir;
    createDirectory( dirString );
    return dirString;
  }

  protected boolean createDirectory( String dirString )
  {
    File dir = new File( dirString );
    // check existance
    if ( dir.exists() )
    {
      return dir.isDirectory();
    }

    if ( !dir.mkdirs() )
    {
      log.error( "can NOT create directory: " + dirString );
      return false;
    }
    log.info( "directory " + dirString + " created." );
    return true;
  }

  protected void onProcessFileSuccess( File file, FtpFileNameInfo fileNameInfo )
  {
    // do nothing
    log.info( "process file " + file.getName() + " success." );
  }

  protected void onInvalidFile( FTPFile ftpFile, FtpConnectionInfo connectionInfo )
  {
    final String fileName = ftpFile.getName();
    log.warn( "invalid ftp file, file name: " + fileName );
    String fileDir = createFailedDirectory( fileName );
    File file = null;
    try
    {
      file = ftpFileService.downloadFile( fileDir, ftpFile );
    }
    catch ( Exception e )
    {
      log.warn( e.toString() );
    }

    if ( file == null )
    {
      log.warn( "download file abort. The file maybe creating." );
    }
    else
    {
      // log to status file
      getStatusLogger( fileDir + "/errors" ).error( "invalid file name: " + file.getName() );

      // send email notification
      sendEmailNotification( connectionInfo, fileName );
    }
    log.info( "downloaded file: " + file.getAbsolutePath() );
  }

  protected void onProcessFileFailed( FtpConnectionInfo connectionInfo, File file, FtpFileNameInfo fileNameInfo )
  {
    // move this file to failed directory
    String dir = createFailedDirectory( fileNameInfo );

    getStatusLogger( dir + "/errors" ).error( "process file failed: " + file.getName() );
    getStatusLogger( dir + "/errors" ).error( "" ); // add an empty line

    String destFileName = dir + "/" + file.getName();
    boolean result = file.renameTo( new File( destFileName ) );
    log.info( "rename file to " + destFileName + ( result ? " success." : " failed." ) );
    log.info( "process file " + file.getName() + " failed." );

    sendEmailNotification( connectionInfo, file, fileNameInfo );
  }

  /*
   * This method is called when the file name format is in-correct.
   */
  protected void sendEmailNotification( FtpConnectionInfo connectionInfo, String fileName )
  {
    sendEmailNotification( connectionInfo.getEmail(), emailNotificationTitle, connectionInfo.getUser(), fileName, "",
                           "", "" );

  }

  /*
   * This method is called when processing the file failed.
   */
  protected void sendEmailNotification( FtpConnectionInfo connectionInfo, File file, FtpFileNameInfo fileNameInfo )
  {
    // distinguish the file type to get the error code and error reason.
    FtpFileNameInfo.FILE_TYPES enumFileType = fileNameInfo.getEnumFileType();
    if ( enumFileType == null )
    {
      log.warn( "processFtpFiles(): Invalid file type." );
      throw new IllegalArgumentException( "processFtpFiles(): Invalid file type." );
    }

    String publicationName = "";
    String editionName = "";
    String sectionName = "";

    sendEmailNotification( connectionInfo.getEmail(), emailNotificationTitle, connectionInfo.getUser(), file.getName(),
                           publicationName, editionName, sectionName );

  }

  protected static String getEmailFieldValue( String name, String reason )
  {
    return name + "(" + reason + ")";
  }

  protected void sendEmailNotification( String emailAddr, String emailTitle, String userName, String documentName,
                                        String publicationName, String editionName, String sectionName )
  {
    if ( emailService == null )
    {
      log.warn( "can NOT send email. EmailService not set." );
      return;
    }

    if ( emailAddr == null || emailAddr.length() == 0 || !emailService.isEmailAddressValid( emailAddr ) )
    {
      log.warn( "can NOT send email. invalid email address: " + emailAddr );
      return;
    }

    try
    {
      Map< String, String > tokens = new HashMap< String, String >();
      tokens.put( "user.name", userName );
      tokens.put( "document.name", documentName );
      tokens.put( "publication.name", publicationName );
      tokens.put( "edition.name", editionName );
      tokens.put( "section.name", sectionName );

      getEmailService().sendEmail( emailAddr, emailTitle, "invalidFtpFile.email", tokens, "text/html" );

      log.info( "Sent email To: " + emailAddr );

    }
    catch ( Exception e )
    {
      log.error( "Cannot sent document status update by email.", e );
    }

  }

  protected String getErrorDescription( int errorCode )
  {
    switch ( errorCode )
    {
      case FC_INVALIDFILE:
        return FD_INVALIDFILE;
      case FC_PROCESSFAILED:
        return FD_PROCESSFAILED;

      default:
        return "";
    }
  }

  protected String getFirstPart( String fileName )
  {
    if ( fileName == null || fileName.length() == 0 )
      return fileName;
    int offset = fileName.indexOf( '_' );
    if ( offset > 0 )
      return fileName.substring( 0, offset );

    offset = fileName.indexOf( '.' );
    if ( offset > 0 )
      return fileName.substring( 0, offset );

    return fileName;
  }

  protected String getDateString()
  {
    return formatDateToFtpFormat( new Date() );
  }

  private static String formatDateToFtpFormat( Date date )
  {
    if ( date == null )
      return null;

    SimpleDateFormat sdf = new SimpleDateFormat( DocNameUtil.FTP_FILENAME_DATE_FORMAT );
    FieldPosition pos = new FieldPosition( 0 );
    StringBuffer empty = new StringBuffer();
    StringBuffer dateString = sdf.format( date, empty, pos );
    return dateString.toString();
  }

  // ////////////////////////////////////////////////////////
  // getter/setter methods
  // ////////////////////////////////////////////////////////

  // ftpFileService
  public FtpFileService getFtpFileServie()
  {
    return ftpFileService;
  }

  public void setFtpFileService( FtpFileService ftpFileService )
  {
    this.ftpFileService = ftpFileService;
  }

  // localDownloadDir
  public String getLocalDownloadDir()
  {
    return localDownloadDir;
  }

  public void setLocalDownloadDir( String localDownloadDir )
  {
    this.localDownloadDir = localDownloadDir;
  }

  // ftpConnectionInfoService
  public FtpConfigService getFtpConfigService()
  {
    return ftpConfigService;
  }

  public void setFtpConfigService( FtpConfigService ftpConfigService )
  {
    this.ftpConfigService = ftpConfigService;
  }

  public EmailService getEmailService()
  {
    return emailService;
  }

  public void setEmailService( EmailService emailService )
  {
    this.emailService = emailService;
  }

  public String getEmailNotificationTitle()
  {
    return emailNotificationTitle;
  }

  public void setEmailNotificationTitle( String emailNotificationTitle )
  {
    this.emailNotificationTitle = emailNotificationTitle;
  }
  
  public void setShouldRun( String sShouldRun )
  {
    shouldRun = Boolean.valueOf( sShouldRun );
  }
  
}
