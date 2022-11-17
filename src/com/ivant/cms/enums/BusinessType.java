package com.ivant.cms.enums;

public enum BusinessType {
	ACCOUNTING ("Accounting"), 
	ADMINISTRATION ("Administration"),
	COMMUNICATIONS ("Communication"), 
	HUMAN_RESOURCES ("Human Resources"), 
	INFORMATION_TECHNOLOGY ("Information Technology"), 
	INTERNAL_AUDIT ("Internal Audit"),
	MANAGEMENT ("Management"),
	MARKETING_AND_SALES ("Marketing And Sales"),
	OPERATIONS ("Operations"), 
	PROCUREMENT ("Procurement"), 
	RESEARCH_AND_DEVELOPMENT ("Research And Development");
	
	private String value;
	
	BusinessType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
