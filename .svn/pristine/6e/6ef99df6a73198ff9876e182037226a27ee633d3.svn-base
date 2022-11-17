package com.ivant.cms.action.admin.dwr;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ivant.cms.entity.SinglePage;

public class DWRMultiPageAction extends AbstractDWRAction {

	private static final Logger logger = Logger.getLogger(DWRMultiPageAction.class);
	
	public void saveNewOrder(List<Long> items) {	
		// get all the single page items
		List<SinglePage> singlePages = new ArrayList<SinglePage>();
		int count = 1;
		
		for(Long l : items) {
			SinglePage sp = singlePageDelegate.find(l);
			sp.setSortOrder(count++);
			
			if(sp != null && sp.getCompany().equals(company)) {
				try{
					if(sp.getCreatedBy() == null) sp.setCreatedBy(userDelegate.load(user.getId()));
					if(sp.getContent() == null) sp.setContent("");
					if(sp.getName() == null) sp.setName("");
				}catch(Exception e){}
				
				singlePages.add(sp);
			}
			else {
				logger.fatal("Problem sorting items since some items does not belong to the given company");
				return;
			}
		}
		 
		// update the database
		singlePageDelegate.batchUpdate(singlePages); 
	}
}
