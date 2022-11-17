package com.ivant.cms.db;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.baseobjects.BaseObject;

public abstract class AbstractBaseDAO<T extends BaseObject> extends AbstractDAO<T, Long> {
	
	private static final Logger log = Logger.getLogger(AbstractBaseDAO.class);
	
	public AbstractBaseDAO(Order ... orders){
		super(orders);
	}
	
	/**
	 * set the isValid = 0
	 */
	@Override
	public boolean delete(T entry){
		final StringBuilder deleteHQL = new StringBuilder(100);
		
		//deleteHQL.append("update ").append(className).append(" set isValid = 0 where id = :id");
		deleteHQL.append("delete ").append(className).append(" where id = :id");
		
		final String hql = deleteHQL.toString();
		
		if (log.isDebugEnabled()) log.debug("Delete HQL: " + hql);

		boolean ret = false;
		Transaction tran = null;
		try {
			Session session = getSession();
			tran = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setParameter("id", entry.getId());
			
			int totalUpdated = query.executeUpdate();

			session.flush();
			commitTransaction(tran);
			
			ret = totalUpdated > 0;
			
			if (log.isDebugEnabled()) log.debug("Total deleted: " + totalUpdated);
			
		} catch (HibernateException e) {
			rollbackTransaction(tran);
			log.fatal(e, e);
		} finally {
			cleanSession();
		}
		
		return ret;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T find(Long primaryKey){
		if (primaryKey == null) {
			return null;
		}
		
		T ret = null;
		try {
			Session session = getSession();

			ret = (T) session.get(clazz, primaryKey);
		} catch (Exception e) {
			log.fatal(e, e);
		} finally {
			cleanSession();
		}

		if(ret != null && ret.getIsValid().equals(Boolean.FALSE)){
			ret = null;
		}
		
		return ret;
	}
	
	
	@Override
	public List<T> findAll() {
		return findAllByCriterion(null, null, null, null, Restrictions.eq("isValid", Boolean.TRUE)).getList();
	}
}
