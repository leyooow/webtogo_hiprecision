package com.ivant.cms.delegate;

import com.ivant.cms.db.MemberPageFileDAO;
import com.ivant.cms.entity.MemberPageFile;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.PageFile;

public class MemberPageFileDelegate 
	extends AbstractBaseDelegate<MemberPageFile, MemberPageFileDAO>{

	private static MemberPageFileDelegate instance = new MemberPageFileDelegate();
	
	public static MemberPageFileDelegate getInstance() {
		return MemberPageFileDelegate.instance;
	}
	
	public MemberPageFileDelegate() {
		super(new MemberPageFileDAO());
		
	}

	public MemberPageFile find(MultiPageFile multiPageFile, MemberType memberType) {
		return dao.find(multiPageFile, memberType);
	}
	
	public MemberPageFile find(PageFile pageFile, MemberType memberType) {
		return dao.find(pageFile, memberType);
	}
}
