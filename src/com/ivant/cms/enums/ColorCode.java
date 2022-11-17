package com.ivant.cms.enums;

public enum ColorCode {
	RED ("red"),
	BLUE ("blue"),
	GREEN ("green"),
	MAGENTA ("magenta"),	
	ORANGE ("orange"),	
	PURPLE ("purple");
	
	private String value;
	
	ColorCode(String value) {
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
