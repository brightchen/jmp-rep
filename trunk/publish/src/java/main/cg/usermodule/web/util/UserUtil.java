package cg.usermodule.web.util;


import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
public class UserUtil {

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
