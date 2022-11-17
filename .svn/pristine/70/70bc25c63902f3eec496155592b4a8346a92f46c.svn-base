package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.ws.rest.model.GroupModel;

@Entity(name="Group")
@Table(name="groups")
public class Group extends CompanyBaseObject {

	private String name;
	private String description;
	private int sortOrder;
	private List<Category> categories;
	private List<Category> enabledCategories;
	private List<Category> categoriesSortDesc;
	
	private List<CategoryItemPriceName> categoryItemPriceNames;
	private boolean featured;
	private int maxCategories;
	private int maxItems;
	private boolean hasBrands;
	private List<Brand> brands;
	private boolean loginRequired;
	private List<ItemImage> images;
	private boolean hasAttributes;
	//private boolean has2Prices;
	private boolean isOutOfStock;
	private List<Attribute> attributes;
	private String sortType;
	private Boolean hasScheduleForm;
	
	private transient List<OtherField> otherFields;
	
	private Boolean itemHasPrice;
	
	private  List<GroupLanguage> groupLanguageList;
	protected transient Language language;
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	@Transient
	public Language getLanguage() {
		return language;
	}
	
	public Group() {
		sortOrder = 1;
		featured = false;
		maxCategories = 5;
		maxItems = 10;
		
		hasBrands = false;
		loginRequired = false;
		hasAttributes = false;
		//has2Prices = false;
		isOutOfStock = false;
	}

	@Basic
	@Column(name="name", length=30, nullable=false)
	public String getName() {
		if(getGroupLanguageList()!=null && language!=null){
			for(GroupLanguage temp :getGroupLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp.getName();
			}
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name="description", length=65535)
	public String getDescription() {
		if(getGroupLanguageList()!=null && language!=null){
			for(GroupLanguage temp :getGroupLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp.getDescription();
			}
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name="sort_order")
	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@OneToMany(targetEntity = Category.class, mappedBy = "parentGroup", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("sortOrder ASC")
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}		
	
	@OneToMany(targetEntity = Category.class, mappedBy = "parentGroup", fetch = FetchType.LAZY)
	@Where(clause = "valid=1 AND hidden=0") 
	@OrderBy("sortOrder ASC")
	public List<Category> getEnabledCategories() {
		return enabledCategories;
	}
	
	public void setEnabledCategories(List<Category> enabledCategories) {
		this.enabledCategories = enabledCategories;
	}
	
	@OneToMany(targetEntity = Category.class, mappedBy = "parentGroup", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("sortOrder DESC")
	public List<Category> getCategoriesSortDesc() {
		return categoriesSortDesc;
	}
	public void setCategoriesSortDesc(List<Category> categoriesSortDesc) {
		this.categoriesSortDesc = categoriesSortDesc;
	}
	
	@OneToMany(targetEntity = CategoryItemPriceName.class, mappedBy = "group", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	public List<CategoryItemPriceName> getCategoryItemPriceNames() {
		return categoryItemPriceNames;
	}

	public void setCategoryItemPriceNames(
			List<CategoryItemPriceName> categoryItemPriceNames) {
		this.categoryItemPriceNames = categoryItemPriceNames;
	}

	@Basic 
	@Column(name = "featured", nullable=false)
	public boolean getFeatured() {
		return featured;
	} 

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
	
	@Basic 
	@Column(name = "max_categories", nullable=false)
	public int getMaxCategories() {
		return maxCategories;
	}

	public void setMaxCategories(int maxCategories) {
		this.maxCategories = maxCategories;
	}

	@Basic 
	@Column(name = "max_items", nullable=false)
	public int getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	@Basic 
	@Column(name = "has_brands", nullable=false)
	public boolean getHasBrands() {
		return hasBrands;
	}

	public void setHasBrands(boolean hasBrands) {
		this.hasBrands = hasBrands;
	}
	
	@OneToMany(targetEntity = Brand.class, mappedBy = "group", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy(value="name")
	@NotFound(action=NotFoundAction.IGNORE)
	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	@Basic
	@Column(name="login_required", nullable=false)
	public boolean getLoginRequired() {
		return loginRequired;
	}
	
	public void setLoginRequired(boolean loginRequired) {
		this.loginRequired = loginRequired;
	}	
	
	@Override
	public String toString() {
		return  "id: " + getId() + " \n" +
				"name: " + getName() + " \n" +
				"company: " + ((getCompany() != null) ? getCompany().getName() : "null") + " \n" +
				"featured:" + getFeatured() + "\n" + 
				"maxItems: " + getMaxItems() + "\n" +
				"maxCategories: " + getMaxCategories() + "\n" +
				"hasBrands: " + getHasBrands() + "\n" +
				"loginRequired:" + getLoginRequired() +"\n" +
			//	"has2Prices:" + gethas2Prices() +"\n" +
				"hasAttribute:" + getHasAttributes() ;
	}

	/*@Basic 
	@Column(name = "has2Prices", nullable=false)
	public boolean gethas2Prices() {
		return has2Prices;
	}

	public void sethas2Prices(boolean has2Prices) {
		this.has2Prices = has2Prices;
	}
	*/
	
	@Basic 
	@Column(name = "isOutOfStock", nullable=false)
	public boolean getIsOutOfStock() {   
		return isOutOfStock;
	}

	public void setIsOutOfStock(boolean isOutOfStock) {
		this.isOutOfStock = isOutOfStock;
	}
	
	/**
	 * @param images the images to set
	 */
	public void setImages(List<ItemImage> images) {
		this.images = images;
	}

	/**
	 * @return the images
	 */
	@OneToMany(targetEntity = GroupImage.class, mappedBy = "group", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("sortOrder asc")
	public List<ItemImage> getImages() {
		return images;
	}
	
	@Basic 
	@Column(name = "has_attributes", nullable=false)
	public boolean getHasAttributes() {
		return hasAttributes;
	}

	public void setHasAttributes(boolean hasAttributes) {
		this.hasAttributes = hasAttributes;
	}	

	
	@OneToMany(targetEntity = Attribute.class, mappedBy = "group", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@NotFound(action=NotFoundAction.IGNORE)
	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Basic 
	@Column(name = "item_has_price", nullable=true)
	public Boolean getItemHasPrice() {
		return itemHasPrice;
	}

	public void setItemHasPrice(Boolean itemHasPrice) {
		this.itemHasPrice = itemHasPrice;
	}
	
	@Transient
	public Integer getPriceNameCount()
	{
		if(getCategoryItemPriceNames() != null)
			return getCategoryItemPriceNames().size();
		else
			return 0;
	}
	
	/**
	 * Get the group other fields.
	 * 
	 * @return the group other fields
	 */
	@OneToMany(targetEntity = OtherField.class, mappedBy="group", fetch=FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@NotFound(action=NotFoundAction.IGNORE)	
	@Where(clause="valid = 1")
	@OrderBy("sortOrder")
	public List<OtherField> getOtherFields()
	{
		return otherFields;
	}
	
	/**
	 * Set the group other fields.
	 * 
	 * @param group other fields
	 */
	public void setOtherFields(List<OtherField> otherFields)
	{
		this.otherFields = otherFields;
	}
	
	@Transient
	public Integer getOtherFieldCount()
	{
		if(getOtherFields() != null)
			return getOtherFields().size();
		else
			return 0;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	@Basic 
	@Column(name = "sort_type")
	public String getSortType() {
		return sortType;
	}

	public void setHasScheduleForm(Boolean hasScheduleForm) {
		this.hasScheduleForm = hasScheduleForm;
	}
	
	@Basic 
	@Column(name = "has_schedule_form")
	public Boolean getHasScheduleForm() {
		
		if(hasScheduleForm==null)
			return Boolean.FALSE;
		
		
		return hasScheduleForm;
	}
	
	@OneToMany(targetEntity = GroupLanguage.class, mappedBy = "defaultGroup", fetch = FetchType.LAZY)
	public List<GroupLanguage> getGroupLanguageList() {
		return groupLanguageList;
	}
	public void setGroupLanguageList(List<GroupLanguage> groupLanguageList) {
		this.groupLanguageList = groupLanguageList;
	}
	
	
	@Transient
	public GroupLanguage getGroupLanguage() {
		if (getGroupLanguageList() != null && language != null)
			for (GroupLanguage temp : getGroupLanguageList()) {
				if (temp.getLanguage().equals(this.language))
					return temp;
			}
		return null;
	}
	
	@Transient
	public List<Category> getRootCategories()
	{
		return CategoryDelegate.getInstance().findAllRootCategories(getCompany(), this, true, Order.asc("sortOrder")).getList();
	}
	
	@Transient
	public GroupModel toGroupModel()
	{
		return new GroupModel(getId(), name, description);
	}
}
