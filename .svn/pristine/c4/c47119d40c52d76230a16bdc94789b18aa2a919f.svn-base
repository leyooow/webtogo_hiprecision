package com.ivant.cart.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Action for company products create, update, and delete.
 * 
 * @author Mark Kenneth M. Rañosa
 *
 */
public class ProductAction extends AbstractBaseAction {
	
	private Logger logger = LoggerFactory.getLogger(ProductAction.class);
	private static final long serialVersionUID = 5036647123665899814L;

	/** Object responsible for company product CRUD tasks */
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	
	/** List of products being sold by the company to members */
	private List<CategoryItem> itemList;
	
	@Override
	public void prepare() throws Exception {
		//populate wishlist item list
		initItemList();
		
		//populate server URL to be redirected to
		initHttpServerUrl();
	}
	
	@Override
	public String execute() throws Exception {		
		return SUCCESS;
	}
	
	/**
	 * Populates {@code itemList} based on the session parameters.
	 */
	private void initItemList() {
		try {			
			ObjectList<CategoryItem> objectList = categoryItemDelegate.findAll(company);
			itemList = objectList.getList();			
		} catch (Exception e) {
			logger.debug("No items found");
		}
	}
	
	/**
	 * Returns {@code itemList} property value.
	 * 
	 * @return - {@code itemList} property value
	 */
	public List<CategoryItem> getItemList() {
		return itemList;
	}
	
	/**
	 * Returns {@code notificationMessage} property value.
	 * 
	 * @return - {@code notificationMessage} property value
	 */
	public String getNotificationMessage() {
		return notificationMessage;
	}
	
}
