package com.ivant.cms.delegate;

import com.ivant.cms.db.KuysenSalesClientDAO;
import com.ivant.cms.entity.KuysenSalesClient;

public class KuysenSalesClientDelegate extends AbstractBaseDelegate<KuysenSalesClient, KuysenSalesClientDAO> {
	
	private static KuysenSalesClientDelegate instance = new KuysenSalesClientDelegate();

	public KuysenSalesClientDelegate() {
		super(new KuysenSalesClientDAO());
	}
	
	public static KuysenSalesClientDelegate getInstance() {
		return instance;
	}
	
	public boolean referenceIdExist(String ref) {
		return dao.referenceIdExist(ref);
	}

}
