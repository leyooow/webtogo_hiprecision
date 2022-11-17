package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="PresetValues")
@Table(name="preset_values")
public class PresetValue extends BaseObject{
	
	private String value;
	private Attribute attribute;
	
	public PresetValue() {
	}
	
	@Basic
	@Column(name = "value", length=2147483647)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@ManyToOne(targetEntity = Attribute.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "attribute_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}	
	
}
