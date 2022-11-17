package com.ivant.cms.delegate;

import com.ivant.cms.db.KuysenSalesParentOrderSetDAO;
import com.ivant.cms.entity.KuysenSalesParentOrderSet;

public class KuysenSalesParentOrderSetDelegate extends AbstractBaseDelegate<KuysenSalesParentOrderSet, KuysenSalesParentOrderSetDAO> {
	
	private static KuysenSalesParentOrderSetDelegate instance = new KuysenSalesParentOrderSetDelegate();

	public KuysenSalesParentOrderSetDelegate() {
		super(new KuysenSalesParentOrderSetDAO());
	}
	
	public static KuysenSalesParentOrderSetDelegate getInstance() {
		return instance;
	}

}
