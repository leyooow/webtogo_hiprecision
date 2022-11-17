package com.ivant.cms.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ivant.cms.entity.Member;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesTransactionBean extends BaseBean {
	
	private KuysenClientBean client;
	private Member member;
	private Double additionalDiscount = 0.0;
	private Double discountedTotal = 0.0;
	private Double totalDiscount = 0.0;
	private List<UUID> parentOrderIdList = new ArrayList<UUID>();
	private Map<String, KuysenSalesAreaBean> orders = new HashMap<String, KuysenSalesAreaBean>();

	public Map<String, KuysenSalesAreaBean> getOrders() {
		return orders;
	}
	
	public void setOrders(Map<String, KuysenSalesAreaBean> orders) {
		this.orders = orders;
	}
	
	public KuysenClientBean getClient() {
		return client;
	}
	
	public void setClient(KuysenClientBean client) {
		this.client = client;
	}
	
	public void addNewArea(String area_name, KuysenSalesAreaBean area) {
		orders.put(area_name, area);
	}

	public List<UUID> getParentOrderIdList() {
		return parentOrderIdList;
	}

	public void setParentOrderIdList(List<UUID> parentOrderIdList) {
		this.parentOrderIdList = parentOrderIdList;
	}

	public Double getAdditionalDiscount() {
		return additionalDiscount;
	}

	public void setAdditionalDiscount(Double additionalDiscount) {
		this.additionalDiscount = additionalDiscount;
	}

	public Double getDiscountedTotal() {
		Double discountedTotal = 0.0;
		for(KuysenSalesAreaBean bean : orders.values()) {
			discountedTotal += bean.getSubTotal();
		}
		 
		return discountedTotal - getTotalDiscount();
	}

	public void setDiscountedTotal(Double discountedTotal) {
		this.discountedTotal = discountedTotal;
	}

	public Double getTotalDiscount() {
		Double totalDiscount = 0.0;
		for(KuysenSalesAreaBean bean : orders.values()) {
			totalDiscount += bean.getSubTotal();
		}
		 
		return Math.floor(totalDiscount * getAdditionalDiscount());
	}

	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
