package com.ivant.cms.ws.rest.model;

public abstract class AbstractCartModel extends AbstractModel 
{
	private String createdOn;
	private Integer itemCount;
	private String totalPrice;
	
	public String getCreatedOn()
	{
		return createdOn;
	}
	
	public void setCreatedOn(String createdOn) 
	{
		this.createdOn = createdOn;
	}
	
	public Integer getItemCount() 
	{
		return itemCount;
	}
	
	public void setItemCount(Integer itemCount)
	{
		this.itemCount = itemCount;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
