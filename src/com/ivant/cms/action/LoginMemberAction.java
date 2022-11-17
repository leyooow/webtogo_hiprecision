package com.ivant.cms.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.MemberAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.PasswordEncryptor;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class LoginMemberAction extends ActionSupport implements Preparable, SessionAware, ServletRequestAware,
			ServletContextAware, CompanyAware, MemberAware {

	private static final long serialVersionUID = -100325022519476647L;
	
	private static final List<String> ALLOWED_PAGES;
	
	static {
		ALLOWED_PAGES = new ArrayList<String>();
		ALLOWED_PAGES.add("sitemap");
		ALLOWED_PAGES.add("printerfriendly");
		ALLOWED_PAGES.add("brands");
		ALLOWED_PAGES.add("calendarevents");
		ALLOWED_PAGES.add("cart");
	}
	
	private Logger logger = Logger.getLogger(getClass());
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	
	private Map session;
	private HttpServletRequest request;
	
	private String username;
	private String password;
	private boolean login = false;
	
	private Company company;
	private Member member;
	private String servletContextName;
	private String pageName;
	private String prevPage;
	private String httpServer;
	private String activationLink;
	
	private ServletContext servletContext;
	private boolean isLocal;
	private String message;
	
	private PasswordEncryptor encryptor;
	
	@Override
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		if(login) {
			encryptor = new PasswordEncryptor();
			password = encryptor.encrypt(password);
			
			logger.debug("LOGGING IN...");
			logger.debug("Member: " + member);
			logger.debug("Username: " + username + "Password: " + password + "Company" + company.getId());
			
			if(username != null && password != null) {
				Member member = memberDelegate.findAccount(username, password, company);
				
				if(member == null) {
					member = memberDelegate.findAccount(username, password, company);
					logger.debug("Member is Null");
				}
				if(member != null) {
					if (member.getActivated() == false){
						logger.debug(username);
						return "inactive";
					}
					member.setLastLogin(new Date());
					memberDelegate.update(member);
					session.put(MemberInterceptor.MEMBER_SESSION_KEY, member.getId());
					
					pageName=request.getParameter("page_name");
					logger.debug(pageName);
					return Action.SUCCESS;
				}
			} 
			addActionError("Invalid Username / Password");
			return ERROR;
		}
		return Action.LOGIN;
	}
	
	public void prepare() throws Exception {
		if(request.getParameter("login") != null) {
			login = true;
		}
		username = request.getParameter("username");
		password = request.getParameter("password");
		
		setUsername(username);
		setPassword(password);
		
		pageName = parsePageName();
		setPrevPage(request.getParameter("page"));
		servletContextName = servletContext.getServletContextName();	
		isLocal = (request.getRequestURI().startsWith("/" + servletContextName)) ? true : false;
		httpServer = (isLocal) ? ("http://" + request.getServerName() + ":" + request.getServerPort() + "/" + servletContextName + "/companies/" + company.getName()) : ("http://" + request.getServerName());
		request.setAttribute("local", this.isLocal);
		
		loadMenu();
	}
	
	public String activate(){
		String activationKey;
		activationKey = request.getParameter("activation");
		Member member = memberDelegate.findActivationKey(activationKey); 
		if( member != null){
			member.setActivated(true);
			memberDelegate.update(member);
			return Action.SUCCESS;
		}
		return Action.ERROR;
	}
	
	public String sendEmail() {	
		Member member = memberDelegate.findAccount(username, company);
		
		if (member != null){
			EmailUtil.connect("smtp.gmail.com", 25); 
			
			StringBuffer content = new StringBuffer();
			content.append("Please click the following link to activate your account.\n\n");		
			//content.append("http://" + company.getServerName() + "/activate.do?activation=" + member.getActivationKey());
			content.append(httpServer + "/activate.do?activation=" + member.getActivationKey());
			content.append("\n\n\nThank You Very Much \n\n");
			content.append(company.getNameEditable());
			if(EmailUtil.send("noreply@webtogo.com.ph", member.getEmail(), "Member account activation from  " + company.getNameEditable() + ".", content.toString())){
				return Action.SUCCESS;
			}
		}
		return Action.ERROR; 
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
		
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setSession(Map arg0) {
		this.session = arg0;
	}

	@Override
	public void setMember(Member member) {
		this.member = member;
		
	}
	
	public Member getMember(Member member){
		return member;
	}

	public void setPrevPage(String prevPage) {
		this.prevPage = prevPage;
	}

	public String getPrevPage() {
		return prevPage;
	}

	public void setActivationLink(String activationLink) {
		this.activationLink = activationLink;
	}

	public String getActivationLink() {
		return activationLink;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
