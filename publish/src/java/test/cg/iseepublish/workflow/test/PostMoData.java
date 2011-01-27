package cg.iseepublish.workflow.test;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

/*
 * Azam Siddiqi
 * July 10/2007
 */


public class PostMoData 
{

    public static final String DEF_URL = "http://localhost:8080/iseepublish/MosmsDoPost";
    public static void main( String args[] ) throws IOException
    {
    	PostMoData post = new PostMoData();
    	while( true )
    	{
            try
            {
        	    System.out.print( "Please input msisdn,operation: " );
        	    byte[] inputBuffer = new byte[ 128 ];
        	    int readLen = System.in.read( inputBuffer );
        	    if( readLen <=0 )
        	        continue;
        	    String inputString = new String( inputBuffer, 0, readLen );
        	    if( inputString.equalsIgnoreCase( "quit" ) )
        	    {
        	        System.out.println( "end of test" );
        	        return;
        	    }
        	    String[] inputs = inputString.split( "," );
        	    if( inputs == null || inputs.length < 2 )
        	    {
        	        System.out.println( "Invalid input." );
        	        continue;
        	    }
        	    post.executeRequest( DEF_URL, inputs[0].trim(), inputs[1].trim() );
            } 
            catch (Exception e) 
            {
                System.out.println("EXCEPTION" + e);
            }        
    
    	}
    }
 

    public void executeRequest( String urlString, String msisdn, String operation )
    {
        String request = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><MOSMS appId=\"1\" username=\"testuser\" password=\"test123\"><MESSAGE msisdn=\""
                       + msisdn 
                       + "\" source=\"44444\" tid=\"132534\">"
                       + operation 
                       + "</MESSAGE></MOSMS>"; 
        executeRequest( urlString, request );
    }
    
    public void executeRequest( String urlString, String request )
    {       
        try
        {
            URLConnection connection = sendQuery( request, urlString );
            
            System.out.println( "send request: " + request );
            
            InputStream ip = connection.getInputStream();
            byte[] a = getStreamContents(ip);
            
            System.out.println("Content: " + new String(a));
                    
            Map< String, List<String> > headFieldsMap = connection.getHeaderFields();
            for( String key : headFieldsMap.keySet() ) 
            {
                System.out.println( "key = " + key + "; value = " + headFieldsMap.get( key ) );
            }
            System.out.println();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
  
   
   public void postXmlMOToServlet()
    { 		
        try{
        String data = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><MOSMS appId=\"1\" username=\"testuser\" password=\"test123\"><MESSAGE msisdn=\"+323456789\" source=\"44444\" tid=\"132534\">ACTIVATE</MESSAGE></MOSMS>";
	
        URLConnection connection = sendQuery(data,
                        "http://localhost:8080/iseepublish/MosmsDoPost");

       
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
		//os.write("content=".getBytes());
		os.write(content.getBytes());
		//os.write(("&" + URLEncoder.encode(key) + "=").getBytes());
		//os.write(URLEncoder.encode(value).getBytes()); 
		
		
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