package com.ivant.cms.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.enums.PaymentTypeEnum;

@Entity(name="Payment")
@Table(name="payment")
public class Payment extends CompanyBaseObject
{
			private Billing billing;
			private PaymentTypeEnum type;
			private double paidAmount;
			private Date paymentDate;
			private String note;
			private String orNumber;
			private String bank;
			private String checkNumber;
			private Date checkDate;
			private String receivedBy;
			
			@Basic
			@Column(name="or_number")			
			public String getOrNumber() {
				return orNumber;
			}

			public void setOrNumber(String orNumber) {
				this.orNumber = orNumber;
			}

			@Basic
			@Column(name="bank")			
			public String getBank() {
				return bank;
			}

			public void setBank(String bank) {
				this.bank = bank;
			}

			@Basic
			@Column(name="check_number")
			public String getCheckNumber() {
				return checkNumber;
			}

			public void setCheckNumber(String checkNumber) {
				this.checkNumber = checkNumber;
			}

			@Basic
			@Column( name="check_date", nullable=true)
			@Temporal(value = TemporalType.TIMESTAMP)
			public Date getCheckDate() {
				return checkDate;
			}

			public void setCheckDate(Date checkDate) {
				this.checkDate = checkDate;
			}

			@Basic
			@Column(name="received_by")
			public String getReceivedBy() {
				return receivedBy;
			}

			public void setReceivedBy(String receivedBy) {
				this.receivedBy = receivedBy;
			}

			public Payment()
			{
				
			}
			
			public Payment(Billing billing, PaymentTypeEnum type, double paidAmount, Date paymentDate, String note)
			{
				
				this.setBilling(billing);
				this.setType(type);
				this.setPaidAmount(paidAmount);
				this.setPaymentDate(paymentDate);
				this.setNote(note);
				this.setCompany(this.billing.getCompany());
				
			}
			
			
			@ManyToOne(targetEntity = Billing.class, fetch = FetchType.LAZY)
			@JoinColumn(name = "billing_id", insertable=true, updatable=true, nullable=true)
			@NotFound(action = NotFoundAction.IGNORE) 
			public Billing getBilling()
			{
				return billing;
			}
			
			
			public void setBilling(Billing billing)
			{
				this.billing = billing;
			}
			
			
			@Enumerated(EnumType.ORDINAL)
			public PaymentTypeEnum getType()
			{
				return type;
			}
			
			
			public void setType(PaymentTypeEnum type)
			{
				this.type = type;
			}
			
			
			@Basic
			@Column(name="paid_amount")
			public double getPaidAmount()
			{
				return paidAmount;
			}
			
			
			public void setPaidAmount(double paidAmount)
			{
				this.paidAmount = paidAmount;
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

			@Basic
			@Column( name="payment_date", nullable=true)
			@Temporal(value = TemporalType.TIMESTAMP)
			public Date getPaymentDate()
			{
				return paymentDate;
			}


			public void setPaymentDate(Date paymentDate)
			{
				this.paymentDate = paymentDate;
			}


}
