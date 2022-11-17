package com.ivant.cms.ws.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.entity.ShoppingCart;

@XmlRootElement(name = "ShoppingCart")
public class ShoppingCartModel extends AbstractCartModel
{
	public ShoppingCartModel()
	{
		
	}
	
	public ShoppingCartModel(ShoppingCart shoppingCart)
	{
		if(shoppingCart == null) return;
		
		setId(shoppingCart.getId());
		setCreatedOn(shoppingCart.getCreatedOn().toString());
		setItemCount(shoppingCart.getItemCount());
		setTotalPrice(shoppingCart.getFormattedTotalItemsPrice());
	}
	
}
