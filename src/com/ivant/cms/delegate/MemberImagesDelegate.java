package com.ivant.cms.delegate;

import com.ivant.cms.db.MemberImagesDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MemberImages;
import com.ivant.cms.entity.list.ObjectList;

public class MemberImagesDelegate extends AbstractBaseDelegate<MemberImages, MemberImagesDAO>
{
	private static MemberImagesDelegate instance = new MemberImagesDelegate();
	
	public static MemberImagesDelegate getInstance() 
	{
		return instance;
	}
	
	public MemberImagesDelegate() 
	{
		super(new MemberImagesDAO());
	}
	
	public MemberImages findByMember(Company company, String membername) 
	{
		return dao.findByMember(company, membername);
	}
	
	public ObjectList<MemberImages> findAll(Company company) 
	{
		return dao.findAll(company);
	}

}