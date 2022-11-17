/**
 * 
 */
package com.ivant.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Edgar S. Dacpano
 *
 */
public class NumberUtil
{
	public static final String DEFAULT_NUMBER_PATTERN = "##,###,###.##";
	/**
	* @param Strips all non-digit number in a String 
	* @return an int number
	*/
	public static final int trimToIntegerNumber(String intString) 
	{
		return Integer.parseInt(intString.replaceAll("[^0-9]", ""));
	}
	
	
	/**
	* @param Removes all non-digit/non-decimal number in a String. 
	* @return a decimal number in String value.
	*/
	public static final String toDecimalNumber(String decimalString)
	{
		decimalString = decimalString.replaceAll("[^0-9.]", "");
		decimalString = decimalString.replaceAll("(\\.)\\1+", "$1");
		String[] split = decimalString.split("\\.");
		if(decimalString.length() > 1){
			int j = 0;
			decimalString = "";
			for (String string1 : split) {
				string1 = string1.replaceAll("(\\.)", "");
				if(j == 0){
					string1 += ".";
				}
				decimalString += string1;
				j++;
			}
		}
		if(decimalString.endsWith(".")){
			decimalString = decimalString.substring(0, decimalString.length()-1);
		}
		return decimalString;
	}
	
	public static final boolean isIntegerNumber(String number)
	{
		try 
		{
			Integer.parseInt(number);
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
	
	public static final boolean isDecimalNumber(String number)
	{
		try 
		{
			Double.parseDouble(number);
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
	
	/**
	 * Get the string value of the number. Default value is "0".
	 * 
	 * @param number the number
	 * 
	 * @return the string value
	 */
	public static final <T extends Number> String getStringValue(T number)
	{
		return getStringValue(number, "0");
	}
	
	/**
	 * Get the string value of the number. Use default value if number is null.
	 * 
	 * @param number the number
	 * @param defaultValue the default value
	 * 
	 * @return the string value
	 */
	public static final <T extends Number> String getStringValue(T number, String defaultValue)
	{
		if(number != null)
		{
			return number.toString();
		}
		else
		{
			return defaultValue;
		}
	}
	
	public static final<T extends Number> String format(T value)
	{
		return format(value, null);
	}
	
	public static final<T extends Number> String format(T value, String pattern)
	{
		DecimalFormat df;
		if(StringUtils.isEmpty(pattern))
			df = new DecimalFormat(DEFAULT_NUMBER_PATTERN);
		else
			df = new DecimalFormat(pattern);
		if(value == null)
			return df.format(0);
		return df.format(value);
	}
	
	/**
	 * Generate random BigDecimal number/s
	 * @param listSize the size of list to be returned
	 * @param minValue the minimum/lowest value
	 * @param maxValue the maximun/highest value
	 * @return a list of BigDecimal numbers, <b>null</b> if <i>minValue</i> is greater or equal to <i>maxValue</i>
	 */
	public static final List<BigDecimal> generateBigDecimalNumber
		(int listSize, BigDecimal minValue, BigDecimal maxValue)
	{
		if(minValue.compareTo(maxValue) <= 0)
		{
			List<BigDecimal> numbers = new ArrayList<BigDecimal>();
			for (int i = 0; i < listSize; i++) 
			{
				BigDecimal number = minValue.add(new BigDecimal(Math.random())
					.multiply((maxValue.subtract(minValue))));
				numbers.add(number);
			}
			return numbers;
		} 
		else 
		{
			return null;
		}
	}
}
