package com.ivant.cms.action.admin.dwr;

import com.ivant.cms.entity.Message;

/**
 * Reverse ajax class resplonsible for showing message item values.
 * 
 * @author Mark Kenneth M. Rañosa
 *
 */
public class DWRBulletinAction extends AbstractDWRAction {
	
	/**
	 * Returns the message content based on the specified {@code messageID}.
	 * 
	 * @param messageID
	 * 			- message item primary key number, must not be null
	 * @return - the message content based on the specified {@code messageID}
	 * 
	 * @throws Exception - The class Exception and its subclasses are a form of 
	 * Throwable that indicates conditions that a reasonable application might want to catch.
	 * 			
	 */
	public String getMessageValue(String messageID) throws Exception {
		Message message = messageDelegate.getMessage(Long.parseLong(messageID), getCompany());
		StringBuilder messageToShow = initMessageToReturn(message);
		
		return messageToShow.toString();
	}

	/**
	 * Prepares and creates the message content to be shown to the user/administrator. 
	 * 
	 * @param message
	 * 			- bulletin board message item, must not be null
	 * @return - the message content to be shown to the user/administrator
	 */
	private StringBuilder initMessageToReturn(Message message) {
		StringBuilder messageToShow = new StringBuilder();
		
		appendAuthorName(message, messageToShow);		
		appenAuthorEmail(message, messageToShow);
		appendContent(message, messageToShow);		
		appendSubject(message, messageToShow);
		appendAttachment(message, messageToShow);
		appendRecipientEmail(message, messageToShow);		
		appendCreatedOn(message, messageToShow);
		
		return messageToShow;
	}

	/**
	 * Adds the author name to the specified {@code messageToShow}.
	 * 
	 * @param message
	 * 			- bulletin board message item, must not be null
	 * @param messageToShow 
	 * 			- content to be shown to the administrator/user
	 */
	private void appendAuthorName(Message message, StringBuilder messageToShow) {
		if(null != message.getAuthorName())
			messageToShow.append("Author Name : ")
				.append(message.getAuthorName())
				.append("<br>");
	}

	/**
	 * Adds the author email to the specified {@code messageToShow}.
	 * 
	 * @param message
	 * 			- bulletin board message item, must not be null
	 * @param messageToShow 
	 * 			- content to be shown to the administrator/user
	 */
	private void appenAuthorEmail(Message message, StringBuilder messageToShow) {
		if(null != message.getAuthorEmail())
			messageToShow.append("Author Email : ")
				.append(message.getAuthorEmail())
				.append("<br>");
	}

	/**
	 * Adds the message content to the specified {@code messageToShow}.
	 * 
	 * @param message
	 * 			- bulletin board message item, must not be null
	 * @param messageToShow 
	 * 			- content to be shown to the administrator/user
	 */
	private void appendContent(Message message, StringBuilder messageToShow) {
		if(null != message.getContent())
			messageToShow.append("Message Text : ")
				.append(message.getContent())
				.append("<br>");
	}

	/**
	 * Adds the message attachement file to the specified {@code messageToShow}.
	 * 
	 * @param message
	 * 			- bulletin board message item, must not be null
	 * @param messageToShow 
	 * 			- content to be shown to the administrator/user
	 */
	private void appendAttachment(Message message, StringBuilder messageToShow) {
		if(null != message.getAttachmentFile())
			messageToShow.append("Attachment : <br>")
				.append("<img src=\"")
				.append(request.getContextPath()).append("/companies/")
				.append(company.getDomainName()).append("/message_attachments/")
				.append(message.getAttachmentFile()).append("\" /><br/>");
	}
	
	/**
	 * Adds the message subject to the specified {@code messageToShow}.
	 * 
	 * @param message
	 * 			- bulletin board message item, must not be null
	 * @param messageToShow 
	 * 			- content to be shown to the administrator/user
	 */
	private void appendSubject(Message message, StringBuilder messageToShow) {
		if(null != message.getSubject())
			messageToShow.append("Subject : ")
				.append(message.getSubject())
				.append("<br>");
	}

	/**
	 * Adds the recipient email to the specified {@code messageToShow}.
	 * 
	 * @param message
	 * 			- bulletin board message item, must not be null
	 * @param messageToShow 
	 * 			- content to be shown to the administrator/user
	 */
	private void appendRecipientEmail(Message message,
			StringBuilder messageToShow) {
		if(null != message.getRecipientEmail())
			messageToShow.append("Recepient Email : ")
				.append(message.getRecipientEmail())
				.append("<br>");
	}

	/**
	 * Adds the message creation timestamp to the specified {@code messageToShow}.
	 * 
	 * @param message
	 * 			- bulletin board message item, must not be null
	 * @param messageToShow 
	 * 			- content to be shown to the administrator/user
	 */
	private void appendCreatedOn(Message message, StringBuilder messageToShow) {
		messageToShow.append("Created On : ")
			.append(message.getCreatedOn());
	}
}
