package com.ivant.cms.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity(name = "GroupLanguage")
@Table(name = "group_language")
public class GroupLanguage extends BaseObject implements Cloneable {
	private Long id;
	private String name;	
	private String description;	
	private Group defaultGroup;
	private Language language;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column(name="name", nullable=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name = "description", length=2147483647)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "defaultGroup")
	@NotFound(action = NotFoundAction.IGNORE) 
	public Group getDefaultGroup() {
		return defaultGroup;
	}
	public void setDefaultGroup(Group defaultGroup) {
		this.defaultGroup = defaultGroup;
	}
	
	@ManyToOne(targetEntity = Language.class, fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")	
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	

	@Transient
	public void cloneOf(Group group){

		group.setLanguage(null);
		this.setDescription(group.getDescription());			
		this.setName(group.getName());
		
		this.setIsValid(true);
		this.setCreatedOn(new Date());
		this.setUpdatedOn(new Date());
		
	}
}
