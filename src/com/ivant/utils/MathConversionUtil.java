package com.ivant.utils;

public class MathConversionUtil {

	/**
	 * @param String
	 * @return inches
	 */
	public static Double feetToInches(String feet) {
		String [] tmp = feet.split("'");
		
		Double totalFeet = Double.parseDouble(tmp[0]) * 12;
		
		return totalFeet + Double.parseDouble(tmp[1]);
	}
}
