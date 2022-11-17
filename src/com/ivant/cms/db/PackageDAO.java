package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.delegate.CategoryItemPackageDelegate;
import com.ivant.cms.entity.CategoryItemPackage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.IPackage;
import com.ivant.cms.entity.list.ObjectList;

public class PackageDAO
		extends AbstractBaseDAO<IPackage>
{
	private CategoryItemPackageDelegate catItemPackageDelegate = CategoryItemPackageDelegate.getInstance();
	
	public PackageDAO()
	{
		super();
	}
	
	public ObjectList<IPackage> findAllActiveItemsWithPaging(Company company, boolean hasParentGroup, int resultPerPage, int pageNumber, Order[] order)
	{
		String[] path = { "parentGroup" };
		int[] joinType = { CriteriaSpecification.INNER_JOIN };
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		if(hasParentGroup)
		{
			junc.add(Restrictions.eq("parentGroup.isValid", Boolean.TRUE));
		}
		
		return findAllByCriterion(pageNumber, resultPerPage, path, null, joinType, order, junc);
	}
	
	public ObjectList<IPackage> findAll(Company company, int resultPerPage, int pageNumber)
	{
		final Order[] order = { Order.asc("name") };
		
		final Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, junc);
	}
	
	public ObjectList<IPackage> findAll(Company company, String[] orderBy)
	{
		Order[] orders = { Order.asc("name") };
		try
		{
			orders = new Order[orderBy.length];
			for(int i = 0; i < orderBy.length; i++)
			{
				orders[i] = Order.asc(orderBy[i]);
			}
		}
		catch(Exception e)
		{
		}
		
		String[] path = { "parentGroup" };
		int[] joinType = { CriteriaSpecification.INNER_JOIN };
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
//		junc.add(Restrictions.eq("parentGroup.isValid", Boolean.TRUE));
		
//		return findAllByCriterion(path, null, joinType, orders, junc);
		
		return findAllByCriterion(-1, 200, null, null, null, orders, junc);
	}
	
	public IPackage findSKU(Company company, String sku){
		final Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("sku", sku));
		
		List<IPackage> packages = findAllByCriterion(null, null, null, null, null,	null, junc).getList();
		
		if(packages.size() > 0){
			return packages.get(0);
		}
		
		return null;
		
	}
	
	public boolean deleteItemMapping(long id)
	{
		List<CategoryItemPackage> catItems = catItemPackageDelegate.findAll(id).getList();
		boolean result = true;
		
		for(int i = 0; i < catItems.size(); i++)
		{
			if(!catItemPackageDelegate.delete(catItems.get(i)))
			{
				result = false;
			}
		}
		
		return result;
	}
}
