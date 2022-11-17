package com.ivant.cms.entity;

import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.ivant.utils.HTMLTagStripper;

@Embeddable
public class ItemDetail {
	
	private String name;
	private String description;	
	private String sku;
	private Double price;
	private Double discountedPrice;
	private Double discount;
	private Double shippingPrice;
	private Double weight;
	private String image;
	private Long realID;
	private String uom;
	private Boolean isOutOfStock;
	private Integer availableQuantity;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name = "description", length=2147483647)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	@Basic
	@Column(name = "realID", length=1000)	
	public Long getRealID() {
		return realID;
	}

	public void setRealID(Long realID) {
		this.realID = realID;
	}

	public String generateFormattedPrice() {
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		return numberFormatter.format(price);
	}
	

	public String generateFormattedShippingPrice() {
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		return numberFormatter.format(shippingPrice);
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		if(null != name)
			stringBuilder.append("Item Name : " ).append(name);
		if(null != description)	
			stringBuilder.append("Item Description : ").append(description);
		if(null != sku)
			stringBuilder.append("Item Sku : ").append(sku);
		if(null != price)
			stringBuilder.append("Item Price : ").append(price);
		if(null != image)
			stringBuilder.append("Item Image : ").append(image);
		
		
		return stringBuilder.toString();
	}

	public void setShippingPrice(Double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public Double getShippingPrice() {
		return shippingPrice;
	}
	
	@Basic
	@Column(name = "weight")	
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @param discountedPrice the discountedPrice to set
	 */
	public void setDiscountedPrice(Double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	/**
	 * @return the discountedPrice
	 */
	@Basic
	@Column(name = "discounted_price", nullable=true)
	public Double getDiscountedPrice() {
		return discountedPrice;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * @return the discount
	 */
	@Basic
	@Column(name = "discount", nullable=true)
	public Double getDiscount() {
		if(discount == null)
		{
			this.discount = 0.00;
		}
		return discount;
	}
	
	public void setUOM(String uom) {
		// TODO Auto-generated method stub
		this.uom = uom;
	}

	public String getUOM() {
		// TODO Auto-generated method stub
		return uom;
	}

	public void setIsOutOfStock(Boolean isOutOfStock) {
		this.isOutOfStock = isOutOfStock;
	}

	@Transient
	public Boolean getIsOutOfStock() {
		return isOutOfStock;
	}



	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	@Transient
	public Integer getAvailableQuantity() {
		return availableQuantity;
	}
	
	@Transient
	public String getDescriptionWithoutTags() {
		return HTMLTagStripper.stripTags(getDescription());
	}
	
}
