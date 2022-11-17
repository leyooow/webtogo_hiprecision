package com.ivant.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.MemberAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.NumberUtil;
import com.ivant.utils.PasswordEncryptor;
import com.ivant.utils.StringUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ForgotMemberPasswordAction extends ActionSupport implements Preparable, SessionAware, ServletRequestAware,
	ServletContextAware, CompanyAware, MemberAware {

	private static final long serialVersionUID = -4961436595526602994L;
	private Logger logger = Logger.getLogger(getClass());
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	
	private static final List<String> ALLOWED_PAGES;
	 
	static {
		ALLOWED_PAGES = new ArrayList<String>();
		ALLOWED_PAGES.add("sitemap");
		ALLOWED_PAGES.add("printerfriendly");
		ALLOWED_PAGES.add("brands");
		ALLOWED_PAGES.add("calendarevents");
		ALLOWED_PAGES.add("cart");
	}
	
	private Map session;
	private HttpServletRequest request;
	private Company company;
	private String email;
	private Member member;
	private PasswordEncryptor encryptor;
	private boolean submitted = false;
	
	private String servletContextName;
	private String pageName;
	private String prevPage;
	private String httpServer;
	
	public String actionMessage;
	
	private ServletContext servletContext;
	private boolean isLocal;
	
	//private String notificationMessage;
	
	@Override
	public String execute() throws Exception {
		if(submitted) {
			Member member = memberDelegate.findEmail(company, email);
			
			if(member == null) {
				if(company.getName().equals("pmc")) {
					addActionError("The email address you entered is invalid.");
					return ERROR;
				}
				addActionError("The email address you entered does not exist in our database.");
				return ERROR;
			}
			 

			String newPass;
			newPass = StringUtil.generateRandomString();
			PasswordEncryptor encryptor=new PasswordEncryptor();
			member.setPassword(encryptor.encrypt(newPass));
			MemberDelegate memberDelegate=MemberDelegate.getInstance();
			memberDelegate.update(member);
			logger.debug(newPass);
			if(!sendEmail(member, newPass)) {
				addActionError("The account was not sent properly. Please contact support@webtogo.com.ph. Thank you");
				return ERROR;
			}
			else {
				
				return SUCCESS;
			}
		}

		
		return INPUT;
	}
	
	public void prepare() throws Exception {
		pageName = parsePageName();
		setPrevPage(request.getParameter("page"));
		servletContextName = servletContext.getServletContextName();	
		isLocal = (request.getRequestURI().startsWith("/" + servletContextName)) ? true : false;
		httpServer = (isLocal) ? ("http://" + request.getServerName() + ":" + request.getServerPort() + "/" + servletContextName + "/companies/" + company.getName()) : ("http://" + request.getServerName());
		request.setAttribute("local", this.isLocal);
		loadMenu();
		loadAllRootCategories();
		List<Category> categories = categoryDelegate.findAllPublished(company, null, null, true, Order.asc("sortOrder")).getList();
		request.setAttribute("categoryMenu", categories);	
		
		Map<String, Object> featuredPages = new HashMap<String, Object>();

		List<MultiPage> featuredMultiPage = multiPageDelegate.findAllFeatured(company).getList();
		
		for(MultiPage mp : featuredMultiPage) {
			if(!mp.getHidden()) {
				featuredPages.put(mp.getName(), mp);
			}
		}
		
		request.setAttribute("featuredPages", featuredPages);
	}
	
	public String gurkkaForgotPassword()
	{
		try
		{
			if(submitted) 
			{
				Member member = memberDelegate.findEmail(company, email);
				if(member == null) 
				{
					addActionError("The email address you entered does not exist in our database.");
					return ERROR;
				}
				if(member != null)
				{
					final String securityInfo[] = member.getInfo1().split("\\|");
					final String securityQuestion = StringUtils.trimToNull(securityInfo[0]);

					session.put("securityQuestion", securityQuestion);
					session.put("memberEmail", email);
					//setNotificationMessage("A system generated password was sent to your email.");
					//notificationMessage = "A system generated password was sent to your email.";
					session.put("notificationMessage", "A system generated password was sent to your email.");
					return SUCCESS;						
				}
				else
				{
					addActionError("The email address you entered does not exist in our database.");
					return ERROR;
				}
			}
			return INPUT;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ERROR;
	}
	
	public String gurkkaSecurityQuestion()
	{
		email = StringUtils.trimToNull(request.getParameter("memberEmail"));
		final String secretAnswerInput = StringUtils.trimToNull(request.getParameter("securityanswer"));
		final Member member = memberDelegate.findEmail(company, email);
		final String securityInfo[] = member.getInfo1().split("\\|");
		final String securityAnswer = StringUtils.trimToNull(securityInfo[securityInfo.length - 1]);
		final HttpSession httpSession = request.getSession(true);
		final Integer numOfTries = !StringUtils.isEmpty(request.getParameter("tries")) 
				? NumberUtil.trimToIntegerNumber(request.getParameter("tries")) 
				: 0;
		
		if(secretAnswerInput != null)
		{
			if(secretAnswerInput.equals(securityAnswer) && numOfTries <= 5)
			{
				String newPass;
				newPass = StringUtil.generateRandomString();
				PasswordEncryptor encryptor=new PasswordEncryptor();
				member.setPassword(encryptor.encrypt(newPass));
				MemberDelegate memberDelegate=MemberDelegate.getInstance();
				memberDelegate.update(member);
				logger.debug(newPass);
				if(!sendEmail(member, newPass)) {
					addActionError("The account was not sent properly. Please contact support@webtogo.com.ph. Thank you");
					return ERROR;
				}
				else {
					setActionMessage("A System Generated Password was sent to your email address.");
					return SUCCESS;
				}
			}
			else
			{
				httpSession.setAttribute("numOfTries", (numOfTries + 1));
				if(numOfTries >= 5)
				{
					addActionError("You have tried more than 5 times. Please contact customerservice@gurkka.com to retrieve your password.");
				}
				else
				{
					addActionError("Invalid security answer.");
				}
				session.put("notificationMessage", "Invalid security answer.");
				return ERROR;
			}
		}
		return ERROR;
	}
	
	private boolean sendEmail(final Member member, String newPassword) {
		EmailUtil.connect("smtp.gmail.com", 25); 
		try{
		if(company.getName().equalsIgnoreCase("gurkkatest")){
			company = companyDelegate.find(346L);
			EmailUtil.connectViaCompanySettings(company);
		}
		}catch(Exception e){logger.info(e);}
		StringBuffer content = new StringBuffer();
		content.append("<p>Password Recovery From " + company.getNameEditable()+"</p>");
		content.append("<p>You can use this generated Password to log-in.</p>");
		content.append("<p> Please Change your Password when you logged-in into your Account</p>" );
		content.append("<p>Username: " + member.getUsername());
		content.append("<p>Password: " + newPassword + "</p><br>");	
		content.append("</p>You can use this link to login:</p><br> " + httpServer);
		content.append("<br><br><br>Thank You Very Much, <br><br>" + company.getNameEditable());
		content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");
		return EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", email, "Password Recovery From " + company.getNameEditable(), content.toString(),null);
	}
	
	private void loadMenu() {
		try {
			Map<String, Menu> menuList = new HashMap<String, Menu>();

			// get the single pages
			List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
			for(SinglePage singlePage : singlePageList) {
				String jspName = singlePage.getJsp(); 
				Menu menu = new Menu(singlePage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(singlePage.getJsp(), menu);
			}
			
			// get the multi pages
			List<MultiPage> multiPageList = multiPageDelegate.findAll(company).getList();
			for(MultiPage multiPage : multiPageList) {
				String jspName = multiPage.getJsp();
				Menu menu = new Menu(multiPage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(multiPage.getJsp(), menu);
			}
			
			// get the form Pages
			List<FormPage> formPageList = formPageDelegate.findAll(company).getList();
			for(FormPage formPage : formPageList) {
				String jspName = formPage.getJsp();
				Menu menu = new Menu(formPage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(formPage.getJsp(), menu);
			}
			
			// get the groups
			List<Group> groupList = groupDelegate.findAll(company).getList();
			for(Group group : groupList) {
				String jspName = group.getName().toLowerCase();
				Menu menu = new Menu(group.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(jspName, menu);
			}
			
			// get the link to the allowed pages
			for(String s: ALLOWED_PAGES) {
				String jspName = s.toLowerCase();
				Menu menu = new Menu(jspName, httpServer + "/" + jspName + ".do"); 
				menuList.put(jspName, menu);
			}
			
			request.setAttribute("menu", menuList); 
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action in FrontMenuInterceptor. " + e);
		}
	}
	
	private String parsePageName() {
		String uri = request.getRequestURI();
		String[] uriSeparated = uri.split("/");
		String last = uriSeparated[uriSeparated.length-1];
		pageName = last.toLowerCase();
		pageName = pageName.replace(".do", "");
		return pageName;
	}

	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	@Override
	public void setMember(Member member) {
		this.member=member;
		
	}

	public void setPrevPage(String prevPage) {
		this.prevPage = prevPage;
	}

	public String getPrevPage() {
		return prevPage;
	}

	@Override
	public void setSession(Map arg0) {
		this.session = arg0;
		
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
		
	}
	
	private void loadAllRootCategories()
	{
		Order[] orders = {Order.asc("sortOrder")};
		List<Category> rootCategories = categoryDelegate.findAllRootCategories(company,true,orders).getList();
		request.setAttribute("rootCategories", rootCategories);		
	}
	
	public String getActionMessage() {
		return actionMessage;
	}
	
	public void setActionMessage(String actionMessage){
		this.actionMessage = actionMessage;
	}
	
}
