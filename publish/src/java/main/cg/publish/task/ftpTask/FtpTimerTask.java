package cg.publish.task.ftpTask;

import java.util.List;
import java.util.TimerTask;
import cg.common.logging.Logger;
import cg.publish.service.FtpFileService;
import cg.publish.service.FtpUserService;
import cg.publish.xmlbinding.ftpusers.FtpUserType;

/**
 * Run as a background process for scheduled FTP document management. Provide the function of fetching pdf files from
 * FTP server periodically.
 */
public class FtpTimerTask extends TimerTask
{

  private static final Logger log = Logger.getLogger( FtpTimerTask.class );

  private FtpUserService ftpUserService;
  private FtpFileService ftpFileService;

  private String ftpServer;
  private String localDownloadDir;

  private int count = 0;

  @Override
  public void run()
  {
    log.info( " *** FTP timer task, count -> " + count++ );

    try
    {
      List< FtpUserType > userList = ftpUserService.getFtpUsers();
      for ( FtpUserType user : userList )
      {
        ftpFileService.startFtpConnection( ftpServer, user.getName(), user.getPassword() );
        if ( ftpFileService.hasPdfFile( user.getName() ) )
        {
//          List< File > fileList = ftpFileService.downloadPdfFiles( localDownloadDir, user.getName() );
//          processFtpFiles( fileList, publication, user.getStyle() );
        }
        else
        {
          log.info( " *** No pdf file found on FTP server for user -> " + user.getName() );
        }
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

  public FtpUserService getFtpUserService()
  {
    return ftpUserService;
  }

  public void setFtpUserService( FtpUserService ftpUserService )
  {
    this.ftpUserService = ftpUserService;
  }

  public FtpFileService getFtpFileService()
  {
    return ftpFileService;
  }

  public void setFtpFileService( FtpFileService ftpFileService )
  {
    this.ftpFileService = ftpFileService;
  }


  public String getFtpServer()
  {
    return ftpServer;
  }

  public void setFtpServer( String ftpServer )
  {
    this.ftpServer = ftpServer;
  }

  public String getLocalDownloadDir()
  {
    return localDownloadDir;
  }

  public void setLocalDownloadDir( String localDownloadDir )
  {
    this.localDownloadDir = localDownloadDir;
  }

}
