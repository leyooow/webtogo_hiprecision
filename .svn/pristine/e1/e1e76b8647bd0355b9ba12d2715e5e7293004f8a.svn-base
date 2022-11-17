package com.ivant.cms.delegate;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.db.MultiPageDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.db.MultiPageDAO;
import com.ivant.cms.entity.list.ObjectList;

public class MultiPageDelegate extends AbstractBaseDelegate<MultiPage, MultiPageDAO>{

	private static Logger logger = Logger.getLogger(MultiPageDelegate.class);
	private static MultiPageDelegate instance = new MultiPageDelegate();
	
	public static MultiPageDelegate getInstance() {
		return instance;
	}
	
	public MultiPageDelegate() {
		super(new MultiPageDAO());
	}
	
	public MultiPage find(Company company, String name) {
		return dao.find(company, name);
	}
	 
	public MultiPage findJsp(Company company, String jsp) {
		return dao.findJsp(company, jsp);
	}
	
	public ObjectList<MultiPage> findAll(Company company) {
		return dao.findAll(company);
	}
	
	public ObjectList<MultiPage> findAllFeatured(Company company) {
		return dao.findAllFeatured(company);
	}
	
	public ObjectList<MultiPage> findAllWithPaging(Company company, int resultPerPage, int pageNumber) {
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, null);
	}
	
	public ObjectList<MultiPage> findAllWithPaging(Company company, int resultPerPage, int pageNumber, String[] orderBy) {
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, orderBy);
	}
	
	public ObjectList<MultiPage> findAllParentMultiPages(Company company)
	{
		return dao.findAllParentMultiPages(company);
		
	}
	
	public ObjectList<MultiPage> findPageByKeyWord(String key, Company company)
	{
		return dao.findPageByKeyWord(key, company);
	}
	
}
