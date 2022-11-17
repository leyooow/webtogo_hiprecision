package com.ivant.cms.entity;

import java.util.Collections;
import java.util.LinkedList;
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
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="Brand")
@Table(name="brands")
public class Brand extends CompanyBaseObject {

	private Group group;
	private String name;
	private String description;
	private String title;
	private String tagline;
	private String shortDescription;
	private String notes;
	private String url;
	private boolean disabled;
	private List<BrandImage> images;
	private List<BrandImage> sortedImages;
	private boolean featured;
	private List<CategoryItem> items;
	private List<CategoryItem> itemsbyname;
	private int sortOrder = 0;
	
	private Brand parentBrand;
	private List<Brand> childrenBrand;
	
	public Brand() {
		disabled = false;
		featured = false;
	}
	
	@Basic
	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name="description" , length=4000)
	public String getDescription() {
		return description;
	}
	  
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Basic
	@Column(name="disabled", nullable=false)
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@OneToMany(targetEntity = BrandImage.class, mappedBy = "brand", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@NotFound(action=NotFoundAction.IGNORE)
	public List<BrandImage> getImages() {
		return images;
	}

	public void setImages(List<BrandImage> images) {
		this.images = images;
	}
	
	@OneToMany(targetEntity = BrandImage.class, mappedBy = "brand", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@javax.persistence.OrderBy("sortOrder asc")
	public List<BrandImage> getSortedImages() {
		return sortedImages;
	}
	 
	public void setSortedImages(List<BrandImage> images) {
		this.sortedImages = images;
	}

	public String providePageType() {
		return "default";
	}
	
	@ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Group getGroup() {
		return group;
	}

	@Basic 
	@Column(name="featured", nullable=false)
	public void setGroup(Group group) {
		this.group = group;
	}

	public boolean isFeatured() {
		return featured;
	}
	
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	@Basic
	@Column(name="sort_order")
	public int getSortOrder() {
		return sortOrder; 
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}	
	
	@OneToMany(targetEntity = CategoryItem.class, mappedBy = "brand", fetch = FetchType.LAZY)
	@Where(clause = "valid=1 and disabled=0")
	
	public List<CategoryItem> getItems() {
		return items;
	}
	
	public void setItems(List<CategoryItem> items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		return "group: " + ((group != null) ? group.getName() : "null") + "\n" +
				"name: " + getName() + "\n" +
				"sort order: " + getSortOrder() + "\n" +
				"disabled: " + isDisabled() + "\n";
	}

	public void setItemsbyname(List<CategoryItem> itemsbyname) {
		this.itemsbyname = itemsbyname;
	}
	
	@OneToMany(targetEntity = CategoryItem.class, mappedBy = "brand", fetch = FetchType.LAZY)
	@Where(clause = "valid=1 and disabled=0")
	@OrderBy(clause="name ASC")
	public List<CategoryItem> getItemsbyname() {
		return itemsbyname;
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
	@Column(name="tagline")
	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	@Basic
	@Column(name="short_description")
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Basic
	@Column(name="notes" , length=4000)
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Basic
	@Column(name="url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@ManyToOne(targetEntity = Brand.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_brand")
	@NotFound(action = NotFoundAction.IGNORE) 
	public Brand getParentBrand() {
		return parentBrand;
	}

	public void setParentBrand(Brand parentBrand) {
		this.parentBrand = parentBrand;
	}

	@OneToMany(targetEntity = Brand.class, mappedBy = "parentBrand", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	//@OrderBy(clause="sortOrder ASC")
	public List<Brand> getChildrenBrand() {
		return childrenBrand;
	}

	public void setChildrenBrand(List<Brand> childrenBrand) {
		this.childrenBrand = childrenBrand;
	}
	
	@Transient
	public String getDescriptor() {
		List<Brand>  listBrand= getParentBrands();
		StringBuffer sb = new StringBuffer();
		Company company = this.getCompany();
		if(company != null)
		for (Brand  brand : listBrand)
			sb.append(" << " + brand.getName());
		return sb.toString();
	}
	
	@Transient
	public List<Brand> getParentBrands(){
		
		return getParentBrand(this);
	}
	
	@Transient
	public List<Brand> getParentBrand(Brand brand) {
		List<Brand> list = new LinkedList<Brand>();
		Brand thisParentBrand = brand.getParentBrand();
		if (brand.getParentBrand() != null
				&& brand.getId() != thisParentBrand.getId()) {
			while (brand.getParentBrand() != null) {
				brand = brand.getParentBrand();
				if (!list.contains(brand))
					list.add(brand);
				else {
					Collections.reverse(list);

					return list;
				}
			}
		}
		Collections.reverse(list);
		return list;
	}
}
