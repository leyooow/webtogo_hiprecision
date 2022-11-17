package com.ivant.cms.action.admin;

import java.util.List;

import org.apache.log4j.Logger;

import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;

public class ListFormPageAction implements Action, UserAware, PagingAware {

	private Logger logger = Logger.getLogger(getClass());
	private FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	
	private User user;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	private List<FormPage> formPages;
	
	public String execute() throws Exception {
		if(user.getCompany() == null) {
			return Action.ERROR;
		}
		if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return Action.ERROR;
		}
		formPages = formPageDelegate.findAllWithPaging(user.getCompany(), itemsPerPage, page).getList();
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

	public void setTotalItems() {
		totalItems = formPageDelegate.findAll(user.getCompany()).getTotal();
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public List<FormPage> getFormPages() {
		return formPages;
	}

	public void setFormPages(List<FormPage> formPages) {
		this.formPages = formPages;
	}
}
