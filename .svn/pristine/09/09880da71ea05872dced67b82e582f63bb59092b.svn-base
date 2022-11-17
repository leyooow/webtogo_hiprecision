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
public class MyHomeTherapistFilter implements Filter
{

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
	{
		final HttpServletResponse res = (HttpServletResponse) response;
		final HttpServletRequest req = (HttpServletRequest) request;
		//final String requestURI = req.getRequestURI();
		final String serverName = req.getServerName();
		if(serverName.contains("register") && serverName.contains("myhometherapist"))
		{
			res.sendRedirect("http://myhometherapist.webtogo.com.ph/register.do"); //temporary
		}
		else
		{
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}
	
}
