package com.ivant.cms.delegate;

import com.ivant.cms.db.GroupImageDAO;
import com.ivant.cms.entity.GroupImage;

public class GroupImageDelegate extends AbstractBaseDelegate<GroupImage, GroupImageDAO>{

	private static GroupImageDelegate instance = new GroupImageDelegate();
	
	public static GroupImageDelegate getInstance() {
		return GroupImageDelegate.instance;
	}
	
	public GroupImageDelegate() {
		super(new GroupImageDAO());
	}
	
}
