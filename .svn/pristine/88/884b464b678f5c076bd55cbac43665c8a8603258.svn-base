package com.ivant.cms.interceptors;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.PagingAware; 
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;


public class PagingInterceptor implements Interceptor {

	private static final long serialVersionUID = -1101833361751151839L;
	//private Logger logger = Logger.getLogger(PagingInterceptor.class);
	
	public static final String PAGING_REQUEST_KEY = "pageNumber";
	public static final String PAGING_UTIL_REQUEST_KEY = "pagingUtil";
	
	protected GroupDelegate groupDelegate = GroupDelegate.getInstance();
	protected CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	
	private Company company;
		
	public void destroy() {		
	}

	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getAttribute(UserInterceptor.USER_REQUEST_KEY);
		
		int page = 0;
		try {
			page = Integer.parseInt(request.getParameter(PagingInterceptor.PAGING_REQUEST_KEY));
			page -= 1;
			if(page < 0) {
				page = 0;
			}
		}
		catch(Exception e) {}
		
		request.setAttribute(PagingInterceptor.PAGING_REQUEST_KEY, page);
		
		Object action = invocation.getAction();
		if(action instanceof PagingAware){
			PagingAware pa = (PagingAware)action;
			pa.setPage(page);
			pa.setTotalItems();
			if(user==null||user.getItemsPerPage()==null)
				pa.setItemsPerPage(15);//-->default
			else
				pa.setItemsPerPage(user.getItemsPerPage());
			
			PagingUtil pagingUtil = new PagingUtil(pa.getTotalItems(), user.getItemsPerPage(), page); 
			request.setAttribute(PagingInterceptor.PAGING_UTIL_REQUEST_KEY, pagingUtil);
		}
		
		return invocation.invoke();
	}
	
	/*--------  Get Wendys Branch ---------------*/
	 
	public List<CategoryItem> getStores()
	{
		System.out.println("################# test here #######################");
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
	
	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

}
