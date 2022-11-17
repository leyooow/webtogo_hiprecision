package com.ivant.cms.component;


import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.YQuote;
import com.ivant.utils.CSVReader;


public class QuoteComponent implements Component{

	    private static String url = "http://download.finance.yahoo.com/d/quotes.csv?s=TNROE.OB&d=t&f=sl1d1t1c1ohgvj1pp2wern";

		private static final Logger logger = Logger.getLogger(QuoteComponent.class);
		private Company company;
		private YQuote quote;
		
		
		public QuoteComponent(Company company) {
			this.company = company;
		}
	
		public String getName() {
			return "investor";
		}
		
		public void prepareComponent(HttpServletRequest request) {


				quote = new YQuote();
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
			          
		         
			          quote.setQCompany(nextWord[0]);
			          if (!nextWord[1].equals("N/A"))
			        	  quote.setLastTrade(new Double(nextWord[1]));
			          
			          quote.setDate(nextWord[2]);
			          quote.setTradeTime(nextWord[3]);
			          if (!nextWord[4].equals("N/A"))
			        	  quote.setChange(new Double(nextWord[4]));
			          if (!nextWord[5].equals("N/A"))
			        	  quote.setOpen(new Double(nextWord[5]));
			          if (!nextWord[6].equals("N/A"))
			        	  quote.setRangeTo(new Double(nextWord[6]));
			          if (!nextWord[7].equals("N/A"))
			        	  quote.setRangeFrom(new Double (nextWord[7]));
			          if (!nextWord[8].equals("N/A"))
			          quote.setVolume(new Integer(nextWord[8]));
			          quote.setMarketCap(nextWord[9]);
			          if (!nextWord[10].equals("N/A"))			          
			        	  quote.setPreviousClose(new Double(nextWord[10]));
			          quote.setChangePercent(nextWord[11]);
			          quote.setWeekRange(nextWord[12]);
			          if (!nextWord[13].equals("N/A"))
			        	  quote.setEps(new Double (nextWord[13]));
			          quote.setCompanyString(nextWord[15]);
			          
			          
			          
			          for (int i=0; i<nextWord.length;i++) System.out.println(nextWord[i]);
			 
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
		
			    logger.debug("quote.company " + quote.getCompanyString());

	}

		public Company getCompany() {
			return company;
		}

		public void setCompany(Company company) {
			this.company = company;
		}

		public YQuote getQuote() {
			return quote;
		}

		public void setQuote(YQuote quote) {
			this.quote = quote;
		}



}	
	
	
