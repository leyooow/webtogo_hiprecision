package com.ivant.cms.action.admin;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.BillingDelegate;
import com.ivant.cms.delegate.PaymentDelegate;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.Payment;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.PaymentTypeEnum;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;

public class ListPaymentAction implements Action, UserAware, ServletRequestAware, PagingAware {

	private Logger logger = Logger.getLogger(getClass());
	private PaymentDelegate paymentDelegate = PaymentDelegate.getInstance();
	private BillingDelegate billingDelegate = BillingDelegate.getInstance();
	
	private Payment payment;
	private Billing billing;
	private User user;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	private HttpServletRequest request;
	private Company company;
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	private List<Payment> payments;
	private List<PaymentTypeEnum> paymentTypes;
	
	public String execute() throws Exception {
		
		
		if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return Action.ERROR;
		}
		if (request.getParameter("billing_id") != null) {
				try {
					long Id = Long.parseLong(request.getParameter("billing_id"));
					billing = billingDelegate.find(Id);
					//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++" + billing.getPayment().size());
				}
				catch(Exception e) {
					billing.setCompany(user.getCompany());
				}
				ObjectList<Payment> objectList = paymentDelegate.findAllWithPaging(billing, user.getCompany(), user.getItemsPerPage(), page, null, null);
				payments = objectList.getList();
	  }
		
		
		if (request.getParameter("billing_id") == null) {
			payments = paymentDelegate.findAllWithPaging(user.getCompany(), user.getItemsPerPage(), page).getList();
			//logger.debug("=======================~~~~ " + payments.size());
		}		
		
				
		// set the status and types
		paymentTypes = Arrays.asList(PaymentTypeEnum.values());
		
		return Action.SUCCESS;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser (User user) {
		this.user = user;
	}

	
	public void setPayment (Payment payment) {
		this.payment = payment;
	}
	
	public Billing getBilling()
	{
		return billing;
	}
	
	public void setBilling (Billing billing) {
		this.billing = billing;
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
		if (request.getParameter("billing_id") != null) {
			try {
				long Id = Long.parseLong(request.getParameter("billing_id"));
				billing = billingDelegate.find(Id);
			}
			catch(Exception e) {
				billing.setCompany(user.getCompany());
			}
//			ObjectList<Payment> objectList = paymentDelegate.findAllByBilling(company, billing);
//			payments = objectList.getList();
//			totalItems = payments.size();
			totalItems = billing.getPayment().size();
  }
		else
			totalItems = paymentDelegate.findAll().size();
	}
	
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	public List<Payment> getPayments() {
		return payments;
	}
	
	
	public List<PaymentTypeEnum> getPaymentStatuses() {
		return paymentTypes;
	}
	
	public List<PaymentTypeEnum> getPaymentTypes() {
		return paymentTypes;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	
}
