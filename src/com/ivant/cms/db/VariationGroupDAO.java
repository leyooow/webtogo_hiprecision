package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.VariationGroup;
import com.ivant.cms.entity.list.ObjectList;

public class VariationGroupDAO extends AbstractBaseDAO<VariationGroup> {

	public VariationGroupDAO() {
		super();
	}
	
	public ObjectList<VariationGroup> findAll(Company company, Order...orders) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		return findAllByCriterion(-1, -1, null, null, null,	orders, conj);
	}
	
	public VariationGroup find(String name) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("name", name));
		
		ObjectList<VariationGroup> obj = findAllByCriterion(-1, -1, null, null, null, null, conj);
		List<VariationGroup> list = obj.getList();
		  
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	
}
