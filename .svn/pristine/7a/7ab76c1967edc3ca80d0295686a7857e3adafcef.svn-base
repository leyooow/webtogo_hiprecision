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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.EventDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.User;
import com.ivant.cms.helper.ListMemberSubmissionHelper;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.JXLUtil;
import com.opensymphony.xwork2.ActionSupport;

/*
 * @author Glenn Allen B. Sapla
 * @version 1.0, May 2009
 */
public class ListOrders extends ActionSupport 
				implements UserAware, CompanyAware, PagingAware, ServletContextAware, ServletRequestAware {
	
	private static final long serialVersionUID = 4050469397961099239L;
	private static final Logger logger = Logger.getLogger(ListMembersSubmissionAction.class);
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private ListMemberSubmissionHelper listMemberSubmissionHelper;
	private  EventDelegate eventDelegate=EventDelegate.getInstance();
	private List<Event> registeredEventsByMember;
	
	private Company company;
	private User user;

	private int page;
	private int totalItems;
	private int itemsPerPage;
	private int filterDays;
	
	private String filePath;
	private String fileName;
	private String fieldName[];
	private String memberId;
	private FileInputStream fInStream;
	private FileInputStream inputStream;
	private long contentLength;
	
	private ServletContext servletContext;
	private HttpServletRequest request;	

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

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

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	private List<CartOrder> orderList=new ArrayList<CartOrder>();
	protected static final CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	protected static final CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	
	public String downloadOrderExcel() throws Exception {
		List<CartOrder> ordersForDownload = null;
		Boolean printAll = false;
		
		
		Member mem = new Member();
		try{
			Long mid = Long.parseLong(request.getParameter("member_id_"));
			if(mid != 0L){
				mem = memberDelegate.find(mid);
			}
			else{
				mem = null;
			}
		}
		catch(Exception e){
			mem = null;
		}
		
		
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));
		//System.out.println("COMPANY NAME : " + company.getName());
		if (request.getParameter("filter").equals("all"))
		{
			if(mem != null){
				ordersForDownload = cartOrderDelegate.findAll(company, mem);
			}
			else{
				ordersForDownload = cartOrderDelegate.findAllByCompany(company);
			}
			
			logger.info("\n\n\nEmail list for " + company.getName() + ":   " + ordersForDownload.size());
		}	
		else
		{
			
			String str_fromDate = request.getParameter("fromDate");
			String str_toDate = request.getParameter("toDate");
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
	        if(mem != null) {
	        	ordersForDownload = cartOrderDelegate.findOrderByDateAndMember(company, fromDate, toDate, mem).getList();
	       }
	        else{
	        	ordersForDownload = cartOrderDelegate.findOrderByDate(company, fromDate, toDate).getList();
	        }
			
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
		
		r=s.createRow(1);
		r=s.createRow(2);
		c = r.createCell((short) 1);
		c.setCellValue("Order Date");
		c = r.createCell((short) 2);
		c.setCellValue("ID");
		c = r.createCell((short) 3);
		c.setCellValue("Customer");		
		c = r.createCell((short) 4);
		c.setCellValue("Shipping Info");		
		c = r.createCell((short) 5);
		c.setCellValue("Items Price");	
		c = r.createCell((short) 6);
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.PURE_NECTAR)) {	
			c.setCellValue("Payment Type");
		}else{
			c.setCellValue("Shipping Info");
		}
		
		c = r.createCell((short) 7);
		c.setCellValue("Status");
		
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE)) {
			c = r.createCell((short) 8);
			c.setCellValue("Branch");
		}
		
		
		
		s.setColumnWidth((short) 1, (short) 9000);
		s.setColumnWidth((short) 2, (short) 9000);
		s.setColumnWidth((short) 3, (short) 9000);
		s.setColumnWidth((short) 4, (short) 9000);	
		s.setColumnWidth((short) 5, (short) 9000);	
		s.setColumnWidth((short) 6, (short) 9000);	
		s.setColumnWidth((short) 7, (short) 9000);	
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.PURE_NECTAR)) {
			s.setColumnWidth((short) 8, (short) 9000);
		}
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE)) {
			s.setColumnWidth((short) 8, (short) 9000);	
			
			if(request.getParameter("branch") != null && !request.getParameter("branch").equals("")) {
				List<CartOrder> list = new ArrayList<CartOrder>();
				String branch = (String) request.getParameter("branch");
				for(CartOrder cartOrder : ordersForDownload) {
					if(cartOrder.getShippingInfo() != null
							&& cartOrder.getShippingInfo().getShippingInfo() != null
								&& cartOrder.getShippingInfo().getShippingInfo().getAddress1().equalsIgnoreCase(branch)) {
						list.add(cartOrder);
					}
				}
				ordersForDownload = list;
			}
		}
		
		short rowNum=3;
		
		for(CartOrder e : ordersForDownload)
		{
			StringBuilder htmlString = new StringBuilder();
//			htmlString.append("<h5>ORDER ID : ");
//			htmlString.append(orderID);
//			htmlString.append("</h5>");
			htmlString.append("Name: ");
			htmlString.append(e.getName() + "\n");
			htmlString.append("Address1: ");
			htmlString.append(e.getAddress1() + "\n");
			htmlString.append("Address2: ");
			
			if(e.getAddress2()!=null)
				htmlString.append(e.getAddress2() + "\n");
			
			htmlString.append("City: ");
			
			if(e.getCity()!=null)
				htmlString.append(e.getCity() + "\n");
			
			htmlString.append("State: ");
			if(e.getState()!=null)
				htmlString.append(e.getState() + "\n");
			
			htmlString.append("Country: ");
			
			if(e.getCountry()!=null)
				htmlString.append(e.getCountry() + "\n");
			
			htmlString.append("Zip Code: ");
			
			if(e.getZipCode()!=null)
				htmlString.append(e.getZipCode() + "\n");
			
			htmlString.append("Phone Number: ");
			
			if(e.getPhoneNumber()!=null)
				htmlString.append(e.getPhoneNumber() + "\n");
			
			htmlString.append("Email Address: ");
			htmlString.append(e.getEmailAddress() + "\n");
			htmlString.append("Comments or special instructions about this order : ");
			
			if(e.getComments()!=null)
				htmlString.append(e.getComments() + "\n");
			
			r=s.createRow((short) ++rowNum);
			c = r.createCell((short) 1);
			c.setCellValue(e.getCreatedOn().toString());
			c = r.createCell((short) 2);
			c.setCellValue(e.getId());
			c = r.createCell((short) 3);
			c.setCellValue(e.getName());
			c = r.createCell((short) 4);
			
			if(company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE)) {
				c.setCellValue(e.getAddress1());
			} else {
				c.setCellValue(htmlString.toString());
			}
			
			c = r.createCell((short) 5);
			c.setCellValue(e.getTotalPriceOkFormatted());
			c = r.createCell((short) 6);
			if(company.getName().equalsIgnoreCase(CompanyConstants.PURE_NECTAR)) {
				c.setCellValue(e.getInfo1());
			}else{
				c.setCellValue(e.getTotalShippingPrice2());	
			}
			
			
			c = r.createCell((short) 7);
			c.setCellValue(e.getStatus().toString());
			
			if(company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE)) {
				c = r.createCell((short) 8);
				c.setCellValue((e.getShippingType().getValue().equalsIgnoreCase("Pickup") ? e.getShippingInfo().getShippingInfo().getAddress1() : ""));
			}
			
			
		}
		
		wb.write(out);
		out.close();
		
		logger.debug("start download...");
		download(filePath);
		
		return SUCCESS;
	}	
	
	public String downloadHiPreOnlineStoreOrderExcel() throws Exception {
		List<CartOrder> ordersForDownload = null;
		
		Member mem = new Member();
		try{
			Long mid = Long.parseLong(request.getParameter("member_id_"));
			if(mid != 0L){
				mem = memberDelegate.find(mid);
			}
			else{
				mem = null;
			}
		}
		catch(Exception e){
			mem = null;
		}
		
		
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));
		//System.out.println("COMPANY NAME : " + company.getName());
		if (request.getParameter("filter").equals("all"))
		{
			if(mem != null){
				ordersForDownload = cartOrderDelegate.findAll(company, mem);
			}
			else{
				ordersForDownload = cartOrderDelegate.findAllByCompany(company);
			}
			
			logger.info("\n\n\nEmail list for " + company.getName() + ":   " + ordersForDownload.size());
		}	
		else
		{
			
			String str_fromDate = request.getParameter("fromDate");
			String str_toDate = request.getParameter("toDate");
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
	        if(mem != null) {
	        	ordersForDownload = cartOrderDelegate.findOrderByDateAndMember(company, fromDate, toDate, mem).getList();
	       }
	        else{
	        	ordersForDownload = cartOrderDelegate.findOrderByDate(company, fromDate, toDate).getList();
	        }
			
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
		
		r=s.createRow(1);
		r=s.createRow(2);
		c = r.createCell((short) 1);
		c.setCellValue("ID");
		c = r.createCell((short) 2);
		c.setCellValue("Order Date");
		c = r.createCell((short) 3);
		c.setCellValue("Customer");		
		c = r.createCell((short) 4);
		c.setCellValue("Address");		
		c = r.createCell((short) 5);
		c.setCellValue("Items");		
		c = r.createCell((short) 6);
		c.setCellValue("Items Price");	
		c = r.createCell((short) 7);
		c.setCellValue("Status");
		c = r.createCell((short) 8);
		c.setCellValue("Branch");
		
		s.setColumnWidth((short) 1, (short) 9000);
		s.setColumnWidth((short) 2, (short) 9000);
		s.setColumnWidth((short) 3, (short) 9000);
		s.setColumnWidth((short) 4, (short) 9000);	
		s.setColumnWidth((short) 5, (short) 9000);	
		s.setColumnWidth((short) 6, (short) 9000);	
		s.setColumnWidth((short) 7, (short) 9000);	
		s.setColumnWidth((short) 8, (short) 9000);	
		
		if(request.getParameter("branch") != null && !request.getParameter("branch").equals("")) {
			List<CartOrder> list = new ArrayList<CartOrder>();
			String branch = (String) request.getParameter("branch");
			for(CartOrder cartOrder : ordersForDownload) {
				if(cartOrder.getShippingInfo() != null
						&& cartOrder.getShippingInfo().getShippingInfo() != null
							&& cartOrder.getShippingInfo().getShippingInfo().getAddress1().equalsIgnoreCase(branch)) {
					list.add(cartOrder);
				}
			}
			ordersForDownload = list;
		}
		
		short rowNum=3;
		
		for(CartOrder e : ordersForDownload)
		{
			String items = "";
			for(CartOrderItem item : e.getItems()) {
				items = (items.equals("") ? item.getItemDetail().getName() : items + ", " +item.getItemDetail().getName());
			}
			r=s.createRow((short) ++rowNum);
			c = r.createCell((short) 1);
			c.setCellValue(e.getId());
			c = r.createCell((short) 2);
			c.setCellValue(e.getCreatedOn().toString());
			c = r.createCell((short) 3);
			c.setCellValue(e.getName());
			c = r.createCell((short) 4);
			c.setCellValue(e.getAddress1());
			c = r.createCell((short) 5);
			c.setCellValue(items);
			c = r.createCell((short) 6);
			c.setCellValue(e.getTotalPriceOkFormatted());
			c = r.createCell((short) 7);
			c.setCellValue(e.getStatus().toString());
			c = r.createCell((short) 8);
			c.setCellValue((e.getShippingType().getValue().equalsIgnoreCase("Pickup") ? e.getShippingInfo().getShippingInfo().getAddress1() : ""));
		}
		
		wb.write(out);
		out.close();
		
		logger.debug("start download...");
		download(filePath);
		
		return SUCCESS;
	}
	
	public String downloadOrderList() throws Exception {
		try{
			
		List<SavedEmail>  emailsForDownload;
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="_Order_List.xls";
		filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
		logger.debug("filepath (member) : " + filePath);	
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);	
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		HSSFRow r = null;
		HSSFCell c = null;
		HSSFDataFormat format = wb.createDataFormat();
		wb.setSheetName(0, "ORDER LIST REPORT - ");		
		
		Member member=memberDelegate.find(Long.parseLong(request.getParameter("member_Id")));
		r=s.createRow(0);
		c = r.createCell((short) 0);
		c.setCellValue("Member Name");
		c = r.createCell((short) 1);
		c.setCellValue(member.getFullName());
		r=s.createRow(1);
		c = r.createCell((short) 0);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		
		c.setCellValue("Date");
		c = r.createCell((short) 1);
		c.setCellValue(dateFormat.format(cal.getTime()));
		
		
		r=s.createRow(3);
		int ctr=0;
		for(String caption:fieldName){
			c = r.createCell((short) ctr);
			c.setCellValue(caption);
			ctr++;
			
		}
		
		
		int rowNum=3;
		int rowEnd=fieldName.length;
		for(int i=0;i<rowEnd;i++){
			
			if(fieldName[i].equalsIgnoreCase("Order Items Details"))
				s.setColumnWidth((short) i, (short) 16000);
			else
				s.setColumnWidth((short) i, (short) 5500);
		}
		orderList = cartOrderDelegate.findAll(company, member);
		
		//System.out.println("Okay hanggang dito");
		int cLoop=0;
		
		for(CartOrder cartOrder:orderList){
			 r=s.createRow((short) ++rowNum);
			int cellCount=0;
		    for(String caption:fieldName){
		    		c = r.createCell((short)cellCount++);
		    		c.setCellValue(""+getFieldValue(cartOrder,caption));
		    }
			;
			
		}
		
		//System.out.println("THE PATH LOCATION IS "+filePath);
		
		wb.write(out);
		out.close();
		
		logger.debug("start download...");
		download(filePath);
		
		}catch(Exception e){
			System.out.print(e);
		}
		
		return SUCCESS;
	}
	
	public String downloadAllOrderList() throws Exception 
	{		
		logger.info("\n\nDownload excel format by " + request.getParameter("filter"));
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdirs();
		
		filePath =  locationPath + "reports";
		
		File writeDir = new File(filePath);
		
		if(!writeDir.exists()) {
			//System.out.println("writeDir does not exist. creating dirs");
			writeDir.mkdirs();
		}
		
		fileName = "List of Orders.xls";
		filePath= writeDir + "/" + fileName;
		
		WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "List of Orders.xls");
		WritableSheet sheet;
		
		WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
		WritableCellFormat headerFormat=new WritableCellFormat(wfobj);
	    headerFormat.setBackground(Colour.AQUA);
	    headerFormat.setAlignment(Alignment.CENTRE);
	    
	    WritableCellFormat cellFormat=new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD));
	    cellFormat.setAlignment(Alignment.CENTRE);	
	    
	    //List<Member> members=memberDelegate.findAll(company).getList();	
	    String[] status = {"NEW","COMPLETED"};
	    String[] sheetName = {"New Orders","Completed Orders"};

		for(int j=0; j<status.length; j++) 
		{
			int column = 0, row = 0, count = 0, currentRow = 0, expandRow = 0;
			sheet = writableWorkbook.createSheet(sheetName[j], j);
			
			//header
			for(String header : fieldName){
				JXLUtil.addLabelCell(sheet, column++, row, header.toUpperCase(), headerFormat);			
				if(header.equalsIgnoreCase("Description"))
					JXLUtil.setColumnView(sheet, count++, 40);
				else if(header.equalsIgnoreCase("Member Name"))
					JXLUtil.setColumnView(sheet, count++, 30);
				else if(header.equalsIgnoreCase("Order Date"))
					JXLUtil.setColumnView(sheet, count++, 30);
				else
					JXLUtil.setColumnView(sheet, count++, 20);			
			}
			row++;
			
			
			if(company.getName().equalsIgnoreCase("apc")){
				Calendar calendar = Calendar.getInstance();
				calendar.set(2012 , 01, 01);
				orderList = cartOrderDelegate.findOrderByDate(company, calendar.getTime(), new Date()).getList();
			} else {
				orderList = cartOrderDelegate.findAllByCompany(company);
			}
			List<CartOrderItem> itemList;
				
			if(!(orderList==null||orderList.size()==0))	{	
				for(CartOrder cartOrder:orderList){	
					column=0;
					
					if(cartOrder.getStatus().name().equalsIgnoreCase(status[j])) {
						itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
						for(String caption:fieldName) {	
							expandRow = 0;				
							if(caption.equalsIgnoreCase("Quantity")) {
								currentRow=row;						
								for(CartOrderItem currentItem : itemList){
									JXLUtil.addNumberCell(sheet, column, currentRow++, currentItem.getQuantity().doubleValue(), cellFormat);
								}
							}
							else if(caption.equalsIgnoreCase("Description")) {
								currentRow=row;
								for(CartOrderItem currentItem : itemList){
									expandRow++;
									JXLUtil.addLabelCell(sheet, column, currentRow++, currentItem.getItemDetail().getName(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
								}
							}
							else if(caption.equalsIgnoreCase("Order Id") || caption.equalsIgnoreCase("Items Total Price") || caption.equalsIgnoreCase("Status"))
								JXLUtil.addLabelCell(sheet, column, row, getFieldValue(cartOrder,caption).toString(), cellFormat);
							else if(caption.equalsIgnoreCase("Status"))
								JXLUtil.addLabelCell(sheet, column, row, getFieldValue(cartOrder,caption).toString(), cellFormat);
							else if(caption.equalsIgnoreCase("Member Name"))
								JXLUtil.addLabelCell(sheet, column, row, getFieldValue(cartOrder,caption).toString().toUpperCase(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
							else {
								JXLUtil.addLabelCell(sheet, column, row, getFieldValue(cartOrder,caption).toString(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
							}					
							column++;
					    }
						row = row + itemList.size()+1;
					}
				}
			}
		}
		//System.out.println("THE PATH LOCATION IS "+filePath);
		
		writableWorkbook.write();
		writableWorkbook.close();				
		download(filePath);		
		
		return SUCCESS;
	}
	
	//PREVIOUS VERSION
	
	/*public String downloadAllOrderList() throws Exception {
		try{
			
		List<SavedEmail>  emailsForDownload;
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="All_Order_List.xls";
		filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
		logger.debug("filepath (member) : " + filePath);	
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);	
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		HSSFRow r = null;
		HSSFCell c = null;
		HSSFDataFormat format = wb.createDataFormat();
		wb.setSheetName(0, "ORDER LIST REPORT - ");		
		
		List<Member> members=memberDelegate.findAll(company).getList();
		
		System.out.println("THE MEMBER IS "+((members==null)?"NULL":"NOOOOOOOOOOOOOOO"));

		
		int currentRow=0;
		
		r=s.createRow(currentRow);
		int ctr=0;
		for(String caption:fieldName){
			c = r.createCell((short) ctr);
			c.setCellValue(caption);
			if(caption.equalsIgnoreCase("Description"))
				s.setColumnWidth((short) ctr, (short) 10000); //Items Total Price
			else if(caption.equalsIgnoreCase("Quantity")||caption.equalsIgnoreCase("Order Id")||caption.equalsIgnoreCase("Items Total Price"))
				s.setColumnWidth((short) ctr, (short) 2000);
			else
				s.setColumnWidth((short) ctr, (short) 5500);
			
			ctr++;
		}
		
		currentRow++;
		
		//for(Member currentMember:members){
			orderList = cartOrderDelegate.findAllByCompany(company);
			//orderList = cartOrderDelegate.findAll(company, currentMember);
			
			if(!(orderList==null||orderList.size()==0)){
	
			//currentMember=null;
			int rowEnd=fieldName.length;
			
			boolean flag=false;
			
			int cartCounter = 0;
			for(CartOrder cartOrder:orderList){
				r=s.createRow(currentRow);
				int cellCount=0;
			    for(String caption:fieldName)
			    {
			    		int iter = cellCount;
			    		HSSFCell iterCell = null;
			    					    		
			    		c = r.createCell((short)cellCount++);
			    		
			    		if(caption.equalsIgnoreCase("Member Name")){
			    			if(cartCounter>0 && cartOrder.getMember().getId() == orderList.get(cartCounter-1).getMember().getId()){
			    				continue;
			    			}
			    		}
			    		else if(caption.equalsIgnoreCase("Quantity")) 
			    		{
			    			List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
			    		
			    			for(CartOrderItem currentItem : itemList){
			    				//quantity += currentItem.getQuantity();
			    				r=s.createRow(iter++);
			    				iterCell = r.createCell((short)4);
			    				iterCell.setCellValue(currentItem.getQuantity());
			    			}			    			
			    		}
			    		else if(caption.equalsIgnoreCase("Description")) 
			    		{
			    			List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
			    			
			    			for(CartOrderItem currentItem : itemList){
			    				//description += currentItem.getItemDetail().getName();
			    				r=s.createRow(iter++);
			    				iterCell = r.createCell((short)5);
			    				iterCell.setCellValue(currentItem.getItemDetail().getName());
			    			}
			    		}else
			    			c.setCellValue(""+getFieldValue(cartOrder,caption));
			    }
			    currentRow++;
				
			}
			
			}
			
	//	}
		
		System.out.println("THE PATH LOCATION IS "+filePath);
		
		wb.write(out);
		out.close();
		
		logger.debug("start download...");
		download(filePath);
		
		}catch(Exception e){
			System.out.print(e);
		}
		
		return SUCCESS;
	}*/
	
	
	
	
	
	private Object getFieldValue(CartOrder cartOrder,String fieldName) {
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		
		CartOrder _cartOrder = cartOrderDelegate.find(cartOrder.getId());
		
		String quantity="";
		String description = "";
		
		if(_cartOrder!=null){
			HashMap data=new HashMap();
			data.put("Member Name", (cartOrder.getMember() != null) ? cartOrder.getMember().getFullName() : "");
			data.put("Order Id",cartOrder.getId());
			data.put("Order Date", cartOrder.getCreatedOn()+"");
			data.put("Items Total Price", cartOrder.getTotalPriceOkFormatted()+"");
			data.put("Status", cartOrder.getStatus()+"");
			
			List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
			String orderDetails="No Information.";
			if(itemList!=null){
				int ctr=0;
				int lastData = itemList.size();
				for(CartOrderItem currentItem : itemList){
					if(currentItem.getItemDetail().getName().indexOf("Shipping Cost")!=-1)
						continue;
					
					
					quantity += currentItem.getQuantity() + ((lastData==ctr+1)?"":"\n");
					description += currentItem.getItemDetail().getName() + ((lastData==ctr+1)?"":"\n");
					
					orderDetails+=currentItem.getItemDetail().getName();
					orderDetails+="-Qty("+currentItem.getQuantity()+")"+" ";
					orderDetails+="-Price("+numberFormatter.format(currentItem.getItemDetail().getPrice())+"), ";
					orderDetails+="-SubTotal("+numberFormatter.format(currentItem.getQuantity()*currentItem.getItemDetail().getPrice())+"), ";
					orderDetails+="-Status("+currentItem.getStatus()+"), ";
					ctr++;
					//if(itemList.size()>1&&itemList.size()<itemList.size())
					
				}
			}
			data.put("Order Items Details", orderDetails);
			data.put("Quantity", quantity);
			data.put("Description", description);
			
			if(data.get(fieldName)!=null){
				return data.get(fieldName);
			}
		}
		
		//System.out.println("walang nireturn");
		return "";
	}



	public String downloadAdeventsRegistrationExcel() throws Exception {
		List<CartOrder> ordersForDownload = null;
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));
		//System.out.println("COMPANY NAME : " + company.getName());
		if (request.getParameter("filter").equals("all"))
		{
			ordersForDownload = cartOrderDelegate.findAllByCompany(company);
			logger.info("\n\n\nEmail list for " + company.getName() + ":   " + ordersForDownload.size());
		}	
		else
		{
			String str_fromDate = request.getParameter("fromDate");
			String str_toDate = request.getParameter("toDate");
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
			ordersForDownload = cartOrderDelegate.findOrderByDate(company, fromDate, toDate).getList();
		}
	
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="BULL_RUN_ONLINE_REGISTRATION_FORM.xls";
		filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
		//filePath =  locationPath + fileName;
		logger.debug("filepath (member) : " + filePath);		
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);		
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s;
		HSSFRow r = null;
		HSSFCell c = null;
		HSSFCellStyle style;
		HSSFDataFormat format = wb.createDataFormat();
   
		String [] categories = {"3K","5K","10K","21K"};
		for(int index = 0; index < categories.length; index++) {
			wb.createSheet(categories[index]);
			s = wb.getSheetAt(index);
			wb.setSheetName(index, categories[index]);
			r=s.createRow(0);
			c = r.createCell((short) 0);
			c.setCellValue("BULL RUN 2015");
			
			r=s.createRow(1);
			r=s.createRow(2);
			c = r.createCell((short) 0);
			c.setCellValue("ORDER DATE");
			c = r.createCell((short) 1);
			c.setCellValue("CATEGORY/DISTANCE");
			c = r.createCell((short) 2);
			c.setCellValue("BIB");
			c = r.createCell((short) 3);
			c.setCellValue("SHIRT SIZE");
			c = r.createCell((short) 4);
			c.setCellValue("LAST NAME");		
			c = r.createCell((short) 5);
			c.setCellValue("FIRST NAME");		
			c = r.createCell((short) 6);
			c.setCellValue("TEAM NAME");		
			c = r.createCell((short) 7);
			c.setCellValue("SEX");	
			c = r.createCell((short) 8);
			c.setCellValue("EMAIL ADDRESS");
			c = r.createCell((short) 9);
			c.setCellValue("ADDRESS");
			c = r.createCell((short) 10);
			c.setCellValue("CONTACT NO.");
			c = r.createCell((short) 11);
			c.setCellValue("AMOUNT PAID");
			c = r.createCell((short) 12);
			c.setCellValue("DELIVERY CHARGE");
			c = r.createCell((short) 13);
			c.setCellValue("CATEGORY");
			c = r.createCell((short) 14);
			c.setCellValue("CONTACT PERSON INFO");
			
			for(int columns = 0; columns<13; columns++) {
				s.setColumnWidth((short) columns, (short) 6000);
			}
			
			short rowNum=2;
			for(CartOrder e : ordersForDownload) {
				
				final List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(e).getList();
				
				for(CartOrderItem cartOrderItem : itemList) {
					
					if(cartOrderItem.getItemDetail().getName().contains(categories[index])) {
						
						StringBuilder htmlString = new StringBuilder();
						htmlString.append("Name: ");
						htmlString.append(e.getName() + "\n");
						htmlString.append("Address1: ");
						htmlString.append(e.getAddress1() + "\n");
						htmlString.append("Address2: ");
						
						if(e.getAddress2()!=null)
							htmlString.append(e.getAddress2() + "\n");
						
						htmlString.append("City: ");
						
						if(e.getCity()!=null)
							htmlString.append(e.getCity() + "\n");
						
						htmlString.append("State: ");
						if(e.getState()!=null)
							htmlString.append(e.getState() + "\n");
						
						htmlString.append("Country: ");
						
						if(e.getCountry()!=null)
							htmlString.append(e.getCountry() + "\n");
						
						htmlString.append("Zip Code: ");
						
						if(e.getZipCode()!=null)
							htmlString.append(e.getZipCode() + "\n");
						
						htmlString.append("Phone Number: ");
						
						if(e.getPhoneNumber()!=null)
							htmlString.append(e.getPhoneNumber() + "\n");
						
						htmlString.append("Email Address: ");
						htmlString.append(e.getEmailAddress() + "\n");
						htmlString.append("Comments or special instructions about this order : ");
						
						if(e.getComments()!=null)
							htmlString.append(e.getComments() + "\n");
						
						r=s.createRow((short) ++rowNum);
						c = r.createCell((short) 0);
						c.setCellValue(e.getCreatedOn().toString());
						c = r.createCell((short) 1);
						c.setCellValue(cartOrderItem.getItemDetail().getName());
						c = r.createCell((short) 2);
						c.setCellValue(e.getId());
						c = r.createCell((short) 3);
						c.setCellValue(cartOrderItem.getItemDetailMap().get("shirtSize"));
						
						boolean hasLastName = true;
						if(cartOrderItem.getItemDetailMap().get("lastName") == null)
							hasLastName = false;
						String lastName = cartOrderItem.getItemDetailMap().get("lastName");
						String firstName = cartOrderItem.getItemDetailMap().get("firstName");
						if(!hasLastName && cartOrderItem.getItemDetailMap().get("name") != null) {
							lastName = cartOrderItem.getItemDetailMap().get("name").split(",")[0];
							firstName = cartOrderItem.getItemDetailMap().get("name").split(",")[1];
						}
						
						c = r.createCell((short) 4);
						
						c.setCellValue(lastName != null ? lastName : "");
						c = r.createCell((short) 5);
						c.setCellValue(firstName != null ? firstName : "");
						c = r.createCell((short) 6);
						c.setCellValue(cartOrderItem.getItemDetailMap().get("teamName"));
						c = r.createCell((short) 7);
						String gender = (cartOrderItem.getItemDetailMap().get("gender") != null ? cartOrderItem.getItemDetailMap().get("gender") : "");
						c.setCellValue(gender.contains("on") ? "" : gender);
						c = r.createCell((short) 8);
						c.setCellValue(cartOrderItem.getItemDetailMap().get("emailAddress"));
						c = r.createCell((short) 9);
						c.setCellValue(cartOrderItem.getItemDetailMap().get("building")+" "+cartOrderItem.getItemDetailMap().get("street")+" "+cartOrderItem.getItemDetailMap().get("city")+" "+cartOrderItem.getItemDetailMap().get("province"));
						c = r.createCell((short) 10);
						c.setCellValue(cartOrderItem.getItemDetailMap().get("contactNumber"));
						c = r.createCell((short) 11);
						c.setCellValue(cartOrderItem.getItemDetail().getPrice());
						c = r.createCell((short) 12);
						c.setCellValue(e.getTotalShippingPrice2());
						c = r.createCell((short) 13);
						c.setCellValue(cartOrderItem.getItemDetailMap().get("category"));
						c = r.createCell((short) 14);
						c.setCellValue(cartOrderItem.getItemDetailMap().get("contactPerson"));
					}//if
				}//cartorder item list
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

	public FileInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	
	
}