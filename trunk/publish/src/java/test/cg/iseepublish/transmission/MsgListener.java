/*
 * This package is for testing iseepublish right now.
 * It can be refactored to a generic package later
 */
package cg.iseepublish.transmission;

public interface MsgListener
{
    public void processRawMsg( byte[] rawMsg );
}