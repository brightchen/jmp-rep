/*
 * This package is for testing iseepublish right now.
 * It can be refactored to a generic package later
 */
package cg.iseepublish.transmission;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpMsgSender implements MsgSender
{
    private HttpURLConnection connection;
    
    public HttpMsgSender( HttpURLConnection connection )
    {
        this.connection = connection;
    }
    
    public void sendMsg( String msg ) throws IOException
    {
        sendMsg( msg.getBytes() );
    }
    
    public void sendMsg( final byte[] msg ) throws IOException
    {
        sendMsg( msg, 0, msg.length );
    }
    
    public void sendMsg( final byte[] msg, int start, int length ) throws IOException
    {
        OutputStream os = connection.getOutputStream();
        os.write( msg, 0, length );

        os.flush();
    }
}