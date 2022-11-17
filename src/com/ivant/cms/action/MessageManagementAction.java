package com.ivant.cms.action;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.MessageDelegate;
import com.ivant.cms.entity.Message;
import com.ivant.cms.entity.YesPayments;
import com.ivant.cms.helper.ImageUploadHelper;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUploadUtil;

/**
 * This Action class manages the listing/posting of all messages in the message
 * board, sending of messages to an email address, and the viewing of a specific
 * message via Message ID
 * 
 * @author Angel
 * 
 */
public class MessageManagementAction extends PageBaseAction {

	private static final long serialVersionUID = 7165567356270931069L;
	private static Logger logger = LoggerFactory
			.getLogger(MessageManagementAction.class);

	private String authorName;
	private String authorEmail;
	private String messageTitle;
	private String recipientEmail;
	private String message;
	private Message messageItem;
	private List<Message> messageList;
	private String successUrl;

	//FIXME: put this in a separate .properties file?
	private static final String PARAM_MESSAGE_ID = "messageid";
	protected final static int ITEMS_PER_PAGE = 12;
//	private static final String NOT_SENT = "notsent";
	
	//Attachment Folders
	private static final String ORIG_FOLDER = "orig";
	private static final String THUMBS_FOLDER = "thumbs";
	
	//Optional maximum image attachments size
	private static int MAX_PICTURE_WIDTH = 372 ;
	private static int MAX_THUMB_WIDTH = 327;
	
	private static final String UPLOADER_FIELD_NAME = "upload";
	private static final String ATTACHMENT_FOLDER = "message_attachments";
	
	private MessageDelegate messageDelegate = MessageDelegate.getInstance();

	@Override
	public void prepare() throws Exception {
		super.prepare();
		loadSiteMenu();
		successUrl = request.getParameter("successUrl");
	}
	
	/**
	 * Sends an email
	 * @return Action Status
	 */
	public String loadAllMessages(){
		logger.info("List messages action...");
		BigInteger itemCount = messageDelegate.getMessageCount(currentCompany);
		if(itemCount == null)
			itemCount = new BigInteger("0");
		messageList = messageDelegate.listAllMessages(currentCompany, ITEMS_PER_PAGE, pageNumber-1);
		setPaging(itemCount.intValue(), ITEMS_PER_PAGE);
		return SUCCESS;
	}

	/**
	 * Sends an email
	 * @return Action Status
	 */
	public String loadMessage(){
		logger.info("Load message action...");
		String idString = request.getParameter(PARAM_MESSAGE_ID);
		Long id = null;
		try{
			id = Long.parseLong(idString);
		} catch (Exception e) {
			logger.error("Cannot parse String {} as Long..", idString);
			return ERROR;
		}
		messageItem = messageDelegate.getMessage(id, currentCompany);
		return SUCCESS;
	}

	/**
	 * This method creates a new message and sends it to a certain recipient
	 * email, if specified
	 * 
	 */
	public String postNewMessage(){
		//check if attached image size is valid
		if (!FileUploadUtil.isFileSizeValid(getRealPath(), UPLOADER_FIELD_NAME, request)){		
			//notify user
			addActionMessage("Your message has been posted in the message board, " 
					+ " because attachment file exceeded the 1 MB file limit.");			
			return ERROR;
		}
		
		//Construct the Message Object
		Message message = constructMessageItem();
		
		//save new message
		Long id = messageDelegate.insert(message);
		if(id == null){
			logger.info("Failed inserting Message Item from {} to {}.", authorEmail, recipientEmail);
			return ERROR;
		}
		
		// Send the message if a recipient email is specified
		logger.info("Sending email to {}...", recipientEmail);
		sendMail(message);
		//ADD NOT SENT PAGE LATER; TODO: Separate uploading from email
//		if (!sent) {
//			return NOT_SENT;
//		}
		
		return SUCCESS;
	}
	
	/**
	 * 
	 * @param id
	 * @return message URL that would direct the recipient to the message detail
	 *         in the company's web site
	 */
	private String getMessageURL(Long id) {
		StringBuilder b = new StringBuilder();
		if(isLocal){
			b.append(getLocalURL());
		} else {
			b.append(getLiveURL());
		}
		b.append("/messagedetail.do?messageid=").
		  append(id);
		
		return b.toString();
	}
	
	/**
	 * Returns the admin url.
	 * 
	 * @return - the admin url
	 */
	private String getAdminUrl() {
		StringBuilder b = new StringBuilder();
		if(isLocal){
			b.append(getLocalAdminURL());
		} else {
			b.append(getLiveURL());
		}
		b.append("/admin");
		
		return b.toString();
	}
	
	/**
	 * Composes and returns the message body that will be sent to the email
	 * recipient
	 * 
	 * @param messageUrl
	 * @return message body that will be sent to the recipient
	 */
	private String getContent(String messageUrl) {
		StringBuilder  b = new StringBuilder();
		b.append("We would like to inform you that ").
		  append(authorName).
		  append(" has a message for you. \nPlease see ").
		  append(messageUrl).
		  append("\n\nThanks.\n\n").
		  append(StringUtils.capitalize(currentCompany.getName()));
		return b.toString();
	}
	
	/**
	 * Composes and returns the message body that will be sent to the administrator.
	 * 
	 * @param messageUrl
	 * @return - message body that will be sent to the administrator.
	 */
	private String getAdminNoficationContent(String messageUrl) {
		StringBuilder  b = new StringBuilder();
		b.append("Please see ").append(messageUrl)
		.append("\nby : ").append(authorName);
		return b.toString();
	}
	
	/**
	 * 
	 * @return Message title that is based on the Company's Name
	 */
	private String getCompanyBasedTitle(){
		StringBuilder b = new StringBuilder();
		b.append("Greetings from ").
		  append(currentCompany.getNameEditable()).
		  append("!");
		return b.toString();
	}

	/**
	 * 
	 * @return a new Message Entity Instance based on present data
	 */
	private Message constructMessageItem() {
		Message msg = new Message();
		msg.setCreatedOn(new Date(System.currentTimeMillis()));
		msg.setUpdatedOn(new Date(System.currentTimeMillis()));
		msg.setIsValid(true);
		msg.setCompany(currentCompany);
		msg.setAuthorEmail(authorEmail);
		msg.setAuthorName(authorName);
		msg.setRecipientEmail(recipientEmail);
		msg.setSubject(messageTitle);
		msg.setContent(message);
		msg.setIsSent(false);
		return msg;
	}
	
	/**
	 * 
	 * Sends Email and/or Uploads Attachment if any
	 * 
	 */

	public boolean sendMail(Message emailMessage) {
		try {
			boolean isModifiedForUpload = false;
			boolean isModifiedForMail = false;
			
			MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
			File[] file = r.getFiles(UPLOADER_FIELD_NAME);
			String[] filename = r.getFileNames(UPLOADER_FIELD_NAME);
			
			//IF attachment is specified
			if((file != null && file.length > 0) && (filename != null && filename.length > 0)){
				//FIXME this line is counting the length of the list not the file size, 
				// 		can be removed after since file checking is already handled above 
				if(file.length > 1000000){
					//Set Message Here
					logger.info("File was not uploaded since it exceeded limit 1MB.");
					addActionMessage("File was not uploaded since it exceeded the 1 MB file limit.");
				} else {
					String path =  getRealPath() + ATTACHMENT_FOLDER + File.separator;
					File uploadedFileDestination = new File(path);
					
					if(!uploadedFileDestination.exists()){
						uploadedFileDestination.mkdirs();
					}
					ImageUploadHelper uploadHelper = new ImageUploadHelper(ORIG_FOLDER, THUMBS_FOLDER, path, MAX_THUMB_WIDTH, MAX_PICTURE_WIDTH);
					boolean uploaded = uploadHelper.upload(file[0], filename[0], true);
					if(uploaded){
						emailMessage.setAttachmentFile(filename[0]);
						isModifiedForUpload = true;
					}
				}
				
			}
			
			boolean sent = false;
			if(StringUtils.isNotBlank(recipientEmail)){
				//Send Mail
				String content = getContent(getMessageURL(emailMessage.getId()));
				EmailUtil.connect("smtp.gmail.com", 25);
				sent = EmailUtil.send(authorEmail, emailMessage
						.getRecipientEmail(), getCompanyBasedTitle(), content);
				
				if (sent) {
					logger.info("Email from {} is successfully sent to {}",
							authorEmail, recipientEmail);
					
					// update message
					emailMessage.setIsValid(true);
					emailMessage.setDateSent(new Date(System.currentTimeMillis()));
					emailMessage.setIsSent(true);
					isModifiedForMail = true;
				} else {
					addActionMessage("Your message was not sent to " + recipientEmail + ".");
				}
			} else {
				logger.info("Message will not be sent since no recipient was specified");
			}
			
			
			if(isModifiedForUpload || isModifiedForMail){
				if(isModifiedForMail && !isModifiedForUpload){
					addActionMessage("Your message has been posted and sent to " + recipientEmail + ".");
				}
				if(!isModifiedForMail && isModifiedForUpload){
					addActionMessage("Your message with image attachment has been posted in the message board.");
				}
				if(isModifiedForMail && isModifiedForUpload){
					addActionMessage("Your message with image attachment has been posted and sent to " + recipientEmail + ".");
				}
				
				boolean updated = messageDelegate.update(emailMessage);
				if(updated){
					logger.info("Successfully updated message with id {}",
							emailMessage.getId());
					return true;
				} else {
					logger.info("Message with id {} was not updated ; sent:{}",
							emailMessage.getId(), sent);
				}
				
				
			} else {
				addActionMessage("Your message has been posted in the message board.");
				
				//notify webtogo administrator
				emailAdministrator(emailMessage);
			}

		} catch (Exception e) {
			logger.error(
					"An Exception occurred while sending email from {} to {}",
					authorEmail, recipientEmail);
			logger.error("Exception : {} ", e.getMessage());
		}
		return false;
	}

	/**
	 * Sends email to the administrator regarding the new message board.
	 * 
	 * @param emailMessage - mail containing the new message board post
	 */
	private void emailAdministrator(Message emailMessage) {
		boolean sent;
		//notify administrator of new message board
		String adminBulletinUrl = getAdminBulletinUrl(emailMessage);
		//generate content
		String content = getAdminNoficationContent(adminBulletinUrl);
		//initialize admin email address
		String adminEmail = "system@ivant.com";
		
		//send email
		EmailUtil.connect("smtp.gmail.com", 25);
		sent = EmailUtil.send(authorEmail, adminEmail, "A New Bulletin Message Created", content);
		
		//log notification to administrator upon successful email delivery
		if (sent) {					
			logger.info("WebToGo Administrator notified of new Message Board [Company : " 
					+ emailMessage.getCompany().getName() + "][Author : " 
					+ emailMessage.getAuthorName() + "]");
		}
	}
	
	public String getEmailForEvents(YesPayments yesPayments){
		String content = 
			///Email Notification
			"Dear Dr. "+yesPayments.getCardHolderNm() +" \n\n\n\n\n\n";
			content+=	"Thank you for your registration for the upcoming 16th PCO Forum.";
			//content+="Manila, Cebu, Davao";
			
			if(yesPayments.getEvents()!=null){
			String[] titleEvents=yesPayments.getEvents();
			for(int i=0;i<yesPayments.getEvents().length-1;i++){
				content+= titleEvents[i];
				content+=((titleEvents[i].length()-1)==i)?"":",";
			}
			}
			content+=".\n\n\n\n"+
				"You may view the program for the 16th PCO Forum. The upcoming 16th PCO Forum has 26 didactic lectures and 22 workshops. You may customize your own program by selecting the didactic lectures and workshops you want to attend using the following link www.philippineoptometry.org/eventsregistration.do.\n\n\n\n"+
				"If you are a PCO Associate Member, you can view and print your certificate in your personal webpage at www.philippineoptometry.com. If you are interested in becoming a PCO Member or you are currently a PCO Associate Member but you have not registered online yet as a PCO Member, you may click the following link for your membership application or membership information update: http://www.philippineoptometry.org/.\n\n\n\n"+
				"Yours truly,\n\n\n\n"+
				"PCO Secretariat\n\n"+"Suite 905 North Tower, \n"+
				"CHB Complex, St. Luke's Medical Center\n\n"+
				"E. Rodriguez Avenue, Quezon City\n\n"+
				"1100 Philippines";
		return content;
	}
	
	public String getEmailForMembership(YesPayments yesPayments){
		String content="";
		
		content +="Dear Dr. "+yesPayments.getCardHolderNm()+":\n\n"+
			"Welcome to PCO!\n\n"+
			"You are now officially registered as PCO Associate Member. As PCO Associate Member, you are entitled to all the privileges that PCO provides to its associate members. Some of these are:\n\n"+
			"-Enjoy the lowest registration rate in all PCO events,\n"+
			"-View the website sections made for PCO Members Only\n"+
			"-Receive current news and events in optometry through your email\n"+
			"-Upload your profile in your own membership page in the PCO Website\n"+
			"-View and print all your Certificates of Attendance beginning this year, which is located in your membership page\n"+
			"-Enjoy the opportunity to acquire ophthalmic equipments you need in your practice with affordable financing terms through our partnership with BPI Leasing\n"+
			"-We encourage you to make it a habit to pay your annual registration fee and events registration fee online.\n\n"+
			 
			"Thank you,\n\n"+
			 
			"Millette H. Romualdez, OD, FPCO, FIACLE, FAAO\n"+
			"Chairman, Membership Committee\n";
		
		return content;
	}
	
	
	
	
	//This is for YES PAYMENTS CLIENTS...
	public void emailClientForSuccessPayment(YesPayments yesPayments) {
		boolean sent;
		String content="";
		if(yesPayments.isMembershipFee())
			content=getEmailForMembership(yesPayments);
		else {
			content=getEmailForEvents(yesPayments);
		}
		//System.out.println(content);
		    //for local testing Only...
			//String toReceiver = "anthony@ivant.com";
			//String toReceiver="anthony@ivant.com";
			//if(yesPayments.getCardHolderEmail()!=null)
			String toReceiver=yesPayments.getCardHolderEmail();
			EmailUtil.connect("smtp.gmail.com", 25);
			sent = EmailUtil.send("system@ivant.com", toReceiver, "Payment Notification from PCO", content);
		
			//log notification to administrator upon successful email delivery
			if (sent) {					
				logger.info("yesPayments is null?? "+(yesPayments!=null)+"EMAIL WAS SENT TO "+toReceiver+" having a message of \n"+content);
			}
	}

	/**
	 * Returns the URL for the bulletin window with the new bulletin message specified.
	 * 
	 * @param emailMessage
	 * 			- the email message containing the new bulletin message, must not be null.
	 * @return - the URL for the bulletin window with the new bulletin message specified.
	 */
	private String getAdminBulletinUrl(Message emailMessage) {
		String adminBulletinUrl = getAdminUrl() 
			+ "/bulletin.do?company_id=" + emailMessage.getCompany().getId()
			+ "&messageID=" + emailMessage.getId();
		return adminBulletinUrl;
	}

	/**
	 * 
	 * @return project/workspace path
	 */
	private String getRealPath() {
		ServletContext servCont = ServletActionContext.getServletContext();
		return servCont.getRealPath(getCompanyImageFolder()) + File.separator;
	}

	private String getCompanyImageFolder() {
		return "/companies/" + currentCompany.getName();
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @return the authorEmail
	 */
	public String getAuthorEmail() {
		return authorEmail;
	}

	/**
	 * @return the messageTitle
	 */
	public String getMessageTitle() {
		return messageTitle;
	}

	/**
	 * @return the recipientEmail
	 */
	public String getRecipientEmail() {
		return recipientEmail;
	}
	
	/**
	 * @return the messageList
	 */
	public List<Message> getMessageList() {
		return messageList;
	}
	
	/**
	 * @return the message
	 */
	public Message getMessageDetail() {
		return messageItem;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @param authorEmail the authorEmail to set
	 */
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	/**
	 * @param messageTitle the messageTitle to set
	 */
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	/**
	 * @param recipientEmail the recipientEmail to set
	 */
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSuccessUrl() {
		return successUrl;
	}

}
