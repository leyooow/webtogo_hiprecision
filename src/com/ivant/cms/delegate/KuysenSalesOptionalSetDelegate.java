package com.ivant.cms.delegate;

import com.ivant.cms.db.KuysenSalesOptionalSetDAO;
import com.ivant.cms.entity.KuysenSalesOptionalSet;

public class KuysenSalesOptionalSetDelegate extends AbstractBaseDelegate<KuysenSalesOptionalSet, KuysenSalesOptionalSetDAO> {
	
	private static KuysenSalesOptionalSetDelegate instance = new KuysenSalesOptionalSetDelegate();

	public KuysenSalesOptionalSetDelegate() {
		super(new KuysenSalesOptionalSetDAO());
	}
	
	public static KuysenSalesOptionalSetDelegate getInstance() {
		return instance;
	}

}
