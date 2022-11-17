package com.ivant.cms.entity;

import java.text.DecimalFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

/**
 * @author Kevin Roy K. Chua
 * @version 1.0, Feb 24, 2010
 * @since 1.0, Feb 24, 2010
 */

@Entity(name = "CategoryItemPackage")
@Table(name = "category_item_package")
public class CategoryItemPackage
		extends CompanyBaseObject
{
	private IPackage iPackage;
	private CategoryItem categoryItem;
	private Float price;
	
	/**
	 * Get the iPackage.
	 * 
	 * @return the iPackage
	 */
	@ManyToOne(targetEntity = IPackage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id", insertable = true, updatable = true)
	@ForeignKey(name = "FK_PACKAGE_CATEGORYITEMPACKAGE")
	@NotFound(action = NotFoundAction.IGNORE)
	public IPackage getiPackage()
	{
		return iPackage;
	}
	
	/**
	 * Set the iPackage.
	 * 
	 * @param iPackage the iPackage
	 */
	public void setiPackage(IPackage iPackage)
	{
		this.iPackage = iPackage;
	}
	
	/**
	 * Get the category item.
	 * 
	 * @return the category item
	 */
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_item_id", insertable = true, updatable = true)
	@ForeignKey(name = "FK_CATEGORYITEM_CATEGORYITEMPACKAGE")
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItem getCategoryItem()
	{
		return categoryItem;
	}
	
	/**
	 * Set the category item.
	 * 
	 * @param categoryItem the category item
	 */
	public void setCategoryItem(CategoryItem categoryItem)
	{
		this.categoryItem = categoryItem;
	}
	
	/**
	 * Get the price.
	 * 
	 * @return the price
	 */
	@Basic
	@Column(name = "price", nullable = true)
	public Float getPrice()
	{
		if(price == null)
		{
			price = 0.0F;
		}
		return price;
	}
	
	/**
	 * Set the price.
	 * 
	 * @param price
	 */
	public void setPrice(Float price)
	{
		this.price = price;
	}
	
	@Transient
	public String getFormattedPrice()
	{
		return new StringBuffer().append(new DecimalFormat("#,##0.00").format(price)).toString();
	}
}
