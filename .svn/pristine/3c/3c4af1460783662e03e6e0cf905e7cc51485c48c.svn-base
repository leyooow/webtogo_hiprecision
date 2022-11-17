package com.ivant.utils.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ivant.cms.helper.NwdiHelper;

public class HibernateFilter implements Filter {
	Logger logger = Logger.getLogger(HibernateFilter.class);
	private FilterConfig filterConfig;
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		boolean toIncludeNwdiHibernateSession = NwdiHelper.toInludeSession(request);
		
		try
		{
			HibernateUtil.createSession();
			NwdiHibernateUtil.createSession((HttpServletRequest)request);			
			chain.doFilter(request, response);
		}
		catch (ServletException e)
		{
			logger.error(e.getMessage(), e.getRootCause());
			logger.error(e, e);
			throw e;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		finally
		{
			HibernateUtil.destroySession();
			if(toIncludeNwdiHibernateSession) NwdiHibernateUtil.destroySession();
		}
		
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
	
	public FilterConfig getFilterConfig() {
		return this.filterConfig;
	}
 
	public void setFilterConfig (FilterConfig filterConfig) {
	  this.filterConfig = filterConfig;
	}
}
