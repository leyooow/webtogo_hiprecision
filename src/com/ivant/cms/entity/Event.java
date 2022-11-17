package com.ivant.cms.entity;

import java.util.Date;
import java.util.List;

//import com.ivant.cms.entity.EventGroup;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.enums.ColorCode;
import com.ivant.cms.entity.EventGroup;


@Entity
@Table(name="event")
public class Event extends CompanyBaseObject {

	private Date eventDate;
	private Date eventEnd;
	private String title;
	private String detail;
	private ColorCode colorCode;
	private String eventCategory;
	private EventGroup eventGroup;
	private String fee;
	
	@ManyToOne(targetEntity=EventGroup.class, fetch=FetchType.LAZY)
	@JoinColumn(name="eventgroup", nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	public EventGroup getEventGroup() {
		return eventGroup;
	}

	public void setEventGroup(EventGroup eventGroup) {
		this.eventGroup = eventGroup;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_date", nullable=false)
	public Date getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_end", nullable=false)
	public Date getEventEnd() {
		return eventEnd;
	}

	public void setEventEnd(Date eventEnd) {
		this.eventEnd = eventEnd;
	}

	@Basic
	@Column(name="title", nullable=false)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Basic
	@Column(name="detail")
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Basic
	@Column(name="event_category")
	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}
		@Basic
	@Column(name="color_code")
	public void setColorCode(ColorCode colorCode) {
		this.colorCode = colorCode;
	}

	public ColorCode getColorCode() {
		return colorCode;
	}

	@Basic
	@Column(name="fee")
	public String getFee() {
		return fee;
	}
	
	public void setFee(String fee) {
		this.fee = fee;
	}	
}
