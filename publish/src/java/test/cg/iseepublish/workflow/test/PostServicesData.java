package cg.iseepublish.workflow.test;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/*
 * Azam Siddiqi
 * July 20/2007
 */


public class PostServicesData {

    public static void main(String args[]) throws IOException{
    	try{
    	PostServicesData post = new PostServicesData();
    	//post.postXmlMOToServlet();
    	post.tempCode();
        } catch (Exception e) {
        	System.out.println("EXCEPTION" + e);
        } 
    }
 
   
    
    public void tempCode()
    {
    
    	String v = "1186948725041";
    	Long l = new Long(v);
    	long lv = l.longValue();
    	Date dl = new Date(lv);
    	System.out.println("DAte:" + dl);
    	
    	String lastNum = "-1";
    	
    	long lastNumber = Long.parseLong(lastNum);
    	System.out.println("Lonmg:" + lastNumber);
    	String xmlResponse = "s522@The client should be activated first.";
    	if(xmlResponse.indexOf("@") != -1)
    	{
	    	String code = xmlResponse.substring(0,xmlResponse.indexOf("@"));
	    	String msg = xmlResponse.substring(xmlResponse.indexOf("@")+1,xmlResponse.length());
	    	System.out.println("Code:" + code);
	    	System.out.println("Msg:" + msg);
    	}
    	Calendar cal = Calendar.getInstance();
    	Date d = cal.getTime();
    	int day = cal.get(Calendar.DAY_OF_MONTH);
    	System.out.println("Day:" + day);
    	cal.set(Calendar.DATE, day-7);
    	//cal.roll(Calendar.DATE, true);
    	System.out.println("DAte:" + cal.getTime());
    	System.out.println("DAte:" + new Date());
    }
   
        
   public void postXmlMOToServlet()
    { 		
        try{
        //String data = "services";
        //String data = "catalog";
        String data = "confirmActivation";
        URLConnection connection = sendQuery(data,
                        "http://localhost:8080/iseepublish/channels");

     
        System.out.println("2");
        InputStream ip = connection.getInputStream();
        byte[] a = getStreamContents(ip);
        
        System.out.println("Content: " + new String(a));
                
        Map map = connection.getHeaderFields();
        for (Iterator i = map.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry entry = (Map.Entry) i.next();
            System.out.println(entry.getKey() + ":*****: " + entry.getValue());
        }
        }catch(Exception e)
		{
        	System.out.println("EXCEPTION caught: " + e);
		}
        
    }

   
   
    public static URLConnection sendQuery(
                            String content,String url) throws IOException {

        URL ur = new URL(url);
        URLConnection conn = (HttpURLConnection)ur.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("User-Agent", "UnwiredTec Enterflex Profiler using example");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");                      
        OutputStream os = conn.getOutputStream();
		os.write("command=".getBytes());
		os.write(content.getBytes());
		os.write(("&phoneid=").getBytes());
		os.write(("2").getBytes()); 
		os.write(("&timestamp=").getBytes());
		os.write(("43563456345").getBytes()); 		
		os.write(("&number=").getBytes());
		os.write(("1").getBytes());
		os.write(("&key=").getBytes());
		os.write(("asdf22352asdf86a99s7da345").getBytes());
		
        os.flush();
        os.close();
        conn.connect();
        return conn;
    }
    
    

    public byte[] getStreamContents(InputStream stream)
    throws IOException
	{
	    BufferedInputStream bis = new BufferedInputStream(stream);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte ab[] = new byte[2048];
	    int i;
	    while((i = bis.read(ab, 0, 2048)) != -1) 
	    {
	        baos.write(ab, 0, i);
	        if(i == -1)
	            break;
	    }
	    baos.flush();
	    baos.close();
	    return baos.toByteArray();
	}     
}