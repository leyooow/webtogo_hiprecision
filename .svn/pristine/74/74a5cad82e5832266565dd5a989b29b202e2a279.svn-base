package com.ivant.cms.ws.rest.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.ItemImage;
import com.ivant.constants.CompanyConstants;

/**
 * 
 * @author Kent
 *
 */
@XmlRootElement(name = "CategoryItem")
public class CategoryItemModel extends AbstractModel 
{
	private String name;
	private String description;
	private List<String> imagesUrl;
	private List<String> imagesUrlOriginal;
	private Double price;
	private String sku;
	private Long categoryId;
	private Long groupId;
	private String shortDescription;
	private String searchTags;
	private String itemDate;
	private Map<String, String> otherFields;
	private Boolean featured;
	private int sortOrder;
	
	private String info1;
	
	public CategoryItemModel()
	{
		
	}
	
	public CategoryItemModel(CategoryItem categoryItem, List<String> otherFieldList)
	{
		DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
		
		if(categoryItem == null) return;
		
		setId(categoryItem.getId());
		setCreatedOn(df.format(categoryItem.getCreatedOn()));
		setUpdatedOn(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(categoryItem.getUpdatedOn()));
		setName(categoryItem.getName());
		setDescription(categoryItem.getDescriptionWithoutTags());
		setPrice(categoryItem.getPrice());
		setSku(categoryItem.getSku());
		setCategoryId(categoryItem.getParent().getId());
		setGroupId(categoryItem.getParentGroup().getId());
		setShortDescription(categoryItem.getShortDescriptionWithoutTags());
		setSearchTags(categoryItem.getSearchTags());
		setFeatured(categoryItem.getFeatured());
		setSortOrder(categoryItem.getSortOrder());
		
		if(categoryItem.getParentGroup() != null){
			if(categoryItem.getParentGroup().getId().longValue() == CompanyConstants.WENDYS_STORE_GROUP_ID.longValue()){
				if(categoryItem.getParent() != null){
					setInfo1(categoryItem.getParent().getName());
				}
			}
		}
		if(categoryItem.getItemDate() != null)
		{
			setItemDate(df.format(categoryItem.getItemDate()));
		}
		
		/** item images */
		imagesUrl = new ArrayList<String>();
		imagesUrlOriginal = new ArrayList<String>();
		List<ItemImage> catItemImages = categoryItem.getImages();
		if(catItemImages != null)
		{
			for(ItemImage catItemImage : catItemImages)
			{
				imagesUrl.add(catItemImage.getImage1());
				imagesUrlOriginal.add(catItemImage.getOriginal());
			}
		}
		
		
		/** other fields */
		otherFields = new HashMap<String, String>();
		final Set<CategoryItemOtherField> categoryItemOtherFields = categoryItem.getCategoryItemOtherFields();
		if(categoryItemOtherFields != null && otherFieldList != null && !otherFieldList.isEmpty())
		{
			for(CategoryItemOtherField catItemOtherField : categoryItemOtherFields)
			{
				final String fieldName = catItemOtherField.getOtherField().getName();
				
				if(otherFieldList.contains(fieldName))
				{
					otherFields.put(fieldName, catItemOtherField.getContent());
				}
			}
		}
		
		
	}
	

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Boolean getFeatured() {
		return featured;
	}

	public void setFeatured(Boolean featured) {
		this.featured = featured;
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

	public List<String> getImagesUrl() {
		return imagesUrl;
	}

	public void setImagesUrl(List<String> imagesUrl) {
		this.imagesUrl = imagesUrl;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getSearchTags() {
		return searchTags;
	}

	public void setSearchTags(String searchTags) {
		this.searchTags = searchTags;
	}

	public Map<String, String> getOtherFields() {
		return otherFields;
	}

	public void setOtherFields(Map<String, String> otherFields) {
		this.otherFields = otherFields;
	}

	public String getItemDate() {
		return itemDate;
	}

	public void setItemDate(String itemDate) {
		this.itemDate = itemDate;
	}

	public List<String> getImagesUrlOriginal() {
		return imagesUrlOriginal;
	}

	public void setImagesUrlOriginal(List<String> imagesUrlOriginal) {
		this.imagesUrlOriginal = imagesUrlOriginal;
	}

	public String getInfo1() {
		return info1;
	}
	
	public void setInfo1(String info1){
		this.info1 = info1;
	}
}
