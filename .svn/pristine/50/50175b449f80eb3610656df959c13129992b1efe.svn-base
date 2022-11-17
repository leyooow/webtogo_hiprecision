package com.ivant.cms.delegate;

import com.ivant.cms.db.CommentLikeDAO;
import com.ivant.cms.entity.Comment;
import com.ivant.cms.entity.CommentLike;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.list.ObjectList;

public class CommentLikeDelegate extends AbstractBaseDelegate<CommentLike, CommentLikeDAO> {
	
	public CommentLikeDelegate() {
		super(new CommentLikeDAO());
	}
	
	private static CommentLikeDelegate instance = new CommentLikeDelegate();
	
	public static CommentLikeDelegate getInstance() {
		return instance;
	}
	
	public CommentLike findByCommentByMember(Comment comment, Member member) {			
		
		return dao.findByCommentByMember(comment, member);
		
		
	}

}
