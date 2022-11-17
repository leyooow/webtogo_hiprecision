package com.ivant.cms.entity;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.json.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.ivant.cms.interfaces.CompanyAware;

@Entity(name = "CommentFile")
@Table(name = "comment_files")
public class CommentFile extends AbstractFile implements CompanyAware, JSONAware {

	private Comment comment;
	private String originalFileName, fileType, filePath;
	
	
	@ManyToOne(targetEntity = Comment.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Comment getComment() {
		return comment;
	}
	
	public void setComment(Comment comment){
		this.comment = comment;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	/*
	public String getFileType(){
		return fileType;
	}
	
	public void setFileType(String fileType){
		this.fileType = fileType;
	}
	
	public String getfilePath(){
		return filePath;
	}
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		
		JSONObject json = new JSONObject();
		
		json.put("id", getId());
		json.put("fileType", getFileType());
		json.put("filePath", getFilePath());
		return json.toJSONString();
	}
	
}
