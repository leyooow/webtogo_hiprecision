package com.ivant.cms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.EnumType;
import javax.persistence.Transient;


import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.enums.ActivityStatusEnum;

@Entity(name="Activity")
@Table(name="activity")
public class Activity extends CompanyBaseObject
{
				private String description;
				private String requestDetails;
				private String remarks;
				private String actionTaken;
				private String requestDate;
				private String designIteration;
				private String type;
				private ActivityStatusEnum status;
				private User createdBy;
				private User updatedBy;
				
				
				@Basic
				@Column( name="description", nullable=false, length=65535)
				public String getDescription() {
					return description;
				}
				
				public void setDescription(String description) {
					this.description = description;
				}
				
				@Enumerated(EnumType.ORDINAL)
				@Column( name="status", nullable=false)
				public ActivityStatusEnum getStatus() {
					return status;
				}
				
				public void setStatus(ActivityStatusEnum status) {
					this.status = status;
				}
				
				@ManyToOne(targetEntity  = User.class)
				@JoinColumn(name = "created_by", nullable=false)				
				public User getCreatedBy() {
					return createdBy;
				}
				
				public void setCreatedBy(User createdBy) {
					this.createdBy = createdBy;
				}
				
				@ManyToOne (targetEntity  = User.class)
				@JoinColumn(name = "updated_by", nullable=true)
				public User getUpdatedBy() {
					return updatedBy;
				}
				
				public void setUpdatedBy(User updatedBy) {
					this.updatedBy = updatedBy;
				}

				public void setRequestDetails(String requestDetails) {
					this.requestDetails = requestDetails;
				}
				@Basic
				@Column( name="requestDetails", nullable=true, length=65535)
				public String getRequestDetails() {
					return requestDetails;
				}

				public void setRemarks(String remarks) {
					this.remarks = remarks;
				}
				@Basic
				@Column( name="remarks", nullable=true, length=65535)
				public String getRemarks() {
					return remarks;
				}

				public void setActionTaken(String actionTaken) {
					this.actionTaken = actionTaken;
				}
				@Basic
				@Column( name="actionTaken", nullable=true, length=65535)
				public String getActionTaken() {
					return actionTaken;
				}

				public void setRequestDate(String requestDate) {
					this.requestDate = requestDate;
				}
				@Basic
				@Column( name="requestDate", nullable=true, length=65535)
				public String getRequestDate() {
					return requestDate;
				}

				public void setDesignIteration(String designIteration) {
					this.designIteration = designIteration;
				}
				@Basic
				@Column( name="designIteration", nullable=true, length=65535)
				public String getDesignIteration() {
					return designIteration;
				}

				public void setType(String type) {
					this.type = type;
				}
				
				@Basic
				@Column( name="type", nullable=true, length=65535)
				public String getType() {
					return type;
				}
				


}
