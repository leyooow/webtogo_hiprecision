package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.NotificationDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Notification;
import com.ivant.cms.entity.list.ObjectList;

public class NotificationDelegate extends AbstractBaseDelegate<Notification, NotificationDAO>
{
	private static NotificationDelegate instance = new NotificationDelegate();
	
	public static NotificationDelegate getInstance() 
	{
		return instance;
	}
	
	public NotificationDelegate() 
	{
		super(new NotificationDAO());
	}
	
	public Notification findByEmail(Company company, String email) 
	{
		return dao.findByEmail(company, email);
	}
	
	public ObjectList<Notification> findAll(Company company)
	{
		return dao.findAll(company);
	}
	
	public ObjectList<Notification> findAllNotifs(Company company, Order...orders){
		return dao.findAllNotifs(company, orders);
	}

}