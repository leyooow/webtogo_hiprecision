package com.ivant.cms.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ivant.cms.beans.NameBean;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.User;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class PromoCodeAction extends ActionSupport implements Preparable, ServletRequestAware, UserAware, CompanyAware
{
	private static final long serialVersionUID = 4048368734861819318L;
	private Logger logger = Logger.getLogger(getClass());
	private PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private PromoCode promoCode;
	private Company company;
	private User user;
	private HttpServletRequest request;
	private Long id;
	private List<PromoCode> promoCodes;
	private String toDate;
	private String fromDate;
	private String dueDate;
	private String errorType;
	private List<CategoryItem> categoryItems;
	
	public void prepare() throws Exception {
		try {
			id = Long.parseLong(request.getParameter("promoCodeId"));
			promoCode = promoCodeDelegate.find(id);
			if(company.getName().equals("hiprecisiononlinestore")) {
				setCategoryItems(categoryItemDelegate.findAll(company).getList());
			}
		}
		catch(Exception e) {
			promoCode = new PromoCode();
			promoCode.setCompany(user.getCompany());
		}
	}
	
	@Override
	public String execute() throws Exception {
		return Action.NONE;
	}
	
	private boolean commonParamsValid() {
		if(user.getCompany() == null) {
			return false;
		}		
	
		return true;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private void fillGurkkaPromoCodeDetails(PromoCode promoCode){
		
		promoCode.setPromoType(request.getParameter("type"));
		promoCode.setPromoFor(request.getParameter("promoCode.promoFor"));
		try{
			promoCode.setMinimumRequirement(Double.parseDouble(request.getParameter("promoCode.minimumRequirement")));
		}catch(Exception e){
			promoCode.setMinimumRequirement(0D);
		}
		
		promoCode.setMinimumRequirementUnit(request.getParameter("promoCode.minimumRequirementUnit"));
		
		promoCode.setMembershipLevel(request.getParameter("promoCode.membershipLevel"));
		
		try{
			promoCode.setMaxUsage(Integer.parseInt(request.getParameter("promoCode.maxUsage")));
		}catch(Exception e){
			promoCode.setMaxUsage(0);
		}
		
		String[] brands = request.getParameterValues("promoCode.brand");
		if(brands != null && brands.length > 0){
			promoCode.setBrand("," + StringUtils.join(brands, ",") + ",");
		}else{
			promoCode.setBrand("");
		}
		
		promoCode.setPromoSpecs(request.getParameter("promoCode.promoSpecs"));
		
	}
	
	public String savePromoCode()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		PromoCode existingPromoCode = promoCodeDelegate.findByCode(company, promoCode.getCode());
		
		try {
			if(existingPromoCode == null) {
				DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy");
				DateTime dt = formatter.parseDateTime(fromDate);
				promoCode.setFromDate(dt);		
				dt = formatter.parseDateTime(toDate);
				promoCode.setToDate(dt);
				
				if(CompanyConstants.GURKKATEST_COMPANY_NAME.equals(company.getName())){
					fillGurkkaPromoCodeDetails(promoCode);
					promoCode.setCurrentUsage(0);
				}
				
				promoCodeDelegate.insert(promoCode);
			}
			else
				errorType = "duplicate";
		} 
		catch(Exception e) {
			// cannot insert object
		}
		
		return Action.SUCCESS;
	}
	
	public String save()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
	 	
		PromoCode existingPromoCode = promoCodeDelegate.findByCode(company, promoCode.getCode());
		
		try {
			if(existingPromoCode == null || existingPromoCode.getId() == promoCode.getId()) {
				DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy");
				DateTime dt = formatter.parseDateTime(fromDate);
				promoCode.setFromDate(dt);		
				dt = formatter.parseDateTime(toDate);
				promoCode.setToDate(dt);
				if(CompanyConstants.GURKKATEST_COMPANY_NAME.equals(company.getName())){
					fillGurkkaPromoCodeDetails(promoCode);
				}
				promoCodeDelegate.update(promoCode);
			}
			else
				errorType = "duplicate";
		} 
		catch(Exception e) {
		}
		
		return Action.SUCCESS;
	}
	
	public String deletePromoCode()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!promoCode.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}
		
		if(company.getName().equals("hiprecisiononlinestore")) {
			promoCodeDelegate.delete(promoCode);
		} else {
			promoCode.setIsValid(Boolean.FALSE);
			promoCode.setIsDisabled(Boolean.TRUE);
			promoCodeDelegate.update(promoCode);
		}
		
		return Action.SUCCESS;
	}
	
	public String editPromoCode()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}

		if(!promoCode.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}

		promoCode = promoCodeDelegate.find(id);
		
		return Action.SUCCESS;
	}

	public PromoCode getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(PromoCode promoCode) {
		this.promoCode = promoCode;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PromoCode> getPromoCodes() {
		return promoCodes;
	}

	public void setPromoCodes(List<PromoCode> promoCodes) {
		this.promoCodes = promoCodes;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public List<CategoryItem> getCategoryItems() {
		return categoryItems;
	}

	public void setCategoryItems(List<CategoryItem> categoryItems) {
		this.categoryItems = categoryItems;
	}
	
	public List<NameBean> getGurkkaProducts(){return null;}
}
