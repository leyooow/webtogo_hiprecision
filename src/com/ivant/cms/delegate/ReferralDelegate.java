package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.ReferralDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Referral;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.ReferralStatus;

public class ReferralDelegate extends AbstractBaseDelegate<Referral, ReferralDAO>{

	private static ReferralDelegate instance = new ReferralDelegate();
	
	public static ReferralDelegate getInstance() {
		return instance;
	}
	
	public ReferralDelegate() {
		super(new ReferralDAO());
	}
	
	
	public List<Referral> findAll(Company company) {
		return dao.findAll(company);
	}
	

	public List<Referral> findAllWithPaging(Company company,int resultPerPage, int pageNumber, String[] orderBy) {
		return dao.findAllWithPaging(company,resultPerPage, pageNumber, orderBy);
	}

	public List<Referral> findAllByIds(Long[] ids, Company company) {
		
		return dao.findAllByIds(ids,company);
	}

	public List<Referral> findAllReferrer(String firstName, String lastName,
			Company company) {
		return dao.findAllReferrer(firstName,lastName,company);
	}

	public List<Referral> findAllReferrer(Member member, Company company) {
		return dao.findAllReferrer(member,company);
	}

	public List<Referral> findByStatus(Company company,
			ReferralStatus referralStatus, Member referrer) {
		return dao.findByStatus(company,referralStatus,referrer);
	}

	public List<Referral> findByStatusAndReward(Company company,
			ReferralStatus referralStatus, Member referrer, String reward) {
		return dao.findByStatusAndReward(company,referralStatus,referrer,reward);
	}

	public Long findLastRequestedId(Company company) {
		return dao.findLastRequestedId(company);
		
	}

	public List<Referral> findAllRequestId(Long requestId, Company company) {
		
		return dao.findAllRequestId(requestId,company);
	}


}
