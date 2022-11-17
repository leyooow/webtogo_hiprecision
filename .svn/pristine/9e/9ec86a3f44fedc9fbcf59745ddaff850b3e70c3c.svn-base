package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity
@Table(name="other_detail")
public class OtherDetail extends CompanyBaseObject {
	
	public CategoryItem face;
	public CategoryItem strap;
	public String text;
	public String textColor;
	public Integer textSize;
	public String textFont;
	public String handColor;
	public String pathDetail;
	
	@OneToOne(targetEntity = CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "face_id", nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	public CategoryItem getFace() {
		return face;
	}

	public void setFace(CategoryItem face) {
		this.face = face;
	}

	@OneToOne(targetEntity = CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "strap_id", nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	public CategoryItem getStrap() {
		return strap;
	}

	public void setStrap(CategoryItem strap) {
		this.strap = strap;
	}

	@Basic
	@Column(name = "text", length = 5000, nullable = true)
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Basic
	@Column(name = "text_color", length = 100, nullable = true)
	public String getTextColor() {
		return textColor;
	}
	
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	
	@Basic 
	@Column(name = "text_size", nullable=true)
	public Integer getTextSize() {
		return textSize;
	}
	
	public void setTextSize(Integer textSize) {
		this.textSize = textSize;
	}
	
	@Basic
	@Column(name = "text_font", length = 100, nullable = true)
	public String getTextFont() {
		return textFont;
	}
	
	public void setTextFont(String textFont) {
		this.textFont = textFont;
	}
	
	@Basic
	@Column(name = "hand_color", length = 100, nullable = true)
	public String getHandColor() {
		return handColor;
	}
	
	public void setHandColor(String handColor) {
		this.handColor = handColor;
	}

	@Basic
	@Column(name = "path_detail", length = 5000, nullable = true)
	public String getPathDetail() {
		return pathDetail;
	}
	
	public void setPathDetail(String pathDetail) {
		this.pathDetail = pathDetail;
	}

}
