package com.ivant.cms.db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;

public class MultiPageFileDAO extends AbstractBaseDAO<MultiPageFile> {
	
	public MultiPageFileDAO() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<MultiPageFile> findAllSinglePageFiles(Company company, MultiPage multiPage, SinglePage singlePage) {
		if(company == null || multiPage == null || singlePage == null){
			return new ArrayList<MultiPageFile>();
		}
		
		Criteria criteria = getSession().createCriteria(MultiPageFile.class);
		criteria.add(Restrictions.eq("singlepage.id", singlePage.getId()));
		criteria.add(Restrictions.eq("multipage.id", multiPage.getId()));
		criteria.add(Restrictions.eq("company.id", company.getId()));
		criteria.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		return criteria.list();
	}
	
	public Integer countAllSinglePageFiles(Company company, MultiPage multiPage, SinglePage singlePage) {
		if(company == null || multiPage == null || singlePage == null){
			return 0;
		}
		
		Criteria criteria = getSession().createCriteria(MultiPageFile.class);
		criteria.add(Restrictions.eq("singlepage.id", singlePage.getId()));
		criteria.add(Restrictions.eq("multipage.id", multiPage.getId()));
		criteria.add(Restrictions.eq("company.id", company.getId()));
		criteria.add(Restrictions.eq("isValid", Boolean.TRUE));
		criteria.setProjection(Projections.count("id"));
		
		return (Integer) criteria.uniqueResult();
	}
	
	public MultiPageFile find(Company company, Long id)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("id", id));
		
		return findAllByCriterion(null, null, null, null, junc).uniqueResult();
	}

	public ObjectList<MultiPageFile> findAllSinglePageFiles(Company company,
			MultiPage multiPage, SinglePage singlePage, Order... order) {
		// TODO Auto-generated method stub
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("singlepage.id", singlePage.getId()));
		junc.add(Restrictions.eq("multipage.id", multiPage.getId()));
		junc.add(Restrictions.eq("company.id", company.getId()));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		return findAllByCriterion(null, null, null, order, junc);
	}
}
