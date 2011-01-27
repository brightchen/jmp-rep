package cg.publish.service;

import java.io.IOException;
import java.util.Map;

import cg.publish.web.util.PublisherUtil;

public class EmailTemplateServiceImpl implements EmailTemplateService
{
  private String emailTemplateDir;

  public String getEmailContent( String templateName, Map< String, String > properties ) throws IOException
  {
    String emailContent = PublisherUtil.getFileAsStr( emailTemplateDir + "/" + templateName );
    for ( String key : properties.keySet() )
    {
      emailContent = emailContent.replaceAll( "\\{" + key + "\\}", properties.get( key ) );
    }
    return emailContent;
  }

  public String getEmailTemplateDir()
  {
    return emailTemplateDir;
  }

  public void setEmailTemplateDir( String templateDir )
  {
    this.emailTemplateDir = templateDir;
  }

}
