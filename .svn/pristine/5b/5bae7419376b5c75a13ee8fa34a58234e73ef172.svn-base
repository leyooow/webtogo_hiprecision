/**
 *
 */
package com.ivant.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

/**
 * Utility class for handling dates like {@link Date} and {@link DateTime}.
 * 
 * @author Edgar S. Dacpano
 */
public class DateUtil
{
	/** default date format using slash(/) as a separator*/
	public final static String DATE_FORMAT_PATTERN_SLASH = "MM/dd/yyyy";
	
	/** default date with time format using slash(/) as a separator*/
	public final static String DATE_TIME_FORMAT_PATTERN_SLASH = "MM/dd/yyyy HH:mm:ss";
	
	/** default date format using dash(-) as a separator*/
	public final static String DATE_FORMAT_PATTERN_DASH = "MM-dd-yyyy";
	
	/**
	 * Get {@link Date} from a string based on the given pattern.
	 * @param stringDate
	 * @param pattern
	 * @return
	 */
	public static final Date getDate(String stringDate, String pattern)
	{
		Date result = null;
		if(StringUtils.isNotEmpty(stringDate))
		{
			try
			{
				result = new SimpleDateFormat(pattern).parse(stringDate);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Get {@link Date} from a string, uses {@link #DATE_FORMAT_PATTERN_SLASH} as its pattern
	 * @param stringDate
	 * @return
	 * @see DateUtil#getDate(String, String)
	 */
	public static final Date getDate(String stringDate)
	{
		return getDate(stringDate, DATE_FORMAT_PATTERN_SLASH);
	}
	
	/**
	 * Use {@link DateTime#toDate()} instead. duh!
	 * @param dateTime
	 * @return
	 */
	public static final Date toDate(DateTime dateTime)
	{
		return dateTime.toDate();
	}
	
	/**
	 * Get {@link DateTime} from a string based on the given pattern.
	 * @param stringDateTime
	 * @param pattern
	 * @return
	 */
	public static final DateTime getDateTime(String stringDateTime, String pattern)
	{
		DateTime result = null;
		if(StringUtils.isNotEmpty(stringDateTime))
		{
			try
			{
				result = DateTimeFormat.forPattern(pattern).parseDateTime(stringDateTime);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Get {@link DateTime} from a string, uses {@link #DATE_FORMAT_PATTERN_SLASH} as its pattern
	 * @param stringDateTime
	 * @return
	 * @see DateUtil#getDateTime(String, String)
	 */
	public static final DateTime getDateTime(String stringDateTime)
	{
		return getDateTime(stringDateTime, DATE_FORMAT_PATTERN_SLASH);
	}
	
	/**
	 * Convert {@link Date} to {@link DateTime}
	 * @param date
	 * @return
	 */
	public static final DateTime toDateTime(Date date)
	{
		return date == null
			? null
			: new DateTime(date); 
	}
	
	/**
	 * Get the days between two {@link DateTime}
	 * @param from
	 * @param to
	 * @return 0 if no difference or an error occured
	 */
	public static final int getDaysBetween(DateTime from, DateTime to)
	{
		int daysBetween = 0;
		try
		{
			if(from != null && to != null)
			{
				daysBetween = Days.daysBetween(from, to).getDays();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return daysBetween;
	}
	
	private DateUtil() {}  // Utility class 
	
}
