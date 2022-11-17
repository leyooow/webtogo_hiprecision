package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity
@Table(name="member_type")
public class MemberType 
	extends CompanyBaseObject{
	
	private String name;
	private List<Member> members;

	@Basic
	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(targetEntity = Member.class, mappedBy = "memberType", fetch = FetchType.LAZY)
	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	
}
