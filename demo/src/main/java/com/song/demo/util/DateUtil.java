/** 
 * Project Name:meatball-common
 * File Name:PhoneFormatCheckUtil.java 
 * Package Name:com.meatball.common.utils
 * Date:2017年10月7日下午1:37:02 
 * Copyright (c) 2017, zhang.xiangyu@foxmail.com All Rights Reserved. 
 */ 
package com.song.demo.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


	/**
	 * @Title: getYear
	 * @Description: todo(获取YYYY格式)
	 * @Params: []
	 * @Return: java.lang.String    返回类型
	 * @Author: 張翔宇
	 * @Date: 2018/10/14 下午5:34
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * @Title: getLastYear
	 * @Description: todo(获取上一年年份)
	 * @Params: []
	 * @Return: java.lang.String    返回类型
	 * @Author: 張翔宇
	 * @Date: 2018/10/14 下午5:34
	 */
	public static String getLastYear() {
		return String.valueOf(Integer.valueOf(formatDate(new Date(), "yyyy")) - 1);
	}

	/**
	 * @Title: getYear
	 * @Description: todo(获取指定日期YYYY格式)
	 * @Params: [date]
	 * @Return: java.lang.String    返回类型
	 * @Author: 張翔宇
	 * @Date: 2018/10/14 下午5:35
	 */
	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}

	/**
	 * @Title: getDay
	 * @Description: todo(获取YYYY-MM-DD格式)
	 * @Params: []
	 * @Return: java.lang.String    返回类型
	 * @Author: 張翔宇
	 * @Date: 2018/10/14 下午5:35
	 */
	public static String getDay() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 * @Title: getDay
	 * @Description: todo(获取指定日期的YYYY-MM-DD格式)
	 * @Params: [date]
	 * @Return: java.lang.String    返回类型
	 * @Author: 張翔宇
	 * @Date: 2018/10/14 下午5:36
	 */
	public static String getDay(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * @Title: getDays 
	 * @Description: TODO(获取YYYYMMDD格式) 
	 * @return String    返回类型
	 */
	public static String getDays() {
		return formatDate(new Date(), "yyyyMMdd");
	}

	/**
	 * @Title: getDays 
	 * @Description: TODO(获取YYYYMMDD格式) 
	 * @param date
	 * @return String    返回类型
	 */
	public static String getDays(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * @Title: getTime 
	 * @Description: TODO(获取YYYY-MM-DD HH:mm:ss格式) 
	 * @return String    返回类型
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @Title: getTime
	 * @Description: TODO(获取YYYY-MM-DD HH:mm:ss格式)
	 * @param date
	 * @return String    返回类型
	 */
	public static String getDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @Title: getAllTime 
	 * @Description: TODO(获取YYYYMMDDHHmmss格式) 
	 * @return String    返回类型
	 */
	public static String getAllTime() {
		return formatDate(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * @Title: formatDate
	 * @Description: todo(根据自定义格式化日期)
	 * @Params: [date, pattern]
	 * @Return: java.lang.String    返回类型
	 * @Author: 張翔宇
	 * @Date: 2018/10/14 下午5:45
	 */
	public static String formatDate(Date date, String pattern) {
		String formatDate;
		if (StringUtils.isNotBlank(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * @Title: format 
	 * @Description: TODO(格式化日期) 
	 * @param date
	 * @param pattern
	 * @return String    返回类型
	 */
	public static String format(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * @Title: format 
	 * @Description: TODO(把日期转换为Timestamp) 
	 * @param date
	 * @return Timestamp    返回类型
	 */
	public static Timestamp format(Date date) {
		return new Timestamp(date.getTime());
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
					startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	// 获得本周一0点时间
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	// 获得本月第一天0点时间
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * @Title: getDaySub 
	 * @Description: TODO(功能描述：时间相减得到天数) 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long    返回类型
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * @Title: getAfterDayDate 
	 * @Description: TODO(得到n天之后的日期) 
	 * @param days
	 * @return String    返回类型
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * @Title: getAfterDayWeek 
	 * @Description: TODO(得到n天之后是周几) 
	 * @param days
	 * @return String    返回类型
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);
		// java.util包
		Calendar canlendar = Calendar.getInstance();
		// 日期减 如果不够减会将月变动
		canlendar.add(Calendar.DATE, daysInt);
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	/**
	 * 使用预设格式将字符串转为Date
	 */
	public static Date parse(String strDate){
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(strDate);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 使用预设格式将字符串转为Date
	 * 只有日期的时候，转换为yyyy-MM-dd 23:59:59
	 */
	public static Date maximumParse(String strDate){
		try {
			if(!isDateString(strDate)){
				strDate = strDate+" 23:59:59";
			}

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(strDate);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 使用预设格式将字符串转为Date
	 * 只有日期的时候，转换为yyyy-MM-dd 00:00:00
	 */
	public static Date minimumParse(String strDate){
		try {
			if(!isDateString(strDate)){
				strDate = strDate+" 00:00:00";
			}

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(strDate);
			return date;
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * @param args
	 * @return void    返回类型
	 * @Title: main
	 * @Description: TODO(格式化Oracle Date)
	 */
//	public static String buildDateValue(Object value){
//		if(Func.isOracle()){
//			return "to_date('"+ value +"','yyyy-mm-dd HH24:MI:SS')";
//		}else{
//			return Func.toStr(value);
//		}
//	}
	/**
	 *
	 * 功能描述: yyyy-mm-dd格式字符串转日期
	 *
	 * @param: [str]
	 * @return: java.util.Date
	 * @auther: 马志超
	 * @date: 2018/10/18 11:23
	 * @Version V1.0
	 */
	public static Date str2Date(String str) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(str);
		return date;
	}

//	public static void main(String[] args) throws ParseException {
//		System.out.println();
//	}

	public static boolean isDateString(String datevalue, String dateFormat) {
		if (StringUtils.isBlank(datevalue)) {
			return false;
		}
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
			Date dd = fmt.parse(datevalue);
			if (datevalue.equals(fmt.format(dd))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDateString(String datevalue) {
		return isDateString(datevalue,"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取指定日期是星期几（设置星期一为一周的开始）
	 * @param day 指定日期
	 * @return 返回星期几，如：1
	 */
	public static int getDayOfWeek(String day){
		int dayOfWeek = 0;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateFormat.parse(day));
			calendar.add(Calendar.DATE, -1); //在指定日期的基础上减一天，满足中国人的习惯
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
			return dayOfWeek;
		}
		return dayOfWeek;
	}

}
