package com.ivant.cms.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.ActivityDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberLogDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.PortalActivityLogDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Activity;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.PortalActivityLog;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;
import com.ivant.cms.interceptors.FrontCompanyInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

public class LogsAction implements Action, SessionAware, Preparable, ServletRequestAware, ServletContextAware,
		UserAware, CompanyAware, PagingAware {

	private static final Logger logger = Logger.getLogger(LogsAction.class);
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
	private static final MemberLogDelegate memberLogDelegate = MemberLogDelegate.getInstance();
	private static final PortalActivityLogDelegate portalActivityLogDelegate = PortalActivityLogDelegate.getInstance();

	private ServletContext servletContext;

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
	private List<Activity> activityList;
	private List<Member> memberList;
	private List<Log> logList;
	private List<Log> categoryItemLogList;
	private List<MemberLog> memberLogList;
	private ObjectList<Log> logList2;
	private ObjectList<Log> categoryItemLogList2;

	private ObjectList<MemberLog> memberLogList2;

	private ObjectList<PortalActivityLog> portalActivityLogList2;
	private List<PortalActivityLog> portalActivityLogList;

	private int page;
	private int totalItems;
	private int itemsPerPage;
	private int pageNumber;

	private String filePath;
	private String fileName;
	private String fieldName[];
	private FileInputStream fInStream;
	private FileInputStream inputStream;
	private long contentLength;

	private int latestSubmissionsCount;
	
	private String filter;

	public void prepare() throws Exception {
		String[] order = { "nameEditable" };

		try {
			companyId = Long.parseLong(request.getParameter("company_id"));
			company = companyDelegate.find(companyId);
		} catch (Exception e) {
			logger.debug("cannot find company");
		}

		// companies = companyDelegate.findAll(order).getList();
		companies = companyDelegate.findAll(order).getList();
		latestSubmissionsCount = savedEmailDelegate.findLatestEmail(company, 3).getSize();

		singlePageList = singlePageDelegate.findAll(company).getList();
		multiPageList = multiPageDelegate.findAll(company).getList();
		formPageList = formPageDelegate.findAll(company).getList();
		groupList = groupDelegate.findAll(company).getList();
		categoryList = categoryDelegate.findAllRootCategories(company, true).getList();
		activityList = activityDelegate.findAll();
		memberList = memberDelegate.findAll(company).getList();

		if (request.getParameter("pageNumber") == null)
			pageNumber = 0;
		else
			pageNumber = Integer.parseInt(request.getParameter("pageNumber")) - 1;

		logList2 = logDelegate.findAll(company, pageNumber, user.getItemsPerPage());
		memberLogList2 = memberLogDelegate.findAll(company, pageNumber, user.getItemsPerPage());
		
		categoryItemLogList2 = logDelegate.findCategoryItemLogsWithFilter(company, filter, EntityLogEnum.CATEGORY_ITEM, pageNumber, user.getItemsPerPage());

		portalActivityLogList2 = portalActivityLogDelegate.findAll(company, pageNumber, user.getItemsPerPage());

		categoryItemLogList = categoryItemLogList2.getList();
		logList = logList2.getList();
		memberLogList = memberLogList2.getList();

		portalActivityLogList = portalActivityLogDelegate.findAll(company, pageNumber, user.getItemsPerPage())
				.getList();
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setTotalItems() {
		this.totalItems = logDelegate.findAll(company, pageNumber, 10).getTotal();
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public List<Log> getLogList() {
		return logList;
	}

	public void setLogList(List<Log> logList) {
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
		if (company != null) {
			session.put(FrontCompanyInterceptor.COMPANY_REQUEST_KEY, company.getId());

			PagingUtil pagingUtil = new PagingUtil(logList2.getTotal(), user.getItemsPerPage(), pageNumber, 5);
			// System.out.println("Item Count: "+pagingUtil.getTotalItems());
			// System.out.println("Page Count: " + pagingUtil.getTotalPages());
			request.setAttribute("pagingUtil", pagingUtil);

			return SUCCESS;
		}
		return Action.NONE;
	}

	public String memberLogs() throws Exception {
		// System.out.println("ENTERING memberLogs METHOD....");
		// System.out.println("MEMBER LOG LIST SIZE : " + memberLogList.size());
		if (company != null) {
			session.put(FrontCompanyInterceptor.COMPANY_REQUEST_KEY, company.getId());

			PagingUtil pagingUtil = new PagingUtil(memberLogList2.getTotal(), user.getItemsPerPage(), pageNumber, 5);
			// System.out.println("Item Count: "+pagingUtil.getTotalItems());
			// System.out.println("Page Count: " + pagingUtil.getTotalPages());
			request.setAttribute("pagingUtil", pagingUtil);

			return SUCCESS;
		}
		return Action.NONE;
	}
	
	public String testLogs() throws Exception {
		// System.out.println("ENTERING memberLogs METHOD....");
		// System.out.println("MEMBER LOG LIST SIZE : " + memberLogList.size());
		if (company != null) {
			session.put(FrontCompanyInterceptor.COMPANY_REQUEST_KEY, company.getId());

			PagingUtil pagingUtil = new PagingUtil(categoryItemLogList2.getTotal(), user.getItemsPerPage(), pageNumber, 5);
			// System.out.println("Item Count: "+pagingUtil.getTotalItems());
			// System.out.println("Page Count: " + pagingUtil.getTotalPages());
			request.setAttribute("pagingUtil", pagingUtil);

			return SUCCESS;
		}
		return Action.NONE;
	}

	public String portalActivityLogs() throws Exception {
		// System.out.println("ENTERING memberLogs METHOD....");
		// System.out.println("MEMBER LOG LIST SIZE : " + memberLogList.size());
		if (company != null) {
			session.put(FrontCompanyInterceptor.COMPANY_REQUEST_KEY, company.getId());

			PagingUtil pagingUtil = new PagingUtil(portalActivityLogList2.getTotal(), user.getItemsPerPage(),
					pageNumber, 5);
			// System.out.println("Item Count: "+pagingUtil.getTotalItems());
			// System.out.println("Page Count: " + pagingUtil.getTotalPages());
			request.setAttribute("pagingUtil", pagingUtil);

			return SUCCESS;
		}
		return Action.NONE;
	}

	public String downloadPortalActivityLogExcel() throws Exception {
		List<PortalActivityLog> logsForDownload = null;
		Boolean printAll = false;

		if (request.getParameter("filter").equals("all")) {
			logsForDownload = portalActivityLogDelegate.findAll(company).getList();
		} else {
			String str_fromDate = request.getParameter("fromDate");
			String str_toDate = request.getParameter("toDate");
			DateFormat formatter;
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formatter.parse(str_toDate);
			c1.setTime(formatter.parse(str_fromDate));
			c1.add(Calendar.DATE, -1);
			c2.setTime(formatter.parse(str_toDate));
			c2.add(Calendar.DATE, 1);
			str_fromDate = formatter.format(c1.getTime());
			str_toDate = formatter.format(c2.getTime());
			Date fromDate = (Date) formatter.parse(str_fromDate);
			Date toDate = (Date) formatter.parse(str_toDate);

			logsForDownload = portalActivityLogDelegate.findLogsByDate(company, fromDate, toDate).getList();
		}

		/*String basePath = servletContext.getRealPath("");
		String locationPath = basePath + File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName = "Forms_Submission_Report.xls";
		filePath = locationPath + "reports" + File.separatorChar + fileName;
		*/
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="Portal_Activity_Logs.xls";
		filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
		
		
		// filePath = locationPath + fileName;
		logger.debug("filepath (logs) : " + filePath);
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		HSSFRow r = null;
		HSSFCell c = null;
		HSSFCellStyle style;
		HSSFDataFormat format = wb.createDataFormat();

		wb.setSheetName(0, "PORTAL ACTIVITY LOGS - ");

		r = s.createRow(0);
		c = r.createCell((short) 0);
		c.setCellValue("Date");
		c = r.createCell((short) 1);
		c.setCellValue("Section");
		c = r.createCell((short) 2);
		c.setCellValue("Topic");
		c = r.createCell((short) 3);
		c.setCellValue("Remarks");
		c = r.createCell((short) 4);
		c.setCellValue("Company");
		c = r.createCell((short) 5);
		c.setCellValue("Sub Company");
		c = r.createCell((short) 6);
		c.setCellValue("Member");

		s.setColumnWidth((short) 0, (short) 9000);
		s.setColumnWidth((short) 1, (short) 9000);
		s.setColumnWidth((short) 2, (short) 15000);
		s.setColumnWidth((short) 3, (short) 9000);
		s.setColumnWidth((short) 4, (short) 9000);
		s.setColumnWidth((short) 5, (short) 9000);
		s.setColumnWidth((short) 6, (short) 9000);

		short rowNum = 1;

		for (PortalActivityLog e : logsForDownload) {
			r = s.createRow((short) ++rowNum);
			c = r.createCell((short) 0);
			c.setCellValue(e.getCreatedOn().toString());
			c = r.createCell((short) 1);
			c.setCellValue(e.getSection());
			c = r.createCell((short) 2);
			c.setCellValue(e.getTopic());
			c = r.createCell((short) 3);
			c.setCellValue(e.getRemarks());

			if (!isNull(e.getMemberParentCompany())) {
				c = r.createCell((short) 4);
				c.setCellValue(e.getMemberParentCompany());
				c = r.createCell((short) 5);
				c.setCellValue(e.getMemberCompany());
			} else {
				c = r.createCell((short) 4);
				c.setCellValue(e.getMemberCompany());
				c = r.createCell((short) 5);
				c.setCellValue("");
			}

			c = r.createCell((short) 6);
			c.setCellValue(e.getMember().getFirstname() + " " + e.getMember().getLastname());

		}

		wb.write(out);
		out.close();

		logger.debug("start download...");
		download(filePath);

		return SUCCESS;
	}

	public void download(String filePath) throws Exception {

		File file = new File(filePath);
		if (!file.exists()) {
			logger.fatal("Unabled to locate file: " + filePath);
		}

		fInStream = new FileInputStream(file);
		inputStream = new FileInputStream(file);
		contentLength = file.length();
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileInputStream getFInStream() {
		return fInStream;
	}

	public void setFInStream(FileInputStream inStream) {
		fInStream = inStream;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public FileInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
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

	public List<MemberLog> getMemberLogList() {
		return memberLogList;
	}

	public void setMemberLogList(List<MemberLog> memberLogList) {
		this.memberLogList = memberLogList;
	}

	public List<PortalActivityLog> getPortalActivityLogList() {
		return portalActivityLogList;
	}

	public void setPortalActivityLogList(List<PortalActivityLog> portalActivityLogList) {
		this.portalActivityLogList = portalActivityLogList;
	}

	protected boolean isNull(Object param) {
		return null == param;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public List<Log> getCategoryItemLogList() {
		return categoryItemLogList;
	}

	public void setCategoryItemLogList(List<Log> categoryItemLogList) {
		this.categoryItemLogList = categoryItemLogList;
	}
	
	
	
	
}
