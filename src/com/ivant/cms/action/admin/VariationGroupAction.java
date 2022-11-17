package com.ivant.cms.action.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.usertype.UserType;

import com.ivant.cms.action.WebAction;
import com.ivant.cms.delegate.VariationGroupDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.VariationGroup;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;

public class VariationGroupAction extends ActionSupport implements UserAware, CompanyAware {

	private static final long serialVersionUID = -7170449348452935906L;
	private static final Logger logger = Logger.getLogger(VariationGroupAction.class);
	private static final VariationGroupDelegate variationGroupDelegate = VariationGroupDelegate.getInstance();
	
	private User user;
	private Company company;
	private List<VariationGroup> variationGroups;
	
	private String name;
	private String description;
	private long variationGroupId;
	private boolean insert;
	private boolean update;
	private VariationGroup variationGroup;
	
	@Override
	public String execute() throws Exception {
		variationGroups = variationGroupDelegate.findAll(company).getList();  
		return SUCCESS;
	}
	
	public String save() throws Exception {
		if(user.getUserType() != com.ivant.cms.enums.UserType.SUPER_USER    &&  user.getUserType() !=com.ivant.cms.enums.UserType.WEBTOGO_ADMINISTRATOR) {
			return WebAction.DASHBOARD;
		}
		
		ObjectList<VariationGroup> variationGroups = variationGroupDelegate.findAll(company);
           
		if(variationGroups.getTotal() >= 3) {
			return ERROR;
		} 
		
		logger.debug("UPDATE : " + update);
		
		if(insert && variationGroupDelegate.find(name) == null) {
			VariationGroup vg = new VariationGroup();
			vg.setCompany(company);
			vg.setName(name);
			vg.setDescription(description);
			
			variationGroupDelegate.insert(vg);
			
			return SUCCESS;
		}
		else if(update) {
			variationGroup = variationGroupDelegate.find(variationGroupId);
			
			if(variationGroup != null) {
				variationGroup.setName(name);
				variationGroup.setDescription(description);
				variationGroupDelegate.update(variationGroup);
				
				return SUCCESS;
			}
		}
		
		return ERROR;
	}

	public String delete() throws Exception {
		if(user.getUserType() != com.ivant.cms.enums.UserType.SUPER_USER   &&  user.getUserType() !=com.ivant.cms.enums.UserType.WEBTOGO_ADMINISTRATOR) {
			return WebAction.DASHBOARD;
		}
		
		if(variationGroupId > 0) {
			variationGroup = variationGroupDelegate.find(variationGroupId);
			if(!variationGroup.getCompany().equals(company)) {
				variationGroup = null;
			}
			else {
				variationGroupDelegate.delete(variationGroup);
				return SUCCESS;
			}
		}
		return ERROR;
	}
	
	public String edit() {
		if(user.getUserType() != com.ivant.cms.enums.UserType.SUPER_USER   &&  user.getUserType() !=com.ivant.cms.enums.UserType.WEBTOGO_ADMINISTRATOR) {
			return WebAction.DASHBOARD;
		}
		
		if(variationGroupId > 0) {
			variationGroup = variationGroupDelegate.find(variationGroupId);
			
			if(variationGroup != null) {
				return SUCCESS;
			}
		}
		
		return ERROR;
	}
	 
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setInsert(boolean insert) {
		this.insert = insert;
	}
	
	public List<VariationGroup> getVariationGroups() {
		return variationGroups;
	}
	
	public void setVariationGroupId(long variationGroupId) {
		this.variationGroupId = variationGroupId;
	}
	
	public VariationGroup getVariationGroup() {
		return variationGroup;
	}
	
	public void setUpdate(boolean update) {
		this.update = update;
	}
}
