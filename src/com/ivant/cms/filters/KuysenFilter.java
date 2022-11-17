/**
 *
 */
package com.ivant.cms.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edgar S. Dacpano
 *
 */
public class KuysenFilter
		implements Filter
{
	
	public static final String COMPANY_URL = "http://kuysen.com/"; 

	@Override
	public void destroy()
	{
		// Do nothing
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final String requestURI = request.getRequestURI();
		
		if(requestURI.contains("html") || requestURI.contains("htm"))
		{
			response.sendRedirect(COMPANY_URL);
		}
		else
		{
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		// Do nothing
	}
	
}
