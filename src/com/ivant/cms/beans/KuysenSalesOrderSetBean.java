package com.ivant.cms.beans;

import com.ivant.cms.entity.CategoryItem;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesOrderSetBean extends BaseBean {
	
	CategoryItem item;
	Boolean isIncluded;
	
	public KuysenSalesOrderSetBean(Long id, CategoryItem item, Boolean isIncluded) {
		this.setId(id);
		this.item = item;
		this.isIncluded = isIncluded;
	}

	public CategoryItem getItem() {
		return item;
	}

	public void setItem(CategoryItem item) {
		this.item = item;
	}

	public Boolean getIsIncluded() {
		return isIncluded;
	}

	public void setIsIncluded(Boolean isIncluded) {
		this.isIncluded = isIncluded;
	}
}
