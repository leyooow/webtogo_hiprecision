package com.ivant.cms.delegate;

import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.MemberFileDAO;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;

public class MemberFileDelegate extends AbstractBaseDelegate<MemberFile, MemberFileDAO>{
	private static MemberFileDelegate instance = new MemberFileDelegate();
	public static MemberFileDelegate getInstance() {
		return MemberFileDelegate.instance;
	}
	public MemberFileDelegate() {
		super(new MemberFileDAO());
	}
	
	public List<MemberFile> findAll(Member member) {
		return dao.findAll(member).getList();
	}
	
	public List<MemberFile> findAll(Company company) {
		return dao.findAll(company).getList();
	}
	
	public List<MemberFile> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders){
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, orders).getList();
	}
	
	public List<MemberFile> findAllWithPaging(Company company, int resultPerPage, 
			int pageNumber, Category category, Boolean withMember, Order...orders) {
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, category, withMember, orders).getList();
	}
	
	public MemberFile findLastByApprovedDate(Company company, Member member) {
		return dao.findLastByApprovedDate(company, member);
	}
	
}
