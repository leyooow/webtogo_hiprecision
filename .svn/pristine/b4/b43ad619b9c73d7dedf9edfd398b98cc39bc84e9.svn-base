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
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ShoppingCartItem;
import com.paypal.sdk.exceptions.PayPalException;
import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;
import com.paypal.sdk.services.NVPCallerServices;

public class PaypalAction {

	private static final Logger logger = Logger.getLogger(PaypalAction.class);
	
	private final NumberFormat formatter = new DecimalFormat("#0.00");
	
	private String username, password, signature, environment, returnUrl, cancelUrl;
	
	private String amount, currencyType, paymentType;
	private String shippingAmount;
	
	String gv_APIEndpoint;
	String gv_nvpHeader;	
	String PAYPAL_URL;
	String token;
	String payerId;
	
	public PaypalAction(String username, String password, String signature, String environment) {
		this.username = username;
		this.password = password;
		this.signature = signature;
		if(environment.equalsIgnoreCase("sandbox")) {
			gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
			PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
		} else {
			gv_APIEndpoint = "https://api-3t.paypal.com/nvp";
			PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
		}

		logger.debug("Paypal from getExpress username: "+username);
		
	}
	
	public PaypalAction(String username, String password, String signature, String environment, 
							String currencyType, String success, String error) {
		this.username = username;
		this.password = password;
		this.signature = signature;
		this.environment = environment;
		this.currencyType = currencyType;
		this.returnUrl = success;
		this.cancelUrl = error;
		
		if(environment.equalsIgnoreCase("sandbox")) {
			gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
			PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
		} else {
			gv_APIEndpoint = "https://api-3t.paypal.com/nvp";
			PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
		}
		
	}
	
	public String setExpressCheckoutRequest(String amount, String shippingAmount){
		logger.debug("SetExpres PAYPAL ");
		this.amount = amount;
		this.shippingAmount = shippingAmount;
		this.paymentType = "Sale";
		HashMap nvp = CallShortcutExpressCheckout();
		String strAck = nvp.get("ACK").toString();

		if(strAck !=null && strAck.equalsIgnoreCase("Success"))
		{ 
			//RedirectURL( nvp.get("TOKEN").toString());
			token = nvp.get("TOKEN").toString();
			logger.debug("SetExpres PAYPAL_URL: "+PAYPAL_URL);
			return strAck;
		}
		return strAck;
	}
	
	public HashMap CallShortcutExpressCheckout() {
		String nvpMap ="";
		
		if(this.categoryItems!=null){
			if(categoryItems.size() > 0) {
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
		
		String nvpstr = "&AMT=" + amount 
		//		+"&SHIPPINGAMT=" + shippingAmount
				+"&PAYMENTACTION=" + paymentType
				+"&CURRENCYCODE=" + currencyType
				+"&RETURNURL=" + URLEncoder.encode(returnUrl) 
				+"&CANCELURL=" + URLEncoder.encode(cancelUrl)
				+nvpMap;
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
		}
		else{
			logger.debug("\n\n\n"+strAck+"\n\n\n");
		}
		
		return nvp;
		
	}
	
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
	
	public HashMap ConfirmPayment( String finalPaymentAmount, HashMap details, String currType)
	{
	
		/*
		'----------------------------------------------------------------------------
		'----	Use the values stored in the session from the previous SetEC call	
		'----------------------------------------------------------------------------
		*/
		String amount			= finalPaymentAmount;
		String token 			= details.get("TOKEN").toString(); //session.getAttribute("TOKEN").toString();
		String currencyType		= currType;
		String paymentType 		= "Sale";
		String payerID 			= details.get("PAYERID").toString(); //session.getAttribute("PAYERID").toString();
		//String serverName 		=  request.getServerName();
		
		String nvpMap ="";
				
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
		
		//String nvpstr  = "&TOKEN=" + token + "&PAYERID=" + payerID + "&PAYMENTACTION=" + paymentType + "&AMT=" + finalPaymentAmount;
		//nvpstr = nvpstr + "&CURRENCYCODE=" + currencyType + nvpMap;
		
		String nvpstr = "&TOKEN=" + token 
				+"&PAYERID=" + payerID 
				+"&AMT=" + amount 
				+"&PAYMENTACTION=" + paymentType
				+"&CURRENCYCODE=" + currencyType
				+nvpMap;
		
		//System.out.println("confirm payment "+nvpstr);
		logger.debug("confirm payment "+nvpstr);
		/* 
		Make the call to PayPal to finalize payment
		If an error occured, show the resulting errors
		*/
		HashMap nvp = httpcall("DoExpressCheckoutPayment", nvpstr);
		
		return nvp;
	}
	
	public HashMap httpcall(String methodName, String nvpStr){
		
		String version = "93.0";
		String agent = "Mozilla/4.0";
		String respText = "";
		HashMap nvp = new HashMap();
		
		//String encodedData = "METHOD=" + methodName + "&VERSION=" + gv_Version + "&PWD=" + password + "&USER=" + username + "&SIGNATURE=" + signature + nvpStr + "&BUTTONSOURCE=" + gv_BNCode;
		
		String encodedData = "USER=" + username
						+"&PWD=" + password
						+"&SIGNATURE=" + signature
						+"&VERSION=" + version
						+""
						+"&METHOD=" + methodName
						+nvpStr;
		
		System.out.println("encoded data : " + encodedData);
		
		System.out.println("gv_APIEndpoint : " + gv_APIEndpoint);
		
		try 
		{
			URL postURL = new URL(gv_APIEndpoint);
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
			if ( rc != -1) {
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
	
	public HashMap deformatNVP( String pPayload ) {
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
	
	public void RedirectURL( String token )
	{
		String payPalURL = PAYPAL_URL + token;
		HttpServletResponse response;
	}
	
	public String getPaypalUrl(){
		return this.PAYPAL_URL;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	private List<CategoryItem> categoryItems;
	public void setCategoryItems(List<CategoryItem> catItems) {
		this.categoryItems=catItems;
	}
	
}
