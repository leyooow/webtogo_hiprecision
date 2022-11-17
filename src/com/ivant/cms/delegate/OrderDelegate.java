package com.ivant.cms.delegate;

import com.ivant.cms.db.OrderDAO;
import com.ivant.cms.entity.Order;

public class OrderDelegate extends AbstractBaseDelegate<Order, OrderDAO> {
	
	private static final OrderDelegate instance = new OrderDelegate();
	
	public static OrderDelegate getInstance() {
		return instance;
	}

	private OrderDelegate() {
		super(new OrderDAO());
	}
}
