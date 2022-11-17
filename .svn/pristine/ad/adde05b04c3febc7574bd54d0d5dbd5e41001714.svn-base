
package com.ivant.cms.action.admin.dwr;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.SinglePage;

public class DWRItemCommentAction extends AbstractDWRAction {

	private static final Logger logger = Logger.getLogger(DWRItemCommentAction.class);
		
	public List<ItemComment> searchItemComment(long id) {
		
		SinglePage singlePage = singlePageDelegate.find(id);
		return itemCommentDelegate.getCommentsByPage(singlePage, Order.desc("createdOn")).getList();
		
	}
	
	public String updateItemComment(long id, Boolean published) {
		
		ItemComment itemComment = itemCommentDelegate.find(id);
		itemComment.setPublished(published);
		itemCommentDelegate.update(itemComment);
		return "success";
		
	}
	
}
