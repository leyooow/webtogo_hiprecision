package com.ivant.cms.action.dwr;

import com.ivant.cms.delegate.CategoryItemComponentDelegate;
import com.ivant.cms.delegate.ComponentDelegate;
import com.ivant.cms.entity.CategoryItemComponent;
import com.ivant.cms.entity.Component;

public class DWRComponentAction {

	private CategoryItemComponentDelegate categoryItemComponentDelegate = CategoryItemComponentDelegate.getInstance(); 
	private ComponentDelegate componentDelegate = ComponentDelegate.getInstance();
	
	public Boolean update(Long categoryItemComponentId, Long componentId, String equation) {
		
		CategoryItemComponent item = categoryItemComponentDelegate.find(categoryItemComponentId);
		Component component = componentDelegate.find(componentId);
		
		item.setComponent(component);
		item.setEquation(equation);
		
		return categoryItemComponentDelegate.update(item);
	}
	
	public Boolean delete(Long categoryItemComponentId) {
		
		CategoryItemComponent item = categoryItemComponentDelegate.find(categoryItemComponentId);
		if(item != null)
			return categoryItemComponentDelegate.delete(item);
		
		return Boolean.FALSE;
	}
}
