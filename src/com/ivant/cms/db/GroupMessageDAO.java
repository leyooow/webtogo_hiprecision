package com.ivant.cms.db;

import java.util.Date;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.GroupMessage;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberMessage;
import com.ivant.cms.entity.list.ObjectList;

public class GroupMessageDAO extends AbstractBaseDAO<GroupMessage> {
	public ObjectList<GroupMessage> findAllByGroup(Company company, String group){
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("groupName",group));

		
		ObjectList<GroupMessage> groupMessages = findAllByCriterion(-1, -1, null, null, null, new Order[] {Order.asc("createdOn")}, conj);
		if(groupMessages.getList().size() == 0) {
			return null;
		}
		else{
			return groupMessages;
		}
	}
	
	public ObjectList<GroupMessage> findAllByGroupAndTime(int pageNumber, int resultPerPage, Company company, String groupName, Date dateAcquired, Order[] order){
		final Conjunction conj = Restrictions.conjunction();

		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("groupName", groupName));
		conj.add(Restrictions.ge("createdOn", dateAcquired));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, conj);
	}
}
