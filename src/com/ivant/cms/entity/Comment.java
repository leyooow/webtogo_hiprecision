package com.ivant.cms.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;


@Entity(name = "Comment")
@Table(name = "comment")
public class Comment extends CompanyBaseObject {


	private String title;
	private String content;
	private Member createdBy;
	private Comment parentComment;
	private transient Integer likeCount;
	private transient Integer replyCount;
	private transient Integer fileCount;
	private List<Comment> replies;
	private List<CommentFile> commentFiles;
	private List<CommentLike> commentLikes;
	

	@OneToMany(targetEntity = Comment.class, mappedBy = "parentComment", fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<Comment> getReplies() {
		return replies;
	}

	public void setReplies(List<Comment> replies) {
		this.replies = replies;
	}

	@Basic
	@Column(name="content", length=2147483647, nullable=false)
	public String getContent() {
		return content;
	}
		
	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(Member createdBy) {
		this.createdBy = createdBy;
	}

	@ManyToOne(targetEntity = Comment.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment")
	@NotFound(action = NotFoundAction.IGNORE) 
	public Comment getParentComment() {
		return parentComment;
	}
	
	public void setParentComment(Comment comment){
		this.parentComment = comment;
	}
	
	@Transient
	public int getReplyCount() {
		this.replyCount = 0;
				if(getReplies() != null)
				{
					this.replyCount += getReplies().size();
				}
			
		return this.replyCount;
	}
	
	@Transient
	public int getLikeCount() {
		this.likeCount = 0;
			
			if(getCommentLikes() != null){
				this.likeCount += getCommentLikes().size();
			}
		
		return this.likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	
	
	@OneToMany(targetEntity = CommentFile.class, mappedBy = "comment", fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<CommentFile> getCommentFiles() {
		return commentFiles;
	}

	public void setCommentFiles(List<CommentFile> commentFiles) {
		this.commentFiles = commentFiles;
	}
	
	@Transient
	public int getFileCount() {
		this.fileCount = 0;
				if(getCommentFiles() != null)
				{
					this.fileCount += getCommentFiles().size();
				}
			
		return this.fileCount;
	}

	
	@OneToMany(targetEntity = CommentLike.class, mappedBy = "comment", fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<CommentLike> getCommentLikes() {
		return commentLikes;
	}

	public void setCommentLikes(List<CommentLike> commentLikes) {
		this.commentLikes = commentLikes;
	}

	@Basic
	@Column(name="title", length=2147483647, nullable=true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Transient
	public Map<Member, CommentLike> getMemberCommentLikeMap(){
		Map<Member, CommentLike> map = new HashMap<Member, CommentLike>();
		if(getCommentLikes() != null && !commentLikes.isEmpty()){
			for(CommentLike commentLike : commentLikes){
				map.put(commentLike.getMember(), commentLike);
			}
		}
		return map;
	}
	
}
