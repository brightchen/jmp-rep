/*
 * $Id: HelperTest.java,v 1.1 2007/05/22 14:43:39 dorelv Exp $
 *
 * This unpublished source code contains trade secrets and copyrighted
 * materials that are the proprietary property of iseemedia, Inc.
 * Unauthorized use, copying or distribution of this source code or the
 * ideas contained herein is a violation of U.S. and international laws
 * and is strictly prohibited.
 *
 * Copyright (c) 2002,2003,2004 iseemedia, Inc. All Rights Reserved.
 *
 */

package cg;

/**
 * @version $Revision: 1.1 $
 * @author dvleju
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;


/**
 * @author dvleju
 *
 * Helper Test class for reading saving files
 */
public class HelperTest extends TestCase {
	


	private static final String fileIn = "C:\\projects\\all_mapitags2.txt";

	private static final String fileOut = "C:\\projects\\mapitags_array_str2.txt";


	public void testP() {

		System.out.println("loading file: " + fileIn);
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileIn));
			BufferedWriter out = new BufferedWriter(new FileWriter(fileOut));
			String line = null;
			int i = 1;
			while ((line = in.readLine()) != null){
				if (line.startsWith(",")){
					String a[] = line.split(" ");
					//out.write("_T(\"" + a[0].substring(1) + "\"), \t\t\t\t// " + (i++) + "\n");
					out.write(a[0].substring(1) + ", \t\t\t\t// " + (i++) + "\n");
				}
			}
			in.close();
			out.close();
		}catch (Exception e) {
			System.out.println("Saving file: Failed Message:\n"
					+ e.getMessage());
			e.printStackTrace();
			
		}
	}
	

	/**
	 * Convenience method to read a file
	 * @param fileName
	 * @return the file content as array of bytes
	 */
	public static byte[] loadFile(String fileName) {
		byte[] data = new byte[0];
		try {
			InputStream in = new FileInputStream(fileName);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (Exception e) {
			System.out.println("Cannot read file. " + e.getMessage());
		}
		System.out.println("Have read file of: " + data.length + " bytes.");
		return data;
	}
	
	/**
	 * Convenience method to save a file
	 * @param fileName
	 */
	public static void saveFile(String fileName, byte [] data) {
		try {
			OutputStream out = new FileOutputStream(fileName);
			out.write(data);
			out.close();
		} catch (Exception e) {
			System.out.println("Cannot save file. " + e.getMessage());
		}
		System.out.println("Have save file of: " + data.length + " bytes.");
	}
	
	protected String cleanFileName(String source) throws Exception{
    	String filename = "";
    	if (source != null){
    		int j = source.indexOf(".ja");
    		if ( j > 1)
    			filename = source.substring(0, j+4);
    		else
    			filename = source;
    	}
    	
    	return filename;
    }
	
	public void testFilename(){
		try{
		String source = "my-build-p900-us.jad/#";
		String file = cleanFileName(source);
		System.out.println("File: '" + file + "'");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}