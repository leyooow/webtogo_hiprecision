package com.ivant.constants;

public class PaymentConstants {

	public static final String PHP_IPAY88_CURRENCY = "PHP";
	
	//Tomato
	/** Dragon Pay Account */
	public static final String TO_DRAGONPAY_MERCHANT_ID = "SWAPWATCHES";
	public static final String TO_DRAGONPAY_SECRET_KEY = "G4dE8sQp";

	//Gurkka
	/** Dragon Pay Account */
	public static final String GR_DRAGONPAY_MERCHANT_ID = "GURKKA";
	public static final String GR_DRAGONPAY_SECRET_KEY = "W$n3PqM@8";
	
	/** Ipay88 Pay Account */
	public static final String GR_IPAY88_MERCHANT_CODE = "PH00085";
	public static final String GR_IPAY88_MERCHANT_KEY = "DHL9UTkfyq";
	public static final String GR_IPAY88_PAYMENT_ID = "1";
	
	//Wendy's
	/** Ipay88 Pay Account */
	public static final String WE_IPAY88_MERCHANT_CODE = "PH00281";
	public static final String WE_IPAY88_MERCHANT_KEY = "5jTL5mVrXf";
	public static final String WE_IPAY88_PAYMENT_ID = "1";

	//Mr. Aircon
	/** PesoPay Account */
	public static final String MR_PESOPAY_MERCHANT_ID = "18060261";
	public static final String MR_PESOPAY_CURRENCY = "608"; //PHP
	public static final Character MR_PESOPAY_PAYTYPE = 'N'; //Sale
	public static final Character MR_PESOPAY_LANG = 'E'; //English
	public static final String MR_PESOPAY_PAYMETHOD = "ALL";
	
	/** Ipay88 Pay Account */
	public static final String MR_IPAY88_MERCHANT_CODE = "PH00137";
	public static final String MR_IPAY88_MERCHANT_KEY = "kKgMFLlkTx";
	public static final String MR_IPAY88_PAYMENT_ID = "1";
	
	//IIEE
	/** Dragon Pay Account */
	public static final String IIEE_DRAGONPAY_MERCHANT_ID = "IIEE";
	public static final String IIEE_DRAGONPAY_SECRET_KEY = "Rg3$KnQz7";

	public static final String PAYMENT_NOTIFICATION_SUCCESS = "Payment Successful. Order confirmation was sent to your email";
	public static final String PAYMENT_NOTIFICATION_PENDING = "Please settle your payment. Order confirmation will be sent to your email after the settlement";
	public static final String PAYMENT_NOTIFICATION_ERROR = "Sorry. Payment Unsuccessful. Order was cancelled";
	public static final String PAYMENT_NOTIFICATION_INVALID =  "Sorry. Payment Invalid. Order was cancelled";
	
	//Test
	public static final String MR_PESOPAY_URL = "http://mraircon.webtogo.com.ph/store.do?notificationMessage=";

}
