package com.ivant.cms.action.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MemberPageFileDelegate;
import com.ivant.cms.delegate.MemberTypeDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.MultiPageFileDelegate;
import com.ivant.cms.delegate.MultiPageLanguageDelegate;
import com.ivant.cms.delegate.PageFileDelegate;
import com.ivant.cms.delegate.PageImageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.SinglePageLanguageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.MemberPageFile;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.PageFile;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.SinglePageLanguage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.ApplicationConstants;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class SinglePageAction extends ActionSupport implements Preparable, ServletContextAware,
				ServletRequestAware, UserAware, CompanyAware {
	
	private static final long serialVersionUID = -244217747366834302L;
	private static final Logger logger = Logger.getLogger(SinglePageAction.class);
	private static SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private static final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private static final PageImageDelegate pageImageDelegate = PageImageDelegate.getInstance();
	private static final MultiPageFileDelegate multiPageFileDelegate = MultiPageFileDelegate.getInstance();	
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private static final PageFileDelegate pageFileDelegate = PageFileDelegate.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final LogDelegate logDelegate = LogDelegate.getInstance();
	private LanguageDelegate languageDelegate = LanguageDelegate.getInstance();
	private SinglePageLanguageDelegate singlePageLanguageDelegate = SinglePageLanguageDelegate.getInstance();
	private MultiPageLanguageDelegate multiPageLanguageDelegate = MultiPageLanguageDelegate.getInstance();
	private ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private MemberTypeDelegate memberTypeDelegate = MemberTypeDelegate.getInstance();
	private MemberPageFileDelegate memberPageFileDelegate = MemberPageFileDelegate.getInstance();
	private ItemComment comment;
	private User user;


	private Company company;
	private CompanySettings companySettings;
	private HttpServletRequest request;
	private ServletContext servletContext;
				
	private SinglePage singlePage;
	private MultiPage multiPage;
	private PageImage image;
	private PageFile pageFile;
	private MultiPageFile multiPageFile;
	
	private File[] files;
	private String[] contentTypes;
	private String[] filenames; 
	
	private String itemDate;
	private Date iDate;
	private List<MultiPageFile> multiPageFiles;
	private List<PageFile> singlePageFiles;
	private Integer invalidFiles;
	private List<Language> languages;
	private List<PageImage> sortedImagesByTitle;
	private String datePublished;
	
	//for hpdsked Only
	private Group group;
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private List<Category> categories;
	private static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private List<Category> categoriesList;
	private List<Category> sortedCategoriesList;
	private List<CategoryItem> items;
	private int page;
	private int itemsPerPage;
	private Category category;
	private String[] memberTypeIdList;
	
	///
	
	private List<Log> logList;
	
	public List<Log> getLogList(){
		return logList;
	}
	
	public void prepare() throws Exception {
		setLanguages(company.getLanguages());
		companySettings = company.getCompanySettings();
		try {
			long singlePageId = Long.parseLong(request.getParameter("singlepage_id"));
			singlePage = singlePageDelegate.find(singlePageId);
			
			List<ItemComment> pagecomments = itemCommentDelegate.getCommentsByPage(singlePage, Order.desc("createdOn")).getList();
			//System.out.println("pagecomments size "+pagecomments.size());
			request.setAttribute("pagecomments", pagecomments);
		}
		catch(Exception e) {
			singlePage = new SinglePage();
			User persistedUser = userDelegate.load(user.getId());
			
			singlePage.setCreatedBy(persistedUser);
			singlePage.setCompany(company);
		}	
		
		if(request.getParameter("language")!=null && singlePage!=null){
			singlePage.setLanguage(languageDelegate.find(request.getParameter("language"),company));
		}
				
		try {
			long imageId = Long.parseLong(request.getParameter("image_id"));
			image = pageImageDelegate.find(imageId);
		}
		catch(Exception e) {}
		
		// try to get the page file
		try {
			if(request.getParameter("file_id") != null) {
				long fileId = Long.parseLong(request.getParameter("file_id"));
				pageFile = pageFileDelegate.find(fileId);
			}

			if(request.getParameter("mfile_id") != null) {
				long mfileId = Long.parseLong(request.getParameter("mfile_id"));
				multiPageFile = multiPageFileDelegate.find(mfileId);
			}
		}
		catch(Exception e) {}
		
		
		// try to create the multi page if multipage_id is existing
		try {
			long multiPageId = Long.parseLong(request.getParameter("multipage_id"));
			multiPage = multiPageDelegate.find(multiPageId);
			singlePage.setParent(multiPage);	
			singlePage.setHasFile(multiPage.getHasFile());
		}
		catch(Exception e) {}
		if(request.getParameter("language")!=null && multiPage!=null){
			multiPage.setLanguage(languageDelegate.find(request.getParameter("language"),company));
		}
		
		// set a default sort order if 0
		if(singlePage.getSortOrder() == 0) {
			ObjectList<SinglePage> singlePageList = singlePageDelegate.findAllWithPaging(company, multiPage, 1, 0, Order.desc("sortOrder"));
			if(singlePageList.getTotal() > 0) {
				SinglePage lastSinglePage = singlePageList.getList().get(0);
				if(lastSinglePage == null) {
					singlePage.setSortOrder(1);
				}
				else {
					singlePage.setSortOrder(lastSinglePage.getSortOrder() + 1);
				}
			}
		}
		
		if(company != null && multiPage != null && singlePage != null){
			multiPageFiles = multiPageFileDelegate.findAllSinglePageFiles(company, multiPage, singlePage);
			if(CompanyConstants.UNIORIENT_AGENTS.equalsIgnoreCase(company.getName())) {
				multiPageFiles = multiPageFileDelegate.findAllSinglePageFiles(company, multiPage, singlePage, Order.desc("createdOn")).getList();
			}
		}
		if(company != null && singlePage != null){
			singlePageFiles = singlePage.getFiles();
		}
		
		logList = logDelegate.findAll(singlePage.getId(), EntityLogEnum.SINGLE_PAGE).getList();
		//System.out.println("Log list length: " + logList.size());
	}
	
	@Override
	public String execute() throws Exception {
		return Action.NONE;
	}
	
	public String save() {
		if(company == null) {
			return Action.ERROR;
		}
		if(company.getName().equalsIgnoreCase("agian")){
			singlePage.setDaysAvailable(request.getParameter("singlePage.prev"));
		}else{
			singlePage.setDaysAvailable(null);
		}
		
		if(company.getName().equalsIgnoreCase("HPDSKED")){
			saveHPDSKEDSchedule();
		}
                                                 
		try{
			if(singlePage.getCreatedBy() == null) singlePage.setCreatedBy(userDelegate.load(user.getId()));
			if(singlePage.getContent() == null) singlePage.setContent("");
			if(singlePage.getName() == null) singlePage.setName("");
		}catch(Exception e){}
		
		if(request.getParameter("language")==null || request.getParameter("language").isEmpty()){
			
			SinglePage oldSinglePage = (SinglePage) request.getSession().getAttribute("oldSinglePage");
			
			Log log = new Log();
			
			
			
			// create the valid to information
			String validTo = (String) request.getParameter("singlePage.validTo");

			if(validTo == null)
			{
				if(request.getParameter("itemDate") != null)
				{
					validTo = (String) request.getParameter("itemDate");
				}
			}
			
			if(validTo != null) {
				try {
					String[] validToDate = validTo.split("-");
					DateTime dt = new DateTime(Integer.valueOf(validToDate[2]), Integer.valueOf(validToDate[0]), 
										Integer.valueOf(validToDate[1]), 0,0,0,0);
					
					singlePage.setValidTo(new Date(dt.getMillis()));
					
					String[] validToDate_items = validTo.split("-");
					DateTime dt_items = new DateTime(Integer.valueOf(validToDate_items[2]), Integer.valueOf(validToDate_items[0]), 
										Integer.valueOf(validToDate_items[1]), 0,0,0,0);
					
					singlePage.setValidTo(new Date(dt_items.getMillis()));
					
				}
				catch(Exception e) {
					logger.fatal("failed to get the valid to date!!!");
				}
			}
			
			Date itemDate_singlepage = null;
			Date date_published = null;
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			try {
			
			itemDate_singlepage = sdf.parse(itemDate);
			}
			
			catch(Exception e){
				logger.fatal("cannot parse date!!!");
			}
			singlePage.setItemDate(itemDate_singlepage);
			
			try {
				date_published = sdf.parse(datePublished);
				singlePage.setDatePublished(date_published);
			} catch (Exception e) {
				singlePage.setDatePublished(new Date());
			}
			
			
			
			if(singlePageDelegate.find(company, singlePage.getName(), multiPage) == null) {
				//if new page
				if(multiPage != null && singlePage.getId() == null) {
					if(company.getName().equals("neltex")) {
						List<SinglePage> singlePages = multiPage.getItems();
						Collections.sort(singlePages, new Comparator<SinglePage>() {
							public int compare(SinglePage s1, SinglePage s2) {
								Integer so1 = new Integer(s1.getSortOrder());
								Integer so2 = new Integer(s2.getSortOrder());
								return so1.compareTo(so2);
							}
						});
						Collections.reverse(singlePages);
						if(singlePages != null && singlePages.size() > 0) {
							singlePage.setSortOrder(singlePages.get(0).getSortOrder()+1);
						} else {
							singlePage.setSortOrder(1);
						}
					} else {
						singlePage.setSortOrder(1);
					}
					saveNewOrder();
				}
				long singlePageId = singlePageDelegate.insert(singlePage);
				singlePage.setId(singlePageId);
				log.setRemarks("Added a new page " + "\"" + singlePage.getName() + "\"");
				//add action log
				log.setEntityType(EntityLogEnum.SINGLE_PAGE);
				log.setEntityID(singlePage.getId());
				log.setCompany(company);
				log.setUpdatedByUser(user);
				logDelegate.insert(log);
				
			}	
			else {
				
				if(singlePage.getId() == null) {
					return ERROR;
				}
				
				boolean isFeatured = false;
				if(singlePage.getIsSingleFeatured()){
					isFeatured = true;
				}
				
				boolean isForm = false;
				if(singlePage.getIsForm()){
					isForm = true;
				}
				
				boolean isHidden = false;
				if(singlePage.getHidden()){
					isHidden = true;
				}
	
				boolean ishome = false;
				if(singlePage.getIsHome()) {
					ishome = true;
				}
				if(singlePage.getIsHome()) {
					singlePageDelegate.disableAll(company);
				}
				
				if(multiPage != null) {
					singlePage.setHasFile(multiPage.getHasFile());
				}
				
				if(request.getParameter("singlePage.isSingleFeatured") != null && request.getParameter("singlePage.isSingleFeatured").equals("true")){
					isFeatured = true;
				}
				
				if(request.getParameter("singlePage.isForm") != null && request.getParameter("singlePage.isForm").equals("true")){
					isForm = true;
				}
				
				if(request.getParameter("singlePage.hidden") != null && request.getParameter("singlePage.hidden").equals("true")){
					isHidden = true;
				}
				
				singlePage.setUpdatedOn(new Date());
				singlePage.setIsHome(ishome);
				singlePage.setIsSingleFeatured(isFeatured);
				singlePage.setIsForm(isForm);
				singlePage.setHidden(isHidden);
				
				if(singlePageDelegate.update(singlePage)){
					String remark = "Updated the page "+ "\"" + singlePage.getName() + "\"";
					String[] diff = LogHelper.getDifference(oldSinglePage, singlePage);
					
					if(diff.length>0){
						String remarks = remark + "~" + LogHelper.join(diff,"~");
						if(remarks.length() > 254)
						{
							remarks = remarks.substring(0, 255);
						}
						
						log.setRemarks(remarks);
						//update action log
						log.setEntityType(EntityLogEnum.SINGLE_PAGE);
						log.setEntityID(singlePage.getId());
						log.setCompany(company);
						log.setUpdatedByUser(user);
						logDelegate.insert(log);
					}
				}
			}
			
			
			singlePage.setDatePosted(new Date());
			
			System.out.println("DATE POSTED :" + singlePage.getDatePosted());
			lastUpdatedDelegate.saveLastUpdated(company);
		}
		//if update different language
		else{
			//if page doesnt exist(insert)
			SinglePageLanguage spLanguage = singlePage.getSinglePageLanguage();
			SinglePage oldSinglePage = (SinglePage) request.getSession().getAttribute("oldSinglePage");
			if(spLanguage==null){
				singlePage.setLanguage(null);
				SinglePageLanguage spl = new SinglePageLanguage();
				spl.cloneOf(singlePage);
				spl.setLanguage(languageDelegate.find(request.getParameter("language"),company));
				singlePageDelegate.refresh(singlePage);
				spl.setDefaultPage(singlePage);
				singlePageLanguageDelegate.insert(spl);
			}
			//if page exist (update)
			else{
				//singlePage.setLanguage(null);
				
				SinglePageLanguage spl = singlePage.getSinglePageLanguage();
				spl.cloneOf(singlePage);
				singlePageDelegate.refresh(singlePage);
				spl.setDefaultPage(singlePage);
				singlePageLanguageDelegate.update(spl);
				
			}
		
		}
		
		if(company.getName().equals("neltex") && files != null) {
			uploadImage();
		}

		return Action.SUCCESS;
	}
	
	private void saveNewOrder() {
		// TODO Auto-generated method stub
		List<SinglePage> singlePages = new ArrayList<SinglePage>();
		int count = 2;
		for(SinglePage sp : multiPage.getItems()) {
			sp.setSortOrder(count++);
			singlePages.add(sp);
		}
//		singlePageDelegate.batchUpdate(singlePages); 
	}

	public String delete() {
		if(company == null) {
			return Action.ERROR;
		}
		if(multiPage == null) {	
			if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
				return ERROR;
			}
		}
		if( !company.equals(singlePage.getCompany()) ) {
			return Action.ERROR;
		}
		
		singlePageDelegate.delete(singlePage);
		
		//delete action log
		Log log = new Log();
		log.setEntityType(EntityLogEnum.SINGLE_PAGE);
		log.setRemarks("Deleted the page "+ "\"" + singlePage.getName() + "\"");
		log.setEntityID(singlePage.getId());
		log.setCompany(company);
		log.setUpdatedByUser(user);
		logDelegate.insert(log);
		
		return Action.SUCCESS;
	}
	
	
	
	public String deleteComment() {
		if(company == null) {
			return Action.ERROR;
		}
	
		if( !company.equals(singlePage.getCompany()) ) {
			return Action.ERROR;
		}
		// try to get the page comment
		//System.out.println("DLETE COMMENT "+request.getParameter("commentid"));
		long singlePageId = Long.parseLong(request.getParameter("singlepage_id"));
		singlePage = singlePageDelegate.find(singlePageId);
		long commentId = Long.parseLong(request.getParameter("commentid"));
		//System.out.println("Delete ID "+commentId);
		comment = itemCommentDelegate.find(commentId);
		itemCommentDelegate.delete(comment);
		
			
		return Action.SUCCESS;
	}
	
	public String edit() {
		if(company == null) {
			return Action.ERROR;
		}
		
		if( !company.equals(singlePage.getCompany()) )  {	
			return Action.ERROR;
		}
		
		request.getSession().setAttribute("oldSinglePage", singlePage.clone());
		//******************************************************************************************************************//
		//******************************************************************************************************************//
		if(company.getName().equalsIgnoreCase("hpdsked")&&singlePage.getParent()!=null&&singlePage.getParent().getName().equalsIgnoreCase("RetiroBranch")){
			if (group == null){
				loadAllItemsAndCategories();
			}
			//System.out.println("<<<<<<<<<<<<<<-------------------------------->>>>>>>>>>");
			//System.out.println(singlePage.getDaysAvailable());
			//System.out.println(singlePage.getStartTime());
			//System.out.println(singlePage.getEndTime());
			//System.out.println(singlePage.getDocRoom());
			//System.out.println("");
			String daysAvailable[] = singlePage.getDaysAvailable().split("<-->");
			String startTime[]= singlePage.getStartTime().split("<-->");
			String endTime[]=singlePage.getEndTime().split("<-->");
			String docRoom[]=singlePage.getDocRoom().split("<-->");
			
			/*
			 * Start Time -- Time
			 * End Time -- Contact Number
			 * Days Available -- Days
			 * Title -- Specialization
			 * Sub Title -- Doctors Name
			 */
			
			request.setAttribute("daysAvailable", daysAvailable);
			request.setAttribute("timeAvailable", startTime);
			request.setAttribute("contactNumber", endTime);
			request.setAttribute("docRoom", docRoom);
			//System.out.println("<<<<<<<<<<<<<<-------------------------------->>>>>>>>>>");
			request.setAttribute("hpdskedBranch","retiroBranch");
		}
		else if(company.getName().equalsIgnoreCase("hpdsked")&&singlePage.getParent()!=null&&singlePage.getStartTime()!=null&&singlePage.getEndTime()!=null){
			
			if (group == null){
				loadAllItemsAndCategories();
			}
			
			String st=singlePage.getStartTime().substring(11,13);
			String et=singlePage.getEndTime().substring(11,13);
			int gst=Integer.parseInt(st);
			int get=Integer.parseInt(et);
			request.setAttribute("_startTime",gst);
			request.setAttribute("_endTime",get);
		}
		
		if(company.getName().equalsIgnoreCase("HPDSKED")&&singlePage.getParent()!=null&&(singlePage.getParent().getName().equalsIgnoreCase("retiroBranch")||singlePage.getParent().getName().equalsIgnoreCase("events")))
		{
			if (group == null){
				loadAllItemsAndCategories();
			}
			return "hpdsked-schedule";
		}
		
		try {
			sortedImagesByTitle = PageImageDelegate.getInstance().findAllSortedByTitle(singlePage);
			request.setAttribute("sortedImagesByTitle", sortedImagesByTitle);
		} catch (Exception e) {}
		
		//******************************************************************************************************************//
		//******************************************************************************************************************//
		
		//System.out.println("ANG RESULT AY----->SUCCESS");
		return Action.SUCCESS;
	}
	
	
	public void loadAllItemsAndCategories(){
		Order[] orders = {Order.asc("parentCategory"),Order.asc("sortOrder"),Order.asc("name")};
		group = groupDelegate.find(Long.parseLong("205"));//request.getParameter("group_id")==205
		categories = categoryDelegate.findAll(company, group).getList();
		//categories = categoryDelegate.findAllWithPaging(user.getCompany(), null, group, user.getItemsPerPage(), page, orders, true).getList();
		Order[] ordersItem = {Order.asc("parent"),Order.asc("sortOrder"),Order.asc("name")};
		categoriesList = categoryDelegate.findAll(company, group).getList();
		sortedCategoriesList = sortList(categoriesList);//sort categoriesList
		items = categoryItemDelegate.findAll(company).getList();
		//items = categoryItemDelegate.findAllWithPaging(user.getCompany(), group, category, itemsPerPage, page, true, ordersItem).getList();
		request.setAttribute("allItems",items);
		request.setAttribute("allCategories",categories);
	}
	
	
	public List<Category> sortList(List<Category> toBeSorted) {
		Category temp;
	    for (int i=1; i < toBeSorted.size(); i++) { 
	        for (int j=0; j < toBeSorted.size()-i; j++) {
	            if (0 < toBeSorted.get(j).getName().compareTo(toBeSorted.get(j+1).getName())) {
	            	temp = toBeSorted.get(j);
	            	toBeSorted.set(j, toBeSorted.get(j+1));
	            	toBeSorted.set(j+1, temp);
	            }
	        }
	    }
		return toBeSorted;
	}
	
	
	private String saveHPDSKEDSchedule(){
//		System.out.println("THE ONLEAVE IS--->"+this.onLeave);
		if(company.getName().equalsIgnoreCase("HPDSKED")&&request.getParameter("SinglePage.isRockwellBranch")!=null)
		{
			singlePage.setDocRoom(request.getParameter("singlePage.room"));
			String et=request.getParameter("SinglePage.endingTime");
			String st=request.getParameter("SinglePage.startingTime");
			
			st=(st.length()==1)?("0"+st):st;
			et=(et.length()==1)?("0"+et):et;
			String sPmAm=request.getParameter("sPmAm");
			String ePmAm=request.getParameter("ePmAm");
			int isPMStart=Integer.parseInt(st);
			int isPMEnd=Integer.parseInt(et);
			//System.out.println("THE PM AM IS "+sPmAm+"-----"+ePmAm+" uuu "+st+"  jj "+et);
			if(sPmAm!=null)
				st=(sPmAm.equalsIgnoreCase("AM")?st:""+(isPMStart+12));
			if(sPmAm.equalsIgnoreCase("PM")&&isPMStart==12)
				st="12";
			if(ePmAm!=null)
				et=(ePmAm.equalsIgnoreCase("AM")?et:""+(isPMEnd+12));
			if(ePmAm.equalsIgnoreCase("PM")&&isPMEnd==12)
				et="12";
			singlePage.setStartTime("0000-00-00 "+st+":00:00");
			singlePage.setEndTime("0000-00-00 "+et+":00:00");
			singlePage.setDocSchool(request.getParameter("singlePage.docSchool"));
			
			String selectedDays="";
			String select[] = request.getParameterValues("SinglePage.checkBoxDays"); 
			if (select != null && select.length != 0) {
				for (int i = 0; i < select.length; i++) {
					selectedDays += select[i]+"   " ;
				}
			}
			singlePage.setDaysAvailable(selectedDays);
			
			//System.out.println("THE COMPANY NAME IS HPDSKED AND "+singlePage.getEndTime()+"------"+singlePage.getStartTime());
		}//SinglePage.isRetiroBranch
		else if(company.getName().equalsIgnoreCase("HPDSKED")&&request.getParameter("SinglePage.isRetiroBranch")!=null)
		{
			singlePage.setTitle(request.getParameter("singlePage.title"));
			//System.out.println("The Subtitle is --------------> "+request.getParameter("singlePage.subTitle").trim());
			singlePage.setSubTitle(request.getParameter("singlePage.subTitle").trim());
			/**ETO ANG MGA ARRAY
			//singlePage.daysSchedule
			//singlePage.timeSchedule
			//singlePage.room
			//singlePage.contactNumber
			 * */
			//request.getP
			String[]  daysSchedule= request.getParameterValues("singlePage.daysSchedule");
			String[] timeSchedule=request.getParameterValues("singlePage.timeSchedule");
			String[] rooms=request.getParameterValues("singlePage.room");
			String[] contactNumbers=request.getParameterValues("singlePage.contactNumber");
			
			String daySched="";
			String timeSched="";
			String roomSched="";
			String contactNumberSched="";

			
			/*
			 * Start Time -- Time
			 * End Time -- Contact Number
			 * Days Available -- Days
			 * Title -- Specialization
			 * Sub Title -- Doctors Name
			 */
				for(String day:daysSchedule){
					if(day.equalsIgnoreCase(""))
						continue;
					daySched+=day+"<-->";
				}
			
				for(String time:timeSchedule)
				{
					if(time.equalsIgnoreCase(""))
						continue;
					timeSched+=time+"<-->";
				}
				for(String room:rooms){
					if(room.equalsIgnoreCase(""))
						continue;
					roomSched+=room+"<-->";
				}
			 	for(String contactNumber:contactNumbers){
			 		if(contactNumber.equalsIgnoreCase(""))
						continue;
			 		contactNumberSched+=contactNumber+"<-->";
			 	}
			 	
				singlePage.setDocRoom(roomSched.trim());
				singlePage.setDaysAvailable(daySched.trim());
				singlePage.setStartTime(timeSched.trim());
				singlePage.setEndTime(contactNumberSched.trim());
		}
		
		return "";
	}
	
	public String deleteImage() {
		if(company == null) {
			return Action.ERROR;
		}
		if( !company.equals(singlePage.getCompany()) )  {	
			return Action.ERROR;
		}  
		if(!singlePage.providePageType().equals(image.getPage().providePageType())) {
			return Action.ERROR;
		}

		String destinationPath = servletContext.getRealPath("companies" + 
				File.separator + company.getName() +
				File.separator + "images" +
				File.separator + "pages");
			
		// delete original
		FileUtil.deleteFile(destinationPath + File.separator + image.getOriginal());
		// delete image1
		FileUtil.deleteFile(destinationPath + File.separator + image.getImage1());
		// delete image2
		FileUtil.deleteFile(destinationPath + File.separator + image.getImage2());
		// delete image2
		FileUtil.deleteFile(destinationPath + File.separator + image.getImage3());
		// delete thumbnail
		FileUtil.deleteFile(destinationPath + File.separator + image.getThumbnail());

		pageImageDelegate.delete(image);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public String deleteFile() {
		if(company == null) {
			return Action.ERROR;
		}
		if( !company.equals(singlePage.getCompany()) )  {	
			return Action.ERROR;
		}  
		
		String destinationPath = servletContext.getRealPath("companies" + 
				File.separator + company.getName() +
				File.separator + "uploads");
	
		FileUtil.deleteFile(destinationPath + File.separator + pageFile.getFilePath());
	
		pageFileDelegate.delete(pageFile);
		
		return SUCCESS;
	}
	
	public String deleteMultiPageFile() {
		if(company == null) {
			return Action.ERROR;
		}
		
		if( singlePage != null && !company.equals(singlePage.getCompany()) )  {	
			return Action.ERROR;
		} 
		
		String destinationPath = servletContext.getRealPath("companies" + 
				File.separator + company.getName() +
				File.separator + ApplicationConstants.MULTIPAGE_FILES_DIRECTORY_NAME + 
				File.separator + multiPage.getId() + 
				File.separator + singlePage.getId()
				);
		
		FileUtil.deleteFile(destinationPath + File.separator + multiPageFile.getFileName());
		
		multiPageFileDelegate.delete(multiPageFile); 
		
		return SUCCESS;
	}
		
	public String uploadImage() { 
		if(company == null) {
			return Action.ERROR;
		}
		if( !company.equals(singlePage.getCompany()) )  {	
			return Action.ERROR;
		}
		//System.out.println("THE FILES IS "+files);
		saveImages(files, filenames, contentTypes);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	

	
	private boolean saveImages(File[] files, String[] filenames, String[] contentTypes) {
				
		CompanySettings companySettings = company.getCompanySettings();
		
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));	
		
		// save new image in boysennewdesign folder
		if(company.getName().equalsIgnoreCase("boysen"))
			destinationPath += File.separator + "boysennewdesign";
		else
			destinationPath += File.separator + company.getName();
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "pages";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));

		// create the orig, image1, image2, image3 and thumbnail folders
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image1"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image2"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image3"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "thumbnail"));

		destinationPath = servletContext.getRealPath(destinationPath);
		
		//System.out.println("SAVING "+files.length +"file.");
		
		Long companyId = company.getId();
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
			String source = files[i].getAbsolutePath();
	
			//System.out.println("File #"+i+": source: "+source);
			
			
			String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"),
			String.valueOf(new Date().getTime()));
	
			if(company.getName().equalsIgnoreCase(CompanyConstants.WILCON) || company.getName().equalsIgnoreCase("rockwell") || company.getName().equalsIgnoreCase("ivantnew") || company.getName().equalsIgnoreCase(CompanyConstants.BCI) || company.getName().equalsIgnoreCase(CompanyConstants.KUYSEN_FURNITURES) && FileUtil.getExtension(filename).equalsIgnoreCase("png")) {
				filename = FileUtil.replaceExtension(filename, "png");
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				FileUtil.copyFile(files[i], origFile);				
				// generate image 1
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename,
				companySettings.getImage1Width(), companySettings.getImage1Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
				// generate image 2
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename,
				companySettings.getImage2Width(), companySettings.getImage2Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
				// generate image 3
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename,
				companySettings.getImage3Width(), companySettings.getImage3Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
			}
	
			else if (!FileUtil.getExtension(filename).equalsIgnoreCase("jpg") && !FileUtil.getExtension(filename).equalsIgnoreCase("jpeg") &&
			!FileUtil.getExtension(filename).equalsIgnoreCase("gif") )
			{
				return false;
			}

			//logger.debug("after the if=================================================");
		
		
			if (FileUtil.getExtension(filename).equalsIgnoreCase("gif")) {

//				File origFile = new File(destinationPath + File.separator + "image1" + File.separator + filename);
	
				// save the original files
//				FileUtil.copyFile(files[i], origFile);
				
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				File origFile2 = new File(destinationPath + File.separator + "image1" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile2);
			
				// generate thumbnail
				ImageUtil.generateGifThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);				
		
				// instead of creating directly from the temporary file, create a file from the original file
			}
			
			
			if (FileUtil.getExtension(filename).endsWith("JPG")) {

//				File origFile = new File(destinationPath + File.separator + "image1" + File.separator + filename);
	
				// save the original files
//				FileUtil.copyFile(files[i], origFile);
				
				filename = FileUtil.replaceExtension(filename, "jpg");
				FileUtil.getExtension(filename).toLowerCase();
				
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				File origFile2 = new File(destinationPath + File.separator + "image1" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile2);
			
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);				
		
				// instead of creating directly from the temporary file, create a file from the original file
			}
			
			if (FileUtil.getExtension(filename).endsWith("GIF")) {

//				File origFile = new File(destinationPath + File.separator + "image1" + File.separator + filename);
	
				// save the original files
//				FileUtil.copyFile(files[i], origFile);
				
				filename = FileUtil.replaceExtension(filename, "gif");
				FileUtil.getExtension(filename).toLowerCase();
				
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				File origFile2 = new File(destinationPath + File.separator + "image1" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile2);
			
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);				
		
				// instead of creating directly from the temporary file, create a file from the original file
			}
	
			//System.out.println("----------------------------------" + FileUtil.getExtension(filename));	
			
			if (FileUtil.getExtension(filename).equalsIgnoreCase("jpg") || FileUtil.getExtension(filename).equalsIgnoreCase("jpeg") || FileUtil.getExtension(filename).endsWith("JPG") )
			{
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename,
				companySettings.getImage1Width(), companySettings.getImage1Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
		
				// generate image 2
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename,
				companySettings.getImage2Width(), companySettings.getImage2Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
		
				// generate image 3
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename,
				companySettings.getImage3Width(), companySettings.getImage3Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
		
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
			}

			if(company.getName().equalsIgnoreCase("uniorientagents")) {
				int counter = 1;
				List<PageImage> singlePageImages = singlePage.getSortedImages();
				for(PageImage pageImage : singlePageImages) {
					pageImage.setSortOrder(counter++);
				}
				pageImageDelegate.batchUpdate(singlePageImages);
			}
			
			
			// create the page image
			PageImage pageImage = new PageImage();
			pageImage.setFilename(filenames[i]);
			pageImage.setPage(singlePage);
			pageImage.setPageType(singlePage.providePageType());
			// pageImage.setUrl(UrlUtil.generateImageUploadUrl(company) + "pages/");
			pageImage.setOriginal("original/" + filename);
			pageImage.setImage1("image1/" + filename);
			pageImage.setImage2("image2/" + filename);
			pageImage.setImage3("image3/" + filename);
			pageImage.setThumbnail("thumbnail/" + filename);
			
			if(company.getName().equalsIgnoreCase("uniorientagents")) {
				pageImage.setSortOrder(0);
			}
			
			pageImageDelegate.insert(pageImage);
		}
		}
		return true;
	}
	
	
	
	
	
	
	public String uploadFile() {
		if(company == null) {
			return Action.ERROR;
		}
		if( !company.equals(singlePage.getCompany()) )  {	
			return Action.ERROR;
		}
		
		saveFiles(files, filenames, contentTypes);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
//		multiPageFiles = multiPageFileDelegate.findAllSinglePageFiles(company, multiPagesinglePage);
		return SUCCESS;
	}
	
	private void saveFiles(File[] files, String[] filenames, String[] contentTypes) {
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		// save new image in boysennewdesign folder
		if(company.getName().equalsIgnoreCase("boysen"))
			destinationPath += File.separator + "boysennewdesign";
		else
			destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "uploads"));
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
				File file = files[i];
				
				//String filename = filenames[i];
				
				String filename = // replace extension to lowercase, some browser cannot open the file properly if the extension is in uppercase
					FileUtil.replaceExtension(
						filenames[i], 
						FileUtil.getExtension(filenames[i]).toLowerCase());
				
				String contentType = contentTypes[i];
				String destination = servletContext.getRealPath(destinationPath + File.separator + "uploads");
				File destFile = new File(destination + File.separator + filename);
				
				FileUtil.copyFile(file, destFile); 
				
				PageFile pageFile = new PageFile();
				pageFile.setCompany(company);
				pageFile.setFileName(filename);
				pageFile.setFilePath("uploads" + "/" + filename);
				pageFile.setFileType(contentType);
				pageFile.setPage(singlePage);
				pageFileDelegate.insert(pageFile);
			} 
		}
	}
	
	public String uploadMultiPageFile() {
		if(company == null) {
			return Action.ERROR;
		}
		if( singlePage != null && !company.equals(singlePage.getCompany()) )  {	
			return Action.ERROR;
		}
		//System.out.println("BEFORE UPLOAD");
		saveMultiPageFiles(files, filenames, contentTypes);
		
		lastUpdatedDelegate.saveLastUpdated(company);

		return SUCCESS;
	}
	
	public String updateFileAccessRights() {
		
		if(multiPage == null) {	
			List<String> memberTypeKeyList = (memberTypeIdList != null) ? Arrays.asList(memberTypeIdList) : new ArrayList<String>();
			List<PageFile> pageFiles = singlePage.getFiles();
			List<MemberType> memberTypes = memberTypeDelegate.findAllWithPaging(company, -1, -1, null).getList();
			if(pageFiles != null) {
				for(PageFile pageFile : pageFiles) {
					for(MemberType memberType: memberTypes) {
						String key = memberType.getId()+"_"+pageFile.getId();
						
						if(!memberTypeKeyList.contains(key)) {
							MemberPageFile pageFileRight = memberPageFileDelegate.find(pageFile, memberType);
							if(pageFileRight != null)
								memberPageFileDelegate.delete(pageFileRight);
						}
					}
				}
			}
			if(memberTypeIdList != null) {	
				
				
				for(int i=0; i<memberTypeIdList.length; i++) {
					String[] memberTypeFile = memberTypeIdList[i].split("_");
					pageFile = pageFileDelegate.find(Long.parseLong(memberTypeFile[1])); 
					MemberType memberType = memberTypeDelegate.find(Long.parseLong(memberTypeFile[0]));
					
					MemberPageFile memberPageFile = new MemberPageFile();
					memberPageFile.setMemberType(memberType);
					memberPageFile.setCompany(company);
					memberPageFile.setPageFile(pageFile);
					if(memberPageFileDelegate.find(pageFile, memberType)==null){
						memberPageFileDelegate.insert(memberPageFile);
					}
					
				}
			}
		} else if(multiPage != null) {
			
			List<String> memberTypeKeyList = (memberTypeIdList != null) ? Arrays.asList(memberTypeIdList) : new ArrayList<String>();
			List<MultiPageFile> multiPageFiles = multiPageFileDelegate.findAllSinglePageFiles(company, multiPage, singlePage);
			List<MemberType> memberTypes = memberTypeDelegate.findAllWithPaging(company, -1, -1, null).getList();
			if(multiPageFiles != null) {
				for(MultiPageFile multiPageFile : multiPageFiles) {
					for(MemberType memberType: memberTypes) {
						String key = memberType.getId()+"_"+multiPageFile.getId();
						
						if(!memberTypeKeyList.contains(key)) {
							MemberPageFile pageFile = memberPageFileDelegate.find(multiPageFile, memberType);
							if(pageFile != null)
								memberPageFileDelegate.delete(pageFile);
						}
					}
				}
			}
			
			if(memberTypeIdList != null) {	
				
				for(int i=0; i<memberTypeIdList.length; i++) {
					String[] memberTypeFile = memberTypeIdList[i].split("_");
					
					multiPageFile = multiPageFileDelegate.find(Long.parseLong(memberTypeFile[1]));
					List<MemberPageFile> list = multiPageFile.getMemberPageFiles();
					
					if(list != null) {
						for(MemberPageFile tempFile : list) {
							memberPageFileDelegate.delete(tempFile);
						}
							
					}
					
					MemberType memberType = memberTypeDelegate.find(Long.parseLong(memberTypeFile[0]));
					
					MemberPageFile memberPageFile = new MemberPageFile();
					memberPageFile.setMemberType(memberType);
					memberPageFile.setCompany(company);
					memberPageFile.setMultiPageFile(multiPageFile);
					memberPageFileDelegate.insert(memberPageFile);
				}
			}
			return "multipage";
		}
		return SUCCESS;
	}
	
	//FIXME: does not print error message
	private void saveMultiPageFiles(File[] files, String[] filenames, String[] contentTypes) {
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		// save new image in boysennewdesign folder
		if(company.getName().equalsIgnoreCase("boysen"))
			destinationPath += File.separator + "boysennewdesign";
		else
			destinationPath += File.separator + company.getName();
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		String dir = ApplicationConstants.MULTIPAGE_FILES_DIRECTORY_NAME + "/"+ multiPage.getId() + "/" + singlePage.getId();
		File fileDirectory = new File(servletContext.getRealPath(destinationPath + File.separator + dir));
		fileDirectory.mkdirs();
		//System.out.println(1);
		invalidFiles = 0;
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
				//System.out.println(2);
				File file = files[i];
				String filename = filenames[i];
				String contentType = contentTypes[i];
				if(isImageFile(contentType)){
					//System.out.println(3);
					invalidFiles++;
					continue;
				}

				String destination = servletContext.getRealPath(destinationPath + File.separator + dir);
				File destFile = new File(destination + File.separator + filename);
				
				if(destFile.length()/1048576 > companySettings.getMaxFileSize()){
					//System.out.println(4);
					invalidFiles++;
					continue;
				}
				//System.out.println(5);
				FileUtil.copyFile(file, destFile); 
				
				MultiPageFile pageFile = new MultiPageFile();
				pageFile.setCompany(company);
				pageFile.setFileName(filename);
				pageFile.setFilePath(dir + "/" + filename);
				pageFile.setFileType(contentType);
				pageFile.setFileSize(destFile.length());
				pageFile.setMultipage(multiPage);
				pageFile.setSinglepage(singlePage);
				multiPageFileDelegate.insert(pageFile); 
			} 
		}
	}
	
	private boolean isImageFile(String contentType) {
		return StringUtils.contains(contentType, "image");
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public SinglePage getSinglePage() {
		return singlePage;
	}
	
	public void setSinglePage(SinglePage singlePage) {
		this.singlePage = singlePage;
	}
	
	public MultiPage getMultiPage() {
		return multiPage;
	} 
	
	// upload methods
	
	public void setUpload(File[] files) {
		this.files = files;
	}
	
	public void setUploadContentType(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}
	
	public void setUploadFileName(String[] filenames) {
		this.filenames = filenames;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public CompanySettings getCompanySettings() {
		return companySettings;
	}
	
	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}

	public void setItemDate(String itemDate) {
		this.itemDate = itemDate;
	}

	public String getItemDate() {
		return itemDate;
	}

	public void setIDate(Date iDate) {
		this.iDate = iDate;
	}

	public Date getIDate() {
		return iDate;
	}

	public List<MultiPageFile> getMultiPageFiles() {
		return multiPageFiles;
	}

	public List<MemberType> getMemberTypes() {
		return memberTypeDelegate.findAllWithPaging(company, -1, -1, null).getList();
	}
	
	public void setMultiPageFiles(List<MultiPageFile> multipageFiles) {
		this.multiPageFiles = multipageFiles;
	}
	
	public Integer getInvalidFiles() {
		return invalidFiles;
	}

	public List<PageFile> getSinglePageFiles() {
		return singlePageFiles;
	}

	public void setSinglePageFiles(List<PageFile> singlePageFiles) {
		this.singlePageFiles = singlePageFiles;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public List<Language> getLanguages() {
		return languages;
	}
	public ItemComment getComment() {
		return comment;
	}

	public void setComment(ItemComment comment) {
		this.comment = comment;
	}

	public void setMemberTypeIdList(String[] memberTypeIdList) {
		this.memberTypeIdList = memberTypeIdList;
	}

	public String getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	
}
