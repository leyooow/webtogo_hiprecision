package com.ivant.cms.action.admin;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;

public class ListUserAction implements Action, UserAware, PagingAware, ServletRequestAware {

	private Logger logger = Logger.getLogger(getClass());
	private UserDelegate userDelegate = UserDelegate.getInstance();
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();	
	
	protected GroupDelegate groupDelegate = GroupDelegate.getInstance();
	protected CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	
	private HttpServletRequest request;
	
	private String userCompanyID = "";

	private User user;
	private int page;
	private String companyId;
	private Company company;
	private int totalItems;
	private int itemsPerPage;
	private List<Company>  companies;
	private List<UserType> userTypes;
	
	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	private List<User> users;
	private List<CategoryItem> companiesAgian;
	
	public String execute() {
		//try implement that when something is done to users.do it will stay at the same company
		if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR &&  user.getUserType() != UserType.COMPANY_ADMINISTRATOR 
				&& user.getUserType() != UserType.SYSTEM_ADMINISTRATOR) {
			return Action.ERROR;
		}
			
		String[] order = {"username"};
		ObjectList<User> objectList;
		ObjectList<CategoryItem> companyAgianList = new ObjectList<CategoryItem>();
		if(user.getUserType() == UserType.COMPANY_ADMINISTRATOR) {
			objectList = userDelegate.findByCompanyWithPaging(itemsPerPage, page, order, user.getCompany());
		}else{
			companyId = request.getParameter("company_id1");
			if(StringUtils.isNotBlank(companyId))
		     {
				userCompanyID = companyId;
					company = companyDelegate.find(Long.valueOf(companyId));
				    objectList = userDelegate.findByCompanyWithPaging(itemsPerPage, page, order, company);
			}
			else if(request.getParameter("id")!= null){
				userCompanyID = user.getCompany().getId().toString();
				company = companyDelegate.find(user.getCompany().getId());
				objectList = userDelegate.findByCompanyWithPaging(itemsPerPage, page, order, company);
			}
			else{
				userCompanyID = user.getCompany().getId().toString();
				objectList = userDelegate.findAllWithPaging(itemsPerPage, page, order);
			}
			company = companyDelegate.find(user.getCompany().getId());
			if(company.getName().equalsIgnoreCase("agian")){
				Group group = groupDelegate.find(613L);
				companyAgianList = categoryItemDelegate.findAllByGroup(company, group);
				
			}
		}	
		if(company.getName().equalsIgnoreCase("agian")){
			companiesAgian = companyAgianList.getList();
		}
		users = objectList.getList();
		if(company.getName().equalsIgnoreCase("agian")){
			users = userDelegate.findAllByCompany(company);
		}
		if(user.getUserType() != UserType.COMPANY_ADMINISTRATOR) {
			String[] orderBy = {"nameEditable"};
			companies = companyDelegate.findAll(orderBy).getList();
		}
		// set the user types
		setUserTypes(Arrays.asList(UserType.values()));		
		
		return Action.SUCCESS;
	}
		
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getPage() {
		return page;
	} 

	public void setPage(int page) {
		this.page = page;
	}
	
	public int getTotalItems() {
		return totalItems;
	}
	
	public String getUserCompanyID() {
		return userCompanyID;
	}

	public void setTotalItems() {
		String[] order = {"username"};
		if(user.getUserType() == UserType.COMPANY_ADMINISTRATOR){
			totalItems = userDelegate.findByCompanyWithPaging(itemsPerPage, page, order, user.getCompany()).getSize();	
		}
		else if(user.getUserType() == UserType.WEBTOGO_ADMINISTRATOR || user.getUserType() == UserType.SUPER_USER || user.getUserType() == UserType.SYSTEM_ADMINISTRATOR){
			if(StringUtils.isNotBlank(request.getParameter("company_id1")))
			{
				//System.out.println("Get Company By Filter=========================================");
				Company company = companyDelegate.find(Long.parseLong(request.getParameter("company_id1")));
				totalItems = userDelegate.findByCompanyWithPaging(itemsPerPage,page,order,company).getSize();
			}
			else if(request.getParameter("id")!= null){
				//System.out.println("Get Company by Page=============================================");
				Company company = companyDelegate.find(Long.parseLong(request.getParameter("id")));
				totalItems = userDelegate.findByCompanyWithPaging(itemsPerPage,page,order,company).getSize();
			}
			else{
				//System.out.println("Get All Company===================================================");
				totalItems = userDelegate.findAll().size();
			}
		}
		else{
			totalItems = userDelegate.findAll().size();
		}	
	}
	
	/*--------  Get Wendys Branch ---------------*/
	 
	public List<CategoryItem> getStores()
	{
		company = companyDelegate.find(404L);
		System.out.println("################# inside list user action #######################");
		final Group stores = groupDelegate.find(company, "stores");
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
	
	public List<CategoryItem> getCompaniesAgianList()
	{
		company = companyDelegate.find(448L);
		
		final Group comp = groupDelegate.find(company, "Companies");
		if(comp != null)
		{
			List<CategoryItem> companiesAgianList = categoryItemDelegate.findAllEnabledWithPaging(company, comp, -1, -1, null).getList();
			if(companiesAgianList.size()>0){
				Collections.sort(companiesAgianList, new Comparator<CategoryItem>(){
					@Override
					public int compare(final CategoryItem object1, final CategoryItem object2){
						return object1.getName().compareTo(object2.getName());
					}
				});
			}
			return companiesAgianList;
		}
		return null;
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUserTypes(List<UserType> userTypes) {
		this.userTypes = userTypes;
	}

	public List<CategoryItem> getCompaniesAgian() {
		return companiesAgian;
	}

	public void setCompaniesAgian(List<CategoryItem> companiesAgian) {
		this.companiesAgian = companiesAgian;
	}
	public List<UserType> getUserTypes() {
		return userTypes;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public Map<String, CategoryItem> getCategoryItemById(){
		Map<String, CategoryItem> listById = new HashMap<String, CategoryItem>();
		List<CategoryItem> listItem = categoryItemDelegate.findAllByGroup(company, groupDelegate.find(company, "stores")).getList();
		for(CategoryItem item : listItem){
			listById.put(String.valueOf(item.getId()), item);
		}
		
		return listById;
	}
	
}
