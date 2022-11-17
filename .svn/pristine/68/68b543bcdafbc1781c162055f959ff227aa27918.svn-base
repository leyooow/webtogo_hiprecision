package com.ivant.cms.action.admin;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.MemberAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import java.util.*;

import com.ivant.utils.EmailUtil;
import com.ivant.utils.Encryption;
import com.ivant.utils.PasswordEncryptor;

public class UpdateMemberAction extends ActionSupport implements Preparable, SessionAware, ServletRequestAware,
			 ServletContextAware, CompanyAware, MemberAware {

	private static final long serialVersionUID = 6315883485788215563L;
	private Logger logger = Logger.getLogger(getClass());
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	
	
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
	
	private Member member;
	private Member member2;
	private String servletContextName;
	private String pageName;
	private String prevPage;
	private String httpServer;
	
	private ServletContext servletContext;
	private boolean isLocal;
	private Company company;
	private CompanySettings companySettings;
	private String selectedCompany;
	private List<Company> companies;
	
	private PasswordEncryptor encryptor;	
	private String password;
	
	private boolean hasLoggedOn = true;


	public void prepare() throws Exception {
		String[] orderBy = {"nameEditable"};
		companies = companyDelegate.findAll(orderBy).getList();
		
		try {
			long memberId = Long.parseLong(request.getParameter("member_id"));
			member2 = memberDelegate.find(memberId);
			logger.info("1  " + member2);
			encryptor = new PasswordEncryptor();
			String decryptedPassword;
			decryptedPassword = encryptor.decrypt(member2.getPassword());
			setPassword(decryptedPassword);
		}
		catch(Exception e) {
			member2  = new Member();
		}
		
		setCompanySettings(company.getCompanySettings());
		pageName = parsePageName();
		servletContextName = servletContext.getServletContextName();	
		isLocal = (request.getRequestURI().startsWith("/" + servletContextName)) ? true : false;
		httpServer = (isLocal) ? ("http://" + request.getServerName() + ":" + request.getServerPort() + "/" + servletContextName + "/companies/" + company.getName()) : ("http://" + request.getServerName());
		request.setAttribute("local", this.isLocal);
		
		loadMenu();
	}
	
	private String parsePageName() {
		String uri = request.getRequestURI();
		String[] uriSeparated = uri.split("/");
		String last = uriSeparated[uriSeparated.length-1];
		pageName = last.toLowerCase();
		pageName = pageName.replace(".do", "");
		return pageName;
	}

	@Override
	public String execute() throws Exception {
		loadMenu();
		
		setCompanySettings(company.getCompanySettings());
		
		if(company == null || companySettings.getHasMemberFeature() == false) {
			return Action.ERROR;
		}
		return Action.NONE;
	}

	
	
	public String save() {        
		if (StringUtils.isEmpty(request.getParameter("member_id")))	{
			if (memberDelegate.findEmail(company, member2.getEmail()) == null) {
				encryptor = new PasswordEncryptor();
				String encryptedPassword, inputPassword;
				logger.info("3  " + member2);
				inputPassword = password;
				encryptedPassword = encryptor.encrypt(inputPassword);
				member2.setPassword(encryptedPassword);
				member2.setCompany(company);
				member2.setDateJoined(getDateTime());
				member2.setActivationKey(Encryption.hash(member2.getCompany() + member2.getEmail() + member2.getUsername() + member2.getPassword()));
				member2.setActivated(false);
				memberDelegate.insert(member2);
				if (sendEmail(member2)){
					return Action.SUCCESS;
				}
			}
		}
		else {
			encryptor = new PasswordEncryptor();
			String encryptedPassword, inputPassword;
			inputPassword = password;
			encryptedPassword = encryptor.encrypt(inputPassword);
			member2.setPassword(encryptedPassword);
			logger.info(member2);
			memberDelegate.update(member2);
		}
				
		return Action.SUCCESS;
	}
	
	private boolean sendEmail(final Member member) {
		EmailUtil.connect("smtp.gmail.com", 25); 
		
		StringBuffer content = new StringBuffer();
		content.append("The following are your account details. \n\n");
		content.append("Username: " + member2.getUsername());
		
		encryptor = new PasswordEncryptor();
		String decryptedPassword;
		decryptedPassword = encryptor.decrypt(member2.getPassword());
		setPassword(decryptedPassword);		
		content.append("\nPassword: " + password + "\n\n");	
		content.append("Please click the following link to activate your account.\n\n");		
		content.append("http://www." + company.getDomainName() + ".com/login?activation=" + member2.getActivationKey());
		content.append("\n\n\nThank You Very Much \n\n");
		content.append(company.getNameEditable());
		return EmailUtil.send("noreply@webtogo.com.ph", member2.getEmail(), "Your account from " + company.getNameEditable() + " has been created.", content.toString());
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

	public Member getMember2() {
		return member2;
	}

	public void setMember2(Member member2) {
		this.member2 = member2;
	}

	public String delete() {
		if(member2.getId() > 0) {
			memberDelegate.delete(member2);	
		}
		
		return Action.SUCCESS;
	}
	
	
	private boolean commonParamsValid() {
//		if(user.getCompany() == null) {
//			return false;
//		}		
		if(member2 == null) {
			return false;
		}
		
		return true;
	}
	
		
	public String edit() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	public String getSelectedCompany()	{
		return selectedCompany;
		
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
	
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	
	private Date getDateTime() {
        Date date = new Date();
        return date;
    }

	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}


	public CompanySettings getCompanySettings() {
		return companySettings;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPassword() {
		return password;
	}


	@Override
	public void setSession(Map arg0) {
		this.session = arg0;
		
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext=arg0;
		
	}

	public void setHasLoggedOn(boolean hasLoggedOn) {
		this.hasLoggedOn = hasLoggedOn;
	}

	public boolean isHasLoggedOn() {
		return hasLoggedOn;
	}
}
