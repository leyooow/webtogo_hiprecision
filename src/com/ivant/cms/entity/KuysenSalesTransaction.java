package com.ivant.cms.entity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import com.ivant.cms.beans.KuysenSalesAreaBean;
import com.ivant.cms.entity.KuysenSalesArea;
import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.utils.DocumentElement;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

@Entity(name = "KuysenSalesTransaction")
@Table(name = KuysenSalesTransaction.TABLE_NAME)
public class KuysenSalesTransaction extends BaseObject {
	
	public static final String TABLE_NAME = "kuysensales_transaction";
	
	private Member member;
	
	private KuysenSalesClient client;
	
	private Double additionalDiscount;
	private Double discountedTotal;
	private Double totalDiscount;
	private List<String> parentOrderIdList;
	private Map<String, KuysenSalesArea> orders;
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}

	@ManyToOne(targetEntity = KuysenSalesClient.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public KuysenSalesClient getClient() {
		return client;
	}
	
	@Basic
	@Column(name="additional_discount")
	public Double getAdditionalDiscount() {
		return additionalDiscount;
	}
	
	@Basic
	@Column(name="discounted_total")
	public Double getDiscountedTotal() {
		return discountedTotal;
	}
	
	@Basic
	@Column(name="total_discount")
	public Double getTotalDiscount() {
		return totalDiscount;
	}
	
	@CollectionOfElements
	@JoinTable(joinColumns = @JoinColumn(name="id"))
	@Type(type="string")
	@Column(name="parent_order_list")
	public List<String> getParentOrderIdList() {
		return parentOrderIdList;
	}
	
	@OneToMany(mappedBy="transaction")
	@MapKey(name="area")
	@Column(name="orders")
	public Map<String, KuysenSalesArea> getOrders() {
		return orders;
	}
	
	public void setAdditionalDiscount(Double additionalDiscount) {
		this.additionalDiscount = additionalDiscount;
	}
	
	public void setDiscountedTotal(Double discountedTotal) {
		this.discountedTotal = discountedTotal;
	}
	
	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	
	public void setParentOrderIdList(List<String> parentOrderIdList) {
		this.parentOrderIdList = parentOrderIdList;
	}

	public void setOrders(Map<String, KuysenSalesArea> orders) {
		this.orders = orders;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setClient(KuysenSalesClient client) {
		this.client = client;
	}
	
	@Transient
	public String getGrandTotal() {
		double grand_total = 0.0;
		
		NumberFormat formatter =  NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		
		for(KuysenSalesArea area : getOrders().values()) {
			grand_total += area.getSubTotal();
		}
		
		return formatter.format(grand_total - getTotalDiscountPrice(grand_total));
	}
	
	@Transient
	public Double getTotalDiscountPrice(double gross) {
		return gross * getAdditionalDiscount();
	}
}
