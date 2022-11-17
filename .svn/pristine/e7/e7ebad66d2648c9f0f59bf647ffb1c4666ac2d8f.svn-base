package com.ivant.cms.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ivant.cms.entity.CategoryItem;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesParentOrderSetBean extends BaseBean {
	
	private UUID parentId;
	private CategoryItem item;
	private Integer quantity = 0;
	private Double discount = 0.0;
	private Double subDiscount = 0.0;
	private Double netPrice = 0.0;
	private Double total = 0.0;
	private List<KuysenSalesOrderSetBean> specifications = new ArrayList<KuysenSalesOrderSetBean>();
	private List<KuysenSalesOptionalSetBean> optionals = new ArrayList<KuysenSalesOptionalSetBean>();
	private Boolean isPackage;
	private Double totalDiscount = 0.0;
	
	public CategoryItem getItem() {
		return item;
	}
	public void setItem(CategoryItem item) {
		this.item = item;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(Double netPrice) {
		this.netPrice = netPrice;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public List<KuysenSalesOrderSetBean> getSpecifications() {
		return specifications;
	}
	public void setSpecifications(List<KuysenSalesOrderSetBean> specifications) {
		this.specifications = specifications;
	}
	public Boolean getIsPackage() {
		return isPackage;
	}
	public void setIsPackage(Boolean isPackage) {
		this.isPackage = isPackage;
	}
	public UUID getParentId() {
		return parentId;
	}
	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}
	public Double getSubDiscount() {
		return subDiscount;
	}
	public void setSubDiscount(Double subDiscount) {
		this.subDiscount = subDiscount;
	}
	public Double getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public List<KuysenSalesOptionalSetBean> getOptionals() {
		return optionals;
	}
	public void setOptionals(List<KuysenSalesOptionalSetBean> optionals) {
		this.optionals = optionals;
	}
	
	public Double pdfNetPrice() {
		Double netprice = item.getPrice() * 1;
		Double maindesc = Math.floor(netprice * discount);
			netprice -=  maindesc;
		Double subdesc = Math.floor(netprice * subDiscount);
			netprice -=  subdesc;
		
		return netprice;
	}
	
	public Double mainTotal() {
		return pdfNetPrice() * getQuantity();
	}
}
