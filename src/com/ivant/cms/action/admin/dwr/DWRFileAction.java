package com.ivant.cms.action.admin.dwr;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ivant.cms.entity.ItemFile;

public class DWRFileAction extends AbstractDWRAction {
	
	private static final Logger logger = Logger.getLogger(DWRFileAction.class);
	
	public void saveNewOrder(List<Long> items) {
		
		List<ItemFile> itemFiles = new ArrayList<ItemFile>();
		int count = 1;
		
		for(Long id : items) {
			ItemFile itm = itemFileDelegate.find(id);
			itm.setSortOrder(count++);
			
			if(itm != null) {
				try{
					if(itm.getDescription() == null) itm.setDescription("");;
					if(itm.getTitle() == null) itm.setTitle("");
				}catch(Exception e){}
				
				itemFiles.add(itm);
			}
			else {
				logger.fatal("Problem sorting items since some items does not belong to the given company");
				return;
			}
		}
		
		itemFileDelegate.batchUpdate(itemFiles);
		
	}

}
