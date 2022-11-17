package com.ivant.cms.beans.transformers;

import com.ivant.cms.beans.KuysenClientBean;
import com.ivant.cms.beans.transformers.base.Transformer;
import com.ivant.cms.entity.KuysenSalesClient;
import com.ivant.cms.entity.KuysenSalesTransaction;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenClientTransformer extends AbstractTransformer<KuysenSalesClient> 
									 implements Transformer<KuysenSalesClient, KuysenClientBean>{

	@Override
	public KuysenSalesClient insertBeanAsEntity(KuysenClientBean bean, Object parent) {

		if(bean.getId() != null) {
			entity = kuysenSalesClientDelegate.find(bean.getId());
		} else {
			entity = new KuysenSalesClient();
		}
		
		entity.setArchitectContact(bean.getArchitectContact());
		entity.setArchitectContractor(bean.getArchitectContractor());
		entity.setArchitectEmail(bean.getArchitectEmail());
		entity.setArchitectName(bean.getArchitectName());
		entity.setClientAddress(bean.getClientAddress());
		entity.setClientEmail(bean.getClientEmail());
		entity.setClientFax(bean.getClientFax());
		entity.setClientMobile(bean.getClientMobile());
		entity.setClientName(bean.getClientName());
		entity.setClientTelephone(bean.getClientTelephone());
		entity.setContactPersonEmail(bean.getContactPersonEmail());
		entity.setContactPersonFax(bean.getContactPersonFax());
		entity.setContactPersonMobile(bean.getContactPersonMobile());
		entity.setContactPersonName(bean.getContactPersonName());
		entity.setContactPersonTelephone(bean.getContactPersonTelephone());
		entity.setDate(bean.getDate());
		entity.setDeliveryAddress(bean.getDeliveryAddress());
		entity.setInteriorDesignerContact(bean.getInteriorDesignerContact());
		entity.setInteriorDesignerEmail(bean.getInteriorDesignerEmail());
		entity.setInteriorDesignerName(bean.getInteriorDesignerName());
		entity.setIsByAdvertisingMaterial(bean.getIsByAdvertisingMaterial());
		entity.setIsOldClient(bean.getIsOldClient());
		entity.setIsReferredBy(bean.getIsReferredBy());
		entity.setRef(bean.getRef());
		entity.setReferredByName(bean.getReferredByName());
		entity.setStatus(bean.getStatus());
		entity.setClientCompany(bean.getClientCompany());
		
		if(bean.getId() != null) {
			kuysenSalesClientDelegate.update(entity);
		} else {
			kuysenSalesClientDelegate.insert(entity);
		}
		
		return entity;
	}

	@Override
	public KuysenClientBean transform(KuysenSalesClient entity) {
		KuysenClientBean bean = new KuysenClientBean();
		
		bean.setId(entity.getId());
		bean.setArchitectContact(entity.getArchitectContact());
		bean.setArchitectContractor(entity.getArchitectContractor());
		bean.setArchitectEmail(entity.getArchitectEmail());
		bean.setArchitectName(entity.getArchitectName());
		bean.setClientAddress(entity.getClientAddress());
		bean.setClientEmail(entity.getClientEmail());
		bean.setClientFax(entity.getClientFax());
		bean.setClientMobile(entity.getClientMobile());
		bean.setClientName(entity.getClientName());
		bean.setClientTelephone(entity.getClientTelephone());
		bean.setContactPersonEmail(entity.getContactPersonEmail());
		bean.setContactPersonFax(entity.getContactPersonFax());
		bean.setContactPersonMobile(entity.getContactPersonMobile());
		bean.setContactPersonName(entity.getContactPersonName());
		bean.setContactPersonTelephone(entity.getContactPersonTelephone());
		bean.setDate(entity.getDate());
		bean.setDeliveryAddress(entity.getDeliveryAddress());
		bean.setInteriorDesignerContact(entity.getInteriorDesignerContact());
		bean.setInteriorDesignerEmail(entity.getInteriorDesignerEmail());
		bean.setInteriorDesignerName(entity.getInteriorDesignerName());
		bean.setIsByAdvertisingMaterial(entity.getIsByAdvertisingMaterial());
		bean.setIsOldClient(entity.getIsOldClient());
		bean.setIsReferredBy(entity.getIsReferredBy());
		bean.setRef(entity.getRef());
		bean.setReferredByName(entity.getReferredByName());
		bean.setStatus(entity.getStatus());
		bean.setClientCompany(entity.getClientCompany());
		
		return bean;
	}

}
