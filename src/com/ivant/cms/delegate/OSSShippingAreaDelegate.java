package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.OSSShippingAreaDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.OSSShippingArea;

public class OSSShippingAreaDelegate extends AbstractBaseDelegate<OSSShippingArea, OSSShippingAreaDAO> {
	
	private static final OSSShippingAreaDelegate instance = new OSSShippingAreaDelegate();
	
	public static OSSShippingAreaDelegate getInstance() {
		return instance;
	}

	private OSSShippingAreaDelegate() {
		super(new OSSShippingAreaDAO());
	}
	
	public List<OSSShippingArea> findAll(Company company) {
		return dao.findAll(company);
	}}
