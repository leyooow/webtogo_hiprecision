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

public class PilipinasBronzeFilter implements Filter
{
	@Override
	public void destroy()
	{

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) 
		throws IOException, ServletException
	{
		final HttpServletResponse res = (HttpServletResponse) response;
		final HttpServletRequest req = (HttpServletRequest) request;
		final String requestURI = req.getRequestURI();
		
		if(requestURI.contains("profile") && requestURI.contains("html"))
		{
			res.sendRedirect("http://www.pilipinasbronze.com/profile.do");
		}
		else if((requestURI.contains("contact") && requestURI.contains("html"))
		 		|| (requestURI.contains("map") && requestURI.contains("html")))
		{
			res.sendRedirect("http://www.pilipinasbronze.com/contact.do");
		}
		else if(requestURI.contains("tiger") && requestURI.contains("html"))
		{
			res.sendRedirect("http://www.pilipinasbronze.com/guide-to-bronze.do?id=15522");
		}
		else if(requestURI.contains("capabilities") && requestURI.contains("html"))
		{
			res.sendRedirect("http://www.pilipinasbronze.com/expertise.do");
		}
		else if(requestURI.contains("manganese") && requestURI.contains("html"))
		{
			res.sendRedirect("http://www.pilipinasbronze.com/guide-to-bronze.do?id=15525");
		}
		else if(requestURI.contains("magnolia") && requestURI.contains("html"))
		{
			res.sendRedirect("http://www.pilipinasbronze.com/guide-to-bronze.do?id=15520");
		}
		else if(requestURI.contains(".html"))
		{
			res.sendRedirect("http://www.pilipinasbronze.com/index.do");
		}
		else
		{
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{

	}

}
