package com.ivant.cms.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.GroupAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class GroupInterceptor implements Interceptor {
	
	private static final long serialVersionUID = 8789256265401473908L;
	private static final Logger logger = Logger.getLogger(GroupInterceptor.class);
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	
	public static String GROUP_REQUEST_KEY = "group";
	public static String GROUPLIST_REQUEST_KEY = "groupList";
	
	public void destroy() {
		logger.debug("DESTROYING GROUP INTERCEPTOR...");
	}
	
	public void init() {
		logger.debug("CREATING GROUP INTERCEPTOR...");
	}
	
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = user.getCompany();
		
		List<Group> forbiddenGroups = user.getForbiddenGroups();
		List<Group> groupList = groupDelegate.findAll(company).getList();	
		if(forbiddenGroups != null && groupList != null) {
			for(Group group : forbiddenGroups) {
				groupList.remove(group);
			}
		}
		request.setAttribute(GroupInterceptor.GROUPLIST_REQUEST_KEY, groupList);
		
		// get the currently selected group base on the request params
		Group group = null;
		try {
			group = groupDelegate.find(Long.parseLong(request.getParameter("group_id")));	
			if(!group.getCompany().equals(company)) {
				group = null;
			}
		}
		catch(Exception e) { 
			logger.debug("group is null");
		}		
		
		Object action = invocation.getAction();
		
		if (action instanceof GroupAware) {
			((GroupAware)action).setGroup(group);
		}
				
		return invocation.invoke();
	}
	
	

}

