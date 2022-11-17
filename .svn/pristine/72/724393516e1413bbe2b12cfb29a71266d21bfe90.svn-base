package com.ivant.cms.delegate;

import java.math.BigInteger;
import java.util.Date;

import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import com.ivant.cms.db.MemberMessageDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberMessage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.MessageRemarks;
import com.ivant.cms.enums.MessageStatus;
import com.ivant.cms.enums.MessageType;

public class MemberMessageDelegate extends AbstractBaseDelegate<MemberMessage, MemberMessageDAO> {
	
	private static MemberMessageDelegate instance = new MemberMessageDelegate();
	
	public static MemberMessageDelegate getInstance() {
		return instance;
	}
	
	public MemberMessageDelegate() {
		super(new MemberMessageDAO());
	}
	
	public MemberMessage find(Company company, Member member, String content, MessageType messageType){
		return dao.find(company, member, content, messageType);
	}
	
	public ObjectList<MemberMessage> findAll(int pageNumber, int resultPerPage, Company company, Member member, Order[] order){
		return dao.findAll(pageNumber, resultPerPage, company, member, order);
	}
	
	public ObjectList<MemberMessage> findAllBySenderAndReceiver(int pageNumber, int resultPerPage, Company company, Member sender,Member receiver, Order[] order){
		return dao.findAllBySenderAndReceiver(pageNumber, resultPerPage, company, sender, receiver, order);
	}
	
	public ObjectList<MemberMessage> findAllByRemarks(int pageNumber, int resultPerPage, Company company, Member member, MessageRemarks remarks, Order[] order) {
		return dao.findAllByRemarks(pageNumber, resultPerPage, company, member, remarks, order);
	}
	
	public ObjectList<MemberMessage> findAllByStatus(int pageNumber, int resultPerPage, Company company, Member member, MessageStatus messageStatus, Order[] order) {
		return dao.findAllByStatus(pageNumber, resultPerPage, company, member, messageStatus, order);
	}
	
	public ObjectList<MemberMessage> findAllByStatusCMS(int pageNumber, int resultPerPage, Company company, Member member, MessageStatus messageStatus, Order[] order) {
		return dao.findAllByStatusCMS(pageNumber, resultPerPage, company, member, messageStatus, order);
	}
	
	public ObjectList<MemberMessage> findAllByMemberAndContent(int pageNumber, int resultPerPage, Company company, Member member, String content, Order[] order){
		return dao.findAllByMemberAndContent(pageNumber, resultPerPage, company, member, content, order);
	}
	
	public ObjectList<MemberMessage> findAllByMemberAndTime(int pageNumber, int resultPerPage, Company company, Member member, Date dateAcquired, Order[] order){
		return dao.findAllByMemberAndTime(pageNumber, resultPerPage, company, member, dateAcquired, order);
	}
	
	public BigInteger findAllByStatusCMSCount(Company company, Member member, User user, MessageStatus messageStatus) {
		return dao.findAllByStatusCMSCount(company, member, user, messageStatus);
	}
	
	public BigInteger findAllByStatusCMSCountMessage(Company company, MessageStatus messageStatus) {
		return dao.findAllByStatusCMSCountMessage(company, messageStatus);
	}
}
