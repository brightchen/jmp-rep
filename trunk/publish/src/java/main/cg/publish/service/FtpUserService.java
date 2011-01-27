/*
 * $Id: FtpUserService.java,v 1.1 2007/07/05 21:45:34 charlesd Exp $
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

import java.util.List;

import cg.publish.xmlbinding.ftpusers.FtpUserType;

/**
 * Interface for FTP User Service.
 *
 * @author Charles Deng
 */
public interface FtpUserService {

	public List<FtpUserType> getFtpUsers() throws Exception;	
	public void saveFtpUser(FtpUserType ftpUser) throws Exception;
	
}
