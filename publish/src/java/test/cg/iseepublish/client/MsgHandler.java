/*
 * This package is for testing iseepublish right now.
 * It can be refactored to a generic package later
 */
package cg.iseepublish.client;

public interface MsgHandler
{
    public int processRawMsg( byte[] rawMsg );
    public int processRawMsg( String rawMsg );
}