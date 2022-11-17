package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.WebtogoExcludedEmails;
import com.ivant.cms.entity.list.ObjectList;

public class WebtogoExcludedEmailsDAO extends AbstractBaseDAO<WebtogoExcludedEmails> {
	
	public WebtogoExcludedEmails find(String email) {
		WebtogoExcludedEmails brand = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("email", email));
		return findAllByCriterion(null, null, null, null, junc).getList().get(0);
		
	}
	
	public ObjectList<WebtogoExcludedEmails> findAllByIdAndGroupName(Company company, String group) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("groupName", group));
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<WebtogoExcludedEmails> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<WebtogoExcludedEmails> findAll(Company company, boolean showAll, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<WebtogoExcludedEmails> findAll(Company company, Group group, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("group", group));
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
}
