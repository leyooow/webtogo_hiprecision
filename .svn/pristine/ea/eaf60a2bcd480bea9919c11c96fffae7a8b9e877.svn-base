package com.ivant.cms.entity;

import java.util.Date;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.enums.SavedEmailType;

@Entity
@Table(name="saved_emails")
public class SavedEmail extends CompanyBaseObject {

	private String formName;
	private String sender,kaptcha;
	private String email;
	private String phone;
	private String emailContent;
	private String uploadFileName;
	private Date emailDateValidated;
	private Boolean emailValid;
	private String testimonial;
	private String downloadLink;
	private Date downloadDate;
	private SavedEmailType savedEmailType;
	
	/*
	 * Bluewarner
	 */
	private String receipt;
	private String promo;
	/*
	 * Bluewarner
	 */
	
	private String otherField1;
	private String otherField2;
	private String otherField3;
	
	private Map<String, String> otherDetailMap;
	
	private Member member;

	@Basic
	@Column(name="form_name", length=50, nullable=false)
	public String getFormName() {
		return formName;
	}
	
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	@Basic
	@Column(name="sender", length=50)
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@Basic
	@Column(name="email", length=100)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Basic
	@Column(name="phone", length=30)
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	/*
	 * Bluewarner
	 */
	
	@Basic
	@Column(name="receipt", length=30)
	public String getReceipt() {
		return receipt;
	}
	
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	
	@Basic
	@Column(name="promo", length=30)
	public String getPromo() {
		return promo;
	}
	
	public void setPromo(String promo) {
		this.promo = promo;
	}
	
	/*
	 * Bluewarner
	 */
	
	@Basic
	@Column(name="wtgkaptcha", length=255)
	public String getKaptcha() {
		return kaptcha;
	}
	
	public void setKaptcha(String kaptcha) {
		this.kaptcha = kaptcha;
	}
	
	@Basic
	@Column(name="email_content", length=Integer.MAX_VALUE) 
	public String getEmailContent() {
		emailContent = emailContent.replaceAll("\n","<br>");
		return emailContent;
	}
	
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
	@Basic
	@Column(name="upload_filename", length=2000)
	public String getUploadFileName() {
		return uploadFileName;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="email_date_validated")
	public Date getEmailDateValidated() {
		return emailDateValidated;
	} 
	
	public void setEmailDateValidated(Date emailDateValidated) {
		this.emailDateValidated = emailDateValidated;
	}
	
	@Basic
	@Column(name="email_valid")
	public Boolean getEmailValid() {
		return emailValid;
	}

	public void setEmailValid(Boolean emailValid) {
		this.emailValid = emailValid;
	}

	@Basic
	@Column(name="testimonial", length=2000)
	public String getTestimonial() {
		return testimonial;
	}

	public void setTestimonial(String testimonial) {
		this.testimonial = testimonial;
	}

	@Basic
	@Column(name="download_link")
	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="download_date")
	public Date getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	@Basic
	@Column(name="other_field_1")
	public String getOtherField1() {
		return otherField1;
	}

	public void setOtherField1(String otherField1) {
		this.otherField1 = otherField1;
	}

	@Basic
	@Column(name="other_field_2")
	public String getOtherField2() {
		return otherField2;
	}

	public void setOtherField2(String otherField2) {
		this.otherField2 = otherField2;
	}

	@Basic
	@Column(name="other_field_3")
	public String getOtherField3() {
		return otherField3;
	}

	public void setOtherField3(String otherField3) {
		this.otherField3 = otherField3;
	}

	public void setOtherDetailMap(Map<String, String> otherDetailMap) {
		// TODO Auto-generated method stub
		this.otherDetailMap = otherDetailMap;
	}
	
    @CollectionOfElements
    public Map<String, String> getOtherDetailMap() {
    	return otherDetailMap;
    }
	
    @ManyToOne(targetEntity=Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	@Basic
	@Column(name = "saved_email_type") 
	public SavedEmailType getSavedEmailType(){
		return savedEmailType;
	}
	
	public void setSavedEmailType(SavedEmailType savedEmailType) {
		this.savedEmailType = savedEmailType;
	}
	
}
