package com.ivant.cms.ws.rest.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.utils.HTMLTagStripper;
/**
 * 
 * @author Anjerico D. Gutierrez
 * @since July 2015
 */
@XmlRootElement(name = "MultiPage")
public class MultiPageModel extends AbstractModel{
	private String name;
	private String title;
	private String description;
	private boolean featured;
	private List<SinglePage2Model> items;
	
	public MultiPageModel(){
		
	}
	
	public MultiPageModel(MultiPage multiPage){
		if(multiPage == null){
			return;
		}
		
		setId(multiPage.getId());
		setCreatedOn(multiPage.getCreatedOn().toString());
		setName(multiPage.getName());
		setTitle(multiPage.getTitle());
		setDescription(multiPage.getDescription());
		setFeatured(multiPage.getFeatured());
		
		if(multiPage.getItems() != null){
			List<SinglePage2Model> list = new ArrayList<SinglePage2Model>();
			for(SinglePage sp : multiPage.getItems()){
				list.add(new SinglePage2Model(sp));
			}
			setItems(list);
		}
		
		
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
		//return HTMLTagStripper.stripTags(description);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public List<SinglePage2Model> getItems() {
		return items;
	}

	public void setItems(List<SinglePage2Model> items) {
		this.items = items;
	}

	
	
}
