package com.ivant.cms.beans.transformers;

import com.ivant.cms.beans.KuysenSalesOptionalSetBean;
import com.ivant.cms.beans.transformers.base.Transformer;
import com.ivant.cms.entity.KuysenSalesOptionalSet;
import com.ivant.cms.entity.KuysenSalesParentOrderSet;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesOptionalSetTransformer extends AbstractTransformer<KuysenSalesOptionalSet>
											   implements Transformer<KuysenSalesOptionalSet, KuysenSalesOptionalSetBean>{

	@Override
	public KuysenSalesOptionalSet insertBeanAsEntity(KuysenSalesOptionalSetBean bean, Object parent) {

		if(bean.getId() != null) {
			entity = kuysenSalesOptionalSetDelegate.find(bean.getId());
		} else {
			entity = new KuysenSalesOptionalSet();
		}
		
		entity.setKuysenSalesParentOrderSet((KuysenSalesParentOrderSet) parent);
		entity.setItem(bean.getItem());
		entity.setQuantity(bean.getQuantity());
		entity.setTotal(bean.getTotal());
		
		kuysenSalesOptionalSetDelegate.insert(entity);
		
		if(bean.getId() != null) {
			kuysenSalesOptionalSetDelegate.update(entity);
			entity = kuysenSalesOptionalSetDelegate.find(bean.getId());
		} else {
			Long id = kuysenSalesOptionalSetDelegate.insert(entity);
			entity = kuysenSalesOptionalSetDelegate.find(id);
		}
		
		return entity;
	}

	@Override
	public KuysenSalesOptionalSetBean transform(KuysenSalesOptionalSet entity) {
		KuysenSalesOptionalSetBean bean = new KuysenSalesOptionalSetBean(entity.getItem());
		
		bean.setId(entity.getId());
		bean.setQuantity(entity.getQuantity());
		bean.setTotal(entity.getTotal());
		
		return bean;
	}

}
