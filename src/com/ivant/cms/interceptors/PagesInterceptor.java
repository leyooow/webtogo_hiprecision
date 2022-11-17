package com.ivant.cms.interceptors;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/*
 * This class interceptors sets the single pages and multi pages on the http request
 * when the page loads
 */

public class PagesInterceptor implements Interceptor  {

	private static final long serialVersionUID = 3165860646344004691L;
	private static final Logger logger = Logger.getLogger(PagesInterceptor.class);
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	
	public static final String PAGELIST_REQUEST_KEY = "menulist";
	
	public void destroy() {
		logger.debug("DESTROYING PAGES INTERCEPTOR...");
	}

	public void init() {
		logger.debug("INITIALIZING PAGES INTERCEPTOR...");
	}

	public String intercept(ActionInvocation invocatoin) throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			User user = (User) request.getAttribute(UserInterceptor.USER_REQUEST_KEY);
			
			List<Menu> menuList = new ArrayList<Menu>();
			List<SinglePage> singlePages = singlePageDelegate.findAll(user.getCompany()).getList();
			List<MultiPage> multiPages = multiPageDelegate.findAll(user.getCompany()).getList();
			
			if(user.getCompany().getCompanySettings().getHasUserRights() != null){
				if(user.getCompany().getCompanySettings().getHasUserRights().equals(Boolean.TRUE)){
					if(user.getForbiddenSinglePages() != null)
						singlePages.removeAll(user.getForbiddenSinglePages());
					if(user.getForbiddenMultiPages() != null)
						multiPages.removeAll(user.getForbiddenMultiPages()); 
				}
			}
			
			// add the single pages to the list
			for(SinglePage singlePage : singlePages) {
				if(!singlePage.getIsForm()) {
					menuList.add(new Menu(singlePage.getName(), "editsinglepage.do?singlepage_id=" + singlePage.getId()));
				}
			}
			
			// add the multi pages to the list
			for(MultiPage multiPage : multiPages) {
				menuList.add(new Menu(multiPage.getName(), "editmultipagechildlist.do?multipage_id=" + multiPage.getId()));
			}
			
			//menuList.add(new Menu ("Contact Us", "editcontactus.do"));
//			if(singlePages.size() == 0 && multiPages.size() == 0)
//				return "dashboard";
			request.setAttribute(PagesInterceptor.PAGELIST_REQUEST_KEY, menuList); 
		} 
		catch(Exception e) {
			logger.debug("error in intercepting action in PageInterceptor...");
			e.printStackTrace();
		}
		
		return invocatoin.invoke();
	}

}
