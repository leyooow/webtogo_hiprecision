package com.ivant.cart.action;

import java.text.DecimalFormat;

public class PesoPay {

	private String merchantId;
	private String orderRef;
	private String mpsMode;
	private String currCode;
	private Double amount;
	private Character lang;
	private String cancelUrl;
	private String failUrl;
	private String successUrl;
	private Character payType;
	private String payMethod;
	private String secureHash;

	public PesoPay() {

	}

	public PesoPay(String merchantId, String orderRef, Double amount,
			String currCode, Character lang, String url, Character payType, String payMethod) {
		this.merchantId = merchantId;
		this.orderRef = orderRef;
		this.amount = amount;
		this.currCode = currCode;
		this.lang = lang;
		this.cancelUrl = url + "CANCELLED";
		this.failUrl = url + "FAILED";
		this.successUrl = url + "CC_SUCCESS";
		this.payType = payType;
		this.payMethod = payMethod;
		
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOrderRef() {
		return orderRef;
	}

	public void setOrderRef(String orderRef) {
		this.orderRef = orderRef;
	}

	public String getMpsMode() {
		return mpsMode;
	}

	public void setMpsMode(String mpsMode) {
		this.mpsMode = mpsMode;
	}

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

	public Double getAmount() {
		DecimalFormat df = new DecimalFormat("0.##");
		
		return Double.parseDouble(df.format(amount));
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Character getLang() {
		return lang;
	}

	public void setLang(Character lang) {
		this.lang = lang;
	}

	public String getCancelUrl() {
		return cancelUrl;
	}

	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	public String getFailUrl() {
		return failUrl;
	}

	public void setFailUrl(String failUrl) {
		this.failUrl = failUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public Character getPayType() {
		return payType;
	}

	public void setPayType(Character payType) {
		this.payType = payType;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getSecureHash() {
		return secureHash;
	}

	public void setSecureHash(String secureHash) {
		this.secureHash = secureHash;
	}
}
