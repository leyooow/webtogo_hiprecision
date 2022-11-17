package com.ivant.cms.action.company;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;

import com.ivant.cms.action.PageDispatcherAction;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;

public class BluechipDispatcherAction extends PageDispatcherAction {
	
	private static final long serialVersionUID = -7718725762675514610L;
	
	
	private Double maxPrice;
	private Double minPrice;
	private String bagType;
	private Long catId;
	private Long id;
	private String searchKey;
	
	
	public String products() throws Exception{
		maxResults = 9;
		
		CategoryItem selectedItem = null;
		if(id != null){
			selectedItem = categoryItemDelegate.find(id);
			if(selectedItem != null){
				if(selectedItem.getCompany() != company){
					selectedItem = null;
				}
			}
		}
		
		if(selectedItem == null){
			Group group  = groupDelegate.find(company, "Products");
			Category parent = categoryDelegate.find(catId);
			
			ObjectList<CategoryItem> productList = null;
			if(StringUtils.isBlank(searchKey)){
				productList = categoryItemDelegate.findAllByPriceWithpaging(page-1, maxResults, company, group, parent, minPrice, maxPrice, bagType);
			}else{
				productList = categoryItemDelegate.findByNameContainsOrSearchTagsContains(company, group, null, searchKey);
			}
			request.setAttribute("productList", productList );
			request.setAttribute("categoryCountBeans", categoryItemDelegate.findCountPerCategory(company, group));
			
			OtherField bagTypeOtherField = otherFieldDelegate.find("Bag Type", false, company, group);
			request.setAttribute("bagTypeCountBeans", categoryItemOtherFieldDelegate.findItemCountPerOtherFieldValue(company, group, bagTypeOtherField));
		}
		
		request.setAttribute("selectedItem", selectedItem);
		
		return SUCCESS;
	}
	
	public String blog(){
		maxResults = 4;
		MultiPage parent = multiPageDelegate.find(company, "Blog");
		request.setAttribute("multiPage", parent);
		if(id != null){
			SinglePage selectedPage = singlePageDelegate.find(id);
			if(selectedPage != null && parent == selectedPage.getParent() && selectedPage.getIsValid() && !selectedPage.getHidden()){
				request.setAttribute("selectedPage", selectedPage);
			}
		}else{
			ObjectList<SinglePage> resultSet = singlePageDelegate.findAllWithPaging(company, parent, maxResults, page-1, Order.desc("id"));
			request.setAttribute("multiPages", resultSet);
		}
		return SUCCESS;
	}
	
	
	
	
	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public String getBagType() {
		return bagType;
	}

	public void setBagType(String bagType) {
		this.bagType = bagType;
	}

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
}
