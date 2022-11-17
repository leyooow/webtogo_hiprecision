package com.ivant.cms.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.entity.Company;
import com.ivant.cms.interfaces.CompanyAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public abstract class BaseAction extends ActionSupport implements Preparable,
		ServletRequestAware, ServletContextAware, CompanyAware {

	private static final long serialVersionUID = -1133798515575235458L;

	protected HttpServletRequest request;
	protected ServletContext servletContext;
	protected String servletContextName;
	protected String httpServer;
	protected Company currentCompany;
	protected String pageName;
	protected boolean isLocal;

	@Override
	public void prepare() throws Exception {
		pageName = parsePageName();
		servletContextName = servletContext.getServletContextName();
		isLocal = (request.getRequestURI().startsWith("/" + servletContextName)) ? true
				: false;
		httpServer = (isLocal) ? getLocalURL() : getLiveURL();
		request.setAttribute("local", this.isLocal);
	}

	protected String getLiveURL() {
		return ("http://" + request.getServerName());
	}

	protected String getLocalURL() {
		return ("http://" + request.getServerName() + ":"
				+ request.getServerPort() + "/" + servletContextName
				+ "/companies/" + currentCompany.getName());
	}
	
	/**
	 * Returns local webtogo admin/backend URL.
	 * 
	 * @return - local webtogo admin/backend URL
	 */
	protected String getLocalAdminURL() {
		return ("http://" + request.getServerName() + ":"
				+ request.getServerPort() + "/" + servletContextName);
	}

	private String parsePageName() {
		String uri = request.getRequestURI();
		String[] uriSeparated = uri.split("/");
		String last = uriSeparated[uriSeparated.length - 1];
		pageName = last.toLowerCase();
		pageName = pageName.replace(".do", "");
		return pageName;
	}

	public String getHttpServer() {
		return httpServer;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void setCompany(Company company) {
		this.currentCompany = company;
	}

	public String getPageName() {
		return pageName;
	}

}
