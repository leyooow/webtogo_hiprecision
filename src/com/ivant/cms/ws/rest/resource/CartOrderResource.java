package com.ivant.cms.ws.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.ws.rest.model.CartOrderModel;

@Path("cartOrder")
public class CartOrderResource extends AbstractResource
{
	private static final Logger logger = LoggerFactory.getLogger(CartOrderResource.class);
	
	private CartOrderModel cartOrderModel;
	private CartOrder cartOrder;
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public CartOrderModel find(@Context HttpHeaders headers,
			@FormParam("id") Long id)
	{
		logger.info("find method executed!");
		
		cartOrder = null;
		
		try
		{
			openSession();
			
			cartOrder = cartOrderDelegate.find(id);
			cartOrderModel = new CartOrderModel(cartOrder);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		return cartOrderModel;
	}
}
