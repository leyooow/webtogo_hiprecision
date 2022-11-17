package com.ivant.cms.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.ws.rest.resource.AbstractResource;
import com.ivant.utils.Encryption;
import com.ivant.utils.hibernate.HibernateUtil;

public class RestFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(RestFilter.class);
	private ServletContext context;

	private static final CompanyDelegate companyDelegate = CompanyDelegate
			.getInstance();

	private String message;
	private String companyId;
	private String companyKey;
	private String host;
	private int maxFileSize = 1024*500;

	@Override
	public void init(FilterConfig config) throws ServletException {
		context = config.getServletContext();
	}

	@Override
	public void destroy() {

	}

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		companyId = request.getHeader(AbstractResource.COMPANY_ID_HEADER_NAME);
		companyKey = request
				.getHeader(AbstractResource.COMPANY_KEY_HEADER_NAME);
		host = request.getHeader(AbstractResource.HOST_HEADER);

		Company company = getCompany(companyId);
		Company hostCompany = getCompanyByDomain(host);

		if (host.contains("localhost") || host.contains("192.168.100.")
				|| host.contains("192.168.0.")) {
			hostCompany = company;
		}

		System.out.println(companyId + " | " + companyKey);
		System.out.println("company : " + company);

		if (StringUtils.isEmpty(companyId) || StringUtils.isEmpty(companyKey)
				|| company == null || hostCompany == null
				|| !company.equals(hostCompany)
				|| !companyKey.equals(Encryption.hash(companyId))) {
			logger.error("Rest authentication failed...");
			printError(servletResponse, company, hostCompany);
		} else {
			logger.info(
					"Rest authentication successful... Welcome {}, forwarding to web service.",
					company.getName());
			chain.doFilter(parseRequest(request), servletResponse);
		}
	}

	private Company getCompany(String companyId) {
		Company company = null;

		try {
			HibernateUtil.createSession();
			company = companyDelegate.find(Long.parseLong(companyId));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.destroySession();
		}

		return company;
	}

	private Company getCompanyByDomain(String domain) {
		Company company = null;

		try {
			HibernateUtil.createSession();
			company = companyDelegate
					.findServerName(domain.replace("www.", ""));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.destroySession();
		}

		return company;
	}

	private void printError(ServletResponse servletResponse, Company company,
			Company hostCompany) {
		try {
			String companyName = company != null ? company.getName() : "null";
			String hostCompanyName = hostCompany != null ? hostCompany
					.getName() : "null";

			servletResponse.setContentType("text/html");
			PrintWriter out = servletResponse.getWriter();
			out.write("<html><head><title>Web Service Error</title></head><body>");
			out.write("<h3>Web Service Error</h3>");
			out.write("<ul>");
			out.write("<li>Authentication Failed!</li>");
			out.write("</ul>");
			out.write("<i>Please contact your web administrator.</i>");
			out.write("</body></html>");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ServletFileUpload#parseRequest() does not return generic type.
	private HttpServletRequest parseRequest(HttpServletRequest request)
			throws ServletException {

		// Check if the request is actually a multipart/form-data request.
        if (!ServletFileUpload.isMultipartContent(request)) {
            // If not, then return the request unchanged.
            return request;
        }
		// Prepare the multipart request items.
		// I'd rather call the "FileItem" class "MultipartItem" instead or so.
		// What a stupid name ;)
		List<FileItem> multipartItems = null;
		
		try {
			// Parse the multipart request items.
			multipartItems = new ServletFileUpload(new DiskFileItemFactory())
					.parseRequest(request);
			// Note: we could use ServletFileUpload#setFileSizeMax() here, but
			// that would throw a
			// FileUploadException immediately without processing the other
			// fields. So we're
			// checking the file size only if the items are already parsed. See
			// processFileField().
		} catch (FileUploadException e) {
			throw new ServletException("Cannot parse multipart request: "
					+ e.getMessage());
		}
		
		
		// Prepare the request parameter map.
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		// Loop through multipart request items.
		for (FileItem multipartItem : multipartItems) {
			if (multipartItem.isFormField()) {
				// Process regular form field (input
				// type="text|radio|checkbox|etc", select, etc).
				processFormField(multipartItem, parameterMap);
			} else {
				// Process form file field (input type="file").
				processFileField(multipartItem, request);
			}
		}

		// Wrap the request with the parameter map which we just created and
		// return it.
		return wrapRequest(request, parameterMap);
	}

	/**
	 * Process multipart request item as regular form field. The name and value
	 * of each regular form field will be added to the given parameterMap.
	 * 
	 * @param formField
	 *            The form field to be processed.
	 * @param parameterMap
	 *            The parameterMap to be used for the HttpServletRequest.
	 */
	private void processFormField(FileItem formField,
			Map<String, String[]> parameterMap) {
		String name = formField.getFieldName();
		String value = formField.getString();
		String[] values = parameterMap.get(name);

		if (values == null) {
			// Not in parameter map yet, so add as new value.
			parameterMap.put(name, new String[] { value });
		} else {
			// Multiple field values, so add new value to existing array.
			int length = values.length;
			String[] newValues = new String[length + 1];
			System.arraycopy(values, 0, newValues, 0, length);
			newValues[length] = value;
			parameterMap.put(name, newValues);
		}
	}

	/**
	 * Process multipart request item as file field. The name and FileItem
	 * object of each file field will be added as attribute of the given
	 * HttpServletRequest. If a FileUploadException has occurred when the file
	 * size has exceeded the maximum file size, then the FileUploadException
	 * will be added as attribute value instead of the FileItem object.
	 * 
	 * @param fileField
	 *            The file field to be processed.
	 * @param request
	 *            The involved HttpServletRequest.
	 */
	private void processFileField(FileItem fileField, HttpServletRequest request) {
		if (fileField.getName().length() <= 0) {
			// No file uploaded.
			request.setAttribute(fileField.getFieldName(), null);
		} else if (maxFileSize > 0 && fileField.getSize() > maxFileSize) {
			// File size exceeds maximum file size.
			request.setAttribute(fileField.getFieldName(),
					new FileUploadException(
							"File size exceeds maximum file size of "
									+ maxFileSize + " bytes."));
			// Immediately delete temporary file to free up memory and/or disk
			// space.
			fileField.delete();
		} else {
			// File uploaded with good size.
			request.setAttribute(fileField.getFieldName(), fileField);
			
		}
	}

	// Utility (may be refactored to public utility class)
	// ----------------------------------------

	/**
	 * Wrap the given HttpServletRequest with the given parameterMap.
	 * 
	 * @param request
	 *            The HttpServletRequest of which the given parameterMap have to
	 *            be wrapped in.
	 * @param parameterMap
	 *            The parameterMap to be wrapped in the given
	 *            HttpServletRequest.
	 * @return The HttpServletRequest with the parameterMap wrapped in.
	 */
	private static HttpServletRequest wrapRequest(HttpServletRequest request,
			final Map<String, String[]> parameterMap) {
		return new HttpServletRequestWrapper(request) {
			public Map<String, String[]> getParameterMap() {
				return parameterMap;
			}

			public String[] getParameterValues(String name) {
				return parameterMap.get(name);
			} 

			public String getParameter(String name) {
				String[] params = getParameterValues(name);
				return params != null && params.length > 0 ? params[0] : null;
			}

			public Enumeration<String> getParameterNames() {
				return Collections.enumeration(parameterMap.keySet());
			}
		};
	}
}
