/**
 *
 */
package com.ivant.cms.action.company;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ivant.cms.action.PageDispatcherAction;
import com.ivant.cms.beans.KuysenClientBean;
import com.ivant.cms.beans.KuysenProductCategoryBean;
import com.ivant.cms.beans.KuysenSalesAreaBean;
import com.ivant.cms.beans.KuysenSalesTransactionBean;
import com.ivant.cms.company.utils.KuysenSalesClientUtils;
import com.ivant.cms.company.utils.KuysenSalesProductUtils;
import com.ivant.cms.company.utils.NeltexProductUtils;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interfaces.PageDispatcherAware;

/**
 * @author Edgar S. Dacpano
 *
 */
public class NeltexQuoteSystemDispatcherAction
		extends PageDispatcherAction
		implements PageDispatcherAware
{
	private static final long serialVersionUID = 1L;
	
	private final NeltexProductUtils neltexProductUtils = NeltexProductUtils.getInstance();
	private final KuysenSalesClientUtils kuysenSalesClientUtils = KuysenSalesClientUtils.getInstance();
	
	private KuysenSalesTransactionBean kuysenSalesTransactionBean;
	private String origin;

	public NeltexQuoteSystemDispatcherAction()
	{
		super();
	}
	
	@Override
	public void prepare() throws Exception
	{
		super.prepare();
	}

	@Override
	public String execute() throws Exception
	{
		String result = super.execute();
		
		logger.info("execute() in " + getClass().getSimpleName());
		
		if(member == null)
		{
			result = LOGIN;
		}
		
		System.out.println(origin);
		
		System.out.println("result is : " + result);
		
		return result;
	}
	
	public KuysenClientBean getClient() {
		return this.kuysenSalesTransactionBean.getClient();
	}
	
	public List<CategoryItem> getClientStatus() {
		return kuysenSalesClientUtils.getClientStatus(company);
	}
	
	public List<KuysenProductCategoryBean> getProductCategory() {
		return neltexProductUtils.getProductCategory();
	}
	
	public String getCategoryArea() {
		List<String> area_names = new ArrayList<String>(kuysenSalesTransactionBean.getOrders().keySet());
		
		if(area_names.size() > 0) {
			if(origin.equals("next")) {
				return area_names.get(0);
			} else if(origin.equals("back")) {
				return area_names.get(area_names.size() - 1);
			}
		}
		
		return null;
	}
	
	public Boolean getLoginMember() {
		return session.containsKey(MemberInterceptor.MEMBER_SESSION_KEY);
	}

	public void setKuysenSalesTransactionBean(KuysenSalesTransactionBean kuysenSalesTransactionBean) {
		this.kuysenSalesTransactionBean = kuysenSalesTransactionBean;
	}
	
	public List<KuysenSalesAreaBean> getAreas() {
		List<KuysenSalesAreaBean> list = new ArrayList<KuysenSalesAreaBean>(kuysenSalesTransactionBean.getOrders().values());
		return list;
	}

	public String getOrigin() {
		return origin;
	} 

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getNext() {
		if(kuysenSalesTransactionBean.getOrders().size() > 0) {
			List<String> keys = new ArrayList<String>(kuysenSalesTransactionBean.getOrders().keySet());
			if(origin.equals("next")) {
				if(kuysenSalesTransactionBean.getOrders().size() > 1) {
					return keys.get(1);
				} else {
					return null;
				}
			} else if(origin.equals("back")) {
				return null;
			}
		}
		
		return null;
	}
	
	public String getBack() {
		if(kuysenSalesTransactionBean.getOrders().size() > 0) {
			List<String> keys = new ArrayList<String>(kuysenSalesTransactionBean.getOrders().keySet());
			if(origin.equals("next")) {
				return null;
			} else if(origin.equals("back")) {
				if(kuysenSalesTransactionBean.getOrders().size() > 1) {
					return keys.get(keys.size() - 2);
				} else {
					return null;
				}
			}
		}
		
		return null;
	}
	
	public String getGrandTotal() {
		Double grandTotal = 0.0;
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		formatter.setMaximumFractionDigits(2);
		
		for(KuysenSalesAreaBean ab : kuysenSalesTransactionBean.getOrders().values()) {
			grandTotal += ab.getSubTotal();
		}

		return formatter.format(grandTotal);
	}
}
