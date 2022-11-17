package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.BasePage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.PageImage;

public class PageImageDAO extends AbstractBaseDAO<PageImage> {
	
	public PageImageDAO() {
		super();
	}

	public List<PageImage> findAllSortedByTitle(BasePage page) {
		final String aliases[] = null;
		final int joinType[] = {CriteriaSpecification.LEFT_JOIN};
		final Junction junc = Restrictions.conjunction();
						junc.add(Restrictions.eq("isValid", Boolean.TRUE));
						junc.add(Restrictions.eq("page.id", page.getId()));
		
		return findAllByCriterion(aliases, aliases, joinType, new Order[] {Order.asc("title")}, junc).getList();
	}
	
	
	
}
