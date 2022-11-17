package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OSSShippingLocation;
import com.ivant.cms.entity.list.ObjectList;

public class OSSShippingLocationDAO extends AbstractBaseDAO<OSSShippingLocation> {
	public OSSShippingLocationDAO() {
		super();
	}
	
	public List<OSSShippingLocation> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));

		String[] path = {"ossShippingArea"};
		Order[] orders = {Order.asc("ossShippingArea.areaName"), Order.asc("locationName")};
		int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		
		return findAllByCriterion(path, null, joinType, orders, junc).getList();
	}
	
	public OSSShippingLocation find(Company company, String location) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("locationName", location));
			
		ObjectList<OSSShippingLocation> locations =  findAllByCriterion(null, null, null, null, conj);
		if(locations.getSize() > 0) {
			return locations.getList().get(0);
		}
		
		return null;
	}
}
