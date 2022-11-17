package com.ivant.utils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class JSUpdateJob implements Job{
	public void execute(JobExecutionContext arg0) throws JobExecutionException {    
	   
	    try{
	    	URL urlTemp = new URL("http://webtogo.com.ph/generateJS.do");
	    	HttpURLConnection tempConnection = (HttpURLConnection)urlTemp.openConnection();
	    	tempConnection.getInputStream();
	    	tempConnection.disconnect();
	    	//System.out.println("JS Updated at: "+new Date());
	    }catch(Exception e){
	    	System.out.println("Exception: "+e.getMessage().toString());
	    }
	  }
}