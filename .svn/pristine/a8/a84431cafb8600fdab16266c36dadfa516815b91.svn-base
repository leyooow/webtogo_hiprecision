package com.ivant.cms.delegate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ivant.cms.db.DAO;
import com.ivant.cms.interfaces.BaseID;

/**
 * We build a face for daos
 * 
 * @author Vincent Lim
 * @version 2.0.1 December 28, 2006
 *
 */
public abstract class AbstractDelegate<T extends BaseID<ID>, ID extends Serializable, D extends DAO<T, ID>> implements Delegate<T, ID>
{
	protected D dao;

	protected AbstractDelegate(D dao)
	{
		if (dao == null)
		{
			throw new NullPointerException("DAO is null!");
		}
		this.dao = dao;
	}

	public boolean delete(T obj)
	{
		return dao.delete(obj);
	}
	
	public T find(ID primaryKey)
	{
		return dao.find(primaryKey);
	}

	public T find(ID primaryKey, boolean readonly)
	{
		return dao.find(primaryKey, readonly);
	}

	public T load(ID primaryKey)
	{
		return dao.load(primaryKey);
	}

	public List<T> findAll()
	{
		return dao.findAll();
	}
	
	public ID insert(T obj)
	{
		return dao.insert(obj);
	}

	public boolean update(T obj)
	{
		return dao.update(obj);
	}
	
	public void refresh(T ...objs)
	{
		dao.refresh(objs);
	}
	
	public void batchInsert(T ...objs)
	{
		dao.batchInsert(Arrays.asList(objs));
	}
	
	public void batchUpdate(T ...objs)
	{
		dao.batchUpdate(Arrays.asList(objs));
	}

	public void batchInsert(Collection<T> objs)
	{
		dao.batchInsert(objs);
	}
	
	public void batchUpdate(Collection<T> objs)
	{
		dao.batchUpdate(objs);
	}

	public T getItemInstance()
	{
		return dao.getItemInstance();
	}
	
	public Class<T> getEntityClass()
	{
		return dao.getEntityClass();
	}
}
