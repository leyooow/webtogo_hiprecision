package com.ivant.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Vincent Ray U. Lim 
 *
 * @version 1.0, Feb 2, 2008
 * @since 3.0, Feb 2, 2008
 *
 */
public class HttpUtility
{
	/**
	 * Get the uri from the request. This method removes the context path.
	 * 
	 * @param request the request
	 * @return the uri
	 */
	public static String getServletPath(HttpServletRequest request)
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

  /**
	 * Retrieves the current request servlet path. Deals with differences between servlet
	 * specs (2.2 vs 2.3+)
	 * 
	 * @param request the request
	 * @return the servlet path
	 */
	public static String getServletPathWithPathInfo(HttpServletRequest request)
	{
		String servletPath = request.getServletPath();

		if (StringUtils.isNotEmpty(servletPath))
		{
			return servletPath;
		}

		final String contextPath = request.getContextPath();
		final String requestUri = request.getRequestURI();
		final String pathInfo = request.getPathInfo();

		int startIndex = contextPath.equals("") ? 0 : contextPath.length();
		int endIndex = pathInfo == null ? requestUri.length() : requestUri.lastIndexOf(pathInfo);

		if (startIndex > endIndex)
		{ // this should not happen
			endIndex = startIndex;
		}

		return requestUri.substring(startIndex, endIndex);
	}
}
