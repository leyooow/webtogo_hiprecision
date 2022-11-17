package com.ivant.cms.delegate;

import com.ivant.cms.db.KuysenSalesAreaDAO;
import com.ivant.cms.entity.KuysenSalesArea;

public class KuysenSalesAreaDelegate extends AbstractBaseDelegate<KuysenSalesArea, KuysenSalesAreaDAO> {
	
	private static KuysenSalesAreaDelegate instance = new KuysenSalesAreaDelegate();

	public KuysenSalesAreaDelegate() {
		super(new KuysenSalesAreaDAO());
	}
	
	public static KuysenSalesAreaDelegate getInstance() {
		return instance;
	}

}
