package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.VariationDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Variation;
import com.ivant.cms.entity.list.ObjectList;

public class VariationDelegate extends AbstractBaseDelegate<Variation, VariationDAO> {

	private static final VariationDelegate instance = new VariationDelegate();
	
	public static VariationDelegate getInstance() {
		return instance;
	}
	
	public VariationDelegate() {
		super(new VariationDAO());
	}
	
	public ObjectList<Variation> findAll(Company company, Order...orders) {
		return dao.findAll(company, orders);
	}
	
	public Variation find(String name) {
		return dao.find(name);
	}
	
}
