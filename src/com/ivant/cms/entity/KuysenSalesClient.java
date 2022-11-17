package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity(name = "KuysenSalesClient")
@Table(name = KuysenSalesClient.TABLE_NAME)
public class KuysenSalesClient extends BaseObject {
	
	public static final String TABLE_NAME = "kuysensales_client";
	
	private String ref;
	private DateTime date;
	private String clientName;
	private String clientAddress;
	private String clientCompany;
	private String clientEmail;
	private String clientTelephone;
	private String clientFax;
	private String clientMobile;
	private String deliveryAddress;
	private String status;

	//Contact Person
	private String contactPersonName;
	private String contactPersonEmail;
	private String contactPersonTelephone;
	private String contactPersonFax;
	private String contactPersonMobile;
	
//Other Information

	//Architect
	private String architectName;
	private String architectEmail;
	private String architectContact;
	private String architectContractor;
	
	//Interior Designer
	private String interiorDesignerName;
	private String interiorDesignerEmail;
	private String interiorDesignerContact;
	
	//How did you hear about us
	private Boolean isOldClient;
	private Boolean isReferredBy;
	private String referredByName;
	private Boolean isByAdvertisingMaterial;
	private Boolean isByOthers;
	
	@Basic
	@Column(name="ref")
	public String getRef() {
		return ref;
	}
	
	@Basic
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(name = "date_of_registration")
	public DateTime getDate() {
		return date;
	}
	
	@Basic
	@Column(name="client_name")
	public String getClientName() {
		return clientName;
	}
	
	@Basic
	@Column(name="client_address")
	public String getClientAddress() {
		return clientAddress;
	}
	
	@Basic
	@Column(name="client_company")
	public String getClientCompany() {
		return clientCompany;
	}
	
	@Basic
	@Column(name="client_email")
	public String getClientEmail() {
		return clientEmail;
	}
	
	@Basic
	@Column(name="client_telephone")
	public String getClientTelephone() {
		return clientTelephone;
	}
	
	@Basic
	@Column(name="client_fax")
	public String getClientFax() {
		return clientFax;
	}
	
	@Basic
	@Column(name="client_mobile")
	public String getClientMobile() {
		return clientMobile;
	}
	
	@Basic
	@Column(name="delivery_address")
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	
	@Basic
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	
	@Basic
	@Column(name="contact_person_name")
	public String getContactPersonName() {
		return contactPersonName;
	}
	
	@Basic
	@Column(name="contact_person_email")
	public String getContactPersonEmail() {
		return contactPersonEmail;
	}
	
	@Basic
	@Column(name="contact_person_telephone")
	public String getContactPersonTelephone() {
		return contactPersonTelephone;
	}
	
	@Basic
	@Column(name="contact_person_fax")
	public String getContactPersonFax() {
		return contactPersonFax;
	}
	
	@Basic
	@Column(name="contact_person_mobile")
	public String getContactPersonMobile() {
		return contactPersonMobile;
	}
	
	@Basic
	@Column(name="architect_name")
	public String getArchitectName() {
		return architectName;
	}
	
	@Basic
	@Column(name="architect_email")
	public String getArchitectEmail() {
		return architectEmail;
	}
	
	@Basic
	@Column(name="architect_contact")
	public String getArchitectContact() {
		return architectContact;
	}
	
	@Basic
	@Column(name="architect_contractor")
	public String getArchitectContractor() {
		return architectContractor;
	}
	
	@Basic
	@Column(name="interior_designer_name")
	public String getInteriorDesignerName() {
		return interiorDesignerName;
	}
	
	@Basic
	@Column(name="interior_designer_email")
	public String getInteriorDesignerEmail() {
		return interiorDesignerEmail;
	}
	
	@Basic
	@Column(name="interior_designer_contact")
	public String getInteriorDesignerContact() {
		return interiorDesignerContact;
	}
	
	@Basic
	@Column(name="is_old_client")
	public Boolean getIsOldClient() {
		return isOldClient;
	}
	
	@Basic
	@Column(name="is_refered_by")
	public Boolean getIsReferredBy() {
		return isReferredBy;
	}
	
	@Basic
	@Column(name="referred_by_name")
	public String getReferredByName() {
		return referredByName;
	}
	
	@Basic
	@Column(name="is_by_advertising_material")
	public Boolean getIsByAdvertisingMaterial() {
		return isByAdvertisingMaterial;
	}
	
	@Basic
	@Column(name="is_by_others")
	public Boolean getIsByOthers() {
		return isByOthers;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public void setDate(DateTime date) {
		this.date = date;
	}
	
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	
	public void setClientTelephone(String clientTelephone) {
		this.clientTelephone = clientTelephone;
	}
	
	public void setClientFax(String clientFax) {
		this.clientFax = clientFax;
	}
	
	public void setClientMobile(String clientMobile) {
		this.clientMobile = clientMobile;
	}
	
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	
	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}
	
	public void setContactPersonTelephone(String contactPersonTelephone) {
		this.contactPersonTelephone = contactPersonTelephone;
	}
	
	public void setContactPersonFax(String contactPersonFax) {
		this.contactPersonFax = contactPersonFax;
	}
	
	public void setContactPersonMobile(String contactPersonMobile) {
		this.contactPersonMobile = contactPersonMobile;
	}
	
	public void setArchitectName(String architectName) {
		this.architectName = architectName;
	}
	
	public void setArchitectEmail(String architectEmail) {
		this.architectEmail = architectEmail;
	}
	
	public void setArchitectContact(String architectContact) {
		this.architectContact = architectContact;
	}
	
	public void setArchitectContractor(String architectContractor) {
		this.architectContractor = architectContractor;
	}
	
	public void setInteriorDesignerName(String interiorDesignerName) {
		this.interiorDesignerName = interiorDesignerName;
	}
	
	public void setInteriorDesignerEmail(String interiorDesignerEmail) {
		this.interiorDesignerEmail = interiorDesignerEmail;
	}
	
	public void setInteriorDesignerContact(String interiorDesignerContact) {
		this.interiorDesignerContact = interiorDesignerContact;
	}
	
	public void setIsOldClient(Boolean isOldClient) {
		this.isOldClient = isOldClient;
	}
	
	public void setIsReferredBy(Boolean isReferredBy) {
		this.isReferredBy = isReferredBy;
	}
	
	public void setReferredByName(String referredByName) {
		this.referredByName = referredByName;
	}
	
	public void setIsByAdvertisingMaterial(Boolean isByAdvertisingMaterial) {
		this.isByAdvertisingMaterial = isByAdvertisingMaterial;
	}
	
	public void setIsByOthers(Boolean isByOthers) {
		this.isByOthers = isByOthers;
	}

	public void setClientCompany(String clientCompany) {
		this.clientCompany = clientCompany;
	}
}
