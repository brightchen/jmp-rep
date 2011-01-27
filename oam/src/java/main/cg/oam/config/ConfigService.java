/*
 * $Id: ConfigService.java,v 1.1 2007/05/22 14:43:33 dorelv Exp $
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

package cg.oam.config;

import cg.common.logging.Logger;


/**
 * Helper class for getting server config file location from System.properties
 * The IT Administartor should set config file location in the batch file used to launch the Application Server
 * as for example: -Diseedocs.config.file="C:\config\iseedocs.properties"
 * 
 * @author dorel vleju
 */
public class ConfigService{

  static Logger _log = Logger.getLogger(ConfigService.class);
  

  public ConfigService() throws Exception{
	  
  }
}