package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.enums.EntityLogEnum;

import antlr.collections.List;

@Entity(name="Log")
@Table(name="log")
public class Log extends CompanyBaseObject{
	private User updatedByUser;
	private Long entityID;
	private EntityLogEnum entityType;
	private String remarks;
	
	private Integer availableQuantity;
	private Integer quantity;
	private String transactionType;
	
	private CategoryItem categoryItem;
	

	/**
	 * @param updatedByUser the updatedByUser to set
	 */
	public void setUpdatedByUser(User updatedByUser) {
		this.updatedByUser = updatedByUser;
	}

	/**
	 * @return the updatedByUser
	 */
	@ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY)
	@JoinColumn(name="updated_by_user", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public User getUpdatedByUser() {
		return updatedByUser;
	}

	/**
	 * @param entityID the entityID to set
	 */
	public void setEntityID(Long entityID) {
		this.entityID = entityID;
	}

	/**
	 * @return the entityID
	 */
	@Basic
	@Column(name="entity_id")
	public Long getEntityID() {
		return entityID;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(EntityLogEnum entityType) {
		this.entityType = entityType;
	}

	/**
	 * @return the entityType
	 */
	@Enumerated(EnumType.STRING)
	@Column( name="entity_type", nullable=false)
	public EntityLogEnum getEntityType() {
		return entityType;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the remarks
	 */
	@Basic
	@Column(name="remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@Basic
	@Column(name="quantity")
	public Integer getQuantity() {
		return quantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	@Basic
	@Column(name="available_quantity")
	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Basic
	@Column(name="transaction_type")
	public String getTransactionType() {
		return transactionType;
	}


	@ManyToOne(targetEntity=CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name="entity_id", insertable = false, updatable = false)
	@NotFound(action=NotFoundAction.IGNORE)
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}

	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	
	
	
}