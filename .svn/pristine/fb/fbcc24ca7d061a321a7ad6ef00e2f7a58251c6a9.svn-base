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

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.ws.rest.model.SinglePage2Model;


/**
 * SinglePage resource for Montero Sports
 * @author Eric John Apondar
 * @since October 2015
 */
@Path("singlePage2")
public class SinglePage2Resource extends AbstractResource{
	private static final Logger logger = LoggerFactory.getLogger(SinglePage2Resource.class);
	private SinglePage2Model singlePageModel;
	private Company company;
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SinglePage2Model findByName(@Context HttpHeaders headers, @FormParam("name") String name){
		logger.info("findByName method executed!");
		SinglePage singlePage = null;
		SinglePage2Model singlePageModel = null;
		try{
			openSession();
			company = getCompany(headers);
			singlePage = singlePageDelegate.find(company, name);
			if(!singlePage.getCompany().equals(company)){
				singlePage = null;
			}
			
			logger.info("^^^^Page Name is :" + singlePage.getName());
			String pageName = singlePage.getName();
			if( pageName.contains("performance_app") ||  pageName.contains("exterior_app") || pageName.contains("interior_app") || pageName.contains("safety_app") ){
				logger.info("^^^^ UNDER SPECIAL TREATMENT ("+ pageName + ") ^^^^");	
				Category category = CategoryDelegate.getInstance().find(company, singlePage.getTitle(), GroupDelegate.getInstance().find(company, "Features Images"));
				if(category != null){
					logger.info("^^^^ category is NOT NULL ^^^^");
					singlePageModel = new SinglePage2Model(singlePage, category);
				}else {
					logger.info("^^^^ category is NULL ^^^^");
					singlePageModel = new SinglePage2Model(singlePage);
				}
			
			}else {
				logger.info("********** HERE ****************");	
				singlePageModel = new SinglePage2Model(singlePage);
			}
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
	public List<SinglePage2Model> findAll(@Context HttpHeaders headers){
		logger.info("findAll method executed");
		List<SinglePage2Model> list = new ArrayList<SinglePage2Model>();
		try{
			openSession();
			company = getCompany(headers);
			List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
			if(singlePageList != null && !singlePageList.isEmpty()){
				for(SinglePage singlePage : singlePageList){
					list.add(new SinglePage2Model(singlePage));
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
