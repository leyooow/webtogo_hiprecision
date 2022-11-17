package com.ivant.cms.db;

import java.math.BigInteger;
import java.util.Date;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberMessage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.MessageRemarks;
import com.ivant.cms.enums.MessageStatus;
import com.ivant.cms.enums.MessageType;

public class MemberMessageDAO extends AbstractBaseDAO<MemberMessage> {
	public MemberMessage find(Company company, Member member, String content, MessageType messageType){
		final Conjunction conj = Restrictions.conjunction();
		final Conjunction conj2 = Restrictions.conjunction();
		final Disjunction disjunc = Restrictions.disjunction();
		
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("senderMember", member));
		conj.add(Restrictions.eq("content", content));
		conj.add(Restrictions.eq("messageType", messageType));
		
		conj2.add(Restrictions.eq("company", company));
		conj2.add(Restrictions.eq("receiverMember", member));
		conj2.add(Restrictions.eq("content", content));
		conj2.add(Restrictions.eq("messageType", messageType));
		
		disjunc.add(Restrictions.or(conj, conj2));
		
		ObjectList<MemberMessage> memberMessageList = findAllByCriterion(-1, -1, null, null, null, new Order[] {Order.asc("createdOn")}, disjunc);
		if(memberMessageList.getList().size() == 0) {
			return null;
		}
		else{
			return memberMessageList.getList().get(0);
		}
	}
	
	public ObjectList<MemberMessage> findAll(int pageNumber, int resultPerPage, Company company, Member member, Order[] order){
		final Conjunction conj = Restrictions.conjunction();
		final Conjunction conj2 = Restrictions.conjunction();
		final Disjunction disjunc = Restrictions.disjunction();
		
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("senderMember", member));
		
		conj2.add(Restrictions.eq("company", company));
		conj2.add(Restrictions.eq("receiverMember", member));
		
		disjunc.add(Restrictions.or(conj, conj2));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, disjunc);
	}
	
	public ObjectList<MemberMessage> findAllBySenderAndReceiver(int pageNumber, int resultPerPage, Company company, Member sender,Member receiver, Order[] order){
		final Conjunction conj = Restrictions.conjunction();
		final Conjunction conj2 = Restrictions.conjunction();
		final Disjunction disjunc = Restrictions.disjunction();
		
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("senderMember", sender));
		conj.add(Restrictions.eq("receiverMember", receiver));
		
		conj2.add(Restrictions.eq("company", company));
		conj2.add(Restrictions.eq("receiverMember", sender));
		conj2.add(Restrictions.eq("senderMember", receiver));
		
		
		disjunc.add(Restrictions.or(conj, conj2));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, disjunc);
	}
	
	public ObjectList<MemberMessage> findAllByRemarks(int pageNumber, int resultPerPage, Company company, Member member, MessageRemarks remarks, Order[] order) {
		final Conjunction conj = Restrictions.conjunction();
		final Conjunction conj2 = Restrictions.conjunction();
		final Disjunction disjunc = Restrictions.disjunction();
		
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("senderMember", member));
		conj.add(Restrictions.eq("messageRemarks", remarks));
		
		conj2.add(Restrictions.eq("company", company));
		conj2.add(Restrictions.eq("receiverMember", member));
		conj2.add(Restrictions.eq("messageRemarks", remarks));
		
		disjunc.add(Restrictions.or(conj, conj2));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, disjunc);
	}
	
	public ObjectList<MemberMessage> findAllByStatus(int pageNumber, int resultPerPage, Company company, Member member, MessageStatus messageStatus, Order[] order) {
		final Conjunction conj = Restrictions.conjunction();
		final Conjunction conj2 = Restrictions.conjunction();
		
		final Disjunction disjunc = Restrictions.disjunction();
		
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("senderMember", member));
		conj.add(Restrictions.eq("senderMessageStatus", messageStatus));
		
		conj2.add(Restrictions.eq("company", company));
		conj2.add(Restrictions.eq("receiverMember", member));
		conj2.add(Restrictions.eq("receiverMessageStatus", messageStatus));
		
		disjunc.add(Restrictions.or(conj, conj2));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, disjunc);
	}
	
	public ObjectList<MemberMessage> findAllByStatusCMS(int pageNumber, int resultPerPage, Company company, Member member, MessageStatus messageStatus, Order[] order) {
		final Conjunction conj = Restrictions.conjunction();
		final Conjunction conj2 = Restrictions.conjunction();
		
		final Disjunction disjunc = Restrictions.disjunction();
		
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("senderMember", member));
		conj.add(Restrictions.eq("receiverMessageStatus", messageStatus));
		
		conj2.add(Restrictions.eq("company", company));
		conj2.add(Restrictions.eq("receiverMember", member));
		conj2.add(Restrictions.eq("senderMessageStatus", messageStatus));
		
		disjunc.add(Restrictions.or(conj, conj2));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, disjunc);
	}
	
	public ObjectList<MemberMessage> findAllByMemberAndContent(int pageNumber, int resultPerPage, Company company, Member member, String content, Order[] order){
		final Conjunction conj = Restrictions.conjunction();
		final Conjunction conj2 = Restrictions.conjunction();
		
		final Disjunction disjunc = Restrictions.disjunction();
		
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("senderMember", member));
		conj.add(Restrictions.eq("content", content));
		
		conj2.add(Restrictions.eq("company", company));
		conj2.add(Restrictions.eq("receiverMember", member));
		conj2.add(Restrictions.eq("content", content));
		
		disjunc.add(Restrictions.or(conj, conj2));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, disjunc);
	}
	
	public ObjectList<MemberMessage> findAllByMemberAndTime(int pageNumber, int resultPerPage, Company company, Member member, Date dateAcquired, Order[] order){
		final Conjunction conj = Restrictions.conjunction();
		final Conjunction conj2 = Restrictions.conjunction();
		
		final Disjunction disjunc = Restrictions.disjunction();
		
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("senderMember", member));
		conj.add(Restrictions.ge("createdOn", dateAcquired));
		
		conj2.add(Restrictions.eq("company", company));
		conj2.add(Restrictions.eq("receiverMember", member));
		conj2.add(Restrictions.ge("createdOn", dateAcquired));
		
		disjunc.add(Restrictions.or(conj, conj2));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, disjunc);
	}
	
	public BigInteger findAllByStatusCMSCount(Company company, Member member, User user, MessageStatus messageStatus) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM member_message where company_id = :currentCompanyID and (sender_member_id = :currentMemberID and receiver_message_status = :currentMessageStatusID and receiver_user_id = :currentUserID) and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setLong("currentMemberID", member.getId()); 
		q.setLong("currentUserID", user.getId());
		q.setLong("currentMessageStatusID", messageStatus == MessageStatus.NEW ? 0L : 1L);
		
		return (BigInteger) q.uniqueResult();
	}
	
	public BigInteger findAllByStatusCMSCountMessage(Company company, MessageStatus messageStatus) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM member_message where company_id = :currentCompanyID and receiver_user_id = 13 and receiver_message_status = 0 and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		//q.setLong("currentMessageStatusID", messageStatus == MessageStatus.NEW ? 0L : 1L);
		
		return (BigInteger) q.uniqueResult();
	}
		
}
