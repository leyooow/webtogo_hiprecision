package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.AbstractBaseDAO;
import com.ivant.cms.entity.baseobjects.BaseObject;

public abstract class AbstractBaseDelegate<T extends BaseObject, D extends AbstractBaseDAO<T>> extends AbstractDelegate<T, Long, D> {

	protected AbstractBaseDelegate(D dao) {
		super(dao);
	}
	
	@Override
	public boolean delete(T obj){
		return dao.delete(obj);
	}
	
	@Override
	public List<T> findAll(){
		return dao.findAll();
	}
	
	@Override
	public T find(Long id){
		return dao.find(id);
	}
}
