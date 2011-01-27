
/*
 * 
 */
package cg.publish.task.ftpTask;

import cg.common.logging.DynamicLoggerFactory;
import cg.common.logging.Logger;
/*
 * @author Bright Chen
 */
public class FtpStatusLoggerFactory extends DynamicLoggerFactory
{
    private static FtpStatusLoggerFactory instance;
    
    public static FtpStatusLoggerFactory getInstance()
    {
        if( instance != null )
            return instance;
        
        instance = new FtpStatusLoggerFactory();
        return instance;
    }

    protected FtpStatusLoggerFactory()
    {
        super();
    }

    public Logger getLogger( String loggerName )
    {
        return super.getLogger( loggerName );
    }
    
    //we can use absolute file name
    public String getBaseDir()
    {
        return "";
    }
    
    //use txt file
    public String getLoggerFileType()
    {
        return ".txt";
    }

}