/******************
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 * @author Bright Chen
 *******************/

package cg.publish.service.report;

/*
 * This interface declares methods specify to a certain type of report
 */
public interface ReportType
{
    String getRawTemplateFileName();
    String getName();   //the name of this report type: "advertisement" etc
}