package com.ivant.cms.db;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.enums.ShippingStatus;

/**
 * Hibernate functionality class for order account.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class CartOrderDAO extends AbstractBaseDAO<CartOrder> {

	/**
	 * Returns the {@link CartOrder} item based on the specified parameters.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param member
	 * 			- company client/buyer
	 * 
	 * @return - the {@link CartOrder} item based on the specified parameters
	 */
	public CartOrder find(Company company, Member member) {
		//get all member order accounts
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		//get order account list
		ObjectList<CartOrder> result = findAllByCriterion(null, null, null, null, junc);
		
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return order account
		return result.getList().get(0);
	}
	
	public CartOrder findLastCartOrder(Company company, Member member, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		ObjectList<CartOrder> result = findAllByCriterion(null, null, null, orders, junc);
		if(result == null || result.getList().isEmpty()){
			return null;
		}
		return result.getList().get(0);
	}
	
	/**
	 * Returns all orders by member.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param member
	 * 			- company client/buyer
	 * 
	 * @return - all orders by member
	 */
	public List<CartOrder> findAll(Company company, Member member) {
		//get all member order accounts
		Order[] orders = {Order.desc("createdOn")};
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		//get order account list
		ObjectList<CartOrder> result = findAllByCriterion(null, null, null, orders, junc);
		
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return order account
		return result.getList();
	}
	
	public List<CartOrder> findAllByMemberAndRewardStatusNotes(Company company, Member member, String statusNotes, Boolean isEqualStatusNotes){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		if(isEqualStatusNotes){
			junc.add(Restrictions.eq("statusNotes", statusNotes));
		}else{
			junc.add(Restrictions.isNull("statusNotes"));
		}
		ObjectList<CartOrder> result = findAllByCriterion(null, null, null, null, junc);
		if(null == result || result.getList().isEmpty()){
			return null;
		}
		return result.getList();
		
	}
	
	public ObjectList<CartOrder> findAllByMemberCompleted(Company company, Member member, Date fromDate, Date toDate)
	{
		final Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		junc.add(Restrictions.ge("createdOn", fromDate));
		junc.add(Restrictions.le("createdOn", toDate));
		junc.add(Restrictions.eq("status", OrderStatus.COMPLETED));

		Order[] orders = { Order.desc("createdOn") };

		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<CartOrder> findAllByMember(Company company, Member member, Date fromDate, Date toDate)
	{
		final Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		junc.add(Restrictions.ge("createdOn", fromDate));
		junc.add(Restrictions.le("createdOn", toDate));

		Order[] orders = { Order.desc("createdOn") };

		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<CartOrder> findAllNonRewardStatusNotesAndEmptyInfo5(Company company, Member member) {
		final Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		junc.add(Restrictions.isNull("statusNotes"));
		junc.add(Restrictions.isNull("info5"));

		Order[] orders = { Order.desc("createdOn") };

		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<CartOrder> findAllByMemberAndOrderStatusWithPaging(Company company, Member member, OrderStatus orderStatus, int resultPerPage, int pageNumber, Order...orders)
	{
		final Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		junc.add(Restrictions.eq("status", orderStatus));
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}
	
	public ObjectList<CartOrder> findAllWithPaging(Company company, Member member,int resultPerPage, int pageNumber, Order...orders) {
		//get all member order accounts
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		//get order account list
		ObjectList<CartOrder> result = findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
		
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return order account
		return result;
	}
	
	/**
	 * Returns the number of orders per company.
	 * 
	 * @param company
	 * 			- company to get the orders from
	 * 
	 * @return - the number of orders per company
	 */
	public BigInteger getOrderCount(Company company) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order WHERE company_id = :currentCompanyID and valid is true");
//		query.append("SELECT COUNT(*) FROM cart_order WHERE company_id = :currentCompanyID and valid = true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		return (BigInteger) q.uniqueResult();
	}
	
	public BigInteger getPaidOrderCount(Company company){
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order where company_id = :currentCompanyID and payment_status = :currentPaymentStatus and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setInteger("currentPaymentStatus", 1);
		return (BigInteger) q.uniqueResult();
	}
	// this lines of code is intended only for wendys CMS
	//for NEW PICKUP
	public BigInteger getNewPickupCount(Company company, String branch){
		try{
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*) FROM cart_order where company_id = :currentCompanyID and status = :currentStatus and payment_status = 1 and shipping_type = :currentShippingType and comments regexp :currentBranch and valid is true");
			SQLQuery q = getSession().createSQLQuery(query.toString());
			q.setLong("currentCompanyID", company.getId());
			q.setInteger("currentStatus", 0); //new
			q.setInteger("currentShippingType", 0); //pickup
			q.setString("currentBranch", "Store: " + branch); // branch
		
			return (BigInteger) q.uniqueResult();
		}
		catch(Exception e){
			System.out.println("ERROR:################"+e.toString());
		}
		return null;
	}
	
	//for new PENDING PICKUP
	public BigInteger getPendingPickupCount(Company company, String branch){
		
		try{
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*) FROM cart_order where company_id = :currentCompanyID and status = :currentStatus and shipping_type = :currentShippingType and comments regexp :currentBranch and valid is true");
			SQLQuery q = getSession().createSQLQuery(query.toString());
			q.setLong("currentCompanyID", company.getId());
			q.setInteger("currentStatus", 1); //pending
			q.setInteger("currentShippingType", 0); //pickup
			q.setString("currentBranch", "Prefferred Store: "+branch); // branch
		return (BigInteger) q.uniqueResult();
		}
		catch(Exception e){
			System.out.println("ERROR:################"+e.toString());
		}
		return null;
	}
	
	//for new COMPLETED PICKUP
	public BigInteger getCompletedPickupCount(Company company, String branch){
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order where company_id = :currentCompanyID and status = :currentStatus and shipping_type = :currentShippingType and comments regexp :currentBranch and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setInteger("currentStatus", 2); //completed
		q.setInteger("currentShippingType", 0); //pickup
		q.setString("currentBranch", "Prefferred Store: "+branch); // branch
		return (BigInteger) q.uniqueResult();
	}
	
	//for new COMPLETED PICKUP
	public BigInteger getCancelledPickupCount(Company company, String branch){
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order where company_id = :currentCompanyID and status = :currentStatus and shipping_type = :currentShippingType and comments regexp :currentBranch and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setInteger("currentStatus", 3); //completed
		q.setInteger("currentShippingType", 0); //pickup
		q.setString("currentBranch", "Prefferred Store: "+branch); // branch
		return (BigInteger) q.uniqueResult();
	}
	
	//for NEW DELIVERY
	public BigInteger getNewDeliveryCount(Company company, String branch){
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order where company_id = :currentCompanyID and status = :currentStatus and shipping_type = :currentShippingType and info1 = :currentBranch and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setInteger("currentStatus", 0); //NEW
		q.setInteger("currentShippingType", 1); //delivery
		q.setString("currentBranch", branch); // branch ID
		return (BigInteger) q.uniqueResult();
	}
	
	//for PENDING DELIVERY
	public BigInteger getPendingDeliveryCount(Company company, String branch){
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order where company_id = :currentCompanyID and status = :currentStatus and shipping_type = :currentShippingType and info1 = :currentBranch and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setInteger("currentStatus", 1); //PENDING
		q.setInteger("currentShippingType", 1); //delivery
		q.setString("currentBranch", branch); // branch ID
		return (BigInteger) q.uniqueResult();
	}
	
	//for COMPLETED DELIVERY
	public BigInteger getCompletedDeliveryCount(Company company, String branch){
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order where company_id = :currentCompanyID and status = :currentStatus and shipping_type = :currentShippingType and info1 = :currentBranch and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setInteger("currentStatus", 2); //COMPLETED
		q.setInteger("currentShippingType", 1); //delivery
		q.setString("currentBranch", branch); // branch ID
		return (BigInteger) q.uniqueResult();
	}
	
	//for CANCELLED DELIVERY
		public BigInteger getCancelledDeliveryCount(Company company, String branch){
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*) FROM cart_order where company_id = :currentCompanyID and status = :currentStatus and shipping_type = :currentShippingType and info1 = :currentBranch and valid is true");
			SQLQuery q = getSession().createSQLQuery(query.toString());
			q.setLong("currentCompanyID", company.getId());
			q.setInteger("currentStatus", 3); //CANCELLED
			q.setInteger("currentShippingType", 1); //delivery
			q.setString("currentBranch", branch); // branch ID
			return (BigInteger) q.uniqueResult();
		}
	
	public List<CartOrder> findAll(Company company) {
		//get all member order accounts
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("company", company));
		//get order account list
		
		String[] paths = {"member"};
		String[] alias = {"_member"};
		int[] join = {CriteriaSpecification.INNER_JOIN};
		
		ObjectList<CartOrder> result = findAllByCriterion(paths, alias, join, new Order[] { Order.desc("createdOn") }, junc);
		
		CartOrder co=new CartOrder();
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return order account
		return result.getList();
	}
	
	public List<CartOrder> findAllByCompany(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));	
		Order[] orders = {Order.desc("createdOn")};
		
		return findAllByCriterion(null, null, null, orders, junc).getList();
	}	
	
	public ObjectList<CartOrder> findOrderByDate(Company company, Date fromDate, Date toDate) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));	
		junc.add(Restrictions.ge("createdOn",fromDate));
		junc.add(Restrictions.le("createdOn",toDate));
				
		Order[] orders = {Order.desc("createdOn")};
		
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<CartOrder> findOrderByDateAndMember(Company company, Date fromDate, Date toDate, Member member) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.ge("createdOn",  fromDate));
		junc.add(Restrictions.le("createdOn", toDate));
		junc.add(Restrictions.eq("member", member));
		Order[] orders = {Order.desc("createdOn")};
		
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public BigInteger getMemberOrderCount(Company company, Member member){
		StringBuilder query = new StringBuilder();
		query.append("SELECT count(distinct DATE(created_on)) FROM cart_order where company_id = :currentCompanyID and member_id = :currentMemberId and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setLong("currentMemberId", member.getId());
		return (BigInteger) q.uniqueResult();
	}
	
	public Boolean findIfFirstPurchase(CartOrder order, Member member){
		Junction junc = Restrictions.conjunction();
		List<CartOrder> listCartOrder = new ArrayList<CartOrder>();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("status", OrderStatus.COMPLETED));	
		/*junc.add(Restrictions.ge("shippingStatus",ShippingStatus.DELIVERED));
		junc.add(Restrictions.le("paymentStatus",PaymentStatus.PAID));*/
		junc.add(Restrictions.eq("member", member));	
		//junc.add(Restrictions.ne("paymentType", PaymentType.COD));
		
		listCartOrder = findAllByCriterion(null, null, null, null, junc).getList();
		
		// points are generated on the process of turning order status from pending to completed
		if(listCartOrder.size() == 0){
			return true;
		}
		/*else if(listCartOrder.size() == 1){
			if(listCartOrder.contains(order)){
				return true;
			}
		}*/
		
		return false;
	}
	
	public ObjectList<CartOrder> findOrderByBranch(Company company, Date fromDate, Date toDate, Member member) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		String[] associationPaths = { "shippingInfo", "memberShippingInfo.shippingInfo" };
		String[] aliases = { "memberShippingInfo", "si" };
		int[] joinTypes = { Criteria.LEFT_JOIN };
		
		junc.add(Restrictions.eq("si.address1", ""));
		
		return findAllByCriterion(associationPaths, aliases, joinTypes, null, junc);
	}
	
}
