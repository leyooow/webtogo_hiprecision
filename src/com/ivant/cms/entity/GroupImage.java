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

@Entity(name = "GroupImage")
@Table(name = "group_images")
public class GroupImage extends BaseImage {

	private Group group;
	private Integer sortOrder = 1;
		
	@ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	@Basic 
	@Column(name = "sort_order")
	public Integer getSortOrder() {
		if(this.sortOrder == null) {
			this.sortOrder = Integer.valueOf(1);
		}
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}