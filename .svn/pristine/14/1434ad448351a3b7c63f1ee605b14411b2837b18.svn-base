package com.ivant.cms.db;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;

import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.interfaces.BaseID;
import com.ivant.utils.hibernate.*;

/**
 * This class provides the basic functionality of the hibernate. This class is intended to be
 * extended and pass the persistent class as a generic parameter.
 * 
 * @author Vincent Ray U. Lim
 * 
 * @version 2.6 May 3, 2007
 * 
 * @param <T> The persistent class (the Class object is retrived in the constructor).
 * @param <ID> The type of the id of the persistent class. As rule of hibernate the id of a class must
 * implement {@link Serializable}.
 * 
 * @see DAO
 * @see com.ivant.cms.utility.HibernateUtil
 * @see com.ivant.cms.interfaces.BaseID
 * @see java.io.Serializable
 * 
 * @since June 21, 2006 (in transpacific project)
 */
public abstract class AbstractDAO<T extends BaseID<ID>, ID extends Serializable> implements DAO<T, ID>
{
	private static final Logger log = Logger.getLogger(AbstractDAO.class);
	
	/**
	 * The class passed thru the generic parameter.
	 */
	protected final Class<T> clazz;
	
	/**
	 * Order in listing the items. Can be null value
	 */
	private final Order orders[];
	
	/**
	 * The entity name... for checking purpose
	 */
	protected final String entityName;
	
	/**
	 * The class name, for shortcut purpose... so that you will not call clazz.getName()
	 */
	protected final String className;
	
	/**
	 * the only constructor
	 * @param orders the default order
	 */
	@SuppressWarnings("unchecked")
	public AbstractDAO(Order ... orders)
	{
		// get the first parameter of the generics.
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.orders = orders;
		
		if (clazz == null)
		{
			log.fatal("The extended class " + getClass() + " must set the generic paramters.");
			throw new NullPointerException("clazz is null");
		}
		
		javax.persistence.Entity entity = clazz.getAnnotation(javax.persistence.Entity.class);
		
		if (entity == null)
		{
			throw new IllegalArgumentException("The entity class must be annotated with javax.persistence.Entity!");
		}
		
		if (entity.name() != null && entity.name().length() > 0)
		{
			this.entityName = entity.name();
		}
		else
		{
			this.entityName = clazz.getName();
		}
		this.className = clazz.getName();
	}

	public T getItemInstance()
	{
		T instance = null;
		try
		{
			instance = clazz.newInstance();
		}
		catch (Exception e)
		{
			log.fatal(e, e);
		}
		return instance;
	}

	public final Class<T> getEntityClass()
	{
		return clazz;
	}
	
	public final String getEntityName()
	{
		return entityName;
	}
	
	public final String getClassName()
	{
		return className;
	}

	@SuppressWarnings("unchecked")
	public T find(ID primaryKey)
	{
		if (primaryKey == null)
		{
			return null;
		}
		
		T ret = null;
		try
		{
			Session session = getSession();

			ret = (T) session.get(clazz, primaryKey);
		}
		catch (Exception e)
		{
			log.fatal(e, e);
		}
		finally
		{
			cleanSession();
		}

		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public T find(ID primaryKey, boolean readonly)
	{
		if (primaryKey == null)
		{
			return null;
		}
		
		T ret = null;
		try
		{
			Session session = getSession();

			ret = (T) session.get(clazz, primaryKey);
			session.setReadOnly(ret, readonly);
		}
		catch (Exception e)
		{
			log.fatal(e, e);
		}
		finally
		{
			cleanSession();
		}

		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public T load(ID primaryKey)
	{
		if (primaryKey == null)
		{
			return null;
		}
		
		T ret = null;
		try
		{
			Session session = getSession();
			
			ret = (T) session.load(clazz, primaryKey);
		}
		catch (Exception e)
		{
			log.fatal(e, e);
		}
		finally
		{
			cleanSession();
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	public ID insert(T entry) throws NullPointerException
	{
		if (entry == null)
		{
			throw new NullPointerException("entry is null!");
		}
		ID serialized = null;
		Transaction trans = null;
		try
		{
			Session session = getSession();
			trans = session.beginTransaction();

			serialized = (ID)session.save(entry);
			
			session.flush();

			commitTransaction(trans);
		}
		catch (Exception e)
		{
			rollbackTransaction(trans);
			log.fatal(e, e);
		}
		finally
		{
			cleanSession();
		}
		return serialized;
	}

	public boolean update(T entry)
	{
		if (entry == null)
		{
			throw new NullPointerException("entry is null!");
		}
		boolean ret = false;
		Transaction tran = null;
		try
		{
			Session session = getSession();
			tran = session.beginTransaction();

			session.update(entry);

			session.flush();
			
			session.refresh(entry);
			
			commitTransaction(tran);
			ret = true;
		}
		catch (Exception e)
		{
			rollbackTransaction(tran);
			log.fatal(e, e);
		}
		finally
		{
			cleanSession();
		}
		return ret;
	}

	/**
	 * Permanently delete a record from the database.
	 * @param entry
	 * @return true if success
	 * @since 2.5.9
	 */
	public boolean delete(T entry)
	{
		if (entry == null)
		{
			throw new NullPointerException("entry is null!");
		}
		boolean ret = false;
		Transaction tran = null;
		try
		{
			Session session = getSession();
			tran = session.beginTransaction();

			session.delete(entry);

			session.flush();
			
			commitTransaction(tran);
			ret = true;
		}
		catch (Exception e)
		{
			rollbackTransaction(tran);
			log.fatal(e, e);
		}
		finally
		{
			cleanSession();
		}
		return ret;
	}
	
	/**
	 * get the session from HibernateUtil
	 * @return
	 */
	protected static Session getSession()
	{
		Session session = HibernateUtil.currentSession(); 
		return session;
	}
	
	/**
	 * close the session from HibernateUtil and set local variable session to null
	 */
	protected static void cleanSession()
	{
		HibernateUtil.closeSession();
	}
	
	protected static void commitTransaction(Transaction tran)
	{
		if (tran != null)
		{
			tran.commit();
		}
	}

	protected static void rollbackTransaction(Transaction tran)
	{
		if (tran != null)
		{
			try
			{
				tran.rollback();
			}
			catch (HibernateException e)
			{
				log.fatal("Exception occurred while rollbacking transaction");
				log.fatal(e, e);
			}
		}
	}
	
	public List<T> findAll()
	{
		return findAllByCriterion(null, null, null, null).getList();
	}
	
	/**
	 * Create a criteria using the session
	 *
	 * @param session the hibernate session
	 * @param clazz the persistent class
	 * @param associationPaths a associated path
	 * @param aliasNames the alias name for the associated path
	 * @param joinTypes the join type for the associated path
	 * @param criterions the query criterions
	 * 
	 * @return a criteria created using {@link Session#createCriteria}
	 * 
	 * @throws NullPointerException when session, clazz or null
	 * @throws IllegalArgumentException see message for details
	 */
	protected static <Y> Criteria createCriteria(Session session, Class<Y> clazz, String associationPaths[], String[] aliasNames, int joinTypes[], Criterion... criterions)
	{
		if (associationPaths != null && joinTypes != null && associationPaths.length != joinTypes.length)
		{
			throw new IllegalArgumentException("associatedPaths and joinTypes parameter must have the same length!");
		}
		
		//Transaction tran = session.beginTransaction();
		Criteria criteria = session.createCriteria(clazz, "this");
		
		if (associationPaths != null && joinTypes != null)
		{
			if (aliasNames != null && aliasNames.length != associationPaths.length)
			{
				throw new IllegalArgumentException("aliasNames parameter does not have the same length with associatedPaths!");
			}
			
			String[] nalias = (aliasNames != null) ? aliasNames : associationPaths; 
			
			for (int i = 0; i < associationPaths.length; i++)
			{
				criteria.createAlias(associationPaths[i], nalias[i], joinTypes[i]);
			}
			for (int i = 0; i < associationPaths.length; i++)
			{
				criteria.setFetchMode(associationPaths[i], FetchMode.DEFAULT);
			}
		}
		
		for (Criterion criterion : criterions)
		{
			if (criterion != null)
				criteria.add(criterion);
		}
		
		return criteria;
	}
	
	public List<Object> findAllByCriterionProjection(Projection projection, Criterion... criterions)
	{
		return findAllByCriterionProjection(Object.class, false, -1, -1, false, null, null,
				null, false, null, projection, null, criterions
		).getList();
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public List<Object> findAllByCriterionProjection(String associationPaths[], int joinTypes[],
			Projection projection, Criterion... criterions)
	{
		return findAllByCriterionProjection(Object.class, false, -1, -1, false, associationPaths, null,
				joinTypes, false, null, projection, null, criterions
		).getList();
	}

	public List<Object> findAllByCriterionProjection(String associationPaths[], String aliasNames[], int joinTypes[],
			Projection projection, Criterion... criterions)
	{
		return findAllByCriterionProjection(Object.class, false, -1, -1, false, associationPaths, aliasNames,
				joinTypes, false, null, projection, null, criterions
		).getList();
	}

	public List<Object> findAllByCriterionProjection(String associationPaths[], String[] aliasNames,
			int joinTypes[], Order orders[], Projection projection, Criterion... criterions)
	{
		return findAllByCriterionProjection(Object.class, false, -1, -1, false, associationPaths, null,
				joinTypes, true, orders, projection, null, criterions
		).getList();
	}

	public <Y> List<Y> findAllByCriterionProjection(Class<Y> nc, String associationPaths[], String aliasNames[], int joinTypes[],
			Projection projection, ResultTransformer resultTransformer, Criterion... criterions)
	{
		return findAllByCriterionProjection(nc, false, -1, -1, false, associationPaths, aliasNames,
				joinTypes, false, null, projection, resultTransformer, criterions
		).getList();
	}

	public <Y> List<Y> findAllByCriterionProjection(Class<Y> nc, String associationPaths[], String aliasNames[], int joinTypes[],
			Order orders[], Projection projection, ResultTransformer resultTransformer, Criterion... criterions)
	{
		return findAllByCriterionProjection(nc, false, -1, -1, false, associationPaths, aliasNames,
				joinTypes, true, orders, projection, resultTransformer, criterions
		).getList();
	}
	
	public <Y> ObjectList<Y> findAllByCriterionProjection(Class<Y> nc, int pageNumber, int maxResults,
			String associationPaths[], String[] aliasNames, int joinTypes[], Order orders[],
			Projection projection, ResultTransformer resultTransformer, Criterion... criterions)
	{
		return findAllByCriterionProjection(nc, true, pageNumber, maxResults, true, associationPaths, aliasNames, joinTypes, true, orders, projection, resultTransformer, criterions);
	}

	public <Y> ObjectList<Y> findAllByCriterionProjection(Class<Y> nc, int pageNumber, int maxResults, boolean doGetTotalPages,
			String associationPaths[], String[] aliasNames, int joinTypes[], Order orders[],
			Projection projection, ResultTransformer resultTransformer, Criterion... criterions)
	{
		return findAllByCriterionProjection(nc, true, pageNumber, maxResults, doGetTotalPages, associationPaths, aliasNames, joinTypes, true, orders, projection, resultTransformer, criterions);
	}
	
	public <Y> ObjectList<Y> findAllByCriterionProjection(Class<Y> nc, boolean hasPagination, int pageNumber, int maxResults,
			String associationPaths[], String[] aliasNames, int joinTypes[],
			boolean hasOrder, Order orders[], Projection projection, ResultTransformer resultTransformer, Criterion... criterions)
	{
		return findAllByCriterionProjection(nc, hasPagination, pageNumber, maxResults, true, associationPaths,
				aliasNames, joinTypes, hasOrder, orders, projection, resultTransformer, criterions);
	}

	@SuppressWarnings("unchecked")
	public <Y> ObjectList<Y> findAllByCriterionProjection(Class<Y> nc, boolean hasPagination, int pageNumber, int maxResults,
			boolean doGetTotalPages, String associationPaths[], String aliasNames[], int joinTypes[],
			boolean hasOrder, Order orders[], Projection projection, ResultTransformer resultTransformer, Criterion... criterions)
	{
		ObjectList<Y> list = new ObjectList<Y>();
		Session session = HibernateUtil.currentSession();
		try
		{
			Criteria criteria = createCriteria(session, clazz, associationPaths, aliasNames, joinTypes, criterions);
			
			if (log.isTraceEnabled()) log.trace("check hasOrder: " + hasOrder + ", check hasPagination: " + hasPagination);
			
			if (hasOrder)
			{
				addOrderToCriteria(criteria, orders, false);
			}
			
			if (doGetTotalPages)
			{
				list.setTotal(getTotalItems(criteria));
			}

			if (hasPagination)
			{
				addPagingToCriteria(criteria, pageNumber, maxResults);
			}
			
			criteria.setProjection(projection);
			
			if (resultTransformer != null)
				criteria.setResultTransformer(resultTransformer);
			
			list.setList(criteria.list());
		}
		catch (HibernateException e)
		{
			log.fatal(e.getMessage(), e);
		}
		finally
		{
			cleanSession();
		}
		return list;
	}
	
	public ObjectList<T> findAllByCriterion(int page, int maxResults, String[] associatedPaths, String[] aliasNames, int [] joinTypes, Order[] orders, Criterion... criterions)
	{
		return findAllByCriterion(this.clazz, page, maxResults, associatedPaths, aliasNames, joinTypes, orders, criterions);
	}
	
	public ObjectList<T> findAllByCriterion(String associationPaths[], String aliasNames[], int joinTypes[], Order[] orders, Criterion... criterions)
	{
		return findAllByCriterion(this.clazz, -1, -1, associationPaths, aliasNames, joinTypes, orders, criterions);
	}

	@SuppressWarnings("unchecked")
	public <E> ObjectList<E> findAllByCriterion(Class<E> clazz, int page, int maxResults, String[] associatedPaths, String[] aliasNames, int [] joinTypes, Order[] orders, Criterion... criterions)
	{
		if (associatedPaths != null && joinTypes != null && associatedPaths.length != joinTypes.length)
		{
			throw new IllegalArgumentException("aliases and joinType parameter must have the same length");
		}
		ObjectList<E> ret = new ObjectList<E>();
		Session session = HibernateUtil.currentSession();
		try
		{
			Criteria criteria = createCriteria(session, clazz, associatedPaths, aliasNames, joinTypes, criterions);
			
			ret.setTotal(getTotalItems(criteria));
			
			addOrderToCriteria(criteria, orders, true);
			
			addPagingToCriteria(criteria, page, maxResults);
			ret.setList(criteria.list());
		}
		catch (HibernateException e)
		{
			log.fatal(e.getMessage(), e);
		}
		finally
		{
			cleanSession();
		}
		return ret;
	}
	
	/**
	 * Compute the total items in the criteria using projection
	 * @param criteria
	 * @return
	 */
	protected static int getTotalItems(Criteria criteria)
	{
		criteria.setProjection(Projections.count("id"));
		
		Integer totalCount = (Integer)criteria.uniqueResult();
		
		int total = totalCount.intValue();
		
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return total;
	}
	
	/**
	 * set the order of the criteria
	 * @param criteria
	 * @param otherOrder
	 * @param useDefaultWhenNull
	 */
	protected void addOrderToCriteria(Criteria criteria, Order[] otherOrder, boolean useDefaultWhenNull)
	{
		Order sortby[] = null;
		
		if (otherOrder != null)
		{
			log.debug("sorting table with order specified");
			sortby = otherOrder;
		}
		else if (orders != null && useDefaultWhenNull)
		{
			log.debug("sorting table with the default order");
			sortby = orders;
		}
		
		if (sortby != null && sortby.length > 0)
		{
			for (Order sort : sortby)
			{
				if (sort != null)
				{
					criteria.addOrder(sort);
				}
			}
		}
		else
		{
			log.debug("sorting is not valid.");
		}
	}
	
	/**
	 * Zero base paging for easier computation.
	 * 
	 * 
	 * @param criteria where the paging to be set
	 * @param page zero base 0 for first page
	 * @param maxResults number of items to be fetch
	 */
	protected static void addPagingToCriteria(Criteria criteria, int page, int maxResults)
	{
		if (page >= 0 && maxResults > 0)
		{
			int firstResult = page * maxResults;
			criteria.setFirstResult(firstResult);
		}
		
		if (maxResults > 0)
		{
			criteria.setMaxResults(maxResults);
		}
	}
	
	public void refresh(T ... objs)
	{
		try
		{
			Session session = getSession();

			for (T obj : objs)
			{
				session.refresh(obj);
			}
		}
		catch (Exception e)
		{
			log.fatal(e, e);
		}
		finally
		{
			cleanSession();
		}
	}
	
	public void batchInsert(Collection<T> objs)
	{
		Transaction tran = null;
		try
		{
			Session session = getSession();
			tran = session.beginTransaction();
			
			int i = 0;
			for (T t : objs)
			{
				if (t != null)
				{
					session.save(t);
					
					if ((++i) % 20 == 0)
					{
						i = 0;
						session.flush();
					}
				}
				else
				{
					log.warn("objs collection contains a null value!");
				}
			}
			session.flush();
			
			commitTransaction(tran);
		}
		catch (Exception e)
		{
			rollbackTransaction(tran);
			log.fatal(e, e);
		}
		finally
		{
			cleanSession();
		}
	}
	
	public void batchUpdate(Collection<T> objs)
	{
		Transaction tran = null;
		try
		{
			Session session = getSession();
			tran = session.beginTransaction();
			
			int i = 0;
			for (T t : objs)
			{
				if (t != null)
				{
					session.update(t);
					
					if ((++i) % 20 == 0)
					{
						i = 0;
						session.flush();
					}
				}
				else
				{
					log.fatal("objs collection contains a null value!");
				}
			}
			session.flush();
			
			commitTransaction(tran);
		}
		catch (Exception e)
		{
			rollbackTransaction(tran);
			log.fatal(e, e);
		}
		finally
		{
			cleanSession();
		}
	}
	
	protected static void assertNotNull(Object obj, String message)
	{
		if (obj == null)
		{
			throw new IllegalArgumentException(message);
		}
	}
	
}
