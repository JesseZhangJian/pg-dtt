package com.bizdata.commons.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
	
    /** 定义常量 **/
    public static final String DATE_SHORT_STR = "yyyyMM";
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";
    public static final String DATE_Full_KEY_STR = "yyyyMMddHHmmss";
    public static final String DATE_TO_MILLISECOND = "yyyyMMddhhmmssSSS";

    /**
     * 日期格式"yyyy-MM-dd"
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    /**
     * 判断是否对应日期格式
     * 
     * @return String
     */
    public static boolean isDate(String date, String format) {
        boolean flg = false;
        try {
            new SimpleDateFormat(format).parse(date);
            flg = true;
        } catch (ParseException e) {
        	LOGGER.error(e.getMessage());
        }
        return flg;
    }
    
    
    /**
	 * 格式化时间
	 * @param date Date对象
	 * @return 格式化后的字符串
	 */
	public static String format(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String result = sdf.format(date);
		return result;
	}
	
	public static String format1(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String result = sdf.format(date);
		return result;
	}
	
	public static Date format2(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date result;
		try {
			result = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	
	public static Date format4(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date result;
		try {
			result = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	public static Date format3(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date result;
		try {
			result = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	/**
	 * 获取系统当前时间
	 * @return String
	 */
	public static String getCurrentDateTime(){
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
	}
    
    /**
     * 使用预设格式将时间转化到毫秒
     * 
     * @param date
     *            日期
     * @return
     */
    public static String date2MS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TO_MILLISECOND);
        StringBuffer sb = new StringBuffer();
        sb.append(sdf.format(date));
        sb.append(random4Length());
        return sb.toString();
    }

    /**
     * 随机获取0000-9999之间的整数
     * 
     * @author zt 创建时间：2017年1月13日 下午2:38:56
     */
    public static String random4Length() {
        int x;
        String y;
        Random ne = new Random();
        x = ne.nextInt(9999);// 为变量赋随机值1000-9999
        if (x < 1000) {
            y = "0" + x;
        } else {
            y = "" + x;
        }
        return y;
    }

    /**
     * 使用预设格式提取字符串日期
     * 
     * @param strDate
     *            日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, DATE_Full_KEY_STR);
    }

    /**
     * 使用用户格式提取字符串日期
     * 
     * @param strDate
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据日期获取格式化日期
     * 
     * @param d
     * @param formatStr
     * @return
     */
    public static String formatDateTime(Date d, String formatStr) {
        return new SimpleDateFormat(formatStr).format(d);
    }

    /**
     * 两个时间比较
     * 
     * @param date
     * @return
     */
    public static int compareDateWithNow(Date date1) {
        Date date2 = new Date();
        int rnum = date1.compareTo(date2);
        return rnum;
    }

    /**
     * 两个时间比较(时间戳比较)
     * 
     * @param date
     * @return
     */
    public static int compareDateWithNow(long date1) {
        long date2 = dateToUnixTimestamp();
        if (date1 > date2) {
            return 1;
        } else if (date1 < date2) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 获取系统当前时间
     * 
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        return df.format(new Date());
    }

    /**
     * 获取系统当前时间
     * 
     * @return
     */
    public static String getNowTime(String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(new Date());
    }
    
    /**
     * 获取本周五的时间
     */
    public static Date getNowFriday(){
    	//获取本周五的时间
    	Calendar cal =Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
    	Date endTime = DateUtil.format2(DateUtil.format(cal.getTime()) + " 00:00:00");
    	
    	return endTime;
    }

    /**
     * 获取上周五的时间
     * @return
     */
    public static Date getLastFriday(){
    	Calendar cal =Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
    	Date endTime = DateUtil.format2(DateUtil.format(cal.getTime()) + " 00:00:00");
    	
    	//获取上周五的时间
    	cal.setTime(endTime);
    	cal.add(Calendar.DAY_OF_YEAR,-7);        	
    	Date startTime = DateUtil.format2(DateUtil.format(cal.getTime()) + " 00:00:00");
    	
    	return startTime;
    }
    /**
     * 获取系统短时间201310
     * 
     * @return
     */
    public static String getJFPTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_SHORT_STR);
        return df.format(new Date());
    }

    /**
     * 将指定的日期转换成Unix时间戳
     * 
     * @param String
     *            date 需要转换的日期 yyyy-MM-dd HH:mm:ss
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 将指定的日期转换成Unix时间戳
     * 
     * @param String
     *            date 需要转换的日期 yyyy-MM-dd
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date, String dateFormat) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 将当前日期转换成Unix时间戳
     * 
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp() {
        long timestamp = new Date().getTime();
        return timestamp;
    }

    /**
     * 将Unix时间戳转换成日期
     * 
     * @param long
     *            timestamp 时间戳
     * @return String 日期字符串
     */
    public static String unixTimestampToDate(long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }

    /**
     * 获取几天前的时间
     * 
     * @param days
     * @return
     */
    public static String previousDay(int days, String formatStr) {
        Date date = new Date(System.currentTimeMillis() - days * 3600000L * 24L);
        SimpleDateFormat df = new SimpleDateFormat(formatStr);
        return df.format(date);
    }
    
    /**
     * 获取几天前的时间
     * 
     * @param days
     * @return
     */
    public static Date previousDayToDate(int days, String formatStr) {
        Date date = new Date(System.currentTimeMillis() - days * 3600000L * 24L);
        return date;
    }

    /**
     * 获取几小时前的时间
     * 
     * @param days
     * @return
     */
    public static String previousHour(int hours, String formatStr) {
        Date date = new Date(System.currentTimeMillis() - hours * 3600000L);
        SimpleDateFormat df = new SimpleDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 获取几分前的时间
     * 
     * @param days
     * @return
     */
    public static String previousMinute(int hours, String formatStr) {
        Date date = new Date(System.currentTimeMillis() - hours * 60000L);
        SimpleDateFormat df = new SimpleDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 获取几秒前的时间
     * 
     * @param second
     * @param formatStr
     * @return
     */
    public static String previousSecond(int seconds, String formatStr) {
        Date date = new Date(System.currentTimeMillis() - seconds * 1000L);
        SimpleDateFormat df = new SimpleDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 计算两个日期相差多少天
     * 
     * @param d1
     *            Date
     * @param d2
     *            Date
     * @return long
     */
    public static long between(Date d1, Date d2) {
        BigDecimal timeQuantum = new BigDecimal(0);
        BigDecimal bd1 = new BigDecimal(d1.getTime());
        BigDecimal bd2 = new BigDecimal(d2.getTime());
        BigDecimal day = new BigDecimal(24L * 60 * 60 * 1000);
        timeQuantum = bd1.subtract(bd2).divideToIntegralValue(day);
        return timeQuantum.longValue();
    }

    /**
     * 获取时间差，7天一下用距今XXXX，7天以上用日期
     * 
     * @param date
     * @return
     */
    public static String getTimeDifference(Date date) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000));
        long min = ((l / (60 * 1000)));
        long s = (l / 1000);
        // MessageUtils messageUtils = null;
        String strings = "";
        // String strings1 = "";
        // String strings2 = "";
        // String strings3 = "";
        // String strings4 = "";
        // String strings5 = "";
        if (day >= 1 && day < 7) {
            day = l / (24 * 60 * 60 * 1000);
            hour = (l / (60 * 60 * 1000) - day * 24);
            min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            strings = "距现在" + day + "天" + hour + "小时" + min + "分" + s + "秒之前";
            // strings1 =
            // messageUtils.getMessage("customer_analysis.DateUtil_strings_1_1");
            // strings2 =
            // messageUtils.getMessage("customer_analysis.DateUtil_strings_1_2");
            // strings3 =
            // messageUtils.getMessage("customer_analysis.DateUtil_strings_1_3");
            // strings4 =
            // messageUtils.getMessage("customer_analysis.DateUtil_strings_1_4");
            // strings5 =
            // messageUtils.getMessage("customer_analysis.DateUtil_strings_1_5");
            // strings = strings1 + day + strings2 + hour + strings3 + min +
            // strings4 + s + strings5;
        } else if (day < 1 && hour >= 1) {
            hour = (l / (60 * 60 * 1000));
            min = ((l / (60 * 1000)) - hour * 60);
            s = (l / 1000 - hour * 60 * 60 - min * 60);
            strings = "距现在" + hour + "小时" + min + "分" + s + "秒之前";
            // strings=messageUtils.getMessage("");
        } else if (hour < 1 && min >= 1) {
            min = ((l / (60 * 1000)));
            s = (l / 1000 - min * 60);
            strings = "距现在" + min + "分" + s + "秒之前";
            // strings=messageUtils.getMessage("");
        } else if (min < 1 && s >= 1) {
            s = (l / 1000);
            strings = "距现在" + s + "秒之前";
            // strings=messageUtils.getMessage("");
        } else if (min < 1 && s == 0) {
            strings = "距现在" + (s + 1) + "秒之前";// 0秒钟设置为1秒之前
            // strings=messageUtils.getMessage("");
        } else {
            strings = dfs.format(date);
        }
        return strings;
    }

    public static long betweenDay(String s1, String s2) {
        return betweenDay(s1, s2, "yyyy-MM-dd HH:mm:ss");
    }

    public static long betweenDay(String start, String end, String pattern) {
        Date d1 = parse(start, pattern);
        Date d2 = parse(end, pattern);
        BigDecimal timeQuantum = new BigDecimal(0);
        BigDecimal bd1 = new BigDecimal(d1.getTime());
        BigDecimal bd2 = new BigDecimal(d2.getTime());
        BigDecimal day = new BigDecimal(86400000L);
        timeQuantum = bd1.subtract(bd2).divideToIntegralValue(day);
        return timeQuantum.longValue();
    }

    /**
     * 计算两个日期相差多少小时
     * 
     * @param d1
     *            Date
     * @param d2
     *            Date
     * @return long
     */
    public static long betweenHour(Date d1, Date d2) {
        BigDecimal timeQuantum = new BigDecimal(0);
        BigDecimal bd1 = new BigDecimal(d1.getTime());
        BigDecimal bd2 = new BigDecimal(d2.getTime());
        BigDecimal hour = new BigDecimal(60L * 60 * 1000);
        timeQuantum = bd1.subtract(bd2).divideToIntegralValue(hour);
        return timeQuantum.longValue();
    }

    public static long betweenHour(String s1, String s2) {
        Date d1 = parse(s1, "yyyy-MM-dd HH:mm:ss");
        Date d2 = parse(s2, "yyyy-MM-dd HH:mm:ss");
        BigDecimal timeQuantum = new BigDecimal(0);
        BigDecimal bd1 = new BigDecimal(d1.getTime());
        BigDecimal bd2 = new BigDecimal(d2.getTime());
        BigDecimal hour = new BigDecimal(60L * 60 * 1000);
        timeQuantum = bd1.subtract(bd2).divideToIntegralValue(hour);
        return timeQuantum.longValue();
    }

    /**
     * 计算两个日期相差多少分钟
     * 
     * @param d1
     *            Date
     * @param d2
     *            Date
     * @return long
     */
    public static long betweenMinute(Date d1, Date d2) {
        BigDecimal timeQuantum = new BigDecimal(0);
        BigDecimal bd1 = new BigDecimal(d1.getTime());
        BigDecimal bd2 = new BigDecimal(d2.getTime());
        BigDecimal minute = new BigDecimal(60L * 1000);
        timeQuantum = bd1.subtract(bd2).divideToIntegralValue(minute);
        return timeQuantum.longValue();
    }

    public static long betweenMinute(String s1, String s2) {
        Date d1 = parse(s1, "yyyy-MM-dd HH:mm:ss");
        Date d2 = parse(s2, "yyyy-MM-dd HH:mm:ss");
        BigDecimal timeQuantum = new BigDecimal(0);
        BigDecimal bd1 = new BigDecimal(d1.getTime());
        BigDecimal bd2 = new BigDecimal(d2.getTime());
        BigDecimal minute = new BigDecimal(60L * 1000);
        timeQuantum = bd1.subtract(bd2).divideToIntegralValue(minute);
        return timeQuantum.longValue();
    }

    /**
     * 计算两个日期相差多少秒
     * 
     * @param d1
     *            Date
     * @param d2
     *            Date
     * @return long
     */
    public static long betweenSecond(Date d1, Date d2) {
        BigDecimal timeQuantum = new BigDecimal(0);
        BigDecimal bd1 = new BigDecimal(d1.getTime());
        BigDecimal bd2 = new BigDecimal(d2.getTime());
        BigDecimal second = new BigDecimal(1000L);
        timeQuantum = bd1.subtract(bd2).divideToIntegralValue(second);
        return timeQuantum.longValue();
    }

    public static long betweenSecond(String s1, String s2) {
        Date d1 = parse(s1, "yyyy-MM-dd HH:mm:ss");
        Date d2 = parse(s2, "yyyy-MM-dd HH:mm:ss");
        BigDecimal timeQuantum = new BigDecimal(0);
        BigDecimal bd1 = new BigDecimal(d1.getTime());
        BigDecimal bd2 = new BigDecimal(d2.getTime());
        BigDecimal second = new BigDecimal(1000L);
        timeQuantum = bd1.subtract(bd2).divideToIntegralValue(second);
        return timeQuantum.longValue();
    }
    
    /** 
     * 获取当年的第一天 
     * @param year 
     * @return 
     */  
    public static Date getCurrYearFirst(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearFirst(currentYear);  
    }  
      
    /** 
     * 获取当年的最后一天 
     * @param year 
     * @return 
     */  
    public static Date getCurrYearLast(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearLast(currentYear);  
    } 
    
    /** 
     * 获取某年第一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }  
      
    /** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
        return currYearLast;  
    } 

    /**
     * 获取今年年份
     * 
     * @return
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }
    
    /**
     * 根据年月获取每月第一天
     * 
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month - 1);  
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
        return new SimpleDateFormat(DATE_SMALL_STR).format(cal.getTime());  
    }

    /**
     * 根据年月获取每月最后一天
     * 
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month - 1);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
        return new SimpleDateFormat(DATE_SMALL_STR).format(cal.getTime());  
    }   

    /**
     * 获取当前月份
     * 
     * @return
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 获取当月第一天
     * 
     * @return
     */
    public static String getFirstDayOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return formatDateTime(c.getTime(), DATE_FULL_STR);
    }

    /**
     * 获取当月最后一天
     * 
     * @return
     */
    public static String getFirstDayOfCurrentMouth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatDateTime(c.getTime(), DATE_FULL_STR);
    }

    /**
     * 获取当月第一天
     * 
     * @return
     */
    public static String getLastDayOfCurrentMouth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);

        return formatDateTime(c.getTime(), DATE_FULL_STR);
    }

    /**
     * 获取上月第一天
     * 
     * @return
     */
    public static Date getPreviousMonthBegin() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Calendar dateToCalender(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    // 根据生日获取生肖
    public static String getZodica(Date birthday) {
        return Zodica.date2Zodica(dateToCalender(birthday));
    }

    // 根据生日获取星座
    public static String getConstellation(Date birthday) {
        return Constellation.date2Constellation(dateToCalender(birthday));
    }

    /**
     * 返回时分秒 getHourMinute("2013-12-17 10:30:20",formatDateTime(new
     * Date(),DATE_FULL_STR))结果5小时48分
     * 
     * @param start_time
     * @param end_time
     * @return
     */
    public static String getHourMinute(String start_time, String end_time) {
        String result = "";
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date begin = dfs.parse(start_time);
            Date end = dfs.parse(end_time);
            long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

            long day1 = between / (24 * 3600);
            long hour1 = between % (24 * 3600) / 3600;
            long minute1 = between % 3600 / 60;
            if (day1 > 3) {
                result = "3天以上";
            } else {
                String day = day1 <= 0 ? "" : day1 + "天";
                String hour = hour1 <= 0 ? "" : hour1 + "小时";
                String minute = minute1 <= 0 ? "" : minute1 + "分";
                result = day + hour + minute;
            }

            // //System.out.println(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String toFormatRQ(String time) {
        String shortstring = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (date == null)
            return shortstring;
        long now = Calendar.getInstance().getTimeInMillis();
        long deltime = (now - date.getTime()) / 1000;
        if (deltime > 365 * 24 * 60 * 60) {
            shortstring = (int) (deltime / (365 * 24 * 60 * 60)) + "年前";
        } else if (deltime > 24 * 60 * 60) {
            shortstring = (int) (deltime / (24 * 60 * 60)) + "天前";
        } else if (deltime > 60 * 60) {
            shortstring = (int) (deltime / (60 * 60)) + "小时前";
        } else if (deltime > 60) {
            shortstring = (int) (deltime / (60)) + "分钟前";
        } else if (deltime > 1) {
            shortstring = deltime + "秒前";
        } else {
            shortstring = "1秒前";
        }
        return shortstring;
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
    /**
     * 给指定的时间增加天数，放回不带时间的日期
     * 
     * @param date
     * @param count
     * @return Date
     */
    public static Date addDayNoTime(Date date, int count) {
        return addDay(parse(getDateFormat(date)), count);
    }

    /**
     * 对传入的日期进行格式化处理 默认格式为yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
        return getDateFormat(date, FORMAT_DATE);
    }

    /**
     * 对传入的日期进行格式化处理
     * 
     * @param date
     * @param format
     * @return
     */
    public static String getDateFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    /**
     * 给指定时间增加天数
     * 
     * @param date
     * @param count
     * @return Date
     */
    public static Date addDay(Date date, int count) {
        return new Date(date.getTime() + 86400000L * count);
    }
    
    /**
     * 比较两个日期的大小(日期格式为：yyyy-MM-dd)
     * 
     * @param date1
     * @param date2
     * @return int
     */
    public static int compare(String date1, String date2) {
        return compare(date1, date2, DATE_SMALL_STR);
    }

    /**
     * 根据传入的格式比较两个时间大小 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param date1
     * @param date2
     * @param format
     * @return int
     */
    public static int compare(String date1, String date2, String format) {
        Date d1 = parseStrToDate(date1, format);
        Date d2 = parseStrToDate(date2, format);
        return d1.compareTo(d2);
    }
    /**
     * 将字符串类型转换为日期类型
     * 
     * @param str
     *            <要转换的字符串>
     * @param format
     *            <转换后的格式>
     * @return Date
     */
    public static Date parseStrToDate(String str, String format) {
        if (str == null || str.length() == 0 || format == null || format.length() == 0) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            return sdf.parse(str);
        } catch (ParseException e) {
        	LOGGER.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 返回传入日期是一年中的几月
     * 
     * @param date
     * @return int
     */
    public static int getMonthOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 返回日期是月中的第几天
     * 
     * @param date
     * @return int
     */
    public static int getDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 月份转换En
     * @param month
     * @return
     */
    public static String getMonthToEn(int month){
    	String result = "";
    	switch (month) {
			case 1:
				result = "Jan";
				break;
			case 2:
				result = "Feb";
				break;
			case 3:
				result = "Mar";
				break;
			case 4:
				result = "Apr";
				break;
			case 5:
				result = "May";
				break;
			case 6:
				result = "Jun";
				break;
			case 7:
				result = "Jul";
				break;
			case 8:
				result = "Aug";
				break;
			case 9:
				result = "Sep";
				break;
			case 10:
				result = "Oct";
				break;
			case 11:
				result = "Nov";
				break;
			case 12:
				result = "Dec";
				break;
			default:
				break;
			}
    	return result;
    }
    
    /*
     * public static void main(String[] args) {
     * System.out.println(getFirstDayOfCurrentMonth()); Date date=
     * parse("2016/12/19 15:21:09","yyyy/MM/dd HH:mm");
     * 
     * //SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd日 hh:mm:ss");
     * System.out.println(date.toString());
     * 
     * }
     */

}

// 生肖
class Zodica {
    private static final String[] zodiacList = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };

    public static String date2Zodica(Calendar time) {
        return zodiacList[time.get(Calendar.YEAR) % 12];
    }
}

// 星座
class Constellation {
    private static final String[] constellationList = { "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
            "天蝎座", "射手座", "魔羯座" };
    private static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };

    public static String date2Constellation(Calendar time) {
        int month = time.get(Calendar.MONTH);
        int day = time.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationList[month];
        }
        // default to return 魔羯
        return constellationList[11];
    }

}
