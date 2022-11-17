package com.ivant.cms.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kevin Roy K. Chua
 *
 * @version 1.0, Aug 27, 2014
 */

public class XssRequestWrapper
		extends HttpServletRequestWrapper
{
	
	 private static final Logger logger = LoggerFactory.getLogger(XssRequestWrapper.class);
	
	/** The parameter will not be stripped of anything if it contains any value in this list */
	private static final List<String> EXEMPTED_PARAMETER_LIST = new ArrayList<String>();
	
	/** The value will not be stripped of anything if it contains any word in this list */
	private static final List<String> EXEMPTED_VALUE_LIST = new ArrayList<String>();
	
	static
	{
		EXEMPTED_PARAMETER_LIST.add("password");
		
		EXEMPTED_VALUE_LIST.add("https://maps.google.com");
		EXEMPTED_VALUE_LIST.add("https://www.google.com/maps/embed");
		EXEMPTED_VALUE_LIST.add("http://www.globe.com.ph");
		EXEMPTED_VALUE_LIST.add("https://www.globe.com.ph");
	}
	
	/**
	 * @param request
	 */
	public XssRequestWrapper(HttpServletRequest request)
	{
		super(request);
	}
	
	@Override
	public String[] getParameterValues(String parameter)
	{
		String[] values = super.getParameterValues(parameter);
		
		if(values == null)
		{
			return null;
		}
		
		int count = values.length;
		String[] encodedValues = new String[count];
		for(int i = 0; i < count; i++)
		{
			if(EXEMPTED_PARAMETER_LIST.contains(parameter))
			{
				encodedValues[i] = values[i];
			}
			else
			{
				encodedValues[i] = stripXSS(values[i]);
			}
		}
		
		return encodedValues;
	}
	
	@Override
	public String getParameter(String parameter)
	{
		//logger.debug("getParameter() invoked"); 
		String value = super.getParameter(parameter);
		
		if(EXEMPTED_PARAMETER_LIST.contains(parameter))
		{
			return value;
		}
		
		return stripXSS(value);
	}
	
	@Override
	public String getHeader(String name)
	{
		String value = super.getHeader(name);
		
		return stripXSS(value);
	}
	
	private String stripXSS(String value)
	{
		//logger.debug("stripXSS()");
		if(value != null)
		{
			// NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
			// avoid encoded attacks.
			value = ESAPI.encoder().canonicalize(value);
			
			// Avoid null characters
			value = value.replaceAll("", "");
			
			// Do not strip values contained in the EXEMPTED_LIST
			for(String exemptedValue : EXEMPTED_VALUE_LIST)
			{
				if(value.contains(exemptedValue))
				{
					return value;
				}
			}
			
			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid css/style tags
			scriptPattern = Pattern.compile("(.*?)<(.*?)style(.*?)>(.*?)<(.*?)/style>(.*?)", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// iframes
			scriptPattern = Pattern.compile("<(.*?)iframe(.*?)", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*(.*?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid meta characters:
			// Open/Close square brackets []
			// Backslash \
			// Caret ^
			// Dollar Sign $
			// Pipe |
			// Question Mark ?
			// Asterisk *
			// Plus Sign +
			// Open/Close curly braces {}
			// Open/Close parenthesis ()
			scriptPattern = Pattern.compile("[\\[\\]\\\\\\^\\$\\|\\?\\*\\+\\{\\}\\(\\)]", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
			value = scriptPattern.matcher(value).replaceAll("");

		}
		return value;
	}
}