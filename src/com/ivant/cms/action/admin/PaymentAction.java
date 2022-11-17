package com.ivant.cms.action.admin;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.BillingDelegate;
import com.ivant.cms.delegate.PaymentDelegate;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.Payment;
import com.ivant.cms.entity.Company;
import com.ivant.cms.enums.BillingStatusEnum;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.enums.PaymentTypeEnum;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class PaymentAction extends ActionSupport implements Preparable, ServletRequestAware, UserAware
{
	private static final long serialVersionUID = 4048368734861819318L;
	private Logger logger = Logger.getLogger(getClass());
	private BillingDelegate billingDelegate = BillingDelegate.getInstance();
	private PaymentDelegate paymentDelegate = PaymentDelegate.getInstance();

	private Billing billing;
	private List<Payment> payments;
	private Payment payment;
	private Company company;
	private User user;
	private int type;
	private HttpServletRequest request;
	private String paymentDate;
	private String checkDate;
	
	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	@Override
	public String execute() throws Exception {
		payments = paymentDelegate.findAll(company).getList();
		
		//logger.debug("=======================~~~~ " + payments.size());
		return Action.NONE;
	}
	
	public void prepare() throws Exception {
		
		if (request.getParameter("billing_id") != null) {
		try {
			long Id = Long.parseLong(request.getParameter("billing_id"));
			billing = billingDelegate.find(Id);
			long Idp = Long.parseLong(request.getParameter("payment_id"));
			payment = paymentDelegate.find(Idp);
		}
		catch(Exception e) {
			billing = new Billing();
			billing.setCompany(user.getCompany());
			payment = new Payment();
			payment.setBilling(billing);
			payment.setCompany(user.getCompany());
		}
	}
		if (request.getParameter("type") != null ) {
			type = Integer.parseInt(request.getParameter("type"));
			for (PaymentTypeEnum pe: PaymentTypeEnum.values())
			{
				if (type == pe.getTypeCode())
					payment.setType(pe);
			}
			}

	}
	
	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public void setBilling(Billing billing)
	{
		this.billing = billing;
	}
	
	public Billing getBilling()
	{
		return this.billing;
	}
	
	
	public Payment getPayment()
	{
		return payment;
	}

	public void setPayment(Payment payment)
	{
		this.payment = payment;
	}

	private boolean commonParamsValid() {
		if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return false;
		} 
		if(user.getCompany() == null) {
			return false;
		}		
	
		return true;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String saveNew()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
	 	
		try {
			billing = billingDelegate.find(new Long (request.getParameter("billing_id")));
			  billing.setStatus(BillingStatusEnum.PAID);
				billingDelegate.update(billing); 
				payment.setBilling(billing);
				payment.setCompany(user.getCompany());
				
				if(checkDate!=null && checkDate.trim().length()!=0) {
				GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(checkDate.substring(6,10)), Integer.parseInt(checkDate.substring(0,2 ))-1 , Integer.parseInt(checkDate.substring(3,5)) );
				payment.setCheckDate(calendar.getTime()); 
				}
				if (paymentDate!=null  && paymentDate.trim().length()!=0) {
				GregorianCalendar calendar2 = new GregorianCalendar(Integer.parseInt(paymentDate.substring(6,10)), Integer.parseInt(paymentDate.substring(0,2 ))-1 , Integer.parseInt(paymentDate.substring(3,5)) );
				payment.setPaymentDate(calendar2.getTime());	
				}
				
				paymentDelegate.insert(payment);
				//System.out.println("added new payment");
		} 
		catch(Exception e) {
			// cannot insert object
		}
		
		
		return Action.SUCCESS;
	}
	
	
	
	
	
	
	public String save()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
	 	
		try {
			if(paymentDelegate.findAll(user.getCompany()) == null) {
				paymentDelegate.insert(payment);
				}
			else {
				paymentDelegate.update(payment);
			}
		} 
		catch(Exception e) {
			// cannot insert or udpate object
		}
		return Action.SUCCESS;
		
	}
	
	public String delete()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!payment.getCompany().equals(payment.getCompany())) {
			return Action.ERROR;
		}
		
		paymentDelegate.delete(payment);
		
		return Action.SUCCESS;
	}
	
	public String edit()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!billing.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}
		
		return Action.SUCCESS;
	}
	
}
