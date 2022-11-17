package com.ivant.cms.delegate;

import com.ivant.cms.db.MultiPageImageDAO;
import com.ivant.cms.entity.MultiPageImage;


public class MultiPageImageDelegate extends AbstractBaseDelegate<MultiPageImage, MultiPageImageDAO>{

	private static MultiPageImageDelegate instance = new MultiPageImageDelegate();
	
	public static MultiPageImageDelegate getInstance() {
		return MultiPageImageDelegate.instance;
	}
	
	public MultiPageImageDelegate() {
		super(new MultiPageImageDAO());
	}
	
}
