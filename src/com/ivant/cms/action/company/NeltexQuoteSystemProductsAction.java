/**
 *
 */
package com.ivant.cms.action.company;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.simple.JSONObject;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.beans.KuysenSalesTransactionBean;
import com.ivant.cms.company.utils.KuysenSalesProductUtils;
import com.ivant.cms.company.utils.NeltexProductUtils;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.constants.KuysenSalesConstants;
import com.opensymphony.xwork2.Action;

/**
 * @author Edgar S. Dacpano
 *
 */
public class NeltexQuoteSystemProductsAction
		extends AbstractBaseAction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final NeltexProductUtils neltexProductUtils = NeltexProductUtils.getInstance();

	private KuysenSalesTransactionBean kuysenSalesTransactionBean;
	private HttpServletRequest request;
	private Company company;

	private InputStream inputStream;

	private String data;

	@Override
	public void prepare() throws Exception {
		logger.info("NELTEX AJAX ACTION IS PREPARING");
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

		Category category = neltexProductUtils.getCategoryById(categoryId);
		JSONObject categoryItemList = neltexProductUtils
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

		List<CategoryItem> category_items = neltexProductUtils
				.getCategoryItemsByKeyword(keyword);
		JSONObject categoryItemList = neltexProductUtils
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

		CategoryItem item = neltexProductUtils.getCategoryItemById(itemId);

		kuysenSalesTransactionBean = neltexProductUtils.addToCart(
				kuysenSalesTransactionBean, item);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION,
				kuysenSalesTransactionBean);

		JSONObject categoryItemList = neltexProductUtils.convertAreaCategoryItemsToJsonObject(kuysenSalesTransactionBean,neltexProductUtils.getAreaName(item),Boolean.TRUE);
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

		JSONObject categoryItemList = neltexProductUtils.convertAreaCategoryItemsToJsonObject(kuysenSalesTransactionBean, area, summaryReload);
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
		
		KuysenSalesTransactionBean kuysenSalesTransactionBean2 = neltexProductUtils.changeDiscount(area, id, discount, kuysenSalesTransactionBean);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION,kuysenSalesTransactionBean2);
		
		JSONObject changed = neltexProductUtils.convertedChangedDiscount(area, id, kuysenSalesTransactionBean, itemNo);
	
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
		
		KuysenSalesTransactionBean kuysenSalesTransactionBean2 = neltexProductUtils.changeSubDiscount(area, id, discount, kuysenSalesTransactionBean);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION,kuysenSalesTransactionBean2);
		
		JSONObject changed = neltexProductUtils.convertedChangedDiscount(area, id, kuysenSalesTransactionBean, itemNo);
	
		inputStream = new StringBufferInputStream(changed.toJSONString());
		
		return SUCCESS;
	}
	
	public String changeQuantity() {
		Integer quantity = null;
		String id = null;
		String area = null;
		String itemNo = null;
		try {
			org.json.JSONObject json = new org.json.JSONObject(data);
			quantity = json.getInt("quantity");
			id = json.getString("id");
			area = json.getString("area");
			itemNo = json.getString("itemNo");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		KuysenSalesTransactionBean kuysenSalesTransactionBean2 = neltexProductUtils.changeQuantity(area, id, quantity, kuysenSalesTransactionBean);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION,kuysenSalesTransactionBean2);
		
		JSONObject changed = neltexProductUtils.convertedChangedQuantity(area, id, kuysenSalesTransactionBean, itemNo);
		
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
		
		kuysenSalesTransactionBean = neltexProductUtils.removeFromCart(parentId, area, kuysenSalesTransactionBean);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION, kuysenSalesTransactionBean);
		
		JSONObject categoryItemList = neltexProductUtils.convertAreaCategoryItemsToJsonObject(kuysenSalesTransactionBean,area,Boolean.TRUE);
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
		
		JSONObject categoryItem = neltexProductUtils.toggleChild(id, area, kuysenSalesTransactionBean, parentId, session, elemId);
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
		
		JSONObject areaItems = neltexProductUtils.loadQuotationSummary(elemId, kuysenSalesTransactionBean);
		inputStream = new StringBufferInputStream(areaItems.toJSONString());
		
		return SUCCESS;
	}
	
	public String checkCart() {
		
		JSONObject jsonResponse = neltexProductUtils.checkCart(kuysenSalesTransactionBean);
		inputStream = new StringBufferInputStream(jsonResponse.toJSONString());
		
		return SUCCESS;
	}

}
