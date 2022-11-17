package com.ivant.cms.action.admin;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.action.MessageManagementAction;
import com.ivant.cms.delegate.MessageDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Message;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class responsible for managing company bulletin board messages.
 * 
 * @author Mark Kenneth Rañosa
 *
 */
public class BulletinAction extends ActionSupport 
	implements Preparable, ServletRequestAware, CompanyAware, UserAware {
	
	private final static int ITEMS_PER_PAGE = 20;
	
	private static final long serialVersionUID = 7165567356270931069L;
	private static Logger logger = LoggerFactory.getLogger(MessageManagementAction.class);

	/** Current webtogo admin user, must not be null*/
	private User user;
	/** Data that is passed through the session */
	private HttpServletRequest request;
	/** Current session webtogo company, must not be null*/
	private Company company;
	
	/** Message author, must not be null */
	private String authorName;
	/** Author's e-mail address, must not be null*/
	private String authorEmail;
	/** Title of the message, must not be null but can be empty */
	private String messageTitle;
	/** Email address of the recipient, must not be empty */
	private String recipientEmail;
	/** Content to be sent, must not be empty*/
	private String message;
	/** The message object, can be null */
	private Message messageItem;
	/** The list of bulletin messages of the company, can be 0 or more */
	private List<Message> messageList;
	/** Url to redirect if action is executed successfully */
	private String successUrl;
	/** Current page number for paging, can be null*/
	private Integer pageNumber;
	
	private MessageDelegate messageDelegate = MessageDelegate.getInstance();

	@Override
	public String execute() throws Exception {
		logger.info("Executing BulletinAction .......");
		return SUCCESS;
	}
	
	@Override
	public void prepare() throws Exception {
		logger.info("Preparing BulletinAction  ..........");
		//initialize message and paging
		initMessageItem();		
		setPageNumber();		
		
		//get number of retrieved messages for current company for paging purposes
		BigInteger itemCount = messageDelegate.getMessageCount(getCompany());
		if(itemCount == null) 
			itemCount = new BigInteger("0");
		
		//show messages per page
		messageList = messageDelegate.listAllMessages(getCompany(), ITEMS_PER_PAGE, pageNumber-1);
		setPaging(itemCount.intValue(), ITEMS_PER_PAGE);
		logger.info("Retrieved MessageList : " + messageList);
	}

	/**
	 * Loads the message item, message loaded is based on the specified messageID.
	 */
	private void initMessageItem() {
		try {			
			//get the specified message item
			long messageID = Long.parseLong(request.getParameter("messageID"));
			
			//load and set the message item
			Message currentMessage = messageDelegate.getMessage(messageID, getCompany());			
			setMessageItem(currentMessage);		
		}
		catch(Exception e) {
			logger.info("Current Message is empty.");			
		}
	}
	
	/**
	 * Removes specified message item.
	 * 
	 * @return - SUCCESS if message is successfully deleted, otherwise ERROR.
	 */
	public String deleteMessage(){
		if(!commonParamsValid()) {
			return ERROR;
		}		 
		
		//notify user upon successful message deletion
		messageDelegate.delete(getMessageItem());
		return SUCCESS;
	}
	
	/**
	 * Parses specified request specified page number to local variable {@code pageNumber}.
	 * Returns the parsed specified page number if successful, otherwise 1.
	 * 
	 * @return - the parsed specified page number if successful, otherwise 1.
	 */
	public int setPageNumber() {
		//get the specified page number
		String page = request.getParameter("page");
 		try{
 			//parse specified page
			pageNumber = Integer.parseInt(page);
		} catch(Exception e){
			//log unparsed value and return to 1st page
			logger.info("Cannot parse {} as page number" , page);
			pageNumber = 1;
			return pageNumber;
		}
		return pageNumber;
	}
	
	/**
	 * Loads the items based on the specified items per page.
	 * 
	 * @param itemSize 
	 * 			- total number of items
	 * @param itemsPerPage 
	 * 			- number of items shown per page
	 */
	public void setPaging(int itemSize, int itemsPerPage) {
		request.setAttribute("pagingUtil", new PagingUtil(itemSize, itemsPerPage, pageNumber, (itemSize/itemsPerPage)));
	}

	/**
	 * Returns true if user is a super user or an administrator, otherwise false.
	 * 
	 * @return - true if user is a super user or an administrator, otherwise false.
	 */
	private boolean commonParamsValid() {
		if(isSuperUser() && isAdministrator()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true if current user is an administrator, otherwise false.
	 * 
	 * @return - true if current user is an administrator, otherwise false.
	 */
	private boolean isAdministrator() {
		return user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR;
	}

	/**
	 * Returns true if current user is a super user, otherwise false.
	 * 
	 * @return - true if current user is a super user, otherwise false.
	 */
	private boolean isSuperUser() {
		return user.getUserType() != UserType.SUPER_USER;
	}
	
	/**
	 * Sets the user property value with the specified user.
	 *
	 * @param user the user to set
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Sets the company property value with the specified company.
	 *
	 * @param company the company to set
	 */
	@Override
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Returns the authorName property value.
	 *
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * Sets the authorName property value with the specified authorName.
	 *
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * Returns the authorEmail property value.
	 *
	 * @return the authorEmail
	 */
	public String getAuthorEmail() {
		return authorEmail;
	}

	/**
	 * Sets the authorEmail property value with the specified authorEmail.
	 *
	 * @param authorEmail the authorEmail to set
	 */
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	/**
	 * Returns the messageTitle property value.
	 *
	 * @return the messageTitle
	 */
	public String getMessageTitle() {
		return messageTitle;
	}

	/**
	 * Sets the messageTitle property value with the specified messageTitle.
	 *
	 * @param messageTitle the messageTitle to set
	 */
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	/**
	 * Returns the recipientEmail property value.
	 *
	 * @return the recipientEmail
	 */
	public String getRecipientEmail() {
		return recipientEmail;
	}

	/**
	 * Sets the recipientEmail property value with the specified recipientEmail.
	 *
	 * @param recipientEmail the recipientEmail to set
	 */
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	/**
	 * Returns the message property value.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message property value with the specified message.
	 *
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the messageItem property value.
	 *
	 * @return the messageItem
	 */
	public Message getMessageItem() {
		return messageItem;
	}

	/**
	 * Sets the messageItem property value with the specified messageItem.
	 *
	 * @param messageItem the messageItem to set
	 */
	public void setMessageItem(Message messageItem) {
		this.messageItem = messageItem;
	}

	/**
	 * Returns the messageList property value.
	 *
	 * @return the messageList
	 */
	public List<Message> getMessageList() {
		return messageList;
	}

	/**
	 * Sets the messageList property value with the specified messageList.
	 *
	 * @param messageList the messageList to set
	 */
	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	/**
	 * Returns the successUrl property value.
	 *
	 * @return the successUrl
	 */
	public String getSuccessUrl() {
		return successUrl;
	}

	/**
	 * Sets the successUrl property value with the specified successUrl.
	 *
	 * @param successUrl the successUrl to set
	 */
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	/**
	 * Returns the request property value.
	 *
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Sets the request property value with the specified request.
	 *
	 * @param request the request to set
	 */
	//TODO
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Returns the user property value.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Returns the company property value.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

}
