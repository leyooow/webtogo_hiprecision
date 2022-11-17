package com.ivant.cms.db;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Variation;
import com.ivant.cms.entity.list.ObjectList;

public class VariationDAO extends AbstractBaseDAO<Variation>{

	public VariationDAO() {
		super();
	}
	
	public ObjectList<Variation> findAll(Company company, Order...orders) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		return findAllByCriterion(-1, -1, null, null, null,	orders, conj);
	}
	
	public Variation find(String name) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("name", name));
		
		ObjectList<Variation> obj = findAllByCriterion(-1, -1, null, null, null, null, conj);
		
		if(obj.getTotal() > 0) {
			return obj.getList().get(0);
		}
		
		return null;
	}
	
}
