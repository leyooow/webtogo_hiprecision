package com.ivant.cms.delegate;

import java.util.Date;
import java.util.List;


import com.ivant.cms.db.MemberFileItemDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.list.ObjectList;

public class MemberFileItemDelegate extends AbstractBaseDelegate<MemberFileItems, MemberFileItemDAO>{
	private static MemberFileItemDelegate instance = new MemberFileItemDelegate();
	
	public static MemberFileItemDelegate getInstance() {
		return MemberFileItemDelegate.instance;
	}
	
	public MemberFileItemDelegate() {
		super(new MemberFileItemDAO());
	}
	
	public MemberFileItems findOtherMemberFileItem(MemberFileItems memberFileItem) {
		return dao.findOtherMemberFileItem(memberFileItem);
	}
	
	public List<MemberFileItems> findAll(Company company, Member member) {
		return dao.findAll(company, member).getList();
	}
	
	public List<MemberFileItems> findAllByStatus(Company company, String status, String[] fileType) {
		return dao.findAllByStatus(company, status, fileType);
	}
	
	public List<MemberFileItems> findAll(Company company) {

		return dao.findAll(company).getList();
	}
	
	//findMemberFileItem
	public MemberFileItems findMemberFileItem(Company company, Long id) {
		return dao.findMemberFileItem(company , id);
	}
	
	public MemberFileItems findMemberFileItemByCreatedOn111(Long id) {
		return dao.findMemberFileItem(null , id);
	}	
	
	public List<MemberFileItems> findByCompany(Company company) {
		return dao.findByCompany(company).getList();
	}
	
	public ObjectList<MemberFileItems> findByDate(Company company, Date fromDate, Date toDate) {
		return dao.findByDate(company, fromDate, toDate);
	}
	
	public MemberFileItems findByInvoice(Company company, String invoiceNumber) {
		return dao.findByInvoice(company, invoiceNumber);
	}

	public Integer findMaxMemberFileItem(Company company) {
		return dao.findMaxMemberFileItem(company);
	}
}
