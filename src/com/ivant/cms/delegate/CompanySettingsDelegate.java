package com.ivant.cms.delegate;

import com.ivant.cms.db.CompanySettingsDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.list.ObjectList;

public class CompanySettingsDelegate extends AbstractBaseDelegate<CompanySettings, CompanySettingsDAO> {

	private static CompanySettingsDelegate instance = new CompanySettingsDelegate();
	
	public static CompanySettingsDelegate getInstance() {
		return CompanySettingsDelegate.instance;
	}
		
	public CompanySettingsDelegate() {
		super(new CompanySettingsDAO());
	}
	
	public CompanySettings find(Company company) {
		return dao.find(company);
	}	
	
	/**
	 * @param isValid - false to include not valid (isValid = false) entries, true otherwise.
	 * @return
	 */
	public ObjectList<CompanySettings> findAll(Boolean isValid)
	{
		return dao.findAll(isValid);
	}
}
