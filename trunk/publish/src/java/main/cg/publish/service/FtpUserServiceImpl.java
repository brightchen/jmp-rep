/*
 * $Id: FtpUserServiceImpl.java,v 1.1 2007/07/05 21:45:34 charlesd Exp $
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
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import cg.publish.xmlbinding.ftpusers.FtpUserType;
import cg.publish.xmlbinding.ftpusers.FtpUsersType;

/**
 * The implementation of FTP User Service.
 *
 * @author Charles Deng
 */
public class FtpUserServiceImpl implements FtpUserService {

	private String ftpUsersFilePath;
	private String ftpUsersFilePathNew;

	private JAXBContext context;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	private JAXBElement<?> element;
	
	public FtpUserServiceImpl() throws JAXBException {
		context = JAXBContext.newInstance( "cg.iseepublish.xmlbinding.ftpusers" );		
		marshaller = context.createMarshaller();
		unmarshaller = context.createUnmarshaller();
	}
	
	public List<FtpUserType> getFtpUsers() throws FileNotFoundException, JAXBException {
		element = (JAXBElement<?>)unmarshaller.unmarshal(new FileInputStream(ftpUsersFilePath));
		return ((FtpUsersType)element.getValue()).getFtpUser();
	}
	
	public void saveFtpUser(FtpUserType ftpUser) throws FileNotFoundException, JAXBException {
		boolean saved = false;
		List<FtpUserType> userList = getFtpUsers();
		for (FtpUserType u : userList) {
			if (u.getName().equals(ftpUser.getName())) {
				u.setPassword(ftpUser.getPassword());
				u.setPublication(ftpUser.getPublication());
				u.setStyle(ftpUser.getStyle());
				saved = true;
			}
		}
		if (!saved) {
			userList.add(ftpUser);
		}
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(element, new FileOutputStream(ftpUsersFilePathNew));
	}

	public String getFtpUsersFilePath() {
		return ftpUsersFilePath;
	}

	public void setFtpUsersFilePath(String ftpUsersFilePath) {
		this.ftpUsersFilePath = ftpUsersFilePath;
	}

	public String getFtpUsersFilePathNew() {
		return ftpUsersFilePathNew;
	}

	public void setFtpUsersFilePathNew(String ftpUsersFilePathNew) {
		this.ftpUsersFilePathNew = ftpUsersFilePathNew;
	}
	
}
