package com.ivant.cms.entity;

import java.util.Date;
import java.util.List;

import com.ivant.cms.entity.Event;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;



@Entity
@Table(name="eventgroup")
public class EventGroup extends CompanyBaseObject {

	private String name;
	private String description;
	private List<Event> events;
	private Integer sortOrder;
	
	

	@Basic
	@Column(name="name", nullable=true)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name="sort_order", nullable=true)
	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Basic
	@Column(name="description", nullable=true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	@OneToMany(targetEntity=Event.class, fetch=FetchType.LAZY, mappedBy="eventGroup")
	@Where(clause="valid=1")
	public List<Event> getEvents() {
		return events;
	}
	
	public void setEvents(List<Event> events) {
		this.events = events;
	}

//	
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="event_date", nullable=false)
//	public Date getEventDate() {
//		return eventDate;
//	}
//
//	
//	public void setEventDate(Date eventDate) {
//		this.eventDate = eventDate;
//	}
//	
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="event_end", nullable=false)
//	public Date getEventEnd() {
//		return eventEnd;
//	}
//
//	public void setEventEnd(Date eventEnd) {
//		this.eventEnd = eventEnd;
//	}
//
//	@Basic
//	@Column(name="title", nullable=false)
//	public String getTitle() {
//		return title;
//	}
//	
//	public void setTitle(String title) {
//		this.title = title;
//	}
	
//	@Basic
//	@Column(name="detail")
//	public String getDetail() {
//		return detail;
//	}
//	
//	public void setDetail(String detail) {
//		this.detail = detail;
//	}

//	@Basic
//	@Column(name="event_category")
//	public String getEventCategory() {
//		return eventCategory;
//	}
//
//	public void setEventCategory(String eventCategory) {
//		this.eventCategory = eventCategory;
//	}
}
