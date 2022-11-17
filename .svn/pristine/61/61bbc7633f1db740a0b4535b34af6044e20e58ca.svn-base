package com.ivant.cms.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.interfaces.NotificationMessageAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Class that sets the notification message based on the session.
 * 
 * <p>In order to use this interceptor, result action name must have a prefix of {@code notify*}.
 * 
 * <p>e.g <code>action name="<b>notify</b>Product" class="com.ivant.cms.action.ProductAction"</code>
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class NotificationMessageInterceptor implements Interceptor{
	
	private static final long serialVersionUID = 5172800256992745825L;
	private static final Logger logger = Logger.getLogger(NotificationMessageInterceptor.class);
	
	public static final String NOTIFICATION_MESSAGE_REQUEST_KEY = "notificationMessage";

	@Override
	public void destroy() {
		logger.debug("DESTROYING NOTIFICATION MESSAGE INTERCEPTOR...");
		
	}

	@Override
	public void init() {
		logger.debug("CREATING NOTIFICATION MESSAGE INTERCEPTOR...");
		
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.debug("NOTIFICATION MESSAGE INTERCEPTOR CALLED...");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		//System.out.println("Current Invoker : " + invocation.getInvocationContext().getName());
		
		try {
			//initialize notification message
			String notificationMessage = "";
			
			//get current notification message
			notificationMessage = (String)session.getAttribute(NOTIFICATION_MESSAGE_REQUEST_KEY);
			
			if(!(invocation.getInvocationContext().getName().indexOf("notify") > -1))
				notificationMessage = null;
			
			if(null != notificationMessage) {
				//set notification message
				Object action = invocation.getAction();
				if (action instanceof NotificationMessageAware) {
					((NotificationMessageAware)action).setNotificationMessage(notificationMessage);
				}
			} else {
				notificationMessage = "";
			}
			
			//add notification to the request
			request.setAttribute(NOTIFICATION_MESSAGE_REQUEST_KEY, notificationMessage);
			
			//System.out.println("NOTIFICATION MESSAGE : " + notificationMessage);
		} catch (Exception e) {
			logger.fatal("problem intercepting action.", e);
		}
		
		
		
		return invocation.invoke();
	}

}
