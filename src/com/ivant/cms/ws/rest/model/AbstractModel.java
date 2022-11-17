package com.ivant.cms.ws.rest.model;


public abstract class AbstractModel implements IModel 
{
	public final static String DATE_FORMAT_PATTERN = "MM/dd/yyyy HH:mm:ss";
	
	protected Long id;
	private String createdOn;
	private String updatedOn;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	
	public String getUpdatedOn() {
		return updatedOn;
	}
	
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
}
