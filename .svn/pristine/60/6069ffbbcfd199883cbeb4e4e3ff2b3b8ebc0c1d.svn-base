package com.ivant.cms.interceptors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CategoryAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CategoryInterceptor implements Interceptor{

	private static final long serialVersionUID = -8753042811521054802L;
	private static final Logger logger = Logger.getLogger(CategoryInterceptor.class);
	private static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	
	public void destroy() {
		logger.debug("DESTROYING CATEGORY INTERCEPTOR...");
	}

	public void init() {
		logger.debug("CREATING CATEGORY INTERCEPTOR...");
	}

	public String intercept(ActionInvocation inv) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = user.getCompany();
		
		Category category = null;
		try {
			category = categoryDelegate.find(Long.parseLong(request.getParameter("category_id")));
			if(!category.getCompany().equals(company)) {
				category = null;
			}
		}
		catch(Exception e) {
			logger.debug("category is null.");
		}
		
		Object action = inv.getAction();
		
		if (action instanceof CategoryAware) {
			((CategoryAware)action).setCategory(category);
		}
		
		return inv.invoke();
	}

	
}

