/*
 * This package is for testing iseepublish right now.
 * It can be refactored to a generic package later
 */
package cg.iseepublish.transmission;

import java.io.IOException;

public interface MsgReceiver
{
    public byte[] receiveMsg() throws IOException;
    public String receiveStringMsg() throws IOException;

}