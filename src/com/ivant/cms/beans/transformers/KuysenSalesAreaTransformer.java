package com.ivant.cms.beans.transformers;

import java.util.ArrayList;
import java.util.List;

import com.ivant.cms.beans.KuysenSalesAreaBean;
import com.ivant.cms.beans.KuysenSalesParentOrderSetBean;
import com.ivant.cms.beans.transformers.base.Transformer;
import com.ivant.cms.delegate.KuysenSalesAreaDelegate;
import com.ivant.cms.entity.KuysenSalesArea;
import com.ivant.cms.entity.KuysenSalesParentOrderSet;
import com.ivant.cms.entity.KuysenSalesTransaction;
import com.ivant.constants.KuysenSalesConstants;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesAreaTransformer extends AbstractTransformer<KuysenSalesArea> 
										implements Transformer<KuysenSalesArea, KuysenSalesAreaBean>{

	@Override
	public KuysenSalesArea insertBeanAsEntity(KuysenSalesAreaBean bean, Object parent) {

		if(bean.getId() != null) {
			entity = kuysenSalesAreaDelegate.find(bean.getId());
		} else {
			entity = new KuysenSalesArea();
		}
		
		entity.setTransaction((KuysenSalesTransaction) parent);
		entity.setArea(bean.getArea());
		entity.setNetTotal(bean.getNetTotal());
		entity.setSubTotal(bean.getSubTotal());
		entity.setTotalDiscount(bean.getTotalDiscount());
		
		if(bean.getId() != null) {
			kuysenSalesAreaDelegate.update(entity);
			entity = kuysenSalesAreaDelegate.find(bean.getId());
		} else {
			Long id = kuysenSalesAreaDelegate.insert(entity);
			entity = kuysenSalesAreaDelegate.find(id);
		}
		
		for(KuysenSalesParentOrderSetBean pordersetbean : bean.getOrders()) {
			KuysenSalesConstants.TRANSFORMERS.get(KuysenSalesParentOrderSetTransformer.class).insertBeanAsEntity(pordersetbean, entity);
		}
		
		return entity;
	}

	@Override
	public KuysenSalesAreaBean transform(KuysenSalesArea entity) {
		KuysenSalesAreaBean bean = new KuysenSalesAreaBean();
		
		bean.setId(entity.getId());
		bean.setArea(entity.getArea());
		bean.setNetTotal(entity.getNetTotal());
		bean.setSubTotal(entity.getSubTotal());
		bean.setTotalDiscount(entity.getTotalDiscount());
		
		List<KuysenSalesParentOrderSetBean> pordersetlist = new ArrayList<KuysenSalesParentOrderSetBean>();
		for(KuysenSalesParentOrderSet porderset : entity.getKuysenSalesParentOrderSets()) {
			KuysenSalesParentOrderSetBean pordersetbean = (KuysenSalesParentOrderSetBean) KuysenSalesConstants.TRANSFORMERS.get(KuysenSalesParentOrderSetTransformer.class).transform(porderset);
			pordersetlist.add(pordersetbean);
		}
		
		bean.setOrders(pordersetlist);
		
		return bean;
	}

}
