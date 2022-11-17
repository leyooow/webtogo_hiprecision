package com.ivant.cms.action.order;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public abstract class OrderAction implements SessionAware {
	
	public static final String VIEW_CART = "viewcart";
	public static final String SHIPPING_INFO_FORM = "shippinginfoform";
	public static final String PAYMENT = "payment";
	public static final String REVIEW_ORDER = "revieworder";
	public static final String SUCCESS = "success";
	public static final String CANCELLED = "cancelled";

	@SuppressWarnings("unchecked")
	protected Map session;
	
	@SuppressWarnings("unchecked")
	public void setSession(Map arg0) {
		session = arg0;
	}
}
