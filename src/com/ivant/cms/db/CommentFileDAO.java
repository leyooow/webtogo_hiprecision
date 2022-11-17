package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CommentFile;
import com.ivant.cms.entity.Company;

public class CommentFileDAO extends AbstractBaseDAO<CommentFile> {

	
	public CommentFileDAO() {
		super();
	}
	
	public CommentFile	find(Company company, Long id)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("id", id));
		return findAllByCriterion(null, null, null, null, junc).uniqueResult();
	}

	public List<CommentFile> findAll(Company company)
	{
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(null, null, null, null, conj).getList();
	}

	
}
