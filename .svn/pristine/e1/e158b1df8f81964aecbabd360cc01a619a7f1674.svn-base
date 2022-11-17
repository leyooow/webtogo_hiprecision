package com.ivant.cms.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;
import org.json.simple.JSONArray;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberMessageDelegate;
import com.ivant.cms.delegate.NotificationDelegate;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberMessage;
import com.ivant.cms.entity.Notification;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.MessageRemarks;
import com.ivant.cms.enums.MessageStatus;
import com.ivant.cms.enums.MessageType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.LanguageAware;
import com.ivant.cms.interfaces.MemberAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

public class AgianNotificationAction extends AbstractBaseAction 
			implements Action, Preparable, ServletRequestAware, ServletResponseAware, ServletContextAware, CompanyAware, MemberAware, SessionAware, LanguageAware
{
	private HttpServletResponse response;
	private Language language;
	private List<Notification> allNotification;
	private String resultNotifications;
	private MemberMessageDelegate memberMessageDelegate = MemberMessageDelegate.getInstance();
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private NotificationDelegate notificationDelegate = NotificationDelegate.getInstance();
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String showNotifications(){
		
		getAllNotifications();
		
		return SUCCESS;
	}
	
	public List<Notification> getAllNotifications() {
		JSONArray jsonArr = new JSONArray();
		
		ObjectList<Notification> notif = notificationDelegate.findAllNotifs(company, Order.desc("updatedOn"));
		
		if(!isNull(notif)){
			jsonArr.addAll(notif.getList());
		}
		
		resultNotifications = jsonArr.toJSONString();
		
		
		return notif.getList();

	}

	public void setAllNotification(List<Notification> allNotification) {
		this.allNotification = allNotification;
	}

	public String getResultNotifications() {
		return resultNotifications;
	}

	public void setResultNotifications(String resultNotifications) {
		this.resultNotifications = resultNotifications;
	}

	public Language getLanguage() {
		return language;
	}
	
	@Override
	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}

	public HttpServletResponse getServletResponse() {
		return response;
	}
	
	
	
}
