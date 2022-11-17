package com.ivant.cms.enums;

public enum MessageRemarks {
	SEEN("Seen"),
	UNSEEN("Unseen");
	private String name;
	MessageRemarks(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
