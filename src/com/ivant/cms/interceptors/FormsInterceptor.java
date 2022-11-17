package com.ivant.cms.interceptors;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class FormsInterceptor implements Interceptor {

	private static final long serialVersionUID = 1112675179116937133L;
	private static final Logger logger = Logger.getLogger(FormsInterceptor.class);
	private static final FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	
	public static String FORM_PAGE_REQUEST_KEY = "formPageList";
	
	public void destroy() {
		logger.debug("DESTROYING FORMS INTERCEPTOR...");
	}

	public void init() {
		logger.debug("CREATING FORMS INTERCEPTOR...");
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			User user = (User) request.getAttribute(UserInterceptor.USER_REQUEST_KEY);
			List<Menu> formPageMenu = new ArrayList<Menu>();
			
			
			List<FormPage> formPageList = formPageDelegate.findAll(user.getCompany()).getList();
			for(FormPage formPage : formPageList) {
				formPageMenu.add(new Menu(formPage.getName(), "editformpage.do?form_id=" + formPage.getId()));
			} 
			 
			List<SinglePage> singlePageForms = singlePageDelegate.findAllForms(user.getCompany()).getList();
			for(SinglePage singlePage : singlePageForms) {
				formPageMenu.add(new Menu(singlePage.getName(), "editsinglepage.do?singlepage_id=" + singlePage.getId()));
			}
			
			request.setAttribute(FormsInterceptor.FORM_PAGE_REQUEST_KEY, formPageMenu);
		}
		catch(Exception e) {
			logger.debug("problem intecepting action in FormsInterceptor");
		}
		return invocation.invoke();
	}

}
