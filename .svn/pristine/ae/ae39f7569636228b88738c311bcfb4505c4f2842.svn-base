package com.ivant.cms.action.admin;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.BusinessType;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.Encryption;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class UserAction extends ActionSupport implements Preparable, ServletRequestAware, UserAware , PagingAware{

	private static final long serialVersionUID = -4843617652737904181L;
	private Logger logger = Logger.getLogger(getClass());
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private UserDelegate userDelegate = UserDelegate.getInstance();	
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private LogDelegate logDelegate = LogDelegate.getInstance();
	private CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	
	protected CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	
	private User user;
	private User user2;
	private HttpServletRequest request;
	
	private Company company;
	private String selectedCompany;
	private List<Company> companies;
	private List<UserType> userTypes;	
	private String userType;	
	
	private String forbiddenSinglePageList;
	private String forbiddenMultiPageList;
	private String forbiddenGroupList;
	private String forbiddenCategoryList;
	
	private int page;
	private int totalItems;
	private int itemsPerPage;
	private int pageNumber;
	
	private List<Log> logList;
	private ObjectList<Log> logList2;
	
	private List<CategoryItem> companiesAgian;
	
	private String startDate = "";
	private String endDate = "";
	
	public String getStartDate(){
		return startDate;
	}
	
	public String getEndDate(){
		return endDate;
	}
	
	public void setPage(int page){
		this.page = page;
	}
	
	public int getPage(){
		return page;
	}
	
	public void setTotalItems() {
		this.totalItems=logDelegate.findAll(user2, pageNumber, user.getItemsPerPage()).getTotal();
	}
	
	public int getTotalItems(){
		return totalItems;
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	public List<Log> getLogList(){
		return logList;
	}
	
	public void setLogList(List<Log> logList){
		this.logList = logList;
	}
	
	public void prepare() throws Exception {
		String[] orderBy = {"nameEditable"};
		companies = companyDelegate.findAll(orderBy).getList();
		
		if(request.getParameter("pageNumber")==null) pageNumber=0;
		else pageNumber = Integer.parseInt(request.getParameter("pageNumber"))-1;
		
		if(request.getParameter("startDate")!=null)startDate =request.getParameter("startDate"); 
		if(request.getParameter("endDate")!=null) endDate =request.getParameter("endDate");
		
		try {
			long userId = Long.parseLong(request.getParameter("user_id"));
			user2 = userDelegate.find(userId);
			logger.info("1  " + user2);
		}
		catch(Exception e) {
			user2  = new User();
		}
		
		//System.out.println("User Action-User info: "+ user2);
		
		if(startDate.length()>0 && endDate.length()>0){
			logList2 = logDelegate.findAll(user2, pageNumber, user.getItemsPerPage(), startDate, endDate);
		}else{
			logList2 = logDelegate.findAll(user2, pageNumber, user.getItemsPerPage());
		}
		
		logList = logList2.getList();
		//System.out.println("Logs of the user: "+logList2.getTotal());		
		ObjectList<CategoryItem> companyAgianList = new ObjectList<CategoryItem>();
		company = companyDelegate.find(user.getCompany().getId());
		if(company.getName().equalsIgnoreCase("agian")){
			Group group = groupDelegate.find(613L);
			companyAgianList = categoryItemDelegate.findAllByGroup(company, group);
			
		}
		
		companiesAgian = companyAgianList.getList();
	}
	
	@Override
	public String execute() throws Exception {
		return Action.NONE;
	}
	
	public String save() {
		if(user.getUserType() != UserType.SUPER_USER   &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR &&  user.getUserType() != UserType.COMPANY_ADMINISTRATOR &&  user.getUserType() != UserType.SYSTEM_ADMINISTRATOR) {
			return Action.ERROR;
		}		
		
				if (request.getParameter("company_id")  != null && request.getParameter("company_id").length()!=0) {
					Company  cc = companyDelegate.find(new Long (request.getParameter("company_id")));
					if (cc != null) 
					{
						user2.setCompany(cc);
					}
				} 
				// set the user types
		setUserTypes(Arrays.asList(UserType.values()));		
					
		userType = request.getParameter("userType");
		user2.setActive(Boolean.TRUE);
		try{
			user2.setBranch(request.getParameter("branch")!=null ? request.getParameter("branch") : "");
			user2.setInfo1(request.getParameter("info1")!=null ? request.getParameter("info1") : "");
			user2.setInfo2(request.getParameter("info2")!=null ? request.getParameter("info2") : "");
			user2.setInfo3(request.getParameter("info3")!=null ? request.getParameter("info3") : "");
			user2.setInfo4(request.getParameter("info4")!=null ? request.getParameter("info4") : "");
		}
		catch(Exception e){
			
		}
		
		if(company.getName().equalsIgnoreCase("agian")){
			user2.setInfo1(request.getParameter("info1"));
		}
		
		if (request.getParameter("password") != null){
			String password = request.getParameter("password");
			user2.setPassword(Encryption.hash(password));
		}
		if (request.getParameter("newpassword") != null && !request.getParameter("newpassword").equals("")){
			String newPassword = request.getParameter("newpassword");
			user2.setPassword(Encryption.hash(newPassword));
		}
		if (StringUtils.isEmpty(request.getParameter("user_id")))
			{
			for (int i = 0; i< userTypes.size(); i++){	
				if (userTypes.get(i).getValue().toString().equals(userType)){
					user2.setUserType(userTypes.get(i));
					break;	
				}
			}
			
			logger.info("3  " + user2);
			//	user2.setUserType(UserType.COMPANY_ADMINISTRATOR);
			//	user2.setItemsPerPage(20);
				userDelegate.insert(user2);
			}
		else
			{
			for (int i = 0; i< userTypes.size(); i++){	
				if (userTypes.get(i).getValue().toString().equalsIgnoreCase(UserType.valueOf(userType).toString())){
					user2.setUserType(userTypes.get(i));
					break;	
				}			
			}
			//	user2.setUserType(UserType.COMPANY_ADMINISTRATOR);
				userDelegate.update(user2);
		}
				
		return Action.SUCCESS;
	}
	
	public String saveUserPageRights(){
		try{
			List<SinglePage> singlePages = new ArrayList<SinglePage>();
			List<MultiPage> multiPages = new ArrayList<MultiPage>();
			List<Group> groups = new ArrayList<Group>();
			List<Category> categories = new ArrayList<Category>();
			if(forbiddenSinglePageList != null && !StringUtils.isEmpty(forbiddenSinglePageList)){
				String[] stringSinglePageList = forbiddenSinglePageList.split(",");
				for(int i=0; i< stringSinglePageList.length; i++)
					singlePages.add(singlePageDelegate.find(Long.parseLong(stringSinglePageList[i])));
			}
			if(forbiddenMultiPageList != null && !StringUtils.isEmpty(forbiddenMultiPageList)){
				String[] stringMultiPageList = forbiddenMultiPageList.split(",");
				for(int i=0; i< stringMultiPageList.length; i++)
					multiPages.add(multiPageDelegate.find(Long.parseLong(stringMultiPageList[i])));
			}
			if(forbiddenGroupList != null && !StringUtils.isEmpty(forbiddenGroupList)){
				String[] stringGroupList = forbiddenGroupList.split(",");
				for(int i=0; i< stringGroupList.length; i++)
					groups.add(groupDelegate.find(Long.parseLong(stringGroupList[i])));
			}
			
			if(forbiddenCategoryList != null && !StringUtils.isEmpty(forbiddenCategoryList)){
				String[] stringCategoryList = forbiddenCategoryList.split(",");
				for(int i=0; i< stringCategoryList.length; i++)
					categories.add(categoryDelegate.find(Long.parseLong(stringCategoryList[i])));
			}
			user2.setForbiddenMultiPages(multiPages);
			user2.setForbiddenSinglePages(singlePages);
			user2.setForbiddenGroups(groups);
			user2.setForbiddenCategories(categories);
			userDelegate.update(user2);
			
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public String delete() {
		if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR && user.getUserType() != UserType.COMPANY_ADMINISTRATOR && user.getUserType() != UserType.SYSTEM_ADMINISTRATOR ) {
			return Action.ERROR;
		}
		if(user2.getId() > 0) {
			userDelegate.delete(user2);	
		}
		
		return Action.SUCCESS;
	}
	
	
	
	private boolean commonParamsValid() {
		if(user.getUserType() != UserType.SUPER_USER   &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return false;
		} 
//		if(user.getCompany() == null) {
//			return false;
//		}		
		if(user2 == null) {
			return false;
		}
		
		return true;
	}
	
	
	
	public String edit() throws Exception{
	/*	if(!commonParamsValid()) {
			return Action.ERROR;
		}
	*/	
		// set the user types
		setUserTypes(Arrays.asList(UserType.values()));	
		
		if(user2 != null && (!user2.getUserType().equals(UserType.SUPER_USER) && !user2.getUserType().equals(UserType.WEBTOGO_ADMINISTRATOR))){
			List<SinglePage> singlePages = singlePageDelegate.findAll(user2.getCompany()).getList();
			List<MultiPage> multiPages = multiPageDelegate.findAll(user2.getCompany()).getList();
			List<Group> groups = groupDelegate.findAll(user2.getCompany()).getList();
			List<Category> categories = categoryDelegate.findAll(user2.getCompany()).getList();
			request.setAttribute("singlePages", singlePages);
			request.setAttribute("multiPages", multiPages);
			request.setAttribute("groups", groups);
			request.setAttribute("categories", categories);
			Map<Long, Boolean> forbiddenSinglePages = new HashMap<Long, Boolean>();
			Map<Long, Boolean> forbiddenMultiPages = new HashMap<Long, Boolean>();
			Map<Long, Boolean> forbiddenGroups = new HashMap<Long, Boolean>();
			Map<Long, Boolean> forbiddenCategories = new HashMap<Long, Boolean>();
			List<SinglePage> forbiddenSinglePagesList = user2.getForbiddenSinglePages();
			List<MultiPage> forbiddenMultiPagesList = user2.getForbiddenMultiPages();
			
			try
			{
				if(user2.getForbiddenSinglePages() != null){
					for(int i=0; i<forbiddenSinglePagesList.size(); i++){
						forbiddenSinglePages.put(forbiddenSinglePagesList.get(i).getId(), false);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			try
			{
				if(user2.getForbiddenMultiPages() != null){
					for(int i=0; i<forbiddenMultiPagesList.size(); i++){
						forbiddenMultiPages.put(forbiddenMultiPagesList.get(i).getId(), false);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			try
			{
				if(user2.getForbiddenGroups() != null){
					for(Group forbiddenGroup : user2.getForbiddenGroups())
						forbiddenGroups.put(forbiddenGroup.getId(), false);
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			try
			{
				if(user2.getForbiddenCategories() != null){
					for(Category forbiddenCategory : user2.getForbiddenCategories())
						forbiddenCategories.put(forbiddenCategory.getId(), false);
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			
			request.setAttribute("forbiddenSinglePages", forbiddenSinglePages);
			request.setAttribute("forbiddenMultiPages", forbiddenMultiPages);
			request.setAttribute("forbiddenGroups", forbiddenGroups);
			request.setAttribute("forbiddenCategories", forbiddenCategories);
		}
		
		PagingUtil pagingUtil = new PagingUtil(logList2.getTotal(), user.getItemsPerPage(), pageNumber, 5);
		//System.out.println("Item Count: "+pagingUtil.getTotalItems());
		//System.out.println("Page Count: " + pagingUtil.getTotalPages());
		request.setAttribute("pagingUtil", pagingUtil);
		
		return Action.SUCCESS;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	
	public String getSelectedCompany()
	{
		return selectedCompany;
		
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
	
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public void setUserTypes(List<UserType> userTypes) {
		this.userTypes = userTypes;
	}

	public List<UserType> getUserTypes() {
		return userTypes;
	}

	public void setForbiddenSinglePageList(String forbiddenSinglePageList) {
		this.forbiddenSinglePageList = forbiddenSinglePageList;
	}

	public void setForbiddenMultiPageList(String forbiddenMultiPageList) {
		this.forbiddenMultiPageList = forbiddenMultiPageList;
	}	
	
	public void setForbiddenGroupList(String forbiddenGroupList){
		this.forbiddenGroupList = forbiddenGroupList;
	}
	
	public String getForbiddenCategoryList() {
		return forbiddenCategoryList;
	}

	public void setForbiddenCategoryList(String forbiddenCategoryList) {
		this.forbiddenCategoryList = forbiddenCategoryList;
	}

	public List<CategoryItem> getCompaniesAgian() {
		return companiesAgian;
	}

	public void setCompaniesAgian(List<CategoryItem> companiesAgian) {
		this.companiesAgian = companiesAgian;
	}
	
	
	/*--------  Get Wendys Branch(es) ---------------*/
	 
	public List<CategoryItem> getStores()
	{
		company = companyDelegate.find(404L);
		System.out.println("################# inside user action #######################");
		final Group stores = groupDelegate.find(company, "stores");
		System.out.println("#################" +company.getName()+ "#######################");
		if(stores != null)
		{
			List<CategoryItem> tempItem = categoryItemDelegate.findAllEnabledWithPaging(company, stores, -1, -1, null).getList();
			if(tempItem.size()>0){
				Collections.sort(tempItem, new Comparator<CategoryItem>(){
					@Override
					public int compare(final CategoryItem object1, final CategoryItem object2){
						return object1.getName().compareTo(object2.getName());
					}
				});
			}
			return tempItem;
		}
		return null;
	}
	
}
