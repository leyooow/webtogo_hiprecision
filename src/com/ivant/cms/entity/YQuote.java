package com.ivant.cms.entity;


import java.util.Date;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

public class YQuote  extends CompanyBaseObject {

	
	private String qcompany;
	private double lastTrade;
	private String date;
	private String tradeTime;
	private double change;
	private double open;
	private double rangeTo;
	private double rangeFrom;
	private int volume;
	private String marketCap;
	private double previousClose;
	private String changePercent;
	private String weekRange;
	private double eps;
	private String companyString;
	
	
	
	public String getQCompany() {
		return qcompany;
	}
	public void setQCompany(String company) {
		this.qcompany = qcompany;
	}
	public double getLastTrade() {
		return lastTrade;
	}
	public void setLastTrade(double lastTrade) {
		this.lastTrade = lastTrade;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getRangeTo() {
		return rangeTo;
	}
	public void setRangeTo(double rangeTo) {
		this.rangeTo = rangeTo;
	}
	public double getRangeFrom() {
		return rangeFrom;
	}
	public void setRangeFrom(double rangeFrom) {
		this.rangeFrom = rangeFrom;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getMarketCap() {
		return marketCap;
	}
	public void setMarketCap(String marketCap) {
		this.marketCap = marketCap;
	}
	public double getPreviousClose() {
		return previousClose;
	}
	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}
	public String getChangePercent() {
		return changePercent;
	}
	public void setChangePercent(String changePercent) {
		this.changePercent = changePercent;
	}
	public String getWeekRange() {
		return weekRange;
	}
	public void setWeekRange(String weekRange) {
		this.weekRange = weekRange;
	}
	public double getEps() {
		return eps;
	}
	public void setEps(double eps) {
		this.eps = eps;
	}
	public String getCompanyString() {
		return companyString;
	}
	public void setCompanyString(String companyString) {
		this.companyString = companyString;
	}
	
	
}
