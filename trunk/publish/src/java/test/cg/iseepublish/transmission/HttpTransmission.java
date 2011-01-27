/*
 * This package is for testing iseepublish right now.
 * It can be refactored to a generic package later
 */
package cg.iseepublish.transmission;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTransmission implements Transmission
{
    private URL url;
    private HttpURLConnection   connection;
    private MsgSender           msgSender;
    private MsgReceiver         msgReceiver;
    
    public HttpTransmission( URL url )
    {
        this.url = url;
    }

    public MsgSender    getMsgSender()
    {
        return msgSender;
    }
    
    public MsgReceiver  getMsgReceiver()
    {
        return msgReceiver;
    }
    
    public void open() throws IOException
    {
        connect();
        msgSender   = new HttpMsgSender( connection );
        msgReceiver = new HttpMsgReceiver( connection );
    }
    
    public void close()
    {
        if( connection != null )
            connection.disconnect();
    }
    
    protected void connect() throws IOException
    {
        if( connection == null )
            connection = ( HttpURLConnection )url.openConnection();
        connection.connect();
    }
    
    protected void configConnection()
    {
        if( connection == null )
            return;
        
        connection.setDoOutput( true );
        connection.setDoInput( true );
        connection.setUseCaches( false );
    }
    
    protected void finalize()
    {
        if( connection != null )
            connection.disconnect();
    }
}