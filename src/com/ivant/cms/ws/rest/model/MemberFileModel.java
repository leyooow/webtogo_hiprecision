package com.ivant.cms.ws.rest.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;

@XmlRootElement(name = "MemberFile")
public class MemberFileModel extends AbstractModel{
	private Member member;
	private String remarks;
	private String value;
	private Date approvedDate;
	private Long approvedBy;
	private String status;
	private CartOrder cartOrder;
	private Company company;
	private String info1;
	private String info2;
	private String info3;
	private String info4;
	private String info5;
	
	private Boolean success;
	private String notificationMessage;
	
	public MemberFileModel() {
		
	}
	
	public MemberFileModel(MemberFile memberFile){
		setMember(memberFile.getMember());
		setRemarks(memberFile.getRemarks());
		setValue(memberFile.getValue());
		setApprovedDate(memberFile.getApprovedDate());
		setApprovedBy(memberFile.getApprovedBy());
		setStatus(memberFile.getStatus());
		setCartOrder(memberFile.getCartOrder());
		setCompany(memberFile.getCompany());
		setInfo1(memberFile.getInfo1());
		setInfo2(memberFile.getInfo2());
		setInfo3(memberFile.getInfo3());
		setInfo4(memberFile.getInfo4());
		setInfo5(memberFile.getInfo5());
		
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Long getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CartOrder getCartOrder() {
		return cartOrder;
	}

	public void setCartOrder(CartOrder cartOrder) {
		this.cartOrder = cartOrder;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public String getInfo3() {
		return info3;
	}

	public void setInfo3(String info3) {
		this.info3 = info3;
	}

	public String getInfo4() {
		return info4;
	}

	public void setInfo4(String info4) {
		this.info4 = info4;
	}

	public String getInfo5() {
		return info5;
	}

	public void setInfo5(String info5) {
		this.info5 = info5;
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	
}
