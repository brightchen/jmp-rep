/* 
 * This unpublished source code contains trade secrets and copyrighted
 * materials that are the proprietary property of iseemedia, Inc.
 * Unauthorized use, copying or distribution of this source code or the
 * ideas contained herein is a violation of U.S. and international laws
 * and is strictly prohibited.
 *
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 *
 */

package cg.publish.web.jasper;

import java.util.Date;
import java.util.List;
import org.apache.struts2.ServletActionContext;

import cg.publish.web.util.Constants;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Base Jasper action class for the common features of the Jasper action.
 * 
 * @author Charles Deng
 */
abstract class BaseJasperAction extends ActionSupport {

	private static final long serialVersionUID = 5970123963237527753L;
	
	public static final String TOTAL = "Total";
	
	private List<?> dataList;
	private String reportStyle;
	private String[] reportStyles = {Constants.REPORT_DAILY, Constants.REPORT_WEEKLY, Constants.REPORT_MONTHLY, Constants.REPORT_QUARTERLY, Constants.REPORT_YEARLY};
	private String[] staticReportStyles = {Constants.REPORT_DAILY, Constants.REPORT_MONTHLY, Constants.REPORT_QUARTERLY, Constants.REPORT_YEARLY};
	private String reportFormat;
	private String[] reportFormats = {"PDF", "CSV", "HTML", "RTF", "XLS"};
	private String[] months = 
		{Constants.JANUARY, Constants.FEBRUARY, Constants.MARCH, Constants.APRIL, 
		 Constants.MAY, Constants.JUNE, Constants.JULY, Constants.AUGUST, 
		 Constants.SEPTEMBER, Constants.OCTOBER, Constants.NOVEMBER, Constants.DECEMBER}; 
	private String[] quarters = {Constants.Q1, Constants.Q2, Constants.Q3, Constants.Q4};
	private String[] years = {"2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018"};	
	private Date startDate;
	private Date endDate;
	private String startMonth, startQuarter, startYear;
	private String endMonth, endQuarter, endYear;	
	private String imageRoot;
	
	public abstract String executeReport() throws Exception;
	
	public abstract String executeStaticReport() throws Exception;

	public abstract List<?> createDataList(String reportStyle, Date startDate, Date endDate) throws Exception;
	
	public abstract String getJasperXmlFilePath();

	public abstract String getJasperCompiledTemplateFilePath();
	
	public List<?> getDataList() {
		return dataList;
	}
	
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReportStyle() {
		return reportStyle;
	}

	public void setReportStyle(String reportStyle) {
		this.reportStyle = reportStyle;
	}

	public String[] getReportStyles() {
		return reportStyles;
	}

	public void setReportStyles(String[] reportStyles) {
		this.reportStyles = reportStyles;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public String[] getReportFormats() {
		return reportFormats;
	}

	public void setReportFormats(String[] reportFormats) {
		this.reportFormats = reportFormats;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getEndQuarter() {
		return endQuarter;
	}

	public void setEndQuarter(String endQuarter) {
		this.endQuarter = endQuarter;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String[] getMonths() {
		return months;
	}

	public void setMonths(String[] months) {
		this.months = months;
	}

	public String[] getQuarters() {
		return quarters;
	}

	public void setQuarters(String[] quarters) {
		this.quarters = quarters;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getStartQuarter() {
		return startQuarter;
	}

	public void setStartQuarter(String startQuarter) {
		this.startQuarter = startQuarter;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String[] getYears() {
		return years;
	}

	public void setYears(String[] years) {
		this.years = years;
	}

	public String[] getStaticReportStyles() {
		return staticReportStyles;
	}

	public void setStaticReportStyles(String[] staticReportStyles) {
		this.staticReportStyles = staticReportStyles;
	}
	
	public String getImageRoot() {
		imageRoot = ServletActionContext.getServletContext().getRealPath("/") + "/images/";
		return imageRoot;
	}
	
}
