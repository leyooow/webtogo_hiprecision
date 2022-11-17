package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.BrandDAO;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class BrandDelegate extends AbstractBaseDelegate<Brand, BrandDAO>{

private static BrandDelegate instance = new BrandDelegate();
	
	public static BrandDelegate getInstance() {
		return instance;
	}
	
	public BrandDelegate() {
		super(new BrandDAO());
	}
	
	public Brand find(Company company, Group group, String name) {
		return dao.find(company, group, name, false);
	}
	
	public Brand findByKeyword(Company company, Group group, String keyword) {
		return dao.find(company, group, keyword, true);
	}
	
	public ObjectList<Brand> findAll(Company company) {
		return dao.findAll(company);
	}
	
	public ObjectList<Brand> findAll(Company company, Group group) {
		return dao.findAll(company, group);
	}
	
	public ObjectList<Brand> findAll(Company company, Group group, Order...orders) {
		return dao.findAll(company, group, orders);
	}
	
	public ObjectList<Brand> findAllEnabled(Company company, boolean showAll, Order...orders) {
		return dao.findAllEnabled(company, showAll, orders);
	}
	
	public ObjectList<Brand> findAllEnabledWithPaging(Company company, int resultPerPage, int pageNumber, boolean showAll, Order...orders) {
		return dao.findAllEnabled(company, resultPerPage, pageNumber, showAll, orders);
	}
	
	public ObjectList<Brand> findAllFeatured(Company company, boolean showAll, Order...orders) {
		return dao.findAllFeatured(company, showAll, orders);
	}
	
	public ObjectList<Brand> findAllFeatured(Company company, boolean showAll) {
		return dao.findAllFeatured(company, showAll);
	}
	
	public ObjectList<Brand> findAll(Company company, boolean showAll, Order...orders) {
		return dao.findAll(company, showAll, orders);
	}
	
	public ObjectList<Brand> findAllWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		return dao.findAllWithPaging(company, group, resultPerPage, pageNumber, orders);
	}
}
