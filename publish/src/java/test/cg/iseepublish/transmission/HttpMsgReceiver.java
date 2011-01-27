/*
 * This package is for testing iseepublish right now.
 * It can be refactored to a generic package later
 */
package cg.iseepublish.transmission;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public class HttpMsgReceiver implements MsgReceiver
{
    private final static int BUF_SIZE = 4096;
    private HttpURLConnection connection;
    
    
    public HttpMsgReceiver( HttpURLConnection connection )
    {
        if( connection == null )
            throw new IllegalArgumentException( "The connection can NOT be null." );
        this.connection = connection;
    }
    
    public byte[] receiveMsg() throws IOException
    {
        InputStream is = connection.getInputStream();
        BufferedInputStream bis = new BufferedInputStream( is );
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        byte buffer[] = new byte[ BUF_SIZE ];
        while( true )
        {
            int readLen = bis.read( buffer );
            if( readLen <= 0 )
                break;
            os.write( buffer, 0, readLen );
        }

        os.flush();
        os.close();
        return os.toByteArray();

    }
    
    public String receiveStringMsg() throws IOException
    {
        return new String( receiveMsg() );
    }
}