package com.ivant.cms.action.admin;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.dataSource.PDFDataSource;
import com.ivant.cms.dataSource.PDFItem;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PDFGenerator;
import com.ivant.utils.PDFCreatorTool;
import com.opensymphony.xwork2.ActionSupport;

public class FormSubmissionAction extends ActionSupport implements CompanyAware, ServletRequestAware, ServletContextAware, PDFGenerator{

	private static final long serialVersionUID = -6584704073037144700L;
	private static final Logger logger = Logger.getLogger(ListFormSubmissionAction.class);
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	
	private HttpServletRequest request;
	private Company company;
	private long submissionId;
	private String selectedValues;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	
	private PDFDataSource datasource;
	private List<PDFItem> pdfItems = new ArrayList<PDFItem>();
	String templateName;
	private PDFCreatorTool PDFCreator = new PDFCreatorTool();
	
	private ServletContext servletContext;
	
	public String approve() {
		SavedEmail savedEmail = savedEmailDelegate.find(submissionId);
		if(savedEmail.getCompany().equals(company)) {
			savedEmail.setEmailValid(true);
			savedEmailDelegate.update(savedEmail);
		}
		return SUCCESS; 
	} 	
	
	public String delete() {
		SavedEmail savedEmail = savedEmailDelegate.find(submissionId);
		if(savedEmail.getCompany().equals(company)) {
			savedEmailDelegate.delete(savedEmail);
		}
		return SUCCESS; 
	} 
	
	public String deleteMultiple() {
		try{
			selectedValues = request.getParameter("selectedValues");
			
			//System.out.println(selectedValues); //testing
			StringTokenizer st = new StringTokenizer(selectedValues,"_");
						
			while (st.hasMoreTokens()) {
				SavedEmail savedEmail = savedEmailDelegate.find(Long.parseLong(st.nextToken()));
				
				if(savedEmail.getCompany().equals(company)) {
					savedEmailDelegate.delete(savedEmail);
				}		
			}
			
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action.", e);
			return ERROR;
		}
		return SUCCESS; 
	} 
	
	public String downloadEmailPDF() throws Exception {
		
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
		String logo = PDFCreator.getLogo();

		
		if(company.getLogo()==null)
			logo="";
		try{
		}catch(Exception e){
		}
		
		//get passed template id
		templateName="";
		try{
			templateName = request.getParameter("template_name");
		}catch(Exception e){
		}
		if(templateName==null || templateName.equals("")){
			templateName="default_submission.jrxml";
		}
		
		SavedEmail savedEmail = savedEmailDelegate.find(submissionId);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("company_name", company.getNameEditable());
		parameters.put("image", servletContext.getRealPath("companies/metroquicash/img/logo-metroquicash.png"));
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
		Date date = new Date();
		String billingDate = dateFormat.format(date);
		parameters.put("date", billingDate);
		parameters.put("submitted_form", savedEmail.getFormName());
		String emailContent = savedEmail.getEmailContent().replaceAll("<br/>"," \n");
		parameters.put("email_content", emailContent);
		
		String fileName = ""+company.getName()+"_Submission_ID_"+savedEmail.getId()+"_"+sdf.format(date.getTime())+".pdf";
		PDFItem pdfItem = new PDFItem("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
		pdfItems.add(pdfItem);
		setDatasource(new PDFDataSource(pdfItems));
		
		PDFCreator.outputPDFWithParameters(datasource, parameters, templateName, fileName);
		
		return SUCCESS;
	}
	
	public void setSubmissionId(long submissionId) {
		this.submissionId = submissionId;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	public PDFDataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(PDFDataSource datasource) {
		this.datasource = datasource;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void generatePriceListReport() {
		// TODO Auto-generated method stub
		
	}
}
