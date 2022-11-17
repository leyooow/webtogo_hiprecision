package com.ivant.cms.delegate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.ivant.cms.interfaces.BaseID;

/**
 * @author pepe
 * @author Vincent Lim
 *
 * All delegate classes will implement this interface
 */
public interface Delegate<T extends BaseID<ID>, ID extends Serializable> {

	/**
	 * Creates an object instance from our database table given the primary key for locating that object
	 * @param primaryKey the primary key for locating the record in our database
	 */
	public T find(ID primaryKey);

	/**
	 * Creates an object instance from our database table given the primary key for locating that object
	 * @param primaryKey the primary key for locating the record in our database
	 * @param readonly makes the instance in readonly and is not dirty checked.
	 */
	public T find(ID primaryKey, boolean readonly);
	
	/**
	 * Load the object instance from our database table given the primary key for locating that object.
	 * You must ensure that the primaryKey exist before calling this method.
	 * @returns a proxy to the object
	 */
	public T load(ID primaryKey);
	
	/**
	 * Retrieves all the records in our table into a <code>java.util.List</code> object.
	 * The keys inside the <code>java.util.List</code> object are the primary keys of the record
	 */
	public List<T> findAll();

	/**
	 * Inserts an object or record into out database table
	 * @param obj the object to be inserted into our database
	 * @return The new id of the object. null when the object is not inserted.
	 */
	public ID insert(T obj);
	
	/**
	 * 
	 */
	public boolean delete(T obj);
	
	/**
	 * Updates a record in our database. The record to be updated is located based on the primary key of the given object.
	 * @param obj
	 */
	public boolean update(T obj);
	
	/**
	 * Refresh the object of the class
	 */
	public void refresh(T ...objs);
	
	/**
	 * insert objects by batch
	 * @param objs objects to be inserted
	 */
	public void batchInsert(T ...objs);
	

	/**
	 * update records by batch
	 * @param objs objects to be updated
	 */
	public void batchUpdate(T ... objs);
	
	/**
	 * insert objects by batch
	 * @param objs objects to be inserted
	 */
	public void batchInsert(Collection<T> objs);
	
	
	/**
	 * update records by batch
	 * @param objs objects to be updated
	 */
	public void batchUpdate(Collection<T> objs);
	
	/**
	 * create and return a new instance of the entity
	 * @return null, if not successful (see log for details)
	 */
	public T getItemInstance();
	
	/**
	 * Get the class of the DAO it handles
	 */
	public Class<T> getEntityClass();
}
