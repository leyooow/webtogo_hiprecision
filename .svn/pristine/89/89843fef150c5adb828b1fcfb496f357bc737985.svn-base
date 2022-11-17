/**
 * 
 */
package com.ivant.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.interfaces.CartItemAware;

/**
 * @author Edgar S. Dacpano
 *
 */
public class CategoryItemUtil
{
	/** Cannot be instatiated */
	private CategoryItemUtil()
	{
	}

	private static final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private static final OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	private static final CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	
	/** Get the {@link CategoryItem} from a {@link ShoppingCartItem} */
	public static final CategoryItem getItemFromShoppingCart(ShoppingCartItem shoppingCartItem)
	{
		CategoryItem result = null;
		if(shoppingCartItem != null)
		{
			if(shoppingCartItem.getCategoryItem() != null)
			{
				result = categoryItemDelegate.find(shoppingCartItem.getCategoryItem().getId());
			}
			else
			{
				final ItemDetail itemDetail = shoppingCartItem.getItemDetail();
				if(itemDetail != null)
				{
					final Long itemID = itemDetail.getRealID();
					result = categoryItemDelegate.find(itemID);
				}
			}
		}
		return result;
	}
	
	/** Get the {@link CategoryItem} from a {@link CartOrderItem} */
	public static final CategoryItem getItemFromCartOrder(CartOrderItem cartOrderItem)
	{
		CategoryItem result = null;
		if(cartOrderItem != null)
		{
			if(cartOrderItem.getCategoryItem() != null)
			{
				result = categoryItemDelegate.find(cartOrderItem.getCategoryItem().getId());
			}
			else
			{
				final ItemDetail itemDetail = cartOrderItem.getItemDetail();
				if(itemDetail != null)
				{
					final Long itemID = itemDetail.getRealID();
					result = categoryItemDelegate.find(itemID);
				}
			}
		}
		return result;
	}
	
	/** Get the {@link CategoryItem} from a {@link CartOrderItem} */
	public static final CategoryItem getItemFromCartItem(CartItemAware cartItemAware)
	{
		CategoryItem result = null;
		if(cartItemAware != null)
		{
			if(cartItemAware.getCategoryItem() != null)
			{
				result = categoryItemDelegate.find(cartItemAware.getCategoryItem().getId());
			}
			else
			{
				final ItemDetail itemDetail = cartItemAware.getItemDetail();
				if(itemDetail != null)
				{
					final Long itemID = itemDetail.getRealID();
					result = categoryItemDelegate.find(itemID);
				}
			}
		}
		return result;
	}
	
	/** Get the content of {@link CategoryItemOtherField} based on {@link OtherField#getName()} by {@link CategoryItem} */
	public static final String getCategoryItemOtherFieldContent(String otherFieldName, CategoryItem item)
	{
		final CategoryItemOtherField ciof = categoryItemOtherFieldDelegate.findByOtherFieldName(item.getCompany(), item, otherFieldName);
		return ciof == null ? null : ciof.getContent();
	}
	
	/** Save/Update the content of {@link CategoryItemOtherField} based on {@link OtherField#getName()} by {@link CategoryItem} */
	public static final boolean updateCategoryItemOtherFieldContent(String otherFieldName, CategoryItem item, String content)
	{
		if(otherFieldName != null && item.getCompany() != null)
		{
			final OtherField of = otherFieldDelegate.find(otherFieldName, item.getCompany());
			return updateCategoryItemOtherFieldContent(of, item, content);
		}
		
		return false;
	}
	
	/** Save/Update the content of {@link CategoryItemOtherField} based on {@link OtherField#getName()} by {@link CategoryItem} */
	public static final boolean updateCategoryItemOtherFieldContent(OtherField of, CategoryItem item, String content)
	{
		try
		{
			final Company company = item.getCompany();
			CategoryItemOtherField ciof = categoryItemOtherFieldDelegate.findByCategoryItemOtherField(company, item, of);
			if(ciof == null && of != null)
			{
				ciof = new CategoryItemOtherField();
				ciof.setContent(content);
				ciof.setCategoryItem(item);
				ciof.setOtherField(of);
				ciof.setCompany(company);
				ciof.setIsValid(Boolean.TRUE);
				return (categoryItemOtherFieldDelegate.insert(ciof) != null);
			}
			else if(of != null)
			{
				final List<CategoryItemOtherField> fields = categoryItemOtherFieldDelegate.findAllByCategoryItemOtherField(company, item, of);
				if(fields != null && !fields.isEmpty())
				{
					boolean partial = false;
					for(CategoryItemOtherField field : fields)
					{
						field.setContent(content);
						if(!categoryItemOtherFieldDelegate.update(ciof))
						{
							partial = true;
						}
					}
					return !partial;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static final <T extends CartItemAware> Collection<CategoryItem> getItemsFromCartByGroup(Group group, Collection<T> cartItems)
	{
		if(group == null)
		{
			return null;
		}
		
		return getItemsFromCartByGroup(group.getId(), cartItems);
	}
	
	public static final <T extends CartItemAware> Collection<CategoryItem> getItemsFromCartByGroup(Long groupId, Collection<T> cartItems)
	{
		if(cartItems == null)
		{
			return null;
		}
		
		Collection<CategoryItem> items = new ArrayList<CategoryItem>();
		
		for(T item : cartItems)
		{
			items.add(item.getCategoryItem());
		}
		
		return items;
	}

}
