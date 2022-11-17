package com.ivant.cms.delegate;

import com.ivant.cms.db.OrderItemFileDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.OrderItemFile;

public class OrderItemFileDelegate extends AbstractBaseDelegate<OrderItemFile, OrderItemFileDAO> {
	

	private static OrderItemFileDelegate instance = new OrderItemFileDelegate();
	
	public static OrderItemFileDelegate getInstance() {
		return OrderItemFileDelegate.instance;
	}
	
	public OrderItemFileDelegate() {
		super(new OrderItemFileDAO());
	}
	
	public OrderItemFile findItemFileID(Company company, long cartOrderItemID)
	{
		return dao.findItemFileID(company, cartOrderItemID);
	}

}
