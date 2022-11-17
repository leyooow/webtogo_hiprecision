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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;

@Entity(name = "ShippingTable")
@Table(name = "shipping_table", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class ShippingTable extends BaseObject implements Cloneable, CompanyAware {

	//this 3 are the only significant attributes. Actually the other attibutes can be deleted
	private Double fromPrice;
	private Double toPrice;
	private Double shippingPrice;
	
	
	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	private UserType userType;
	private Company company;	
	private Integer itemsPerPage; 
	private Date lastLogin;
	
	
	@Basic 
	@Column(name = "items_per_page", nullable=true)
	public Integer getItemsPerPage()
	{
		return itemsPerPage;
	}
	//private int numberPerPage;

	public void setItemsPerPage(Integer itemsPerPage)
	{
		this.itemsPerPage = itemsPerPage;
	}

	@Basic
	@Column(name = "username", length = 40, nullable = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	@Basic
	@Column(name = "password", length = 40, nullable = true)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "email", length = 60, nullable = true)
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
	@Column(name = "lastname", length = 50, nullable = true)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "user_type", length = 30, nullable = true)
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	@ManyToOne(targetEntity = Company.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
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
		if (lastname != null)
			sb.append(lastname + ", ");
		if (firstname != null)
			sb.append(firstname);
		return sb.toString();
	}

	@Override
	@Transient
	public User clone() {
		User clone = new User();

		clone.setId(getId());
		clone.setCompany(getCompany());
		clone.setCreatedOn(getCreatedOn());
		clone.setEmail(getEmail());
		clone.setFirstname(getFirstname());
		clone.setLastname(getLastname());
		clone.setPassword(getPassword());
		clone.setUsername(getUsername());
		clone.setUpdatedOn(getUpdatedOn());
		clone.setUserType(getUserType());
		clone.setItemsPerPage(getItemsPerPage());
		clone.setLastLogin(getLastLogin());
		
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
				+ userType.hashCode()
				+ ((company != null) ? company.hashCode() : 0);
	}

	@Override
	public String toString() {
		return "id: " + getId() + "\n" + "username: " + getUsername() + "\n"
				+ "password: " + getPassword() + "\n" + "email: " + getEmail()
				+ "\n" + "firstname: " + getFirstname() + "\n" + "lastname: "
				+ getLastname() + "\n" + "user type: " + getUserType() + "\n"
				+ "company: "
				+ ((getCompany() != null) ? getCompany().getName() : "null")
				+ "\n";
	}

	public void setFromPrice(Double fromPrice) {
		this.fromPrice = fromPrice;
	}

	public Double getFromPrice() {
		return fromPrice;
	}

	public void setToPrice(Double toPrice) {
		this.toPrice = toPrice;
	}

	public Double getToPrice() {
		return toPrice;
	}

	public void setShippingPrice(Double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public Double getShippingPrice() {
		return shippingPrice;
	}
}

