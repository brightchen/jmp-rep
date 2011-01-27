/******************
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 * @author Bright Chen
 *******************/

package cg.publish.service.report;

import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.FieldPosition;


/*
 * This class
 */
public class ReportInfo
{
    public static final int STYLE_IGNORE_REPEAT_CONTENT = 0x01;
    
    public ReportInfo( Date startDate, Date endDate, String reportFormat, ReportType reportType,
                       String defTemplateFileDirectory )
    {
        this.startDate  = startDate;
        this.endDate    = endDate;
        this.reportFormat   = reportFormat;
        this.reportType     = reportType;
        this.defTemplateFileDirectory = defTemplateFileDirectory;
        
    }
    
    public void setStartDate( Date startDate )
    {
        this.startDate = startDate;
    }
    public Date getStartDate()
    {
        return startDate;
    }

    public void setEndDate( Date endDate )
    {
        this.endDate = endDate;
    }
    public Date getEndDate()
    {
        return endDate;
    }

    public String getReportFileUrl()
    {
        if( reportFileUrl == null )
            reportFileUrl = generateReportFileUrl();

        return reportFileUrl;
    }
    
    public void setReportFormat( String reportFormat )
    {
        this.reportFormat = reportFormat;
    }
    public String getReportFormat()
    {
        return reportFormat;
    }

    public void setReportType( ReportType reportType )
    {
        this.reportType = reportType;
    }
    public ReportType getReportType()
    {
        return reportType;
    }
    
    //reportContent;
    public void setReportContent( byte[] reportContent )
    {
        this.reportContent = reportContent;
    }
    public byte[] getReportContent()
    {
        return reportContent;
    }

    //datasource;
    public void setDataSource( List<?> dataSource )
    {
        this.dataSource = dataSource;
    }
    public List<?> getDataSource()
    {
        return dataSource;
    }

    //reportStyles
    public void setReportStyles( int reportStyles )
    {
        this.reportStyles = reportStyles;
    }
    public int getReportStyles()
    {
        return reportStyles;
    }
    
    //defTemplateFileDirectory
    public void setDefTemplateFileDirectory( String defTemplateFileDirectory )
    {
        this.defTemplateFileDirectory = defTemplateFileDirectory;
    }
    public String getDefTemplateFileDirectory()
    {
        return defTemplateFileDirectory;
    }
    
    public boolean isIgnoreRepeatContent()
    {
        return ( ( reportStyles & STYLE_IGNORE_REPEAT_CONTENT ) != 0 );
    }

    // generate the report file url
    // requirement on strategy:
    // unique if same ( startDate, endDate, reportFormat and reportType )
    protected String generateReportFileUrl()
    {
        return reportType.getName() + formatDate( startDate ) + "_" + formatDate( endDate ) + "." + reportFormat;
    }
    
    protected static String formatDate( Date date )
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
        FieldPosition fpos = new FieldPosition( 0 );

        StringBuffer b = new StringBuffer();
        StringBuffer sb = sdf.format( calendar.getTime(), b, fpos );

        return sb.toString();
    }
    
    private String  reportFileUrl;
    private Date    startDate;
    private Date    endDate;
    private String  reportFormat;       // "PDF" or others
    private ReportType  reportType;     // advertisement report or other reports
    private byte[]      reportContent;
    private List<?>     dataSource;
    private String  defTemplateFileDirectory;
    
    private int     reportStyles = STYLE_IGNORE_REPEAT_CONTENT;    //for example, ignore repeat content;
}