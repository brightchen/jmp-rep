/*
 * $Id: EmailService.java,v 1.1 2007/05/22 14:40:07 dorelv Exp $
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

package cg.common.email;

import java.util.Map;


/**
 * Service interface for sending emails
 * 
 * @author dorel vleju
 */
public interface EmailService {
  
	/**
	 * Sends email using a template message
	 * @param to the recipent address/es
	 * @param subject the email subject
	 * @param emailTemplate template as string
	 * @param contentType content type to be set within the body of message
	 * @param tokens tokens to be replaced within the template
	 * @throws Exception
	 */
	void sendEmail(String to, String subject, String emailTemplate, Map tokens, String contentType) throws Exception;
	
	/**
	 * @param from the sender address
	 * @param to the recipent address/es
	 * @param bcc the recipent bcc address/es
	 * @param subject subject the email subject
	 * @param content the content of the body, may contain tokens
	 * @param contentType content type to be set within the body of message
	 * @param tokens tokens tokens to be replaced within the content
	 * @throws Exception
	 */
	void sendEmail(String from, String to, String bcc, String subject, String content, Map tokens, String contentType) throws Exception;
	
	/**
	 * @param address
	 * @return true if the email address is proper formated ie. xxx@yy.zz
	 */
	public boolean isEmailAddressValid(String address);
	
	public String getTestEmailAddress();
}
