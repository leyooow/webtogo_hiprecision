package com.ivant.cms.delegate;

import com.ivant.cms.db.KuysenSalesOrderSetDAO;
import com.ivant.cms.entity.KuysenSalesOrderSet;

public class KuysenSalesOrderSetDelegate extends AbstractBaseDelegate<KuysenSalesOrderSet, KuysenSalesOrderSetDAO> {
	
	private static KuysenSalesOrderSetDelegate instance = new KuysenSalesOrderSetDelegate();

	public KuysenSalesOrderSetDelegate() {
		super(new KuysenSalesOrderSetDAO());
	}
	
	public static KuysenSalesOrderSetDelegate getInstance() {
		return instance;
	}

}
