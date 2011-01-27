/*
 * This unpublished source code contains trade secrets and copyrighted
 * materials that are the proprietary property of iseemedia, Inc.
 * Unauthorized use, copying or distribution of this source code or the
 * ideas contained herein is a violation of U.S. and international laws
 * and is strictly prohibited.
 *
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 *
 */

package cg.publish.web.util;

import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * A utility class for Publisher web application
 * 
 * @author Charles Deng
 *
 */
public class PublisherUtil {

	/**
	 * @param downloadLink - such as "C:/dir/a.pdf"
	 * 
	 * @return - file name in the downloadLink, such as "a.pdf".
	 */
	public static String parseFileName(String downloadLink) {
		String lastToken = null;
		StringTokenizer strToken = new StringTokenizer(downloadLink, "/");
		while (strToken.hasMoreTokens()) {
			lastToken = strToken.nextToken();
		}
		return lastToken;
	}
	
	public static String encodePassword(String password, String algorithm) throws NoSuchAlgorithmException {
        byte[] unencodedPassword = password.getBytes();
        MessageDigest messageDigest = null;        
        messageDigest = MessageDigest.getInstance(algorithm);      
        messageDigest.reset();
        messageDigest.update(unencodedPassword);
        byte[] encodedPassword = messageDigest.digest();
        StringBuffer strBuffer = new StringBuffer();
        for (byte anEncodedPassword : encodedPassword) {
            if ((anEncodedPassword & 0xff) < 0x10) {
            	strBuffer.append("0");
            }
            strBuffer.append(Long.toString(anEncodedPassword & 0xff, 16));
        }
        return strBuffer.toString();
    }
	
	public static String getRandomNumberAsStr(int digits) {
		Long randomCode = new Random(new Date().getTime()).nextLong();
		return randomCode.toString().substring(1, digits+1);
	}
	
	public static String getFileAsStr(String filePath) throws IOException {
		FileInputStream xml = new FileInputStream(filePath);
		byte[] xmlBytes = new byte[xml.available()];
		xml.read(xmlBytes);
		return new String(xmlBytes);
	}
	
}
