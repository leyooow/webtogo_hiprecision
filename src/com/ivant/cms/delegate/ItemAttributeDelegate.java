package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.ItemAttributeDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ItemAttribute;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class ItemAttributeDelegate extends AbstractBaseDelegate<ItemAttribute, ItemAttributeDAO>{

private static ItemAttributeDelegate instance = new ItemAttributeDelegate();
	
	public static ItemAttributeDelegate getInstance() {
		return instance;
	}
	
	public ItemAttributeDelegate() {
		super(new ItemAttributeDAO());
	}
	
	public ObjectList<ItemAttribute> findAll(CategoryItem item) {
		return dao.findAll(item);
	}
	
}
