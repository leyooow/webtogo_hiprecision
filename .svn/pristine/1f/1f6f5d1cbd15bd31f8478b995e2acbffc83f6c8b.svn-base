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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.utils.HttpUtility;

public class CkFinderFilter implements Filter{
	private static Logger logger = LoggerFactory
	.getLogger(CkFinderFilter.class);
	private ServletContext context;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		final String requestURI = HttpUtility.getServletPath(request);
		logger.trace("requestURI: {}", requestURI);
		
		context.getRequestDispatcher(requestURI).forward(request,servletResponse);		
				
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		context = config.getServletContext();
		
	}

}
