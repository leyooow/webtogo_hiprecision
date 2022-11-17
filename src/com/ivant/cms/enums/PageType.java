package com.ivant.cms.enums;

public enum PageType {
	SINGLEPAGE("singlePage"), MULTIPAGE("multiPage"), FORMPAGE("formPage"), GROUP("groupPage"), DEFAULT("default");
	
	private final transient String name;
	
	private PageType(String name)
	{
		this.name = name;
	}
	
	public static PageType[] getPageTypes()
	{
		return new PageType[] {SINGLEPAGE, MULTIPAGE, FORMPAGE};
	}
	
	public String getName() 
	{
		return name;
	}
	
}
