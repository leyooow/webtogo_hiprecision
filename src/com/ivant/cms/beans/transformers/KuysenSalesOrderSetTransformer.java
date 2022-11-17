package com.ivant.cms.beans.transformers;

import com.ivant.cms.beans.KuysenSalesOrderSetBean;
import com.ivant.cms.beans.transformers.base.Transformer;
import com.ivant.cms.entity.KuysenSalesOrderSet;
import com.ivant.cms.entity.KuysenSalesParentOrderSet;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesOrderSetTransformer extends AbstractTransformer<KuysenSalesOrderSet>
											implements Transformer<KuysenSalesOrderSet, KuysenSalesOrderSetBean>{

	@Override
	public KuysenSalesOrderSet insertBeanAsEntity(KuysenSalesOrderSetBean bean, Object parent) {

		if(bean.getId() != null) {
			entity = kuysenSalesOrderSetDelegate.find(bean.getId());
		} else {
			entity = new KuysenSalesOrderSet();
		}
		
		entity.setKuysenSalesParentOrderSet((KuysenSalesParentOrderSet) parent);
		entity.setIsIncluded(bean.getIsIncluded());
		entity.setItem(bean.getItem());
		
		if(bean.getId() != null) {
			kuysenSalesOrderSetDelegate.update(entity);
			entity = kuysenSalesOrderSetDelegate.find(bean.getId());
		} else {
			Long id = kuysenSalesOrderSetDelegate.insert(entity);
			entity = kuysenSalesOrderSetDelegate.find(id);
		}

		return entity;
	}

	@Override
	public KuysenSalesOrderSetBean transform(KuysenSalesOrderSet entity) {
		return new KuysenSalesOrderSetBean(entity.getId(), entity.getItem(), entity.getIsIncluded());
	}

}
