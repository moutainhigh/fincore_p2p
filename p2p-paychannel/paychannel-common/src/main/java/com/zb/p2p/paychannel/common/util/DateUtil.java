package com.zb.p2p.paychannel.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 时间工具类
 * 
 * @author szy
 *
 */
public final class DateUtil extends DateUtils {
	
	/** 一天的秒数 */
	public final static long ONE_DAY_SECONDS = 86400;
	/** 一天的毫秒数 */
	public final static long ONE_DAY_MILL_SECONDS = 86400000;
	/** yyyyMMdd时间格式 */
	public final static String SHORT_FORMAT = "yyyyMMdd";
	/** yyyyMMddHHmmss时间格式 */
	public final static String LONG_FORMAT = "yyyyMMddHHmmss";
	/** 比long少秒的日期格式 yyyyMMddHHmm */
	public final static String LOWER_LONG_FORMAT = "yyyyMMddHHmm";
	/** yyyy-MM-dd时间格式 */
	public final static String WEB_FORMAT = "yyyy-MM-dd";
	/** HHmmss时间格式 */
	public final static String TIME_FORMAT = "HHmmss";
	/** yyyyMM时间格式 */
	public final static String MONTH_FORMAT = "yyyyMM";
	/** yyyy年MM月dd日时间格式 */
	public final static String CHINESE_DT_FORMAT = "yyyy年MM月dd日";
	/** yyyy-MM-dd HH:mm:ss时间格式 */
	public final static String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd HH:mm时间格式 */
	public final static String NO_SECOND_FORMAT = "yyyy-MM-dd HH:mm";
	/** yyyy时间格式 */
	public static String YEAR_FORMAT = "yyyy";
	/** yyyy年MM月dd日HH点mm分ss秒 */
	public final static String CHINESE_ALL_DATE_FORMAT = "yyyy年MM月dd日HH点mm分ss秒";
	/** HH:mm时间格式 */
	public final static String HOURS_FORMAT = "HH:mm";
	/** HH:mm时间格式 */
	public final static String SECOND_FORMAT = "HH:mm:ss";
	/** yyyy.MM.dd 时间格式 */
	public final static String DATE_PICKER_FORMAT = "yyyy.MM.dd";
	/** 零时零分零秒 */
	public static final String START_TIME = " 00:00:00";
	/** 23:59:59 */
	public static final String END_TIME = " 23:59:59";

	public static final String FORMATE_WITH_MILLISECONT = "yyyy-MM-dd HH:mm:ss:SSS";

	/**
	 * 按给定格式返回日期的文本形式
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if(date == null)
			return "";
		return new SimpleDateFormat(pattern).format(date);
	}

	public static String formatDate(final Date date) {
		if (date == null)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String formatDateTime(final Date date) {
		if (date == null)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 计算指定时间域后的时间,如2个月前的日期( 2011-11-11 11:11:11, Calendar.MONTH, -2, true) 结果为 2011-09-11 00:00:00
	 *
	 * @param date      参数时间，为空默认为当前时间
	 * @param field     时间域，如Calendar.MONTH
	 * @param amount    增加值，负数为减少值
	 * @param cleanTime 是否清空时间，如传入2016-11-11 11:11:11会转化为2016-11-11 00:00:00
	 */
	public static Date getDateAfterField(Date date, int field, int amount, boolean cleanTime) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		if (cleanTime) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		}
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 获取指定时间域的开始时间，如(2011-11-11 11:11:11, Calendar.MONTH) 返回 2011-11-01 00:00:00
	 *
	 * @param date  参数时间
	 * @param field 时间域，如Calendar.MONTH
	 */
	public static Date getBeginTime(Date date, int field) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		switch (field) {
			case Calendar.YEAR:
				calendar.set(Calendar.MONTH, 1);
			case Calendar.MONTH:
				calendar.set(Calendar.DAY_OF_MONTH, 1);
			default:
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
		}
		return calendar.getTime();
	}
	/**
	 * 取 days 天后的日期
	 * 
	 * @param day
	 * @return date
	 */
	public static Date getDateAfterDays(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	/**
	 * 获得yyyyMMddHHmmss格式的时间
	 * 
	 * @param date
	 *            转换前的时间
	 * @return 转换后的时间
	 */
	public static String getLongDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(LONG_FORMAT);

		return getDateString(date, dateFormat);
	}
	
	/**
	 * 格式化时间
	 * 
	 * @param date
	 *            转换前的日期
	 * @param dateFormat
	 *            需要转换的格式
	 * @return 转换后的日期
	 */
	public static String getDateString(Date date, DateFormat dateFormat) {
		if (date == null || dateFormat == null) {
			return null;
		}

		return dateFormat.format(date);
	}
	
	/**
	 * 将给定格式的文本形式日期转换为Date类型
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse(String dateStr, String pattern) {

		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 传入结束时间，判断当前时间与结束时间的时间间隔，类似于“1天25分38秒”这种格式
	 * @param endDate
	 * @return
	 */
	public static String getSurplusDate(Date endDate) {
		Date newDate = new Date();
		Long supmin = endDate.getTime()-newDate.getTime();
		if(supmin>0){
			long day=supmin/(24*60*60*1000);
			long sur1 = supmin-day*24*60*60*1000;
			
			long hour=sur1/(60*60*1000);
			long sur2 = sur1-hour*60*60*1000;
			
			long min=sur2/(60*1000);
			long sur3 = sur2-min*60*1000;
			
			long mis=sur3/(1000);
			//return day+"天"+hour+"时"+min+"分"+mis+"秒";
			return day+","+hour+","+min+","+mis;
		}else{
			//return "0天0时0分0秒";
			return "0,0,0,0";
		}
	}
	/**
	 * 传入结束时间，判断当前时间与结束时间的时间间隔，类似于“1天25分38秒”这种格式
	 * @param endDate
	 * @return
	 */
	public static String getSurplusDateString(Date endDate) {
		Date newDate = new Date();
		Long supmin = endDate.getTime()-newDate.getTime();
		if(supmin>0){
			long day=supmin/(24*60*60*1000);
			String dayStr = day+"";
			if(day<10){
				dayStr = "0"+dayStr;
			}
			long sur1 = supmin-day*24*60*60*1000;
			
			long hour=sur1/(60*60*1000);
			String hourStr = hour+"";
			if(hour<10){
				hourStr = "0"+hourStr;
			}
			long sur2 = sur1-hour*60*60*1000;
			
			long min=sur2/(60*1000);
			String minStr = min+"";
			if(min<10){
				minStr = "0"+minStr;
			}
			long sur3 = sur2-min*60*1000;
			
			long mis=sur3/(1000);
			String misStr = mis+"";
			if(mis<10){
				misStr = "0"+misStr;
			}
			if(day+hour+min < 1){
				return misStr+"秒";
			}else if(day+hour < 1){
				return minStr+"分"+misStr+"秒";
			}else if(day < 1){
				return hourStr+"小时"+minStr+"分"+misStr+"秒";
			}
			return dayStr+"天"+hourStr+"小时"+minStr+"分"+misStr+"秒";
		}else{
			return "00天00小时00分00秒";
		}
	}
	
	/**
	 * 传入两日期，获取相差的小时
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParsException 
	 */
	public static long differDateHours(Date startDate,Date endDate) throws ParseException {
		Long l = endDate.getTime() - startDate.getTime();
		long hours = l/(60*60*1000);
		return hours;
	}
	/**
	 * 取日期计算后的值
	 * 
	 * @param currentDate传过来的日期值
	 * @param day 需要计算值，如加一天，则day为1；加5天，day为5
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static Date getDaysCounts(Date currentDate, int day) {
		Calendar   calendar   =   new GregorianCalendar(); 
		calendar.setTime(currentDate); 
	    calendar.add(calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动 
	    currentDate=calendar.getTime();   //这个时间就是日期往后推一天的结果 

		return currentDate;
	}
	/**
	 * 返回日期或者时间，如果传入的是日期，返回日期的 23:59:59 时间
	 * @param timeString
	 * @return
	 * @throws Exception
	 */
	public static Timestamp getDateLast(String timeString) throws Exception{
		if (timeString == null || timeString.equals("")) {
			return null;
		}
		if (timeString.length() > 10) {
			return getTime(timeString, NEW_FORMAT);
		} else {
			return getTime(timeString +" 23:59:59", NEW_FORMAT);
		}
	}
	/**
	 * 自定义格式的字符串转换成日期
	 * @param timeString
	 * @param fmt
	 * @return
	 * @throws Exception
	 */
	public static Timestamp getTime(String timeString, String fmt) throws Exception {
		if(StringUtils.isBlank(timeString)) return null;
		SimpleDateFormat myFormat = new SimpleDateFormat(fmt);
		Date date = myFormat.parse(timeString);
		myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return getTime(myFormat.format(date));
	}
	/**
	 * yyyy-MM-dd HH:mm:ss 转换成 Timestamp
	 * @param timeString
	 * @return
	 */
	public static Timestamp getTime(String timeString) {
		return Timestamp.valueOf(timeString);
	}
	public static Integer getYear(Date date) {
		SimpleDateFormat myFormat = new SimpleDateFormat(YEAR_FORMAT);
		return Integer.parseInt(myFormat.format(null == date?new Date():date));
	}
	public static String getBirthdayStr(Date birthday) {
		SimpleDateFormat BIRTHDAY_FORMAT = new SimpleDateFormat(WEB_FORMAT);
		String birthdayStr = BIRTHDAY_FORMAT.format(birthday);
		String[] arr = birthdayStr.split("-");
		String result = arr[0] + "年" + arr[1] + "月" + arr[2] + "日";
		return result;
	}
	
	/**
	 * 获取当前时间的时间戳，精确到毫秒
	 * @return 返回当前时间的时间戳
	 */
	public static Long getTimestamp()
	{
		return System.currentTimeMillis();
	}
	/**
	 * 获取当天剩余的秒数
	 * @return
	 */
	public static long getSecondsLeftNow() {
		long currentTimeMillis = System.currentTimeMillis();
		long diff = ONE_DAY_MILL_SECONDS - (currentTimeMillis % ONE_DAY_MILL_SECONDS);
		return diff;
	}
	
	public static void main(String[] args) {
		System.out.println(formatDate(new Date()));
	}
	
    /**
     * 查询指定时间是星期几,分别用数字1~7表示
     * @param date
     */
	public static int dayForWeek(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
