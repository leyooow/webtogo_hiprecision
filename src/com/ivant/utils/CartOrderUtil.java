/**
 *
 */
package com.ivant.utils;

import java.util.List;

import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.interfaces.CartAware;
import com.ivant.cms.interfaces.CartItemAware;

/**
 * Utility class for entities {@link CartOrder}, {@link CartOrderItem}, {@link ShoppingCart} and {@link ShoppingCartItem} 
 * @author Edgar S. Dacpano
 *
 */
public final class CartOrderUtil
{
	
	private CartOrderUtil() // utility class
	{
	}
	
	/**
	 * Checks if the cart has discounted items with value greater than zero. 
	 * @param item
	 * @return
	 */
	public static final <T extends CartAware<U>, U extends CartItemAware> boolean hasDiscountItems(T cart)
	{
		boolean hasDiscountItems = false;
		if(cart != null)
		{
			final List<U> items = cart.getItems();
			if(items != null && !items.isEmpty())
			{
				for(U item : items)
				{
					if(hasDiscount(item))
					{
						return true;
					}
				}
			}
		}
		return hasDiscountItems;
	}
	
	/**
	 * Checks if the item has a discount value greater than zero. 
	 * @param item
	 * @return
	 */
	public static final <T extends CartItemAware> boolean hasDiscount(T item)
	{
		boolean hasDiscount = false;
		if(item != null)
		{
			final ItemDetail itemDetail = item.getItemDetail();
			if(itemDetail != null)
			{
				final Double discount = itemDetail.getDiscount();
				if(discount != null && discount > 0.00)
				{
					hasDiscount = true;
				}
			}
		}
		return hasDiscount;
	}
	
	/**
	 * Checks if the item has a discount value greater than zero. 
	 * @param item
	 * @return
	 */
	public static final <T extends CartItemAware> boolean isDiscountItem(T item)
	{
		boolean isDiscount = false;
		if(item != null)
		{
			final ItemDetail itemDetail = item.getItemDetail();
			if(itemDetail != null)
			{
				final Double discount = itemDetail.getDiscount();
				if(discount != null && discount > 0.00 && itemDetail.getPrice() <= 0.00)
				{
					isDiscount = true;
				}
			}
		}
		return isDiscount;
	}
	
	/**
	 * Note: the items to be set here will only be transient.
	 * Use {@link CartAware#getItems()} to get the included items
	 * @param cart
	 * @param discountPhrase - string to append to the item name to indicate it is a discount item 
	 *//*
	public static final <T extends CartAware<U>, U extends CartItemAware> void includeDiscountedItems(T cart, String discountPhrase)
	{
		if(cart != null)
		{
			final List<U> items = cart.getItems();
			if(items != null && !items.isEmpty())
			{
				final List<U> includeDiscountedItems = new LinkedList<U>();
				for(U item : items)
				{
					if(hasDiscount(item))
					{
						final ItemDetail cartItemDetail = item.getItemDetail();
						if(cartItemDetail != null)
						{
							final Double zero = Double.valueOf(0);
							
							final Double discountAmount = cartItemDetail.getDiscount();
							
							cartItemDetail.setDiscount(zero);
							item.setItemDetail(cartItemDetail);
							
							includeDiscountedItems.add(item);
							
							@SuppressWarnings("unchecked")
							final U discountItem = (U) ((item instanceof ShoppingCartItem)
								? new ShoppingCartItem()
								: new CartOrderItem());
							
							final ItemDetail itemDetail = new ItemDetail();
							final boolean success = ReflectionUtil.copyValues(cartItemDetail, itemDetail);
							if(success)
							{
								itemDetail.setDiscount(discountAmount);
								itemDetail.setDiscountedPrice(zero);
								itemDetail.setPrice(-discountAmount);
								itemDetail.setName(cartItemDetail.getName() + " " + StringUtils.trimToEmpty(discountPhrase));
								
								discountItem.setItemDetail(itemDetail);
								discountItem.setQuantity(zero.intValue());
								
								includeDiscountedItems.add(discountItem);
							}
						}
					}
					else
					{
						includeDiscountedItems.add(item);
					}
				}
				cart.setItems(includeDiscountedItems);
			}
		}
	}*/
	
}
