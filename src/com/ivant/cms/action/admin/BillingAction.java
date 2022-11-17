package com.ivant.cms.action.admin;


import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.dataSource.BillingDataSource;
import com.ivant.cms.delegate.BillingDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.BillingStatusEnum;
import com.ivant.cms.enums.BillingTypeEnum;
import com.ivant.cms.enums.EntityLogEnum;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.helper.DateHelper;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.ApplicationConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class BillingAction  extends ActionSupport implements Preparable, ServletRequestAware, UserAware    //, CompanyAware
{
	private static final long serialVersionUID = 4048368734861819318L;
	private Logger logger = Logger.getLogger(getClass());
	private BillingDelegate billingDelegate = BillingDelegate.getInstance();
	private BillingDataSource billingDS = new BillingDataSource();
	private Billing billing;
	private Company company;
	private User    user;
	private int type;
	private HttpServletRequest request;
	private Long id;
	private List<BillingStatusEnum> billingStatuses;
	private List<BillingTypeEnum> billingTypes;
	private List<Billing> billings;
	private String toDate;
	private String fromDate;
	private String dueDate;
	private String docName;
	private String disposition;
	private LogDelegate logDelegate = LogDelegate.getInstance();
	
	public String getFromDate() {
		return fromDate;
	}

	public List<Billing> getBillings() {
		return billings;
	}

	public BillingDataSource getBillingDS() {
		return billingDS;
	}

	public void setBillingDS(BillingDataSource billingDS) {
		this.billingDS = billingDS;
	}

	public void setBillings(List<Billing> billings) {
		this.billings = billings;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		return Action.NONE;
	}
	
	public void setBilling(Billing billing) {
		this.billing = billing;
	}
	
	public Billing getBilling() {
		return this.billing;
	}
	

	public void prepare() throws Exception {
		try {
			long Id = Long.parseLong(request.getParameter("billing_id"));
			billing = billingDelegate.find(Id);
		}
		catch(Exception e) {
			billing = new Billing();
			billing.setCompany(user.getCompany());
		}
		
		if (request.getParameter("type") != null ) {
					type = Integer.parseInt(request.getParameter("type"));
					for (BillingTypeEnum be: BillingTypeEnum.values())
					{
						if (type == be.getTypeCode())
							billing.setType(be);
					}
		}
	}
	
	public void setBillingTypes(List<BillingTypeEnum> billingTypes) {
		this.billingTypes = billingTypes;
	}

	private boolean commonParamsValid() {
		if(user.getUserType() != UserType.SUPER_USER && user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return false;
		} 
		if(user.getCompany() == null) {
			return false;
		}		
	
		return true;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	public String saveNew()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
	 	
		try {
			GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(fromDate.substring(6,10)), Integer.parseInt(fromDate.substring(0,2 ))-1 , Integer.parseInt(fromDate.substring(3,5)) );
			billing.setFromDate(calendar.getTime());		
			//logger.debug("DATE=================" + billing.getFromDate());
			GregorianCalendar calendar2 = new GregorianCalendar(Integer.parseInt(toDate.substring(6,10)), Integer.parseInt(toDate.substring(0,2 ))-1 , Integer.parseInt(toDate.substring(3,5)) );
			billing.setToDate(calendar2.getTime());
			//logger.debug("DATE=================" + billing.getToDate());
			GregorianCalendar calendar3 = new GregorianCalendar(Integer.parseInt(dueDate.substring(6,10)), Integer.parseInt(dueDate.substring(0,2 ))-1 , Integer.parseInt(dueDate.substring(3,5)) );
			billing.setDueDate(calendar3.getTime());
			//logger.debug("DATE=================" + billing.getDueDate());
			billing.setStatus(BillingStatusEnum.PENDING);
			billingDelegate.insert(billing);
		} 
		catch(Exception e) {
			// cannot insert object
		}
		return Action.SUCCESS;
	}
	
	
	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String save()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
	 	
		try {
			GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(fromDate.substring(6,10)), Integer.parseInt(fromDate.substring(0,2 ))-1 , Integer.parseInt(fromDate.substring(3,5)) );
			billing.setFromDate(calendar.getTime());		
			logger.debug("DATE=======================================" + billing.getFromDate());
			GregorianCalendar calendar2 = new GregorianCalendar(Integer.parseInt(toDate.substring(6,10)), Integer.parseInt(toDate.substring(0,2 ))-1 , Integer.parseInt(toDate.substring(3,5)) );
			billing.setToDate(calendar2.getTime());
			GregorianCalendar calendar3 = new GregorianCalendar(Integer.parseInt(dueDate.substring(6,10)), Integer.parseInt(dueDate.substring(0,2 ))-1 , Integer.parseInt(dueDate.substring(3,5)) );
			billing.setDueDate(calendar3.getTime());			
			billingDelegate.update(billing);
			
			//System.out.println("company id: "+billing.getCompany().getId());
			Log logFile = new Log();
			logFile.setEntityID(billing.getId());
			logFile.setEntityType(EntityLogEnum.BILLING);
			logFile.setUpdatedByUser(user);
			logFile.setCompany(billing.getCompany());
			logFile.setRemarks(request.getParameter("remarks").toString());
			//System.out.println("-------2---------");
			logDelegate.insert(logFile);
			//System.out.println("Success");
		} 
		catch(Exception e) {
			// cannot udpate object
			//System.out.println("ERROR");			
		}
		
		return Action.SUCCESS;
	}
	

	public String delete()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!billing.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}
		if (billing.getPayment() != null) {
		billing.setStatus(BillingStatusEnum.CANCELLED);
		billingDelegate.update(billing);
		request.setAttribute("message", "Cannot cancel a billing record with existing payment.");
		}
		
		return Action.SUCCESS;
		
	}
	
	public String editbilling()
	{
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!billing.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}

		billingStatuses = Arrays.asList(BillingStatusEnum.values());
		billingTypes = Arrays.asList(BillingTypeEnum.values());
		
		billing = billingDelegate.find(id);

		//request.setAttribute("billingTypes", billingTypes);
		
		return Action.SUCCESS;
	}
	
	
	public String generateReport(){
		float x=ApplicationConstants.VAT;
		long Id = Long.parseLong(request.getParameter("billing_id"));
		billing = billingDelegate.find(Id);
		String desc = billing.getType().getValue();
//		if (billing.getNote() != null){
//			desc = desc + "(" + billing.getNote() + ")";
//		}
		
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		numberFormatter.setMaximumFractionDigits(2);
		
		   Calendar cal = Calendar.getInstance();
		   cal.setTime (new Date());
		   cal.add (Calendar.DATE, 7);
		billingDS.setBilling(billing);
		billingDS.setBillingDate(new Date());
		billingDS.setDescription(desc);
		billingDS.setVat(""+(x*100)+"%");
		billingDS.setDueDate(cal.getTime());
		billingDS.setVatValue(numberFormatter.format(Double.parseDouble(""+(billing.getAmountDue()-(billing.getAmountDue()/(1+x))))));
		billingDS.setRealValue(numberFormatter.format(Double.parseDouble(""+(billing.getAmountDue()/(1+x)))));
		String note = billingDS.getBilling().getNote();
		if (note != "" && note != null){
			note = "Note: " + note;
			billingDS.getBilling().setNote(note);
		}
		
		DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
		Date date2 = new Date();
		String billingDate2 = dateFormat.format(date2);
		
		
		docName = "billingStatement_" + billing.getCompany().getName() +"_"+ billingDate2;
		disposition = "attachment";
		//billingDS = new BillingDataSource(billing,billingDate,company,desc,company.getPhone());
		
/*
		setBillings(Arrays.asList(billing));
	
		JasperReport jr;
		try {
			jr = JasperCompileManager.compileReport(JRLoader.getResourceInputStream("billing_statement_sample.jrxml"));

			
			/* Create the datasource by using the items created. 
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(billings);
			
			/* The parameters. 
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			/* Fill the report 
			JasperPrint print = JasperFillManager.fillReport(jr, parameters, datasource);
			
			/* Export the report to pdf. 
			JasperExportManager.exportReportToPdfFile(print, "out-bean-ds.pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
/*
	try {
			JasperCompileManager.compileReportToFile(
            		getClass().getResource("/report/billing_statement_sample.jrxml"),
                    "/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/webtogo/WEB-INF/jasper/billing_statement_sample.jasper");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        } */
		return Action.SUCCESS;		
	}	
	
	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	//Company company, int resultPerPage, int pageNumber, BillingStatusEnum status, BillingTypeEnum type, Date dueDate, Date fromDate, Date toDate
	public String findByCriteria()
	{
	int resultPerPage =0;
	int pageNumber =0;
	BillingStatusEnum status =null;
	BillingTypeEnum type =null;
	Date dueDate = null;
	Date fromDate = null;
	Date toDate = null;
	
	Company company;
	
	
	
	company = user.getCompany();
	
//	if (request.getParameter("resultPerPage") != null)
//			resultPerPage= Integer.parseInt(request.getParameter("resultPerPage"));
	
	resultPerPage = user.getItemsPerPage();
	
	if (request.getParameter("pageNumber") != null)
		pageNumber= Integer.parseInt(request.getParameter("pageNumber"));
	
	if (request.getParameter("status") != null)
		status = BillingStatusEnum.valueOf(request.getParameter("status"));
	
	if (request.getParameter("type") != null)
		type = BillingTypeEnum.valueOf(request.getParameter("type"));
	
	if (request.getParameter("dueDate") != null)
		dueDate= DateHelper.parseDate(request.getParameter("dueDate"));
	
	if (request.getParameter("fromDate") != null)
		fromDate = DateHelper.parseDate(request.getParameter("fromDate"));	
	
	if (request.getParameter("toDate") != null)
		toDate = DateHelper.parseDate(request.getParameter("toDate"));
	

	request.setAttribute("billings",billingDelegate.findAllWithPaging(company, resultPerPage, pageNumber, status, type, dueDate, fromDate, toDate, null));
		
	return Action.NONE;
	}
	

	public List<BillingStatusEnum> getBillingStatuses() {
		return billingStatuses;
	}
	
	public List<BillingTypeEnum> getBillingTypes() {
		return billingTypes;
	}

//	public void setCompany(Company company)
//	{
//		this.company=billing.getCompany();
//		
//	}
	
}
