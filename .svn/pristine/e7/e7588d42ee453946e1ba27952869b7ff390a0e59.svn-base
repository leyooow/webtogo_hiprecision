/**
 * 
 */
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

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.interfaces.CompanyAware;

/**
 * @author Administrator
 *
 */
@Entity(name="MemberPoll")
@Table(name="members_polls")
public class MemberPoll 
	extends BaseObject 
	implements Cloneable, CompanyAware 
{
	private Company company;
	private Member member;
	private CategoryItem categoryItem;
	private ItemComment itemComment;
	private SinglePage singlePage;
	private String pollType;
	private String remarks;
	private String description;
	
	
	@Override
	public void setCompany(Company company) 
	{
		this.company = company;
	}

	@ManyToOne(targetEntity = Company.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}

	@ManyToOne(targetEntity = Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}

	@ManyToOne(targetEntity = CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "category_item_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	
	@ManyToOne(targetEntity = ItemComment.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "itemComment_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public ItemComment getItemComment() {
		return itemComment;
	}
	
	@ManyToOne(targetEntity = SinglePage.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "singlePage_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public SinglePage getSinglePage() {
		return singlePage;
	}
	
	
	@Basic
	@Column(name = "polly_type", nullable = true)
	public String getPollType() {
		return pollType;
	}

	@Basic
	@Column(name = "remarks", nullable = true)
	public String getRemarks() {
		return remarks;
	}

	@Basic
	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setMember(Member member) {
		this.member = member;
	}


	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	public void setItemComment(ItemComment itemComment) {
		this.itemComment = itemComment;
	}

	public void setSinglePage(SinglePage singlePage) {
		this.singlePage = singlePage;
	}
	
	public void setPollType(String pollType) {
		this.pollType = pollType;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
