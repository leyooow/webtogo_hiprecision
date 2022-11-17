package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.KuysenSalesTransaction;
import com.ivant.cms.entity.list.ObjectList;

public class KuysenSalesTransactionDAO extends AbstractBaseDAO<KuysenSalesTransaction> {

	public KuysenSalesTransactionDAO() {
		super();
	} 
	
	public ObjectList<KuysenSalesTransaction> findAllWithPaging(int resultPerPage, int pageNumber, Order[] order) {			
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				order, junc);
	}
	
}
