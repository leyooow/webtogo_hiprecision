package com.ivant.cms.ws.rest.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;

@XmlRootElement(name = "Combo")
public class ComboModel extends AbstractModel {

	private String name = " ";
	private List<ComboItemModel> comboItems = new ArrayList<ComboItemModel>();
	
	public ComboModel(){}
	
	public ComboModel(String name, String contents, CategoryDelegate categoryDelegate, CategoryItemDelegate categoryItemDelegate) {
		setName(name);
		System.out.println("contents : " + contents);
		if(!name.equalsIgnoreCase("Combo_Item") && !name.equalsIgnoreCase("Combo_Meal") && !name.equalsIgnoreCase("Drinks Size")) {
			System.out.println("flag : 1");
			if(!name.equalsIgnoreCase("Float") && !name.equalsIgnoreCase("Iced Tea")) {
				System.out.println("flag : 2");
				List<Long> ids = new ArrayList<Long>();
				for(String s : contents.split(";")) {
					if(s.trim().length() > 0) {
						
						Long id = null;
						try {
							id = new Long(s);
						} catch(Exception a) {}
						
						if(id != null) {
							ids.add(id);
						}
					}
				}
				
				if(ids.size() > 0) {
					Collections.sort(ids);
					for(Long id : ids) {
						CategoryItem catitem = categoryItemDelegate.find(id);
						if(catitem != null) {
							comboItems.add(new ComboItemModel(catitem));
						}
					}
				}
			} else {
				System.out.println("flag : 3");
				List<Long> ids = new ArrayList<Long>();
				for(String s : contents.split(";")) {
					if(s.trim().length() > 0) {
						
						Long id = null;
						try {
							id = new Long(s);
						} catch(Exception a) {}
						
						if(id != null) {
							ids.add(id);
						}
					}
				}
				
				if(ids.size() > 0) {
					Collections.sort(ids);
					for(Long id : ids) {
						Category category = categoryDelegate.find(id);
						for(CategoryItem sub : category.getEnabledItems()) {
							comboItems.add(new ComboItemModel(sub));
						}
					}
				}
			}
		} else if (name.equalsIgnoreCase("Combo_Meal")) {
			System.out.println("flag : 1");
			setName("Drinks Size");
			if(contents.equals("8465")) {
				Category category = categoryDelegate.find(8883L);
				comboItems.add(new ComboItemModel("0","Regular Combo","0.0"));
				for(CategoryItem sub : category.getEnabledItems()) {
					comboItems.add(new ComboItemModel(sub));
				}
			} else if(contents.equals("8876")) {
				Category category = categoryDelegate.find(8884L);
				comboItems.add(new ComboItemModel("0","Regular Combo","0.0"));
				for(CategoryItem sub : category.getEnabledItems()) {
					comboItems.add(new ComboItemModel(sub));
				}
			}
		}
		
		
	}

	public String getName() {
		return name;
	}

	public List<ComboItemModel> getComboItems() {
		return comboItems;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setComboItems(List<ComboItemModel> comboItems) {
		this.comboItems = comboItems;
	}
}
