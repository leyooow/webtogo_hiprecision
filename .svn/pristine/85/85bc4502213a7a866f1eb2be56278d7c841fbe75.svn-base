package com.ivant.cms.db;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.OrderStatus;

/**
 * Hibernate functionality class for ordered cart items.
 * 
 * @author Mark Kenneth M. Rañosa
 *
 */
public class CartOrderItemDAO extends AbstractBaseDAO<CartOrderItem> {
	
	/**
	 * Returns all ordered items with the specified parameters.
	 * 
	 * @param order
	 * 			- order that the item belongs to
	 * 
	 * @return - all ordered items with the specified parameters
	 */
	public ObjectList<CartOrderItem> findAll(CartOrder order) {
		//get all valid ordered item with the same company and member
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("order", order));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<CartOrderItem> findAllOrderByPrice(CartOrder order) {
		Junction junc = Restrictions.conjunction();
		Order[] orders =  {Order.asc("price")};
		junc.add(Restrictions.eq("order", order));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public CartOrderItem findByStatusAndCategoryItem(Company company, CartOrder order, CategoryItem categoryItem, String status)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("order", order));
		junc.add(Restrictions.eq("categoryItem", categoryItem));
		junc.add(Restrictions.eq("status", status));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		List<CartOrderItem> result = findAllByCriterion(null, null, null, null, junc).getList();
		if(result != null && result.size() > 0){
			
			return result.get(0);
			
		}
		return null;
	}
	
	public ObjectList<CartOrderItem> findAllByCartOrderAndStatus(Company company, CartOrder order, String status)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("order", order));
		junc.add(Restrictions.eq("status", status));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public List<CartOrderItem> findAllByNameCompleted(Company company, String name, Date fromDate, Date toDate) {
		//get all valid ordered item with the same company and member
		Junction junc = Restrictions.conjunction();		
		//junc.add(Restrictions.eq("order", order));
		junc.add(Restrictions.eq("itemDetail.name", name));
		//junc.add(Restrictions.eq("order.status", OrderStatus.COMPLETED));
		junc.add(Restrictions.ge("createdOn", fromDate));
		junc.add(Restrictions.ge("company", company));
		junc.add(Restrictions.le("createdOn", toDate));
		
		
		//get ordered item
		ObjectList<CartOrderItem> result = findAllByCriterion(null, null, null, null, junc);
		
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return ordered item
		return result.getList();
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
		//get all valid ordered item with the same company and member
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("order", order));
		junc.add(Restrictions.eq("itemDetail", itemDetail));
		
		//get ordered item
		ObjectList<CartOrderItem> result = findAllByCriterion(null, null, null, null, junc);
		
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return ordered item
		return result.getList().get(0);
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
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order_item WHERE order_id = :currentOrderID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentOrderID", cartOrder.getId());
		return (BigInteger) q.uniqueResult();
	}
	
	public BigInteger findCartCountByOrder(Company company, CartOrder cOrder) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order_item WHERE company_id = :currentCompanyID and order_id = :currentOrderID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setLong("currentOrderID", cOrder.getId());
		return (BigInteger) q.uniqueResult();
	}
	
	public List<CartOrderItem> findAllByCompany(Company company, int maxResult) {
		String[] path = {"order"};
		String[] alias = {"order"};
		int[] join = {CriteriaSpecification.INNER_JOIN};
		Order[] orders = { Order.desc("createdOn") };		
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("order.company", company));
		junc.add(Restrictions.isNotNull("itemDetail.image"));
		
		return findAllByCriterion(-1, maxResult, path, alias, join, orders, junc).getList();
	}
	
	public BigInteger findCompleteOrderItemCountByCategoryItem(Company company, CategoryItem item){
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order_item WHERE company_id = :currentCompanyID and category_item_id = :currentCategoryItemID and valid is true and order_id in (select id from cart_order where status = 2 and payment_status = 1 )");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setLong("currentCategoryItemID", item.getId());
		return (BigInteger) q.uniqueResult();
	}
}
