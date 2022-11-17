/**
 * 
 */
package com.ivant.cms.ws.rest.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ivant.cms.ws.rest.bean.AbstractClientBean;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

/**
 * @author Kevin Roy K. Chua
 * @author Edgar Dacpano
 * @version 1.0 May 26, 2014
 * @since 1.0, May 26, 2014
 */
public abstract class AbstractBaseClient
{
	/** The date/time formatter - MMddYYYYHHmmss */
	protected static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("MMddYYYYHHmmss");
	
	/**
	 * Get the base URI of the application.
	 * 
	 * @return the base URI
	 */
	protected final URI getBaseUri(String uri)
	{
		return UriBuilder.fromUri(uri).build(); 
	}
	
	/**
	 * Get the web resource to access the REST service
	 * 
	 * @return the service
	 */
	protected final WebResource getService(String uri)
	{
		final ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 60000); // 1 minute
		clientConfig.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, 1800000); // 30 minutes
		
		final Client client = Client.create(clientConfig);
		
		return client.resource(getBaseUri(uri));
	}
	
	/**
	 * Create form adding the parameter map.
	 * 
	 * @param parameterMap the parameter map
	 * @return the created form
	 */
	protected final Form createForm(Map<String, String[]> parameterMap)
	{
		final Form form = new Form();
		
		if(parameterMap != null)
		{
			for(Map.Entry<String, String[]> entry : parameterMap.entrySet())
			{
				final List<String> valueList = Arrays.asList(entry.getValue());
				final Iterator<String> iterator = valueList.iterator();
				
				while(iterator.hasNext())
				{
					form.add(entry.getKey(), iterator.next());
				}
			}
		}
		
		return form;
	}
	
	/**
	 * 

	 * @return
	 */
	/**
	 * 
	 * @param uri
	 * @param parameterMap
	 * @param headerMap
	 * @param paths - the path to be added to the base URI, ie String[] {"a", "b", "c"} will result to "baseURI/a/b/c"
	 * @return
	 */
	protected final WebResource.Builder getBuilder(String uri, Map<String, String[]> parameterMap, Map<String, Object> headerMap, String...paths)
	{
		if(paths == null || paths.length == 0)
		{
			return null;
		}
		
		WebResource webResource = getService(uri);
		for(String path : paths)
		{
			webResource = webResource.path(path);
		}
		
		if(parameterMap != null)
		{
			for(Map.Entry<String, String[]> entry : parameterMap.entrySet())
			{
				final List<String> valueList = Arrays.asList(entry.getValue());
				final Iterator<String> iterator = valueList.iterator();
				
				while(iterator.hasNext())
				{
					webResource = webResource.queryParam(entry.getKey(), iterator.next());
				}				
			}
		}
		
		WebResource.Builder builder = null;
		if(headerMap != null)
		{
			for(Map.Entry<String, Object> entry : headerMap.entrySet())
			{
				final String key = entry.getKey();
				final Object value = entry.getValue();
				if(builder == null)
				{
					builder = webResource.header(key, value);	
				}
				else
				{
					builder = builder.header(key, value);
				}
			}
		}
				
		System.out.println("WEB RES URI: " + webResource.getURI());
		
		return builder;
	}
	
	/**
	 * append the bean to a form
	 * @param form
	 * @param beanRootElementName - the name value of the annotation XmlRootElement from the client
	 * @param bean
	 * @param clazz
	 * @throws JAXBException
	 * @throws IOException
	 */
	protected final <T extends AbstractClientBean> void addClientBean(Form form, String beanRootElementName, T bean, Class<T> clazz) throws JAXBException, IOException
	{
		final OutputStream out = new ByteArrayOutputStream();
		final JAXBContext context = JAXBContext.newInstance(clazz);
		final Marshaller m = context.createMarshaller();
		
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(bean, out);
		
		out.close();
		
		System.out.println("Bean to XML: " + out.toString());
		
		form.add(beanRootElementName, out.toString());
	}

}