package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberShippingInfo;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Hibernate functionality class for member shipping information.
 * 
 * @author Mark Kenneth M. Rañosa
 *
 */
public class MemberShippingInfoDAO extends AbstractBaseDAO<MemberShippingInfo> {
	
	/**
	 * Returns the {@link MemberShippingInfo} item based on the specified parameters.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param member
	 * 			- company client/buyer
	 * 
	 * @return - the {@link MemberShippingInfo} item based on the specified parameters
	 */
	public MemberShippingInfo find(Company company, Member member) {
		//get all member shipping information
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		//get shipping info list
		ObjectList<MemberShippingInfo> result = findAllByCriterion(null, null, null, null, junc);
		
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return shipping info
		return result.getList().get(0);
	}
}
