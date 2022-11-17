package com.ivant.cms.action.admin.dwr;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ivant.cms.entity.BrandImage;
import com.ivant.cms.entity.CategoryFile;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.GroupImage;
import com.ivant.cms.entity.ItemImage;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.entity.CategoryImage;
import com.ivant.cms.entity.MultiPageImage;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.entity.interfaces.Page;

public class DWRImageAction extends AbstractDWRAction {

	private static final Logger logger = Logger.getLogger(DWRImageAction.class);
	
	public void updateImageTitle(String type, long imageId, String title) {
		if(type.equals("page")) {
			PageImage image = getPageImage(imageId);
			image.setTitle(title);
			pageImageDelegate.update(image);
		}
		else if(type.equals("item")) {
			ItemImage image = getItemImage(imageId);
			image.setTitle(title);
			itemImageDelegate.update(image);
		}
		else if(type.equals("category")) {
			CategoryImage image = getCategoryImage(imageId);
			image.setTitle(title);
			categoryImageDelegate.update(image);
		}else if(type.equals("multipage")) {
			MultiPageImage image = multiPageImageDelegate.find(imageId);
			image.setTitle(title);
			multiPageImageDelegate.update(image);
		}else if(type.equals("group")) {
			GroupImage image = groupImageDelegate.find(imageId);
			image.setTitle(title);
			groupImageDelegate.update(image);
		}else if(type.equals("brand")){
			BrandImage image = brandImageDelegate.find(imageId);
			image.setTitle(title);
			brandImageDelegate.update(image);
		}
		
	}
	
	public void updateImageCaption(String type, long imageId, String caption) {
		if(type.equals("page")) {
			PageImage image = getPageImage(imageId);
			image.setCaption(caption);
			pageImageDelegate.update(image);
		}
		else if(type.equals("item")) {
			ItemImage image = getItemImage(imageId);
			image.setCaption(caption);
			itemImageDelegate.update(image);
		}
		else if(type.equals("category")) {
			CategoryImage image = getCategoryImage(imageId);
			image.setCaption(caption);
			categoryImageDelegate.update(image);
		}else if(type.equals("multipage")) {
			MultiPageImage image = multiPageImageDelegate.find(imageId);
			image.setCaption(caption);
			multiPageImageDelegate.update(image);
		}else if(type.equals("group")) {
			GroupImage image = getGroupImage(imageId);
			image.setCaption(caption);
			groupImageDelegate.update(image);
		}else if(type.equals("brand")){
			BrandImage image = brandImageDelegate.find(imageId);
			image.setCaption(caption);
			brandImageDelegate.update(image);
		}				
	}
	
	public void updateImageDescription(String type, long imageId, String description) {
		if(type.equals("page")) {
			PageImage image = getPageImage(imageId);
			image.setDescription(description);
			pageImageDelegate.update(image);
		}
		else if(type.equals("item")) {
			ItemImage image = getItemImage(imageId);
			image.setDescription(description);
			itemImageDelegate.update(image);
		}
		else if(type.equals("category")) {
			CategoryImage image = getCategoryImage(imageId);
			image.setDescription(description);
			categoryImageDelegate.update(image);
		}else if(type.equals("multipage")) {
			MultiPageImage image = multiPageImageDelegate.find(imageId);
			image.setDescription(description);
			multiPageImageDelegate.update(image);
		}else if(type.equals("group")) {
			GroupImage image = groupImageDelegate.find(imageId);
			image.setDescription(description);
			groupImageDelegate.update(image);
		}else if(type.equals("brand")){
			BrandImage image = brandImageDelegate.find(imageId);
			image.setDescription(description);
			brandImageDelegate.update(image);
		}		
	}
	
	private PageImage getPageImage(long id) {
		PageImage image = pageImageDelegate.find(id);
		Page page = (image).getPage();
		
		if( !((CompanyBaseObject)page).getCompany().equals(company) ) {
			image = null;
		} 
		
		return image;
	}
	
	private ItemImage getItemImage(long id) {
		ItemImage image = itemImageDelegate.find(id);
		CategoryItem item = (image).getItem();
		
		if( !((CompanyBaseObject)item).getCompany().equals(company) ) {
			item = null;
		}
		
		return image;
	}
	
	private CategoryImage getCategoryImage(long id) {
		CategoryImage image = categoryImageDelegate.find(id);
		Category category = (image).getCategory();
		
		if( !((CompanyBaseObject)category).getCompany().equals(company) ) {
			category = null;
		}
		
		return image;
	}
	
	private GroupImage getGroupImage(long id) {
		GroupImage image = groupImageDelegate.find(id);
		Group group = (image).getGroup();
		
		if( !((CompanyBaseObject)group).getCompany().equals(company) ) {
			group = null;
		}
		
		return image;
	}
	
	public void saveNewOrder(List<Long> items) {	
		// get all the category items
		List<ItemImage> itemImages = new ArrayList<ItemImage>();
		int count = 1;
		
		for(Long l : items) {
			ItemImage itm = itemImageDelegate.find(l);
			itm.setSortOrder(count++);
			
			if(itm != null) {
				try{
					if(itm.getDescription() == null) itm.setDescription("");;
					if(itm.getTitle() == null) itm.setTitle("");
				}catch(Exception e){}
				
				itemImages.add(itm);
			}
			else {
				logger.fatal("Problem sorting items since some items does not belong to the given company");
				return;
			}
		}
		// update the database
		itemImageDelegate.batchUpdate(itemImages); 
	}
	
	public void saveGroupImagesOrder(List<Long> items) {	
		// get all the category items
		List<GroupImage> groupImages = new ArrayList<GroupImage>();
		int count = 1;
		
		for(Long l : items) {
			GroupImage itm = groupImageDelegate.find(l);
			itm.setSortOrder(count++);
			
			if(itm != null) {
				try{
					if(itm.getDescription() == null) itm.setDescription("");;
					if(itm.getTitle() == null) itm.setTitle("");
				}catch(Exception e){}
				
				groupImages.add(itm);
			}
			else {
				logger.fatal("Problem sorting items since some items does not belong to the given company");
				return;
			}
		}
		// update the database
		groupImageDelegate.batchUpdate(groupImages); 
	}
	
	public void savePageImagesOrder(List<Long> items) {	
		// get all the category items
		List<PageImage> pageImages = new ArrayList<PageImage>();
		int count = 1;
		
		for(Long l : items) {
			PageImage itm = pageImageDelegate.find(l);
			itm.setSortOrder(count++);
			
			if(itm != null) {
				try{
					if(itm.getDescription() == null) itm.setDescription("");;
					if(itm.getTitle() == null) itm.setTitle("");
				}catch(Exception e){}
				
				pageImages.add(itm);
			}
			else {
				logger.fatal("Problem sorting items since some items does not belong to the given company");
				return;
			}
		}
		// update the database
		pageImageDelegate.batchUpdate(pageImages); 
	}
	
	public void saveBrandImagesOrder(List<Long> items) {	
		// get all the category items
		List<BrandImage> brandImages = new ArrayList<BrandImage>();
		int count = 1;
		
		for(Long l : items) {
			BrandImage itm = brandImageDelegate.find(l);
			itm.setSortOrder(count++);
			
			if(itm != null) {
				try{
					if(itm.getDescription() == null) itm.setDescription("");;
					if(itm.getTitle() == null) itm.setTitle("");
				}catch(Exception e){}
				
				brandImages.add(itm);
			}
			else {
				logger.fatal("Problem sorting items since some items does not belong to the given company");
				return;
			}
		}
		// update the database
		brandImageDelegate.batchUpdate(brandImages); 
	}
	
	
//------------------------ for file upload  --------------------------------------------------
	
	
	public void updateFileTitle(String type, long fileId, String title) {
		if(type.equals("page")) {
			PageImage image = getPageImage(fileId);
			image.setTitle(title);
			pageImageDelegate.update(image);
		}
		else 
			if(type.equals("item")) {
			ItemFile file = getItemFile(fileId);
			file.setTitle(title);
			itemFileDelegate.update(file);
		}
		else if(type.equals("category")) {
			CategoryFile file = getCategoryFile(fileId);
			file.setTitle(title);
			categoryFileDelegate.update(file);
		}		
	}
	
	public void updateFileCaption(String type, long fileId, String caption) {
		if(type.equals("page")) {
			PageImage image = getPageImage(fileId);
			image.setCaption(caption);
			pageImageDelegate.update(image);
		}
		else 
			if(type.equals("item")) {
			ItemFile file = getItemFile(fileId);
			file.setCaption(caption);
			itemFileDelegate.update(file);
		}
		else if(type.equals("category")) {
			CategoryFile file = getCategoryFile(fileId);
			file.setCaption(caption);
			categoryFileDelegate.update(file);
		}		
	}
	
	public void updateFileDescription(String type, long fileId, String description) {
		if(type.equals("page")) {
			PageImage image = getPageImage(fileId);
			image.setDescription(description);
			pageImageDelegate.update(image);
		}
		else 
			if(type.equals("item")) {
			ItemFile file = getItemFile(fileId);
			file.setDescription(description);
			itemFileDelegate.update(file);
		}
		else if(type.equals("category")) {
			CategoryFile file = getCategoryFile(fileId);
			file.setDescription(description);
			categoryFileDelegate.update(file);
		}		
	}
	
//	private PageImage getPageImage(long id) {
//		PageImage image = pageImageDelegate.find(id);
//		Page page = (image).getPage();
//		
//		if( !((CompanyBaseObject)page).getCompany().equals(company) ) {
//			image = null;
//		} 
//		
//		return image;
//	}
	
	private ItemFile getItemFile(long id) {
		ItemFile file = itemFileDelegate.find(id);
		CategoryItem item = (file).getItem();
		
		if( !((CompanyBaseObject)item).getCompany().equals(company) ) {
			item = null;
		}
		
		return file;
	}
	
	private CategoryFile getCategoryFile(long id) {
		CategoryFile file = categoryFileDelegate.find(id);
		Category category = (file).getCategory();
		
		if( !((CompanyBaseObject)category).getCompany().equals(company) ) {
			category = null;
		}
		
		return file;
	}
	
	
	
	
	
	
	
	
}
