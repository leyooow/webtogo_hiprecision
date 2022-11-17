package com.ivant.cms.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.Colour;
import jxl.write.BorderLineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.ReferralDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.Referral;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.ReferralStatus;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.JXLUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ListReferralAction extends ActionSupport 
	implements UserAware, CompanyAware, PagingAware, ServletContextAware, ServletRequestAware {

	
	private static final Logger logger = Logger.getLogger(ListMembersSubmissionAction.class);

	ReferralDelegate referralDelegate = ReferralDelegate.getInstance();
	MemberDelegate memberDelegate =  MemberDelegate.getInstance();
	
	
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
	
	private Long referrerId;
	
	private ReferralStatus referralStatus;
	
	private String reward;
	
	
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
	
	@SuppressWarnings("deprecation")
	public String downloadDetails() throws Exception 
	{		
		logger.info("\n\nDownload excel format by " + request.getParameter("filter"));
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		filePath =  locationPath + "reports";
		
		File writeDir = new File(filePath);
		
		WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "Referral List.xls");
		WritableSheet sheet;
		
		filePath= writeDir+"/Referral List.xls";
		fileName = "Referral List.xls";
		
		WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
		WritableCellFormat headerFormat=new WritableCellFormat(wfobj);
	    headerFormat.setBackground(Colour.AQUA);
	    headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
	    
	    WritableCellFormat cellFormat=new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD));
	    cellFormat.setAlignment(Alignment.CENTRE);	
	    
	    WritableCellFormat contentFormat=new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD));
	    contentFormat.setAlignment(Alignment.LEFT);	
	    contentFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		
		String captions[]={"Date Created","Vacation Specialist", "Referrer", "Client Name", "Contact Number", "Email", "Reward", "Status", "Action Date"};

		ReferralStatus status[] = ReferralStatus.REFERRAL_STATUSES;
		
		if(referralStatus!=null){
			status=new ReferralStatus[]{referralStatus};
		}
		
		
		for(int j=0; j<status.length; j++) 
		{
			int col = 0, row = 0, count = 0, currentRow = 0, expandRow = 0;
			sheet = writableWorkbook.createSheet(status[j].getValue(), j);
				
			//header
			for(String header : captions){
				JXLUtil.addLabelCell(sheet, col++, row, header.toUpperCase(), headerFormat);			
				JXLUtil.setColumnView(sheet, count++, 30);			
			}
			
			if(status[j].equals(ReferralStatus.REQUESTED) || status[j].equals(ReferralStatus.REDEEMED)){
				JXLUtil.addLabelCell(sheet, col++, row, "REQUEST ID", headerFormat);			
				JXLUtil.setColumnView(sheet, count++, 30);
			}
			row++;
			
			//SOON TO BE BY COMPANY
			Member referrer = null;
			if(referrerId!=null)
				referrer = memberDelegate.find(referrerId);
			List<Referral> referralsByStatus = referralDelegate.findByStatusAndReward(company,status[j],referrer,reward);
			
			
			for(Referral referral:referralsByStatus){
				col = 0;
				JXLUtil.addLabelCell(sheet, col++, row, referral.getCreatedOn().toString(), contentFormat);
				JXLUtil.addLabelCell(sheet, col++, row, ((referral.getReferredBy()!=null)?referral.getReferredBy().getInfo1():""), contentFormat);
				JXLUtil.addLabelCell(sheet, col++, row, ((referral.getReferredBy()!=null)?referral.getReferredBy().getFullName():""), contentFormat);
				JXLUtil.addLabelCell(sheet, col++, row, referral.getFullname(), contentFormat);
				JXLUtil.addLabelCell(sheet, col++, row, referral.getContactNumber(), contentFormat);
				JXLUtil.addLabelCell(sheet, col++, row, referral.getEmail(), contentFormat);
				JXLUtil.addLabelCell(sheet, col++, row, referral.getReward(), contentFormat);
				JXLUtil.addLabelCell(sheet, col++, row, referral.getStatus().getValue(), contentFormat);
				JXLUtil.addLabelCell(sheet, col++, row, referral.getDateApproved()+"", contentFormat);
				
				if(status[j].equals(ReferralStatus.REQUESTED)|| status[j].equals(ReferralStatus.REDEEMED)){
					JXLUtil.addLabelCell(sheet, col++, row, referral.getRequestId()+"", contentFormat);					
				}
				row++;
			}
			
				
			}
		//}
		//System.out.println("THE PATH LOCATION IS "+filePath);
		
		writableWorkbook.write();
		writableWorkbook.close();				
		download(filePath);		
		
		return SUCCESS;
	}
	
	
	
	
	
	
	public String downloadDetails1() throws Exception 
	{		
		logger.info("\n\nDownload excel format by " + request.getParameter("filter"));
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
		new File(locationPath).mkdir();
		filePath =  locationPath + "reports";
		
		File writeDir = new File(filePath);
		
		WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "ReferralList.xls");
		WritableSheet sheet;
		
		filePath= writeDir+"/ReferralList.xls";
		fileName = "ReferralList.xls";
		
		WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
		WritableCellFormat headerFormat=new WritableCellFormat(wfobj);
	    headerFormat.setBackground(Colour.AQUA);
	    
	    WritableCellFormat cellFormat=new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD));
	    cellFormat.setAlignment(Alignment.CENTRE);	
		
		String captions[]={"Date Created", "Referrer", "Cleint Name", "Contact Number", "Email", "Rewards", "Status" };
		//		"Company Address", "Position", "Status", "Action By", "Date"};
		
		String[] status = {"Approved", "Rejected", "Cancelled"};
		
		int sheetCounter = 0;
		
		
			
			int col = 0, row = 0, count = 0, currentRow = 0, expandRow = 0;
			sheet = writableWorkbook.createSheet("1111111111111", sheetCounter);
			
			for(String header : captions){
				JXLUtil.addLabelCell(sheet, col++, row, header.toUpperCase(), headerFormat);			
				JXLUtil.setColumnView(sheet, count++, 30);			
			}
			
			for(ReferralStatus refStatus : ReferralStatus.REFERRAL_STATUSES){	
		List<Referral> referralList=referralDelegate.findAll();
			
			for(Referral referral: referralList)
			{			
				
				col = 0;
				
						JXLUtil.addLabelCell(sheet, col++, row,"yohan", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
						JXLUtil.addLabelCell(sheet, col++, row,"yohan 1", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
						JXLUtil.addLabelCell(sheet, col++, row, "yohan 3", cellFormat);
					
					row++;
			}
		}
		
		//System.out.println("THE PATH LOCATION IS "+filePath);
		
		writableWorkbook.write();
		writableWorkbook.close();				
		download(filePath);		
		
		return SUCCESS;
	}
	
	public void download(String filePath) throws Exception {
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
		this.totalItems = referralDelegate.findAll().size();
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



	public void setReferrerId(Long referrerId) {
		this.referrerId = referrerId;
	}



	public Long getReferrerId() {
		return referrerId;
	}



	public void setReferralStatus(ReferralStatus referralStatus) {
		this.referralStatus = referralStatus;
	}



	public ReferralStatus getReferralStatus() {
		return referralStatus;
	}



	public void setReward(String reward) {
		this.reward = reward;
	}



	public String getReward() {
		return reward;
	}


	
	
}
