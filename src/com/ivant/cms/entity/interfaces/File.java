package com.ivant.cms.entity.interfaces;

public interface File {

	public String getFileName();
	public void setFileName(String fileName);

	public String getFilePath();
	public void setFilePath(String filePath);
	
	public Long getFileSize();
	public void setFileSize(Long fileSize);
	
	public String getFileType();
	public void setFileType(String fileType);
}
