package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.BatchUpdateExcelFile;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class BatchUpdateExcelFileDAO extends AbstractBaseDAO<BatchUpdateExcelFile> {

	public boolean isInTheDatabase(Company company, Group group, String absolutePath) {
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("group", group));
		junc.add(Restrictions.eq("fileLocation", absolutePath));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		int size = findAllByCriterion(null, null, null, null, junc).getSize();
		
		return (size>0) ? true : false;
	}

	public ObjectList<BatchUpdateExcelFile> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("isValid", true));
		return findAllByCriterion(null, null, null, null, junc);
	}

	public ObjectList<BatchUpdateExcelFile> findAll(Company company, Group group) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("isValid", true));
		junc.add(Restrictions.eq("group",group));
		
		Order[] orders = {Order.desc("updatedOn")};
		
		return findAllByCriterion(null, null, null, orders, junc);
	}

	public ObjectList<BatchUpdateExcelFile> findByPath(Company company, Group group, String absolutePath) {
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("group", group));
		junc.add(Restrictions.eq("fileLocation", absolutePath));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	
}
