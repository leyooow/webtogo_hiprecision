package com.ivant.cms.action.healthonlineasia;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.opensymphony.xwork2.Action;

public class PatientResetPassword {
	
	private InputStream inputStream;
	
	private String pid;  //username
	private String lastname;  //password

	private String username;
	private String password;
	
	private String birthdayStr;
	private String lastTestDateStr;
	private String lastTestBranchCode;
	private String url; 
	@SuppressWarnings("unchecked")
	public String checkUser() throws JSONException{ 
        @SuppressWarnings({ })
        HttpClient httpClient = new HttpClient();
        
        HttpMethod method = new PostMethod("https://healthonlineasia.ivant.com/findhouser.do?username=" + username);
		HttpClientParams params = new HttpClientParams();
		params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		
		method.setParams(params);
		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();
		
		System.out.println("username = " + username );
		System.out.println("birthdayStr = " + birthdayStr);
		System.out.println("lastTestDateStr = " + lastTestDateStr);
		System.out.println("lastTestBranchCode = " + lastTestBranchCode);
		
		
		try {
			int status = httpClient.executeMethod(method);
			if(status!= HttpStatus.SC_OK){
				json.put("error", "An error has occured. Please contact us at support@healthonline.com ");
			}else{
				json = (JSONObject)parser.parse(new InputStreamReader(method.getResponseBodyAsStream()));
				
				System.out.println("Return: " + json.toString());
				
				if((Boolean) json.get("exist")){
					String url = json.get("baseUrl").toString();
					method = new PostMethod(url + "/resetpassword.do?pid=" + username + "&birthdayStr=" + birthdayStr + "&lastTestDateStr=" + lastTestDateStr + "&lastTestBranchCode=" + lastTestBranchCode);
					params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
					
					method.setParams(params);
					
					status = httpClient.executeMethod(method);
					if(status!= HttpStatus.SC_OK){
						json.put("error", "An error has occured. Please contact us at support@healthonline.com ");
					}else{
						json = (JSONObject)parser.parse(new InputStreamReader(method.getResponseBodyAsStream()));
					}
					///------------------------------------------------------///
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		System.out.println("Return: " + json.toString());
        inputStream = new ByteArrayInputStream(json.toString().getBytes());
        //return returnJson.toString();
		return Action.SUCCESS;
    }
	
	@SuppressWarnings("unchecked")
	public String findAllBranch() throws JSONException{ 
        @SuppressWarnings({ })
        HttpClient httpClient = new HttpClient();
        
        HttpMethod method = new PostMethod("https://healthonlineasia.ivant.com/findbranch.do");
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
				
				json.put("name", json.get("name"));
				json.put("code", json.get("code"));
				
				System.out.println("Return: " + json.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		System.out.println("Return: " + json.toString());
        inputStream = new ByteArrayInputStream(json.toString().getBytes());
        //return returnJson.toString();
		return Action.SUCCESS;
    }

	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPid() 
	{
		return pid;
	}

	public void setPid(String pid) 
	{
		this.pid = pid;
	}
	
	public String getLastname()
	{
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}
	
	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public String getLastTestDateStr() {
		return lastTestDateStr;
	}

	public void setLastTestDateStr(String lastTestDateStr) {
		this.lastTestDateStr = lastTestDateStr;
	}

	public String getLastTestBranchCode() {
		return lastTestBranchCode;
	}

	public void setLastTestBranchCode(String lastTestBranchCode) {
		this.lastTestBranchCode = lastTestBranchCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
