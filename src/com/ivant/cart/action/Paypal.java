package com.ivant.cart.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ShoppingCartItem;
import com.paypal.sdk.core.nvp.NVPDecoder;
import com.paypal.sdk.core.nvp.NVPEncoder;
import com.paypal.sdk.exceptions.PayPalException;
import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;
import com.paypal.sdk.services.NVPCallerServices;

/*
 * Paypal payment system class library
 * This module uses paypal express checkout
 * Author: Rey Bumalay
 */
@SuppressWarnings("unchecked") 
public class Paypal{
	
	private static final Logger logger = Logger.getLogger(Paypal.class);
	private HttpServletRequest session;
	public HttpServletRequest request;
	private NVPCallerServices caller = null;
	private final NumberFormat formatter = new DecimalFormat("#0.00");

	private String environment = "live";	
//	private String environment = "sandbox";

	private String username, password, signature, success, error;
	
	private String headerImage;

	//BN Code is only applicable for partners
	public String gv_BNCode		= "PP-ECWizard"; 
	
	boolean bSandbox = false;
	
	String HTTPREQUEST_PROXYSETTING_SERVER = "";
	String HTTPREQUEST_PROXYSETTING_PORT = "";
	boolean USE_PROXY = false;
	
	String gv_Version = "60.0";
	
	//WinObjHttp Request proxy settings.
	String gv_ProxyServer	= HTTPREQUEST_PROXYSETTING_SERVER;
	String gv_ProxyServerPort = HTTPREQUEST_PROXYSETTING_PORT;
	int gv_Proxy		= 2;	//'setting for proxy activation
	boolean gv_UseProxy	= USE_PROXY;
	
	String gv_APIEndpoint;
	String gv_nvpHeader;	
	String PAYPAL_URL;
	String token;
	String payerId;
	//----------------
	
	
	public Paypal(Company company) {
//		gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
//		PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
		username=company.getPalUsername();
		password=company.getPalPassword();
		signature=company.getPalSignature();
		
		gv_APIEndpoint = "https://api-3t.paypal.com/nvp";
		PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
		//gv_APIEndpoint = "https://api-3t.paypal.com/nvp?";
		//PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";

		logger.debug("Paypal from getExpress username: "+username);
	/*	System.out.println("Paypal from getExpress password: "+company.getPalPassword());
		System.out.println("Paypal from getExpress sig: "+company.getPalSignature());
		System.out.println("Paypal from getExpress environ: "+environment); */
		caller = new NVPCallerServices();
		
		try {
			APIProfile apiProfile = ProfileFactory.createSignatureAPIProfile();
		/*	apiProfile.setAPIUsername(company.getPalUsername());
			apiProfile.setAPIPassword(company.getPalPassword());
			apiProfile.setSignature(company.getPalSignature()); */
			apiProfile.setAPIUsername(username);
			apiProfile.setAPIPassword(password);
			apiProfile.setSignature(signature);
			apiProfile.setEnvironment(environment);
			caller.setAPIProfile(apiProfile);

		}
		catch(PayPalException ppe) {
			logger.fatal("problem setting up paypal payment", ppe);
		}
	}
	
	public Paypal(Company company, String environment) {
//		gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
//		PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
		username=company.getPalUsername();
		password=company.getPalPassword();
		signature=company.getPalSignature();
		this.environment=environment;
		
		if(environment.equalsIgnoreCase("sandbox")) {
			gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
			PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
		} else {
			gv_APIEndpoint = "https://api-3t.paypal.com/nvp";
			PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
		}
		//gv_APIEndpoint = "https://api-3t.paypal.com/nvp?";
		//PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";

		logger.debug("Paypal from getExpress username: "+username);
	/*	System.out.println("Paypal from getExpress password: "+company.getPalPassword());
		System.out.println("Paypal from getExpress sig: "+company.getPalSignature());
		System.out.println("Paypal from getExpress environ: "+environment); */
		caller = new NVPCallerServices();
		
		try {
			APIProfile apiProfile = ProfileFactory.createSignatureAPIProfile();
		/*	apiProfile.setAPIUsername(company.getPalUsername());
			apiProfile.setAPIPassword(company.getPalPassword());
			apiProfile.setSignature(company.getPalSignature()); */
			apiProfile.setAPIUsername(username);
			apiProfile.setAPIPassword(password);
			apiProfile.setSignature(signature);
			apiProfile.setEnvironment(environment);
			caller.setAPIProfile(apiProfile);

		}
		catch(PayPalException ppe) {
			logger.fatal("problem setting up paypal payment", ppe);
		}
	}
	
	public Paypal(String username, String password, String signature, String enviroment) {
		this.username = username;
		this.password = password;
		this.signature = signature;
		this.environment = enviroment;
		
//		gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
//		PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";

		gv_APIEndpoint = "https://api-3t.paypal.com/nvp";
		PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
		
		caller = new NVPCallerServices();
		logger.debug("Paypal from checkout without error param username: "+username+"password: "+password+" signature "+signature+" environment "+environment );
		try {
			APIProfile apiProfile = ProfileFactory.createSignatureAPIProfile();
			apiProfile.setAPIUsername(username);
			apiProfile.setAPIPassword(password);
			apiProfile.setSignature(signature);
			apiProfile.setEnvironment(environment);
			
			caller.setAPIProfile(apiProfile);
		}
		catch(PayPalException ppe) {
			logger.fatal("problem setting up paypal payment", ppe);
		}
	}
	
	public Paypal(String username, String password, String signature, String enviroment, String success, String error) {
		this.username = username;
		this.password = password;
		this.signature = signature;
		this.environment = enviroment;
		this.success = success;
		this.error = error;
		
	if(enviroment.equalsIgnoreCase("sandbox")) {
		gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
		PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
	} else {
		gv_APIEndpoint = "https://api-3t.paypal.com/nvp";
		PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
	}
		caller = new NVPCallerServices();
		logger.debug("Paypal from checkout with error param username: "+username+"password: "+password+" signature "+signature+" environment "+environment );
		try {
			APIProfile apiProfile = ProfileFactory.createSignatureAPIProfile();
			apiProfile.setAPIUsername(username);
			apiProfile.setAPIPassword(password);
			apiProfile.setSignature(signature);
			apiProfile.setEnvironment(environment);
			
			caller.setAPIProfile(apiProfile);
		}
		catch(PayPalException ppe) {
			logger.fatal("problem setting up paypal payment", ppe);
		}
	}
	
	public Paypal(String username, String password, String certPath, String certPassword,String enviroment, String success, String error) {
		this.username = username;
		this.password = password;
		this.environment = enviroment;
		this.success = success;
		this.error = error;
		
//		gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
//		PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";

		gv_APIEndpoint = "https://api-3t.paypal.com/nvp";
		PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
		
		caller = new NVPCallerServices();
		logger.debug("Paypal from checkout with error param username: "+username+"password: "+password+" signature "+signature+" environment "+environment );
		try {
			APIProfile apiProfile = ProfileFactory.createSignatureAPIProfile();
			apiProfile.setAPIUsername(username);
			apiProfile.setAPIPassword(password);
			apiProfile.setCertificateFile(certPath);
			apiProfile.setPrivateKeyPassword(certPassword);
			apiProfile.setEnvironment(environment);
			
			caller.setAPIProfile(apiProfile);
		}
		catch(PayPalException ppe) {
			logger.fatal("problem setting up paypal payment", ppe);
		}
	}
	

	
	public String setExpressCheckoutRequest(String amt,String currencyType,List<ShoppingCartItem> cartItemList){
		logger.debug("SetExpres PAYPAL ");
		HashMap nvp = CallShortcutExpressCheckout (amt, currencyType, "Sale", success, error,cartItemList,null);
//		HashMap nvp = CallShortcutExpressCheckout (amt, "USD", "Sale", success, error);
		String strAck = nvp.get("ACK").toString();

		if(strAck !=null && strAck.equalsIgnoreCase("Success"))
		{ 
			//System.out.println("\n\n\n\nSUCCESS\n\n\n");
			//' Redirect to paypal.com
			RedirectURL( nvp.get("TOKEN").toString());
			token = nvp.get("TOKEN").toString();
			//System.out.println("TOKEN from set express "+token);
			logger.debug("SetExpres PAYPAL_URL: "+PAYPAL_URL);
			//System.out.println("The return of setExpressCheckoutRequest1111 is "+strAck+" having a token of "+token);
			
			return strAck;
		}
		  
			// Display a user friendly Error on the page using any of the following error information returned by PayPal
			/*
			String ErrorCode = nvp.get("L_ERRORCODE0").toString();
			String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
			String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
			String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
		 */
			return strAck;
	}
	public String setExpressCheckoutRequest(String amt,String currencyType,List<ShoppingCartItem> cartItemList, CategoryItem catItem){
		logger.debug("SetExpres PAYPAL ");
		HashMap nvp = CallShortcutExpressCheckout (amt, currencyType, "Sale", success, error,cartItemList,catItem);
	//	HashMap nvp = CallShortcutExpressCheckout (amt, "USD", "Sale", success, error);
		String strAck = nvp.get("ACK").toString();

		if(strAck !=null && strAck.equalsIgnoreCase("Success"))
		{ 
			//System.out.println("\n\n\n\nSUCCESS\n\n\n");
			//' Redirect to paypal.com
			RedirectURL( nvp.get("TOKEN").toString());
			token = nvp.get("TOKEN").toString();
			logger.debug("SetExpres PAYPAL_URL: "+PAYPAL_URL);
			return strAck;
		}
		  
			// Display a user friendly Error on the page using any of the following error information returned by PayPal
			/*
			String ErrorCode = nvp.get("L_ERRORCODE0").toString();
			String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
			String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
			String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
		 	*/
			return strAck;
	}
	
	public Map<String, String> getExpressCheckoutDetail(String token) throws PayPalException {
		NVPEncoder encoder = new NVPEncoder();

		encoder.add("METHOD","GetExpressCheckoutDetails");
		encoder.add("TOKEN", token);
		
		String response = caller.call(encoder.encode());
		NVPDecoder decoder = new NVPDecoder();
		decoder.decode(response);
		
		Map<String, String> result = null;
		
		if(!decoder.get("ACK").equals("Failure")) {
			result = new HashMap<String, String>();
				
			result.put("CORRELATIONID", decoder.get("CORRELATIONID"));
			result.put("TOKEN", decoder.get("TOKEN"));
			result.put("EMAIL", decoder.get("EMAIL"));
			result.put("PAYERID", decoder.get("PAYERID"));
			result.put("PAYERSTATUS", decoder.get("PAYERSTATUS"));
			result.put("FIRSTNAME", decoder.get("FIRSTNAME"));
			result.put("LASTNAME", decoder.get("LASTNAME"));
			result.put("COUNTRYCODE", decoder.get("COUNTRYCODE"));
			result.put("ADDRESSID", decoder.get("ADDRESSID"));
			result.put("ADDRESSSTATUS", decoder.get("ADDRESSSTATUS"));
			result.put("ERRORSMSG", getErrors(decoder));
		}
		else {
			logger.fatal("PAYPAL RETURNED THE FOLLOWING ERROR: ");
			logger.fatal(getErrors(decoder));
		}
		
		return result;
	}
	
	/*public Map<String, String> doExpressCheckoutPayment(Order order) throws PayPalException {
		NVPEncoder encoder = new NVPEncoder();

		encoder.add("METHOD","DOExpressCheckoutPayment");
		encoder.add("TOKEN", order.getToken());
		encoder.add("PAYMENTACTION", "Sale");
		encoder.add("PAYERID", order.getOrderDetail().getPayerId());
		encoder.add("AMT", String.valueOf(order.getTotalPrice()));
//		encoder.add("INVNUM", order.getId().toString());
//		encoder.add("NOTIFYURL", "");
		encoder.add("ITEMAMT", String.valueOf(order.getTotalPrice()));
		
		int count = 0;
		for(OrderItem item : order.getItems()) {
			encoder.add("L_NAME" + count, item.getName());
			encoder.add("L_NUMBER" + count, item.getItemNumber());
			encoder.add("L_QTY" + count, String.valueOf(item.getQuantity()));
			encoder.add("L_AMT" + count, String.valueOf(item.getTotalPrice()));
			
			count += 1;
		}
		
		String response = caller.call(encoder.encode());
		NVPDecoder decoder = new NVPDecoder();
		decoder.decode(response);
		
		Map<String, String> result = null;
		
		if(!decoder.get("ACK").equals("Failure")) {
			result = new HashMap<String, String>();
			
			result.put("CORRELATIONID", decoder.get("CORRELATIONID"));
			result.put("TOKEN", decoder.get("TOKEN"));
			result.put("TRANSACTIONID", decoder.get("TRANSACTIONID"));
			result.put("TRANSACTIONTYPE", decoder.get("TRANSACTIONTYPE"));
			result.put("PAYMENTTYPE", decoder.get("PAYMENTTYPE"));
			result.put("AMT", decoder.get("AMT"));
			result.put("CURRENCYCODE", decoder.get("CURRENCYCODE"));
			result.put("FEEAMT", decoder.get("FEEAMT"));
			result.put("TAXAMT", decoder.get("TAXAMT"));
			result.put("PAYMENTSTATUS", decoder.get("PAYMENTSTATUS"));
			result.put("PENDINGREASON", decoder.get("PENDINGREASON"));
			result.put("REASONCODE", decoder.get("REASONCODE"));
			result.put("ERRORSMSG", getErrors(decoder));
		}
		else {
			logger.fatal("PAYPAL RETURNED THE FOLLOWING ERROR: ");
			logger.fatal(getErrors(decoder));
		}
		
		return result;
	}*/
	
	 
	
	private String getErrors(NVPDecoder decoder) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while(decoder.get("L_LONGMESSAGE"+i) != null ) {
			sb.append("*** [" + decoder.get("L_ERRORCODE"+i) + "] " + decoder.get("L_LONGMESSAGE"+i));
			sb.append(", ");
			i+=1;
		}
		 
		return sb.toString();
	}
	
	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}
	
	public HashMap CallShortcutExpressCheckout( String paymentAmount, String currencyCodeType, String paymentType, 
			String returnURL, String cancelURL,List<ShoppingCartItem> cartItemList, CategoryItem catItem)
	{
		//System.out.println("CallShortcutExpressCheckout itemName");
	
	//	session.setAttribute("paymentType", paymentType);
	//	session.setAttribute("currencyCodeType", currencyCodeType);
	//	session.setAttribute("itemName", itemName);
		
		/* 
		Construct the parameter string that describes the PayPal payment
		the varialbes were set in the web form, and the resulting string
		is stored in $nvpstr
		*/

		String nvpMap ="";
		if(cartItemList != null)
		{
			if(cartItemList.size()>0){
				//System.out.println("cartItemList.size() "+cartItemList.size());
				for(int i=0; i<cartItemList.size(); i++)
				{
					
					nvpMap=nvpMap+"&L_NAME"+i+"="+cartItemList.get(i).getItemDetail().getName();
					nvpMap=nvpMap+"&L_AMT"+i+"="+ formatter.format(cartItemList.get(i).getItemDetail().getPrice());
					nvpMap=nvpMap+"&L_QTY"+i+"="+cartItemList.get(i).getQuantity().toString();
					nvpMap=nvpMap+"&L_NUMBER"+i+"="+cartItemList.get(i).getId().toString();
					System.out.println("NVPMAP "+nvpMap);
		
				}
				
			}
			
		}
		
		if(this.categoryItems!=null){
			if(categoryItems.size()>0){
				//System.out.println("categoryItems.size() "+categoryItems.size());
				//for(int i=0; i<categoryItems.size(); i++)
			//	{
				int i=0;
					for(CategoryItem categoryItem:categoryItems){
					nvpMap=nvpMap+"&L_NAME"+i+"="+ categoryItem.getName().replace(" ", "+");//categoryItem.getItemDetail().getName().replace(" ", "+");
					nvpMap=nvpMap+"&L_AMT"+i+"="+ categoryItem.getPrice();//formatter.format(categoryItem.getItemDetail().getPrice());
					nvpMap=nvpMap+"&L_QTY"+i+"="+ categoryItem.getOtherDetails();//categoryItem.getOtherDetails();
					nvpMap=nvpMap+"&L_NUMBER"+i+"="+ categoryItem.getSku();//categoryItem.getSku();
					System.out.println("NVPMAP "+nvpMap);
					i++;
				}
				
			}
		}
		
		
		if(catItem!=null){
			nvpMap=nvpMap+"&L_NAME0=" +catItem.getName();
			nvpMap=nvpMap+"&L_AMT0=" +paymentAmount;
			nvpMap=nvpMap+"&L_QTY0=1";
			nvpMap=nvpMap+"&L_NUMBER0=" +catItem.getId().toString();
			//System.out.println("NVPMAP "+nvpMap);
			
		}
		
		
		String nvpstr = "&AMT=" + paymentAmount + "&PAYMENTACTION=" + paymentType + "&ReturnUrl=" + URLEncoder.encode( returnURL ) + "&CANCELURL=" + URLEncoder.encode( cancelURL ) + "&CURRENCYCODE=" + currencyCodeType+nvpMap;
		System.out.println(" nvpstr "+ nvpstr);
		logger.debug("NVPSTR "+nvpstr);
		
		/* 
		Make the call to PayPal to get the Express Checkout token
		If the API call succeded, then redirect the buyer to PayPal
		to begin to authorize payment.  If an error occured, show the
		resulting errors
		*/
		
		HashMap nvp = httpcall("SetExpressCheckout", nvpstr);
		
		if(nvp != null) {
			for(Object item : nvp.keySet()) {
				System.out.println("nvp is not null");
				nvp.get(item).toString();
			}
		} else {
			System.out.println("nvp is null");
		}
		
		String strAck = nvp.get("ACK").toString();
		System.out.println("strAck : " + strAck);
		
		if(strAck !=null && strAck.equalsIgnoreCase("Success"))
		{
			logger.debug("\n\n\nSUCCESS - "+strAck+"\n\n\n");
			//session.setAttribute("TOKEN", strAck);
		}
		else{
			logger.debug("\n\n\n"+strAck+"\n\n\n");
		}
		
		return nvp;
	}

/*********************************************************************************
* CallMarkExpressCheckout: Function to perform the SetExpressCheckout API call 
*
* Inputs:  
*		paymentAmount:  	Total value of the shopping cart
*		currencyCodeType: 	Currency code value the PayPal API
*		paymentType: 		paymentType has to be one of the following values: Sale or Order or Authorization
*		returnURL:			the page where buyers return to after they are done with the payment review on PayPal
*		cancelURL:			the page where buyers return to when they cancel the payment review on PayPal
*		shipToName:		the Ship to name entered on the merchant's site
*		shipToStreet:		the Ship to Street entered on the merchant's site
*		shipToCity:			the Ship to City entered on the merchant's site
*		shipToState:		the Ship to State entered on the merchant's site
*		shipToCountryCode:	the Code for Ship to Country entered on the merchant's site
*		shipToZip:			the Ship to ZipCode entered on the merchant's site
*		shipToStreet2:		the Ship to Street2 entered on the merchant's site
*		phoneNum:			the phoneNum  entered on the merchant's site  
*
* Output: Returns a HashMap object containing the response from the server.
*********************************************************************************/
public HashMap CallMarkExpressCheckout( String paymentAmount, String currencyCodeType, String paymentType, String returnURL, 
String cancelURL, String shipToName, String shipToStreet, String shipToCity, String shipToState,
String shipToCountryCode, String shipToZip, String shipToStreet2, String phoneNum)
	{
	
		session.setAttribute("paymentType", paymentType);
		session.setAttribute("currencyCodeType", currencyCodeType);
		
		/* 
		Construct the parameter string that describes the PayPal payment
		the varialbes were set in the web form, and the resulting string
		is stored in $nvpstr
		*/
		String nvpstr = "ADDROVERRIDE=1&Amt=" + paymentAmount + "&PAYMENTACTION=" + paymentType;
		nvpstr = nvpstr + "&CURRENCYCODE=" + currencyCodeType + "&ReturnUrl=" + URLEncoder.encode( returnURL ) + "&CANCELURL=" + URLEncoder.encode( cancelURL );
		nvpstr = nvpstr + "&SHIPTONAME=" + shipToName + "&SHIPTOSTREET=" + shipToStreet + "&SHIPTOSTREET2=" + shipToStreet2;
		nvpstr = nvpstr + "&SHIPTOCITY=" + shipToCity + "&SHIPTOSTATE=" + shipToState + "&SHIPTOCOUNTRYCODE=" + shipToCountryCode;
		nvpstr = nvpstr + "&SHIPTOZIP=" + shipToZip + "&PHONENUM=" + phoneNum;
		
		/* 
		Make the call to PayPal to set the Express Checkout token
		If the API call succeded, then redirect the buyer to PayPal
		to begin to authorize payment.  If an error occured, show the
		resulting errors
		*/
		
		HashMap nvp = httpcall("SetExpressCheckout", nvpstr);
		String strAck = nvp.get("ACK").toString();
		if(strAck !=null && !(strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning")))
		{
		session.setAttribute("TOKEN", nvp.get("token").toString());
		}			
		return nvp;
	}


/*********************************************************************************
* GetShippingDetails: Function to perform the GetExpressCheckoutDetails API call 
*
* Inputs:  None
*
* Output: Returns a HashMap object containing the response from the server.
*********************************************************************************/
public HashMap GetShippingDetails( String token)
	{
		/* 
		Build a second API request to PayPal, using the token as the
		ID to get the details on the payment authorization
		*/
		
		String nvpstr= "&TOKEN=" + token;
		
		/*
		Make the API call and store the results in an array.  If the
		call was a success, show the authorization details, and provide
		an action to complete the payment.  If failed, show the error
		*/
		
		HashMap nvp = httpcall("GetExpressCheckoutDetails", nvpstr);
/*		String strToken = nvp.get("TOKEN").toString();
		if(strToken !=null)
		{
			TODO: save paymentTransactionDetails
			PaymentDetails pd = new PaymentDetails();
			pd.setToken();
		

		}			*/
		return nvp;
	}

/*********************************************************************************
* GetShippingDetails: Function to perform the DoExpressCheckoutPayment API call 
*
* Inputs:  None
*
* Output: Returns a HashMap object containing the response from the server.
*********************************************************************************/
public HashMap ConfirmPayment( String finalPaymentAmount, HashMap details, String currType,List<ShoppingCartItem> cartItemList, CategoryItem catItem)
	{
	
		/*
		'----------------------------------------------------------------------------
		'----	Use the values stored in the session from the previous SetEC call	
		'----------------------------------------------------------------------------
		*/
		String token 			=  details.get("TOKEN").toString(); //session.getAttribute("TOKEN").toString();
		String currencyCodeType	=  currType;
		String paymentType 		=  "Sale";
		String payerID 			=  details.get("PAYERID").toString(); //session.getAttribute("PAYERID").toString();
		//String serverName 		=  request.getServerName();
		

		String nvpMap ="";
		if(cartItemList != null)
		{
			for(int i=0; i<cartItemList.size(); i++)
			{
				
				nvpMap=nvpMap+"&L_NAME"+i+"="+cartItemList.get(i).getItemDetail().getName();
				nvpMap=nvpMap+"&L_AMT"+i+"="+ formatter.format(cartItemList.get(i).getItemDetail().getPrice());
				nvpMap=nvpMap+"&L_QTY"+i+"="+cartItemList.get(i).getQuantity().toString();
				nvpMap=nvpMap+"&L_NUMBER"+i+"="+cartItemList.get(i).getId().toString();
				
	
			}
		}
		
				
		if(this.categoryItems!=null){
			if(categoryItems.size()>0){
				//System.out.println("categoryItems.size() "+categoryItems.size());
				//for(int i=0; i<categoryItems.size(); i++)
			//	{
				int i=0;
					for(CategoryItem categoryItem:categoryItems){
					nvpMap=nvpMap+"&L_NAME"+i+"="+categoryItem.getItemDetail().getName().replace(" ", "+");
					nvpMap=nvpMap+"&L_AMT"+i+"="+ formatter.format(categoryItem.getItemDetail().getPrice());
					nvpMap=nvpMap+"&L_QTY"+i+"="+categoryItem.getOtherDetails();
					nvpMap=nvpMap+"&L_NUMBER"+i+"="+categoryItem.getSku();
					//System.out.println("NVPMAP "+nvpMap);
					i++;
				}
				
			}
		}
		
		
		if(catItem!=null){
			nvpMap=nvpMap+"&L_NAME0="+catItem.getName();
			nvpMap=nvpMap+"&L_AMT0="+finalPaymentAmount;
			nvpMap=nvpMap+"&L_QTY0=1";
			nvpMap=nvpMap+"&L_NUMBER0="+catItem.getId().toString();
			//System.out.println("NVPMAP "+nvpMap);
			
		}
		String nvpstr  = "&TOKEN=" + token + "&PAYERID=" + payerID + "&PAYMENTACTION=" + paymentType + "&AMT=" + finalPaymentAmount;
		nvpstr = nvpstr + "&CURRENCYCODE=" + currencyCodeType + nvpMap;
		//System.out.println("confirm payment "+nvpstr);
		logger.debug("confirm payment "+nvpstr);
		/* 
		Make the call to PayPal to finalize payment
		If an error occured, show the resulting errors
		*/
		HashMap nvp = httpcall("DoExpressCheckoutPayment", nvpstr);
		
		return nvp;
	}


/*********************************************************************************
* httpcall: Function to perform the API call to PayPal using API signature
* 	@methodName is name of API  method.
* 	@nvpStr is nvp string.
* returns a NVP string containing the response from the server.
*********************************************************************************/
public HashMap httpcall( String methodName, String nvpStr){
	
		String version = "60.0";
		String agent = "Mozilla/4.0";
		String respText = "";
		HashMap nvp = new HashMap();
		
		//deformatNVP( nvpStr );	
		String encodedData = "METHOD=" + methodName + "&VERSION=" + gv_Version + "&PWD=" + password + "&USER=" + username + "&SIGNATURE=" + signature + nvpStr + "&BUTTONSOURCE=" + gv_BNCode;
		System.out.println("encoded data : " + encodedData);
		
		System.out.println("gv_APIEndpoint : " + gv_APIEndpoint);
		try 
		{
			URL postURL = new URL( gv_APIEndpoint );
			HttpURLConnection conn = (HttpURLConnection)postURL.openConnection();
			
			// Set connection parameters. We need to perform input and output, 
			// so set both as true. 
			conn.setDoInput (true);
			conn.setDoOutput (true);
			
			// Set the content type we are POSTing. We impersonate it as 
			// encoded form data 
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty( "User-Agent", agent );
			
			//conn.setRequestProperty( "Content-Type", type );
			conn.setRequestProperty( "Content-Length", String.valueOf( encodedData.length()) );
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Host", "www.paypal.com");
			// get the output stream to POST to. 
			DataOutputStream output = new DataOutputStream( conn.getOutputStream());
			output.writeBytes( encodedData );
			output.flush();
			output.close ();
			
			// Read input from the input stream.
			DataInputStream  in = new DataInputStream (conn.getInputStream()); 
			int rc = conn.getResponseCode();
			if ( rc != -1)
			{
			BufferedReader is = new BufferedReader(new InputStreamReader( conn.getInputStream()));
			String _line = null;
			while(((_line = is.readLine()) !=null)){
				respText = respText + _line;
			}		
			
			System.out.println("RESPONSE: "+respText);
			
			nvp = deformatNVP( respText );
			}
			return nvp;
		}
		catch( IOException e )
		{
			e.printStackTrace();
			// handle the error here
			return null;
		}
}

/*********************************************************************************
* deformatNVP: Function to break the NVP string into a HashMap
* 	pPayLoad is the NVP string.
* returns a HashMap object containing all the name value pairs of the string.
*********************************************************************************/
public HashMap deformatNVP( String pPayload )
	{
		HashMap nvp = new HashMap(); 
		StringTokenizer stTok = new StringTokenizer( pPayload, "&");
		while (stTok.hasMoreTokens()){
			StringTokenizer stInternalTokenizer = new StringTokenizer( stTok.nextToken(), "=");
			if (stInternalTokenizer.countTokens() == 2){
				String key = URLDecoder.decode( stInternalTokenizer.nextToken());
				String value = URLDecoder.decode( stInternalTokenizer.nextToken());
				nvp.put( key.toUpperCase(), value );
				System.out.println("key : " + key + " value : " + " " + value);
			}
		}
		return nvp;
}

/*********************************************************************************
* RedirectURL: Function to redirect the user to the PayPal site
* 	token is the parameter that was returned by PayPal
* returns a HashMap object containing all the name value pairs of the string.
*********************************************************************************/
public void RedirectURL( String token )
	{
		//System.out.println("THEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE TOKEN IS "+token);
		String payPalURL = PAYPAL_URL + token;
		HttpServletResponse response;
		//this.checkoutUrl = payPalURL;
		//response.sendRedirect( payPalURL );
		//response.setStatus(302);
		//response.setHeader( "Location", payPalURL );
		//response.setHeader( "Connection", "close" );		
	}

//public String getCheckoutUrl() {
//	return this.checkoutUrl;
//}

public String getPaypalUrl(){
	return this.PAYPAL_URL;
}

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getSignature() {
	return signature;
}

public void setSignature(String signature) {
	this.signature = signature;
}

private List<CategoryItem> categoryItems;
public void setCategoryItems(List<CategoryItem> catItems) {
	this.categoryItems=catItems;
}


}
