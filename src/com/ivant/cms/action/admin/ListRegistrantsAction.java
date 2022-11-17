package com.ivant.cms.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
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
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.RegistrationItemOtherFieldDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.CompanyStatusEnum;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.JXLUtil;
import com.ivant.utils.SortingUtil;
import com.opensymphony.xwork2.ActionSupport;
/*
 * Author: Glenn Allen Sapla
 * Date: Aug 26, 2010
 * 
 * For registration you must consider the jsp pages and the setInitCompany methods at the ff classes
 * RegistrantsAction.java, RegistrationAction.java, ListRegistrantsAction.java
 * For JSP page refer to companies/bright/registration.jsp, admin/registration.jsp, admin/editRegistration.jsp
 */
public class ListRegistrantsAction extends ActionSupport 
				implements UserAware, CompanyAware, PagingAware, ServletContextAware, ServletRequestAware {
	
	private static final long serialVersionUID = 4050469397961099239L;
	private static final Logger logger = Logger.getLogger(ListRegistrantsAction.class);
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private RegistrationItemOtherFieldDelegate registrationDelegate= RegistrationItemOtherFieldDelegate.getInstance();
	private OtherFieldDelegate otherFieldDelegate= OtherFieldDelegate.getInstance();
	
	private Company company;
	private Boolean hasRegistrants;

	private int page;
	private int totalItems;
	private int itemsPerPage;

	private String filePath;
	private String fileName;
	private String year;
	private FileInputStream fInStream;
	private long contentLength;
	private long submissionId;
	
	private List<Member> members;
	private Member member;
	private List<String> names2;
	private List<RegistrationItemOtherField> items;
	private Map<String, String> mapFieldValues;
	private Map<String, String> mapFileName;
	private User user;
	private List<RegistrationItemOtherField> infoRepeating;
	private Map<String, String> mapRepeatingFieldValues;
	
	private ServletContext servletContext;
	private HttpServletRequest request;	
	private Integer yearLimit;
	
	//list of schools for company bright
	private String[] brightSchools = {
			"Alia College Ltd",
			"Alphington Grammar School",
			"Aquinas College",
			"Association for Christian Education of Box Hill",
			"Assumption Catholic College of Kilmore",
			"Australian International Academy of Education (Inc.)",
			"Ballarat and Queen`s Anglican Grammar School",
			"Bacchus Marsh Grammar Inc.",
			"Beaconhills Christian College Ltd",
			"Bendigo Senior Secondary College",
			"Bentleigh Secondary College",
			"Billanook College Limited",
			"Box Hill Institute of TAFE",
			"Box Hill Senior Secondary College",
			"Brentwood Secondary College",
			"Brighton Grammar School",
			"Brighton Secondary College",
			"Bruswick Secondary College",
			"Camberwell Anglican Girls` Grammar School[girls only]",
			"Carey Baptist Grammar School Limited",
			"Caroline Chisholm Catholic College",
			"Caufield Grammar School",
			"Centre for Adult Education (CAE)",
			"Cheltenham Seconadry College",
			"Chisholm Institute of TAFE",
			"Christian Brothers College St. Kilda",
			"Debney Park Secondary College",
			"Department of Education (Victoria)[for students w/ disabilities]",
			"Doncaster Secondary College",
			"Dromana Secondary College ",
			"East Doncaster Secondary College",
			"Eltham College",
			"Fintona Girls School[girls only]",
			"Firbank Grammar Schoool[girls only]",
			"Fountain Gate Secondary College",
			"The Geelong College",
			"Geelong Grammar School",
			"Gippsland Grammar",
			"Girton Grammar School Ltd",
			"Glen Waverley Secondary College ",
			"Gordon Institute of TAFE",
			"Goulburn Valley Grammar School Limited",
			"Haileybury",
			"Hallam Senior College",
			"Hawthorn Secondary College ",
			"Highvale Secondary College ",
			"Holmesglen Institute of TAFE",
			"The Hamilton and Alexandra College",
			"Heathdale Christian College Limited",
			"Heatherton Christian College",
			"Hillcrest Christian College (Vic) Inc.",
			"Holmes Commercial Colleges (Melbourne) Limited",
			"Huntingtower School Association",
			"The Islamic Schools of Victoria Inc.",
			"The Ivanhoe Girls Grammar School[girls only]",
			"Kangan Batman Institute of TAFE",
			"Kardinia International College (Geelong) Ltd",
			"Keysborough Secondary College",
			"Keysborough Secondary College - Chandler Campus",
			"Keysborough Secondary College - Coomoora Campus",
			"Keysborough Secondary College - Heatherhill Campus",
			"Keysborough Secondary College - Springvale Campus",
			"The Kilmore International School Ltd",
			"Kilvington Girls` Grammar Limited[girls only]",
			"Kingswood College Limited",
			"Koonung Secondary College",
			"Korowa Anglican Girls School[girls only]",
			"The Knox School Limited",
			"Lalor North Secondary College",
			"Lauriston Girls` School",
			"Life Ministry Centre Limited",
			"Little Yarra Steiner School Limited",
			"Loyola College",
			"Lowther Hall Anglican Grammar School[girls only]",
			"Luther College",
			"Lyndale Secondary College",
			"Macedon Grammar School Co-operative Ltd",
			"Mackillop Catholic Regional College",
			"Maranatha Christian School",
			"Marist-Sion College",
			"Maroondah Secondary College",
			"Mater Christi College",
			"Mazenod Regional College",
			"McKinnon Secondary College",
			"Melbourne Girls` College",
			"Melbourne Girls Grammar - An Anglican School",
			"Melbourne Grammar School",
			"Mentone Grammar School",
			"Mercy Diocesan College",
			"Methodist Ladies` College Limited",
			"Mill Park Secondary College",
			"Minaret College Inc",
			"Monivae College",
			"Mornington Secondary College",
			"Mount Eliza Secondary College",
			"Mount St. Joseph Girls` College - Altona Westgirls only]",
			"Mount Waverley Secondary College",
			"Mowbray College",
			"Nazareth College",
			"Noble Park Secondary College",
			"Notre Dame College",
			"Ozford College Pty Ltd",
			"Parkdale Secondary College",
			"The Peninsula School",
			"Penleigh and Essendon Grammar School",
			"The Presbyterian School of St. Andrew Limited",
			"Presentation College Windsor",
			"Preston Girls` Secondary College[girls only]",
			"Princes Hill Secondary College",
			"Reservoir District Secondary College",
			"Ringwood Secondary College ",
			"RMIT University (RMIT)",
			"Rosebud  Secondary College",
			"Rowville Secondary College",
			"Ruyton",
			"Sacre Coeur",
			"Saint Ignatius College Geelong",
			"Scotch College",
			"Selimiye Foundation Limited",
			"Seventh-Day Clarendon College",
			"Shelford Girls` Grammar[girls only]",
			"Stawell Secondary College",
			"St. Albans Secondary College",
			"St. Aloysius College",
			"St. Catherine`s School[girls only]",
			"St. Joseph`s College (Newtown)",
			"St. Joseph`s Regional College",
			"St. Kevin`s College (Christian Brothers Vic Property Ltd)",
			"St. Leonard`s College",
			"St. Margaret`s School",
			"St. Mary of the Angels Secondary College",
			"St. Michael`s Grammar School",
			"St. Patricks College Ballarat",
			"St. Paul`s Anglican Grammar School Limited",
			"Stott`s Colleges Pty Ltd",
			"Strathcona Baptist Girls Grammar School Limited[girls only]",
			"Stratmore Secondary College",
			"Swinburne Senior Secondary College",
			"Tayloys Institute of Advanced Studies Ltd (TIAS)",
			"Thomas Carr College Tarneit",
			"Tintern Schools",
			"Toorak College[girls only]",
			"Trinity Grammar School Kew",
			"Vermont Secondary College",
			"Victoria University",
			"Victoria University Secondary College",
			"Victorian College of the Arts Secondary School",
			"Victorian Conference of the Seventh Day Adventist Church",
			"Waverly Christian College Inc",
			"Wellington Secondary College",
			"Werribee Secondary College ",
			"Wesley College Melbourne",
			"Wetsall Secondary College",
			"Westbourne Grammar School",
			"Wheelers Hill Secondary College",
			"Whitefriars College Inc.",
			"Wodonga Senior Secondary College",
			"Woodleigh School",
			"Xin Yi Dai (The New Generation Chinese Culture School) Inc.",
			"Yarra Valley Grammar"	
	};

	@Override
	public String execute() throws Exception {
		this.setInitCompanies();
		hasRegistrants=false;
		members = memberDelegate.findAllWithPagingWithPurpose("Registration", company, page, itemsPerPage, Order.desc("createdOn")).getList();
		//used wether to show download excel box or not
		if(getMembers()!=null && members.size()>0){
			hasRegistrants=true;
		}
		int year = new DateTime().getYear();
		request.setAttribute("year", year);
		return super.execute();
	}
	
	public void setInitCompanies(){
		mapFileName = new  TreeMap<String, String>();
		names2 = new ArrayList();
		if(company.getId()==150L){//Bright
			mapFileName.put("digital", "digital");
			mapFileName.put("passport", "passport");
			mapFileName.put("visa", "visa");
			mapFileName.put("birth","birth");
			mapFileName.put("financial","financial");
			mapFileName.put("academic", "academic");
			mapFileName.put("ecoe", "ecoe");
			names2.add("digital");
			names2.add(	"passport");
			names2.add(	"visa");
			names2.add(	"birth");
			names2.add(	"financial");
			names2.add(	"academic");
			names2.add(	"ecoe");
		}

	}
	
	public String edit(){
		
		if(submissionId != 0){
			Calendar cal = Calendar.getInstance();
			setYearLimit(cal.get(Calendar.YEAR)-2006);
			if(request.getParameter("year")!=null && request.getParameter("year")!="")
			if(Integer.parseInt(request.getParameter("year")) > 2006+getYearLimit() || Integer.parseInt(request.getParameter("year"))<=2006)
				return ERROR;
			
			this.setInitCompanies();
			//initialize member to edit
	
			Member mem = memberDelegate.find(this.submissionId);
			setMember(mem);
			if(mem == null) return ERROR;
			
			items= mem.getRegistrationItemOtherFields();	
			member=mem;
			setYear(request.getParameter("year")); 
			//sort fields by sort order
			if(items!=null)
				SortingUtil.sortBaseObject("otherField.sortOrder", true,items );
			else return ERROR;
			if(request.getParameter("year")!=null && !request.getParameter("year").isEmpty())
				items=registrationDelegate.findAll(company, member,request.getParameter("year"),null).getList();
			mapFieldValues = new TreeMap<String,String>();
			for(RegistrationItemOtherField e: items){
				mapFieldValues.put(e.getOtherField().getName(), e.getContent());
			}	
			setMapRepeatingFieldValues(new TreeMap<String,String>());
			setInfoRepeating(registrationDelegate.findAllWithIndexing(company, member, request.getParameter("year")).getList());
			if(request.getParameter("year")!=null &&  !request.getParameter("year").isEmpty()){
				for(RegistrationItemOtherField e: getInfoRepeating()){
					getMapRepeatingFieldValues().put(e.getOtherField().getName()+e.getIndex(), e.getContent());
				}
			}
		}
		return SUCCESS;
	}
	

	public String downloadRegistrantsIave() throws Exception {
		List<Member> members = new ArrayList();
		int maxYear = Calendar.getInstance().get(Calendar.YEAR);
		
		if (request.getParameter("filter").equals("all")){
			members = memberDelegate.findPurpose(company,"Registration");
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
	        members = memberDelegate.findPurposeByDate(company,"Registration" ,fromDate, toDate);
			logger.debug("\n\n\nEmail list for " + company.getName() + ":   " + members.size());			
		}
	
		Integer totalRegistrants =0, totalVolunteers =0, totalHours=0, totalBeneficiaries=0;
		
		if(members.size()!=0){	
			totalRegistrants = members.size();
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			filePath =  locationPath + "reports";
			new File(locationPath).mkdir();
		
			File writeDir = new File(filePath);
			WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "All Registration.xls");
			
			for(Integer dataYear=maxYear; dataYear>2006; dataYear--){
				WritableSheet writableSheet = JXLUtil.createWritableSheet(writableWorkbook, dataYear.toString(), maxYear-dataYear);
				filePath= writeDir+"/All Registration.xls";
				fileName = "All Registration.xls";
				int count=0;
				
				//headers
				Map<Integer, String> headers = new TreeMap<Integer,String>();
				//add headers here in this format <column number, String>
				headers.put(0,"LOGIN INFO");
				headers.put(3,"IDENTIFYING INFO");
				headers.put(28,"DESCRIPTION OF ORGANIZATION INFO");
				headers.put(34,"SCOPE AND GEOGRAPHIC INFO");
				headers.put(42,"UNITED NATIONS MILLENIUM DEVELOPMENT GOALS");
				headers.put(51,"VOLUNTEER MANAGEMENT TRAININGS");
				headers.put(57,"THEMATIC AREAS");
				headers.put(74,"SECTORS");
				headers.put(87,"VOLUNTEERS");
				headers.put(88,"SEX DISTRIBUTION");
				headers.put(90,"AGE RANGE");
				headers.put(94,"C0MPUTATION OF VOLUNTEER HOURS AND NUMBER OF BENEFICIARIES");
				headers.put(100,"GRAND TOTALS PER YEAR");
				headers.put(102,"AGREEMENT");
				
				int maxColumn = 100;
				int offset =0;
				for(int x=0; x<maxColumn; x++){
					if(headers.containsKey(x)){
						//get the offset for merging
						for(int y=x+1; y<maxColumn; y++){
							if(headers.containsKey(y)){
								offset = y-1;
								break;
							}
						}
						JXLUtil.addLabelCell(writableSheet, x, 0, headers.get(x), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
						//JXLUtil.setColumnView(writableSheet, x, headers.get(x).length()*2);
						writableSheet.mergeCells(x, 0, offset, 0);
					}
				}
				
				//set the subheaders
				//List<RegistrationItemOtherField> temp2 = members.get(0).getRegistrationItemOtherFields();
				List<OtherField> temp2 = otherFieldDelegate.find(company);
				
				if(temp2!=null)
					SortingUtil.sortBaseObject("sortOrder", true,temp2 );
				
				//subheaders
				for(OtherField temp3 : temp2){		
					JXLUtil.addLabelCell(writableSheet, count, 1, temp3.getName(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
					JXLUtil.setColumnView(writableSheet, count, temp3.getName().length()*2);
					count++;
				}		
				
				this.setInitCompanies();
				int row=3, col=0;
				
				//get the generic values of the fields
				List<RegistrationItemOtherField> items;
				
				for(Member temp : members){
					//generic values
					items = registrationDelegate.findAllWithNote(company, temp, null).getList();
					if(items!=null)
						SortingUtil.sortBaseObject("otherField.sortOrder", true,items);
					for(RegistrationItemOtherField temp4 : items){		
						//System.out.println(temp4.getContent()+" Compare "+temp4.getOtherField().getName());
						JXLUtil.addLabelCell(writableSheet, temp4.getOtherField().getSortOrder()-1, row, temp4.getContent(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
						JXLUtil.setColumnView(writableSheet, temp4.getOtherField().getSortOrder()-1, temp4.getOtherField().getName().length()*2);
					}
					//year specific values
					items = registrationDelegate.findAllWithNote(company, temp, dataYear.toString()).getList();
					if(items!=null)
						SortingUtil.sortBaseObject("otherField.sortOrder", true,items);

					for(RegistrationItemOtherField temp4 : items){		
						JXLUtil.addLabelCell(writableSheet, temp4.getOtherField().getSortOrder()-1, row, temp4.getContent(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
						JXLUtil.setColumnView(writableSheet, temp4.getOtherField().getSortOrder()-1, temp4.getOtherField().getName().length()*2);
						
						if(temp4.getOtherField().getSortOrder()==88)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalVolunteers += Integer.parseInt(temp4.getContent());
						if(temp4.getOtherField().getSortOrder()==101)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalHours += Integer.parseInt(temp4.getContent());
						if(temp4.getOtherField().getSortOrder()==102)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalBeneficiaries += Integer.parseInt(temp4.getContent());
					}
					//repeating values
					Map<String, String> mapRepeatingFieldValues = new TreeMap<String,String>();
					List<RegistrationItemOtherField> infoRepeating = registrationDelegate.findAllWithIndexing(company, temp, dataYear.toString()).getList();
					
					int tempRow=0;
					
					for(RegistrationItemOtherField e: infoRepeating){
						mapRepeatingFieldValues.put(e.getOtherField().getName()+e.getIndex(), e.getContent());
						JXLUtil.addLabelCell(writableSheet, e.getOtherField().getSortOrder()-1, row+e.getIndex(), e.getContent(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
						JXLUtil.setColumnView(writableSheet, e.getOtherField().getSortOrder()-1, row+e.getOtherField().getName().length()*2);
						if(e.getIndex() > tempRow)
							tempRow = e.getIndex();
					}
					row += tempRow+2;
					
				}//for member
			}//for year
			
			//summary
			WritableSheet writableSheet = JXLUtil.createWritableSheet(writableWorkbook, "SUMMARY", maxYear-2006);
			JXLUtil.addLabelCell(writableSheet, 0, 0, "SUMMARY", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
			JXLUtil.setColumnView(writableSheet, 0, 25);
			
			JXLUtil.addLabelCell(writableSheet, 0, 2, "Total No of Registrants", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
			JXLUtil.addLabelCell(writableSheet, 0, 3, "Total No of Volunteers", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
			JXLUtil.addLabelCell(writableSheet, 0, 4, "Total No of Hours", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
			JXLUtil.addLabelCell(writableSheet, 0, 5, "Total No of Beneficiaries", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));

			JXLUtil.addLabelCell(writableSheet, 1, 2, totalRegistrants.toString(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
			JXLUtil.addLabelCell(writableSheet, 1, 3, totalVolunteers.toString(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
			JXLUtil.addLabelCell(writableSheet, 1, 4, totalHours.toString(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
			JXLUtil.addLabelCell(writableSheet, 1, 5, totalBeneficiaries.toString(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
			
			
			writableWorkbook.write();
			writableWorkbook.close();
			
			//System.out.println("Excel creation completed.");
		download(filePath);
		return SUCCESS;
		}	
		return ERROR;
	}
	
	private String officeAddress;
	private String organizationName;
	public String downloadRegistrantFormsIave() throws Exception 
	{				
		int maxYear = Calendar.getInstance().get(Calendar.YEAR);
		if(submissionId != 0)
		{			
			Member mem = memberDelegate.find(this.submissionId);
			setMember(mem);
			if(mem == null) return ERROR;
			
			items= mem.getRegistrationItemOtherFields();	
			member=mem;
			
			if(items!=null)
				SortingUtil.sortBaseObject("otherField.sortOrder", true,items );
			else return ERROR;
			
			mapFieldValues = new TreeMap<String,String>();
			for(RegistrationItemOtherField e: items){
				mapFieldValues.put(e.getOtherField().getName(), e.getContent());
			}	
			
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			filePath =  locationPath + "reports";
		
			File writeDir = new File(filePath);
			
			WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "Registration Forms.xls");		
			writableWorkbook.createSheet("Organization Information", 0);
			WritableSheet orgInfoSheet = writableWorkbook.getSheet(0);
					
			filePath= writeDir+"/Registration Forms.xls";
			fileName = "Registration Forms.xls";
						
			WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
			WritableCellFormat titleCellFormat=new WritableCellFormat(wfobj);
		    titleCellFormat.setBackground(Colour.AQUA);		    
		    titleCellFormat.setWrap(true);
		    
		    WritableFont wfobj2=new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
			WritableCellFormat titleCellFormat2=new WritableCellFormat(wfobj2);	    
		    titleCellFormat2.setWrap(true);
		    titleCellFormat2.setVerticalAlignment(VerticalAlignment.TOP);
			
		    String checked="checked";
			int column = 0;
			int row = 0;
			
			//ORGANIZATION INFORMATION SHEET			
			JXLUtil.setColumnView(orgInfoSheet, 0, 25);
			JXLUtil.setColumnView(orgInfoSheet, 1, 70);
			
			JXLUtil.addLabelCell(orgInfoSheet, column, row, "LOGIN INFORMATION", titleCellFormat);
			orgInfoSheet.mergeCells(0, 0, 1, 0);
			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Username:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, member.getUsername(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "First Name:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, mapFieldValues.get("First Name"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Last Name:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, mapFieldValues.get("Last Name"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Email:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, mapFieldValues.get("E-mail Address"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			
			row++;
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "IDENTIFYING INFORMATION", titleCellFormat);
			orgInfoSheet.mergeCells(0, row, 1, row);
			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Name of Organization::", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, mapFieldValues.get("Organization Name"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Year Established:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "'"+mapFieldValues.get("Year Established"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Office Address:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, mapFieldValues.get("Office Address"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Phone Number:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, mapFieldValues.get("Phone Number"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Fax Number:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, mapFieldValues.get("Fax Number"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Mobile Number:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "'"+mapFieldValues.get("Mobile Number"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Network Affiliation:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "1) "+mapFieldValues.get("Network Affiliation1"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				JXLUtil.addLabelCell(orgInfoSheet, column+1, ++row, "2) "+mapFieldValues.get("Network Affiliation2"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				JXLUtil.addLabelCell(orgInfoSheet, column+1, ++row, "3) "+mapFieldValues.get("Network Affiliation3"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				JXLUtil.addLabelCell(orgInfoSheet, column+1, ++row, "4) "+mapFieldValues.get("Network Affiliation4"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				JXLUtil.addLabelCell(orgInfoSheet, column+1, ++row, "5) "+mapFieldValues.get("Network Affiliation5"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				JXLUtil.addLabelCell(orgInfoSheet, column+1, ++row, "6) "+mapFieldValues.get("Network Affiliation6"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			
			row++;
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "AGENCY", titleCellFormat);
			orgInfoSheet.mergeCells(0, row, 1, row);
			
				if(checked.equals(mapFieldValues.get("SEC"))){
					JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Name:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "Securities and Exchange Commission (SEC)", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Year:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "'"+mapFieldValues.get("SEC Year"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				}
				if(checked.equals(mapFieldValues.get("DSWD"))){
					JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Name:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "Department of Social Welfare and Development", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Year:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "'"+mapFieldValues.get("DSWD Year"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				}
				if(checked.equals(mapFieldValues.get("PCNC"))){
					JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Name:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "Philippine Council of NGO Certification (PCNC)", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Year:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "'"+mapFieldValues.get("PCNC Year"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				}
				if(checked.equals(mapFieldValues.get("CDA"))){
					JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Name:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "Cooperative Development Authority (CDA)", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Year:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "'"+mapFieldValues.get("CDA Year"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				}
				if(checked.equals(mapFieldValues.get("PNVSCA"))){
					JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Name:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "Philippine National Volunteer Service Coordinatinf Agency (PNVSCA)", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Year:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
					JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "'"+mapFieldValues.get("PNVSCA Year"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				}
				
			row++;
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "DESCRIPTION OF ORGANIZATION", titleCellFormat);
			orgInfoSheet.mergeCells(0, row, 1, row);
			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Type of Organization:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
		
			if(checked.equals(mapFieldValues.get("Government"))){
				JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "Government", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			}else if(checked.equals(mapFieldValues.get("Non-profit Organization"))){
				JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "Non-profit Organization", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			}else if(checked.equals(mapFieldValues.get("Academe-based"))){
				JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "Academe-based", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			}else if(checked.equals(mapFieldValues.get("Corporate"))){
				JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "Corporate", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			}else if(checked.equals(mapFieldValues.get("Other Types of Org"))){
				JXLUtil.addLabelCell(orgInfoSheet, column+1, row, "Other Types of Organization", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			}
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Narrative Description:", titleCellFormat2);
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, mapFieldValues.get("Narrative Description"), titleCellFormat2);
			
			row++;
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "SCOPE AND GEOGRAPHIC INFORMATION", titleCellFormat);
			orgInfoSheet.mergeCells(0, row, 1, row);
			
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Scrope:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			
			String scope="";
			if(checked.equals(mapFieldValues.get("Nationwide")))
				scope =scope + "Nationwide";
			if(checked.equals(mapFieldValues.get("Luzon")))
				scope =scope + " Luzon";
			if(checked.equals(mapFieldValues.get("Visayas")))
				scope =scope + " Visayas";
			if(checked.equals(mapFieldValues.get("Nationwide")))
				scope =scope + " Mindanao";
			
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, scope, JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "Area/s Covered:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			
			String area="";
			if(checked.equals(mapFieldValues.get("Province")))
				area =area +  "Province";
			if(checked.equals(mapFieldValues.get("City")))
				area =area + " City";
			if(checked.equals(mapFieldValues.get("Municipality")))
				area =area + " Municipality";
			if(checked.equals(mapFieldValues.get("Barangay")))
				area =area + " Barangay";
			
			JXLUtil.addLabelCell(orgInfoSheet, column+1, row, area, JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			
			row++;
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "UNITED NATIONS MILLENIUM DEVELOPMENT GOALS ", titleCellFormat);
			orgInfoSheet.mergeCells(0, row, 1, row);			
			
			if(checked.equals(mapFieldValues.get("Eradication of extreme proverty and hunger")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Eradication of extreme proverty and hunger", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Combating HIV/AIDS, malaria, and other diseases")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Combating HIV/AIDS, malaria, and other diseases", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Achievement of universal primary education")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Achievement of universal primary education", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Ensuring environmental sustainability")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Ensuring environmental sustainability", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Promotion of gender and equality and empowerment of women")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Promotion of gender and equality and empowerment of women", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Developing a global partnership for development")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Developing a global partnership for development", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Reduction of child mortality")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Reduction of child mortality", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Other UN Millenium Development Goals")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Other UN Millenium Development Goals", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			
			row++;
			JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "VOLUNTEER MANAGEMENT TRAININGS ", titleCellFormat);
			orgInfoSheet.mergeCells(0, row, 1, row);
			
			if(checked.equals(mapFieldValues.get("Recruitment and Selection of volunteers")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Recruitment and Selection of volunteers", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Monitoring and Evaluation")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Monitoring and Evaluation", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Orientation, Formation and Training of volunteers")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Orientation, Formation and Training of volunteers", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Volunteer Recognition")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Volunteer Recognition", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Volunteer Nurturance / Retention")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Volunteer Nurturance / Retention", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			if(checked.equals(mapFieldValues.get("Developing a global partnership for development")))
				JXLUtil.addLabelCell(orgInfoSheet, column, ++row, "- Other Volunteer Management Training", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			
			int sheetNumber=1;			
			WritableSheet surveySheet;
			/* 2007 - 2011 SURVEY FORMS */			
			for(int year=2007; year<=maxYear; year++) 
			{				
				row = 0;
				surveySheet = writableWorkbook.createSheet(year+" Survey", sheetNumber++);
				
				items=registrationDelegate.findAll(company, member,Integer.toString(year),null).getList();				
				mapFieldValues = new TreeMap<String,String>();
				for(RegistrationItemOtherField e: items){
					mapFieldValues.put(e.getOtherField().getName(), e.getContent());
				}
				
				JXLUtil.setColumnView(surveySheet, 0, 25);
				JXLUtil.setColumnView(surveySheet, 1, 25);
				JXLUtil.setColumnView(surveySheet, 2, 25);
				JXLUtil.setColumnView(surveySheet, 3, 25);
				JXLUtil.setColumnView(surveySheet, 4, 25);
				
				JXLUtil.addLabelCell(surveySheet, column, row++, year+" SURVEY FORM", titleCellFormat);
				surveySheet.mergeCells(0, 0, 4, 0);
				
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Thermatic Areas:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				if(checked.equals(mapFieldValues.get("Agriculture")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Agriculture", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Culture and Sports")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Culture and Sports", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Education")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Education", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Electoral Assitance")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Electoral Assitance", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Emergency Relief")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Emergency Relief", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Environment")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Environment", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Nutrition Food")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Nutrition Food", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Gender")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Gender", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Governance")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Governance", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("HIV / AIDS")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "HIV / AIDS", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Health")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Health", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Human Rights")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Human Rights", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));				
				if(checked.equals(mapFieldValues.get("Information and Communication Technologies (ICT)")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Information and Communication Technologies (ICT)", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Peace and conflict")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Peace and conflict", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Poverty reduction")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Poverty Reduction", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Other Thematic Areas")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Other Thematic Areas", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Sectors:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				if(checked.equals(mapFieldValues.get("Children and Youth")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Children and Youth", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Person with disabilities")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Person with disabilities", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Internal Refugees and Displaced Persons")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Internal Refugees and Displaced Persons", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Farmers")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Farmers", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Fisher folks")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Fisher folks", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Indigenous Peoples")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Indigenous Peoples", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Informal Settlers")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Informal Settlers", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Women")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Women", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Senior Citizens")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Senior Citizens", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Private Sector")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Private Sector", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Civil Society")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Civil Society", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				if(checked.equals(mapFieldValues.get("Other Sectors")))
					JXLUtil.addLabelCell(surveySheet, column+1, ++row, "Other Sectors", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));				
				
				row++;
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Fiscal Year:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));	
				String fiscalYear = "";
				if(mapFieldValues.get("Fiscal Year From") != null)
					fiscalYear = mapFieldValues.get("Fiscal Year From")+" - ";
				if(mapFieldValues.get("Fiscal Year To") != null)
					fiscalYear =fiscalYear + mapFieldValues.get("Fiscal Year To");
				JXLUtil.addLabelCell(surveySheet, column+1, row, fiscalYear, JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
		
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Total Number of Volunteers:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				JXLUtil.addLabelCell(surveySheet, column+1, row, mapFieldValues.get("Total Number Of Volunteers"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
			
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Total Male Volunteers:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				JXLUtil.addLabelCell(surveySheet, column+1, row, mapFieldValues.get("Sex Male"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Total Female Volunteers:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				JXLUtil.addLabelCell(surveySheet, column+1, row, mapFieldValues.get("Sex Female"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				
				//AGE RANGE
				row++;
				JXLUtil.addLabelCell(surveySheet, column, ++row, "AGE RANGE", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,true));	
				
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Adolescent 13-17 yrs old:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				JXLUtil.addLabelCell(surveySheet, column+1, row, mapFieldValues.get("No of Adolescent (13-17)"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Youth 18-35 yrs old:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				JXLUtil.addLabelCell(surveySheet, column+1, row, mapFieldValues.get("No of Youth (18-35)"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Adult 36-59 yrs old:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				JXLUtil.addLabelCell(surveySheet, column+1, row, mapFieldValues.get("No of Adult (36-59)"), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));
				
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Senior Citizens 60 yrs old and above:", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				JXLUtil.addLabelCell(surveySheet, column+1, row, mapFieldValues.get("No of Senior Citizens (60 and above)"), titleCellFormat2);
				
				//Computation of Volunteer Hours and Number of Beneficiaries
				row++;
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Computation of Volunteer Hours and Number of Beneficiaries", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,true));
				
				JXLUtil.addLabelCell(surveySheet, column, row, "No. of Volunteers", titleCellFormat);
				JXLUtil.addLabelCell(surveySheet, column+1, row, "No. of Days", titleCellFormat);
				JXLUtil.addLabelCell(surveySheet, column+2, row, "No. of Hours", titleCellFormat);
				JXLUtil.addLabelCell(surveySheet, column+3, row, "Total No. of Hours", titleCellFormat);
				JXLUtil.addLabelCell(surveySheet, column+4, row, "No. of Beneficiaries", titleCellFormat);
				
				setMapRepeatingFieldValues(new TreeMap<String,String>());
				setInfoRepeating(registrationDelegate.findAllWithIndexing(company, member, Integer.toString(year)).getList());
				if(getInfoRepeating()!=null &&  !getInfoRepeating().isEmpty()){
					for(RegistrationItemOtherField e: getInfoRepeating()){			
						getMapRepeatingFieldValues().put(e.getOtherField().getName()+e.getIndex(), e.getContent());
					}
				}
				
				WritableCellFormat titleCellFormat3=new WritableCellFormat(wfobj2);	    
			    titleCellFormat3.setAlignment(Alignment.CENTRE);
			    
			    Integer totalBeneficiaries = 0;
			    Double totalHours = 0.0;
			    				
			   	for(int i=0; i<mapRepeatingFieldValues.size(); i++) {
					//System.out.println("pumasok ");
			   		if(mapRepeatingFieldValues.get("No of Volunteers"+i) != null && mapRepeatingFieldValues.get("No of Volunteers"+i).length()>0){
						row++;
						JXLUtil.addLabelCell(surveySheet, column, row, mapRepeatingFieldValues.get("No of Volunteers"+i), titleCellFormat3);
						JXLUtil.addLabelCell(surveySheet, column+1, row, mapRepeatingFieldValues.get("No of Days"+i), titleCellFormat3);
						JXLUtil.addLabelCell(surveySheet, column+2, row, mapRepeatingFieldValues.get("No of Hours"+i), titleCellFormat3);
						JXLUtil.addLabelCell(surveySheet, column+3, row, mapRepeatingFieldValues.get("Total No of Hours"+i), titleCellFormat3);
						JXLUtil.addLabelCell(surveySheet, column+4, row, mapRepeatingFieldValues.get("No of Beneficiaries"+i), titleCellFormat3);
						
						if(mapRepeatingFieldValues.get("Total No of Hours"+i) != null && mapRepeatingFieldValues.get("Total No of Hours"+i).length()>0){
							totalHours += Double.parseDouble(mapRepeatingFieldValues.get("Total No of Hours"+i).trim());
						}
						if(mapRepeatingFieldValues.get("No of Beneficiaries"+i) != null && mapRepeatingFieldValues.get("No of Beneficiaries"+i).length()>0){
							totalBeneficiaries += Integer.parseInt(mapRepeatingFieldValues.get("No of Beneficiaries"+i).trim());
						}	
			   		}
				}	
			   	row = row+2;
			   	JXLUtil.addLabelCell(surveySheet, column, ++row, "Grand Total Number of Hours :", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				JXLUtil.addLabelCell(surveySheet, column+1, row, Double.toString(totalHours), titleCellFormat3);
				
				JXLUtil.addLabelCell(surveySheet, column, ++row, "Grand Total Number of Beneficiaries :", JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 10,false));			
				JXLUtil.addLabelCell(surveySheet, column+1, row, Integer.toString(totalBeneficiaries), titleCellFormat3);
			}
			writableWorkbook.write();
			writableWorkbook.close();
			
			//System.out.println("Excel creation completed.");
			download(filePath);
			return SUCCESS;
		}		
		return ERROR;
	}
	
	public String downloadSurveySummaryIave() throws Exception 
	{			
		List<Member> members = new ArrayList();
		int maxYear = Calendar.getInstance().get(Calendar.YEAR);
		int totalYearSurvey = maxYear - 2007;
		
		members = memberDelegate.findPurpose(company,"Registration");
		List<RegistrationItemOtherField> items2 = null;
		
		Double grandTotalRegistrants = 0.0, grandTotalVolunteers = 0.0, grandTotalHours = 0.0, grandTotalDays = 0.0, grandTotalBeneficiaries = 0.0;
		Double grandTotalAdolescent = 0.0, grandTotalYouth = 0.0, grandTotalAdult = 0.0, grandTotalSenior = 0.0;
		Double grandTotalMale = 0.0, grandTotalFemale = 0.0;
		Integer[] grandTotalThematicAreas = new Integer[16];
		Integer[] grandTotalSectors = new Integer[12];
		for(int i=0; i<grandTotalThematicAreas.length;i++)
			grandTotalThematicAreas[i] = 0;			
		for(int i=0; i<grandTotalSectors.length;i++)
			grandTotalSectors[i] = 0;
		
		Double totalRegistrants = 0.0, totalVolunteers = 0.0, totalHours = 0.0, totalDays = 0.0, totalBeneficiaries = 0.0;
		Double totalAdolescent = 0.0, totalYouth = 0.0, totalAdult = 0.0, totalSenior = 0.0;
		Double totalMale = 0.0, totalFemale = 0.0;	
		Integer[] totalThematicAreas = new Integer[16];
		Integer[] totalSectors = new Integer[12];
		
		WritableCellFormat wcf1=new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
		wcf1.setAlignment(Alignment.RIGHT);
				
		if(members.size()!=0){
			totalRegistrants = (double)members.size(); 
						
			//create excel file
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			filePath =  locationPath + "reports";	
			File writeDir = new File(filePath);
			WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "Surveys Summary.xls");
			
			filePath= writeDir+"/Surveys Summary.xls";
			fileName = "Surveys Summary.xls";
			for(Integer dataYear = 2007; dataYear <= maxYear; dataYear++)
			{
				//totalRegistrants = 0; 
				totalVolunteers = 0.0; totalHours = 0.0; totalDays = 0.0; totalBeneficiaries = 0.0;
				totalAdolescent = 0.0; totalYouth = 0.0; totalAdult = 0.0; totalSenior = 0.0;
				totalMale = 0.0; totalFemale = 0.0;	
								
				for(int i=0; i<totalThematicAreas.length;i++)
					totalThematicAreas[i] = 0;			
				for(int i=0; i<totalSectors.length;i++)
					totalSectors[i] = 0;
				
				WritableSheet writableSheet = JXLUtil.createWritableSheet(writableWorkbook, dataYear.toString()+" Survey", totalYearSurvey-(maxYear-dataYear));
			
				WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
				WritableCellFormat titleCellFormat=new WritableCellFormat(wfobj);
			    titleCellFormat.setBackground(Colour.AQUA);		    
			    titleCellFormat.setWrap(true);
			    
			    	    
			    //ORGANIZATION INFORMATION SHEET			
				JXLUtil.setColumnView(writableSheet, 0, 35);
				JXLUtil.setColumnView(writableSheet, 1, 25);
				
				JXLUtil.addLabelCell(writableSheet, 0, 0, dataYear+" SURVEY", titleCellFormat);
				writableSheet.mergeCells(0, 0, 1, 0);
				
				List<OtherField> temp2 = otherFieldDelegate.find(company);				
				if(temp2!=null)
					SortingUtil.sortBaseObject("sortOrder", true,temp2 );
							    
				int row=1, col=0;
								
				for(Member temp : members)
				{					
					List<RegistrationItemOtherField> items;
					//year specific values					
					items = registrationDelegate.findAllWithNote(company, temp, dataYear.toString()).getList();
					if(items!=null) 
						SortingUtil.sortBaseObject("otherField.sortOrder", true,items);
					if(items.size()>0)
						items2 = items;
					
					for(RegistrationItemOtherField temp4 : items){		
						//JXLUtil.addLabelCell(writableSheet, temp4.getOtherField().getSortOrder()-1, row, temp4.getContent(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
						//JXLUtil.setColumnView(writableSheet, temp4.getOtherField().getSortOrder()-1, temp4.getOtherField().getName().length()*2);
						
						if(temp4.getOtherField().getSortOrder()==88)
							if(StringUtils.isNotBlank(temp4.getContent()) && Character.isDigit(temp4.getContent().charAt(0)))
								totalVolunteers += Double.parseDouble(temp4.getContent().replace(",", ""));
						if(temp4.getOtherField().getSortOrder()==101)
							if(StringUtils.isNotBlank(temp4.getContent()) && Character.isDigit(temp4.getContent().charAt(0)))
								totalHours += Double.parseDouble(temp4.getContent().replace(",", ""));
						if(temp4.getOtherField().getSortOrder()==102)
							if(StringUtils.isNotBlank(temp4.getContent()) && Character.isDigit(temp4.getContent().charAt(0)))
								totalBeneficiaries += Double.parseDouble(temp4.getContent().replace(",", ""));
						if(temp4.getOtherField().getSortOrder()==96)
							if(StringUtils.isNotBlank(temp4.getContent()) && Character.isDigit(temp4.getContent().charAt(0)))
								totalDays += Double.parseDouble(temp4.getContent().replace(",", ""));
						
						//MALE AND FEMALE
						if(temp4.getOtherField().getSortOrder()==90)
							if(StringUtils.isNotBlank(temp4.getContent()) && Character.isDigit(temp4.getContent().charAt(0)))
								totalFemale += Double.parseDouble(temp4.getContent().replace(",", ""));
						if(temp4.getOtherField().getSortOrder()==89)
							if(StringUtils.isNotBlank(temp4.getContent()) && Character.isDigit(temp4.getContent().charAt(0)))
								totalMale += Double.parseDouble(temp4.getContent().replace(",", ""));
						
						//AGE RANGE
						if(temp4.getOtherField().getSortOrder()==91)
							if(StringUtils.isNotBlank(temp4.getContent()) && Character.isDigit(temp4.getContent().charAt(0)))
								totalAdolescent += Double.parseDouble(temp4.getContent().replace(",", ""));
						if(temp4.getOtherField().getSortOrder()==92)
							if(StringUtils.isNotBlank(temp4.getContent()) && Character.isDigit(temp4.getContent().charAt(0)))
								totalYouth += Double.parseDouble(temp4.getContent().replace(",", ""));
						if(temp4.getOtherField().getSortOrder()==93)
							if(StringUtils.isNotBlank(temp4.getContent()) && Character.isDigit(temp4.getContent().charAt(0)))
								totalAdult += Double.parseDouble(temp4.getContent().replace(",", ""));
						if(temp4.getOtherField().getSortOrder()==94)
							if(StringUtils.isNotBlank(temp4.getContent()) && Character.isDigit(temp4.getContent().charAt(0)))
								totalSenior += Double.parseDouble(temp4.getContent().replace(",", ""));
												
						//THEMATIC AREAS
						if(temp4.getOtherField().getSortOrder()==58)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[0] += 1;
						if(temp4.getOtherField().getSortOrder()==59)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[1] += 1;
						if(temp4.getOtherField().getSortOrder()==60)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[2] += 1;
						if(temp4.getOtherField().getSortOrder()==61)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[3] += 1;
						if(temp4.getOtherField().getSortOrder()==62)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[4] += 1;
						if(temp4.getOtherField().getSortOrder()==63)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[5] += 1;
						if(temp4.getOtherField().getSortOrder()==64)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[6] += 1;
						if(temp4.getOtherField().getSortOrder()==65)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[7] += 1;					
						if(temp4.getOtherField().getSortOrder()==66)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[8] += 1;
						if(temp4.getOtherField().getSortOrder()==67)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[9] += 1;
						if(temp4.getOtherField().getSortOrder()==68)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[10] += 1;
						if(temp4.getOtherField().getSortOrder()==69)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[11] += 1;
						if(temp4.getOtherField().getSortOrder()==70)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[12] += 1;
						if(temp4.getOtherField().getSortOrder()==71)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[13] += 1;
						if(temp4.getOtherField().getSortOrder()==72)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[14] += 1;
						if(temp4.getOtherField().getSortOrder()==73)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalThematicAreas[15] += 1;
						
						//SECTORS
						if(temp4.getOtherField().getSortOrder()==75)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[0] += 1;
						if(temp4.getOtherField().getSortOrder()==76)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[1] += 1;
						if(temp4.getOtherField().getSortOrder()==77)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[2] += 1;
						if(temp4.getOtherField().getSortOrder()==78)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[3] += 1;
						if(temp4.getOtherField().getSortOrder()==79)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[4] += 1;
						if(temp4.getOtherField().getSortOrder()==80)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[5] += 1;
						if(temp4.getOtherField().getSortOrder()==81)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[6] += 1;
						if(temp4.getOtherField().getSortOrder()==82)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[7] += 1;						
						if(temp4.getOtherField().getSortOrder()==83)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[8] += 1;
						if(temp4.getOtherField().getSortOrder()==84)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[9] += 1;
						if(temp4.getOtherField().getSortOrder()==85)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[10] += 1;
						if(temp4.getOtherField().getSortOrder()==86)
							if(StringUtils.isNotBlank(temp4.getContent()))
								totalSectors[11] += 1;
					}					
				}//for member				
				
				//grandTotalRegistrants += totalRegistrants; 	
				grandTotalVolunteers += totalVolunteers; 
				grandTotalHours += totalHours; 
				grandTotalDays += totalDays; 
				grandTotalBeneficiaries += totalBeneficiaries;
				
				grandTotalMale += totalMale; 
				grandTotalFemale += totalFemale;
				
				grandTotalAdolescent += totalAdolescent; 
				grandTotalYouth += totalYouth; 
				grandTotalAdult += totalAdult; 
				grandTotalSenior += totalSenior;
				
				for(int i=0; i<totalThematicAreas.length;i++)
					grandTotalThematicAreas[i] += totalThematicAreas[i];
				
				for(int i=0; i<totalSectors.length;i++)
					grandTotalSectors[i] += totalSectors[i];
				
				row++;
				JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Registrants:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
				JXLUtil.addNumberCell(writableSheet, col+1, row++, totalRegistrants, wcf1);
				JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Volunteers:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
				JXLUtil.addNumberCell(writableSheet, col+1, row++, totalVolunteers, wcf1);
				JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Hours:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
				JXLUtil.addNumberCell(writableSheet, col+1, row++, totalHours, wcf1);
				JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Beneficiaries:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
				JXLUtil.addNumberCell(writableSheet, col+1, row++, totalBeneficiaries,wcf1);
				
				//////////////
				row++;
				JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Male Volunteers:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
				JXLUtil.addNumberCell(writableSheet, col+1, row++, totalMale, wcf1);
				JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Female Volunteers:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
				JXLUtil.addNumberCell(writableSheet, col+1, row++, totalFemale, wcf1);
				
				///////////////
				row++;
				JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Adolescent (13-17):", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
				JXLUtil.addNumberCell(writableSheet, col+1, row++, totalAdolescent, wcf1);
				JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Youth (18-35):", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
				JXLUtil.addNumberCell(writableSheet, col+1, row++, totalYouth, wcf1);
				JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Adult (36-59):", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
				JXLUtil.addNumberCell(writableSheet, col+1, row++, totalAdult, wcf1);
				JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Senior Citizens (60 and above):", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
				JXLUtil.addNumberCell(writableSheet, col+1, row++, totalSenior, wcf1);
				
				row++;
				JXLUtil.addLabelCell(writableSheet, col, row++, "THEMATIC AREAS:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12, true));
				
				int index=0, sortOrder=58;
				for(RegistrationItemOtherField temp5 : items2){	
					if(temp5.getOtherField().getSortOrder()==sortOrder && (temp5.getOtherField().getSortOrder()<= 73)) {
						JXLUtil.addLabelCell(writableSheet, col, row, temp5.getOtherField().getName(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
						JXLUtil.addNumberCell(writableSheet, col+1, row++, totalThematicAreas[index].doubleValue(), wcf1);
						index++; sortOrder++;
					}				
				}			
				row++;
				JXLUtil.addLabelCell(writableSheet, col, row++, "SECTORS:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12, true));
				
				index=0; sortOrder=75;
				for(RegistrationItemOtherField temp5 : items2){	
					if(temp5.getOtherField().getSortOrder()==sortOrder&& (temp5.getOtherField().getSortOrder()<= 86)) {
						JXLUtil.addLabelCell(writableSheet, col, row, temp5.getOtherField().getName(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
						JXLUtil.addNumberCell(writableSheet, col+1, row++, totalSectors[index].doubleValue(), wcf1);
						index++; sortOrder++;
					}				
				}
				
			}//for year
			
			///////////////////////////
			grandTotalRegistrants += totalRegistrants;
			
			//SUMMARY
			WritableSheet writableSheet = JXLUtil.createWritableSheet(writableWorkbook, "SUMMARY", maxYear-2006);
			
			WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			WritableCellFormat titleCellFormat=new WritableCellFormat(wfobj);
		    titleCellFormat.setBackground(Colour.AQUA);		    
		    titleCellFormat.setWrap(true);
			JXLUtil.addLabelCell(writableSheet, 0, 0, "SUMMARY", titleCellFormat);
			writableSheet.mergeCells(0, 0, 1, 0);
			
			JXLUtil.setColumnView(writableSheet, 0, 35);
			JXLUtil.setColumnView(writableSheet, 1, 25);
			
			int row = 1, col = 0;
			row++;
			JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Registrants:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
			JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalRegistrants, wcf1);
			JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Volunteers:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
			JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalVolunteers, wcf1);
			JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Hours:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
			JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalHours, wcf1);
			JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Beneficiaries:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
			JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalBeneficiaries, wcf1);
			
			//////////////
			row++;
			JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Male Volunteers:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
			JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalMale, wcf1);
			JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Female Volunteers:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
			JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalFemale, wcf1);
			
			///////////////
			row++;
			JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Adolescent (13-17):", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
			JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalAdolescent, wcf1);
			JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Youth (18-35):", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
			JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalYouth, wcf1);
			JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Adult (36-59):", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
			JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalAdult, wcf1);
			JXLUtil.addLabelCell(writableSheet, col, row, "Total No. of Senior Citizens (60 and above):", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
			JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalSenior, wcf1);
			
			row++;
			JXLUtil.addLabelCell(writableSheet, col, row++, "THEMATIC AREAS:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12, true));
			
			int index=0, sortOrder=58;
			for(RegistrationItemOtherField temp5 : items2){	
				if(temp5.getOtherField().getSortOrder()==sortOrder && (temp5.getOtherField().getSortOrder()<= 73)) {
					JXLUtil.addLabelCell(writableSheet, col, row, temp5.getOtherField().getName(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
					JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalThematicAreas[index].doubleValue(), wcf1);
					index++; sortOrder++;
				}				
			}			
			row++;
			JXLUtil.addLabelCell(writableSheet, col, row++, "SECTORS:", JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12, true));
			
			index=0; sortOrder=75;
			for(RegistrationItemOtherField temp5 : items2){	
				if(temp5.getOtherField().getSortOrder()==sortOrder&& (temp5.getOtherField().getSortOrder()<= 86)) {
					JXLUtil.addLabelCell(writableSheet, col, row, temp5.getOtherField().getName(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 12));
					JXLUtil.addNumberCell(writableSheet, col+1, row++, grandTotalSectors[index].doubleValue(), wcf1);
					index++; sortOrder++;
				}				
			}
			
			writableWorkbook.write();
			writableWorkbook.close();
			
			//System.out.println("Excel creation completed.");
			download(filePath);
			return SUCCESS;
		}	
		return ERROR;
	}
	
	public String downloadRegistrantsSummaryIave() throws Exception 
	{			
		List<Member> members = memberDelegate.findPurpose(company,"Registration");
		List<RegistrationItemOtherField> items2 = null;
		
		String[] header = {"LOGIN INFORMATION", "IDENTIFYING INFORMATION", "AGENCY", "DESCRIPTION OF ORGANIZATION", "SCOPE AND GEOGRAPHIC INFORMATION", 
						   "UNITED NATIONS MILLENIUM DEVELOPMENT GOALS", "VOLUNTEER MANAGEMENT TRAININGS"};
		
		String[] subHeader = {"Username", "First Name", "Last Name", "Email", "Name of Organization", "Year Established", "Office Address", 
							  "Phone Number", "Fax Number", "Mobile Number", "Network Affiliation", "Name", "Year", "Type of Organization", 
							  "Narrative Description", "Scope", "Area/s Covered"};
		
		String[] agency = {"SEC", "DSWD", "PCNC", "CDA", "PNVSCA"};
		String[] orgType = {"Government", "Non-profit Organization", "Academe-based", "Corporate", "Other Types of Org"};
		
		String[] scope = {"Nationwide", "Luzon", "Visayas", "Mindanao"};
		String[] areas = {"Province", "City", "Municipality", "Barangay"};
		String[] goals = {"Eradication of extreme proverty and hunger", "Combating HIV/AIDS, malaria, and other diseases", 
						  "Achievement of universal primary education", "Ensuring environmental sustainability", 
						  "Promotion of gender and equality and empowerment of women", "Developing a global partnership for development",
						  "Reduction of child mortality", "Other UN Millenium Development Goals"};
		String[] trainings = {"Recruitment and Selection of volunteers", "Monitoring and Evaluation", 
						      "Orientation, Formation and Training of volunteers", "Volunteer Recognition", 
						      "Volunteer Nurturance / Retention", "Other Volunteer Management Training"};
		int[] length = {25, 25, 25, 30, 30, 20, 50, 25, 25, 25, 60, 20, 15, 30, 50, 25, 25};
		
		int row=0, col=0;
		if(members.size()!=0){
									
			//create excel file
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			filePath =  locationPath + "reports";
			new File(locationPath).mkdir();
			File writeDir = new File(filePath);			
			
			WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "IAVE Registrants.xls");
			WritableSheet writableSheet = JXLUtil.createWritableSheet(writableWorkbook, "List of Registrants", 1);
			
			filePath= writeDir+"/IAVE Registrants.xls";
			fileName = "IAVE Registrants.xls";
			
			//Cell Format
			WritableCellFormat wcf1=new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
			wcf1.setAlignment(Alignment.RIGHT);
			
			//Cell Format
			WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD);
			WritableFont wfobj2=new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			
			wfobj.setColour(Colour.BLACK);
			WritableCellFormat subTitleCellFormat = new WritableCellFormat(wfobj2);
			subTitleCellFormat.setBackground(Colour.GRAY_25);
			
			wfobj.setColour(Colour.WHITE);
			WritableCellFormat[] titleCellFormat = new WritableCellFormat[7];			
			
			for(int i=0; i<7; i++){
				titleCellFormat[i] = new WritableCellFormat(wfobj);
			}
			//make Header
			writableSheet.mergeCells(0, 0, 3, 0);
			titleCellFormat[0].setBackground(Colour.AQUA);	
			JXLUtil.addLabelCell(writableSheet, 0, row, header[0], titleCellFormat[0]);	
			
			writableSheet.mergeCells(4, 0, 10, 0);			
			titleCellFormat[1].setBackground(Colour.BLUE_GREY);	
			JXLUtil.addLabelCell(writableSheet, 4, row, header[1], titleCellFormat[1]);	
			
			writableSheet.mergeCells(11, 0, 12, 0);			
			titleCellFormat[2].setBackground(Colour.GREEN);		
			JXLUtil.addLabelCell(writableSheet, 11, row, header[2], titleCellFormat[2]);	
			
			writableSheet.mergeCells(13, 0, 14, 0);			
			titleCellFormat[3].setBackground(Colour.RED);	
			JXLUtil.addLabelCell(writableSheet, 13, row, header[3], titleCellFormat[3]);	
			
			writableSheet.mergeCells(15, 0, 16, 0);			
			titleCellFormat[4].setBackground(Colour.PLUM);		
			JXLUtil.addLabelCell(writableSheet, 15, row, header[4], titleCellFormat[4]);	
			
			writableSheet.mergeCells(17, 0, 17, 1);			
			titleCellFormat[5].setBackground(Colour.TAN);		
			JXLUtil.addLabelCell(writableSheet, 17, row, header[5], titleCellFormat[5]);	
			JXLUtil.setColumnView(writableSheet, 17,70);
			
			writableSheet.mergeCells(18, 0, 18, 1);			
			titleCellFormat[6].setBackground(Colour.SEA_GREEN);		
			JXLUtil.addLabelCell(writableSheet, 18, row, header[6], titleCellFormat[6]);
			JXLUtil.setColumnView(writableSheet, 18,70);
			
			//sub header
			col=0; row=1;
			for(int i=0; i<subHeader.length; i++) {
				JXLUtil.addLabelCell(writableSheet, col, row, subHeader[i], subTitleCellFormat);
				JXLUtil.setColumnView(writableSheet, col++,length[i]);
			}
			JXLUtil.addLabelCell(writableSheet, col++, row, "", subTitleCellFormat);
			JXLUtil.addLabelCell(writableSheet, col++, row, "", subTitleCellFormat);
			
			RegistrationItemOtherField field;
			int sortOrder=0, count=0, num=1;
			col=0; row=2;
			for(Member member : members)
			{
				//System.out.println("MEMO: "+member);
				count++;
				col=0;
				//LOGIN INFORMATION
				JXLUtil.addLabelCell(writableSheet, col++, row, count+") "+member.getUsername(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
				field = registrationDelegate.findByName(company, "First Name", member.getId());
				//System.out.println("MEMO: "+field);
				JXLUtil.addLabelCell(writableSheet, col++, row, ((field == null)? "" : field.getContent()), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
				field = registrationDelegate.findByName(company, "Last Name", member.getId());
				JXLUtil.addLabelCell(writableSheet, col++, row, ((field == null)? "" : field.getContent()), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
				field = registrationDelegate.findByName(company, "E-mail Address", member.getId());
				JXLUtil.addLabelCell(writableSheet, col++, row, ((field == null)? "" : field.getContent()), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
				
				//IDENTIFYING INFORMATION
				field = registrationDelegate.findByName(company, "Organization Name", member.getId());
				JXLUtil.addLabelCell(writableSheet, col++, row, ((field == null)? "" : field.getContent()), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
				field = registrationDelegate.findByName(company, "Year Established", member.getId());
				JXLUtil.addLabelCell(writableSheet, col++, row, ((field == null)? "" : field.getContent()), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));				
				field = registrationDelegate.findByName(company, "Office Address", member.getId());
				JXLUtil.addLabelCell(writableSheet, col++, row, ((field == null)? "" : field.getContent()), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));				
				field = registrationDelegate.findByName(company, "Phone Number", member.getId());
				JXLUtil.addLabelCell(writableSheet, col++, row, ((field == null)? "" : field.getContent()), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
				field = registrationDelegate.findByName(company, "Fax Number", member.getId());
				JXLUtil.addLabelCell(writableSheet, col++, row, ((field == null)? "" : field.getContent()), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));				
				field = registrationDelegate.findByName(company, "Mobile Number", member.getId());
				JXLUtil.addLabelCell(writableSheet, col++, row, ((field == null)? "" : field.getContent()), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
				
				//Network Affiliation	
				int tempRow = row;
				for(int i=1; i<=5; i++) {
					field = registrationDelegate.findByName(company, "Network Affiliation"+i, member.getId());
					if(field != null) {
						if(!field.getContent().isEmpty())
								JXLUtil.addLabelCell(writableSheet, col, tempRow++, i+") "+field.getContent(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
					}
				}
				
				//Agency	
				col++;
				num=1;
				tempRow = row;
				for(int i=0; i<agency.length; i++) {
					field = registrationDelegate.findByName(company, agency[i], member.getId());
					if(field != null) {
						if(!field.getContent().isEmpty()) {
							JXLUtil.addLabelCell(writableSheet, col, tempRow, (num++)+") "+agency[i], JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
							field = registrationDelegate.findByName(company, agency[i]+" Year", member.getId());
							JXLUtil.addLabelCell(writableSheet, col+1, tempRow++, field.getContent(), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
						}
					}
				}
				
				//DESCRIPTION OF ORGANIZATION
				col+=2;
				tempRow = row;
				for(int i=0; i<orgType.length; i++) {
					field = registrationDelegate.findByName(company, orgType[i], member.getId());
					if(field != null) {
						if(!field.getContent().isEmpty()) {
							JXLUtil.addLabelCell(writableSheet, col, tempRow++, orgType[i], JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
						}
					}
				}
				field = registrationDelegate.findByName(company, "Narrative Description", member.getId());
				JXLUtil.addLabelCell(writableSheet, col+1, row, ((field == null)? "" : field.getContent()), JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
				
				//SCOPE
				col+=2;
				num=1;
				tempRow = row;
				for(int i=0; i<scope.length; i++) {
					field = registrationDelegate.findByName(company, scope[i], member.getId());
					if(field != null) {
						if(!field.getContent().isEmpty()) {
							JXLUtil.addLabelCell(writableSheet, col, tempRow++, (num++)+") "+scope[i], JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
						}
					}
				}
				num=1;
				tempRow = row;
				for(int i=0; i<areas.length; i++) {
					field = registrationDelegate.findByName(company, areas[i], member.getId());
					if(field != null) {
						if(!field.getContent().isEmpty()) {
							JXLUtil.addLabelCell(writableSheet, col+1, tempRow++, (num++)+") "+areas[i], JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));
						}
					}
				}
				
				//GOALS	
				col+=2;
				num=1;
				tempRow = row;
				for(int i=0; i<goals.length; i++) {
					field = registrationDelegate.findByName(company, goals[i], member.getId());
					if(field != null) {
						if(!field.getContent().isEmpty()) {
							JXLUtil.addLabelCell(writableSheet, col, tempRow++, (num++)+") "+goals[i], JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));						
						}
					}
				}
				
				//TRAININGS	
				col++;
				num=1;
				tempRow = row;
				for(int i=0; i<trainings.length; i++) {
					field = registrationDelegate.findByName(company, trainings[i], member.getId());
					if(field != null) {
						if(!field.getContent().isEmpty()) {
							JXLUtil.addLabelCell(writableSheet, col, tempRow++, (num++)+") "+trainings[i], JXLUtil.getWritableCellFormat(WritableFont.ARIAL, 11));						
						}
					}
				}
				row+=9;
			}			
			writableWorkbook.write();
			writableWorkbook.close();
			
			//System.out.println("Excel creation completed.");
			download(filePath);
			return SUCCESS;
		}	
		return ERROR;
	}
	
	public String downloadRegistrants() throws Exception {
		List<Member> members = new ArrayList();
		
		if (request.getParameter("filter").equals("all")){
			members = memberDelegate.findPurpose(company,"Registration");
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
	        members = memberDelegate.findPurposeByDate(company,"Registration" ,fromDate, toDate);
			logger.debug("\n\n\nEmail list for " + company.getName() + ":   " + members.size());			
		}
	
		if(members.size()!=0){	
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "WEB-INF" + File.separatorChar;
			new File(locationPath).mkdir();
			filePath =  locationPath + "reports";
			filePath="C:/";//Do not remove 
		
			File writeDir = new File(filePath);
			WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "test2.xls");									
			WritableSheet writableSheet = JXLUtil.createWritableSheet(writableWorkbook, "WebToGo", 0);
			filePath= writeDir+"/test2.xls";
			//JXLUtil.addImage(writableSheet, 0, 0, 5, 5, "C:/Users/Vincent/Desktop/Photo0003.jpg");
			int count=0;
			List<RegistrationItemOtherField>  temp2 = members.get(0).getRegistrationItemOtherFields();
			if(temp2!=null)
				SortingUtil.sortBaseObject("otherField.sortOrder", true,temp2 );
			for(RegistrationItemOtherField temp3 : temp2){		
				JXLUtil.addLabelCell(writableSheet, count, 0, temp3.getOtherField().getName(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8,true));
				JXLUtil.setColumnView(writableSheet, count, temp3.getOtherField().getName().length()*2);
				count++;
			}		
			
			this.setInitCompanies();
			int row=2, col=0;
			
			List<RegistrationItemOtherField> items;
			for(Member temp : members){		
				col=0;
				items=temp.getRegistrationItemOtherFields();
				if(items!=null)
					SortingUtil.sortBaseObject("otherField.sortOrder", true,items );
				for(RegistrationItemOtherField registrations : items){
					if(names2.contains(registrations.getOtherField().getName())){
						JXLUtil.addLabelCell(writableSheet, col, row,company.getServerName()+"/attachments/registrations/"+registrations.getContent(), JXLUtil.getWritableCellFormat(WritableFont.TIMES, 8));
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
		download(filePath);
		return SUCCESS;
		}	
		return ERROR;
	}
	
	public void download(String filePath) throws Exception {
		File file = new File(filePath);
		if(!file.exists()) {
			logger.fatal("Unabled to locate file: " + filePath);
		}
		
		fInStream = new FileInputStream(file);
		contentLength = file.length();
	}

	public void setCompany(Company company) {
		this.company = company;
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
		this.totalItems = memberDelegate.findPurpose(company,"Registration").size();
	}
	
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
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

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setItems(List<RegistrationItemOtherField> items) {
		this.items = items;
	}

	public List<RegistrationItemOtherField> getItems() {
		return items;
	}

	public void setSubmissionId(long submissionId) {
		this.submissionId = submissionId;
	}

	public long getSubmissionId() {
		return submissionId;
	}

	public void setBrightSchools(String[] brightSchools) {
		this.brightSchools = brightSchools;
	}

	public String[] getBrightSchools() {
		return brightSchools;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

	public void setMapFileName(Map<String, String> mapFileName) {
		this.mapFileName = mapFileName;
	}

	public Map<String, String> getMapFileName() {
		return mapFileName;
	}

	public void setMapFieldValues(Map<String, String> mapFieldValues) {
		this.mapFieldValues = mapFieldValues;
	}

	public Map<String, String> getMapFieldValues() {
		return mapFieldValues;
	}

	@Override
	public void setUser(User user) {
		this.user=user;
	}

	public void setInfoRepeating(List<RegistrationItemOtherField> infoRepeating) {
		this.infoRepeating = infoRepeating;
	}

	public List<RegistrationItemOtherField> getInfoRepeating() {
		return infoRepeating;
	}

	public void setMapRepeatingFieldValues(Map<String, String> mapRepeatingFieldValues) {
		this.mapRepeatingFieldValues = mapRepeatingFieldValues;
	}

	public Map<String, String> getMapRepeatingFieldValues() {
		return mapRepeatingFieldValues;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYear() {
		return year;
	}

	public void setYearLimit(Integer yearLimit) {
		this.yearLimit = yearLimit;
	}

	public Integer getYearLimit() {
		return yearLimit;
	}

	/**
	 * @return the officeAddress
	 */
	public String getOfficeAddress() {
		return officeAddress;
	}

	/**
	 * @param officeAddress the officeAddress to set
	 */
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}	
}
