package com.ivant.cms.db;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Wishlist;
import com.ivant.cms.entity.WishlistType;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Hibernate functionality class for wishlist items.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class WishlistDAO extends AbstractBaseDAO<Wishlist> {
	
	/**
	 * Returns all wishlist items with the specified parameters.
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
		//get all valid wishlist item with the same company and member
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		return findAllByCriterion(null, null, null, null, junc);
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
		//get all valid wishlist item with the same company and member
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		junc.add(Restrictions.eq("item", categoryItem));
		
		//get wishlist item
		ObjectList<Wishlist> result = findAllByCriterion(null, null, null, null, junc);
		
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return wishlist item
		return result.getList().get(0);
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
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM wishlist WHERE company_id = :currentCompanyID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		return (BigInteger) q.uniqueResult();
	}
	
	public BigInteger getMemberWishlistCount(Company company, Member member) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM wishlist WHERE company_id = :currentCompanyID and member_id = :currentMemberID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setLong("currentMemberID", member.getId());
		return (BigInteger) q.uniqueResult();
	}
	
	public ObjectList<Wishlist> findAllByItem(Company company, Member member, CategoryItem categoryItem) {
		//get all valid wishlist item with the same company and member
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		junc.add(Restrictions.eq("item", categoryItem));
		
		//get wishlist item
		//return findAllByCriterion(null, null, null, null, junc);
		return findAllByCriterion(null, null, null, null, null,	null, junc);
		
	}
	
	public Wishlist findByMemberItemAndType(Company company, Member member, CategoryItem categoryItem, WishlistType wishlistType){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		junc.add(Restrictions.eq("item", categoryItem));
		
		if(wishlistType != null){
			junc.add(Restrictions.eq("wishlistType", wishlistType));
		}
		else{
			junc.add(Restrictions.isNull("wishlistType"));
		}
		List<Wishlist> res = findAllByCriterion(null, null, null, null, null, null, junc).getList();
		if(res != null){
			if(res.size() > 0){
				return res.get(0);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	public ObjectList<Wishlist> findAllByMemberAndType(Company company, Member member, WishlistType wishlistType) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		if(wishlistType != null){
			junc.add(Restrictions.eq("wishlistType", wishlistType));
		}
		else{
			junc.add(Restrictions.isNull("wishlistType"));
		}
		
		return findAllByCriterion(null, null, null, null, null, null, junc);
	}
}
