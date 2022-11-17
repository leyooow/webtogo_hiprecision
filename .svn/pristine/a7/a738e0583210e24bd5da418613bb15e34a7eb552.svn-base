package com.ivant.cms.delegate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.db.WishlistDAO;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Wishlist;
import com.ivant.cms.entity.WishlistType;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Runtime Data Access implementation class for {@link Wishlist}.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class WishlistDelegate extends AbstractBaseDelegate<Wishlist, WishlistDAO>{
	
	/** Thread safe {@code WishlistDelegate} class instance*/
	private volatile static WishlistDelegate instance;
	
	/**
	 * Creates a new instance of class {@code WishlistDelegate}.
	 */
	protected WishlistDelegate(WishlistDAO dao) {
		super(new WishlistDAO());		
	}
	
	/**
	 * Returns {@code WishlistDelegate} class instance.
	 * 
	 * @return
	 */
	public static WishlistDelegate getInstance(){
		//generate a new instance, initial instance is null
		if(instance == null){
			//ensure thread safety
			synchronized (WishlistDelegate.class) {
				if(instance == null)
					instance = new WishlistDelegate(new WishlistDAO());
			}
		}
		return instance;
	}
	
	/**
	 * Returns all wishlist items with the parameters.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param member
	 * 			- company client/buyer
	 * 
	 * @return - all wishlist items with the specified {@code company} 
	 * 			and {@code member} parameters
	 */
	public ObjectList<Wishlist> findAll(Company company, Member member) {
		return dao.findAll(company, member);
	}
	
	/**
	 * Returns the {@link Wishlist} item based on the specified parameters.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param member
	 * 			- company client/buyer
	 * @param categoryItem
	 * 			- item that user added to his/her wishlist table
	 * 
	 * @return - the {@link Wishlist} item based on the specified parameters
	 */
	public Wishlist find(Company company, Member member, CategoryItem categoryItem) {
		return dao.find(company, member, categoryItem);
	}

	/**
	 * Returns all wishlist by company, with paging.
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
	public List<Wishlist> listAllWishlist(Company company, int resultsPerPage, int pageNumber) {
		return dao.findAllByCriterion(pageNumber, resultsPerPage, null, null, null,
				new Order[] { Order.desc("createdOn") },
				Restrictions.eq("company", company),
				Restrictions.eq("isValid", Boolean.TRUE)).getList();
	}
	
	/**
	 * Returns the number of wishlist per company.
	 * 
	 * @param company
	 * 			- company to get the wishlist from
	 * 
	 * @return - the number of wishlist per company
	 */
	public BigInteger getWishlistCount(Company company) {
		return dao.getWishlistCount(company);
	}
	
	public BigInteger getMemberWishlistCount(Company company, Member member) {
		return dao.getMemberWishlistCount(company, member);
	}
	
	public ObjectList<Wishlist> findAllByItem(Company company, Member member, CategoryItem categoryItem) {
		return dao.findAllByItem(company, member, categoryItem);
	}
	
	public Wishlist findByMemberItemAndType(Company company, Member member, CategoryItem categoryItem, WishlistType wishlistType){
		return dao.findByMemberItemAndType(company, member, categoryItem, wishlistType);
	}
	
	public ObjectList<Wishlist> findAllByMemberAndType(Company company, Member member, WishlistType wishlistType) {
		return dao.findAllByMemberAndType(company, member, wishlistType);
	}
	
	
}
