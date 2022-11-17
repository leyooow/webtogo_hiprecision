package com.ivant.cms.action.admin;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.BusinessType;
import com.ivant.cms.enums.CompanyStatusEnum;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;

public class ListCompanyAction implements Action, UserAware, PagingAware {

	private Logger logger = Logger.getLogger(getClass());
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	

	private User user;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	private List<Company> activeCompanies;
	private List<Company> ongoingCompanies;
	
	private List<Company> companiesList;
	private List<BusinessType> businessTypes;
	

	public String execute() throws Exception
	{
		if(user.getUserType() != UserType.SUPER_USER && user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR)
		{
			return Action.ERROR;
		}
		
		activeCompanies = companyDelegate.findByStatus(CompanyStatusEnum.ACTIVE).getList();
		ongoingCompanies = companyDelegate.findByStatus(CompanyStatusEnum.ONGOING).getList();

		String[] order = { "name" };
		// System.out.println("1  ItemsPerPage,  TotalItems :  " + itemsPerPage + "  " + totalItems);
		ObjectList<Company> objectList = companyDelegate.findAllWithPaging(user.getItemsPerPage(), page, order);
		setItemsPerPage(user.getItemsPerPage());
		setTotalItems();
		// System.out.println("2  ItemsPerPage,  TotalItems :  " + itemsPerPage + "  " + totalItems);
		// set the business types
		businessTypes = Arrays.asList(BusinessType.values());
		companiesList = objectList.getList();
		return Action.SUCCESS;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getPage() {
		return page;
	} 

	public void setPage(int page) {
		this.page = page;
	}
	
	public int getTotalItems() {
		return totalItems;
		//return totalItems;
	}


	public void setTotalItems() {
		totalItems = companyDelegate.findAll().size();
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public List<Company> getCompaniesList() {
		return companiesList;
	}
	
	public List<BusinessType> getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(List<BusinessType> businessTypes) {
		this.businessTypes = businessTypes;
	}

	/**
	 * @return the activeCompanies
	 */
	public List<Company> getActiveCompanies()
	{
		return activeCompanies;
	}

	/**
	 * @return the ongoingCompanies
	 */
	public List<Company> getOngoingCompanies()
	{
		return ongoingCompanies;
	}

	/**
	 * @param activeCompanies the activeCompanies to set
	 */
	public void setActiveCompanies(List<Company> activeCompanies)
	{
		this.activeCompanies = activeCompanies;
	}

	/**
	 * @param ongoingCompanies the ongoingCompanies to set
	 */
	public void setOngoingCompanies(List<Company> ongoingCompanies)
	{
		this.ongoingCompanies = ongoingCompanies;
	}
}
