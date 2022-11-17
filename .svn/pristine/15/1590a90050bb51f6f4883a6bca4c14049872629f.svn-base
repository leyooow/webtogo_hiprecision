package com.ivant.cms.ws.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.entity.Member;

@XmlRootElement(name = "Member")
public class MemberModel extends AbstractModel
{
	private String firstname;
	private String middlename;
	private String lastname;
	private String mobile;
	private String landline;
	private String email;
	private String address1;
	private String address2;
	private String city;
	private String province;
	private String info1;
	private String info2;
	private String info3;
	private String info4;
	private Boolean success;
	private String notificationMessage;
	
	public MemberModel()
	{
		
	}
	
	public MemberModel(Member member)
	{
		if(member == null)
			return;
		
		setId(member.getId());
		setCreatedOn(member.getCreatedOn().toString());
		setFirstname(member.getFirstname());
		setMiddlename(member.getMiddlename());
		setLastname(member.getLastname());
		setLandline(member.getLandline());
		setEmail(member.getEmail());
		setMobile(member.getMobile());
		setAddress1(member.getAddress1());
		setAddress2(member.getAddress2());
		setCity(member.getCity());
		setProvince(member.getProvince());
		
		setInfo1(member.getInfo1());
		setInfo2(member.getInfo2());
		setInfo3(member.getInfo3());
		setInfo4(member.getInfo4());
	}
	
	public String getFirstname()
	{
		return firstname;
	}
	
	public void setFirstname(String firstname) 
	{
		this.firstname = firstname;
	}
	
	public String getMiddlename()
	{
		return middlename;
	}

	public void setMiddlename(String middlename)
	{
		this.middlename = middlename;
	}

	public String getLastname() 
	{
		return lastname;
	}
	
	public void setLastname(String lastname) 
	{
		this.lastname = lastname;
	}
	
	public String getMobile() 
	{
		return mobile;
	}
	
	public void setMobile(String mobile) 
	{
		this.mobile = mobile;
	}
	
	public String getLandline() 
	{
		return landline;
	}
	
	public void setLandline(String landline) 
	{
		this.landline = landline;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getAddress1() 
	{
		return address1;
	}
	
	public void setAddress1(String address1) 
	{
		this.address1 = address1;
	}

	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province) 
	{
		this.province = province;
	}

	public String getInfo1()
	{
		return info1;
	}

	public void setInfo1(String info1)
	{
		this.info1 = info1;
	}

	public String getInfo2() 
	{
		return info2;
	}

	public void setInfo2(String info2) 
	{
		this.info2 = info2;
	}

	public String getInfo3() 
	{
		return info3;
	}

	public void setInfo3(String info3)
	{
		this.info3 = info3;
	}

	public String getInfo4() 
	{
		return info4;
	}

	public void setInfo4(String info4)
	{
		this.info4 = info4;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	
}
