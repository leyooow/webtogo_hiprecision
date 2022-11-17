package com.ivant.cms.beans;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesAreaBean extends BaseBean {
	
	private String area;
	private List<KuysenSalesParentOrderSetBean> orders = new ArrayList<KuysenSalesParentOrderSetBean>();
	private Double totalDiscount = 0.0;
	private Double subTotal = 0.0;
	private Double netTotal = 0.0;
	
	public KuysenSalesAreaBean(){}
	
	public KuysenSalesAreaBean(String area) {
		this.area = area;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}

	public Double getSubTotal() {
		double total = 0;
		
		for(KuysenSalesParentOrderSetBean bean : getOrders()) {
			total += bean.mainTotal();
			for(KuysenSalesOptionalSetBean ob : bean.getOptionals()) {
				total += ob.getTotal();
			}
		}
		
		return total;
	}
	
	public String getSubTotalString() {
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		
		return formatter.format(getSubTotal());
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public List<KuysenSalesParentOrderSetBean> getOrders() {
		return orders;
	}

	public void setOrders(List<KuysenSalesParentOrderSetBean> orders) {
		this.orders = orders;
	}
	
	public void addNewOrder(KuysenSalesParentOrderSetBean order) {
		orders.add(order);
	}

	public Double getTotalDiscount() {
		return getNetTotal() - getSubTotal();
	}
	
	public String getTotalDiscountString() {
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		
		return formatter.format(getTotalDiscount());
	}

	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public Double getNetTotal() {
		Double total = 0.0;
		for(KuysenSalesParentOrderSetBean bean : orders) {
			total += bean.getNetPrice();
			for(KuysenSalesOptionalSetBean optional : bean.getOptionals()) {
				total += optional.getItem().getPrice() * optional.getQuantity();
			}
		}
		return total;
	}

	public void setNetTotal(Double netTotal) {
		this.netTotal = netTotal;
	}
}
