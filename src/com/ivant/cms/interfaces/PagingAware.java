package com.ivant.cms.interfaces;

public interface PagingAware {

	public void setPage(int page);
	public int getPage();

	public void setTotalItems();
	public int getTotalItems();
	
	public void setItemsPerPage(int itemsPerPage);
}
