package com.ivant.cms.action.dwr;

import java.util.List;

public class DWRItemVariation {

	private long id;
	private String name;
	private String sku;
	private float price;
	private float weight;
	private List<String> images;
	
	public DWRItemVariation(long id, String name, String sku, float price, float weight) {
		this.id = id;
		this.name = name;
		this.sku = sku;
		this.price = price;
		this.weight = weight;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public List<String> getImages() {
		return images;
	}
	
	public void setImages(List<String> images) {
		this.images = images;
	}
	
}
