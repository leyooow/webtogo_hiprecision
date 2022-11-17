package com.ivant.cms.db;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.ivant.cms.entity.Comment;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.utils.CategoryItemPackageWrapper;

public class CommentDAO extends AbstractBaseDAO<Comment> {

	public CommentDAO() {
		super();
	}
	
	public ObjectList<Comment> findAllTopics(Company company) {			
		Junction conj = Restrictions.conjunction();
		Order[] orders = new Order[]{
			Order.desc("createdOn")
		};
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.isNull("parentComment"));
			
		return findAllByCriterion(null, null, null, orders, conj);
	}
	
	public ObjectList<Comment> findAllWithParentId(Comment comment) {			
	
		Junction conj = Restrictions.conjunction();
		Order[] orders = new Order[]{
			Order.desc("createdOn")
		};
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("parentComment", comment));
		
			
		return findAllByCriterion(null, null, null, orders, conj);
	}
	
	
	

}
