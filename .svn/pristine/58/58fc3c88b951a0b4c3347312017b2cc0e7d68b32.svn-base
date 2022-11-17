package com.ivant.cms.action.admin.dwr;

import java.util.List;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemPackageDelegate;
import com.ivant.cms.delegate.PackageDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemPackage;
import com.ivant.cms.entity.IPackage;

public class DWRPackageItemAction extends AbstractDWRAction{
	CategoryItemDelegate catItemDelegate = CategoryItemDelegate.getInstance();
	CategoryItemPackageDelegate catItemPackageDelegate = CategoryItemPackageDelegate.getInstance();
	PackageDelegate packDelegate= PackageDelegate.getInstance();
	
	public String addItem(long catItemId, long packageId, float price){
		CategoryItem cat = catItemDelegate.find(catItemId);
		IPackage pack = packDelegate.find(packageId); 
		
		CategoryItemPackage temp = new CategoryItemPackage();
		temp.setPrice(price);
		temp.setCategoryItem(cat);
		temp.setiPackage(pack);
		temp.setCompany(this.getCompany());
		
		long id = catItemPackageDelegate.insert(temp);
		
		if(id>=0) return "ok";
		
		return "failed";
	}
	
	public String editItem(long itemId, float price){
		CategoryItemPackage temp = catItemPackageDelegate.find(itemId);
		temp.setPrice(price);
		boolean result = catItemPackageDelegate.update(temp);
		return result+"";
	}
	
	public String deleteItem(long itemId){
		CategoryItemPackage temp = catItemPackageDelegate.find(itemId);
		boolean result = catItemPackageDelegate.delete(temp);
		return result+"";
	}
	
	public String getPrice(long itemId){
		CategoryItem cat = catItemDelegate.find(itemId);
		return cat.getPrice() + "";
	}
	
	public String getList(long packageId){
		List<CategoryItemPackage> list = catItemPackageDelegate.findAll(packageId).getList();
		StringBuffer sb = new StringBuffer("{[");
		
		for(int i=0;i<list.size();i++)
		{
			if(i>0) sb.append(","); 
			
			CategoryItemPackage temp = list.get(i);			
			sb.append("{id: "+ temp.getId() + ",");			
			sb.append("cat_id: " + (temp.getCategoryItem() != null ? temp.getCategoryItem().getId() : 0) + ",");
			sb.append("name: '"+ (temp.getCategoryItem() != null ? temp.getCategoryItem().getName() : "") + "',");
			sb.append("price: "+ temp.getPrice() + ",");
			sb.append("regular_price: " + (temp.getCategoryItem() != null ? temp.getCategoryItem().getPrice() : 0.0) +"}");
			
			System.out.println(sb);
		}				
		return sb.toString() + "]}";
	}
}
