package com.ivant.cms.enums;

public enum UserType {
	SUPER_USER ("Super User"),
	WEBTOGO_ADMINISTRATOR ("WTG Administrator"),
	COMPANY_ADMINISTRATOR ("Company Administrator"),
	COMPANY_STAFF ("Company Staff"),	
	NORMAL_USER ("Normal User"),
	/* for AGIAN */
	SYSTEM_ADMINISTRATOR ("System Administrator"),
	PORTAL_ADMINISTRATOR ("Portal Administrator"),
	USER_GROUP_ADMINISTRATOR ("User Group Administrator"),
	USER_SUB_GROUP_ADMINISTRATOR ("User Sub Group Administrator"),
	CONTENT_ADMINISTRATOR ("Content Administrator"),
	EVENTS_ADMINISTRATOR ("Events Administrator");
	
	
	private String value;
	
	UserType(String value) {
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
