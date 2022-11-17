package com.ivant.cms.ws.rest.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cart.action.iPay88;
import com.ivant.cms.entity.CartOrder;
import com.ivant.constants.PaymentConstants;

@XmlRootElement(name = "CartOrder")
public class CartOrderModel extends AbstractCartModel
{
	private DecimalFormat df = new DecimalFormat("#.00");
	private String amount;
	private String userName;
	private String userEmail;
	private String userContact;
	private String remark;
	private String signature;
	
	public CartOrderModel()
	{
		
	}
	
	public CartOrderModel(CartOrder cartOrder)
	{
		if(cartOrder == null) return;
		
		double totalPrice = cartOrder.getTotalPrice();
		String totalPriceFormatted = "";
		Double shippingCost = cartOrder.getTotalShippingPrice2();
		if(shippingCost != null){
			totalPrice += shippingCost;
		}
		try{
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			totalPriceFormatted = numberFormatter.format(Double.parseDouble((new DecimalFormat ("#.00")).format(totalPrice)));
		}catch(Exception e){}
		
		setId(cartOrder.getId());
		setCreatedOn(cartOrder.getCreatedOn().toString());
		setItemCount(cartOrder.getItemCount());
		setTotalPrice(totalPriceFormatted);
		setAmount(df.format(cartOrder.getTotalPrice()));
		setUserName(cartOrder.getMember() != null ? cartOrder.getMember().getUsername() : "");
		setUserEmail(cartOrder.getMember() != null ? cartOrder.getMember().getEmail() : "");
		setUserContact(cartOrder.getMember() != null ? cartOrder.getMember().getMobile() : "");
		setRemark(cartOrder.getComments());
		iPay88 ipay88 = new iPay88(PaymentConstants.WE_IPAY88_MERCHANT_KEY, PaymentConstants.WE_IPAY88_MERCHANT_CODE, 
				getId().toString(), totalPrice, PaymentConstants.PHP_IPAY88_CURRENCY);

		setSignature(ipay88.getIPay88Signature());
	}

	public String getAmount() 
	{
		return amount;
	}

	public void setAmount(String amount) 
	{
		this.amount = amount;
	}

	public String getUserName() 
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getUserEmail() 
	{
		return userEmail;
	}

	public void setUserEmail(String userEmail) 
	{
		this.userEmail = userEmail;
	}

	public String getUserContact() 
	{
		return userContact;
	}

	public void setUserContact(String userContact) 
	{
		this.userContact = userContact;
	}

	public String getRemark() 
	{
		return remark;
	}

	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getSignature()
	{
		return signature;
	}
	
	public void setSignature(String signature) 
	{
		this.signature = signature;
	}

}
