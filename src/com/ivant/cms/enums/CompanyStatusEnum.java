package com.ivant.cms.enums;

/**
 * Status of company
 * 
 * @author Gil Antonio C. Pandes
 *
 */

public enum CompanyStatusEnum{
	/** Company web page is being developed */
	ONGOING,
	/** Company web page is being used */
	ACTIVE,
	/** Company web page is being used using their host */
	ACTIVE_NO_HOSTING,
	/** Company is suspended */
	SUSPENDED;
	
	public int getNum() 
	{
		return Integer.valueOf(ordinal());
	}
	
	public static CompanyStatusEnum getIndex(int index)
	{
		CompanyStatusEnum status[] = values();
		
		if (index >= 0 && index < status.length)	
			return status[index];
		return ONGOING;
	}

}