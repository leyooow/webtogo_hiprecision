package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.KuysenSalesClient;
import com.ivant.cms.entity.list.ObjectList;

public class KuysenSalesClientDAO extends AbstractBaseDAO<KuysenSalesClient> {

	public KuysenSalesClientDAO() {
		super();
	} 
	
	public boolean referenceIdExist(String ref) {
		ObjectList<KuysenSalesClient> items = null;
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("ref", ref));
		try {
			items = findAllByCriterion(null, null, null, null, junc);
		}
		catch(Exception e) {}
		
		return items.getSize() > 0 ? true : false;
	}
	
}
