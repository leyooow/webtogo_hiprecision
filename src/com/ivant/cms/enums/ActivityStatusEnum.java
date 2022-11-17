package com.ivant.cms.enums;


public enum ActivityStatusEnum
{
		NEW ("New", 1),
		DONE ("Done", 2),
		REWORK ("Rework", 3),
		CANCELLED ("Cancelled", 4);
		
		private final String statusName;
		private final int statusCode;
		
		ActivityStatusEnum (String status, int code)
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
