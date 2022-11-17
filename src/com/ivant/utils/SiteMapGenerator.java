package com.ivant.utils;

import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;

/**
 * utility object. this will be used to lazily fetch the information needed to generate a sitemap
 * @author Rey Bumalay
 *
 */

public class SiteMapGenerator {

	private static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private static final SinglePageDelegate singelPageDelegate = SinglePageDelegate.getInstance();
	private static final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private static final FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	
	private Company company;
	
	public SiteMapGenerator(Company company) {
		this.company = company;
	}
	
	public List<SinglePage> getSinglePageList() {
		return singelPageDelegate.findAllPublished(company, null, Order.asc("id")).getList();
	}
	
	public List<FormPage> getFormPageList() {
		return formPageDelegate.findAll(company).getList();
	}
	
	public List<MultiPage> getMultiPageList() {
		return multiPageDelegate.findAll(company).getList();
	}
	
	public List<Group> getGroupList() {
		return groupDelegate.findAll(company).getList();
	}
}
