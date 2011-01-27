/*
 * This package is for testing iseepublish right now.
 * It can be refactored to a generic package later
 */
package cg.iseepublish.transmission;

import java.io.IOException;

public interface MsgSender
{
    public void sendMsg( String msg ) throws IOException;
    public void sendMsg( final byte[] msg ) throws IOException;
    public void sendMsg( final byte[] msg, int start, int length ) throws IOException;
}