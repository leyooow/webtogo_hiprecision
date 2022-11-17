package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.AttributeDAO;
import com.ivant.cms.entity.Attribute;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class AttributeDelegate extends AbstractBaseDelegate<Attribute, AttributeDAO>{

private static AttributeDelegate instance = new AttributeDelegate();
	
	public static AttributeDelegate getInstance() {
		return instance;
	}
	
	public AttributeDelegate() {
		super(new AttributeDAO());
	}
	
	public Attribute find(Company company, Group group, String name) {
		return dao.find(company, group, name);
	}
	
	public ObjectList<Attribute> findAll(Company company, Group group) {
		return dao.findAll(company, group);
	}
	
	public ObjectList<Attribute> findAll(Company company, Group group, Order...orders) {
		return dao.findAll(company, group, orders);
	}
	
	public ObjectList<Attribute> findAllFeatured(Company company, boolean showAll, Order...orders) {
		return dao.findAllFeatured(company, showAll, orders);
	}
	
	public ObjectList<Attribute> findAllFeatured(Company company, boolean showAll) {
		return dao.findAllFeatured(company, showAll);
	}
	
	public ObjectList<Attribute> findAll(Company company, boolean showAll, Order...orders) {
		return dao.findAll(company, showAll, orders);
	}
	
	public ObjectList<Attribute> findAllWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		return dao.findAllWithPaging(company, group, resultPerPage, pageNumber, orders);
	}
}
