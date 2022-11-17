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
@Entity(name = "Receipts")
@Table(name = "receipts_images")
public class Receipts extends BaseImage {
	private Member member;
	private Double points;
	private Boolean redeemed;
	private File file;
	private Company company;

	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	@Basic
	@Column(name="points", nullable=false)
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
		this.points = points;
	}	
	@Basic
	@Column(name="receiptImage")
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
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
	
	
}