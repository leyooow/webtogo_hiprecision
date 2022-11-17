package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.VariationGroupDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.VariationGroup;
import com.ivant.cms.entity.list.ObjectList;

public class VariationGroupDelegate extends AbstractBaseDelegate<VariationGroup, VariationGroupDAO> {

	private static VariationGroupDelegate instance = new VariationGroupDelegate();
	
	public static VariationGroupDelegate getInstance() {
		return instance;
	}
	
	public VariationGroupDelegate() {
		super(new VariationGroupDAO());
	}
	 
	public ObjectList<VariationGroup> findAll(Company company, Order...orders) {
		return dao.findAll(company, orders);
	}
	
	public VariationGroup find(String name) {
		return dao.find(name);
	}
	
}
