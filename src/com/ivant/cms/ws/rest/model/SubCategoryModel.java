package com.ivant.cms.ws.rest.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;

@XmlRootElement(name = "SubCategory")
public class SubCategoryModel extends AbstractModel {
	
	private static final String PHP = " - PHP ";
	
	private static final DecimalFormat currency_formmater = new DecimalFormat("#.00");
	
	private String name = " ";
	private String description = " ";
	private String imageUrl = "image";
	private List<SubCategoryItemModel> items = new ArrayList<SubCategoryItemModel>();

	public SubCategoryModel() {}
	
	public SubCategoryModel(Category category, CategoryDelegate categoryDelegate, CategoryItemDelegate categoryItemDelegate) {
	
		setId(category.getId());
		setCreatedOn(category.getCreatedOn().toString());
		setName(category.getName());
		
		StringBuilder desc = new StringBuilder();
		for(CategoryItem item : category.getEnabledItems()) {
			String itemName = item.getName().replaceAll(category.getName(), "");
			desc.append(itemName.trim().concat(PHP).concat(currency_formmater.format(item.getPrice())).concat("\n"));
			
			items.add(new SubCategoryItemModel(item, categoryDelegate, categoryItemDelegate));
		}
		setDescription(desc.toString().trim());
		
		if(category.getImages().size() > 0) {
			setImageUrl(category.getImages().get(0).getOriginal());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<SubCategoryItemModel> getItems() {
		return items;
	}

	public void setItems(List<SubCategoryItemModel> items) {
		this.items = items;
	}
}
