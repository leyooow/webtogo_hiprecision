/**
 *
 */
package com.ivant.cms.ws.rest.client;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

/**
 * @author Edgar S. Dacpano
 */
public class TestClient
		extends AbstractBaseClient
{
	private static TestClient instance;
	
	private TestClient()
	{
		super();
	}
	
	public String test()
	{
		final Map<String, String[]> parameterMap = new TreeMap<String, String[]>();
		parameterMap.put(ClientConfigurationServlet.getInstance().getWendysParameterNameDateRequest1(), new String[] { FORMATTER.print(new DateTime()) });
		
		final Form form = new Form();
		form.add("message", "sdadsadsa");
		form.add("companyId", Long.valueOf(404));
		form.add("broadcastDate", FORMATTER.print(new DateTime()));
		form.add("endDate", FORMATTER.print(new DateTime().plusHours(1)));
		
		/*
		 * final TestClientBean test = new TestClientBean();
		 * test.setMessage("messsageeeeeeeeeeeeeeee");
		 * test.setCompanyId(Long.valueOf(404));
		 * test.setBroadcastDate(FORMATTER.print(new DateTime()));
		 * test.setEndDate(FORMATTER.print(new DateTime()));
		 * try
		 * {
		 * addClientBean(form, "test", test, TestClientBean.class);
		 * }
		 * catch(Exception e)
		 * {
		 * e.printStackTrace();
		 * }
		 */
		
		final WebResource.Builder builder = getBuilder(ClientConfigurationServlet.getInstance().getWendysUri1(), parameterMap, null, "rest", "apns", "notificationlog", "add");
		final ClientResponse clientResponse = builder.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, form);
		
		final String entits = clientResponse.getEntity(String.class);
		if(clientResponse.getStatus() == HttpServletResponse.SC_OK || clientResponse.getStatus() == HttpServletResponse.SC_NO_CONTENT)
		{
			return entits;
		}
		else
		{
			return "FAILED";
		}
	}
	
	public static final TestClient getInstance()
	{
		if(instance == null)
		{
			instance = new TestClient();
		}
		return instance;
	}
	
}
