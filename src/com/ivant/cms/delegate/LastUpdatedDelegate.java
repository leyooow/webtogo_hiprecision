package com.ivant.cms.delegate;

import java.util.Date;

import com.ivant.cms.db.LastUpdatedDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.LastUpdated;

public class LastUpdatedDelegate extends AbstractBaseDelegate<LastUpdated, LastUpdatedDAO> {

	private static LastUpdatedDelegate instance = new LastUpdatedDelegate();
	
	public static LastUpdatedDelegate getInstance() {
		return LastUpdatedDelegate.instance;
	}
	
	public LastUpdatedDelegate() {
		super(new LastUpdatedDAO());
	}
	
	public void saveLastUpdated(final Company company) {
		LastUpdated lastUpdated = dao.findByCompany(company);
		
		if(lastUpdated == null) {
			dao.insertLastUpdated(company);
		}
		else {
			lastUpdated.setUpdatedOn(new Date());
			dao.updateLastUpdated(company, lastUpdated);
		}
	}
}
