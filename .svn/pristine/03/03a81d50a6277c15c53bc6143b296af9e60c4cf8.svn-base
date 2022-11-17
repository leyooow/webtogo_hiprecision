package com.ivant.cms.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.transform.ResultTransformer;

import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.interfaces.BaseID;

/** 
 * @author pepe Mar 9, 2004
 * @author Erwin
 * @author Vincent Ray Lim
 * 
 * @param <T> the persistent class, the class must implement {@link BaseID}
 * @param <ID> the type of the primary key of the class, the class must implement {@link Serializable}
 * 
 * @version 2.5.9.1 May 2, 2007
 * @since Jan 17, 2006
 */
public interface DAO<T extends BaseID<ID>, ID extends Serializable> 
{
	
	/**
	 * create and return a new instance of the entity
	 * @return null, if not successful (see log for details)
	 */
	public T getItemInstance();
	
	/**
	 * Get the persistent class that this DAO handles
	 * @return the class object of the persistent class 
	 */
	public Class<T> getEntityClass();
	
	/**
	 * Get the entity name if the name provided is not null or the class name itself
	 * @return the entity name
	 */
	public String getEntityName();
	
	/**
	 * Get the class name.
	 * @return the class name
	 */
	public String getClassName();
	
	/**
	 * Finds an object instance from our database table given the primary key for locating that object.
	 * @param primaryKey the primary key for locating the record in our database.
	 * @return Object creates and returns the found Object. The implementatin should make sure the 
	 * correct subclass is instatiated
	 */
	public T find(ID primaryKey);
	
	/**
	 * Finds an object instance from our database table given the primary key for locating that object
	 * @param primaryKey the primary key for locating the record in our database, 
	 * @param readonly makes the instance in readonly and is not dirty checked.
	 * @return Object creates and returns the found Object. The implementatin should make sure the 
	 * correct subclass is instatiated
	 */
	public T find(ID primaryKey, boolean readonly);

	/**
	 * Load the object instance from our database table given the primary key for locating that object.
	 * You must ensure that the primaryKey exist before calling this method.
	 * @param primaryKey the primary key for locating the record in our database, 
	 * @return the proxy to the item.
	 */
	public T load(ID primaryKey);
	
	/**
	 * Inserts an object into out database table
	 * @param entry the object to be inserted into our database
	 * @return the primary key of the entry that is inserted
	 * @throws NullPointerException when entry is null
	 */
	public ID insert(T entry) throws NullPointerException;
	
	/**
	 * Permanently delete a record from the database.
	 * @param entry the entity to be deleted
	 * @return true if success.
	 * @throws NullPointerException when entry is null
	 * @since 2.5.9
	 */
	public boolean delete(T entry) throws NullPointerException;
	
	/**
	 * Updates a record in our database. The record to be updated is located based on the primary key of
	 * the given object.
	 * @param entry the object to be updated into our database. 
	 *   The object must be loaded using the {@link #find}, {@link #load} 
	 *   or any object that has been retrieved from the database
	 * @throws NullPointerException when entry is null
	 * @return true if success
	 */
	public boolean update(T entry) throws NullPointerException;
	
	/** 
	 * Insert objects by batch
	 * @param objs objects to be inserted
	 */
	public void batchInsert(Collection<T> objs);
	
	/**
	 * update records by batch
	 * @param objs objects to be updated
	 */
	public void batchUpdate(Collection<T> objs);
	
	/**
	 * Refresh the persistent instance.
	 */
	public void refresh(T ...objs);
	
	/**
	 * Retrieves all the records in our table into a <code>java.util.List</code> object.
	 * The keys inside the <code>java.util.List</code> object are the primary keys of the record
	 */
	public List<T> findAll();
	
	/**
	 * Find all item by Hibernate Criterions.
	 * @param clazz the persistent class 
	 * @param page 0 base page number
	 * @param maxResults maximum number of items to be retrive
	 * @param associatedPath names of the join entity. Must have the same length with joinType
	 * @param aliasNames the alias name of the associatedPaths
	 * @param joinType the type of join must be perform. Must have the same length with associationPaths
	 * @param orders order of the items
	 * @param criterions criterias that must match
	 * @see org.hibernate.Criteria
	 * @see org.hibernate.criterion.CriteriaSpecification
	 * @see org.hibernate.criterion.Criterion
	 * @see org.hibernate.criterion.Order
	 * @see org.hibernate.criterion.Restrictions
	 * @return list of the items.
	 * @throws IllegalArgumentException see the message for details.
	 */
	public <E> ObjectList<E> findAllByCriterion(Class<E> clazz, int page, int maxResults,
			String[] associatedPath, String[] aliasNames, int[] joinType, Order[] orders,
			Criterion... criterions);
	
	/**
	 * Find all item by Hibernate Criterions.
	 * @param page 0 base page number
	 * @param maxResults maximum number of items to be retrive
	 * @param associatedPath names of the join entity. Must have the same length with joinType
	 * @param aliasNames TODO
	 * @param joinType the type of join must be perform. Must have the same length with associationPaths
	 * @param orders order of the items
	 * @param criterions criterias that must match
	 * @return list of the items.
	 * @throws IllegalArgumentException see the message for details.
	 */
	public ObjectList<T> findAllByCriterion(int page, int maxResults, String[] associatedPath,
			String[] aliasNames, int[] joinType, Order[] orders, Criterion... criterions);
	
	/**
	 * Find all item by Hibernate Criterions.
	 * @param associatedPath names of the join entity. Must have the same length with joinType
	 * @param aliasNames the alias name of the associatedPaths
	 * @param joinType the type of join must be perform. Must have the same length with associationPaths
	 * @param orders order of the items
	 * @param criterions criterias that must match
	 * @return list of the items.
	 * @throws IllegalArgumentException see the message for details.
	 */
	public ObjectList<T> findAllByCriterion(String associationPaths[], String aliasNames[],
			int joinTypes[], Order[] orders, Criterion... criterions);
	
	/**
	 * <p>Query using Hibernate Criterions in projection mode.</p>
	 * 
	 * <p>
	 * 	<b>Note</b>:
	 * 	This method do not page, does not compute total, and order the result. 
	 * </p>
	 * 
	 * @param projection the hibernate projection
	 * @param criterions criterias that must match
	 * 
	 * @throws IllegalArgumentException see the message for details.
	 * 
	 * @return list of the items depends on the projection.
	 * @since 2.5.6
	 */
	public List<Object> findAllByCriterionProjection(Projection projection, Criterion... criterions);
	
	/**
	 * <p>Query using Hibernate Criterions in projection mode.</p>
	 * 
	 * <p>
	 * 	<b>Note</b>:
	 * 	This method do not page, does not compute total, and order the result. 
	 * </p>
	 * 
	 * @param associationPaths names of the join entity. Must have the same length with joinType
	 * @param joinTypes the type of join must be perform. Must have the same length with associationPaths
	 * @param projection the hibernate projection
	 * @param criterions criterias that must match
	 * 
	 * @throws IllegalArgumentException see the message for details.
	 * 
	 * @return list of the items depends on the projection.
	 * @since 2.5.5
	 * @deprecated
	 */
	@Deprecated
	public List<Object> findAllByCriterionProjection(String associationPaths[], int joinTypes[],
			Projection projection, Criterion... criterions);
	
	/**
	 * <p>Query using Hibernate Criterions in projection mode.</p>
	 * 
	 * <p>
	 * 	<b>Note</b>:
	 * 	This method do not page, does not compute total, and order the result. 
	 * </p>
	 * 
	 * @param associationPaths names of the join entity. Must have the same length with joinType
	 * @param aliasNames the alias of the join entity. Must have the same length with associationPaths
	 * @param joinTypes the type of join must be perform. Must have the same length with associationPaths
	 * @param projection the hibernate projection
	 * @param criterions criterias that must match
	 * 
	 * @throws IllegalArgumentException see the message for details.
	 * 
	 * @return list of the items depends on the projection.
	 * @since 2.5.5
	 */
	public List<Object> findAllByCriterionProjection(String associationPaths[], String aliasNames[],
			int joinTypes[], Projection projection, Criterion... criterions);
	
	/**
	 * Query using Hibernate Criterions in projection mode.
	 * 
	 * @param associationPaths names of the join entity. Must have the same length with joinType
	 * @param aliasNames the alias of the join entity. Must have the same length with associationPaths
	 * @param joinTypes the type of join must be perform. Must have the same length with associationPaths
	 * @param orders array of hibernate order.
	 * @param projection the hibernate projection
	 * @param criterions criterias that must match
	 * 
	 * @return list of the items depends on the projection.
	 * 
	 * @throws IllegalArgumentException see the message for details.
	 * 
	 * @since 2.3
	 */
	public List<Object> findAllByCriterionProjection(String[] associationPaths, String[] aliasNames,
			int[] joinTypes, Order[] orders, Projection projection, Criterion... criterions);
	
	/**
	 * Query using Hibernate Criterions in projection mode.
	 * 
	 * <p>
	 * 	<b>Note</b>:
	 * 	This method do not page, does not compute total, and order the result. 
	 * </p>
	 * 
	 * @param nc The class expected to be returned in a list. For generic purpose.
	 * @param associationPaths names of the join entity. Must have the same length with joinType
	 * @param aliasNames the alias of the join entity. Must have the same length with associationPaths
	 * @param joinTypes the type of join must be perform. Must have the same length with associationPaths
	 * @param projection the hibernate projection
	 * @param resultTransformer the transformer for the result of the projection
	 * @param criterions criterias that must match
	 * 
	 * @throws IllegalArgumentException see the message for details.
	 * 
	 * @return list of the items depends on the projection.
	 * @since 2.5.5
	 */
	public <Y> List<Y> findAllByCriterionProjection(Class<Y> nc, String associationPaths[],
			String aliasNames[], int joinTypes[], Projection projection,
			ResultTransformer resultTransformer, Criterion... criterions);
	
	/**
	 * Query using Hibernate Criterions in projection mode.
	 * <p>
	 * 	<b>Note</b>:
	 * 	This method do not page, and does not compute total. But this will order the result by field. 
	 * </p>
	 * 
	 * @param nc The class expected to be returned in a list. For generic purpose.
	 * @param associationPaths names of the join entity. Must have the same length with joinType
	 * @param aliasNames the alias of the join entity. Must have the same length with associationPaths
	 * @param joinTypes the type of join must be perform. Must have the same length with associationPaths
	 * @param orders the order of the result
	 * @param projection the hibernate projection
	 * @param resultTransformer the transformer for the result of the projection
	 * @param criterions criterias that must match
	 * 
	 * @throws IllegalArgumentException see the message for details.
	 * 
	 * @return list of the items depends on the projection.
	 * @since 2.5.5
	 */
	public <Y> List<Y> findAllByCriterionProjection(Class<Y> nc, String associationPaths[],
			String aliasNames[], int joinTypes[], Order orders[], Projection projection,
			ResultTransformer resultTransformer, Criterion... criterions);
	
	/**
	 * Query using Hibernate Criterions in projection mode.
	 * 
	 * @param nc The class expected to be returned in a list. For generic purpose.
	 * @param pageNumber 0 based page number. (valid value is from 0 and positive numbers only)
	 * @param maxResults number of results to be retrive. (valid value is positive number excluding 0)
	 * @param associationPaths names of the join entity. Must have the same length with joinType
	 * @param aliasNames the alias of the join entity. Must have the same length with associationPaths
	 * @param joinTypes the type of join must be perform. Must have the same length with associationPaths
	 * @param orders the order of the result
	 * @param projection the hibernate projection
	 * @param resultTransformer the transformer for the result of the projection
	 * @param criterions criterias that must match
	 * 
	 * @throws IllegalArgumentException see the message for details.
	 * 
	 * @return list of the items depends on the projection.
	 * @since 2.5.5
	 */
	public <Y> ObjectList<Y> findAllByCriterionProjection(Class<Y> nc, int pageNumber, int maxResults,
			String associationPaths[], String[] aliasNames, int joinTypes[], Order orders[],
			Projection projection, ResultTransformer resultTransformer, Criterion... criterions);
	
	/**
	 * Query using Hibernate Criterions in projection mode.
	 * 
	 * @param nc The class expected to be returned in a list. For generic purpose.
	 * @param hasPagination if to use the pagination method and compute the total. using the pageNumber and maxResult parameter
	 * @param pageNumber 0 based page number. (valid value is from 0 and positive numbers only)
	 * @param maxResults number of results to be retrive. (valid value is positive number excluding 0)
	 * @param associationPaths names of the join entity. Must have the same length with joinType
	 * @param aliasNames the alias of the join entity. Must have the same length with associationPaths
	 * @param joinTypes the type of join must be perform. Must have the same length with associationPaths
	 * @param hasOrder if we will order the result using the orders parameter
	 * @param orders the order of the result
	 * @param projection the hibernate projection
	 * @param resultTransformer the transformer for the result of the projection
	 * @param criterions criterias that must match
	 * @throws IllegalArgumentException see the message for details.
	 * 
	 * @return list of the items depends on the projection.
	 * @since 2.5.5
	 */
	public <Y> ObjectList<Y> findAllByCriterionProjection(Class<Y> nc, boolean hasPagination,
			int pageNumber, int maxResults, String associationPaths[], String[] aliasNames,
			int joinTypes[], boolean hasOrder, Order orders[], Projection projection,
			ResultTransformer resultTransformer, Criterion... criterions);

	/**
	 * Query using Hibernate Criterions in projection mode.
	 * 
	 * @param nc The class expected to be returned in a list. For generic purpose.
	 * @param pageNumber 0 based page number. (valid value is from 0 and positive numbers only)
	 * @param maxResults number of results to be retrive. (valid value is positive number excluding 0)
	 * @param doGetTotalPages if the method will compute the total number of items in database (good for retrieving total number of result when there is paging)
	 * @param associationPaths names of the join entity. Must have the same length with joinType
	 * @param aliasNames the alias of the join entity. Must have the same length with associationPaths
	 * @param joinTypes the type of join must be perform. Must have the same length with associationPaths
	 * @param orders the order of the result
	 * @param projection the hibernate projection
	 * @param resultTransformer the transformer for the result of the projection
	 * @param criterions criterias that must match
	 * 
	 * @throws IllegalArgumentException see the message for details.
	 * 
	 * @return list of the items depends on the projection.
	 * @since 2.5.6
	 */
	public <Y> ObjectList<Y> findAllByCriterionProjection(Class<Y> nc, int pageNumber, int maxResults, boolean doGetTotalPages,
			String associationPaths[], String[] aliasNames, int joinTypes[], Order orders[],
			Projection projection, ResultTransformer resultTransformer, Criterion... criterions);
	
	/**
	 * Query using Hibernate Criterions in projection mode.
	 * 
	 * @param nc The class expected to be returned in a list. For generic purpose.
	 * @param hasPagination if to use the pagination method and compute the total. using the pageNumber and maxResult parameter
	 * @param pageNumber 0 based page number. (valid value is from 0 and positive numbers only)
	 * @param maxResults number of results to be retrive. (valid value is positive number excluding 0)
	 * @param doGetTotalPages if the method will compute the total number of items in database (good for retrieving total number of result when there is paging)
	 * @param associationPaths names of the join entity. Must have the same length with joinType
	 * @param aliasNames the alias of the join entity. Must have the same length with associationPaths
	 * @param joinTypes the type of join must be perform. Must have the same length with associationPaths
	 * @param hasOrder if we will order the result using the orders parameter
	 * @param orders the order of the result
	 * @param projection the hibernate projection
	 * @param resultTransformer the transformer for the result of the projection
	 * @param criterions criterias that must match
	 * 
	 * @throws IllegalArgumentException see the message for details.
	 * 
	 * @return list of the items depends on the projection.
	 * @since 2.5.6
	 */
	public <Y> ObjectList<Y> findAllByCriterionProjection(Class<Y> nc, boolean hasPagination,
			int pageNumber, int maxResults, boolean doGetTotalPages, String associationPaths[],
			String[] aliasNames, int joinTypes[], boolean hasOrder, Order orders[],
			Projection projection, ResultTransformer resultTransformer, Criterion... criterions);
	
}
