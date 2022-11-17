package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.PresetValueDAO;
import com.ivant.cms.entity.Attribute;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.PresetValue;
import com.ivant.cms.entity.list.ObjectList;

public class PresetValueDelegate extends AbstractBaseDelegate<PresetValue, PresetValueDAO>{

private static PresetValueDelegate instance = new PresetValueDelegate();
	
	public static PresetValueDelegate getInstance() {
		return instance;
	}
	
	public PresetValueDelegate() {
		super(new PresetValueDAO());
	}
	
	public PresetValue find(Company company, Group group, String name) {
		return dao.find(company, group, name);
	}
	
	public ObjectList<PresetValue> findAll(Company company, Group group) {
		return dao.findAll(company, group);
	}
	
	public ObjectList<PresetValue> findAll(Company company, Group group, Order...orders) {
		return dao.findAll(company, group, orders);
	}
	
	public ObjectList<PresetValue> findAllFeatured(Company company, boolean showAll, Order...orders) {
		return dao.findAllFeatured(company, showAll, orders);
	}
	
	public ObjectList<PresetValue> findAllFeatured(Company company, boolean showAll) {
		return dao.findAllFeatured(company, showAll);
	}
	
	public ObjectList<PresetValue> findAll(Company company, boolean showAll, Order...orders) {
		return dao.findAll(company, showAll, orders);
	}
	
	public ObjectList<PresetValue> findAllWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		return dao.findAllWithPaging(company, group, resultPerPage, pageNumber, orders);
	}
	
	public ObjectList<PresetValue> findAll(Attribute attribute) {
		return dao.findAll(attribute);
	}
	
}
