package com.ivant.cms.delegate;

import java.math.BigInteger;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.CompareDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Compare;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.list.ObjectList;

public class CompareDelegate extends AbstractBaseDelegate<Compare, CompareDAO> {

	private volatile static CompareDelegate instance;
	protected CompareDelegate(CompareDAO dao) {
		super(new CompareDAO());
	}
	
	public static CompareDelegate getInstance() {
		if(instance == null){
			synchronized (CompareDelegate.class) {
				if(instance == null) {
					instance = new CompareDelegate(new CompareDAO());
				}
			}
		}
		return instance;
	}
	
	public ObjectList<Compare> findAll(Company company, Member member) {
		return dao.findAll(company, member);
	}
	
	public Compare find(Company company, Member member, CategoryItem categoryItem) {
		return dao.find(company, member, categoryItem);
	}
	
	public ObjectList<Compare> findAllCompareWithPaging(Company company, int resultsPerPage, int pageNumber, Order...orders) {
		return dao.findAllCompareWithPaging(company, resultsPerPage, pageNumber, orders );
	}
	
	public ObjectList<Compare> findAllCompareByMemberWithPaging(Company company, Member member, int resultsPerPage, int pageNumber, Order...orders){
		return dao.findAllCompareByMemberWithPaging(company, member, resultsPerPage, pageNumber, orders);
	}
	
	public BigInteger findCompareCount(Company company) {
		return dao.findCompareCount(company);
	}
	
	public BigInteger findCompareCountByMember(Company company, Member member) {
		return dao.findCompareCountByMember(company, member);
	}
	
}
