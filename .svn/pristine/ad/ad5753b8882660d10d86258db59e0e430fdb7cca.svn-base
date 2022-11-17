package com.ivant.cms.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.ivant.cms.interfaces.BaseID;

/**
 *  NOTE: Not all table columns were mapped in this entity. Only the needed as to date.
 * @author Eric John Apondar
 * @since Mar 14, 2017
 */
@Entity(name="NwdiPatient")
@Table(name=NwdiPatient.TABLE_NAME)
@Transactional(readOnly=true)
public class NwdiPatient implements BaseID<Long>, JSONAware, Serializable{
	
	private static final long serialVersionUID = -8524685060053171726L;

	public static final String TABLE_NAME = "PATIENTINFO";
	
	private Long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String patientNo;
	private DateTime birthDate;
	private String email;
	private String address;
	private String barangay;
	private String municipality;
	private String zipcode;
	private String sex;
	private String status;
	private String phonenos;
	
	private List<NwdiResult> results;
	
	
	
	@Id
	@Column( name="ID" )
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column(name="FIRSTNAME")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Basic
	@Column(name="MIDDLENAME")
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	@Basic
	@Column(name="LASTNAME")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	@Basic
	@Column(name="PATIENTNO")
	public String getPatientNo() {
		return patientNo;
	}
	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}
	
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(name="DBIRTH")
	public DateTime getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(DateTime birthDate) {
		this.birthDate = birthDate;
	}
	
	@Basic
	@Column(name="EMAILADD")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Basic
	@Column(name="ADDRESS")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Basic
	@Column(name="BARANGAY")
	public String getBarangay() {
		return barangay;
	}
	public void setBarangay(String barangay) {
		this.barangay = barangay;
	}
	
	@Basic
	@Column(name="MUNICIPALITY")
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	
	@Basic
	@Column(name="ZIPCODE")
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@Basic
	@Column(name="SEX")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Basic
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Basic
	@Column(name="PHONENOS")
	public String getPhonenos() {
		return phonenos;
	}
	public void setPhonenos(String phonenos) {
		this.phonenos = phonenos;
	}
	
	
	@OneToMany(targetEntity=NwdiResult.class, fetch=FetchType.LAZY)
	@JoinColumn(name="PATIENTNO", referencedColumnName="PATIENTNO",  nullable=true, insertable=false, updatable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public List<NwdiResult> getResults() {
		return results;
	}
	public void setResults(List<NwdiResult> results) {
		this.results = results;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		json.put("firstname", firstName);
		json.put("middlename", middleName);
		json.put("lastname", lastName);
		json.put("email", email);
		json.put("birthdate", birthDate);
		return json.toJSONString();
	}
	
	@Override
	public String toString() {
		return toJSONString();
	}
	
}
