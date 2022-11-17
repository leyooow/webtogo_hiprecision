package com.ivant.cms.beans;

import com.ivant.cms.entity.CategoryItem;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesOptionalSetBean extends BaseBean {
	
	private CategoryItem item;
	private Integer quantity;
	private Double total;
	
	public KuysenSalesOptionalSetBean(CategoryItem item) {
		this.item = item;
		this.quantity = 0;
		this.total = 0.0;
	}

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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
