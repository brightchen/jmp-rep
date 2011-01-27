/*
 * $Id: IseedocsCategoryFactory.java,v 1.1 2007/05/22 14:40:08 dorelv Exp $
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

import org.apache.log4j.spi.LoggerFactory;


/**
 * A wrapper class for LoggerFactory
 * A different implementation might be used later
 * @author dorel vleju
 * 
 */


public class IseedocsCategoryFactory implements LoggerFactory {
      
  public IseedocsCategoryFactory() {
  }    
    
  public Logger makeNewLoggerInstance(String name) {
    return new Logger(name);
  }    
}
