package com.ivant.cms.action.admin.dwr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.entity.SavedEmail;

public class DWRFormSubmissionAction extends AbstractDWRAction {

	private static final Logger logger = Logger.getLogger(DWRImageAction.class);
	
	public String getMessage(long id) {
		SavedEmail savedEmail = savedEmailDelegate.find(id);
		if(savedEmail.getCompany().equals(company)) {
			return savedEmail.getEmailContent().replace("\n", "<br>");
		}
		return "";
	}	
	
	public List<SavedEmail> getSavedEmailByFormName(String formName) {
		
		if(company.getName().equals("mundipharma2")) {
			List<SavedEmail> list = savedEmailDelegate.findEmailByFormName(company, formName, Order.asc("createdOn")).getList();
			List<SavedEmail> items = new ArrayList<SavedEmail>();
			HashMap<String, SavedEmail> set = new HashMap<String, SavedEmail>();
			Collections.reverse(list);
			for(SavedEmail se : list) {
				String begin = se.getEmailContent().split("###")[0];
				String bNumber = begin.split(":")[1];
				if(!set.containsKey(bNumber)) {
					items.add(se);
					set.put(bNumber, se);
				}
			}
			return items;
		}
		return savedEmailDelegate.findEmailByFormName(company, formName, Order.asc("createdOn")).getList();
	}
}
