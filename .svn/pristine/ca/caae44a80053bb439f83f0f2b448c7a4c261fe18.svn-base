package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemPrice;
import com.ivant.cms.entity.CategoryItemPriceName;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.list.ObjectList;

public class CategoryItemPriceDAO extends AbstractBaseDAO<CategoryItemPrice>{

	public CategoryItemPrice findByCategoryItemPriceName(Company company, CategoryItem categoryItem, CategoryItemPriceName categoryItemPriceName)
	{
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("categoryItemPriceName", categoryItemPriceName));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("categoryItem", categoryItem));
		
		return findAllByCriterion(null, null, null, null, junc).uniqueResult();
	}

	public ObjectList<CategoryItemPrice> findAllByCategoryItem(Company company, CategoryItem categoryItem)
	{
		final Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("categoryItem", categoryItem));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
}
