package com.ivant.cms.delegate;

import com.ivant.cms.db.MicePhilippinesMemberDAO;
import com.ivant.cms.entity.MicePhilippinesMember;

public class MicePhilippinesMemeberDelegate extends
		AbstractBaseDelegate<MicePhilippinesMember, MicePhilippinesMemberDAO> {

	private static MicePhilippinesMemeberDelegate instance = new MicePhilippinesMemeberDelegate();

	public static MicePhilippinesMemeberDelegate getInstance() {
		return MicePhilippinesMemeberDelegate.instance;
	}

	protected MicePhilippinesMemeberDelegate() {
		super(new MicePhilippinesMemberDAO());
	}

	public MicePhilippinesMember findMicePhilippinesMember(String id) {
		return dao.findMicePhilippinesMember(id);
	}

}
