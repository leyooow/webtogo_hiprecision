package com.ivant.cms.action.admin.dwr;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.ivant.utils.HTMLTagStripper;
import java.util.ArrayList;

public class DWRHtmlContentAction extends AbstractDWRAction{

	public ArrayList<String> getHtmlContent(String url, String afterTag, String beforeTag, int offset){
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
		 	
		    stockQuote = html.substring(html.lastIndexOf(afterTag)+offset);
		    stockQuote = stockQuote.substring(1, stockQuote.indexOf(beforeTag));
		    
		}catch(Exception e){
			System.out.println(e.getLocalizedMessage());
		}
		//System.out.println("Stock Quote old html: "+stockQuote);		
		String ss = HTMLTagStripper.stripTags(stockQuote);
		//String ss = stockQuote.replaceAll("<(.*?)>", "");
		//System.out.println("Stock Quote new html: "+ ss);
		ArrayList<String> listStr = new ArrayList<String>();
		String[] arrayStr = ss.split(" ");
		for (int i=0; i<arrayStr.length; i++)
		{ arrayStr[i] = arrayStr[i].trim();
		  //System.out.println(arrayStr[i]); 
		  if (arrayStr[i].trim().length() !=0) listStr.add(arrayStr[i]);
		}
		for (String sss : listStr)  System.out.println(sss.toString());
	    return listStr;
	}
}
