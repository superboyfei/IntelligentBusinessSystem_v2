package com.centerm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String getDateStringByMillis(long milllis){
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeInMillis(milllis);
		return dateToString(cal.getTime());
	}
	
	/**
	 * 将Date转化为字符串，Date格式为yyyy-MM-dd HH:mm:ss
	 * @param date 格式为"yyyy-MM-dd HH:mm:ss"的时间
	 * @return 格式为"yyyy-MM-dd HH:mm:ss"的String
	 * */
	public static String timeToString(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static String timeToFileName(Date date){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	/**
	 * 将Date转化为字符串，Date格式为yyyy-MM-dd
	 * @param date 格式为"yyyy-MM-dd"的时间
	 * @return 格式为"yyyy-MM-dd"的String
	 * */
	public static String dateToString(Date date){
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	/**
	 * 将字符串转化为Date，Date格式为yyyy-MM-dd HH:mm:ss
	 * @param date 时间格式字符串"yyyy-MM-dd HH:mm:ss"
	 * @return 格式为yyyy-MM-dd HH:mm:ss的Date
	 * */
	public static Date stringToTime(String date){
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	/**
	 * 将字符串转化为Date，Date格式为yyyy-MM-dd
	 * @param date 时间格式字符串"yyyy-MM-dd"
	 * @return 格式为yyyy-MM-dd的Date
	 * */
	public static Date stringToDate(String date){
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	/**
	 * 获取两个日期之间的月份差
	 * @param startDate endDate时间格式字符串"yyyy-MM-dd"
	 * @return Integer 月份差
	 * */
	public static Integer getMonthsBetweenTwoDate(String startDate,String endDate){
		int result = 0;
		int startYear = Integer.parseInt(startDate.substring(0, 4));
		int endYear = Integer.parseInt(endDate.substring(0, 4));
		int startMonth = Integer.parseInt(startDate.substring(5, 7));
		int endMonth = Integer.parseInt(endDate.substring(5, 7)) + 1;
		
		result = (endYear - startYear) * 12 + (endMonth - startMonth);
		return result;
	}
	
	/**
	 * 获取两个日期之间的月份差
	 * @param startDate endDate时间格式字符串"yyyy-MM-dd"
	 * @return Integer 月份差
	 * */
	public static Integer getQuartersBetweenTwoDate(String startDate,String endDate){
		int result = 0;
		int startYear = Integer.parseInt(startDate.substring(0, 4));
		int endYear = Integer.parseInt(endDate.substring(0, 4));
		int startQuarter = Integer.parseInt(startDate.substring(5, 6));
		int endQuarter = Integer.parseInt(endDate.substring(5, 6)) + 1;
		
		result = (endYear - startYear) * 4 + (endQuarter - startQuarter);
		return result;
	}
	
	/**
	 * 获取两个日期之间的年份差
	 * @param startDate endDate时间格式字符串"yyyy-MM-dd"
	 * @return Integer 年份差
	 * */
	public static Integer getYearsBetweenTwoDate(String startDate,String endDate){
		return Integer.parseInt(endDate.substring(0, 4)) - Integer.parseInt(startDate.substring(0, 4)) + 1;
	}
	
	/**
	 * 获取日期的年份
	 * @param date时间格式字符串"yyyy-MM-dd"
	 * @return Integer 年份
	 * */
	public static Integer getYearOfDate(String date){
		return Integer.parseInt(date.substring(0, 4));
	}
	/**
	 * 获取日期的月份
	 * @param date时间格式字符串"yyyy-MM-dd"
	 * @return Integer 月份
	 * */
	public static Integer getMonthOfDate(String date){
		return Integer.parseInt(date.substring(5, 7));
	}
	
	/**
	 * 获取日期的月份
	 * @param date时间格式字符串"yyyy-MM-dd"
	 * @return Integer 月份
	 * */
	public static Integer getDayOfDate(String date){
		return Integer.parseInt(date.substring(8, 10));
	}
	
	/**
	 * 获取日期的月份
	 * @param date时间格式字符串"yyyy-季度"
	 * @return Integer 月份
	 * */
	public static Integer getQuarterOfDate(String date){
		return Integer.parseInt(date.substring(5, 6));
	}
	
	/**
	 * 生成格式字串"yyyy-MM~yyyy-MM(第N季度)"
	 * @param year:Integer 年份
	 * @param quarter:Integer 季度
	 * @return 格式字符串"yyyy-MM~yyyy-MM(第N季度)"
	 * */
	public static String getDateQuarterString(Integer year,Integer quarter){
		Integer startMonth = (quarter - 1) * 3 + 1;
		Integer endMonth = (quarter - 1) * 3 + 3;
		StringBuffer dateString = new StringBuffer();
		dateString.append(String.valueOf(year)).append("-").append(startMonth > 9 ? startMonth : "0" + startMonth)
			.append("~")
			.append(String.valueOf(year)).append("-").append(endMonth > 9 ? endMonth : "0" + endMonth)
			.append("(第").append(quarter).append("季度)");
		
		return dateString.toString();
	}
	
	/**
	 * 生成按季度查询开始日期"yyyy-MM-dd"
	 * @param year:Integer 年份
	 * @param quarter:Integer 季度
	 * @return 日期"yyyy-MM-dd"
	 * */
	public static Date getStartDateQuarter(Integer year,Integer quarter){
		Integer startMonth = (quarter - 1) * 3 + 1;
		StringBuffer dateString = new StringBuffer();
		dateString.append(String.valueOf(year)).append("-").append(startMonth > 9 ? startMonth : "0" + startMonth).append("-01");
		
		return stringToDate(dateString.toString());
	}

	/**
	 * 生成按季度查询结束日期"yyyy-MM-dd"
	 * @param year:Integer 年份
	 * @param quarter:Integer 季度
	 * @return 日期"yyyy-MM-dd"
	 * */
	public static Date getEndDateQuarter(Integer year,Integer quarter){
		Integer endMonth = (quarter - 1) * 3 + 3;
		StringBuffer dateString = new StringBuffer();
		dateString.append(String.valueOf(year)).append("-").append(endMonth > 9 ? endMonth : "0" + endMonth).append("-31");
		
		return stringToDate(dateString.toString());
	}
	
	/**
	 * 生成日期格式字串"yyyy-MM"
	 * @param year:Integer 年份
	 * @param month:Integer 月份
	 * @return date时间格式字符串"yyyy-MM"
	 * */
	public static String getDateMonthString(Integer year,Integer month){
		String dateString = String.valueOf(year);
		if(month > 9){
			dateString = dateString + "-" + month;
		} else {
			dateString = dateString + "-0" + month;
		}
		return dateString;
	}
	
	/**
	 * 生成日期格式字串"yyyy-MM"
	 * @param year:Integer 年
	 * @param month:Integer 月
	 * @param day:Integer 日
	 * @return date时间格式字符串"yyyy-MM-dd"
	 * */
	public static String getDateString(Integer year,Integer month,Integer day){
		String dateString = String.valueOf(year);
		if(month > 9){
			dateString = dateString + "-" + month;
		} else {
			dateString = dateString + "-0" + month;
		}
		if(day > 9){
			dateString = dateString + "-" + day;
		} else {
			dateString = dateString + "-0" + day;
		}
		return dateString;
	}
	
	/**
	 * 判断给定日期是否介于开始时间和结束时间之间
	 * @param date:String 待判断日期
	 * @param startDate:String 开始日期
	 * @param endDate:String 结束日期
	 * @return boolean
	 * */
	public static boolean checkDateBetweenDates(String date,String startDate,String endDate){
		return getYearOfDate(date) >= getYearOfDate(startDate)
				&& getYearOfDate(date) <= getYearOfDate(endDate)
				&& getMonthOfDate(date) >= getMonthOfDate(startDate)
				&& getMonthOfDate(date) <= getMonthOfDate(endDate)
				&& getDayOfDate(date) >= getDayOfDate(startDate)
				&& getDayOfDate(date) <= getDayOfDate(endDate);
	}
}
