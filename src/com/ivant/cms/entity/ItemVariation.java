package com.ivant.cms.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity
@Table(name="item_variation")
public class ItemVariation extends CompanyBaseObject {

	private CategoryItem categoryItem;
	private String name;
	private String sku;
	private float price;
	private float weight;
	private List<ItemVariationImage> images;
	
	public ItemVariation() {
		setIsValid(true);
		price = 0.0f;
		weight = 0.0f;
	}

	@ManyToOne(targetEntity=CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name="item", nullable=false)
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}

	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}

	@Basic
	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name="sku", length=32, nullable=false, unique=true)
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Basic
	@Column(name="price", nullable=false)
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Basic
	@Column(name="weight", nullable=false)
	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	@OneToMany(targetEntity=ItemVariationImage.class, fetch=FetchType.LAZY, mappedBy="itemVariation")
	@Where(clause = "valid=1")
	public List<ItemVariationImage> getImages() {
		return images;
	}

	public void setImages(List<ItemVariationImage> images) {
		this.images = images;
	}
	
	@Transient
	public List<String> getImagesString() {
		List<String> imagesStr = new ArrayList<String>();
		
		for(ItemVariationImage img : images) {
			imagesStr.add(img.getImage1());
			imagesStr.add(img.getImage2());
			imagesStr.add(img.getImage3());
			imagesStr.add(img.getImage4());
			
		}
		
		return imagesStr;
	}

}
