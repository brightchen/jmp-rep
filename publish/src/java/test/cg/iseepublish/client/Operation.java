
package cg.iseepublish.client;

/*
 * operation = send request + receive response + handle response
 */

import java.io.IOException;

import cg.iseepublish.transmission.Transmission;

public class Operation
{
    Transmission transmission;
    MsgHandler msgHandler;
    
    public Operation( Transmission transmission, MsgHandler msgHandler )
    {
        setTransmission( transmission );
        setMsgHandler( msgHandler );
    }

    /*
     * execute an operation
     * return the result code
     */
    public int execute( String request ) throws IOException
    {
        transmission.getMsgSender().sendMsg( request );
        String response = transmission.getMsgReceiver().receiveStringMsg();
        return msgHandler.processRawMsg( response );
    }
    
    public void setTransmission( Transmission transmission )
    {
        this.transmission = transmission;
    }

    public void setMsgHandler( MsgHandler msgHandler )
    {
        this.msgHandler = msgHandler;
    }
    
    
}