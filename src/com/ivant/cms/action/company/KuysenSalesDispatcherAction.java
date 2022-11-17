/**
 *
 */
package com.ivant.cms.action.company;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;

import com.ivant.cms.action.PageDispatcherAction;
import com.ivant.cms.beans.KuysenClientBean;
import com.ivant.cms.beans.KuysenProductCategoryBean;
import com.ivant.cms.beans.KuysenSalesAreaBean;
import com.ivant.cms.beans.KuysenSalesTransactionBean;
import com.ivant.cms.company.utils.KuysenSalesClientUtils;
import com.ivant.cms.company.utils.KuysenSalesProductUtils;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.KuysenSalesClientDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interfaces.KuysenClientAware;
import com.ivant.cms.interfaces.KuysenSalesTransactionAware;
import com.ivant.cms.interfaces.PageDispatcherAware;
import com.ivant.constants.KuysenSalesConstants;

/**
 * @author Edgar S. Dacpano
 * @author Daniel B. Sario
 *
 */
public class KuysenSalesDispatcherAction
		extends PageDispatcherAction
		implements PageDispatcherAware,
				   KuysenSalesTransactionAware
{
	private static final long serialVersionUID = 1L;
	
	private final KuysenSalesClientDelegate kuysenSalesClientDelegate = KuysenSalesClientDelegate.getInstance();
	private final KuysenSalesProductUtils kuysenSalesProductUtils = KuysenSalesProductUtils.getInstance();
	private final KuysenSalesClientUtils kuysenSalesClientUtils = KuysenSalesClientUtils.getInstance();
	
	private KuysenSalesTransactionBean kuysenSalesTransactionBean;
	private String origin;

	public KuysenSalesDispatcherAction()
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
		
		System.out.println("result is : " + result);
		
		return result;
	}
	
	public String cancel() {
		String result = SUCCESS;
		
		if(member == null)
		{
			result = LOGIN;
		}
		
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION, null);
		
		return result;
	}
	
	public String newClient() {
		String result = SUCCESS;
		
		if(member == null)
		{
			result = LOGIN;
		}
		
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION, null);
		session.put(KuysenSalesConstants.KUYSEN_CLIENT_BEAN, null);
		
		return result;
	}
	
	public KuysenClientBean getClient() {
		return this.kuysenSalesTransactionBean.getClient();
	}
	
	public List<CategoryItem> getClientStatus() {
		return kuysenSalesClientUtils.getClientStatus(company);
	}
	
	public List<KuysenProductCategoryBean> getProductCategory() {
		return kuysenSalesProductUtils.getProductCategory();
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

	@Override
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
		formatter.setMinimumFractionDigits(2);
		
		for(KuysenSalesAreaBean ab : kuysenSalesTransactionBean.getOrders().values()) {
			grandTotal += ab.getSubTotal();
		}

		return formatter.format(grandTotal);
	}
	
	public String getAdditionalDiscount() {
		return Integer.toString(new Double(kuysenSalesTransactionBean.getAdditionalDiscount() * 100).intValue());
	}
	
	public String getTotalDiscount() {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		
		return formatter.format(kuysenSalesTransactionBean.getTotalDiscount()); 
	}
	
	public String getTotalNetAmount() {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		
		return formatter.format(kuysenSalesTransactionBean.getDiscountedTotal()); 
	}
	
	public String logout() {
		request.getSession().invalidate();
		
		return SUCCESS;
	}
	
	public String getGeneratedRef() {
		
		while(true) {
			String uuid = UUID.randomUUID().toString();
			if(!kuysenSalesClientDelegate.referenceIdExist(uuid)) {
				return uuid;
			}
		}
	}
}


