package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="Promo")
@Table(name="promo")
public class Promo extends CompanyBaseObject {

	private String name;
	private String content;
	private Boolean checked;
	private Long apiId;
	
	@Basic
	@Column(name = "name", length = 255, nullable = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name = "content", length = Integer.MAX_VALUE, nullable = true)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Basic
	@Column(name = "checked", nullable = true)
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	@Basic
	@Column(name = "apiId", nullable = true)
	public Long getApiId() {
		return apiId;
	}
	public void setApiId(Long apiId) {
		this.apiId = apiId;
	}
	
	
	
}
