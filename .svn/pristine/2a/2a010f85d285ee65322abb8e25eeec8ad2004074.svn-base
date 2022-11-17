package com.ivant.cms.entity;

import com.ivant.cms.enums.PageType;

// object representing the menu with name and url property
// note that this class doesnt persist the database

public class Menu
{
	/** The page/bean. */
	private Object page;
	
	/** The object/entry/ item */
	private Group group;
	
	private String name;
	private String pageType;
	private String url;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPageType()
	{
		return pageType;
	}
	
	public void setPageType(String pageType)
	{
		this.pageType = pageType;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public Menu(String name, String url)
	{
		this.name = name;
		this.url = url;
	}
	
	public Menu(String name, String pageType, String url)
	{
		this.name = name;
		this.pageType = pageType;
		this.url = url;
	}
	
	public <T extends AbstractPage> Menu(T page, String url)
	{
		setPage(page);
		setUrl(url);
		setName(page.getName());
		setPageType(page.getPageType().name());
	}
	
	public Menu(Group group, String url)
	{
		setGroup(group);
		setUrl(url);
		setName(group.getName());
		setPageType(PageType.GROUP.name());
	}
	
	/**
	 * @return the page
	 */
	public Object getPage()
	{
		return page;
	}
	
	/**
	 * @param page the page to set
	 */
	public void setPage(Object page)
	{
		this.page = page;
	}
	
	/**
	 * @return the group
	 */
	public Group getGroup()
	{
		return group;
	}
	
	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group)
	{
		this.group = group;
	}
	
	@Override
	public String toString()
	{
		return "[name: " + getName() + ", url: " + getUrl() + "]";
	}
}
