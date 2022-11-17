package com.ivant.cms.delegate;

import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.CategoryItemPackageDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemPackage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.IPackage;
import com.ivant.cms.entity.list.ObjectList;

/**
 * @author Kevin Roy K. Chua
 * @version 1.0, Feb 24, 2010
 * @since 1.0, Feb 24, 2010
 */
public class CategoryItemPackageDelegate
		extends AbstractBaseDelegate<CategoryItemPackage, CategoryItemPackageDAO>
{
	private static CategoryItemPackageDelegate instance = new CategoryItemPackageDelegate();
	
	public static CategoryItemPackageDelegate getInstance()
	{
		return instance;
	}
	
	public CategoryItemPackageDelegate()
	{
		super(new CategoryItemPackageDAO());
	}
	
	public ObjectList<CategoryItemPackage> findAllWithPaging(Company company, Long categoryItemId, int resultPerPage, int pageNumber, Order... orders)
	{
		return dao.findAllWithPaging(company, Arrays.asList(categoryItemId), resultPerPage, pageNumber, orders);
	}
	
	public ObjectList<CategoryItemPackage> findAllWithPaging(Company company, List<Long> categoryItemIds, int resultPerPage, int pageNumber, Order... orders)
	{
		return dao.findAllWithPaging(company, categoryItemIds, resultPerPage, pageNumber, orders);
	}
	
	public ObjectList<CategoryItemPackage> findAll(long packageId)
	{
		return dao.findAllByCriterion(packageId);
	}
	
	public CategoryItemPackage findByCategoryItem(Company company, CategoryItem catItem, IPackage ipackage){
		return dao.findByCategoryItem(company, catItem, ipackage);
	}
}