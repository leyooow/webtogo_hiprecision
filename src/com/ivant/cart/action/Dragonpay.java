package com.ivant.cart.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Dragonpay {

	private String merchantId;
	private String secretKey;
	private String txnId;
	private Double amount;
	private String currency;
	private String description;
	private String email;
	private String digest;

	private static String paymentSwitchUrl = "https://secure.dragonpay.ph/Pay.aspx";
	
	private static String paymentSwitchUrlTest = "http://test.dragonpay.ph/Pay.aspx";

	public String getSHA1Digest(String message){

		byte[] data = message.getBytes();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hash = md.digest(data);

			String result = "";
			for (byte b : hash) {
				result += Integer.toHexString((b  & 0xff) + 0x100).substring(1);
			}
			
			//System.out.println("SHA1: " + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String payWithDragonPay(String merchantId, String txnId,
			Double amount, String currency, String description, String email,
			String secretKey) {
		String message = String.format("%s:%s:%.2f:%s:%s:%s:%s", merchantId,
				txnId, amount, currency, description, email, secretKey);

		//System.out.println("MESSAGE: " + message);
		String digest = getSHA1Digest(message);

		String redirectString = String.format(
				"%s?merchantid=%s&txnid=%s&amount=%.2f&ccy=%s"
						+ "&description=%s&email=%s&digest=%s",
				paymentSwitchUrl, merchantId, txnId, amount, currency,
				URLEncoder.encode(description), URLEncoder.encode(email),
				digest);

		return redirectString;

	}
	
	public String payWithDragonPayTest(String merchantId, String txnId,
			Double amount, String currency, String description, String email,
			String secretKey) {
		String message = String.format("%s:%s:%.2f:%s:%s:%s:%s", merchantId,
				txnId, amount, currency, description, email, secretKey);

		String digest = getSHA1Digest(message);

		String redirectString = String.format(
				"%s?merchantid=%s&txnid=%s&amount=%.2f&ccy=%s"
						+ "&description=%s&email=%s&digest=%s",
				paymentSwitchUrlTest, merchantId, txnId, amount, currency,
				URLEncoder.encode(description), URLEncoder.encode(email),
				digest);

		return redirectString;

	}
	
	public boolean isTransactionValid(String txnid, String refno, String status, String message, String secretkey, String digest){
				
		String dg = String.format("%s:%s:%s:%s:%s",txnid, refno, status, message, secretkey);
		String output = getSHA1Digest(dg);
		
		if(output.equals(digest)){
			return true;
		}else {
			return false;
		}

	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

}
