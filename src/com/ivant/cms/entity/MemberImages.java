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

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="MemberImages")
@Table(name="member_images")
public class MemberImages extends CompanyBaseObject{
	
	private Member member;
	private String promocode;
	private String ornumber;
	private String status;
	private String comment;
	private String imagename;
	private String uploadedImageName;
	private Company company;
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable=true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	
	/*@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}*/
	
	@Basic
	@Column(name = "promo_code", length = 100, nullable = true)
	public String getPromoCode(){
		return promocode;
	}
	
	public void setPromoCode(String promocode){
		this.promocode = promocode;
	}
	
	@Basic
	@Column(name = "or_number", length = 100, nullable = true)
	public String getOrNumber(){
		return ornumber;
	}
	
	public void setOrNumber(String ornumber){
		this.ornumber = ornumber;
	}
	
	@Basic
	@Column(name = "code_status", length = 255, nullable = true)
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	@Basic
	@Column(name = "code_comment", length = 255, nullable = true)
	public String getComment(){
		return comment;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	@Basic
	@Column(name = "image_name", length = 100, nullable = true)
	public String getImageName(){
		return imagename;
	}
	
	public void setImageName(String imagename){
		this.imagename = imagename;
	}
	
	@Basic
	@Column(name = "uploaded_image_name", length = 100, nullable = true)
	public String getUploadedImageName() {
		return uploadedImageName;
	}
	public void setUploadedImageName(String uploadedImageName) {
		this.uploadedImageName = uploadedImageName;
	}
	
	
}
