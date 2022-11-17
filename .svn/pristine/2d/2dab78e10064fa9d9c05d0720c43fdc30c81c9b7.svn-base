package com.ivant.cms.action.admin;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.ActivityDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Activity;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.interceptors.FrontCompanyInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

public class DashboardAction implements Action, SessionAware, Preparable,
					ServletContextAware, ServletRequestAware, UserAware, CompanyAware {

	private static final Logger logger = Logger.getLogger(DashboardAction.class);
	private static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	
	private static final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private static final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private static final FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private static final ActivityDelegate activityDelegate = ActivityDelegate.getInstance();	
	private static final LogDelegate logDelegate = LogDelegate.getInstance();
	private static final ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate.getInstance();
	private static final MemberFileItemDelegate memberFileItemDelegate = MemberFileItemDelegate.getInstance();
	
	private Map session;
	private HttpServletRequest request;

	private List<Company> companies;
	private long companyId;
	private Company company;
	private User user;
	
	private List<SinglePage> singlePageList;
	private List<MultiPage> multiPageList;	
	private List<FormPage> formPageList;
	private List<Group> groupList;	
	private List<Category> categoryList;
	private List<Activity>  activityList;
	private List<Member> memberList;
	private List<Log> logList;
	private List<Category> categoryTemp;

	private int latestSubmissionsCount;
	private int latest24Comment;
	private int latest72Comment;
	
	private ServletContext servletContext;
	private String filename;
	private File logo;
	
	
	public void prepare() throws Exception {
		String[] order = { "nameEditable" };
		
		try {
			companyId = Long.parseLong(request.getParameter("company_id"));
			company = companyDelegate.find(companyId);
		}
		catch(Exception e) {
			logger.debug("cannot find company");
		}
				
		//companies = companyDelegate.findAll(order).getList();
		companies = companyDelegate.findAll(order).getList();
		latestSubmissionsCount = savedEmailDelegate.findLatestEmail(company, 3).getSize();
		latest24Comment = itemCommentDelegate.findLatestComments(company, 1, Order.desc("createdOn")).getSize();
		latest72Comment = itemCommentDelegate.findLatestComments(company, 1, Order.desc("createdOn")).getSize();
		
		singlePageList = singlePageDelegate.findAll(company).getList();
		multiPageList = multiPageDelegate.findAll(company).getList();	
		formPageList = formPageDelegate.findAll(company).getList();
		groupList = groupDelegate.findAll(company).getList();	
		categoryList = categoryDelegate.findAllRootCategories(company,true).getList();
		categoryTemp = categoryDelegate.findAllRootCategories(company,true).getList();
		activityList = activityDelegate.findAll();
		memberList = memberDelegate.findAll(company).getList();
		
		removeForbiddenPages();
		
		//Clickbox
		String[] fileType = {".xls", ".xlsx", ".xlsm"};
		request.setAttribute("totalForQuotation", memberFileItemDelegate.findAllByStatus(company, "For Quotation", fileType).size());
		request.setAttribute("totalOrderOnProcess", memberFileItemDelegate.findAllByStatus(company, "Order On Process", fileType).size());
		request.setAttribute("totalQuoteSent", memberFileItemDelegate.findAllByStatus(company, "Quote Sent", fileType).size());
		request.setAttribute("totalPaid", memberFileItemDelegate.findAllByStatus(company, "Paid", fileType).size());
		request.setAttribute("totalForShipping", memberFileItemDelegate.findAllByStatus(company, "For Shipping", fileType).size());
		request.setAttribute("totalDelivered", memberFileItemDelegate.findAllByStatus(company, "Delivered", fileType).size());
		
		int temp1 = 10;
		logList = logDelegate.findLastWeek(company).getList();
		if(logList.size()<10) temp1 = logList.size();
		logList = logList.subList(0, temp1);		//get the l0 most recent logs.
		
		logger.debug("Log size: " + logList.size());
	}
	
	public int getLatest24Comment() {
		return latest24Comment;
	}

	public int getLatest72Comment() {
		return latest72Comment;
	}	

	public List<Log> getLogList(){
		return logList;
	}
	
	public void setLogList(List<Log> logList){
		this.logList = logList;
	} 
	
	public List<Activity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}
	

	public List<SinglePage> getSinglePageList() {
		return singlePageList;
	}

	public void setSinglePageList(List<SinglePage> singlePageList) {
		this.singlePageList = singlePageList;
	}

	public List<MultiPage> getMultiPageList() {
		return multiPageList;
	}

	public void setMultiPageList(List<MultiPage> multiPageList) {
		this.multiPageList = multiPageList;
	}

	public List<FormPage> getFormPageList() {
		return formPageList;
	}

	public void setFormPageList(List<FormPage> formPageList) {
		this.formPageList = formPageList;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}


	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		if (company != null && companyId > 0) {
			session.put(FrontCompanyInterceptor.COMPANY_REQUEST_KEY, company.getId());
			if(request.getParameter("companyStatus")!=null){
				session.put("companyStatus", request.getParameter("companyStatus"));
			}
			return SUCCESS;
		}
		return Action.NONE; 
	}
	
	private void removeForbiddenPages() {
		try
		{
			List<SinglePage> forbiddenSinglePages = user.getForbiddenSinglePages();
			List<MultiPage> forbiddenMultiPages = user.getForbiddenMultiPages();
			List<Group> forbiddenGroups = user.getForbiddenGroups();
			List<Category> forbiddenCategories = user.getForbiddenCategories();
			
			if(forbiddenSinglePages != null && singlePageList != null) {
				for(SinglePage singlePage : forbiddenSinglePages) {
					singlePageList.remove(singlePage);
				}
			}
			
			if(forbiddenMultiPages != null && multiPageList != null) {
				for(MultiPage multiPage : forbiddenMultiPages) {
					multiPageList.remove(multiPage);
				}
			}
			
			if(forbiddenGroups != null && groupList != null) {
				for(Group group : forbiddenGroups) {
					groupList.remove(group);
				}
			}
			
			if(forbiddenGroups != null && groupList != null && categoryList != null) {
				for(Category category : categoryTemp) {
					for(Group group : forbiddenGroups) {
						if(group == category.getParentGroup())
						{
							categoryList.remove(category);
						}
					}
				}
			}
			
			if(forbiddenCategories != null && categoryList != null) {
				for(Category category : forbiddenCategories) {
					categoryList.remove(category);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String statistics() {
		company = user.getCompany();
		// System.out.println("user: "+ user.getFullName());
		// System.out.println("company: " + user.getCompany().getDomainName());
		// request.setAttribute("domainName",
		// user.getCompany().getDomainName());

		return Action.NONE;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getLatestSubmissionsCount() {
		return latestSubmissionsCount;
	}
	

	public File getLogo() {
		return logo;
	}
	
	public void setLogo(File logo) {
		this.logo = logo;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public String saveLogo(){
		  save(logo,filename);
		  return Action.SUCCESS;
	}
	
	
	public void save(File logo, String filenames){
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
	
		destinationPath = servletContext.getRealPath(destinationPath);

		File origFile = new File(destinationPath + File.separator + File.separator + filenames);
		FileUtil.copyFile(logo, origFile);	
		
		try {
			companyId = Long.parseLong(request.getParameter("company_id"));
			company = companyDelegate.find(companyId);
		}
		catch(Exception e) {
			logger.debug("cannot find company");
		}
		
		if(company.getLogo()!=null){
			destinationPath = servletContext.getRealPath("companies" + 
					File.separator + company.getName() +
					File.separator + "images");
			FileUtil.deleteFile(destinationPath + File.separator + company.getLogo());
		}
		
		company.setLogo(filename);
		companyDelegate.update(company);		
		
	}


	
	
}
