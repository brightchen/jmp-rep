package cg.publish.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import cg.common.logging.Logger;
import cg.publish.task.ftpTask.FtpFileInfo;
import cg.publish.task.ftpTask.FtpFileInfoMap;
import cg.publish.web.util.Constants;

/**
 * The implementation of FTP File Service.
 */
public class FtpFileServiceImpl implements FtpFileService 
{
    
    private static final Logger log = Logger.getLogger(FtpFileServiceImpl.class);
    
    private FTPClient ftpClient;
    private FTPFile[] ftpFiles;
    
    private FtpFileInfoMap ftpFileInfoMap;
    
    public FtpFileInfoMap getFtpFileInfoMap() {
        return ftpFileInfoMap;
    }

    public void setFtpFileInfoMap(FtpFileInfoMap ftpFileInfoMap) {
        this.ftpFileInfoMap = ftpFileInfoMap;
    }

    public FtpFileServiceImpl(){
    }
    
    public FtpFileServiceImpl(
            String ftpServer, 
            String username, 
            String password) throws Exception {
        
        startFtpConnection(ftpServer, username, password);
    }
    
    
    public boolean hasPdfFile(String userName) {
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.getName().toLowerCase().endsWith(".pdf")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ftpFileInfoMap.getMap().remove(userName);
        //log.info(" *** Removed ftpFileInfoList of ftpFileInfoMap for ftpUser -> " + userName);
        return false;
    }
    
    @SuppressWarnings("unchecked")
    public List<File> downloadPdfFiles(String localDownloadDir, String userName) {
        List<File> fileList = new ArrayList<File>();        
        List<FtpFileInfo> ftpFileInfoList = (List)ftpFileInfoMap.getMap().get(userName);
        if (ftpFileInfoList == null || ftpFileInfoList.size() == 0) {
            ftpFileInfoMap.getMap().put(userName, createFtpFileInfoList());
            log.info(" *** Added new ftpFileInfoList to ftpFileInfoMap for ftpUser -> " + userName);
        } else {
            try {
                FTPFile[] ftpFiles = ftpClient.listFiles();
                for (FTPFile ftpFile : ftpFiles) {
                    String ftpFileName = ftpFile.getName();
                    if (isStableFtpFile(ftpFileInfoList, ftpFile)) {
                        File localFile = new File(localDownloadDir + "/" + ftpFileName);
                        OutputStream localFileOutputStream = new FileOutputStream(localFile);
                        ftpClient.retrieveFile(ftpFileName, localFileOutputStream);
                        localFileOutputStream.close();
                        ftpClient.deleteFile(ftpFileName);
                        fileList.add(localFile);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }       
        return fileList;
    }
    
    private boolean isStableFtpFile(
            List<FtpFileInfo> ftpFileInfoList, 
            FTPFile ftpFile) {
        
        String ftpFileName = ftpFile.getName();
        if (!ftpFileName.toLowerCase().endsWith(".pdf")) return false;
        boolean isNewFtpFile = true;
        for (FtpFileInfo ftpFileInfo : ftpFileInfoList) {
            if (ftpFileInfo.getFileName().equals(ftpFileName)) {
                isNewFtpFile = false;
                if (ftpFileInfo.getCreated().equals(ftpFile.getTimestamp().getTime())) {
                    if (ftpFileInfo.getFileSize() == ftpFile.getSize()) {
                        if (ftpFileInfo.getStatus() == null || !ftpFileInfo.getStatus().equals(Constants.FTP_PROCESSING_FILE)) {
                            ftpFileInfo.setStatus(Constants.FTP_PROCESSING_FILE);
                            log.info(" *** Start processing old ftpFile, fileName -> " + ftpFileName);
                            return true;
                        } else {
                            log.info(" *** Old ftpFile is still under processing, bypass it, fileName -> " + ftpFileName);
                            return false;                       
                        }
                    } else {
                        ftpFileInfo.setFileSize(ftpFile.getSize());
                        log.info(" *** Old ftpFile is still under uploading, bypass it, fileName -> " + ftpFileName);
                        return false;
                    }
                } else {
                    ftpFileInfo.setCreated(ftpFile.getTimestamp().getTime());
                    ftpFileInfo.setFileSize(ftpFile.getSize());
                    log.info(" *** Old ftpFile is recreated, bypass it, fileName -> " + ftpFileName);
                    return false;
                }
            }
        }
        if (isNewFtpFile) {
            FtpFileInfo ftpFileInfoNew = 
                new FtpFileInfo(ftpFile.getName(), ftpFile.getTimestamp().getTime(), ftpFile.getSize());
            ftpFileInfoList.add(ftpFileInfoNew);
            log.info(" *** Added new ftpFile to ftpFileInfoList, fileName -> " + ftpFileName);
        }
        return false;
    }

    private List<FtpFileInfo> createFtpFileInfoList() {
        List<FtpFileInfo> list = new ArrayList<FtpFileInfo>();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.getName().toLowerCase().endsWith(".pdf")) {
                    FtpFileInfo ftpFileInfoNew = 
                        new FtpFileInfo(ftpFile.getName(), ftpFile.getTimestamp().getTime(), ftpFile.getSize());
                    list.add(ftpFileInfoNew);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return list;
    }

    public void startFtpConnection(
            String ftpServer, 
            String username, 
            String password) throws SocketException, IOException {

        
        closeFtpConnection();
        ftpClient = new FTPClient();        
        ftpClient.connect(ftpServer);
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            log.error(" *** FTP connection is not completed positively.");
            ftpClient.disconnect();
        }
        if (!ftpClient.login(username, password)) {
            log.error(" *** FTP connection login fail, please check username/password.");
            ftpClient.disconnect();
            ftpClient = null;    //release it
        }
        else
        {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
        }
    }
    
    public void closeFtpConnection() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                log.error(" *** Fail to disconnect ftp client.");
                e.printStackTrace();
            }
        }
    }

    ////////////////////////////////////////////////////////
    // Followings are methods add by Bright Chen
    ////////////////////////////////////////////////////////
    
    //we can cache the file list
    public FTPFile[] listFtpFiles() throws IOException
    {
        if( ftpClient == null )
        {
            log.warn( "listFtpFiles(): ftp client not initialized." );
            return null;
        }
        ftpFiles = ftpClient.listFiles();
        return ftpFiles;
    }
    
    public List< String > listFileName() 
    {
        try 
        {
            FTPFile[] ftpFiles = listFtpFiles();
            if( ftpFiles == null || ftpFiles.length == 0 )
                return null;
            
            List< String > fileNames = new ArrayList< String >( ftpFiles.length );
            for( FTPFile ftpFile : ftpFiles ) 
            {
                fileNames.add( ftpFile.getName() );
            }
            return fileNames;
        } 
        catch( IOException e ) 
        {
            log.warn( "listFileName() IOException: " + e.toString() );
            return null;
        }
    }
    
    /*
     * download file from ftp-server, keep the file in <localDownloadDir>
     * return the local file
     * 
     */
    public File downloadFile( String localDownloadDir, FTPFile ftpFile ) 
            throws FileNotFoundException, IOException
    {
        String ftpFileName = ftpFile.getName();
        File localFile = new File( localDownloadDir + "/" + ftpFileName );
        if( localFile.exists() )
        {
            log.warn( "The file exists, it has been downloaded or downloading." );
            return null;
        }

        if( !isStableFtpFile( ftpFile ) )
            return null;

        OutputStream localFileOutputStream = new FileOutputStream( localFile );
        boolean result = ftpClient.retrieveFile( ftpFileName, localFileOutputStream );
        if( result )
        {
            localFileOutputStream.flush();
            localFileOutputStream.close();

            log.debug( "file downloaded, going to remove it from ftp server: " + ftpFileName );
            ftpClient.deleteFile( ftpFileName );
        }
        else
        {
            localFileOutputStream.close();
            localFile.delete();
            localFile = null;
        }
        
        return localFile;
    }

    protected boolean isStableFtpFile( FTPFile ftpFile )
    {
        try
        {
            long preSize = ftpFile.getSize();
            Thread.sleep( 1000 );
            
            ftpFiles = ftpClient.listFiles();
            FTPFile thePostFtpFile = null;
            for( FTPFile postFtpFile : ftpFiles )
            {
                log.info( "file: " + postFtpFile );
                if( postFtpFile.getName().equalsIgnoreCase( ftpFile.getName() ) )
                {
                    thePostFtpFile = postFtpFile;
                    break;
                }
            }
            if( thePostFtpFile == null )
            {
                log.warn( "The original ftp file deleted: " + ftpFile.getName() );
                return false;
            }
            
            long postSize = thePostFtpFile.getSize();
            log.info( "preSize=" + preSize + "; postSize=" + postSize );
            return ( preSize == postSize );
        }
        catch( Exception e )
        {
            log.debug( e.toString() );
            return false;
        }
      
    }
}
