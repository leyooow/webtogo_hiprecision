package com.ivant.cms.ws.rest.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.ws.rest.model.CategoryModel;
import com.ivant.cms.ws.rest.model.SubCategoryModel;

@Path("category")
public class CategoryResource extends AbstractResource
{
	private static final Logger logger = LoggerFactory.getLogger(CategoryResource.class);
	
	private Company company;
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CategoryModel find(@Context HttpHeaders headers,
			@FormParam("id") Long id)
	{
		logger.info("find method executed!");
		
		Category category = null;
		CategoryModel categoryModel = null;
		try
		{
			openSession();
			company = getCompany(headers);
			category = categoryDelegate.find(id);
			
			if(!category.getCompany().equals(company))
			{
				category = null;
			}
			
			categoryModel = new CategoryModel(category);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		return categoryModel;
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/getAll")
	public List<CategoryModel> findAll(@Context HttpHeaders headers,
			@FormParam("parentId") Long parentId,
			@FormParam("groupId") Long groupId,
			@FormParam("showAll") Boolean showAll)
	{
		List<CategoryModel> list = new ArrayList<CategoryModel>();
		Category parentCategory = null;
		Group parentGroup = null;
		
		try
		{
			openSession();
			company = getCompany(headers);
			parentCategory = categoryDelegate.find(parentId);
			parentGroup = groupDelegate.find(company, groupId);
			
			List<Category> categoryList = categoryDelegate.findAll(company, parentCategory, parentGroup,
					 (showAll != null ? showAll : false), false).getList();
			
			if(categoryList != null && !categoryList.isEmpty())
			{
				for(Category category : categoryList)
				{
					list.add(new CategoryModel(category));
				}
			}
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
	@Path("/getAllSubCategory")
	public List<SubCategoryModel> findAllSubCategory(@Context HttpHeaders headers,
			@FormParam("id") Long id) {

		Category category = null;
		List<SubCategoryModel> subCategoryModels = new ArrayList<SubCategoryModel>();
		try
		{
			openSession();
			company = getCompany(headers);
			category = categoryDelegate.find(id);
			
			if(!category.getCompany().equals(company))
			{
				category = null;
			}
			
			if(category.getChildrenCategory().size() > 0) {
				for(Category subCategory : category.getChildrenCategory()) {
					System.out.println("sub category id : " + subCategory.getId().toString());
					SubCategoryModel subCategoryModel = new SubCategoryModel(subCategory, categoryDelegate, categoryItemDelegate);
					subCategoryModels.add(subCategoryModel);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		return subCategoryModels;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/getCurrentDateTime")
	public String findCurrentDateTime(@Context HttpHeaders headers)
	{
		logger.info("find method execute");
		
		JSONObject obj = new JSONObject();
		
		
		try
		{
			openSession();
			Company company = getCompany(headers);
			System.out.println("company ? " + company);
			
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
			int day = now.get(Calendar.DAY_OF_MONTH);
			int hour = now.get(Calendar.HOUR_OF_DAY);
			int minute = now.get(Calendar.MINUTE);
			int second = now.get(Calendar.SECOND);
			int millis = now.get(Calendar.MILLISECOND);
			
			
			obj.put("success", true);
			
			obj.put("year", year);
			obj.put("month", month);
			obj.put("day", day);
			obj.put("hour", hour);
			obj.put("minute", minute);
			obj.put("second", second);
			obj.put("millisecond", millis);
			obj.put("ampm", now.get(Calendar.AM_PM) == Calendar.AM ? 0 : 1);

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			obj.put("error", true);
			
		}
		finally
		{
			closeSession();
		}
		
		return obj.toJSONString();
	}
}
