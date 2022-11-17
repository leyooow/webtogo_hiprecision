package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interfaces.CompanyAware;

@Entity(name="Notification")
@Table(name="notification")
public class Notification extends BaseObject implements Cloneable, CompanyAware, JSONAware{
	
	private String title, content, by, type, notif_one, notif_two, notif_three;
	private Company company;
	@Override
	public void setCompany(Company company){
		this.company = company;
	}
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}
	
	@Basic
	@Column(name = "notification_title", length = 100, nullable = true)
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	@Basic
	@Column(name = "notification_content", length = 2147483647, nullable = true)
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	@Basic
	@Column(name = "notification_by", length = 1000, nullable = true)
	public String getBy(){
		return by;
	}
	
	public void setBy(String by){
		this.by = by;
	}
	
	@Basic
	@Column(name = "notification_type", length = 100, nullable = true)
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	@Basic
	@Column(name = "notification_one", length = 100, nullable = true)
	public String getNotifOne(){
		return notif_one;
	}
	
	public void setNotifOne(String notif_one){
		this.notif_one = notif_one;
	}
	
	@Basic
	@Column(name = "notification_two", length = 100, nullable = true)
	public String getNotifTwo(){
		return notif_two;
	}
	
	public void setNotifTwo(String notif_two){
		this.notif_two = notif_two;
	}
	
	@Basic
	@Column(name = "notification_three", length = 100, nullable = true)
	public String getNotifThree(){
		return notif_three;
	}
	
	public void setNotifThree(String notif_three){
		this.notif_three = notif_three;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		json.put("title", getTitle());
		json.put("content", getContent());
		json.put("by", getBy());
		json.put("type",getType());
		json.put("notif_one",getNotifOne());
		json.put("notif_two",getNotifTwo());
		json.put("notif_three",getNotifThree());
		json.put("createdOn",getCreatedOn().toString());
		return json.toJSONString();
	}
}
