package com.ivant.cms.action.admin;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.User;
import com.ivant.cms.helper.ListMemberSubmissionHelper;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.JXLUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.util.ServletContextAware;
import org.apache.struts2.interceptor.ServletRequestAware;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;

/*
 * @
 * @
 */
public class ListMemberFileItems extends ActionSupport 
				implements UserAware, CompanyAware, PagingAware, ServletContextAware, ServletRequestAware {
	
	private static final long serialVersionUID = 4050469397961099239L;
	private static final Logger logger = Logger.getLogger(ListMembersSubmissionAction.class);
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private MemberFileDelegate memberFileDelegate=MemberFileDelegate.getInstance();
	private MemberFileItemDelegate memberFileItemsDelegate=MemberFileItemDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate=CategoryItemDelegate.getInstance();
	private ListMemberSubmissionHelper listMemberSubmissionHelper;
	private GroupDelegate groupDelegate=GroupDelegate.getInstance();
	
	
	
	private Company company;
	private User user;

	private int page;
	private int totalItems;
	private int itemsPerPage;
	private int filterDays;
	
	private String filePath;
	private String fileName;
	private FileInputStream fInStream;
	private long contentLength;
	private String itemSalesToBeDownloaded;

	private ServletContext servletContext;
	private HttpServletRequest request;	
	
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

	public String downloadDetails() throws Exception 
	{		
		logger.info("\n\nDownload excel format by " + request.getParameter("filter"));
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		filePath =  locationPath + "reports";
		
		File writeDir = new File(filePath);
		
		WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "Members File Items List.xls");
		WritableSheet sheet;
		
		filePath= writeDir+"/Members File Items List.xls";
		fileName = "Members File Items List.xls";
		
		WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
		WritableCellFormat headerFormat=new WritableCellFormat(wfobj);
	    headerFormat.setBackground(Colour.AQUA);
	    
	    WritableCellFormat cellFormat=new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD));
	    cellFormat.setAlignment(Alignment.CENTRE);	
		
		String captions[]={"Member Name", "Email", "Value", "Invoice Number", "Description", "Points", "Distributor", "Company Name",
				"Company Address", "Position", "Status", "Action By", "Date"};
		
		String[] status = {"Approved", "Rejected", "Cancelled"};
		
		for(int j=0; j<status.length; j++) 
		{
			int col = 0, row = 0, count = 0, currentRow = 0, expandRow = 0;
			sheet = writableWorkbook.createSheet(status[j], j);
				
			//header
			for(String header : captions){
				JXLUtil.addLabelCell(sheet, col++, row, header.toUpperCase(), headerFormat);			
				JXLUtil.setColumnView(sheet, count++, 30);			
			}
			row++;
			
			//SOON TO BE BY COMPANY
			//List<MemberFileItems> memberFileItem_1=memberFileItemsDelegate.findAll(company);
			List<MemberFileItems> memberFileItem_1 = null;
			if(company.getName().equals("apc")){
				Calendar cal = Calendar.getInstance();
				cal.set(2012, 01, 01);
				Date fromDate = cal.getTime();
				memberFileItem_1=memberFileItemsDelegate.findByDate(company, fromDate, new Date()).getList();
			} else {
				memberFileItem_1=memberFileItemsDelegate.findByCompany(company);
			}
			
			for(MemberFileItems memberFileItem: memberFileItem_1)
			{				
				if(memberFileItem.getMemberFile()!=null&&memberFileItem.getMemberFile().getMember()==null)
					continue;
				
				MemberFile memberFile=memberFileDelegate.find(memberFileItem.getMemberFile().getId());
				Member member=memberDelegate.find(memberFile.getMember().getId());
				
				if(memberFile.getStatus()!=null && memberFile.getStatus().equalsIgnoreCase(status[j]))
				{
					col = 0;
				
					if(member != null){
						JXLUtil.addLabelCell(sheet, col++, row, (member.getFullName()==null)?"":member.getFullName().toUpperCase(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
						JXLUtil.addLabelCell(sheet, col++, row, (member.getEmail()==null)?"":member.getEmail(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
						JXLUtil.addLabelCell(sheet, col++, row, (member.getValue()==null)?"0":member.getValue(), cellFormat);
					}
					
					if(memberFile!=null){
						JXLUtil.addLabelCell(sheet, col++, row, (memberFileItem.getInvoiceNumber()==null)?"":memberFileItem.getInvoiceNumber(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
						
						String desc = "";
						StringBuffer formattedDesc = new StringBuffer();				
						
						/*
						*/
						if(memberFileItem.getDescription().indexOf("<table width=100% id=toolTipTable>")!=-1)
						{							
							desc = memberFileItem.getDescription();
							
							int itemNum = 1, begin, end;
							String subStr;
																	
							if(desc.indexOf("<tr class=left>") != -1) {									
								begin = desc.indexOf("<tr class=left>");
								end = desc.indexOf("</tr>");
							} 
							else {
								begin = desc.indexOf("<tr align=left>");
								end = desc.indexOf("</tr>");
							}
							
							
							int y=1;
							String total = desc.substring(desc.lastIndexOf("<td align=center>")+17, desc.lastIndexOf("</td>"));
							while(begin > 0) {
																
								subStr = desc.substring(begin, end);
								
								formattedDesc.append("Item "+itemNum+":  ");
								formattedDesc.append(subStr.substring(subStr.indexOf("<td>")+4, subStr.indexOf("</td>")));
								subStr = subStr.replaceFirst("<td>", "");
								subStr = subStr.replaceFirst("</td>", "");
								
								formattedDesc.append("\nPrice:  ");
								
								if(subStr.indexOf("<td class=center>") != -1) {									
									formattedDesc.append(subStr.substring(subStr.indexOf("<td class=center>")+17, subStr.indexOf("</td>")));
									subStr = subStr.replaceFirst("<td class=center>", "");
								}else {
									formattedDesc.append(subStr.substring(subStr.indexOf("<td align=center>")+17, subStr.indexOf("</td>")));
									subStr = subStr.replaceFirst("<td align=center>", "");
								}
								subStr = subStr.replaceFirst("</td>", "");
								
								formattedDesc.append("\nQty:  ");
								if(subStr.indexOf("<td class=center>") != -1) {
									formattedDesc.append(subStr.substring(subStr.indexOf("<td class=center>")+17, subStr.indexOf("</td>")));
									subStr = subStr.replaceFirst("<td class=center>", "");
								}else {
									formattedDesc.append(subStr.substring(subStr.indexOf("<td align=center>")+17, subStr.indexOf("</td>")));
									subStr = subStr.replaceFirst("<td align=center>", "");
								}
								subStr = subStr.replaceFirst("</td>", "");
								
								formattedDesc.append("\nPts:  ");
								if(subStr.indexOf("<td class=center>") != -1) {
									formattedDesc.append(subStr.substring(subStr.indexOf("<td class=center>")+17, subStr.indexOf("</td>")));
									subStr = subStr.replaceFirst("<td class=center>", "");
								} else {
									formattedDesc.append(subStr.substring(subStr.indexOf("<td align=center>")+17, subStr.indexOf("</td>")));
									subStr = subStr.replaceFirst("<td align=center>", "");
								}
								subStr = subStr.replaceFirst("</td>", "");
								
								formattedDesc.append("\n\n");
								
								if(subStr.indexOf("<tr class=left>") != -1) {									
									desc = desc.replaceFirst("<tr class=left>", "");
									desc = desc.replaceFirst("</tr>", "");
								} 
								else {
									desc = desc.replaceFirst("<tr align=left>", "");
									desc = desc.replaceFirst("</tr>", "");
								}
								
								if(subStr.indexOf("<td class=center>") != -1) 								
									begin = desc.indexOf("<tr class=left>");
								else 
									begin = desc.indexOf("<tr align=left>");
																
								end = desc.indexOf("</tr>");
								itemNum++;
							}
							
							if(desc.indexOf("<td class=center>") != -1) 		
								subStr = desc.substring(desc.lastIndexOf("<td class=center>")+17, desc.lastIndexOf("</td>"));
							else {
								subStr = desc.substring(desc.lastIndexOf("<td align=center>")+17, desc.lastIndexOf("</td>"));
								subStr = desc.substring(desc.lastIndexOf("<strong>")+8, desc.lastIndexOf("</strong>"));
							}							
							formattedDesc.append("Total Points:  "+subStr);
						}						
						JXLUtil.addLabelCell(sheet, col++, row, formattedDesc.toString(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
												
						//POINTS
						JXLUtil.addLabelCell(sheet, col++, row, (memberFileItem.getMemberFile().getMember()==null) ? "0.0" : memberFileItem.getMemberFile().getMember().getValue(), cellFormat);
						JXLUtil.addLabelCell(sheet, col++, row, (memberFileItem.getDistributor()==null)?"":memberFileItem.getDistributor(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					}//memberFile
					
					
					if(member != null)
					{
						JXLUtil.addLabelCell(sheet, col++, row, (member.getReg_companyName()==null)? "" : member.getReg_companyName(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
						JXLUtil.addLabelCell(sheet, col++, row, (member.getReg_companyAddress()==null)? "" : member.getReg_companyAddress(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
						JXLUtil.addLabelCell(sheet, col++, row, (member.getReg_companyPosition()==null)? "" : member.getReg_companyPosition(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					}
					
					if(memberFile.getStatus()!=null)
						JXLUtil.addLabelCell(sheet, col++, row, (memberFile.getStatus()==null)? "" : memberFile.getStatus(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					
					if(member != null)
					{
						if(memberFile.getApprovedBy()!=null){
							UserDelegate userDelegate=UserDelegate.getInstance();
							User user=userDelegate.find(memberFile.getApprovedBy());
							if (user != null) {
								JXLUtil.addLabelCell(sheet, col++, row, user.getFullName(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
							} else {
								JXLUtil.addLabelCell(sheet, col++, row, "", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
							}
						}
						
						if(memberFile.getApprovedDate()!=null)
							JXLUtil.addLabelCell(sheet, col++, row, memberFile.getApprovedDate().toString(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					}
					row++;
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
	
/*	public String downloadDetails() throws Exception {
		try{
		List<SavedEmail>  emailsForDownload;
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));

		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="Member_File_Items_List.xls";
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
		
		
		r=s.createRow(0);
		
		String captions[]={
				"Member Name","Email", "Value",
				"Invoice Number",	"Description",	"Points",	"Distributor",
				"Company Name","Company Address","Position","Status","Action By","Date"
				};
		int ctr=0;
		for(String caption:captions){
			c = r.createCell((short) ctr);
			c.setCellValue(caption);
			ctr++;
			
		}
		int rowEnd=captions.length;
		for(int i=0;i<rowEnd;i++){
				s.setColumnWidth((short) i, (short) 7000);
		}
		short rowNum=1;
		
		
		//SOON TO BE BY COMPANY
		//List<MemberFileItems> memberFileItem_1=memberFileItemsDelegate.findAll(company);
		List<MemberFileItems> memberFileItem_1=memberFileItemsDelegate.findByCompany(company);
		
		for(MemberFileItems memberFileItem: memberFileItem_1)
		{
			ctr=0;
			r=s.createRow((short) rowNum);
			
			if(memberFileItem.getMemberFile()!=null&&memberFileItem.getMemberFile().getMember()==null)
				continue;
			MemberFile memberFile=memberFileDelegate.find(memberFileItem.getMemberFile().getId());
			Member member=memberDelegate.find(memberFile.getMember().getId());
			
			if(member != null){
			c = r.createCell((short) 0);
			c.setCellValue((member.getFullName()==null)?"":member.getFullName());
			
			c = r.createCell((short) 1);
			c.setCellValue((member.getEmail()==null)?"":member.getEmail());
			
			c = r.createCell((short) 2);
			c.setCellValue((member.getValue()==null)?"0":member.getValue());
			}
			
			if(memberFile!=null){
			c = r.createCell((short) 3);
			c.setCellValue((memberFileItem.getInvoiceNumber()==null)?"":memberFileItem.getInvoiceNumber());
			
			if(memberFileItem.getDescription().indexOf("<table width=100% id=toolTipTable>")!=-1){
				String desc=memberFileItem.getDescription().replace("<table width=100% id=toolTipTable>", "");
				 desc=desc.replace("<table>", "");
				 desc=desc.replace("</tr>", "");
				 desc=desc.replace("</td>", "");
				 String trs[]=desc.split("<tr>");
				 String realDesc="\tItem\tPrice\tPts\tQty\tSubPrice\tSubPts";
				 int ctr1=0;
				 
				 breakHere:
				 for(String tds:trs){					 
					 String []tdContents=tds.split("<td>");
					 
					 for(String tdContent:tdContents){
						 if(tdContent.indexOf("<b>Total</b")!=-1)
							 break breakHere;
						 realDesc+=tdContent+"\t";
					 }
					 realDesc+="\n";
					 ctr1++;
				 }
				 
			c = r.createCell((short) 4);
			c.setCellValue((realDesc==null)?"":realDesc);
			}
			
			c = r.createCell((short) 5);
			c.setCellValue((memberFileItem.getRemarks()==null)?"":memberFileItem.getRemarks());
			
			
			c = r.createCell((short) 6);
			c.setCellValue((memberFileItem.getDistributor()==null)?"":memberFileItem.getDistributor());
			
			
				if(memberFile.getApprovedBy()!=null){
				UserDelegate userDelegate=UserDelegate.getInstance();
				User user=userDelegate.find(memberFile.getApprovedBy());
				c = r.createCell((short) 11);
				c.setCellValue(user.getFullName());
				}
				
				if(memberFile.getApprovedDate()!=null){
				c = r.createCell((short) 12);
					c.setCellValue(""+memberFile.getApprovedDate());
				}
				
			
			}//memberFile
			if(member != null){
			c = r.createCell((short) 7);
			c.setCellValue((member.getReg_companyName()==null)?"":member.getReg_companyName());
			
			c = r.createCell((short) 8);
			c.setCellValue((member.getReg_companyAddress()==null)?"":member.getReg_companyAddress());
			
			c = r.createCell((short) 9);
			c.setCellValue((member.getReg_companyPosition()==null)?"":member.getReg_companyPosition());
			}
			
			if(memberFile.getStatus()!=null){
			c = r.createCell((short) 10);
			c.setCellValue((memberFile.getStatus()==null)?"":memberFile.getStatus());
			}
			
			if(memberFileItem.getCreatedOn()!=null){
			c = r.createCell((short) 11);
			c.setCellValue(memberFile.getCreatedOn());
			}
			
			
			
			
			rowNum++;
			
		}
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
	/********************************************************************/
	
	public String downloadMemberFileReceipts() throws Exception {
		
		try{
		List<SavedEmail>  emailsForDownload;
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));
		
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		fileName="Member_File_Receipt_List.xls";
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
		Member member=memberDelegate.find(Long.parseLong(request.getParameter("member_id")));

		r=s.createRow(0);
		c = r.createCell((short) 0);
		c.setCellValue("Member Name");
		c = r.createCell((short) 1);
		c.setCellValue(member.getFullName());
		
		r=s.createRow(1);
		c = r.createCell((short) 0);
		c.setCellValue("Total remaining Points");
		c = r.createCell((short) 1);
		c.setCellValue(member.getValue());
		
		r=s.createRow(5);
		String captions[]=request.getParameterValues("fieldName");
		int ctr=0;
		for(String caption:captions){
			c = r.createCell((short) ctr);
			c.setCellValue(caption);
			ctr++;
			
		}
		int rowEnd=captions.length;
		for(int i=0;i<rowEnd;i++){
				s.setColumnWidth((short) i, (short) 7000);
		}
		
		//Title	Invoice	Points	Created On	Action By	Approved Date	Status															

		
		List<MemberFileItems> memberFileItems=showAllReceipts(member);

		
		short rowNum=6;
		for(MemberFileItems memberFileItem: memberFileItems)
		{
			r=s.createRow((short) rowNum);
			int ct=0;
			c = r.createCell((short) ct++);
			c.setCellValue(memberFileItem.getFilename());
			
			c = r.createCell((short) ct++);
			c.setCellValue(memberFileItem.getInvoiceNumber());
			
			c = r.createCell((short) ct++);
			c.setCellValue(memberFileItem.getRemarks());
			

			c = r.createCell((short) ct++);
			c.setCellValue(memberFileItem.getValue());
			
			c = r.createCell((short) ct++);
			c.setCellValue(memberFileItem.getMemberFile().getStatus());
			
			
			c = r.createCell((short) ct++);
			c.setCellValue(""+memberFileItem.getCreatedOn());
			
			//receiptList.memberFile.approvedBy
			c = r.createCell((short) ct++);
			
			if(memberFileItem.getMemberFile().getApprovedBy() != null){
				UserDelegate userDelegate=UserDelegate.getInstance();
				User user=userDelegate.find(memberFileItem.getMemberFile().getApprovedBy());
				c.setCellValue(user.getFullName());
			}
			
			
			c = r.createCell((short) ct++);
			
			c.setCellValue(""+memberFileItem.getMemberFile().getApprovedDate());
			

			rowNum++;
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
	
	private Group group;
	
	public String downloadSalesReportBasedFromUploadedReceipts() throws Exception 
	{
		logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));	
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		filePath =  locationPath + "reports";
		
		File writeDir = new File(filePath);
		
		WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "Sales Report.xls");
		WritableSheet sheet;
		
		filePath= writeDir+"/Sales Report.xls";
		fileName = "Sales Report.xls";
		
		WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
		WritableCellFormat headerFormat=new WritableCellFormat(wfobj);
	    headerFormat.setBackground(Colour.AQUA);
	    
	    WritableCellFormat cellFormat=new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD));
	    cellFormat.setAlignment(Alignment.CENTRE);	
		
		listMemberSubmissionHelper=new ListMemberSubmissionHelper();
		
		String groupId=(company.getName().equalsIgnoreCase("APC")?"212":"221");//westernDigital 221
		group=groupDelegate.find(company, Long.parseLong(""+groupId));
		List<CategoryItem> listOfItems = categoryItemDelegate.findAllByGroup(company, group).getList();			
			
		String[] status = {"Approved", "Rejected", "Cancelled"};
		String captions[]=request.getParameterValues("fieldName");
		
		short rowNum=0;
		for(int sheetCount=0; sheetCount<status.length; sheetCount++) 
		{
			int column = 0, row = 0, count = 0, currentRow = 0, expandRow = 0;			
			
			//create sheet
			sheet = writableWorkbook.createSheet(status[sheetCount], sheetCount);
			
			//header	
			if(captions != null) {
				for(String header:captions){
					JXLUtil.addLabelCell(sheet, column++, row, header.toUpperCase(), headerFormat);			
					if(header.equalsIgnoreCase("Item Name") || header.equalsIgnoreCase("Company Name"))
						JXLUtil.setColumnView(sheet, count++, 50);
					else
						JXLUtil.setColumnView(sheet, count++, 25);						
				}
			}
			row++;	
				
			for(CategoryItem loi : listOfItems)
			{
				List<MemberFileItems> mfi=memberFileItemDelegate.findByCompany(company);					    	 
				if(mfi!=null)
				{						
					for(MemberFileItems _mfi:mfi)
					{							 			
						if(_mfi.getMemberFile().getMember()==null)
							continue;
						 			
						if(_mfi.getDescription()!=null&&_mfi.getDescription().indexOf(loi.getName())!=-1)
						{
							if(_mfi.getMemberFile()!=null &&_mfi.getMemberFile().getStatus()!=null && (_mfi.getMemberFile().getStatus().equalsIgnoreCase(status[sheetCount]))) {
						 	
							 	column = 0;
								for(String caption:captions)
								{
								 	if(caption.equalsIgnoreCase("Item Name")){
								 		if(loi.getName()!=null)
								 			JXLUtil.addLabelCell(sheet, column++, row, loi.getName(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
								 	}
								 	else if(caption.equalsIgnoreCase("Invoice Number")){
								 		if(_mfi.getInvoiceNumber()!=null)
								 			JXLUtil.addLabelCell(sheet, column++, row, _mfi.getInvoiceNumber(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
									}
								 	else if(caption.equalsIgnoreCase("Status")){
								 		JXLUtil.addLabelCell(sheet, column++, row, _mfi.getMemberFile().getStatus(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
								 	}
								 	else if(caption.equalsIgnoreCase("Date Created")){
								 		if(_mfi.getCreatedOn()!=null&&_mfi.getCreatedOn().toLocaleString()!=null)
								 			JXLUtil.addLabelCell(sheet, column++, row, _mfi.getCreatedOn().toLocaleString(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
								 	}
								 	else if(caption.equalsIgnoreCase("Approved Date")){
								 		if(_mfi.getMemberFile()!=null&&_mfi.getMemberFile().getApprovedDate()!=null)
								 			JXLUtil.addLabelCell(sheet, column++, row, _mfi.getMemberFile().getApprovedDate().toLocaleString(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
								 	}
								 	else if(caption.equalsIgnoreCase("Points")){
								 		if(_mfi.getMemberFile()!=null&&_mfi.getMemberFile().getMember().getValue()==null||_mfi.getMemberFile().getMember().getValue().length()==0)
								 			JXLUtil.addLabelCell(sheet, column++, row, "0.00", cellFormat);
								 		else
								 			JXLUtil.addLabelCell(sheet, column++, row, _mfi.getMemberFile().getMember().getValue(), cellFormat);
								 	}
								 	else if(caption.equalsIgnoreCase("Member Name")){
								 		if(_mfi.getMemberFile()!=null&&_mfi.getMemberFile().getMember()!=null)
								 			JXLUtil.addLabelCell(sheet, column++, row, _mfi.getMemberFile().getMember().getFullName().toUpperCase(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
								 	}
								 	else if(caption.equalsIgnoreCase("Company Name")){
								 		if(_mfi.getMemberFile()!=null&&_mfi.getMemberFile().getMember()!=null)
								 			JXLUtil.addLabelCell(sheet, column++, row, _mfi.getMemberFile().getMember().getReg_companyName().toUpperCase(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
								 	}
								}
								row++;
							}								 			
						} 		
					}
				}
			}				
		}//first loop	
			
		//System.out.println("THE PATH LOCATION IS "+filePath);
		
		writableWorkbook.write();
		writableWorkbook.close();				
		download(filePath);		
		
		return SUCCESS;
	}
	
	//PREVIOUS VERSION

/*public String downloadSalesReportBasedFromUploadedReceipts() throws Exception {
	
	try{
	List<SavedEmail>  emailsForDownload;
	logger.info("\n\nDownload in excel format by " + request.getParameter("filter"));

	String basePath = servletContext.getRealPath("");
	String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
	new File(locationPath).mkdir();
	fileName="Sales Report.xls";
	filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
	logger.debug("filepath (member) : " + filePath);	
	File file = new File(filePath);
	FileOutputStream out = new FileOutputStream(file);	
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet[] sheet = new HSSFSheet[3];//wb.createSheet();
	HSSFRow row = null;
	HSSFCell column = null;
	HSSFDataFormat format = wb.createDataFormat();
	
	wb.setSheetName(0, "UPLOADED RECEIPTS REPORT - ");		

	
	
	listMemberSubmissionHelper=new ListMemberSubmissionHelper();
	short rowNum=0;
	listMemberSubmissionHelper.setCompany(company);
	
	
	System.out.println("THE COMPANY IS------>"+company.getName());
	String groupId=(company.getName().equalsIgnoreCase("APC")?"212":"221");//westernDigital 221
	group=groupDelegate.find(company, Long.parseLong(""+groupId));
	List<CategoryItem> listOfItems= categoryItemDelegate.findAllByGroup(company, group).getList();

	
	List<CategoryItem> listOfItems= categoryItemDelegate.findAllByGroup(company, group).getList();
	
	
	for(int sheetNum=0; sheetNum<sheet.length; sheetNum++) 
	{
		row=sheet[sheetNum].createRow(0);
		String captions[]=request.getParameterValues("fieldName");
		int ctr=0;
		if(captions != null) {
			for(String caption:captions){
				column = row.createCell((short) ctr);
				column.setCellValue(caption);
				ctr++;				
			}
		}
		
		//System.out.println("END MEMO");
		int rowEnd=captions.length;
		for(int i=0;i<rowEnd;i++){
			if(i==0||i==rowEnd-1)
				sheet[sheetNum].setColumnWidth((short) i, (short) 12000);
			else	
				sheet[sheetNum].setColumnWidth((short) i, (short) 7000);					
		}
		
		for(CategoryItem loi : listOfItems)
		{
			    	 List<MemberFileItems> mfi=memberFileItemDelegate.findByCompany(company);
			    	 
					 if(mfi!=null){
					 	//	System.out.println("-->mfi not null-->"+mfi.size()+"===loi=="+loi.getId());
						 System.out.println("MEMO 1");
					 		for(MemberFileItems _mfi:mfi){
					 			
					 			if(_mfi.getMemberFile().getMember()==null)
					 				continue;
					 			
					 			if(_mfi.getMemberFile()!=null &&_mfi.getMemberFile().getStatus()!=null && (_mfi.getMemberFile().getStatus().equalsIgnoreCase("Rejected")))
					 				continue;
					 				System.out.println("-->mfiID-->"+_mfi.getId());
					 			//System.out.println("-->mfi not null-->"+_mfi.getDescription());
					 			if(_mfi.getDescription()!=null&&_mfi.getDescription().indexOf(loi.getName())!=-1){

					 				row=sheet.createRow((short) ++rowNum);
					 				int cellCount=0;
					 				for(String caption:captions){
					 					  column = row.createCell((short) cellCount++);
					 					  //c.setCellValue(listMemberSubmissionHelper.getFieldValue(caption));
					 					  
					 					  String value="";
					 					 if(caption.equalsIgnoreCase("Item Name")){
					 						 column = row.createCell((short)0);
					 						 if(loi.getName()!=null)
					 							 column.setCellValue(loi.getName());
					 					  }else if(caption.equalsIgnoreCase("Invoice Number")){
					 						 column = row.createCell((short)1);
					 						if(_mfi.getInvoiceNumber()!=null)
						 					 column.setCellValue(_mfi.getInvoiceNumber());
					 					  }else if(caption.equalsIgnoreCase("Status")){
					 						 System.out.println("MEMO 2 "+_mfi.getMemberFile().getStatus());
					 						 column = row.createCell((short)2);
					 						if(_mfi.getMemberFile()!=null&&_mfi.getMemberFile().getStatus()!=null)
					 							column.setCellValue(_mfi.getMemberFile().getStatus());
					 					  }
					 					 else if(caption.equalsIgnoreCase("Date Created")){
					 						 column = row.createCell((short)3);
					 						 if(_mfi.getCreatedOn()!=null&&_mfi.getCreatedOn().toLocaleString()!=null)
					 							column.setCellValue(_mfi.getCreatedOn().toLocaleString());
					 					  }
					 					else if(caption.equalsIgnoreCase("Approved Date")){
					 						 column = row.createCell((short)4);
					 						if(_mfi.getMemberFile()!=null&&_mfi.getMemberFile().getApprovedDate()!=null)
						 					 column.setCellValue(_mfi.getMemberFile().getApprovedDate().toLocaleString());
					 					  }
					 					else if(caption.equalsIgnoreCase("Points")){
					 						 column = row.createCell((short)5);
					 						 if(_mfi.getMemberFile()!=null&&_mfi.getMemberFile().getMember().getValue()==null||_mfi.getMemberFile().getMember().getValue().length()==0)
					 							 column.setCellValue("0.00");
					 						 else
					 							column.setCellValue(_mfi.getMemberFile().getMember().getValue());
					 					 }
					 					else if(caption.equalsIgnoreCase("Member Name")){
					 						 column = row.createCell((short)6);
					 						 if(_mfi.getMemberFile()!=null&&_mfi.getMemberFile().getMember()!=null)
						 					 column.setCellValue(_mfi.getMemberFile().getMember().getFullName());
					 					  }
					 					else if(caption.equalsIgnoreCase("Company Name")){
					 						 column = row.createCell((short)7);
					 						 if(_mfi.getMemberFile()!=null&&_mfi.getMemberFile().getMember()!=null)
						 					 column.setCellValue(_mfi.getMemberFile().getMember().getReg_companyName());
					 					  }
					 				  }
					 			}
					 			
					 			
					 			
					 	}
					}
		    
		}
	

	}//first loop	
	
	String i=itemSalesToBeDownloaded;
	
	
	
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
	
	
	private static final MemberFileItemDelegate memberFileItemDelegate = MemberFileItemDelegate.getInstance();
	
	private List<MemberFileItems> showAllReceipts(Member member) {
		List<MemberFileItems> receiptList;
		receiptList=new ArrayList<MemberFileItems>();
		List<MemberFile> memberFile=null;
		MemberFileDelegate memberFileDelegate=MemberFileDelegate.getInstance();
		//System.out.println(((member!=null))+">>>>>>>>>>>>>>>>>>>>>MEMBER IS ");
				memberFile=memberFileDelegate.findAll(member);
			for(MemberFile memFile:memberFile){
				MemberFileItems fileInfo=memberFileItemDelegate.findMemberFileItem(company , memFile.getId());
				if(fileInfo!=null)
					receiptList.add(fileInfo);
				}
			if(receiptList!=null){
				//System.out.println("THE TOTAL NUMBER OF ITEMS FOUND IS/ARE "+receiptList.size());
			}
			Collections.reverse(receiptList);
			return receiptList;
	}

	public void download(String filePath) throws Exception {
		//System.out.println("END MEMO");
		File file = new File(filePath);
		if(!file.exists()) {
			logger.fatal("Unabled to locate file: " + filePath);
		}
		//System.out.println("END MEMO");
		fInStream = new FileInputStream(file);
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



	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}


	public void setItemSalesToBeDownloaded(String itemSalesToBeDownloaded) {
		this.itemSalesToBeDownloaded = itemSalesToBeDownloaded;
	}



	public String getItemSalesToBeDownloaded() {
		return itemSalesToBeDownloaded;
	}
	
	
	
}