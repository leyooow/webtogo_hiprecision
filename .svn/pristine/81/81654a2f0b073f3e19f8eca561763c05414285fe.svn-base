package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.ShoppingCartDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.ShoppingCart;

/**
 * Runtime Data Access implementation class for {@link ShoppingCart}.
 * 
 * @author Mark Kenneth M. Rañosa
 *
 */
public class ShoppingCartDelegate extends AbstractBaseDelegate<ShoppingCart, ShoppingCartDAO> {
	
	/** Thread safe {@code ShoppingCartDelegate} class instance*/
	private volatile static ShoppingCartDelegate instance;

	/**
	 * Creates a new instance of class {@code ShoppingCartDelegate}.
	 */
	protected ShoppingCartDelegate(ShoppingCartDAO dao) {
		super(new ShoppingCartDAO());
	}
	
	/**
	 * Returns {@code ShoppingCartDelegate} class instance.
	 * 
	 * @return - {@code ShoppingCartDelegate} class instance
	 */
	public static ShoppingCartDelegate getInstance(){
		//generate a new instance, initial instance is null
		if(instance == null){
			//ensure thread safety
			synchronized (ShoppingCartDelegate.class) {
				if(instance == null)
					instance = new ShoppingCartDelegate(new ShoppingCartDAO());
			}
		}
		return instance;
	}
	
	/**
	 * Returns the {@link ShoppingCart} item based on the specified parameters.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param member
	 * 			- company client/buyer
	 * 
	 * @return - the {@link ShoppingCart} item based on the specified parameters
	 */
	public ShoppingCart find(Company company, Member member) {
		return dao.find(company, member);
	}
	

}
