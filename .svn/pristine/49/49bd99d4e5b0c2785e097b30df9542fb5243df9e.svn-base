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
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.ws.rest.model.SinglePageModel;


/**
 * 
 * @author Anjerico D. Gutierrez
 *
 */
@Path("singlePage")
public class SinglePageResource extends AbstractResource{
	private static final Logger logger = LoggerFactory.getLogger(SinglePageResource.class);
	private SinglePageModel singlePageModel;
	private Company company;
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SinglePageModel findByName(@Context HttpHeaders headers, @FormParam("name") String name){
		logger.info("findByName method executed!");
		SinglePage singlePage = null;
		SinglePageModel singlePageModel = null;
		try{
			openSession();
			company = getCompany(headers);
			singlePage = singlePageDelegate.find(company, name);
			if(!singlePage.getCompany().equals(company)){
				singlePage = null;
			}
			singlePageModel = new SinglePageModel(singlePage);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			closeSession();
		}
		return singlePageModel;
	}
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/getAll")
	public List<SinglePageModel> findAll(@Context HttpHeaders headers){
		logger.info("findAll method executed");
		List<SinglePageModel> list = new ArrayList<SinglePageModel>();
		try{
			openSession();
			company = getCompany(headers);
			List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
			if(singlePageList != null && !singlePageList.isEmpty()){
				for(SinglePage singlePage : singlePageList){
					list.add(new SinglePageModel(singlePage));
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
