package com.robert.calendar;

import java.io.Serializable;
import java.util.Date;

public class DateSelectParam implements Serializable{
	private String startDayName, endDayName;
	private String startDay, endDay;
	private Date startDate;
	private Date endDate;

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getEndDayName() {
		return endDayName;
	}

	public void setEndDayName(String endDayName) {
		this.endDayName = endDayName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getStartDayName() {
		return startDayName;
	}

	public void setStartDayName(String startDayName) {
		this.startDayName = startDayName;
	}

}
