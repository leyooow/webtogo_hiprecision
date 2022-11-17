package com.ivant.cms.filters;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//NOTE: This is no longer an HTML File Filter but a PL File filter
public class HTMLFileFilter implements Filter {

	private static Logger logger = LoggerFactory
			.getLogger(HTMLFileFilter.class);
	private static final String capturePath = "(/awstats/)([A-Za-z].*)(pl)";
	private final Pattern capturePathPattern = Pattern.compile(capturePath);
	private ServletContext context;

	public HTMLFileFilter() {
		super();
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		context = config.getServletContext();
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		final String requestURI = HttpUtility.getServletPath(request);
		logger.trace("requestURI: {}", requestURI);
		Matcher match = capturePathPattern.matcher(requestURI);

		if (match.matches()) {
			logger.trace("Matches");
			String domainName = request.getParameter("config");
			String URI = "http://72.249.183.248/awstats/awstats.pl?config=" + domainName;
			logger.trace("fowarding to: {}", URI);
			context.getRequestDispatcher(URI).forward(request,servletResponse);

		} else {
			logger.trace("Do not match");
			chain.doFilter(request, servletResponse);
		}
	}
	
}
