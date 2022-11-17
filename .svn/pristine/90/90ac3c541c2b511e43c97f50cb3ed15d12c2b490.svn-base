package com.ivant.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author erwin
 *
 */
public final class Encryption {
    
    private static String bytesToHex (byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; ++i) {
			sb.append ((Integer.toHexString((b[i] & 0xFF) | 0x100)).substring(1,3));
		}
		return sb.toString();
	}
    
    public static String hash(String data)
    {
        try {
            byte[] mybytes = data.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5digest = md5.digest (mybytes);
            return bytesToHex (md5digest);
        }
        catch(NoSuchAlgorithmException e){
        	
        }
        return "";
    }
}

