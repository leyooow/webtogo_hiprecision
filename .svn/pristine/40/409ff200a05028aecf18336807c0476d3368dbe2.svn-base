package com.ivant.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Kevin Roy K. Chua
 * @version 1.0, Jul 5, 2010
 * @since 1.0, Jul 5, 2010
 */
public class HMAC
{
	private static final String UTF8_CHARSET = "UTF-8";
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	
	/**
	 * Get the MAC to be used for signing.
	 * 
	 * @param secretKey the secret key
	 * @return the MAC
	 */
	public static Mac getMac(String secretKey)
	{
		try
		{
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(UTF8_CHARSET), HMAC_SHA256_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(secretKeySpec);
			return mac;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Compute the HMAC.
	 * 
	 * @param secretKey the secret key to base the computation
	 * @param stringToSign the string to be signed
	 * @return base64-encoded HMAC value
	 */
	public static String sign(String secretKey, String stringToSign)
	{
		Mac mac = getMac(secretKey);
		if(mac == null)
		{
			return null;
		}
		else
		{
			String signature = null;
			byte[] data;
			byte[] rawHmac;
			try
			{
				data = stringToSign.getBytes(UTF8_CHARSET);
				rawHmac = mac.doFinal(data);
				Base64 encoder = new Base64();
				signature = new String(encoder.encode(rawHmac));
			}
			catch(UnsupportedEncodingException e)
			{
				throw new RuntimeException(UTF8_CHARSET + " is unsupported!", e);
			}
			return signature;
		}
	}
	
	/**
	 * Compute the HMAC.
	 * 
	 * @param secretKey the secret key to base the computation
	 * @param parameterMap the parameter map to be converted as string
	 * @return base64-encoded HMAC value
	 */
	public static String sign(String secretKey, Map<String, String[]> parameterMap)
	{
		return sign(secretKey, arrangeParameters(parameterMap));
	}
	
	/**
	 * Arrange the parameters using the natural ordering of the tree map.
	 * 
	 * @param parameters the parameters
	 * @return the arranged parameters
	 */
	public static final String arrangeParameters(Map<String, String[]> parameters)
	{
		parameters = new TreeMap<String, String[]>(parameters);
		
		final StringBuffer arrangedParameters = new StringBuffer();
		final Iterator<Map.Entry<String, String[]>> iterator = parameters.entrySet().iterator();
		
		while(iterator.hasNext())
		{
			final Map.Entry<String, String[]> entry = iterator.next();
			final List<String> valueList = Arrays.asList(entry.getValue());
			final Iterator<String> valueListIterator = valueList.iterator();
			
			while(valueListIterator.hasNext())
			{
				arrangedParameters.append(entry.getKey()).append("=").append(valueListIterator.next());
				if(valueListIterator.hasNext())
				{
					arrangedParameters.append("&");
				}
			}
			
			if(iterator.hasNext())
				arrangedParameters.append("&");
		}
		
		return arrangedParameters.toString();
	}
	
	public static void main(String... args)
	{
		System.out.println("hmac: " + sign("t1c]c3Tj0]\\[]Es", "companyId=3&userId=582"));
	}
}