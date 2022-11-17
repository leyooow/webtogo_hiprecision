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
import com.ivant.cms.interfaces.CompanyAware;

@Entity(name = "GroupMessage")
@Table(name ="group_message")
public class GroupMessage extends BaseObject implements CompanyAware, JSONAware {
	private Member sender;
	private String content;
	private Company company;
	private Long groupId;
	private String groupName;
	
	
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
		this.sender = senderMember;
	}
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getSender() {
		return sender;
	}
	
	public void setSender(Member sender) {
		this.sender = sender;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Basic
	@Column(name = "content", nullable = true)
	public String getContent() {
		return content;
	}
	
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	@Basic
	@Column(name = "group_id", nullable = true)
	public Long getGroupId() {
		return groupId;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Basic
	@Column(name = "group_name", nullable = true)
	public String getGroupName() {
		return groupName;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateToday = new Date();
		
		String today = sdf.format(dateToday);
		String dateCreated = sdf.format(getCreatedOn());
		
		if(today.equals(dateCreated)){
			SimpleDateFormat hma = new SimpleDateFormat("hh:mm a");
			dateCreated = hma.format(getCreatedOn());
		}else{
			SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yy");
			dateCreated = fmt.format(getCreatedOn());
		}
		

		JSONObject json = new JSONObject();
		json.put("sender_id", getSender().getId());
		json.put("createdOn", dateCreated);
		json.put("content", getContent());
		json.put("sender_firstname", getSender().getFirstname());
		json.put("sender_lastname", getSender().getLastname());
		json.put("sender_info2", getSender().getInfo2());
		json.put("group_id", getGroupId());
		

		return json.toJSONString();
	}
}
