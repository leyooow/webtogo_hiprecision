package com.ivant.cms.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity(name = "Message")
@Table(name = "message")
public class Message extends BaseObject {
	private Date dateSent;
	private String authorName;
	private String authorEmail;
	private String recipientEmail;
	private String subject;
	private String content;
	private Boolean isSent;
	private String attachmentFile;
	private Company company;

	public Message() {
		super();
	}
	
	/**
	 * @return the dateSent
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_sent")
	public Date getDateSent() {
		return dateSent;
	}

	/**
	 * @param dateSent
	 *            the dateSent to set
	 */
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	/**
	 * @return the authorName
	 */
	@Basic
	@Column(name="author_name", nullable=false)
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName
	 *            the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the authorEmail
	 */
	@Basic
	@Column(name="author_email", nullable=false)
	public String getAuthorEmail() {
		return authorEmail;
	}

	/**
	 * @param authorEmail
	 *            the authorEmail to set
	 */
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	/**
	 * @return the recipientEmail
	 */
	@Basic
	@Column(name="recipient_email", nullable=false)
	public String getRecipientEmail() {
		return recipientEmail;
	}

	/**
	 * @param recipientEmail
	 *            the recipientEmail to set
	 */
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	/**
	 * @return the subject
	 */
	@Basic
	@Column(name="subject", nullable=false)
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the content
	 */
	@Basic
	@Column(name = "content", columnDefinition = "TEXT")
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the sent
	 */
	@Basic
	@Column(name="is_sent")
	public Boolean getIsSent() {
		return isSent;
	}

	/**
	 * @param isSent the sent to set
	 */
	public void setIsSent(Boolean isSent) {
		this.isSent = isSent;
	}

	/**
	 * @return the company
	 */
	@Basic
	@OneToOne()
	@JoinColumn(name = "company_id", nullable = false)
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the attachmentFile
	 */
	@Basic
	@Column(name="attachment")
	public String getAttachmentFile() {
		return attachmentFile;
	}

	/**
	 * @param attachmentFile the attachmentFile to set
	 */
	public void setAttachmentFile(String attachmentFile) {
		this.attachmentFile = attachmentFile;
	}

}
