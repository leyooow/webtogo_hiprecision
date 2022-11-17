package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.ItemFileDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.entity.list.ObjectList;

public class ItemFileDelegate extends AbstractBaseDelegate<ItemFile, ItemFileDAO> {
	

	private static ItemFileDelegate instance = new ItemFileDelegate();
	
	public static ItemFileDelegate getInstance() {
		return ItemFileDelegate.instance;
	}
	
	public ItemFileDelegate() {
		super(new ItemFileDAO());
	}
	
	public ItemFile findFileID(Company company, long cartOrderItemID)
	{
		return dao.findFileID(company, cartOrderItemID);
	}
	
	public List<ItemFile> findAll(Company company){
		return dao.findAll(company);
	}
	
	public ItemFile findItemFileID(Company company, long realID)
	{
		return dao.findItemFileID(company,realID);
	}
	
	public ObjectList<ItemFile> findAll(Company company, CategoryItem categoryItem)
	{
		return dao.findAll(company, categoryItem);
	}

}
