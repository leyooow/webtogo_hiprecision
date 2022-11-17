package com.ivant.cms.delegate;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.db.PortalActivityLogDAO;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.PortalActivityLog;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;

public class PortalActivityLogDelegate extends AbstractBaseDelegate<PortalActivityLog, PortalActivityLogDAO>{
	
	private final static PortalActivityLogDelegate instance = new PortalActivityLogDelegate();
	
	public static PortalActivityLogDelegate getInstance() {
		return instance;
	}

	private PortalActivityLogDelegate() {
		super(new PortalActivityLogDAO());
	}
	
	public ObjectList<PortalActivityLog> findAll(Company company) {
		return dao.findAll(company);
	}
	
	public ObjectList<PortalActivityLog> findAll(Company company,int pageNumber, int maxResult) {
		return dao.getAllLogs(company,pageNumber,maxResult);
	}
	
	public ObjectList<PortalActivityLog> findAll(User user,int pageNumber, int maxResult) {
		return dao.getAllLogs(user,pageNumber,maxResult);
	}
	
	public ObjectList<PortalActivityLog> findAll(User user,int pageNumber, int maxResult, String startDate, String endDate) {
		return dao.getAllLogs(user,pageNumber,maxResult, startDate, endDate);
	}
	
	public ObjectList<PortalActivityLog> findAllByMember(Company company, Member member)
	{
		return dao.findAllByMember(company, member);
	}
	
	public ObjectList<PortalActivityLog> findAllByMemberAndRemarks(Company company, Member member, String remarksKeyword)
	{
		return dao.findAllByMemberAndRemarks(company, member, remarksKeyword);
	}

	public ObjectList<PortalActivityLog> findAllByRemarks(Company company, Object remarksKeyword) {
		// TODO Auto-generated method stub
		return dao.findAllByRemarks(company, remarksKeyword);
	}
	
	public List<PortalActivityLog> findByDate(Company company, Member member, Date date){
		return dao.findByDate(company, member, date);
	}
	
	public List<PortalActivityLog> findAllToday(Company company, Member member){
		return dao.findByDate(company, member, new Date());
	}
	
	public ObjectList<PortalActivityLog> findLogsByDate(Company company, Date fromDate, Date toDate) {
		return dao.findLogsByDate(company, fromDate, toDate);
	}
}
