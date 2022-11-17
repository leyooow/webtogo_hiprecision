package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.PackageDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.IPackage;
import com.ivant.cms.entity.list.ObjectList;

public class PackageDelegate
		extends AbstractBaseDelegate<IPackage, PackageDAO>
{
	private static PackageDelegate instance = new PackageDelegate();
	
	public static PackageDelegate getInstance() {
		return instance;
	}
	
	public PackageDelegate() {
		super(new PackageDAO());
	}
	
	public ObjectList<IPackage> findAll(Company company) {
		return dao.findAll(company, null);
	}
	
	public ObjectList<IPackage> findAll(Company company, int resultPerPage, int pageNumber)
	{
		return dao.findAll(company, resultPerPage, pageNumber);
	}
	
	public ObjectList<IPackage> findAllActiveItemsWithPaging(Company company, int resultPerPage, int pageNumber, Order... order) {
		return dao.findAllActiveItemsWithPaging(company, true, resultPerPage, pageNumber, order);
	}
	
	public IPackage findSKU(Company company, String sku){
		return dao.findSKU(company, sku);
	}
	
	public boolean deletePackage(IPackage obj){
		boolean result = dao.deleteItemMapping(obj.getId());
		return result && delete(obj);
	}
}
