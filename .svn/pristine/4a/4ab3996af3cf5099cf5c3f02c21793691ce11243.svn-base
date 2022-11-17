package com.ivant.cms.action.admin.dwr;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.AttributeDelegate;
import com.ivant.cms.delegate.PresetValueDelegate;
import com.ivant.cms.entity.Attribute;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.PresetValue;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.GroupAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class DWRPresetValueAction extends ActionSupport
	implements Preparable, ServletRequestAware, UserAware, CompanyAware, GroupAware {
	
	private static final long serialVersionUID = -5574751015332609721L;
	private static Logger logger = LoggerFactory.getLogger(DWRPresetValueAction.class);
	
	private Company company;
	private Group group;
	private User user;
	private PresetValue presetValue;
	private PresetValueDelegate presetValueDelegate = PresetValueDelegate.getInstance();
	private List<PresetValue> presetValues;
	private Attribute attribute;
	private AttributeDelegate attributeDelegate = AttributeDelegate.getInstance();
	private PresetValueDelegate updatedPresetValue = PresetValueDelegate.getInstance();
	
	public DWRPresetValueAction() {
	}
	

	@Override
	public void prepare() throws Exception
	{
		presetValueDelegate.findAll(attribute);
	}
	
	public String execute()
	{
			return SUCCESS;
	}

	/* DWR Methods start. */
	
	public PresetValue addPresetValue(String presetValue, String attributeId)
	{
		List<PresetValue> presetValues;
		attribute = attributeDelegate.find(Long.parseLong(attributeId)); 
		this.presetValue = new PresetValue();
		this.presetValue.setValue(presetValue);
		this.presetValue.setAttribute(attribute);

		presetValueDelegate.insert(this.presetValue);
		
		presetValues = updatedPresetValue.findAll();
		return updatedPresetValue.find(presetValues.get(presetValues.size()-1).getId());
	}
	
	public boolean updatePresetValue(String Id, String value)
	{
		this.presetValue = presetValueDelegate.find(Long.parseLong(Id));
		this.presetValue.setValue(value);
		presetValueDelegate.update(this.presetValue);
		return true;
	}
	
	public boolean deletePresetValue(long id)
	{
		this.presetValue = presetValueDelegate.find(id);
		if(this.presetValue != null)
		{
			presetValueDelegate.delete(presetValue);
			return true;	
		}
		else
		{
			return false;
		}
	}
	
	/* DWR Methods end. */

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		
	}


	public PresetValue getPresetValue() {
		return presetValue;
	}


	public void setPresetValue(PresetValue presetValue) {
		this.presetValue = presetValue;
	}


	public List<PresetValue> getPresetValues() {
		return presetValues;
	}


	public void setPresetValues(List<PresetValue> presetValues) {
		this.presetValues = presetValues;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
}
