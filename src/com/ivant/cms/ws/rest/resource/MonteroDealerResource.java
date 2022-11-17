package com.ivant.cms.ws.rest.resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.ws.rest.model.MonteroDealerModel;
import com.ivant.cms.ws.rest.model.SinglePage2Model;


/**
 * Montero Dealer resource for Montero Sports
 * @author Eric John Apondar
 * @since October 2015
 */
@Path("monteroDealer")
public class MonteroDealerResource extends AbstractResource{
	private static final Logger logger = LoggerFactory.getLogger(MonteroDealerResource.class);
	private Company company;
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/getAll")
	public List<MonteroDealerModel> findAll(@Context HttpHeaders headers){
		logger.info("findAll method executed");
		List<MonteroDealerModel> list = new ArrayList<MonteroDealerModel>();
		try{
			openSession();
			company = getCompany(headers);
			List<CategoryItem> items = getListCategoryItemMap().get("Dealers");
			if(items != null && !items.isEmpty()){
				for(CategoryItem item : items){
					list.add(new MonteroDealerModel(item));
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
	
	
	/* Get Category Item Map  */
	public Map<String, List<CategoryItem>> getListCategoryItemMap(){
		Map<String, List<CategoryItem>> categoryItemMap = new HashMap<String, List<CategoryItem>>();
		final List<Group> listCategories = groupDelegate.findAll(company).getList();
		for(Group g: listCategories){
			categoryItemMap.put(g.getName(), categoryItemDelegate.findAllEnabledWithPaging(company, g, -1, -1, null).getList());
		}
		return categoryItemMap;
	}
}
