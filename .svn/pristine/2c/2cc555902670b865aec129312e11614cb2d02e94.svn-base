package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.entity.interfaces.File;

@MappedSuperclass
public abstract class AbstractFile extends CompanyBaseObject implements File {

	private String fileName;
	private String filePath;
	private Long fileSize;
	private String fileType;
	 
	@Basic
	@Column(name="filename", length=255, nullable=false)
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String filename) {
		this.fileName = filename;
	}
	
	@Basic
	@Column(name="filepath", length=255, nullable=false)
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Basic
	@Column(name="filesize")
	public Long getFileSize() {
		return fileSize;
	}
	 
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	@Basic
	@Column(name="filetype", length=255)
	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
