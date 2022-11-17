package com.ivant.cms.ws.rest.client;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.ivant.utils.HMAC;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class SmsClient extends AbstractBaseClient 
{
	private static SmsClient instance;
	
	public SmsClient()
	{
		super();
	}
	
	public static final SmsClient getInstance()
	{
		if(instance == null)
		{
			instance = new SmsClient();
		}
		return instance;
	}
	
	public String sendSms(String uri, 
							String deviceId,
							String clientId,
							String msisdn,
							String smsMessage,
							String countryCode,
							String applicationType,
							String companyName,
							String companyPrivateKey,
							String companyHeaderNamePublicKey,
							String companyPublicKey,
							String companyHeaderNameSharedSecret) throws IOException
	{
		//final Map<String, String[]> parameterMap = new TreeMap<String, String[]>();
		//parameterMap.put(ClientConfigurationServlet.getInstance().getWendysParameterNameDateRequest2(), new String[] { FORMATTER.print(new DateTime()) });
		
		Client client = Client.create();
		WebResource service = client.resource(getBaseURI(uri)).path("rest").path("message").path("sendsms");
		final Form form = new Form();
		form.add("deviceId", deviceId);
		form.add("clientId", clientId);
		form.add("msisdn", msisdn);
		form.add("smsMessage", smsMessage);
		form.add("countryCode", countryCode);
		form.add("applicationType", applicationType);
		form.add("companyName", companyName);
		final String dtRequest = DateTimeFormat.forPattern("MMddYYYYHHmmss").print(new DateTime());
		final Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		parameterMap.put("dtRequest", new String[]{dtRequest});
		final String key = HMAC.sign(companyPrivateKey, parameterMap);
		service = service.queryParam("dtRequest", dtRequest);
		final ClientResponse clientResponse = service.accept(MediaType.APPLICATION_JSON)
				.header(companyHeaderNamePublicKey, companyPublicKey)
				.header(companyHeaderNameSharedSecret, key)
				.post(ClientResponse.class, form);
		System.out.println("###########################################");
		System.out.println("###########################################");
		System.out.println(clientResponse.getEntity(String.class));
		System.out.println("###########################################");
		System.out.println("###########################################");
		//String result = (String)clientResponse.getEntity(String.class);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		if(clientResponse.getStatus() == HttpServletResponse.SC_OK || clientResponse.getStatus() == HttpServletResponse.SC_NO_CONTENT)
		{
			System.out.println("@@@@@@@@@@-- ABCDEFG --@@@@@@");
			return "SUCCESS";
		}
		else
		{
			System.out.println("@@@@@@@@@@-- XYZ --@@@@@@");
			return "FAILED";
		}
		
	}
	
	private static URI getBaseURI(String uri)
	{
		return UriBuilder.fromUri(uri).build();
	}
	
}
