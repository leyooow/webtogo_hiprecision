package com.ivant.cms.delegate;

import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.MemberTypeDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.list.ObjectList;

public class MemberTypeDelegate 
	extends AbstractBaseDelegate<MemberType, MemberTypeDAO>{

	private static MemberTypeDelegate instance = new MemberTypeDelegate();
	
	public static MemberTypeDelegate getInstance() {
		return MemberTypeDelegate.instance;
	}
	
	public MemberTypeDelegate() {
		super(new MemberTypeDAO());
	}

	public ObjectList<MemberType> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, orders);
	}
	
	public List<MemberType> findAll(Company company) {
		return dao.findAll(company);
	}
}
