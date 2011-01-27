/*
 * $Id: Logger.java,v 1.1 2007/05/22 14:40:08 dorelv Exp $
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

package cg.common.logging;

import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggerFactory;


/**
 * A wrapper class for Log4J Logger
 * A different implementation might be used later
 * @author dorel vleju
 * 
 */

public class Logger extends org.apache.log4j.Logger {
	
	static final LoggerFactory _categoryFactory = new IseedocsCategoryFactory();
	static Logger _engineLogger = null;
	
	protected Logger(String name) {
		super(name);
	}

	/**
	 * Retrieve a logger named according to the value of the <code>name</code>
	 * parameter. If the named logger already exists, then the existing instance
	 * will be returned. Otherwise, a new instance is created.
	 * 
	 * <p>
	 * By default, loggers do not have a set level but inherit it from their
	 * neareast ancestor with a set level. This is one of the central features
	 * of log4j.
	 * 
	 * @param name
	 *            The name of the logger to retrieve.
	 */
	static public Logger getLogger(String name) {
		Object logger = LogManager.getLogger(name, _categoryFactory);
		if (logger instanceof Logger){
			return (Logger)logger; 
		}else{
			System.err.println("!!!!!! logger is instance of '" + logger.getClass().getName() + "' !!!!!!!");
			return getEngineLogger();
		}
	}
	
	static public Logger getEngineLogger() {
		if (_engineLogger == null){
			_engineLogger = (Logger)LogManager.getLogger("engine", _categoryFactory);
		}
		System.out.println("Requesting 'engine' Logger....");
		return _engineLogger;
	}

	/**
	 * Shorthand for <code>getLogger(clazz.getName())</code>.
	 * 
	 * @param clazz
	 *            The name of <code>clazz</code> will be used as the name of
	 *            the logger to retrieve. See {@link #getLogger(String)} for
	 *            more detailed information.
	 */
	static public Logger getLogger(Class clazz) {

		Object logger = LogManager.getLogger(clazz.getName(), _categoryFactory);
		if (logger instanceof Logger){
			return (Logger)logger; 
		}else{
			System.err.println("!!!!!! logger is instance of '" + logger.getClass().getName() + "' !!!!!!!");
			return getEngineLogger();
		}
	}
	
	static public void setLogLevel(Level level){
		_engineLogger.setLevel(level);
		Logger.getRootLogger().setLevel(level);
	}

}
