package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OSSShippingArea;
import com.ivant.cms.entity.list.ObjectList;

public class OSSShippingAreaDAO extends AbstractBaseDAO<OSSShippingArea> {
	public OSSShippingAreaDAO() {
		super();
	}
	
	public List<OSSShippingArea> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		Order order[] = {Order.asc("areaName")};
		return findAllByCriterion(null, null, null, order, junc).getList();
	}

}
