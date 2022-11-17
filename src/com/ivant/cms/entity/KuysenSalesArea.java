package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity(name = "KuysenSalesArea")
@Table(name = KuysenSalesArea.TABLE_NAME)
public class KuysenSalesArea extends BaseObject {

	public static final String TABLE_NAME = "kuysensales_area";
	
	private KuysenSalesTransaction transaction;
	
	private String area;
	private Double totalDiscount;
	private Double subTotal;
	private Double netTotal;
	private List<KuysenSalesParentOrderSet> kuysenSalesParentOrderSets;
	
	@ManyToOne(targetEntity = KuysenSalesTransaction.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction")
	@NotFound(action = NotFoundAction.IGNORE) 
	public KuysenSalesTransaction getTransaction() {
		return transaction;
	}
	
	@Basic
	@Column(name="area")
	public String getArea() {
		return area;
	}
	
	@Basic
	@Column(name="total_discount")
	public Double getTotalDiscount() {
		return totalDiscount;
	}
	
	@Basic
	@Column(name="subtotal")
	public Double getSubTotal() {
		return subTotal;
	}
	
	@Basic
	@Column(name="net_total")
	public Double getNetTotal() {
		return netTotal;
	}
	
	@OneToMany(targetEntity = KuysenSalesParentOrderSet.class, mappedBy = "area", fetch = FetchType.LAZY)
	public List<KuysenSalesParentOrderSet> getKuysenSalesParentOrderSets() {
		return kuysenSalesParentOrderSets;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	
	public void setNetTotal(Double netTotal) {
		this.netTotal = netTotal;
	}

	public void setTransaction(KuysenSalesTransaction transaction) {
		this.transaction = transaction;
	}

	public void setKuysenSalesParentOrderSets(List<KuysenSalesParentOrderSet> kuysenSalesParentOrderSets) {
		this.kuysenSalesParentOrderSets = kuysenSalesParentOrderSets;
	}
}

