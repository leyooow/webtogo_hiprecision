package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemVariation;
import com.ivant.cms.entity.list.ObjectList;

public class ItemVariationDAO extends AbstractBaseDAO<ItemVariation> {

	public ItemVariationDAO() {
		super();
	}
	
	public List<ItemVariation> findAll(Company company, CategoryItem item, Order...orders) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("categoryItem", item));
		return findAllByCriterion(-1, -1, null, null, null,	orders, conj).getList();
	}
	
	public ItemVariation find(CategoryItem categoryItem, String name) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("name", name));
		conj.add(Restrictions.eq("categoryItem", categoryItem));
		
		ObjectList<ItemVariation> result = findAllByCriterion(-1, -1, null, null, null,	null, conj);
		if(result.getSize() > 0) {
			return result.getList().get(0);
		}
		
		return null;
	}
	
	public ItemVariation find(Company company, String sku) {
		return findAllByCriterion(null, null, null, null, 
				Restrictions.eq("company.id", company.getId()), 
				Restrictions.eq("sku", sku)).uniqueResult();
	}
}
