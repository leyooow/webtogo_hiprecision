package com.ivant.cms.delegate;

import java.util.Date;
import java.util.List;

import com.ivant.cms.db.MemberLogDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;

public class MemberLogDelegate extends AbstractBaseDelegate<MemberLog, MemberLogDAO>{
	
	private final static MemberLogDelegate instance = new MemberLogDelegate();
	
	public static MemberLogDelegate getInstance() {
		return instance;
	}

	private MemberLogDelegate() {
		super(new MemberLogDAO());
	}
	
	public ObjectList<MemberLog> findLastWeek(Company company) {
		return dao.getLogsLastWeek(company);
	}
	
	public ObjectList<MemberLog> findAll(Company company,int pageNumber, int maxResult) {
		return dao.getAllLogs(company,pageNumber,maxResult);
	}
	
	public ObjectList<MemberLog> findAll(User user,int pageNumber, int maxResult) {
		return dao.getAllLogs(user,pageNumber,maxResult);
	}
	
	public ObjectList<MemberLog> findAll(User user,int pageNumber, int maxResult, String startDate, String endDate) {
		return dao.getAllLogs(user,pageNumber,maxResult, startDate, endDate);
	}
	
	public ObjectList<MemberLog> findAllByMember(Company company, Member member)
	{
		return dao.findAllByMember(company, member);
	}
	
	public ObjectList<MemberLog> findAllByMemberAndRemarks(Company company, Member member, String remarksKeyword)
	{
		return dao.findAllByMemberAndRemarks(company, member, remarksKeyword);
	}

	public ObjectList<MemberLog> findAllByRemarks(Company company, Object remarksKeyword) {
		// TODO Auto-generated method stub
		return dao.findAllByRemarks(company, remarksKeyword);
	}
	
	public List<MemberLog> findByDate(Company company, Member member, Date date){
		return dao.findByDate(company, member, date);
	}
	
	public List<MemberLog> findAllToday(Company company, Member member){
		return dao.findByDate(company, member, new Date());
	}
}
