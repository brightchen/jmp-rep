/*
 * $Id: ReportDuration.java,v 1.1 2007/07/27 15:53:13 charlesd Exp $
 * 
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

/**
 * Used for representing a single Report Duration.
 * 
 * @author Charles Deng
 */
public class ReportDuration {
	
	private String dateStr;
	private Date startDate;
	private Date endDate;
	
	public ReportDuration(String dateStr, Date startDate, Date endDate) {
		this.dateStr = dateStr;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}
