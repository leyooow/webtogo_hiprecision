package com.ivant.cms.enums;

public enum PaymentTypeEnum
{
	CASH ("Cash", 1),
	CHECK ("Check", 2),
	BANK_DEPOSIT ("Bank Deposit", 3),
	OTHERS ("Others", 4);
	
	private final String typeName;
	private final int typeCode;
	
	
	PaymentTypeEnum (String name, int code)
	{
			this.typeName = name;
			this.typeCode = code;
	}
	
  public String getTypeName()
  {
  		return typeName;
  }
  
  public int getTypeCode()
  {
  	return typeCode;
  }
	
	public int getNum() 
	{
			return ordinal();
	}
	
	public String getValue()
	{
		return this.typeName;
	}
  
}
