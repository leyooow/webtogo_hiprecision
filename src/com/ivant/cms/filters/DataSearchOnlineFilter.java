/**
 *
 * Copyright (c) 2009 Ivant Technologies, All rights reserved.
 * 
 */
package com.ivant.cms.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.ivant.utils.hibernate.HibernateUtil;

/**
 * 
 * @author Kevin Chua
 * @date July 18, 2013
 *
 */
public class DataSearchOnlineFilter 
		implements Filter
{		
	private ServletContext context;
	
	private String toPath;
	
	private String capturePath;		
	
	@Override
	public void init(FilterConfig config) throws ServletException
	{
		context = config.getServletContext();
		
		toPath = "/dwr/${1}";
		capturePath = "\\/companies\\/hpds\\/dwr\\/(.+)";				
	}

	@Override
	public void destroy()
	{
		toPath = null;
		capturePath = null;
		context = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		final HttpServletRequest req = (HttpServletRequest) request;
		 
		final String requestURI = getServletPath(req);
		
		final String childRequestURI = requestURI.replaceFirst(capturePath, "$1");
		final String finalPath = toPath.replace("${1}", childRequestURI);								
		
		try
		{
			HibernateUtil.createSession();			
			context.getRequestDispatcher(finalPath).forward(request, response);
		}
		finally
		{
			HibernateUtil.destroySession();
		}		
	}
	
	/**
	 * Get the uri from the request. This method removes the context path.
	 * 
	 * @param request the request
	 * @return the uri
	 */
	private String getServletPath(HttpServletRequest request)
	{
		String servletPath = request.getServletPath();

		if (StringUtils.isNotEmpty(servletPath))
		{
			return servletPath;
		}

		final String contextPath = request.getContextPath();
		final String requestUri = request.getRequestURI();

		int startIndex = contextPath.equals("") ? 0 : contextPath.length();

		return requestUri.substring(startIndex);
	}
}