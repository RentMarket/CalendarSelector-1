package com.robert.calendar;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarUtils {

	private static int dayOfMonth, monthOfYear, curYear;

	static {
		dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		monthOfYear = Calendar.getInstance().get(Calendar.MONTH);
		curYear = Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * Gets the days in month.
	 *
	 * @param month the month
	 * @param year  the year
	 * @return the days in month
	 */
	public static int getDaysInMonth(int month, int year) {
		switch (month) {
			case Calendar.JANUARY:
			case Calendar.MARCH:
			case Calendar.MAY:
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.OCTOBER:
			case Calendar.DECEMBER:
				return 31;
			case Calendar.APRIL:
			case Calendar.JUNE:
			case Calendar.SEPTEMBER:
			case Calendar.NOVEMBER:
				return 30;
			case Calendar.FEBRUARY:
				return (year % 4 == 0) ? 29 : 28;
			default:
				throw new IllegalArgumentException("Invalid Month");
		}
	}

	/**
	 * Gets the flow month days.
	 *
	 * @param flowMonth the flow month
	 * @return the flow month days
	 */
	public static int getFlowMonthDays(int flowMonth) {
		int totalDays = 0;
		for (int i = 0; i < flowMonth; i++) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, i + 1);
			int days = getDaysInMonth(c.get(Calendar.MONTH), c.get(Calendar.YEAR));
			totalDays += days;
		}
		return totalDays;
	}

	/**
	 * Current month remain days.
	 *
	 * @return the int
	 */
	public static int currentMonthRemainDays() {
		Calendar c = Calendar.getInstance();
		return getDaysInMonth(c.get(Calendar.MONTH), c.get(Calendar.YEAR)) - c.get(Calendar.DAY_OF_MONTH) + 1;
	}

	/**
	 * Through month.
	 *
	 * @param calendar the calendar
	 * @param passDays the pass days
	 * @return the int
	 */
	public static int throughMonth(Calendar calendar, int passDays) {
		Calendar c = (Calendar) calendar.clone();
		int curMonth = c.get(Calendar.MONTH);
		int curYear = c.get(Calendar.YEAR);
		c.add(Calendar.DAY_OF_MONTH, passDays - 1);
		int monthCount = (c.get(Calendar.YEAR) - curYear) * 12 + (c.get(Calendar.MONTH) - curMonth);
		return monthCount;
	}

	/**
	 * Gets the days of month.
	 *
	 * @param calendar the calendar
	 * @return the days of month
	 */
	public static String[] getDaysOfMonth(Calendar calendar) {
		Calendar month = (Calendar) calendar.clone();
		String[] days;
		final int FIRST_DAY_OF_WEEK = 0; // Sunday = 0, Monday = 1
		month.set(Calendar.DAY_OF_MONTH, 1);
		int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
		int firstDay = (int) month.get(Calendar.DAY_OF_WEEK);

		if (firstDay == 1) {
			days = new String[lastDay + (FIRST_DAY_OF_WEEK * 6)];
		} else {
			days = new String[lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1)];
		}

		int j = FIRST_DAY_OF_WEEK;

		if (firstDay > 1) {
			for (j = 0; j < firstDay - FIRST_DAY_OF_WEEK; j++) {
				days[j] = "";
			}
		} else {
			for (j = 0; j < FIRST_DAY_OF_WEEK * 6; j++) {
				days[j] = "";
			}
			j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
		}

		int dayNumber = 1;
		for (int i = j - 1; i < days.length; i++) {
			days[i] = "" + dayNumber;
			dayNumber++;
		}
		return days;
	}

	/**
	 * Gets the days of month.
	 *
	 * @param calendar the calendar
	 * @param passDays the pass days
	 * @return the days of month
	 */
	public static ArrayList<Day> getDaysOfMonth(Calendar calendar, int passDays, String startDay, String endDay, Context context) {
		Calendar calStart = null;
		Calendar calEnd = null;
		if (null != startDay) {
			calStart = Calendar.getInstance();
			calStart.setTime(DateUtil.stringtoDate(startDay, DateUtil.LONG_DATE_FORMAT));
		}
		if (null != endDay) {
			calEnd = Calendar.getInstance();
			calEnd.setTime(DateUtil.stringtoDate(endDay, DateUtil.LONG_DATE_FORMAT));
		}
		Calendar month = (Calendar) calendar.clone();
		final int FIRST_DAY_OF_WEEK = 0; // Sunday = 0, Monday = 1
		month.set(Calendar.DAY_OF_MONTH, 1);
		int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
		//获取月起始日期是周几（1-7，1表示周日）
		int firstDay = (int) month.get(Calendar.DAY_OF_WEEK);
		ArrayList<Day> days = new ArrayList<Day>();
		int size;
		if (firstDay == 1) {
			size = lastDay + (FIRST_DAY_OF_WEEK * 6);
		} else {
			size = lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1);
		}
		for (int i = 0; i < size; i++) {
			days.add(new Day("", Day.DayType.NOT_ENABLE));
		}
		int j = FIRST_DAY_OF_WEEK;
		if (firstDay > 1) {
			for (j = 0; j < firstDay - FIRST_DAY_OF_WEEK; j++) {
				days.set(j, new Day("", Day.DayType.NOT_ENABLE));
			}
		} else {
			for (j = 0; j < FIRST_DAY_OF_WEEK * 6; j++) {
				days.set(j, new Day("", Day.DayType.NOT_ENABLE));
			}
			j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
		}

		int dayNumber = 1;

		DateFormatter fomatter = new DateFormatter(context.getResources());
		long firstDayMillis = month.getTimeInMillis();
		int solarTerm1 = LunarCalendar.getSolarTerm(month.get(Calendar.YEAR), month.get(Calendar.MONTH) * 2 + 1);
		int solarTerm2 = LunarCalendar.getSolarTerm(month.get(Calendar.YEAR), month.get(Calendar.MONTH) * 2 + 2);
		for (int i = j - 1; i < days.size(); i++) {
			Day.DayType type;
			if (month.get(Calendar.YEAR) == curYear && month.get(Calendar.MONTH) == monthOfYear) {
				//当前年月
				if (dayNumber >= dayOfMonth && dayNumber < dayOfMonth + passDays) {
					type = Day.DayType.ENABLE;
					if (dayNumber == dayOfMonth) {
						type = Day.DayType.TODAY;
					} else if (dayNumber == dayOfMonth + 1) {
						type = Day.DayType.TOMORROW;
					} else if (dayNumber == dayOfMonth + 2) {
						type = Day.DayType.T_D_A_T;
					}
				} else {
					type = Day.DayType.NOT_ENABLE;
				}
			} else {
				if (dayNumber <= passDays) {
					type = Day.DayType.ENABLE;
					// 明天/后天在下个月
					int remainDays = getDaysInMonth(monthOfYear, curYear) - dayOfMonth;//0-明天后天都在下月，1-后天在下月
					if (remainDays < 2 && dayNumber <= 2 && Math.abs(month.get(Calendar.MONTH) - monthOfYear) == 1
							&& month.get(Calendar.YEAR) == curYear) {
						if (remainDays == 1) {
							if (dayNumber == 1) {
								type = Day.DayType.T_D_A_T;
							}
						} else if (remainDays == 0) {
							if (dayNumber == 1) {
								type = Day.DayType.TOMORROW;
							} else if (dayNumber == 2) {
								type = Day.DayType.T_D_A_T;
							}
						}
					}
				} else {
					type = Day.DayType.NOT_ENABLE;
				}
			}
			Day newDay = new Day("" + dayNumber, type);
			if (calStart != null && calStart.get(Calendar.YEAR) == month.get(Calendar.YEAR)
					&& (calStart.get(Calendar.MONTH) + 1) == (month.get(Calendar.MONTH) + 1)
					&& calStart.get(Calendar.DAY_OF_MONTH) == dayNumber) {
				newDay.setIsStartDate(true);
			}
			if (calEnd != null && calEnd.get(Calendar.YEAR) == month.get(Calendar.YEAR)
					&& (calEnd.get(Calendar.MONTH) + 1) == (month.get(Calendar.MONTH) + 1)
					&& calEnd.get(Calendar.DAY_OF_MONTH) == dayNumber) {
				newDay.setIsEndDate(true);
			}
			LunarCalendar dayLunar = new LunarCalendar(firstDayMillis +
					(dayNumber - 1) * LunarCalendar.DAY_MILLIS);
			int gregorianDay = dayLunar.getGregorianDate(Calendar.DAY_OF_MONTH);
			// 农历节日 > 公历节日 > 农历月份 > 二十四节气 > 农历日
			int index = dayLunar.getLunarFestival();
			if (index >= 0) {
				// 农历节日
				newDay.setIsFestival(true);
				newDay.setLunar(fomatter.getLunarFestivalName(index).toString());
			} else {
				index = dayLunar.getGregorianFestival();
				if (index >= 0) {
					// 公历节日
					newDay.setIsFestival(true);
					newDay.setLunar(fomatter.getGregorianFestivalName(index).toString());
				} else if (dayLunar.getLunar(LunarCalendar.LUNAR_DAY) == 1) {
					// 初一,显示月份
					newDay.setLunar(fomatter.getMonthName(dayLunar).toString());
				} else if (gregorianDay == solarTerm1) {
					// 节气1
					newDay.setIsSolarTerm(true);
					newDay.setLunar(fomatter.getSolarTermName(dayLunar.getGregorianDate(Calendar.MONTH) * 2).toString());
				} else if (gregorianDay == solarTerm2) {
					// 节气2
					newDay.setIsSolarTerm(true);
					newDay.setLunar(fomatter.getSolarTermName(dayLunar.getGregorianDate(Calendar.MONTH) * 2 + 1).toString());
				} else {
					newDay.setLunar(fomatter.getDayName(dayLunar).toString());
				}
			}
			days.set(i, newDay);
			dayNumber++;
		}
		return days;
	}
}