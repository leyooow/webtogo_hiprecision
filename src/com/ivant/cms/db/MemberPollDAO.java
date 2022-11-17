package com.ivant.cms.db;

import java.math.BigInteger;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberPoll;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;

public class MemberPollDAO extends AbstractBaseDAO<MemberPoll> {
	
	public MemberPoll find(Company company, Member member, String pollType, String remarks, ItemComment itemComment){
		
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.eq("itemComment", itemComment));
		conj.add(Restrictions.eq("remarks", remarks));
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] { Order.asc("createdOn") }, conj);
		
		if(memberPollList.getList().size() == 0){
			return null;
		}
		else{
			
			return memberPollList.getList().get(0);
		}
	}
	
	public MemberPoll find(Company company, Member member, String pollType, String remarks, CategoryItem item) {
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.eq("remarks", remarks));
		conj.add(Restrictions.eq("categoryItem", item));
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] {Order.asc("createdOn")}, conj);
		if(memberPollList.getList().size() == 0){
			return null;
		}
		else{
			return memberPollList.getList().get(0);
		}
	}
	
	public MemberPoll find(Company company, Member member, String pollType, String remarks, SinglePage singlePage) {
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.eq("remarks", remarks));
		conj.add(Restrictions.eq("singlePage", singlePage));
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] {Order.asc("createdOn")}, conj);
		if(memberPollList.getList().size() == 0){
			return null;
		}
		else{
			return memberPollList.getList().get(0);
		}
	}
	
	public ObjectList<MemberPoll> findAll(Company company, String pollType, String remarks, SinglePage singlePage) {
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company",  company));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.eq("remarks", remarks));
		conj.add(Restrictions.eq("singlePage", singlePage));
		return findAllByCriterion(-1, -1, null, null, null, new Order[] {Order.asc("createdOn")}, conj);
		
	}
	
	public Integer findCount(Company company, String pollType, String remarks, ItemComment itemComment)
	{
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.eq("remarks", remarks));
		conj.add(Restrictions.eq("itemComment", itemComment));
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] { Order.asc("createdOn") }, conj);
		
		return memberPollList.getList().size();
	}
	
	public Integer findCount(Company company, String pollType, String remarks, CategoryItem item)
	{
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.eq("remarks", remarks));
		conj.add(Restrictions.eq("categoryItem", item));
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] {Order.asc("createdOn")}, conj);
		return memberPollList.getList().size();
	}
	
	public Integer findCount(Company company, String pollType, String remarks, SinglePage singlePage)
	{
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.eq("remarks", remarks));
		conj.add(Restrictions.eq("singlePage", singlePage));
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] {Order.asc("createdOn")}, conj);
		return memberPollList.getList().size();
	}
	
	public ObjectList<MemberPoll> find(Member member, String pollType)
	{
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("pollType", pollType));
		
		return findAllByCriterion(-1, -1, null, null, null,
				new Order[] { Order.asc("createdOn") }, conj);
	}
	
	public ObjectList<MemberPoll> findByPollType(String pollType)
	{
		final Conjunction conj = Restrictions.conjunction();
		
		/*conj.add(Restrictions.eq("member", member));*/
		conj.add(Restrictions.eq("pollType", pollType));
		
		return findAllByCriterion(-1, -1, null, null, null,
				new Order[] { Order.asc("createdOn") }, conj);
		
	}
	
	public MemberPoll find(Member member, CategoryItem categoryItem, String pollType) 
	{
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("categoryItem", categoryItem));
		conj.add(Restrictions.eq("pollType", pollType));
		
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] { Order.asc("createdOn") }, conj);
		 if(memberPollList.getList().size() == 0)
		 {
			 return null;
		 }
		 else
		 {
			 return memberPollList.getList().get(0);
		 }
		 
		 /*
		  * getTotalItems(conj); // - this value is an integer
		  */
	}
	
	public MemberPoll findByCategoryItem(CategoryItem categoryItem) 
	{
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("categoryItem", categoryItem));
		
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] { Order.asc("createdOn") }, conj);
		 if(memberPollList.getList().size() == 0)
		 {
			 return null;
		 }
		 else
		 {
			 return memberPollList.getList().get(0);
		 }
		 
		 /*
		  * getTotalItems(conj); // - this value is an integer
		  */
	}
	
	public ObjectList<MemberPoll> findSupporter(CategoryItem categoryItem, String pollType)
	{
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("categoryItem", categoryItem));
		conj.add(Restrictions.eq("pollType", pollType));
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] { Order.asc("createdOn") }, conj);
		 if(memberPollList.getList().size() == 0)
		 {
			 return null;
		 }
		 else
		 {
			 return memberPollList;
		 }
	}
	
	public int findSupportCount(CategoryItem categoryItem, String pollType) 
	{
		final Conjunction conj = Restrictions.conjunction();
		
		//conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("categoryItem", categoryItem));
		conj.add(Restrictions.eq("pollType", pollType));
		
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] { Order.asc("createdOn") }, conj);
		return memberPollList.getList().size();
	}
	
	public int findVoteCount(CategoryItem categoryItem, String pollType, String remarks) 
	{
		final Conjunction conj = Restrictions.conjunction();
		
		//conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("categoryItem", categoryItem));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.eq("remarks", remarks));
		
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] { Order.asc("createdOn") }, conj);
		return memberPollList.getList().size();
	}
	
	public int findRemarksIndexVoteCount(CategoryItem categoryItem, String pollType, String remarks) 
	{
		final Conjunction conj = Restrictions.conjunction();
		
		//conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("categoryItem", categoryItem));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.ilike("remarks", remarks, MatchMode.ANYWHERE));
		
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] { Order.asc("createdOn") }, conj);
		return memberPollList.getList().size();
	}
	
	public Integer findBumpPollCount(ItemComment itemComment) 
	{
		final Conjunction conj = Restrictions.conjunction();
		
		//conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("itemComment", itemComment));
		
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] { Order.asc("createdOn") }, conj);
		return memberPollList.getList().size();
		 
		 /*
		  * getTotalItems(conj); // - this value is an integer
		  */
		  
	}
	
	public MemberPoll find(Member member, ItemComment itemComment) 
	{
		final Conjunction conj = Restrictions.conjunction();
		
		//conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("itemComment", itemComment));
		
		ObjectList<MemberPoll> memberPollList = findAllByCriterion(-1, -1, null, null, null, new Order[] { Order.asc("createdOn") }, conj);
		 if(memberPollList.getList().size() == 0)
		 {
			 return null;
		 }
		 else
		 {
			 return memberPollList.getList().get(0);
		 }
		 
		 /*
		  * getTotalItems(conj); // - this value is an integer
		  */
	}
	
	public ObjectList<MemberPoll> findAccount(Member member, String[] pollType)
	{
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("pollType", pollType));
		
		return findAllByCriterion(-1, -1, null, null, null,
				new Order[] { Order.asc("createdOn") }, conj);
	}
	
	public ObjectList<MemberPoll> findCommentPoll(ItemComment itemComment)
	{
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("itemComment", itemComment));
		return findAllByCriterion(-1, -1, null, null, null, new Order[]{Order.asc("createdOn")},conj);
	}
	
	public ObjectList<MemberPoll> findAllSingleByPollType(Company company, Member member, String pollType){
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.isNotNull("singlePage"));
		return findAllByCriterion(-1, -1, null, null, null, new Order[]{Order.asc("createdOn")}, conj);
	}
	
	public ObjectList<MemberPoll> findAllCategoryItemByPollType(Company company, Member member, String pollType) {
		final Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("pollType", pollType));
		conj.add(Restrictions.isNotNull("categoryItem"));
		return findAllByCriterion(-1, -1, null, null, null, new Order[]{Order.asc("createdOn")}, conj);
	}
	
	public Integer findSinglePagePollCount(Company company, SinglePage singlePage, String pollType) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(id) FROM members_polls WHERE company_id = :currentCompanyID and singlePage_id = :currentPageID and polly_type = :currentPollType  and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		//q.setLong("currentMemberID", member.getId());
		q.setLong("currentPageID", singlePage.getId());
		q.setString("currentPollType", pollType);
		try{
			return ((BigInteger) q.uniqueResult()).intValue();
		}catch(Exception e){
			return 0;
		}
		
		
	}
}
