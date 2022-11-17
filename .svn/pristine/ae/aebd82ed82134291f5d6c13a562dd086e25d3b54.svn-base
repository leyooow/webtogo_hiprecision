package com.ivant.cms.beans.transformers;

import com.ivant.cms.delegate.KuysenSalesAreaDelegate;
import com.ivant.cms.delegate.KuysenSalesClientDelegate;
import com.ivant.cms.delegate.KuysenSalesOptionalSetDelegate;
import com.ivant.cms.delegate.KuysenSalesOrderSetDelegate;
import com.ivant.cms.delegate.KuysenSalesParentOrderSetDelegate;
import com.ivant.cms.delegate.KuysenSalesTransactionDelegate;
import com.ivant.cms.entity.baseobjects.BaseObject;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public abstract class AbstractTransformer<E extends BaseObject> {
	
	protected E entity;

	protected static KuysenSalesTransactionDelegate kuysenSalesTransactionDelegate = KuysenSalesTransactionDelegate.getInstance();
	protected static KuysenSalesAreaDelegate kuysenSalesAreaDelegate = KuysenSalesAreaDelegate.getInstance();
	protected static KuysenSalesParentOrderSetDelegate kuysenSalesParentOrderSetDelegate = KuysenSalesParentOrderSetDelegate.getInstance();
	protected static KuysenSalesOrderSetDelegate kuysenSalesOrderSetDelegate = KuysenSalesOrderSetDelegate.getInstance();
	protected static KuysenSalesOptionalSetDelegate kuysenSalesOptionalSetDelegate = KuysenSalesOptionalSetDelegate.getInstance();
	protected static KuysenSalesClientDelegate kuysenSalesClientDelegate = KuysenSalesClientDelegate.getInstance();
}
