package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OrderBy;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="RegistrationItemOtherField")
@Table(name="registration_item_other_field")
public class RegistrationItemOtherField
		extends CompanyBaseObject
{
	private OtherField otherField;
	private Member member;
	private String content;
	private Integer index;
	private String note;
	
	@ManyToOne(targetEntity = OtherField.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "other_field_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public OtherField getOtherField() {
		return otherField;
	}
	
	
	
	public void setOtherField(OtherField otherField) {
		this.otherField = otherField;
	}
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Member getMember() {
		return member;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	/**
	 * Get the content.
	 * 
	 * @return the content
	 */
	@Basic
	@Column(name = "content", length = 8000)
	public String getContent()
	{
		return content;
	}
	
	/**
	 * Set the content.
	 * 
	 * @param content
	 */
	public void setContent(String content)
	{
		this.content = content;
	}



	public void setIndex(Integer index) {
		this.index = index;
	}


	@Basic
	@Column(name = "indexing")
	public Integer getIndex() {
		return index;
	}



	public void setNote(String note) {
		this.note = note;
	}


	@Basic
	@Column(name = "note", length = 8000)
	public String getNote() {
		return note;
	}
}
