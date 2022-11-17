package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Referral;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.ReferralStatus;

public class ReferralDAO  extends AbstractBaseDAO<Referral>{

	public List<Referral> findAll(Company company) {
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		
		Order[] orders = {Order.desc("id")};
		
		return findAllByCriterion(null, null, null, orders, null,	null, junc).getList();
	}

	public List<Referral> findAllWithPaging(Company company,
			int resultPerPage, int pageNumber, String[] orderBy) {
		
		Order[] orders = {Order.desc("id")};

		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, junc).getList();
	
	}

	public List<Referral> findAllByIds(Long[] ids, Company company) {
		Junction junc = Restrictions.conjunction();
		Junction disJunc = Restrictions.disjunction();
		
		Order[] orders = {Order.asc("referredBy")};
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.isNotNull("referredBy"));
		
		for(Long id:ids){
			disJunc.add(Restrictions.eq("id", id));
		}
		
		if(ids!=null){
			junc.add(disJunc);
		}
		
		return findAllByCriterion(null, null, null, orders, null,	null, junc).getList();
	}

	public List<Referral> findAllReferrer(String firstName, String lastName,
			Company company) {
		
		String [] paths = { "referredBy"};
		String [] aliases = { "referredBy"};
		int [] joinTypes = { CriteriaSpecification.INNER_JOIN};
		//Order [] orders = { Order.desc("id") };
		
		Junction conj = Restrictions.conjunction();
		
		
		conj.add(Restrictions.eq("company", company));
		
		

		//System.out.println("FNAME in DAO IS : "+firstName);
		//System.out.println("LNAME in DAO IS : "+lastName);
		
		if(firstName == null){
			conj.add(Restrictions.ilike("referredBy.firstname", "", MatchMode.START));
		}else
			conj.add(Restrictions.like("referredBy.firstname", firstName, MatchMode.START));
			
		if(lastName == null){
			conj.add(Restrictions.ilike("referredBy.lastname", "", MatchMode.START));
		}else
			conj.add(Restrictions.like("referredBy.lastname", lastName, MatchMode.START));
		
		/*System.out.println("        ------>"+findAllByCriterion(paths, aliases, joinTypes, null, 
				conj).toString());*/
		
		//System.out.println(" ------->"+conj.toString());
		return findAllByCriterion(paths, aliases, joinTypes, null, 
				conj).getList();
		
	}

	public List<Referral> findAllReferrer(Member member, Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("referredBy", member));
		
		return findAllByCriterion(null, null, null, null, null, 
				null, junc).getList();
	}

	public List<Referral> findByStatus(Company company,
			ReferralStatus referralStatus, Member referrer) {
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		
		Order[] orders = {Order.desc("id")};
		
		
		if(referrer !=null)
			junc.add(Restrictions.eq("referredBy", referrer));
		
		if(referralStatus!=null){
			if(referralStatus.equals(ReferralStatus.REQUESTED)){
				orders = new Order[]{Order.desc("requestId")};
			}
			junc.add(Restrictions.eq("status", referralStatus));
		}
		
		return findAllByCriterion(null, null, null, orders, null, 
				null, junc).getList();
	}

	public List<Referral> findByStatusAndReward(Company company,
			ReferralStatus referralStatus, Member referrer, String reward) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		
		Order[] orders = {Order.desc("id")};
		
		if(referrer !=null)
			junc.add(Restrictions.eq("referredBy", referrer));
		
		if(referralStatus!=null){
			if(referralStatus.equals(ReferralStatus.REQUESTED)){
				orders = new Order[]{Order.desc("requestId")};
			}
			junc.add(Restrictions.eq("status", referralStatus));
		}
		
		if(reward!=null)
			junc.add(Restrictions.ilike("reward", reward , MatchMode.ANYWHERE));
		
		
		return findAllByCriterion(null, null, null, orders, null, 
				null, junc).getList();
	}

	public Long findLastRequestedId(Company company) {
		
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("company", company));
		
		Order[] orders = {Order.asc("requestId")};
		
		List<Referral> refList = findAllByCriterion(null, null, null, orders, null, 
				null, junc).getList();
		
		if(refList.size()!=0){
			if(refList.get(refList.size()-1).getRequestId() == null)
				return 0L;
			return refList.get(refList.size()-1).getRequestId();
		}
		
		return 0L;
	}

	public List<Referral> findAllRequestId(Long requestId, Company company) {
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("requestId", requestId));
		
		return findAllByCriterion(null, null, null, null, null, 
				null, junc).getList();
	}


}
