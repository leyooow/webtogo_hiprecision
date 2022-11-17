package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.Entity;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity
@Table(name="item_variation_image")
public class ItemVariationImage extends BaseObject {

	private ItemVariation itemVariation;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	
	public ItemVariationImage() {
		
	}
	
	@ManyToOne(targetEntity = ItemVariation.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_variation_id", nullable=false)
	public ItemVariation getItemVariation() {
		return itemVariation;
	}

	public void setItemVariation(ItemVariation itemVariation) {
		this.itemVariation = itemVariation;
	}

	@Basic
	@Column(name="image1")
	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	@Basic
	@Column(name="image2")
	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	@Basic
	@Column(name="image3")
	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	@Basic
	@Column(name="image4")
	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}
}
