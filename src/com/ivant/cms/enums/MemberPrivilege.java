package com.ivant.cms.enums;

public enum MemberPrivilege
{
	SUPER_MEMBER("Super Member"), 
	ADMINISTRATOR("Administrator"), 
	NORMAL_MEMBER("Normal Member");

	private String value;

	MemberPrivilege(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
	
	public static MemberPrivilege searchMemberPrivilege(String name)
	{
		try
		{
			return MemberPrivilege.valueOf(name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return MemberPrivilege.NORMAL_MEMBER;
	}
}
