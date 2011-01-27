/*
 * $Id: FtpFileInfo.java,v 1.1 2008/05/05 21:38:53 brightc Exp $
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

package cg.publish.task.ftpTask;

import java.io.Serializable;
import java.util.Date;

/**
 * Used to store a ftp pdf file's information.
 * 
 * @author Charles Deng
 */
public class FtpFileInfo implements Serializable {

	private static final long serialVersionUID = -7185032613639963696L;
	
	private String fileName;
	private Date created;
	private long fileSize;
	private String status; // could be : null, processing
	
	public FtpFileInfo (String fileName, Date created, long fileSize) {
		this.fileName = fileName;
		this.created = created;
		this.fileSize = fileSize;
	}
	
	public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FtpFileInfo)) return false;
        FtpFileInfo o = (FtpFileInfo)obj;
        return (fileName.equals(o.getFileName()) && created.equals(o.getCreated()));
	}
	
	public int hashCode() {
		return fileName.hashCode() + 31*created.hashCode();
	}
	
	public String toString() {
		return "FileName -> " + fileName;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}
