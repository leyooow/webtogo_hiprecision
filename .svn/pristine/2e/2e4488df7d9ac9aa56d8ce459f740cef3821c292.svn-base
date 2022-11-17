package com.ivant.cms.action.admin;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.AttributeDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.entity.Attribute;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.GroupAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.ivant.cms.delegate.LastUpdatedDelegate;

public class ListAttributeAction extends ActionSupport implements UserAware, CompanyAware, ServletRequestAware, GroupAware {

	private static final long serialVersionUID = 8456819791013861486L;
	private static final Logger logger = Logger.getLogger(ListBrandAction.class);
	private static final AttributeDelegate attributeDelegate = AttributeDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	
	private User user;
	private Company company;
	private Group group;
			
	private ServletRequest request;
	private int page;
	private int totalItems;

	private int itemsPerPage;
	
	private List<Attribute> attributes; 
	private Attribute attribute;
	 

	@Override
	public String execute() throws Exception {
		
		if(company == null) {
			return ERROR;
		}
		
		if(group == null) {
			
			return ERROR;
		}
		
		if(!group.getHasAttributes()) {
			return ERROR;
		}
		
		if(!group.getCompany().equals(company)) {
			return ERROR;
		}
				
		attributes = attributeDelegate.findAll(company, group).getList();
		return SUCCESS;
	}
	
	
	public String sort(){
		if(user.getCompany() == null) {
			return Action.ERROR;
		}
		if(attribute != null) {
			if(!attribute.getCompany().equals(user.getCompany())) {
				return Action.ERROR;
			}
		}
		
		Order[] orders = {Order.asc("sortOrder"),Order.asc("name")};
				
		attributes = attributeDelegate.findAll(user.getCompany(), group, orders).getList();			

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
			attribute = attributeDelegate.find(Long.parseLong(id[i]));
			attributeDelegate.update(attribute);			
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
	
	public List<Attribute> getAttributes() {
		return attributes;
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

}
