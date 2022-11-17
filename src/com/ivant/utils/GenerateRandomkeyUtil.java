package com.ivant.utils;

/**
 * @author Isaac Arenas Pichay
 * @since Apr 22, 2014
 */
public class GenerateRandomkeyUtil {
	public static final String alpabhet_numeric = "1234567890abcdefghijklmnopqrstuvwxyz";

	public static String generateAccessKey(int size) {
		String accesskey = "";

		for (int i = 0; i < size; i++) {
			int random = (int) (((Math.random() * 100)) % alpabhet_numeric
					.length());
			accesskey += alpabhet_numeric.charAt(random);
		}

		return accesskey;
	}
}
