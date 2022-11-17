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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;

@Entity(name = "User")
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class User extends BaseObject implements Cloneable, CompanyAware {

	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	private UserType userType;
	private Company company;	
	private Integer itemsPerPage; 
	private Date lastLogin;
	private Boolean active;
	private String branch;
	private String info1;
	private String info2;
	private String info3;
	private String info4;
	
	private List<SinglePage> forbiddenSinglePages;
	private List<MultiPage> forbiddenMultiPages;
	private List<Group> forbiddenGroups;
	private List<Category> forbiddenCategories;
	
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
	@Column(name = "username", length = 40, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	@Basic
	@Column(name = "password", length = 40, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	@Column(name = "lastname", length = 50, nullable = true)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "user_type", length = 30, nullable = false)
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
		clone.setBranch(getBranch());
		clone.setInfo1(getInfo1());
		clone.setInfo2(getInfo2());
		clone.setInfo3(getInfo3());
		clone.setInfo4(getInfo4());
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
				+ "\n" + "branch: " + getBranch() + "\n"
				+ "\n" + "info1: " + getInfo1() + "\n"
				+ "\n" + "info2: " + getInfo2() + "\n"
				+ "\n" + "info3: " + getInfo3() + "\n"
				+ "\n" + "info4: " + getInfo4() + "\n";
	}
	
	@ManyToMany(targetEntity = SinglePage.class, fetch = FetchType.LAZY)
	@JoinTable(name = "user_single_page", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "single_page_id") })
	@Fetch(FetchMode.SELECT)
	@ForeignKey(name = "FK_USER_FORBIDDEN_SINGLE_PAGE", inverseName = "FK_SINGLE_PAGE_USER")
	public List<SinglePage> getForbiddenSinglePages() {
		return forbiddenSinglePages;
	}

	public void setForbiddenSinglePages(List<SinglePage> forbiddenSinglePages) {
		this.forbiddenSinglePages = forbiddenSinglePages;
	}
	
	@ManyToMany(targetEntity = MultiPage.class, fetch = FetchType.LAZY)
	@JoinTable(name = "user_multi_page", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "multi_page_id") })
	@Fetch(FetchMode.SELECT)
	@ForeignKey(name = "FK_USER_FORBIDDEN_MULTI_PAGE", inverseName = "FK_MULTI_PAGE_USER")
	public List<MultiPage> getForbiddenMultiPages() {
		return forbiddenMultiPages;
	}

	public void setForbiddenMultiPages(List<MultiPage> forbiddenMultiPages) {
		this.forbiddenMultiPages = forbiddenMultiPages;
	}

	@ManyToMany(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinTable(name = "user_group_page", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "group_id") })
	@Fetch(FetchMode.SELECT)
	@ForeignKey(name = "FK_USER_FORBIDDEN_GROUP_PAGE", inverseName = "FK_GROUP_USER")
	public List<Group> getForbiddenGroups() {
		return forbiddenGroups;
	}

	public void setForbiddenGroups(List<Group> forbiddenGroups) {
		this.forbiddenGroups = forbiddenGroups;
	}
	
	
	@ManyToMany(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinTable(name = "user_category_page", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "category_id") })
	@Fetch(FetchMode.SELECT)
	@ForeignKey(name = "FK_USER_FORBIDDEN_CATEGORY_PAGE", inverseName = "FK_CATEGORY_USER")
	public List<Category> getForbiddenCategories() {
		return forbiddenCategories;
	}

	public void setForbiddenCategories(List<Category> forbiddenCategories) {
		this.forbiddenCategories = forbiddenCategories;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Basic
	@Column(name = "active")
	public Boolean getActive() {
		if(active == null)
		{
			this.active = Boolean.TRUE;
		}
		return active;
	}
	
	@Basic
	@Column(name = "branch", length = 50, nullable = true)
	public String getBranch() {
		return branch;
	}
	
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	@Basic
	@Column(name = "info1", length = 500, nullable = true)
	public String getInfo1() {
		return info1;
	}
	
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	@Basic
	@Column(name = "info2", length = 500, nullable = true)
	public String getInfo2() {
		return info2;
	}
	
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	
	@Basic
	@Column(name = "info3", length = 500, nullable = true)
	public String getInfo3() {
		return info3;
	}
	
	public void setInfo3(String info3) {
		this.info3 = info3;
	}
	
	@Basic
	@Column(name = "info4", length = 500, nullable = true)
	public String getInfo4() {
		return info4;
	}
	
	public void setInfo4(String info4) {
		this.info4 = info4;
	}
	
}
