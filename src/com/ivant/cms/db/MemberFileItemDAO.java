package com.ivant.cms.db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.entity.list.ObjectList;

public class MemberFileItemDAO extends AbstractBaseDAO<MemberFileItems> {
	
	public MemberFileItemDAO() {
		super();
	}
	
	public ObjectList<MemberFileItems> findAll(Company company, Member member) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("member.id", member.getId()));
		
		return findAllByCriterion(-1, -1, null, null, null, 
				null, conj);
	}	
	
	public ObjectList<MemberFileItems> findAll(Company company) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		//TO FO NEXT
		conj.add(Restrictions.eq("company", company));
		return findAllByCriterion(-1, -1, null, null, null, 
				null, conj);
	}
	
	public List<MemberFileItems> findAllByStatus(Company company, String status, String[] fileType) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));	
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("memberFile.status", status));
		
		String paths[] = {"memberFile"};
		String aliases[] = {"memberFile"};
		int joinTypes[] = {CriteriaSpecification.INNER_JOIN};
		
		Junction disj = Restrictions.disjunction();
		for(int i=0; i<fileType.length; i++)
			disj.add(Restrictions.like("filename", fileType[i], MatchMode.END));
		
		conj.add(disj);
		
		return findAllByCriterion(-1, -1, paths, aliases, joinTypes, null, conj).getList();
	}
	
	public MemberFileItems findOtherMemberFileItem(MemberFileItems memberFileItem) {

		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.ne("id", memberFileItem.getId()));
		conj.add(Restrictions.eq("invoiceNumber", memberFileItem.getInvoiceNumber()));
		
		ObjectList<MemberFileItems> memberFileItemsList =  findAllByCriterion(null, null, null, null, conj);
		if(memberFileItemsList.getSize() > 0) {
			return memberFileItemsList.getList().get(0);
		}
		return null;
	}

	public MemberFileItems findMemberFileItem(Company company, Long id) {

		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("memberFile.id", id));
		
		
		//APC GC ITEMS
		if(company.getName().equalsIgnoreCase("apc")){
			try{
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd-MMM-yy");
				Date date = (Date) formatter.parse("01-July-12");
				conj.add(Restrictions.gt("createdOn", date));
			} catch (ParseException e) {
				System.out.println("Exception : " + e);
			}
			
		}
		
		ObjectList<MemberFileItems> memberFileItemsList =  findAllByCriterion(null, null, null, null, conj);
		if(memberFileItemsList.getSize() > 0) {
			return memberFileItemsList.getList().get(0);
		}
		return null;
	}
	
	public MemberFileItems findMemberFileItemByCreatedOn(Long id) {

		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("memberFile.id", id));
		
		Order[] orders = {Order.asc("created_on")};
		
		ObjectList<MemberFileItems> memberFileItemsList =  findAllByCriterion(null, null, null, orders, conj);
		if(memberFileItemsList.getSize() > 0) {
			return memberFileItemsList.getList().get(0);
		}
		return null;
	}	
	
	public MemberFileItems findByInvoice(Company company, String invoiceNumber) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("invoiceNumber", invoiceNumber));
		
		ObjectList<MemberFileItems> memberFileItemsList =  findAllByCriterion(null, null, null, null, conj);
		if(memberFileItemsList.getSize() > 0) {
			return memberFileItemsList.getList().get(0);
		}
		return null;
	}		
	
	public Integer findMaxMemberFileItem(Company company) {
		Criteria criteria = getSession().createCriteria(MemberFileItems.class);
		criteria.add(Restrictions.eq("company", company));
		criteria.add(Restrictions.eq("isValid", Boolean.TRUE));
		criteria.setProjection(Projections.max("invoiceNumber"));
		
		try
		{
			return Integer.parseInt(criteria.uniqueResult().toString());
		}
		catch(Exception e)
		{
			return 100000;
		}
	}	

	public ObjectList<MemberFileItems> findByCompany(Company company) {
		// TODO Auto-generated method stub
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		//TO FO NEXT
		if(company.getName().equalsIgnoreCase("apc"))
			conj.add(Restrictions.eq("company", company));			
		else
			conj.add(Restrictions.eq("company", company));
		
		Order[] order = {Order.asc("createdOn")};
		
		return findAllByCriterion(-1, -1, null, null, null, order, conj);
	}
	
	public ObjectList<MemberFileItems> findByDate(Company company, Date fromDate, Date toDate) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));	
		junc.add(Restrictions.ge("createdOn",fromDate));
		junc.add(Restrictions.le("createdOn",toDate));
				
		Order[] orders = {Order.desc("createdOn")};
		
		return findAllByCriterion(null, null, null, orders, junc);
	}
}
