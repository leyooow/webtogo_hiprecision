package com.ivant.cms.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity
@Table(name="member_page_file")
public class MemberPageFile 
	extends CompanyBaseObject{

	private MemberType memberType;
	private PageFile pageFile;
	private MultiPageFile multiPageFile;
	
	@ManyToOne(targetEntity=MemberType.class, fetch=FetchType.LAZY)
	@JoinColumn(name="member_type", nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	public MemberType getMemberType() {
		return memberType;
	}
	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}
	
	@ManyToOne(targetEntity=PageFile.class, fetch=FetchType.LAZY)
	@JoinColumn(name="page_file", nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	public PageFile getPageFile() {
		return pageFile;
	}
	public void setPageFile(PageFile pageFile) {
		this.pageFile = pageFile;
	}
	
	@ManyToOne(targetEntity=MultiPageFile.class, fetch=FetchType.LAZY)
	@JoinColumn(name="multi_page_file", nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	public MultiPageFile getMultiPageFile() {
		return multiPageFile;
	}
	public void setMultiPageFile(MultiPageFile multiPageFile) {
		this.multiPageFile = multiPageFile;
	}
	
	
}
