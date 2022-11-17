package com.ivant.cms.ws.rest.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.ws.rest.model.MultiPageModel;

/**
 * 
 * @author Anjerico D. Gutierrez
 *
 */

@Path("multiPage")
public class MultiPageResource extends AbstractResource {
	
	private static final Logger logger = LoggerFactory.getLogger(MultiPageResource.class);
	private MultiPageModel multiPageModel;
	private Company company;
	
	/*
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MultiPageModel find(@Context HttpHeaders headers, @FormParam("id") Long id){
		logger.info("find method executed!");
		MultiPage multiPage = null;
		MultiPageModel multiPageModel = null;
		try{
			openSession();
			company = getCompany(headers);
			multiPage = multiPageDelegate.find(id);
			if(!multiPage.getCompany().equals(company)){
				multiPage = null;
			}
			multiPageModel = new MultiPageModel(multiPage);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			closeSession();
		}
		return multiPageModel;
	}
	*/
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MultiPageModel findByName(@Context HttpHeaders headers, @FormParam("name") String name){
		logger.info("findByName method executed!");
		MultiPage multiPage = null;
		MultiPageModel multiPageModel = null;
		try{
			openSession();
			company = getCompany(headers);
			multiPage = multiPageDelegate.find(company, name);
			if(!multiPage.getCompany().equals(company)){
				multiPage = null;
			}
			multiPageModel = new MultiPageModel(multiPage);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			closeSession();
		}
		return multiPageModel;
	}
	
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/getAll")
	public List<MultiPageModel> findAll(@Context HttpHeaders headers){
		logger.info("findAll method executed!");
		List<MultiPageModel> list = new ArrayList<MultiPageModel>();
		try
		{
			openSession();
			company = getCompany(headers);
			List<MultiPage> multiPageList = multiPageDelegate.findAll(company).getList();
			if(multiPageList != null && !multiPageList.isEmpty()){
				for(MultiPage multiPage : multiPageList){
					list.add(new MultiPageModel(multiPage));
				}
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			closeSession();
		}
		return list;
	}
	
	
	
	
}
