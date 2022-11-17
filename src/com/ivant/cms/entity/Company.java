package com.ivant.cms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.enums.BusinessType;

@Entity(name = "Company")
@Table(name = "company")
public class Company extends BaseObject {

	private String name;
	private String nameEditable;
	private String address;
	private String address2;
	private String state;
	private String phone;
	private String cellphone;
	private String fax;
	private String contactPerson;
	private String contactPersonDesignation;
	private String remarks;
	private BusinessType businessType;
	private String email;
	private List<User> users;
	private String domainName;
	private Date expiryDate;
	private CompanySettings companySettings;
	private String logo;
	private String serverName;
	private String keywords;
	private String registrationClassName;
	private String title;
	private String statement;
	private LastUpdated lastUpdated;
	private Double flatRateShippingPrice;
	private List<ShippingTable> shippingTable;
	private List<Language> languages;
	
	//setting for HTML META tags
	private String metaDescription;
	private String metaAuthor;
	private String metaCopyright;
	
	//setting for Paypal
	private String palCurrencyType;
	private String palUsername;
	private String palPassword;
	private String palSignature;
	private String palSuccessUrl;
	private String palCancelUrl;
	
	private Integer notifDuration;
	
	
	private Company secondaryCompany;
	
	
	@Basic
	@Column(name = "meta_description", columnDefinition="text", nullable = true)
	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@Basic
	@Column(name = "meta_author", nullable = true)
	public String getMetaAuthor() {
		return metaAuthor;
	}

	public void setMetaAuthor(String metaAuthor) {
		this.metaAuthor = metaAuthor;
	}

	@Basic
	@Column(name = "meta_copyright", nullable = true)
	public String getMetaCopyright() {
		return metaCopyright;
	}

	public void setMetaCopyright(String metaCopyright) {
		this.metaCopyright = metaCopyright;
	}
	
	@Basic
	@Column(name = "keywords", columnDefinition="text", nullable = true)
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Basic
	@Column(name = "title", columnDefinition="text", nullable = true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Basic
	@Column(name = "name", length = 50, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "name_editable", length = 50)
	public String getNameEditable() {
		return nameEditable;
	}

	public void setNameEditable(String nameEditable) {
		this.nameEditable = nameEditable;
	}

	@Basic
	@Column(name = "address", length = 255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Basic
	@Column(name = "address2", length = 255)
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	@Basic
	@Column(name = "phone", length = 30)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Basic
	@Column(name = "contact_person", length = 50)
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "business_type")
	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	@Basic
	@Column(name = "email", nullable = false, columnDefinition = "text")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(targetEntity = User.class, mappedBy = "company", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("username asc")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Basic
	@Column(name = "domain_name", length = 50, nullable = false)
	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	@OneToOne(targetEntity = CompanySettings.class, mappedBy = "company", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}

	@Basic
	@Column(name = "logo", length = 255)
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Basic
	@Column(name = "server_name", length = 255)
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@OneToOne(targetEntity = LastUpdated.class, mappedBy = "company", fetch = FetchType.LAZY)
	@Where(clause = "valid=1") 
	public LastUpdated getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LastUpdated lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Company) {
			if (getId().equals(((Company) obj).getId())) {
				return true;
			}
		} else {
			throw new IllegalArgumentException();
		}
		return false;
	}

	@Override
	public String toString() {
		return "id: " + getId() + "\n" + "name: " + getName() + "\n"
				+ "address: " + getAddress() + "\n" + "phone: " + getPhone()
				+ "\n" + "contact person: " + getContactPerson() + "\n"
				+ "business type: " + getBusinessType() + "\n" + "email: "
				+ getEmail() + "\n";
	}

	@Basic
	@Column(name = "expiry_date", nullable = true)
	@Temporal(value = TemporalType.DATE)
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Basic
	@Column(name = "state", length = 50)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Basic
	@Column(name = "cellphone", length = 30)
	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@Basic
	@Column(name = "fax", length = 30)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}


	@Basic
	@Column(name = "contact_person_designation", length = 50, nullable = true)
	public String getContactPersonDesignation() {
		return contactPersonDesignation;
	}
	
	public void setContactPersonDesignation(String contactPersonDesignation) {
		this.contactPersonDesignation = contactPersonDesignation;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getStatement() {
		return statement;
	}

	public void setFlatRateShippingPrice(Double flatRateShippingPrice) {
		this.flatRateShippingPrice = flatRateShippingPrice;
	}

	public Double getFlatRateShippingPrice() {
		return flatRateShippingPrice;
	}
	
	
	public void setShippingTable(List<ShippingTable> shippingTable) {
		this.shippingTable = shippingTable;
	}

	@OneToMany(targetEntity = ShippingTable.class, mappedBy = "company", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	public List<ShippingTable> getShippingTable() {
		return shippingTable;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the remarks
	 */
	@Basic
	@Column(name = "remarks", nullable = true)
	public String getRemarks() {
		return remarks;
	}

	@ManyToOne(targetEntity = Company.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "secondary_company_id", nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	public Company getSecondaryCompany() {
		return secondaryCompany;
	}

	public void setSecondaryCompany(Company secondaryCompany) {
		this.secondaryCompany = secondaryCompany;
	}

	public void setRegistrationClassName(String registrationClassName) {
		this.registrationClassName = registrationClassName;
	}
	@Basic
	@Column(name = "registration_class_name", length = 100)
	public String getRegistrationClassName() {
		return registrationClassName;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	
	@OneToMany(targetEntity = Language.class, mappedBy = "company", fetch = FetchType.LAZY)
	public List<Language> getLanguages() {
		return languages;
	}
	
	@Basic
	@Column(name = "paypal_username", columnDefinition = "text")
	public String getPalUsername() {
		return palUsername;
	}

	public void setPalUsername(String palUsername) {
		this.palUsername = palUsername;
	}
	
	@Basic
	@Column(name = "paypal_password", columnDefinition = "text")
	public String getPalPassword() {
		return palPassword;
	}

	public void setPalPassword(String palPassword) {
		this.palPassword = palPassword;
	}
	
	@Basic
	@Column(name = "paypal_signature", columnDefinition = "text")
	public String getPalSignature() {
		return palSignature;
	}

	public void setPalSignature(String palSignature) {
		this.palSignature = palSignature;
	}
	@Basic
	@Column(name = "paypal_success", columnDefinition = "text")
	public String getPalSuccessUrl() {
		return palSuccessUrl;
	}

	public void setPalSuccessUrl(String palSuccessUrl) {
		this.palSuccessUrl = palSuccessUrl;
	}
	@Basic
	@Column(name = "paypal_cancel", columnDefinition = "text")
	public String getPalCancelUrl() {
		return palCancelUrl;
	}

	public void setPalCancelUrl(String palCancelUrl) {
		this.palCancelUrl = palCancelUrl;
	}
	@Basic
	@Column(name = "paypal_currencyType", columnDefinition = "text")
	public String getPalCurrencyType() {
		return palCurrencyType;
	}

	public void setPalCurrencyType(String palCurrencyType) {
		this.palCurrencyType = palCurrencyType;
	}
	
	@Basic
	@Column(name = "notif_duration", length = 50)
	public Integer getNotifDuration() {
		return notifDuration;
	}

	public void setNotifDuration(Integer notifDuration) {
		this.notifDuration = notifDuration;
	}
	
}
