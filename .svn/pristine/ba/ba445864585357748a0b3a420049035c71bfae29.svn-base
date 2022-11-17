package com.ivant.cms.ws.rest.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.PageFileDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.PageFile;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.ws.rest.bean.FileUploadBean;
import com.ivant.cms.ws.rest.model.MemberModel;
import com.ivant.constants.CompanyConstants;
import com.ivant.constants.WendysConstants;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.Encryption;
import com.ivant.utils.FileUtil;
import com.ivant.utils.PasswordEncryptor;
import com.ivant.utils.StringUtil;



@Path("/member")
public class MemberResource extends AbstractResource {
	private static final Logger logger = LoggerFactory
			.getLogger(MemberResource.class);

	private MemberModel memberModel;
	private Member member;
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private final PageFileDelegate pageFileDelegate = PageFileDelegate.getInstance();
	private final MemberFileDelegate memberFileDelegate = MemberFileDelegate.getInstance();
	private final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private SinglePage singlePage;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext servletContext;
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MemberModel find(@Context HttpHeaders headers,
			@FormParam("id") Long id) {
		logger.info("find method invoked! params id: {} ", id);

		member = null;
		memberModel = null;

		try {
			openSession();
			Company company = getCompany(headers);
			if (id != null) {
				member = memberDelegate.find(id);
			}

			if (member != null && company.equals(member.getCompany())) {
				memberModel = new MemberModel(member);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}

		return memberModel;
	}

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/verify")
	public MemberModel verify(@Context HttpHeaders headers,
			@FormParam("username") String username,
			@FormParam("password") String password) {
		logger.info(
				"find method invoked! params \nusername: {} \npasssword: {}",
				new Object[] { username, password });

		member = null;
		memberModel = null;

		try {
			openSession();
			Company company = getCompany(headers);
			if (StringUtils.isNotEmpty(username)
					&& StringUtils.isNotEmpty(password)) {
				String encryptedPassword = encryptPassword(password);
				member = memberDelegate.findAccount(username,
						encryptedPassword, company);
			}

			if (member != null && company.equals(member.getCompany())) {
				memberModel = new MemberModel(member);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}

		return memberModel;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/verify")
	public MemberModel verifyGET(@Context HttpHeaders headers,
			@QueryParam("username") String username,
			@QueryParam("password") String password) {
		logger.info(
				"find method invoked! params \nusername: {} \npasssword: {}",
				new Object[] { username, password });

		member = null;
		memberModel = null;

		try {
			openSession();
			Company company = getCompany(headers);
			if (StringUtils.isNotEmpty(username)
					&& StringUtils.isNotEmpty(password)) {
				PasswordEncryptor encryptor = new PasswordEncryptor();
				String encryptedPassword = encryptor.encrypt(password);

				member = memberDelegate.findAccount(username,
						encryptedPassword, company);
			}

			if (member != null && company.equals(member.getCompany())) {
				memberModel = new MemberModel(member);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}

		return memberModel;
	}

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/register")
	public MemberModel register(@Context HttpHeaders headers,
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("email") String email,
			@FormParam("firstname") String firstname,
			@FormParam("middlename") String middlename,
			@FormParam("lastname") String lastname,
			@FormParam("landline") String landline,
			@FormParam("mobile") String mobile,
			@FormParam("address1") String address1,
			@FormParam("address2") String address2,
			@FormParam("city") String city,
			@FormParam("province") String province,
			@FormParam("info1") String info1, @FormParam("info2") String info2,
			@FormParam("info3") String info3, @FormParam("info4") String info4,
			@FormParam("memberCompanyName") String memberCompanyName,
			@FormParam("sendEmail") Boolean sendEmail) {
		logger.info("find method invoked");

		MemberModel memberModel = new MemberModel();
		Member member = null;

		try {
			openSession();
			company = getCompany(headers);

			String encryptedPass = encryptPassword(password);
			member = new Member();
			member.setDateJoined(new Date());
			member.setCompany(company);
			member.setUsername(username);
			member.setPassword(encryptedPass);
			member.setFirstname(firstname);
			member.setMiddlename(middlename);
			member.setLastname(lastname);
			member.setEmail(email);
			member.setLandline(landline);
			member.setMobile(mobile);
			member.setAddress1(address1);
			member.setAddress2(address2);
			member.setCity(city);
			member.setProvince(province);
			member.setNewsletter(false);
			member.setInfo1(info1);
			member.setInfo2(info2);
			member.setInfo3(info3);
			member.setInfo4(info4);
			member.setReg_companyName(memberCompanyName);

			member.setActivationKey(Encryption.hash(company + email + username
					+ encryptedPass));

			if (memberDelegate.findAccount(username, company) != null) {
				member = null;
			} else {
				Long id = memberDelegate.insert(member);
				if (id != null) {
					member = memberDelegate.find(id);
				}
			}

			/*
			 * if(sendEmail) { sendEmail(member, company); }
			 */

			if (member != null) 
			{
				memberModel = new MemberModel(member);
				memberModel.setSuccess(Boolean.TRUE);
				memberModel.setNotificationMessage("New member created!");

				if(CompanyConstants.WENDYS == company.getId())
				{
					addRewards(member);
				}
			} 
			else 
			{
				memberModel.setSuccess(Boolean.FALSE);
				memberModel.setNotificationMessage("Member with username "
						+ username + " already exist!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}

		return memberModel;
	}

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/updatePassword")
	public MemberModel updatePassword(@Context HttpHeaders headers,
			@FormParam("id") Long id,
			@FormParam("oldpassword") String oldPassword,
			@FormParam("newpassword") String newPassword) {
		member = null;
		MemberModel memberModel = new MemberModel();
		String username = "";
		try {
			openSession();
			Company company = getCompany(headers);
			member = memberDelegate.find(id);
			username = member.getUsername();
			
			if (StringUtils.isNotEmpty(username)
					&& StringUtils.isNotEmpty(oldPassword)) {
				String encryptedOldPassword = encryptPassword(oldPassword);
				member = memberDelegate.findAccount(username,
						encryptedOldPassword, company);
			}

			if (member != null) {
				String encryptedNewPassword = encryptPassword(newPassword);
				member.setPassword(encryptedNewPassword);
				if (memberDelegate.update(member)) {
					memberModel = new MemberModel(member);
					memberModel.setSuccess(Boolean.TRUE);
					memberModel.setNotificationMessage("Member " + member.getUsername() + " password successfully updated!");
				} else {
					memberModel.setSuccess(Boolean.FALSE);
					memberModel.setNotificationMessage("Member " + member.getUsername() + " password failed to update!");
				}
			}
			else{
				memberModel.setSuccess(Boolean.FALSE);
				memberModel.setNotificationMessage("Member " + username + " doesn't exist!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}

		return memberModel;
	}
	
	// /////////////////////////////////////////////////////////////////////////////////////
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/updateMember")
	public MemberModel updateMember(@Context HttpHeaders headers,
			@FormParam("id") Long id, @FormParam("email") String email,
			@FormParam("firstname") String firstname,
			@FormParam("middlename") String middlename,
			@FormParam("lastname") String lastname,
			@FormParam("landline") String landline,
			@FormParam("mobile") String mobile,
			@FormParam("address1") String address1,
			@FormParam("address2") String address2,
			@FormParam("city") String city,
			@FormParam("province") String province,
			@FormParam("info1") String info1, @FormParam("info2") String info2,
			@FormParam("info3") String info3, @FormParam("info4") String info4,
			@FormParam("username") String username) {
		logger.info("find updateMember invoked");

		MemberModel memberModel = new MemberModel();
		Member member = null;

		try {
			openSession();
			Company company = getCompany(headers);

			member = memberDelegate.find(id);
			
			
			if (member != null) {
				Member member2 = memberDelegate.findAccount(username, company);
				Boolean sameMember = Boolean.TRUE;
				if(member2!=null){
					sameMember = member.getId()==member2.getId();
				}
				if(sameMember){
					member.setCompany(company);
					member.setUsername(username);
					member.setFirstname(firstname);
					member.setMiddlename(middlename);
					member.setLastname(lastname);
					member.setEmail(email);
					member.setLandline(landline);
					member.setMobile(mobile);
					member.setAddress1(address1);
					member.setAddress2(address2);
					/*
					try{
						if(CompanyConstants.WENDYS == company.getId().intValue()){
							//get the corresponding city
							Group g = groupDelegate.find(CompanyConstants.WENDYS_STORE_GROUP_ID);
							if(g != null){
							     Category c = categoryDelegate.findContainsName(company, city, g);
							     if(c != null){
								    if(c.getName().toLowerCase().replace("city", "").trim().equalsIgnoreCase(city.toLowerCase().replace("city", "").trim())){
									   city = c.getName();
								     }
							      }
							}
						}
						
						member.setCity(city);
					}catch(Exception e){}
					*/
					member.setCity(city);
					
					member.setProvince(province);
					member.setInfo1(info1);
					member.setInfo2(info2);
					member.setInfo3(info3);
					member.setInfo4(info4);
					if (memberDelegate.update(member)) {
						memberModel = new MemberModel(member);
						memberModel.setSuccess(Boolean.TRUE);
						memberModel.setNotificationMessage("Member "
								+ member.getUsername() + " successfully updated!");
					} else {
						memberModel.setSuccess(Boolean.FALSE);
						memberModel.setNotificationMessage("Member "
								+ member.getUsername() + " failed to update!");
					}
				}else{
					memberModel.setSuccess(Boolean.FALSE);
					memberModel.setNotificationMessage("Member with username "
							+ username + " already exist!");
				}
				
			} else {
				memberModel.setSuccess(Boolean.FALSE);
				memberModel.setNotificationMessage("Member " + username
						+ " doesn't exist!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			memberModel.setSuccess(Boolean.FALSE);
			memberModel.setNotificationMessage(e.toString());
		} finally {
			closeSession();
		}

		return memberModel;
	}

		// ///////////////////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////////////////////////
			
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/uploadPhoto")
	public MemberModel uploadPhoto(@Context HttpHeaders headers,
			@Context HttpServletRequest request, @QueryParam("id") Long id) {
		member = null;

		MemberModel memberModel = new MemberModel();
		try {
			openSession();
			Company company = getCompany(headers);
			member = memberDelegate.find(id);
			if (member != null) {
				FileItem fileItem = (FileItem) request.getAttribute("file");
				String fileName = fileItem.getName();
				InputStream inputStream = fileItem.getInputStream();
				
				servletContext = request.getSession().getServletContext();
				
				singlePage = singlePageDelegate.find(Long.parseLong("16925"));
				String destinationPath = "companies";
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				destinationPath += File.separator + company.getName();
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "uploads"));
				FileUploadBean fileUploadBean = new FileUploadBean();
				String filename = fileName+ "" + (new DateTime()).toString("yyyyMMdd_hhmmssSSS") 
						+ "." + FileUtil.getExtension(fileName);
				File file = new File(request.getSession().getServletContext().getRealPath(destinationPath + File.separator + "uploads"));
				file.mkdirs();
				File newFile = new File(file, filename);
				try {
					OutputStream outpuStream = new FileOutputStream(newFile);
					int read = 0;
					byte[] bytes = new byte[1024];
					while ((read = inputStream.read(bytes)) != -1) {
						outpuStream.write(bytes, 0, read);
					}
					outpuStream.flush();
					outpuStream.close();
					fileUploadBean.setFileLocation("uploads/" + filename);
					
					final String path = destinationPath;
					final File uploadedFileDestination = new File(path);
					if(!uploadedFileDestination.exists()){
						uploadedFileDestination.mkdirs();
					}
					
					PageFile pageFile = new PageFile();
					pageFile.setCompany(company);
					pageFile.setFileName(filename);
					pageFile.setFilePath("uploads" + "/" + filename);
					pageFile.setPage(singlePage);
					pageFileDelegate.insert(pageFile);
					
					
					member.setInfo1(filename);
					if (memberDelegate.update(member)) {
						memberModel = new MemberModel(member);
						memberModel.setSuccess(Boolean.TRUE);
						memberModel.setNotificationMessage("Member " + member.getUsername() + " profile photo successfully uploaded!");
					}
					else {
						memberModel.setSuccess(Boolean.FALSE);
						memberModel.setNotificationMessage("Member " + member.getUsername() + " profile photo failed to upload!");
					}
				
				} catch (IOException e) {

					e.printStackTrace();
				}
					
			}
			else{
				memberModel.setSuccess(Boolean.FALSE);
				memberModel.setNotificationMessage("Member with ID number " + id + " doesn't exist!");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}

		return memberModel;
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/forgotPassword")
	public MemberModel forgotPassword(@Context HttpHeaders headers,
			@FormParam("email") String email)
	{
		logger.info("forgotPassword method invoked");
		MemberModel memberModel = new MemberModel();
		
		try {
			openSession();
			Company company = getCompany(headers);
			if (StringUtils.isNotEmpty(email)) 
			{
				member = memberDelegate.findEmail(company, email);
			}

			if (member != null && company.equals(member.getCompany()))
			{
				String newPassword = StringUtil.generateRandomString();
				
				member.setPassword(encryptPassword(newPassword));
				memberDelegate.update(member);
				
				if(sendForgotPasswordEmail(member, newPassword))
				{
					memberModel.setSuccess(true);
					memberModel.setNotificationMessage("Forgot password successful! " +
							"Your new password was sent to your email.");
				}
				else
				{
					memberModel.setSuccess(false);
					memberModel.setNotificationMessage("Forgot password failed! " +
							"The email was not sent properly. Please contact support@webtogo.com.ph. Thank you!");
				}
			}
			else
			{
				memberModel.setSuccess(false);
				memberModel.setNotificationMessage("Email not found!");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			closeSession();
		}
		
		return memberModel;
	}
		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////
		
		// ///////////////////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////////////////////////
			/*
			@POST
			@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
			@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
			@Path("/uploadPhoto")
			public MemberModel uploadPhoto(@Context HttpHeaders headers,
					@FormParam("id") Long id,
					@FormParam("uploadedPhoto") String oldPassword) {
				member = null;
				MemberModel memberModel = new MemberModel();
				try {
					openSession();
					Company company = getCompany(headers);
					member = memberDelegate.find(id);
					if (member != null) {
					
							singlePage = singlePageDelegate.find(Long.parseLong("16925"));
							String destinationPath = "companies";
							FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
							destinationPath += File.separator + company.getName();
							FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
							FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "uploads"));
							final MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
							final File[] file = r.getFiles("uploadedPhoto");
							final String[] filename = r.getFileNames("uploadedPhoto");
							String destination = servletContext.getRealPath(destinationPath + File.separator + "uploads");
							File dest = new File(destination + File.separator + filename[0]);
							final String path = destinationPath;
							final File uploadedFileDestination = new File(path);
							if(!uploadedFileDestination.exists()){
								uploadedFileDestination.mkdirs();
							}
							FileUtil.copyFile(file[0], dest);
							request.setAttribute("uploadFile3", dest.getAbsolutePath());
							request.setAttribute("uploadFile3name",  filename[0]);
							PageFile pageFile = new PageFile();
							pageFile.setCompany(company);
							pageFile.setFileName(filename[0]);
							pageFile.setFilePath("uploads" + "/" + filename);
							pageFile.setPage(singlePage);
							pageFileDelegate.insert(pageFile);
							member.setInfo1(filename[0]);
							if (memberDelegate.update(member)) {
								memberModel = new MemberModel(member);
								memberModel.setSuccess(Boolean.TRUE);
								memberModel.setNotificationMessage("Member " + member.getUsername() + " profile photo successfully uploaded!");
							}
							else {
								memberModel.setSuccess(Boolean.FALSE);
								memberModel.setNotificationMessage("Member " + member.getUsername() + " profile photo failed to upload!");
							}
					}
					else{
						memberModel.setSuccess(Boolean.FALSE);
						memberModel.setNotificationMessage("Member with ID number " + id + " doesn't exist!");
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					closeSession();
				}

				return memberModel;
			}
			*/
		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////
	
	private boolean sendEmail(Member member, Company company) {
		if (member != null) {

			if (company.getId() == CompanyConstants.GURKKA
					|| company.getId() == CompanyConstants.GURKKA_TEST) {
				try {
					EmailUtil.connect(company.getCompanySettings().getSmtp(),
							Integer.parseInt(company.getCompanySettings()
									.getPortNumber()), company
									.getCompanySettings().getEmailUserName(),
							company.getCompanySettings().getEmailPassword());
				} catch (Exception e) {
					EmailUtil.connect("smtp.gmail.com", 25);
					e.printStackTrace();
				}
			} else {
				EmailUtil.connect("smtp.gmail.com", 25);
			}

			final StringBuffer content = new StringBuffer();
			String memberName = "";

			if (company.getId() == CompanyConstants.GURKKA
					|| company.getId() == CompanyConstants.GURKKA_TEST) {
				if (!StringUtils.isEmpty(member.getReg_companyName())) {
					memberName = member.getReg_companyName();
				} else {
					memberName = member.getFirstname() + " "
							+ member.getLastname();
				}
			} else {
				memberName = member.getFirstname() + " " + member.getLastname();
			}

			content.append("Hi " + memberName + ",\r\n\r\n" + "Welcome to "
					+ company.getNameEditable() + "! ");
			content.append("\r\n\r\nPlease click on the link below to activate your account. \r\n\r\n");
			content.append(company.getDomainName() + "/activate.do?activation="
					+ member.getActivationKey());
			content.append("\r\n\r\n\r\nThank you for registering.\r\n\r\n\r\nAll the Best, \r\n\r\n");
			content.append("The " + company.getNameEditable() + " Team");
			final String[] to = member.getEmail().split(" ");
			if (EmailUtil
					.send("noreply@webtogo.com.ph", to,
							"Welcome to " + company.getNameEditable() + "!",
							content.toString(), null)) {
				return true;
			}
		}

		return false;
	}

	private boolean sendForgotPasswordEmail(final Member member, String newPassword)
	{
		Company company = member.getCompany();
		EmailUtil.connect("smtp.gmail.com", 25); 
		
		StringBuffer content = new StringBuffer();
		content.append("<p>Password Recovery From " + company.getNameEditable()+"</p>");
		content.append("<p>You can use this generated Password to log-in.</p>");
		content.append("<p> Please Change your Password when you logged-in into your Account</p>" );
		content.append("<p>Username: " + member.getUsername());
		content.append("<p>Password: " + newPassword + "</p><br>");	
		content.append("<br>Thank You Very Much, <br><br>" + company.getNameEditable());
		content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");
	
		return EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", member.getEmail(), 
				"Password Recovery From " + company.getNameEditable(), content.toString(),null);
	}
	
	private void addRewards(Member member) 
	{
		Category category = categoryDelegate.find(WendysConstants.REGISTER_REWARDS_CATEGORY_ID);
		
		List<MemberFile> memberFiles = memberFileDelegate.findAllWithPaging(company, 1, 0, category, false, 
				Order.asc("createdOn"));
		
		if(memberFiles.size() > 0)
		{
			MemberFile memberFile = memberFiles.get(0);
			memberFile.setMember(member);
			
			memberFileDelegate.update(memberFile);
		}
	}
	
	private String encryptPassword(String password) {
		PasswordEncryptor encryptor = new PasswordEncryptor();
		String encryptedPassword = encryptor.encrypt(password);

		return encryptedPassword;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	
	
}
