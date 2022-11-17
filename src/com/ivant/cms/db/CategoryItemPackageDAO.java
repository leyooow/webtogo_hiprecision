package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemPackage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.IPackage;
import com.ivant.cms.entity.list.ObjectList;


/**
 * @author Kevin Roy K. Chua
 *
 * @version 1.0, Feb 24, 2010
 * @since 1.0, Feb 24, 2010
 *
 */
public class CategoryItemPackageDAO
		extends AbstractBaseDAO<CategoryItemPackage>
{
	public ObjectList<CategoryItemPackage> findAllWithPaging(Company company, List<Long> categoryItemIds, 
			int resultPerPage, int pageNumber, Order...orders) 
	{
		Junction junc = Restrictions.conjunction();
		String [] path = {"iPackage"};
		int [] joinType = {CriteriaSpecification.INNER_JOIN};
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.in("categoryItem.id", categoryItemIds));
		
		return findAllByCriterion(pageNumber, resultPerPage, path, null, joinType, orders, junc);
	}

	public ObjectList<CategoryItemPackage> findAllByCriterion(long packageId) {
		Junction junc = Restrictions.conjunction();
		
		String[] path = {"iPackage","categoryItem"};
		int[] joinType = {CriteriaSpecification.INNER_JOIN,CriteriaSpecification.LEFT_JOIN};
		
		junc.add(Restrictions.eq("iPackage.id", packageId));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		Order[] orders = {Order.asc("categoryItem.name")};
		return this.findAllByCriterion(path, null, joinType, orders, junc);
	}
	
	public CategoryItemPackage findByCategoryItem(Company company, CategoryItem catItem, IPackage ipackage){
		Junction junc = Restrictions.conjunction();
		
		
		junc.add(Restrictions.eq("iPackage", ipackage));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("categoryItem", catItem));
		
		List<CategoryItemPackage> itemPackages = this.findAllByCriterion(null, null, null, null, junc).getList();
		
		if(itemPackages.size() > 0){
			return itemPackages.get(0);
		}else{
			
			return null;
		}

	}
}
