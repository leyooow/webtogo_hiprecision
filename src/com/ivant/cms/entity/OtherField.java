package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.enums.AppearanceType;
import com.ivant.cms.enums.FormType;

@Entity(name="OtherField")
@Table(name="other_field")
public class OtherField
		extends CompanyBaseObject
{
	private Group group;
	private String name;
	private FormType formType;
	private Integer sortOrder; 
	private AppearanceType appearanceType;
	private List<OtherFieldValue> otherFieldValueList;
	
	@ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	/**
	 * Get the name.
	 * 
	 * @return the name
	 */
	@Basic
	@Column(name = "name")
	public String getName()
	{		
		return name;
	}
	
	/**
	 * Set the name.
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Basic
	@Column(name = "sort_order", nullable=true)
	public Integer getSortOrder() {
		return sortOrder;
	}
	
	/**
	 * @param appearanceType the appearanceType to set
	 */
	public void setAppearanceType(AppearanceType appearanceType) 
	{
		this.appearanceType = appearanceType;
	}

	/**
	 * @return the appearanceType
	 */
	@Enumerated(EnumType.STRING)
	@Column( name="appearance_type")
	public AppearanceType getAppearanceType() 
	{
		return appearanceType;
	}

	public void setFormType(FormType formType) {
		this.formType = formType;
	}
	
	@Basic
	@Column( name="form_type")
	public FormType getFormType() {
		return formType;
	}

	@OneToMany(targetEntity = OtherFieldValue.class, mappedBy = "otherField", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id ASC")
	public List<OtherFieldValue> getOtherFieldValueList() {
		return otherFieldValueList;
	}

	public void setOtherFieldValueList(List<OtherFieldValue> otherFieldValueList) {
		this.otherFieldValueList = otherFieldValueList;
	}


}
