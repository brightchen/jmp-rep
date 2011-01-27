/*
 * $Id: FtpConfigServiceImpl.java,v 1.1 2008/05/05 21:38:53 brightc Exp $
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import cg.common.logging.Logger;
import cg.publish.xmlbinding.ftpConfig.FtpConnectionInfo;
import cg.publish.xmlbinding.ftpConfig.FtpConnectionInfos;

/**
 * The implementation of FTP connection Service.
 * The example FtpConfig file
 * <ftpConnectionInfos>
 *   <ftpConnectionInfo server="server1" user="user1" password="password1" />
 *   <ftpConnectionInfo server="server2" user="user2" password="password2" />
 * </ftpConnectionInfos>
 *
 * @author Bright Chen
 */
public class FtpConfigServiceImpl implements FtpConfigService
{
    private static final Logger log = Logger.getLogger( FtpConfigServiceImpl.class );
    
    private String ftpConfigFilePath;

    private JAXBContext context;
    //private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    
    public FtpConfigServiceImpl() throws JAXBException 
    {
        context = JAXBContext.newInstance( "cg.iseepublish.xmlbinding.ftpConfig" );       
        //marshaller = context.createMarshaller();
        unmarshaller = context.createUnmarshaller();
    }
    
    public List< FtpConnectionInfo > getFtpConnectionInfoList() throws FileNotFoundException, JAXBException 
    {
        log.debug( "ftpConfigFilePath: " + ftpConfigFilePath );
        JAXBElement<?> element = ( JAXBElement<?> )unmarshaller.unmarshal( new FileInputStream( ftpConfigFilePath) );
        log.debug( "JAXBElement: " + element );
        
        return ( (FtpConnectionInfos)element.getValue() ).getFtpConnectionInfo();
    }

    //ftpConfigFilePath
    public String getFtpConfigFilePath()
    {
        return ftpConfigFilePath;
    }
    public void setFtpConfigFilePath( String ftpConfigFilePath )
    {
        this.ftpConfigFilePath = ftpConfigFilePath;
    }
}
