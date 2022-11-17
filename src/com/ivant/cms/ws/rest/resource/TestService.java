package com.ivant.cms.ws.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.ivant.cms.entity.Company;

/**
 * 
 * @author Kent
 *
 */
@Path("/test")
public class TestService extends AbstractResource
{

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String helloPost(@Context HttpHeaders headers)
	{
		Company company = null;
		
		try
		{
			openSession();
			company = getCompany(headers);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		if(company == null)
			return "Hello anonymous company! <" + headers.getRequestHeader(HOST_HEADER) +">";
		else
			return "Hello " + company.getName();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String helloGet(@Context HttpHeaders headers)
	{
		Company company = null;
		
		try
		{
			openSession();
			company = getCompany(headers);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		if(company == null)
			return "Hello anonymous company! <" + headers.getRequestHeader(HOST_HEADER) +">";
		else
			return "Hello " + company.getName();
	}
}
