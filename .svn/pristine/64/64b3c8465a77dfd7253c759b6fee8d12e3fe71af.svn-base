package com.ivant.cms.action.admin;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import org.apache.log4j.Logger;

import com.ivant.cms.entity.ContactUs;
import com.ivant.cms.delegate.ContactUsDelegate;
import com.ivant.cms.delegate.PageImageDelegate;

import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.FileUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ivant.utils.EmailUtil;

public class ContactUsAction extends ActionSupport implements Preparable, ServletContextAware,
ServletRequestAware, UserAware {
	
	private static final long serialVersionUID = 4137618690542101030L;
	private Logger logger = Logger.getLogger(getClass());
	private ContactUsDelegate contactUsDelegate = ContactUsDelegate.getInstance();
	private PageImageDelegate imageDelegate = PageImageDelegate.getInstance();
	
	private User user; 
	private HttpServletRequest request;
	private ServletContext servletContext;
	private ContactUs contactus;
	
	private PageImage image;
	
	private File[] files;
	private String[] contentTypes;
	private String[] filenames;
	private boolean update = false;
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setContactUs(ContactUs cu) {
		this.contactus = cu;
	}

	public ContactUs getContactUs() {
		return contactus ;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}
	
	public String edit()
	{
		contactus = contactUsDelegate.findContactUsByCompany(user.getCompany().getId());
		//System.out.println("contactus to jsp: " + contactus);
		request.setAttribute("contactus", contactus);
		return Action.SUCCESS;
	}
	
	public String save() {
		
		if(user.getCompany() == null) {
			return Action.ERROR;
		}

		contactus = contactUsDelegate.findContactUsByCompany(user.getCompany().getId());
		//System.out.println("contact us: " + contactus);
//		if (contactus != null)
//		{
//	  	contactus.setTitle(request.getParameter("contactUs.title"));
//	  	contactus.setDetails(request.getParameter("contactUs.details"));
//	  	contactus.setMaps(request.getParameter("contactUs.maps"));
//	  	contactus.setEmail(request.getParameter("contactUs.email"));
//			if (contactUsDelegate.find(contactus.getId()) != null) 
//					{
//					contactUsDelegate.update(contactus);
//					request.setAttribute("contactUs", contactus);
//					}
//		}
		
		if(update) {
			
			contactus.setUpdatedOn(new Date());
			contactUsDelegate.update(contactus);
			saveImages(files, filenames);
		}
		else {
			logger.debug("---------------------");
			logger.debug(contactus);
			if(contactUsDelegate.find(user.getCompany().getId()) != null) {	
				long contactusId = contactUsDelegate.insert(contactus);
				contactus.setId(contactusId);
				saveImages(files, filenames);
			}
		}		
		return Action.SUCCESS;
	}

	public String deleteImage() {
		if(user.getCompany() == null) {
			return Action.ERROR;
		}
		if( !user.getCompany().equals(contactus.getCompany()) )  {	
			return Action.ERROR;
		}
		if(!contactus.providePageType().equals(image.getPage().providePageType())) {
			return Action.ERROR;
		}
		
		imageDelegate.delete(image);
		
		return Action.SUCCESS;
	}
	
	private void saveImages(File[] files, String[] filenames) {
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + user.getCompany().getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "uploads";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		String relative = "../" + destinationPath.replace('\\', '/');
		destinationPath = servletContext.getRealPath(destinationPath);
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
//				File source = files[i];
//				File destination = new File(destinationPath + File.separator + filenames[i]);
//				String filename = filenames[i];
//				 
//				FileUtil.copyFile(source, destination);
//				
//				PageImage image = new PageImage(contactus, 
//						destination.getAbsolutePath(), 
//						relative + "/" +filename,
//						filename);
//				imageDelegate.insert(image);
			}
		}
	}
	
	
	// upload methods
	
	public void setUpload(File[] files) {
		this.files = files;
	}
	
	public void setUploadContentType(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}
	
	public void setUploadFileName(String[] filenames) {
		this.filenames = filenames;
	}
	
	public void prepare() throws Exception {
		try {
			long contactusId = Long.parseLong(request.getParameter("contactus_id"));
			contactus = contactUsDelegate.find(contactusId);
		}
		catch(Exception e) {
			contactus = new ContactUs();
			contactus.setCreatedOn(new Date());
			contactus.setUpdatedOn(new Date());
			contactus.setCreatedBy(user);
			contactus.setCompany(user.getCompany());
		}	
		
		try {
			long imageId = Long.parseLong(request.getParameter("image_id"));
			image = imageDelegate.find(imageId);
		}
		catch(Exception e) {}

		if(request.getParameter("update") != null) {
			update = true;
		}
	}

	@SuppressWarnings("unchecked")
	public String send() {
		String recipient = "noel@ivant.com";   // CHANGE THIS EMAIL ADDRESS
		StringBuffer st = new StringBuffer();
		Map map;
		
		map = request.getParameterMap();
		
		Iterator it = map.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pairs = (Map.Entry)it.next();
      //System.out.println(pairs.getKey() + " = " + pairs.getValue());
      st.append (pairs.getKey() + ": " + pairs.getValue() + "\n");
  }
		
//		st.append("Name: " + getName() + "\n");
//		st.append("Company: " + getCompany() + "\n");
//		st.append("ContactNumber: " + getContactNumber() + "\n");
//		st.append("Subject: " + getSubject() + "\n\n");
//		st.append("Message: " + getMessage() + "\n");
    
		if(EmailUtil.send(contactus.getEmail(), recipient, "WebToGo", 
				st.toString())) {
			addActionMessage("Thank you. We will inform you as soon as possible after we process your request.");
		}else
			addActionMessage("Send Email Failed!");
//		includes();
    
    
		return SUCCESS;
	}
	
     //-------------------------------------------
	@SuppressWarnings("unchecked")
	public static void sendTest(ContactUs cu) {
		String recipient = "noel@ivant.com";   // CHANGE THIS EMAIL ADDRESS
		StringBuffer st = new StringBuffer();

		st.append(cu.getTitle()+ "\n");
		st.append(cu.getDetails()+ "\n");
		st.append(cu.getEmail()+ "\n");
    //System.out.println("sending..");
		EmailUtil.connect("www.ivant.com",25);
		EmailUtil.send(cu.getEmail(), recipient, "WebToGo", 
				st.toString());
    
	}
	//-----------------------------------------------
	
	
	public static void main (String args[])
	{
		ContactUs ccuu = new ContactUs("cu_title", "cu_details", "cu_email@yahoo.com");
		sendTest(ccuu);
	}
	
}
