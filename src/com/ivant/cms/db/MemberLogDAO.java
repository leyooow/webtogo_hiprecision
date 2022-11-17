package com.ivant.cms.db;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;


public class MemberLogDAO extends AbstractBaseDAO<MemberLog>{

	public MemberLogDAO() {
		super();
	}
	
	public ObjectList<MemberLog> getLogsLastWeek(Company company){
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("company", company));
		DateTime dateToday = new DateTime();
		junc.add(Restrictions.ge("createdOn", dateToday.minusDays(7).toDate()));
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<MemberLog> getAllLogs(Company company, int pageNumber, int maxResult){
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("company", company));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(pageNumber, maxResult, null, null, null, orders, junc);
	}
	
	public ObjectList<MemberLog> getAllLogs(User user, int pageNumber, int maxResult){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("updatedByUser", user));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(pageNumber, maxResult, null, null, null, orders, junc);
	}
	
	@SuppressWarnings("deprecation")
	public ObjectList<MemberLog> getAllLogs(User user, int pageNumber, int maxResult, String startDate, String endDate){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("updatedByUser", user));
		
		String[] start = startDate.split("-");
		String[] end = endDate.split("-");
		
		Calendar calStart = Calendar.getInstance(); 
		calStart.set(
				Integer.parseInt(start[0]), 
				Integer.parseInt(start[1]), 
				Integer.parseInt(start[2])
			);
		
		Calendar calEnd = Calendar.getInstance(); 
		calEnd.set(
				Integer.parseInt(end[0]), 
				Integer.parseInt(end[1]), 
				Integer.parseInt(end[2])
			);
		
		Date dateS = new DateTime().toDate();
		Date dateE = new DateTime().toDate();
		dateS.setYear(Integer.parseInt(start[0])-1900);
		dateS.setMonth(Integer.parseInt(start[1])-1);
		dateS.setDate(Integer.parseInt(start[2]));
		dateS.setHours(0);
		dateS.setMinutes(0);
		dateS.setSeconds(0);
		
		dateE.setYear(Integer.parseInt(end[0])-1900);
		dateE.setMonth(Integer.parseInt(end[1])-1);
		dateE.setDate(Integer.parseInt(end[2]));
		dateE.setHours(23);
		dateE.setMinutes(59);
		dateE.setSeconds(59);
		
		//System.out.println(dateS);
		//System.out.println(dateE);
		
		junc.add(Restrictions.ge("createdOn", dateS));
		junc.add(Restrictions.le("createdOn", dateE));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(pageNumber, maxResult, null, null, null, orders, junc);
	}

	public ObjectList<MemberLog> findAllByMember(Company company, Member member)
	{
		final Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("member", member));
		
		return findAllByCriterion(null, null, null, null, conj);
	}
	
	public ObjectList<MemberLog> findAllByMemberAndRemarks(Company company, Member member, String remarksKeyword)
	{
		final Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.like("remarks", remarksKeyword, MatchMode.ANYWHERE));
		
		return findAllByCriterion(null, null, null, null, conj);
	}
	
	public ObjectList<MemberLog> findAllByRemarks(Company company, Object remarksKeyword)
	{
		final Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		if(company.getName().equalsIgnoreCase("mundipharma2")) {
			conj.add(Restrictions.in("remarks", (Object[]) remarksKeyword));
		}
		
		return findAllByCriterion(null, null, null, null, conj);
	}

	public List<MemberLog> findByDate(Company company, Member member, Date date){
		final Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		if(member != null){
			conj.add(Restrictions.eq("member", member));
		}
		if(date != null){
			conj.add(Restrictions.ge("createdOn", (new DateTime().withTime(0, 0, 0, 0)).toDate()  ));
			conj.add(Restrictions.le("createdOn", (new DateTime().withTime(23, 59, 59, 999)).toDate() ));
		}
		return findAllByCriterion(null, null, null, null, conj).getList();
	}
}
