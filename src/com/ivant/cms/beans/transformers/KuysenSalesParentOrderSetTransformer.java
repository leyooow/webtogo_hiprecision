package com.ivant.cms.beans.transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ivant.cms.beans.KuysenSalesOptionalSetBean;
import com.ivant.cms.beans.KuysenSalesOrderSetBean;
import com.ivant.cms.beans.KuysenSalesParentOrderSetBean;
import com.ivant.cms.beans.transformers.base.Transformer;
import com.ivant.cms.entity.KuysenSalesArea;
import com.ivant.cms.entity.KuysenSalesOptionalSet;
import com.ivant.cms.entity.KuysenSalesOrderSet;
import com.ivant.cms.entity.KuysenSalesParentOrderSet;
import com.ivant.constants.KuysenSalesConstants;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesParentOrderSetTransformer extends AbstractTransformer<KuysenSalesParentOrderSet>
												  implements Transformer<KuysenSalesParentOrderSet, KuysenSalesParentOrderSetBean>{

	@Override
	public KuysenSalesParentOrderSet insertBeanAsEntity(KuysenSalesParentOrderSetBean bean, Object parent) {

		if(bean.getId() != null) {
			entity = kuysenSalesParentOrderSetDelegate.find(bean.getId());
		} else {
			entity = new KuysenSalesParentOrderSet();
		}
		
		entity.setArea((KuysenSalesArea) parent);
		entity.setDiscount(bean.getDiscount());
		entity.setIsPackage(bean.getIsPackage());
		entity.setItem(bean.getItem());
		entity.setNetPrice(bean.getNetPrice());
		entity.setParentId(bean.getParentId().toString());
		entity.setQuantity(bean.getQuantity());
		entity.setSubDiscount(bean.getSubDiscount());
		entity.setTotal(bean.getTotal());
		entity.setTotalDiscount(bean.getTotalDiscount());
		
		if(bean.getId() != null) {
			kuysenSalesParentOrderSetDelegate.update(entity);
			entity = kuysenSalesParentOrderSetDelegate.find(bean.getId());
		} else {
			Long id = kuysenSalesParentOrderSetDelegate.insert(entity);
			entity = kuysenSalesParentOrderSetDelegate.find(id);
		}
		
		for(KuysenSalesOrderSetBean ordersetbean : bean.getSpecifications()) {
			KuysenSalesConstants.TRANSFORMERS.get(KuysenSalesOrderSetTransformer.class).insertBeanAsEntity(ordersetbean, entity);
		}
		
		for(KuysenSalesOptionalSetBean optionalsetbean : bean.getOptionals()) {
			KuysenSalesConstants.TRANSFORMERS.get(KuysenSalesOptionalSetTransformer.class).insertBeanAsEntity(optionalsetbean, entity);
		}
		
		return entity;
	}

	@Override
	public KuysenSalesParentOrderSetBean transform(KuysenSalesParentOrderSet entity) {
		KuysenSalesParentOrderSetBean bean = new KuysenSalesParentOrderSetBean();
		
		bean.setId(entity.getId());
		bean.setDiscount(entity.getDiscount());
		bean.setIsPackage(entity.getIsPackage());
		bean.setItem(entity.getItem());
		bean.setNetPrice(entity.getNetPrice());
		bean.setParentId(UUID.fromString(entity.getParentId()));
		bean.setQuantity(entity.getQuantity());
		bean.setSubDiscount(entity.getSubDiscount());
		bean.setTotal(entity.getTotal());
		bean.setTotalDiscount(entity.getTotalDiscount());
		
		List<KuysenSalesOrderSetBean> ordersetbeanlist = new ArrayList<KuysenSalesOrderSetBean>();
		for(KuysenSalesOrderSet orderset : entity.getSpecifications()) {
			KuysenSalesOrderSetBean ordersetbean = (KuysenSalesOrderSetBean) KuysenSalesConstants.TRANSFORMERS.get(KuysenSalesOrderSetTransformer.class).transform(orderset);
			ordersetbeanlist.add(ordersetbean);
		}
		bean.setSpecifications(ordersetbeanlist);
		
		List<KuysenSalesOptionalSetBean> optionalsetbeanlist = new ArrayList<KuysenSalesOptionalSetBean>();
		for(KuysenSalesOptionalSet optionalset : entity.getOptionals()) {
			KuysenSalesOptionalSetBean optionalsetbean = (KuysenSalesOptionalSetBean) KuysenSalesConstants.TRANSFORMERS.get(KuysenSalesOptionalSetTransformer.class).transform(optionalset);
			optionalsetbeanlist.add(optionalsetbean);
		}
		bean.setOptionals(optionalsetbeanlist);
		
		return bean;
	}

}
