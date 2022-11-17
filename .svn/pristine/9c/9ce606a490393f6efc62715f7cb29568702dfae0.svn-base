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

public class EasternWireFilter implements Filter
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
		
		if(requestURI.contains("gabions"))
		{
			res.sendRedirect("http://easternwire.ph/products.do?item_id=31845");
		}
		else if(requestURI.contains("evg3d"))
		{
			res.sendRedirect("http://easternwire.ph/products.do?item_id=31599");
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
