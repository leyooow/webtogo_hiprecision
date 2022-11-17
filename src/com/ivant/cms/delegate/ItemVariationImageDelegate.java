package com.ivant.cms.delegate;

import com.ivant.cms.db.ItemVariationImageDAO;
import com.ivant.cms.entity.ItemVariationImage;

public class ItemVariationImageDelegate extends AbstractBaseDelegate<ItemVariationImage, ItemVariationImageDAO>{

private static final ItemVariationImageDelegate instance = new ItemVariationImageDelegate();
	
	public static ItemVariationImageDelegate getInstance() {
		return instance;
	}
	
	public ItemVariationImageDelegate() {
		super( new ItemVariationImageDAO());
	}
	
}
