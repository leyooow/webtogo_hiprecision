package com.ivant.cms.enums;


public enum BillingStatusEnum
{
		PENDING ("Pending", 1),
		PAID ("Paid", 2),
		OVERDUE ("Overdue", 3),
		CANCELLED ("Cancelled", 4);
		
		private final String statusName;
		private final int statusCode;
		
		BillingStatusEnum (String status, int code)
		{	this.statusName = status;
		  this.statusCode = code;
		}
		
		public String getStatusName()
		{
			return statusName;
		}
		
		public int getStatusCode()
		{
			return statusCode;
		}

		public int getNum()
		{
			return ordinal();
		}
		
		public String getValue()
		{
			return this.statusName;
		}
}
