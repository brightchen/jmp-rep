package cg.publish.service.report;

import java.io.File;
import java.io.FileOutputStream;

import cg.common.logging.Logger;


/*
 * This class define the work flow and anything which is unrelated to 
 * the detail implementation of report service.
 * 
 * it seems the best structure is using Bridge pattern to separate the abstract from the implementation.
 * but for simplify, use a template pattern is not so bad as the work flow is configurable
 */
public abstract class AbstractReportService implements ReportService
{
    public static enum ReportFormats { PDF, CSV, HTML, XLS, XML, RTF };
    public static final int MODE_TANSIENT = 0X0001;

    private static final Logger log = Logger.getLogger( AbstractReportService.class );

    protected   int                 serviceMode = 0;
    protected   String              isStoreReport;
    private     ReportContentFilter reportContentFilter = null;
    
    public boolean createReport( ReportInfo reportInfo )
    {
        // check if the report already existed
        boolean transientMode = ( ( serviceMode & MODE_TANSIENT ) != 0 );
        log.debug( "serviceMode = " + serviceMode + "; which is " + 
                   ( transientMode ? "" : "NOT " ) +
                   "transient mode. " );

        String reportFileUrl = "";
        if( !transientMode && isStoreReport.equals("true"))
        {
            reportFileUrl = reportInfo.getReportFileUrl();
            if( ( new File(reportFileUrl) ).exists() )
            {
                log.info( "The report file already existed. fileUrl = " + reportFileUrl );
                return true;    // the file already created
            }
        }
        
        // generate content;
        try
        {
            byte[] reportContent = generateReportContent( reportInfo );
            if( reportContent == null || reportContent.length == 0 )
            {
                log.info( "the report content is empty" );
                return true;
            }
            
            //filter content;
            if( reportContentFilter != null )
            {
                reportContent = reportContentFilter.filterContent( reportInfo, reportContent );
            }
            
            // keep the content;
            reportInfo.setReportContent( reportContent );
            
            if( !transientMode && isStoreReport.equals("true"))
            {
                //save it into file;
                try
                {
                    log.info( "Going to write content to report file. fileUrl = " + reportFileUrl );
                    FileOutputStream fos = new FileOutputStream( reportFileUrl );
                    fos.write( reportContent );
                    fos.close();
                    log.info( "The report file has been saved. fileUrl = " + reportFileUrl );
                    return true;
                }
                catch( Exception e )
                {
                    log.error( "write report to file exception.", e );
                    log.error( "write report content to file failed. fileUrl = " + reportFileUrl, e );
                    return false;
                }
            }
            
            return true;
        }
        catch( Exception e )
        {
            log.error( "generate report error.", e );
            return false;
        }
    }
    
    protected abstract byte[] generateReportContent( ReportInfo reportInfo ) throws Exception;
    
    //serviceMode
    public void setServiceMode( String serviceModeString )
    {
        try
        {
            if( serviceModeString.length() > 2 
             && ( serviceModeString.startsWith( "0x" ) || serviceModeString.startsWith( "0X" ) ) )
            {
                //hex
                serviceMode = Integer.valueOf( serviceModeString, 16 );
            }
            else
            {
                serviceMode = Integer.valueOf( serviceModeString );
            }
        }
        catch( Exception e )
        {
            log.debug( "Invalid service mode string: " + serviceModeString );
        }
    }
    /*
    public void setServiceMode( int serviceMode )
    {
        this.serviceMode = serviceMode;
    }
    */
    
    public int getServiceMode()
    {
        return serviceMode;
    }
    
    public String getIsStoreReport () {
      return this.isStoreReport;
    }
    
    public void setIsStoreReport (String isStoreReport) {
      if (isStoreReport == null || !isStoreReport.equals("true")) {
        this.isStoreReport = "false";
      } else {
        this.isStoreReport = "true";
      }
    }
    
    public ReportContentFilter getReportContentFilter()
    {
        return reportContentFilter;
    }

    public void setReportContentFilter(ReportContentFilter reportContentFilter)
    {
        this.reportContentFilter = reportContentFilter;
    }
}