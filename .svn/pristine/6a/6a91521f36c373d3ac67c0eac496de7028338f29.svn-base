package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.ItemImageDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ItemImage;

public class ItemImageDelegate extends AbstractBaseDelegate<ItemImage, ItemImageDAO>{

	private static ItemImageDelegate instance = new ItemImageDelegate();
	
	public static ItemImageDelegate getInstance() {
		return ItemImageDelegate.instance;
	}
	
	public ItemImageDelegate() {
		super(new ItemImageDAO());
	}
	
	public List<ItemImage> findAllByItems(List<CategoryItem> items)
	{
		return dao.findAllByItems(items);
	}
}
