package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity
@Table(name="variation_group")
public class VariationGroup extends CompanyBaseObject {

	private String name;
	private String description;
	private int sort;
	private List<Variation> variations;
	
	public VariationGroup() {
		sort = 1;
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
	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Basic
	@Column(name="sort", nullable=false)
	public int getSort() {
		return sort;
	}
	
	public void setSort(int sort) {
		this.sort = sort;
	}

	@OneToMany(targetEntity=Variation.class, fetch=FetchType.LAZY, mappedBy="variationGroup")
	@Where(clause = "valid=1") 
	public List<Variation> getVariations() {
		return variations;
	}

	public void setVariations(List<Variation> variations) {
		this.variations = variations;
	}
}
