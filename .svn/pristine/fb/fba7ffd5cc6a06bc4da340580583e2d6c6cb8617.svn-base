package com.ivant.cms.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.CompanySettingsDelegate;
import com.ivant.cms.delegate.ShippingTableDelegate;
import com.ivant.cms.delegate.WebtogoExcludedEmailsDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.ShippingTable;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.WebtogoExcludedEmails;
import com.ivant.cms.enums.BusinessType;
import com.ivant.cms.enums.CompanyStatusEnum;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.CompanyUtil;
import com.ivant.utils.PropertiesUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CompanyAction extends ActionSupport implements Preparable, ServletRequestAware, UserAware {

	private static final long serialVersionUID = -4843617652737904181L;
	private Logger logger = Logger.getLogger(getClass());
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private ShippingTableDelegate shippingTableDelegate = ShippingTableDelegate.getInstance();
	private CompanySettingsDelegate companySettingsDelegate = CompanySettingsDelegate.getInstance();
	private List<UserType> userTypes;	
	private User user;
	private HttpServletRequest request;
	
	private Company company;
	private Company company2;
	private CompanySettings companySettings;
	private boolean suspended;
	private List<Company> companies;
	
	
	private List<BusinessType> businessTypes;	
	private String businessType;
	private String expiryDate;
	private String onlineDate;	

	//webtogo only and super user
	private static final WebtogoExcludedEmailsDelegate webtogoExcludedEmailsDelegate = WebtogoExcludedEmailsDelegate.getInstance();
	private List<WebtogoExcludedEmails> webtogoExcludedEmailsList;


	public boolean isSuspended() {
		return suspended;
	}



	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}


	public List<BusinessType> getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(List<BusinessType> businessTypes) {
		this.businessTypes = businessTypes;
	}

	public String getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Company getCompany2() {
		return company2;
	}

	public void setCompany2(Company company2) {
		this.company2 = company2;
	}


	private String selectedCompany;
		
	public void prepare() throws Exception {
		try {
			long companyId = Long.parseLong(request.getParameter("company_id"));
			company2 = companyDelegate.find(companyId);
			companySettings = company2.getCompanySettings();
			suspended = companySettings.getSuspended();
		}
		catch(Exception e) {
			if (request.getParameter("company_id") == null)
			company2 = new Company();
		}		
		
		// set the business types
		businessTypes = Arrays.asList(BusinessType.values());
		String[] orderBy ={"nameEditable"};
		companies = companyDelegate.findAll(orderBy).getList();
		webtogoExcludedEmailsList = webtogoExcludedEmailsDelegate.findAll();
	} 

	@Override
	public String execute() throws Exception {
		// set the business types
		businessTypes = Arrays.asList(BusinessType.values());
		return Action.NONE;
	}

	public String saveCompany() {
		if(user.getUserType() != UserType.SUPER_USER ) {
			return Action.ERROR;
		}		
		
//		System.out.println("Name Editable: "+company2.getNameEditable());
		CompanySettings companySettings = company2.getCompanySettings();
		company2 = companyDelegate.find(companyDelegate.insert(company2));
		companySettings.setCompany(company2);
		companySettings.setDoNotGenerateBilling(false);
		companySettingsDelegate.insert(companySettings);
		//System.out.println("company settings id: " +company2.getCompanySettings().getId());
		return Action.SUCCESS;
	}
	public String addDomainName(){
		try{
			if(request.getParameter("add_domain_id") != ""){
				WebtogoExcludedEmails dom = webtogoExcludedEmailsDelegate.find(Long.parseLong(request.getParameter("add_domain_id")));
				String dom_name = request.getParameter("add_domainname");
				dom.setEmail(dom_name);
				webtogoExcludedEmailsDelegate.update(dom);
			}else{
				WebtogoExcludedEmails dom = new WebtogoExcludedEmails();
				String dom_name = request.getParameter("add_domainname");
				String type = request.getParameter("add_domaintype");
				dom.setEmail(dom_name);
				dom.setCompany(company2);
				dom.setType(type);
				webtogoExcludedEmailsDelegate.insert(dom);
			}
		}catch(Exception e){
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String deleteDomainName(){
		try{
			WebtogoExcludedEmails dom = webtogoExcludedEmailsDelegate.find(Long.parseLong(request.getParameter("delete_domain_id1")));
			webtogoExcludedEmailsDelegate.delete(dom);
			
		}catch(Exception e){
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String save() {
		setUserTypes(Arrays.asList(UserType.values()));	
		/*List<ShippingTable> tmp = company2.getShippingTable();
		String s = request.getParameter("myInputs");
		String[] all=s.split(",");
		int tableSize= all.length;
		ShippingTable a =tmp.get(0);
		a.setUsername(s);
		tmp.set(0, a);
		company2.setShippingTable(tmp);
		
		for(int c=0;c<tableSize;c++){
			ShippingTable x= new ShippingTable();
			x.setCompany(company2);
			shippingTableDelegate.insert(x);
		}*/
		
		//HPDS LOCAL/OFFLINE NOTIF DURATION: REMOTE SAVE
		//////////////////////////////////////////////////////////////////////////////////
		
		final String propertiesFileName = "hpds-remote.properties";
		final Properties properties = PropertiesUtil.getProperties(propertiesFileName);
		final String serviceURL = properties.getProperty("notif.url");
		final PostMethod postMethod = new PostMethod(serviceURL);	
		
		//--------------------------------------------------------------------
		
		String notifDuration = request.getParameter("notifDuration");
		if(notifDuration == null){
			company2.setNotifDuration(0);
		}
		else if(notifDuration.length() == 0){
			company2.setNotifDuration(0);
		}
		else{
			company2.setNotifDuration(Integer.parseInt(notifDuration) );
		}
		
		//---------------------------------------------------------------------
		
		postMethod.addParameter("notifDuration", Integer.toString(company2.getNotifDuration()));
		
		logger.info("Remote saving to: " + serviceURL);

			try {
			new HttpClient().executeMethod(postMethod);
			} catch (org.apache.commons.httpclient.HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//System.out.println("company settings id: " +company2.getCompanySettings().getId());
		//logger.info("Remote Saving of Notif Duration: success");

		
		///////////////////////////////////////////////////////////////////////////////////
		//REMOTE SAVE END
		
		for(ShippingTable temporary : shippingTableDelegate.findAll()){
			if(temporary.getCompany()!=null)
			if(temporary.getCompany().getId()==company2.getId()){
				shippingTableDelegate.delete(temporary);
			}
		}
		
		companySettings.setCompanyStatus(CompanyStatusEnum.getIndex(Integer.parseInt(request.getParameter("companyStatus"))));

		List<ShippingTable> tmp = new ArrayList<ShippingTable>();
		int counter=0;
		double temp2=0;
		//while(request.getParameter("fromPrice_"+counter)!=null && request.getParameter("toPrice_"+counter)!=null && request.getParameter("shippingPrice_"+counter)!=null){		
		while(counter!=100){
			if((request.getParameter("toPrice_"+counter)!=null && request.getParameter("shippingPrice_"+counter)!=null) && (request.getParameter("toPrice_"+counter)!="" && request.getParameter("shippingPrice_"+counter)!="")){
				ShippingTable x= new ShippingTable();
				x.setCompany(company2);
				if(counter==0){
					x.setFromPrice(Double.parseDouble(""+0));
					temp2=Double.parseDouble(request.getParameter("toPrice_"+counter));
				}
				else{	
					x.setFromPrice(temp2+0.01d);
					temp2=Double.parseDouble(request.getParameter("toPrice_"+counter));		
				}
				x.setToPrice(Double.parseDouble(request.getParameter("toPrice_"+counter)));
				x.setShippingPrice(Double.parseDouble(request.getParameter("shippingPrice_"+counter)));
				tmp.add(x);
				shippingTableDelegate.insert(x);
			}
			counter++;	
		}
		
		String temp1 = request.getParameter("canBatchUpdateExcelFiles");
		boolean temp21 = (temp1 == null) ? false : true;
		companySettings.setCanBatchUpdateExcelFiles(temp21);
		companySettings.setShippingType(request.getParameter("shippingType"));
		if(company2.getName().equalsIgnoreCase("agian")){
			company2.getCompanySettings().setAccountExpiryDate(request.getParameter("company2.account_expiry_date"));
		}
		
		//shippingTableDelegate.batchUpdate(tmp);
		//company2.setShippingTable(tmp);
		//companyDelegate.update(company2);
		
		if(user.getUserType() != UserType.SUPER_USER  &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR && user.getUserType() != UserType.COMPANY_ADMINISTRATOR && user.getUserType() != UserType.COMPANY_STAFF) {
			return Action.ERROR;
		}		
		
//				if (request.getParameter("company_id")  != null) {
//					Company  cc = companyDelegate.find(new Long (request.getParameter("company_id")));
//					if (cc != null) 
//					{
//						user2.setCompany(cc);
//					}
//				}	
		businessType = request.getParameter("businessType");
		
		
		for (int i = 0; i< businessTypes.size(); i++){	
			if (businessTypes.get(i).name().toString().equals(businessType)){
				company2.setBusinessType(businessTypes.get(i));
				break;	
			}
			
		}
	

		if(user.getUserType() == UserType.SUPER_USER  ||  user.getUserType() == UserType.WEBTOGO_ADMINISTRATOR) {
			if (expiryDate!=null && expiryDate.length()!=0) {			
				GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(expiryDate.substring(6,10)), Integer.parseInt(expiryDate.substring(0,2 ))-1 , Integer.parseInt(expiryDate.substring(3,5)) );
				company2.setExpiryDate(calendar.getTime());
			}
			if (onlineDate!=null && onlineDate.length()!=0) {			
				GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(onlineDate.substring(6,10)), Integer.parseInt(onlineDate.substring(0,2 ))-1 , Integer.parseInt(onlineDate.substring(3,5)) );
				companySettings.setOnlineDate(calendar.getTime());
			}
		}
		
		
		if (StringUtils.isEmpty(request.getParameter("company_id")))
			{
				companyDelegate.insert(company2);
				company2.getCompanySettings().setCompany(company2);
				companySettingsDelegate.insert(company2.getCompanySettings());				
			}
		else
			{
			//System.out.println("check suspend: " + (request.getParameter("suspended")!=null ? Boolean.TRUE : Boolean.FALSE));
//			if (companySettings != null)
//				companySettings.setSuspended(request.getParameter("suspended")!=null ? Boolean.TRUE : Boolean.FALSE);
			if(companySettings.getCompanyStatus() == CompanyStatusEnum.SUSPENDED)
				companySettings.setSuspended(Boolean.TRUE);
			else
				companySettings.setSuspended(Boolean.FALSE);
			company2.setCompanySettings(companySettings);	
			companyDelegate.update(company2);
		}
		return Action.SUCCESS;
	}
	
/*	placed in pageDispatcher: due to conflict
 * //for HPDS ONLINE NOTIF DURATION: REMOTE SAVE
	@SuppressWarnings("rawtypes")
	public void remoteSaveNotif(){
		Company companyNotif = companyDelegate.find(Long.valueOf(CompanyConstants.HIPRECISION_DATA_SEARCH));
		logger.info("Entering remoteSaveNotif...");
		String notifDuration = request.getParameter("notifDuration");
		companyNotif.setNotifDuration(Integer.parseInt(notifDuration));
		
		companyDelegate.update(companyNotif);
	}*/
	
	
	public String delete() {
		if(user.getUserType() != UserType.SUPER_USER   &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return Action.ERROR;
		}
		if(company2.getId() > 0) {
			companyDelegate.delete(company2);	
		}
		
		return Action.SUCCESS;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	public String getSelectedCompany()
	{
		return selectedCompany;
		
	}
	
	
	
	private boolean commonParamsValid() {
		if(user.getUserType() != UserType.SUPER_USER  &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return false;
		} 
//		if(user.getCompany() == null) {
//			return false;
//		}		
		if(company2 == null) {
			return false;
		}
		
		return true;
	}
	
	
	public String edit() {
	//	if(!commonParamsValid()) {
	//		return Action.ERROR;
	//	}

		long companyId = Long.parseLong(request.getParameter("company_id"));
		company2 = companyDelegate.find(companyId);
		companySettings = company2.getCompanySettings();
		if (companySettings!=null)
			suspended = companySettings.getSuspended();
		
		businessTypes = Arrays.asList(BusinessType.values());
		logger.info("businesstype size: "  + businessTypes.size());
		return Action.SUCCESS;
	}
	
	
	public String getStatistics(){
		Company company2=null;
		
		Long id = Long.parseLong(request.getParameter("id"));
		company2 = companyDelegate.find(id);
		if (company2 == null)
				return Action.ERROR;
		else {
				selectedCompany = company2.getDomainName();
				
				return Action.NONE;
		}
	}
	
	public String copyCompany()
	{
		try
		{
			final String sourceCompany = request.getParameter("sourceCompany");
			final String destinationCompany = request.getParameter("destinationCompany");
			
			if(StringUtils.isNotEmpty(sourceCompany) && StringUtils.isNotEmpty(destinationCompany))
			{
				final Long sourceId = Long.valueOf(sourceCompany);
				final Long destinationId = Long.valueOf(destinationCompany);
				
				final Company fromCompany = companyDelegate.find(sourceId);
				final Company toCompany = companyDelegate.find(destinationId);
				
				if(fromCompany != null && toCompany != null)
				{
					CompanyUtil.copyCompanyPagesAndGroups(fromCompany, toCompany);
					return SUCCESS;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ERROR;
	}



	public void setUserTypes(List<UserType> userTypes) {
		this.userTypes = userTypes;
	}



	public List<UserType> getUserTypes() {
		return userTypes;
	}
	
	
	public List<WebtogoExcludedEmails> getWebtogoExcludedEmailsList() {
		return webtogoExcludedEmailsList;
	}

	public void setWebtogoExcludedEmailsList(List<WebtogoExcludedEmails> webtogoExcludedEmailsList) {
		this.webtogoExcludedEmailsList = webtogoExcludedEmailsList;
	}
	
	
	
}
