package com.ivant.utils;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ivant.constants.ApplicationConstants;

/**
 * 
 * 
 * @author Eric John Apondar
 * @since Mar 15, 2017
 */
public class ContentTypeFilter
		implements Filter
{
	
	private Logger logger = Logger.getLogger(ContentTypeFilter.class);
	
	@Override
	public void destroy()
	{
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
				
		String contentType = req.getHeader("Content-Type");
		
		if(isExploit(contentType)){
			logger.info("POSSIBLE STRUTS EXPLOIT FILTERED .. Printing request ..");
			printRequest(req);
			res.sendError(400);
			return;
		}
		
		chain.doFilter(request, response);	
	}
	
	private boolean isExploit(String contentType){
		if(contentType != null){
			contentType = contentType.toLowerCase().trim();
			if(!contentType.isEmpty()){
				if(contentType.contains("multipart/")){
					if(contentType.contains("ognlutil")){
						return true;
					}
					/*if(!Character.isAlphabetic(contentType.charAt(0))){
						return true;
					}*/
				}
			}
		}
		
		return false;
	}
	

	@SuppressWarnings("rawtypes")
	private void printRequest(HttpServletRequest req){
				
		Enumeration headerNames = req.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			logger.info("[Header] " + headerName + " : " + req.getHeader(headerName));
		}
		Enumeration params = req.getParameterNames();

		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			logger.info("[Parameter]  " + paramName + " : " + req.getParameter(paramName));
		}
		
	}
	
		
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		
	}
}