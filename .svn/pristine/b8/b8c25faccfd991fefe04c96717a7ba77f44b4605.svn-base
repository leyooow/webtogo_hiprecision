package com.ivant.cms.entity;

import java.io.File;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.BaseObject;
@Entity(name = "MemberFileItems")//Receipts
@Table(name = "member_files_items")
public class MemberFileItems extends BaseObject{
	private MemberFile memberFile;
	private Double points;
	private Boolean redeemed;
	private Company company;
	private String original;
	private String filename;
	private String title;
	private String description;
	private String distributor;
	private String invoiceNumber;
	private String freight;
	private String Remarks;
	private String companyName_SOLD;
	private String value;//this value represents the current value that being uploaded by the user..
	private Double amount;
	
	@ManyToOne(targetEntity = MemberFile.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_file_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public MemberFile getMemberFile() {
		return memberFile;
	}
	
	public void setMemberFile(MemberFile memberFile) {
		this.memberFile = memberFile;
	}

	@Basic
	@Column(name="original")
	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}	

	@Basic
	@Column(name="filename")
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Basic
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Basic
	@Column(name="description")
	@Type(type="text")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Basic
	@Column(name="value")
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Basic
	@Column(name="distributor")
	public String getDistributor() {
		return distributor;
	}
	
	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	
	@Basic
	@Column(name="invoiceNumber")
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
	@Basic
	@Column(name="freight")
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}	
	
	@Basic
	@Column(name="companyName_SOLD")
	public String getCompanyName_SOLD() {
		return companyName_SOLD;
	}
	public void setCompanyName_SOLD(String companyName_SOLD) {
		this.companyName_SOLD = companyName_SOLD;
	}
	
	@Basic
	@Column(name="remarks")
	@Type(type="text")
	public String getRemarks() {
		return Remarks;
	}
	
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	
	@ManyToOne(targetEntity = Company.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	@Basic
	@Column(name="amount")
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmount() {
		return amount;
	}
}