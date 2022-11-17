package com.ivant.cms.enums;

/**
 * @author Glenn Allen B. Sapla
 *
 * @version 1.0, Sept. 20, 2010
 * @since 1.0, Sept. 20, 2010
 *
 */
public enum FormType
{
	TEXT("text"),
	FILE("file"),
	RADIO("radio"),
	DROPDOWN("drop_down"),
	DATE("date"),
	BUTTON("button"),
	CHECKBOX("checkbox"),
	HIDDEN("hidden"),
	IMAGE("image"),
	PASSWORD("password"),
	SUBMIT("submit");
	
	private String value;
	
	FormType(String value) 
	{
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}