package cg.common.logging;

/**
 * a org.apache.log4j.Level wrapper class
 * 
 */

public class Level extends org.apache.log4j.Level
{
  private static final long serialVersionUID = 7356283316048753741L;

  public final static Level OFF = new Level( OFF_INT, "OFF", 0 );
  public final static Level FATAL = new Level( FATAL_INT, "FATAL", 0 );
  public final static Level ERROR = new Level( ERROR_INT, "ERROR", 3 );
  public final static Level WARN = new Level( WARN_INT, "WARN", 4 );
  public final static Level INFO = new Level( INFO_INT, "INFO", 6 );
  public final static Level DEBUG = new Level( DEBUG_INT, "DEBUG", 7 );

  public final static Level[] OAM_LEVELS =
  { DEBUG, INFO, ERROR };

  protected Level( int level, String levelStr, int syslogEquivalent )
  {
    super( level, levelStr, syslogEquivalent );
  }

  public static Level getLevelByName( String levelStr )
  {

    Level type = DEBUG;
    if ( levelStr.equals( OFF.toString() ) )
    {
      type = OFF;
    }
    else if ( levelStr.equals( FATAL.getName() ) )
    {
      type = FATAL;
    }
    else if ( levelStr.equals( ERROR.getName() ) )
    {
      type = ERROR;
    }
    else if ( levelStr.equals( WARN.getName() ) )
    {
      type = WARN;
    }
    else if ( levelStr.equals( INFO.getName() ) )
    {
      type = INFO;
    }
    else if ( levelStr.equals( DEBUG.getName() ) )
    {
      type = DEBUG;
    }

    return type;
  }

  protected Level()
  {
    super( DEBUG_INT, "DEBUG", 7 );
  }

  public String getName()
  {
    return toString();
  }

}
