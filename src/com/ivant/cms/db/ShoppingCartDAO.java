package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Hibernate functionality class for shopping cart account.
 * 
 * @author Mark Kenneth M. Rañosa
 *
 */
public class ShoppingCartDAO extends AbstractBaseDAO<ShoppingCart> {

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
	public ShoppingCart find(Company company, Member member) {
		//get all member shopping cart account
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		//get shopping cart account list
		ObjectList<ShoppingCart> result = findAllByCriterion(null, null, null, null, junc);
		
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return shopping cart account
		return result.getList().get(0);
	}
}
