package com.ivant.cms.company.utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Ostermiller.util.StringTokenizer;
import com.ivant.cms.beans.KuysenProductCategoryBean;
import com.ivant.cms.beans.KuysenSalesAreaBean;
import com.ivant.cms.beans.KuysenSalesOptionalSetBean;
import com.ivant.cms.beans.KuysenSalesOrderSetBean;
import com.ivant.cms.beans.KuysenSalesParentOrderSetBean;
import com.ivant.cms.beans.KuysenSalesTransactionBean;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.PackageDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemPackage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.IPackage;
import com.ivant.constants.KuysenSalesConstants;

public class KuysenSalesProductUtils {
	
	private static final KuysenSalesProductUtils instance = new KuysenSalesProductUtils();
	
	private final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private final PackageDelegate packageDelegate = PackageDelegate.getInstance();
	
	public static KuysenSalesProductUtils getInstance() {
		return KuysenSalesProductUtils.instance;
	}
	
	public KuysenSalesTransactionBean addToCart(KuysenSalesTransactionBean transaction, CategoryItem item) {
		String area_name = getAreaName(item);
		
		if(!isAreaAlreadyExist(transaction.getOrders(), area_name)) {
			
			KuysenSalesAreaBean area = new KuysenSalesAreaBean(area_name);
			area.setTotalDiscount(0.0);
			area.setSubTotal(item.getPrice());
			
			KuysenSalesParentOrderSetBean parent_order = new KuysenSalesParentOrderSetBean();
			parent_order.setItem(item);
			parent_order.setParentId(generateParentId(transaction));
			
			parent_order.setOptionals(getFittings(item));
			
			if(isPackage(item)) {
				parent_order.setIsPackage(Boolean.TRUE);
				parent_order.setSpecifications(getPackageItems(item));
			} else {
				parent_order.setIsPackage(Boolean.FALSE);
			}
			
			parent_order.setTotal(item.getPrice());
			parent_order.setDiscount(0.0);
			parent_order.setNetPrice(item.getPrice());
			parent_order.setQuantity(KuysenSalesConstants.PRODUCT_INITIAL_QUANTITY);
			
			area.addNewOrder(parent_order);
			
			transaction.addNewArea(area_name, area);
		} else {
			KuysenSalesAreaBean area = transaction.getOrders().get(area_name);
			
			if(isAlreadyInTheCart(area, item) && isPackage(item)) {
				area.addNewOrder(createParentOrder(transaction, item));
				area.setSubTotal(area.getSubTotal() + item.getPrice());
			} else if(!isAlreadyInTheCart(area, item)) {
				area.addNewOrder(createParentOrder(transaction, item));
				area.setSubTotal(area.getSubTotal() + item.getPrice());
			}
			
			transaction.getOrders().put(area.getArea(), area);
		}

		return transaction;
	}
	
	public KuysenSalesParentOrderSetBean createParentOrder(KuysenSalesTransactionBean transaction, CategoryItem item) {
		KuysenSalesParentOrderSetBean parent_order = new KuysenSalesParentOrderSetBean();
		parent_order.setItem(item);
		parent_order.setTotal(item.getPrice());
		parent_order.setDiscount(0.0);
		parent_order.setNetPrice(item.getPrice());
		parent_order.setQuantity(KuysenSalesConstants.PRODUCT_INITIAL_QUANTITY);
		parent_order.setParentId(generateParentId(transaction));
		
		parent_order.setOptionals(getFittings(item));
		
		if(isPackage(item)) {
			parent_order.setIsPackage(Boolean.TRUE);
			parent_order.setSpecifications(getPackageItems(item));
		} else {
			parent_order.setIsPackage(Boolean.FALSE);
		}
		
		return parent_order;
	}
	
	public Boolean isAlreadyInTheCart(KuysenSalesAreaBean area, CategoryItem item) {
		
		for(KuysenSalesParentOrderSetBean parent : area.getOrders()) {
			if(parent.getItem().getId().equals(item.getId())) {
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}
	
	public Boolean isAreaAlreadyExist(Map<String,KuysenSalesAreaBean> areas, String area_name) {
		for(String key : areas.keySet()) {
			if(key.equalsIgnoreCase(area_name)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public List<KuysenSalesOrderSetBean> getPackageItems(CategoryItem item) {
		List<IPackage> packages = packageDelegate.findAll(getMainCompany()).getList();
		List<CategoryItemPackage> category_item_packages = null;
		List<KuysenSalesOrderSetBean> package_items = new ArrayList<KuysenSalesOrderSetBean>();
		KuysenSalesOrderSetBean tempBean = null;
		
		for(IPackage item_package : packages) {
			if(item_package.getName().equalsIgnoreCase(item.getName())) {
				category_item_packages = item_package.getCategoryItemPackages();
				break;
			}
		}
		
		for(CategoryItemPackage cipackage : category_item_packages) {
			tempBean = new KuysenSalesOrderSetBean(null, cipackage.getCategoryItem(), Boolean.TRUE);
			package_items.add(tempBean);
		}
		
		return package_items;
	}
	
	public List<KuysenSalesOptionalSetBean> getFittings(CategoryItem item) {
		List<KuysenSalesOptionalSetBean> fittingItemBeans = new ArrayList<KuysenSalesOptionalSetBean>();
		
		for(Long id : item.getSubCategoryItems()) {
			fittingItemBeans.add(new KuysenSalesOptionalSetBean(categoryItemDelegate.find(id)));
		}
		
		return fittingItemBeans;
	}
	
	public Boolean isPackage(CategoryItem item) {
		
		List<IPackage> packages = packageDelegate.findAll(getMainCompany()).getList();
		
		for(IPackage item_package : packages) {
			if(item_package.getName().equalsIgnoreCase(item.getName())) {
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}
	
	public List<CategoryItem> getCategoryItemsByKeyword(String keyword) {
		List<CategoryItem> category_items = new ArrayList<CategoryItem>();
		CategoryItem category_item = null;
		
		/*SPECIFIC SEARCH*/
		
		/*BY ID*/
		try {
			category_item = getCategoryItemById(Long.parseLong(keyword));
			if(category_item != null) {
				category_items.add(category_item);
				return category_items;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		/*BY NAME*/
		category_item = getCategoryItemByName(keyword);
		if(category_item != null) {
			category_items.add(category_item);
			return category_items;
		}
		
		/*BY KUYSEN SALES NAME*/
		category_item = getCategoryItemByKuysenSalesName(keyword);
		if(category_item != null) {
			category_items.add(category_item);
			return category_items;
		}
		
		/*BY ARTICLE NO*/
		category_item = getCategoryItemByArticle(keyword);
		if(category_item != null) {
			category_items.add(category_item);
			return category_items;
		}
		
		/*FIND BY SEARCH TAGS*/
		return getCategoryItemBySearchTag(keyword);
	}
	
	public JSONObject convertAreaCategoryItemsToJsonObject(KuysenSalesTransactionBean transaction, String area_name, Boolean summaryReload) {
		JSONObject convertedCatItems = new JSONObject();
		JSONArray category_items_array = new JSONArray();
		Double grand_total = 0.0;
		
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		
		for(String a : transaction.getOrders().keySet()) {
			System.out.println("KEY : " +  a + " : " +  area_name);
		}
		
		KuysenSalesAreaBean area = transaction.getOrders().get(area_name);
		
		if(area == null) {
			for(String a : transaction.getOrders().keySet()) {
				area = transaction.getOrders().get(a);
				break;
			}
			
			if(area == null) {
				return new JSONObject();
			}
		}
		
		List<KuysenSalesParentOrderSetBean> orders = area.getOrders();
		
		for(KuysenSalesParentOrderSetBean order : orders) {
			JSONObject itemObj = new JSONObject();
			JSONArray specifications = new JSONArray();
			JSONArray fittings = new JSONArray();
			
			itemObj.put("ParentId", order.getParentId().toString());
			itemObj.put("Id", order.getItem().getId());
			itemObj.put("IsPackage", order.getIsPackage());
			itemObj.put("Article", order.getItem().getDescriptionWithoutTags());
			
			try {
				itemObj.put("Image", order.getItem().getImages() != null && order.getItem().getImages().size() > 0 ? "http://www.kuysen.com" + KuysenSalesConstants.CATEGORY_IMAGES_DIRECTORY + order.getItem().getImages().get(0).getImage3() : "");
			} catch(Exception e) {
				itemObj.put("Image","");
			}
			
			itemObj.put("Name", order.getItem().getBrand().getParentBrand() != null ? order.getItem().getBrand().getParentBrand().getName() : order.getItem().getBrand() != null ? order.getItem().getBrand().getName() : "");
			itemObj.put("Description", order.getItem().getSku().trim());
			itemObj.put("Price", formatter.format(order.getItem().getPrice()));
			itemObj.put("Discount", order.getDiscount() * 100);
			itemObj.put("SubDiscount", order.getSubDiscount() * 100);
			itemObj.put("Quantity", order.getQuantity());
			itemObj.put("NetPrice", formatter.format(order.pdfNetPrice()));
			itemObj.put("Total", formatter.format(order.mainTotal()));
			itemObj.put("Level", "main");
				
				if(order.getIsPackage()) {
					for(KuysenSalesOrderSetBean spec : order.getSpecifications()) {
						JSONObject subItemObj = new JSONObject();
						
						subItemObj.put("Id", spec.getItem().getId());
						subItemObj.put("Article", spec.getItem().getDescriptionWithoutTags());
						subItemObj.put("Name", spec.getItem().getSku());
						subItemObj.put("Price", formatter.format(spec.getItem().getPrice()));
						subItemObj.put("Included", spec.getIsIncluded());
						subItemObj.put("Level", "sub");
						
						specifications.add(subItemObj);
					}
					
					itemObj.put("Specifications", specifications);
				}
				
				if(order.getOptionals().size() > 0) {
					itemObj.put("hasFittings", Boolean.TRUE);
					for(KuysenSalesOptionalSetBean fitting : order.getOptionals()) {
						JSONObject subItemObj = new JSONObject();
						
						subItemObj.put("Id", fitting.getItem().getId());
						subItemObj.put("Article", fitting.getItem().getDescriptionWithoutTags());
						subItemObj.put("Name", fitting.getItem().getSku());
						subItemObj.put("Price", formatter.format(fitting.getItem().getPrice()));
						subItemObj.put("Quantity", fitting.getQuantity());
						subItemObj.put("Level", "sub");
						
						fittings.add(subItemObj);
					}
					
					itemObj.put("Fittings", fittings);
				} else {
					itemObj.put("hasFittings", Boolean.FALSE);
				}
				
			category_items_array.add(itemObj);
		}
		
		convertedCatItems.put("Items", category_items_array);
		convertedCatItems.put("Area", area_name);
		convertedCatItems.put("SubTotal", formatter.format(area.getSubTotal()));
		convertedCatItems.put("summaryReload", summaryReload);
		
		List<String> area_names = new ArrayList<String>(transaction.getOrders().keySet());
		for(int ctr = 0;ctr < area_names.size();ctr++) {
			if(area_names.get(ctr).equalsIgnoreCase(area_name)) {
				if(ctr == 0) {
					convertedCatItems.put("Back", null);
				} else {
					convertedCatItems.put("Back", area_names.get(ctr - 1));
				}
				
				if(ctr == area_names.size() - 1) {
					convertedCatItems.put("Next", null);
				} else {
					if(area_names.size() > 1) {
						convertedCatItems.put("Next", area_names.get(ctr + 1));
					} else {
						convertedCatItems.put("Next", null);
					}
				}
			}
		}
		
		for(KuysenSalesAreaBean ab : transaction.getOrders().values()) {
			grand_total += ab.getSubTotal();
		}
		
		convertedCatItems.put("GrandTotal", grand_total.toString());
		
		return convertedCatItems;
	}
	
	public String getAreaName(CategoryItem item) {
		StringTokenizer tag_tokens = null;
		String[] tags = null;
		String area_name = null;
		
		if(item.getSearchTags() != null) {
			tag_tokens = new StringTokenizer(item.getSearchTags(), ",");
			tags = tag_tokens.toArray();
			
			if(tags.length > 0) {
				area_name = tags[0];
			}
		}
		
		if(area_name == null) {
			area_name = "no area";
		}
		
		return area_name;
	}
	
	public JSONObject convertCategoryItemsToJsonObject(List<CategoryItem> category_items, Integer pageNo) {
		JSONObject convertedCatItems = new JSONObject();
		JSONArray category_items_array = new JSONArray();
		CategoryItem item = null;

		for (int ctr = ((pageNo - 1) * KuysenSalesConstants.CATEGORY_ITEMS_MAX_ITEMS_PER_PAGE);ctr < category_items.size();ctr++) {
			item = category_items.get(ctr);
			JSONObject itemObj = new JSONObject();

			itemObj.put("Id", item.getId());
			itemObj.put("Name", item.getSku() != null ? item.getSku() : "");
			itemObj.put("Description", item.getDescriptionWithoutTags() != null ? item.getDescriptionWithoutTags() : "");
			
			try {
				itemObj.put("Image", item.getImages() != null && item.getImages().size() > 0 ? "http://www.kuysen.com" + KuysenSalesConstants.CATEGORY_IMAGES_DIRECTORY + item.getImages().get(0).getImage3() : "");
			} catch(Exception e) {
				itemObj.put("Image","");
			}
			
			category_items_array.add(itemObj);
			
			if(category_items_array.size() == KuysenSalesConstants.CATEGORY_ITEMS_MAX_ITEMS_PER_PAGE) {
				break;
			}
			
			if(ctr == category_items.size() - 1) {
				convertedCatItems.put("Status", KuysenSalesConstants.CATEGORY_ITEM_FETCH_STATUS_LAST_PAGE);
			}

		}

		convertedCatItems.put("Items", category_items_array);
		
		if(!convertedCatItems.containsKey("Status")) {
			if(convertedCatItems.size() > 0) {
				convertedCatItems.put("Status", KuysenSalesConstants.CATEGORY_ITEM_FETCH_STATUS_OK);
			} else {
				convertedCatItems.put("Status", KuysenSalesConstants.CATEGORY_ITEM_FETCH_STATUS_NO_ITEM);
			}
		}
		
		convertedCatItems.put("NextPageNo", ++pageNo);

		return convertedCatItems;
	}
	
	public KuysenSalesTransactionBean changeDiscount(String area, String id, Integer discount, KuysenSalesTransactionBean transaction) {
		KuysenSalesAreaBean parent_area = transaction.getOrders().get(area);
		KuysenSalesParentOrderSetBean parent = null;
		Double area_subtotal = 0.0;
		Double totalDiscount = 0.0;
		
		for(KuysenSalesParentOrderSetBean p : parent_area.getOrders()){
			if(p.getParentId().toString().equalsIgnoreCase(id)) {
				parent = p;
				break;
			}
		}
		
		parent.setDiscount(discount / 100.0);
		totalDiscount += Math.floor(parent.getDiscount() * parent.getNetPrice());
		parent.setTotal(parent.getNetPrice() - Math.floor(parent.getDiscount() * parent.getNetPrice()));
		totalDiscount += Math.floor(parent.getSubDiscount() * parent.getTotal());
		parent.setTotal(parent.getTotal() - Math.floor(parent.getSubDiscount() * parent.getTotal()));
		parent.setTotalDiscount(totalDiscount);
		
		totalDiscount = 0.0;
		
		if(parent.getOptionals().size() > 0) {
			double optional_total = 0D;
			for(KuysenSalesOptionalSetBean optional_bean : parent.getOptionals()) {
				optional_total += optional_bean.getTotal();
			}
			parent.setTotal(parent.getTotal() + optional_total);
		}
		
		for(KuysenSalesParentOrderSetBean pb : transaction.getOrders().get(area).getOrders()) {
			area_subtotal += pb.getTotal();
			totalDiscount += pb.getTotalDiscount();
		}
		
		transaction.getOrders().get(area).setSubTotal(area_subtotal);
		transaction.getOrders().get(area).setTotalDiscount(totalDiscount);
		
		return transaction;
	}
	
	public KuysenSalesTransactionBean changeSubDiscount(String area, String id, Integer discount, KuysenSalesTransactionBean transaction) {
		KuysenSalesAreaBean parent_area = transaction.getOrders().get(area);
		KuysenSalesParentOrderSetBean parent = null;
		Double area_subtotal = 0.0;
		Double totalDiscount = 0.0;
		
		
		for(KuysenSalesParentOrderSetBean p : parent_area.getOrders()){
			if(p.getParentId().toString().equalsIgnoreCase(id)) {
				parent = p;
				break;
			}
		}
		
		parent.setSubDiscount(discount / 100.0);
		totalDiscount += Math.floor(parent.getDiscount() * parent.getNetPrice());
		parent.setTotal(parent.getNetPrice() - Math.floor(parent.getDiscount() * parent.getNetPrice()));
		totalDiscount += Math.floor(parent.getSubDiscount() * parent.getTotal());
		parent.setTotal(parent.getTotal() - Math.floor(parent.getSubDiscount() * parent.getTotal()));
		parent.setTotalDiscount(totalDiscount);
		
		totalDiscount = 0.0;
		
		if(parent.getOptionals().size() > 0) {
			double optional_total = 0D;
			for(KuysenSalesOptionalSetBean optional_bean : parent.getOptionals()) {
				optional_total += optional_bean.getTotal();
			}
			parent.setTotal(parent.getTotal() + optional_total);
		}
		
		for(KuysenSalesParentOrderSetBean pb : transaction.getOrders().get(area).getOrders()) {
			area_subtotal += pb.getTotal();
			totalDiscount += pb.getTotalDiscount();
		}
		
		
		
		transaction.getOrders().get(area).setSubTotal(area_subtotal);
		transaction.getOrders().get(area).setTotalDiscount(totalDiscount);
		
		return transaction;
	}
	
	public JSONObject convertedChangedDiscount(String area, String id, KuysenSalesTransactionBean transaction, String itemNo) {
		KuysenSalesAreaBean parent_area = transaction.getOrders().get(area);
		KuysenSalesParentOrderSetBean parent = null;
		Double sub_total = 0.0;
		Double grand_total = 0.0;
		JSONObject json = new JSONObject();
		
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		
		for(KuysenSalesParentOrderSetBean p : parent_area.getOrders()){
			if(p.getParentId().toString().equalsIgnoreCase(id)) {
				parent = p;
				break;
			}
		}
		
		json.put("ItemNo", itemNo);
		json.put("ParentId", parent.getParentId().toString());
		json.put("Id", parent.getItem().getId());
		json.put("Article", parent.getItem().getDescriptionWithoutTags());
		
		try {
			json.put("Image", parent.getItem().getImages() != null && parent.getItem().getImages().size() > 0 ? "http://www.kuysen.com" + KuysenSalesConstants.CATEGORY_IMAGES_DIRECTORY + parent.getItem().getImages().get(0).getImage3() : "");
		} catch(Exception e) {
			json.put("Image","");
		}
		
		json.put("Name", parent.getItem().getBrand().getParentBrand() != null ? parent.getItem().getBrand().getParentBrand().getName() : parent.getItem().getBrand() != null ? parent.getItem().getBrand().getName() : "");
		json.put("Description", parent.getItem().getSku().trim());
		json.put("Price", formatter.format(parent.getItem().getPrice()));
		json.put("Discount", parent.getDiscount() * 100);
		json.put("SubDiscount", parent.getSubDiscount() * 100);
		json.put("Quantity", parent.getQuantity());
		json.put("NetPrice", formatter.format(parent.pdfNetPrice()));
		json.put("Total", formatter.format(parent.mainTotal()));
		json.put("Level", "main");
		json.put("Area", parent_area.getArea());
		
		for(KuysenSalesParentOrderSetBean pb : transaction.getOrders().get(parent_area.getArea()).getOrders()) {
			sub_total += pb.getTotal();
		}
		
		json.put("SubTotal", transaction.getOrders().get(parent_area.getArea()).getSubTotalString());
		
		for(KuysenSalesAreaBean ab : transaction.getOrders().values()) {
			grand_total += ab.getSubTotal();
		}
		
		json.put("GrandTotal", formatter.format(grand_total));
		
		return json;
	}
	
	public KuysenSalesTransactionBean changeQuantity(String area, String id, Integer quantity, String fid, KuysenSalesTransactionBean transaction) {
		KuysenSalesAreaBean parent_area = transaction.getOrders().get(area);
		KuysenSalesParentOrderSetBean parent = null;
		Double area_subtotal = 0.0;
		Double totalDiscount = 0.0;
		
		for(KuysenSalesParentOrderSetBean p : parent_area.getOrders()){
			if(p.getParentId().toString().equalsIgnoreCase(id)) {
				parent = p;
				break;
			}
		}
		
		if(fid.equals("")) {
			parent.setQuantity(quantity);
		} 
		
		parent.setNetPrice(parent.getItem().getPrice() * parent.getQuantity());
		totalDiscount += Math.floor(parent.getDiscount() * parent.getNetPrice());
		parent.setTotal(parent.getNetPrice() - Math.floor(parent.getDiscount() * parent.getNetPrice()));
		totalDiscount += Math.floor(parent.getSubDiscount() * parent.getTotal());
		parent.setTotal(parent.getTotal() - Math.floor(parent.getSubDiscount() * parent.getTotal()));
		parent.setTotalDiscount(totalDiscount);
		
		totalDiscount = 0.0;
		
		if(!fid.equals("")) {
			if(parent.getOptionals().size() > 0) {
				Long fitting_id = new Long(fid);
				
				for(KuysenSalesOptionalSetBean optional_set : parent.getOptionals()) {
					if(optional_set.getItem().getId().longValue() == fitting_id.longValue()) {
						optional_set.setQuantity(quantity);
						optional_set.setTotal(optional_set.getItem().getPrice() * quantity);
						parent.setTotal(parent.getTotal() + optional_set.getTotal());
						break;
					}
				}
			}
		} else {
			if(parent.getOptionals().size() > 0) {
				double optional_total = 0D;
				for(KuysenSalesOptionalSetBean optional_bean : parent.getOptionals()) {
					optional_total += optional_bean.getTotal();
				}
				parent.setTotal(parent.getTotal() + optional_total);
			}
		}
		
		for(KuysenSalesParentOrderSetBean pb : transaction.getOrders().get(area).getOrders()) {
			area_subtotal += pb.getTotal();
			totalDiscount += pb.getTotalDiscount();
		}
		
		transaction.getOrders().get(area).setSubTotal(area_subtotal);
		transaction.getOrders().get(area).setTotalDiscount(totalDiscount);
		
		return transaction;
	}
	 
	public JSONObject convertedChangedQuantity(String area, String id, KuysenSalesTransactionBean transaction, String itemNo) {
		KuysenSalesAreaBean parent_area = transaction.getOrders().get(area);
		KuysenSalesParentOrderSetBean parent = null;
		Double sub_total = 0.0;
		Double grand_total = 0.0;
		JSONObject json = new JSONObject();
		
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		
		for(KuysenSalesParentOrderSetBean p : parent_area.getOrders()){
			if(p.getParentId().toString().equalsIgnoreCase(id)) {
				parent = p;
				break;
			}
		}
		
		json.put("ItemNo", itemNo);
		json.put("ParentId", parent.getParentId().toString());
		json.put("Id", parent.getItem().getId());
		json.put("Article", parent.getItem().getDescriptionWithoutTags());
		
		try {
			json.put("Image", parent.getItem().getImages() != null && parent.getItem().getImages().size() > 0 ? "http://www.kuysen.com" + KuysenSalesConstants.CATEGORY_IMAGES_DIRECTORY + parent.getItem().getImages().get(0).getImage3() : "");
		} catch(Exception e) {
			json.put("Image","");
		}
		
		json.put("Name", parent.getItem().getBrand().getParentBrand() != null ? parent.getItem().getBrand().getParentBrand().getName() : parent.getItem().getBrand() != null ? parent.getItem().getBrand().getName() : "");
		json.put("Description", parent.getItem().getSku().trim());
		json.put("Price", formatter.format(parent.getItem().getPrice()));
		json.put("Discount", parent.getDiscount() * 100);
		json.put("SubDiscount", parent.getSubDiscount() * 100);
		json.put("Quantity", parent.getQuantity());
		json.put("NetPrice", formatter.format(parent.pdfNetPrice()));
		json.put("Total", formatter.format(parent.mainTotal()));
		json.put("Level", "main");
		json.put("Area", parent_area.getArea());
		
		json.put("SubTotal", transaction.getOrders().get(parent_area.getArea()).getSubTotalString());
		
		for(KuysenSalesAreaBean ab : transaction.getOrders().values()) {
			grand_total += ab.getSubTotal();
		}
		
		json.put("GrandTotal", formatter.format(grand_total));
		
		return json;
	}
	
	public List<KuysenProductCategoryBean> getProductCategory() {
		Company kuysen_main = getMainCompany();
		List<KuysenProductCategoryBean> kuysenProductCategory = new ArrayList<KuysenProductCategoryBean>();

		Category kuysen_product_category = categoryDelegate.find(KuysenSalesConstants.PRODUCTS_CATEGORY_ID);

		List<Category> kuysen_product_sub_categories = kuysen_product_category.getChildrenCategory();
		
		for(Category c : kuysen_product_sub_categories) {
			generateProductCategory(kuysenProductCategory,c);
		}
		
		return kuysenProductCategory;
	}
	
	public void generateProductCategory(List<KuysenProductCategoryBean> kuysenProductCategory,Category category) {
		if(category.getEnabledItems().size() > 0) {
			kuysenProductCategory.add(new KuysenProductCategoryBean(category.getEnabledItems(), generateProductCategoryName(category),category.getId()));
		}
		
		if(category.getChildrenCategory().size() > 0) {
			for(Category childCategory : category.getChildrenCategory()) {
				generateProductCategory(kuysenProductCategory,childCategory);
			}
		}
	}
	
	public String generateProductCategoryName(Category category) {
		String productCategoryName = "";
		
		for(Category parentCategory : category.getParentCategories()) {
			productCategoryName += parentCategory.getName() + " >> ";
		}
		
		productCategoryName += category.getName();

		return productCategoryName;
	}
	
	private UUID generateParentId(KuysenSalesTransactionBean transaction) {
		Boolean found = Boolean.TRUE;
		UUID generatedUUID = null;
		
		while(found) {
			generatedUUID = UUID.randomUUID();
			found = Boolean.FALSE;
			
			for(UUID uuid : transaction.getParentOrderIdList()) {
				if(generatedUUID.equals(uuid)) {
					found = Boolean.TRUE;
					break;
				}
			}
		}
		transaction.getParentOrderIdList().add(generatedUUID);
		return generatedUUID;
	}
	
	public KuysenSalesTransactionBean removeFromCart(String parentId, String area, KuysenSalesTransactionBean transaction) {
		
		KuysenSalesAreaBean transaction_area = transaction.getOrders().get(area);
		
		for(int ctr = 0;ctr < transaction_area.getOrders().size();ctr++) {
			if(transaction_area.getOrders().get(ctr).getParentId().toString().equalsIgnoreCase(parentId)) {
				transaction.getOrders().get(area).setSubTotal(transaction.getOrders().get(area).getSubTotal() - transaction.getOrders().get(area).getOrders().get(ctr).getTotal());
				transaction.getOrders().get(area).getOrders().remove(ctr);
				
					if(transaction.getOrders().get(area).getOrders().size() < 1) {
						transaction.getOrders().remove(area);
					}
					
				break;
			}
		}
		
		return transaction;
	}
	
	public JSONObject toggleChild(Long id, String area_name, KuysenSalesTransactionBean transaction, String parentId, Map session, String elemId) {
		Boolean found = Boolean.FALSE;
		KuysenSalesOrderSetBean toggledItem = null;
		
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		
		for(KuysenSalesParentOrderSetBean pb :  transaction.getOrders().get(area_name).getOrders()) {
			if(parentId.equalsIgnoreCase(pb.getParentId().toString())) {
				found = Boolean.TRUE;
				
				for(KuysenSalesOrderSetBean sb :  pb.getSpecifications()) {
					if(id.equals(sb.getItem().getId())) {
						sb.setIsIncluded(!sb.getIsIncluded());
						toggledItem = sb;
						System.out.println(toggledItem.getItem().getName());
						break;
					}
				}
				if(found) {
					break;
				}
			}
		}
		
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION, transaction);
		
		JSONObject json = new JSONObject();
		
		json.put("ContainerId", elemId);
		json.put("Id", toggledItem.getItem().getId().toString());
		json.put("Area", area_name);
		json.put("ParentId", parentId);
		json.put("Included", toggledItem.getIsIncluded());
		json.put("Name", toggledItem.getItem().getName());
		json.put("Price", formatter.format(toggledItem.getItem().getPrice()));
		json.put("Article", toggledItem.getItem().getDescriptionWithoutTags());
		
		return json;
	}
	
	public JSONObject loadQuotationSummary(String elemId, KuysenSalesTransactionBean transaction) {
		JSONObject convertedAreaItems = new JSONObject();
		JSONArray area_array = new JSONArray();
		Double grandTotal = 0.0;
		
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		
		for(KuysenSalesAreaBean area : transaction.getOrders().values()) {
			JSONObject areaBean = new JSONObject();
			JSONArray area_items_array = new JSONArray();
			
			areaBean.put("Area", area.getArea());
			
			for(KuysenSalesParentOrderSetBean p : area.getOrders()) {
				JSONObject item = new JSONObject();
				String caption = "";
				if(p.getIsPackage()) {
					caption = " SET";
				}
				
				item.put("Name", p.getItem().getSku() + caption);
				item.put("Quantity", p.getQuantity().toString());
				
				area_items_array.add(item);
			}
			
			areaBean.put("Items", area_items_array);
			
			areaBean.put("Total", formatter.format(area.getSubTotal()));
			
			area_array.add(areaBean);
			
			grandTotal += area.getSubTotal();
		}
		
		convertedAreaItems.put("ElemId", elemId);
		convertedAreaItems.put("Items", area_array);
		convertedAreaItems.put("GrandTotal", formatter.format(grandTotal));
		
		return convertedAreaItems;
	}
	
	public JSONObject checkCart(KuysenSalesTransactionBean transaction) {
		JSONObject message = new JSONObject();
		
		if(transaction.getOrders().size() > 0) {
			message.put("Message", null);
		} else {
			message.put("Message", "There is no items in the Cart!!!");
		}
		
		return message;
	}
	
	private Company getMainCompany() {
		return companyDelegate.find(KuysenSalesConstants.KUYSEN_MAIN_COMPANY_ID);
	}
	
	public CategoryItem getCategoryItemById(Long id) {
		return categoryItemDelegate.find(id);
	}
	
	public List<CategoryItem> getCategoryItemBySearchTag(String keyword) {
		return categoryItemDelegate.findAllByTags(getMainCompany(), keyword).getList();
	}
	
	public CategoryItem getCategoryItemByName(String name) {
		return categoryItemDelegate.findByName(getMainCompany(), name);
	}
	
	public CategoryItem getCategoryItemByKuysenSalesName(String sku) {
		return categoryItemDelegate.findSKU(getMainCompany(), sku);
	}
	
	public CategoryItem getCategoryItemByArticle(String article) {
		return categoryItemDelegate.findByArticle(getMainCompany(), article);
	}
	
	public Category getCategoryById(Long categoryId) {
		return categoryDelegate.find(categoryId);
	}

}
