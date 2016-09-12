package com.robert.calendar;


public class Day {

	public Day(String name, DayType type) {
		setName(name);
		setType(type);
	}

	public enum DayType {
		TODAY, TOMORROW, T_D_A_T, ENABLE, NOT_ENABLE
	}

	private String name;
	private DayType type;
	private boolean isStartDate;

	public boolean isEndDate() {
		return isEndDate;
	}

	public void setIsEndDate(boolean isEndDate) {
		this.isEndDate = isEndDate;
	}

	public boolean isStartDate() {
		return isStartDate;
	}

	public void setIsStartDate(boolean isStartDate) {
		this.isStartDate = isStartDate;
	}

	private boolean isEndDate;

	boolean isFestival = false;
	boolean isSolarTerm = false;
	private String lunar;

	public String getLunar() {
		return lunar;
	}

	public void setLunar(String lunar) {
		this.lunar = lunar;
	}

	public boolean isFestival() {
		return isFestival;
	}

	public void setIsFestival(boolean isFestival) {
		this.isFestival = isFestival;
	}

	public boolean isSolarTerm() {
		return isSolarTerm;
	}

	public void setIsSolarTerm(boolean isSolarTerm) {
		this.isSolarTerm = isSolarTerm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DayType getType() {
		return type;
	}

	public void setType(DayType type) {
		this.type = type;
	}
}
