package com.ivant.cms.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity
@Table(name = "order_item")
public class OrderItem extends BaseObject {
	
	private ProductItem productItem;
	private Integer quantity;
	
	private Order order;
	
	public OrderItem() {
		quantity = Integer.valueOf(1);
	}
	
	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "id", column = @Column(name = "pi_id", nullable = false)),
			@AttributeOverride(name = "name", column = @Column(name = "pi_name")),
			@AttributeOverride(name = "sku", column = @Column(name = "pi_sku")),
			@AttributeOverride(name = "price", column = @Column(name = "pi_price", nullable = false))
	})
	public ProductItem getProductItem() {
		return productItem;
	}
	
	@Basic
	@Column(name = "quantity")
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setProductItem(ProductItem productItem) {
		this.productItem = productItem;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@ManyToOne(targetEntity=Order.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	@Where(clause = "valid=1")
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Transient
	public double getTotalPrice() {
		return quantity * productItem.getPrice().doubleValue();
	}
}
