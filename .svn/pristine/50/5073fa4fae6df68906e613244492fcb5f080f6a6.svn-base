package com.ivant.cms.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import com.ivant.cms.delegate.EventDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MicePhilippinesMemeberDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.User;
import com.ivant.cms.helper.ListMemberSubmissionHelper;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;

/*
 * @author Glenn Allen B. Sapla
 * @version 1.0, May 2009
 */
public class ListMembersSubmissionAction extends ActionSupport 
				implements UserAware, CompanyAware, PagingAware, ServletContextAware, ServletRequestAware {
	
	private static final long serialVersionUID = 4050469397961099239L;
	private static final Logger logger = Logger.getLogger(ListMembersSubmissionAction.class);
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private MicePhilippinesMemeberDelegate micePhilippinesMemeberDelegate = MicePhilippinesMemeberDelegate.getInstance();
	private ListMemberSubmissionHelper listMemberSubmissionHelper;
	private EventDelegate eventDelegate=EventDelegate.getInstance();
	private List<Event> registeredEventsByMember;
	private Company company;
	private User user;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	private int filterDays;
	private String filePath;
	private String fileName;
	private FileInputStream fInStream;
	private FileInputStream inputStream;
	private long contentLength;
	private ServletContext servletContext;
	private HttpServletRequest request;		
	private HttpServletResponse response;
	private String fieldName[];

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
	
	@Override
	public String execute() throws Exception {
		if(filterDays > 0) {
			emails = savedEmailDelegate.findLatestEmail(company, 3).getList();
		}
		else {
			emails = savedEmailDelegate.findAllWithPaging(company, page, itemsPerPage, Order.desc("createdOn")).getList();
		}
		return super.execute();
	}
	
	public String downloadCustomDetails() throws Exception {
		try{
		listMemberSubmissionHelper=new ListMemberSubmissionHelper();
		List<SavedEmail>  emailsForDownload;
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="_Member_List.xls";
		filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
		logger.debug("filepath (member) : " + filePath + "--" + fieldName.length);	
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);	
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFDataFormat format = workbook.createDataFormat();
		List<Member> members = new ArrayList<Member>();
		if(company.getName().equalsIgnoreCase("agian")){
			String list_name = request.getParameter("list_name");
			
			if(list_name.equals("active")){
				final List<Member> memListCounts = memberDelegate.findAll(company).getList();
				List<Member> memListCountss = new ArrayList<Member>();
				DateTime today = new DateTime();
				DateTime today90 = today.minusDays(90);
				
				for(final Member member : memListCounts){
					if(!isNull(member.getLastLogin())){
						Long d = today.toDate().getTime();
						Long a = (member.getLastLogin()).getTime();
						Long d90 = today90.toDate().getTime();
						if(d > a && a > d90){
							if(user.getUserType().getValue().equals("System Administrator")){
								memListCountss.add(member);
							}else{
								if(user.getInfo1().equals(member.getReg_companyName()) || user.getInfo1().equals(member.getInfo7())){
									memListCountss.add(member);
								}
							}
						}else{
							
						}
					}
				}
				members = memListCountss;
			}else if(list_name.equals("inactive")){
				final List<Member> memListCounts = memberDelegate.findAll(company).getList();
				List<Member> memListCountss = new ArrayList<Member>();
				DateTime today = new DateTime();
				DateTime today90 = today.minusDays(90);
				
				for(final Member member : memListCounts){
					if(!isNull(member.getLastLogin())){
						Long d = today.toDate().getTime();
						Long a = (member.getLastLogin()).getTime();
						Long d90 = today90.toDate().getTime();
						if(d > a && a > d90){
							
						}else{
							if(user.getUserType().getValue().equals("System Administrator")){
								memListCountss.add(member);
							}else{
								if(user.getInfo1().equals(member.getReg_companyName()) || user.getInfo1().equals(member.getInfo7())){
									memListCountss.add(member);
								}
							}
						}
					}
				}
				members = memListCountss;
			}else if(list_name.equals("verified")){
				final List<Member> memListCounts = memberDelegate.findAll(company).getList();
				List<Member> memListCountss = new ArrayList<Member>();
				for(final Member member : memListCounts){
					if(member.getVerified()){
						if(user.getUserType().getValue().equals("System Administrator")){
							memListCountss.add(member);
						}else{
							if(user.getInfo1().equals(member.getReg_companyName()) || user.getInfo1().equals(member.getInfo7())){
								memListCountss.add(member);
							}
						}
					}
				}
				members = memListCountss;
			}else if(list_name.equals("notverified")){
				final List<Member> memListCounts = memberDelegate.findAll(company).getList();
				List<Member> memListCountss = new ArrayList<Member>();
				for(final Member member : memListCounts){
					if(!member.getVerified()){
						if(user.getUserType().getValue().equals("System Administrator")){
							memListCountss.add(member);
						}else{
							if(user.getInfo1().equals(member.getReg_companyName()) || user.getInfo1().equals(member.getInfo7())){
								memListCountss.add(member);
							}
						}
					}
				}
				members = memListCountss;
			}else{
				final List<Member> memListCounts = memberDelegate.findAll(company).getList();
				List<Member> memListCountss = new ArrayList<Member>();
				for(final Member member : memListCounts){
					if(user.getUserType().getValue().equals("System Administrator")){
						memListCountss.add(member);
					}else{
						if(user.getInfo1().equals(member.getReg_companyName()) || user.getInfo1().equals(member.getInfo7())){
							memListCountss.add(member);
						}
					}
				}
				members = memListCountss;
			}
		}else{
			members = memberDelegate.findAll(company).getList();
		}
		workbook.setSheetName(0, "FORM SUBMISSION REPORT - ");		
		short cellCount=0;
		row=sheet.createRow(0);
		//Setting the Column name for excel
		for(String caption:fieldName){
			sheet.setColumnWidth((short) cellCount, (short) 7000);
	    	if(caption.equalsIgnoreCase("Registered Events")&&company.getName().equalsIgnoreCase("PCO")){
	    			cell = row.createCell((short)cellCount++);
	    			cell.setCellValue("Membership Registration");
	    		sheet.setColumnWidth((short) cellCount, (short) 7000);
	    			cell = row.createCell((short)cellCount++);
	    			cell.setCellValue("Events Registration");
	    	}else{
	    		cell = row.createCell((short) cellCount++);
	    		cell.setCellValue(caption); //cell header
	    	}
		}
		
		short rowNum=1;
		
		
		for(Member e : members)
		{}
		
		
		//System.out.println("THE PATH LOCATION IS "+filePath);
		
		workbook.write(out);
		out.close();
		
		logger.debug("start download...");
		//System.out.println("FILE PATH ::: " + filePath);
			
		download(filePath);
		
		}catch(Exception e){
			System.out.print(e);
		}
		
		return SUCCESS;
	}
	
	public String downloadGurkkaDetails()
	{
		try
		{
			fileName = "Gurkka_Member_List.xls";
			final String basePath = servletContext.getRealPath("");
			final String locationPath = basePath + File.separatorChar + "WEB-INF" + File.separatorChar;
			final File dir = new File(locationPath);
			if(!dir.exists())
			{
				dir.mkdir();
			}
			final String filePath = locationPath + "reports" + File.separatorChar + fileName;
			final File file = new File(filePath);
			final FileOutputStream out = new FileOutputStream(file);	
			
			final HSSFWorkbook workbook = new HSSFWorkbook();
			final HSSFSheet sheet = workbook.createSheet();
			final HSSFRow headerRow = sheet.createRow(0);
			
			final short width = 7500;
			final String[] headers = 
			{ 
				"Username", 
				"Email", 
				"ID", 
				"Name / Company", 
				"Date Joined",
				"Mobile No.",
				"Landline",
				"Fax",
				"Address",
				"Area",
				"Location",
				"Zipcode"
			};
			
			for(short i = 0; i < headers.length; i++)
			{
				final HSSFCell cell = headerRow.createCell(i);
				sheet.setColumnWidth(i, width);
				cell.setCellValue(headers[i]);
			}
			
			final List<Member> gurkkaMembers = memberDelegate.findAll(company).getList();
			if(gurkkaMembers != null && !gurkkaMembers.isEmpty())
			{
				for(short i = 0; i < gurkkaMembers.size(); i++)
				{
					final Member member = gurkkaMembers.get(i);
					final short rowCount = (short) (i + 1);
					sheet.setColumnWidth(rowCount, width);
					final HSSFRow dataRow = sheet.createRow(rowCount);
					
					short ctr = 0;
					dataRow.createCell(ctr++).setCellValue(member.getUsername());
					dataRow.createCell(ctr++).setCellValue(member.getEmail());
					dataRow.createCell(ctr++).setCellValue(member.getMobile());
					dataRow.createCell(ctr++).setCellValue(member.getLandline());
					dataRow.createCell(ctr++).setCellValue(member.getFax());
					dataRow.createCell(ctr++).setCellValue(member.getProvince());
					dataRow.createCell(ctr++).setCellValue(member.getCity());
					dataRow.createCell(ctr++).setCellValue(member.getZipcode());
				}
			}
  			
			workbook.write(out);
			out.close();
			download(filePath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println("GURKEKSAKDASKDKASKDSAKDKSA_____________");
		return SUCCESS;
	}
	
	public String downloadWatsonAAAMemberDetails() throws Exception {
		DateTime startDate = null;
		DateTime endDate = null;
		
		if(!StringUtils.isEmpty(request.getParameter("startDate")) && !StringUtils.isEmpty(request.getParameter("endDate"))) {
			String [] start = request.getParameter("startDate").split("-");
			String [] end = request.getParameter("endDate").split("-");
		
			startDate = new DateTime(new Integer(start[2]).intValue(),new Integer(start[0]).intValue(),new Integer(start[1]).intValue(),0,0,0,0);
			endDate = new DateTime(new Integer(end[2]).intValue(),new Integer(end[0]).intValue(),new Integer(end[1]).intValue(),23,59,59,999);
		}
		
		try {
			
			listMemberSubmissionHelper=new ListMemberSubmissionHelper();
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			fileName="Watson_AAA_Entry_List.xls";
			filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
			logger.debug("filepath (member) : " + filePath);	
			File file = new File(filePath);
			
			FileOutputStream out = new FileOutputStream(file);	
			
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFRow r = null;
			HSSFCell c = null;
			HSSFDataFormat format = wb.createDataFormat();
			
			wb.setSheetName(0, "FORM SUBMISSION REPORT - ");		
			
			short cellCount=0;
			
			r = s.createRow(0);
			
			// Generated Random Winner
			
			//Setting the Column name for excel
			for(String caption : fieldName){
				
				s.setColumnWidth((short) cellCount, (short) 7000);

		    	c = r.createCell((short) cellCount++);
		    	
		    	c.setCellValue(caption);
		    	
			}
			
			short rowNum=3;
			

			
			wb.write(out);

			out.close();
			
			download(filePath);
		
		} catch(Exception e) {
			System.out.print(e);
		}
		
		return SUCCESS;
	}
	
	public String downloadWatsonGlamMemberDetails() throws Exception {
		DateTime startDate = null;
		DateTime endDate = null;
		
		if(!StringUtils.isEmpty(request.getParameter("startDate")) && !StringUtils.isEmpty(request.getParameter("endDate"))) {
			String [] start = request.getParameter("startDate").split("-");
			String [] end = request.getParameter("endDate").split("-");
		
			startDate = new DateTime(new Integer(start[2]).intValue(),new Integer(start[0]).intValue(),new Integer(start[1]).intValue(),0,0,0,0);
			endDate = new DateTime(new Integer(end[2]).intValue(),new Integer(end[0]).intValue(),new Integer(end[1]).intValue(),23,59,59,999);
		}
		
		try {
			
			listMemberSubmissionHelper=new ListMemberSubmissionHelper();
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			fileName="Watson_Glam_Entry_List.xls";
			filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
			logger.debug("filepath (member) : " + filePath);	
			File file = new File(filePath);
			
			FileOutputStream out = new FileOutputStream(file);	
			
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFRow r = null;
			HSSFCell c = null;
			HSSFDataFormat format = wb.createDataFormat();
			
			wb.setSheetName(0, "FORM SUBMISSION REPORT - ");		
			
			short cellCount=0;
			
			r = s.createRow(0);
			
			// Generated Random Winner
			
			//Setting the Column name for excel
			for(String caption : fieldName){
				
				s.setColumnWidth((short) cellCount, (short) 7000);

		    	c = r.createCell((short) cellCount++);
		    	
		    	c.setCellValue(caption);
		    	
			}
			
			short rowNum=3;
			

			
			wb.write(out);

			out.close();
			
			download(filePath);
		
		} catch(Exception e) {
			System.out.print(e);
		}
		
		return SUCCESS;
	}	
	
	public String downloadWatsonStyleMemberDetails() throws Exception {
		DateTime startDate = null;
		DateTime endDate = null;
		
		if(!StringUtils.isEmpty(request.getParameter("startDate")) && !StringUtils.isEmpty(request.getParameter("endDate"))) {
			String [] start = request.getParameter("startDate").split("-");
			String [] end = request.getParameter("endDate").split("-");
		
			startDate = new DateTime(new Integer(start[2]).intValue(),new Integer(start[0]).intValue(),new Integer(start[1]).intValue(),0,0,0,0);
			endDate = new DateTime(new Integer(end[2]).intValue(),new Integer(end[0]).intValue(),new Integer(end[1]).intValue(),23,59,59,999);
		}
		
		try {
			
			listMemberSubmissionHelper=new ListMemberSubmissionHelper();
			String basePath = servletContext.getRealPath("");
			//System.out.println("basePath: " + basePath);
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			//System.out.println("locationPath: " + locationPath + "\n");
			new File(locationPath).mkdir();
			fileName="Watson_Style_Entry_List.xls";
			filePath =  locationPath + "jasper-report"+  File.separatorChar  + fileName;
			logger.debug("filepath (member) : " + filePath);	
			File file = new File(filePath);
			
			FileOutputStream out = new FileOutputStream(file);	
			
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFRow r = null;
			HSSFCell c = null;
			HSSFDataFormat format = wb.createDataFormat();
			
						
			wb.setSheetName(0, "FORM SUBMISSION REPORT - ");		
			
			short cellCount=0;
			
			r = s.createRow(0);
			
//			 Generated Random Winner
			
			//Setting the Column name for excel
			for(String caption : fieldName){
				
				s.setColumnWidth((short) cellCount, (short) 7000);

		    	c = r.createCell((short) cellCount++);
		    	
		    	c.setCellValue(caption);
		    	
			}
			
			short rowNum=3;
			

			
			wb.write(out);

			out.close();
			
			download(filePath);
			return SUCCESS;
		
		} catch(Exception e) {
			System.out.print(e);
		}
		
		return SUCCESS;
	}		
	
	public String downloadWatsonMemberDetails() throws Exception {
		DateTime startDate = null;
		DateTime endDate = null;
		
		if(!StringUtils.isEmpty(request.getParameter("startDate")) && !StringUtils.isEmpty(request.getParameter("endDate"))) {
			String [] start = request.getParameter("startDate").split("-");
			String [] end = request.getParameter("endDate").split("-");
		
			startDate = new DateTime(new Integer(start[2]).intValue(),new Integer(start[0]).intValue(),new Integer(start[1]).intValue(),0,0,0,0);
			endDate = new DateTime(new Integer(end[2]).intValue(),new Integer(end[0]).intValue(),new Integer(end[1]).intValue(),23,59,59,999);
		}
		
		try {
			
			listMemberSubmissionHelper=new ListMemberSubmissionHelper();
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			fileName="Watson_Member_List.xls";
			filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
			logger.debug("filepath (member) : " + filePath);	
			File file = new File(filePath);
			
			FileOutputStream out = new FileOutputStream(file);	
			
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFRow r = null;
			HSSFCell c = null;
			HSSFDataFormat format = wb.createDataFormat();
			
						
			wb.setSheetName(0, "FORM SUBMISSION REPORT - ");		
			
			short cellCount=0;
			
			r = s.createRow(0);
			
			// Generated Random Winner
			
			//Setting the Column name for excel
			for(String caption : fieldName){
				
				s.setColumnWidth((short) cellCount, (short) 7000);

		    	c = r.createCell((short) cellCount++);
		    	
		    	c.setCellValue(caption);
		    	
			}
			
			short rowNum=3;
			

			
			wb.write(out);

			out.close();
			
			download(filePath);
		
		} catch(Exception e) {
			System.out.print(e);
		}
		
		return SUCCESS;
	}
	
	public String downloadWatsonKiddieMemberDetails() throws Exception {
		DateTime startDate = null;
		DateTime endDate = null;
		
		if(!StringUtils.isEmpty(request.getParameter("startDate")) && !StringUtils.isEmpty(request.getParameter("endDate"))) {
			String [] start = request.getParameter("startDate").split("-");
			String [] end = request.getParameter("endDate").split("-");
		
			startDate = new DateTime(new Integer(start[2]).intValue(),new Integer(start[0]).intValue(),new Integer(start[1]).intValue(),0,0,0,0);
			endDate = new DateTime(new Integer(end[2]).intValue(),new Integer(end[0]).intValue(),new Integer(end[1]).intValue(),23,59,59,999);
		}
		
		try {
			
			listMemberSubmissionHelper=new ListMemberSubmissionHelper();
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			fileName="Watson_Kiddie_Entry_List.xls";
			filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
			logger.debug("filepath (member) : " + filePath);	
			File file = new File(filePath);
			
			FileOutputStream out = new FileOutputStream(file);	
			
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFRow r = null;
			HSSFCell c = null;
			HSSFDataFormat format = wb.createDataFormat();
			
						
			wb.setSheetName(0, "FORM SUBMISSION REPORT - ");		
			
			short cellCount=0;
			
			r = s.createRow(0);
			
			// Generated Random Winner
			
			//Setting the Column name for excel
			for(String caption : fieldName){
				
				s.setColumnWidth((short) cellCount, (short) 7000);

		    	c = r.createCell((short) cellCount++);
		    	
		    	c.setCellValue(caption);
		    	
			}
			
			short rowNum=3;
			

			
			wb.write(out);

			out.close();
			
			download(filePath);
		
		} catch(Exception e) {
			System.out.print(e);
		}
		
		return SUCCESS;
	}
	
	public String downloadWatsonSUSDMemberDetails() throws Exception {
		DateTime startDate = null;
		DateTime endDate = null;
		
		if(!StringUtils.isEmpty(request.getParameter("startDate")) && !StringUtils.isEmpty(request.getParameter("endDate"))) {
			String [] start = request.getParameter("startDate").split("-");
			String [] end = request.getParameter("endDate").split("-");
		
			startDate = new DateTime(new Integer(start[2]).intValue(),new Integer(start[0]).intValue(),new Integer(start[1]).intValue(),0,0,0,0);
			endDate = new DateTime(new Integer(end[2]).intValue(),new Integer(end[0]).intValue(),new Integer(end[1]).intValue(),23,59,59,999);
		}
		
		try {
			
			listMemberSubmissionHelper=new ListMemberSubmissionHelper();
			String basePath = servletContext.getRealPath("");
			//System.out.println("basePath: " + basePath);
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			//System.out.println("locationPath: " + locationPath + "\n");
			new File(locationPath).mkdir();
			fileName="Watson_Sun_Up_to_Sun_Down_Entry_List.xls";
			filePath =  locationPath + "jasper-report"+  File.separatorChar  + fileName;
			logger.debug("filepath (member) : " + filePath);	
			File file = new File(filePath);
			
			FileOutputStream out = new FileOutputStream(file);	
			
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFRow r = null;
			HSSFCell c = null;
			HSSFDataFormat format = wb.createDataFormat();
			
			//List<WatsonStyleResult>  watsonStyleResultList = watsonStyleResultDelegate.findAllMembers(itemsPerPage, page, startDate, endDate).getList();
			wb.setSheetName(0, "FORM SUBMISSION REPORT - ");		
			
			short cellCount=0;
			
			r = s.createRow(0);
			
//			 Generated Random Winner
			
			//Setting the Column name for excel
			for(String caption : fieldName){
				
				s.setColumnWidth((short) cellCount, (short) 7000);

		    	c = r.createCell((short) cellCount++);
		    	
		    	c.setCellValue(caption);
		    	
			}
			short rowNum=3;
			

			wb.write(out);

			out.close();
			
			download(filePath);
			return SUCCESS;
		
		} catch(Exception e) {
			System.out.print(e);
		}
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
	
	public String[] getFieldName() {
		return fieldName;
	}

	public void setFieldName(String[] fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	protected boolean isNull(Object param)
	{
		return null == param;
	}
}