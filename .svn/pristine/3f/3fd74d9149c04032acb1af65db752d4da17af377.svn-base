package com.ivant.cms.delegate;

import com.ivant.cms.db.CartOrderPromoCodeDAO;
import com.ivant.cms.entity.CartOrderPromoCode;

public class CartOrderPromoCodeDelegate extends AbstractBaseDelegate<CartOrderPromoCode, CartOrderPromoCodeDAO>
{
	private static CartOrderPromoCodeDelegate instance = new CartOrderPromoCodeDelegate();
	
	public static CartOrderPromoCodeDelegate getInstance() 
	{
		return instance;
	}
	
	public CartOrderPromoCodeDelegate() 
	{
		super(new CartOrderPromoCodeDAO());
	}
	
}