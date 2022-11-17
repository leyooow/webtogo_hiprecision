package com.ivant.cms.enums;

public enum BillingTypeEnum
{
		SETUP_FEE ("Setup Fee", 1),
		SUBSCRIPTION_FEE ("Subscription Fee", 2),
		EMAIL_FEE	("Email Fee", 3),
		OTHERS ("Others", 4),
		WEB_MAINTENANCE ("Web Maintenance and Hosting Fee", 5),
		SERVER_BACKUP_TROUBLESHOOTING_MAINTENANCE_FEE ("Server Backup Troubleshooting and Maintenance Fee", 6),
		BASIC_HOSTING_FEE ("Basic Hosting Fee", 7);
		
		
		private final String typeName;
		private final int typeCode;
		
		BillingTypeEnum (String name, int code)
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
