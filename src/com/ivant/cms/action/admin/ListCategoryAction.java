package com.ivant.cms.action.admin;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.GroupAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ListCategoryAction extends ActionSupport implements  Preparable, ServletRequestAware, 
				UserAware, PagingAware, CompanyAware, GroupAware {

	private static final long serialVersionUID = -8327848488407758491L;
	private static final Logger logger = Logger.getLogger(ListCategoryAction.class);
	private static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private static final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	
	private User user;
	private ServletRequest request;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	private int chosenCategoryId;
	
	private List<Category> categories;
	private List<CategoryItem> catItems;
	private Category category;
	private CategoryItem categoryItem;
	private Group group;
	private boolean showAll = true;
	private Company company;
	private CompanySettings companySettings;
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the companySettings
	 */
	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	/**
	 * @param companySettings the companySettings to set
	 */
	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}

	private String returnValue = Action.SUCCESS;
	
	@Override
	public String execute() throws Exception {
		itemsPerPage=1000;
		if(user.getCompany() == null) {
			returnValue = Action.ERROR;
		}
		if(category != null) {
			if(!category.getCompany().equals(user.getCompany())) {
				returnValue = Action.ERROR;
			}
		}
		
		company = user.getCompany();
		setCompanySettings(company.getCompanySettings());
		
		Order[] orders = {Order.asc("parentCategory"),Order.asc("sortOrder"),Order.asc("name")};
		
		if (group == null){
			group = groupDelegate.find(Long.parseLong(request.getParameter("group_id")));
		}
		if(category == null) { 
			if(showAll) {
				categories = categoryDelegate.findAllWithPaging(user.getCompany(), null, group, user.getItemsPerPage(), page, orders, true).getList();
			}
			else {
				categories = categoryDelegate.findAllWithPaging(user.getCompany(), null, group, user.getItemsPerPage(), page, orders, false).getList();
			}	
		}
		else {
			categories = categoryDelegate.findAllWithPaging(user.getCompany(), category, group, totalItems, page, orders, false).getList();						
		}
		
		if(categories != null) {
			if(user.getCompany().getCompanySettings().getHasUserRights()){
				for(Category forbiddenCategory: user.getForbiddenCategories()){
					categories.remove(forbiddenCategory);
				}		
			}
		}
			
		return returnValue;
	}
	
	public void prepare() throws Exception {
//		if(!group.getCompany().equals(user.getCompany())) {
//			group = null;
//		} 
				
		//try to create the category
		try {
			long categoryId = Long.parseLong(request.getParameter("category_id"));
			if(categoryId == 0) {
				returnValue = Action.ERROR;
			}
			category = categoryDelegate.find(categoryId);
			showAll = false;
		}
		catch(Exception e) { 
			logger.debug("no category created.");
		}
	}
	
	public String sort(){
		if(user.getCompany() == null) {
			return Action.ERROR;
		}
		if(category != null) {
			if(!category.getCompany().equals(user.getCompany())) {
				return Action.ERROR;
			}
		}
		
		Order[] orders = {Order.asc("parentCategory"),Order.asc("sortOrder"),Order.asc("name")};
				
		categories = categoryDelegate.findAllPublished(user.getCompany(), category, group,false,orders).getList();			

		return Action.SUCCESS;
	}
	
	public String sortCatItems(){
		if(user.getCompany() == null) {
			return Action.ERROR;
		}
		Category parent = categoryDelegate.find(Long.parseLong(request.getParameter("catId")));
		Order[] orders = {Order.asc("sortOrder"),Order.asc("name")};
		catItems = categoryItemDelegate.findAll(company, parent, orders, true).getList();
		request.setAttribute("parent", parent.getName());
		request.setAttribute("category", parent);
		return Action.SUCCESS;
	}
	
	public String update(){
		String[] id, sortOrder;
		id = request.getParameterValues("catId");
		sortOrder = request.getParameterValues("sortOrder");
		
		if(id == null){
			return Action.NONE;
		}
		for (int i=0; i<id.length; i++){
			category = categoryDelegate.find(Long.parseLong(id[i]));
			category.setSortOrder(Integer.parseInt(sortOrder[i]));
			categoryDelegate.update(category);			
		}
		lastUpdatedDelegate.saveLastUpdated(company);
		return Action.SUCCESS;
	}
	
	public String updateItems(){
		String[] id, sortOrder;
		id = request.getParameterValues("catId");
		sortOrder = request.getParameterValues("sortOrder");
		
		if(id == null){
			return Action.NONE;
		}
		for (int i=0; i<id.length; i++){
			categoryItem = categoryItemDelegate.find(Long.parseLong(id[i]));
			categoryItem.setSortOrder(Integer.parseInt(sortOrder[i]));
			categoryItemDelegate.update(categoryItem);
		}
		lastUpdatedDelegate.saveLastUpdated(company);
		return Action.SUCCESS;
	}
	
	public String updateChildrenCategories(){
		String[] id, sortOrder;
		id = request.getParameterValues("catId");
		sortOrder = request.getParameterValues("sortOrder");
		
		if(id == null){
			return Action.NONE;
		}
		for (int i=0; i<id.length; i++){
			//System.out.println("------ID------"+id[i]);
			category = categoryDelegate.find(Long.parseLong(id[i]));
			category.setSortOrder(Integer.parseInt(sortOrder[i]));
			categoryDelegate.update(category);
		}
		lastUpdatedDelegate.saveLastUpdated(company);
		return Action.SUCCESS;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
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

	public void setTotalItems() {
//		if(category != null) {
//			totalItems = category.get.size();
//		}
//		else {
			totalItems = categoryDelegate.findAll(user.getCompany(), group).getTotal();
//}
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public Group getGroup() {
		return group;
	}

	/**
	 * @param catItems the catItems to set
	 */
	public void setCatItems(List<CategoryItem> catItems) {
		this.catItems = catItems;
	}

	/**
	 * @return the catItems
	 */
	public List<CategoryItem> getCatItems() {
		return catItems;
	}

	/**
	 * @param categoryItem the categoryItem to set
	 */
	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}

	/**
	 * @return the categoryItem
	 */
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	

	public Map<String,Group> getGroupMap()
	{
		// get the groups
		final List<Group> groupList = groupDelegate.findAll(company).getList();
		final Map<String, Group> groupMap = new HashMap<String, Group>();
		final Map<Long, Group> groupIdMap = new HashMap<Long, Group>();
		for(final Group group : groupList)
		{
			final String jspName = group.getName().toLowerCase();
			//group.setLanguage(language);
			//final Menu menu = new Menu(group, httpServer + "/" + jspName + ".do");
			//menuList.put(jspName, menu);
			
			groupMap.put(group.getName(), group);
			groupMap.put(jspName, group);
			groupIdMap.put(group.getId(), group);
		}
		request.setAttribute("groupList", groupList);
		request.setAttribute("groupMap", groupMap);
		request.setAttribute("groupIdMap", groupIdMap);
		return groupMap;
	}
	/*--------  Get Wendys Branch(es) ---------------*/
	 
	public List<CategoryItem> getStores()
	{
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
	
	
	
}
