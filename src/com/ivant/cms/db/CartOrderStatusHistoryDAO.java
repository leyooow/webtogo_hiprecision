package com.ivant.cms.db;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderStatusHistory;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
/**
 * 
 * @author Anjerico D. Gutierrez
 * @since July 27, 2015
 */
public class CartOrderStatusHistoryDAO extends AbstractBaseDAO<CartOrderStatusHistory> {
	public ObjectList<CartOrderStatusHistory> findByOrder(Company company, CartOrder cartOrder){
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company",company));
		conj.add(Restrictions.eq("cartOrder", cartOrder));
		return findAllByCriterion(-1, -1, null, null, null, new Order[]{Order.desc("createdOn")},conj);
	}
	
	public ObjectList<CartOrderStatusHistory> findByUser(Company company, User user) {
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company",company));
		conj.add(Restrictions.eq("user", user));
		return findAllByCriterion(-1, -1, null, null, null, new Order[]{Order.desc("createdOn")},conj);
	}
	
}
