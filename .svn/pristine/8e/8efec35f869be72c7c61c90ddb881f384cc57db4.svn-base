package com.ivant.cms.action.admin.dwr;

import com.ivant.cms.entity.MemberFileItems;

public class DWRMemberFileItemsAction extends AbstractDWRAction {

	public boolean update(String id, String title, String description) {
		MemberFileItems item = memberFileItemDelegate.find(new Long(id));
		item.setTitle(title);
		item.setDescription(description);
		memberFileItemDelegate.update(item);
		return true;		
	}
	
}
