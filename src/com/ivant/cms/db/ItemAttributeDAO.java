package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Attribute;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemAttribute;
import com.ivant.cms.entity.list.ObjectList;

public class ItemAttributeDAO extends AbstractBaseDAO<ItemAttribute> {
	
	public ItemAttributeDAO() {
		super();
	}
	
	public ObjectList<ItemAttribute> findAll(CategoryItem item) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("categoryItem", item));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
}
