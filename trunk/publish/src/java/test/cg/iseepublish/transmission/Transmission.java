/*
 * This package is for testing iseepublish right now.
 * It can be refactored to a generic package later
 */
package cg.iseepublish.transmission;

import java.io.IOException;

public interface Transmission
{
    public MsgSender    getMsgSender();
    public MsgReceiver  getMsgReceiver();
    public void         open()  throws IOException;
    public void         close();

}