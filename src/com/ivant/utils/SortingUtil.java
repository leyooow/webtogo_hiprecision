package com.ivant.utils;

import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.Logger;

import com.ivant.cms.entity.baseobjects.BaseObject;

/**
* @author Kevin Roy K. Chua
*
* @version 1.0, Apr 15, 2009
* @since 1.0, Apr 15, 2009
*
*/
public class SortingUtil
{
	private static final Logger logger = Logger.getLogger(SortingUtil.class);
	
	public static void sortBaseObject(String property, boolean isAscending, List<? extends BaseObject> data)
	{
		boolean sort = true;
		if(isAscending)
		{
			BeanComparator beanComparator = new BeanComparator(property,new NullComparator(false));
			
			for(BaseObject obj: data) {
				if(obj.getId() == null || obj.getIsValid() == null || obj.getUpdatedOn() == null || obj.getCreatedOn() == null) {
					sort = false;
					break;
				}
			}	
			
			if(sort) {
				Collections.sort(data, beanComparator);
			}
		}
		else
		{
			BeanComparator beanComparator = new BeanComparator(property, new ReverseComparator(new ComparableComparator()));
			Collections.sort(data, beanComparator);
		}
	}
	
	public static void sortBean(String property, boolean isAscending, List<?> data)
	{
		if(isAscending)
		{
			BeanComparator beanComparator = new BeanComparator(property);
			Collections.sort(data, beanComparator);
		}
		else
		{
			BeanComparator beanComparator = new BeanComparator(property, new ReverseComparator(new ComparableComparator()));
			Collections.sort(data, beanComparator);
		}
	}
}