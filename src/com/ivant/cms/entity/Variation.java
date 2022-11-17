package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity
@Table(name="variation")
public class Variation extends CompanyBaseObject {

	private VariationGroup variationGroup;
	private String name;
	private int sort;
	
	public Variation() {
		sort = 1;
	}

	@ManyToOne(targetEntity=VariationGroup.class, fetch=FetchType.LAZY)
	@JoinColumn(name="variation_group", nullable=false)
	public VariationGroup getVariationGroup() {
		return variationGroup;
	}

	public void setVariationGroup(VariationGroup variationGroup) {
		this.variationGroup = variationGroup;
	}

	@Basic
	@Column(name="name", length=64, nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name="sort", nullable=false)
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
