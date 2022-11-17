package com.ivant.cms.ws.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.ws.rest.model.GroupModel;

@Path("/group")
public class GroupResource extends AbstractResource
{
	private static final Logger logger = LoggerFactory.getLogger(GroupResource.class);
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.TEXT_PLAIN })
	public String find(@Context HttpHeaders headers,
			@FormParam("id") Long id)
	{
		logger.info("find method execute");
		
		Group group = null;
		try
		{
			openSession();
			Company company = getCompany(headers);
			System.out.println("company ? " + company);
			
			group = groupDelegate.find(company, id);
			System.out.println("group ? " + group);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		if(group == null) return null;
		
		return "SUCCESS";
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public GroupModel get(@Context HttpHeaders headers,
			@QueryParam("id") Long id)
	{
		Group group = null;
		try
		{
			openSession();
			Company company = getCompany(headers);
			group = groupDelegate.find(company, id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		if(group == null) throwWebApplicationException(Status.NO_CONTENT);
		
		return group.toGroupModel();
	}
	
}
