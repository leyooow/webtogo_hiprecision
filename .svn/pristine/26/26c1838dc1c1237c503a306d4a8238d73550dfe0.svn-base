package com.ivant.cms.action.admin;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.ivant.cms.beans.NameBean;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;

public class ListPromoCodeAction implements Action, UserAware, PagingAware, CompanyAware {
	private PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	HttpServletRequest request;
	ServletContext servletContext;
	
	private List<CategoryItem> categoryItems;
	
	private User user;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	private List<PromoCode> promoCodes;
	
	public String execute() throws Exception {
		ObjectList<PromoCode> objectList = promoCodeDelegate.findAllWithPaging(user.getCompany(), user.getItemsPerPage(), page, null, null, null);
		promoCodes = objectList.getList();
		request = ServletActionContext.getRequest();
		Company company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		if(company.getName().equals("hiprecisiononlinestore")) {
			setCategoryItems(categoryItemDelegate.findAll(company).getList());
		}
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
		this.totalItems = promoCodeDelegate.findAll(company).getList().size();
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public void setUser (User user) {
		this.user = user;
	}
	
	public List<PromoCode> getPromoCodes() {
		return promoCodes;
	}
	
	@Override
	public void setCompany(Company company) {
		// TODO Auto-generated method stub
	}

	public List<CategoryItem> getCategoryItems() {
		return categoryItems;
	}

	public void setCategoryItems(List<CategoryItem> categoryItems) {
		this.categoryItems = categoryItems;
	}
	
	public List<NameBean> getGurkkaProducts(){
		return null;
	}
}
