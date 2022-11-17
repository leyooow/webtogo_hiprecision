package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
//
import com.ivant.cms.entity.Comment;
import com.ivant.cms.entity.CommentLike;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.list.ObjectList;

public class CommentLikeDAO extends AbstractBaseDAO<CommentLike> {

	public CommentLikeDAO() {
		super();
	}
	
//	public ObjectList<CommentLike> findAllByComment(Comment comment) {			
//		Junction conj = Restrictions.conjunction();
//
//		
//		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
//		conj.add(Restrictions.eq("commentId", comment));
//			
//		return findAllByCriterion(null, null, null, null, conj);
//	}
//	
//	public ObjectList<CommentLike> findAllByMember(Member member) {			
//		Junction conj = Restrictions.conjunction();
//
//		
//		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
//		conj.add(Restrictions.eq("memberId", member));
//			
//		return findAllByCriterion(null, null, null, null, conj);
//	}
//	
//	
	public CommentLike findByCommentByMember(Comment comment, Member member) {			
		Junction conj = Restrictions.conjunction();

		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("comment", comment));
		conj.add(Restrictions.eq("member", member));
			
		ObjectList<CommentLike> commentLikes =  findAllByCriterion(null, null, null, null, conj);
		if(commentLikes.getSize() > 0) {
			return commentLikes.getList().get(0);
		}
		return null;
	}
}
