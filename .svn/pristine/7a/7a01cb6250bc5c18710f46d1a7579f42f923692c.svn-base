package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.OrderItemFile;

public class OrderItemFileDAO extends AbstractBaseDAO<OrderItemFile> {

	public OrderItemFileDAO() {
		super();
	}
	
	public OrderItemFile findItemFileID(Company company, long cartOrderItemID)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("cartOrderItemID", cartOrderItemID));
		
		return (findAllByCriterion(null, null, null, null, junc)).getList().get(0);
	}
	
}
