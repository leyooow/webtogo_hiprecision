package com.ivant.cms.action.admin;

import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.ActivityDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Activity;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.interceptors.FrontCompanyInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

import java.util.regex.*;

public class EmailValidateAction implements Action, SessionAware,
					ServletContextAware, ServletRequestAware, CompanyAware {

	private static final Logger logger = Logger.getLogger(EmailValidateAction.class);
	private static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	
	private Map session;
	private HttpServletRequest request;

	private List<Company> companies;
	private List<SavedEmail> emails;
	private long companyId;
	private Company company;
	
	private ServletContext servletContext;

	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		String[] order = { "nameEditable" };
		SavedEmail email;
		Matcher validator;
		int count;
		Pattern pattern=Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+.[a-zA-Z]");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date;
		
		try {
			companyId = Long.parseLong(request.getParameter("company_id"));
			company = companyDelegate.find(companyId);
		}
		catch(Exception e) {
			logger.debug("cannot find company");
		}
				
		companies = companyDelegate.findAll(order).getList();
		emails = savedEmailDelegate.findAll(company).getList();
		count = emails.size();
		
		for(int i=0; i<count; i++){
			date = new Date();
			validator = pattern.matcher(emails.get(i).getEmail());
			//syntax checking
			//if(validator.matches()==true){
				//check email if it is existing
				//emails.get(i).setEmailValid(EmailUtil.isAddressValid(emails.get(i).getEmail()));
			//}
			//else{
				emails.get(i).setEmailValid(validator.matches());
			//}
			emails.get(i).setEmailDateValidated(dateFormat.parse(dateFormat.format(date)));
		}
		
		savedEmailDelegate.batchUpdate(emails);
		
		return Action.SUCCESS; 
	}
	
	public String validate(){
		return Action.SUCCESS;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public void setSession(Map session) {
		this.session = session;
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

	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	
}
