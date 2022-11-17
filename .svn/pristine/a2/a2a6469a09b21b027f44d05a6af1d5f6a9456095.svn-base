package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="PortalActivityLog")
@Table(name="portal_activity_log")
public class PortalActivityLog extends CompanyBaseObject {
	
	private Member member;
	private String remarks;
	private String section, topic, member_company, member_parent_company;
	
	
	@ManyToOne(targetEntity=Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Basic
	@Column(name="remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Basic
	@Column(name="section")
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
	
	@Basic
	@Column(name="topic")
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	@Basic
	@Column(name="member_company")
	public String getMemberCompany() {
		return member_company;
	}

	public void setMemberCompany(String member_company) {
		this.member_company = member_company;
	}
	
	@Basic
	@Column(name="member_parent_company")
	public String getMemberParentCompany() {
		return member_parent_company;
	}

	public void setMemberParentCompany(String member_parent_company) {
		this.member_parent_company = member_parent_company;
	}
}
