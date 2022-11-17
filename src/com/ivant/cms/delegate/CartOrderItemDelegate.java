package com.ivant.cms.delegate;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.db.CartOrderItemDAO;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Runtime Data Access implementation class for {@link CartOrderItem}.
 * 
 * @author Mark Kenneth M. Rañosa
 *
 */
public class CartOrderItemDelegate extends AbstractBaseDelegate<CartOrderItem, CartOrderItemDAO> {
	
	/** Thread safe {@code CartOrderItemDelegate} class instance*/
	private volatile static CartOrderItemDelegate instance;
	
	/**
	 * Creates a new instance of class {@code CartOrderItemDelegate}.
	 */
	protected CartOrderItemDelegate(CartOrderItemDAO dao) {
		super(new CartOrderItemDAO());
	}

	/**
	 * Returns {@code CartOrderItemDelegate} class instance.
	 * 
	 * @return - {@code CartOrderItemDelegate} class instance
	 */
	public static CartOrderItemDelegate getInstance(){
		//generate a new instance, initial instance is null
		if(instance == null){
			//ensure thread safety
			synchronized (CartOrderItemDelegate.class) {
				if(instance == null)
					instance = new CartOrderItemDelegate(new CartOrderItemDAO());
			}
		}
		return instance;
	}
	
	public List<CartOrderItem> findAllByNameCompleted(Company company, String name, Date fromDate, Date toDate) {
		return dao.findAllByNameCompleted(company, name, fromDate, toDate);
	}

	/**
	 * Returns all ordered items with the specified parameters.
	 * 
	 * @param order
	 * 			- order that the item belongs to
	 * 
	 * @return - all ordered items with the specified parameters
	 */
	public ObjectList<CartOrderItem> findAll(CartOrder order) {
		return dao.findAll(order);
	}
	
	public ObjectList<CartOrderItem> findAllOrderByPrice(CartOrder order) {
		return dao.findAllOrderByPrice(order);
	}
	
	public CartOrderItem findByStatusAndCategoryItem(Company company, CartOrder order, CategoryItem categoryItem, String status)
	{
		return dao.findByStatusAndCategoryItem(company, order, categoryItem, status);
	}
	
	public ObjectList<CartOrderItem> findAllByCartOrderAndStatus(Company company, CartOrder order, String status)
	{
		return dao.findAllByCartOrderAndStatus(company, order, status);
	}
	
	/**
	 * Returns the {@link CartOrderItem} item based on the specified parameters.
	 * 
	 * @param order
	 * 			- order that the item belongs to
	 * @param itemDetail
	 * 			- detail of the item that has been ordered
	 * 
	 * @return - the {@link CartOrderItem} item based on the specified parameters
	 */
	public CartOrderItem find(CartOrder order, ItemDetail itemDetail) {
		return dao.find(order, itemDetail);
	}
	
	/**
	 * Returns all items per order, with paging.
	 * 
	 * @param cartOrder
	 * 			- order that the items belong to
	 * @param resultsPerPage
	 * 			- number of items to show per page
	 * @param pageNumber
	 * 			- current page number
	 * 
	 * @return - all items per order, with paging
	 */
	public List<CartOrderItem> listAllOrders(CartOrder cartOrder, int resultsPerPage, int pageNumber) {
		return dao.findAllByCriterion(pageNumber, resultsPerPage, null, null, null,
				new Order[] { Order.desc("createdOn") },
				Restrictions.eq("order", cartOrder),
				Restrictions.eq("isValid", Boolean.TRUE)).getList();
	}
	
	/**
	 * Returns the number of items per order.
	 * 
	 * @param cartOrder
	 * 			- order that the items belong to
	 * 
	 * @return - the number of items per order
	 */
	public BigInteger getItemCount(CartOrder cartOrder) {
		return dao.getItemCount(cartOrder);
	}
	
	public BigInteger findCartCountByOrder(Company company, CartOrder cOrder) {
		return dao.findCartCountByOrder(company, cOrder);
	}
	
	public List<CartOrderItem> findAllByCompany(Company company, int maxResult) {
		return dao.findAllByCompany(company, maxResult);
	}
	
	public BigInteger findOrderItemCountByCategoryItem(Company company, CategoryItem item){
		return dao.findCompleteOrderItemCountByCategoryItem(company, item);
	}
	
}
