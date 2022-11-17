package com.ivant.cms.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name = "CommentLike")
@Table(name = "comment_like")
public class CommentLike extends CompanyBaseObject {

	private Member member;
	private Comment comment;
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "like_by", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	@ManyToOne(targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "comment_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE)
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	

	
}
