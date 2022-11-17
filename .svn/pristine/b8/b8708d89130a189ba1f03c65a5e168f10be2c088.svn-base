package com.ivant.cms.action.admin;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.BrandDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.GroupAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ListBrandAction extends ActionSupport implements UserAware, CompanyAware, ServletRequestAware, PagingAware, GroupAware {

	private static final long serialVersionUID = 8456819791013861486L;
	private static final Logger logger = Logger.getLogger(ListBrandAction.class);
	private static final BrandDelegate brandDelegate = BrandDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	
	private User user;
	private Company company;
	private Group group;
			
	private ServletRequest request;
	private int page;
	private int totalItems;


	private int itemsPerPage;
	
	private List<Brand> brands;
	private List<Brand> allBrands;
	private Brand brand;
	 

	@Override
	public String execute() throws Exception {
		if(company == null) {
			return ERROR;
		}
	
		if(group == null) {
			return ERROR;
		}
		
		if(!group.getHasBrands()) {
			return ERROR;
		}
		
		if(!group.getCompany().equals(company)) {
			return ERROR;
		}
				
		brands = brandDelegate.findAllWithPaging(company, group, itemsPerPage, page, Order.asc("name")).getList();
		
		allBrands = brandDelegate.findAll(company, group , Order.asc("name")).getList();
		
		return SUCCESS;
	}
	
	
	public String sort(){
		if(user.getCompany() == null) {
			return Action.ERROR;
		}
		if(brand != null) {
			if(!brand.getCompany().equals(user.getCompany())) {
				return Action.ERROR;
			}
		}
		
		Order[] orders = {Order.asc("sortOrder"),Order.asc("name")};
				
		brands = brandDelegate.findAll(user.getCompany(), group, orders).getList();			

		return Action.SUCCESS;
	}
	
	public String update(){
		String[] id, sortOrder;

		sortOrder = request.getParameterValues("sortOrder");
		id = request.getParameterValues("brandId");
		if(id == null){
			return Action.NONE;
		}
		for (int i=0; i<id.length; i++){
			brand = brandDelegate.find(Long.parseLong(id[i]));
			brand.setSortOrder(Integer.parseInt(sortOrder[i]));
			brandDelegate.update(brand);			
		}
		lastUpdatedDelegate.saveLastUpdated(company);
		return Action.SUCCESS;
	}	
	

	public void setUser(User user) {
		this.user = user;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getPage() {
		return page;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setTotalItems() {
		totalItems = brandDelegate.findAll(company, group).getTotal();
	}
	
	public List<Brand> getBrands() {
		return brands;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public static GroupDelegate getGroupDelegate() {
		return groupDelegate;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}


	public List<Brand> getAllBrands() {
		return allBrands;
	}

}
