package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity(name="Attribute")
@Table(name="attribute")
public class Attribute extends CompanyBaseObject {
	
	private String name;
	private String description;
	private Group group;
	private List<ItemAttribute> attributes;
	private String dataType;
	private List<PresetValue> presetValues;
	
	public Attribute() {
	}				@Basic		@Column(name="name", nullable=false)		public String getName() {			return name;		}				public void setName(String name) {			this.name = name;		}				@Basic		@Column(name="description")		public String getDescription() {			return description;		}		  		public void setDescription(String description) {			this.description = description;		}	
		@Basic
		@Column(name="data_type")
		public String getDataType() {
			return dataType;
		}
		  
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
				@ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)		@JoinColumn(name = "group_id", nullable=false)		@NotFound(action = NotFoundAction.IGNORE) 		public Group getGroup() {			return group;		}
		@Basic 		@Column(name="featured", nullable=false)		public void setGroup(Group group) {			this.group = group;		}
		
		@OneToMany(targetEntity = ItemAttribute.class, mappedBy = "attribute", fetch = FetchType.LAZY)
		@Where(clause = "valid=1")
		@NotFound(action=NotFoundAction.IGNORE)
		public List<ItemAttribute> getAttributes() {
			return attributes;
		}

		public void setAttributes(List<ItemAttribute> attributes) {
			this.attributes = attributes;
		}
		
		@OneToMany(targetEntity = PresetValue.class, mappedBy = "attribute", fetch = FetchType.LAZY)
		@Where(clause = "valid=1")
		@NotFound(action=NotFoundAction.IGNORE)
		public List<PresetValue> getPresetValues() {
			return presetValues;
		}

		public void setPresetValues(List<PresetValue> presetValues) {
			this.presetValues = presetValues;
		}
		}
