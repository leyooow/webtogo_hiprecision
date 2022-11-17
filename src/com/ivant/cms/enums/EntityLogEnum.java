package com.ivant.cms.enums;

public enum EntityLogEnum{
	BILLING ("Billing"),
	SINGLE_PAGE ("Single Page"),
	CATEGORY_ITEM ("Category Item"), 
	MULTI_PAGE ("Multi Page"), 
	CATEGORY ("Category"),
	MEMBERFILEITEM ("Member File Item"),
	;
	
	private final String typeName;
	
	EntityLogEnum (String name)
	{
		this.typeName = name;
	}
	
	public String getTypeName()
	{
		return typeName;
	}
	
	
	public int getNum() 
	{
		return ordinal();
	}
	
	public String getValue()
	{
		return this.typeName;
	}
}
