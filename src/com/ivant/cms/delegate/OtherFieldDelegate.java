package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.OtherFieldDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.OtherField;

public class OtherFieldDelegate
		extends AbstractBaseDelegate<OtherField, OtherFieldDAO>
{
	private static OtherFieldDelegate instance = new OtherFieldDelegate();
	
	public static OtherFieldDelegate getInstance() {
		return instance;
	}
	
	public OtherFieldDelegate() {
		super(new OtherFieldDAO());
	}	
	
	public OtherField find(String name,Company company) {
		return dao.find(name, company, false);
	}
	
	public OtherField find(String name, boolean isKeyword, Company company, Group group) {
		return dao.find(name, isKeyword, company, group);
	}
	
	public OtherField findByKeyword(String name, Company company)
	{
		return dao.find(name, company, true);
	}
	
	public List<OtherField> find(Company company) {	
		return dao.find(company);
	}
	
	public List<OtherField> findByGroup(Company company, Group group) {	
		return dao.findByGroup(company, group);
	}
}
