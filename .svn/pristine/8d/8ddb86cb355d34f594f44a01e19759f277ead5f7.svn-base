package com.ivant.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public final class StringUtil {

	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	
	public static final char[] CHARACTERS = {
					'a', 'b', 'c', 'd' , 'e' ,'f' ,'g', 'h', 'i', 'j', 'k', 'l',
					'm', 'n', 'o', 'p' , 'q' ,'r' ,'s', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	public static final char[] ALPHANUMERIC = {
					'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 
					'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
					'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
					't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', 
					'9', '0', };
	
	public static final String DOUBLE_QUOTE = "\\\"";
	public static final String SYMBOL_TRADE_MARK = "\u2122";
	public static final String SYMBOL_REGISTERED = "\u00AE";
	
	/** check if all strings are not empty*/
	public static boolean areNotEmpty(String...strings)
	{
		for(String s : strings)
		{
			if(StringUtils.isEmpty(s))
			{
				return false;
			}
		}
		return true;
	}
	
	/** check if all strings are not empty*/
	public static boolean areEmpty(String...strings)
	{
		for(String s : strings)
		{
			if(StringUtils.isNotEmpty(s))
			{
				return false;
			}
		}
		return true;
	}
	
	public static String generateRandomString(int length)
	{
		Random r = new Random();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++)
		{
			int randomNumber = r.nextInt(CHARACTERS.length);
			sb.append(CHARACTERS[randomNumber]);
		}

		return sb.toString();
	}

	public static String generateRandomString()
	{
		return generateRandomString(8);
	}

	public static String generateRandomPassword(int length)
	{
		Random r = new Random();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++)
		{
			int randomNumber = r.nextInt(ALPHANUMERIC.length);
			sb.append(ALPHANUMERIC[randomNumber]);
		}

		return sb.toString();
	}

	public static String generateRandomPassword()
	{
		return generateRandomPassword(8);
	}
	
	public static final String decodeUTF8(byte[] bytes)
	{
		return new String(bytes, UTF8_CHARSET);
	}
	
	public static final byte[] encodeUTF8(String string)
	{
		return string.getBytes(UTF8_CHARSET);
	}
	
	public static String urlEncode(String value) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, "UTF-8");
	}
	
	public static String encloseWithDoubleQuote(String string) {
		return DOUBLE_QUOTE + string + DOUBLE_QUOTE;
	}
	
	public static String removeLastComma(String str)
	{
		if(str.contains(","))
			return str.substring(0, str.lastIndexOf(","));
		
		return str;
	}
	
	public static String fixDisplay(String string, String columnName) {
		if(string == null)
			return string;
		
		//NEW LINE
		if(string.contains("\n"))
			string = string.replace("\n", "\\n");
		
		//NEW LINE
		if(string.contains("\r"))
			string = string.replace("\r", "\\r");
		
		//SINGLE QUOTE
		if(string.contains("\'"))
			string = string.replace("'", "\\\'");
		
		//DOUBLE QUOTE TO SINGLE QUOTE IF DESCRIPTION
		if("description".equals(columnName) || "short_description".equals(columnName) || "content".equals(columnName))
			string = string.replace("\"", "\'");
		
		//DOUBLE QUOTE
		if(string.contains("\""))
			string = string.replace("\"", "\\\"");
		
		//TRADEMARK
		if(string.contains("&trade;"))
			string = string.replace("&trade;", SYMBOL_TRADE_MARK);
		
		//REGISTERED
		if(string.contains("&reg;"))
			string = string.replace("&reg;", SYMBOL_REGISTERED);
		
		//SPACE
		if(string.contains("&nbsp;"))
			string = string.replace("&nbsp;", " ");
		
		return string;
	}
	
}
