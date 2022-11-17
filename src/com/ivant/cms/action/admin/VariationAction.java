package com.ivant.cms.action.admin;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import org.apache.commons.collections.SetUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.action.WebAction;
import com.ivant.cms.delegate.VariationDelegate;
import com.ivant.cms.delegate.VariationGroupDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.Variation;
import com.ivant.cms.entity.VariationGroup;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class VariationAction extends ActionSupport implements CompanyAware, UserAware, Preparable {

	private static final long serialVersionUID = -2827867050802134110L;
	private static final Logger logger = Logger.getLogger(VariationAction.class);
	private static final VariationDelegate variationDelegate = VariationDelegate.getInstance();
	private static final VariationGroupDelegate variationGroupDelegate = VariationGroupDelegate.getInstance();
	private Company company;
	private User user;
	
	private List<VariationGroup> variationGroups;
	private List<Variation> variations;
	private long variationGroupId;	
	private long variationId;
	private String name;
	
	private boolean insert;
	private boolean update;
	
	private Variation variation;
	
	public void prepare() throws Exception {
		variationGroups = variationGroupDelegate.findAll(company, Order.asc("name"), 
				Order.asc("name")).getList();	
		
	}
	
	@Override
	public String execute() throws Exception {
		if(user.getUserType() != com.ivant.cms.enums.UserType.SUPER_USER   &&  user.getUserType() !=com.ivant.cms.enums.UserType.WEBTOGO_ADMINISTRATOR) {
			return WebAction.DASHBOARD;
		}
		
		variations = variationDelegate.findAll(company).getList();
		return SUCCESS;
	}
	
	public String save() throws Exception { 
		if(user.getUserType() != com.ivant.cms.enums.UserType.SUPER_USER   &&  user.getUserType() !=com.ivant.cms.enums.UserType.WEBTOGO_ADMINISTRATOR) {
			return WebAction.DASHBOARD;
		}
		
		if(variationGroupId > 0 && name != null) {
			
			VariationGroup vg = variationGroupDelegate.find(variationGroupId);
			
			if(insert && variationDelegate.find(name) == null) {
				if(vg != null) {
					variation = new Variation();
					variation.setCompany(company);
					variation.setName(name);
					variation.setVariationGroup(vg);
					 
					variationDelegate.insert(variation);
					
					return SUCCESS;
				}
			} 
			else if(update && variationId > 0) {
				variation = variationDelegate.find(variationId);
				if(variation.getCompany().equals(company)) {
					variation.setName(name);
					variation.setVariationGroup(vg);
					
					return SUCCESS;
				}
			}
			
		} 
		
		return ERROR;
	}
	
	public String edit() throws Exception {
		if(user.getUserType() != com.ivant.cms.enums.UserType.SUPER_USER   &&  user.getUserType() !=com.ivant.cms.enums.UserType.WEBTOGO_ADMINISTRATOR) {
			return WebAction.DASHBOARD;
		}
		
		if(variationId > 0) {
			variation = variationDelegate.find(variationId);
			if(!variation.getCompany().equals(company)) {
				variation = null;
			}
		}
		 
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if(user.getUserType() != com.ivant.cms.enums.UserType.SUPER_USER   &&  user.getUserType() !=com.ivant.cms.enums.UserType.WEBTOGO_ADMINISTRATOR) {
			return WebAction.DASHBOARD;
		}
		
		if(variationId > 0) {
			variation = variationDelegate.find(variationId);
			if(variation.getCompany().equals(company)) {
				variationDelegate.delete(variation);
			}
		}
		
		return ERROR;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	public void setUser(User user) {
		this.user = user;
	} 

	public List<VariationGroup> getVariationGroups() {
		return variationGroups;
	}
	
	public void setVariationGroupId(long variationGroupId) {
		this.variationGroupId = variationGroupId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setInsert(boolean insert) {
		this.insert = insert;
	}
	
	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	public void setVariationId(long variationId) {
		this.variationId = variationId;
	}
	
	public List<Variation> getVariations() {
		return variations;
	}
	
	public Variation getVariation() {
		return variation;
	}
}
