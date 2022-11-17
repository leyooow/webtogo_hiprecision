package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OSSShippingLocation;
import com.ivant.cms.entity.OSSShippingRate;
import com.ivant.cms.entity.list.ObjectList;

public class OSSShippingRateDAO extends AbstractBaseDAO<OSSShippingRate> {
	public OSSShippingRateDAO() {
		super();
	}
	
	public List<OSSShippingRate> findAll(Company company, OSSShippingLocation location, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("ossShippingLocation", location));
		return findAllByCriterion(null, null, null, orders, junc).getList();
	}
}
