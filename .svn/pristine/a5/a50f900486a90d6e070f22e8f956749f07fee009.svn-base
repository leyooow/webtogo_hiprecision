package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity
@Table(name="item_comment")
public class ItemComment extends BaseObject {
	private ItemComment parentItemComment;
	private List<ItemComment> childrenItemComment;
	private String content;
	private CategoryItem item;
	private Member member;
	private Company company;
	private String firstname;
	private String lastname;
	private double value;
	private SinglePage page;
	private String email;
	private Boolean published;
	
	@ManyToOne(targetEntity = ItemComment.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_item_comment")
	@NotFound(action = NotFoundAction.IGNORE)
	public ItemComment getParentItemComment(){
		return parentItemComment;
	}
	
	public void setParentItemComment(ItemComment parentItemComment){
		this.parentItemComment = parentItemComment;
	}
	
	@OneToMany(targetEntity = ItemComment.class, mappedBy = "parentItemComment", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("createdOn DESC")
	public List<ItemComment> getChildrenItemComment(){
		//List<ItemComment> tempListItemComment = itemCommentDelegate.
		return childrenItemComment;
	}
	
	public void setChildrenItemComment(List<ItemComment> childrenItemComment){
		this.childrenItemComment = childrenItemComment;
	}
	
	@Basic
	@Column(name = "content", nullable = true)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
		
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItem getItem() {
		return item;
	}
	
	public void setItem(CategoryItem item) {
		this.item = item;
	}
	
	@ManyToOne(targetEntity = SinglePage.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "page_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public SinglePage getPage() {
		return page;
	}
	
	public void setPage(SinglePage page) {
		this.page = page;
	}
	
	@ManyToOne(targetEntity = Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}

	@Basic
	@ManyToOne()
	@JoinColumn(name = "company_id", nullable = false)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Basic
	@Column(name = "first_name", nullable = true)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Basic
	@Column(name = "last_name", nullable = true)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Transient
	public String getFullName()
	{
		StringBuffer str = new StringBuffer();
		
		if (lastname != null)
			str.append(lastname + ", ");
		if (firstname != null)
			str.append(firstname);
		return str.toString();
	}
	
	@Basic
	@Column(name="email", length=100, nullable = true)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Basic
	@Column(name = "value", nullable = true)
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	@Basic
	@Column(name = "published", nullable = true)
	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}
}