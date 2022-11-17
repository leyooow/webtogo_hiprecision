package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity(name = "ContactUs")
@Table(name = "contact_us")
@PrimaryKeyJoinColumn(name = "page_id")
public class ContactUs extends BasePage {

	private String title;
	private String details;
	private String email;
	
	public ContactUs() {
		
	}
//	public ContactUs("cu_title", "cu_details", "cu_email", new Date(), company, user,
//	"myname");
	public ContactUs(String title, String details, String email) {
		
		this.setTitle(title);
		this.setDetails(details);
		this.setEmail(email);
		
	}
	
	@Override
	@Basic
	@Column(name="title", length=50, nullable=false)
	public String getTitle()
	{
		return title;
	}

	@Override
	public void setTitle(String title)
	{
		this.title = title;
	}

	@Basic
	@Column(name="details", length=255, nullable=false)
	public String getDetails()
	{
		return details;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}
	
	@Basic
	@Column(name="email", length=60, nullable=true)
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	@Override
	@Transient
	public String providePageType() {
		return "cu";
	}

}
