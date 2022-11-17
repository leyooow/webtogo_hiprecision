package com.ivant.cms.delegate;

import com.ivant.cms.db.PreOrderDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.PreOrder;

/**
 * Runtime Data Access implementation class for {@link ShoppingCart}.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class PreOrderDelegate extends AbstractBaseDelegate<PreOrder, PreOrderDAO> {
	
	/** Thread safe {@code ShoppingCartDelegate} class instance*/
	private volatile static PreOrderDelegate instance;

	/**
	 * Creates a new instance of class {@code ShoppingCartDelegate}.
	 */
	protected PreOrderDelegate(PreOrderDAO dao) {
		super(new PreOrderDAO());
	}
	
	/**
	 * Returns {@code ShoppingCartDelegate} class instance.
	 * 
	 * @return - {@code ShoppingCartDelegate} class instance
	 */
	public static PreOrderDelegate getInstance(){
		//generate a new instance, initial instance is null
		if(instance == null){
			//ensure thread safety
			synchronized (PreOrderDelegate.class) {
				if(instance == null)
					instance = new PreOrderDelegate(new PreOrderDAO());
			}
		}
		return instance;
	}
	
	/**
	 * Returns the {@link PreOrder} item based on the specified parameters.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param member
	 * 			- company client/buyer
	 * 
	 * @return - the {@link PreOrder} item based on the specified parameters
	 */
	public PreOrder find(Company company, Member member) {
		return dao.find(company, member);
	}

}
