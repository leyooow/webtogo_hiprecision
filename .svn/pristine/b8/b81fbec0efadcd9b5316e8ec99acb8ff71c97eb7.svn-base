package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.MicePhilippinesMember;

public class MicePhilippinesMemberDAO extends
		AbstractBaseDAO<MicePhilippinesMember> {

	public MicePhilippinesMemberDAO() {
		super();
	}

	public MicePhilippinesMember findMicePhilippinesMember(String id) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("memberID", id));

		List<MicePhilippinesMember> member = findAllByCriterion(null, null,
				null, null, junc).getList();

		if (member.size() == 1)
			return member.get(0);

		return null;
	}

}
