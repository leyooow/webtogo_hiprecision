package com.ivant.cms.entity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.ivant.utils.SHA1Util;

public class YesPayments {
	private String merchantGroup;
	private String merchantCode;
	private String merchantReference;
	private String transactionType;
	private String cardId;
	private String customerAccountNumber;
	private String billNumber;
	private String cardBin;
	private String hashValue;
	private String crcyCd;
	private String amount;
	private String cardHolderEmail;
	private String notes;
	private String realTime;
	private String langCd;
	private String secretKey;
	//for Post Result, THESE DATA CAME FROM YESPAYMENTS
	private String transactionId;
	private String responseCode;
	private String responseDescription;
	private String cardNbrMasked;
	private String cardHolderNm;
	private String expYYYY;
	private String expMM;
	private String warningArray;
	private boolean membershipFee;
	private String donation;
	
	public String getDonation() {
		return donation;
	}

	public void setDonation(String donation) {
		this.donation = donation;
	}

	public boolean isMembershipFee() {
		return membershipFee;
	}

	public void setMembershipFee(boolean membershipFee) {
		this.membershipFee = membershipFee;
	}
	
	public boolean generateNewMerchantReference(){
		if(this.merchantReference.length()>12){
			this.merchantReference.substring(0, 12);
		}
		
		if(this.membershipFee&&this.merchantReference.indexOf("M")==-1){
			this.merchantReference="M"+this.merchantReference;
			this.merchantReference=this.merchantReference.substring(0, 12);
			return true;
		}	
		
		return false;
	}
	
	private String events[];

	public String[] getEvents() {
		return events;
	}

	public void setEvents(String[] events) {
		this.events = events;
	}

	//for the payment only
	//PCO
	private String feeType;
	private String feeTitle;
	private String feeCost;

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeTitle() {
		return feeTitle;
	}

	public void setFeeTitle(String feeTitle) {
		this.feeTitle = feeTitle;
	}

	public String getFeeCost() {
		return feeCost;
	}

	public void setFeeCost(String feeCost) {
		this.feeCost = feeCost;
	}

	public YesPayments(){
//		this.merchantGroup="PCOPT";
//		this.merchantCode="PCOPT";
		this.merchantGroup="IVANT";
		this.merchantCode="IVANT";
//		this.secretKey="&5AV3_The_3@RtH!";
		this.secretKey="h5tRA_ep!a6_$uhA";
		this.transactionType="20";
		this.crcyCd="PHP";
		this.merchantReference=(new Date().getTime()+"").substring(0, 12);// merchant Reference....
		this.realTime="Y";
		this.cardHolderNm="leonard concepcion";
		this.cardHolderEmail="leonard@ivant.com";
		this.amount="1200";
	}
	
	public YesPayments(String merchantGroup,String merchantCode,String secretKey,String transactionType,String crcyCd){
		this.merchantGroup=merchantGroup;
		this.merchantCode=merchantCode;
		this.secretKey=secretKey;
		this.transactionType=transactionType;
		this.crcyCd=crcyCd;
	}
	
	public String getSecretKey() {
		return secretKey;
	}
	
	public String getMerchantGroup() {
		return merchantGroup;
	}
	
	public void setMerchantGroup(String merchantGroup) {
		this.merchantGroup = merchantGroup;
	}
	
	public String getMerchantCode() {
		return merchantCode;
	}
	
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	public String getMerchantReference() {
		return merchantReference;
	}
	
	public void setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public String getCardId() {
		return cardId;
	}
	
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getCustomerAccountNumber() {
		return customerAccountNumber;
	}
	
	public void setCustomerAccountNumber(String customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}
	
	public String getBillNumber() {
		return billNumber;
	}
	
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	
	public String getCardBin() {
		return cardBin;
	}
	
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	
	public String getHashValue() {
		return hashValue;
	}
	
	public void setHashValue(String hashValue) {
			this.hashValue=_SHA1();
	}
	
	public String _SHA1(){
		try {
			if(this.merchantGroup == null)
				this.merchantGroup="";
			if(this.merchantCode == null)
				this.merchantCode="";
			if(this.merchantReference == null)
				this.merchantReference="";
			if(this.crcyCd == null)
				this.crcyCd="";
			if(this.amount == null)
				this.amount="";
			if(this.transactionType == null)
				this.transactionType="";
			if(this.cardBin == null)
				this.cardBin="";
			if(this.secretKey == null)
				this.secretKey="";
			String plainText=this.merchantGroup+this.merchantCode+this.merchantReference+this.crcyCd+this.amount+this.transactionType+this.cardBin+this.secretKey;
			this.hashValue=SHA1Util.SHA1(plainText);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//System.out.println("The hash Value is "+hashValue);
		this.hashValue = hashValue;
		
		return hashValue;
	}
	
	public String getCrcyCd() {
		return crcyCd;
	}
	
	public void setCrcyCd(String crcyCd) {
		this.crcyCd = crcyCd;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getCardHolderEmail() {
		return cardHolderEmail;
	}
	
	public void setCardHolderEmail(String cardHolderEmail) {
		this.cardHolderEmail = cardHolderEmail;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getRealTime() {
		return realTime;
	}
	
	public void setRealTime(String realTime) {
		this.realTime = realTime;
	}
	
	public String getLangCd() {
		return langCd;
	}
	
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}
	
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	//this is for POSTRESULTV2, the data here are from YESPAYMENT
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public String getCardNbrMasked() {
		return cardNbrMasked;
	}

	public void setCardNbrMasked(String cardNbrMasked) {
		this.cardNbrMasked = cardNbrMasked;
	}

	public String getCardHolderNm() {
		return cardHolderNm;
	}

	public void setCardHolderNm(String cardHolderNm) {
		this.cardHolderNm = cardHolderNm;
	}

	public String getExpYYYY() {
		return expYYYY;
	}

	public void setExpYYYY(String expYYYY) {
		this.expYYYY = expYYYY;
	}

	public String getExpMM() {
		return expMM;
	}

	public void setExpMM(String expMM) {
		this.expMM = expMM;
	}

	public String getWarningArray() {
		return warningArray;
	}

	public void setWarningArray(String warningArray) {
		this.warningArray = warningArray;
	}
}
