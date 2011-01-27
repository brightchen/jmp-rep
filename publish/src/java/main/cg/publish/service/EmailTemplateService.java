package cg.publish.service;

import java.io.IOException;
import java.util.Map;

public interface EmailTemplateService
{
  public String getEmailContent(String templateName, Map<String, String> properties) throws IOException;
}
