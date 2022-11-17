package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity(name = "KuysenSalesOrderSet")
@Table(name = KuysenSalesOrderSet.TABLE_NAME)
public class KuysenSalesOrderSet extends BaseObject {

	public static final String TABLE_NAME = "kuysensales_orderset";
	
	private KuysenSalesParentOrderSet kuysenSalesParentOrderSet;
	
	private CategoryItem item;
	private Boolean isIncluded;
	
	@ManyToOne(targetEntity = KuysenSalesParentOrderSet.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public KuysenSalesParentOrderSet getKuysenSalesParentOrderSet() {
		return kuysenSalesParentOrderSet;
	}
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItem getItem() {
		return item;
	}
	
	@Basic
	@Column(name="is_included")
	public Boolean getIsIncluded() {
		return isIncluded;
	}
	
	public void setKuysenSalesParentOrderSet(KuysenSalesParentOrderSet kuysenSalesParentOrderSet) {
		this.kuysenSalesParentOrderSet = kuysenSalesParentOrderSet;
	}
	
	public void setItem(CategoryItem item) {
		this.item = item;
	}
	
	public void setIsIncluded(Boolean isIncluded) {
		this.isIncluded = isIncluded;
	}
}
