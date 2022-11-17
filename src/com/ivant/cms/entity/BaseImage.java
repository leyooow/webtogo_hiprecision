package com.ivant.cms.entity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.entity.interfaces.Image;
@MappedSuperclass
public abstract class BaseImage extends BaseObject implements Image {

//	private String url;
	private String filename;
	private String original;
	private String image1;
	private String image2;
	private String image3;
	private String thumbnail;
	private String title;
	private String caption;
	private String description;
	

	@Basic
	@Column(name="original")
	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
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
	@Column(name="thumbnail")
	public String getThumbnail() {
		return thumbnail;
	}
	
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Basic
	@Column(name="filename", nullable=false)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Basic
	@Column(name="caption")
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Basic
	@Column(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Basic
	@Column(name="description", length=1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
