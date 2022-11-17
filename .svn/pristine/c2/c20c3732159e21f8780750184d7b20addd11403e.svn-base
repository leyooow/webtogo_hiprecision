package com.ivant.cms.delegate;

import com.ivant.cms.db.BrandImageDAO;
import com.ivant.cms.entity.BrandImage;

public class BrandImageDelegate extends AbstractBaseDelegate<BrandImage, BrandImageDAO>{

	private static BrandImageDelegate instance = new BrandImageDelegate();
	
	public static BrandImageDelegate getInstance() {
		return BrandImageDelegate.instance;
	}
	
	public BrandImageDelegate() {
		super(new BrandImageDAO());
	}
	
}
