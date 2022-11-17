package com.ivant.cms.beans.transformers.base;

import com.ivant.cms.beans.BaseBean;
import com.ivant.cms.entity.baseobjects.BaseObject;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public interface Transformer<E extends BaseObject, B extends BaseBean> {

	/*
	 * Transform a Bean to Entity
	 */
	public E insertBeanAsEntity(B bean, Object parent);
	
	/*
	 * Transform an Entity to Bean
	 */
	public B transform(E entity);
}
