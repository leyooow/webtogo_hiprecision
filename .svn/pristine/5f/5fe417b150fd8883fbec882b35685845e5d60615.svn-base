package com.ivant.cms.delegate;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.db.CartOrderDAO;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.ShippingType;

/**
 * Runtime Data Access implementation class for {@link CartOrder}.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class CartOrderDelegate extends AbstractBaseDelegate<CartOrder, CartOrderDAO> {
	
	/** Thread safe {@code CartOrderDelegate} class instance*/
	private volatile static CartOrderDelegate instance;
	
	/**
	 * Creates a new instance of class {@code CartOrderDelegate}.
	 */
	protected CartOrderDelegate(CartOrderDAO dao) {
		super(new CartOrderDAO());
	}

	/**
	 * Returns {@code CartOrderDelegate} class instance.
	 * 
	 * @return - {@code CartOrderDelegate} class instance
	 */
	public static CartOrderDelegate getInstance(){
		//generate a new instance, initial instance is null
		if(instance == null){
			//ensure thread safety
			synchronized (CartOrderDelegate.class) {
				if(instance == null)
					instance = new CartOrderDelegate(new CartOrderDAO());
			}
		}
		return instance;
	}
	
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
		return dao.find(company, member);
	}
	
	public CartOrder findLastCartOrder(Company company, Member member, Order...orders ){
		return dao.findLastCartOrder(company, member, orders);
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
		return dao.findAll(company, member);
	}
	
	public List<CartOrder> findAllByMemberAndRewardStatusNotes(Company company, Member member, String statusNotes, Boolean isEqualStatusNotes){
		return dao.findAllByMemberAndRewardStatusNotes(company, member, statusNotes, isEqualStatusNotes);
	}
	
	public ObjectList<CartOrder> findAllWithPaging(Company company, Member member,int resultPerPage, int pageNumber, Order...orders) {
		return dao.findAllWithPaging(company,member,resultPerPage,pageNumber,orders);
	}
	
	/**
	 * Returns all orders by company, with paging.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param resultsPerPage
	 * 			- number of items to show per page
	 * @param pageNumber
	 * 			- current page number
	 * 
	 * @return - all orders by company, with paging
	 */
	public List<CartOrder> listAllOrders(Company company, int resultsPerPage, int pageNumber) {
		return dao.findAllByCriterion(pageNumber, resultsPerPage, null, null, null,
				new Order[] { Order.desc("createdOn") },
				Restrictions.eq("company", company),
				Restrictions.eq("isValid", Boolean.TRUE)).getList();
	}
	
	public List<CartOrder> listAllOrdersByStatusNotes(Company company, String statusNotes, Boolean isEqualStatusNotes, int resultsPerPage, int pageNumber) {
		return dao.findAllByCriterion(pageNumber, resultsPerPage, null, null, null,
				new Order[] { Order.desc("createdOn") },
				Restrictions.eq("company", company),
				isEqualStatusNotes ? Restrictions.eq("statusNotes", statusNotes) : Restrictions.isNull("statusNotes"),
				Restrictions.eq("isValid", Boolean.TRUE)).getList();
	}
	
	public List<CartOrder> listAllPaidOrders(Member member, Company company, int resultsPerPage, int pageNumber, String status, String paymentStatus, String shippingType, String branch_comments, String branch_info1) {
		
		Conjunction conj1 = Restrictions.conjunction();
		Conjunction conj2 = Restrictions.conjunction();
		Conjunction conj3 = Restrictions.conjunction();
		Disjunction disjunc = Restrictions.disjunction();
		
		if(member != null){
			conj1.add(Restrictions.eq("member", member));
		}
		
		if(shippingType.equals("")){
		     conj1.add(Restrictions.eq("shippingType", ShippingType.PICKUP));
		}
		if(paymentStatus.equals("")){
		     conj1.add(Restrictions.eq("paymentStatus", PaymentStatus.PAID));
		}
		conj1.add(Restrictions.eq("company", company));
		conj1.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		
		if(!branch_comments.equals("")){
			conj1.add(Restrictions.ilike("comments", branch_comments, MatchMode.ANYWHERE));
		}
		//if(!branch_info1.equals("")){
		//	conj1.add(Restrictions.ilike("info1", branch_info1, MatchMode.ANYWHERE));
		//}
		
		
		if(!status.equals("")){
			if(status.equalsIgnoreCase("new")){
				conj1.add(Restrictions.eq("status", OrderStatus.NEW));
			}
			else if(status.equalsIgnoreCase("pending")){
				conj1.add(Restrictions.eq("status", OrderStatus.PENDING));
			}
			else if(status.equalsIgnoreCase("completed")){
				conj1.add(Restrictions.eq("status", OrderStatus.COMPLETED));
			}
			else if(status.equalsIgnoreCase("delivery in transit")){
				conj1.add(Restrictions.eq("status", OrderStatus.DELIVERY_IN_TRANSIT));
			}
			else if(status.equalsIgnoreCase("cancelled")){
				conj1.add(Restrictions.eq("status", OrderStatus.CANCELLED));
			}
		}
		
		if(!paymentStatus.equals("")){
			if(paymentStatus.equalsIgnoreCase("paid")){
				conj1.add(Restrictions.eq("paymentStatus", PaymentStatus.PAID));
			}
			else if(paymentStatus.equalsIgnoreCase("pending")){
				conj1.add(Restrictions.eq("paymentStatus", PaymentStatus.PENDING));
			}
		}
		
		if(!shippingType.equals("")){
			if(shippingType.equalsIgnoreCase("pickup")){
				conj1.add(Restrictions.eq("shippingType", ShippingType.PICKUP));
			}
			else if(shippingType.equalsIgnoreCase("delivery")){
				conj1.add(Restrictions.eq("shippingType", ShippingType.DELIVERY));
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		if(member != null){
			conj2.add(Restrictions.eq("member", member));
		}
		
		if(shippingType.equals("")){
		     conj2.add(Restrictions.eq("shippingType", ShippingType.DELIVERY));
		}
		if(paymentStatus.equals("")){
		     conj2.add(Restrictions.eq("paymentStatus", PaymentStatus.PAID));
		}
		
		conj2.add(Restrictions.eq("company", company));
		conj2.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		//if(!branch_comments.equals("")){
		//	conj2.add(Restrictions.ilike("comments", branch_comments, MatchMode.ANYWHERE));
		//}
		if(!branch_info1.equals("")){
			conj2.add(Restrictions.ilike("info1", branch_info1, MatchMode.ANYWHERE));
		}
		
		if(!status.equals("")){
			if(status.equalsIgnoreCase("new")){
				conj2.add(Restrictions.eq("status", OrderStatus.NEW));
			}
			else if(status.equalsIgnoreCase("pending")){
				conj2.add(Restrictions.eq("status", OrderStatus.PENDING));
			}
			else if(status.equalsIgnoreCase("completed")){
				conj2.add(Restrictions.eq("status", OrderStatus.COMPLETED));
			}
			else if(status.equalsIgnoreCase("delivery in transit")){
				conj2.add(Restrictions.eq("status", OrderStatus.DELIVERY_IN_TRANSIT));
			}
			else if(status.equalsIgnoreCase("cancelled")){
				conj2.add(Restrictions.eq("status", OrderStatus.CANCELLED));
			}
		}
		
		if(!paymentStatus.equals("")){
			if(paymentStatus.equalsIgnoreCase("paid")){
				conj2.add(Restrictions.eq("paymentStatus", PaymentStatus.PAID));
			}
			else if(paymentStatus.equalsIgnoreCase("pending")){
				conj2.add(Restrictions.eq("paymentStatus", PaymentStatus.PENDING));
			}
		}
		if(!shippingType.equals("")){
			if(shippingType.equalsIgnoreCase("pickup")){
				conj2.add(Restrictions.eq("shippingType", ShippingType.PICKUP));
			}
			else if(shippingType.equalsIgnoreCase("delivery")){
				conj2.add(Restrictions.eq("shippingType", ShippingType.DELIVERY));
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		if(member != null){
			conj3.add(Restrictions.eq("member", member));
		}
		if(shippingType.equals("")){
		     conj3.add(Restrictions.eq("shippingType", ShippingType.DELIVERY));
		}
		if(paymentStatus.equals("")){
		     conj3.add(Restrictions.eq("paymentStatus", PaymentStatus.PENDING));
		}
		conj3.add(Restrictions.eq("company", company));
		conj3.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		//if(!branch_comments.equals("")){
		//	conj3.add(Restrictions.ilike("comments", branch_comments, MatchMode.ANYWHERE));
		//}
		if(!branch_info1.equals("")){
			conj3.add(Restrictions.ilike("info1", branch_info1, MatchMode.ANYWHERE));
		}
		
		if(!status.equals("")){
			if(status.equalsIgnoreCase("new")){
				conj3.add(Restrictions.eq("status", OrderStatus.NEW));
			}
			else if(status.equalsIgnoreCase("pending")){
				conj3.add(Restrictions.eq("status", OrderStatus.PENDING));
			}
			else if(status.equalsIgnoreCase("completed")){
				conj3.add(Restrictions.eq("status", OrderStatus.COMPLETED));
			}
			else if(status.equalsIgnoreCase("delivery in transit")){
				conj3.add(Restrictions.eq("status", OrderStatus.DELIVERY_IN_TRANSIT));
			}
			else if(status.equalsIgnoreCase("cancelled")){
				conj3.add(Restrictions.eq("status", OrderStatus.CANCELLED));
			}
		}
		
		if(!paymentStatus.equals("")){
			if(paymentStatus.equalsIgnoreCase("paid")){
				conj3.add(Restrictions.eq("paymentStatus", PaymentStatus.PAID));
			}
			else if(paymentStatus.equalsIgnoreCase("pending")){
				conj3.add(Restrictions.eq("paymentStatus", PaymentStatus.PENDING));
			}
		}
		if(!shippingType.equals("")){
			if(shippingType.equalsIgnoreCase("pickup")){
				conj3.add(Restrictions.eq("shippingType", ShippingType.PICKUP));
			}
			else if(shippingType.equalsIgnoreCase("delivery")){
				conj3.add(Restrictions.eq("shippingType", ShippingType.DELIVERY));
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		
		disjunc.add(Restrictions.or(Restrictions.or(conj1, conj2),conj3));//pickup , paid
		//disjunc.add(conj2);//delivery , paid
		//disjunc.add(conj3);//delivery , pending
		
		
		
		
		return dao.findAllByCriterion(pageNumber, resultsPerPage, null, null, null,
				new Order[] { Order.desc("createdOn") },
				disjunc).getList() ;
		
		
		
		
	}
	
public List<CartOrder> listAllPaidOrdersByDate(Member member, Date fromDeyt, Date toDeyt, Company company, int resultsPerPage, int pageNumber, String status, String paymentStatus, String shippingType, String branch_comments, String branch_info1) {
		
		Conjunction conj1 = Restrictions.conjunction();
		Conjunction conj2 = Restrictions.conjunction();
		Conjunction conj3 = Restrictions.conjunction();
		Disjunction disjunc = Restrictions.disjunction();
		
		if(member != null){
			conj1.add(Restrictions.eq("member", member));
		}
		
		if(shippingType.equals("")){
		     conj1.add(Restrictions.eq("shippingType", ShippingType.PICKUP));
		}
		if(paymentStatus.equals("")){
		     conj1.add(Restrictions.eq("paymentStatus", PaymentStatus.PAID));
		}
		conj1.add(Restrictions.eq("company", company));
		conj1.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj1.add(Restrictions.ge("createdOn", fromDeyt));
		conj1.add(Restrictions.lt("createdOn", toDeyt));
		
		if(!branch_comments.equals("")){
			conj1.add(Restrictions.ilike("comments", branch_comments, MatchMode.ANYWHERE));
		}
		//if(!branch_info1.equals("")){
		//	conj1.add(Restrictions.ilike("info1", branch_info1, MatchMode.ANYWHERE));
		//}
		
		
		if(!status.equals("")){
			if(status.equalsIgnoreCase("new")){
				conj1.add(Restrictions.eq("status", OrderStatus.NEW));
			}
			else if(status.equalsIgnoreCase("pending")){
				conj1.add(Restrictions.eq("status", OrderStatus.PENDING));
			}
			else if(status.equalsIgnoreCase("completed")){
				conj1.add(Restrictions.eq("status", OrderStatus.COMPLETED));
			}
			else if(status.equalsIgnoreCase("delivery in transit")){
				conj1.add(Restrictions.eq("status", OrderStatus.DELIVERY_IN_TRANSIT));
			}
			else if(status.equalsIgnoreCase("cancelled")){
				conj1.add(Restrictions.eq("status", OrderStatus.CANCELLED));
			}
		}
		
		if(!paymentStatus.equals("")){
			if(paymentStatus.equalsIgnoreCase("paid")){
				conj1.add(Restrictions.eq("paymentStatus", PaymentStatus.PAID));
			}
			else if(paymentStatus.equalsIgnoreCase("pending")){
				conj1.add(Restrictions.eq("paymentStatus", PaymentStatus.PENDING));
			}
		}
		
		if(!shippingType.equals("")){
			if(shippingType.equalsIgnoreCase("pickup")){
				conj1.add(Restrictions.eq("shippingType", ShippingType.PICKUP));
			}
			else if(shippingType.equalsIgnoreCase("delivery")){
				conj1.add(Restrictions.eq("shippingType", ShippingType.DELIVERY));
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		if(member != null){
			conj2.add(Restrictions.eq("member", member));
		}
		
		if(shippingType.equals("")){
		     conj2.add(Restrictions.eq("shippingType", ShippingType.DELIVERY));
		}
		if(paymentStatus.equals("")){
		     conj2.add(Restrictions.eq("paymentStatus", PaymentStatus.PAID));
		}
		
		conj2.add(Restrictions.eq("company", company));
		conj2.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj2.add(Restrictions.ge("createdOn", fromDeyt));
		conj2.add(Restrictions.le("createdOn", toDeyt));
		
		//if(!branch_comments.equals("")){
		//	conj2.add(Restrictions.ilike("comments", branch_comments, MatchMode.ANYWHERE));
		//}
		if(!branch_info1.equals("")){
			conj2.add(Restrictions.ilike("info1", branch_info1, MatchMode.ANYWHERE));
		}
		
		if(!status.equals("")){
			if(status.equalsIgnoreCase("new")){
				conj2.add(Restrictions.eq("status", OrderStatus.NEW));
			}
			else if(status.equalsIgnoreCase("pending")){
				conj2.add(Restrictions.eq("status", OrderStatus.PENDING));
			}
			else if(status.equalsIgnoreCase("completed")){
				conj2.add(Restrictions.eq("status", OrderStatus.COMPLETED));
			}
			else if(status.equalsIgnoreCase("delivery in transit")){
				conj2.add(Restrictions.eq("status", OrderStatus.DELIVERY_IN_TRANSIT));
			}
			else if(status.equalsIgnoreCase("cancelled")){
				conj2.add(Restrictions.eq("status", OrderStatus.CANCELLED));
			}
		}
		
		if(!paymentStatus.equals("")){
			if(paymentStatus.equalsIgnoreCase("paid")){
				conj2.add(Restrictions.eq("paymentStatus", PaymentStatus.PAID));
			}
			else if(paymentStatus.equalsIgnoreCase("pending")){
				conj2.add(Restrictions.eq("paymentStatus", PaymentStatus.PENDING));
			}
		}
		if(!shippingType.equals("")){
			if(shippingType.equalsIgnoreCase("pickup")){
				conj2.add(Restrictions.eq("shippingType", ShippingType.PICKUP));
			}
			else if(shippingType.equalsIgnoreCase("delivery")){
				conj2.add(Restrictions.eq("shippingType", ShippingType.DELIVERY));
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		if(member != null){
			conj3.add(Restrictions.eq("member", member));
		}
		if(shippingType.equals("")){
		     conj3.add(Restrictions.eq("shippingType", ShippingType.DELIVERY));
		}
		if(paymentStatus.equals("")){
		     conj3.add(Restrictions.eq("paymentStatus", PaymentStatus.PENDING));
		}
		conj3.add(Restrictions.eq("company", company));
		conj3.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj3.add(Restrictions.ge("createdOn", fromDeyt));
		conj3.add(Restrictions.le("createdOn", toDeyt));
		
		//if(!branch_comments.equals("")){
		//	conj3.add(Restrictions.ilike("comments", branch_comments, MatchMode.ANYWHERE));
		//}
		if(!branch_info1.equals("")){
			conj3.add(Restrictions.ilike("info1", branch_info1, MatchMode.ANYWHERE));
		}
		
		if(!status.equals("")){
			if(status.equalsIgnoreCase("new")){
				conj3.add(Restrictions.eq("status", OrderStatus.NEW));
			}
			else if(status.equalsIgnoreCase("pending")){
				conj3.add(Restrictions.eq("status", OrderStatus.PENDING));
			}
			else if(status.equalsIgnoreCase("completed")){
				conj3.add(Restrictions.eq("status", OrderStatus.COMPLETED));
			}
			else if(status.equalsIgnoreCase("delivery in transit")){
				conj3.add(Restrictions.eq("status", OrderStatus.DELIVERY_IN_TRANSIT));
			}
			else if(status.equalsIgnoreCase("cancelled")){
				conj3.add(Restrictions.eq("status", OrderStatus.CANCELLED));
			}
		}
		
		if(!paymentStatus.equals("")){
			if(paymentStatus.equalsIgnoreCase("paid")){
				conj3.add(Restrictions.eq("paymentStatus", PaymentStatus.PAID));
			}
			else if(paymentStatus.equalsIgnoreCase("pending")){
				conj3.add(Restrictions.eq("paymentStatus", PaymentStatus.PENDING));
			}
		}
		if(!shippingType.equals("")){
			if(shippingType.equalsIgnoreCase("pickup")){
				conj3.add(Restrictions.eq("shippingType", ShippingType.PICKUP));
			}
			else if(shippingType.equalsIgnoreCase("delivery")){
				conj3.add(Restrictions.eq("shippingType", ShippingType.DELIVERY));
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		
		disjunc.add(Restrictions.or(Restrictions.or(conj1, conj2),conj3));//pickup , paid
		//disjunc.add(conj2);//delivery , paid
		//disjunc.add(conj3);//delivery , pending
		
		
		
		
		return dao.findAllByCriterion(pageNumber, resultsPerPage, null, null, null,
				new Order[] { Order.desc("createdOn") },
				disjunc).getList() ;
		
		
		
		
	}
	
	//this is a method use to add conjunction for wendys
	  public void addCriteriaConjunction(Conjunction conj, String status, String paymentStatus, String shippingType, String branch){
		 
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
		return dao.getOrderCount(company);
	}
	
	public BigInteger getPaidOrderCount(Company company){
		return dao.getPaidOrderCount(company);
	}
	
	public BigInteger getNewPickupCount(Company company, String branch){
		return dao.getNewPickupCount(company, branch);
	}
	
	public BigInteger getPendingPickupCount(Company company, String branch){
		return dao.getPendingPickupCount(company, branch);
	}
	
	public BigInteger getCompletedPickupCount(Company company, String branch){
		return dao.getCompletedPickupCount(company, branch);
	}
	
	public BigInteger getCancelledPickupCount(Company company, String branch){
		return dao.getCancelledPickupCount(company, branch);
	}
	
	public BigInteger getNewDeliveryCount(Company company, String branch){
		return dao.getNewDeliveryCount(company, branch);
	}
	
	public BigInteger getPendingDeliveryCount(Company company, String branch){
		return dao.getPendingDeliveryCount(company, branch);
	}
	
	public BigInteger getCompletedDeliveryCount(Company company, String branch){
		return dao.getCompletedDeliveryCount(company, branch);
	}
	
	public BigInteger getCancelledDeliveryCount(Company company, String branch){
		return dao.getCancelledDeliveryCount(company, branch);
	}
	
	public List<CartOrder> findAllByCompany(Company company) {
		return dao.findAllByCompany(company);
	}
	
	public ObjectList<CartOrder> findOrderByDate(Company company, Date fromDate, Date toDate) {
		return dao.findOrderByDate(company, fromDate, toDate);
	}
	
	public ObjectList<CartOrder> findOrderByDateAndMember(Company company, Date fromDate, Date toDate, Member member){
		return dao.findOrderByDateAndMember(company, fromDate, toDate, member);
	}
	
	public ObjectList<CartOrder> findAllByMemberCompleted(Company company, Member member, Date fromDate, Date toDate)
	{
		return dao.findAllByMemberCompleted(company, member, fromDate, toDate);
	}
	
	public ObjectList<CartOrder> findAllByMember(Company company, Member member, Date fromDate, Date toDate)
	{
		return dao.findAllByMember(company, member, fromDate, toDate);
	}
	
	public ObjectList<CartOrder> findAllNonRewardStatusNotesAndEmptyInfo5(Company company, Member member){
		return dao.findAllNonRewardStatusNotesAndEmptyInfo5(company, member);
	}
	
	public ObjectList<CartOrder> findAllByMemberAndOrderStatus(Company company, Member member, OrderStatus orderStatus, int resultPerPage, int pageNumber, Order...orders){
		return dao.findAllByMemberAndOrderStatusWithPaging(company, member, orderStatus, resultPerPage, pageNumber, orders);
	}
	
	public BigInteger getMemberOrderCount(Company company, Member member) {
		return dao.getMemberOrderCount(company, member);
	}
	
	public Boolean findIfFirstPurchase(CartOrder order, Member member){
		return dao.findIfFirstPurchase(order, member);
	}
	
}
