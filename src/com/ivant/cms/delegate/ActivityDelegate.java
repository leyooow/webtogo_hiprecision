package com.ivant.cms.delegate;

import com.ivant.cms.db.ActivityDAO;
import com.ivant.cms.entity.Activity;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.list.ObjectList;

public class ActivityDelegate extends AbstractBaseDelegate< Activity, ActivityDAO> {
	
	private static final ActivityDelegate instance = new ActivityDelegate();
	
	public static ActivityDelegate getInstance() {
		return instance;
	}

	private ActivityDelegate() {
		super(new ActivityDAO());
	}
	
	public ObjectList<Activity> findAllWithPaging(Company company,int resultPerPage, int pageNumber, String[] orderBy) {
		return dao.findAllWithPaging(company,resultPerPage, pageNumber, orderBy);
	}
	
	public ObjectList<Activity> findAll(Company company) {
		return dao.findAll(company);
	}
}