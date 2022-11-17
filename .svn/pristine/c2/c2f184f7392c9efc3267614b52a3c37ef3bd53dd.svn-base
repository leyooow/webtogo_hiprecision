package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.OSSShippingLocationDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.OSSShippingLocation;

public class OSSShippingLocationDelegate extends AbstractBaseDelegate<OSSShippingLocation, OSSShippingLocationDAO> {
	
	private static final OSSShippingLocationDelegate instance = new OSSShippingLocationDelegate();
	
	public static OSSShippingLocationDelegate getInstance() {
		return instance;
	}

	private OSSShippingLocationDelegate() {
		super(new OSSShippingLocationDAO());
	}
	
	public List<OSSShippingLocation> findAll(Company company) {
		return dao.findAll(company);
	}

	public OSSShippingLocation find(Company company, String location) {
		return dao.find(company, location);
	}
}
