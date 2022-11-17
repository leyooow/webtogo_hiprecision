package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.PreOrder;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Hibernate functionality class for preorder.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class PreOrderDAO extends AbstractBaseDAO<PreOrder> {

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
	public PreOrder find(Company company, Member member) {
		//get all member shopping cart account
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		//get shopping cart account list
		ObjectList<PreOrder> result = findAllByCriterion(null, null, null, null, junc);
		
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return shopping cart account
		return result.getList().get(0);
	}
}
