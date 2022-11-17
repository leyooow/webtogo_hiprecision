package com.ivant.cms.ws.rest.model;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
/**
 * Dealer model for Montero Sports
 * @author Eric John Apondar
 * @since October 2015
 */
@XmlRootElement(name = "MonteroDealer")
public class MonteroDealerModel extends AbstractModel{
	private String name;
	private String address;
	private String email;
	private List<String> contact;
	private String lat;
	private String lng;
	private Long categoryId;
	private String categoryName;
	
	
	public MonteroDealerModel(){}
	
	public MonteroDealerModel(CategoryItem categoryItem){
		if(categoryItem == null) return;
		
		DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
		
		setId(categoryItem.getId());
		setCreatedOn(df.format(categoryItem.getCreatedOn()));
		setName(categoryItem.getName());
		setLatLang(categoryItem.getSearchTags());
		setAddress(categoryItem.getSku());
		
		Map<String, CategoryItemOtherField> otherFieldMap = categoryItem.getCategoryItemOtherFieldMap();
		if(otherFieldMap.containsKey("Email Address")) setEmail(otherFieldMap.get("Email Address").getContent());
		if(otherFieldMap.containsKey("Contact")) {
			if(otherFieldMap.get("Contact").getContent() != null){
				String[] arr = otherFieldMap.get("Contact").getContent().trim().split("/");
				if(arr != null){
					if(arr.length > 0){
						for(int i=0; i < arr.length; i++){
							arr[i] = arr[i].replaceAll("[^\\d]", "").trim();
						}
						setContact(Arrays.asList(arr));
					}
				}
			}
		}
		
		setCategoryId(categoryItem.getParent().getRootCategory().getId());
		setCategoryName(categoryItem.getParent().getRootCategory().getName());
		
	}
	
	/** private methods **/
	
	private void setLatLang(String searchTags) {
		if(searchTags == null) return;
		String latLang[] = searchTags.split(",");
		if(latLang.length == 2){
			setLat(latLang[0].trim());
			setLng(latLang[1].trim());
		}
	}

	/** getters and setters **/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getContact() {
		return contact;
	}
	public void setContact(List<String> contact) {
		this.contact = contact;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

}
