package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;


@Entity
@Table(name="schedule")
public class Schedule extends CompanyBaseObject{
	
	private CategoryItem categoryItem;
	
	private String dailySchedule;
	private Integer startTime;
	private String startTimePost;
	private Integer endTime;
	private String endTimePost;
	
	

	
	@Basic
	@Column(name = "daily_schedule")
	public String getDailySchedule() {
		return dailySchedule;
	}
	public void setDailySchedule(String dailySchedule) {
		this.dailySchedule = dailySchedule;
	}
	
	@Basic
	@Column(name = "start_time")
	public Integer getStartTime() {
		return startTime;
	}
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	
	@Basic
	@Column(name = "start_time_post")
	public String getStartTimePost() {
		return startTimePost;
	}
	public void setStartTimePost(String startTimePost) {
		this.startTimePost = startTimePost;
	}
	
	@Basic
	@Column(name = "end_time")
	public Integer getEndTime() {
		return endTime;
	}
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	
	@Basic
	@Column(name = "end_time_post")
	public String getEndTimePost() {
		return endTimePost;
	}
	public void setEndTimePost(String endTimePost) {
		this.endTimePost = endTimePost;
	}
	/*
	startTime;
	startTimePost;
	endTime;
	endTimePost;
	*/
	@Transient 
	public String getFormattedTime(){
		return "from "+startTime+":00 "+startTimePost.toLowerCase()+" to "+endTime+":00 "+endTimePost.toLowerCase();
	}
	
	
	@Transient
	public Boolean isTodaySchedule(String day){
		Boolean result = Boolean.FALSE;
		
		
		
		if(getDailySchedule()!=null && getDailySchedule().toLowerCase().indexOf(day.toLowerCase())!=-1)
			
			result = Boolean.TRUE;
		
		
		return result;
		
	}
	
	@Transient
	public Boolean getMonday(){
		return isTodaySchedule("monday");
	}
	@Transient
	public Boolean getTuesday(){
		return isTodaySchedule("tuesday");
	}
	@Transient
	public Boolean getWednesday(){
		return isTodaySchedule("wendesday");
	}
	@Transient
	public Boolean getThursday(){
		return isTodaySchedule("thursday");
	}
	@Transient
	public Boolean getFriday(){
		return isTodaySchedule("friday");
	}
	@Transient
	public Boolean getSaturday(){
		return isTodaySchedule("saturday");
	}
	@Transient
	public Boolean getSunday(){
		return isTodaySchedule("sunday");
	}
	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "category_item_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	
	
	
	
	
	
}
