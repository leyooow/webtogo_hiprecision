package com.ivant.cms.delegate;

import com.ivant.cms.db.OtherFieldValueDAO;
import com.ivant.cms.entity.OtherFieldValue;

public class OtherFieldValueDelegate 
	extends AbstractBaseDelegate<OtherFieldValue, OtherFieldValueDAO>{

	private static OtherFieldValueDelegate instance = new OtherFieldValueDelegate(new OtherFieldValueDAO());
	
	public OtherFieldValueDelegate(OtherFieldValueDAO dao) {
		super(dao);
	}

	public static OtherFieldValueDelegate getInstance() {
		return instance;
	}
}
