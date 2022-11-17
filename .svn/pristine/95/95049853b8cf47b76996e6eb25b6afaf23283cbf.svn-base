package com.ivant.cms.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Kevin Roy K. Chua
 *
 * @version 1.0, Mar 29, 2011
 * @since 1.0, Mar 29, 2011
 *
 */
public class GenTransAction
		extends ActionSupport
		implements ServletRequestAware
{
	private static final long serialVersionUID = -4092932112967858328L;

	private HttpServletRequest request;
	
	public String receive() throws Exception
	{		
		/*System.out.println("ANONYMOUS NAME          : " + request.getParameter("anonymousName"));
		System.out.println("ANONYMOUS EMAIL         : " + request.getParameter("anonymousEmail"));
		System.out.println("ANONYMOUS NO OF SPEAKERS: " + request.getParameter("anonymousNoOfSpeakers"));
		System.out.println("ANONYMOUS TEMPLATES     : " + request.getParameter("anonymousTemplates"));
		System.out.println("UPLOADED FILE NAME      : " + request.getParameter("uploadedFileName"));
		System.out.println("UPLOADED AUDIO TIME     : " + request.getParameter("uploadedAudioTime"));
		System.out.println("UPLOADED AUDIO PRICE    : " + request.getParameter("uploadedAudioPrice"));
		System.out.println("UPLOADED AUDIO TOTAL    : " + request.getParameter("uploadedAudioTotal"));
		System.out.println("ANONYMOUS REMARKS       : " + request.getParameter("anonymousRemarks"));
		System.out.println("NEW JOB ID              : " + request.getParameter("newJobId"));*/
		
		request.setAttribute("anonymousName", request.getParameter("anonymousName"));
		request.setAttribute("anonymousEmail", request.getParameter("anonymousEmail"));
		request.setAttribute("anonymousNoOfSpeakers", request.getParameter("anonymousNoOfSpeakers"));
		request.setAttribute("anonymousTemplates", request.getParameter("anonymousTemplates"));
		request.setAttribute("uploadedFileName", request.getParameter("uploadedFileName"));
		request.setAttribute("uploadedAudioTime", request.getParameter("uploadedAudioTime"));
		request.setAttribute("uploadedAudioPrice", request.getParameter("uploadedAudioPrice"));
		request.setAttribute("uploadedAudioTotal", request.getParameter("uploadedAudioTotal"));
		request.setAttribute("anonymousRemarks", request.getParameter("anonymousRemarks"));
		request.setAttribute("newJobId", request.getParameter("newJobId"));
		
		return SUCCESS;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}		
}