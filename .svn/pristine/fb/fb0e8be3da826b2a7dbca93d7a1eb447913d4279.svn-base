package com.ivant.cms.beans.transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ivant.cms.beans.KuysenClientBean;
import com.ivant.cms.beans.KuysenSalesAreaBean;
import com.ivant.cms.beans.KuysenSalesTransactionBean;
import com.ivant.cms.beans.transformers.base.Transformer;
import com.ivant.cms.entity.KuysenSalesArea;
import com.ivant.cms.entity.KuysenSalesClient;
import com.ivant.cms.entity.KuysenSalesTransaction;
import com.ivant.constants.KuysenSalesConstants;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesTransactionTransformer extends AbstractTransformer<KuysenSalesTransaction> 
											   implements Transformer<KuysenSalesTransaction, KuysenSalesTransactionBean> {

	@Override
	public KuysenSalesTransaction insertBeanAsEntity(KuysenSalesTransactionBean bean, Object parent) {
		
		if(bean.getId() != null) {
			entity = kuysenSalesTransactionDelegate.find(bean.getId());
		} else {
			entity = new KuysenSalesTransaction();
		}
		
		entity.setMember(bean.getMember());
		entity.setClient((KuysenSalesClient) KuysenSalesConstants.TRANSFORMERS.get(KuysenClientTransformer.class).insertBeanAsEntity(bean.getClient(), parent));
		entity.setAdditionalDiscount(bean.getAdditionalDiscount());
		entity.setDiscountedTotal(bean.getDiscountedTotal());
		entity.setTotalDiscount(bean.getTotalDiscount());
		
		List<String> ids = new ArrayList<String>();
		for(UUID uuid : bean.getParentOrderIdList()) {
			ids.add(uuid.toString());
		}
		
		entity.setParentOrderIdList(ids);
		
		if(bean.getId() != null) {
			kuysenSalesTransactionDelegate.update(entity);
			entity = kuysenSalesTransactionDelegate.find(bean.getId());
		} else {
			Long id = kuysenSalesTransactionDelegate.insert(entity);
			entity = kuysenSalesTransactionDelegate.find(id);
		}
		
		for(String key : bean.getOrders().keySet()) {
			KuysenSalesConstants.TRANSFORMERS.get(KuysenSalesAreaTransformer.class).insertBeanAsEntity(bean.getOrders().get(key), entity);
		}
		
		return entity;
	}

	@Override
	public KuysenSalesTransactionBean transform(KuysenSalesTransaction entity) {
		KuysenSalesTransactionBean bean = new KuysenSalesTransactionBean();
		
		bean.setId(entity.getId());
		bean.setMember(entity.getMember());
		bean.setClient((KuysenClientBean) KuysenSalesConstants.TRANSFORMERS.get(KuysenClientTransformer.class).transform(entity.getClient()));
		bean.setAdditionalDiscount(entity.getAdditionalDiscount());
		bean.setDiscountedTotal(entity.getDiscountedTotal());
		bean.setTotalDiscount(entity.getTotalDiscount());
		
		List<UUID> ids = new ArrayList<UUID>();
		for(String uuid : entity.getParentOrderIdList()) {
			ids.add(UUID.fromString(uuid.toString()));
		}
		
		Map<String, KuysenSalesAreaBean> orders = new HashMap<String, KuysenSalesAreaBean>();
		for(String key : entity.getOrders().keySet()) {
			orders.put(key, (KuysenSalesAreaBean) KuysenSalesConstants.TRANSFORMERS.get(KuysenSalesAreaTransformer.class).transform(entity.getOrders().get(key)));
		}
		
		bean.setOrders(orders);
		bean.setParentOrderIdList(ids);
		
		return bean;
	}

}
