package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interceptors.CompanyInterceptor;

@Entity
@Table(name="pre_order")
public class PreOrder  extends CompanyBaseObject {
	
	private Member member;
	private List<PreOrderItem> items;
	
	CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	HttpServletRequest request;
	ServletContext servletContext;

	@ManyToOne(targetEntity=Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}	

	public void setMember(Member member) {
		this.member = member;
	}

	@OneToMany(targetEntity=PreOrderItem.class, fetch=FetchType.LAZY, mappedBy="preOrder")
	@Where(clause="valid=1")
	public List<PreOrderItem> getItems() {
		return items;
	}
	
	public void setItems(List<PreOrderItem> items) {
		this.items = items;
	}
	
	@Transient
	public Integer getTotalPreOrderQuantity(){
		int total = 0;
		
		if(null != items && !items.isEmpty())
		{
			for(int i=0; i<items.size(); i++)
			{
				total = total + items.get(i).getQuantity();
			}
			
			return total;
		}
		
		//return an empty list count by default
		return 0;
	}	
	
	@Transient
	public double getTotalPreOrderPrice(){
		double total = 0;
		
		if(null != items && !items.isEmpty())
		{
			for(int i=0; i<items.size(); i++)
			{
				total = total + items.get(i).getQuantity() * items.get(i).getCategoryItem().getPrice();
			}
			
			return total;
		}
		
		//return an empty list count by default
		return 0;
	}	
}
