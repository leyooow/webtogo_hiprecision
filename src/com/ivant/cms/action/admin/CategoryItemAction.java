package com.ivant.cms.action.admin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cms.delegate.BrandDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemComponentDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemHistoryDelegate;
import com.ivant.cms.delegate.CategoryItemLanguageDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldBranchDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldLanguageDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldMachineDelegate;
import com.ivant.cms.delegate.CategoryItemPriceDelegate;
import com.ivant.cms.delegate.ComponentDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemAttributeDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.delegate.ItemFileDelegate;
import com.ivant.cms.delegate.ItemImageDelegate;
import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberImagesDelegate;
import com.ivant.cms.delegate.MemberPollDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.NotificationDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.OtherFieldValueDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.delegate.ScheduleDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Attribute;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemHistory;
import com.ivant.cms.entity.CategoryItemLanguage;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.CategoryItemOtherFieldBranch;
import com.ivant.cms.entity.CategoryItemOtherFieldLanguage;
import com.ivant.cms.entity.CategoryItemOtherFieldMachine;
import com.ivant.cms.entity.CategoryItemPrice;
import com.ivant.cms.entity.CategoryItemPriceName;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Component;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemAttribute;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.entity.ItemImage;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberImages;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.Notification;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.OtherFieldValue;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.Schedule;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.GroupAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.cms.ws.rest.client.PromoItemClient;
import com.ivant.constants.CompanyConstants;
import com.ivant.constants.WendysConstants;
import com.ivant.utils.CategoryItemUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.HashUtil;
import com.ivant.utils.ImageUtil;
import com.ivant.utils.PropertiesUtil;
import com.ivant.utils.QRGenerator;
import com.ivant.utils.ReflectionUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CategoryItemAction extends ActionSupport implements Preparable, ServletRequestAware, 
							ServletContextAware, UserAware, CompanyAware, GroupAware, ServletResponseAware{
 
	private static final long serialVersionUID = 6469858130683551656L;
	private static final Logger logger = Logger.getLogger(CategoryItemAction.class);
	private static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private static final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private static final MemberPollDelegate memberPollDelegate = MemberPollDelegate.getInstance();
	
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	
	
	private static final CategoryItemOtherFieldBranchDelegate categoryItemOtherFieldBranchDelegate = CategoryItemOtherFieldBranchDelegate.getInstance();
	private static final CategoryItemOtherFieldMachineDelegate categoryItemOtherFieldMachineDelegate = CategoryItemOtherFieldMachineDelegate.getInstance();

	
	private static final CategoryItemPriceDelegate categoryItemPriceDelegate = CategoryItemPriceDelegate.getInstance();
	private static final CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	private static final ItemImageDelegate itemImageDelegate = ItemImageDelegate.getInstance();
	private static final ItemFileDelegate itemFileDelegate = ItemFileDelegate.getInstance();	
	private static final BrandDelegate brandDelegate = BrandDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private static final ItemAttributeDelegate itemAttributeDelegate = ItemAttributeDelegate.getInstance();
	private static final LogDelegate logDelegate = LogDelegate.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final OtherFieldValueDelegate otherFieldValueDelegate = OtherFieldValueDelegate.getInstance();
	private LanguageDelegate languageDelegate = LanguageDelegate.getInstance();
	private CategoryItemLanguageDelegate categoryItemLanguageDelegate = CategoryItemLanguageDelegate.getInstance();
	private CategoryItemOtherFieldLanguageDelegate categoryItemOtherFieldLanguageDelegate = CategoryItemOtherFieldLanguageDelegate.getInstance();
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	
	
	private final ComponentDelegate componentDelegate = ComponentDelegate.getInstance();
	private final CategoryItemComponentDelegate categoryItemComponentDelegate = CategoryItemComponentDelegate.getInstance();
	
	private ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate.getInstance();
	private static final ScheduleDelegate scheduleDelegate = ScheduleDelegate.getInstance();
	
	private static final CategoryItemHistoryDelegate categoryItemHistoryDelegate = CategoryItemHistoryDelegate.getInstance();
	
	private static final OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	
	//private static final SmsClient smsClient = SmsClient.getInstance();
	
	private static final String DATE_PARAM = "datenowdate";
	private static final String SECRET_KEY_PARAM = "secretkey";
	private static final String SECRET_KEY = "1v@ntT3c]-[";
	private static final String ENCODING_DEFAULT = "UTF-8";
	
	private User user;
	private Company company;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext servletContext;
	
	private CategoryItem item;
	private Category category;
	private String parent;

	private Brand brand;
	private Group group;
	private CompanySettings companySettings;
	private ItemImage itemImage;
	private ItemFile itemFile;
	ItemAttribute itemAttribute; 

	private String[] contentTypes;
	private String[] filenames;
	
	private FileInputStream fInStream;
	private long contentLength;	
	private InputStream inputStream;
	
	private String fileName;
	private String itemDate;
	private Date iDate;
	private String searchTags;
	private String otherTags;
	
	private Log categoryLog;
	private Integer quantity;
	private String transactionType;
	private String remarks;
	
	private String jspMessage;
	private List<Log> logList;
	
	private String catId;
	
	//for Item that has Schedule
	private String day_monday[];
	private String day_tuesday[];
	private String day_wednesday[];
	private String day_thursday[];
	private String day_friday[];
	private String day_saturday[];
	private String day_sunday[];
	private Integer startTime[];
	private String startTimePost[];
	private Integer endingTime[];
	private String endingTimePost[];
	private Long itemScheduleId[];
	
	private Language language;
	private String httpServer;
	private List<Language> languages;
	
	private String[] fittings;
	
	private Integer pageNumber;
	
	private String successUrl;
	private String errorUrl;
	
	private File fileimage1;
	private String uploadStatus = "false";
	
	private List<Category> categories;
	
	private int formType;
	
	//agian
	private String agianEventName = "";
	
	PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	MemberImagesDelegate memberImagesDelegate = MemberImagesDelegate.getInstance();
	
	private NotificationDelegate notificationDelegate = NotificationDelegate.getInstance();
	private Long notifID;
	
	private final String NOT_ADDED_LIST_SESSION_PARAM = "notAddedList";
	private List<String> notAddedList;
	
	private String[] machineIds;
	private String[] branchIds;
	
	public String[] getMachineIds()
	{
		return machineIds;
	}

	public void setMachineIds(String[] machineIds)
	{
		this.machineIds = machineIds;
	}
	
	
	public String[] getBranchIds()
	{
		return branchIds;
	}

	public void setBranchIds(String[] branchIds)
	{
		this.branchIds = branchIds;
	}
	
	public List<Log> getLogList(){
		return logList;
	}
	
	public String getJspMessage() {
		return jspMessage;
	}

	public void setJspMessage(String jspMessage) {
		this.jspMessage = jspMessage;
	}

	private File[] files;
	public File[] getFiles() {
		return files;
	}
	
	public void setFiles(File[] files) {
		this.files = files;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public FileInputStream getFInStream() {
		return fInStream;
	}

	public void setFInStream(FileInputStream inStream) {
		fInStream = inStream;
	}

	private String errorType;
	

	@SuppressWarnings("unchecked")
	public void prepare() throws Exception 
	{		
		System.out.println("#### testttttttttt 1");
		setLanguages(company.getLanguages());
		companySettings = company.getCompanySettings();
		jspMessage="";
		//ArrayList<String> allAttributes = new ArrayList<String>();
		
		try {
			group = groupDelegate.find(Long.parseLong(request.getParameter("group_id")));			
		} catch (Exception e) {
			logger.debug("no group..." + e);
		}
		
		// try to create the parent category
		try {
			long parentCategoryId = Long.parseLong(request.getParameter("parent"));
			category = categoryDelegate.find(parentCategoryId);
			
			category.setParentGroup(group);
			if(!category.getCompany().equals(company) ) {
				category = null;
			}	
			
			item.setParent(category);		
			
		}
		catch(Exception e) {
			logger.debug("no parent name..." + e);
		}
		
		
		//loadBranches();
		//loadMachines();
		
		// try to create the category item 
		try {
			long categoryItemId = Long.parseLong(request.getParameter("item_id"));
			catId = request.getParameter("catId");
			item = categoryItemDelegate.find(categoryItemId);
			
			if(item == null && (company.getId() == CompanyConstants.HIPRECISION_DATA_SEARCH || company.getName().contains("hiprecision") ))
			{
				item = categoryItemDelegate.find(categoryItemId, Boolean.FALSE);
				
			}
		}
		catch(Exception e) {
			item = new CategoryItem();
			item.setCompany(company);
			item.setCreatedOn(new Date());
			item.setUpdatedOn(new Date());
			if(request.getParameter("search_tags") != null) searchTags = request.getParameter("search_tags");
			if(request.getParameter("other_tags") != null) otherTags = request.getParameter("other_tags");
			
			if(company.getName().equalsIgnoreCase("gurkkatest") && group.getName().equalsIgnoreCase("")){
				item.setSearchTags(searchTags);
				System.out.println("###### SearchTags :1: "+ searchTags);
				System.out.println("###### SearchTags :1.1: "+ item.getSearchTags());
				try{
					otherTags = request.getParameter("item.otherTags");
				}catch(Exception e1){}
				item.setOtherTags(otherTags);
				String tempSearchTags = "";
				CategoryItem tempCatItem = new CategoryItem();
				for(String s : searchTags.split("")){
					//test if not an empty string
					if(s.trim().length() > 0){
						tempCatItem = categoryItemDelegate.findByGroupAndName(company, group, s);
						
					}
				}
			}
			else{
				item.setSearchTags(searchTags);
				System.out.println("###### SearchTags :2: "+ searchTags);
				try{
					otherTags = request.getParameter("item.otherTags");
				}catch(Exception e1){}
				item.setOtherTags(otherTags);
			}
			item.setParentGroup(group);
		}

		//setting up language
		if(request.getParameter("language")!=null && item!=null){
			item.setLanguage(languageDelegate.find(request.getParameter("language"),company));
		}	
		
		// try to get the item image 
		try {
			long itemImageId = Long.parseLong(request.getParameter("image_id"));
			itemImage = itemImageDelegate.find(itemImageId);
			if(!itemImage.getItem().equals(item)) {
				itemImage = null;
			}
		}
		catch(Exception e) {
			logger.debug("no item image...");
		}
		
		
		// try to get the item file
		try {
			long itemFileId = Long.parseLong(request.getParameter("file_id"));
			itemFile = itemFileDelegate.find(itemFileId);
			if(!itemFile.getItem().equals(item)) {
				itemFile = null;
			}
		}
		catch(Exception e) {
			logger.debug("no item file...");
		}
		
		// try to create the brand {
		try {
			logger.debug(request.getParameter("BRAND ID: " + request.getParameter("brand_id")));
			
			long brandId = Long.parseLong(request.getParameter("brand_id"));
			brand = brandDelegate.find(brandId);
		}
		catch(Exception e) {
			logger.debug("no parent brand...");
		}
		
		if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
		{}
		
		if(item != null)
		{
			logList = logDelegate.findAll(item.getId(), EntityLogEnum.CATEGORY_ITEM).getList();
		}
		
		try{
			notAddedList = (List<String>) request.getSession().getAttribute(NOT_ADDED_LIST_SESSION_PARAM);
			request.getSession().setAttribute(NOT_ADDED_LIST_SESSION_PARAM, new ArrayList<String>());
			request.getSession().removeAttribute(NOT_ADDED_LIST_SESSION_PARAM);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(group != null){
			categories = group.getCategories();
			if(user != null && user.getCompany().getCompanySettings().getHasUserRights()){
				for(Category forbiddenCategory: user.getForbiddenCategories()){
					categories.remove(forbiddenCategory);
				}	
			}
			
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "deprecation"})
	public String updateAvailableQuantity() {
		JSONObject json = new JSONObject();		
		boolean updated = false;
		try {
			if(item != null) {
				if(transactionType.equals("ADD"))
					item.setAvailableQuantity(item.getAvailableQuantity() + quantity);
				else if(transactionType.equals("SUBTRACT"))
					item.setAvailableQuantity(item.getAvailableQuantity() - quantity);
				
				int outOfStock = 0;				
				if(company.getId() == 104) {	   // HBC
					outOfStock = 50;
				}else if(company.getId() == 257) { // One Stop Shop
					outOfStock = 3;
				}
				
				if(item.getAvailableQuantity() < outOfStock) {
					item.setIsOutOfStock(true);
				}else {
					item.setIsOutOfStock(false);	
				}
				
				if(categoryItemDelegate.update(item)) {
					categoryLog = new Log();
					categoryLog.setCompany(company);
					categoryLog.setUpdatedByUser(user);
					categoryLog.setEntityID(item.getId());
					categoryLog.setEntityType(EntityLogEnum.CATEGORY_ITEM);
					categoryLog.setTransactionType(transactionType);
					categoryLog.setAvailableQuantity(item.getAvailableQuantity());
					categoryLog.setQuantity(quantity);
					categoryLog.setRemarks(remarks);					
					
					logDelegate.insert(categoryLog);
					updated = true;
				}
			}			
			if(updated) {
				json.put("updated", true);
				json.put("availableQuantity", item.getAvailableQuantity());
				json.put("groupId", item.getParentGroup().getId());
				json.put("itemId", item.getId());
			}
			else
				json.put("updated", false);
			
			setInputStream(new StringBufferInputStream(json.toJSONString()));			
			return Action.SUCCESS;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return Action.ERROR;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public String execute() throws Exception {
		return Action.NONE;
	}
	
	public String save() throws IOException
	{
		//ClientConfigurationServlet.getInstance().getWendysPrivateKey2()
		/*
		final String result = SmsClient.getInstance().sendSms("https://sms.ivant.com",
				"QUEUEPAD123456", "1", "+639166681005", "Send Message Test 11-GENERIC", "PH", "WTGWENDYS", "Wendys",
				ClientConfigurationServlet.getInstance().getWendysPrivateKey2(),
				ClientConfigurationServlet.getInstance().getWendysHeaderNamePublicKey2(),
				ClientConfigurationServlet.getInstance().getWendysPublicKey2(),
				ClientConfigurationServlet.getInstance().getWendysHeaderNameSharedSecret2());
		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");
		System.out.println(result);
		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");
		*/
		if(company.getName().equals("bluelivetofeel")){
			logger.info("save invoked...");
			uploadStatus = request.getParameter("uploadImage");
			logger.info(uploadStatus);
		}
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		
		successUrl = (request.getParameter("successUrl") != null ? request.getParameter("successUrl") : "");
		setErrorUrl((request.getParameter("errorUrl") != null ? request.getParameter("errorUrl") : ""));
		
		String languageString = request.getParameter("language");
		if(remoteSave(null, null))
		{
			
			Log log = new Log();
			long itemId=-1;
			
			CategoryItem oldCategoryItem = (CategoryItem) request.getSession().getAttribute("oldCategoryItem");
			
			
			if(company.getName().equals("bluelivetofeel") && uploadStatus.equals("true")){
				if(!commonParamsValid()) {
					
				} 
			}else{
				if(!commonParamsValid()) {
					return Action.ERROR;
				} 
			}
			
			try {
				String[] dateString = itemDate.split("-");
				if(company.getName().equalsIgnoreCase("wendys")){
					itemDate = itemDate.replace(" ", "-");
					itemDate = itemDate.replace(":", "-");
					dateString = itemDate.split("-");
				}
				DateTime dt = new DateTime();
				if(company.getName().equalsIgnoreCase("wendys")){
					
					dt = new DateTime(Integer.parseInt(dateString[2]), 
							Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]), 
							Integer.parseInt(dateString[3]),
							Integer.parseInt(dateString[4]),
							Integer.parseInt(dateString[5]),0);
				}
				else
				{
					dt = new DateTime(Integer.parseInt(dateString[2]), 
							Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]), 0, 0 ,0 ,0);
				}
				iDate = new Date(dt.getMillis());
	
			}
			catch(Exception e) {
				logger.fatal("unable to properly process event date!!!");
			} 		
			
			if((languageString==null || languageString.isEmpty()) && !uploadStatus.equals("true")){
				if(categoryItemDelegate.find(item.getId()) == null) {
					System.out.println("##### diffent ########");
					ObjectList<CategoryItem> items = categoryItemDelegate.findAll(company, category, true);
												
					if(company.getName().equals("neltex")) {
						for(CategoryItem catItem : items) {
							if(catItem.getName().contains(item.getName())) {
								jspMessage=""+item.getName()+" already exists.";
								errorType = "itemexist";
								return ERROR;
							}
						}
					}
					
					if(items.getSize() < group.getMaxItems()) {
						item.setParent(category);
						item.setParentGroup(group);
						item.setBrand(brand);
						item.setItemDate(iDate);
						if(company.getName().equals("neltex")) {
							List<CategoryItem> categoryItems = items.getList();
							Collections.sort(categoryItems, new Comparator<CategoryItem>() {
								public int compare(CategoryItem s1, CategoryItem s2) {
									Integer so1 = new Integer(s1.getSortOrder());
									Integer so2 = new Integer(s2.getSortOrder());
									return so1.compareTo(so2);
								}
							});
							Collections.reverse(categoryItems);
							if(categoryItems != null && categoryItems.size() > 0) {
								item.setSortOrder(categoryItems.get(0).getSortOrder()+1);
							} else {
								item.setSortOrder(1);
							}
						}
						if(fittings != null) {
							Map<String, Long> catItemsMap = new HashMap<String, Long>();
							for(String catItemString : fittings) {
								catItemsMap.put(catItemString, new Long(catItemString));
							}
							item.setSubCategoryItems(new ArrayList<Long>(catItemsMap.values()));
						}
						if(company.getId() == CompanyConstants.HIPRECISION_DATA_SEARCH)
						{
							try
							{ // decode the name and sku, this is for special text/characters 
								final String nameEnc = item.getName();
								final String skuEnc = item.getSku();
								
								logger.info("Encoded Name: " + nameEnc + ", SKU: " + skuEnc);
								
								final String nameDec = java.net.URLDecoder.decode(nameEnc, ENCODING_DEFAULT);
								final String skuDec = java.net.URLDecoder.decode(skuEnc, ENCODING_DEFAULT);
								
								logger.info("Decoded Name: " + nameDec + ", SKU: " + skuDec);
								
								item.setName(nameDec);
								item.setSku(skuDec);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							
							//for hipre Data Search only
							if(item.getId() == null) // if item is a new item with same name
							{
								CategoryItem catDuplicate = categoryItemDelegate.findDuplicate(company, item.getName(), item.getSku(), item.getParent(), item.getParentGroup());
								if(catDuplicate != null){
									CategoryItemHistory categoryItemHistory = new CategoryItemHistory();
									
									categoryItemHistory.setRemarks("DUPLICATE :: Attempting to add duplicate Category Item with ID : " + catDuplicate.getId() + ", Name :  " + catDuplicate.getName() + ", SKU : " + catDuplicate.getSku() + " to database by User with username : " + user.getUsername() + ", User ID : " + user.getId());
									
									categoryItemHistory.setCompany(company);
									categoryItemHistory.setCategoryItem(catDuplicate);
									categoryItemHistory.setUser(user);
									
									categoryItemHistoryDelegate.insert(categoryItemHistory);
									
									
									logger.info("New Item cannot be added, because this item already exist from the database!");
									addActionError("Item cannot be added! Already exists from the database.");
									return ERROR;
								}
							}
							else
							{
								item.setIsValid(Boolean.TRUE);
							}
						}
						System.out.println("####### Category Item Search Tags  0  " + item.getSearchTags());
						System.out.println("####### Category Item Search Tags  6  " + item.getSearchTags());
						
						//for gurkka
						/*
						if(company.getId() == CompanyConstants.GURKKA_TEST && item.getSearchTags() != null){
							boolean isNum = false;
							CategoryItem itemFilter = new CategoryItem();
							Long strid = 0L;
							String tempSearchTags = "";
							System.out.println("#### Inside for Gurkka ");
							for(String s : item.getSearchTags().split(";")){
								System.out.println("#### Inside for Gurkka for Each ");
								try{
									if(!StringUtils.isEmpty(s) && StringUtils.isNumeric(s)){
										System.out.println("#### Inside for Gurkka - isnumeric");
										try{
											strid = Long.parseLong(s);
											itemFilter = categoryItemDelegate.find(strid);
											System.out.println("#### Inside for Gurkka - before gurkka condition for null ");
											if(itemFilter != null){
												System.out.println("#### Inside for Gurkka - itemfilter is not null");
												tempSearchTags = tempSearchTags + itemFilter.getId() + ";";
											}
											else{
												System.out.println("#### Inside for Gurkka - itemfilter is null");
											}
										}catch(Exception e){}
									}
									else{
										if(!StringUtils.isNumeric(s)){
											System.out.println("#### Inside for Gurkka - is not numeric");
											tempSearchTags = tempSearchTags + s + ";";
										}
									}
								}catch(Exception e1){}
							}
							//searchTags = tempSearchTags;
							System.out.println("######### Search Tag here 1 :: " + tempSearchTags);
							item.setSearchTags(tempSearchTags);
							System.out.println("###### SearchTags :3: "+ tempSearchTags);
						}
						*/
						String grp = request.getParameter("group_id");
						Group grpp = groupDelegate.find(Long.parseLong(grp));
						
						if(company.getName().equalsIgnoreCase("agian")){
							notifID = categoryItemDelegate.insert(item);
							item = categoryItemDelegate.find(notifID);
							if(grp.equals("614")){
								Notification notif = new Notification();
								notif.setCompany(company);
								notif.setTitle("1 new event");
								notif.setBy("");
								notif.setType("event");
								notif.setContent(item.getDescription());
								System.out.println(item.getId());
								notif.setNotifOne("events.do?event=" + item.getId() + "#panel" + item.getId());
								notificationDelegate.insert(notif);
								
								
							}else if (grp.equals("611")){
								Notification notif = new Notification();
								notif.setCompany(company);
								notif.setTitle("1 New file category");
								notif.setBy("");
								notif.setType("database");
								notif.setContent(item.getDescription());
								notif.setNotifOne("knowledge-database.do?db=" + item.getParent().getId());
								notificationDelegate.insert(notif);
							}
						}else{
							item = categoryItemDelegate.find(categoryItemDelegate.insert(item));
						}
						
						
						
						
						// Insert record to category item Logs
						if(item != null){
							CategoryItemHistory categoryItemHistory = new CategoryItemHistory();
							try{
								categoryItemHistory.setRemarks("INSERT :: Category Item with ID : " + item.getId() + ", Name :  " + item.getName() + ", SKU : " + item.getSku() + " was added to database by User with username : " + user.getUsername() + ", User ID : " + user.getId());
							}catch(Exception e){}
							categoryItemHistory.setCompany(company);
							categoryItemHistory.setCategoryItem(item);
							categoryItemHistory.setUser(user);
							
							categoryItemHistoryDelegate.insert(categoryItemHistory);
						}
						
						/////////////////////////////////////////////////////////
						
						
						if(item.getParentGroup().getHasScheduleForm()){
							item = newScheduleForm(item);
							if(item.getSchedules() != null)
								scheduleDelegate.batchInsert(item.getSchedules());
						}
		
						
						log.setRemarks("Added a " + category.getParentGroup().getName() + " item " +"\""+ item.getName() + "\"");
						
						for(int i=0; i<group.getAttributes().size(); i++) {
							itemAttribute = new ItemAttribute();
							itemAttribute.setValue(request.getParameter(group.getAttributes().get(i).getName()));
							itemAttribute.setDisabled(false);
							itemAttribute.setAttribute(group.getAttributes().get(i));
							itemAttribute.setCategoryItem(item);
							itemAttributeDelegate.insert(itemAttribute);
						}
					}
					else {
						jspMessage="Maximum number of items exceeded.";
						errorType = "maxitems";
						//System.out.println("error max items");
						return ERROR;
					}
				}
				else {
					logger.info("item.getId()" + item.getId());
					if(item.getId() == null) {
						errorType = "itemexist";
		
						return ERROR;
					}
					else {
						logger.info("========= here 1");
						item.setParent(category);
						item.setUpdatedOn(new Date());
						item.setBrand(brand);
						item.setItemDate(iDate);
						
						if(fittings != null) {
							Map<String, Long> catItemsMap = new HashMap<String, Long>();
							for(String catItemString : fittings) {
								catItemsMap.put(catItemString, new Long(catItemString));
							}
							item.setSubCategoryItems(new ArrayList<Long>(catItemsMap.values()));
						}
						
						
						if(request.getParameter("search_tags") != null) searchTags = request.getParameter("search_tags");
						//change below line of code for gurkka
						item.setSearchTags(searchTags);
						
						if(request.getParameter("other_tags") != null) otherTags = request.getParameter("other_tags");
						try{
							otherTags = request.getParameter("item.otherTags");
						}catch(Exception e1){}
						item.setOtherTags(otherTags);
						
						if(company.getName().equals("sandigan")){
							String ranks[] = request.getParameterValues("ranks");
							String info1 = "";
							for(String rank : ranks){
								info1 += rank + " ";
							}
							item.setInfo1(info1);
						}
						
//						if(categoryItemDelegate.update(item)){
						if(categoryItemDelegate.update(item, company, user)){
							// Insert record to category item Logs
							if(item != null){
								try
								{
									CategoryItemHistory categoryItemHistory = new CategoryItemHistory();
									
									categoryItemHistory.setRemarks("UPDATE :: Category Item with ID : " + item.getId() + ", Name :  " + item.getName() + ", SKU : " + item.getSku() + " was added to database by User with username : " + user.getUsername() + ", User ID : " + user.getId());
									
									categoryItemHistory.setCompany(company);
									categoryItemHistory.setCategoryItem(item);
									categoryItemHistory.setUser(user);
									
									categoryItemHistoryDelegate.insert(categoryItemHistory);
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
							}
							
							/////////////////////////////////////////////////////////
							if(item.getParentGroup()!=null && item.getParentGroup().getHasScheduleForm())
							{
								
								item = updateScheduleForm(item);
								
								itemId = item.getId();
								String remark = "Updated the " + category.getParentGroup().getName() + " item " + "\"" + item.getName() + "\"";
								
								String[] diff = LogHelper.getDifference(oldCategoryItem, item);
								
								logger.info("remarks... " + remarks);
								if(diff.length > 0)
								{// set the update remarks when the fields are updated.
									remark = remark + "~" + LogHelper.join(diff, "~");
									log.setRemarks(remark);
								}
							}
							
							
						}
						
						for(int i=0; i<group.getAttributes().size(); i++) {
							try {
								if(item.getAttributes().isEmpty()) {
									itemAttribute = new ItemAttribute();
									itemAttribute.setValue(request.getParameter(group.getAttributes().get(i).getName()));
									itemAttribute.setAttribute(group.getAttributes().get(i));
									itemAttribute.setCategoryItem(item);
									itemAttribute.setDisabled(false);
									
									itemAttributeDelegate.insert(itemAttribute);
								}
								else {
									if(group.getAttributes().size() == item.getAttributes().size()) {
										long itemAttributeId = Long.parseLong(request.getParameter(item.getAttributes().get(i).getId().toString()));
										itemAttribute = itemAttributeDelegate.find(itemAttributeId);
										itemAttribute.setValue(request.getParameter(item.getAttributes().get(i).getAttribute().getName()));
										itemAttribute.setDisabled(false);
										
										itemAttributeDelegate.update(itemAttribute);
									}
									else {
										int count=0;
										for(int k=0; k<item.getAttributes().size(); k++)
											if(group.getAttributes().get(i).getName() == item.getAttributes().get(k).getAttribute().getName()) count=1;
											if(count == 0) {
												itemAttribute = new ItemAttribute();
												itemAttribute.setValue(request.getParameter(group.getAttributes().get(i).getName()));
												itemAttribute.setAttribute(group.getAttributes().get(i));
												itemAttribute.setCategoryItem(item);
												itemAttribute.setDisabled(false);
												
												itemAttributeDelegate.insert(itemAttribute);
												categoryItemDelegate.update(item);
												// Insert record to category item Logs
												if(item != null){
													CategoryItemHistory categoryItemHistory = new CategoryItemHistory();
													
													categoryItemHistory.setRemarks("UPDATE :: Category Item with ID : " + item.getId() + ", Name :  " + item.getName() + ", SKU : " + item.getSku() + " was added to database by User with username : " + user.getUsername() + ", User ID : " + user.getId());
													
													categoryItemHistory.setCompany(company);
													categoryItemHistory.setCategoryItem(item);
													categoryItemHistory.setUser(user);
													
													categoryItemHistoryDelegate.insert(categoryItemHistory);
												}
												
												/////////////////////////////////////////////////////////
												
											}
											count = 0;
									}
								}	
							}
							catch(Exception e) {
								logger.debug("cannot insert item attribute.");
							}
						}
					}
				}
				
				if(item.getId() != null && ((group.getItemHasPrice() != null) ? group.getItemHasPrice() : false))
					savePrices(item);
				
			}else{
				
				if(!uploadStatus.equals("true")){
					//if categoryitem doesnt exist(insert)
					CategoryItemLanguage categoryItemLanguage = item.getCategoryItemLanguage();
					
					if(categoryItemLanguage==null){
						item.setLanguage(null);
						categoryItemLanguage = new CategoryItemLanguage();
						categoryItemLanguage.cloneOf(item);
						categoryItemLanguage.setLanguage(languageDelegate.find(request.getParameter("language"),company));
						categoryItemDelegate.refresh(item);
						categoryItemLanguage.setDefaultCategoryItem(item);
						categoryItemLanguageDelegate.insert(categoryItemLanguage);
					}
					//if categoryitem exist (update)
					else{							
						categoryItemLanguage.cloneOf(item);
						categoryItemDelegate.refresh(item);
						categoryItemLanguage.setDefaultCategoryItem(item);
						categoryItemLanguageDelegate.update(categoryItemLanguage);
						
					}
				}
			}
			
			if(item.getId() != null && company.getCompanySettings().getHasOtherFields())
			{
				//if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
				//{}
				//else
				//{
					saveOtherFields(item,languageString);
					//if(item.getParentGroup().getId() == 168L) {
					//	saveOtherFieldBranch(item);
					//	saveOtherFieldMachine(item);
					//}
						
				//}
			}
			 
//			log.setEntityType(EntityLogEnum.CATEGORY_ITEM);
//			log.setEntityID(itemId);
//			log.setCompany(company);
//			log.setUpdatedByUser(user);
			
			List<Log> updatedLogs = putToListAllChangesOnCategoryItem(oldCategoryItem);
			//System.out.println("-------2" + log.getRemarks());
			if(!company.getName().equals("bluelivetofeel") && !uploadStatus.equals("true")){
//				logDelegate.insert(log);
				if(updatedLogs.size() > 0){
					logger.info("*********************************************UPDATED LOGS IS GREATER THAN 0*********************************************");
					logDelegate.batchInsert(updatedLogs);
				}
				lastUpdatedDelegate.saveLastUpdated(company);
			}
			
			
			
//			item = categoryItemDelegate.find(categoryItemDelegate.insert(item));
			
			
			
			if(company.getName().equals("neltex") && files != null) {
				uploadImage();
			}
			System.out.println("############## labas ng upload ###################");
			if(company.getName().equals("wendys")) {
				System.out.println("############## dumaan sa upload ###################");
				MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
				File[] file = r.getFiles("fileimage1");
				String[] filename = r.getFileNames("fileimage1");
				String[] contenttype = r.getContentTypes("fileimage1");
				files = file;
				filenames = filename;
				contentTypes = contenttype;
				
				if(files!=null){
					uploadImage();	
				}
				if(item.getParentGroup().getName().equals("stores") && company.getId() == CompanyConstants.WENDYS){
					if(item.getSearchTags() != null){
						
						if(item.getImages().size() > 0){
							itemImage = item.getImages().get(0);
							deleteImage();
						}
						
					
						QRGenerator qrGenerator = new QRGenerator();
						
						String destinationPath = "companies";
						FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
						destinationPath += File.separator + company.getName();
						FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
						destinationPath += File.separator + "images";
						FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
						destinationPath += File.separator + "qrcodes";
						FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
						
						destinationPath = servletContext.getRealPath(destinationPath);
						
						File qr = new File(destinationPath + File.separator + item.getSearchTags() + ".jpg");
						System.out.println("FILE = " + qr.getAbsolutePath() + " " + qr.getPath());
						qr = qrGenerator.generateQRCode(item.getSearchTags(), qr);
						File newQr = new File(destinationPath + File.separator + item.getSearchTags() + ".jpg");
						System.out.println(newQr.getPath());
						System.out.println(newQr.exists());
						System.out.println(newQr.getName());
						System.out.println(newQr.toString());
						
						File[] file2 = new File[1];
						String[] filename2 = new String[1];
						String[] contenttype2 = new String[1];
						
						file2[0] = newQr;
						filename2[0] = newQr.getName();
						contenttype2[0] = "";
						
						files = file2;
						filenames = filename2;
						contentTypes = contenttype2;
						
						uploadImage();
					}
				}
			}
			
			if(company.getName().equals("politiker")) {
				MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
				File[] file = r.getFiles("fileimage1");
				String[] filename = r.getFileNames("fileimage1");
				String[] contenttype = r.getContentTypes("fileimage1");
				files = file;
				filenames = filename;
				contentTypes = contenttype;
				
				if(files!=null)
				{
					uploadImage();
				}
				
			}
			
			
			if(company.getName().equalsIgnoreCase("gurkkatest")) {
				MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
				File[] file = r.getFiles("fileimage1");
				String[] filename = r.getFileNames("fileimage1");
				String[] contenttype = r.getContentTypes("fileimage1");
				files = file;
				filenames = filename;
				contentTypes = contenttype;
				
				if(files!=null)
				{
					uploadImage();
				}
				
				obj.put("success", true);
				
				objList.add(obj);
				obj2.put("listCocktailsItemDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));
				System.out.println("######################DUmaan dito :: "+obj2.toJSONString()+"###################");
			}


			//bluelivetofeel
			
			if(company.getName().equals("bluelivetofeel")) {
				/*JSONObject obj1 = new JSONObject();
				obj1.put("success", false);*/
					successUrl = request.getParameter("successUrl");
					errorUrl = request.getParameter("errorUrl");
					
					PromoCode promo_code = promoCodeDelegate.findByCode(company, request.getParameter("promocode"));
					Member member = new Member();
					member = memberDelegate.findByUsername(company, request.getParameter("user"));
					MemberImages member_image = new MemberImages();
					if(promo_code != null){
						
						if(promo_code.getIsDisabled() == true){
							/*obj1.put("message", "Promo code already submitted.");
							setInputStream(new ByteArrayInputStream(obj1.toJSONString().getBytes()));*/
							errorUrl +="alreadysubmitted";
							return ERROR;
						}else{
							//obj1.put("success", true);
							member_image.setCompany(company);
							member_image.setPromoCode(request.getParameter("promocode"));
							member_image.setOrNumber(request.getParameter("ornumber"));
							member_image.setImageName(request.getParameter("image_name"));
							member_image.setMember(member);
							member_image.setStatus("New");
							promo_code.setIsDisabled(true);
							logger.info(promo_code.getIsDisabled());
							promo_code.setNote(request.getParameter("user"));
							promoCodeDelegate.update(promo_code);
							
							MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
							File[] file = r.getFiles("files");
							String[] filename = r.getFileNames("files");
							String[] contenttype = r.getContentTypes("files");
							files = file;
							filenames = filename;
							contentTypes = contenttype;
							
							if(files!=null)
							{
								logger.info("UPLOADING..............");
								saveImagesBlue(files, filenames, contentTypes, member_image);
								logger.info("UPLOADING SUCCESS !!!!!!!!!!!!!!!!!!!!!!!!!");
								//setInputStream(new ByteArrayInputStream(obj1.toJSONString().getBytes()));
								return Action.SUCCESS;
								
							}
						}
						
					
					}else{
						/*obj1.put("message", "Promo Code Invalid.");
						setInputStream(new ByteArrayInputStream(obj1.toJSONString().getBytes()));*/
						errorUrl+="invalid";
						return ERROR;
					}
				
			}
			
			//bluelivetofeel
			String grp = request.getParameter("group_id");
			Group grpp = groupDelegate.find(Long.parseLong(grp));
			if(company.getName().equalsIgnoreCase("agian") && grpp.getId() == 614){
				
				for(int q = 6; q < 9; q++){
					String id = "1747" + q;
					Category parentCat = categoryDelegate.find(Long.parseLong(id));
					/*List<CategoryItem> itemss = categoryItemDelegate.findAllByGroup(company, grpp).getList();*/
					List<CategoryItem> itemss = categoryItemDelegate.findAllByGroupAndParent(company, grpp, parentCat).getList();
					
					int n = itemss.size(), c, d, swap;
					int array[] = new int[itemss.size()];
					int z = 0;
					for(CategoryItem ci : itemss) {
						String name = ci.getName();
						String[] names = name.split(" ");
						String[] date = names[0].split("-");
						array[z] = Integer.parseInt(date[0] + "" + date[1]);
						z++;
					}
					
					for (c = 0; c < ( n - 1 ); c++) {
					      for (d = 0; d < n - c - 1; d++) {
					        if (array[d] < array[d+1]) 
					        {
					          swap       = array[d];
					          array[d]   = array[d+1];
					          array[d+1] = swap;
					        }
					      }
					    }
					String[] array2 = new String[array.length];
					for (c = 0; c < n; c++){ 
						System.out.println(array[c]);
						int j = array[c];
						String x = Integer.toString(j);
						x = x.substring(0, 4) + "-" + x.substring(4, x.length());
						array2[c] = x;
						System.out.println(x);
					}
					
					for(CategoryItem ci : itemss) {
						for(int y = 0; y < array2.length; y++){
							if(ci.getName().contains(array2[y])){
								ci.setSortOrder(y);
								categoryItemDelegate.update(ci);
								System.out.println(ci.getName());
							}
						}
					}
				}
			}
			
			System.out.println("######################DUmaan dito sa success ###################");
			return Action.SUCCESS;
		}
		else
		{
			if(company.getName().equalsIgnoreCase("gurkkatest")) {
				obj.put("errorMessage", "error");
				
				objList.add(obj);
				obj2.put("listCocktailsItemDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));
				System.out.println("######################DUmaan sa error ###################");
				return Action.SUCCESS;
			}
			return Action.ERROR;
		}
	}
	
	private List<Log> putToListAllChangesOnCategoryItem(CategoryItem oldCategoryItem){
		List<Log> updateLogList = new ArrayList<Log>();
		Log log = new Log();
						
		try{
		log.setEntityType(EntityLogEnum.CATEGORY_ITEM);
		log.setEntityID(item.getId());
		log.setCompany(company);
		log.setUpdatedByUser(user);
		
		if(!oldCategoryItem.getParent().equals(item.getParent())){
			log.setRemarks("Change Parent Category from " + oldCategoryItem.getName() + " to " + item.getName());
			updateLogList.add(log);
		}
		
		if(!oldCategoryItem.getName().equals(item.getName())){
			log.setRemarks("Change Item Name from " + oldCategoryItem.getName() + " to " + item.getName());
			updateLogList.add(log);
		}
		
		if(!oldCategoryItem.getSku().equals(item.getSku())){
			log.setRemarks("Change SKU from " + oldCategoryItem.getSku() + " to " + item.getSku());
			updateLogList.add(log);
		}
		
		if(oldCategoryItem.getCategoryItemOtherFields().size() > 0){
			int counter = 0;
			List<CategoryItemOtherField> newCategoryItemOtherField = new ArrayList<CategoryItemOtherField>(item.getCategoryItemOtherFields());
			for(CategoryItemOtherField otherField: oldCategoryItem.getCategoryItemOtherFields()){
				CategoryItemOtherField newOtherField = newCategoryItemOtherField.get(counter);
				if(!otherField.getContent().equals(newOtherField.getContent())){
					log.setRemarks("Change "+otherField.getOtherField().getName() +" from "+ otherField.getContent() + " to " + newOtherField.getContent());
					updateLogList.add(log);
				}
				counter++;
			}
		}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
		return updateLogList;
	}
	
	
	
	
	private CategoryItem newScheduleForm(CategoryItem item) {
		
		List<Schedule> scheduleList = new ArrayList<Schedule>();

		for(int i = 0; i < day_monday.length ; i++){
			
			Schedule schedule = new Schedule();
			
				schedule = setScheduleDetails(schedule , i , item);
			
			scheduleList.add(schedule);
			
		}
		item.setSchedules(scheduleList);
		return item;
	}
	
	private CategoryItem updateScheduleForm(CategoryItem item){
		
		//System.out.println("UPDATE SCHEDULE FORM :: START");
		List<Schedule> oldScheduleList = item.getSchedules();
		List<Schedule> scheduleList = new ArrayList<Schedule>();
		
		//System.out.println("UPDATE SCHEDULE FORM :: ");
		
		
		if( itemScheduleId!=null && itemScheduleId.length > 0){
			//System.out.println("ITEM SCHEDULE SIZE :: " + itemScheduleId.length );
			for(Long id:itemScheduleId){
				Schedule s = scheduleDelegate.find(id);
				if(s!=null)
					oldScheduleList.remove(s);
			}
		}
		//delete all schedules that are now not selected
		if(oldScheduleList!=null && oldScheduleList.size()>0)
			for(Schedule sched:oldScheduleList)
				scheduleDelegate.delete(sched);
		
		//System.out.println("(day_monday!=null)"+(day_monday!=null));
		
		
		if(day_monday!=null)
		for(int i = 0; i < day_monday.length ; i++){
			Boolean isUpdate = Boolean.FALSE;
			Schedule schedule = new Schedule();
			if(itemScheduleId!=null && itemScheduleId.length>i){
				schedule = scheduleDelegate.find(itemScheduleId[i]);
				isUpdate = Boolean.TRUE;
			}
			
			schedule = setScheduleDetails(schedule , i , item);
			
			//System.out.println("isUpdate  "+isUpdate);
			
			
			if(isUpdate)
				scheduleDelegate.update(schedule);
			else
				scheduleDelegate.insert(schedule);
			
			scheduleList.add(schedule);
		}
		item.setSchedules(scheduleList);
		
		//System.out.println("UPDATE SCHEDULE FORM :: END");
		return item;
			
	}

	private Schedule setScheduleDetails(Schedule schedule,int i,CategoryItem item){
		
		schedule.setIsValid(Boolean.TRUE);
		schedule.setCompany(company);
		schedule.setCreatedOn(new Date());
		
		String dailySchedule =
				day_monday[i] +","  +day_tuesday[i] +"," +day_wednesday[i] +"," +day_thursday[i] +"," 
				+day_friday[i] +"," +day_saturday[i] +"," +day_sunday[i];
		
		schedule.setDailySchedule(dailySchedule);
		schedule.setStartTime(startTime[i]);
		schedule.setStartTimePost(startTimePost[i]);
		schedule.setEndTime(endingTime[i]);
		schedule.setEndTimePost(endingTimePost[i]);
		schedule.setCategoryItem(item);
		
		return schedule;
	}
	
	private void savePrices(CategoryItem item)
	{
		List<CategoryItemPriceName> names = item.getParent().getParentGroup().getCategoryItemPriceNames();
		if(names != null)
		{
			if(names.size() > 0)
			{
				item.setCategoryItemPriceName(names.get(0));
				categoryItemDelegate.update(item);
			}
			if(names.size() > 1)
			{
				for(int i = 1; i < names.size(); i++)
				{
					
					CategoryItemPrice itemPrice = categoryItemPriceDelegate.findByCategoryItemPriceName(company, item, names.get(i));
					if(itemPrice != null)
					{
						String priceString = request.getParameter("p"+names.get(i).getId());
						if(priceString==null||priceString.equalsIgnoreCase(""))
							priceString = "0.00";
						Double temp = Double.parseDouble(priceString);
						//System.out.println("Price String: "+temp);
						itemPrice.setAmount(temp);
						categoryItemPriceDelegate.update(itemPrice);
					//	}
					}
					else
					{
						String priceString = request.getParameter("p"+names.get(i).getId());
						if(StringUtils.isNotEmpty(priceString))
						{
							//System.out.println("Price String: "+priceString);
							CategoryItemPrice newItemPrice = new CategoryItemPrice();
							newItemPrice.setAmount(Double.parseDouble(priceString));
							newItemPrice.setCategoryItem(item);
							newItemPrice.setCategoryItemPriceName(names.get(i));
							newItemPrice.setCompany(company);
							newItemPrice.setIsValid(Boolean.TRUE);
							
							categoryItemPriceDelegate.insert(newItemPrice);
						}
					}
				}
			}
		}
	}
	

	private void saveOtherFieldBranch(CategoryItem item)
	{
		System.out.println("saveOtherFieldBranch INVOKED!");
		
		//Delete old branch/s
		List<CategoryItemOtherFieldBranch> branches = categoryItemOtherFieldBranchDelegate.findByCategoryItem(company, item).getList();
		for(CategoryItemOtherFieldBranch branch : branches){
			categoryItemOtherFieldBranchDelegate.delete(branch);
			//System.out.println("Delete Branch id: " + branch.getId().toString());
		}
		
		//Insert new branch/s
		for(String id : branchIds){
			
			System.out.println("Branch id: " + id);
			CategoryItem branch = categoryItemDelegate.find(Long.parseLong(id));
			
			if(branch != null) {
				String branchName = branch.getName();
				System.out.println("Branch NAME: " + branchName);
				
				CategoryItemOtherFieldBranch newBranchItem = new CategoryItemOtherFieldBranch();
				newBranchItem.setCategoryItemBranch(branch);
				newBranchItem.setContent(branchName);
				newBranchItem.setCompany(company);
				newBranchItem.setCategoryItem(item);
				
				categoryItemOtherFieldBranchDelegate.insert(newBranchItem);
			}
			

		}
	}
	
	private void saveOtherFieldMachine(CategoryItem item)
	{
		System.out.println("saveOtherFieldMachine INVOKED!");
		
		//Delete old machine/s
		List<CategoryItemOtherFieldMachine> machines = categoryItemOtherFieldMachineDelegate.findByCategoryItem(company, item).getList();
		for(CategoryItemOtherFieldMachine machine : machines){
			categoryItemOtherFieldMachineDelegate.delete(machine);
			//System.out.println("Delete machine id: " + machine.getId().toString());
		}
		
		//Insert new machine/s
		for(String id : machineIds){
			
			System.out.println("Machine id: " + id);
			CategoryItem machine = categoryItemDelegate.find(Long.parseLong(id));
			
			if(machine != null) {
				String machineName = machine.getName();
				System.out.println("Machine NAME: " + machineName);
				
				CategoryItemOtherFieldMachine newMachineItem = new CategoryItemOtherFieldMachine();
				newMachineItem.setCategoryItemMachine(machine);
				newMachineItem.setContent(machineName);
				newMachineItem.setCompany(company);
				newMachineItem.setCategoryItem(item);
				
				categoryItemOtherFieldMachineDelegate.insert(newMachineItem);
			}
			

		}
	}
	
	
	private void saveOtherFields(CategoryItem item, String languageString)
	{
		List<Log> updateLogList = new ArrayList<Log>();
		
		List<OtherField> otherFields = item.getParent().getParentGroup().getOtherFields();
		if(otherFields != null)
		{
			if(otherFields.size() > 0)
			{
				for(int i = 0; i < otherFields.size(); i++)
				{
					final OtherField of = otherFields.get(i);
					String content = request.getParameter("o" + of.getId());
					String name = of.getName();
					if(company.getName().equalsIgnoreCase("agian") && name.equals("Date")){
						content = request.getParameter("agian_event_date");
						String[] dateToName = content.split(" ");
						String m = dateToName[0];
						if(m.equalsIgnoreCase("january")){
							m = "01";
						}else if(m.equalsIgnoreCase("february")){
							m = "02";
						}else if(m.equalsIgnoreCase("march")){
							m = "03";
						}else if(m.equalsIgnoreCase("april")){
							m = "04";
						}else if(m.equalsIgnoreCase("may")){
							m = "05";
						}else if(m.equalsIgnoreCase("june")){
							m = "06";
						}else if(m.equalsIgnoreCase("july")){
							m = "07";
						}else if(m.equalsIgnoreCase("august")){
							m = "08";
						}else if(m.equalsIgnoreCase("september")){
							m = "09";
						}else if(m.equalsIgnoreCase("october")){
							m = "10";
						}else if(m.equalsIgnoreCase("november")){
							m = "11";
						}else if(m.equalsIgnoreCase("december")){
							m = "12";
						}else{
							m = "01";
						}
						String[] dd = dateToName[1].split(",");
						String d = dd[0];
						if(d.length() == 1){
							d = "0" + d;
						}
						String y = dateToName[2];
						agianEventName = y + "-" + m + d;
						
						String itemName = item.getName();
						String[] names = itemName.split(" ");
						if(!NumberUtils.isNumber(names[0])){
							item.setName(agianEventName + " " + itemName);
							categoryItemDelegate.update(item);
						}
					}
					if((company.getId() == 321) && (name.contains("Article No.")))
					{
						content = "";
						String[] contents = request.getParameterValues("o" + of.getId());
						for(int index = 0; index < contents.length; index++)
						{
							String str = contents[index];
							content += (content != ""
								? "$$" + str
								: str);
						}
					}
					
					if(CompanyConstants.HIPRECISION_DATA_SEARCH == company.getId())
					{
						try
						{
							if(content != null)
							{
								content = java.net.URLDecoder.decode(content, ENCODING_DEFAULT);
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
					
					if(CompanyConstants.WENDYS == company.getId())
					{
						if(StringUtils.equalsIgnoreCase(name, "Selected Wendy's Branches") || Long.valueOf(690).equals(of.getId()))
						{
							final boolean sendNotification = StringUtils.containsIgnoreCase(content, WendysConstants.SUFFIX_BRANCH_ALL);
							
							if(sendNotification)
							{
								final CategoryItem it = categoryItemDelegate.find(item.getId());
								final Date itemDate = it.getItemDate();
								if(itemDate != null)
								{
									final DateTime dt = new DateTime(itemDate);
									if(dt.isAfterNow())
									{
										final String message = StringUtils.abbreviate(it.getName(), 0, 40);
										final String response = PromoItemClient.getInstance()
												.sendNotification(it, 
														message,
														new DateTime(), 
														dt);
												
												logger.info("Response: " + response);
									}
								}
							}
						}
					}
					
					CategoryItemOtherField otherField = categoryItemOtherFieldDelegate.findByCategoryItemOtherField(company, item, of);
					
					if(languageString == null || languageString.isEmpty())
					{
						
						if(otherField != null)
						{
							if(company.getId() == CompanyConstants.GURKKA_TEST){}
							else{
								
								otherField.setContent(content);
							}
							
							
//							categoryItemOtherFieldDelegate.update(otherField);
							categoryItemOtherFieldDelegate.update(item, otherField, company, user, updateLogList);
						}
						else
						{
							if(StringUtils.isNotEmpty(content))
							{
								CategoryItemOtherField newOtherField = new CategoryItemOtherField();
								if(company.getId() == CompanyConstants.GURKKA_TEST){}
								else{
									newOtherField.setContent(content);
								}
								
								newOtherField.setCategoryItem(item);
								newOtherField.setOtherField(of);
								newOtherField.setCompany(company);
								newOtherField.setIsValid(Boolean.TRUE);
								
								categoryItemOtherFieldDelegate.insert(newOtherField);
							}
						}
					}
					else
					{
						CategoryItemOtherFieldLanguage categoryItemOtherFieldLanguage = new CategoryItemOtherFieldLanguage();
						if(otherField == null)
						{
							otherField = new CategoryItemOtherField();
							otherField.setContent("");
							otherField.setCategoryItem(item);
							otherField.setOtherField(of);
							otherField.setCompany(company);
							otherField.setIsValid(Boolean.TRUE);
							
							otherField = categoryItemOtherFieldDelegate.find(categoryItemOtherFieldDelegate.insert(otherField));
						}
						otherField.setLanguage(languageDelegate.find(languageString, company));
						categoryItemOtherFieldLanguage = otherField.getCategoryItemOtherFieldLanguage();
						// if CategoryItemOtherFieldLanguage doesnt exist(insert)
						if(categoryItemOtherFieldLanguage == null)
						{
							otherField.setLanguage(null);
							categoryItemOtherFieldLanguage = new CategoryItemOtherFieldLanguage();
							categoryItemOtherFieldLanguage.cloneOf(otherField);
							categoryItemOtherFieldLanguage.setContent(content);
							categoryItemOtherFieldLanguage.setLanguage(languageDelegate.find(languageString, company));
							categoryItemOtherFieldDelegate.refresh(otherField);
							categoryItemOtherFieldLanguage.setDefaultCategoryItemOtherField(otherField);
							categoryItemOtherFieldLanguageDelegate.insert(categoryItemOtherFieldLanguage);
						}
						// if CategoryItemOtherFieldLanguage exist (update)
						else
						{
							categoryItemOtherFieldLanguage.cloneOf(otherField);
							categoryItemOtherFieldDelegate.refresh(otherField);
							categoryItemOtherFieldLanguage.setContent(content);
							categoryItemOtherFieldLanguage.setDefaultCategoryItemOtherField(otherField);
							categoryItemOtherFieldLanguageDelegate.update(categoryItemOtherFieldLanguage);
							
						}
					}
					
				}
				logDelegate.batchInsert(updateLogList);
				
			}
		}
		
	}
	
	public void saveWilconOtherFields(CategoryItem item, String content, int index){
		List<OtherField> otherFields = item.getParent().getParentGroup().getOtherFields();
		
		if(otherFields != null){
			if(otherFields.size() > 0){
				final OtherField of = otherFields.get(index);
				String name = of.getName();
				
				CategoryItemOtherField otherField = categoryItemOtherFieldDelegate.findByCategoryItemOtherField(company, item, of);
				if(otherField != null){
					otherField.setContent(content);
					categoryItemOtherFieldDelegate.update(otherField);
					
				}else{
					if(StringUtils.isNotEmpty(content)){
						CategoryItemOtherField newOtherField = new CategoryItemOtherField();
						newOtherField.setContent(content);
						
						newOtherField.setCategoryItem(item);
						newOtherField.setOtherField(of);
						newOtherField.setCompany(company);
						newOtherField.setIsValid(Boolean.TRUE);
						
						categoryItemOtherFieldDelegate.insert(newOtherField);
					}
				}
				
				
			}
		}
		
	}
	
	public String delete() {
		
		if(CompanyConstants.LANDSYSTEMS == company.getId())
		{
			user = UserDelegate.getInstance().findAllByCompany(company).get(0);
			if(user == null)
			{
				user = UserDelegate.getInstance().find(76L); // Maxine na lang, Hahaha
			}
		}
		
		if(!commonParamsValid()) {
			return Action.ERROR;
		} 
		if(!item.getCompany().equals(company)) {
			return Action.ERROR;
		}
/* if to delete all item attributes when the item is deleted
 		for(int i=0; i<group.getAttributes().size(); i++) {
			if(group.getAttributes().get(0).getIsValid()) {
				try {
					long itemAttributeId = Long.parseLong(request.getParameter("item_itemattribute["+i+"]"));
					itemAttribute= itemAttributeDelegate.find(itemAttributeId);
				}
				catch(Exception e){}
			}
		}
		itemAttributeDelegate.delete(itemAttribute);
*/
		
		// Insert record to category item Logs
		if(item != null){
			CategoryItemHistory categoryItemHistory = new CategoryItemHistory();
			
			categoryItemHistory.setRemarks("DELETED :: Category Item with ID : " + item.getId() + ", Name :  " + item.getName() + ", SKU : " + item.getSku() + " was added to database by User with username : " + user.getUsername() + ", User ID : " + user.getId());
			
			categoryItemHistory.setCompany(company);
			categoryItemHistory.setCategoryItem(item);
			categoryItemHistory.setUser(user);
			
			categoryItemHistoryDelegate.insert(categoryItemHistory);
		}
		
		/////////////////////////////////////////////////////////
		
		String cat = group.getName();
		item.setSubCategoryItems(new ArrayList<Long>());
		categoryItemDelegate.update(item);
		item = categoryItemDelegate.find(item.getId());
		final boolean deleted = categoryItemDelegate.delete(item);
		if(deleted && company.getId() == CompanyConstants.HIPRECISION_DATA_SEARCH)
		{
			final Map<String, String> customParams = new HashMap<String, String>();
			customParams.put("item.valid", Boolean.FALSE.toString());
			customParams.put("item.sku", item.getSku());
			customParams.put("item.name", item.getName());
			customParams.put("isValid", Boolean.FALSE.toString());
			customParams.put("delete", Boolean.TRUE.toString());
			if(remoteSave(item.getSku(), customParams))	
			{
				logger.info("Item deleted in company#id " + company.getId());
			}
		}
		if(company.getName().equalsIgnoreCase("politiker")){
			try{
				
				
				//MemberPoll poll = memberPollDelegate.findByCategoryItem(item);
				//memberPollDelegate.delete(poll);
				
				
				
			}
			catch(Exception e){
				
			}
		}
		//delete action log
		Log log = new Log();
		log.setEntityType(EntityLogEnum.CATEGORY_ITEM);
		log.setRemarks("Deleted the " + cat + " item " +"\""+ item.getName()+"\"");
		log.setEntityID(item.getId());
		log.setCompany(company);
		log.setUpdatedByUser(user);
		logDelegate.insert(log);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public String deleteComment() {
		ItemComment comment;
		if(company == null) {
			return Action.ERROR;
		}
			
		// try to get the page comment
		
		//System.out.println("DELETE COMMENT "+request.getParameter("commen_tid"));
		long commentId = Long.parseLong(request.getParameter("comment_id"));
		
		item = categoryItemDelegate.find(Long.parseLong(request.getParameter("item_id")));
		
		request.setAttribute("item", item); 
		
		//System.out.println("Delete ID "+commentId);
		comment = itemCommentDelegate.find(commentId);
		
		itemCommentDelegate.delete(comment);		
			
		return Action.SUCCESS;
	}
	
	public String edit() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		} 
		
		if(!item.getCompany().equals(company)) {
			return Action.ERROR;
		}
		
		preparePrices(item);
		prepareOtherFields(item);
		
		String[] transType = {"ADD", "SUBTRACT", "SYSTEM"};
		request.getSession().setAttribute("listOfLogs",logDelegate.findAllLogs(company, item.getId(), EntityLogEnum.CATEGORY_ITEM, transType));
		
		//System.out.println("HEre2!!!");
		request.getSession().setAttribute("oldCategoryItem",item.clone());
		
		if(CompanyConstants.LANDSYSTEMS == company.getId())
		{ /* Testing muna */
			loadMenu();
		}
		
		
		loadBranchesSelected();
		loadMachinesSelected();
		
		return Action.SUCCESS;
	}
	
	private void loadBranches() {
		
		List<CategoryItem> branchList = null;
		if(Long.parseLong(request.getParameter("group_id")) == 168){
			System.out.println("Analytes Group SELECTED!");
			
			Group branches = null;
			branches = groupDelegate.find(159L);
			branchList = categoryItemDelegate.findAllByGroup(company, branches).getList();
		}
		request.setAttribute("branchList", branchList);
	}
	
	private void loadMachines() {
		
		List<CategoryItem> machineList = null;
		if(Long.parseLong(request.getParameter("group_id")) == 168){
			System.out.println("Analytes Group SELECTED!");
			
			Group machines = null;
			machines = groupDelegate.find(170L);
			machineList = categoryItemDelegate.findAllByGroup(company, machines).getList();
		}
		request.setAttribute("machineList", machineList);
	}
	
	private void loadMachinesSelected() {

		List<CategoryItemOtherFieldMachine> machineListSelected = null;
		if(Long.parseLong(request.getParameter("group_id")) == 168){
			machineListSelected = categoryItemOtherFieldMachineDelegate.findByCategoryItem(company, item).getList();
		}
		request.setAttribute("machineListSelected", machineListSelected);
	}
	
	private void loadBranchesSelected() {

		List<CategoryItemOtherFieldBranch> branchListSelected = null;
		if(Long.parseLong(request.getParameter("group_id")) == 168){
			branchListSelected = categoryItemOtherFieldBranchDelegate.findByCategoryItem(company, item).getList();
		}
		request.setAttribute("branchListSelected", branchListSelected);
	}
	
	
	
	private void preparePrices(CategoryItem item)
	{
		
		List<CategoryItemPriceName> names = item.getParentGroup().getCategoryItemPriceNames();
		HashMap<Long, Double> prices = new HashMap<Long, Double>();
		if(names != null)
		{
			if(names.size() > 1)
			{
				for(int i=1; i< names.size(); i++)
				{
					CategoryItemPrice price = categoryItemPriceDelegate.findByCategoryItemPriceName(company, item, names.get(i));
					if(price != null)
						prices.put(names.get(i).getId(), price.getAmount());
					else
						prices.put(names.get(i).getId(), 0d);
				}
			}
			request.setAttribute("prices", prices);
		}
	}
	
	private void prepareOtherFields(CategoryItem item)
	{
		
		List<OtherField> otherFields = item.getParentGroup().getOtherFields();
		HashMap<Long, String> temp = new HashMap<Long, String>();
		if(otherFields != null)
		{
			if(otherFields.size() > 0)
			{
				for(int i=0; i< otherFields.size(); i++)
				{
					CategoryItemOtherField otherField = categoryItemOtherFieldDelegate.findByCategoryItemOtherField(company, item, otherFields.get(i));
					
					if(otherField != null){
						//setting up language
						if(request.getParameter("language")!=null && item!=null){
							otherField.setLanguage(languageDelegate.find(request.getParameter("language"),company));
						}	
						temp.put(otherFields.get(i).getId(), otherField.getContent());
					}
					else
						temp.put(otherFields.get(i).getId(), "");
				}
			}
			request.setAttribute("otherFields", temp);
		}
	}
	
	public String uploadImage() { 
		logger.debug("UPLOOOOOOOOOOOOOOAAAAAAAAAAAAADDDDDDDDDDD");
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		if( !company.equals(item.getCompany()) )  {	
			return Action.ERROR;
		}
		
		
		
		saveImages(files, filenames, contentTypes);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public String deleteImage() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		if( !company.equals(item.getCompany()) )  {	
			return Action.ERROR;
		}

		String destinationPath = servletContext.getRealPath("companies" + 
				File.separator + company.getName() +
				File.separator + "images" +
				File.separator + "items");
		
		// delete original
		FileUtil.deleteFile(destinationPath + File.separator + itemImage.getOriginal());		
		// delete image1
		FileUtil.deleteFile(destinationPath + File.separator + itemImage.getImage1());
		// delete image2
		FileUtil.deleteFile(destinationPath + File.separator + itemImage.getImage2());
		// delete image2
		FileUtil.deleteFile(destinationPath + File.separator + itemImage.getImage3());
		// delete thumbnail
		FileUtil.deleteFile(destinationPath + File.separator + itemImage.getThumbnail());

		itemImageDelegate.delete(itemImage);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public String none(){
		return Action.SUCCESS;
	}
	
	public String saveManaged(){
		
		String[] cboxes = request.getParameterValues("cboxes");
		 
		for(int i=0; i<item.getAttributes().size(); i++) {
			//System.out.println("EEEEE "+ request.getParameter(item.getAttributes().get(i).getId().toString()));
			long itemAttributeId = Long.parseLong(request.getParameter(item.getAttributes().get(i).getId().toString()));
			itemAttribute = itemAttributeDelegate.find(itemAttributeId);
			
			if(cboxes == null){
				itemAttribute.setDisabled(true);
			}
			else{
				for( String it : cboxes){
					if(item.getAttributes().get(i).getAttribute().getName().equals(it)) {
						itemAttribute.setDisabled(false);
						break;
					}
					else itemAttribute.setDisabled(true);
				}
			}
			itemAttributeDelegate.update(itemAttribute);
		}
		
		if(group.getAttributes().size() > item.getAttributes().size()){
			boolean contains = false;
			for( Attribute x : group.getAttributes()){
				for( ItemAttribute y : item.getAttributes())
					if(x.getName().equals(y.getAttribute().getName())) {
						contains = true; 
						break;
					}
					else contains = false;
				
				if(!contains){
					ItemAttribute itemAttribute = new ItemAttribute();
					itemAttribute.setAttribute(x);
					itemAttribute.setCategoryItem(item);
					for( String it : cboxes){
						if(it.equals(x.getName())) {
							itemAttribute.setDisabled(false);
							break;
						}
						else itemAttribute.setDisabled(true);
					}
					
					itemAttributeDelegate.insert(itemAttribute);
				}
				contains = false;
			}
		}
		return Action.SUCCESS;
	}
	
	
	//bluelivetofeel
	
	private void saveImagesBlue(File[] files, String[] filenames, String[] contentTypes, MemberImages member_image) {
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		destinationPath += File.separator + company.getName();
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "uploadedImages";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				
		// create the image1, image2, image3 and thumbnail folders
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image1"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image2"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image3"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "thumbnail"));
		
		destinationPath = servletContext.getRealPath(destinationPath);
		
		
		for(int i = 0; i < files.length; i++) {			
			if(files[i].exists()) {
				String source = files[i].getAbsolutePath();
				
				String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"), 
						String.valueOf(new Date().getTime()));	
				/*
				//I removed this code because it intercept the gif / png upload, and change the file to JPG format
				filename = FileUtil.replaceExtension(filename, "jpg"); 
				*/
				
				// generate original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				FileUtil.copyFile(files[i], origFile);	
				
				if(i==0) {
					member_image.setUploadedImageName(filename);
					memberImagesDelegate.insert(member_image);
				}
				
				Long companyId = company.getId();
				// generate image 1
				
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename, 
								companySettings.getImage1Width(), companySettings.getImage1Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
					}
					else{
						ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename, 
								companySettings.getImage1Width(), companySettings.getImage1Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
					}
				}catch(Exception e){}
				
				// generate image 2
				
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename, 
								companySettings.getImage2Width(), companySettings.getImage2Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
					}
					else{
						ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename, 
								companySettings.getImage2Width(), companySettings.getImage2Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
					}
				}catch(Exception e){}
				
				// generate image 3
				
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename, 
								companySettings.getImage3Width(), companySettings.getImage3Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
					}
					else{
						ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename, 
								companySettings.getImage3Width(), companySettings.getImage3Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
					}
				}catch(Exception e){}
				
				// generate thumbnail
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
						
					}
					else{
						ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
						
					}
				}catch(Exception e){}
				
			}
		}
	}
	
	//bluelivetofeel
	
	private void saveImages(File[] files, String[] filenames, String[] contentTypes) {
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
		destinationPath += File.separator + "items";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				
		// create the image1, image2, image3 and thumbnail folders
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image1"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image2"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image3"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "thumbnail"));
		
		destinationPath = servletContext.getRealPath(destinationPath);
		
		
		for(int i = 0; i < files.length; i++) {			
			if(files[i].exists()) {
				String source = files[i].getAbsolutePath();
				
				String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_").replace(".JPG", ".jpg"), 
						String.valueOf(new Date().getTime()));	
				/*
				//I removed this code because it intercept the gif / png upload, and change the file to JPG format
				filename = FileUtil.replaceExtension(filename, "jpg"); 
				*/
				
				// generate original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				FileUtil.copyFile(files[i], origFile);	
				Long companyId = company.getId();
				// generate image 1
				
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename, 
								companySettings.getImage1Width(), companySettings.getImage1Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
					}
					else{
						ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename, 
								companySettings.getImage1Width(), companySettings.getImage1Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
					}
				}catch(Exception e){}
				
				// generate image 2
				
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename, 
								companySettings.getImage2Width(), companySettings.getImage2Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
					}
					else{
						ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename, 
								companySettings.getImage2Width(), companySettings.getImage2Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
					}
				}catch(Exception e){}
				
				// generate image 3
				
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename, 
								companySettings.getImage3Width(), companySettings.getImage3Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
					}
					else{
						ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename, 
								companySettings.getImage3Width(), companySettings.getImage3Heigth(), 
								ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
					}
				}catch(Exception e){}
				
				// generate thumbnail
				
				try{
					if(filename.toLowerCase().indexOf(".gif")>0){
						ImageUtil.generateGifThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
						
					}
					else{
						ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
						
					}
				}catch(Exception e){}
				
				ItemImage itemImage = new ItemImage();
				itemImage.setFilename(filenames[i].replace(".JPG", ".jpg"));
				itemImage.setItem(item);
//				itemImage.setUrl(UrlUtil.generateImageUploadUrl(company) + "items/");
				itemImage.setOriginal("original/" + filename);
				itemImage.setImage1("image1/" + filename);
				itemImage.setImage2("image2/" + filename);
				itemImage.setImage3("image3/" + filename);
				itemImage.setThumbnail("thumbnail/" + filename);
				
				itemImageDelegate.insert(itemImage);
			}
		}
	}
	
	
	public boolean commonParamsValid() {
		if(company == null) {
			return false;
			
		}
		if(group == null) {
			return false;
		}
		
		return true;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CategoryItem getItem() {
		return item;
	}

	public void setItem(CategoryItem item) {
		this.item = item;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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
	

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	public String getErrorType() {
		return errorType;
	}
	
	public ItemFile getItemFile() {
		return itemFile;
	}

	public void setItemFile(ItemFile itemFile) {
		this.itemFile = itemFile;
	}
	
	

	
	public String deleteFile() {
		if(company == null) {
			return Action.ERROR;
		}
		if( !company.equals(item.getCompany()) )  {	
			return Action.ERROR;
		}  
		
		String destinationPath = servletContext.getRealPath("companies" + 
				File.separator + company.getName() +
				File.separator + "images" + File.separator  + "items"  + File.separator + "uploads");
	
		FileUtil.deleteFile(destinationPath + File.separator + itemFile.getFilePath());
	
		
		
		
		try {
			logger.info("FILE: " + destinationPath + File.separator + "-m-" + File.separator +  itemFile.getFilePath());
		boolean success = (new File(destinationPath + File.separator + itemFile.getFilePath())).delete();
	    if (!success) {
	        // Deletion failed
	    }		
		}catch (Exception e) {
	    	e.getStackTrace();
	    }
	    itemFileDelegate.delete(itemFile);
		lastUpdatedDelegate.saveLastUpdated(company);
		
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
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator  + "images" + File.separator + "items"    + File.separator + "uploads"));
		int cnt = 0;
		for(int i = 0; i < files.length; i++) {
			if(company.getName().equalsIgnoreCase("agian")){
				cnt++;
				if(files[i].exists()) {
					File file = files[i];
					String filename = filenames[i];
					String contentType = contentTypes[i];
					String destination0 = servletContext.getRealPath(destinationPath + File.separator   + "images" + File.separator + "items");
					String destination = servletContext.getRealPath(destinationPath + File.separator   + "images" + File.separator + "items"    + File.separator + "uploads");
					////System.out.println("DESTINATION:  " + destination);
					new File(destination0).mkdir();
					new File(destination).mkdir();
					logger.info("UPloaddddd : "  +  destination + File.separator + filename  );
					File destFile = new File(destination + File.separator + filename);
					
					FileUtil.copyFile(file, destFile); 
					
					ItemFile itemFile = new ItemFile();
					itemFile.setCompany(company);
					itemFile.setFileName(filename);
					itemFile.setFilePath("uploads" + "/" + filename);
					itemFile.setFileType(contentType);
					itemFile.setItem(item);
					itemFile.setFileSize(file.length()/1000);
					//System.out.println("filesize-------------------------------------"+itemFile.getFileSize());
					itemFileDelegate.insert(itemFile);
				}
				String grp = request.getParameter("group_id");
				Notification notif = new Notification();
				notif.setCompany(company);
				notif.setTitle(cnt + " new file/s has been uploaded");
				notif.setBy("");
				notif.setType("database");
				if(grp.equals("614")){
					notif.setNotifOne("knowledge-database.do?db=2");
				}else{
					notif.setNotifOne("knowledge-database.do?db="+item.getParent().getId());
				}
				notificationDelegate.insert(notif);
			}else{
				if(files[i].exists()) {
					File file = files[i];
					String filename = filenames[i];
					String contentType = contentTypes[i];
					String destination0 = servletContext.getRealPath(destinationPath + File.separator   + "images" + File.separator + "items");
					String destination = servletContext.getRealPath(destinationPath + File.separator   + "images" + File.separator + "items"    + File.separator + "uploads");
					////System.out.println("DESTINATION:  " + destination);
					new File(destination0).mkdir();
					new File(destination).mkdir();
					logger.info("UPloaddddd : "  +  destination + File.separator + filename  );
					File destFile = new File(destination + File.separator + filename);
					
					FileUtil.copyFile(file, destFile); 
					
					ItemFile itemFile = new ItemFile();
					itemFile.setCompany(company);
					itemFile.setFileName(filename);
					itemFile.setFilePath("uploads" + "/" + filename);
					itemFile.setFileType(contentType);
					itemFile.setItem(item);
					itemFile.setFileSize(file.length()/1000);
					//System.out.println("filesize-------------------------------------"+itemFile.getFileSize());
					itemFileDelegate.insert(itemFile);
				} 
			}
		}
	}

	
	
	
	
	public String downloadFile() throws Exception {
		
		
		
		// try to get the item file
		try {
			long itemFileId = Long.parseLong(request.getParameter("file_id"));
			itemFile = itemFileDelegate.find(itemFileId);
			if(!itemFile.getItem().equals(item)) {
				itemFile = null;
			}
		}
		catch(Exception e) {
			logger.debug("no item file...");
		}
		
		
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		fileName = itemFile.getFileName();
		File file  = new File (servletContext.getRealPath(destinationPath + File.separator   + "images" + File.separator + "items"    + File.separator  + itemFile.getFilePath()));
		logger.info("DOWNload : " + servletContext.getRealPath(destinationPath + File.separator   + "images" + File.separator + "items"    + File.separator  + itemFile.getFilePath()));
//		File file = new File(destination + File.separator + itemFile.getFilePath());
//		String filePath = destFile;
//		File file = new File(filePath);
		if(!file.exists()) {
			logger.fatal("Unabled to locate file: ");
		}
		
		fInStream = new FileInputStream(file);
		inputStream = new FileInputStream(file);
		contentLength = file.length();
		return SUCCESS;
	}
	
	
	
	
	public String uploadFile() { 
		
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		if( !company.equals(item.getCompany()) )  {	
			return Action.ERROR;
		}
		
		saveFiles(files, filenames, contentTypes);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	private void loadMenu() {
		try {
			Map<String, Menu> menuList = new HashMap<String, Menu>();
			if(httpServer == null || httpServer.isEmpty() )
			{
				httpServer = ("http://" + request.getServerName());
			}
			
			// get the single pages
			List<SinglePage> singlePageList = SinglePageDelegate.getInstance().findAll(company).getList();
			for(SinglePage singlePage : singlePageList) {
				singlePage.setLanguage(language);
				String jspName = singlePage.getJsp(); 
				Menu menu = new Menu(singlePage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(singlePage.getJsp(), menu);
			}
			// get the multi pages
			List<MultiPage> multiPageList = MultiPageDelegate.getInstance().findAll(company).getList();
			request.setAttribute("multiPageList", multiPageList);
			//System.out.println("THESE ARE THE MULTIPAGES "+multiPageList.size());	
			for(MultiPage multiPage : multiPageList) {
				multiPage.setLanguage(language);
				String jspName = multiPage.getJsp();
				Menu menu = new Menu(multiPage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(multiPage.getJsp(), menu);
			}
			
			// get the form Pages
			List<FormPage> formPageList = FormPageDelegate.getInstance().findAll(company).getList();
			for(FormPage formPage : formPageList) {
				String jspName = formPage.getJsp();
				Menu menu = new Menu(formPage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(formPage.getJsp(), menu);
			}
			// get the groups
			List<Group> groupList = groupDelegate.findAll(company).getList();
			request.setAttribute("groupList", groupList);
			for(Group group : groupList) {
				String jspName = group.getName().toLowerCase();
				Menu menu = new Menu(group.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(jspName, menu);
			}
			request.setAttribute("menu", menuList); 
			/*--------------------------------------------------------------------------*/
			/*---------------------------------------------------------------------------*/
			
			/*-------------------------------------------------------------*/
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action in FrontMenuInterceptor. " + e);
		}
	}

	public String getItemDate() {
		return itemDate;
	}

	public void setItemDate(String itemDate) {
		this.itemDate = itemDate;
	}

	public Date getIDate() {
		return iDate;
	}

	public void setIDate(Date date) {
		iDate = date;
	}

	public void setCategoryLog(Log categoryLog) {
		this.categoryLog = categoryLog;
	}

	public Log getCategoryLog() {
		return categoryLog;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatId() {
		return catId;
	}
	
	

	public String[] getDayArray(){
		
		return new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		
	}



	public String[] getStartTimePost() {
		return startTimePost;
	}

	public void setStartTimePost(String[] startTimePost) {
		this.startTimePost = startTimePost;
	}

	public String[] getEndingTimePost() {
		return endingTimePost;
	}

	public void setEndingTimePost(String[] endingTimePost) {
		this.endingTimePost = endingTimePost;
	}

	public String[] getDay_monday() {
		return day_monday;
	}

	public void setDay_monday(String[] day_monday) {
		this.day_monday = day_monday;
	}

	public String[] getDay_tuesday() {
		return day_tuesday;
	}

	public void setDay_tuesday(String[] day_tuesday) {
		this.day_tuesday = day_tuesday;
	}

	public String[] getDay_wednesday() {
		return day_wednesday;
	}

	public void setDay_wednesday(String[] day_wednesday) {
		this.day_wednesday = day_wednesday;
	}

	public String[] getDay_thursday() {
		return day_thursday;
	}

	public void setDay_thursday(String[] day_thursday) {
		this.day_thursday = day_thursday;
	}

	public String[] getDay_friday() {
		return day_friday;
	}

	public void setDay_friday(String[] day_friday) {
		this.day_friday = day_friday;
	}

	public String[] getDay_saturday() {
		return day_saturday;
	}

	public void setDay_saturday(String[] day_saturday) {
		this.day_saturday = day_saturday;
	}

	public String[] getDay_sunday() {
		return day_sunday;
	}

	public void setDay_sunday(String[] day_sunday) {
		this.day_sunday = day_sunday;
	}

	public void setEndingTime(Integer endingTime[]) {
		this.endingTime = endingTime;
	}

	public Integer[] getEndingTime() {
		return endingTime;
	}

	public void setStartTime(Integer startTime[]) {
		this.startTime = startTime;
	}

	public Integer[] getStartTime() {
		return startTime;
	}

	public void setItemScheduleId(Long itemScheduleId[]) {
		this.itemScheduleId = itemScheduleId;
	}

	public Long[] getItemScheduleId() {
		return itemScheduleId;
	}

	public List<Component> getComponentList() {
		
		return componentDelegate.findAll(-1, -1, null, company).getList();
	}
	
	
	public String getSample(){
		return "THIS IS SAMPLE";
	}
	
	private String message;
	
	public String saveUploadItem() throws IOException{
		String contentType = contentTypes[0];
		String pathName = filenames[0];
		String filetype = FilenameUtils.getExtension(pathName);
		
		logger.info("Executing --- addUploadItem()");
		group = groupDelegate.find(Long.parseLong(request.getParameter("groupId")));
		
		if(!commonParamsValid()) {
			setMessage("You dont have permission to upload items.");
			return Action.ERROR;
		}
		
		if(contentType.compareTo("application/vnd.ms-excel")!=0 && contentType.compareTo("text/csv")!=0){
			setMessage("File not valid. Create your file using MsExcel 2003 or CSV file.");
			return Action.ERROR;
		}
		
		//read the uploaded file
		InputStream input = null;
		
		try {
			input = new FileInputStream(files[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			setMessage("File not found.");
			return Action.ERROR;
		}
		
		HSSFWorkbook wb=null;
		
		try {
			
			
			if(filetype.equals("xls")){
				wb = new HSSFWorkbook(input);
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
			setMessage("File not valid. Create your file using MsExcel 2003 or CSV file.");
			return Action.ERROR;
		}catch (Exception e) {
			e.printStackTrace();
			setMessage("File not valid. Create your file using MsExcel 2003 or CSV file.");
			return Action.ERROR;
		} 
		
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.KUYSEN)){
			kuysenItemUpload(wb);
		}
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.SANDIGAN)){
			sandiganItemUpload(wb);
		}
		
		if(company.getName().equalsIgnoreCase("ayrosohardware")){
			ayrosoItemUpload(wb);
		}
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.WILCON)){
			if(filetype.equals("xls")){
				wilconExcelUpload(wb);
			}
			
		}
		
		if(company.getId() == CompanyConstants.HIPRECISION_DATA_SEARCH){
			hpdsItemUpload(wb);
		}
		
		setMessage("Item successfully added.");
		return SUCCESS;
	}
	
	private void ayrosoItemUpload(HSSFWorkbook wb) {
		// TODO Auto-generated method stub
		Log log = new Log();
		int itemsRead=0;
		
		final short CODE_COLUMN = 1;
		final short NAME_COLUMN = 2;
		final short DESCRIPTION_COLUMN = 3;
		final short BRAND_COLUMN = 4;
		final short GROUP_CATEGORY_COLUMN = 5;
		final short CATEGORY_COLUMN = 6;
		final short SUB_CATEGORY_COLUMN = 7;
		
		ObjectList<CategoryItem> allItem = categoryItemDelegate.findAllByGroup(company, group);
		HashMap<String, CategoryItem> allItemMap = new HashMap<String, CategoryItem>();
		for(CategoryItem item:allItem.getList()){
			String key = item.getName()+item.getSku();
			allItemMap.put(key, item);
		}
		
		for(int index=0; index<wb.getNumberOfSheets();index++){	//sheet iteration
			HSSFSheet sheet  = wb.getSheetAt(index);
			System.out.println(" SHEET INDEX " + index);
			for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); itemsRead++) {		//row iteration
				Row row = rowIterator.next();				
				HashMap<String, String> otherFieldMap = new HashMap<String, String>();
				
				System.out.println(" ROW INDEX " + row.getRowNum());
				
				String code = "";
				String name = "";
				String description = "";
				String brandName = "";
				String groupCategory = "";
				String category = "";
				String subCategory = "";
				
				Brand brand = new Brand();
				Category groupParent = new Category();
				Category parent = new Category();
				Category subParent = new Category();
				for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();) {	//cell iteration
					
					if(row.getRowNum()<6){
						//start in second row
						break;
					}						
					Cell cell = cellIterator.next();
					
					System.out.println(" CELL INDEX " + cell.getColumnIndex());
					
					switch (cell.getColumnIndex()) {
					case CODE_COLUMN:
						try {
							switch(cell.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									code = cell.getStringCellValue();
									break;
								case Cell.CELL_TYPE_NUMERIC:
									code = new Double(cell.getNumericCellValue()).toString();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case NAME_COLUMN:
						try {
							name = cell.getStringCellValue();
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case DESCRIPTION_COLUMN:
						try {
							description = cell.getStringCellValue();
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case BRAND_COLUMN:
						try {
							brandName = cell.getStringCellValue();
							brand = brandDelegate.find(company, group, brandName);
							if(brand == null && !StringUtils.isBlank(brandName)){								
								
								brand = new Brand();
								brand.setGroup(group);
								brand.setCompany(company);
								brand.setName(brandName);
								brand.setDescription(brandName);
								brand = brandDelegate.find(brandDelegate.insert(brand));
								lastUpdatedDelegate.saveLastUpdated(company);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case GROUP_CATEGORY_COLUMN:
						try {
							groupCategory = cell.getStringCellValue();
							groupParent = categoryDelegate.find(company, groupCategory, group);
							if(groupParent == null && !StringUtils.isBlank(groupCategory)){								
								User persistedUser = userDelegate.load(user.getId());													
								
								groupParent = new Category();
								groupParent.setParentGroup(group);
								groupParent.setCompany(company);
								groupParent.setName(groupCategory);
								groupParent.setDescription(groupCategory);
								groupParent.setCreatedBy(persistedUser);
								groupParent = categoryDelegate.find(categoryDelegate.insert(groupParent));
								lastUpdatedDelegate.saveLastUpdated(company);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case CATEGORY_COLUMN:
						try {
							category = cell.getStringCellValue();
							parent = categoryDelegate.find(company, category, groupParent, group);
							if(parent == null && !StringUtils.isBlank(category) && groupParent != null){								
								User persistedUser = userDelegate.load(user.getId());													
								
								parent = new Category();
								parent.setParentGroup(group);
								parent.setCompany(company);
								parent.setName(category);
								parent.setDescription(category);
								parent.setParentCategory(groupParent);
								parent.setCreatedBy(persistedUser);
								parent = categoryDelegate.find(categoryDelegate.insert(parent));
								lastUpdatedDelegate.saveLastUpdated(company);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case SUB_CATEGORY_COLUMN:
						try {
							subCategory = cell.getStringCellValue();
							subParent = categoryDelegate.find(company, subCategory, parent, group);
							if(subParent == null && !StringUtils.isBlank(subCategory) && parent != null){								
								User persistedUser = userDelegate.load(user.getId());													
								
								subParent = new Category();
								subParent.setParentGroup(group);
								subParent.setCompany(company);
								subParent.setName(subCategory);
								subParent.setDescription(subCategory);
								subParent.setParentCategory(parent);
								subParent.setCreatedBy(persistedUser);
								subParent = categoryDelegate.find(categoryDelegate.insert(subParent));
								lastUpdatedDelegate.saveLastUpdated(company);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
				
				if((groupParent != null || parent!=null || subParent != null) && !StringUtils.isBlank(code) && !StringUtils.isBlank(name)){
					String key = name+code;
					item = allItemMap.get(key);
					if(item==null){
						item = new CategoryItem();
						item.setName(name);
						item.setSku(code);
						item.setBrand(brand);
						item.setCompany(company);
						item.setCreatedOn(new Date());
						item.setUpdatedOn(new Date());
						item.setParentGroup(group);
						item.setParent((subParent != null ? subParent : parent != null ? parent : groupParent));
						item.setSearchTags(item.getName()+" "+code);
						System.out.println("###### SearchTags :5: "+ searchTags);
						try{
							otherTags = request.getParameter("item.otherTags");
						}catch(Exception e1){}
						item.setOtherTags(otherTags);
						item.setDescription(description);
						System.out.println("####### Category Item Search Tags  2  " + item.getSearchTags());
						item = categoryItemDelegate.find(categoryItemDelegate.insert(item));
						allItemMap.put(key,item);
						logger.info(item.getName() + " successfully added.");
					} else {
						item.setName(name);	
						item.setSku(code);
						item.setBrand(brand);
						item.setCompany(company);
						item.setCreatedOn(new Date());
						item.setUpdatedOn(new Date());
						item.setParentGroup(group);
						item.setParent((subParent != null ? subParent : parent != null ? parent : groupParent));
						item.setSearchTags(item.getName()+" "+code);
						try{
							otherTags = request.getParameter("item.otherTags");
						}catch(Exception e1){}
						item.setOtherTags(otherTags);
						item.setDescription(description);
						categoryItemDelegate.update(item);
						logger.info(item.getName() + " successfully updated.");
					}
					
					log.setEntityType(EntityLogEnum.CATEGORY_ITEM);
					log.setEntityID(item.getId());
					log.setCompany(company);
					log.setUpdatedByUser(user);
					// System.out.println("-------2" + log.getRemarks());
					logDelegate.insert(log);
					lastUpdatedDelegate.saveLastUpdated(company);
				}
				
			}
		}
		
	}

	private void sandiganItemUpload(HSSFWorkbook wb){
		Log log = new Log();		
		int itemsRead=0;
		
		final short RANK_COLUMN = 0;
		final short NAME_COLUMN = 1;
		final short BDAY_COLUMN = 2;
		final short CERTIFICATE_COLUMN = 3;
		final short REGISTRATION_NO_COLUMN = 4;
		final short COURSE_COLUMN = 5;
		final short BACTH_NO_COLUMN = 6;
		final short DURATION_COLUMN = 7;
		final short DAYS_COLUMN = 8;
		
		//load all item and put in map
		ObjectList<CategoryItem> allItem = categoryItemDelegate.findAllByGroup(company, group);
		HashMap<String, CategoryItem> allItemMap = new HashMap<String, CategoryItem>();
		for(CategoryItem item:allItem.getList()){
			String key = item.getName()+item.getParent().getName();
			allItemMap.put(key, item);
		}
		
		
		for(int index=0; index<wb.getNumberOfSheets();index++){	//sheet iteration
			HSSFSheet sheet  = wb.getSheetAt(index);
			for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); itemsRead++) {		//row iteration
				Row row = rowIterator.next();				
				HashMap<String, String> otherFieldMap = new HashMap<String, String>();
				
				String rank = "";
				String name = "";
				Date bday = null;
				String certificate = "";
				String registrationNo = "";
				String course = "";
				String batchNo = "";
				String duration = "";
				String days = "";
				
				Category parent = new Category();
				for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();) {	//cell iteration
					
					if(row.getRowNum()<1){
						//start in second row
						break;
					}						
					Cell cell = cellIterator.next();
					switch (cell.getColumnIndex()) {
					case RANK_COLUMN:
						try {
							rank = cell.getStringCellValue();							
						} catch (Exception e) {
							e.printStackTrace();
						}
						otherFieldMap.put("Rank", rank);
						break;
					case NAME_COLUMN:
						try {
							name = cell.getStringCellValue();
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case BDAY_COLUMN:
						try {
							bday = cell.getDateCellValue();
						} catch (Exception e) {
							bday = new Date(cell.getStringCellValue());
							e.printStackTrace();
						}
						if(bday!=null){
							SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
							String birthday = format.format(bday);							
							otherFieldMap.put("Bday", birthday);
						}						
						break;
					case CERTIFICATE_COLUMN:
						try {
							certificate = cell.getStringCellValue();							
						} catch (Exception e) {
							e.printStackTrace();
						}
						otherFieldMap.put("Certificate", certificate);
						break;
					case REGISTRATION_NO_COLUMN:
						try {
							registrationNo = cell.getStringCellValue();
						} catch (Exception e) {
							e.printStackTrace();
						}
						otherFieldMap.put("Registration No.", registrationNo);
						break;
					case COURSE_COLUMN:
						try {
							course = cell.getStringCellValue();
							parent = categoryDelegate.find(company, course, group);
							if(parent == null && !StringUtils.isBlank(course)){								
								User persistedUser = userDelegate.load(user.getId());													
								
								parent = new Category();
								parent.setParentGroup(group);
								parent.setCompany(company);
								parent.setName(course);
								parent.setDescription(course);
								parent.setCreatedBy(persistedUser);
								parent = categoryDelegate.find(categoryDelegate.insert(parent));
								lastUpdatedDelegate.saveLastUpdated(company);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case BACTH_NO_COLUMN:
						try {
							batchNo = cell.getStringCellValue();
						} catch (Exception e) {
							e.printStackTrace();
						}
						otherFieldMap.put("Batch No.", batchNo);
						break;

					case DURATION_COLUMN:
						try {
							duration = cell.getStringCellValue();
						} catch (Exception e) {
							e.printStackTrace();
						}
						otherFieldMap.put("Duration", duration);
						break;
						
					case DAYS_COLUMN:
						try {
							days = String.valueOf(cell.getNumericCellValue());
						} catch (Exception e) {
							e.printStackTrace();
						}
						otherFieldMap.put("Days", days);
						break;
						
					default:
						break;
					}
					
				}
				if(parent!=null&&!StringUtils.isBlank(course)){
					String key = name+course;
					item = allItemMap.get(key);
					if(item==null){
						item = new CategoryItem();
						item.setName(name);					
						item.setCompany(company);
						item.setCreatedOn(new Date());
						item.setUpdatedOn(new Date());
						item.setParentGroup(group);
						item.setParent(parent);
						item.setSearchTags(item.getName()+" "+course);
						try{
							otherTags = request.getParameter("item.otherTags");
						}catch(Exception e1){}
						item.setOtherTags(otherTags);
						System.out.println("####### Category Item Search Tags   3 " + item.getSearchTags());
						item = categoryItemDelegate.find(categoryItemDelegate.insert(item));
						allItemMap.put(key,item);
						logger.info(item.getName() + " successfully added.");
					}else{
						item.setName(name);					
						item.setCompany(company);
						item.setCreatedOn(new Date());
						item.setUpdatedOn(new Date());
						item.setParentGroup(group);
						item.setParent(parent);
						item.setSearchTags(item.getName()+" "+course);
						try{
							otherTags = request.getParameter("item.otherTags");
						}catch(Exception e1){}
						item.setOtherTags(otherTags);
						categoryItemDelegate.update(item);
						logger.info(item.getName() + " successfully updated.");
						
					}
					
					if (item != null) {
						if (item.getId() != null
								&& company.getCompanySettings()
										.getHasOtherFields()) {
							List<OtherField> otherFields = item.getParent()
									.getParentGroup().getOtherFields();
							if (otherFields != null) {
								if (otherFields.size() > 0) {
									for (int i = 0; i < otherFields.size(); i++) {
										CategoryItemOtherField otherField = categoryItemOtherFieldDelegate
												.findByCategoryItemOtherField(
														company, item,
														otherFields.get(i));
										boolean isNewOption = true;

										// if not null other field is for update
										if (otherField != null) {
											String content = otherFieldMap
													.get(otherFields.get(i)
															.getName());

											if (content != null) {
												// inserting new options in
												// other fields
												if (otherFields
														.get(i)
														.getOtherFieldValueList() != null
														&& otherFields
																.get(i)
																.getOtherFieldValueList()
																.size() > 0) {

													for (OtherFieldValue value : otherFields
															.get(i)
															.getOtherFieldValueList()) {
														if (content
																.equals(value)) {
															isNewOption = false;
															break;
														}
													}
													if (isNewOption) {
														OtherFieldValue newFieldValue = new OtherFieldValue();
														newFieldValue
																.setValue(content);
														newFieldValue
																.setOtherField(otherFields
																		.get(i));

														otherFieldValueDelegate
																.insert(newFieldValue);
													}

												}

												otherField.setContent(content);
												categoryItemOtherFieldDelegate
														.update(otherField);
											}
										} else {
											String content = otherFieldMap
													.get(otherFields.get(i)
															.getName());
											if (StringUtils.isNotEmpty(content)) {
												// inserting new options in
												// other fields
												if (otherFields
														.get(i)
														.getOtherFieldValueList() != null
														&& otherFields
																.get(i)
																.getOtherFieldValueList()
																.size() > 0) {

													for (OtherFieldValue value : otherFields
															.get(i)
															.getOtherFieldValueList()) {
														if (content
																.equals(value)) {
															isNewOption = false;
															break;
														}
													}
													if (isNewOption) {
														OtherFieldValue newFieldValue = new OtherFieldValue();
														newFieldValue
																.setValue(content);
														newFieldValue
																.setOtherField(otherFields
																		.get(i));

														otherFieldValueDelegate
																.insert(newFieldValue);
													}

												}

												CategoryItemOtherField newOtherField = new CategoryItemOtherField();
												newOtherField
														.setContent(content);
												newOtherField
														.setCategoryItem(item);
												newOtherField
														.setOtherField(otherFields
																.get(i));
												newOtherField
														.setCompany(company);
												newOtherField
														.setIsValid(Boolean.TRUE);

												categoryItemOtherFieldDelegate
														.insert(newOtherField);
											}
										}
									}
								}
							}

						}

						log.setEntityType(EntityLogEnum.CATEGORY_ITEM);
						log.setEntityID(item.getId());
						log.setCompany(company);
						log.setUpdatedByUser(user);
						// System.out.println("-------2" + log.getRemarks());
						logDelegate.insert(log);
						lastUpdatedDelegate.saveLastUpdated(company);

					}
				}
			}
		}
	}
	
	private void kuysenItemUpload(HSSFWorkbook wb){
		Log log = new Log();
		//load all item and put in map
		ObjectList<CategoryItem> allItem = categoryItemDelegate.findAllByGroup(company, group);
		HashMap<String, CategoryItem> allItemMap = new HashMap<String, CategoryItem>();
		for(CategoryItem item:allItem.getList()){
			String artNo = item.getCategoryItemOtherFieldMap().get("ART. NO.").getContent();
			allItemMap.put(artNo, item);
		}
		
		//read the values from the file
		int itemsRead=0;
		boolean isAboveThirdRow = false;
		
		for(int index=0; index<wb.getNumberOfSheets();index++){	//sheet iteration
			HSSFSheet sheet  = wb.getSheetAt(index);
			for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); itemsRead++) {		//row iteration
				Row row = rowIterator.next();
				HashMap<String, String> otherFieldMap = new HashMap<String, String>();
				
				String name = "";
				Double price = 0.0;
				String artNoKey = "";
				
				Category parent = new Category();
				for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();) {	//cell iteration
					
					if(row.getRowNum()<=1){
						//start in third row
						break;
					}	
					isAboveThirdRow = true;
					Cell cell = cellIterator.next();
					if(cell.getColumnIndex()==0){			//column BRAND
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}											
						brand = brandDelegate.find(company, group, value);
						if(brand == null){	
							brand = new Brand();
							brand.setGroup(group);
							brand.setCompany(company);
							brand.setName(value);
							brandDelegate.insert(brand);
							lastUpdatedDelegate.saveLastUpdated(company);
							brand = brandDelegate.find(company, group, value);
						}
						
						
					}
					if(cell.getColumnIndex()==1){			//column SERIES
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}
						otherFieldMap.put("Series", value);					
					}
					if(cell.getColumnIndex()==2){			//column ART. NO.
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}
						
						otherFieldMap.put("ART. NO.", value);
						artNoKey = value;
					}
					if(cell.getColumnIndex()==3){			//column COMMENTS
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}
						
						otherFieldMap.put("Comments", value);
					}
					if(cell.getColumnIndex()==4){			//column CAT1 parent category
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}
														
						parent = categoryDelegate.find(company, value, group);
						if(parent == null){								
							User persistedUser = userDelegate.load(user.getId());													
							
							parent = new Category();
							parent.setParentGroup(group);
							parent.setCompany(company);
							parent.setName(value);
							parent.setDescription(value + " description");
							parent.setCreatedBy(persistedUser);
							parent = categoryDelegate.find(categoryDelegate.insert(parent));
							lastUpdatedDelegate.saveLastUpdated(company);
						}
						
					}
					if(cell.getColumnIndex()==5){			//column CAT2 child of CAT1
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}
						
						if(!StringUtils.isBlank(value)){
							Category parentCategory = parent;//CAT 1
							parent = categoryDelegate.find(company,value,parentCategory,group);
							if(parent == null){	
								User persistedUser = userDelegate.load(user.getId());													
								
								parent = new Category();
								parent.setParentCategory(parentCategory);//CAT 1 parent category
								parent.setParentGroup(group);
								parent.setCompany(company);
								parent.setName(value);
								parent.setDescription(value + " description");
								parent.setCreatedBy(persistedUser);
								parent = categoryDelegate.find(categoryDelegate.insert(parent));
								lastUpdatedDelegate.saveLastUpdated(company);
								
							}
						}						
					}
					if(cell.getColumnIndex()==6){			//column CAT3 child of CAT2
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}						
						if(!StringUtils.isBlank(value)){
							Category parentCategory = parent;//CAT 2
							parent = categoryDelegate.find(company,value,parentCategory,group);
							if(parent == null){	
								User persistedUser = userDelegate.load(user.getId());													
								
								parent = new Category();
								parent.setParentCategory(parentCategory);//CAT 2 parent category
								parent.setParentGroup(group);
								parent.setCompany(company);
								parent.setName(value);
								parent.setDescription(value + " description");
								parent.setCreatedBy(persistedUser);
								parent = categoryDelegate.find(categoryDelegate.insert(parent));
								lastUpdatedDelegate.saveLastUpdated(company);							
							}
						}	
					}
					if(cell.getColumnIndex()==7){			//column ITEM NAME
						
						try{
							name= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								name = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								name="";
							}
						}
						
						
					}
					if(cell.getColumnIndex()==8){			//Item Name(For Quotation)
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}
						
						otherFieldMap.put("Item Name(For Quotation)", value);
					}
					if(cell.getColumnIndex()==9){			//des 1
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}
						
						otherFieldMap.put("DES 1", value);
					}
					if(cell.getColumnIndex()==10){			//des 2
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}
						
						otherFieldMap.put("DES 2", value);
					}
					if(cell.getColumnIndex()==11){			//des 3
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}
						
						otherFieldMap.put("DES 3", value);
					}
					if(cell.getColumnIndex()==12){			//price
						String value = "";
						try{
							value= cell.getStringCellValue();
						}catch(NumberFormatException e){
							try{
								value = cell.getNumericCellValue() + "";
							}catch(Exception ex){
								value="";
							}
						}
						if(!StringUtils.isBlank(value)){
							try {
								price = Double.parseDouble(value);
								
							} catch (Exception e) {
								price = Double.parseDouble("0");
							}
							
						}
					}
				}
				if(isAboveThirdRow){					
					
					item = allItemMap.get(artNoKey);
					if(item!=null){
						item.setName(name);
						item.setPrice(price);
						item.setBrand(brand);
						item.setCompany(company);						
						item.setUpdatedOn(new Date());
						item.setParentGroup(group);
						item.setParent(parent);
						item.setSearchTags(item.getName()+" "+item.getBrand().getName());
						try{
							otherTags = request.getParameter("item.otherTags");
						}catch(Exception e1){}
						item.setOtherTags(otherTags);
						categoryItemDelegate.update(item);						
					}else{						
						item = new CategoryItem();
						item.setName(name);
						item.setPrice(price);
						item.setBrand(brand);
						item.setCompany(company);
						item.setCreatedOn(new Date());
						item.setUpdatedOn(new Date());
						item.setParentGroup(group);
						item.setParent(parent);
						item.setSearchTags(item.getName()+" "+item.getBrand().getName());
						try{
							otherTags = request.getParameter("item.otherTags");
						}catch(Exception e1){}
						item.setOtherTags(otherTags);
						System.out.println("####### Category Item Search Tags  4  " + item.getSearchTags());
						item = categoryItemDelegate.find(categoryItemDelegate.insert(item));
					}
					
					
					
					
					if(item!=null){
						logger.info(item.getName() + " successfully added.");
						if(item.getId() != null && company.getCompanySettings().getHasOtherFields()){
							List<OtherField> otherFields = item.getParent().getParentGroup().getOtherFields();
							if(otherFields != null)
							{			
								if(otherFields.size() > 0)
								{
									for(int i = 0; i < otherFields.size(); i++)
									{
										CategoryItemOtherField otherField = categoryItemOtherFieldDelegate.findByCategoryItemOtherField(company, item, otherFields.get(i));
										boolean isNewOption = true;		
										
										//if not null other field is for update
										if(otherField != null)
										{
											String content = otherFieldMap.get(otherFields.get(i).getName());
											
											if(content != null)
											{
												//inserting new options in other fields
												if(otherFields.get(i).getOtherFieldValueList() != null
														&& otherFields.get(i).getOtherFieldValueList().size()>0){ 
													
													for(OtherFieldValue value : otherFields.get(i).getOtherFieldValueList()) {
														if(content.equals(value)){
															isNewOption = false;
															break;
														}
													}
													if(isNewOption){
														OtherFieldValue newFieldValue = new OtherFieldValue();
														newFieldValue.setValue(content);
														newFieldValue.setOtherField(otherFields.get(i));
														
														otherFieldValueDelegate.insert(newFieldValue);
													}
													
												}
												
												otherField.setContent(content);
												categoryItemOtherFieldDelegate.update(otherField);
											}
										}
										else
										{
											String content = otherFieldMap.get(otherFields.get(i).getName());
											if(StringUtils.isNotEmpty(content))
											{
												//inserting new options in other fields
												if(otherFields.get(i).getOtherFieldValueList() != null
														&& otherFields.get(i).getOtherFieldValueList().size()>0){
													
													for(OtherFieldValue value : otherFields.get(i).getOtherFieldValueList()) {
														if(content.equals(value)){
															isNewOption = false;
															break;
														}
													}
													if(isNewOption){
														OtherFieldValue newFieldValue = new OtherFieldValue();
														newFieldValue.setValue(content);
														newFieldValue.setOtherField(otherFields.get(i));
														
														otherFieldValueDelegate.insert(newFieldValue);
													}
													
												}
												
												CategoryItemOtherField newOtherField = new CategoryItemOtherField();
												newOtherField.setContent(content);
												newOtherField.setCategoryItem(item);
												newOtherField.setOtherField(otherFields.get(i));
												newOtherField.setCompany(company);
												newOtherField.setIsValid(Boolean.TRUE);
												
												categoryItemOtherFieldDelegate.insert(newOtherField);
											}
										}
									}
								}
							}
							
						}
							
						 
						log.setEntityType(EntityLogEnum.CATEGORY_ITEM);
						log.setEntityID(item.getId());
						log.setCompany(company);
						log.setUpdatedByUser(user);
						//System.out.println("-------2" + log.getRemarks());
						logDelegate.insert(log);
						lastUpdatedDelegate.saveLastUpdated(company);
					}
					
				}
			}
		}
	}

	private void hpdsItemUpload(HSSFWorkbook wb)
	{
		Log log = new Log();
		// load all item and put in map
		
		for(int index = 0; index < wb.getNumberOfSheets(); index++)
		{	// sheet iteration
			
			final HashMap<String, Integer> columnsMap = new HashMap<String, Integer>();
			
			final HSSFSheet sheet = wb.getSheetAt(index);
			
			final Iterator<Row> rowIterator = sheet.rowIterator();
			while(rowIterator.hasNext())
			{
				final HSSFRow row = (HSSFRow) rowIterator.next();
				if(row.getRowNum() < 1)
				{
					Integer rowIndex = Integer.valueOf(0);
					String column = getStringCellValue(row, rowIndex);
					columnsMap.put(column, rowIndex);
					logger.info(column + " col, row " + rowIndex);
					while(column != null)
					{
						rowIndex++;
						column = getStringCellValue(row, rowIndex);
						columnsMap.put(column, rowIndex);
						logger.info(column + " col,, row " + rowIndex);
					}
					
					final String[] skipColumns = new String[]
					{
						"ITEM NAME",
						"SKU",
						//"TEST",
						//"TEST CODE(S)",
					};
					
					for(String col : skipColumns)
					{
						columnsMap.remove(col);
					}
					
					for(Entry<String, Integer> e : columnsMap.entrySet())
					{
						logger.info(e.getKey() + " ----------------- " + e.getValue());
					}
					
					continue;
				}
				
				final String name = getStringCellValue(row, 0);
				final String sku = getStringCellValue(row, 1);
				if(name != null && sku != null)
				{
					logger.info("Searching for items with sku: " + sku + ", name: " + name);
					final ObjectList<CategoryItem> itemObjs = categoryItemDelegate.findAll(company, sku, name, null);
					final List<CategoryItem> items = itemObjs.getList();
					logger.info("Query size: " + itemObjs.getSize());
					
					final boolean isNew = itemObjs.getSize() == 0;
					if(isNew) //insert new
					{
						CategoryItem item = new CategoryItem();
						item.setName(name);
						item.setSku(sku);
						item.setParent(category);
						item.setParentGroup(group);
						item.setCompany(company);
						item = categoryItemDelegate.find(categoryItemDelegate.insert(item));
						items.add(item);
					}
					
					for(CategoryItem item : items)
					{
						if(item != null)
						{
							final Map<String, String> customParams = new HashMap<String, String>();
							
							customParams.put("item.sku", sku);
							customParams.put("item.name", name);
							// customParams.put("item_id", item.getId().toString());
							
							logger.info("Updating item id: " + item.getId());
							for(Entry<String, Integer> e : columnsMap.entrySet())
							{
								final String otherFieldName = e.getKey();
								String content = getStringCellValue(row, e.getValue());
								
								try
								{
									if(content != null)
									{
										content = new String(content.getBytes(), "UTF-8");
									}
								}
								catch(UnsupportedEncodingException e1)
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								logger.info("Updating other field content " + otherFieldName + "... content " + content);
								
								final OtherField of = otherFieldDelegate.find(otherFieldName, company);
								if(of != null)
								{
									if(content != null)
									{
										content = content.replaceAll("(\r\n|\n)", "<br />");
										
									}
									
									customParams.put("o" + of.getId(), content);	
									CategoryItemUtil.updateCategoryItemOtherFieldContent(of, item, content);
								}
							}
							
							try
							{
								remoteSave(sku, customParams);
							}
							catch(Exception e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							logger.info(item.getName() + " successfully saved.");
							
							log.setEntityType(EntityLogEnum.CATEGORY_ITEM);
							log.setEntityID(item.getId());
							log.setCompany(company);
							log.setUpdatedByUser(user);
							// System.out.println("-------2" + log.getRemarks());
							logDelegate.insert(log);
							lastUpdatedDelegate.saveLastUpdated(company);
						}
					}
				}
			}
		}
	}
	
	private String getStringCellValue(HSSFRow row, int index)
	{
		if(row != null)
		{
			final HSSFCell cell = row.getCell(index);
			if(cell != null)
			{
				try
				{
					return cell.getStringCellValue();
				}
				catch(Exception e)
				{
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					return cell.getStringCellValue();
				}
			}
		}
		return null;
	}
	
	
	private void wilconExcelUpload(HSSFWorkbook wb) {
		notAddedList = new ArrayList<String>();
		
		Log log = new Log();
		int itemsRead=0;
		
		final short GROUP_CATEGORY_COLUMN = 0; // Parent Category
		final short CATEGORY_COLUMN = 1; // Sub Category 1
		final short SUB_CATEGORY_COLUMN_1 = 2; // Sub Category 2
		final short SUB_CATEGORY_COLUMN_2 = 3; // Sub Category 3
		final short SUB_CATEGORY_COLUMN_3 = 4; // Sub Category 4
		final short SUB_CATEGORY_COLUMN_4 = 5; // Sub Category 5
		final short BRAND_COLUMN = 6; // Brand
		final short NAME_COLUMN = 7; // Item Name
		final short CODE_COLUMN = 8; // SKU
		final short PRICE_COLUMN = 9; // Price
		final short DESCRIPTION_COLUMN = 10; // Product Description
		final short IS_FEATURED = 11; // Featured Product?
		
		
		
		ObjectList<CategoryItem> allItem = categoryItemDelegate.findAllByGroup(company, group);
		HashMap<String, CategoryItem> allItemMap = new HashMap<String, CategoryItem>();
		for(CategoryItem item:allItem.getList()){
			String key = item.getName()+item.getSku();
			allItemMap.put(key, item);
		}
		
		for(int index=0; index<wb.getNumberOfSheets();index++){	//sheet iteration
			HSSFSheet sheet  = wb.getSheetAt(index);
			System.out.println(" SHEET INDEX " + index);
			for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); itemsRead++) {		//row iteration
				Row row = rowIterator.next();				
				HashMap<String, String> otherFieldMap = new HashMap<String, String>();
				
				System.out.println(" ROW INDEX " + row.getRowNum());
				
				String code = "";
				String name = "";
				String description = "";
				String brandName = "";
				String groupCategory = "";
				String category = "";
				String subCategory1 = "";
				String subCategory2 = "";
				String subCategory3 = "";
				String subCategory4 = "";
				double price = 0D;
				boolean isFeatured = false;
				
				Brand brand = new Brand();
				Category groupParent = new Category();
				Category parent = new Category();
				Category subParent1 = null;
				Category subParent2 = null;
				Category subParent3 = null;
				Category subParent4 = null;
				boolean firstRow = true;
				for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();) {	//cell iteration
					
					if(row.getRowNum()<1){
						// check if headers was correct
						
						
						//start in second row
						break;
					}		
					firstRow = false;
					
					Cell cell = cellIterator.next();
					
					System.out.println(" CELL INDEX " + cell.getColumnIndex());
					
					switch (cell.getColumnIndex()) {
						case CODE_COLUMN:
							try {
								switch(cell.getCellType()) {
									case Cell.CELL_TYPE_STRING:
										code = cell.getStringCellValue();
										break;
									case Cell.CELL_TYPE_NUMERIC:
										code = new Double(cell.getNumericCellValue()).toString();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case NAME_COLUMN:
							try {
								name = cell.getStringCellValue();
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case DESCRIPTION_COLUMN:
							try {
								description = cell.getStringCellValue();
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case BRAND_COLUMN:
							try {
								brandName = cell.getStringCellValue();
								brand = brandDelegate.find(company, group, brandName);
								if(brand == null && !StringUtils.isBlank(brandName)){								
									
									brand = new Brand();
									brand.setGroup(group);
									brand.setCompany(company);
									brand.setName(brandName);
									brand.setDescription(brandName);
									brand = brandDelegate.find(brandDelegate.insert(brand));
									lastUpdatedDelegate.saveLastUpdated(company);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case GROUP_CATEGORY_COLUMN:
							try {
								groupCategory = cell.getStringCellValue();
								groupParent = categoryDelegate.find(company, groupCategory, group);
								if(groupParent == null && !StringUtils.isBlank(groupCategory)){								
									User persistedUser = userDelegate.load(user.getId());													
									
									groupParent = new Category();
									groupParent.setParentGroup(group);
									groupParent.setCompany(company);
									groupParent.setName(groupCategory);
									groupParent.setDescription(groupCategory);
									groupParent.setCreatedBy(persistedUser);
									groupParent = categoryDelegate.find(categoryDelegate.insert(groupParent));
									lastUpdatedDelegate.saveLastUpdated(company);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case CATEGORY_COLUMN:
							try {
								category = cell.getStringCellValue();
								parent = categoryDelegate.find(company, category, groupParent, group);
								if(parent == null && !StringUtils.isBlank(category) && groupParent != null){								
									User persistedUser = userDelegate.load(user.getId());													
									
									parent = new Category();
									parent.setParentGroup(group);
									parent.setCompany(company);
									parent.setName(category);
									parent.setDescription(category);
									parent.setParentCategory(groupParent);
									parent.setCreatedBy(persistedUser);
									parent = categoryDelegate.find(categoryDelegate.insert(parent));
									lastUpdatedDelegate.saveLastUpdated(company);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case SUB_CATEGORY_COLUMN_1:
							try {
								subCategory1 = cell.getStringCellValue();
								subParent1 = categoryDelegate.find(company, subCategory1, parent, group);
								if(subParent1 == null && !StringUtils.isBlank(subCategory1) && parent != null){								
									User persistedUser = userDelegate.load(user.getId());													
									
									subParent1 = new Category();
									subParent1.setParentGroup(group);
									subParent1.setCompany(company);
									subParent1.setName(subCategory1);
									subParent1.setDescription(subCategory1);
									subParent1.setParentCategory(parent);
									subParent1.setCreatedBy(persistedUser);
									subParent1 = categoryDelegate.find(categoryDelegate.insert(subParent1));
									lastUpdatedDelegate.saveLastUpdated(company);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case SUB_CATEGORY_COLUMN_2:
							try {
								subCategory2 = cell.getStringCellValue();
								subParent2 = categoryDelegate.find(company, subCategory2, subParent1, group);
								if(subParent2 == null && !StringUtils.isBlank(subCategory2) && subParent1 != null){								
									User persistedUser = userDelegate.load(user.getId());													
									
									subParent2 = new Category();
									subParent2.setParentGroup(group);
									subParent2.setCompany(company);
									subParent2.setName(subCategory2);
									subParent2.setDescription(subCategory2);
									subParent2.setParentCategory(subParent1);
									subParent2.setCreatedBy(persistedUser);
									subParent2 = categoryDelegate.find(categoryDelegate.insert(subParent2));
									lastUpdatedDelegate.saveLastUpdated(company);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case SUB_CATEGORY_COLUMN_3:
							try {
								subCategory3 = cell.getStringCellValue();
								subParent3 = categoryDelegate.find(company, subCategory3, subParent2, group);
								if(subParent3 == null && !StringUtils.isBlank(subCategory3) && subParent2 != null){								
									User persistedUser = userDelegate.load(user.getId());													
									
									subParent3 = new Category();
									subParent3.setParentGroup(group);
									subParent3.setCompany(company);
									subParent3.setName(subCategory3);
									subParent3.setDescription(subCategory3);
									subParent3.setParentCategory(subParent2);
									subParent3.setCreatedBy(persistedUser);
									subParent3 = categoryDelegate.find(categoryDelegate.insert(subParent3));
									lastUpdatedDelegate.saveLastUpdated(company);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case SUB_CATEGORY_COLUMN_4:
							try {
								subCategory4 = cell.getStringCellValue();
								subParent4 = categoryDelegate.find(company, subCategory4, subParent3, group);
								if(subParent4 == null && !StringUtils.isBlank(subCategory4) && subParent3 != null){								
									User persistedUser = userDelegate.load(user.getId());													
									
									subParent4 = new Category();
									subParent4.setParentGroup(group);
									subParent4.setCompany(company);
									subParent4.setName(subCategory4);
									subParent4.setDescription(subCategory4);
									subParent4.setParentCategory(subParent3);
									subParent4.setCreatedBy(persistedUser);
									subParent4 = categoryDelegate.find(categoryDelegate.insert(subParent4));
									lastUpdatedDelegate.saveLastUpdated(company);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case PRICE_COLUMN:
							try{
								if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
									price = cell.getNumericCellValue();
								}else{
									price = Double.parseDouble(new String(cell.getStringCellValue()).replace(",", ""));
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						break;
						
						case IS_FEATURED:
							try{
								isFeatured = StringUtils.isNotBlank(cell.getStringCellValue());
							}catch(Exception e){
								e.printStackTrace();
							}
						break;
					}
				}
				
				if((groupParent != null || parent!=null || subParent1 != null) 
						&& !StringUtils.isBlank(code) 
						&& !StringUtils.isBlank(name) 
						&& StringUtils.isNotBlank(brandName)
						&& StringUtils.isNotBlank(description)){
					
					String key = name+code;
					item = allItemMap.get(key);
					if(item==null){
						item = new CategoryItem();
						item.setName(name);
						item.setSku(code);
						item.setBrand(brand);
						item.setCompany(company);
						item.setCreatedOn(new Date());
						item.setUpdatedOn(new Date());
						item.setParentGroup(group);
						
						//item.setParent((subParent1 != null ? subParent1 : parent != null ? parent : groupParent));
						if(subParent4 != null) item.setParent(subParent4);
						else if(subParent3 != null) item.setParent(subParent3);
						else if(subParent2 != null) item.setParent(subParent2);
						else if(subParent1 != null) item.setParent(subParent1);
						else if(parent != null) item.setParent(parent);
						else if(groupParent != null) item.setParent(groupParent);
							
							
						item.setSearchTags(item.getName()+" "+code);
						item.setPrice(price);
						item.setFeatured(isFeatured);
						System.out.println("###### SearchTags :5: "+ searchTags);
						try{
							otherTags = request.getParameter("item.otherTags");
						}catch(Exception e1){}
						item.setOtherTags(otherTags);
						item.setDescription(description);
						System.out.println("####### Category Item Search Tags  2  " + item.getSearchTags());
						item = categoryItemDelegate.find(categoryItemDelegate.insert(item));
						allItemMap.put(key,item);
						logger.info(item.getName() + " successfully added.");
					} else {
						item.setName(name);	
						item.setSku(code);
						item.setBrand(brand);
						item.setCompany(company);
						item.setCreatedOn(new Date());
						item.setUpdatedOn(new Date());
						item.setPrice(price);
						item.setFeatured(isFeatured);
						item.setParentGroup(group);
						
						//item.setParent((subParent1 != null ? subParent1 : parent != null ? parent : groupParent));
						if(subParent4 != null) item.setParent(subParent4);
						else if(subParent3 != null) item.setParent(subParent3);
						else if(subParent2 != null) item.setParent(subParent2);
						else if(subParent1 != null) item.setParent(subParent1);
						else if(parent != null) item.setParent(parent);
						else if(groupParent != null) item.setParent(groupParent);
						
						item.setSearchTags(item.getName()+" "+code);
						try{
							otherTags = request.getParameter("item.otherTags");
						}catch(Exception e1){}
						item.setOtherTags(otherTags);
						item.setDescription(description);
						categoryItemDelegate.update(item);
						logger.info(item.getName() + " successfully updated.");
					}
					
					log.setEntityType(EntityLogEnum.CATEGORY_ITEM);
					log.setEntityID(item.getId());
					log.setCompany(company);
					log.setUpdatedByUser(user);
					// System.out.println("-------2" + log.getRemarks());
					logDelegate.insert(log);
					lastUpdatedDelegate.saveLastUpdated(company);
				}else if(!firstRow){
					notAddedList.add("SHEET " + (index+1) + ": Item at row " + (row.getRowNum() + 1) + " was not added.");
				}
				
			}
		}
		
		request.getSession().setAttribute(NOT_ADDED_LIST_SESSION_PARAM, notAddedList);
		
	}
	
	public String saveUploadItemImages() throws IOException {
		logger.info("Executing --- addUploadItemImages()");
		group = groupDelegate.find(Long.parseLong(request.getParameter("groupId")));
		
		if(!commonParamsValid()) {
			setMessage("You dont have permission to upload items.");
			return Action.ERROR;
		}
		
		if(files[0] == null)
		{
			addActionError("Please include a zipfile in the textbox.");
			return SUCCESS;
		}
		
		File zip = new File(files[0].getAbsolutePath());
		String unZippedFileFolder = servletContext.getRealPath("uploadimages_"+company.getName());
		
		System.out.println("Zip absolute path Source: " + zip.getAbsolutePath());
		FileUtil.unZip(zip.getAbsolutePath(), unZippedFileFolder);
		
		File dir = new File(unZippedFileFolder);
		
		if(dir.isDirectory())
			for(File file : dir.listFiles()) {
				File[] files = new File[1];
				files[0] = file;
				String[] fileNames = new String[1];
				fileNames[0] = file.getName();
				String[] contentTypes = new String[1];
				contentTypes[0] = "";
				
				String fileName = file.getName();
				String name = fileName.replace(".jpg", "").replace(".JPG", "");
				
				List<CategoryItem> items = null;
				
				if(CompanyConstants.WILCON.equals(company.getName())){
					// SKU as image file name
					items = categoryItemDelegate.findAll(company, group, name).getList();
				
				}else{
					// name as image file name
					items = categoryItemDelegate.findAllByName(company, name);
				}
				
				for(CategoryItem categoryItem : items) {
					setItem(categoryItem);
					saveImages(files, fileNames, contentTypes);
					
				}
				file.delete();
			}
		dir.delete();
		
		setMessage("Item images successfully added.");
		return SUCCESS;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args){
		try {
			System.out.println("• 0126".getBytes("UTF-8").toString());
			
			System.out.println(URLEncoder.encode("• 0126"));
			System.out.println(URLDecoder.decode("%E2%80%A2+0126", ENCODING_DEFAULT));
			System.out.println(URLDecoder.decode("%95+0126", ENCODING_DEFAULT));

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Boolean remoteSave(String sku, Map<String, String> customParams)
	{
		logger.info("Entering remoteSave...");
		Boolean success = Boolean.TRUE;		
//		
//		if(request != null && StringUtils.isBlank(request.getParameter(SECRET_KEY_PARAM)))
//		{
//			final String propertiesFileName = "hpds-remote.properties";
//			final Properties properties = PropertiesUtil.getProperties(propertiesFileName);
//			
//			if(properties != null)
//			{
//						
//				final String itemSKU = sku == null ? request.getParameter("item.sku") : sku;
//				logger.info("Item SKU: " + itemSKU);
//				if(StringUtils.isNotBlank(itemSKU))
//				{
//					
//					//Currently this is for HPDS only - add company setting for different company implementation															
//					try {
//						final String serviceURL = properties.getProperty("service.url");
//						final PostMethod postMethod = new PostMethod(serviceURL);					
//						final String now = DateTimeFormat.forPattern("MMddYYYYhhmmss").print(new DateTime());
//												
//						postMethod.addParameter(SECRET_KEY_PARAM, HashUtil.sign(SECRET_KEY, now + itemSKU));
//						postMethod.addParameter(DATE_PARAM, now);
//						postMethod.addParameter("companyId", company.getId().toString());	
////						postMethod.setRequestHeader("Content-Type", "charset=utf-8");
//
//						
//						Enumeration parameterNames = request.getParameterNames();
//						while(parameterNames.hasMoreElements())
//						{
//							final String parameterName = parameterNames.nextElement().toString();
//							String parameterValue = request.getParameter(parameterName);
//							
//							if(parameterName.equals("item.sku") || parameterName.equals("o44"))
//								parameterValue = URLEncoder.encode(request.getParameter(parameterName), ENCODING_DEFAULT);
//								
//							postMethod.addParameter(parameterName, parameterValue);							
//
//							logger.info(parameterName + ": "+parameterValue);
//						}	
//						
//						if(customParams != null)
//						{
//							for(Entry<String, String> e : customParams.entrySet())
//							{
//								String content = e.getValue();
//								if(content != null)
//								{
//									content = java.net.URLEncoder.encode(content, ENCODING_DEFAULT);
//								}
//								postMethod.addParameter(e.getKey(), content);
//							}
//						}
//						
//						logger.info("Remote saving to: " + serviceURL);
//						new HttpClient().executeMethod(postMethod);		
//						 
//						for(NameValuePair nvp : postMethod.getParameters())
//						{
//							logger.info("Parameter name: " + nvp.getName() + ", value: " + nvp.getValue());
//						}
//						
//						if(!SUCCESS.equalsIgnoreCase(postMethod.getResponseBodyAsString()))
//						{
//							success = Boolean.FALSE;
//						}
//						
//						logger.info("Response: " + ReflectionUtil.printAllGetterValues(postMethod));
//					} catch(Exception e) {
//						logger.error(e.getMessage());
//						success = Boolean.FALSE;
//					}
//				}
//				else
//				{
//					success = Boolean.FALSE;
//				}
//			}
//			else
//			{
//				logger.info("Properties file \"" + propertiesFileName + "\" not found.");
//			}
//		}
		
		logger.info("Remote save success? " + success);
		return success;
	}
	
	public void remoteSaveService() throws Exception
	{	
		logger.info("remote save service method...");
		String status = "ERROR";
		
		if(request != null)
		{
			final String now = request.getParameter(DATE_PARAM);
			String itemSKU = request.getParameter("item.sku");
			String itemTestCode = request.getParameter("o44");			

			
			if(StringUtils.isNotBlank(now) && StringUtils.isNotBlank(itemSKU))
			{
				logger.info("itemSKU: " + itemSKU);
				try
				{
					itemTestCode = URLDecoder.decode(itemTestCode, ENCODING_DEFAULT);
					itemSKU = java.net.URLDecoder.decode(itemSKU, ENCODING_DEFAULT);
					logger.info("Decoded itemSKU: " + itemSKU + ", Decoded itemTestCode: " + itemTestCode);
					item.setSku(itemSKU);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				logger.info("request.getParameter(SECRET_KEY_PARAM) " + request.getParameter(SECRET_KEY_PARAM));
				
				logger.info("HashUtil.sign(SECRET_KEY, now + itemSKU) " + HashUtil.sign(SECRET_KEY, now + itemSKU));
				
				//validate access - process only when valid secret key
				if(StringUtils.trimToEmpty(request.getParameter(SECRET_KEY_PARAM)).equalsIgnoreCase(HashUtil.sign(SECRET_KEY, now + itemSKU)))
				{					
					try {
						
 						if(request.getParameter("delete") != null)
						{
							final Boolean delete = Boolean.valueOf(request.getParameter("delete"));
							if(delete)
							{
								final boolean deleted = categoryItemDelegate.delete(item);
								if(deleted)
								{
									status = "SUCCESS";
								}
							}
						}
						else
						{
							edit();
							status = save();							
						}
					} catch(Exception e) {						
						e.printStackTrace();
						logger.error(e.getMessage());
					}
				}
				else
				{					
					logger.error("Invalid secret key... \"" + request.getParameter(SECRET_KEY_PARAM) + "\"");
				}
			}
		}									
		
		response.getWriter().print(status);
	}	
	
	/*
	 * Kuysen Enterprise
	 * Fittings
	 * getter
	 * Daniel B. Sario
	 */
	public List<CategoryItem> getFittingsItem() {
		return categoryDelegate.find(6118L).getItems();
	}

	/*
	 * Kuysen Enterprise
	 * Fittings
	 * setter
	 * Daniel B. Sario
	 */
	public void setFittings(String[] fittings) {
		this.fittings = fittings;
	}

	public Integer getPageNumber()
	{
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber)
	{
		this.pageNumber = pageNumber;
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
	
	public File getFileimage1() {
		return fileimage1;
	}

	public void setFileimage1(File fileimage1) {
		this.fileimage1 = fileimage1;
	}
	
	/** K: group name
	 *	V: list of category items 
	 */
	public Map<String, List<CategoryItem>> getGroupItemsMap()
	{
		final Map<String, List<CategoryItem>> map = new HashMap<String, List<CategoryItem>>();
		final List<Group> groups = groupDelegate.findAll(company).getList();
		for(Group g : groups)
		{
			final List<CategoryItem> items = categoryItemDelegate.findAllByGroup(company, g).getList();
			if(items.size() > 0){
				Collections.sort(items,new Comparator<CategoryItem>(){
					@Override
					public int compare(final CategoryItem object1, final CategoryItem object2){
						return object1.getName().compareTo(object2.getName());
					}
				});
			}
			map.put(g.getName(), items);
		}
		return map;
	}
	
	public Map<String,Group> getGroupMap()
	{
		// get the groups
		final List<Group> groupList = groupDelegate.findAll(company).getList();
		final Map<String, Group> groupMap = new HashMap<String, Group>();
		final Map<Long, Group> groupIdMap = new HashMap<Long, Group>();
		for(final Group group : groupList)
		{
			final String jspName = group.getName().toLowerCase();
			group.setLanguage(language);
			final Menu menu = new Menu(group, httpServer + "/" + jspName + ".do");
			//menuList.put(jspName, menu);
			
			groupMap.put(group.getName(), group);
			groupMap.put(jspName, group);
			groupIdMap.put(group.getId(), group);
		}
		request.setAttribute("groupList", groupList);
		request.setAttribute("groupMap", groupMap);
		request.setAttribute("groupIdMap", groupIdMap);
		return groupMap;
	}
	
	 /* Get Wendys Categories */
		public List<Category> getPromosCategory()
		{
			final Group promosCategory = groupDelegate.find(company, "PROMOS");
			if(promosCategory != null)
			{
				return categoryDelegate.findAll(company,promosCategory).getList();
			}
			return null;
		}
		
		/*--------  Get Wendys Branch(es) ---------------*/
		 
		public List<CategoryItem> getStores()
		{
			final Group stores = groupDelegate.find(company, "stores");
			if(stores != null)
			{
				return categoryItemDelegate.findAllEnabledWithPaging(company, stores, -1, -1, null).getList();
			}
			return null;
		}
		
		/* Get  Categories Map */
		public Map<String, List<Category>> getListCategoryMap()
		{
			Map<String, List<Category>> categoryMap = new HashMap<String, List<Category>>();
			final List<Group> listCategories = groupDelegate.findAll(company).getList();
			for(Group g : listCategories){
				categoryMap.put(g.getName(),categoryDelegate.findAll(company, g).getList());
			}
			return categoryMap;
		}
		
		public Map<String, List<CategoryItem>> getCategoryByNameMap() {
			Map<String, List<CategoryItem>> category_itemMap = new HashMap<String, List<CategoryItem>>();
			final List<Category> listCategories = categoryDelegate.findAll(company, groupDelegate.find(company, "combos")).getList();
			for(Category category : listCategories) {
				category_itemMap.put(category.getName(), categoryItemDelegate.findAll(company, category, Boolean.TRUE).getList());
			}
			return category_itemMap;
		}
		/*
		public Map<String, CategoryItem> getCategoryItemByStringIdMap() {
			System.out.println("#### test 1");
			Map<String, CategoryItem> categoryItemByStringMap = new HashMap<String, CategoryItem>();
			List<CategoryItem> listCategoryItem = new ArrayList<CategoryItem>();
			listCategoryItem = categoryItemDelegate.findAllByGroup(company, group, false).getList();
			System.out.println("#### test 2");
			if(listCategoryItem.size() > 0){
				for(CategoryItem ci : listCategoryItem){
					if(ci != null){
						categoryItemByStringMap.put(String.valueOf(ci.getId()), ci);
					}
				}
			}
			logger.info(categoryItemByStringMap);
			System.out.println("#### test");
			return categoryItemByStringMap;
		}
		*/
		/* Get Category Item Map  */
		public Map<String, List<CategoryItem>> getListCategoryItemMap(){
			Map<String, List<CategoryItem>> categoryItemMap = new HashMap<String, List<CategoryItem>>();
			Order[] orders = { Order.asc("name") };
			final List<Group> listCategories = groupDelegate.findAll(company).getList();
			for(Group g: listCategories){
				if(company.getId() == CompanyConstants.GURKKA_TEST){
					categoryItemMap.put(g.getName(), categoryItemDelegate.findAllEnabledWithPaging(company, g, -1, -1, orders).getList());
				}
				else{
					categoryItemMap.put(g.getName(), categoryItemDelegate.findAllEnabledWithPaging(company, g, -1, -1, null).getList());
				}
			}
			return categoryItemMap;
		}
		
		public Map<String, List<CategoryItem>> getListAllCategoryItemMap(){
			Map<String, List<CategoryItem>> categoryItemMap = new HashMap<String, List<CategoryItem>>();
			Order[] orders = { Order.asc("name") };
			final List<Group> listCategories = groupDelegate.findAll(company).getList();
			for(Group g: listCategories){
				if(company.getId() == CompanyConstants.GURKKA_TEST){
					categoryItemMap.put(g.getName(), categoryItemDelegate.findAllWithPaging(company, g, -1, -1, orders).getList());
				}
				else{
					categoryItemMap.put(g.getName(), categoryItemDelegate.findAllWithPaging(company, g, -1, -1, null).getList());
				}
			}
			return categoryItemMap;
		}
		
		public List<Member> getMemberList(){
			return memberDelegate.findAll(company).getList();
		}
		
		public List<CategoryItem> getGurkkaPromoBasketByCurrentItem() {
			List<CategoryItem> res = new ArrayList<CategoryItem>();
			List<OtherField> listOf = new ArrayList<OtherField>();
			Long tempId = 0L;
			CategoryItem cat = new CategoryItem();
			OtherField of = new OtherField();
			if(item != null && company != null){
				if(company.getId().intValue() == CompanyConstants.GURKKA_TEST){}
			}
			return res;
		}

		public List<String> getNotAddedList() {
			return notAddedList;
		}
		
		public List<Category> getAgianKDCategories(){
			Group group = groupDelegate.find(611L);
			List<Category> gal = categoryDelegate.findAll(company, group).getList();
			return gal;
			
		}

		public List<Category> getCategories() {
			return categories;
		}

		public void setCategories(List<Category> categories) {
			this.categories = categories;
		}

		
		
		
		
		
		public int getFormType() {
			return formType;
		}

		public void setFormType(int formType) {
			this.formType = formType;
		}

	
		
}