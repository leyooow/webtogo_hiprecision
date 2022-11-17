package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.CommentDAO;
import com.ivant.cms.entity.Comment;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.list.ObjectList;

public class CommentDelegate extends AbstractBaseDelegate<Comment, CommentDAO> {


	public CommentDelegate() {
		super(new CommentDAO());
	}
	
	private static CommentDelegate instance = new CommentDelegate();
	
	public static CommentDelegate getInstance() {
		return instance;
	}
	
	public ObjectList<Comment> findAllTopics(Company company){
		return dao.findAllTopics(company);	
	}
	
	public ObjectList<Comment> findAllWithParentId(Comment comment){
		
		return dao.findAllWithParentId(comment);
		
	}
}
