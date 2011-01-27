package cg.publish.service.report;

import cg.common.logging.Logger;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import com.opensymphony.xwork2.ActionInvocation;

public class ReportResult extends StreamResult
{
  private static final long serialVersionUID = 4370345678456754351L;

  protected void doExecute( String finalLocation, ActionInvocation invocation ) throws Exception
  {
    // log information
    String contentDisposition = (String) ServletActionContext.getRequest().getAttribute( "contentDisposition" );
    String contentType = (String) ServletActionContext.getRequest().getAttribute( "contentType" );

    log.debug( "contentDisposition = " + contentDisposition );
    log.debug( "contentType = " + contentType );
    if ( contentDisposition != null )
      setContentDisposition( contentDisposition );
    if ( contentType != null )
      setContentType( contentType );

    super.doExecute( finalLocation, invocation );
  }

  private static final Logger log = Logger.getLogger( ReportResult.class );
}