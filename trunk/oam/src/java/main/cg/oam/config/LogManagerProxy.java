/*
 * $Id: LogManagerProxy.java,v 1.1 2007/05/22 14:43:34 dorelv Exp $
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

import java.util.Properties;

import cg.common.logging.Level;
import cg.common.logging.Logger;


/**
 * Proxy to update the Logging settings
 * @author dorel vleju
 */
public class LogManagerProxy {
	
	public static final String log4j_threshold = "log4j.threshold";
	public static final String log4j_appender_F_File = "log4j.appender.F.File";
	
	private static Logger _log = Logger.getLogger(LogManagerProxy.class);
	
	private static LogManagerProxy _instance;
	
	private Level _logLevel = Level.DEBUG;
	private String _logsDirectory = "/usr/local/server/logs";

	public LogManagerProxy() {
		
	}
	
	public static LogManagerProxy getInstance() {
		if (_instance == null){
			_instance = new LogManagerProxy();
			// load current configuration
			try{
				Properties properties = PropertiesManager.getPropertiesManager().getLogProperties();
				_instance.setLogLevel(Level.getLevelByName(properties.getProperty(log4j_threshold)));
				String logsDirectory = "";
				String logFileName = properties.getProperty(log4j_appender_F_File);
				String delimiter = "/";
				int j = logFileName.lastIndexOf(delimiter);
				if (j == -1){
					delimiter = "\\";
					j = logFileName.lastIndexOf(delimiter);
				}
				if (j != -1){
					logsDirectory = logFileName.substring(0, j);
				}
				_instance.setLogsDirectory(logsDirectory);
			}catch(Exception  e){
				_log.error("Unable to load current logging settings.", e);
			}
		}
		return _instance;
	}
	
	public static boolean restoreDefaults(){
		_instance = new LogManagerProxy();
		// do not load settings from configuration file
		return true;
	}
	
	public boolean save(){
		try{
			PropertiesManager.getPropertiesManager().updateLogProperties(getSettings());
			_log.info("Have saved the current logging settings to the properties file.");
			return true;
		}catch(Exception  e){
			_log.error("Unable to save current logging settings.", e);			
		}
		
		return false;
	}
	
	public Properties getSettings(){
		Properties settings = new Properties();
		
		settings.put(log4j_threshold, _logLevel.getName());
		settings.put(log4j_appender_F_File, getLogsDirectory());

		return settings;
	}


  public void setLogDebugOff(){
	  setLogLevel(Level.INFO);
  }
  
  public void setLogDebugOn(){
	  setLogLevel(Level.DEBUG);
  }
  
  public void setLevel(String levelName){
	  _logLevel = Level.getLevelByName(levelName);
	  Logger.setLogLevel(_logLevel);
  }
  
  public void setLogLevel(Level level){
	  _logLevel = level;
	  Logger.setLogLevel(_logLevel);
  }
  
  public Level getLogLevel(){
	  return _logLevel;
  }
  
  public String getLogsDirectory(){
	  return _logsDirectory;
  }

	public void setLogsDirectory(String logsDirectory) {
		_logsDirectory = logsDirectory;
	}

}
