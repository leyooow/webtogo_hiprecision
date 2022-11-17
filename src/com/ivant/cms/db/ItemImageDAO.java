package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ItemImage;

public class ItemImageDAO extends AbstractBaseDAO<ItemImage> {
	
	public ItemImageDAO() {
		super();
	}
	
	public List<ItemImage> findAllByItems(List<CategoryItem> items)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		if(items != null && !items.isEmpty())
			junc.add(Restrictions.in("item", items));
		
		Order[] orders = {Order.asc("item"),Order.asc("sortOrder"),Order.asc("filename")};
		
		return findAllByCriterion(-1, -1, null, null, null, orders, junc).getList();
	}
}
