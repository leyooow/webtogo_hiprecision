package com.ivant.cms.db;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.list.ObjectList;

public class MemberFileDAO extends AbstractBaseDAO<MemberFile> {
	
	public MemberFileDAO() {
		super();
	}
	
	public ObjectList<MemberFile> findAll(Company company, Member member) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("member.id", member.getId()));
		
		return findAllByCriterion(-1, -1, null, null, null, 
				null, conj);
	}	
	public ObjectList<MemberFile> findAll(Member member) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("member.id", member.getId()));
		
		Order[] orders = {Order.desc("createdOn")};
		
		return findAllByCriterion(-1, -1, null, null, null, orders, conj);
	}	
	
	public ObjectList<MemberFile> findAll(Company company) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(-1, -1, null, null, null, orders, conj);
	}
	
	public ObjectList<MemberFile> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, null, junc);
		
	}
	
	public ObjectList<MemberFile> findAllWithPaging(Company company, int resultPerPage, int pageNumber, 
			Category category, Boolean withMember, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		if(category != null)
		{
			junc.add(Restrictions.eq("category", category));
		}
		
		if(withMember != null)
		{
			if(withMember)
			{
				junc.add(Restrictions.isNotNull("member"));
			}
				
			else
			{
				junc.add(Restrictions.isNull("member"));
			}
		}
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, null, junc);
		
	}
	
	public MemberFile findLastByApprovedDate(Company company, Member member){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		Order[] orders = {Order.desc("approvedDate")};
		ObjectList<MemberFile> result =  findAllByCriterion(-1, -1, null, null, null, orders, junc);
		if(result == null || result.getList().isEmpty()){
			return null;
		}
		return result.getList().get(0);
	}
	
}
