package com.ivant.cms.action.dwr;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.action.admin.dwr.AbstractDWRAction;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ShoppingCartItem;

public class DWRPaymentAction extends AbstractDWRAction {
	
		
	private static final Logger logger = Logger.getLogger(DWRPaymentAction.class);
	private CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate=CategoryItemDelegate.getInstance();
	
	
	public List<Category> loadArea(Long id, Long groupId){
		//System.out.println("Enter here "+id+" groupId "+groupId);
		Category parentCategory=categoryDelegate.find(id);
		Group parentGroup=groupDelegate.find(groupId);
		//System.out.println("parent name "+parentCategory.getName());
		//System.out.println(" groupName "+parentGroup.getName());
		//System.out.println(" companyName "+parentCategory.getCompany());
		 List<Category> portions=new ArrayList<Category>();
		 portions=null;
		 portions=categoryDelegate.findAll(parentCategory.getCompany(),parentCategory,parentGroup,false, false).getList();
		//System.out.println("LOAD LOAD AREA SIZE: "+categoryDelegate.findAll(parentCategory.getCompany(),parentCategory,parentGroup,false, false).getList().size());
		if (parentCategory != null){
			return portions; 
		}
		return null;
	}
	
	public List<CategoryItem> loadPackage(Long id){
		 List<CategoryItem> packageItems=new ArrayList<CategoryItem>();
		 packageItems=null;
		 Order[] orders = {Order.asc("name")};
		 
		Category parentCategory=categoryDelegate.find(id);
		//System.out.println("parent name "+parentCategory.getName());
		//System.out.println(" companyName "+parentCategory.getCompany());

		packageItems=categoryItemDelegate.findAll(parentCategory.getCompany(),parentCategory,orders,false, false).getList();
		//System.out.println("LOAD LOAD package SIZE: "+categoryItemDelegate.findAll(parentCategory.getCompany(),parentCategory,orders,false, false).getList().size());
		if (parentCategory != null){
			return packageItems; 
		}
		return null;
	}
	
	public List<Double> findNetPrice(Long id){
		 List<Double> prices=new ArrayList<Double>();

		CategoryItem catItem=categoryItemDelegate.find(id);
		//System.out.println("catItem "+catItem.getPrice());
		prices.add(catItem.getPrice());
		for(int i=0;i<catItem.getCategoryItemPrices().size();i++){
			prices.add(catItem.getCategoryItemPrices().get(i).getAmount());
		}
		

		return prices;
	}
}
