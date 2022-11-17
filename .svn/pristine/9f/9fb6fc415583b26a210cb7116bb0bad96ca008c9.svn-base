package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity(name = "KuysenSalesParentOrderSet")
@Table(name = KuysenSalesParentOrderSet.TABLE_NAME)
public class KuysenSalesParentOrderSet extends BaseObject {

	public static final String TABLE_NAME = "kuysensales_parent_orderset";
	
	private KuysenSalesArea area;
	
	private String parentId;
	private CategoryItem item;
	private Integer quantity;
	private Double discount;
	private Double subDiscount;
	private Double netPrice;
	private Double total;
	private Boolean isPackage;
	private Double totalDiscount;
	private List<KuysenSalesOrderSet> specifications;
	private List<KuysenSalesOptionalSet> optionals;
	
	@ManyToOne(targetEntity = KuysenSalesArea.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public KuysenSalesArea getArea() {
		return area;
	}
	
	@Basic
	@Column(name="parent_id")
	public String getParentId() {
		return parentId;
	}
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItem getItem() {
		return item;
	}
	
	@Basic
	@Column(name="quantity")
	public Integer getQuantity() {
		return quantity;
	}
	
	@Basic
	@Column(name="discount")
	public Double getDiscount() {
		return discount;
	}
	
	@Basic
	@Column(name="sub_discount")
	public Double getSubDiscount() {
		return subDiscount;
	}
	
	@Basic
	@Column(name="net_price")
	public Double getNetPrice() {
		return netPrice;
	}
	
	@Basic
	@Column(name="total")
	public Double getTotal() {
		return total;
	}
	
	@Basic
	@Column(name="is_package")
	public Boolean getIsPackage() {
		return isPackage;
	}
	
	@Basic
	@Column(name="total_discount")
	public Double getTotalDiscount() {
		return totalDiscount;
	}
	
	@OneToMany(targetEntity = KuysenSalesOrderSet.class, mappedBy = "kuysenSalesParentOrderSet", fetch = FetchType.LAZY)
	public List<KuysenSalesOrderSet> getSpecifications() {
		return specifications;
	}
	
	@OneToMany(targetEntity = KuysenSalesOptionalSet.class, mappedBy = "kuysenSalesParentOrderSet", fetch = FetchType.LAZY)
	public List<KuysenSalesOptionalSet> getOptionals() {
		return optionals;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public void setItem(CategoryItem item) {
		this.item = item;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	public void setSubDiscount(Double subDiscount) {
		this.subDiscount = subDiscount;
	}
	
	public void setNetPrice(Double netPrice) {
		this.netPrice = netPrice;
	}
	
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public void setIsPackage(Boolean isPackage) {
		this.isPackage = isPackage;
	}
	
	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public void setArea(KuysenSalesArea area) {
		this.area = area;
	}

	public void setSpecifications(List<KuysenSalesOrderSet> specifications) {
		this.specifications = specifications;
	}

	public void setOptionals(List<KuysenSalesOptionalSet> optionals) {
		this.optionals = optionals;
	}
}
