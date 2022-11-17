package com.ivant.cms.delegate;

import com.ivant.cms.db.TermsDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Terms;

public class TermsDelegate extends AbstractBaseDelegate<com.ivant.cms.entity.Terms, TermsDAO> {

	private static TermsDelegate instance = new TermsDelegate();
	
	public static TermsDelegate getInstance() {
		return TermsDelegate.instance;
	}
	
	public TermsDelegate() {
		super(new TermsDAO());
	}
	
	public Terms findByCompany(Company company) {
		return dao.findByCompany(company);
	}
}
