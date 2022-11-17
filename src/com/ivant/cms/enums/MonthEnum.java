package com.ivant.cms.enums;

public enum MonthEnum {
	JANUARY ("January",1),
	FEBRUARY ("February",2),
	MARCH ("March",3),
	APRIL ("April",4),
	MAY ("May",5),
	JUNE ("June",6),
	JULY ("July",7),
	AUGUST ("August",8),
	SEPTEMBER ("September",9),
	OCTOBER ("October",10),
	NOVEMBER ("November",11),
	DECEMBER ("December",12);
	
	private String monthName;
	private int monthNum;
	
	private MonthEnum(String monthName, int monthNum) {
		this.monthName = monthName;
		this.monthNum = monthNum;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public int getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(int monthNum) {
		this.monthNum = monthNum;
	}

		
}
