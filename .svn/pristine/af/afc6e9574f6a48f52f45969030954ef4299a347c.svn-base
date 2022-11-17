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

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

/**
 * @author Kevin Roy K. Chua
 * @author Edgar S. Dacpano
 * @version 1.1, Jul 19, 2014
 * @since 1.0, Feb 23, 2010
 */

@Entity(name = "IPackage")
@Table(name = "packages")
public class IPackage
		extends CompanyBaseObject
{
	private String name;
	private String sku;/** SKU / Code*/
	private String description;
	
	private Double price;
	private int duration, stock;
	private String image, info1;
	
	private Group parentGroup;
	private transient List<CategoryItemPackage> categoryItemPackages;
	
	public IPackage()
	{
		super();
	}
	
	@Basic
	@Column(name = "name", nullable = false)
	public String getName()
	{
		return name;
	}
	
	@Basic
	@Column(name = "sku")
	public String getSku()
	{
		return sku;
	}
	
	@Basic
	@Column(name = "description")
	public String getDescription()
	{
		return description;
	}
	
	@ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_group", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Group getParentGroup()
	{
		return parentGroup;
	}
	
	/**
	 * Get the category item packages.
	 * 
	 * @return the category item packages
	 */
	@OneToMany(targetEntity = CategoryItemPackage.class, mappedBy = "iPackage", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Where(clause = "valid = 1")
	public List<CategoryItemPackage> getCategoryItemPackages()
	{
		return categoryItemPackages;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public void setCategoryItemPackages(List<CategoryItemPackage> categoryItemPackages)
	{
		this.categoryItemPackages = categoryItemPackages;
	}
	
	public void setParentGroup(Group parentGroup)
	{
		this.parentGroup = parentGroup;
	}
	@Basic
	@Column(name = "price")
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double value) {
		this.price = value;
	}
	@Basic
	@Column(name = "duration", nullable=true)
	public int getDuration() {
		return duration;
	}
	public void setDuration(int value) {
		this.duration = value;
	}
	@Basic
	@Column(name = "stock", nullable=true)
	public int getStock() {
		return stock;
	}
	public void setStock(int value) {
		this.stock = value;
	}
	@Basic
	@Column(name = "image")
	public String getImage() {
		return image;
	}
	public void setImage(String value) {
		this.image = value;
	}
	
	@Basic
	@Column(name = "info1")
	public String getInfo1() {
		return info1;
	}
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	
	@Transient
	public List<CategoryItemPackage> getItems()
	{
		return getCategoryItemPackages();
	}
	
	@Transient
	public Float getTotalPrice()
	{
		Float totalPrice = 0F;
		
		final List<CategoryItemPackage> items = getItems();
		if(CollectionUtils.isNotEmpty(items))
		{
			for(CategoryItemPackage cip : items)
			{
				totalPrice += cip.getPrice() == null ? 0F : cip.getPrice(); 
			}
		}
		
		return totalPrice;
	}
	
	@Transient
	public String getHtmlName()
	{
		String htmlName = name.replace("'", "&rsquo;");
		return htmlName;
	}
}
