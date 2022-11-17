package com.ivant.utils;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlContent {
	
	public String getHtmlContent(String url, String afterTag, String beforeTag)
	{
		String stockQuote="";
		//System.out.println("MEMO getHtmlContent()");
		try{
			StringBuilder html = new StringBuilder();
			URL urlTemp = new URL(url);
			HttpURLConnection tempConnection = (HttpURLConnection)urlTemp.openConnection();
			BufferedInputStream in = new BufferedInputStream(tempConnection.getInputStream());
			
			Reader r = new InputStreamReader(in);
			int i;
		    while ((i = r.read()) != -1) {
		    	html.append((char) i);
		    }
		    //System.out.println("RESULT :: "+html);
		 	r.close();
		    stockQuote = html.substring(html.indexOf(afterTag)+afterTag.length());
		    stockQuote = stockQuote.substring(1, stockQuote.indexOf(beforeTag));
		    
		}catch(Exception e){
			System.out.println(e.getLocalizedMessage());
		}
		
		stockQuote = stockQuote.trim().replaceAll("00:00:00.0", "").replaceAll("\"", "").replaceAll(":", "").replace(",", "").replace(" ", "");
		
		return stockQuote;
	}
	
	/*
	public ArrayList<String> getHtmlContent(String url, String afterTag, String beforeTag){
		String stockQuote="";
		
		try{
			StringBuilder html = new StringBuilder();
			URL urlTemp = new URL(url);
			HttpURLConnection tempConnection = (HttpURLConnection)urlTemp.openConnection();			
			
			BufferedInputStream in = new BufferedInputStream(tempConnection.getInputStream());			
			Reader r = new InputStreamReader(in);
			int i;
		    while ((i = r.read()) != -1) {
		    	html.append((char) i);
		    }
		    r.close();
		 	
		    stockQuote = html.substring(html.indexOf(afterTag)+afterTag.length());
		    stockQuote = stockQuote.substring(1, stockQuote.indexOf(beforeTag));
		    
		}catch(Exception e){
			System.out.println(e.getLocalizedMessage());
		}
		String ss = HTMLTagStripper.stripTags(stockQuote);
		
		ArrayList<String> listStr = new ArrayList<String>();
		String[] arrayStr = ss.split(" ");
		for (int i=0; i<arrayStr.length; i++)
		{ 
			arrayStr[i] = ((((arrayStr[i].trim()).replaceAll("", "")).replaceAll("	", "")).replaceAll("\n", "")).replaceAll("\r", "");
				  
			if (arrayStr[i].trim().length() !=0) listStr.add(arrayStr[i]);
		}
		for (String sss : listStr)  System.out.println(sss.toString());
	    return listStr;
	}
	*/
}
