package com.ivant.cart.action;

/**
	Initialize the values required for PayDollar Integration
 */
public class HBCPaymentInput{
	
	/** payDollar Url*/
	private String submitForm;
	
	/** merchant id provided by PayDollar */
	private String merchantId;
	
	/** Currency of the payment */
	private String currCode;
	
	/** The URLs to be directed after successful, failed, or cancelled transactions */
	private String successUrl, failUrl, cancelUrl;
	
	/** BDO details */
	private long amount;
	private char lang;
	private char payType;
	private String payMethod;
	
	
	public HBCPaymentInput(){
		this.setSuccessUrl("/receiveDataFeed.do");
		this.setFailUrl("/home.do");
		this.setCancelUrl("/orderList.do");
		
		this.setSubmitForm("https://www.paydollar.com/ECN/eng/payment/payForm.jsp");
		this.setMerchantId("100000120");
		this.setCurrCode("840");
		this.setPayMethod("CC");
		this.setPayType('N');
		this.setLang('E');
		
	}
	
	public String getSubmitForm() {
		return submitForm;
	}

	public void setSubmitForm(String submitForm) {
		this.submitForm = submitForm;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getFailUrl() {
		return failUrl;
	}

	public void setFailUrl(String failUrl) {
		this.failUrl = failUrl;
	}

	public String getCancelUrl() {
		return cancelUrl;
	}

	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(long amount) {
		this.amount = amount;
	}

	/**
	 * @return the amount
	 */
	public long getAmount() {
		return amount;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(char lang) {
		this.lang = lang;
	}

	/**
	 * @return the lang
	 */
	public char getLang() {
		return lang;
	}

	/**
	 * @param payType the payType to set
	 */
	public void setPayType(char payType) {
		this.payType = payType;
	}

	/**
	 * @return the payType
	 */
	public char getPayType() {
		return payType;
	}

	/**
	 * @param payMethod the payMethod to set
	 */
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	/**
	 * @return the payMethod
	 */
	public String getPayMethod() {
		return payMethod;
	}
	
}
