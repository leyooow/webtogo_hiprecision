package com.ivant.cms.delegate;

import java.util.List;
import com.ivant.cms.db.PreOrderItemDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.PreOrder;
import com.ivant.cms.entity.PreOrderItem;

/**
 * Runtime Data Access implementation class for {@link ShoppingCart}.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class PreOrderItemDelegate extends AbstractBaseDelegate<PreOrderItem, PreOrderItemDAO> {
	
	/** Thread safe {@code ShoppingCartDelegate} class instance*/
	private volatile static PreOrderItemDelegate instance;

	/**
	 * Creates a new instance of class {@code ShoppingCartDelegate}.
	 */
	protected PreOrderItemDelegate(PreOrderItemDAO dao) {
		super(new PreOrderItemDAO());
	}
	
	/**
	 * Returns {@code ShoppingCartDelegate} class instance.
	 * 
	 * @return - {@code ShoppingCartDelegate} class instance
	 */
	public static PreOrderItemDelegate getInstance(){
		//generate a new instance, initial instance is null
		if(instance == null){
			//ensure thread safety
			synchronized (PreOrderItemDelegate.class) {
				if(instance == null)
					instance = new PreOrderItemDelegate(new PreOrderItemDAO());
			}
		}
		return instance;
	}
	
	/**
	 * Returns the {@link PreOrderItem} item based on the specified parameters.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param member
	 * 			- company client/buyer
	 * 
	 * @return - the {@link PreOrderItem} item based on the specified parameters
	 */
	public PreOrderItem find(PreOrder preOrder, CategoryItem categoryItem) {
		return dao.find(preOrder, categoryItem);
	}

	public List<PreOrderItem> findAll(Company company, PreOrder preOrder) {
		return dao.findAll(company, preOrder);
	}
}
