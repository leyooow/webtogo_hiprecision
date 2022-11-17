package com.ivant.utils;

import java.io.UnsupportedEncodingException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class HashUtil 
{
	private static final String UTF8_CHARSET = "UTF-8";
	
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";	
	
	/**
	 * Get the MAC to be used for signing.
	 * 
	 * @param secretKey the secret key
	 * 
	 * @return the MAC
	 */
	public static Mac getMac(String secretKey)
	{
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(UTF8_CHARSET), HMAC_SHA256_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(secretKeySpec);
			return mac;
		} catch(Exception e) {
			return null;
		}
	}
	
	/**
   * Compute the HMAC.
   * 
   * @param secretKey the secret key to base the computation
   * @param stringToSign the string to be signed
   * 
   * @return base64-encoded hmac value
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
	    try {
	    	data = stringToSign.getBytes(UTF8_CHARSET);
	      rawHmac = mac.doFinal(data);
	      Base64 encoder = new Base64();
	      signature = new String(encoder.encode(rawHmac));
	    } catch(UnsupportedEncodingException e) {
	    	throw new RuntimeException(UTF8_CHARSET + " is unsupported!", e);
	    }
	    return signature;  	
	  }
  } 
}
