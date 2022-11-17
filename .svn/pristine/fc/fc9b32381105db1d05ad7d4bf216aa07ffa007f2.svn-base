package com.ivant.utils;

import com.ivant.utils.CSVReader;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import com.ivant.cms.entity.YQuote;

import java.io.*;



public class QuoteDownload {
	
  private static String url = "http://download.finance.yahoo.com/d/quotes.csv?s=TNRO.OB&d=t&f=sl1d1t1c1ohgvj1pp2wern";
  private YQuote yquote;
  
 
  public void QuoteDownload() {
	  
	   final YQuote yquote;
	  
	    yquote = new YQuote();
	    HttpClient client = new HttpClient();
	    GetMethod method = new GetMethod(url);
	    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
	    		new DefaultHttpMethodRetryHandler(3, false));

	    try {
	      int statusCode = client.executeMethod(method);

	      if (statusCode != HttpStatus.SC_OK) {
	        System.err.println("Method failed: " + method.getStatusLine());
	      }

	      byte[] responseBody = method.getResponseBody();

	      //System.out.println("HERE--->   " + new String(responseBody));
	      CSVReader reader = new CSVReader(new StringReader(new String(responseBody)));
	      
	      String [] nextWord = null;
	      
	    
	      while ((nextWord = reader.readNext()) != null) {
	          // nextWord[] is an array of values from the line
	    	  //System.out.println(nextWord);
	          //System.out.println("--> " + nextWord[0] + ", " +  nextWord[1] + ", " + nextWord[2] + ", " + nextWord[3]);
	          //System.out.println("size " + nextWord.length);
	          
         
	          yquote.setQCompany(nextWord[0]);
	          yquote.setLastTrade(new Double(nextWord[1]));
	          yquote.setDate(nextWord[2]);
	          yquote.setTradeTime(nextWord[3]);
	          yquote.setChange(new Double(nextWord[4]));
	          yquote.setOpen(new Double(nextWord[5]));
	          yquote.setRangeTo(new Double(nextWord[6]));
	          yquote.setRangeFrom(new Double (nextWord[7]));
	          yquote.setVolume(new Integer(nextWord[8]));
	          yquote.setMarketCap(nextWord[9]);
	          yquote.setPreviousClose(new Double(nextWord[10]));
	          yquote.setChangePercent(nextWord[11]);
	          yquote.setWeekRange(nextWord[12]);
	          yquote.setEps(new Double (nextWord[13]));
	          yquote.setCompanyString(nextWord[15]);
	          
	          
	          
	         // for (int i=0; i<nextWord.length;i++) System.out.println(nextWord[i]);
	 
	      }
	    } catch (HttpException e) {
	      System.err.println("Fatal protocol violation: " + e.getMessage());
	      e.printStackTrace();
	    } catch (IOException e) {
	      System.err.println("Fatal transport error: " + e.getMessage());
	      e.printStackTrace();
	    } finally {
	      method.releaseConnection();
	    }  

  }
  
}
  
  
  
  
  
  
  
  
  
  
  
  
//  public static void main(String[] args) {
//    // Create an instance of HttpClient.
//    HttpClient client = new HttpClient();
//
//    // Create a method instance.
//    GetMethod method = new GetMethod(url);
//    
//    // Provide custom retry handler is necessary
//    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
//    		new DefaultHttpMethodRetryHandler(3, false));
//
//    try {
//      // Execute the method.
//      int statusCode = client.executeMethod(method);
//
//      if (statusCode != HttpStatus.SC_OK) {
//        System.err.println("Method failed: " + method.getStatusLine());
//      }
//
//      // Read the response body.
//      byte[] responseBody = method.getResponseBody();
//
//      // Deal with the response.
//      // Use caution: ensure correct character encoding and is not binary data
//      System.out.println("HERE--->   " + new String(responseBody));
//      CSVReader reader = new CSVReader(new StringReader(new String(responseBody)));
//      
//      String [] nextWord = null;
//      
//    
//      while ((nextWord = reader.readNext()) != null) {
//          // nextWord[] is an array of values from the line
//    	  System.out.println(nextWord);
//          System.out.println("--> " + nextWord[0] + ", " +  nextWord[1] + ", " + nextWord[2] + ", " + nextWord[3]);
//          System.out.println("size "+ nextWord.length);
//          for (int i=0; i<nextWord.length;i++) System.out.println(nextWord[i]);
// 
//      }
//    } catch (HttpException e) {
//      System.err.println("Fatal protocol violation: " + e.getMessage());
//      e.printStackTrace();
//    } catch (IOException e) {
//      System.err.println("Fatal transport error: " + e.getMessage());
//      e.printStackTrace();
//    } finally {
//      // Release the connection.
//      method.releaseConnection();
//    }  
//  }
//}