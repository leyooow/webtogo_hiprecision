package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.PreOrder;
import com.ivant.cms.entity.PreOrderItem;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Hibernate functionality class for preorder.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class PreOrderItemDAO extends AbstractBaseDAO<PreOrderItem> {

	/**
	 * Returns the {@link ShoppingCart} item based on the specified parameters.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param member
	 * 			- company client/buyer
	 * 
	 * @return - the {@link ShoppingCart} item based on the specified parameters
	 */
	public PreOrderItem find(PreOrder preOrder, CategoryItem categoryItem) {
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("preOrder", preOrder));
		junc.add(Restrictions.eq("categoryItem", categoryItem));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		ObjectList<PreOrderItem> result = findAllByCriterion(null, null, null, null, junc);
		
		if(null == result || result.getList().isEmpty())
			return null;
		
		return result.getList().get(0);
	}
	
	public List<PreOrderItem> findAll(Company company, PreOrder preOrder) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("preOrder", preOrder));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		ObjectList<PreOrderItem> result = findAllByCriterion(null, null, null, null, junc);
		
		if(null == result || result.getList().isEmpty())
			return null;
		
		return result.getList();
	}	
}
