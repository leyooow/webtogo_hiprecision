/**
 * 
 */
package com.ivant.utils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

/**
 * @author Edgar S. Dacpano
 *
 */
public class CompanyBaseObjectUtil extends CompanyBaseObject implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Cannot be instantiated */
	private CompanyBaseObjectUtil()
	{
	}

	/**
	 * Converts a list of {@link BaseObject}s to a Map with <br>
	 * K: Long - the id of this {@link BaseObject} <br>
	 * V: T: The object
	 * @param <T>
	 * @param data
	 * @return
	 */
	public static final <T extends CompanyBaseObject> Map<Long, T> toMap(List<T> data)
	{
		final Map<Long, T> map = new LinkedHashMap<Long, T>();
		for(T obj : data)
		{
			map.put(obj.getId(), obj);
		}
		return map;
	}
	
	/**
	 * Converts a list of {@link BaseObject}s to a Map with <br>
	 * K: Long - the id of this {@link BaseObject} <br>
	 * V: U: The return type of the methodName entered, see {@link #getValueByMethodName(BaseObject, String)}
	 * @param data
	 * @param methodName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends CompanyBaseObject, U> Map<Long, U> toMapByMethodName(List<T> data, String methodName)
	{
		final Map<Long, U> map = new LinkedHashMap<Long, U>();
		for(T obj : data)
		{
			map.put(obj.getId(), (U) getValueByMethodName(obj, methodName));
		}
		return map;
	}
	
	/**
	 * see {@link #getValueByMethodName(BaseObject, String)}
	 * @param data
	 * @param methodName - not case-sensitive
	 * @return list of the return type of the method
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends CompanyBaseObject, U> List<U> getValueByMethodName(List<T> data, String methodName)
	{
		final List<U> list = new ArrayList<U>();
		for(T t : data)
		{
			list.add((U) getValueByMethodName(t, methodName));
		}
		return list;
	}
	
	/**
	 * @param data
	 * @param methodName - not case-sensitive
	 * @return the return type of the method
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends CompanyBaseObject, U> U getValueByMethodName(T data, String methodName)
	{
		final Method[] methods = data.getClass().getMethods();
		if(methods != null && methods.length > 0)
		{
			for(int i = 0; i < methods.length; i++)
			{
				if(StringUtils.equalsIgnoreCase(methods[i].getName(), methodName))
				{
					try
					{
						return (U) methods[i].invoke(data);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	
}
