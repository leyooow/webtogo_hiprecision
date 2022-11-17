package com.ivant.cms.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.enums.MessageRemarks;
import com.ivant.cms.enums.MessageStatus;
import com.ivant.cms.enums.MessageType;
import com.ivant.cms.interfaces.CompanyAware;


@Entity(name = "MemberMessage")
@Table(name ="member_message")
public class MemberMessage extends BaseObject implements Cloneable, CompanyAware, JSONAware{
	private Company company;
	private Member senderMember;
	private User senderUser;
	private String content;
	private MessageType messageType;
	private MessageRemarks messageRemarks;
	private Member receiverMember;
	private User receiverUser;
	private MessageStatus senderMessageStatus;
	private MessageStatus receiverMessageStatus;
	
	@Override
	public void setCompany(Company company){
		this.company = company;
	}
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}
	
	
	public void setSenderMember(Member senderMember) {
		this.senderMember = senderMember;
	}
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_member_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getSenderMember() {
		return senderMember;
	}
	
	public void setSenderUser(User senderUser) {
		this.senderUser = senderUser;
	}
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_user_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public User getSenderUser() {
		return senderUser;
	}
	
	public void setReceiverMember(Member receiverMember) {
		this.receiverMember = receiverMember;
	}
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_member_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getReceiverMember() {
		return receiverMember;
	}
	
	public void setReceiverUser(User receiverUser){
		this.receiverUser = receiverUser;
	}
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_user_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public User getReceiverUser() {
		return receiverUser;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Basic
	@Column(name = "content", nullable = true)
	public String getContent() {
		return content;
	}
	
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
	@Basic
	@Column(name = "message_type")
	public MessageType getMessageType() {
		return messageType;
	}
	
	public void setMessageRemarks(MessageRemarks messageRemarks){
		this.messageRemarks = messageRemarks;
	}
	
	@Basic
	@Column(name = "message_remarks")
	public MessageRemarks getMessageRemarks() {
		return messageRemarks;
	}
	
	@Basic
	@Column(name = "sender_message_status")
	public MessageStatus getSenderMessageStatus() {
		return senderMessageStatus;
	}
	
	public void setSenderMessageStatus(MessageStatus senderMessageStatus) {
		this.senderMessageStatus = senderMessageStatus;
	}
	
	@Basic
	@Column(name = "receiver_message_status")
	public MessageStatus getReceiverMessageStatus() {
		return receiverMessageStatus;
	}
	
	public void setReceiverMessageStatus(MessageStatus receiverMessageStatus) {
		this.receiverMessageStatus = receiverMessageStatus;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateToday = new Date();
		
		String today = sdf.format(dateToday);
		String dateCreated = sdf.format(getCreatedOn());
		JSONObject json = new JSONObject();
		
		if(today.equals(dateCreated)){
			SimpleDateFormat hma = new SimpleDateFormat("hh:mm a");
			dateCreated = hma.format(getCreatedOn());

		}else{
			SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yy");
			dateCreated = fmt.format(getCreatedOn());

		}
		

		
		if(getSenderUser()!=null && company.getName().equals("rockwell")){
			json.put("sender_id", getSenderUser().getId());
			json.put("createdOn", dateCreated);
			json.put("content", getContent());
			json.put("sender_firstname", getSenderUser().getFirstname());
			json.put("sender_lastname", getSenderUser().getLastname());
			json.put("sender_info2", getSenderUser().getInfo2());
		}else{
			json.put("sender_id", getSenderMember().getId());
			json.put("createdOn", dateCreated);
			json.put("content", getContent());
			json.put("sender_firstname", getSenderMember().getFirstname());
			json.put("sender_lastname", getSenderMember().getLastname());
			json.put("sender_info2", getSenderMember().getInfo2());
		}
		
		
		if(getReceiverMember()!=null){
		
		json.put("receiver_id", getReceiverMember().getId());
		json.put("receiver_firstname",  getReceiverMember().getFirstname());
		json.put("receiver_lastname",  getReceiverMember().getLastname());
		json.put("receiver_info2", getReceiverMember().getInfo2());
		}
		

		return json.toJSONString();
	}
}
