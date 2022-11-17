/**
 *
 */
package com.ivant.cms.ws.rest.client;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.utils.HMAC;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

/**
 * @author Edgar S. Dacpano
 *
 */
public class PromoItemClient
		extends AbstractBaseClient
{
	private static PromoItemClient instance;
	
	private PromoItemClient()
	{
		super();
	}
	
	public String sendNotification(CategoryItem item, String message, DateTime broadcastDate, DateTime endDate)
	{
		final Map<String, String[]> parameterMap = new TreeMap<String, String[]>();
		parameterMap.put(ClientConfigurationServlet.getInstance().getWendysParameterNameDateRequest1(), new String[] { FORMATTER.print(new DateTime()) });
		
		final Map<String, Object> headerMap = new TreeMap<String, Object>();
		headerMap.put(ClientConfigurationServlet.getInstance().getWendysHeaderNamePublicKey1(), ClientConfigurationServlet.getInstance().getWendysPublicKey1());
		headerMap.put(ClientConfigurationServlet.getInstance().getWendysHeaderNameSharedSecret1(), HMAC.sign(ClientConfigurationServlet.getInstance().getWendysPrivateKey1(), parameterMap));
		
		final Form form = new Form();
		form.add("message", message);
		form.add("companyId", item.getCompany().getId());
		form.add("entityId", item.getId());
		form.add("broadcastDate", FORMATTER.print(broadcastDate));
		form.add("endDate", FORMATTER.print(endDate));
		
		final WebResource.Builder builder = getBuilder(ClientConfigurationServlet.getInstance().getWendysUri1(), parameterMap, headerMap, "rest", "apns", "notificationlog", "add");
		final ClientResponse clientResponse = builder.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, form);
		
		final String entits = clientResponse.getEntity(String.class);
		
		System.out.println("RESULT: " + entits);
		
		if(clientResponse.getStatus() == HttpServletResponse.SC_OK || clientResponse.getStatus() == HttpServletResponse.SC_NO_CONTENT)
		{
			return entits;
		}
		else
		{
			return "FAILED";
		}
	}
	
	public static PromoItemClient getInstance()
	{
		if(instance == null)
		{
			instance = new PromoItemClient();
		}
		return instance;
	}
}
