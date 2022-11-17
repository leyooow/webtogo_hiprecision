package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.CompanyDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.CompanyStatusEnum;

public class CompanyDelegate extends AbstractBaseDelegate<Company, CompanyDAO> {

	private static CompanyDelegate instance = new CompanyDelegate();
	
	public static CompanyDelegate getInstance() {
		return CompanyDelegate.instance;
	}
		
	public CompanyDelegate() {
		super(new CompanyDAO());
	}
	
	public Company find(String name) {
		return dao.find(name);
	}
	
	public Company findServerName(String serverName) {
		return dao.findServerName(serverName);
	}
	
	public ObjectList<Company> findAll(String[] orderBy) {
		return dao.findAll(orderBy);
	} 
	
	public ObjectList<Company> findAllWithPaging(int resultPerPage, int pageNumber) {
		return dao.findAllWithPaging(resultPerPage, pageNumber, null);
	}
	
	public ObjectList<Company> findAllWithPaging(int resultPerPage, int pageNumber, String[] orderBy) {
		return dao.findAllWithPaging(resultPerPage, pageNumber, orderBy);
	}
	public ObjectList<Company> findByStatus(CompanyStatusEnum companyStatus) {
		return dao.findByStatus(companyStatus) ;
	}
	
	public ObjectList<Company> findByStatus(CompanyStatusEnum companyStatus,String[] orderBy) {
		return dao.findByStatus(companyStatus,orderBy) ;
	}
	
	public List<Company> filterByStatus(CompanyStatusEnum[] companyStatus) {
		return dao.filterByStatus(companyStatus);
	}
	
	/**
	 * @param isValid - false to include not valid (isValid = false) entries, true otherwise.
	 * @return
	 */
	public ObjectList<Company> findAll(Boolean isValid)
	{
		return dao.findAll(isValid);
	}
	
}
