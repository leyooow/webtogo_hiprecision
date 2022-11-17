package com.ivant.cms.ws.rest.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.hibernate.criterion.Order;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.ws.rest.bean.ProductUpdateBean;
import com.ivant.cms.ws.rest.model.CategoryItemModel;
import com.ivant.cms.ws.rest.model.SubCategoryModel;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.DateUtil;

@Path("categoryItem")
public class CategoryItemResource extends AbstractResource
{
	private static final Logger logger = LoggerFactory.getLogger(CategoryItemResource.class);
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CategoryItemModel find(@Context HttpHeaders headers,
			@FormParam("id") Long id,
			@FormParam("otherFields") List<String> otherFields)
	{
		logger.info("find method executed!");
		
		CategoryItem categoryItem = null;
		CategoryItemModel categoryItemModel = null;
		Company company = null;
		
		try
		{
			openSession();
			company = getCompany(headers);
			
			categoryItem = categoryItemDelegate.find(company, id);
			categoryItemModel = new CategoryItemModel(categoryItem, otherFields);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		
		return categoryItemModel;
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/getAll")
	public List<CategoryItemModel> findAll(@Context HttpHeaders headers,
			@FormParam("categoryId") Long categoryId,
			@FormParam("groupId") Long groupId,
			@FormParam("fromDate") String fromDate,
			@FormParam("toDate") String toDate,
			@FormParam("showAll") Boolean showAll,
			@FormParam("otherFields") List<String> otherFields)
	{
		List<CategoryItemModel> list = new ArrayList<CategoryItemModel>();
		Category category = null;
		Group group = null;
		Company company = null;
		
		try
		{
			openSession();
			company = getCompany(headers);
			
			category = categoryDelegate.find(categoryId);
			group = groupDelegate.find(company, groupId);
			
			Date from = DateUtil.getDate(fromDate, DateUtil.DATE_TIME_FORMAT_PATTERN_SLASH);
			Date to = DateUtil.getDate(toDate, DateUtil.DATE_TIME_FORMAT_PATTERN_SLASH);
			
			List<CategoryItem> categoryItemList = categoryItemDelegate.findByCriteria(-1, -1, 
					company, group, category, from, to, (showAll != null ? showAll : false), 
					new Order[] { Order.asc("sortOrder") }).getList();
			
			if(categoryItemList != null && !categoryItemList.isEmpty())
			{
				for(CategoryItem categoryItem : categoryItemList)
				{
					list.add(new CategoryItemModel(categoryItem, otherFields));
				}
			}
		}
		catch(WebApplicationException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		return list;
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/getAllFeatured")
	public List<CategoryItemModel> findAllFeatured(@Context HttpHeaders headers,
			@FormParam("categoryId") Long categoryId,
			@FormParam("groupId") Long groupId,
			@FormParam("showAll") Boolean showAll,
			@FormParam("otherFields") List<String> otherFields)
	{
		List<CategoryItemModel> list = new ArrayList<CategoryItemModel>();
		Category category = null;
		Group group = null;
		Company company = null;
		
		try
		{
			openSession();
			company = getCompany(headers);
			
			category = categoryDelegate.find(categoryId);
			group = groupDelegate.find(company, groupId);
			
			List<CategoryItem> categoryItemList = categoryItemDelegate.findAllFeaturedWithPaging(
					company, group, category, -1, -1, (showAll != null ? showAll : false),
					true,
					new Order[] { Order.asc("sortOrder") }).getList();
			
			if(categoryItemList != null && !categoryItemList.isEmpty())
			{
				for(CategoryItem categoryItem : categoryItemList)
				{
					list.add(new CategoryItemModel(categoryItem, otherFields));
				}
			}
		}
		catch(WebApplicationException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		return list;
	}
	
	
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/search")
	public List<CategoryItemModel> search(@Context HttpHeaders headers,
			@FormParam("keyword") String keyword,
			@FormParam("otherFields") List<String> otherFields)
	{
		List<CategoryItemModel> list = new ArrayList<CategoryItemModel>();
		
		Company company = null;
		
		try
		{
			openSession();
			company = getCompany(headers);
			
			
			List<CategoryItem> categoryItemList = categoryItemDelegate.search(keyword, company);
			categoryItemList.addAll(categoryItemDelegate.searchByTags(keyword, company));
			if(categoryItemDelegate.findSKU(company, keyword)!=null){
				categoryItemList.add(categoryItemDelegate.findSKU(company, keyword));
			}
			final HashSet h = new HashSet(categoryItemList);
			categoryItemList.clear();
			categoryItemList.addAll(h);
			
			if(categoryItemList != null && !categoryItemList.isEmpty())
			{
				for(CategoryItem categoryItem : categoryItemList)
				{
					list.add(new CategoryItemModel(categoryItem, otherFields));
				}
			}
		}
		catch(WebApplicationException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		return list;
	}
	/*
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/hasNewProductUpdate")
	public String findNewProductUpdate(@Context HttpHeaders headers,
			@QueryParam("updatedate") String deyt) {
		Company company = null;
		JSONObject obj = new JSONObject();
		
		try
		{
			//System.out.println("#1##"+date);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date result =  df.parse(deyt);
			
			openSession();
			company = getCompany(headers);
			Group productGroup = new Group();
			 productGroup = groupDelegate.find(CompanyConstants.WENDYS_PRODUCT_GROUP_ID);
			 if(productGroup != null){
				 obj.put("hasNewUpdate", categoryItemDelegate.hasNewUpdate(company, productGroup, result));
				 //obj.put("groupNotNull", Boolean.TRUE);
				 //return categoryItemDelegate.hasNewUpdate(company, productGroup, date).toString();
			 }
			 else{
				 obj.put("hasNewUpdate", Boolean.FALSE);
				 //obj.put("groupNotNull", Boolean.FALSE);
			 }
		}
		catch(Exception e){
			e.printStackTrace();
			obj.put("error", true);
		}
		finally
		{
			closeSession();
		}
		return obj.toJSONString();
	}
	*/
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/hasNewProductUpdate")
	public ProductUpdateBean findNewProductUpdate(@Context HttpHeaders headers,
			@FormParam("updatedate") String deyt,
			@DefaultValue("0") @QueryParam("id") String prodid) {
		Company company = null;
		JSONObject obj = new JSONObject();
		ProductUpdateBean pub = new ProductUpdateBean();
		Long pid = 0L;
		Category cat = new Category();
		//System.out.println("pid val : "+prodid);
		
		try
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date result =  df.parse(deyt);
			
			openSession();
			try{
				pid = Long.parseLong(prodid);
				cat = categoryDelegate.find(pid);
			}catch(Exception e){
				pid = 0L;
			}
			//System.out.println("pid val 2 : "+pid);
			company = getCompany(headers);
			Group productGroup = new Group();
			 productGroup = groupDelegate.find(CompanyConstants.WENDYS_PRODUCT_GROUP_ID);
			 if(productGroup != null){
				 Boolean tempRes = categoryItemDelegate.hasNewUpdate(company, productGroup, result, pid, cat);
				 if(!tempRes){
					 //query another
					 tempRes = categoryDelegate.hasNewUpdate(company, productGroup, result, pid, cat);
				 }
				 if(!tempRes){
					 //query for itself
					 tempRes = categoryDelegate.hasNewSelfUpdate(company, productGroup, result, pid, cat);
				 }
				 if(!tempRes){
					 //for grand child
					 tempRes = categoryItemDelegate.hasNewGrandChildUpdate(company, productGroup, result, pid, cat);
				 }
				 
				 pub.setHasNewUpdate(tempRes);
				 pub.setCurrentServerDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			 }
			 else{
				 pub.setHasNewUpdate(Boolean.FALSE);
				 pub.setCurrentServerDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
				 //obj.put("groupNotNull", Boolean.FALSE);
			 }
		}
		catch(Exception e){
			e.printStackTrace();
			pub.setHasNewUpdate(Boolean.FALSE);
			pub.setCurrentServerDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		}
		finally
		{
			closeSession();
		}
		return pub;
	}
	
}
