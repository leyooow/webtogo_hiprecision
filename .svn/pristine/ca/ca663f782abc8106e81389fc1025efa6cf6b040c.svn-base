package com.ivant.cms.delegate;

import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.KuysenSalesTransactionDAO;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.KuysenSalesTransaction;
import com.ivant.cms.entity.list.ObjectList;

public class KuysenSalesTransactionDelegate extends AbstractBaseDelegate<KuysenSalesTransaction, KuysenSalesTransactionDAO> {
	
	private static KuysenSalesTransactionDelegate instance = new KuysenSalesTransactionDelegate();

	public KuysenSalesTransactionDelegate() {
		super(new KuysenSalesTransactionDAO());
	}
	
	public static KuysenSalesTransactionDelegate getInstance() {
		return instance;
	}
	
	public ObjectList<KuysenSalesTransaction> findAllWithPaging(int resultPerPage, int pageNumber, Order... order) {
		return dao.findAllWithPaging(resultPerPage, pageNumber, order);
	}

}
