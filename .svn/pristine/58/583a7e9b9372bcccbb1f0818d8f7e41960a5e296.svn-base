package com.ivant.cms.ws.rest.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryImage;
import com.ivant.cms.entity.Group;

/**
 * 
 * @author Kent
 *
 */
@XmlRootElement(name = "Category")
public class CategoryModel extends AbstractModel
{
	private String name;
	private String description;
	private Long parentGroup;
	private Long parentCategory;
	private List<String> imagesUrl;
	
	public CategoryModel()
	{
	}
	
	public CategoryModel(Category category)
	{
		if(category == null) return;
		
		
		
		
		setId(category.getId());
		setCreatedOn(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(category.getCreatedOn()));
		setUpdatedOn(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(category.getUpdatedOn()));
		setName(category.getName());
		setDescription(category.getDescriptionWithoutTags());
		
		Group group = category.getParentGroup();
		if(group != null)
		{
			setParentGroup(category.getParentGroup().getId());
		}
		Category parent = category.getParentCategory();
		if(parent != null)
		{
			setParentCategory(category.getParentCategory().getId());
		}
		
		imagesUrl = new ArrayList<String>();
		List<CategoryImage> catImages = category.getImages();
		if(catImages != null)
		{
			for(CategoryImage catImage : catImages)
			{
				imagesUrl.add(catImage.getImage1());
			}
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


	public Long getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(Long parentGroup) {
		this.parentGroup = parentGroup;
	}

	public Long getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Long parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<String> getImagesUrl() {
		return imagesUrl;
	}

	public void setImagesUrl(List<String> imagesUrl) {
		this.imagesUrl = imagesUrl;
	}
}
