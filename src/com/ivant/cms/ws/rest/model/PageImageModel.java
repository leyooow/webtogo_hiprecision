package com.ivant.cms.ws.rest.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.ivant.utils.HTMLTagStripper;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.PageImage;

/**
 * @author Eric John Apondar
 * @since October 2015
 */
public class PageImageModel extends AbstractModel{
	private String filename;
	private String original;
	private String image1;
	private String image2;
	private String image3;
	private String thumbnail;
	private String title;
	private String caption;
	private String description;
	
	private String validityDate;
	private String lock;
	
	private String appTracking;
	
	public PageImageModel(){}
	
	public PageImageModel(PageImage pageImage){
		if(pageImage == null) return;
		DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
		
		setId(pageImage.getId());
		setCreatedOn(df.format(pageImage.getCreatedOn()));
		
		setFilename(pageImage.getFilename());
		setOriginal(pageImage.getOriginal());
		setImage1(pageImage.getImage1());
		setImage2(pageImage.getImage2());
		setImage3(pageImage.getImage3());
		setThumbnail(pageImage.getThumbnail());
		setTitle(pageImage.getTitle());
		setCaption(pageImage.getCaption());
		setDescription(pageImage.getDescription());
	}
	
	public PageImageModel(CategoryItem item){
		if(item == null) return;
		DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
		
		if(item.getImages() == null) return;
		if(item.getImages().size() < 1) return;
		
		setId( item.getImages().get(0).getId() );
		setCreatedOn(df.format( item.getImages().get(0).getCreatedOn() ));
		
		setFilename(item.getImages().get(0).getFilename());
		setOriginal(item.getImages().get(0).getOriginal());
		setImage1(item.getImages().get(0).getImage1());
		setImage2(item.getImages().get(0).getImage2());
		setImage3(item.getImages().get(0).getImage3());
		setThumbnail(item.getImages().get(0).getThumbnail());
		
		setTitle(item.getName());
		setCaption(item.getShortDescription());
		setDescription(item.getDescription());
		
		setValidityDate(item.getSku());
		setLock(item.getSku());
		
		try{
			setAppTracking(item.getCategoryItemOtherFieldMap().get("App Tracking").getContent());
		}catch(Exception e){
		}
	}
	
	/** getters and setters **/
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		if(title != null) 	this.title = HTMLTagStripper.stripTags(title.replaceAll("(\\r|\\n|\\t)", ""));
		else this.title = title;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		if(caption != null)	this.caption = HTMLTagStripper.stripTags3(caption.replaceAll("(\\r|\\t)", ""));
		else this.caption = caption;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		if(description != null)	this.description = HTMLTagStripper.stripTags3(description.replaceAll("(\\r|\\t)", ""));
		else this.description = description;
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

	public String getAppTracking() {
		return appTracking;
	}

	public void setAppTracking(String appTracking) {
		this.appTracking = appTracking;
	}
}
