package com.ivant.cms.action.company.json;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.io.FileUtils;
import org.hibernate.criterion.Order;
import org.json.simple.JSONObject;
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.dataSource.GemAttachmentDatasource;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.utils.Encryption;
import com.ivant.utils.FileUtil;
import com.ivant.utils.PagingUtil;
import com.ivant.utils.PasswordEncryptor;
import com.ivant.utils.StringUtil;

public class MundipharmaJSONAction extends AbstractBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1424925375315788744L;
	
	private InputStream inputStream;
	
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	private final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	
	private String smtp;
	private String portNumber;
	private String mailerUserName;
	private String mailerPassword;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/yyyy HH:mm");
	
	private PasswordEncryptor encryptor;
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	public String forgotPassword() {
		try {
			JSONObject obj = new JSONObject();
			
			String pageModule = request.getParameter("pageModule");
			String email = request.getParameter("email");
			
			Member member = memberDelegate.findAllByEmailModule(pageModule, email, company);
			
			if(member == null) {
				obj.put("success", false);
				obj.put("errorMessage", "The email address you entered does not exist in our database.");
			}

			if(member != null) {
				String newPass;
				newPass = StringUtil.generateRandomString();
				PasswordEncryptor encryptor=new PasswordEncryptor();
				member.setPassword(encryptor.encrypt(newPass));
				MemberDelegate memberDelegate=MemberDelegate.getInstance();
				memberDelegate.update(member);
				
				logger.debug(newPass);
				if(!sendEmail(member, newPass)) {
					obj.put("success", true);
					obj.put("errorMessage", "The account was not sent properly. Please contact support@webtogo.com.ph. Thank you");
				}
				else {
					obj.put("success", true);
				}
			}
			
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private boolean sendEmail(final Member member, String newPassword) {
		
		httpServer = (isLocal) ? ("http://" + request.getServerName() + ":"
				+ request.getServerPort() + "/" + servletContextName
				+ "/companies/" + company.getDomainName())
				: ("http://" + request.getServerName());
		
		StringBuffer content = new StringBuffer();
		/*content.append("<p>Password Recovery From " + company.getNameEditable()+"</p>");*/
		content.append("<p>You can use this generated Password to log-in.</p>");
		content.append("<p> Please Change your Password when you logged-in into your Account</p>" );
		content.append("<p>Username: " + member.getPageModuleUsername());
		content.append("<p>Password: " + newPassword + "</p><br>");	
		content.append("</p>You can use this link to login:</p><br> " + httpServer + "/gem.do");
		/*content.append("<br><br><br>Thank You Very Much, <br><br>" + company.getNameEditable());*/
		content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");
		final EmailSenderAction emailSenderAction = new EmailSenderAction();
		return emailSenderAction.sendEmailToGemMember(company, member.getEmail(), "Password Recovery From GEM!", content, null);
	}
	
	@SuppressWarnings("unchecked")
	public String sendEmailToGemMember() {
		try {
			final int gemLimit = 10;
			String from = request.getParameter("sendersName");
			String senderEmail = request.getParameter("senderEmail");
			String senderDepartment = request.getParameter("senderDepartment");
			String to = request.getParameter("selectedMember");
			String recipientID = request.getParameter("selectedMemberID");
			String selectedValueList = request.getParameter("selectedValueList");
			String onDate = request.getParameter("onDate");
			String byLetter = request.getParameter("byLetter");
			String becauseLetter = request.getParameter("becauseLetter");
			
			if(request.getParameter("selectedMember") != null && request.getParameter("selectedMember").equals("")
					&& request.getParameter("selectedValueList") != null && request.getParameter("selectedValueList").equals("")
					&& request.getParameter("onDate") != null && request.getParameter("onDate").equals("")
					&& request.getParameter("byLetter") != null && request.getParameter("byLetter").equals("")
					&& request.getParameter("becauseLetter") != null && request.getParameter("becauseLetter").equals("")) {
				JSONObject obj = new JSONObject();
				obj.put("success", false);
				obj.put("errorMessage", "All fields are required.");
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				return SUCCESS;
			}
			
			final EmailSenderAction emailSenderAction = new EmailSenderAction();
			int year = new Integer(onDate.split("/")[2]);
			int month = new Integer(onDate.split("/")[0])-1;
			int dayDate = new Integer(onDate.split("/")[1]);
			Date date = new Date(year,month,dayDate);
			Calendar calDate = Calendar.getInstance();
			calDate.setTime(date);
			calDate.set(Calendar.YEAR, year);
			date = calDate.getTime();
			
			int quarter = 1;
			String quarterStr = "1st";
			Date fromDate = date;
			Date toDate = date;
			if(date.getMonth()+1 <= 3) {
				Calendar cal = Calendar.getInstance();
			    cal.setTime(fromDate);
			    cal.set(Calendar.MONTH, 0);
			    fromDate = cal.getTime();
			    cal.set(Calendar.MONTH, 2);
				toDate = cal.getTime();
			} else if(date.getMonth()+1 <= 6) {
				Calendar cal = Calendar.getInstance();
			    cal.setTime(fromDate);
			    cal.set(Calendar.MONTH, 3);
			    fromDate = cal.getTime();
			    cal.set(Calendar.MONTH, 5);
				toDate = cal.getTime();
				quarter = 2;
				quarterStr = "2nd";
			} else if(date.getMonth()+1 <= 9) {
				Calendar cal = Calendar.getInstance();
			    cal.setTime(fromDate);
			    cal.set(Calendar.MONTH, 6);
			    fromDate = cal.getTime();
			    cal.set(Calendar.MONTH, 8);
				toDate = cal.getTime();
				quarter = 3;
				quarterStr = "3rd";
			} else {
				Calendar cal = Calendar.getInstance();
			    cal.setTime(fromDate);
			    cal.set(Calendar.MONTH, 9);
			    fromDate = cal.getTime();
			    cal.set(Calendar.MONTH, 11);
				toDate = cal.getTime();
				quarter = 4;
				quarterStr = "4th";
			}
			
			Calendar fromCalendar = Calendar.getInstance();
			fromCalendar.setTime(fromDate);
			fromCalendar.set(Calendar.DAY_OF_MONTH, 1);
			Date firstDayOfMonth = fromCalendar.getTime();
			
			Calendar toCalendar = Calendar.getInstance();
			toCalendar.setTime(toDate);
			toCalendar.set(Calendar.DATE, toCalendar.getActualMaximum(Calendar.DATE));

	        Date lastDayOfMonth = toCalendar.getTime();
			
			ObjectList<SavedEmail> quarterEmails = savedEmailDelegate.findEmailByEmailDateValidatedAndFormName(company, firstDayOfMonth, lastDayOfMonth, "GEM");
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			Date january = calendar.getTime();
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
			Date december = calendar.getTime();
			ObjectList<SavedEmail> savedEmails = savedEmailDelegate.findEmailByEmailDateValidatedAndFormName(company, january, december, "GEM");
			List<SavedEmail> emailList = new ArrayList<SavedEmail>();
			Integer gemCardCount = 1;
			if(savedEmails!=null){
				gemCardCount = 1;
				for(SavedEmail se : savedEmails.getList()) {
					if(se.getMember() != null) {
						if(se.getMember().getId() == member.getId()) {
							emailList.add(se);
						}
					} 
//					else if(se.getSender().equalsIgnoreCase(from)) {
//						emailList.add(se);
//					}
				}
				
				gemCardCount+=emailList.size();
			}
			
			List<SavedEmail> quarterList = new ArrayList<SavedEmail>();
			
			Integer count = 1;
			if(quarterEmails!=null){
				count = 1;
				for(SavedEmail se : quarterEmails.getList()) {
					if(se.getMember() != null) {
						if(se.getMember().getId() == member.getId()) {
							quarterList.add(se);
						}
					} 
//					else if(se.getSender().equalsIgnoreCase(from)) {
//						quarterList.add(se);
//					}
				}
				
				count+=quarterList.size();
			}
			
			if(senderEmail.equals("gerry.arnedo@mundipharma.com.ph") || senderEmail.equals("epmap_ellaine@mundipharma.com.ph") || senderEmail.equals("epmap_karen@mundipharma.com.ph")){
				if(count>25) {
					JSONObject obj = new JSONObject();
					obj.put("success", false);
					obj.put("errorMessage", "You have exceeded 25 GEM Cards for "+quarterStr+" quarter ("+year+").");
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
					return SUCCESS;
				}
			}else{
				if(count>10) {
					JSONObject obj = new JSONObject();
					obj.put("success", false);
					obj.put("errorMessage", "You have exceeded 10 GEM Cards for "+quarterStr+" quarter ("+year+").");
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
					return SUCCESS;
				}
			}
			
			
			
			int inquiryCountLength = gemCardCount.toString().length();
			String gemCardNo = member.getPageModuleUsername()+toCalendar.get(Calendar.YEAR)+String.format("%03d",gemCardCount);
			if(gemCardNo != null) gemCardNo = gemCardNo.toUpperCase();
			
			StringBuffer content = new StringBuffer();
			content.append(
					htmlMessageTemplate.replace("--to--", to.split("==")[0])
					.replace("--value_list--", selectedValueList)
					.replace("--onDate--", onDate)
					.replace("--by_letter--", byLetter)
					.replace("--because_letter--", becauseLetter)
					.replace("--GEM Card No.--", gemCardNo)
					.replace("--from--", from)
					);
			
			String attachment = null;
			// process attachment
			try{
				// create file
				String filePath = servletContext.getRealPath("") + File.separatorChar 
						+ "companies" + File.separatorChar 
						+ company.getName() + File.separatorChar 
						+ "pdf" + File.separator;
				FileUtil.createDirectory(filePath);
				File pdf = new File(filePath, "gem_attachment_" + System.currentTimeMillis() + ".pdf");
				
				// compile jasper report
				final JasperReport jr = JasperCompileManager.compileReport(JRLoader.getResourceInputStream("gem_attachment.jrxml"));
				
				// set report parameters
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("to", to.split("==")[0]);
				parameters.put("onDate", onDate);
				parameters.put("valueList", selectedValueList);
				parameters.put("byLetter", byLetter);
				parameters.put("becauseLetter", becauseLetter);
				parameters.put("from", from);
				parameters.put("cardNo", gemCardNo);
				
				// print report (with dummy datasource)
				final JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new GemAttachmentDatasource(Arrays.asList(new String[]{"test"})));
				byte[] pdfByteArray = JasperExportManager.exportReportToPdf(jp);				
				FileUtils.writeByteArrayToFile(pdf, pdfByteArray);
				
				attachment = pdf.getAbsolutePath();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(emailSenderAction.sendEmailToGemMember(company, to.split("==")[1], "Thanks for being a GEM!", content, attachment)){
				
				SimpleDateFormat sdf1 = new SimpleDateFormat("MM/DD/yyyy");
				
				SavedEmail savedEmail = new SavedEmail();
				savedEmail.setFormName("GEM");
				String emailContent = "Sender=="+from
						+"##Sender Department=="+senderDepartment
						+"##Recipient=="+to.split("==")[2]
						+"##Recipient Department=="+to.split("==")[3]
						+"##Gem Card No.=="+gemCardNo
						+"##Created Date/Time=="+sdf.format(getDateTime())
						+"##Values=="+selectedValueList
						+"##Specific Action=="+byLetter
						+"##Event Date/Time=="+onDate
						+"##Impact of Action=="+becauseLetter
						+"##Sender ID=="+member.getId()
						+"##Recipient ID=="+recipientID;
				savedEmail.setEmailContent(emailContent);
				savedEmail.setCompany(company);
				savedEmail.setSender(from);
				savedEmail.setEmail(senderEmail);
				savedEmail.setPhone("");
				savedEmail.setTestimonial("");
				savedEmail.setEmailDateValidated(date);
				savedEmail.setMember(member);
				
				savedEmailDelegate.insert(savedEmail);
				
				JSONObject obj = new JSONObject();
				obj.put("success", true);
				obj.put("gemCardNo", gemCardNo);
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			}
			else {
				JSONObject obj = new JSONObject();
				obj.put("success", false);
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String resetPassword() {
		try {
		
			String newPassword = request.getParameter("newPassword");
			String confirmPassword = request.getParameter("confirmPassword");

			encryptor = new PasswordEncryptor();
			encryptor.decrypt(member.getPassword());

			// encrypt new password
			newPassword = encryptor.encrypt(newPassword);
			// update new member password
			member.setPassword(newPassword);

			// update new member
			member.setUpdatedOn(new Date());
			memberDelegate.update(member);

			logger.debug("newPassword : " + newPassword);

			JSONObject obj = new JSONObject();
			obj.put("success", true);
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String saveMember() {
		try {
		
			String memberId = request.getParameter("memberId");
			String userName = request.getParameter("pageModuleUsername");
			String firstname = request.getParameter("firstName");
			String lastname = request.getParameter("lastName");
			String email = request.getParameter("email");
			String department = request.getParameter("department");
			String type = request.getParameter("type");
			
			JSONObject obj = new JSONObject();
			obj.put("success", false);
			
			List<Member> membersList = memberDelegate.findAllByPageModule("GEM", company).getList();
			List<Member> list = new ArrayList<Member>();
			
			Member member = new Member();
			
			if(!memberId.equalsIgnoreCase("0")) {
				member = memberDelegate.find(new Long(memberId));
				for(Member mem : membersList) {
					if(mem.getId()!=member.getId())
						list.add(mem);
				}
			} else {
				String password = request.getParameter("password");
				encryptor = new PasswordEncryptor();
				password = encryptor.encrypt(password);
				member.setPassword(password);
				member.setCompany(company);
				member.setPageModule("GEM");
				member.setUsername(userName+"GEM");
				member.setCreatedOn(getDateTime());
				member.setDateJoined(getDateTime());
				member.setNewsletter(false);
				member.setMemberType(null);
				member.setInfo1("");
				member.setActivated(true);
				member.setActivationKey(Encryption.hash(member.getCompany() + member.getEmail() + member.getUsername() + member.getPassword()));
				list = membersList;
			}
			
			member.setPageModuleUsername(userName);
			member.setFirstname(firstname);
			member.setLastname(lastname);
			member.setEmail(email);
			member.setValue3(department);
			member.setValue(type);
			
			String message = "";
			
			for(Member mem : list) {
				if(mem.getPageModuleUsername().equalsIgnoreCase(userName))
					message +="User Name already exists\n";
				if(mem.getEmail().equalsIgnoreCase(email))
					message +="Email already exists";
			}
			
			if(message.equalsIgnoreCase("")) {
				if(memberId.equalsIgnoreCase("0")) {
					memberDelegate.insert(member);
					obj.put("success", true);
				}
				else {
					member.setUpdatedOn(getDateTime());
					memberDelegate.update(member);
					obj.put("success", true);
				}
			} else {
				obj.put("success", false);
				obj.put("message", message);
			}

			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String saveMaintenance() {
		
		try {
			
			String module = request.getParameter("maintenanceModule");
			String id = request.getParameter("maintenanceId");
			String name = request.getParameter("name");
			String action = request.getParameter("action");
			
			JSONObject obj = new JSONObject();
			obj.put("success", true);
			
			MultiPage parent = multiPageDelegate.find(company, "GEM");
			SinglePage singlePage = singlePageDelegate.find(company, module, parent);
			
			String message = "";
			
			String content = singlePage.getContentWithoutTags();
			String oldStr = "";
			String newStr = "";
			String newContent = "";
			
			if(action.equalsIgnoreCase("add")) {
				boolean isFirst = true;
				int strId = 0;
				for(String str : content.split(",")) {
					strId = Integer.parseInt(extractDigits(str));
					String strName = str.replace(extractDigits(str)+".", "");
					if(strName.toLowerCase().replaceAll(" ", "").equalsIgnoreCase(name.toLowerCase().replaceAll(" ", ""))) {
						message += "This "+module+" already exists.";
					}
					isFirst = false;
				}
				strId++;
				newStr=strId+"."+name;
				content+=(isFirst?"":",")+newStr;
				newContent = content;
			} else if(action.equalsIgnoreCase("edit")) {
				for(String str : content.split(",")) {
					if(!str.contains(id)) {
						String strName = str.replace(extractDigits(str)+".", "");
						if(strName.toLowerCase().replaceAll(" ", "").equalsIgnoreCase(name.toLowerCase().replaceAll(" ", ""))) {
							message += "This "+module+" already exists.";
							break;
						}
					}
				}
				for(String str : content.split(",")) {
					if(str.contains(id)) {
						oldStr = str;
						break;
					}
				}
				newStr = id+"."+name;
				newContent = content.replace(oldStr, newStr);
			} else {
				boolean isFirst = true;
				for(String str : content.split(",")) {
					if(str.contains(id)) {
						oldStr = str;
						break;
					}
					isFirst = false;
				}
				newStr = "";
				newContent = content.replace((isFirst?"":",")+oldStr, "");
			}
			singlePage.setContent(newContent);
			singlePage.setUpdatedOn(getDateTime());
			if(message.equalsIgnoreCase("")) {
				singlePageDelegate.update(singlePage);
				if(!module.equalsIgnoreCase("Values") && !action.equalsIgnoreCase("add"))
					updateMembersTypeAndDepartment(oldStr, newStr, module);
				obj.put("success", true);
				obj.put("newStr", newStr);
			} else {
				obj.put("success", false);
				obj.put("message", message);
			}
			
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	private void updateMembersTypeAndDepartment(String oldStr, String newStr, String module) {
		oldStr = oldStr.replaceAll(" ", "").toLowerCase();
		List<Member> membersList = memberDelegate.findAllByPageModule("GEM", company).getList();
		List<Member> updateList = new ArrayList<Member>();
		if(module.equalsIgnoreCase("Department")) {
			for(Member mem : membersList) {
				String department = mem.getValue3().replaceAll(" ", "").toLowerCase();
				if(department.equalsIgnoreCase(oldStr)) {
					mem.setValue3(newStr);
					updateList.add(mem);
				}
			}
		} else if(module.equalsIgnoreCase("Type")) {
			for(Member mem : membersList) {
				String type = mem.getValue().replaceAll(" ", "").toLowerCase();
				if(type.equalsIgnoreCase(oldStr)) {
					mem.setValue(newStr);
					updateList.add(mem);
				}
			}
		}
		if(updateList!=null) {
			memberDelegate.batchUpdate(updateList);
		}
	}
	
	public String extractDigits(String src) {
	    StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < src.length(); i++) {
	        char c = src.charAt(i);
	        if (Character.isDigit(c)) {
	            builder.append(c);
	        }
	    }
	    return builder.toString();
	}
	
	private Date getDateTime()
	{
		final Date date = new Date();
		return date;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getMailerUserName() {
		return mailerUserName;
	}

	public void setMailerUserName(String mailerUserName) {
		this.mailerUserName = mailerUserName;
	}

	public String getMailerPassword() {
		return mailerPassword;
	}

	public void setMailerPassword(String mailerPassword) {
		this.mailerPassword = mailerPassword;
	}
	
	private final String htmlMessageTemplate = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n<head style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n    <meta name=\"viewport\" content=\"width=device-width\" style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n</head>\r\n<body style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;height: 100%;background: #fff;-webkit-font-smoothing: antialiased;-webkit-text-size-adjust: none;width: 100% !important;\">\r\n<table class=\"body-wrap\" style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;height: 100%;background: #fff;-webkit-font-smoothing: antialiased;-webkit-text-size-adjust: none;width: 100% !important;\">\r\n<tr style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n\t<td class=\"logo\" style=\"margin: 0;padding: 10px 0px;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n\t\t\t<img src=\"http://www.mundipharma.com.ph/images/mundipharma-logo.png\" style=\"margin: 0 auto;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;max-width: 100%;display: block;width: 140px;\">\r\n\t\t</td>\r\n</tr>\r\n    <tr style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n\t\t\r\n        <td class=\"container\" style=\"margin: 0 auto !important;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;display: block !important;clear: both !important;max-width: 580px !important;\">\r\n\r\n            <!-- Message start -->\r\n            <table style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;border-collapse: collapse;width: 100% !important;\">\r\n                <tr style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n                    <td align=\"center\" style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n\t\t\t\t\t\t<div class=\"masthead\" style=\"margin: 0;padding: 10px 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;background: #71bc37;color: white;border-radius: 44px;-webkit-border-radius: 44px;-o-border-radius: 44px;-moz-border-radius: 44px;-ms-border-radius: 44px;max-width: 500px;position: relative;top: 30px;-webkit-box-shadow: -1px 7px 0px -2px rgba(219,219,219,1);-moz-box-shadow: -1px 7px 0px -2px rgba(219,219,219,1);box-shadow: -1px 7px 0px -2px rgba(219,219,219,1);\">\r\n\r\n                        <h1 style=\"margin: 0 auto !important;padding: 0;font-size: 25px;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.25;margin-bottom: 20px;max-width: 90%;text-transform: uppercase;\">GO THE EXTRA MILE!</h1>\r\n\t\t\t\t\t\t</div>\r\n\r\n                    </td>\r\n                </tr>\r\n                <tr style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n                    <td class=\"content\" style=\"margin: 0;padding: 70px 35px 30px;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;background: white;border: 5px solid #71bc37;-webkit-box-shadow: -2px 12px 15px -6px rgba(0,0,0,0.47);-moz-box-shadow: -2px 12px 15px -6px rgba(0,0,0,0.47);box-shadow: -2px 12px 15px -6px rgba(0,0,0,0.47);\">\r\n\r\n                        <h2 style=\"margin: 0;padding: 0;font-size: 28px;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.25;margin-bottom: 20px;text-align: center;\">You made it happen!</h2>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<p style=\"margin: 0;padding: 0;font-size: 16px;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;font-weight: normal;margin-bottom: 20px;\">Dear --to--,</p>\r\n                        <p style=\"margin: 0;padding: 0;font-size: 16px;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;font-weight: normal;margin-bottom: 20px;\">\r\n\t\t\t\t\t\tThank you for practicing --value_list-- on --onDate-- by --by_letter--. Because of this, --because_letter--.</p>\r\n\r\n\r\n\r\n                        <p style=\"margin: 0;padding: 0;font-size: 16px;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;font-weight: normal;margin-bottom: 20px;\">From: <strong style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">--from--</strong></p>\r\n\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n\r\n        </td>\r\n    </tr>\r\n    <tr style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n        <td class=\"container\" style=\"margin: 0 auto !important;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;display: block !important;clear: both !important;max-width: 580px !important;\">\r\n\r\n            <!-- Message start -->\r\n            <table style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;border-collapse: collapse;width: 100% !important;\">\r\n                <tr style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;\">\r\n                    <td class=\"content footer\" align=\"center\" style=\"margin: 0;padding: 10px;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;background: none;border: none;-webkit-box-shadow: none;-moz-box-shadow: none;box-shadow: none;\">\r\n                        <p style=\"margin: 0;padding: 0;font-size: 14px;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;font-weight: normal;margin-bottom: 0;color: #FFF;text-align: center;\">Sent by <a href=\"#\" style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;color: #FFF;text-decoration: none;font-weight: bold;\">Mundipharma</a> |\r\n                        <a href=\"mailto:\" style=\"margin: 0;padding: 0;font-size: 100%;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;color: #FFF;text-decoration: none;font-weight: bold;\">hello@company.com</a></p>\r\n\t\t\t\t\t\t<p style=\"margin: 0;padding: 0;font-size: 14px;font-family: 'Avenir Next', &quot;Helvetica Neue&quot;, &quot;Helvetica&quot;, Helvetica, Arial, sans-serif;line-height: 1.65;font-weight: normal;margin-bottom: 0;color: #F00;text-align: center;\">This is a system generated email. No need to reply. <br/> GEM Card No. --GEM Card No.--</p>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n\r\n        </td>\r\n    </tr>\r\n</table>\r\n</body>\r\n</html>";
	
}
