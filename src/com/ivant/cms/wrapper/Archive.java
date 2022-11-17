package com.ivant.cms.wrapper;

import java.util.List;

public class Archive {
	private Integer year;
	private List<ArchiveMonth> months;
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public List<ArchiveMonth> getMonths() {
		return months;
	}
	public void setMonths(List<ArchiveMonth> months) {
		this.months = months;
	}
	
}
