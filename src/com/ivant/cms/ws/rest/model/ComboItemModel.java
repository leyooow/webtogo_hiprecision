package com.ivant.cms.ws.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import com.ivant.cms.entity.CategoryItem;

@XmlRootElement(name = "ComboItem")
public class ComboItemModel extends AbstractModel{

	private String catid = " ";
	private String name = " ";
	private String price = "0.0";
	private String imageUrl = "";
	
	public ComboItemModel(){}
	
	public ComboItemModel(CategoryItem item) {
		setCatid(item.getId().toString());
		setName(item.getName());
		setPrice(Double.toString(item.getPrice()));
		
		if(item.getImages().size() > 0) {
			setImageUrl(item.getImages().get(0).getOriginal());
		} else {
			setImageUrl("image");
		}
		
	}
	
	public ComboItemModel(String id, String name, String price) {
		setCatid(id);
		setName(name);
		setPrice(price);
		setImageUrl("image");
	}

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
