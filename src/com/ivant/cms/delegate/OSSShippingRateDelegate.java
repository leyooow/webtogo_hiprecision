package com.ivant.cms.delegate;

import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.OSSShippingRateDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.OSSShippingLocation;
import com.ivant.cms.entity.OSSShippingRate;

public class OSSShippingRateDelegate extends AbstractBaseDelegate<OSSShippingRate, OSSShippingRateDAO> {
	
	private static final OSSShippingRateDelegate instance = new OSSShippingRateDelegate();
	
	public static OSSShippingRateDelegate getInstance() {
		return instance;
	}

	private OSSShippingRateDelegate() {
		super(new OSSShippingRateDAO());
	}
	
	public List<OSSShippingRate> findAll(Company company, OSSShippingLocation location, Order...orders) {
		return dao.findAll(company, location, orders);
	}
}
