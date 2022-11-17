package com.ivant.cms.action.company;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.JSONException;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.beans.KuysenProductCategoryBean;
import com.ivant.cms.beans.KuysenSalesTransactionBean;
import com.ivant.cms.company.utils.KuysenSalesProductUtils;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.KuysenSalesTransactionAware;
import com.ivant.constants.KuysenSalesConstants;
import com.opensymphony.xwork2.Action;

public class KuysenSalesProductsAction extends AbstractBaseAction implements
		Action, ServletRequestAware, CompanyAware, KuysenSalesTransactionAware {

	private final KuysenSalesProductUtils kuysenSalesProductUtils = KuysenSalesProductUtils
			.getInstance();

	private KuysenSalesTransactionBean kuysenSalesTransactionBean;
	private HttpServletRequest request;
	private Company company;

	private InputStream inputStream;

	private String data;

	@Override
	public void prepare() throws Exception {
		logger.info("KUYSEN AJAX ACTION IS PREPARING");
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public String getProductsByCategory() {
		Long categoryId = null;
		Integer pageNo = null;
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			categoryId = json.getLong("categoryId");
			pageNo = json.getInt("pageNo");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		Category category = kuysenSalesProductUtils.getCategoryById(categoryId);
		JSONObject categoryItemList = kuysenSalesProductUtils
				.convertCategoryItemsToJsonObject(category.getEnabledItems(),
						pageNo);
		inputStream = new StringBufferInputStream(
				categoryItemList.toJSONString());

		return SUCCESS;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public String getProductsByKeyword() {
		String keyword = null;
		Integer pageNo = null;
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			keyword = (String) json.get("keyword");
			pageNo = json.getInt("pageNo");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		List<CategoryItem> category_items = kuysenSalesProductUtils
				.getCategoryItemsByKeyword(keyword);
		JSONObject categoryItemList = kuysenSalesProductUtils
				.convertCategoryItemsToJsonObject(category_items, pageNo);
		inputStream = new StringBufferInputStream(
				categoryItemList.toJSONString());

		return SUCCESS;
	}

	public String addToCart() {
		Long itemId = null;
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			itemId = json.getLong("itemId");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		CategoryItem item = kuysenSalesProductUtils.getCategoryItemById(itemId);

		kuysenSalesTransactionBean = kuysenSalesProductUtils.addToCart(
				kuysenSalesTransactionBean, item);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION,
				kuysenSalesTransactionBean);

		JSONObject categoryItemList = kuysenSalesProductUtils.convertAreaCategoryItemsToJsonObject(kuysenSalesTransactionBean,kuysenSalesProductUtils.getAreaName(item),Boolean.TRUE);
		inputStream = new StringBufferInputStream(categoryItemList.toJSONString());

		return SUCCESS;
	}

	public String loadProductsByArea() {
		String area = null;
		Boolean summaryReload = null;
 		
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			area = json.getString("area");
			summaryReload = json.getBoolean("summaryReload");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject categoryItemList = kuysenSalesProductUtils.convertAreaCategoryItemsToJsonObject(kuysenSalesTransactionBean, area, summaryReload);
		inputStream = new StringBufferInputStream(categoryItemList.toJSONString());

		return SUCCESS;
	}
	
	public String changeDiscount() {
		Integer discount = null;
		String id = null;
		String area = null;
		String itemNo = null;
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			discount = json.getInt("discount");
			id = json.getString("id");
			area = json.getString("area");
			itemNo = json.getString("itemNo");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		KuysenSalesTransactionBean kuysenSalesTransactionBean2 = kuysenSalesProductUtils.changeDiscount(area, id, discount, kuysenSalesTransactionBean);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION,kuysenSalesTransactionBean2);
		
		JSONObject changed = kuysenSalesProductUtils.convertedChangedDiscount(area, id, kuysenSalesTransactionBean, itemNo);
	
		inputStream = new StringBufferInputStream(changed.toJSONString());
		
		return SUCCESS;
	}
	
	public String changeSubDiscount() {
		Integer discount = null;
		String id = null;
		String area = null;
		String itemNo = null;
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			discount = json.getInt("discount");
			id = json.getString("id");
			area = json.getString("area");
			itemNo = json.getString("itemNo");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		KuysenSalesTransactionBean kuysenSalesTransactionBean2 = kuysenSalesProductUtils.changeSubDiscount(area, id, discount, kuysenSalesTransactionBean);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION,kuysenSalesTransactionBean2);
		
		JSONObject changed = kuysenSalesProductUtils.convertedChangedDiscount(area, id, kuysenSalesTransactionBean, itemNo);
	
		inputStream = new StringBufferInputStream(changed.toJSONString());
		
		return SUCCESS;
	}
	
	public String changeAdditionalDiscount() {
		Integer discount = null;
		
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			discount = json.getInt("discount");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		
		kuysenSalesTransactionBean.setAdditionalDiscount(discount / 100.0);
		
		JSONObject changed = new JSONObject();
		
		changed.put("totalDiscount", formatter.format(kuysenSalesTransactionBean.getTotalDiscount()));
		changed.put("discountedTotal", formatter.format(kuysenSalesTransactionBean.getDiscountedTotal()));
	
		inputStream = new StringBufferInputStream(changed.toJSONString());
		
		return SUCCESS;
	}
	
	public String changeQuantity() {
		Integer quantity = null;
		String id = null;
		String area = null;
		String itemNo = null;
		String fid = null;
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			quantity = json.getInt("quantity");
			id = json.getString("id");
			area = json.getString("area");
			itemNo = json.getString("itemNo");
			fid = json.getString("fid");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		KuysenSalesTransactionBean kuysenSalesTransactionBean2 = kuysenSalesProductUtils.changeQuantity(area, id, quantity, fid, kuysenSalesTransactionBean);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION,kuysenSalesTransactionBean2);
		
		JSONObject changed = kuysenSalesProductUtils.convertedChangedQuantity(area, id, kuysenSalesTransactionBean, itemNo);
		
		inputStream = new StringBufferInputStream(changed.toJSONString());
		
		return SUCCESS;
	}

	@Override
	public void setCompany(Company company) {
		this.company = company;

	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public void setKuysenSalesTransactionBean(
			KuysenSalesTransactionBean kuysenSalesTransactionBean) {
		this.kuysenSalesTransactionBean = kuysenSalesTransactionBean;
	}
	
	public String getCategoryArea() {
		System.out.println("GETTING CATEGORY AREA");
		for(String area_name : kuysenSalesTransactionBean.getOrders().keySet()) {
			System.out.println("area name : " + area_name);
			return area_name;
		}
		
		return null;
	}
	
	public String removeFromCart() {
		
		String parentId = null;
		String area = null;
		
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			parentId = json.getString("parentId");
			area = json.getString("area");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		kuysenSalesTransactionBean = kuysenSalesProductUtils.removeFromCart(parentId, area, kuysenSalesTransactionBean);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION, kuysenSalesTransactionBean);
		
		JSONObject categoryItemList = kuysenSalesProductUtils.convertAreaCategoryItemsToJsonObject(kuysenSalesTransactionBean,area,Boolean.TRUE);
		inputStream = new StringBufferInputStream(categoryItemList.toJSONString());
		
		return SUCCESS;
	}
	
	public String toggleChild() {
		String elemId = null;
		String area = null;
		Long id = null;
		String parentId = null;
		
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			elemId = json.getString("elemId");
			area = json.getString("area");
			id = json.getLong("id");
			parentId = json.getString("parentId");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		JSONObject categoryItem = kuysenSalesProductUtils.toggleChild(id, area, kuysenSalesTransactionBean, parentId, session, elemId);
		inputStream = new StringBufferInputStream(categoryItem.toJSONString()); 
		
		return SUCCESS;
	}
	
	public String loadQuotationSummary() {
		
		String elemId = null;
		
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			elemId = json.getString("elemId");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		JSONObject areaItems = kuysenSalesProductUtils.loadQuotationSummary(elemId, kuysenSalesTransactionBean);
		inputStream = new StringBufferInputStream(areaItems.toJSONString());
		
		return SUCCESS;
	}
	
	public String checkCart() {
		
		JSONObject jsonResponse = kuysenSalesProductUtils.checkCart(kuysenSalesTransactionBean);
		inputStream = new StringBufferInputStream(jsonResponse.toJSONString());
		
		return SUCCESS;
	}

}
