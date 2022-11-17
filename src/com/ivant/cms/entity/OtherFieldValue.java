package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity(name="OtherFieldValue")
@Table(name="other_field_value")
public class OtherFieldValue 
	extends BaseObject{

	private OtherField otherField;
	private String value;
	
	@ManyToOne(targetEntity = OtherField.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "other_field_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public OtherField getOtherField() {
		return otherField;
	}
	public void setOtherField(OtherField otherField) {
		this.otherField = otherField;
	}
	
	/**
	 * Get the name.
	 * 
	 * @return the name
	 */
	@Basic
	@Column(name = "value")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
