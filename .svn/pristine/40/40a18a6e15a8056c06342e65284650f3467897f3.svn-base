package com.ivant.utils;



public final class HTMLTagStripper {


	
	public static String stripTags(String htmlString) {
		String newText5 = null;
		try {
			String newText1;
			String newText2;
			String newText3;
			String newText4;
			
			
			newText1 = htmlString.replaceAll("<(.*?)>", " ");
			newText2 = newText1.replaceAll("<br>","\n");
			newText3 = newText2.replaceAll("&nbsp;"," ");
			newText4 = newText3.replace('(', ' ');
			newText5 = newText4.replace(')', ' ');
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return newText5;
	}
	
public static String stripTags2(String htmlString) {
		
		String newText1;
		String newText2;
		String newText3;
		
		newText1 = htmlString.replaceAll("<(.*?)>", "");
		newText2 = newText1.replaceAll("<br>","\n");
		newText3 = newText2.replaceAll("&nbsp;","");
		
		return newText3;
	}


public static String stripTags3(String htmlString) {
	String newText5 = null;
	try {
		String newText1;
		String newText2;
		String newText3;
		String newText4;
		
		
		newText1 = htmlString.replaceAll("(\\r|\\n|\\t)", "").replaceAll("<br>","\n");
		newText2 = newText1.replaceAll("<br />","\n");
		newText3 = newText2.replaceAll("<br/>","\n");
		newText4 = newText3.replaceAll("&nbsp;"," ");
		newText5 = newText4.replaceAll("<(.*?)>", " ");
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return newText5;
}
}
