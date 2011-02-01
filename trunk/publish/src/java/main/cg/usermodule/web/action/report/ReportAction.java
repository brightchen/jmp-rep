/*
 * $Id: ReportAction.java,v 1.6 2008/06/09 19:15:16 brightc Exp $
 * @version     1.0
 * @Date        2008/03/10 
 * @Author      Bright Chen
 * 
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 *
 */

package cg.publish.web.action.report;

//import java.util.ArrayList;
//import java.util.List;


import java.util.Date;
import java.util.Calendar;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cg.common.logging.Logger; 

import cg.publish.service.report.ReportInfo;
import cg.publish.service.report.ReportService;
import cg.publish.service.report.ReportType;
import cg.publish.web.action.BaseAction;
import cg.publish.web.jasper.ReportDurationUtil;

import com.opensymphony.xwork2.Preparable;

/**
 * ReportAction: action class for creating the Report.
 */
public class ReportAction extends BaseAction implements Preparable
{
    private static final long   serialVersionUID = 5765357945738590838L;
    private static final Logger log              = Logger.getLogger( ReportAction.class );
    public  static final String TOTAL            = "Total";

    private ReportService       reportService;
    private String[]            reportFormats    = { "PDF", "CSV", "HTML", "RTF", "XLS" };
    
    private String              reportFormat;
    private Date                startDate;
    private Date                endDate;
    //private ReportInfo          reportInfo;     // the info to show in result

    // the attributes for show the result
    private String              reportFileUrl;
    private String              contentType;
    private InputStream         reportInputStream;  //the input stream for the result

    public static String getContentType( String reportFormat )
    {
        if( reportFormat.compareToIgnoreCase( "PDF" ) == 0 )
            return "application/pdf";

        if( reportFormat.compareToIgnoreCase( "CSV" ) == 0 )
            return "text/plain";

        if( reportFormat.compareToIgnoreCase( "HTML" ) == 0 )
            return "text/html";

        if( reportFormat.compareToIgnoreCase( "RTF" ) == 0 )
            return "application/rtf";

        if( reportFormat.compareToIgnoreCase( "XLS" ) == 0 )
            return "application/vnd.ms-excel";

        return null;
    }
    
    public void prepare() throws Exception
    {
        clearErrorsAndMessages();
        
        startDate = new Date();
        endDate = new Date();
    }

    public String input()
    {
        return SUCCESS;
    }

    public HttpServletRequest getRequest()
    {
        return ServletActionContext.getRequest();
    }

    public String executeRTFReport() throws Exception
    {
        return executeReport( "RTF" );
    }

    public String executeXLSReport() throws Exception
    {
        return executeReport( "XLS" );
    }

    public String executePDFReport() throws Exception
    {
        return executeReport( "PDF" );
    }

    public String executeCVSReport() throws Exception
    {
        return executeReport( "CVS" );
    }

    public String executeReport( String reportFormat ) throws Exception
    {
        this.reportFormat = reportFormat;
        return execute();
    }

    public String execute() throws Exception
    {
        //startDate = ( Date )getRequest().getSession().getAttribute( "startDate" );
        //endDate = ( Date )getRequest().getSession().getAttribute( "endDate" );

        log.info( " *** In ReportAction.execute() " );
        log.info( " *** reportFormat -> " + reportFormat );
        log.info( " *** startDate -> " + startDate );
        log.info( " *** endDate -> " + endDate );

        if (startDate == null)
        {
            return onInvalidInput( "Error - Please specify a Start Date." );
        }
        if (endDate == null)
        {
            return onInvalidInput( "Error - Please specify an End Date." );
        }
        if (endDate.before( startDate ))
        {
            return onInvalidInput( "Error - End Date can't before the Start Date." );
        }

        Date now = Calendar.getInstance().getTime();
        if( startDate.after( now ) || endDate.after( now ) )
        {
            return onInvalidInput( "Error - Start Date/End Date can't after today." );
        }
        Date endDateNew = ReportDurationUtil.increaseDate( endDate, 1 ); // increase one day to the endDate

        ReportInfo reportInfo = createReportInfo( startDate, endDateNew, reportFormat );
        log.info( "report info: " );
        log.info( "startDate:   " + reportInfo.getStartDate() );
        log.info( "endDate:     " + reportInfo.getEndDate() );
        log.info( "report file: " + reportInfo.getReportFileUrl() );


        // delegate to ReportFactory to generate the report;
        boolean bSuccess = reportService.createReport( reportInfo );
        if (!bSuccess)
        {
            
            return ERROR;
        }
        log.debug( "report created by report service." );

        // set reportFileUrl for showing the result
        setReportFileUrl( reportInfo.getReportFileUrl() );
        setContentType( getContentType( reportInfo.getReportFormat() ) );

        setContextAttributes();
        //ServletActionContext.getRequest().setAttribute( "reportFileUrl", getReportFileUrl() );
        //ServletActionContext.getRequest().setAttribute( "contentType", getContentType() );
        
        log.debug( "going to create-report-file-input-stream." );
        bSuccess = createReportFileInputStream( reportInfo );
        if (!bSuccess)
        {
            addActionError( "Error - Can't create report file input stream. ReportFileUrl = " + reportFileUrl );
            return ERROR;
        }
        
        return SUCCESS;
    }

    protected void setContextAttributes()
    {
        ServletActionContext.getRequest().setAttribute( "contentDisposition", "attachment; filename=\"" + getReportFileUrl() + "\"" );
        //ServletActionContext.getRequest().setAttribute( "contentDisposition", getReportFileUrl() );
        ServletActionContext.getRequest().setAttribute( "contentType", getContentType() );
    }
    
    /*
     * The method is called by execute() when detect input is invalid
     */
    protected String onInvalidInput( String reason )
    {
        addActionError( reason );
        String result = input();
        return ( result.equals( SUCCESS ) ? INPUT : result );
    }
    
    protected ReportInfo createReportInfo( Date startDate, Date endDate, String reportFormat )
    {
        return createReportInfo( startDate, endDate, reportFormat, null );
    }

    protected ReportInfo createReportInfo( Date startDate, Date endDate, String reportFormat, ReportType reportType )
    {
        return new ReportInfo( startDate, endDate, reportFormat, reportType,
                               getDefJasperTemplateDir() );
    }
    
    protected String getDefJasperTemplateDir()
    {
        String defDir = ServletActionContext.getServletContext().getRealPath("/")
                            + "jasper";
        log.info( "default jasper template directory: " + defDir );
        return defDir;
    }
    
    protected boolean createReportFileInputStream( ReportInfo reportInfo )
    {
        // if the data in memory, use the memory
        byte[] reportContent = reportInfo.getReportContent();
        if( reportContent != null )
        {
            log.info( "create reportInputStream from memory." );
            reportInputStream = new ByteArrayInputStream( reportContent );
            return true;
        }
        
        // use file
        if( reportFileUrl == null || reportFileUrl.length() == 0 )
        {
            log.error( "reportFileUrl is null or empty. reportFileUrl = " + reportFileUrl );
            return false;
        }
        try
        {
            log.info( "create reportInputStream file. fileName = " + reportInfo.getReportFileUrl() );
            reportInputStream = new FileInputStream( reportInfo.getReportFileUrl() );
        }
        catch( FileNotFoundException e )
        {
            log.error( "report file not found. reportFileName = " + reportInfo.getReportFileUrl(), e );
            return false;
        }
        return true;
    }
    

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate( Date endDate )
    {
        this.endDate = endDate;
    }
    
    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate( Date startDate )
    {
        this.startDate = startDate;
    }

    public String getReportFormat()
    {
        return reportFormat;
    }

    public void setReportFormat( String reportFormat )
    {
        this.reportFormat = reportFormat;
    }

    public String[] getReportFormats()
    {
        return reportFormats;
    }

    public void setReportFormats( String[] reportFormats )
    {
        this.reportFormats = reportFormats;
    }

    /******************
    public ReportInfo getReportInfo()
    {
        return reportInfo;
    }
    public void setReportInfo( ReportInfo reportInfo )
    {
        this.reportInfo = reportInfo;
    }
    /******************/

    public String getContentType()
    {
        return contentType;
    }
    public void setContentType( String contentType )
    {
        this.contentType = contentType;
    }
    
    public String getReportFileUrl()
    {
        return reportFileUrl;
    }
    public void setReportFileUrl( String reportFileUrl )
    {
        this.reportFileUrl = reportFileUrl;
    }
    
    public InputStream getReportInputStream()
    {
        return reportInputStream;
    }
    public void setReportInputStream( InputStream reportInputStream )
    {
        this.reportInputStream = reportInputStream;
    }
    

    public ReportService getReportService()
    {
        return reportService;
    }
    public void setReportService( ReportService reportService )
    {
        this.reportService = reportService;
    }
    /*******************************/
    
}
