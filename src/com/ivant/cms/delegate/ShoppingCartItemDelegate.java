package com.ivant.cms.delegate;

import java.math.BigInteger;

import com.ivant.cms.db.ShoppingCartItemDAO;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Runtime Data Access implementation class for {@link ShoppingCartItem}.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class ShoppingCartItemDelegate extends AbstractBaseDelegate<ShoppingCartItem, ShoppingCartItemDAO> {
	
	/** Thread safe {@code ShoppingCartItemDelegate} class instance*/
	private volatile static ShoppingCartItemDelegate instance;
	
	/**
	 * Creates a new instance of class {@code ShoppingCartItemDelegate}.
	 */
	protected ShoppingCartItemDelegate(ShoppingCartItemDAO dao) {
		super(new ShoppingCartItemDAO());
	}

	/**
	 * Returns {@code ShoppingCartItemDelegate} class instance.
	 * 
	 * @return - {@code ShoppingCartItemDelegate} class instance
	 */
	public static ShoppingCartItemDelegate getInstance(){
		//generate a new instance, initial instance is null
		if(instance == null){
			//ensure thread safety
			synchronized (ShoppingCartItemDelegate.class) {
				if(instance == null)
					instance = new ShoppingCartItemDelegate(new ShoppingCartItemDAO());
			}
		}
		return instance;
	}
	
	/**
	 * Returns all shopping cart items with the specified parameters.
	 * 
	 * @param shoppingCart
	 * 			- shopping cart where the item belongs to
	 * 
	 * @return - all shopping cart items with the specified parameters
	 */
	public ObjectList<ShoppingCartItem> findAll(ShoppingCart shoppingCart) {
		return dao.findAll(shoppingCart);
	}
	
	public ObjectList<ShoppingCartItem> findAllByCompany(Company company){
		return dao.findAllByCompany(company);
	}
	
	public ObjectList<ShoppingCartItem> findAllByCartAndCompany(ShoppingCart shoppingCart, Company company){
		return dao.findAllByCartAndCompany(shoppingCart, company);
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
		return dao.find(shoppingCart, itemDetail);
	}
	
	public ObjectList<ShoppingCartItem> findAll(ShoppingCart shoppingCart, Object[] ids) {
		return dao.findAll(shoppingCart, ids);
	}
	
	public ObjectList<ShoppingCartItem> findAllByShoppingCart(ShoppingCart shoppingCart) {
		return dao.findAllByShoppingCart(shoppingCart);
	}
	
	public ShoppingCartItem findByShoppingCartAndItem(ShoppingCart shoppingCart, CategoryItem categoryItem) {
		return dao.findByShoppingCartAndItem(shoppingCart, categoryItem);
	}
	
	public ObjectList<ShoppingCartItem> findAllByShoppingCartAndCategoryItemParentGroup(ShoppingCart shoppingCart, Group categoryItemParentGroup){
		return dao.findAllByShoppingCartAndCategoryItemParentGroup(shoppingCart, categoryItemParentGroup);
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
		return dao.find(shoppingCart, itemDetail, isValid);
	}

	public ObjectList<ShoppingCartItem> findAllByPrice(ShoppingCart shoppingCart) {
		return dao.findAllByPrice(shoppingCart);
	}
	
	
	public BigInteger findCartCountByOrder(Company company, ShoppingCart shoppingCart) {
		return dao.findCartCountByOrder(company, shoppingCart);
	}
	
	/**
	 * When <code>countByQuantity</code> is true, returns the sum of quantities,
	 * otherwise, performs {@link #findCartCountByOrder(Company, ShoppingCart)} 
	 * @param company
	 * @param shoppingCart
	 * @param countByQuantity
	 * @return
	 */
	public BigInteger findCartCountByOrder(Company company, ShoppingCart shoppingCart, Boolean countByQuantity) {
		return dao.findCartCountByOrder(company, shoppingCart, countByQuantity);
	}
	
}
