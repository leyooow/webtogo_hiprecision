package com.ivant.cms.ws.rest.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ItemImage;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.utils.HTMLTagStripper;
/**
 * SinglePage model for Montero Sports
 * @author Eric John Apondar
 * @since October 2015
 */
@XmlRootElement(name = "SinglePage")
public class SinglePage2Model extends AbstractModel{
	private String name;
	private String title;
	private String content;
	private List<PageImageModel> images;
	
	private String validityDate;
	private String lock;
	
	public SinglePage2Model(){
		
	}
	
	public SinglePage2Model(SinglePage singlePage){
		if(singlePage == null){
			return;
		}
		
		setId(singlePage.getId());
		setCreatedOn(singlePage.getCreatedOn().toString());
		setName(singlePage.getName());
		setTitle(singlePage.getTitle());	
		setContent(singlePage.getContent());
		
		if(singlePage.getImages() != null){
			List<PageImageModel> imageModelList = new ArrayList<PageImageModel>();
			for(PageImage image : singlePage.getImages()){
				imageModelList.add(new PageImageModel(image));
			}
			setImages(imageModelList);
		}
		
		setValidityDate(singlePage.getSubTitle());
		setLock(singlePage.getSubTitle());
	}
	
	public SinglePage2Model(SinglePage singlePage, Category category){
		
		if(singlePage == null){
			return;
		}
		
		setId(singlePage.getId());
		setCreatedOn(singlePage.getCreatedOn().toString());
		setName(singlePage.getName());
		setTitle(singlePage.getTitle());	
		setContent(singlePage.getContent());
		
		
		if(category!=null){
			List<CategoryItem> catItems = category.getEnabledItems();
			if(catItems != null){
				List<PageImageModel> imageModelList = new ArrayList<PageImageModel>();
				for(CategoryItem item : catItems){
					imageModelList.add(new PageImageModel(item));
				}
				setImages(imageModelList);
			}
		}
		
		setValidityDate(singlePage.getSubTitle());
		setLock(singlePage.getSubTitle());
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		if(content != null) this.content = HTMLTagStripper.stripTags3(content.replaceAll("(\\r|\\t)", ""));
		else this.content = content;
	}

	public List<PageImageModel> getImages() {
		return images;
	}

	public void setImages(List<PageImageModel> images) {
		this.images = images;
	}

	public String getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(String validityDate) {
		SimpleDateFormat f = new SimpleDateFormat("MM-dd-yyyy");
		try{
			f.parse(validityDate);
			this.validityDate = validityDate;
		}catch(Exception e){
			this.validityDate = null;
		}
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = "false";
		if(lock != null){
			SimpleDateFormat f = new SimpleDateFormat("MM-dd-yyyy");
			if("lock".equalsIgnoreCase(lock)){
				this.lock = "true";
			}else{
				try{
					f.parse(lock);
					this.lock = "true";
				}catch(Exception e){
					this.lock = "false";
				}
			}
		}
	}
}
