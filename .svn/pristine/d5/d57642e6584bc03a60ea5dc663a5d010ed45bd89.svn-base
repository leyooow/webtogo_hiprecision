package com.ivant.cms.enums;

public enum ReferralStatus {
	
	APPROVED("Approved","Smile.png"),
	REJECTED("Rejected","Sad.png"),
	PENDING("Pending",""),
	REDEEMED("Redeemed","Cool.png"),
	REQUESTED("Requested","Blushing.png");
	
	
	private String value;
	private String img;
	
	ReferralStatus(String value,String img){
		this.value = value;
		this.img = img;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getImg() {
		return img;
	}
	
	public final static ReferralStatus []REFERRAL_STATUSES =
	{
		PENDING,
		APPROVED,
		REJECTED,
		REQUESTED,
		REDEEMED
		
	};
}
