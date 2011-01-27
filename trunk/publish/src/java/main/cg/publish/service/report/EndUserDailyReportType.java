/******************
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 * @author Bright Chen
 *******************/

package cg.publish.service.report;

/*
 * This interface declares methods specify to a certain type of report
 */
public class EndUserDailyReportType implements ReportType
{
    public String getRawTemplateFileName()
    {
        return rawTemplateFileName;
    }
    
    public void setRawTemplateFileName( String rawTemplateFileName )
    {
        this.rawTemplateFileName = rawTemplateFileName;
    }
    
    //the name of this report type
    public String getName()
    {
        return "EndUserDailyReport";
    }
    
    private String rawTemplateFileName="EndUserDaily_report.jrxml";
}