package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="BatchUpdateExcelFile")
@Table(name="batchupdate_excelfile")
public class BatchUpdateExcelFile extends CompanyBaseObject {
	private String fileLocation;
	private String fileName;
	private String contentType;
	private Group group;
	private User user;
	
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	@Basic
	@Column(name="file_location")
	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Basic
	@Column(name="file_name")
	public String getFileName() {
		return fileName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Basic
	@Column(name="content_type")
	public String getContentType() {
		return contentType;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	@ManyToOne(targetEntity=Group.class, fetch=FetchType.LAZY)
	@JoinColumn(name="group_id", nullable=false)
	public Group getGroup() {
		return group;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	public User getUser() {
		return user;
	}
}
