/*
 * $Id: ReportDurationUtil.java,v 1.4 2007/08/02 20:56:30 charlesd Exp $
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cg.publish.web.util.Constants;

/**
 * Utility class for creating the Report Duration List.
 * 
 * @author Charles Deng
 */
public class ReportDurationUtil {
	
	public static final int MILLISECONDS_PER_HOUR = 60*60*1000;
	public static final int MILLISECONDS_PER_DAY = 24*60*60*1000;
	public static final int MILLISECONDS_PER_SHORT_DAY = 23*60*60*1000;
	public static final int DAYS_PER_WEEK = 7;
	public static final int MONTHS_PER_QUARTER = 3;
	public static final String DATE_DELIMITER = "-";
		
	public static List<ReportDuration> createReportDurationList(
					String reportStyle, 
					Date startDate, 
					Date endDate) throws Exception {
		
		if (startDate == null || endDate == null || !endDate.after(startDate)) 
			throw new Exception(" *** Error in cg.iseepublish.web.jasper.ReportDurationUtil.createReportDurationList() -> " + 
					"startDate/endDate is null or endDate is not after startDate. ");
		List<ReportDuration> list = new ArrayList<ReportDuration>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startDate.getTime()/1000*1000);
		int startYearInt = calendar.get(Calendar.YEAR);
		int startMonthInt = calendar.get(Calendar.MONTH) + 1 ;
		
		if (reportStyle.equals(Constants.REPORT_DAILY)) {
			long count = (endDate.getTime() - startDate.getTime())/MILLISECONDS_PER_DAY; // get days between
			long mod = (endDate.getTime() - startDate.getTime())%MILLISECONDS_PER_DAY;
			if (mod == MILLISECONDS_PER_SHORT_DAY) count++;
			for (int i = 0; i < count; i++) {				
				String dateStr = createDateStr(calendar);
				Date date01 = calendar.getTime();
				calendar.setTimeInMillis(calendar.getTimeInMillis() + MILLISECONDS_PER_DAY);
				adjustCalendar(calendar);				
				Date date02 = calendar.getTime();
				list.add(new ReportDuration(dateStr, date01, date02));
			}
		} else if (reportStyle.equals(Constants.REPORT_WEEKLY)) {
			long count = (endDate.getTime() - startDate.getTime())/MILLISECONDS_PER_DAY/DAYS_PER_WEEK;
			long mod = (endDate.getTime() - startDate.getTime())%(MILLISECONDS_PER_DAY*DAYS_PER_WEEK);
			if (mod == (MILLISECONDS_PER_DAY*DAYS_PER_WEEK - MILLISECONDS_PER_HOUR)) count++;
			for (int i = 0; i < count; i++) {
				String dateStr = createDateStr(calendar);
				Date date01 = calendar.getTime();
				calendar.setTimeInMillis(calendar.getTimeInMillis() + MILLISECONDS_PER_DAY*DAYS_PER_WEEK);
				adjustCalendar(calendar);
				Date date02 = calendar.getTime();
				list.add(new ReportDuration(dateStr, date01, date02));				
			}
			// add the last several days as an extra week to list
			addExtraAsLast(list, calendar, startDate, endDate);			
		} else if (reportStyle.equals(Constants.REPORT_MONTHLY)) {
			Calendar endDateCalendar = Calendar.getInstance();
			endDateCalendar.setTimeInMillis(endDate.getTime()/1000*1000);
			long count = 12*(endDateCalendar.get(Calendar.YEAR) - startYearInt) 
				+ endDateCalendar.get(Calendar.MONTH) + 1 - startMonthInt;
			for (int i = 0; i < count; i++) {
				int yearInt = calendar.get(Calendar.YEAR);
				int monthInt = calendar.get(Calendar.MONTH) + 1 ;
				String dateStr = createDateStr(calendar);
				Date date01 = calendar.getTime();
				if (monthInt++ > 12) {
					yearInt++;
					monthInt = 1;
				}
				// set next month's first day as the beginning of a new month
				calendar.set(yearInt, monthInt-1, 1, 0, 0, 0); 
				Date date02 = calendar.getTime();
				list.add(new ReportDuration(dateStr, date01, date02));
			}
			// add the last several days as an extra month to list
			addExtraAsLast(list, calendar, startDate, endDate);
		} else if (reportStyle.equals(Constants.REPORT_QUARTERLY)) {
			Calendar endDateCalendar = Calendar.getInstance();
			endDateCalendar.setTimeInMillis(endDate.getTime()/1000*1000);
			long count = (12*(endDateCalendar.get(Calendar.YEAR) - startYearInt) 
				+ endDateCalendar.get(Calendar.MONTH) + 1 - startMonthInt)/MONTHS_PER_QUARTER;
			for (int i = 0; i < count; i++) {
				int yearInt = calendar.get(Calendar.YEAR);
				int monthInt = calendar.get(Calendar.MONTH);
				String dateStr = createDateStr(calendar);
				Date date01 = calendar.getTime();
				
				monthInt = monthInt - monthInt%MONTHS_PER_QUARTER + MONTHS_PER_QUARTER;
				if (monthInt > 11) {
					yearInt++;
					monthInt = 0;
				}
				// set next quarter's first day as the beginning of a new quarter
				calendar.set(yearInt, monthInt, 1, 0, 0, 0); 
				Date date02 = calendar.getTime();
				list.add(new ReportDuration(dateStr, date01, date02));
			}
			// add the last several days as an extra quarter to list
			addExtraAsLast(list, calendar, startDate, endDate);
		} else if (reportStyle.equals(Constants.REPORT_YEARLY)) {
			Calendar endDateCalendar = Calendar.getInstance();
			endDateCalendar.setTimeInMillis(endDate.getTime()/1000*1000);
			long count = endDateCalendar.get(Calendar.YEAR) - startYearInt;
			for (int i = 0; i < count; i++) {
				int yearInt = calendar.get(Calendar.YEAR);
				int monthInt = calendar.get(Calendar.MONTH) + 1 ;
				String dateStr = createDateStr(calendar);
				Date date01 = calendar.getTime();
				// set next year-month's first day as the beginning of a new year
				calendar.set(yearInt+1, monthInt-1, 1, 0, 0, 0); 
				Date date02 = calendar.getTime();
				list.add(new ReportDuration(dateStr, date01, date02));
			}
			// add the last several month-days as an extra year to list
			addExtraAsLast(list, calendar, startDate, endDate);
		}
		return list;
	}
	
	public static String createDateStr(Calendar calendar) {
		int monthInt = calendar.get(Calendar.MONTH) + 1 ;
		String monthStr = monthInt < 10 ? "0" + monthInt : "" + monthInt;
		int dayInt = calendar.get(Calendar.DAY_OF_MONTH);
		String dayStr = dayInt < 10 ? "0" + dayInt : "" + dayInt;
		String dateStr = calendar.get(Calendar.YEAR) + DATE_DELIMITER + monthStr + DATE_DELIMITER + dayStr;
		return dateStr;
	}

	public static String createDateStr(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return createDateStr(calendar);
	}
	
	private static void addExtraAsLast(List<ReportDuration> list, Calendar calendar, Date startDate, Date endDate) {
		if (list.size() > 0) {
			ReportDuration dateDurationLast = list.get(list.size()-1);
			if (dateDurationLast.getEndDate().before(endDate)) {
				list.add(new ReportDuration(createDateStr(calendar), dateDurationLast.getEndDate(), endDate));
			}
		} else {
			list.add(new ReportDuration(createDateStr(calendar), startDate, endDate));
		}
	}
	
	private static void adjustCalendar(Calendar calendar) {
		if (calendar.get(Calendar.HOUR_OF_DAY) != 0) {
			if (calendar.get(Calendar.HOUR_OF_DAY) == 1) 
				calendar.setTimeInMillis(calendar.getTimeInMillis() - MILLISECONDS_PER_HOUR);
			if (calendar.get(Calendar.HOUR_OF_DAY) == 23) 
				calendar.setTimeInMillis(calendar.getTimeInMillis() + MILLISECONDS_PER_HOUR);
		}
	}

	public static Date increaseDate(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime() + i*MILLISECONDS_PER_DAY);
		adjustCalendar(calendar);
		return calendar.getTime();
	}
	
	public static Date decreaseDate(Date date, int i) {
		return increaseDate(date, -i);
	}
	
}
