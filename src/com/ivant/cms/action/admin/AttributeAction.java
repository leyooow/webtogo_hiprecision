package com.ivant.cms.action.admin;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.AttributeDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemAttributeDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.PresetValueDelegate;
import com.ivant.cms.entity.Attribute;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemAttribute;
import com.ivant.cms.entity.PresetValue;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.GroupAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class AttributeAction extends ActionSupport implements Preparable, ServletRequestAware, ServletContextAware,
					UserAware, CompanyAware, GroupAware {

	private static final long serialVersionUID = -6643627984835985732L;
	private static final Logger logger = Logger.getLogger(AttributeAction.class);
	private static final AttributeDelegate attributeDelegate = AttributeDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private static final ItemAttributeDelegate itemAttributeDelegate = ItemAttributeDelegate.getInstance();
	private static final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private static final PresetValueDelegate presetValueDelegate = PresetValueDelegate.getInstance();
	private HttpServletRequest request;
	private Company company;
	private Group group;
	private CompanySettings companySettings;
	
	private Attribute attribute;
	private List<PresetValue> presetValues;
	private String evt;
	private User user;
	private String attName, attDesc, dataType;
	
	private String errorType;
	
	public void prepare() throws Exception {
		companySettings = company.getCompanySettings();
		attName = request.getParameter("attribute_name");
		attDesc = request.getParameter("attribute_description");
		dataType = request.getParameter("datatype");
		try {
			long attId = Long.parseLong(request.getParameter("attribute_id"));
			attribute = attributeDelegate.find(attId);
			presetValues = presetValueDelegate.findAll(attribute).getList();
		}
		catch(Exception e) {
			attribute = new Attribute();
		}
	}

	public String save() throws Exception {
		if(!commonParamsValid()) {
			return ERROR;
		}
		attribute.setName(attName);
		attribute.setDescription(attDesc);
		attribute.setGroup(group);
		attribute.setCompany(company);
		attribute.setDataType(dataType);
		if(attributeDelegate.find(company, group, attribute.getName()) == null) {
			attributeDelegate.insert(attribute);
		}
		else {
			if(attribute.getId() == null) {
				errorType = "attributeexist";
				return ERROR;
			}
			else {
				attributeDelegate.update(attribute);
			}
		}
		
		lastUpdatedDelegate.saveLastUpdated(company);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if(!commonParamsValid()) {
			return ERROR;
		}
		
		if(!attribute.getCompany().equals(company)) {
			return ERROR;
		}
		
		/** delete all attribute in the items */
		for(int i=0; i<attribute.getAttributes().size(); i++){
			itemAttributeDelegate.delete(attribute.getAttributes().get(i));
		}
		attributeDelegate.delete(attribute);
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return SUCCESS;
	}
	
	public String edit() throws Exception {
		if(!commonParamsValid()) {
			return ERROR;
		} 
		
		return SUCCESS;
	}
	
	private boolean commonParamsValid() {
		if(company == null) {
			return false;
		}
		
		if(group == null) {
			return false;
		}
		return true;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
		
	public String getEvt() {
		return evt;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public CompanySettings getCompanySettings() {
		return companySettings;
	}
	
	public String getErrorType() {
		return errorType;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUser(User user) {
		this.user = user;
		
	}
	
	public static GroupDelegate getGroupDelegate() {
		return groupDelegate;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	public List<PresetValue> getPresetValues() {
		return presetValues;
	}

	public void setPresetValues(List<PresetValue> presetValues) {
		this.presetValues = presetValues;
	}
	
}
