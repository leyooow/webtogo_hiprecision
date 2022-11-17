package com.ivant.cms.action.admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.OSSShippingAreaDelegate;
import com.ivant.cms.delegate.OSSShippingLocationDelegate;
import com.ivant.cms.delegate.OSSShippingRateDelegate;
import com.ivant.cms.entity.OSSShippingArea;
import com.ivant.cms.entity.OSSShippingLocation;
import com.ivant.cms.entity.OSSShippingRate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Backend action for creating, viewing and editing orders from companies.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class OSSShippingLocationAction extends ActionSupport 
	implements Preparable, ServletRequestAware, CompanyAware, UserAware{
	
	private static Logger logger = LoggerFactory.getLogger(OSSShippingLocationAction.class);
	private static final long serialVersionUID = 3617798953897716137L;
	private static final OSSShippingAreaDelegate areaDelegate = OSSShippingAreaDelegate.getInstance();
	private static final OSSShippingLocationDelegate locationDelegate = OSSShippingLocationDelegate.getInstance();
	private static final OSSShippingRateDelegate rateDelegate = OSSShippingRateDelegate.getInstance();
	private final static int ITEMS_PER_PAGE = 20;
	
	/** Current webtogo admin user, must not be null*/
	@SuppressWarnings("unused")
	private User user;

	/** Data that is passed through the session */
	private HttpServletRequest request;
	
	/** Current session webtogo company, must not be null*/
	private Company company;
	
	/** Url to redirect if action is executed successfully */
	private String successUrl;
	
	/** Current page number for paging, can be null*/
	private Integer pageNumber;
	
	private OSSShippingLocation location;
	private OSSShippingRate rate;
	private List<OSSShippingArea> areas;
	private List<OSSShippingLocation> locations;
	private List<OSSShippingRate> rates;
	private Long locationID;
	
	@Override
	public void prepare() throws Exception {
		//initialize paging
		setPageNumber();
		
		areas = areaDelegate.findAll(company);
		locations = locationDelegate.findAll(company);
		
		try {
			locationID = Long.parseLong(request.getParameter("location_id"));
			location = locationDelegate.find(locationID);
		}
		catch(Exception e) {
			location = new OSSShippingLocation();
			location.setCompany(user.getCompany());
		}
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String save()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}		
	
		Long areaID = Long.parseLong(request.getParameter("areaID"));
		OSSShippingArea area = areaDelegate.find(areaID);
		String locationName = request.getParameter("locationname");
		String description = request.getParameter("description");
		String formRates[] = request.getParameterValues("rate");
		String formWeights[] = request.getParameterValues("weight");
		
		if(locationID != null)
		{
			location = locationDelegate.find(locationID);
			rates = rateDelegate.findAll(company, location, Order.asc("weight"));
			
			if(location != null)
			{
				location.setOssShippingArea(area);
				location.setLocationName(locationName);
				location.setDescription(description);
				locationDelegate.update(location);
				
				for(int i=0; i<rates.size(); i++)
				{
					rate = rateDelegate.find(rates.get(i).getId());
					rate.setRate(Double.parseDouble(formRates[i].toString()));
					rate.setWeight(Double.parseDouble(formWeights[i].toString()));
					rateDelegate.update(rate);
				}
			}
		}
		else
		{
			location.setOssShippingArea(area);
			location.setLocationName(locationName);
			location.setDescription(description);
			location = locationDelegate.find(locationDelegate.insert(location));
			
			for(int i=0; i<formRates.length; i++)
			{
				rate = new OSSShippingRate();
				rate.setCompany(user.getCompany());
				rate.setOssShippingLocation(location);
				rate.setRate(Double.parseDouble(formRates[i].toString()));
				rate.setWeight(Double.parseDouble(formWeights[i].toString()));
				rateDelegate.insert(rate);
			}
		}
		
		return SUCCESS;
	}
	
	public String edit() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!location.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}
		
		rates = rateDelegate.findAll(company, location, Order.asc("weight"));
		
		return Action.SUCCESS;
	}
	
	public String delete()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}		

		if(!location.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}
		
		rates = rateDelegate.findAll(company, location, Order.asc("weight"));
		
		for(int i=0; i<rates.size(); i++)
		{
			rate = rateDelegate.find(rates.get(i).getId());
			rateDelegate.delete(rate);
		}
		
		locationDelegate.delete(location);
		
		return SUCCESS;
	}
	
	private boolean commonParamsValid() {
		if(user.getUserType() != UserType.SUPER_USER && user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR && user.getUserType() != UserType.COMPANY_ADMINISTRATOR) {
			return false;
		} 

		return true;
	}
	
	/**
	 * Parses specified request specified page number to local variable {@code pageNumber}.
	 * Returns the parsed specified page number if successful, otherwise 1.
	 * 
	 * @return - the parsed specified page number if successful, otherwise 1.
	 */
	public int setPageNumber() {
		//get the specified page number
		String page = request.getParameter("page");
 		try{
 			//parse specified page
			pageNumber = Integer.parseInt(page);
		} catch(Exception e){
			//log unparsed value and return to 1st page
			logger.info("Cannot parse {} as page number" , page);
			pageNumber = 1;
			return pageNumber;
		}
		return pageNumber;
	}
	
	/**
	 * Loads the items based on the specified items per page.
	 * 
	 * @param itemSize 
	 * 			- total number of items
	 * @param itemsPerPage 
	 * 			- number of items shown per page
	 */
	public void setPaging(int itemSize, int itemsPerPage) {
		request.setAttribute("pagingUtil", new PagingUtil(itemSize, itemsPerPage, pageNumber, (itemSize/itemsPerPage)));
	}
	
	/**
	 * Returns the successUrl property value.
	 *
	 * @return the successUrl
	 */
	public String getSuccessUrl() {
		return successUrl;
	}

	/**
	 * Sets the successUrl property value with the specified successUrl.
	 *
	 * @param successUrl the successUrl to set
	 */
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	/**
	 * Returns the request property value.
	 *
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Sets the request property value with the specified request.
	 *
	 * @param request the request to set
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Sets the user property value with the specified user.
	 *
	 * @param user the user to set
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Sets the company property value with the specified company.
	 *
	 * @param company the company to set
	 */
	@Override
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public OSSShippingLocation getLocation()
	{
		return location;
	}
	
	public void setLocation(OSSShippingLocation location)
	{
		this.location = location;
	}
	
	public List<OSSShippingArea> getAreas()
	{
		return areas;
	}
	
	public void setAreas(List<OSSShippingArea> areas)
	{
		this.areas = areas;
	}
	
	public List<OSSShippingLocation> getLocations()
	{
		return locations;
	}
	
	public void setLocations(List<OSSShippingLocation> locations)
	{
		this.locations = locations;
	}
	
	public List<OSSShippingRate> getRates()
	{
		return rates;
	}
	
	public void setRates(List<OSSShippingRate> rates)
	{
		this.rates = rates;
	}	
}
