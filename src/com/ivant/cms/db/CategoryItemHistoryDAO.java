package com.ivant.cms.db;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderStatusHistory;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemHistory;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
/**
 * 
 * @author Anjerico D. Gutierrez
 * @since October 12, 2015
 */
public class CategoryItemHistoryDAO extends AbstractBaseDAO<CategoryItemHistory> {
	public ObjectList<CategoryItemHistory> findByCategoryItem(Company company, CategoryItem categoryItem){
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company",company));
		conj.add(Restrictions.eq("categoryItem", categoryItem));
		return findAllByCriterion(-1, -1, null, null, null, new Order[]{Order.desc("createdOn")},conj);
	}
	
	public ObjectList<CategoryItemHistory> findByUser(Company company, User user) {
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company",company));
		conj.add(Restrictions.eq("user", user));
		return findAllByCriterion(-1, -1, null, null, null, new Order[]{Order.desc("createdOn")},conj);
	}
	
}
