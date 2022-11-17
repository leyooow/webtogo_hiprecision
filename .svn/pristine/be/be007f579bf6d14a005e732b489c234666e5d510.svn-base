package com.ivant.cms.action.dwr;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.ivant.cms.action.admin.dwr.AbstractDWRAction;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;

public class DWRSinglePageAction extends AbstractDWRAction {
	
	private static final Logger logger = Logger.getLogger(DWRSinglePageAction.class);
	
	public List<SinglePage> loadGurkkaTrivia(String keyWord){
		logger.info("DWRSinglePageAction.loadGurkkaTrivia..........");
		
		Order[] orders = {Order.asc("name")};
		List<SinglePage> listGurkkaTrivia = new ArrayList<SinglePage>();
		List<SinglePage> listGurkkaTrivia2 = new ArrayList<SinglePage>();
		List<SinglePage> listGurkkaTrivia3 = new ArrayList<SinglePage>();
		MultiPage mp = new MultiPage();
		// mp = multiPageDelegate.find(company, GurkkaConstants.TRIVIA_MULTIPAGE_NAME);
		
		if(mp != null){
			listGurkkaTrivia = singlePageDelegate.findAll(company, mp, orders).getList();
		}
		if(listGurkkaTrivia.size() > 0){
			for(SinglePage sp_ : listGurkkaTrivia){
				if(sp_!= null){
					listGurkkaTrivia2.add(sp_);
				}
			}
		}
		//logger.info("Retrieve size : "+ listGurkkaTrivia.size());
		return listGurkkaTrivia2;
	}
}
