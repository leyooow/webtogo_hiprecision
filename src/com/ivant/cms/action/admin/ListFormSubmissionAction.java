package com.ivant.cms.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import com.ivant.cms.delegate.BillingDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.CompanyStatusEnum;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.JXLUtil;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ListFormSubmissionAction extends ActionSupport 
				implements UserAware, CompanyAware, PagingAware, ServletContextAware, ServletRequestAware {
	
	private static final long serialVersionUID = 4050469397961099239L;
	private static final Logger logger = Logger.getLogger(ListFormSubmissionAction.class);
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	private static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private static final BillingDelegate billingDelegate = BillingDelegate.getInstance();
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	
	private Company company;
	private User user;
	private Boolean hasRegistrants;

	private int page;
	private int totalItems;
	private int itemsPerPage;
	private int filterDays;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	
	private String filePath;
	private String fileName;
	private FileInputStream fInStream;
	private FileInputStream inputStream;
	private long contentLength;
	
	private ServletContext servletContext;
	private HttpServletRequest request;	
	
	private String startDate;
	private String endDate;
	
	CompanyStatusEnum[] status = {CompanyStatusEnum.ONGOING, CompanyStatusEnum.ACTIVE, CompanyStatusEnum.ACTIVE_NO_HOSTING};
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	
	
	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public FileInputStream getFInStream() {
		return fInStream;
	}


	public void setFInStream(FileInputStream inStream) {
		fInStream = inStream;
	}


	public FileInputStream getInputStream() {
		return inputStream;
	}



	public void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
	}



	public long getContentLength() {
		return contentLength;
	}


	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	private List<SavedEmail> emails;
	private List<SavedEmail> emailsList;
	private List<SavedEmail> reservationList;
	private List<SavedEmail> registrationList;
	
	@Override
	public String execute() throws Exception {
		hasRegistrants=false;
		List<Member> members = memberDelegate.findPurpose(company,"Registration");
		if(members!=null){
			hasRegistrants=true;
		}
		
		emailsList = savedEmailDelegate.findAllWithPaging(company, page, itemsPerPage, Order.desc("createdOn")).getList();
		
		if(company.getName().equalsIgnoreCase("life")) {
			setRegistrations();
		}
		
		if(company.getName().equalsIgnoreCase("noelle")) {
			setReservations();
		}
		/*
		if(filterDays > 0) {
			emails = savedEmailDelegate.findLatestEmail(company, 3).getList();
		}
		else {
			emails = savedEmailDelegate.findAllWithPaging(company, page, itemsPerPage, Order.desc("createdOn")).getList();
		}*/
		int year = new DateTime().getYear();
		request.setAttribute("year", year);
		return Action.SUCCESS;
	}
	
	public void setRegistrations() {
		List<SavedEmail> list = new ArrayList<SavedEmail>();
		for(SavedEmail se : emailsList) {
			String formName = se.getFormName().toLowerCase();
			if(formName.equalsIgnoreCase("events")) {
				list.add(se);
			}
		}
		
		itemsPerPage = user.getItemsPerPage();
		String eventName = request.getParameter("eventName");
		registrationList = savedEmailDelegate.findEmailByFormName(company, "Events", Order.desc("createdOn")).getList();
		List<SavedEmail> newList = new ArrayList<SavedEmail>();
		if(eventName != null && !eventName.equals("")) {
			eventName = eventName.toLowerCase();
			for(SavedEmail se : registrationList) {
				String name = se.getOtherField1().toLowerCase();
				int pos = name.indexOf(eventName);
				if(pos >= 0) {
					newList.add(se);
				}
			}
			registrationList = newList;
		}
		
		final int pageNumber = getPageNumber();
		newList = new ArrayList<SavedEmail>();
		int count = 1;
		for(int i = (pageNumber - 1) * itemsPerPage; i < registrationList.size(); i++)
		{
			if(count <= itemsPerPage)
				newList.add(registrationList.get(i));
			count++;
		}
		request.setAttribute("registrationList", newList);
		PagingUtil pagingUtil = new PagingUtil(registrationList.size(), itemsPerPage, pageNumber, itemsPerPage);
		request.setAttribute("pagingUtil", pagingUtil);
	}
	
	public void setReservations() {
		
		itemsPerPage = user.getItemsPerPage();
		final Order[] orders = { Order.asc("sortOrder") };
		List<Category> rootCategories;
		rootCategories = categoryDelegate.findAllRootCategories(company, true, orders).getList();
		String programId = request.getParameter("programId");
		reservationList = savedEmailDelegate.findEmailByFormName(company, "Program", Order.desc("createdOn")).getList();
		List<SavedEmail> newList = new ArrayList<SavedEmail>();
		if(programId != null && !programId.equals("")) {
			for(SavedEmail se : reservationList) {
				if(se.getOtherField2().equals(programId)) {
					newList.add(se);
				}
			}
			reservationList = newList;
		}
		
		/*
		List<SavedEmail> filterList = new ArrayList<SavedEmail>();
		HashMap<String, SavedEmail> set = new HashMap<String, SavedEmail>();
		
		for(SavedEmail se : reservationList) {
			if(!set.containsKey(se.getEmailContent().split("###")[0].split(":")[1])) {
				filterList.add(se);
				set.put(se.getEmailContent().split("###")[0].split(":")[1], se);
			}
		}
		reservationList = filterList;*/
		
		final int pageNumber = getPageNumber();
		newList = new ArrayList<SavedEmail>();
		int count = 1;
		for(int i = (pageNumber - 1) * itemsPerPage; i < reservationList.size(); i++)
		{
			if(count <= itemsPerPage)
				newList.add(reservationList.get(i));
			count++;
		}
		request.setAttribute("reservationList", newList);
		PagingUtil pagingUtil = new PagingUtil(reservationList.size(), itemsPerPage, pageNumber, itemsPerPage);
		request.setAttribute("pagingUtil", pagingUtil);
		
		request.setAttribute("rootCategories", rootCategories);
		
	}
	
	private int getPageNumber() {
		int pageNumber = 1;
		try
		{
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			logger.debug("page number is " + pageNumber);
		}
		catch(final Exception e)
		{
			logger.debug("setting page to 1");
		}
		return pageNumber;
	}



	public String skechersRaffle() {
		
		DateTime dateFrom = new DateTime(new DateTime().getYear(), new DateTime().getMonthOfYear(), new DateTime().getDayOfMonth(), 0, 0, 0, 0);
		DateTime dateTo = new DateTime(new DateTime().getYear(), new DateTime().getMonthOfYear(), new DateTime().getDayOfMonth(), 23, 59, 59, 999);

		if(!StringUtils.isEmpty(request.getParameter("startDate")) && !StringUtils.isEmpty(request.getParameter("endDate")))
		{
			final String[] start = request.getParameter("startDate").split("-");
			final String[] end = request.getParameter("endDate").split("-");

			dateFrom = new DateTime(new Integer(start[2]).intValue(), new Integer(start[0]).intValue(), new Integer(start[1]).intValue(), 0, 0, 0, 0);
			dateTo = new DateTime(new Integer(end[2]).intValue(), new Integer(end[0]).intValue(), new Integer(end[1]).intValue(), 23, 59, 59, 999);
		}

		final String[] sDate = dateFrom.toString().substring(0, 10).split("-");
		final String[] eDate = dateTo.toString().substring(0, 10).split("-");

		setStartDate(sDate[1] + "-" + sDate[2] + "-" + sDate[0]);
		setEndDate(eDate[1] + "-" + eDate[2] + "-" + eDate[0]);
		emailsList = savedEmailDelegate.findEmailByDateAndFormNameWithPaging(company, dateFrom, dateTo, "Raffle entry form", page, itemsPerPage, Order.desc("createdOn")).getList();
		
		return SUCCESS;
		
	}
	
	public String downloadSkechersRaffleEntryDetails() throws Exception {
		
		try{ 
			
			String str_fromDate = request.getParameter("startDate");
			String str_toDate = request.getParameter("endDate");
	        DateFormat formatter ; 	    
	        Calendar c1 = Calendar.getInstance();
	        Calendar c2 = Calendar.getInstance();
	        formatter = new SimpleDateFormat("MM-dd-yyyy");
	        formatter.parse(str_toDate);
	        c1.setTime(formatter.parse(str_fromDate));
	        c1.add(Calendar.DATE, -1);
	        c2.setTime(formatter.parse(str_toDate));
	        c2.add(Calendar.DATE, 1);
	        str_fromDate = formatter.format(c1.getTime());
	        str_toDate = formatter.format(c2.getTime());
	        Date fromDate = (Date)formatter.parse(str_fromDate);
	        Date toDate = (Date)formatter.parse(str_toDate);
	        
			emailsList = savedEmailDelegate.findEmailByDateAndFormName(company, fromDate, toDate, "Raffle entry form").getList();
			
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			fileName="Skechers_Raffle_Entries.xls";
			filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
			//filePath =  locationPath + fileName;
			logger.debug("filepath (member) : " + filePath);		
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file);		
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFRow r = null;
			HSSFCell c = null;
			HSSFCellStyle style;
			HSSFDataFormat format = wb.createDataFormat();

			r=s.createRow(0);
			c = r.createCell((short) 0);
			c.setCellValue("RAFFLE ENTRIES");
			
			r=s.createRow(2);
			c = r.createCell((short) 0);
			c.setCellValue("NAME");
			c = r.createCell((short) 1);
			c.setCellValue("COMPLETE ADDRESS");
			c = r.createCell((short) 2);
			c.setCellValue("EMAIL ADDRESS");
			c = r.createCell((short) 3);
			c.setCellValue("CONTACT NO.");
			c = r.createCell((short) 4);
			c.setCellValue("OFFICIAL RECEIPT NO.");
			c = r.createCell((short) 5);
			c.setCellValue("BRANCH");
			c = r.createCell((short) 6);
			c.setCellValue("RAFFLE CODE");
			
			int counter = 3;
			for(SavedEmail email : emailsList) {
				r=s.createRow(counter);
				for(int i = 0; i < 7; i++) {
					switch (i) {
					case 0:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("name"));
						break;
					case 1:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("address"));
						break;
					case 2:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("email"));
						break;
					case 3:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("contactNo"));
						break;
					case 4:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("officialReceipt"));
						break;
					case 5:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("branch"));
						break;
					case 6:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("raffleCode"));
						break;
					}
				}
				counter++;
			}
			
			wb.write(out);
			out.close();
			
			logger.debug("start download...");
			download(filePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String generateSkechersWinner() {
		
		DateTime dateFrom = new DateTime(new DateTime().getYear(), new DateTime().getMonthOfYear(), new DateTime().getDayOfMonth(), 0, 0, 0, 0);
		DateTime dateTo = new DateTime(new DateTime().getYear(), new DateTime().getMonthOfYear(), new DateTime().getDayOfMonth(), 23, 59, 59, 999);

		if(!StringUtils.isEmpty(request.getParameter("startDate")) && !StringUtils.isEmpty(request.getParameter("endDate")))
		{
			final String[] start = request.getParameter("startDate").split("-");
			final String[] end = request.getParameter("endDate").split("-");

			dateFrom = new DateTime(new Integer(start[2]).intValue(), new Integer(start[0]).intValue(), new Integer(start[1]).intValue(), 0, 0, 0, 0);
			dateTo = new DateTime(new Integer(end[2]).intValue(), new Integer(end[0]).intValue(), new Integer(end[1]).intValue(), 23, 59, 59, 999);
		}

		final String[] sDate = dateFrom.toString().substring(0, 10).split("-");
		final String[] eDate = dateTo.toString().substring(0, 10).split("-");

		setStartDate(sDate[1] + "-" + sDate[2] + "-" + sDate[0]);
		setEndDate(eDate[1] + "-" + eDate[2] + "-" + eDate[0]);
		emailsList = savedEmailDelegate.findEmailByDateAndFormNameWithPaging(company, dateFrom, dateTo, "Raffle entry form", page, itemsPerPage, Order.desc("createdOn")).getList();
		
		request.setAttribute("displayStartDate", new DateTime(Integer.parseInt(sDate[0]), Integer.parseInt(sDate[1]), Integer.parseInt(sDate[2]), 0, 0, 0, 0).toDate());
		request.setAttribute("displayEndDate", new DateTime(Integer.parseInt(eDate[0]), Integer.parseInt(eDate[1]), Integer.parseInt(eDate[2]), 0, 0, 0, 0).toDate());
		
		if(emailsList.size() > 0)
		{
			final int index = new Random().nextInt(emailsList.size());

			request.setAttribute("skechersWinner", emailsList.get(index));
		}
		
		return SUCCESS;
		
	}
	
	public String bluewarnerRaffle() {
		
		DateTime dateFrom = new DateTime(new DateTime().getYear(), new DateTime().getMonthOfYear(), new DateTime().getDayOfMonth(), 0, 0, 0, 0);
		DateTime dateTo = new DateTime(new DateTime().getYear(), new DateTime().getMonthOfYear(), new DateTime().getDayOfMonth(), 23, 59, 59, 999);

		if(!StringUtils.isEmpty(request.getParameter("startDate")) && !StringUtils.isEmpty(request.getParameter("endDate")))
		{
			final String[] start = request.getParameter("startDate").split("-");
			final String[] end = request.getParameter("endDate").split("-");

			dateFrom = new DateTime(new Integer(start[2]).intValue(), new Integer(start[0]).intValue(), new Integer(start[1]).intValue(), 0, 0, 0, 0);
			dateTo = new DateTime(new Integer(end[2]).intValue(), new Integer(end[0]).intValue(), new Integer(end[1]).intValue(), 23, 59, 59, 999);
		}

		final String[] sDate = dateFrom.toString().substring(0, 10).split("-");
		final String[] eDate = dateTo.toString().substring(0, 10).split("-");

		setStartDate(sDate[1] + "-" + sDate[2] + "-" + sDate[0]);
		setEndDate(eDate[1] + "-" + eDate[2] + "-" + eDate[0]);
		emailsList = savedEmailDelegate.findEmailByDateAndFormNameWithPaging(company, dateFrom, dateTo, "Home", page, itemsPerPage, Order.desc("createdOn")).getList();
		
		return SUCCESS;
		
	}
	
	public String downloadBluewarnerRaffleEntryDetails() throws Exception {
		
		try{ 
			
			String str_fromDate = request.getParameter("startDate");
			String str_toDate = request.getParameter("endDate");
	        DateFormat formatter ; 	    
	        Calendar c1 = Calendar.getInstance();
	        Calendar c2 = Calendar.getInstance();
	        formatter = new SimpleDateFormat("MM-dd-yyyy");
	        formatter.parse(str_toDate);
	        c1.setTime(formatter.parse(str_fromDate));
	        c1.add(Calendar.DATE, -1);
	        c2.setTime(formatter.parse(str_toDate));
	        c2.add(Calendar.DATE, 1);
	        str_fromDate = formatter.format(c1.getTime());
	        str_toDate = formatter.format(c2.getTime());
	        Date fromDate = (Date)formatter.parse(str_fromDate);
	        Date toDate = (Date)formatter.parse(str_toDate);

	        if(!str_fromDate.equals(null) || !str_fromDate.equals("")){
	        	emailsList = savedEmailDelegate.findEmailByDateAndFormName(company, fromDate, toDate, "Home").getList();
	        }else{
	        	emailsList = savedEmailDelegate.findAll(company).getList();
	        }
	        
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			fileName="Bluewarner_Raffle_Entries.xls";
			filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
			//filePath =  locationPath + fileName;
			logger.debug("filepath (member) : " + filePath);		
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file);		
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFRow r = null;
			HSSFCell c = null;
			HSSFCellStyle style;
			HSSFDataFormat format = wb.createDataFormat();

			r=s.createRow(0);
			c = r.createCell((short) 0);
			c.setCellValue("ENTRIES");
			
			r=s.createRow(2);
			c = r.createCell((short) 0);
			c.setCellValue("NAME");
			c = r.createCell((short) 1);
			c.setCellValue("ADDRESS");
			c = r.createCell((short) 2);
			c.setCellValue("EMAIL ADDRESS");
			c = r.createCell((short) 3);
			c.setCellValue("CONTACT NUMBER");
			c = r.createCell((short) 4);
			c.setCellValue("RECEIPT NUMBER");
			c = r.createCell((short) 5);
			c.setCellValue("PROMO CODE");
			
			int counter = 3;
			for(SavedEmail email : emailsList) {
				r=s.createRow(counter);
				for(int i = 0; i < 6; i++) {
					switch (i) {
					case 0:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("sender"));
						break;
					case 1:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("address"));
						break;
					case 2:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("email"));
						break;
					case 3:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("phone"));
						break;
					case 4:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("officialReceipt"));
						break;
					case 5:
						c = r.createCell((short) i);
						c.setCellValue(email.getOtherDetailMap().get("raffleCode"));
						break;
					}
				}
				counter++;
			}
			
			wb.write(out);
			out.close();
			
			logger.debug("start download...");
			download(filePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String downloadEmails() throws Exception {
		
		List<SavedEmail>  emailsForDownload;
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));
		
		if (request.getParameter("filter").equals("all")){
			emailsForDownload = savedEmailDelegate.findAll(company).getList();
			logger.info("\n\n\nEmail list for " + company.getName() + ":   " + emailsForDownload.size());
		}else{
			String fromMonth = request.getParameter("byMonth");
			String fromYear = request.getParameter("byYear");
			int int_toMonth;
			int int_toYear;
			if(Integer.parseInt(fromMonth)==12){
				int_toMonth = 1;
				int_toYear = Integer.parseInt(fromYear) +1;
			}else{
				int_toMonth = Integer.parseInt(fromMonth) +1;
				int_toYear = Integer.parseInt(fromYear);				
			}			
			String str_fromDate=fromMonth.concat("-"+ fromYear);
			String str_toDate=int_toMonth+ "-"+ int_toYear;
			logger.info("\n\nFrom " + str_fromDate);
			logger.info("\n\nFrom " + str_toDate);
	        DateFormat formatter ; 	    
	        formatter = new SimpleDateFormat("MM-yyyy");
	        Date fromDate = (Date)formatter.parse(str_fromDate);
	        Date toDate = (Date)formatter.parse(str_toDate);		
	        
	        
	        if(company.getName().equals("bluewarner")){
	        	if(!str_fromDate.equals(null) || !str_fromDate.equals("")){
	        		emailsForDownload = savedEmailDelegate.findEmailByDate(company, fromDate, toDate).getList();
		        }else{
		        	emailsForDownload = savedEmailDelegate.findAll(company).getList();
		        }
	        }else{
	        	emailsForDownload = savedEmailDelegate.findEmailByDate(company, fromDate, toDate).getList();
	        }
	        
	        
			
			logger.debug("\n\n\nEmail list for " + company.getName() + ":   " + emailsForDownload.size());			
		}
	
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="Forms_Submission_Report.xls";
		filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
		//filePath =  locationPath + fileName;
		logger.debug("filepath (member) : " + filePath);		
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);		
		
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		HSSFRow r = null;
		HSSFCell c = null;
		HSSFCellStyle style;
		HSSFDataFormat format = wb.createDataFormat();
   
	    
		wb.setSheetName(0, "FORM SUBMISSION REPORT - ");		
		

		
		/*r=s.createRow(1);
		r=s.createRow(2);
		c = r.createCell((short) 1);
		c.setCellValue("Form Name");
		c = r.createCell((short) 2);
		c.setCellValue("Sender");
		c = r.createCell((short) 3);
		c.setCellValue("Company");		
		c = r.createCell((short) 4);
		c.setCellValue("Phone");		
		c = r.createCell((short) 5);
		c.setCellValue("Email Address");	
		c = r.createCell((short) 6);
		c.setCellValue("Content");
		c = r.createCell((short) 7);
		c.setCellValue("Date Received");
		c = r.createCell((short) 8);
		c.setCellValue("Remarks");
		
		s.setColumnWidth((short) 1, (short) 9000);
		s.setColumnWidth((short) 2, (short) 9000);
		s.setColumnWidth((short) 3, (short) 9000);
		s.setColumnWidth((short) 4, (short) 9000);	
		s.setColumnWidth((short) 5, (short) 9000);	
		s.setColumnWidth((short) 6, (short) 9000);	
		s.setColumnWidth((short) 7, (short) 9000);	
		s.setColumnWidth((short) 8, (short) 9000); */
		
		if(company.getName().equalsIgnoreCase("bluewarner")) {
			r=s.createRow(1);
			r=s.createRow(2);
			c = r.createCell((short) 1);
			c.setCellValue("");		
			c = r.createCell((short) 2);
			c.setCellValue("Name");		
			c = r.createCell((short) 3);
			c.setCellValue("Address");
			c = r.createCell((short) 4);
			c.setCellValue("Phone");		
			c = r.createCell((short) 5);
			c.setCellValue("Email Address");	
			c = r.createCell((short) 6);
			c.setCellValue("Receipt Number");	
			c = r.createCell((short) 7);
			c.setCellValue("Promo Code");
			c = r.createCell((short) 8);
			c.setCellValue("Date");
			
			s.setColumnWidth((short) 1, (short) 9000);
			s.setColumnWidth((short) 2, (short) 9000);
			s.setColumnWidth((short) 3, (short) 9000);
			s.setColumnWidth((short) 4, (short) 9000);	
			s.setColumnWidth((short) 5, (short) 9000);	
			s.setColumnWidth((short) 6, (short) 9000);	
			s.setColumnWidth((short) 7, (short) 9000);	
			s.setColumnWidth((short) 8, (short) 9000);
			
		}else{
		
		
			r=s.createRow(1);
			r=s.createRow(2);
			c = r.createCell((short) 1);
			c.setCellValue("Form Name");
			c = r.createCell((short) 2);
			c.setCellValue("Sender");
			c = r.createCell((short) 3);
			c.setCellValue("Company");		
			c = r.createCell((short) 4);
			c.setCellValue("Phone");		
			c = r.createCell((short) 5);
			c.setCellValue("Email Address");	
			c = r.createCell((short) 6);
			c.setCellValue("Content");
			c = r.createCell((short) 7);
			c.setCellValue("Date Received");
			c = r.createCell((short) 8);
			c.setCellValue("Remarks");
			
			s.setColumnWidth((short) 1, (short) 9000);
			s.setColumnWidth((short) 2, (short) 9000);
			s.setColumnWidth((short) 3, (short) 9000);
			s.setColumnWidth((short) 4, (short) 9000);	
			s.setColumnWidth((short) 5, (short) 9000);	
			s.setColumnWidth((short) 6, (short) 9000);	
			s.setColumnWidth((short) 7, (short) 9000);	
			s.setColumnWidth((short) 8, (short) 9000);
		}
		
		short rowNum=3;
		short count = 0;
		String emailContent;
		
		for(SavedEmail e : emailsForDownload)
		{
			if(company.getName().equalsIgnoreCase("bluewarner")) {
				r=s.createRow((short) ++rowNum);
				c = r.createCell((short) 1);
				c.setCellValue(++count);
				c = r.createCell((short) 2);
				c.setCellValue(e.getSender());
				c = r.createCell((short) 3);
				c.setCellValue(e.getOtherDetailMap().get("address"));
				c = r.createCell((short) 4);
				c.setCellValue(e.getPhone());
				c = r.createCell((short) 5);
				c.setCellValue(e.getEmail());
				c = r.createCell((short) 6);
				c.setCellValue(e.getOtherDetailMap().get("receipt"));
				c = r.createCell((short) 7);
				c.setCellValue(e.getOtherDetailMap().get("promo"));
				c = r.createCell((short) 8);
			     SimpleDateFormat formatter
		         = new SimpleDateFormat ("MM.dd.yyyy HH:mm:ss");
				c.setCellValue(formatter.format(e.getCreatedOn()));
			}else{
			
			    r=s.createRow((short) ++rowNum);
				c = r.createCell((short) 1);
				c.setCellValue(e.getFormName());
				c = r.createCell((short) 2);
				c.setCellValue(e.getSender());
				c = r.createCell((short) 3);
				c.setCellValue(e.getCompany().getName());
				c = r.createCell((short) 4);
				c.setCellValue(e.getPhone());
				c = r.createCell((short) 5);
				c.setCellValue(e.getEmail());
				c = r.createCell((short) 6);
				emailContent = e.getEmailContent();
				emailContent = emailContent.replaceAll("<br>","\n");
				emailContent = emailContent.replaceAll("<(.*?)>", "");
				c.setCellValue(emailContent);
				
				c = r.createCell((short) 7);
				
			     SimpleDateFormat formatter
		         = new SimpleDateFormat ("MM.dd.yyyy");
				c.setCellValue(formatter.format(e.getCreatedOn()));
				//c = r.createCell((short) 7);
//				 DateTime dt = m.getMembershipDate();
//				 DateTimeFormatter fmt = DateTimeFormat.forPattern("MM-dd-yyyy hh:mm a");
//				 String str = fmt.print(dt);				
//				c.setCellValue(str);				
//				c = r.createCell((short) 8);
//				c.setCellValue(m.getSongDownloads().size());				
//				style2 = wb.createCellStyle();
//				style2.setDataFormat(format2.getFormat("$###.##"));
				
				c = r.createCell((short) 8);
				c.setCellValue("");
			}
				
		}
		
		
		
		
		wb.write(out);
		out.close();
		
		logger.debug("start download...");
		download(filePath);
		
		
		
		return SUCCESS;
	}
	

	public String downloadRegistrants() throws Exception {
		

		List<Member> members = memberDelegate.findPurpose(company,"Registration");
		
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		//fileName="Cancelled_Billing_Payments_Summary.xls";
		filePath =  locationPath + "reports";
		//filePath="C:/Users/Eron/Desktop";
	
		File writeDir = new File(filePath);
		WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "test2.xls");									
		WritableSheet writableSheet = JXLUtil.createWritableSheet(writableWorkbook, "WebToGo", 0);
		filePath= writeDir+"/test2.xls";
		//JXLUtil.addImage(writableSheet, 0, 0, 5, 5, "C:/Users/Vincent/Desktop/Photo0003.jpg");
		int count=0;
		List<RegistrationItemOtherField>  temp2 = members.get(0).getRegistrationItemOtherFields();
		
		for(RegistrationItemOtherField temp3 : temp2){		
			JXLUtil.addLabelCell(writableSheet, count, 0, temp3.getOtherField().getName(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
			JXLUtil.setColumnView(writableSheet, count, temp3.getOtherField().getName().length()*2);
			count++;
		}		
		List<String> names2 = new ArrayList();
		names2.add("digital");
		names2.add(	"passport");
		names2.add(	"visa");
		names2.add(	"birth");
		names2.add(	"financial");
		names2.add(	"academic");
		names2.add(	"ecoe");
		int row=2, col=0;
		for(Member temp : members){		
			col=0;
			for(RegistrationItemOtherField registrations : temp.getRegistrationItemOtherFields()){
				if(names2.contains(registrations.getOtherField().getName())){
					JXLUtil.addLabelCell(writableSheet, col, row, servletContext.getRealPath("")+"/companies/bright/attachments/registrations/"+registrations.getContent(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8));
				}
				else{
					JXLUtil.addLabelCell(writableSheet, col, row, registrations.getContent(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8));
				}
				col++;
			}
		row++;
		}
		writableWorkbook.write();
		writableWorkbook.close();
		
		//System.out.println("Excel creation completed.");
		
		
		
		/*
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="Forms_Submission_Report.xls";
		//filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
		filePath =  locationPath + fileName;
		logger.debug("filepath (member) : " + filePath);		
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);		
		
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		HSSFRow r = null;
		HSSFCell c = null;    
		wb.setSheetName(0, "FORM SUBMISSION REPORT - ");		
		
		r=s.createRow(1);
		r=s.createRow(2);
		c = r.createCell((short) 1);
		c.setCellValue("Form Name");
		c = r.createCell((short) 2);
		c.setCellValue("Sender");
		c = r.createCell((short) 3);
		c.setCellValue("Company");		
		c = r.createCell((short) 4);
		c.setCellValue("Phone");		
		c = r.createCell((short) 5);
		c.setCellValue("Email Address");	
		c = r.createCell((short) 6);
		c.setCellValue("Content");
		c = r.createCell((short) 7);
		c.setCellValue("Date Received");
		c = r.createCell((short) 8);
		c.setCellValue("Remarks");
		
		s.setColumnWidth((short) 1, (short) 9000);
		s.setColumnWidth((short) 2, (short) 9000);
		s.setColumnWidth((short) 3, (short) 9000);
		s.setColumnWidth((short) 4, (short) 9000);	
		s.setColumnWidth((short) 5, (short) 9000);	
		s.setColumnWidth((short) 6, (short) 9000);	
		s.setColumnWidth((short) 7, (short) 9000);	
		s.setColumnWidth((short) 8, (short) 9000);
		
		short rowNum=3;
		String emailContent;
		
		for(SavedEmail e : emailsForDownload)
		{
			    r=s.createRow((short) ++rowNum);
				c = r.createCell((short) 1);
				c.setCellValue(e.getFormName());
				c = r.createCell((short) 2);
				c.setCellValue(e.getSender());
				c = r.createCell((short) 3);
				c.setCellValue(e.getCompany().getName());
				c = r.createCell((short) 4);
				c.setCellValue(e.getPhone());
				c = r.createCell((short) 5);
				c.setCellValue(e.getEmail());
				c = r.createCell((short) 6);
				
				emailContent = e.getEmailContent();
				emailContent = emailContent.replaceAll("<br>","\n");
				emailContent = emailContent.replaceAll("<(.*?)>", "");
				c.setCellValue(emailContent);
				
				c = r.createCell((short) 7);
				
			     SimpleDateFormat formatter
		         = new SimpleDateFormat ("MM.dd.yyyy");
				c.setCellValue(formatter.format(e.getCreatedOn()));
				
				c = r.createCell((short) 8);
				c.setCellValue("");
				
		}
		
		
		
		
		wb.write(out);
		out.close();
		
		logger.debug("start download...");*/
		download(filePath);
		
		
		
		return SUCCESS;
	}
	
	public List<Company> sortList(List<Company> toBeSorted) {
		Company temp;
	    for (int i=1; i < toBeSorted.size(); i++) { 
	        for (int j=0; j < toBeSorted.size()-i; j++) {
	            if (0 < (""+toBeSorted.get(j).getNameEditable()).compareTo(""+toBeSorted.get(j+1).getNameEditable())) {
	            	temp = toBeSorted.get(j);
	            	toBeSorted.set(j, toBeSorted.get(j+1));
	            	toBeSorted.set(j+1, temp);
	            }
	        }
	    }
		return toBeSorted;
	}
	
	public String generateCompaniesDirectoryXLS() throws Exception{	
		

		List<Company>  companies;
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));
		//System.out.println("GENERATE COMPANIES DIRECTORY");
		if(request.getParameter("filter")!=null){
			if (request.getParameter("filter").equals("all")){
				
				
				//companies = companyDelegate.findAll();
				companies = companyDelegate.filterByStatus(status);
				companies = sortList(companies); 
				//System.out.println("all companies");
				
			}else{
		
				companies = companyDelegate.findByStatus(CompanyStatusEnum.getIndex(Integer.parseInt(request.getParameter("filter")))).getList();
				//System.out.println("company size: "+companies.size());
				
			} 
			
		}else{
			//companies = companyDelegate.findAll();
			companies = companyDelegate.filterByStatus(status);
			companies = sortList(companies); 
			//System.out.println("all companies");
		}
	
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="Companies_Directory.xls";
		filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
		//filePath =  locationPath + fileName;
		logger.debug("filepath (member) : " + filePath);		
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);		
		
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		HSSFRow r = null;
		HSSFCell c = null;
		HSSFCellStyle style;
		HSSFDataFormat format = wb.createDataFormat();
   
	    
		wb.setSheetName(0, "Companies_Directory - ");		
		

		
		r=s.createRow(1);
		r=s.createRow(2);
		c = r.createCell((short) 0);
		c.setCellValue("COMPANY");
		c = r.createCell((short) 1);
		c.setCellValue("STATUS");
		c = r.createCell((short) 2);
		c.setCellValue("ADDRESS");		
		c = r.createCell((short) 3);
		c.setCellValue("PHONE");		
		c = r.createCell((short) 4);
		c.setCellValue("CONTACT");	
		c = r.createCell((short) 5);
		c.setCellValue("WEB URL");
		c = r.createCell((short) 6);
		c.setCellValue("CHARGE");
		
		s.setColumnWidth((short) 0, (short) 9000);
		s.setColumnWidth((short) 1, (short) 9000);
		s.setColumnWidth((short) 2, (short) 9000);
		s.setColumnWidth((short) 3, (short) 9000);	
		s.setColumnWidth((short) 4, (short) 9000);	
		s.setColumnWidth((short) 5, (short) 9000);	
		s.setColumnWidth((short) 6, (short) 9000);	
		
		short rowNum=3;
		
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		for(Company e : companies)
		{
			if(e.getCompanySettings()!=null){
			    r=s.createRow((short) ++rowNum);
				c = r.createCell((short) 0);
				c.setCellValue(e.getNameEditable());
				c = r.createCell((short) 1);
				c.setCellValue(e.getCompanySettings().getCompanyStatus().name());
				c = r.createCell((short) 2);
				c.setCellValue(e.getAddress());
				c = r.createCell((short) 3);
				c.setCellValue(e.getPhone());
				c = r.createCell((short) 4);
				c.setCellValue(e.getContactPerson());
				c = r.createCell((short) 5);
				c.setCellValue(e.getServerName());
				c = r.createCell((short) 6);
				c.setCellValue(numberFormatter.format(Double.parseDouble(""+e.getCompanySettings().getMonthlyCharge())));
			}	
		}
		
		
		
		
		wb.write(out);
		out.close();
		
		logger.debug("start download...");
		download(filePath);
	
		return SUCCESS;
		
	}
	
public String generateBillingCancelledXLS() throws Exception{	
		
		List<Company> companies = companyDelegate.filterByStatus(status);
		companies = sortList(companies);
		List<Billing> bills; 
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="Cancelled_Billing_Payments_Summary.xls";
		filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
		//filePath =  locationPath + fileName;
		logger.debug("filepath (member) : " + filePath);		
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);		
		
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		HSSFRow r = null;
		HSSFCell c = null;
		HSSFCellStyle style;
		HSSFDataFormat format = wb.createDataFormat();
   
	    
		wb.setSheetName(0, "Cancelled_Billing_Payments - ");		
		

		
		r=s.createRow(1);
		r=s.createRow(2);
		c = r.createCell((short) 0);
		c.setCellValue("ID");
		c = r.createCell((short) 1);
		c.setCellValue("COMPANY");
		c = r.createCell((short) 2);
		c.setCellValue("TYPE");		
		c = r.createCell((short) 3);
		c.setCellValue("FROM");		
		c = r.createCell((short) 4);
		c.setCellValue("TO");	
		c = r.createCell((short) 5);
		c.setCellValue("AMOUNT");
		c = r.createCell((short) 6);
		c.setCellValue("STATUS");
		
		s.setColumnWidth((short) 0, (short) 2000);
		s.setColumnWidth((short) 1, (short) 9000);
		s.setColumnWidth((short) 2, (short) 9000);
		s.setColumnWidth((short) 3, (short) 9000);	
		s.setColumnWidth((short) 4, (short) 9000);	
		s.setColumnWidth((short) 5, (short) 9000);	
		s.setColumnWidth((short) 6, (short) 9000);	
		
		short rowNum=3;
		
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		for(Company e : companies)
		{
			bills = billingDelegate.findAll(e).getList();
			if(e.getCompanySettings()!=null){
				for(Billing bill: bills){
					if((""+bill.getStatus()).equals("CANCELLED")){
					    r=s.createRow((short) ++rowNum);
						c = r.createCell((short) 0);
						c.setCellValue(""+bill.getId());
						c = r.createCell((short) 1);
						c.setCellValue(e.getNameEditable());
						c = r.createCell((short) 2);
						c.setCellValue(bill.getType().name());
						c = r.createCell((short) 3);
						c.setCellValue(sdf.format(bill.getFromDate()));
						c = r.createCell((short) 4);
						c.setCellValue(sdf.format(bill.getToDate()));
						c = r.createCell((short) 5);
						c.setCellValue(numberFormatter.format(Double.parseDouble(""+bill.getAmountDue())));
						c = r.createCell((short) 6);
						c.setCellValue(""+bill.getStatus());
					}
				}
			}	
		}
		
		
		
		
		wb.write(out);
		out.close();
		
		logger.debug("start download...");
		download(filePath);
		return SUCCESS;
		
	}

public String generateBillingPendingXLS() throws Exception{	
	
	List<Company> companies = companyDelegate.filterByStatus(status);
	companies = sortList(companies);
	List<Billing> bills; 
	String basePath = servletContext.getRealPath("");
	String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
	new File(locationPath).mkdir();
	fileName="Pending_Billing_Payments_Summary.xls";
	filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
	//filePath =  locationPath + fileName;
	logger.debug("filepath (member) : " + filePath);		
	File file = new File(filePath);
	FileOutputStream out = new FileOutputStream(file);		
	
	
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet s = wb.createSheet();
	HSSFRow r = null;
	HSSFCell c = null;
	HSSFCellStyle style;
	HSSFDataFormat format = wb.createDataFormat();

    
	wb.setSheetName(0, "Pending_Billing_Payments - ");		
	

	
	r=s.createRow(1);
	r=s.createRow(2);
	c = r.createCell((short) 0);
	c.setCellValue("ID");
	c = r.createCell((short) 1);
	c.setCellValue("COMPANY");
	c = r.createCell((short) 2);
	c.setCellValue("TYPE");		
	c = r.createCell((short) 3);
	c.setCellValue("FROM");		
	c = r.createCell((short) 4);
	c.setCellValue("TO");	
	c = r.createCell((short) 5);
	c.setCellValue("AMOUNT");
	c = r.createCell((short) 6);
	c.setCellValue("STATUS");
	
	s.setColumnWidth((short) 0, (short) 2000);
	s.setColumnWidth((short) 1, (short) 9000);
	s.setColumnWidth((short) 2, (short) 9000);
	s.setColumnWidth((short) 3, (short) 9000);	
	s.setColumnWidth((short) 4, (short) 9000);	
	s.setColumnWidth((short) 5, (short) 9000);	
	s.setColumnWidth((short) 6, (short) 9000);	
	
	short rowNum=3;
	
	NumberFormat numberFormatter;
	numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
	numberFormatter.setMinimumFractionDigits(2);
	for(Company e : companies)
	{
		bills = billingDelegate.findAll(e).getList();
		if(e.getCompanySettings()!=null){
			for(Billing bill: bills){
				if((""+bill.getStatus()).equals("PENDING")){
				    r=s.createRow((short) ++rowNum);
					c = r.createCell((short) 0);
					c.setCellValue(""+bill.getId());
					c = r.createCell((short) 1);
					c.setCellValue(e.getNameEditable());
					c = r.createCell((short) 2);
					c.setCellValue(bill.getType().name());
					c = r.createCell((short) 3);
					c.setCellValue(sdf.format(bill.getFromDate()));
					c = r.createCell((short) 4);
					c.setCellValue(sdf.format(bill.getToDate()));
					c = r.createCell((short) 5);
					c.setCellValue(numberFormatter.format(Double.parseDouble(""+bill.getAmountDue())));
					c = r.createCell((short) 6);
					c.setCellValue(""+bill.getStatus());
				}
			}
		}	
	}
	
	
	
	
	wb.write(out);
	out.close();
	
	logger.debug("start download...");
	download(filePath);
	return SUCCESS;
	
}

public String generateBillingLastPaymentXLS() throws Exception{	
	
	List<Company> companies = companyDelegate.filterByStatus(status);
	companies = sortList(companies);
	List<Billing> bills; 
	String basePath = servletContext.getRealPath("");
	String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
	new File(locationPath).mkdir();
	fileName="Latest_Billing_Payments_Summary.xls";
	filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
	//filePath =  locationPath + fileName;
	logger.debug("filepath (member) : " + filePath);		
	File file = new File(filePath);
	FileOutputStream out = new FileOutputStream(file);		
	
	
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet s = wb.createSheet();
	HSSFRow r = null;
	HSSFCell c = null;
	HSSFCellStyle style;
	HSSFDataFormat format = wb.createDataFormat();

    
	wb.setSheetName(0, "Latest_Billing_Payments - ");		
	

	
	r=s.createRow(1);
	r=s.createRow(2);
	c = r.createCell((short) 0);
	c.setCellValue("ID");
	c = r.createCell((short) 1);
	c.setCellValue("COMPANY");
	c = r.createCell((short) 2);
	c.setCellValue("TYPE");		
	c = r.createCell((short) 3);
	c.setCellValue("FROM");		
	c = r.createCell((short) 4);
	c.setCellValue("TO");	
	c = r.createCell((short) 5);
	c.setCellValue("AMOUNT");
	c = r.createCell((short) 6);
	c.setCellValue("STATUS");
	
	s.setColumnWidth((short) 0, (short) 2000);
	s.setColumnWidth((short) 1, (short) 9000);
	s.setColumnWidth((short) 2, (short) 9000);
	s.setColumnWidth((short) 3, (short) 9000);	
	s.setColumnWidth((short) 4, (short) 9000);	
	s.setColumnWidth((short) 5, (short) 9000);	
	s.setColumnWidth((short) 6, (short) 9000);	
	
	short rowNum=3;
	
	NumberFormat numberFormatter;
	numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
	numberFormatter.setMinimumFractionDigits(2);
	for(Company e : companies)
	{
		bills = billingDelegate.findAll(e).getList();
		if(bills.size()>0){
			int x=bills.size()-1;
			Billing bill;
			do{		
				bill=bills.get(x);
				
				x--;		
			}while(!(""+bill.getStatus()).equals("PAID") && x>=0);
			if(e.getCompanySettings()!=null){
			if((""+bill.getStatus()).equals("PAID")){
			    r=s.createRow((short) ++rowNum);
				c = r.createCell((short) 0);
				c.setCellValue(""+bill.getId());
				c = r.createCell((short) 1);
				c.setCellValue(e.getNameEditable());
				c = r.createCell((short) 2);
				c.setCellValue(bill.getType().name());
				c = r.createCell((short) 3);
				c.setCellValue(sdf.format(bill.getFromDate()));
				c = r.createCell((short) 4);
				c.setCellValue(sdf.format(bill.getToDate()));
				c = r.createCell((short) 5);
				c.setCellValue(numberFormatter.format(Double.parseDouble(""+bill.getAmountDue())));
				c = r.createCell((short) 6);
				c.setCellValue(""+bill.getStatus());
			}
		}	
	}
	
	}
	
	
	wb.write(out);
	out.close();
	
	logger.debug("start download...");
	download(filePath);
	return SUCCESS;
	
}
	
	public void download(String filePath) throws Exception {
		
		File file = new File(filePath);
		if(!file.exists()) {
			logger.fatal("Unabled to locate file: " + filePath);
		}
		
		fInStream = new FileInputStream(file);
		inputStream = new FileInputStream(file);
		contentLength = file.length();
	}
	
	
	public void setEmails(List<SavedEmail> emails) {
		this.emails = emails;
	}


	public void setCompany(Company company) {
		this.company = company;
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
	}
	
	public void setTotalItems() {
		this.totalItems = savedEmailDelegate.findAll(company).getTotal();
	}
	
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public List<SavedEmail> getEmails() {
		return emails;
	}
	
	public void setFilterDays(int filterDays) {
		this.filterDays = filterDays;
	}



	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}



	public void setHasRegistrants(Boolean hasRegistrants) {
		this.hasRegistrants = hasRegistrants;
	}



	public Boolean getHasRegistrants() {
		return hasRegistrants;
	}
	
	public void setEmailsList(List<SavedEmail> emailsList) {
		this.emailsList = emailsList;
	}

	public List<SavedEmail> getEmailsList() {
		return emailsList;
	}



	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}



	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}



	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public List<SavedEmail> getReservationList() {
		return reservationList;
	}



	public void setReservationList(List<SavedEmail> reservationList) {
		this.reservationList = reservationList;
	}



	public List<SavedEmail> getRegistrationList() {
		return registrationList;
	}



	public void setRegistrationList(List<SavedEmail> registrationList) {
		this.registrationList = registrationList;
	}
		
	
	
	
}
