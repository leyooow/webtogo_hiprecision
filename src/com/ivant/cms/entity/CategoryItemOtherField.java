package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="CategoryItemOtherField")
@Table(name="category_item_other_field")
public class CategoryItemOtherField
		extends CompanyBaseObject implements Cloneable
{
	private OtherField otherField;
	private CategoryItem categoryItem;
	private String content;
	protected transient Language language;
	
	private  List<CategoryItemOtherFieldLanguage> categoryItemOtherFieldLanguageList;
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	@Transient
	public Language getLanguage() {
		return language;
	}
	
	@ManyToOne(targetEntity = OtherField.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "other_field_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public OtherField getOtherField() {
		return otherField;
	}
	
	public void setOtherField(OtherField otherField) {
		this.otherField = otherField;
	}
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_item_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	
	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	/**
	 * Get the content.
	 * 
	 * @return the content
	 */
	@Basic
	@Column(name = "content", length=2147483647)
	public String getContent()
	{
		if(getCategoryItemOtherFieldLanguageList()!=null && language!=null){
			for(CategoryItemOtherFieldLanguage temp :getCategoryItemOtherFieldLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp.getContent();
			}
		}
		return content;
	}
	
	/**
	 * Set the content.
	 * 
	 * @param content
	 */
	public void setContent(String content)
	{
		this.content = content;
	}
	
	@Override
	protected CategoryItemOtherField clone() throws RuntimeException {
		CategoryItemOtherField categoryItemOtherField = null;
		try {
			categoryItemOtherField = (CategoryItemOtherField)super.clone();
		}
		catch(CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
		return categoryItemOtherField;
	}
	
	
	@OneToMany(targetEntity = CategoryItemOtherFieldLanguage.class, mappedBy = "defaultCategoryItemOtherField", fetch = FetchType.LAZY)
	public List<CategoryItemOtherFieldLanguage> getCategoryItemOtherFieldLanguageList() {
		return categoryItemOtherFieldLanguageList;
	}
	public void setCategoryItemOtherFieldLanguageList(
			List<CategoryItemOtherFieldLanguage> categoryItemOtherFieldLanguageList) {
		this.categoryItemOtherFieldLanguageList = categoryItemOtherFieldLanguageList;
	}
	@Transient
	public CategoryItemOtherFieldLanguage getCategoryItemOtherFieldLanguage() {
		if (getCategoryItemOtherFieldLanguageList() != null && language != null)
			for (CategoryItemOtherFieldLanguage temp : getCategoryItemOtherFieldLanguageList()) {
				if (temp.getLanguage().equals(this.language))
					return temp;
			}
		return null;
	}
	
}
