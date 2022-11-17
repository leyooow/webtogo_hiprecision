package com.ivant.cms.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderStatusHistoryDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.CartOrderStatusHistory;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Anjerico D. Gutierrez
 * @since July 27, 2015
 */
public class CartOrderStatusHistoryAction extends ActionSupport
			implements Preparable, ServletRequestAware, UserAware, CompanyAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6921401812766645484L;
	
	private CartOrder cartOrder;
	private Company company;
	private User user;
	private HttpServletRequest request;
	
	private String successUrl;
	private String errorUrl;
	
	protected UserDelegate userDelegate = UserDelegate.getInstance();
	protected CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	protected CartOrderStatusHistoryDelegate cartOrderStatusHistoryDelegate = CartOrderStatusHistoryDelegate.getInstance();
	
	@Override
	public void setCompany(Company company) {
		// TODO Auto-generated method stub
		this.company = company;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user = user;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String getSuccessUrl()
	{
		return successUrl;
	}
	
	public void setSuccessUrl(String successUrl)
	{
		this.successUrl = successUrl;
	}
	
	public String getErrorUrl()
	{
		return errorUrl;
	}
	
	public void setErrorUrl(String errorUrl)
	{
		this.errorUrl = errorUrl;
	}
	
	public List<CartOrderStatusHistory> getCartOrderStatusHistoryByUser() {
		try{
			if(request.getParameter("user_id") != null){
				user = userDelegate.find(Long.parseLong(request.getParameter("user_id")));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return cartOrderStatusHistoryDelegate.findByUser(company, user).getList();
	}
	
	public List<CartOrderStatusHistory> getCartOrderStatusHistoryByOrder() {
		try{
			if(request.getParameter("order_id") != null){
				cartOrder = cartOrderDelegate.find(Long.parseLong(request.getParameter("order_id")));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return cartOrderStatusHistoryDelegate.findByOrder(company, cartOrder).getList();
	}
	

}
