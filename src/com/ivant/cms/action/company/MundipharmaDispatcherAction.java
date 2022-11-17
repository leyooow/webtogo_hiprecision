package com.ivant.cms.action.company;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import net.sf.jasperreports.engine.export.oasis.CellStyle;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.hibernate.criterion.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gdata.util.common.util.Base64;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.action.PageDispatcherAction;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.interfaces.PageDispatcherAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.Encryption;
import com.ivant.utils.FileUtil;
import com.ivant.utils.HTMLTagStripper;
import com.ivant.utils.PagingUtil;
import com.ivant.utils.PasswordEncryptor;
import com.ivant.utils.StringUtil;
import com.opensymphony.xwork2.Action;

public class MundipharmaDispatcherAction extends PageDispatcherAction implements PageDispatcherAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MemberFileDelegate memberFileDelegate = MemberFileDelegate.getInstance();
	private MemberFileItemDelegate memberFileItemDelegate = MemberFileItemDelegate.getInstance();
	
	private List<SavedEmail> trackerList = new ArrayList<SavedEmail>();
	private List<Member> memberList = new ArrayList<Member>();
	
	//for digital marketing
	private List<Member> memberDigitalMktgList = new ArrayList<Member>();
	private List<MemberFileItems> memberFileItems = new ArrayList<MemberFileItems>();
	
	private List<SavedEmail> gemReportsList = new ArrayList<SavedEmail>();
	private List<Member> gemMemberList = new ArrayList<Member>();
	
	private MemberFileItems selectedVideo;
	
	private Member member2;
	
	private PasswordEncryptor encryptor;
	
	private String successUrl;
	private String errorUrl;
	
	private Long memberTypeId;
	
	private String filePath;
	private String fileName;
	private long contentLength;
	
	private File[] files;
	private String[] contentTypes;
	private String[] filenames;
	
	private String pageModule;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	private Calendar date = Calendar.getInstance();
	
	public void prepare() throws Exception
	{
		super.prepare();
	}
	
	@Override
	public String execute() throws Exception
	{
		final String result = super.execute();
		
		if(isNull(member)) {
			return ERROR;
		}
		
		member2 = null;
		if(request.getParameter("member_id") != null && !request.getParameter("member_id").equalsIgnoreCase("")) {
			final long memberId = Long.parseLong(request.getParameter("member_id"));
			member2 = memberDelegate.find(memberId);
			encryptor = new PasswordEncryptor();
			member2.setPassword(encryptor.decrypt(member2.getPassword()));
			member2.setValue2(encryptor.decrypt(member2.getPassword()));
			member2.setValue3(member2.getPassword());
		}
		
		Boolean isForTest = (Boolean) request.getSession().getAttribute("isForTest");
		request.setAttribute("isForTest", isForTest != null && isForTest ? true : false);
		String formName = (isForTest != null && isForTest ? "MDG Calling Card Issuance Tracker - Test" : "MDG Calling Card Issuance Tracker");
		
		trackerList = savedEmailDelegate.findEmailByFormName(company, formName, Order.desc("createdOn")).getList();
		
		Collections.reverse(trackerList);
		
		int itemsPerPage = 15;
		int pageNumber = getPageNumber();
		
		List<SavedEmail> trackerItems = new ArrayList<SavedEmail>();
		int count = 1;
		for(int i = (pageNumber - 1) * itemsPerPage; i < trackerList.size(); i++)
		{
			if(count <= itemsPerPage)
				trackerItems.add(trackerList.get(i));
			count++;
		}
		request.setAttribute("trackerItems", trackerItems);
		final PagingUtil trackersPagingUtil = new PagingUtil(trackerList.size(), itemsPerPage, pageNumber, itemsPerPage);
		request.setAttribute("trackersPagingUtil", trackersPagingUtil);
		
		for(Member e : memberDelegate.findAll(company).getList()) {
			if(e.getValue2() != null && e.getValue2().contains("Digital Marketing")) {
				memberDigitalMktgList.add(e);
			}
			else if(e.getPageModule() != null && e.getPageModule().equalsIgnoreCase("GEM")) {
				//if(member.getId()!=e.getId())
					gemMemberList.add(e);
			}
			else {
				memberList.add(e);
			}
		}
		
		if(request.getParameter("video_id") != null && !request.getParameter("video_id").equalsIgnoreCase("")) {
			selectedVideo = memberFileItemDelegate.find(new Long(request.getParameter("video_id")));
		}
		setRecentUploadedVideos();
		
		setGemReportsList();
		
		return result;
	}
	
	private void setGemReportsList() {
		// TODO Auto-generated method stub
		String formName = "GEM";
		gemReportsList = savedEmailDelegate.findEmailByFormName(company, formName, Order.desc("createdOn")).getList();
		
		int itemsPerPage = 15;
		int pageNumber = getPageNumber();
		
		List<SavedEmail> gemReportsItems = new ArrayList<SavedEmail>();
		int count = 1;
		for(int i = (pageNumber - 1) * itemsPerPage; i < gemReportsList.size(); i++)
		{
			if(count <= itemsPerPage)
				gemReportsItems.add(gemReportsList.get(i));
			count++;
		}
		request.setAttribute("gemReportsItems", gemReportsItems);
		final PagingUtil gemReportsPagingUtil = new PagingUtil(gemReportsList.size(), itemsPerPage, pageNumber, itemsPerPage);
		request.setAttribute("gemReportsPagingUtil", gemReportsPagingUtil);
		
		Collections.reverse(gemMemberList);
		
		List<Member> gemMemberItems = new ArrayList<Member>();
		count = 1;
		for(int i = (pageNumber - 1) * itemsPerPage; i <gemMemberList.size(); i++)
		{
			if(count <= itemsPerPage)
				gemMemberItems.add(gemMemberList.get(i));
			count++;
		}
		request.setAttribute("gemMemberItems", gemMemberItems);
		final PagingUtil gemMembersPagingUtil = new PagingUtil(gemMemberList.size(), itemsPerPage, pageNumber, itemsPerPage);
		gemMembersPagingUtil.setHasLeftDots(true);
		gemMembersPagingUtil.setHasPrev(true);
		gemMembersPagingUtil.setHasNext(true);
		gemMembersPagingUtil.setHasRightDots(true);
		request.setAttribute("gemMembersPagingUtil", gemMembersPagingUtil);
		
	}

	public String saveUploadMembers(){
	
		//read the uploaded file
		if(pageModule != null) {
			uploadMembers();
		}
		
		InputStream input = null;
		MultiPartRequestWrapper s = (MultiPartRequestWrapper) request;
		files = s.getFiles("file_upload");
		try {
			input = new FileInputStream(files[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			setMessage("File not found.");
			return Action.ERROR;
		}
		
		HSSFWorkbook wb=null;
		try {
			wb = new HSSFWorkbook(input);
		}
		catch (IOException e) {
			e.printStackTrace();
			setMessage("File not valid. Create your file using MsExcel 2003.");
			return Action.ERROR;
		}catch (Exception e) {
			e.printStackTrace();
			setMessage("File not valid. Create your file using MsExcel 2003.");
			return Action.ERROR;
		} 
		
		int itemsRead=0;
		
		final short USERNAME_COLUMN = 0;
		final short EMAIL_COLUMN = 1;
		final short FIRSTNAME_COLUMN = 2;
		final short LASTNAME_COLUMN = 3;
		final short USERTYPE_COLUMN = 4;
		final short UNIT_COLUMN = 5;
		final short PASSWORD_COLUMN = 6;
		
		List<Member> memberList = new ArrayList<Member>();
		
		for(int index=0; index<wb.getNumberOfSheets();index++){	//sheet iteration
			HSSFSheet sheet  = wb.getSheetAt(index);
			System.out.println(" SHEET INDEX " + index);
			for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); itemsRead++) {		//row iteration
				Row row = rowIterator.next();				
				HashMap<String, String> otherFieldMap = new HashMap<String, String>();
				
				System.out.println(" ROW INDEX " + row.getRowNum());
				
				String userName = "";
				String email = "";
				String firstName = "";
				String lastName = "";
				String userType= "";
				String unit = "";
				String password= "";
				
				Member newMember = new Member();
				
				for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();) {	//cell iteration
					
					if(row.getRowNum()<1){
						//start in second row
						break;
					}						
					Cell cell = cellIterator.next();
					
					System.out.println(" CELL INDEX " + cell.getColumnIndex());
					
					switch (cell.getColumnIndex()) {
					case USERNAME_COLUMN:
						userName = cell.getStringCellValue();
						break;
					case EMAIL_COLUMN:
						email = cell.getStringCellValue();
						break;
					case FIRSTNAME_COLUMN:
						firstName = cell.getStringCellValue();
						break;
					case LASTNAME_COLUMN:
						lastName = cell.getStringCellValue();
						break;
					case USERTYPE_COLUMN:
						userType = cell.getStringCellValue();
						break;
					case UNIT_COLUMN:
						unit = cell.getStringCellValue();
						break;
					case PASSWORD_COLUMN:
						password = cell.getStringCellValue();
						break;
					}
					
				}
				
				if(!userName.equalsIgnoreCase("")) {
					encryptor = new PasswordEncryptor();
					String encryptedPassword, inputPassword;
					inputPassword = password;
					encryptedPassword = encryptor.encrypt(inputPassword);
					newMember.setValue(userType);
					newMember.setAddress1(unit);
					newMember.setUsername(userName);
					newMember.setPassword(encryptedPassword);
					newMember.setEmail(email);
					newMember.setFirstname(firstName);
					newMember.setLastname(lastName);
					newMember.setCompany(company);
					newMember.setDateJoined(getDateTime());
					newMember.setNewsletter(false);
					newMember.setMemberType(memberTypeDelegate.find(memberTypeId));
					newMember.setInfo1("");
					newMember.setActivationKey(Encryption.hash(newMember.getCompany() + newMember.getEmail() + newMember.getUsername() + newMember.getPassword()));
					memberList.add(newMember);
				}
			}
				
		}
		
		memberDelegate.batchInsert(memberList);
		
		return Action.SUCCESS;
	}
	
	public String uploadMembers() {
		// TODO Auto-generated method stub
		//read the uploaded file
		InputStream input = null;
		MultiPartRequestWrapper s = (MultiPartRequestWrapper) request;
		files = s.getFiles("file_upload");
		try {
			input = new FileInputStream(files[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			setMessage("File not found.");
			return Action.ERROR;
		}
		
		HSSFWorkbook wb=null;
		try {
			wb = new HSSFWorkbook(input);
		}
		catch (IOException e) {
			e.printStackTrace();
			setMessage("File not valid. Create your file using MsExcel 2003.");
			return Action.ERROR;
		}catch (Exception e) {
			e.printStackTrace();
			setMessage("File not valid. Create your file using MsExcel 2003.");
			return Action.ERROR;
		} 
		
		int itemsRead = 0;
		
		final short USERNAME_COLUMN = 0;
		final short INITIALPASSWORD_COLUMN = 1;
		final short FIRSTNAME_COLUMN = 2;
		final short LASTNAME_COLUMN = 3;
		final short EMAIL_COLUMN = 4;
		final short DEPARTMENT_COLUMN = 5;
		final short TYPE_COLUMN = 6;
		
		List<Member> memberList = new ArrayList<Member>();
		
		for(int index=0; index<wb.getNumberOfSheets();index++){	//sheet iteration
			HSSFSheet sheet  = wb.getSheetAt(index);
			System.out.println(" SHEET INDEX " + index);
			for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); itemsRead++) {		//row iteration
				Row row = rowIterator.next();				
				HashMap<String, String> otherFieldMap = new HashMap<String, String>();
				
				System.out.println(" ROW INDEX " + row.getRowNum());
				
				String userName = "";
				String initialPassword = "";
				String firstName = "";
				String lastName = "";
				String email= "";
				String department = "";
				String type = "";
				
				Member newMember = new Member();
				
				for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();) {	//cell iteration
					
					if(row.getRowNum()<1){
						//start in second row
						break;
					}						
					Cell cell = cellIterator.next();
					
					System.out.println(" CELL INDEX " + cell.getColumnIndex());
					
					switch (cell.getColumnIndex()) {
					case USERNAME_COLUMN:
						userName = cell.getStringCellValue();
						break;
					case INITIALPASSWORD_COLUMN:
						initialPassword = cell.getStringCellValue();
						break;
					case FIRSTNAME_COLUMN:
						firstName = cell.getStringCellValue();
						break;
					case LASTNAME_COLUMN:
						lastName = cell.getStringCellValue();
						break;
					case EMAIL_COLUMN:
						email = cell.getStringCellValue();
						break;
					case DEPARTMENT_COLUMN:
						department = cell.getStringCellValue();
						break;
					case TYPE_COLUMN:
						type = cell.getStringCellValue();
						break;
					}
					
				}
				
				if(!userName.equalsIgnoreCase("")) {
					encryptor = new PasswordEncryptor();
					String encryptedPassword, inputPassword;
					inputPassword = initialPassword;
					encryptedPassword = encryptor.encrypt(inputPassword);
					newMember.setValue(type);//usertype
//					newMember.setAddress1(unit);
					newMember.setUsername(userName+pageModule);
					newMember.setPassword(encryptedPassword);
					newMember.setEmail(email);
					newMember.setFirstname(firstName);
					newMember.setLastname(lastName);
					newMember.setCompany(company);
					newMember.setDateJoined(getDateTime());
					newMember.setNewsletter(false);
					newMember.setMemberType(memberTypeDelegate.find(memberTypeId));
					newMember.setValue3(department);
					newMember.setPageModule(pageModule);
					newMember.setPageModuleUsername(userName);
					newMember.setActivationKey(Encryption.hash(newMember.getCompany() + newMember.getEmail() + newMember.getUsername() + newMember.getPassword()));
					newMember.setActivated(true);
					memberList.add(newMember);
				}
			}
				
		}
		
		memberDelegate.batchInsert(memberList);
		
		return Action.SUCCESS;
	}

	public String save()
	{
		if(StringUtils.isEmpty(request.getParameter("member_id")))
		{
			if(memberDelegate.findEmail(company, member2.getEmail()) == null)
			{
				String password = member2.getPassword();
				encryptor = new PasswordEncryptor();
				String encryptedPassword, inputPassword;
				logger.info("3  " + member2);
				inputPassword = password;
				encryptedPassword = encryptor.encrypt(inputPassword);
				member2.setPassword(encryptedPassword);
				member2.setCompany(company);
				member2.setDateJoined(getDateTime());
				member2.setNewsletter(false);
				member2.setMemberType(memberTypeDelegate.find(memberTypeId));
				member2.setInfo1("");
				member2.setActivationKey(Encryption.hash(member2.getCompany() + member2.getEmail() + member2.getUsername() + member2.getPassword()));
				memberDelegate.insert(member2);
			}
		}
			else {
				String password = request.getParameter("password");
				String isPasswordChanged = request.getParameter("isPasswordChanged");
				final long memberId = Long.parseLong(request.getParameter("member_id"));
				member2 = memberDelegate.find(memberId);
				encryptor = new PasswordEncryptor();
				String encryptedPassword, inputPassword;
				inputPassword = password;
				encryptedPassword = encryptor.encrypt(inputPassword);
				//member2.setCompany(company);
				//member2.setNewsletter(false);
				member2.setValue(request.getParameter("memberType"));
				member2.setAddress1(request.getParameter("location"));
				member2.setUsername(request.getParameter("userName"));
				member2.setEmail(request.getParameter("email"));
				member2.setFirstname(request.getParameter("firstname"));
				member2.setLastname(request.getParameter("lastname"));
				if(isPasswordChanged.equalsIgnoreCase("true")) {
					member2.setPassword(encryptedPassword);
				}
				member2.setMemberType(memberTypeDelegate.find(memberTypeId));
				logger.info(member2);
				memberDelegate.update(member2);
			}
		
		return Action.SUCCESS;
	}
	
	public String delete() {
		SavedEmail savedEmail = savedEmailDelegate.find(new Long(request.getParameter("submissionId")));
		savedEmail.setIsValid(false);
		savedEmailDelegate.update(savedEmail);
		return SUCCESS; 
	}
	
	public String deleteMultiple() {
		try{
			String selectedValues = request.getParameter("selectedValues");
			
			//System.out.println(selectedValues); //testing
			StringTokenizer st = new StringTokenizer(selectedValues,"_");
						
			while (st.hasMoreTokens()) {
				SavedEmail savedEmail = savedEmailDelegate.find(Long.parseLong(st.nextToken()));
				savedEmail.setIsValid(false);
				savedEmailDelegate.update(savedEmail);
				savedEmailDelegate.update(savedEmail);
			}
			
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action.", e);
			return ERROR;
		}
		return SUCCESS; 
	} 
	
	@SuppressWarnings("unchecked")
	public String deleteVideo() throws Exception, IOException {
		try {
			String id = request.getParameter("video-id");
			MemberFileItems mfi = memberFileItemDelegate.find(new Long(id));
			memberFileItemDelegate.delete(mfi);
			JSONObject obj = new JSONObject();
			obj.put("isDeleted", true);
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String uploadFile() {
		try {
			
			MultiPartRequestWrapper s = (MultiPartRequestWrapper) request;
			files = s.getFiles("upload-file");
			filenames = s.getFileNames("upload-file");
			contentTypes = s.getContentTypes("upload-file");
			saveFiles(files, filenames, contentTypes);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private void saveFiles(File[] files, String[] filenames, String[] contentTypes) throws Exception {
		try {
			
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			
			String destinationPath = "companies";
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			
			
			destinationPath += File.separator + company.getName();
			
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator  + "images" + File.separator + "items"    + File.separator + "uploads"));
			
			MemberFile memberFile = new MemberFile();
			List<MemberFile> memberFiles = memberFileDelegate.findAll(member);
			if(isNull(memberFiles) || memberFiles.size() == 0) {
				memberFile = new MemberFile();
				memberFile.setCompany(company);
				memberFile.setMember(member);
				memberFileDelegate.insert(memberFile);
				memberFile = memberFileDelegate.findAll(member).get(0);
			} else {
				memberFile = memberFiles.get(0);
			}
			
			for(int i = 0; i < files.length; i++) {
				if(files[i].exists()) {
					File file = files[i];
					String filename = filenames[i];
					String contentType = contentTypes[i];
					String destination0 = servletContext.getRealPath(destinationPath + File.separator   + "images" + File.separator + "items");
					String destination = servletContext.getRealPath(destinationPath + File.separator   + "images" + File.separator + "items"    + File.separator + "uploads");
					////System.out.println("DESTINATION:  " + destination);
					//new File(destination0).mkdir();
					//new File(destination).mkdir();
					//logger.info("UPloaddddd : "  +  destination + File.separator + filename  );
					//File destFile = new File(destination + File.separator + filename);
					
					filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"), 
							String.valueOf(new Date().getTime()));	
					//filename = FileUtil.replaceExtension(filename, "jpg"); 
					// generate original image
					File destFile = new File(destination + File.separator + filename);
					
					FileUtil.copyFile(file, destFile);
					
					String posterImage = request.getParameter("posterImage");
					String posterFilename = "";
					if(posterImage != null && !posterImage.equals("")) {
						 //byte[] decodedBytes = DatatypeConverter.parseBase64Binary(posterImage );
						posterImage = posterImage.replace("data:image/png;base64,", "");
						byte[] decodedBytes = Base64.decode(posterImage.getBytes());
						 InputStream in = new ByteArrayInputStream(decodedBytes);
							BufferedImage bfi = ImageIO.read(in);
					        File posterImageFile = new File("saved.png");
					        ImageIO.write(bfi , "png", posterImageFile);
					        bfi.flush();
					        posterFilename = FileUtil.replaceExtension(filename, "png");
					        destFile = new File(destination + File.separator + posterFilename);
					        FileUtil.copyFile(posterImageFile, destFile); 
					}
					
					MemberFileItems items = new MemberFileItems();
					items.setMemberFile(memberFile);
					items.setFilename(filename);
					items.setOriginal(posterFilename);
					items.setCompany(company);
					items.setTitle(title);
					items.setDescription(description);
					items.setValue(member.getAddress1());
					//System.out.println("filesize-------------------------------------"+itemFile.getFileSize());
					memberFileItemDelegate.insert(items);
				} 
			}
			
			//send notification to uploader
			final EmailSenderAction emailSenderAction = new EmailSenderAction();
			StringBuffer content = new StringBuffer();
			content.append("Your video has been uploaded " + filenames[0]);
			emailSenderAction.sendNotification(company, member.getEmail(), "Notification: Digital Marketing", content, null);
			
			setNotificationMessage("Your video has been uploaded.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setRecentUploadedVideos() {
		memberFileItems = memberFileItemDelegate.findByCompany(company);
		Collections.reverse(memberFileItems);
		for(MemberFileItems item: memberFileItems) {
			if(item.getRemarks() != null && item.getRemarks().equals("FEATURED")) {
//				request.setAttribute("featuredVideo", item);
				break;
			}
		}
	}
	
	public String downloadEmails() throws Exception {
		
		try {
			
			trackerList = (List<SavedEmail>) request.getSession().getAttribute("sortedList");
			
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			fileName="Forms_Submission_Report.xls";
			filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
			//filePath =  locationPath + fileName;
			logger.debug("filepath (member) : " + filePath);		
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file);		
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy hh:mm aa");
			
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
			c.setCellValue("Date and Time");
			c = r.createCell((short) 2);
			c.setCellValue("Med Rep");
			c = r.createCell((short) 3);
			c.setCellValue("Unit");
			c = r.createCell((short) 4);
			c.setCellValue("Doctor's Family Name");		
			c = r.createCell((short) 5);
			c.setCellValue("Doctor's First Name");		
			c = r.createCell((short) 6);
			c.setCellValue("Beginning Number");	
			c = r.createCell((short) 7);
			c.setCellValue("Ending Number");
			c = r.createCell((short) 8);
			c.setCellValue("Number of Cards");
			
			for(int x=1; x<=8; x++) {
				s.setColumnWidth((short) x, (short) 9000);
			}
			
			short rowNum=3;
			String emailContent;
			
			for(SavedEmail e : trackerList)
			{
				String doctorsFamilyName = "";
				try {
					doctorsFamilyName = e.getEmailContent().split("###")[2].split(":")[1];
				} catch (ArrayIndexOutOfBoundsException ai) {
					doctorsFamilyName = "";
				}
				
				String doctorsFirstName = "";
				try {
					doctorsFirstName = e.getEmailContent().split("###")[3].split(":")[1];
				} catch (ArrayIndexOutOfBoundsException ai) {
					doctorsFamilyName = "";
				}
				
				String beginNum = "";
				String endNum = "";
				
				try {
					beginNum = e.getEmailContent().split("###")[0].split(":")[1];
					endNum = e.getEmailContent().split("###")[1].split(":")[1];
				} catch (ArrayIndexOutOfBoundsException ai) {
					beginNum = "0";
					endNum = "0";
				}
				   
				r=s.createRow((short) ++rowNum);
				c = r.createCell((short) 1);
				c.setCellValue(sdf.format(e.getCreatedOn()));
				c = r.createCell((short) 2);
				c.setCellValue(e.getMember().getLastname() + " " + e.getMember().getFirstname());
				c = r.createCell((short) 3);
				c.setCellValue(e.getMember().getAddress1());
				c = r.createCell((short) 4);
				c.setCellValue(doctorsFamilyName != null && !doctorsFamilyName.equals("") ? doctorsFamilyName : "");
				c = r.createCell((short) 5);
				c.setCellValue(doctorsFirstName != null && !doctorsFirstName.equals("") ? doctorsFirstName : "");
				c = r.createCell((short) 6);
				c.setCellValue(beginNum != null && !beginNum.equals("") ? beginNum : "");
				c = r.createCell((short) 7);
				
				c.setCellValue(endNum != null && !endNum.equals("") ? endNum : "");
				c = r.createCell((short) 8);
				c.setCellValue(Integer.parseInt(endNum != null && !endNum.equals("") ? endNum : "0")-Integer.parseInt(beginNum != null && !beginNum.equals("") ? beginNum : "0")+1);
					
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
	
	public String downloadDigitalMarketingLogs() throws Exception {
		
		try {
			
			List<MemberFileItems> items =  memberFileItemDelegate.findByCompany(company);
			String[] ids = new String[items.size()];
			String[] dlIds = new String[items.size()];
			String[] playIds = new String[items.size()];
			String strIds = "";
			int index = 0;
			for(MemberFileItems item : items) {
				ids[index] = "'Download Video : "+item.getId()+", 'Play Video : "+item.getId()+"'";
				dlIds[index] = "Download Video : "+item.getId()+"";
				playIds[index] = "Play Video : "+item.getId()+"";
				strIds += (strIds.equals("") ? "'Download Video : "+item.getId()+"', 'Play Video : "+item.getId()+"'" 
						: ", 'Download Video : "+item.getId()+"' , 'Play Video : "+item.getId()+"'");
				index++;
			}
			
			List<MemberLog> memberLogs = memberLogDelegate.findAllByRemarks(company, (Object) ids).getList();
			List<MemberLog> dlLogs = memberLogDelegate.findAllByRemarks(company, (Object) dlIds).getList();
			List<MemberLog> playedLogs = memberLogDelegate.findAllByRemarks(company, (Object) playIds).getList();
			
			HashMap<String, List<MemberLog>> dlMap = new HashMap<String, List<MemberLog>>();
			for(MemberLog ml : dlLogs) {
				String key = ml.getRemarks().replaceAll("\\D+","");
				if(dlMap.containsKey(key)) {
					dlMap.get(key).add(ml);
				} else {
					List<MemberLog> nMl = new ArrayList<MemberLog>();
					nMl.add(ml);
					dlMap.put(key, nMl);
				}
			}
			
			HashMap<String, List<MemberLog>> playMap = new HashMap<String, List<MemberLog>>();
			for(MemberLog ml : playedLogs) {
				String key = ml.getRemarks().replaceAll("\\D+","");
				if(playMap.containsKey(key)) {
					playMap.get(key).add(ml);
				} else {
					List<MemberLog> nMl = new ArrayList<MemberLog>();
					nMl.add(ml);
					playMap.put(key, nMl);
				}
			}
			
			//SimpleDateFormat sdfTime = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss");
			
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			fileName="Digital_Marketing_Logs.xls";
			filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
			//filePath =  locationPath + fileName;
			logger.debug("filepath (member) : " + filePath);		
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file);		
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFSheet s2 = wb.createSheet();
			HSSFRow r = null;
			HSSFCell c = null;
			HSSFCellStyle style = wb.createCellStyle();
			//style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			HSSFDataFormat format = wb.createDataFormat();
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss aa");
			
			wb.setSheetName(0, "Play - ");
			wb.setSheetName(1, "Download - ");
			
			r=s.createRow(1);
			r=s.createRow(2);
			c = r.createCell((short) 1);
			c.setCellValue("Video");
			c = r.createCell((short) 2);
			c.setCellValue("Country");
			c = r.createCell((short) 3);
			c.setCellValue("Member");
			c = r.createCell((short) 4);
			c.setCellValue("Date");
			
			r=s2.createRow(1);
			r=s2.createRow(2);
			c = r.createCell((short) 1);
			c.setCellValue("Video");
			c = r.createCell((short) 2);
			c.setCellValue("Country");
			c = r.createCell((short) 3);
			c.setCellValue("Member");
			c = r.createCell((short) 4);
			c.setCellValue("Date");
			
			for(int x=1; x<=8; x++) {
				s.setColumnWidth((short) x, (short) 9000);
				s2.setColumnWidth((short) x, (short) 9000);
			}
			
			short rowNum=3;
			short colNum = 1;
			int rownum = rowNum;
			
			for(MemberFileItems mfi : items) {
				
				r=s.createRow(rowNum);
				c = r.createCell((short) 1);
				c.setCellValue(mfi.getTitle());
				c.setCellStyle(style);
				int rowTo = 0;
				try {
					rowTo = rowNum+playMap.get(mfi.getId().toString()).size()-1;
				} catch (NullPointerException e) {
					e.printStackTrace();
					rowTo = rowNum;
				}
				s.addMergedRegion(new Region(rowNum, (short) 1, rowTo, (short) 1));
				s.addMergedRegion(new Region(rowNum, (short) 2, rowTo, (short) 2));
				
				System.out.println(playMap.get(mfi.getId().toString()).size());
				
				c = r.createCell((short) 2);
				c.setCellValue(mfi.getMemberFile().getMember().getAddress1());
				c.setCellStyle(style);
				
				int mlCount = 1;
				if(playMap.get(mfi.getId().toString()) != null) {
					for(MemberLog ml : playMap.get(mfi.getId().toString())) {
						if(mlCount>1) {
							rowNum++;
							r = s.createRow(rowNum);
							c = r.createCell((short) 3);
							c.setCellValue(ml.getMember().getFullName());
							
							c = r.createCell((short) 4);
							c.setCellValue(sdf.format(ml.getCreatedOn()));
						} else {
							c = r.createCell((short) 3);
							c.setCellValue(ml.getMember().getFullName());
							
							c = r.createCell((short) 4);
							c.setCellValue(sdf.format(ml.getCreatedOn()));
						}
						mlCount++;
					}
				}
				rowNum++;
			}
			
			//Download sheet
			rowNum=3;
			for(MemberFileItems mfi : items) {
				
				r=s2.createRow(rowNum);
				c = r.createCell((short) 1);
				c.setCellValue(mfi.getTitle());
				c.setCellStyle(style);
				int rowTo = 0;
				try {
					rowTo = rowNum+dlMap.get(mfi.getId().toString()).size()-1;
				} catch (NullPointerException e) {
					e.printStackTrace();
					rowTo = rowNum;
				}
				s2.addMergedRegion(new Region(rowNum, (short) 1, rowTo, (short) 1));
				s2.addMergedRegion(new Region(rowNum, (short) 2, rowTo, (short) 2));
				
				System.out.println(playMap.get(mfi.getId().toString()).size());
				
				c = r.createCell((short) 2);
				//c.setCellValue(mfi.getValue());
				c.setCellValue(mfi.getMemberFile().getMember().getAddress1());
				c.setCellStyle(style);
				
				int mlCount = 1;
				
				if(dlMap.get(mfi.getId().toString()) != null) {
					for(MemberLog ml : dlMap.get(mfi.getId().toString())) {
						if(mlCount>1) {
							rowNum++;
							r = s2.createRow(rowNum);
							c = r.createCell((short) 3);
							c.setCellValue(ml.getMember().getFullName());
							
							c = r.createCell((short) 4);
							c.setCellValue(sdf.format(ml.getCreatedOn()));
						} else {
							c = r.createCell((short) 3);
							c.setCellValue(ml.getMember().getFullName());
							
							c = r.createCell((short) 4);
							c.setCellValue(sdf.format(ml.getCreatedOn()));
						}
						mlCount++;
					}
				}
				rowNum++;
			}
			
			wb.write(out);
			out.close();
			
			logger.info("start download...");
			logger.debug("start download...");
			download(filePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String downloadGemEmails() throws Exception {
		
		try {
			
			gemReportsList = (List<SavedEmail>) request.getSession().getAttribute("newGemReportList");
			
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			fileName="GEM_Reports.xls";
			filePath =  locationPath + "reports"+  File.separatorChar  + fileName;
			//filePath =  locationPath + fileName;
			logger.debug("filepath (member) : " + filePath);		
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file);		
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy hh:mm aa");
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			HSSFRow r = null;
			HSSFCell c = null;
			HSSFCellStyle style;
			HSSFDataFormat format = wb.createDataFormat();
			
			wb.setSheetName(0, "GEM FORM SUBMISSION REPORT - ");		
			
			r=s.createRow(0);
			c = r.createCell((short) 0);
			c.setCellValue("Sender");
			c = r.createCell((short) 1);
			c.setCellValue("Sender Department");
			c = r.createCell((short) 2);
			c.setCellValue("Recipient");
			c = r.createCell((short) 3);
			c.setCellValue("Recipient Department");		
			c = r.createCell((short) 4);
			c.setCellValue("Gem Card No.");		
			c = r.createCell((short) 5);
			c.setCellValue("Created Date/Time");	
			c = r.createCell((short) 6);
			c.setCellValue("Values");
			c = r.createCell((short) 7);
			c.setCellValue("Specific Action");
			c = r.createCell((short) 8);
			c.setCellValue("Event Date/Time");
			c = r.createCell((short) 9);
			c.setCellValue("Impact of Action");
			
			for(int x=0; x<=9; x++) {
				s.setColumnWidth((short) x, (short) 9000);
			}
			
			short rowNum=0;
			
			for(SavedEmail e : gemReportsList)
			{
				String senderName = e.getEmailContent().split("##")[0].split("==")[1];
				String senderDepartment = e.getEmailContent().split("##")[1].split("==")[1];
				String recipient = e.getEmailContent().split("##")[2].split("==")[1];
				String recipientDepartment = e.getEmailContent().split("##")[3].split("==")[1];
				String gemCardNo = e.getEmailContent().split("##")[4].split("==")[1];
				String createdDateTime = e.getEmailContent().split("##")[5].split("==")[1];
				String values = e.getEmailContent().split("##")[6].split("==")[1];
				String specificAction = e.getEmailContent().split("##")[7].split("==")[1];
				String eventDateTime = e.getEmailContent().split("##")[8].split("==")[1];
				String impactOfAction = e.getEmailContent().split("##")[9].split("==")[1];
				   
				r=s.createRow((short) ++rowNum);
				c = r.createCell((short) 0);
				c.setCellValue(senderName);
				c = r.createCell((short) 1);
				c.setCellValue(senderDepartment);
				c = r.createCell((short) 2);
				c.setCellValue(recipient);
				c = r.createCell((short) 3);
				c.setCellValue(recipientDepartment);
				c = r.createCell((short) 4);
				c.setCellValue(gemCardNo);
				c = r.createCell((short) 5);
				c.setCellValue(new SimpleDateFormat("MM/dd/YYYY HH:mm").format(e.getCreatedOn()));
				c = r.createCell((short) 6);
				c.setCellValue(values);
				c = r.createCell((short) 7);
				c.setCellValue(HTMLTagStripper.stripTags(specificAction));
				c = r.createCell((short) 8);
				c.setCellValue(eventDateTime);
				c = r.createCell((short) 9);
				c.setCellValue(HTMLTagStripper.stripTags(impactOfAction));
					
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
	
	public void download(String filePath) throws Exception {
		
		File file = new File(filePath);
		if(!file.exists()) {
			logger.fatal("Unabled to locate file: " + filePath);
		}
		
		fileInputStream = new FileInputStream(file);
		inputStream = new FileInputStream(file);
		contentLength = file.length();
	}
	
	@SuppressWarnings("unchecked")
	public String getAllGemMembers() throws Exception {
		try {
			List<Member> members = memberDelegate.findAllByPageModule(pageModule, company).getList();
			JSONArray objList = new JSONArray();
			JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();
			obj.put("success", true);
			obj.put("members", members);
			objList.add(obj);
			obj2.put("objListDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString()
					.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private Date getDateTime()
	{
		final Date date = new Date();
		return date;
	}

	public List<SavedEmail> getTrackerList() {
		return trackerList;
	}

	public void setTrackerList(List<SavedEmail> trackerList) {
		this.trackerList = trackerList;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

	public Member getMember2() {
		return member2;
	}

	public void setMember2(Member member2) {
		this.member2 = member2;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
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

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public List<Member> getMemberDigitalMktgList() {
		return memberDigitalMktgList;
	}

	public void setMemberDigitalMktgList(List<Member> memberDigitalMktgList) {
		this.memberDigitalMktgList = memberDigitalMktgList;
	}

	public String[] getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}

	public String[] getFilenames() {
		return filenames;
	}

	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}

	public List<MemberFileItems> getMemberFileItems() {
		return memberFileItems;
	}

	public void setMemberFileItems(List<MemberFileItems> memberFileItems) {
		this.memberFileItems = memberFileItems;
	}

	public MemberFileItems getSelectedVideo() {
		return selectedVideo;
	}

	public void setSelectedVideo(MemberFileItems selectedVideo) {
		this.selectedVideo = selectedVideo;
	}

	public String getPageModule() {
		return pageModule;
	}

	public void setPageModule(String pageModule) {
		this.pageModule = pageModule;
	}
	
	public List<SavedEmail> getGemReportsList() {
		return gemReportsList;
	}

	public void setGemReportsList(List<SavedEmail> gemReportsList) {
		this.gemReportsList = gemReportsList;
	}

	public List<Member> getGemMemberList() {
		return gemMemberList;
	}

	public void setGemMemberList(List<Member> gemMemberList) {
		this.gemMemberList = gemMemberList;
	}
	
}
