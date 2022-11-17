package com.ivant.cms.action.dwr;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.ivant.cms.action.admin.dwr.AbstractDWRAction;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ItemVariation;
import com.ivant.cms.entity.SinglePage;

public class DWRItemAction extends AbstractDWRAction {
	
	private WebContext ctx = WebContextFactory.get();
	private HttpServletRequest req = ctx.getHttpServletRequest();
	private HttpSession session = req.getSession();

	private static final Logger logger = Logger.getLogger(DWRItemAction.class);
	
	public DWRItemVariation searchProduct(long itemId, String searchString) {
		CategoryItem item = categoryItemDelegate.find(itemId);
		
		if(item != null) {
			ItemVariation itemVariation = itemVariationDelegate.find(item, searchString);
			
			if(itemVariation != null) {
				DWRItemVariation dwrItemVariation = new DWRItemVariation(itemVariation.getId(),
						itemVariation.getName(), itemVariation.getSku(), itemVariation.getPrice(),
						itemVariation.getWeight());
				
				dwrItemVariation.setImages(itemVariation.getImagesString());
				
				return dwrItemVariation;
			}
		}
		
		return null;
	}
	
	public void saveNewOrder(List<Long> items) {	
		// get all the category items
		List<CategoryItem> categoryItems = new ArrayList<CategoryItem>();
		int count = 1;
		
		for(Long l : items) {
			CategoryItem itm = categoryItemDelegate.find(l);
			itm.setSortOrder(count++);
			
			if(itm != null && itm.getCompany().equals(company)) {
				try{
					if(itm.getDescription() == null) itm.setDescription("");;
					if(itm.getName() == null) itm.setName("");
				}catch(Exception e){}
				
				categoryItems.add(itm);
			}
			else {
				logger.fatal("Problem sorting items since some items does not belong to the given company");
				return;
			}
		}
		// update the database
		categoryItemDelegate.batchUpdate(categoryItems); 
	}
	
	@SuppressWarnings("unchecked")
	public void saveItem(Long itemId, Integer quantity) {
		
		CategoryItem item = categoryItemDelegate.find(itemId);
		item.setOrderQuantity(quantity);
		List<CategoryItem> catItems = (List<CategoryItem>) req.getSession().getAttribute("noLoginCartItems");
		if(catItems != null) {
			
			boolean isItemExists = false;
			for(CategoryItem catItem : catItems) {
				if(catItem.getName().equalsIgnoreCase(item.getName())) {
					isItemExists = true;
					Integer orderQuantity = catItem.getOrderQuantity();
					catItem.setOrderQuantity(orderQuantity + quantity);
					break;
				}
			}
			
			if(!isItemExists)
				catItems.add(item);
			
		}
		else {
			catItems = new ArrayList<CategoryItem>();
			catItems.add(item);
		}
		req.getSession().setAttribute("noLoginCartItems", catItems);
		
	}
	
	@SuppressWarnings("unchecked")
	public void removeItem(Long itemId) {
		
		CategoryItem item = categoryItemDelegate.find(itemId);
		List<CategoryItem> catItems = (List<CategoryItem>) req.getSession().getAttribute("noLoginCartItems");
		for(CategoryItem catItem : catItems) {
			if(catItem.getName().equalsIgnoreCase(item.getName())) {
				catItems.remove(catItem);
				break;
			}
		}
		
	}
	
}
