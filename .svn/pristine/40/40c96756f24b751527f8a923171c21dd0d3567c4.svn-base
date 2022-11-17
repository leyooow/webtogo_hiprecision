package com.ivant.cms.ws.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Kent
 *
 */
@XmlRootElement(name = "Group")
public class GroupModel extends AbstractModel
{
	private String name;
	private String description;
	
	public GroupModel()
	{
		
	}
	
	public GroupModel(Long id, String name, String description)
	{
		setId(id);
		setName(name);
		setDescription(description);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
