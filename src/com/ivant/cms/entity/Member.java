package com.ivant.cms.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.NwdiPatientDelegate;
import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.enums.ReferralStatus;
import com.ivant.cms.interfaces.CompanyAware;

@Entity(name = "Member")
@Table(name = "member", uniqueConstraints = { @UniqueConstraint(columnNames = { "username","company_id"}) })
public class Member extends BaseObject implements Cloneable, CompanyAware, JSONAware {

	private String username;
	private String password;
	private String email;
	private String fullName;
	private String nameOnCertificate;
	private String firstname;
	private String lastname;
	private Company company;
	private MemberType memberType;
	private Date dateJoined;
	private Date lastLogin;
	private Date emailNotificationDate;
	private String activationKey;
	private String purpose;
	private Boolean activated;
	private Boolean newsletter = false;
	private Boolean emailNotification;
	private String status;
	private boolean verified = false;
	//note: this fields could be used in some instances --> points, total purchases, credits and any value.
	private String value;
	private String value2;
	private String value3;
	
	//if needed only, company needed in registration
	private String reg_companyName;
	private String reg_companyAddress;
	private String landline;
	private String mobile;
	private String fax;
	private String middlename;
	private String reg_companyPosition;
	private String referredByName;
	private String referredByNumber;
	private String address1;
	private String address2;
	private String province;
	private String city;
	private String zipcode;
	private String servicingOutlet; 
	private Boolean seniorCitizen; 
	
	//note: for some transactions that they do.
	private String logs;
	
	//extra information
	private String info1;
	private String info2;
	private String info3;
	private String info4;
	private String info5;
	
	private String info6;
	private String info7;
	
	//for multiple moduleLogin
	private String pageModule;
	private String pageModuleUsername;
	
	private CategoryItem categoryItem;
	
	
	private List<Referral> referralList;
	private List<Referral> approvedReferrals;
	
	private List<RegistrationItemOtherField> registrationItemOtherFields;
	
	private Long nwdiPatientId;
	
	public Member(){
		activated = true;
		emailNotification = false;
	}
	
	@Basic
	@Column(name = "username", length = 40, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}
	
	@Basic
	@Column(name = "verified", nullable = false)
	public boolean getVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@Basic
	@Column(name = "status", length = 40, nullable = true)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Basic
	@Column(name = "password", length = 40, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	@Basic
	@Column(name = "email", length = 60, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Basic
	@Column(name = "firstname", length = 50, nullable = true)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@Basic
	@Column(name = "middlename", length = 50, nullable = true)
	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	@Basic
	@Column(name = "fax", length = 50, nullable = true)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Basic
	@Column(name = "landline", length = 50, nullable = true)
	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	@Basic
	@Column(name = "mobile", length = 50, nullable = true)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Basic
	@Column(name = "lastname", length = 50, nullable = true)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Basic
	@Column(name = "activation_key", length = 300, nullable = true)
	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}
	public String getActivationKey() {
		return activationKey;
	}
	
	public String getInfo4() {
		return info4;
	}

	public void setInfo4(String info4) {
		this.info4 = info4;
	}

	@Basic
	@Column(name = "activated", nullable = false)
	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	@Basic
	@Column(name = "newsletter", nullable = false)
	public Boolean getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Boolean newsletter) {
		this.newsletter = newsletter;
	}
	@Basic
	@Column(name = "value", nullable = true)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Basic
	@Column(name = "value_2", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	@Basic
	@Column(name = "value_3", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	@Basic
	@Column(name = "logs", nullable = true)
	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}
	
	@Basic
	@Column(name = "member_companyName", nullable = true)
	public String getReg_companyName() {
		return reg_companyName;
	}

	public void setReg_companyName(String reg_companyName) {
		this.reg_companyName = reg_companyName;
	}
	@Basic
	@Column(name = "member_companyAddress", nullable = true)
	public String getReg_companyAddress() {
		return reg_companyAddress;
	}

	public void setReg_companyAddress(String reg_companyAddress) {
		this.reg_companyAddress = reg_companyAddress;
	}


	@ManyToOne(targetEntity = Company.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}
	
	@OneToMany(targetEntity = RegistrationItemOtherField.class, mappedBy = "member", fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)	
	public List<RegistrationItemOtherField> getRegistrationItemOtherFields() {
		return registrationItemOtherFields;
	}
	
	
	public void setRegistrationItemOtherFields(List<RegistrationItemOtherField> registrationItemOtherFields) {
		this.registrationItemOtherFields = registrationItemOtherFields;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@ManyToOne(targetEntity = MemberType.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "member_type_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_joined")
	public Date getDateJoined() {
		return dateJoined;
	} 
	
	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_login")
	public Date getLastLogin() {
		return lastLogin;
	} 
	
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	@Transient
	public String getFullName() {
		StringBuilder sb = new StringBuilder(50);
		if (StringUtils.trimToNull(lastname) != null)
			sb.append(lastname + ", ");
		if (StringUtils.trimToNull(firstname) != null)
			sb.append(firstname);
		return sb.toString();
	}

	@Override
	@Transient
	public Member clone() {
		Member clone = new Member();

		clone.setId(getId());
		clone.setCompany(getCompany());
		clone.setCreatedOn(getCreatedOn());
		clone.setEmail(getEmail());
		clone.setFirstname(getFirstname());
		clone.setLastname(getLastname());
		clone.setPassword(getPassword());
		clone.setUsername(getUsername());
		clone.setUpdatedOn(getUpdatedOn());
		clone.setDateJoined(getDateJoined());
		
		return clone;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			if (this.getId() == ((User) obj).getId()) {
				return true;
			}
		} else {
			throw new IllegalArgumentException();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return username.hashCode() + email.hashCode() + password.hashCode()
				+ firstname.hashCode() + lastname.hashCode()
				+ ((company != null) ? company.hashCode() : 0);
	}

	@Override
	public String toString() {
		return "id: " + getId() + "\n" + "username: " + getUsername() + "\n"
				+ "password: " + getPassword() + "\n" + "email: " + getEmail()
				+ "\n" + "firstname: " + getFirstname() + "\n" + "lastname: "
				+ getLastname() + "\n" + "\n"
				+ "company: "
				+ ((getCompany() != null) ? getCompany().getName() : "null")
				+ "\n";
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Basic
	@Column(name = "purpose", length = 40, nullable = true)
	public String getPurpose() {
		return purpose;
	}
	@Basic
	@Column(name = "reg_companyPosition", length = 255, nullable = true)
	public String getReg_companyPosition() {
		return reg_companyPosition;
	}

	public void setReg_companyPosition(String reg_companyPosition) {
		this.reg_companyPosition = reg_companyPosition;
	}
	
	@Basic
	@Column(name = "referred_by_name", length = 40, nullable = true)
	public String getReferredByName() {
		return referredByName;
	}

	public void setReferredByName(String referredByName) {
		this.referredByName = referredByName;
	}	
	
	@Basic
	@Column(name = "referred_by_number", length = 40, nullable = true)
	public String getReferredByNumber() {
		return referredByNumber;
	}

	public void setReferredByNumber(String referredByNumber) {
		this.referredByNumber = referredByNumber;
	}	
	
	@Basic
	@Column(name = "address1", length = Integer.MAX_VALUE, nullable = true) // changed length from 40 to Integer.MAX_VALUE
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}	
	
	@Basic
	@Column(name = "address2", length = Integer.MAX_VALUE, nullable = true) // changed length from 40 to Integer.MAX_VALUE
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}	

	@Basic
	@Column(name = "province", length = 40, nullable = true)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}	
	
	@Basic
	@Column(name = "city", length = 40, nullable = true)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}	

	@Basic
	@Column(name = "zipcode", length = 40, nullable = true)
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}	
	
	@Basic
	@Column(name = "servicing_outlet", length = 200, nullable = true)
	public String getServicingOutlet() {
		return servicingOutlet;
	}

	public void setServicingOutlet(String servicingOutlet) {
		this.servicingOutlet = servicingOutlet;
	}	
	
	@Basic
	@Column(name = "email_notification")
	public Boolean getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(Boolean emailNotification) {
		this.emailNotification = emailNotification;
	}

	public String getNameOnCertificate() {
		return nameOnCertificate;
	}

	public void setNameOnCertificate(String nameOnCertificate) {
		this.nameOnCertificate = nameOnCertificate;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}	
	
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Referral.class, mappedBy = "referredBy")
	@Fetch(value = FetchMode.SELECT)
	@OrderBy("id desc")
	@LazyCollection(value = LazyCollectionOption.EXTRA)
	public List<Referral> getReferralList()
	{
		if (referralList == null)
		{
			referralList = new LinkedList<Referral>();
		}
		return referralList;
	}
	
	public void setReferralList(List<Referral> referralList)
	{
		this.referralList = referralList;
	}

	public void setApprovedReferrals(List<Referral> approvedReferrals) {
		this.approvedReferrals = approvedReferrals;
	}
	
	@Transient
	public List<Referral> getApprovedReferrals() {
		if (referralList == null)
		{
			return new LinkedList<Referral>();
		}
		approvedReferrals = new LinkedList<Referral>();
		for(Referral ref:getReferralList()){
			if(ref.getStatus().equals(ReferralStatus.APPROVED))
				approvedReferrals.add(ref);
		}
		
		return approvedReferrals;
	}
	
	@Transient
	public List<Referral> getRequestedReferrals() {
		if (referralList == null)
		{
			return new LinkedList<Referral>();
		}
		List<Referral> requestedReferrals = new LinkedList<Referral>();
		for(Referral ref:getReferralList()){
			if(ref.getStatus().equals(ReferralStatus.REQUESTED))
				requestedReferrals.add(ref);
		}
		
		return requestedReferrals;
	}

	@Basic
	@Column(name = "info_1", nullable = true)
	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	@Basic
	@Column(name = "info_2", nullable = true)
	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	@Basic
	@Column(name = "info_3", nullable = true)
	public String getInfo3() {
		return info3;
	}

	public void setInfo3(String info3) {
		this.info3 = info3;
	}


	@Basic
	@Column(name = "senior_citizen")
	public Boolean getSeniorCitizen() {
		return seniorCitizen;
	}

	public void setSeniorCitizen(Boolean seniorCitizen) {
		this.seniorCitizen = seniorCitizen;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "email_notification_date")
	public Date getEmailNotificationDate() {
		return emailNotificationDate;
	}

	public void setEmailNotificationDate(Date emailNotificationDate) {
		this.emailNotificationDate = emailNotificationDate;
	}
	
	@Transient
	public  Map<Long, CartOrder> getLastOrderTransactionMap(){
		final Map<Long, CartOrder> lastOrderTransactionMap = new HashMap<Long, CartOrder>();
		final List<Member> listMemberItem = MemberDelegate.getInstance().findAll(company).getList();
		for(Member memberItem : listMemberItem) {
			//lastOrderTransactionMap(memberItem.getId(),)
		}
		
		
		return lastOrderTransactionMap;
	}
	
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "category_item_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}

	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	@Basic
	@Column(name = "info_4", nullable = true)
	public String getInfo6() {
		return info6;
	}
	
	public void setInfo6(String info6) {
		this.info6 = info6;
	}
	
	@Basic
	@Column(name = "info_5", nullable = true)
	public String getInfo5() {
		return info5;
	}

	public void setInfo5(String info5) {
		this.info5 = info5;
	}
	
	@Basic
	@Column(name = "info_6", nullable = true)
	public String getInfo7() {
		return info7;
	}
	
	public void setInfo7(String info7) {
		this.info7 = info7;
	}
	
	@Basic
	@Column(name = "page_module", nullable = true)
	public String getPageModule() {
		return pageModule;
	}

	public void setPageModule(String pageModule) {
		this.pageModule = pageModule;
	}
	
	@Basic
	@Column(name = "page_module_username", nullable = true)
	public String getPageModuleUsername() {
		return pageModuleUsername;
	}

	public void setPageModuleUsername(String pageModuleUsername) {
		this.pageModuleUsername = pageModuleUsername;
	}
	
	@Basic
	@Column(name="nwdi_patient_id")
	public Long getNwdiPatientId() {
		return nwdiPatientId;
	}

	public void setNwdiPatientId(Long nwdiPatientId) {
		this.nwdiPatientId = nwdiPatientId;
	}
	
	@Transient
	public NwdiPatient getNwdiPatient(){
		if(nwdiPatientId != null){
			return NwdiPatientDelegate.getInstance().find(nwdiPatientId);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		json.put("lastname", getLastname());
		json.put("firstname", getFirstname());
		json.put("company", getReg_companyName());
		json.put("info2", getInfo2());
		json.put("tag", getInfo1());
		json.put("email", getEmail());
		json.put("position", getReg_companyPosition());
		json.put("username", getUsername());
		json.put("mobile", getMobile());
		json.put("info3", getInfo3());
		json.put("info4", getInfo4());
		json.put("info5", getInfo5());
		json.put("value2", getValue2());
		json.put("value3", getValue3());
		return json.toJSONString();
	}
	

}
