package com.ivant.cms.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.action.admin.CategoryItemAction;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberImages;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.opensymphony.xwork2.Action;


public class EmailSenderAttachmentsAction implements Action, ServletRequestAware,
		CompanyAware,ServletContextAware{
	
	private static final long serialVersionUID = 7165567356270931069L;
	private static final Logger logger = Logger
			.getLogger(EmailSenderAttachmentsAction.class);
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate
			.getInstance();

	private static final String ATTACHMENT_FOLDER = "message_attachments";
	
	private HttpServletRequest request;
	private Company company;

	private String successUrl;
	private String errorUrl;
	
	private int maxFileSize;
	private File file;
	private String contentType;
	private String filename; 
	//private Company currentCompany;
	private String myEmail;
	private String error;
	
	private String smtp;
	private String portNumber;
	private String mailerUserName;
	private String mailerPassword;
	
	private ServletContext servletContext;
	private CompanySettings companySettings;
	
	PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private String uploadStatus = "false";
	private String newFilename;
	
	//----venture cap file uploads---
	
	private File validID;
	private String validIDFileName;
	private String validIDContentType;
	private File bill;
	private String billFileName;
	private String billContentType;
	private File checking;
	private String checkingFileName;
	private String checkingContentType;
	
	
	
	
	public String getValidIDContentType() {
		return validIDContentType;
	}

	public void setValidIDContentType(String validIDContentType) {
		this.validIDContentType = validIDContentType;
	}

	public String getBillContentType() {
		return billContentType;
	}

	public void setBillContentType(String billContentType) {
		this.billContentType = billContentType;
	}

	public String getCheckingContentType() {
		return checkingContentType;
	}

	public void setCheckingContentType(String checkingContentType) {
		this.checkingContentType = checkingContentType;
	}

	public File getValidID() {
		return validID;
	}

	public void setValidID(File validID) {
		this.validID = validID;
	}

	public String getValidIDFileName() {
		return validIDFileName;
	}

	public void setValidIDFileName(String validIDFileName) {
		this.validIDFileName = validIDFileName;
	}

	public File getBill() {
		return bill;
	}

	public void setBill(File bill) {
		this.bill = bill;
	}

	public String getBillFileName() {
		return billFileName;
	}

	public void setBillFileName(String billFileName) {
		this.billFileName = billFileName;
	}

	public File getChecking() {
		return checking;
	}

	public void setChecking(File checking) {
		this.checking = checking;
	}

	public String getCheckingFileName() {
		return checkingFileName;
	}

	public void setCheckingFileName(String checkingFileName) {
		this.checkingFileName = checkingFileName;
	}
	
	public String getMyEmail() {
		return myEmail;
	}

	public void setMyEmail(String myEmail) {
		this.myEmail = myEmail;
	}
	
	@SuppressWarnings("unchecked")
	private TreeMap  tm = new TreeMap();
	
		
	public String execute() throws Exception {
		if(file != null) {
			long maxFileSizeInMB = maxFileSize * 1048576;
			if(file.length() > maxFileSizeInMB) {
				return ERROR;
			}
		} 
		
		error = "";
		Boolean valid=true;
		//email
		
		if(company.getId()== 46){
			// for ivant contact page
			if(request.getParameter("1b|email")!=null)
				 valid = EmailUtil.isAddressValid(request.getParameter("1b|email"));
			
			if(!valid)
			{
				error="error1";	
				//System.out.println("invalid eadd");
			}
		}


		//kaptcha
		Boolean kaptchaError = false;
		String kaptchaReceived = (String)request.getParameter("kaptcha");
		//System.out.println("kaptcha: "+kaptchaReceived);
		if(kaptchaReceived != null)
		{
			String kaptchaExpected = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			
			if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected))
			{
				error = "error2";
				kaptchaError = true;
				
				if(!valid && kaptchaError)
				{
					error = "error3";
				}
				
				
			}
		}
		
		/*
		 * Panasonic
		 */
		if(company.getName().equalsIgnoreCase("panasonic")) {
			uploadStatus = request.getParameter("uploadImage");
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			
			PromoCode model_num = promoCodeDelegate.findByNote(company, request.getParameter("1f|model_number"));
			if(model_num != null){
				PromoCode promo_code = promoCodeDelegate.findByCode(company, request.getParameter("1e|promo_code"));
				
				if(promo_code != null){
					
					if(promo_code.getIsDisabled() == true){
						errorUrl += "alreadysubmitted";
						return ERROR;
					}else{
						// make the promo not valid
						//promo_code.setIsValid(false);
						promo_code.setIsDisabled(true);
						promoCodeDelegate.update(promo_code);
						if(company.getName().equals("panasonic")){
							MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
							File[] file = r.getFiles("1h|file_upload");
							String[] filename = r.getFileNames("1h|file_upload");
							String[] contenttype = r.getContentTypes("1h|file_upload");
							
							if(file!=null)
							{
								logger.info("UPLOADING..............");
								saveImagesPanasonic(file, filename, contenttype);
								logger.info("UPLOADING SUCCESS !!!!!!!!!!!!!!!!!!!!!!!!!");
								
							}
						}
					}
					
				
				}else{
					errorUrl += "invalid";
					return ERROR;
				}
			}else{
				errorUrl += "modelinvalid";
				return ERROR;
			}
			
			
			
		}
		/*
		 * Panasonic
		 */
		
		
		if(company.getName().equalsIgnoreCase("agian")) {
			uploadStatus = request.getParameter("uploadImage");
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			
			Member check = memberDelegate.findAccount(request.getParameter("member_user"), company);
			if(check != null){
				if(uploadStatus.equals("true")){
					MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
					File[] file = r.getFiles("files");
					String[] filename = r.getFileNames("files");
					String[] contenttype = r.getContentTypes("files");
					
					if(file!=null)
					{
						logger.info("UPLOADING..............");
						saveImagesPanasonic(file, filename, contenttype);
						logger.info("UPLOADING SUCCESS !!!!!!!!!!!!!!!!!!!!!!!!!");
						
					}
				}
				
				String fname = request.getParameter("fname");
				String lname = request.getParameter("lname");
				String email = request.getParameter("email");
				String comp = request.getParameter("company_name");
				String tag = request.getParameter("tag");
				String nickname = request.getParameter("nickname");
				String mobile = request.getParameter("mobile");
				String birth = request.getParameter("info3");
				String expertise = request.getParameter("info4");
				String cert = request.getParameter("value2");
				String aff = request.getParameter("value3");
				String pos = request.getParameter("reg_companyPosition");
				/*String username = request.getParameter("username");*/
				/*String pass = request.getParameter("retype_password");*/
				
				check.setFirstname(fname);
				check.setLastname(lname);
				check.setEmail(email);
				check.setReg_companyName(comp);
				check.setInfo1(tag);
				/*check.setUsername(username);*/
				/*check.setPassword(pass);*/
				if(uploadStatus.equals("true")){
					System.out.println(newFilename);
					check.setInfo2(newFilename);
				}
				check.setInfo5(nickname);
				check.setMobile(mobile);
				check.setInfo3(birth);
				check.setInfo4(expertise);
				check.setValue2(cert);
				check.setValue3(aff);
				check.setReg_companyPosition(pos);
				memberDelegate.update(check);
				
				
				return Action.SUCCESS;
				
			}else{
				return Action.ERROR;
			}
		}
		
		
		if(!valid || kaptchaError)
			return Action.ERROR;

		if (!sendEmail()) {
				return Action.ERROR;
		}
		replyInquirer();
		
		return Action.SUCCESS;
	}
	
	private void saveImagesPanasonic(File[] files, String[] filenames, String[] contentTypes) {
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		destinationPath += File.separator + company.getName();
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "uploadedImages";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				
		// create the image1, image2, image3 and thumbnail folders
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image1"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image2"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image3"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "thumbnail"));
		
		destinationPath = servletContext.getRealPath(destinationPath);
		
		
		for(int i = 0; i < files.length; i++) {			
			if(files[i].exists()) {
				String source = files[i].getAbsolutePath();
				
				String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"), 
						String.valueOf(new Date().getTime()));	
				System.out.println(filename);
				newFilename = filename;
				/*
				//I removed this code because it intercept the gif / png upload, and change the file to JPG format
				filename = FileUtil.replaceExtension(filename, "jpg"); 
				*/
				
				// generate original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				FileUtil.copyFile(files[i], origFile);
				
				Long companyId = company.getId();
				// generate image 1
				
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename, 
								companySettings.getImage1Width(), companySettings.getImage1Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
					}
					else{
						ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename, 
								companySettings.getImage1Width(), companySettings.getImage1Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
					}
				}catch(Exception e){}
				
				// generate image 2
				
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename, 
								companySettings.getImage2Width(), companySettings.getImage2Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
					}
					else{
						ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename, 
								companySettings.getImage2Width(), companySettings.getImage2Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
					}
				}catch(Exception e){}
				
				// generate image 3
				
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename, 
								companySettings.getImage3Width(), companySettings.getImage3Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
					}
					else{
						ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename, 
								companySettings.getImage3Width(), companySettings.getImage3Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
					}
				}catch(Exception e){}
				
				// generate thumbnail
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
						
					}
					else{
						ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
						
					}
				}catch(Exception e){}
				
			}
		}
	}


	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@SuppressWarnings("unchecked")
	private boolean sendEmail() throws Exception {
		boolean emailSent = false;
		StringBuffer content = new StringBuffer();
		
		try {
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			String subject = request.getParameter("subject");
			String to = request.getParameter("to");
			String from = request.getParameter("from");
			String title = request.getParameter("title");	
			
			List<String> ignored = new ArrayList<String>();
			ignored.add("subject");
			ignored.add("to");
			ignored.add("from");
			ignored.add("title");
			ignored.add("submit");
			ignored.add("successurl");
			ignored.add("errorurl");
			ignored.add("se_formname");
			ignored.add("se_sender");
			ignored.add("se_email");
			ignored.add("se_phone");
			ignored.add("maxfilesize");

			Iterator<Map.Entry<String, String[]>> iterator = request
					.getParameterMap().entrySet().iterator();
			
			
/*
			content.append("<html112>");
			content.append("<head>");
			content.append("<title112>").append(title).append("</title>");
			content.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
			content.append("</head>");
			content.append("<body>");
			content.append("<span>").append("--------------------------------------------------").append("</span>");
			content.append("<br/><br/>");
			content.append("<p>");
*/			
			
			//ORIGINAL CODE
			/*content = content.append("\n" + title + "\n");
			content = content
					.append("--------------------------------------------------\n\n");*/
			while (iterator.hasNext()) {
				Map.Entry<String, String[]> entry = iterator.next();
				if (entry.getKey().contains("|")){
					tm.put(entry.getKey(), entry.getValue());
				}
				if (request.getParameter("se_hasDelimiter")==null || !(request.getParameter("se_hasDelimiter").equalsIgnoreCase("true"))) {
					if (entry != null) {
						if (!ignored.contains(entry.getKey().toLowerCase())) {
							//ORIGINAL CODE
							/*content = content.append(decodeKey(entry.getKey())
									+ " : " + request.getParameter(entry.getKey())
									+ "\n\n");*/
							
							content.append(decodeKey(entry.getKey())).append(" : ").append(request.getParameter(entry.getKey()));
							content.append("<br/><br/>");
						}
					} else {
						break;
					}
				}
			}
			
			//-------------------------------------
			
			
			if ((request.getParameter("se_hasDelimiter")!= null && request.getParameter("se_hasDelimiter").equalsIgnoreCase("true"))) {
				Set  set = tm.entrySet();
				Iterator i = set.iterator(); 
				boolean hasLabel = false;
				while (i.hasNext()){
					Map.Entry me = (Map.Entry)i.next();
					
					String requestStr=me.getKey().toString();
					String fieldName=decodeKey(me.getKey().toString().substring(me.getKey().toString().indexOf("|")+1));
					
					/*if(subject.equalsIgnoreCase("Online Inquiry Submission"))
					{
						subject = "Website Online Inquiry";
					}*/
					
					if(fieldName.equalsIgnoreCase("name") || me.getKey().toString().equalsIgnoreCase("name"))
					{
						if(!request.getParameter(requestStr).equals(""))
						{
							//subject = "Website Online Inquiry - " + request.getParameter(requestStr);
							subject = subject + " - " + request.getParameter(requestStr);
						}
					}
					if(requestStr.equalsIgnoreCase("1e|programs"))
					{		
						String[] programs = request.getParameterValues("1e|programs");						
						StringBuffer strPrograms = new StringBuffer();
						
						strPrograms.append(" : ");
						for(int j=0;j<programs.length;j++)
							strPrograms.append("("+(j+1)+") "+programs[j]+" ");
									
						content.append("Programs "+strPrograms.toString());
					}
					else if(requestStr.equalsIgnoreCase("11|label1") || requestStr.equalsIgnoreCase("22|label2") || requestStr.equalsIgnoreCase("44|label3") ||requestStr.equalsIgnoreCase("66|label4") || requestStr.equalsIgnoreCase("77|label5")) 
					{	
						hasLabel = true;
						if(requestStr.equalsIgnoreCase("11|label1"))
							content.append("<br/><strong>CHILD'S INFORMATION</strong>");
						else if(requestStr.equalsIgnoreCase("22|label2"))
							content.append("<br/><strong>PARENT'S INFORMATION</strong>");
						else if(requestStr.equalsIgnoreCase("44|label3"))
							content.append("<br/><strong>PERSON/S AUTHORIZED TO FETCH ME IN SCHOOL</strong>");
						else if(requestStr.equalsIgnoreCase("66|label4"))
							content.append("<br/><strong>WHY DID YOU ENROLL IN TUMBLE TOTS</strong>");
						else if(requestStr.equalsIgnoreCase("77|label5"))
							content.append("<br/><strong>HOW DID YOU LEARN ABOUT TUMBLE TOTS?</strong>");
					}
					else if(requestStr.equalsIgnoreCase("6a|reason")) 
					{
						if(request.getParameter("6a|reason").equalsIgnoreCase("REFERRAL BY"))
							content.append("Answer : Referral by "+request.getParameter("referral1"));
						else if(request.getParameter("6a|reason").equalsIgnoreCase("OTHERS"))
							content.append("Answer : "+request.getParameter("others1"));
						else
							content.append("Answer : "+request.getParameter(me.getKey().toString()));
					}
					else if(requestStr.equalsIgnoreCase("7a|answer")) 
					{
						if(request.getParameter("7a|answer").equalsIgnoreCase("REFERRAL BY"))
							content.append("Answer : Referral by "+request.getParameter("referral2"));
						else if(request.getParameter("7a|answer").equalsIgnoreCase("OTHERS"))
							content.append("Answer : "+request.getParameter("others2"));
						else
							content.append("Answer : "+request.getParameter(me.getKey().toString()));
					} 
					
					else if(requestStr.equalsIgnoreCase("1a|position_applied_for")) {
						content.append("I. PERSONAL INFORMATION <br/><br/>");
						content.append(decodeKey(me.getKey().toString().substring(me.getKey().toString().indexOf("|")+1))).append(" : ").append(request.getParameter(me.getKey().toString()));
					} else if(requestStr.equalsIgnoreCase("2a|elementary")){
						content.append("II. EDUCATIONAL BACKGROUND <br/><br/>");
						content.append(decodeKey(me.getKey().toString().substring(me.getKey().toString().indexOf("|")+1))).append(" : ").append(request.getParameter(me.getKey().toString()));
					} else if(requestStr.equalsIgnoreCase("2k|company_name_&_address")) {
						String html = "<table width='100%' border='0' cellspacing='0' cellpadding='5' align='center'>	"
							+	"		<tr>	"
							+	"			<td class='labelCareer' colspan='3'>III. WORK EXPERIENCE</td>	"
							+	"		</tr>	"
							+	"		<tr>	"
							+	"			<td width='20%'>Name & Address of Company</td>	"
							+	"			<td width='20%'>Position Held</td>	"
							+	"			<td width='20%'>Duration of Employment</td>	"
							+	"			<td width='20%'>Last Salary</td>	"
							+	"			<td width='20%'>Reason for Leaving</td>	"
							+	"		</tr>	";
						String[] companies = request.getParameterValues(requestStr);
						String[] positions = request.getParameterValues("position_held");
						String[] durations = request.getParameterValues("duration_of_employment");
						String[] salaries = request.getParameterValues("last_salary");
						String[] reasons = request.getParameterValues("reason_for_leaving");
						String workExperience = "";	
						for(int index=0; index<companies.length; index++) {
							workExperience += "<tr>	"
								+"		<td>"+companies[index]+"</td>	"
								+"		<td>"+positions[index]+"</td>	"
								+"		<td>"+durations[index]+"</td>	"
								+"		<td>"+salaries[index]+"</td>	"
								+"		<td>"+reasons[index]+"</td>	"
								+"	 </tr>";
						}
						html += workExperience + "</table>";
						content.append(html);
					} else if(requestStr.equalsIgnoreCase("2p|hobbies")) {
						String html = "<table width='100%' border='0' cellspacing='0' cellpadding='5' align='left'>	"
							+	"		<tr>	"
							+	"			<td class='labelCareer' colspan='3'>IV. HOBBIES, SPECIAL SKILLS, INTERESTS.</td>	"
							+	"		</tr>	"
							+	"		<tr>	"
							+	"			<td width='33%'>List of hobbies & Special Interests</td>	"
							+	"			<td width='33%'>Job-related Skills</td>	"
							+	"			<td width='33%'>Computer Skills</td>	"
							+	"		</tr>	";
						String[] hobbies = request.getParameterValues(requestStr);
						String[] jobSkills = request.getParameterValues("job-related_skills");
						String[] comSkills = request.getParameterValues("computer_skills");
						String hobbieshtml = "";	
						for(int index=0; index<hobbies.length; index++) {
							hobbieshtml += "<tr>	"
								+"		<td>"+hobbies[index]+"</td>	"
								+"		<td>"+jobSkills[index]+"</td>	"
								+"		<td>"+comSkills[index]+"</td>	"
								+"	 </tr>";
						}
						html += hobbieshtml + "</table>";
						content.append(html);
					} else if(requestStr.equalsIgnoreCase("2s|seminars")) {
						String html = "<table width='100%' border='0' cellspacing='0' cellpadding='5' align='left'>	"
							+	"		<tr>	"
							+	"			<td class='labelCareer' colspan='3'>V. List of seminars attended</td>	"
							+	"		</tr>	"
							+	"		<tr>	"
							+	"			<td width='33%'>Name of Seminars</td>	"
							+	"			<td width='33%'>Conducted by</td>	"
							+	"			<td width='33%'>Venue/Date held</td>	"
							+	"		</tr>	";
						String[] seminars = request.getParameterValues(requestStr);
						String[] conductsBy = request.getParameterValues("conducted_by");
						String[] venuesDates = request.getParameterValues("venue/date_held");
						String seminarHtml = "";	
						for(int index=0; index<seminars.length; index++) {
							seminarHtml += "<tr>	"
								+"		<td>"+seminars[index]+"</td>	"
								+"		<td>"+conductsBy[index]+"</td>	"
								+"		<td>"+venuesDates[index]+"</td>	"
								+"	 </tr>";
						}
						html += seminarHtml + "</table><br/><br/>";
						content.append(html);
					} else if (requestStr.equalsIgnoreCase("2t|other_information")) {
						content.append("VI. OTHER INFORMATION <br/><br/>");
						content.append("Have you been forced to resign from any of your previous employment?<br/>");
						String q1 = request.getParameter("isForcedResign1");
						content.append((q1.equals("No") ? q1 : q1 + " - " + request.getParameter("isForcedResignDetail"))+"<br/><br/>");
						content.append("Have you been convicted from any crime?<br/>");
						String q2 = request.getParameter("isConvicted1");
						content.append((q2.equals("No") ? q2 : q2 + " - " + request.getParameter("isConvictedDetail"))+"<br/><br/>");
						content.append("Did you suffer from any serious disease or ailment?<br/>");
						String q3 = request.getParameter("isIll1");
						content.append((q3.equals("No") ? q3 : q3 + " - " + request.getParameter("isIllDetail"))+"<br/><br/>");
						content.append("Do you possess a driver's license?<br/>");
						String q4 = request.getParameter("hasDriversLicense1");
						content.append((q4.equals("No") ? q4 : q4 + " - " + request.getParameter("isPro1"))+"<br/><br/>");
						content.append("Residence Details<br/>"+request.getParameter("residenceDetails"));
					}
					
					else {
						content.append(decodeKey(me.getKey().toString().substring(me.getKey().toString().indexOf("|")+1))).append(" : ").append(request.getParameter(me.getKey().toString()));
						content.append("<br/><br/>");
					}
					
					//ORIGINAL CODE
					/*content = content.append(decodeKey(me.getKey().toString().substring(me.getKey().toString().indexOf("|")+1)));
					content.append(" : " + request.getParameter(me.getKey().toString()));
					content.append("\n\n");*/
					
					
					
					MultiPartRequestWrapper s = (MultiPartRequestWrapper) request;
					File[] sfile = s.getFiles("1h|file_upload");
					
					if(hasLabel) {
						content.append("<br/><br/>");
					}
					else if(sfile == null)
					{
						content.append("<br/><br/>");
					}
					else
					{						
						content.append("\r\n\r\n");
					}
					
					//System.out.print(me.getKey() + ": ");
					//System.out.println(request.getParameter(me.getKey().toString()));
				}
			}

/*			
			content.append("</p>");
			content.append("</body>");
			content.append("</html112>");
*/			
			
//			file = new File(request.getParameter("1h|attachment"));
			
			if(company.getId() == 336) {
				String testimonial = request.getParameter("se_testimonial");
				if(testimonial.equals("Web Form")) {
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyMMdd");
					String strDate = sdf.format(date);
					ObjectList<SavedEmail> list = savedEmailDelegate.findByTestimonial(company, sdf.parse(strDate), testimonial);
					if(list != null){
						subject = subject + " Q"+sdf2.format(date)+""+String.format("%02d",list.getSize()+1);
						}
					else{
						subject = subject + " Q"+sdf2.format(date)+""+01;}
				}
			}
			//--------------------------------------
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			
			MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
			File[] file = r.getFiles("1h|file_upload");
			String[] filename = r.getFileNames("1h|file_upload");
			
			if(company.getName().equals("stockbridge") || company.getName().equals("lilys2") || company.getName().equals("neltex") || company.getName().equals("itsa")){
				file = r.getFiles("10z|file_upload");
				filename = r.getFileNames("10z|file_upload");
			}
			
			if(company.getName().equals("metroquicash")) {
				file = r.getFiles("2a|file_upload");
				filename = r.getFileNames("2a|file_upload");
			}
			
			if((file != null && file.length > 0) && (filename != null && filename.length > 0)){
				
				//comment by jayvie
				//String path =  getRealPath() + ATTACHMENT_FOLDER + File.separator;
				String path = ATTACHMENT_FOLDER + File.separator;
				
				File uploadedFileDestination = new File(path);
				
				if(!uploadedFileDestination.exists()){
					uploadedFileDestination.mkdirs();
				}
				
				File dest = new File(path + filename[0]);					
				FileUtil.copyFile(file[0], dest);
				
		        //System.out.println("with file");
				if(filename.length > 1)
				{
				  File[] destnames = new File[filename.length];
				  String[] filenames = new String[filename.length];
				
				  for(int i=0; i<filenames.length;i++)
				  {
				    destnames[i] = new File(path + filename[i]);
				    FileUtil.copyFile(file[i], destnames[i]);
				    filenames[i] = destnames[i].getAbsolutePath();
				  }
				  
				  emailSent = EmailUtil.sendWithManyAttachments(from, to.split(","), subject, content.toString(), filenames);
				  if(company.getName().equals("metroquicash")) {
					  for(int i=0; i<filenames.length;i++)
					  {
						  destnames[i] = new File(path + filename[i]);
						  filenames[i] = destnames[i].getAbsolutePath();
						  FileUtil.deleteFile(filenames[i]);
					  }
				  }
				}
				else
				{
				//emailSent = EmailUtil.send(from, to.split(","), subject, content.toString(), dest.getAbsolutePath());
				  emailSent = EmailUtil.sendWithHTMLFormat(from, to.split(","), subject, content.toString(), dest.getAbsolutePath());
				}
				
				//dest.delete();
			} else {
				////System.out.println("without file");
				
				//emailSent = EmailUtil.send(from, to.split(","), subject, content.toString(), null);
				emailSent = EmailUtil.sendWithHTMLFormat(from, to.split(","), subject, content.toString(), null);				
			}
			
//			logger.info("File Attachment " + file);
//			String uploadDir = ServletActionContext.getServletContext().getRealPath("temp") + File.separator;
//			//filename = uploadDir + "webapps/ROOT/companies/" + company + File.separator + file.getName();
//			
//			filename = uploadDir + file.getName();
//			
//			System.out.println("Email upload directory and filename: "+uploadDir+filename);
//			System.out.println("Email upload directory: "+uploadDir);
//			System.out.println("Email filename: "+filename);
//			
//			if(filename != null) {
//				File dest = renameTempFile(file, filename);
//				content.append("destinations: ");
//				content.append(dest);
//				emailSent = EmailUtil.send(from, to.split(","), subject, content.toString(), dest.getAbsolutePath());
//			}
//			else { 
//				emailSent = EmailUtil.send(from, to.split(","), subject, content.toString(), null);
//			}
		} catch (Exception e) {
			logger.debug("failed to send email in " + company.getName()
					+ "... " + e);
		}
 
		saveEmailInformation(content.toString());

		return emailSent;
	}

	private File renameTempFile(File source, String fn) {  
		String[] decoded = fn.split("[.]"); 
		 
		try { 
			File dest = File.createTempFile("attachment ", "." + decoded[decoded.length-1]);
			FileUtil.copyFile(source, dest);
			return dest;
		}
		catch(IOException ioe) {
			logger.fatal("unable to rename temp file", ioe);
		}
		return null; 
	}
	
	private void saveEmailInformation(String emailContent) {
		try {
			String formName = request.getParameter("se_formName");
			String sender = request.getParameter("se_sender");
			String senderEmail = request.getParameter("se_email");
			String senderPhone = request.getParameter("se_phone");
			String testimonial = request.getParameter("se_testimonial");
			
			MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
			File[] file = r.getFiles("1h|file_upload");
			String[] filename = r.getFileNames("1h|file_upload");
			
			String uploadFileName = "";
			
			if(filename != null)
			{
				uploadFileName = filename[0];
			}

			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(sender);
			savedEmail.setEmail(senderEmail);
			savedEmail.setPhone(senderPhone);
			savedEmail.setFormName(formName);
			savedEmail.setEmailContent(emailContent);
			
			if(company.getName().equals("panasonic")){
				savedEmail.setTestimonial(request.getParameter("1b|address"));
				savedEmail.setUploadFileName(newFilename);
			}else{
				savedEmail.setTestimonial(testimonial);
				savedEmail.setUploadFileName(uploadFileName);
			}
			if(company.getName().equals("panasonic")){
				savedEmail.setPromo(request.getParameter("1e|promo_code"));
				savedEmail.setReceipt(request.getParameter("1f|model_number"));
			}
			
			long id = savedEmailDelegate.insert(savedEmail);
			
			if(company.getName().equals("metroquicash")) {
				savedEmail = savedEmailDelegate.find(id);
				file = r.getFiles("2a|file_upload");
				filename = r.getFileNames("2a|file_upload");
				File[] destnames = new File[filename.length];
				String[] filenames = new String[filename.length];
				String path = getRealPath() + ATTACHMENT_FOLDER + File.separator;
				uploadFileName = "";
				for(int i=0; i<filename.length; i++) {
					String fname = FileUtil.insertPostfix(filename[i].replace(" ", "_"), 
							String.valueOf(id));
					destnames[i] = new File(path + fname);
				    FileUtil.copyFile(file[i], destnames[i]);
				    filenames[i] = destnames[i].getAbsolutePath();
				    uploadFileName += (uploadFileName.equals("") ? fname : "#%#"+fname);
				}
				savedEmail.setUploadFileName(uploadFileName);
				savedEmailDelegate.update(savedEmail);
			}
			
		} catch (Exception e) {
			logger.debug("failed to save the email information for "
					+ company.getName());
		}
	}
	
	public String sendLoanApplicationByVenuturecap() throws Exception, IOException {
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		try {
			
			
			String dateOfApplication = (String) request.getParameter("dateOfApplication");
			String amount = (String) request.getParameter("amount");
			String term = (String) request.getParameter("term");
			String purpose = (String) request.getParameter("purpose");
			
			String lname = (String) request.getParameter("lname");
			String fname = (String) request.getParameter("fname");
			String mname = (String) request.getParameter("mname");
			
			String address = (String) request.getParameter("address");
			String mobile = (String) request.getParameter("mobile");
			String telephone = (String) request.getParameter("telephone");
			
			String email = (String) request.getParameter("email");
			String facebook = (String) request.getParameter("facebook");
			String fax = (String) request.getParameter("fax");
			
			
			String civil = (String) request.getParameter("civil");
			String nationality = (String) request.getParameter("nationality");
			String age = (String) request.getParameter("age");
			
			String employer = (String) request.getParameter("employer");
			String employment = (String) request.getParameter("employment");
			String businessTel = (String) request.getParameter("businessTel");
			
			String sss = (String) request.getParameter("sss");
			String tin = (String) request.getParameter("tin");
			

			String from = request.getParameter("from");
			String subject = "Loan Application";
			
			StringBuffer content = new StringBuffer();
			
			content.append("<h2>LOAN DETAILS</h2>");
			content.append("Date of Application: "+dateOfApplication+"<br/>");
			content.append("Applied Applied For: <b>Php "+amount+"</b><br/>");
			content.append("Term: "+term+"<br/>");
			content.append("Purpose: "+purpose+"<br/><br/>");
			
			
			content.append("<h2>PERSONAL INFORMATION</h2>");
			content.append("Name: "+lname+","+fname+" "+mname+"<br/>");
			content.append("Address: "+address+"<br/>");
			content.append("Mobile Number: "+mobile+"<br/>");
			content.append("Telephone Number: "+telephone+"<br/>");
			
			content.append("Email Address: "+email+"<br/><br/>");
			content.append("Facebook Account: "+facebook+"<br/>");
			content.append("Fax: "+fax+"<br/>");
			
			content.append("Civil Status: "+civil+"<br/>");
			content.append("Nationality: "+nationality+"<br/><br/>");
			content.append("Age: "+age+"<br/>");
	
			content.append("<h2>EMPLOYER/BUSINESS INFORMATION</h2>");
			content.append("Employer/Business: "+employer+"<br/>");
			content.append("Date Started: "+employment+"<br/>");
			content.append("Business Telephone Number: "+businessTel+"<br/>");
			content.append("SSS/GSIS: "+sss+"<br/>");
			content.append("TIN: "+tin+"<br/>");
			

			MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
			File[] files = r.getFiles("files");
			String[] filename = r.getFileNames("files");
			String[] contenttype = r.getContentTypes("files");
			
			String path = servletContext.getRealPath("/companies/venturecap/uploads");
			System.out.println(path);
			logger.info(path);
			
			String[] attachments = new String[files.length];
			
			int count = 0;
			for(File file : files){
				filename[count] = System.currentTimeMillis() + "." + FilenameUtils.getExtension(filename[count]);
				FileUtils.copyFile(file, new File(path + File.separator + filename[count]));
				attachments[count] = path + File.separator + filename[count];
				
				count++;
			}
			
			/*String[] fileNames = new String[10];
			
			
			//test
			
			
			
			if(validIDFileName != null){
				filename = System.currentTimeMillis() + "." + FilenameUtils.getExtension(validIDFileName);
				FileUtils.copyFile(validID, new File(path + File.separator + filename));
				fileNames[0] = path + File.separator + filename;
			}
			
			if(billFileName != null){
				filename = System.currentTimeMillis() + "." + FilenameUtils.getExtension(billFileName);
				FileUtils.copyFile(bill, new File(path + File.separator + filename));
				fileNames[1] = path + File.separator + filename;
			}
			
			if(checkingFileName != null){
				filename = System.currentTimeMillis() + "." + FilenameUtils.getExtension(checkingFileName);
				FileUtils.copyFile(checking, new File(path + File.separator + filename));
				fileNames[2] = path + File.separator + filename;
			}*/
				

			String[] toEmails = company.getEmail().split(",");
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			
			EmailUtil.sendWithManyAttachments(from, toEmails, subject, content.toString(), attachments);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	private String decodeKey(String key) {
		return firstLetterWordUpperCase(key.replace("_", " "));
	}

	private String firstLetterWordUpperCase(String s) {
		char[] chars = s.trim().toLowerCase().toCharArray();
		boolean found = false;

		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i])) {
				found = false;
			}
		}

		return String.valueOf(chars);
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
	
	public void setUpload(File file) {
		this.file = file;
	}
	
	public void setUploadContentType(String contentType) {
		this.setContentType(contentType);
	}
	
	public void setUploadFileName(String filename) {
		this.filename = filename;
	}
	
	public File getUpload(){
		return file;
	}
	
	public String getUploadFileName(){
		return filename;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}
	
	private String getRealPath() {
		ServletContext servCont = ServletActionContext.getServletContext();
		return servCont.getRealPath(getCompanyImageFolder()) + File.separator;
	}

	private String getCompanyImageFolder() {
		return "/companies/" + company.getName();
	}
	
	public String sendZunicBrochure(){

//		System.out.println("MaxFileSize: " + maxFileSize);
		//System.out.println("send zunic brochure =================================================");
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		
//		MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
//		File file = new File("...");
//		String filename = "...";
//		
//		if((file != null && file.length > 0) && (filename != null && filename.length > 0)){
			
		if (request.getParameter("email")!=null)
			myEmail = request.getParameter("email");
		
		
			String path =  getRealPath() +  File.separator ;
			//System.out.println("path ===== " + path);
			File uploadedFileDestination = new File(path);
			
			if(!uploadedFileDestination.exists()){
				uploadedFileDestination.mkdirs();
			}
			//File file = new File(path + "Zunic_Brochure.pdf");
			File dest = new File(path + "Zunic_Brochure.pdf");					
			//FileUtil.copyFile(file, dest);
			String from="noreply@webtogo.com.ph";
			//System.out.println("myEmail===" + myEmail);
			String to=myEmail;
			String subject = "Zunic Brochure Download";
			String content="\n\nPer your request, we have sent our brochure to this email address.";
			
			EmailUtil.send(from, to, subject, content, dest.getAbsolutePath());
			//dest.delete();

		
		return Action.SUCCESS;
		
		
	}

	
	public String sendWelcomeEmail(){

		//System.out.println("send welcome email =================================================");
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
					
		if (request.getParameter("email")!=null)
			myEmail = request.getParameter("email");
		
			String from="noreply@webtogo.com.ph";
			//System.out.println("myEmail===" + myEmail);
			String to=myEmail;
			String subject = "Cose Bags User Signup";
			String content="\n\nThank you for signing up in our Cose Bags website. You can access the website sections using the details below:" +
					"\n\n" +
					"Password: ilovecose" +
					"\n\n" + 
					"Happy browsing!" +
					"\n\n" + 
					"From The Cose Bags Team" ;
			EmailUtil.send(from, to, subject, content, null);
		
		return Action.SUCCESS;
		
		
	}
	
	public String sendUrbanEmail(){

		//System.out.println("send welcome email =================================================");
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		
			
		if (request.getParameter("email")!=null)
			myEmail = request.getParameter("email");
		
			String from="noreply@webtogo.com.ph";
			//System.out.println("myEmail===" + myEmail);
			String to=myEmail;
			String subject = "Urban Bags User Signup";
			String content="\n\nThank you for signing up in our Urban Bags website. You can access the website sections using the details below:" +
					"\n\n" +
					"Password: iloveurbanbags" +
					"\n\n" + 
					"Happy browsing!" +
					"\n\n" + 
					"From The Urban Bags Team" ;
			EmailUtil.send(from, to, subject, content, null);

		return Action.SUCCESS;
		
		
	}
	
	public String sendStreetDanceEmail(){

		//System.out.println("send welcome email =================================================");
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			
		if (request.getParameter("email")!=null)
			myEmail = request.getParameter("email");
		
			String from="noreply@webtogo.com.ph";
			//System.out.println("myEmail===" + myEmail);
			String to=myEmail;
			String subject = "Welcome to Skechers Street Dance Battle!";
			String content="\n\nHi! \n\nWelcome to Skechers Street Dance Battle! By signing up for our newsletter, you will now have access to the inside scoop, discounts and freebies from Skechers!" +
					"\n\n" +
					"Thanks for registering." +
					"\n\n" + 
					"All the best," +
					"\n\n" + 
					"From The Skechers Street Dance Team" ;
			EmailUtil.send(from, to, subject, content, null);

		return Action.SUCCESS;
		
		
	}
	
	public String sendSkechersEmail(){

		//System.out.println("send welcome email =================================================");
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			
		if (request.getParameter("email")!=null)
			myEmail = request.getParameter("email");
		
			String from="noreply@webtogo.com.ph";
			//System.out.println("myEmail===" + myEmail);
			String to=myEmail;
			String subject = "Welcome to Skechers!";
			String content="\n\nHi! \n\nWelcome to Skechers! By signing up for our newsletter, you will now have access to the inside scoop, discounts and freebies from Skechers!" +
					"\n\n" +
					"Thanks for registering." +
					"\n\n" + 
					"All the best," +
					"\n\n" + 
					"The Skechers Team" ;
			EmailUtil.send(from, to, subject, content, null);

		return Action.SUCCESS;
		
		
	}
	
	public String sendCrownEmail(){

		//System.out.println("send welcome email =================================================");
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		
		if (request.getParameter("email")!=null)
			myEmail = request.getParameter("email");

			String from="noreply@webtogo.com.ph";
			//System.out.println("myEmail===" + myEmail);
			String to=myEmail;
			String subject = "Crown Supply Signup";
			String content="\n\nCreative Sudio Watercolor Pencil Basics by Faber-Castell." +
					"\n\n" +
					"Learn how to use watercolor pencils and apply the Faber-Castell art techniques." +
					"\n\n" +
					"You can download the Free e-Book here:" +
					"\n\n" + 
					"http://www.crownsupplymanila.com/downloads/free_e_book_creative_studio_dwload/creative_studio.pdf" +
					"\n\n\n" + 
					"from The Crown Supply Team" ;
			EmailUtil.send(from, to, subject, content, null);

		return Action.SUCCESS;
		
		
	}
	
	public String sendCrown(){

		//System.out.println("send welcome email =================================================");
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		
		if (request.getParameter("email2")!=null)
			myEmail = request.getParameter("email2");

			String from="noreply@webtogo.com.ph";
			//System.out.println("myEmail===" + myEmail);
			String to=myEmail;
			String subject = "Crown Supply Signup";
			String content="\n\nDraw with Oinstein by Faber-Castell" +
					"\n\n" +
					"Learn fun drawing using various Faber-Castell coloring materials. Draw great animal cartoons using basic shapes in no-time." +
					"\n\n" +
					"You can download the Free e-Book here:" +
					"\n\n" + 
					"http://www.crownsupplymanila.com/downloads/free_e_book_draw_with_oinstein_download/draw_with_oinstein.pdf" +
					"\n\n\n" + 
					"from The Crown Supply Team" ;
			EmailUtil.send(from, to, subject, content, null);

		return Action.SUCCESS;
		
		
	}
	
	public String sendScriberiteEmail(){

		//System.out.println("send welcome email =================================================");
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			
		if (request.getParameter("email")!=null)
			myEmail = request.getParameter("email");
		
			String from="noreply@webtogo.com.ph";
			//System.out.println("myEmail===" + myEmail);
			String to=myEmail;
			String subject = "PDF Download Link";
			String content="\n\nThank you for your interest in our Article 10 Things You Need To Know About Medical Transcription Business" +
					"\n\n" +
					"You can download the free PDF here:" +
					"\n\n" + 
					"http://www.scriberite.com/downloads/pdf/10_things_to_know_about_medical_transcription_business.pdf" +
					"\n\n\n" + 
					"from Scriberite Team" ;
			EmailUtil.send(from, to, subject, content, null);
		
		return Action.SUCCESS;
		
		
	}
	
	public String sendEcommerceEmail(){

		//System.out.println("send welcome email =================================================");
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			
		if (request.getParameter("email")!=null)
			myEmail = request.getParameter("email");
		
			String from="noreply@webtogo.com.ph";
			//System.out.println("myEmail===" + myEmail);
			String to=myEmail;
			String subject = "Welcome to One Stop Shop Market!";
			String content="\n\nHi! \n\nWelcome to One Stop Shop Market! By signing up for our newsletter, you will now have access to the inside scoop, discounts and freebies from One Stop Shop Market!" +
					"\n\n" +
					"Thanks for registering." +
					"\n\n" + 
					"All the best," +
					"\n\n" + 
					"One Stop Shop Market" ;
			EmailUtil.send(from, to, subject, content, null);

		return Action.SUCCESS;
		
		
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	/* for Custom Email Settings */

	/*nagsesend ng notification message dun sa naginquire*/
	public void replyInquirer(){
		boolean sent = false;
		if(company.getCompanySettings().getAutoAnswer() || company.getName().equals("panasonic")){
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName,
					mailerPassword);
			
			String message = company.getCompanySettings().getMessage();
			String subject = company.getCompanySettings().getSubject();
			
			if(company.getName().equals("panasonic")){
				subject = "Panasonic and NBA All-Star Promo";
				message = "Thank you for joining the Panasonic Basketball Dreams Promo. This is equivalent to 1 raffle entry for the draw.";
			}
			
			sent = EmailUtil.sendWithHTMLFormat(mailerUserName, request.getParameter("se_email"), subject, message,null);
			
		}
		
		
	}
	/*==========---------------===========*/
	
	/*
	 * this function is called to check and set the email settings of 
	 * a company.
	 *
	 *
	 */
	
	public void setEmailSettings() {

		if (company.getCompanySettings().getEmailUserName() != null
				&& !company.getCompanySettings().getEmailUserName().equals("")) {
			setMailerUserName(company.getCompanySettings().getEmailUserName());
		} else {
			setMailerUserName(EmailUtil.DEFAULT_USERNAME);
		}
		if (company.getCompanySettings().getEmailPassword() != null
				&& !company.getCompanySettings().getEmailPassword().equals("")) {
			setMailerPassword(company.getCompanySettings().getEmailPassword());
		} else {
			setMailerPassword(EmailUtil.DEFAULT_PASSWORD);
		}
		if (company.getCompanySettings().getSmtp() != null
				&& !company.getCompanySettings().getSmtp().equals("")) {
			setSmtp(company.getCompanySettings().getSmtp());
		} else {
			setSmtp("smtp.gmail.com");
		}
		if (company.getCompanySettings().getPortNumber() != null
				&& !company.getCompanySettings().getPortNumber().equals("")) {
			setPortNumber(company.getCompanySettings().getPortNumber());
		} else {
			setPortNumber("587");
		}
	}

	/* Custom Email Settings */

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public void setMailerUserName(String mailerUserName) {
		this.mailerUserName = mailerUserName;
	}

	public void setMailerPassword(String mailerPassword) {
		this.mailerPassword = mailerPassword;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}