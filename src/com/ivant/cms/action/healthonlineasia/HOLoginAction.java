package com.ivant.cms.action.healthonlineasia;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;

import org.json.JSONException;
import org.json.simple.JSONObject;
//import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.opensymphony.xwork2.Action;


public class HOLoginAction /*extends BaseAction implements Preparable*/{
	
	//private static final long serialVersionUID = 1L;
	
	private InputStream inputStream;
	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	private String username;
	private String password;
	
	public static final String URL_HI_PRECISION = "www.hi-precision.com.ph/portal.do";
	
	@SuppressWarnings("unchecked")
	public String authenticate() throws JSONException{ 
        @SuppressWarnings({ })
        HttpClient httpClient = new HttpClient();
        
        HttpMethod method = null;
		try {
			/**
			 * returnUrl parameter was added to allow user to return on either healthonlineasia.com/login.do or hi-precision.com.ph/portal.do during log-out
			 */
			method = new PostMethod(URIUtil.encodeQuery("https://healthonlineasia.ivant.com/patientlogin.do?username=" + username + "&password=" + password +"&returnUrl=" + URL_HI_PRECISION));
		} catch (URIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpClientParams params = new HttpClientParams();
		params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		
		method.setParams(params);
		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();
		
		try {
			int status = httpClient.executeMethod(method);
			if(status!= HttpStatus.SC_OK){
				json.put("error", "An error has occured. Please contact us at support@healthonline.com ");
			}else{
				json = (JSONObject)parser.parse(new InputStreamReader(method.getResponseBodyAsStream()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		System.out.println("Return: " + json.toString());
        inputStream = new ByteArrayInputStream(json.toString().getBytes(UTF8_CHARSET));
        //return returnJson.toString();
		return Action.SUCCESS;
    }
	
	@SuppressWarnings("unchecked")
	public String authenticateCorporate() throws JSONException{ 
		HttpClient httpClient = new HttpClient();
		
        HttpMethod method = null;
		try {
			method = new PostMethod(URIUtil.encodeQuery("https://healthonlineasia.ivant.com/corporatelogin.do?username=" + username + "&password=" + password+"&returnUrl=" + URL_HI_PRECISION));
		} catch (URIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpClientParams params = new HttpClientParams();
		params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		
		method.setParams(params);
		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();
		
		System.out.println(method.toString());
		
		try {
			int status = httpClient.executeMethod(method);
			if(status!= HttpStatus.SC_OK){
				json.put("error", "An error has occured. Please contact us at support@healthonline.com ");
			}else{
				json = (JSONObject)parser.parse(new InputStreamReader(method.getResponseBodyAsStream()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		System.out.println("Return: " + json.toString());
        inputStream = new ByteArrayInputStream(json.toString().getBytes(UTF8_CHARSET));
        //return returnJson.toString();
		return Action.SUCCESS;
    }
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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
	
}
