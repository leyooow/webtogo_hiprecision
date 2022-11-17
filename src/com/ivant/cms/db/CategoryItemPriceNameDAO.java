package com.ivant.cms.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItemPriceName;
import com.ivant.cms.entity.Group;

public class CategoryItemPriceNameDAO extends AbstractBaseDAO<CategoryItemPriceName>{

	public List<CategoryItemPriceName> findByGroup(Group group)
	{
		final String paths[] = null;
		final String aliases[] = null;
		final int joinTypes[] = {Criteria.LEFT_JOIN};
		final Order orders[] = null;
		final Junction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("group", group));
		
		return findAllByCriterion(paths, aliases, joinTypes, orders, conj).getList();
	}

	public CategoryItemPriceName findByName(String name, Group group)
	{
		final String paths[] = null;
		final String aliases[] = null;
		final int joinTypes[] = {Criteria.LEFT_JOIN};
		final Order orders[] = null;
		final Junction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("group", group));
		conj.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		
		return findAllByCriterion(paths, aliases, joinTypes, orders, conj).uniqueResult();
		
	}
}
