

package cg.common.logging;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggerRepository;

import org.apache.log4j.Layout;
import org.apache.log4j.Appender;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.FileAppender;

/**
 * This class provides methods to create dynamic logger; 
 * It is just used for logging the result of Ftp tasks for the iseepublish project right now
 * generic refactoring is expected
 * 
 * @author Bright Chen
 * 
 */
public class DynamicLoggerFactory 
{
    public static final String LOG_CONFIG = "log4j.properties";
    private static LoggerRepository repository = null;
    //defaultly, use absolute dir;
    protected String baseDir = "";
    protected String loggerFileType = ".log";
    
    static
    {
        classInit();
    }
    
    
    public static void classInit()
    {
        repository = LogManager.getLoggerRepository(); 
        if( repository != null )
        {
            System.out.println( "log4j had initialized." );
            return;
        }

        //let's try to create repository manually
        PropertyConfigurator.configure( LOG_CONFIG );
        
        repository = LogManager.getLoggerRepository();
        if( repository == null )
        {
            System.out.println( "can NOT initiate log4j" );
            return;
        }
        System.out.println( "initiate log4j success" );
    }
    
    public DynamicLoggerFactory()
    {
    }

    /*
     * For simplicy, use FIleAppender and SimpleLayout right now
     */
    public Logger getLogger( String loggerName )
    {
        if( repository == null )
        {
            System.out.println( "No repository" );
            return null;
        }
        
        Logger logger = Logger.getLogger( loggerName );
        if( logger == null )
        {
            System.out.println( "Can NOT create logger: " + loggerName );
            return null;
        }
        
        if( logger.getAppender( loggerName ) != null )
        {
            return logger;
        }

        //create and add appender;
        try
        {
            return configLogger( logger, loggerName );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }


    protected Logger configLogger( Logger logger, String loggerName ) throws java.io.IOException
    {
        Layout layout = createLayout();
        Appender appender = createAppender( layout, loggerName );
        System.out.println( "add an appender to the logger." );
        logger.addAppender( appender );
        return logger;
    }
    
    protected Layout createLayout()
    {
        return new SimpleLayout();
    }
    
    protected Appender createAppender( Layout layout, String loggerName ) throws java.io.IOException
    {
        final String filePath = getLoggerFilePath( loggerName );
        Appender appender = new FileAppender( layout, filePath );
        appender.setName( loggerName );
        return appender;
    }
    
    protected String getLoggerFilePath( String loggerName )
    {
        return getBaseDir() + "/" + loggerName.replace( '.', '/' ) + getLoggerFileType();
    }

    //baseDir;
    public String getBaseDir()
    {
        return baseDir;
    }
    public void setBaseDir( String baseDir )
    {
        this.baseDir = baseDir;
    }

    //loggerFileType
    public String getLoggerFileType()
    {
        return loggerFileType;
    }
    public void setLoggerFileType( String loggerFileType )
    {
        this.loggerFileType = loggerFileType;
    }
}