/*
 * $Id: FtpFileInfoMap.java,v 1.1 2008/05/05 21:38:54 brightc Exp $
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Used as a wrapper class of map information of pdf files' status.
 * 
 * @author Charles Deng
 */
public class FtpFileInfoMap implements Serializable {

	private static final long serialVersionUID = -3558763122036540629L;

	private Map<String, List<FtpFileInfo>> map = new HashMap<String, List<FtpFileInfo>>();

	public Map<String, List<FtpFileInfo>> getMap() {
		return map;
	}
	
}
