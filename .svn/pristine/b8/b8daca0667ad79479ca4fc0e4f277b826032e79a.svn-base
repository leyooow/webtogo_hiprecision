package com.ivant.cart.action;

import java.security.MessageDigest;
import java.text.DecimalFormat;

import sun.misc.BASE64Encoder;

public class iPay88 {

	private String merchantCode;
	private String merchantKey;
	private String paymentId;
	private String refNo;
	private Double amount;
	private String amountStr;
	private String currency;
	private String prodDesc;
	private String userName;
	private String userEmail;
	private String userContact;
	private String remark;
	private String lang;
	private String signature;
	private String status;
	private String responseURL;
	private String backendURL;

	// TEST
	// private static String iPay88URL =
	// "https://sandbox.ipay88.com.ph/epayment/entry.asp";

	public iPay88(){
		
	}
	
	public iPay88(String merchantKey, String merchantCode, String refNo, Double amount, String currency){
		this.merchantKey = merchantKey;
		this.merchantCode = merchantCode;
		this.refNo = refNo;
		this.amount = amount;
		this.currency = currency;
	}
	
	public iPay88(String merchantKey, String merchantCode, String paymentId, String refNo, String amount, String currency, String status){
		this.merchantKey = merchantKey;
		this.merchantCode = merchantCode;
		this.paymentId = paymentId;
		this.refNo = refNo;
		this.amountStr = amount;
		this.currency = currency;
		this.status = status;
	}
	
	public String encrypt(String message) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			md.update(message.getBytes("UTF-8"));
			byte raw[] = md.digest();
			String hash = (new BASE64Encoder()).encode(raw);
			//System.out.println("MESSAGE " + message);
			//System.out.println("SHA " + hash);
			return hash;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getIPay88Signature() {
		DecimalFormat df = new DecimalFormat("#.00");
		String amount = df.format(getAmount()).replace(".", "");
		String s = String.format("%s%s%s%s%s", getMerchantKey(),
				getMerchantCode(), getRefNo(), amount, getCurrency());

		String signature = encrypt(s);
		return signature;
	}

	public Boolean isTransactionValid(String signature) {
		String amount = getAmountStr().replace(".", "");
		String s = String.format("%s%s%s%s%s%s%s", getMerchantKey(),
				getMerchantCode(), getPaymentId(), getRefNo(), amount, getCurrency(), getStatus());

		if(signature.equals(encrypt(s))){
			return true;
		}else{
			return false;
		}

	}
	
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserContact() {
		return userContact;
	}

	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getResponseURL() {
		return responseURL;
	}

	public void setResponseURL(String responseURL) {
		this.responseURL = responseURL;
	}

	public String getBackendURL() {
		return backendURL;
	}

	public void setBackendURL(String backendURL) {
		this.backendURL = backendURL;
	}

}
