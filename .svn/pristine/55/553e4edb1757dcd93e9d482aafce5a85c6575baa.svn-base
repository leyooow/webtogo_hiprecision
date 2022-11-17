package com.ivant.cms.db;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Hibernate functionality class for shopping cart items.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class ShoppingCartItemDAO extends AbstractBaseDAO<ShoppingCartItem> {
	
	/**
	 * Returns all shopping cart items with the specified parameters.
	 * 
	 * @param shoppingCart
	 * 			- shopping cart where the item belongs to
	 * @param member
	 * 			- company client/buyer
	 * 
	 * @return - all shopping cart items with the specified parameters
	 */
	public ObjectList<ShoppingCartItem> findAll(ShoppingCart shoppingCart) {
		//get all valid shopping cart item with the same company and member
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("shoppingCart", shoppingCart));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<ShoppingCartItem> findAllByCompany(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, null, junc);
		
	}
	
	public ObjectList<ShoppingCartItem> findAllByCartAndCompany(ShoppingCart shoppingCart, Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("shoppingCart", shoppingCart));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, null, junc);
		
	}
	
	public ObjectList<ShoppingCartItem> findAll(ShoppingCart shoppingCart, Object[] ids) {
		//get all valid shopping cart item with the same company and member
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("shoppingCart", shoppingCart));
		junc.add(Restrictions.in("id", ids));
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<ShoppingCartItem> findAllByShoppingCart(ShoppingCart shoppingCart) {
		//get all valid shopping cart item with the same company and member
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("shoppingCart", shoppingCart));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ShoppingCartItem findByShoppingCartAndItem(ShoppingCart shoppingCart, CategoryItem categoryItem){
		Junction junc = Restrictions.conjunction();
		List<ShoppingCartItem> shoppingCartItemList = new ArrayList<ShoppingCartItem>();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("shoppingCart", shoppingCart));
		junc.add(Restrictions.eq("categoryItem", categoryItem));
		
		
		shoppingCartItemList = findAllByCriterion(null, null, null, null, junc).getList();
		if(shoppingCartItemList != null && shoppingCartItemList.size() > 0){
			return shoppingCartItemList.get(0);
		}
		return null;
	}
	
	public ObjectList<ShoppingCartItem> findAllByShoppingCartAndCategoryItemParentGroup(ShoppingCart shoppingCart, Group categoryItemParentGroup){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("shoppingCart", shoppingCart));
		junc.add(Restrictions.eq("categoryItem.parentGroup", categoryItemParentGroup));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	/**
	 * Returns the {@link ShoppingCartItem} item based on the specified parameters.
	 * 
	 * @param shoppingCart
	 * 			- shopping cart where the item belongs to
	 * @param itemDetail
	 * 			- detail of the item that has been added to the cart 
	 * 
	 * @return - the {@link ShoppingCartItem} item based on the specified parameters
	 */
	public ShoppingCartItem find(ShoppingCart shoppingCart, ItemDetail itemDetail) {
		
		itemDetail.setImage(null);
		//get all valid shopping cart item with the same company and member
		Junction junc = Restrictions.conjunction();	
		//System.out.println(" +++++++++++++++++++++   " + shoppingCart.getId() + " " + itemDetail.getSku() );
		
		junc.add(Restrictions.eq("shoppingCart", shoppingCart));
		junc.add(Restrictions.eq("itemDetail.realID", itemDetail.getRealID()));		
		
		//get shopping cart item
		ObjectList<ShoppingCartItem> result = findAllByCriterion(null, null, null, null, junc);
		//System.out.println(" +++++++++++" + result.getSize());
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		//return shopping cart item
		return result.getList().get(0);
	}
	
	/**
	 * Returns the {@link ShoppingCartItem} item based on the specified parameters.
	 * 
	 * @param shoppingCart
	 * 			- shopping cart where the item belongs to
	 * @param itemDetail
	 * 			- detail of the item that has been added to the cart 
	 * @param isValid
	 * 			- current status of cart item, status can either be deleted or not
	 * 
	 * @return - the {@link ShoppingCartItem} item based on the specified parameters
	 */
	public ShoppingCartItem find(ShoppingCart shoppingCart, ItemDetail itemDetail, Boolean isValid) {
		//get all valid shopping cart item with the same company and member
		Junction junc = Restrictions.conjunction();		
		junc.add(Restrictions.eq("shoppingCart", shoppingCart));
		junc.add(Restrictions.eq("itemDetail", itemDetail));
		junc.add(Restrictions.eq("isValid", isValid));
		
		//get shopping cart item
		ObjectList<ShoppingCartItem> result = findAllByCriterion(null, null, null, null, junc);
		
		//validate result
		if(null == result || result.getList().isEmpty())
			return null;
		
		//return shopping cart item
		return result.getList().get(0);
	}
	
	public ObjectList<ShoppingCartItem> findAllByPrice(ShoppingCart shoppingCart) {
		//get all valid shopping cart item with the same company and member
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("shoppingCart", shoppingCart));
		
		Order[] orders = {Order.desc("itemDetail.shippingPrice")};
		
		return findAllByCriterion(null, null, null, orders, junc);
	}	
	
	public BigInteger findCartCountByOrder(Company company, ShoppingCart shoppingCart) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_item WHERE company_id = :currentCompanyID and cart_id = :currentCartID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setLong("currentCartID", shoppingCart.getId());
		return (BigInteger) q.uniqueResult();
	}
	
	public BigInteger findCartCountByOrder(Company company, ShoppingCart shoppingCart, Boolean countByQuantity) {
		
		StringBuilder query = new StringBuilder();
		if(countByQuantity){
			query.append("SELECT SUM(quantity) FROM cart_item WHERE company_id = :currentCompanyID and cart_id = :currentCartID and valid is true");
		}else{
			query.append("SELECT COUNT(*) FROM cart_item WHERE company_id = :currentCompanyID and cart_id = :currentCartID and valid is true");
		}
		
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setLong("currentCartID", shoppingCart.getId());
		
		try{
		
			return BigInteger.valueOf( Long.valueOf( ((BigDecimal) q.uniqueResult()).longValue() ) );
		}
		catch(Exception e){
			System.out.println("### Cart item count was set to zero! ###");
			return BigInteger.valueOf(0L);
		}
	}
}
