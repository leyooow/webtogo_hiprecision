package com.ivant.cms.entity;

import java.io.File;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.ivant.cms.delegate.ItemAttributeDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.interfaces.BaseID;
@Entity(name = "MemberFiles")//Receipts
@Table(name = "member_files")
public class MemberFile extends BaseObject{
	
	UserDelegate userDelegate = UserDelegate.getInstance();
	
	private Member member;
	private String remarks;//wendys: reward code
	//private double price;
	//private double points;
	private String value;
	private Date approvedDate;
	private Long approvedBy;
	private String status;
	private CartOrder cartOrder;
	private Company company;
	
	private Category category;
	private CategoryItem categoryItem;
	
	private String info1;
	private String info2;
	private String info3;
	private String info4;
	private String info5;
	
	/*@Basic
	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Basic
	@Column(name = "points")
	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}*/
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable=true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	@Basic
	@Column(name="remarks")
	@Type(type="text")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Basic
	@Column(name="approved_value")
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

	@Basic
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Transient
	public String getUpdatedBy() {				
		if(userDelegate.find(getApprovedBy()) == null)
			return "";
		else
			return userDelegate.find(getApprovedBy()).getFullName();
	}
	
	@ManyToOne(targetEntity = CartOrder.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "cartOrder_id")
	@NotFound(action = NotFoundAction.IGNORE) 
	public CartOrder getCartOrder() {
		return cartOrder;
	}
	public void setCartOrder(CartOrder cartOrder) {
		this.cartOrder = cartOrder;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}
	
	
	
	
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Category getCategory() {
		return category;
	}
	
	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryItem_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	
	
	
	
	
	
	
	
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	@Basic
	@Column(name = "info1", nullable = true)
	public String getInfo1() {
		return info1;
	}
	
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	
	@Basic
	@Column(name = "info2", nullable = true)
	public String getInfo2() {
		return info2;
	}
	
	public void setInfo3(String info3) {
		this.info3 = info3;
	}
	
	@Basic
	@Column(name = "info3", nullable = true) 
	public String getInfo3() {
		return info3;
	}
	
	public void setInfo4(String info4) {
		this.info4 = info4;
	}
	
	@Basic
	@Column(name = "info4", nullable = true)
	public String getInfo4() {
		return info4;
	}
	
	public void setInfo5(String info5){
		this.info5 = info5;
	}
	
	@Basic
	@Column(name = "info5", nullable = true)
	public String getInfo5(){
		return info5;
	}
	
}