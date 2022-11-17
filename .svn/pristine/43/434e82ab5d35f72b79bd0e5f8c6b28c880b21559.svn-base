package com.ivant.cms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.EnumType;
import javax.persistence.Transient;

import com.ivant.cms.entity.Payment;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.enums.BillingStatusEnum;
import com.ivant.cms.enums.BillingTypeEnum;

@Entity(name="Billing")
@Table(name="billing")
public class Billing extends CompanyBaseObject
{
				private BillingStatusEnum status;
				private BillingTypeEnum type;
				private Date fromDate;
				private Date toDate;
				private Date dueDate;
				private double amountDue;
				private String note;
				private List<Payment> payment;
				private double totalPayments;
				
				
				@Transient
				public double getTotalPayments() {
					double total=0.00;
					for (Payment  p : payment)
					{
						total+=p.getPaidAmount();
					}
					
					return total;
				}



				public Billing()
				{
					
				}
				
				public Billing (BillingStatusEnum status, BillingTypeEnum type, Date fromDate, Date toDate, Date dueDate, double amountDue, String note, Company company)
				{
					this.setStatus(status);
					this.setType(type);
					this.setFromDate(fromDate);
					this.setToDate(toDate);
					this.setDueDate(dueDate);
					this.setAmountDue(amountDue);
					this.setNote(note);
					this.setCompany(company);
					this.setIsValid(true);
					this.setCreatedOn(new Date());
					
				}
				
				
				
				@Enumerated(EnumType.ORDINAL)
				@Column( name="status", nullable=false)
				public BillingStatusEnum getStatus()
				{
					return status;
				}
				
				public void setStatus(BillingStatusEnum status)
				{
					this.status = status;
				}
				
				@Enumerated(EnumType.ORDINAL)
				@Column( name="type", nullable=false)
				public BillingTypeEnum getType()
				{
					return type;
				}
				
				
				public void setType(BillingTypeEnum type)
				{
					this.type = type;
				}
				
				@Basic
				@Column( name="from_date", nullable=true)
				@Temporal(value = TemporalType.DATE)
				public Date getFromDate()
				{
					return fromDate;
				}
				
				
				public void setFromDate(Date fromDate)
				{
					this.fromDate = fromDate;
				}
				
				@Basic
				@Column( name="to_date", nullable=true)
				@Temporal(value = TemporalType.DATE)
				public Date getToDate()
				{
					return toDate;
				}
				
				public void setToDate(Date toDate)
				{
					this.toDate = toDate;
				}
				
				@Basic
				@Column( name="due_date", nullable=true)
				@Temporal(value = TemporalType.DATE)
				public Date getDueDate()
				{
					return dueDate;
				}
				
				
				public void setDueDate(Date dueDate)
				{
					this.dueDate = dueDate;
				}
				
				@Basic
				@Column(name="amount_due", nullable=false)
				public double getAmountDue()
				{
					return amountDue;
				}
				
				
				public void setAmountDue(double amountDue)
				{
					this.amountDue = amountDue;
				}
				
				@Basic
				@Column(name="note", length=255, nullable=true)
				public String getNote()
				{
					return note;
				}
				
				
				public void setNote(String note)
				{
					this.note = note;
				}

				@OneToMany(targetEntity = Payment.class, mappedBy = "billing")
				public List<Payment> getPayment()
				{
					return payment;
				}

				public void setPayment(List<Payment> payment)
				{
					this.payment = payment;
				}


}
