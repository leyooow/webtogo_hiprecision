package com.ivant.cms.interfaces;

import java.io.Serializable;

/**
 * interface for objects that will have its own unique identification number as required for persistence
 * @author Administrator
 *
 * @param <T>
 */
public interface BaseID<T extends Serializable> {
	/**
	 * get the object unique id
	 * @return
	 */
	public T getId();
	/**
	 * set the objects unique id
	 * @param id
	 */
	public void setId(T id);
}
