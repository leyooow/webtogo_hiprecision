/**
 * 
 */
package com.ivant.utils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.ivant.cms.entity.baseobjects.BaseObject;

/**
 * Utility for using java reflection
 * @author Edgar S. Dacpano
 */
public class ReflectionUtil
{
	public static final String ENTITY_ID_SETTER_METHOD = "setId";
	
	public static final String PRIM_BOOLEAN_PREFIX = "is";
	
	public static final String GET_PREFIX = "get";
	
	public static final String SET_PREFIX = "set";
	
	/** Cannot be instantiated */
	private ReflectionUtil()
	{
	}
	
	/**
	 * Converts a list of {@link BaseObject}s to a Map with <br>
	 * K: Long - the id of this {@link BaseObject} <br>
	 * V: T: The object
	 * 
	 * @param <T>
	 * @param data
	 * @return
	 */
	public static final <T extends BaseObject> Map<Long, T> toMap(List<T> data)
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
	 * V: U: The return type of the methodName entered, see
	 * {@link #getValueByMethodName(BaseObject, String)}
	 * 
	 * @param data
	 * @param methodName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends BaseObject, U> Map<Long, U> toMapByMethodName(List<T> data, String methodName)
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
	 * 
	 * @param data
	 * @param methodName - not case-sensitive
	 * @return list of the return type of the method
	 */
	@SuppressWarnings("unchecked")
	public static final <T, U> List<U> getValueByMethodName(List<T> data, String methodName)
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
	public static final <T, U> U getValueByMethodName(T data, String methodName)
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
	
	///** Converts a POJO or Primitive Object value to a readable String. Defaults to {@link #toString()}.<br>
	// * i.e. - Boolean - false will be converted to "No", true to "Yes" */
	/*public static final <T, U> String convertToReadableValue(T data, String methodName)
	{
		final U value = getValueByMethodName(data, methodName);
		
		String readableValue = null;
		if(value != null)
		{
			if(value instanceof Boolean)
			{
				if((Boolean) value)
				{
					readableValue = "Yes";
				}
				else
				{
					readableValue = "No";
				}
			}
			// add more to POJOs or Primitive Objects here i.e. Long, Char, etc...
			else
			{
				readableValue = value.toString();
			}
		}
		
		return StringUtils.trimToEmpty(readableValue);
	}*/
	
	/**
	 * Copies a T object to another T object
	 * @param from
	 * @param to
	 * @return true if successfully copied without error(s)
	 */
	public static final <T> boolean copyValues(T from, T to)
	{
		try
		{
			/**
			 * K: Getter method names (from)
			 * V: Setter method names (to)
			 */
			final Map<String, String> methodsMap = new HashMap<String, String>();
			
			/** Get the auditables of parent classes */
			Class<?> parentClass = null;
			do
			{
				if(parentClass == null)
				{
					parentClass = from.getClass();
				}
				if(parentClass != null)
				{
					final Method[] parentMethods = parentClass.getDeclaredMethods();
					if(parentMethods.length > 0)
					{
						for(Method method : parentMethods)
						{
							if(isGetterMethod(from, method))
							{
								final String getterName = method.getName();
								
								String setterName = null;
								if(StringUtils.equalsIgnoreCase(StringUtils.substring(getterName, 0, 3), "get"))
								{
									setterName = "set" + getterName.substring(3);
								}
								else if(StringUtils.equalsIgnoreCase(StringUtils.substring(getterName, 0, 2), "is"))
								{
									setterName = "set" + getterName.substring(2);
								}
								if(setterName != null)
								{
									methodsMap.put(getterName, setterName);
								}
							}
						}
					}
					parentClass = parentClass.getSuperclass();
				}
			}
			while(parentClass != null);
			
			if(methodsMap != null && !methodsMap.isEmpty())
			{
				for(Entry<String, String> entry : methodsMap.entrySet())
				{
					final String getterName = entry.getKey();
					final String setterName = entry.getValue();
					
					if(!ENTITY_ID_SETTER_METHOD.equalsIgnoreCase(setterName))
					{
						/** Get the auditables of parent classes */
						Class<?> toParentClass = null;
						do
						{
							if(toParentClass == null)
							{
								toParentClass = to.getClass();
							}
							if(toParentClass != null)
							{
								final Method[] parentMethods = toParentClass.getDeclaredMethods();
								if(parentMethods.length > 0)
								{
									for(Method method : parentMethods)
									{
										if(isSetterMethod(to, method))
										{
											final String methodName = method.getName();
											if(setterName.equalsIgnoreCase(methodName))
											{
												try
												{
													method.invoke(to, getValueByMethodName(from, getterName));
												}
												catch(Exception e)
												{
													e.printStackTrace();
												}
											}
										}
									}
								}
							}
							toParentClass = toParentClass.getSuperclass();
						}
						while(toParentClass != null);
					}
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static final <T> String printAllGetterValues(T entry)
	{
		final StringBuilder sb = new StringBuilder();
		if(entry != null)
		{
			try
			{
				for(PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(entry.getClass()).getPropertyDescriptors())
				{
					final Method method = propertyDescriptor.getReadMethod();
					if(method != null)
					{
						sb.append(method.getName())
						.append(" = ")
						.append(method.invoke(entry))
						.append("\n");
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
		return sb.toString();
	}
	
	public static final <T> String printAllDeclaredFieldsToGetterMethod(T entry)
	{
		final StringBuilder sb = new StringBuilder();
		if(entry != null)
		{
			try
			{
				final Field[] fields = entry.getClass().getDeclaredFields();
				if(fields.length > 0)
				{
					for(Field field : fields)
					{
						final String fieldName = field.getName();
						final StringBuilder columnName = new StringBuilder();
						for(int i = 0; i < field.getName().length(); i++)
						{
							final Character charAt = fieldName.charAt(i) ;
							if(Character.isUpperCase(charAt))
							{
								columnName.append("_");
							}
							columnName.append(Character.toLowerCase(charAt));
						}
						
						final Class<?> type = field.getType();
						final String typeSimpleName = ClassUtils.primitiveToWrapper(type).getSimpleName();
						
						sb.append("\t@Basic" + "\n");
						sb.append("\t@Column(name = \"" + columnName.toString() + "\")\n");
						sb.append("\tpublic " + typeSimpleName + " get" + WordUtils.capitalize(fieldName) + "()\n");
						sb.append("\t{" + "\n");
						
						if(typeSimpleName.equals("Boolean"))
						{
							sb.append("\t\tif(" + fieldName + " == null)\n");
							sb.append("\t\t{" + "\n");
							sb.append("\t\t\tthis." + fieldName + " = Boolean.FALSE;\n");
							sb.append("\t\t}" + "\n");
						}
						else if(typeSimpleName.equals("Integer"))
						{
							sb.append("\t\tif(" + fieldName + " == null)\n");
							sb.append("\t\t{" + "\n");
							sb.append("\t\t\tthis." + fieldName + " = " + getValueByMethodName(entry, "get" + fieldName).toString() + ";\n");
							sb.append("\t\t}" + "\n");
						}
						else if(typeSimpleName.equals("Double"))
						{
							sb.append("\t\tif(" + fieldName + " == null)\n");
							sb.append("\t\t{" + "\n");
							sb.append("\t\t\tthis." + fieldName + " = " + getValueByMethodName(entry, "get" + fieldName).toString() +"D;\n");
							sb.append("\t\t}" + "\n");
						}
						sb.append("\t\treturn " + fieldName + ";\n");
						sb.append("\t}" + "\n");
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString(); 
	}
	
	public static final <T> String printAllDeclaredFieldsToSetterMethod(T entry)
	{
		final StringBuilder sb = new StringBuilder();
		if(entry != null)
		{
			try
			{
				final Field[] fields = entry.getClass().getDeclaredFields();
				if(fields.length > 0)
				{
					for(Field field : fields)
					{
						final String fieldName = field.getName();
						final Class<?> type = field.getType();
						final String typeSimpleName = ClassUtils.primitiveToWrapper(type).getSimpleName();
						
						sb.append("\tpublic void set" + WordUtils.capitalize(fieldName) + "(" + typeSimpleName + " " + fieldName + ")\n");
						sb.append("\t{" + "\n");
						sb.append("\t\tthis." + fieldName + " = " + fieldName + ";\n");
						sb.append("\t}" + "\n");
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static final <T> boolean isGetterMethod(T entry, Method method)
	{
		try
		{
			if(method != null)
			{
				final String methodName = method.getName();
				final String returnType = method.getReturnType().toString();
				
				if(!StringUtils.containsIgnoreCase(returnType, "void") && 
					(StringUtils.equalsIgnoreCase(StringUtils.substring(methodName, 0, 3), GET_PREFIX)
						|| StringUtils.equalsIgnoreCase(StringUtils.substring(methodName, 0, 2), PRIM_BOOLEAN_PREFIX)))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static final <T> boolean isSetterMethod(T entry, Method method)
	{
		try
		{
			if(method != null)
			{
				final String methodName = method.getName();
				final String returnType = method.getReturnType().toString();
				
				if(StringUtils.containsIgnoreCase(returnType, "void") && 
					StringUtils.equalsIgnoreCase(StringUtils.substring(methodName, 0, 3), SET_PREFIX))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
}
