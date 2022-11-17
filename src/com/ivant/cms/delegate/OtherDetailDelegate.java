package com.ivant.cms.delegate;

import com.ivant.cms.db.OtherDetailDAO;
import com.ivant.cms.entity.OtherDetail;

public class OtherDetailDelegate extends AbstractBaseDelegate<OtherDetail, OtherDetailDAO>{

	private static OtherDetailDelegate instance = new OtherDetailDelegate();
	
	public static OtherDetailDelegate getInstance() {
		return instance;
	}
	
	private OtherDetailDelegate() {
		super(new OtherDetailDAO());
	}
}
