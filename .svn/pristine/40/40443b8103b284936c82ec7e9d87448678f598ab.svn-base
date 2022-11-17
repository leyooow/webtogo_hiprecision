package com.ivant.cms.action.admin.dwr;

import java.util.ArrayList;
import java.util.List;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.entity.CategoryItem;

/**
 * 
 * @author Samiel Gerard C. Santos
 * @param value to be parse and the type assumed
 * @return 
 * 		null if param-value is parsed as param-type
 * 		message if not parsed
 *
 */
public class DWRItemAttributeAction extends AbstractDWRAction {
	
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	
	public String getMessage(String value, String type, String name) {
		StringBuilder message = new StringBuilder();
		
		if(type.equals("numeric")){
			try{
				Integer.parseInt(value);
			}
			catch(Exception e){
				if(value.isEmpty()) return null;
				message.append(name);
				message.append(" value is not numeric.");
				return message.toString();
			}
			
		}
		
		return null;
	}
	

}
