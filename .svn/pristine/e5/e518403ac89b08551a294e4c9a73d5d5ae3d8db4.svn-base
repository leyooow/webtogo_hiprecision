package com.ivant.cms.action;

import java.io.IOException;

import com.ivant.cms.interfaces.PageDispatcherAware;
import com.ivant.cms.ws.rest.client.ClientConfigurationServlet;
import com.ivant.cms.ws.rest.client.SmsClient;

public class SmsAction extends PageDispatcherAction
	implements PageDispatcherAware{
	
	private String uri;
	private String deviceId;
	private String clientId;
	private String msisdn;
	private String smsMessage;
	private String countryCode;
	private String applicationType;
	private String companyName;
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void prepare() throws Exception
	{
		super.prepare();
	}
	
	@Override
	public String execute() throws Exception
	{
		return super.execute();
	}
	/*
	public String sendSms() throws IOException
	{
		final String result = SmsClient.getInstance().sendSms("https://sms.ivant.com",
				"QUEUEPAD123456", "1", "+639166681005", "Send Message Test 11-GENERIC", "PH", "WTGWENDYS", "Wendys",
				ClientConfigurationServlet.getInstance().getWendysPrivateKey2(),
				ClientConfigurationServlet.getInstance().getWendysHeaderNamePublicKey2(),
				ClientConfigurationServlet.getInstance().getWendysPublicKey2(),
				ClientConfigurationServlet.getInstance().getWendysHeaderNameSharedSecret2());
		return result;
	}
	*/
	
	public String sendSms() throws IOException
	{
		try 
		{
			System.out.println("URI:"+getUri()+"\n"+
					"DEVICE ID:"+getDeviceId()+"\n"+
					"CLIENT ID:"+getClientId()+"\n"+
					"MSISDN:"+getMsisdn()+"\n"+
					"MESSAGE:"+getSmsMessage()+"\n"+
					"COUNTRY CODE:"+getCountryCode()+"\n"+
					"APPLICATION TYPE:"+getApplicationType()+"\n"+
					"COMPANY NAME:"+getCompanyName());
			final String result = SmsClient.getInstance().sendSms(getUri(),
					getDeviceId(), getClientId(), getMsisdn(), getSmsMessage(), getCountryCode(), getApplicationType(), getCompanyName(),
					ClientConfigurationServlet.getInstance().getWendysPrivateKey2(),
					ClientConfigurationServlet.getInstance().getWendysHeaderNamePublicKey2(),
					ClientConfigurationServlet.getInstance().getWendysPublicKey2(),
					ClientConfigurationServlet.getInstance().getWendysHeaderNameSharedSecret2());
			System.out.println("@@@@@@@@@@-- SUCCESS --@@@@@@");
		}
		catch (Exception e) 
		{
			System.err.println(e);
			return ERROR;
		}
		
		return SUCCESS; 
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getSmsMessage() {
		return smsMessage;
	}

	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
	
}
