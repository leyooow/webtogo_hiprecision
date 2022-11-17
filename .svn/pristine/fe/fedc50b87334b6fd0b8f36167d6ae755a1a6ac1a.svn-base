package com.ivant.cms.action.admin;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.PromoDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Promo;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;

public class ListPromoAction implements Action, UserAware, PagingAware, CompanyAware {
	private PromoDelegate promoDelegate = PromoDelegate.getInstance();
	HttpServletRequest request;
	ServletContext servletContext;
	
	private User user;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	private List<Promo> promos;
	
	public String execute() throws Exception {
		ObjectList<Promo> objectList = promoDelegate.findAll(user.getCompany());
		promos = objectList.getList();
		return Action.SUCCESS;
	}
	
	public int getPage() {
		return page;
	} 

	public void setPage(int page) {
		this.page = page;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	public int getTotalItems() {
		return totalItems;
	}
	
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public void setTotalItems() {
		request = ServletActionContext.getRequest();
		Company company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		this.totalItems = promoDelegate.findAll(company).getList().size();
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public void setUser (User user) {
		this.user = user;
	}
	
	public List<Promo> getPromos() {
		return promos;
	}
	
	@Override
	public void setCompany(Company company) {
		// TODO Auto-generated method stub
	}
}
