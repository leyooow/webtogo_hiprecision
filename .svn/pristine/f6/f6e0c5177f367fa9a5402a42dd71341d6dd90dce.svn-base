package com.ivant.cms.delegate;

import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.MultiPageFileDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;


public class MultiPageFileDelegate extends AbstractBaseDelegate<MultiPageFile, MultiPageFileDAO>{

	private static MultiPageFileDelegate instance = new MultiPageFileDelegate();
	
	public static MultiPageFileDelegate getInstance() {
		return MultiPageFileDelegate.instance;
	}
	
	public MultiPageFileDelegate() {
		super(new MultiPageFileDAO());
	}

	public List<MultiPageFile> findAllSinglePageFiles(Company company, MultiPage multiPage, SinglePage singlePage) {
		return dao.findAllSinglePageFiles(company, multiPage, singlePage);
	}
	
	public Integer countAllSinglePageFiles(Company company, MultiPage multiPage, SinglePage singlePage) {
		return dao.countAllSinglePageFiles(company, multiPage, singlePage);
	}
	
	public MultiPageFile find(Company company, Long id)
	{
		return dao.find(company, id);
	}

	public ObjectList<MultiPageFile> findAllSinglePageFiles(Company company,
			MultiPage multiPage, SinglePage singlePage, Order... order) {
		// TODO Auto-generated method stub
		return dao.findAllSinglePageFiles(company, multiPage, singlePage, order);
	}
	
}
