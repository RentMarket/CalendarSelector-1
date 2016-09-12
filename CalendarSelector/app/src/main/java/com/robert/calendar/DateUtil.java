/**
 * DateUtil.java [v 1.0.0]
 * class:com.rroa.common.DateUtil
 * 高翔 Create at 2013-11-1.上午9:57:59
 */
package com.robert.calendar;

import android.text.TextUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateUtil {

	public static final int DAY_TimeMillis = 86400000;

	static final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

	// 格式：年－月－日 小时：分钟：秒
	public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";

	// 格式：年月日 小时分钟秒
	public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";

	public static final String FORMAT_FOUR = "yyyy-MM-dd HH:mm:ss.SSS";

	// 格式：年－月－日 小时：分钟
	public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

	// 格式：年－月－日
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

	// 格式：小时：分钟：秒
	public static final String LONG_TIME_FORMAT = "HH:mm:ss";

	// 格式：23:33
	public static final String LONG_TIME_NO_AP_FORMAT = "HH:mm";
	// 格式：年-月
	public static final String MONTG_DATE_FORMAT = "yyyy-MM";
	// 格式：月－日
	public static final String SHORT_DATE_FORMAT = "MM-dd";

	// 格式：8：45 pm
	public static final String SHORT_TIME_AP_FORMAT = "hh:mm a";

	// 天的加减
	public static final int SUB_DAY = Calendar.DATE;

	// 小时的加减
	public static final int SUB_HOUR = Calendar.HOUR;

	// 分钟的加减
	public static final int SUB_MINUTE = Calendar.MINUTE;

	// 月加减
	public static final int SUB_MONTH = Calendar.MONTH;

	// 秒的加减
	public static final int SUB_SECOND = Calendar.SECOND;

	// 年的加减
	public static final int SUB_YEAR = Calendar.YEAR;

	@SuppressWarnings("unused")
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	 *
	 * @param sourceDate
	 * @param formatLength
	 * @return 重组后的数据
	 */
	public static String addzero(int sourceDate, int formatLength) {
		/*
		 * 0 指前面补充零 formatLength 字符总长度为 formatLength d 代表为正数。
		 */
		String newString = String.format("%0" + formatLength + "d", sourceDate);
		return newString;
	}

	/**
	 * 获取明天的日期
	 */
	public static String afterDay() {
		return DateUtil.dateToString(DateUtil.nextDay(new Date(), 1), DateUtil.LONG_DATE_FORMAT);
	}

	/**
	 * 获取昨天的日期
	 *
	 * @return
	 */
	public static String befoDay() {
		return befoDay(DateUtil.LONG_DATE_FORMAT);
	}

	/**
	 * 根据时间类型获取昨天的日期
	 *
	 * @param format
	 * @return
	 * @author chenyz
	 */
	public static String befoDay(String format) {
		return DateUtil.dateToString(DateUtil.nextDay(new Date(), -1), format);
	}

	/**
	 * 获取当前的日期(yyyy-MM-dd)
	 */
	public static String currDay() {
		return DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT);
	}

	/**
	 * @param dateStr
	 * @param amount
	 * @return
	 */
	public static String dateSub(int dateKind, String dateStr, int amount) {
		Date date = stringtoDate(dateStr, FORMAT_ONE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(dateKind, amount);
		return dateToString(calendar.getTime(), FORMAT_ONE);
	}

	/**
	 * 把日期转换为字符串
	 *
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		String result = "";
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			result = formater.format(date);
		} catch (Exception e) {
			// log.error(e);
		}
		return result;
	}

	/**
	 * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
	 *
	 * @param date1 Date
	 * @param date2 Date
	 * @return long
	 */
	public static long dayDiff(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / DAY_TimeMillis;
	}

	public static long dayDiff(long currentTimeMillis, long endTime_long) {
		return (currentTimeMillis - endTime_long) / DAY_TimeMillis;
	}

	/**
	 * 比较指定日期与当前日期的差
	 *
	 * @param before
	 * @return
	 * @author chenyz
	 */
	public static long dayDiffCurr(String before) {
		Date currDate = DateUtil.stringtoDate(currDay(), LONG_DATE_FORMAT);
		Date beforeDate = stringtoDate(before, LONG_DATE_FORMAT);
		return (currDate.getTime() - beforeDate.getTime()) / DAY_TimeMillis;

	}

	/**
	 * 根据生日获取星座
	 *
	 * @param birth YYYY-mm-dd
	 * @return
	 */
	public static String getAstro(String birth) {
		if (!isDate(birth)) {
			birth = "2000" + birth;
		}
		if (!isDate(birth)) {
			return "";
		}
		int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1, birth.lastIndexOf("-")));
		int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
		String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
		int[] arr = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};
		int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
		return s.substring(start, start + 2) + "座";
	}

	public static String getChatTime(long timesamp) {
		if (timesamp > 0) {
			String result = "";
			SimpleDateFormat sdf = new SimpleDateFormat("dd");
			Date today = new Date(System.currentTimeMillis());
			Date otherDay = new Date(timesamp);
			int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
			switch (temp) {
				case 0:
					result = "今天 " + getHourAndMin(timesamp);
					break;
				case 1:
					result = "昨天 " + getHourAndMin(timesamp);
					break;
				case 2:
					result = "前天 " + getHourAndMin(timesamp);
					break;
				default:
					result = getTime(timesamp);
					break;
			}
			return result;
		} else {
			return "";
		}
	}

	/**
	 * 是否显示聊天时间
	 *
	 * @param timesamp      新的消息时间
	 * @param otherTimesamp 上条消息时间
	 * @return-间隔超过num分钟?true:false
	 */
	public static boolean getChatTimeVisible(long timesamp, long otherTimesamp, int num) {
		if (otherTimesamp == 0) {
			return true;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			Date time = new Date(timesamp);
			Date otherTime = new Date(otherTimesamp);
			long temp = time.getTime() / 60000 - otherTime.getTime() / 60000;
			return temp <= num ? false : true;
		}
	}

	/**
	 * 获取当前时间的指定格式
	 *
	 * @param format
	 * @return
	 */
	public static String getCurrDate(String format) {
		return dateToString(new Date(), format);
	}

	/**
	 * 获得当前日期字符串，格式"yyyy_MM_dd_HH_mm_ss"
	 *
	 * @return
	 */
	public static String getCurrent() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		StringBuffer sb = new StringBuffer();
		sb.append(year).append("_").append(addzero(month, 2)).append("_").append(addzero(day, 2)).append("_")
				.append(addzero(hour, 2)).append("_").append(addzero(minute, 2)).append("_").append(addzero(second, 2));
		return sb.toString();
	}

	/**
	 * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
	 *
	 * @param day
	 * @return
	 */
	public static Date getDateByNum(int day) {
		GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
		Date date = gd.getTime();
		date = nextDay(date, day);
		return date;
	}

	/**
	 * 返回日期的天
	 *
	 * @param date Date
	 * @return int
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 取得当前时间距离1900/1/1的天数
	 *
	 * @return
	 */
	public static int getDayNum() {
		int daynum = 0;
		GregorianCalendar gd = new GregorianCalendar();
		Date dt = gd.getTime();
		GregorianCalendar gd1 = new GregorianCalendar(1900, 1, 1);
		Date dt1 = gd1.getTime();
		daynum = (int) ((dt.getTime() - dt1.getTime()) / (24 * 60 * 60 * 1000));
		return daynum;
	}

	/**
	 * 获取某年某月的天数
	 *
	 * @param year  int
	 * @param month int 月份[1-12]
	 * @return int
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得某月的天数
	 *
	 * @param year  int
	 * @param month int
	 * @return int
	 */
	public static int getDaysOfMonth(String year, String month) {
		int days = 0;
		if (month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8")
				|| month.equals("10") || month.equals("12")) {
			days = 31;
		} else if (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")) {
			days = 30;
		} else {
			if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
					|| Integer.parseInt(year) % 400 == 0) {
				days = 29;
			} else {
				days = 28;
			}
		}

		return days;
	}

	/**
	 * 获取本月第一天
	 *
	 * @param format
	 * @return
	 */
	public static String getFirstDayOfMonth(String format) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		return dateToString(cal.getTime(), format);
	}

	/**
	 * 获取每月的第一周
	 *
	 * @param year
	 * @param month
	 * @return
	 * @author chenyz
	 */
	public static int getFirstWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, 1);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	public static String getHourAndMin(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}

	/**
	 * 获取本月最后一天
	 *
	 * @param format
	 * @return
	 */
	public static String getLastDayOfMonth(String format) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return dateToString(cal.getTime(), format);
	}

	/**
	 * 获取每月的最后一周
	 *
	 * @param year
	 * @param month
	 * @return
	 * @author chenyz
	 */
	public static int getLastWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, getDaysOfMonth(year, month));
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 返回日期的月份，1-12
	 *
	 * @param date Date
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回日期中的小时
	 *
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回日期中的分钟
	 *
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
	 *
	 * @return
	 */
	public static String getNow() {
		Calendar today = Calendar.getInstance();
		return dateToString(today.getTime(), FORMAT_ONE);
	}

	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
		return format.format(new Date(time));
	}

	/**
	 * 获得当前日期
	 *
	 * @return int
	 */
	public static int getToday() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得当前月份
	 *
	 * @return int
	 */
	public static int getToMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前年份
	 *
	 * @return int
	 */
	public static int getToYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的年
	 *
	 * @param date Date
	 * @return int
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 针对yyyy-MM-dd HH:mm:ss格式,显示yyyymmdd
	 */
	public static String getYmdDateCN(String datestr) {
		if (datestr == null)
			return "";
		if (datestr.length() < 10)
			return "";
		StringBuffer buf = new StringBuffer();
		buf.append(datestr.substring(0, 4)).append(datestr.substring(5, 7)).append(datestr.substring(8, 10));
		return buf.toString();
	}

	public static long hourDiff(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 3600000;
	}

	/**
	 * 判断日期是否有效,包括闰年的情况
	 *
	 * @param date YYYY-mm-dd
	 * @return
	 */
	public static boolean isDate(String date) {
		StringBuffer reg = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
		reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
		reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
		reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
		reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
		reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern p = Pattern.compile(reg.toString());
		return p.matcher(date).matches();
	}

	/**
	 * 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
	 *
	 * @param date  日期 为null时表示当天
	 * @param month 相加(相减)的月数
	 */
	public static Date nextDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTime();
	}

	/**
	 * 取得距离今天 day 日的日期
	 *
	 * @param day
	 * @param format
	 * @return
	 * @author chenyz
	 */
	public static String nextDay(int day, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, day);
		return dateToString(cal.getTime(), format);
	}

	/**
	 * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
	 *
	 * @param date  日期 为null时表示当天
	 * @param month 相加(相减)的月数
	 */
	public static Date nextMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 取得指定日期过 day 周后的日期 (当 day 为负数表示指定月之前)
	 *
	 * @param date 日期 为null时表示当天
	 */
	public static Date nextWeek(Date date, int week) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.WEEK_OF_MONTH, week);
		return cal.getTime();
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 *
	 * @param dateStr
	 * @return
	 */
	public static Date stringtoDate(String dateStr, String format) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr);
		} catch (Exception e) {
			// log.error(e);
			d = null;
		}
		return d;
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 */
	public static Date stringtoDate(String dateStr, String format, ParsePosition pos) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr, pos);
		} catch (Exception e) {
			d = null;
		}
		return d;
	}

	/**
	 * 两个日期相减
	 *
	 * @param firstTime
	 * @param secTime
	 * @return 相减得到的秒数
	 */
	public static long timeSub(String firstTime, String secTime) {
		long first = stringtoDate(firstTime, FORMAT_ONE).getTime();
		long second = stringtoDate(secTime, FORMAT_ONE).getTime();
		return (second - first) / 1000;
	}

	/**
	 * 获得以现在时间比较的XX天xx小时xx秒前
	 *
	 * @param time
	 * @return
	 */
	public static String toStringDateFromNow(long time) {
		String returnStr;
		long currentDate = System.currentTimeMillis();
		long dtime = currentDate - time;
		if (dtime < 3600000) {
			int m = (int) Math.floor(dtime / 60000);
			if (m <= 0) {
				returnStr = "刚刚";
			} else {
				returnStr = m + "分钟前";
			}
		} else if (dtime < DAY_TimeMillis) {
			returnStr = (int) Math.floor(dtime / 1000 / 60 / 60) + "小时前";
		} else if (dtime < 172800000) {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
			returnStr = "昨天 " + timeFormat.format(new Date(time));
		} else if (dtime < 259200000) {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
			returnStr = "前天 " + timeFormat.format(new Date(time));
		} else if (dtime < 345600000) {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
			returnStr = (int) Math.floor(dtime / DAY_TimeMillis) + "天前的 " + timeFormat.format(new Date(time));
		} else {
			SimpleDateFormat timeFormat = new SimpleDateFormat("yy年MM月dd HH:mm", Locale.CHINA);
			returnStr = timeFormat.format(new Date(time));
		}
		return returnStr;
	}

	public static String toStringDateForCallLog(long time) {
		String returnStr;
		long currentDate = System.currentTimeMillis();
		long dtime = currentDate - time;
		if (dtime < 3600000) {
			int m = (int) Math.floor(dtime / 60000);
			if (m <= 0) {
				returnStr = "刚刚";
			} else {
				returnStr = m + "分钟前";
			}
		} else if (dtime < DAY_TimeMillis) {
			returnStr = (int) Math.floor(dtime / 1000 / 60 / 60) + "小时前";
		} else if (dtime < 172800000) {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
			returnStr = "昨天 " + timeFormat.format(new Date(time));
		} else if (dtime < 259200000) {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
			returnStr = "前天 " + timeFormat.format(new Date(time));
		} else if (dtime < 345600000) {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
			returnStr = (int) Math.floor(dtime / DAY_TimeMillis) + "天前的 " + timeFormat.format(new Date(time));
		} else {
			SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd", Locale.CHINA);
			returnStr = timeFormat.format(new Date(time));
		}
		return returnStr;
	}

	/**
	 * 比较两个日期的年差
	 *
	 * @param befor
	 * @param after
	 * @return
	 */
	public static int yearDiff(String before, String after) {
		Date beforeDay = stringtoDate(before, LONG_DATE_FORMAT);
		Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
		return getYear(afterDay) - getYear(beforeDay);
	}

	/**
	 * 比较指定日期与当前日期的差
	 *
	 * @param befor
	 * @param after
	 * @return
	 */
	public static int yearDiffCurr(String after) {
		Date beforeDay = new Date();
		Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
		return getYear(beforeDay) - getYear(afterDay);
	}

	/**
	 * 获取年龄
	 *
	 * @param strBirthday
	 * @return
	 */
	public static int getAge(String strBirthday) {
		if (TextUtils.isEmpty(strBirthday)) {
			return 0;
		}
		try {
			// 使用calendar进行计算
			Calendar calendar = Calendar.getInstance();
			// 获取当前时间毫秒值
			long now = (new Date()).getTime();
			long birthdate = stringtoDate(strBirthday, "yyyy-MM-dd").getTime();
			long time = now - birthdate;
			int count = 0;
			// 时间换算
			long days = time / 1000 / 60 / 60 / 24;
			// 判断闰年
			int birthYear = Integer.parseInt(strBirthday.substring(0, 4));
			for (int i = calendar.get(Calendar.YEAR); i >= birthYear; i--) {
				if ((i % 4 == 0 && !(i % 100 == 0)) || (i % 400 == 0)) {
					count++;
				}
			}
			// 加入闰年因素进行整理换算
			return ((int) days - count) / 365;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getTimeDiffTravel(String startTime, String endTime) {
		long miniDiff = (DateUtil.stringtoDate(endTime, DateUtil.FORMAT_TWO).getTime() - DateUtil.stringtoDate(
				startTime, DateUtil.FORMAT_TWO).getTime()) / 60000;
		int hourDiff = (int) DateUtil.hourDiff(DateUtil.stringtoDate(startTime, DateUtil.FORMAT_TWO),
				DateUtil.stringtoDate(endTime, DateUtil.FORMAT_TWO));
		int dayPart = hourDiff / 24;
		int hourPart = hourDiff % 24;
		int miniPart = (int) (miniDiff % 60);
		StringBuilder timeDiff = new StringBuilder();
		if (dayPart != 0) {
			timeDiff.append(dayPart + "天");
		}
		if (hourPart != 0) {
			timeDiff.append(hourPart + "小时");
		}
		if (miniPart != 0) {
			timeDiff.append(miniPart + "分钟");
		}
		return timeDiff.toString();
	}

	/**
	 * 获取指定日期是星期几
	 * 参数为null时表示获取当前日期是星期几
	 *
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekOfDays[w];
	}

	public static List<String> getNextDates(int num) {
		Calendar calendar = Calendar.getInstance();
		List<String> strDates = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			if (i != 0)
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			strDates.add(calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+1+"-"+calendar.get(Calendar.DATE));
		}
		return strDates;
	}
}
