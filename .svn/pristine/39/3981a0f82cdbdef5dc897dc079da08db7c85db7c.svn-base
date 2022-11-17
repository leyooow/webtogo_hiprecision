package com.ivant.cms.delegate;

import java.util.Date;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.GroupMessageDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.GroupMessage;
import com.ivant.cms.entity.list.ObjectList;

public class GroupMessageDelegate extends AbstractBaseDelegate<GroupMessage, GroupMessageDAO> {
	private static GroupMessageDelegate instance = new GroupMessageDelegate();
	
	public static GroupMessageDelegate getInstance() {
		return instance;
	}
	
	public GroupMessageDelegate() {
		super(new GroupMessageDAO());
	}
	
	public ObjectList<GroupMessage> findAllByGroup(Company company, String group){
		return dao.findAllByGroup(company, group);
		
	}
	
	public ObjectList<GroupMessage> findAllByGroupAndTime(int pageNumber, int resultPerPage, Company company, String groupName, Date dateAcquired, Order[] order){
		return dao.findAllByGroupAndTime(pageNumber, resultPerPage, company, groupName, dateAcquired, order);
		
	}
}
