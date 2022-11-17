/**
 *
 */
package com.ivant.cms.interfaces;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.action.PageDispatcherAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;


/**
 * A class implementing this interface should extend the class {@link PageDispatcherAction}. 
 * Why? see {@link com.ivant.cms.action.company.package-info.java}
 * @see com.ivant.cms.action.company
 * @author Edgar S. Dacpano
 *
 */
public interface PageDispatcherAware 
	extends Action, 
			Preparable, 
			ServletRequestAware, 
			ServletResponseAware, 
			ServletContextAware, 
			CompanyAware, 
			MemberAware, 
			SessionAware, 
			LanguageAware, 
			MobileAware
{
}
