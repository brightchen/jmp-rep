package cg.publish.web.jasper;

import java.util.Calendar;
import java.util.Date;

import cg.publish.web.util.Constants;

public class ReportUtil {
	
	public static Date parseDateForStartDate(String year, String quarter, String month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(calendar.getTimeInMillis()/1000*1000);
		if (quarter == null) {
			if (month == null) {
				calendar.set(Integer.parseInt(year), 0, 1, 0, 0, 0);
			} else {
				int monthInt = parseMonthToInt(month);
				calendar.set(Integer.parseInt(year), monthInt, 1, 0, 0, 0);
			}
		} else {
			if (quarter.equals(Constants.Q1)) calendar.set(Integer.parseInt(year), 0, 1, 0, 0, 0);
			if (quarter.equals(Constants.Q2)) calendar.set(Integer.parseInt(year), 3, 1, 0, 0, 0);
			if (quarter.equals(Constants.Q3)) calendar.set(Integer.parseInt(year), 6, 1, 0, 0, 0);
			if (quarter.equals(Constants.Q4)) calendar.set(Integer.parseInt(year), 9, 1, 0, 0, 0);
		}
		return calendar.getTime();
	}
	
	public static Date parseDateForEndDate(String year, String quarter, String month) {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		calendar.setTimeInMillis(calendar.getTimeInMillis()/1000*1000);
		if (quarter == null) {
			if (month == null) {
				calendar.set(Integer.parseInt(year)+1, 0, 1, 0, 0, 0);
			} else {
				int monthInt = parseMonthToInt(month);
				if (monthInt < 11) {
					calendar.set(Integer.parseInt(year), monthInt+1, 1, 0, 0, 0);
				} else {
					calendar.set(Integer.parseInt(year)+1, 0, 1, 0, 0, 0);
				}
			}
		} else {
			if (quarter.equals(Constants.Q1)) calendar.set(Integer.parseInt(year), 3, 1, 0, 0, 0);
			if (quarter.equals(Constants.Q2)) calendar.set(Integer.parseInt(year), 6, 1, 0, 0, 0);
			if (quarter.equals(Constants.Q3)) calendar.set(Integer.parseInt(year), 9, 1, 0, 0, 0);
			if (quarter.equals(Constants.Q4)) calendar.set(Integer.parseInt(year)+1, 0, 1, 0, 0, 0);
		}
		Date endDate = ReportDurationUtil.decreaseDate(calendar.getTime(), 1);
		if (endDate.after(now)) endDate = now;
		return endDate;
	}

	public static int parseMonthToInt(String month) {
		if (month.equals(Constants.JANUARY)) return 0;
		if (month.equals(Constants.FEBRUARY)) return 1;
		if (month.equals(Constants.MARCH)) return 2;
		if (month.equals(Constants.APRIL)) return 3;
		if (month.equals(Constants.MAY)) return 4;
		if (month.equals(Constants.JUNE)) return 5;
		if (month.equals(Constants.JULY)) return 6;
		if (month.equals(Constants.AUGUST)) return 7;
		if (month.equals(Constants.SEPTEMBER)) return 8;
		if (month.equals(Constants.OCTOBER)) return 9;
		if (month.equals(Constants.NOVEMBER)) return 10;
		if (month.equals(Constants.DECEMBER)) return 11;
		else return 0;
	}

}
