/*
 * $Id: FtpConfigService.java,v 1.1 2008/05/05 21:38:53 brightc Exp $
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

import cg.publish.xmlbinding.ftpConfig.FtpConnectionInfo;

/**
 * Interface for FTP connection Service.
 *
 * @author Bright Chen
 */
public interface FtpConfigService 
{
    public List< FtpConnectionInfo > getFtpConnectionInfoList() throws Exception;    
}
