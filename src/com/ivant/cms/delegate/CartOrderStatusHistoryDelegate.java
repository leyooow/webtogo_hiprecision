package com.ivant.cms.delegate;

import com.ivant.cms.db.CartOrderStatusHistoryDAO;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderStatusHistory;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
/**
 * 
 * @author Anjerico D. Gutierrez
 * @since July 27, 2015
 */
public class CartOrderStatusHistoryDelegate extends AbstractBaseDelegate<CartOrderStatusHistory, CartOrderStatusHistoryDAO>{
	private static CartOrderStatusHistoryDelegate instance = new CartOrderStatusHistoryDelegate();
	public static CartOrderStatusHistoryDelegate getInstance() {
		return instance;
	}
	
	public CartOrderStatusHistoryDelegate() {
		super(new CartOrderStatusHistoryDAO());
	}
	
	public ObjectList<CartOrderStatusHistory> findByOrder(Company company, CartOrder cartOrder) {
		return dao.findByOrder(company, cartOrder);
	}
	
	public ObjectList<CartOrderStatusHistory> findByUser(Company company, User user) {
		return dao.findByUser(company, user);
	}
	
}
