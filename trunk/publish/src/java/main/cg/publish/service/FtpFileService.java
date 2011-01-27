/*
 * $Id: FtpFileService.java,v 1.3 2008/05/05 21:38:52 brightc Exp $
 *
 * This unpublished source code contains trade secrets and copyrighted
 * materials that are the proprietary property of iseemedia, Inc.
 * Unauthorized use, copying or distribution of this source code or the
 * ideas contained herein is a violation of U.S. and international laws
 * and is strictly prohibited.
 *
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 *
 */
package cg.publish.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

/**
 * Interface for FTP File Service.
 *
 * @author Charles Deng
 */
public interface FtpFileService {

	/**
	 * @param localDownloadDir - local directory that hold downloaded FTP files
	 * @return list of local files that downloaded
	 */
	public List<File> downloadPdfFiles(String localDownloadDir, String userName);
	
	public boolean hasPdfFile(String userName);
	
	public void startFtpConnection(String ftpServer, String username, String password) throws Exception;
	
	public void closeFtpConnection();
	
	public FTPFile[] listFtpFiles() throws IOException;
	public List< String > listFileName();
	public File downloadFile( String localDownloadDir, FTPFile ftpFile ) throws FileNotFoundException, IOException;
	
}
