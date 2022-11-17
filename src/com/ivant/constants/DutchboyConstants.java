package com.ivant.constants;

public class DutchboyConstants {
	public static final String FILENAME_CATEGORY = "category.txt";
	public static final String FILENAME_CATEGORY_ITEM = "category_item.txt";
	public static final String FILENAME_ITEM_IMAGES = "item_images.txt";
	public static final String FILENAME_CATEGORY_ITEM_COMPONENT = "category_item_component.txt";
	public static final String FILENAME_COMPONENT = "component.txt";
	public static final String FILENAME_COMPONENT_CATEGORY = "component_category.txt";
	public static final String FILENAME_FAQ = "faq.txt";
	public static final String FILENAME_PAINTING101 = "paintingmanual.txt";
	public static final String FILENAME_NEWS = "news.txt";
	public static final String FILENAME_HOWSTO = "howsto.txt";
	
	/** CATEGORY FIELDS */
	public static final String [] FIELDS_CATEGORY = {
		"getId",
		"getCreatedOn",
		"getIsValid",
		"getUpdatedOn",
		"getName",
		"getHidden",
		"getSortOrder",
		"getCreatedBy",
		"getCompany",
		"getParentCategory",
		"getParentGroup"
//		"description",
//		"title",
	};
	
	/** CATEGORY ITEM FIELDS */
	public static final String [] FIELDS_CATEGORY_ITEM = {
		"getId",
		"getCreatedOn",
		"getIsValid",
		"getUpdatedOn",
		"getDescription",
		"getShortDescription",
		"getDisabled",
		"getFeatured",
		"getName",
		"getPrice",
		"getSku",
		"getSortOrder",
		"getParentGroup",
		"getParent",
		"getCompany",
		"getShippingPrice",
		"getIsOutOfStock",
		"getSearchTags",
		"getCategoryItemOtherFieldMap"
	};
	
	public static final String [] FIELDS_ITEM_IMAGES = {
		"getId",
		"getCreatedOn",
		"getIsValid",
		"getUpdatedOn",
		"getItem",
		"getCaption",
		"getImage2",
		"getTitle",
		"getSortOrder",
		"getDescription"
	};
	
	/** CATEGORY ITEM COMPONENT FIELDS */
	public static final String [] FIELDS_CATEGORY_ITEM_COMPONENT = {
		"getId",
		"getCreatedOn",
		"getIsValid",
		"getUpdatedOn",
		"getValue",
		"getCategoryItem",
		"getComponent",
		"getEquation",
		"getVariable"
	};
	
	public static final String [] FIELDS_COMPONENT = {
		"getId",
		"getCreatedOn",
		"getIsValid",
		"getUpdatedOn",
		"getName",
		"getCategory"
	};
	
	public static final String [] FIELDS_COMPONENT_CATEGORY = {
		"getId",
		"getCreatedOn",
		"getIsValid",
		"getUpdatedOn",
		"getName",
	};
	
	public static final String [] FIELDS_MULTIPAGE_ITEM = {
		"getId",
		"getCreatedOn",
		"getIsValid",
		"getUpdatedOn",
		"getName",
		"getContent",
		"getSubtitle"
	};

}
