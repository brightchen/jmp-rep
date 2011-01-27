package cg.oam.config;

import java.util.Properties;

public class BuildinProperties
{
  private static Properties props;
  
  public static Properties getBuildinProperties()
  {
    if( props != null )
      return props;
    
    props = new Properties();
    
    //ftp
    props.put( "ftpTask.shouldRun", "false" );
    props.put( "ftp.delay.start.milliseconds", "10000000" ); 
    props.put( "ftp.period.milliseconds", "10000000" );
    props.put( "ftp.usersFilePath", "c:\\temp" );
    props.put( "ftp.usersFilePath", "c:\\temp" );
    props.put( "ftp.usersFilePathNew", "c:\\temp" );
    props.put( "mail.smtp.host", "localhost" );
    props.put( "mail.smtp.username", "smtpuser" );
    props.put( "mail.smtp.password", "smtppass" );
    props.put( "mail.from", "mailfrom" );
    props.put( "mail.iseepublish_server", "" );
    props.put( "mail.template.dir", "c:\\temp" );
    props.put( "ftp.ftpConfig", "ftpconfig" );
    props.put( "ftp.localDownloadDir", "c:\\temp" );
    
    //db
    //derby
    //"jdbc:derby://localhost:1527/database;create=true"
    props.put( "db.jdbc.driver_class", "org.apache.derby.jdbc.EmbeddedDriver" );
    props.put( "db.jdbc.url", "jdbc:derby:test;create=true" );
    props.put( "db.username", "user1" );
    props.put( "db.password", "user1" );
    props.put( "db.validation.query", "" );
    props.put( "ajax.default.layout.filePath", "c:\\temp" );
//    props.put( "", "" );
//    props.put( "", "" );

    return props;
  }
}
