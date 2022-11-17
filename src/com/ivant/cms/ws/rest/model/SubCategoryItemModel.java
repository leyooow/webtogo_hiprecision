package com.ivant.cms.ws.rest.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.util.StringUtils;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.ItemImage;

@XmlRootElement(name = "SubCategoryItem")
public class SubCategoryItemModel extends AbstractModel {

	private String name = " ";
	private String description = " ";
	private List<String> imagesUrl = new ArrayList<String>();
	private List<String> imagesUrlOriginal = new ArrayList<String>();
	private Double price = 0.0;
	private String sku = " ";
	private Long categoryId = 0L;;
	private Long groupId = 0L;
	private String shortDescription = " ";
	private String searchTags = " ";
	private String itemDate = " ";
	private List<ComboModel> combos = new ArrayList<ComboModel>();
	private Boolean featured = false;
	private int sortOrder = 0;
	
	public SubCategoryItemModel() {}
	
	public SubCategoryItemModel(CategoryItem categoryItem, CategoryDelegate categoryDelegate, CategoryItemDelegate categoryItemDelegate) {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
		
		if(categoryItem == null) return;
		System.out.println("sub category item id : " + categoryItem.getId().toString());
		setId(categoryItem.getId());
		setCreatedOn(df.format(categoryItem.getCreatedOn()));
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
		final Set<CategoryItemOtherField> categoryItemOtherFields = categoryItem.getCategoryItemOtherFields();
		if(categoryItemOtherFields != null)
		{
			for(CategoryItemOtherField catItemOtherField : categoryItemOtherFields)
			{
				final String fieldName = catItemOtherField.getOtherField().getName();
				if(catItemOtherField.getContent() != null) {
					combos.add(new ComboModel(fieldName, catItemOtherField.getContent(), categoryDelegate, categoryItemDelegate));
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

	public List<ComboModel> getCombos() {
		return combos;
	}

	public void setCombos(List<ComboModel> combos) {
		this.combos = combos;
	}

}
