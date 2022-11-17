package com.ivant.cms.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.enums.PageType;
import com.ivant.utils.HTMLTagStripper;




@Entity(name="Category")
@Table(name="category")
public class Category extends AbstractPage implements Cloneable{

	private int SHORT_DESCRIPTION_LENGTH = 500;
	private String description;
	private List<CategoryItem> items;
	private List<CategoryItem> items2;
	private List<CategoryItem> items3;
	private List<CategoryItem> items4;
	private List<CategoryItem> items5;
	private List<CategoryItem> availableItems;
	private List<CategoryItem> enabledItems;
//	private List<Category> categories;
	private Category parentCategory;
	private Group parentGroup;
	private List<CategoryItem> featuredItems;
	private List<Category> childrenCategory;
	private List<CategoryImage> images;
	private List<CategoryFile> files;
	@SuppressWarnings("unused")
	private String shortDescription;
	private String descriptor;
	private Hashtable<Long, Object> infiniteLoopAvoidance = new Hashtable();
	
	private Category rootCategory = findRootCategory(this);
	
	private  List<CategoryLanguage> categoryLanguageList;
	
	private String info1;
	private String info2;
	private String info3;
	private String info4;
	private String info5;
	private String info6;
	
	private String price;
	private String points;
	
	private Boolean flag1;
	private Boolean flag2;
	

	@OneToMany(targetEntity = CategoryImage.class, mappedBy = "category", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")
	public List<CategoryImage> getImages() {
		return images;
	}

	public void setImages(List<CategoryImage> images) {
		this.images = images;
	}
	
	/**
	 * @return the files
	 */
	@OneToMany(targetEntity = CategoryFile.class, mappedBy = "category", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")
	public List<CategoryFile> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<CategoryFile> files) {
		this.files = files;
	}

	@Basic
	@Column(name="shortDescription", length=4000)
	public String getShortDescription() {
		return shortDescription;
	}
	
	@Basic
	@Column(name="info1", length = 4000)
	public String getInfo1() {
		return info1;
	}
	
	@Basic
	@Column(name="info2", length = 4000)
	public String getInfo2() {
		return info2;
	}
	
	@Basic
	@Column(name="info3", length = 4000)
	public String getInfo3() {
		return info3;
	}
	
	@Basic
	@Column(name="info4", length = 4000)
	public String getInfo4() {
		return info4;
	}
	
	@Basic
	@Column(name="info5", length = 4000)
	public String getInfo5() {
		return info5;
	}
	
	@Basic
	@Column(name="info6", length = 4000)
	public String getInfo6(){
		return info6;
	}
	
	
	
	
	
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	
	public void setInfo3(String info3){
		this.info3 = info3;
	}
	
	public void setInfo4(String info4){
		this.info4 = info4;
	}
	
	public void setInfo5(String info5) {
		this.info5 = info5;
	}
	
	public void setInfo6(String info6) {
		this.info6 = info6;
	}
	
	@Basic
	@Column(name = "price")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Basic
	@Column(name = "points")
	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
	
	
	
	@Basic
	@Column(name = "description", length=2147483647)
	public String getDescription() {
		
		if(getCategoryLanguageList()!=null && language!=null){
			for(CategoryLanguage temp :getCategoryLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp.getDescription();
			}
		}
		
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(targetEntity = CategoryItem.class, mappedBy = "parent", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("sortOrder ASC")
	public List<CategoryItem> getItems() {
		return items;
	} 
	public void setItems(List<CategoryItem> items) {
		this.items = items;
	}
	
  	@OneToMany(targetEntity = CategoryItem.class, mappedBy = "parent", fetch = FetchType.LAZY)
  	@Where(clause = "valid=1 AND disabled=0")
	@OrderBy("sortOrder ASC")
	public List<CategoryItem> getEnabledItems() {
		return enabledItems;
	}
	
	public void setEnabledItems(List<CategoryItem> enabledItems) {
		this.enabledItems = enabledItems;
	}
	
	@OneToMany(targetEntity = CategoryItem.class, mappedBy = "parent", fetch = FetchType.LAZY)
	@Where(clause = "valid=1 and available_quantity > 0")
	@OrderBy("price ASC")
	public List<CategoryItem> getAvailableItems() {
		return availableItems;
	}
	
	public void setAvailableItems(List<CategoryItem> availableItems) {
		this.availableItems = availableItems;
	}
	
//	@OneToMany(targetEntity = Category.class, mappedBy = "parentCategory", fetch = FetchType.LAZY)
//	@Where(clause = "valid=1")
//	public List<Category> getCategories() {
//		return categories;
//	}
//	
//	public void setCategories(List<Category> categories) {
//		this.categories = categories;
//	}
	
	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_category")
	@NotFound(action = NotFoundAction.IGNORE) 
	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parent) {
		this.parentCategory = parent;
	}
	
	@ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_group")
	@NotFound(action = NotFoundAction.IGNORE) 
	public Group getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(Group parentGroup) {
		this.parentGroup = parentGroup;
	}
	
	@OneToMany(targetEntity = CategoryItem.class, mappedBy = "parent", fetch = FetchType.LAZY)
	@Where(clause = "valid=1 and featured=1")  
	public List<CategoryItem> getFeaturedItems() {
		return featuredItems;
	}
	
	public void setFeaturedItems(List<CategoryItem> featuredItems) {
		this.featuredItems = featuredItems;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Category) {
			if( ((Category)obj).getId() == this.getId()) {
				return true;
			}
		}
		else {
			throw new IllegalArgumentException();
		}
		return false;
	}
	///@OrderBy("sortOrder ASC")
	@OneToMany(targetEntity = Category.class, mappedBy = "parentCategory", fetch = FetchType.LAZY)
	@Where(clause = "valid=1 and hidden=0")
	@OrderBy("sortOrder ASC")
	public List<Category> getChildrenCategory(){
	return childrenCategory;
	}

	public void setChildrenCategory(List<Category> c){
		childrenCategory = c;
	}
	
	
	@Override
	public String toString() {
		return  "id: " + getId() + "\n" +
				"name: " + getName() + "\n" +
				"company: " + ((getCompany() != null) ? getCompany().getName() : "null") + "\n" +
				"created by: " + ((getCreatedBy() != null) ? getCreatedBy().getUsername() : "null") + "\n" +
				"description: " + getDescription() + "\n" +
				"items: " + getItems() + "\n" +
				//"categories: " + ((getCategories() != null) ? getCategories().size() : "null") + "\n" +
				"parent category: " + ((getParentCategory() != null) ? getParentCategory().getName() : "null") + "\n" +
				"sort order: " + getSortOrder() + "\n";
	}
	
//-----------------------------------------------------------------	
	//////////////////////////////////
	
	@Transient
	public Category getRootCategory() {
		return rootCategory;
	}
	
	public void setRootCategory(Category rootCategory) {
		this.rootCategory = rootCategory;
	}
	
	@Transient
	public String getDescriptor() {
		
		List<Category>  listCat= getParentCategories();
		StringBuffer sb = new StringBuffer();
		Company company = this.getCompany();
		if(company != null)
			//System.out.println("Category.java, Company: "+company.getName());
		for (Category  ccc : listCat)
			sb.append(" << " + ccc.getName());
		return sb.toString();
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	@Transient
	public Category findRootCategory(Category category)
	{
		if (category.getParentCategory()==null){
				return category;
		}
		else{
				return findRootCategory(category.getParentCategory());
		}
	}
	
	/*
	 * Author: Glenn Allen Sapla
	 * May 2009
	 */
	@Transient
	public Category findRootCategory2(Category category)
	{
		infiniteLoopAvoidance.put(category.getId(), category);
		//2nd method aiming to avoid crashes
		if (category.getParentCategory()==null || infiniteLoopAvoidance.get(category.getParentCategory().getId())!=null){
				//remove contents/keys
				infiniteLoopAvoidance.clear();
				return category;
		}
		else{
				return findRootCategory2(category.getParentCategory());
		}
	}
	
	@Transient
	public List<Category> getParentCategories(){
		
		return getParentCategory(this);
	}
	
	@Transient
	public List<Category> getParentCategory(Category category)
	{   
		List<Category> list = new LinkedList<Category>();
		Category thisParentCategory = category.getParentCategory();
		if(category.getParentCategory() != null && category.getId()!=thisParentCategory.getId()){
			while (category.getParentCategory()!=null) {
				//System.out.println("Current Category: "+category.getId()+category.getName());
				//System.out.println("Parent Category: "+category.getParentCategory().getId()+category.getParentCategory().getName());
					category = category.getParentCategory();
					if(!list.contains(category))
						list.add(category);
					else
					{
						Collections.reverse(list);
						
						return list;
					}
			}
		}
		Collections.reverse(list);
		return list;
	}
	
	@Transient
	public List<Category> getDesCategory()
	{
		Company company = this.getCompany();
		if(company != null)
		{
			//System.out.println(company.getName() + ": inside getDesCategory");
			//System.out.println(getId()+" "+company.getName()+" Category: "+getName());
		}
		List<Category> children = getDesCategory();
		if(children == null) {
			return null;
		}
		
		List<Category> allCategories = new LinkedList<Category>();
		for (Category child : children) {
			List<Category> c = child.getDesCategory();
			if (c!=null)
				allCategories.addAll(c);
		}
		
		return allCategories;
	}
	
	/*
	 * @return - list of all the category descendants of the current category
	 */
	@Transient
	public List<Category> getDesCategory2()
	{   
		Company company = this.getCompany();
		if(company != null)
		{
			//System.out.println(company.getName() + ": inside getDesCategory2");
			//System.out.println(getId()+" "+company.getName()+" Category: "+getName());
		}
		List<Category> children = new LinkedList<Category>();
		
		if(this.getChildrenCategory() == null) {
			return null;
		}
		children = this.getChildrenCategory();
		
		List<Category> allCategories = new LinkedList<Category>();
		for (Category child : children) {
			List<Category> c = child.getDesCategory2();
			if (!c.isEmpty()) {
				allCategories.addAll(c);
			}
			else allCategories.add(child);
		}
		return allCategories;
	}
	
	/*
	 * @return - size of the list of all category descendants' items
	 */
	@Transient
	public int getDesCategoryItemSize()
	{   
		List<CategoryItem> c = getDesCategoryItems();
		return c.size();
	}
	
	/*
	 * @return - list of all category descendants' items 
	 */
	@Transient
	public List<CategoryItem> getDesCategoryItems()
	{   
		List<Category> childrenCat = getDesCategory2();
		List<CategoryItem> childrenItems = new ArrayList<CategoryItem>();
		
		if(childrenCat == null) {
			childrenItems.addAll(this.getItems());
			return null;
		}

		for (Category childCat : childrenCat) {
				if(childCat.getChildrenCategory().isEmpty())
				childrenItems.addAll(childCat.getItems());
		}
		return childrenItems;
	}
	
	@SuppressWarnings("unchecked")
	@Transient
	public List<CategoryItem> getSortedDateItems()
	{
		int x=1;
		int y=0;
		for (CategoryItem c: enabledItems){ 
			if(c.getItemDate() == null){
				//System.out.println("-----------------null" + x++);
			}
			else if(c.getItemDate() != null) {
				//System.out.println(c.getItemDate());
			}
		}
		
		//System.out.println("-------------------------------" + y);
		Collections.sort(enabledItems, new DateComparator());
		//for (CategoryItem c: enabledItems)  System.out.println(c.getItemDate());
		return enabledItems;
	}

	@OneToMany(targetEntity = CategoryItem.class, mappedBy = "parent", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("name ASC")
	public List<CategoryItem> getItems2() {
		return items2;
	}
	
	@OneToMany(targetEntity = CategoryItem.class, mappedBy = "parent", fetch = FetchType.LAZY)
	@Where(clause = "valid=1 and disabled=0")
	@OrderBy("sortOrder ASC")
	public List<CategoryItem> getItems3() {
		return items3;
	}
	
	@OneToMany(targetEntity = CategoryItem.class, mappedBy = "parent", fetch = FetchType.LAZY)
	@Where(clause = "valid=1 and disabled=0")
	@OrderBy("price ASC")
	public List<CategoryItem> getItems4() {
		return items4;
	}
	
	@OneToMany(targetEntity = CategoryItem.class, mappedBy = "parent", fetch = FetchType.LAZY)
	@Where(clause = "valid=1 and disabled=0")
	@OrderBy("createdOn DESC")
	public List<CategoryItem> getItems5() {
		return items5;
	}
	
	public void setItems2(List<CategoryItem> items2) {
		this.items2 = items2;
	}

	public void setItems3(List<CategoryItem> items3) {
		this.items3 = items3;
	}
	
	public void setItems4(List<CategoryItem> items4) {
		this.items4 = items4;
	}
	public void setItems5(List<CategoryItem> items5) {
		this.items5 = items5;
	}
	
	@Transient
	public Set<Brand> getChildrenBrands() {
		Set<Brand> brands = new HashSet<Brand>();
		
		List<CategoryItem> items = getItems3();
		
		if(items != null) {
			for(CategoryItem item : items) {
				brands.add(item.getBrand());
			}
		}
		
		return brands;
	}
	
	@Transient
	public Map<Brand, List<CategoryItem>> getItemsMap() {
		Map<Brand, List<CategoryItem>> map = new HashMap<Brand, List<CategoryItem>>();
		
		List<CategoryItem> items = this.enabledItems;
		
		if(items != null) {
			for(CategoryItem item : items) {
				if(map.get(item.getBrand()) == null)
					map.put(item.getBrand(), new ArrayList<CategoryItem>());
				
				map.get(item.getBrand()).add(item);
			}
		}
		return map;
	}
//
	@Transient
	public String getMetaDescription() {
		String metaDescription = getDescription();
		if(metaDescription != null)
		{
			metaDescription = metaDescription.replaceAll("\\<.*?>","");
			metaDescription = metaDescription.replaceAll("\\&.*?\\;","");
			return metaDescription;
		}
		else
			return "";
		
	}
	
	@Transient
	public String getDescriptionWithoutTags()
	{
		return HTMLTagStripper.stripTags(getDescription()); 
	}
	

	@Transient
	public String getShortDescriptionWithoutTags()
	{
		return HTMLTagStripper.stripTags(getShortDescription()); 
	}

	@OneToMany(targetEntity = CategoryLanguage.class, mappedBy = "defaultCategory", fetch = FetchType.LAZY)
	public List<CategoryLanguage> getCategoryLanguageList() {
		return categoryLanguageList;
	}

	public void setCategoryLanguageList(List<CategoryLanguage> categoryLanguageList) {
		this.categoryLanguageList = categoryLanguageList;
	}
	
	
	@Transient
	public CategoryLanguage getCategoryLanguage() {
		if (getCategoryLanguageList() != null && language != null)
			for (CategoryLanguage temp : getCategoryLanguageList()) {
				if (temp.getLanguage().equals(this.language))
					return temp;
			}
		return null;
	}
	
	@Override
	public Category clone() throws RuntimeException {
		Category catogory = null;
		try {
			catogory = (Category)super.clone();
		}
		catch(CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
		return catogory;
	}
	
	@Transient
	public String getName() {
		if(getCategoryLanguageList()!=null && language!=null)
			for(CategoryLanguage temp :getCategoryLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp.getName();
			}
		return name;
	}

	@Transient
	@Override
	public PageType getPageType()
	{
		return PageType.GROUP;
	}
	@Transient
	public List<Brand> getAllCategoryItemBrand(){
		 List<Brand> categoryItemBrands = new ArrayList<Brand>();
		 for(CategoryItem item:items){
			 if(item.getBrand()!=null){
				 //remove first to prevent duplicate brand
				 Brand rootBrand = getRootBrand(item.getBrand());
				 categoryItemBrands.remove(rootBrand);
				 categoryItemBrands.add(rootBrand);
			 }
		 }
		 Comparator<Brand> categoryDisplayOrder = new Comparator<Brand>() {
			@Override
			public int compare(Brand brand1, Brand brand2) {
				Integer sortOrder1 = brand1.getSortOrder();
				Integer sortOrder2 = brand2.getSortOrder();
				return sortOrder1.compareTo(sortOrder2);
			}

		};
		Collections.sort(categoryItemBrands, categoryDisplayOrder);
		 
		 return categoryItemBrands;
	}
	
	public Brand getRootBrand(Brand brand){
		if(brand.getParentBrand()!=null){
			return getRootBrand(brand.getParentBrand());
		}
		return brand;
	}
	
	
	@Basic
	@Column(name="flag1")
	public Boolean getFlag1() {
		return flag1;
	}

	public void setFlag1(Boolean flag1) {
		this.flag1 = flag1;
	}
	
	@Basic
	@Column(name="flag2")
	public Boolean getFlag2() {
		return flag2;
	}

	public void setFlag2(Boolean flag2) {
		this.flag2 = flag2;
	}
	
}


class DateComparator implements Comparator{

	public int compare(Object cat1, Object cat2){

	//parameter are of type Object, so we have to downcast it to CategoryItem objects

	
		
	Date catt1 = ( (CategoryItem) cat1).getItemDate();
	//System.out.println(catt1);
	if (catt1 == null)
		catt1 = new Date(1L);
	Date catt2 = ( (CategoryItem) cat2).getItemDate();
	//System.out.println(catt2);
	if (catt2 == null)
		catt2 = new Date(1L);
	
	if( catt1.after(catt2) )

	return -1;

	else if( catt1.before(catt2) )

	return 1;

	else

	return 0;

	}

	}
