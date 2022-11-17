package com.ivant.cms.delegate;

import com.ivant.cms.db.FormPageDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.list.ObjectList;

public class FormPageDelegate extends AbstractBaseDelegate<FormPage, FormPageDAO>{

	private static FormPageDelegate instance = new FormPageDelegate();
	
	public static FormPageDelegate getInstance() {
		return instance;
	}
	
	public FormPageDelegate() {
		super(new FormPageDAO());
	}
	
	public FormPage find(Company company, String name) {
		return dao.find(company,name);
	}
	
	public FormPage findJsp(Company company, String jsp) {
		return dao.findJsp(company,jsp);
	}
	
	public ObjectList<FormPage> findAll(Company company) {
		return dao.findAll(company);
	}
	
//	public ObjectList<FormPage> findAllPublished(Company company) {		
//		return dao.findAllPublished(company, true);
//	}
//	
//	public ObjectList<FormPage> findAllPublished(Company company, boolean published) {		
//		return dao.findAllPublished(company, published);
//	}
	
	public ObjectList<FormPage> findAllWithPaging(Company company, int resultPerPage, int pageNumber) {
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, null);
	}
	 
	public ObjectList<FormPage> findAllWithPaging(Company company, int resultPerPage, int pageNumber, String[] orderBy) {
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, orderBy);
	}
}
