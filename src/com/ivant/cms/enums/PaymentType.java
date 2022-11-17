package com.ivant.cms.enums;

/**
 * Status of current order.
 * 
 * @author Mark Kenneth M. Ra�osa
 * @author Edgar Dacpano
 * 
 */
public enum PaymentType
{
	/** Set other payment modes/types here */
	PAYPAL("Paypal"), 
	BANK("Bank"), 
	CASH("Cash"), 
	CREDIT_CARD("Credit Card"), 
	DRAGON_PAY("Dragon Pay"), 
	GLOBAL_PAY("Global Pay"), 
	BANK_DEPOSIT("Bank Deposit"),
	I_PAY88("iPay"),
	MONERIS("Moneris"), 
	COD("Cash On Delivery"),
	COPU("Cash On Pickup"),
	OTHERS("Others")
	;

	private String value;

	PaymentType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
	
	public static PaymentType searchPaymentType(String name)
	{
		try
		{
			return PaymentType.valueOf(name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return PaymentType.OTHERS;
	}
	
	/**
	 * 
	 * @param value
	 * @return the payment type that matches the given value
	 * @author Eric John Apondar
	 */
	public static PaymentType searchByValue(String value)
	{
		for(PaymentType p : values()){
			if(p.value.equals(value)) return p;
		}
		return PaymentType.OTHERS;
	}
}
