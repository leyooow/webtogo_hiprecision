package com.ivant.cms.entity;

import javax.persistence.Embeddable;

@Embeddable
public class ProductItem {
	private Long id;
	private String name;
	private String sku;
	private Float price;
	
	public ProductItem() {
		price = Float.valueOf(0F);
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSku() {
		return sku;
	}
	public Float getPrice() {
		return price;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
}