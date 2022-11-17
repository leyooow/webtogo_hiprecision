package com.ivant.cms.action;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.hibernate.criterion.Order;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompanySettingsDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
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
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.PageFile;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.ivant.utils.PasswordEncryptor;
import com.opensymphony.xwork2.Action;

public class UploadMemberFileItems extends AbstractBaseAction {
	private static final long serialVersionUID = -244217747366834302L;
	private static final Logger logger = Logger.getLogger(UploadMemberFileItems.class);
	private static SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private static final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private static final PageImageDelegate pageImageDelegate = PageImageDelegate.getInstance();
	private static final MultiPageFileDelegate multiPageFileDelegate = MultiPageFileDelegate.getInstance();	
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private static final PageFileDelegate pageFileDelegate = PageFileDelegate.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final LogDelegate logDelegate = LogDelegate.getInstance();
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private static final CompanySettingsDelegate companySettingsDelegate = CompanySettingsDelegate.getInstance();
	private LanguageDelegate languageDelegate = LanguageDelegate.getInstance();
	private SinglePageLanguageDelegate singlePageLanguageDelegate = SinglePageLanguageDelegate.getInstance();
	private MultiPageLanguageDelegate multiPageLanguageDelegate = MultiPageLanguageDelegate.getInstance();
	private ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private static final MemberFileItemDelegate memberFileItemDelegate = MemberFileItemDelegate.getInstance();
	private static final MemberFileItemDelegate receiptImageDelegate = MemberFileItemDelegate.getInstance();
	private static final MemberFileDelegate memberFileDelegate = MemberFileDelegate.getInstance();
	private FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private List<MemberFileItems> receiptList;
	private List<MemberFileItems> memberFileItemsList;
	private ItemComment comment;
	private User user;
	private Company company;
	private CompanySettings companySettings;
	private HttpServletRequest request;
	private ServletContext servletContext;
	private static final List<String> ALLOWED_PAGES;
	
	static {
		ALLOWED_PAGES = new ArrayList<String>();
		ALLOWED_PAGES.add("sitemap");
		ALLOWED_PAGES.add("printerfriendly");
		ALLOWED_PAGES.add("brands");
		ALLOWED_PAGES.add("calendarevents");
		ALLOWED_PAGES.add("cart");
	}	
	private Member member2;
				
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
	
	//listing of all Items
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
	private static final String ATTACHMENT_FOLDER = "message_attachments";
	
	private String freight;

	private List<Log> logList;
	
	public List<Log> getLogList(){
		return logList;
	}
	
	public List<MemberFileItems> getReceiptList() {
		return receiptList;
	}
	
	public void prepare() throws Exception {
		initHttpServerUrl();
		companySettings = companySettingsDelegate.find(company);
		loadFeaturedPages(companySettings.getMaxFeaturedPages());
		loadFeaturedSinglePages(companySettings.getMaxFeaturedSinglePages());
		loadMenu();
	}
	
	@SuppressWarnings("unchecked")
	private TreeMap  tm = new TreeMap();

	@Override
	public String execute() throws Exception {
		return Action.NONE;
	}
	
	/**
	 * Populates new httpServer URL to be redirected to.
	 */
	protected void initHttpServerUrl() {
		servletContextName = servletContext.getServletContextName();
		isLocal = (request.getRequestURI().startsWith("/" + servletContextName)) ? true
				: false;
		httpServer = (isLocal) ? ("http://" + request.getServerName() + ":"
				+ request.getServerPort() + "/" + servletContextName
				+ "/companies/" + company.getDomainName())
				: ("http://" + request.getServerName());
	}
	
	private void loadFeaturedPages(int max) {
		Map<String, Object> featuredPages = new HashMap<String, Object>();
		List<MultiPage> featuredMultiPage = multiPageDelegate.findAllFeatured(company).getList();
		
		for(MultiPage mp : featuredMultiPage) {
			if(!mp.getHidden()) {
				featuredPages.put(mp.getName(), mp);
			}
		}
		
		//System.out.println("size:::: " + featuredMultiPage.size());
		request.setAttribute("featuredPages", featuredPages);
	}
	
	private void loadFeaturedSinglePages(int max) {
		Map<String, Object> featuredSinglePages = new HashMap<String, Object>();
		List<SinglePage> featuredSinglePage = singlePageDelegate.findAllFeatured(company).getList();
		
		for(SinglePage mp : featuredSinglePage) {
			if(!mp.getHidden()) {
				featuredSinglePages.put(mp.getName(), mp);
			}
		}
		
		request.setAttribute("featuredSinglePages", featuredSinglePages);
	}
	
	private void loadMenu() {
		try {
			Map<String, Menu> menuList = new HashMap<String, Menu>();

			// get the single pages
			List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
			for(SinglePage singlePage : singlePageList) {
				String jspName = singlePage.getJsp(); 
				Menu menu = new Menu(singlePage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(singlePage.getJsp(), menu);
			}
			
			// get the multi pages
			List<MultiPage> multiPageList = multiPageDelegate.findAll(company).getList();
			request.setAttribute("multiPageList", multiPageList);
			for(MultiPage multiPage : multiPageList) {
				String jspName = multiPage.getJsp();
				Menu menu = new Menu(multiPage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(multiPage.getJsp(), menu);
			}
			
			// get the form Pages
			List<FormPage> formPageList = formPageDelegate.findAll(company).getList();
			for(FormPage formPage : formPageList) {
				String jspName = formPage.getJsp();
				Menu menu = new Menu(formPage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(formPage.getJsp(), menu);
			}
			
			// get the groups
			List<Group> groupList = groupDelegate.findAll(company).getList();
			for(Group group : groupList) {
				String jspName = group.getName().toLowerCase();
				Menu menu = new Menu(group.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(jspName, menu);
			}
			
			// get the link to the allowed pages
			for(String s: ALLOWED_PAGES) {
				String jspName = s.toLowerCase();
				Menu menu = new Menu(jspName, httpServer + "/" + jspName + ".do"); 
				menuList.put(jspName, menu);
			}
			
			request.setAttribute("menu", menuList); 
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action in FrontMenuInterceptor. " + e);
		}
	}	
	
	public String listAllCategoriesAndItems(){
	if(company.getName().equalsIgnoreCase("apc")||company.getName().equalsIgnoreCase("westerndigital"))
		if (group == null){
			Order[] orders = {Order.asc("parentCategory"),Order.asc("sortOrder"),Order.asc("name")};
			//for local
			String groupId="";
			if(company.getName().equalsIgnoreCase("apc"))
				groupId="211";
			if(company.getName().equalsIgnoreCase("westerndigital"))
				groupId="214";
			group = groupDelegate.find(Long.parseLong(groupId));//request.getParameter("group_id")==205
			//for live
			//group = groupDelegate.find(Long.parseLong("212"));//request.getParameter("group_id")==205
			categories = categoryDelegate.findAllWithPaging(user.getCompany(), null, group, user.getItemsPerPage(), page, orders, true).getList();
			Order[] ordersItem = {Order.asc("parent"),Order.asc("sortOrder"),Order.asc("name")};
			categoriesList = categoryDelegate.findAll(user.getCompany(), group).getList();
			sortedCategoriesList = sortList(categoriesList);//sort categoriesList
			items = categoryItemDelegate.findAllWithPaging(user.getCompany(), group, category, itemsPerPage, page, true, ordersItem).getList();
			request.setAttribute("allItems",items);
			request.setAttribute("allCategories",categories);
		}
	return Action.SUCCESS;
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
				File.separator + "ImagesReceipts");
			
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

	public String uploadImage() { 
		if(company == null) {
			return Action.ERROR;
		}
		///if( !company.equals(singlePage.getCompany()) )  {	
		//	return Action.ERROR;
		
		//}
		if(files==null){
			request.setAttribute("errorMessage", "Invalid");
			return Action.ERROR;
		}
		if(!(files.length==1)){
			request.setAttribute("errorMessage", "Unexpected error occur, Please try to Upload your files again");
			return Action.ERROR;
		}
		//System.out.println("THE FILES ARE "+files);
		
		saveImages(files, filenames, contentTypes);
		
		if(request.getParameter("member_id") != null)
			member2 = memberDelegate.find(Long.parseLong(request.getParameter("member_id")));
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public String uploadFile() { 
		if(company == null) {
			return Action.ERROR;
		}

		if(files==null){
			request.setAttribute("errorMessage", "Invalid");
			return Action.ERROR;
		}
		
		//System.out.println("THE FILES ARE "+files);
		
		saveFiles(files, filenames, contentTypes);
		lastUpdatedDelegate.saveLastUpdated(company);
		
		sendEmail();
		
		return Action.SUCCESS;
	}
	
	public String deleteDeposit() {
		if(company.getName().equalsIgnoreCase("giftcard"))
		{
			String invoiceNumber = request.getParameter("invoice_number");
			MemberFileItems memberFileItems = memberFileItemDelegate.findByInvoice(company, invoiceNumber);
			memberFileItems.setOriginal(null);
			memberFileItems.setFilename(null);
			memberFileItemDelegate.update(memberFileItems);
		}
		
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private boolean sendEmail() {
		boolean emailSent = false;
		StringBuffer content = new StringBuffer();

		try {
			String successUrl = request.getParameter("successUrl");
			String errorUrl = request.getParameter("errorUrl");

			String subject = request.getParameter("subject");
			String to = request.getParameter("to");
			String from = request.getParameter("from");
			String title = request.getParameter("title");		

			List<String> ignored = new ArrayList<String>();
			ignored.add("subject");
			ignored.add("to");
			ignored.add("from");
			ignored.add("title");
			ignored.add("submit");
			ignored.add("successurl");
			ignored.add("errorurl");
			ignored.add("se_formname");
			ignored.add("se_sender");
			ignored.add("se_email");
			ignored.add("se_phone");
			ignored.add("maxfilesize");

			Iterator<Map.Entry<String, String[]>> iterator = request
					.getParameterMap().entrySet().iterator();
			
			while (iterator.hasNext()) {
				Map.Entry<String, String[]> entry = iterator.next();
				if (entry.getKey().contains("|")){
					tm.put(entry.getKey(), entry.getValue());
				}
				if (request.getParameter("se_hasDelimiter")==null || !(request.getParameter("se_hasDelimiter").equalsIgnoreCase("true"))) {
					if (entry != null) {
						if (!ignored.contains(entry.getKey().toLowerCase())) {
							content.append(decodeKey(entry.getKey())).append(" : ").append(request.getParameter(entry.getKey()));
							content.append("<br/><br/>");
						}
					} else {
						break;
					}
				}
			}
			
			if ((request.getParameter("se_hasDelimiter")!= null && request.getParameter("se_hasDelimiter").equalsIgnoreCase("true"))) {
				Set  set = tm.entrySet();
				Iterator i = set.iterator();
				while (i.hasNext()){
					Map.Entry me = (Map.Entry)i.next();
					
					String requestStr=me.getKey().toString();
					String fieldName=decodeKey(me.getKey().toString().substring(me.getKey().toString().indexOf("|")+1));
					
					if(fieldName.equalsIgnoreCase("name") || me.getKey().toString().equalsIgnoreCase("name"))
					{
						if(!request.getParameter(requestStr).equals(""))
						{
							subject = subject + " - " + request.getParameter(requestStr);
						}
					}
					
					content.append(decodeKey(me.getKey().toString().substring(me.getKey().toString().indexOf("|")+1))).append(" : ").append(request.getParameter(me.getKey().toString()));
					
					MultiPartRequestWrapper s = (MultiPartRequestWrapper) request;
					File[] sfile = s.getFiles("1h|file_upload");
					
					if(sfile == null)
					{
						content.append("<br/><br/>");
					}
					else
					{
						content.append("\r\n\r\n");
					}
					
					//System.out.print(me.getKey() + ": ");
					//System.out.println(me.getValue());
				}
			}			
			
			EmailUtil.connect("smtp.gmail.com", 25);
			
			MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
			File[] file = r.getFiles("upload");
			String[] filename = r.getFileNames("upload");
			
			if((file != null && file.length > 0) && (filename != null && filename.length > 0)){
				
				String path =  getRealPath() + ATTACHMENT_FOLDER + File.separator;
				File uploadedFileDestination = new File(path);
				
				if(!uploadedFileDestination.exists()){
					uploadedFileDestination.mkdirs();
				}
				
				File dest = new File(path + filename[0]);					
				FileUtil.copyFile(file[0], dest);
				
		        //System.out.println("with file");
				if(filename.length > 1)
				{
				  File[] destnames = new File[2];
				  String[] filenames = new String[2];
				
				  for(int i=0; i<filenames.length;i++)
				  {
				    destnames[i] = new File(path + filename[i]);
				    FileUtil.copyFile(file[i], destnames[i]);
				    filenames[i] = destnames[i].getAbsolutePath();
				  }
				  
				  emailSent = EmailUtil.sendWithManyAttachments(from, to.split(","), subject, content.toString(), filenames);
				}
				else
				{
				  File[] destnames = new File[1];
				  String[] filenames = new String[1];
			
				  for(int i=0; i<filenames.length;i++)
				  {
				    destnames[i] = new File(path + filename[i]);
				    FileUtil.copyFile(file[i], destnames[i]);
				    filenames[i] = destnames[i].getAbsolutePath();
				  }
				  
				  emailSent = EmailUtil.sendWithManyAttachments(from, to.split(","), subject, content.toString(), filenames);
				}
			} else {
				//System.out.println("without file");
				
				emailSent = EmailUtil.sendWithHTMLFormat(from, to.split(","), subject, content.toString(), null);				
			}
		} catch (Exception e) {
			logger.debug("failed to send email in " + company.getName()
					+ "... " + e);
		}

		return emailSent;
	}
	
	private boolean saveImages(File[] files, String[] filenames, String[] contentTypes) {
		CompanySettings companySettings = company.getCompanySettings();
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));		
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "ImagesReceipts";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));

		// create the orig, image1, image2, image3 and thumbnail folders
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image1"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image2"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image3"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "thumbnail"));

		destinationPath = servletContext.getRealPath(destinationPath);
		
		Long companyId = company.getId();
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
			String source = files[i].getAbsolutePath();
	
			//System.out.println("File #"+i+": source: "+source);
			
			String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"),
			String.valueOf(new Date().getTime()));
	
	
			if (!FileUtil.getExtension(filename).equalsIgnoreCase("jpg") && !FileUtil.getExtension(filename).equalsIgnoreCase("jpeg") &&
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
			}
			
			// create the page image
			// create the page image
			MemberFile memberFile=new MemberFile();
			MemberFileItems receiptImage = new MemberFileItems();
			
			receiptImage.setFilename(filenames[i]);
			
			
			// pageImage.setUrl(UrlUtil.generateImageUploadUrl(company) + "pages/");
			if(member == null){
				Member member1=new Member();
				member1.setId(Long.parseLong(request.getParameter("member_id")));
				memberFile.setMember(member1);
				Long newInsertedId=memberFileDelegate.insert(memberFile);
				if(newInsertedId!=null){
					//System.out.println("NEW MEMBERFILE WAS INSERTED having an ID of"+newInsertedId);
					memberFile=memberFileDelegate.find(newInsertedId);
				}
			}
			
			
		
	
			//insert this in member_File table
			//memberFile.setRemarks("sample remarks Only");
			//memberFile.setValue(value)
			receiptImage.setMemberFile(memberFile);
			// insert this in memberFileItems

			receiptImage.setFilename(filename);
			//if(request.getParameter("points")!=null)
				//receiptImage.setPoints(Double.parseDouble(request.getParameter("points")));
			receiptImage.setOriginal("original/" + filename);
			String itemDescription="";
			
			
			String itemArray[]=request.getParameterValues("itemArray");
			String priceArray[]=request.getParameterValues("priceArray");
			String qtyArray[]=request.getParameterValues("qtyArray");
			String pointsArray[]=request.getParameterValues("pointsArray");
			String subtotal[]=request.getParameterValues("subtotal");
			String subpoints[]=request.getParameterValues("subpoints");
			String generatedTableForDescription="";
			Double total_amount=0.0;
			Double total_point=0.0;
			if(!(itemArray==null&&priceArray==null&&qtyArray==null&&pointsArray==null))
			{
				String totalPrice=request.getParameter("totalPrice");
				String totalPoints=request.getParameter("totalPoints");
				generatedTableForDescription+="<table width=100% id=toolTipTable>";
				for(int index=0;index<itemArray.length;index++){
					generatedTableForDescription+="<tr align=left><td>"+itemArray[index]+"</td>";
					generatedTableForDescription+="<td align=center>"+pointsArray[index]+"</td>";
					generatedTableForDescription+="<td align=center>"+qtyArray[index]+"</td>";
					generatedTableForDescription+="<td align=center>"+subpoints[index]+"</td></tr>";
				}
				generatedTableForDescription+=("<tr><td>Total</td><td>&nbsp;</td><td>&nbsp;</td><td align=center><strong>"+request.getParameter("totalPoints")+" GC.</strong></td></tr>");
				generatedTableForDescription+="</table>";
				//total Points
				receiptImage.setValue(totalPoints);
			
			}
			receiptImage.setRemarks(request.getParameter("remarks"));
			receiptImage.setDistributor(request.getParameter("distributor"));
			receiptImage.setInvoiceNumber(request.getParameter("invoiceNumber"));
			receiptImage.setCompanyName_SOLD(request.getParameter("companyName_SOLD"));
			receiptImage.setDescription(generatedTableForDescription);
			receiptImage.setCompany(company);
			receiptImageDelegate.insert(receiptImage);
			request.setAttribute("receiptImage",receiptImage);
			
			
			//THIS WOULD TRIGGER TO ADMIN that a new Receipt has been uploaded...
			Member m=memberDelegate.find(memberFile.getMember().getId());
			if(m!=null){
				request.setAttribute("member", m);
				member = m;
				showAllReceipts();
				
				if(request.getParameter("byAdmin") == null || request.getParameter("byAdmin").length() < 1) {
					sendEmailNotificationToAdmin(m,receiptImage);
					//System.out.println(">>> Send Email Notification to Admin");
				}
			}else{
					//System.out.println("NO M MEMBER WAS FOUND");
			}
			
		}
		}
		
		return true;
	}

	private List<MemberFileItems> showAllReceipts() {
		List<MemberFileItems> receiptList;
		receiptList=new ArrayList<MemberFileItems>();
		List<MemberFile> memberFile=null;
		MemberFileDelegate memberFileDelegate=MemberFileDelegate.getInstance();
		//System.out.println(((member!=null))+">>>>>>>>>>>>>>>>>>>>>MEMBER IS ");
				memberFile=memberFileDelegate.findAll(member);
			for(MemberFile memFile:memberFile){
				MemberFileItems fileInfo=receiptImageDelegate.findMemberFileItem(company , memFile.getId());
				if(fileInfo!=null)
					receiptList.add(fileInfo);
				}
			if(receiptList!=null){
				//System.out.println("THE TOTAL NUMBER OF ITEMS FOUND IS/ARE "+receiptList.size());
			}
			Collections.reverse(receiptList);
			request.setAttribute("receiptList",receiptList);
			return receiptList;
	}

	
	
	public String sendEmailNotificationToAdmin(Member member,MemberFileItems memberFileItem) {	
		if (company!=null){
			EmailUtil.connect("smtp.gmail.com", 25);
			StringBuffer content = new StringBuffer();
			
			
			content.append("<p><h3>Invoice Details:  </h3></p>");
			if(company.getName().equalsIgnoreCase("apc"))
				content.append("<p><h4> Distributor:   "+memberFileItem.getDistributor()+"</h4></p>");
			content.append("<p><h4> Salesman Company Name:   "+member.getReg_companyName()+"</h4></p>");
			content.append("<p><h4> Company Name:   "+memberFileItem.getCompanyName_SOLD()+"</h4></p>");
			content.append("<p><h4> Invoice Number: "+memberFileItem.getInvoiceNumber()+"</h4></p>");
			content.append("<p><h4> Remarks:  "+memberFileItem.getRemarks()+"</h4></p>");
			content.append("<p><h4>&nbsp; </h4></p>");
			content.append("<p><h4>&nbsp; </h4></p>");
			content.append("<p style='color:red'>This is a system generated email. Please do not reply to this email.</p>");
			

			
			
			
			/*
			if(EmailUtil.send("noreply@webtogo.com.ph", "anthony@ivant.com", 
					company.getNameEditable()+":Invoice and Items Upload for Approval " + member.getFullName()
					+ "!", content.toString())){
				successfully=true;
			}
			
			*/
			
			EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", company.getEmail().split(","),  company.getNameEditable()+": Invoice and Items Upload for Approval: " + member.getFullName().toUpperCase(),  content.toString(), null);
			
			/*
			if(EmailUtil.send("noreply@webtogo.com.ph", "Elizabeth.Galvez@schneider-electric.com", 
					company.getNameEditable()+":Invoice and Items Upload for Approval " + member.getFullName()
					+ "!", content.toString())){
				successfully=true;
			}
			
			
			if(EmailUtil.send("noreply@webtogo.com.ph", "firsttierbrands@gmail.com", 
					company.getNameEditable()+":Invoice and Items Upload for Approval " + member.getFullName()
					+ "!", content.toString())){
				successfully=true;
			}
			*/
			
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("MM/dd/yyyy");
			  
			String message="";
			message+="<p>Date:"+formatter.format(currentDate.getTime())+"</p><br>";
			message+="<p>Invoice Number: "+memberFileItem.getInvoiceNumber() +"</p><br>";
			message+="<p>Salesman Company Name:   "+member.getReg_companyName()+"</p><br>";
			message+="<p>Company Name:"+member.getReg_companyName()+"</p><br>";
			message+="<p>Status: For Verification</p><br><br>";
			message+="<p>Please allow up to 3 working days for Invoice and Items review pending its Approval. ,</p><br>";
			message+="<p>Thank you.</p>";
			message+="<p>&nbsp;</p>";
			message+="<p style='color:red'>This is a system generated email. Please do not reply to this email.</p>";
			
			String messageTitle = company.getNameEditable() +" : Invoice Upload Update";
			
			// this message title is for APC only
	
			if(company.getName().equalsIgnoreCase("apc")){
				messageTitle="APC by Schneider Electric : Invoice upload update.";
			}
			else{
				message = "";
				FormPage uploadedReceiptEmail=formPageDelegate.find(company, company.getName()+"-uploadedReceiptEmail");
				messageTitle = uploadedReceiptEmail.getTitle();
				message = uploadedReceiptEmail.getTopContent();
				
				message = message.replace("(name of the user)",  member.getFirstname()+" "+member.getLastname());
				message = message.replace("(member.companyRegisteredEmail)", member.getReg_companyName());
				message = message.replace("(memberFileItem.invoiceNumber)", memberFileItem.getInvoiceNumber());
			}
			boolean successfully = false;
			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph",  member.getEmail(),  messageTitle ,  message, null)){
				successfully=true;
			}
			
			if(successfully)
				return SUCCESS;
			
		}
		return Action.ERROR; 
	}
	
	private void saveFiles(File[] files, String[] filenames, String[] contentTypes) {
		String destinationPath = "companies";
		Member member = memberDelegate.find(Long.parseLong(request.getParameter("member_id")));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + company.getName();
		int maxInvoice = memberFileItemDelegate.findMaxMemberFileItem(company);
		String invoiceNumber = request.getParameter("invoice_number");
		
		maxInvoice = maxInvoice + 1;

		if(company.getName().equalsIgnoreCase("giftcard"))
		{
			maxInvoice = maxInvoice - 1;
		}
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "uploads"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "uploads" + File.separator + member.getId()));
		
		if(company.getName().equalsIgnoreCase("giftcard"))
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "uploads" + File.separator + member.getId() + File.separator + invoiceNumber));
		else
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "uploads" + File.separator + member.getId() + File.separator + maxInvoice));
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
				File file = files[i];
				String filename = filenames[i];
				String contentType = contentTypes[i];
				String destination = "";
				
				if(company.getName().equalsIgnoreCase("giftcard"))
					destination = servletContext.getRealPath(destinationPath + File.separator + "uploads" + File.separator + member.getId() + File.separator + invoiceNumber);
				else
					destination = servletContext.getRealPath(destinationPath + File.separator + "uploads" + File.separator + member.getId() + File.separator + maxInvoice);
				
				File destFile = new File(destination + File.separator + filename);
				FileUtil.copyFile(file, destFile); 
				
				if(company.getName().equalsIgnoreCase("clickbox"))
				{
					MemberFile memberFile = new MemberFile();
					MemberFileItems memberFileItems = new MemberFileItems();	
				
					memberFile.setMember(member);
//					memberFile.setRemarks(remarks);
//					memberFile.setValue(value);
//					memberFile.setApprovedDate(approvedDate);
//					memberFile.setApprovedBy(approvedBy);
					
					if(company.getId() == 257) {
						if(request.getParameter("forQuotation") != null && request.getParameter("forQuotation").length()>0)
							memberFile.setStatus("For Quotation");
						else
							memberFile.setStatus("Order On Process");
					}		
					else
						memberFile.setStatus("Pending");
					
					memberFileDelegate.insert(memberFile);
				
					memberFileItems.setMemberFile(memberFile);
					memberFileItems.setRemarks(request.getParameter("1e|Notes"));
					memberFileItems.setCompany(company);
					memberFileItems.setOriginal("uploads" + "/" + member.getId() + "/" + maxInvoice + "/" + filename);
					memberFileItems.setFilename(filename);
//					memberFileItems.setTitle(title);
//					memberFileItems.setDescription(description);
//					memberFileItems.setDistributor(distributor);
					memberFileItems.setInvoiceNumber(Integer.toString(maxInvoice));
					memberFileItems.setFreight(freight);
//					memberFileItems.setRemarks(remarks);
					memberFileItemDelegate.insert(memberFileItems);
				}
				
				if(company.getName().equalsIgnoreCase("giftcard"))
				{
					MemberFileItems memberFileItems = memberFileItemDelegate.findByInvoice(company, invoiceNumber);
					memberFileItems.setOriginal("uploads" + "/" + member.getId() + "/" + invoiceNumber + "/" + filename);
					memberFileItems.setFilename(filename);
					memberFileItemDelegate.update(memberFileItems);
				}
			} 
		}
	}
	
	public void setCurrentMember(Member m){
		member=m;
	}
	
	public String showMemberReceipt(){
	//	member2.setId(Long.parseLong("1055"));
		int approvedPoints = 0;
		int redeemedPoints = 0;
		receiptList=new ArrayList<MemberFileItems>();
		List<MemberFile> memberFile=new ArrayList<MemberFile>();
		//System.out.println(((member!=null))+">>>>>>>>>>>>>>>>>>>>>MEMBER IS ");
		if((member!=null))
				memberFile=memberFileDelegate.findAll(member);
		else 
			return ERROR;
		for(MemberFile memFile:memberFile){
			MemberFileItems fileInfo=receiptImageDelegate.findMemberFileItem(company , memFile.getId());
			if(fileInfo!=null) {
				receiptList.add(fileInfo);
				//System.out.println("company is null " + company == null);
				//System.out.println("memFile.getStatus()     " + memFile.getStatus());
				//System.out.println("FILE UPLOADED ON ====" + fileInfo.getCreatedOn() + " - " + fileInfo.getValue());
				//fileInfo.getMemberFile().getStatus();
				
				if(company.getName().equalsIgnoreCase("apc")) {
					if(memFile.getStatus() != null){
						if(memFile.getStatus().equalsIgnoreCase("Approved") || memFile.getStatus().contains("Waiting")){
							approvedPoints += Integer.parseInt(fileInfo.getValue());
						} else if (memFile.getStatus().equalsIgnoreCase("Redeemed")) {
							redeemedPoints += Integer.parseInt(fileInfo.getValue());
						}
					}
				}
			}
		}
		
		if(receiptList!=null){
			//System.out.println("THE TOTAL NUMBER OF ITEMS FOUND IS/ARE "+receiptList.size());
			Collections.reverse(receiptList);
			request.setAttribute("receiptList",receiptList);
			
			//apc gc points
			request.setAttribute("approvedPoints", approvedPoints);
			request.setAttribute("redeemedPoints", redeemedPoints);
		}

		//System.out.println("The current UserS nAME is "+member.getFullName());
		if(request.getParameter("profile")!=null){
			request.setAttribute("profile","0");
			Member me=new Member();
			me=memberDelegate.find(member.getId());
			if(me!=null){
				PasswordEncryptor encryptor=new PasswordEncryptor();
				//me.setPassword(encryptor.decrypt(me.getPassword()).trim());<<--Not working well on live.
				request.setAttribute("updateOk", "true");
				me.setPassword("");
				request.setAttribute("member_1",me);

			}
		}else{
			member.setPassword("");
			request.setAttribute("updateOk", "true");
			request.setAttribute("member_1",member);
		}
		
		return Action.SUCCESS;
	}
	
	public String editAccount()
	{
		if(member == null)
			return Action.INPUT;
		
		return Action.SUCCESS;
	}
	
	public String onlineShipping()
	{
		if(member == null)
			return Action.INPUT;

		return Action.SUCCESS;
	}
	
	public String showMemberFiles(){
		if(member == null)
			return Action.INPUT;
		
		memberFileItemsList=new ArrayList<MemberFileItems>();
		List<String> createdOnList=new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		List<MemberFile> memberFile=new ArrayList<MemberFile>();
		//System.out.println(((member!=null))+">>>>>>>>>>>>>>>>>>>>>MEMBER IS ");
		
		if((member!=null))
			memberFile=memberFileDelegate.findAll(member);
		else 
			return ERROR;
		
		for(MemberFile memFile:memberFile){
			MemberFileItems fileInfo=memberFileItemDelegate.findMemberFileItem(company , memFile.getId());
			if(fileInfo!=null)
			{
				memberFileItemsList.add(fileInfo);
				createdOnList.add(format.format(fileInfo.getCreatedOn()));
			}
		}
				
		if(memberFileItemsList!=null){
			//System.out.println("THE TOTAL NUMBER OF ITEMS FOUND IS/ARE "+memberFileItemsList.size());
			request.setAttribute("memberFileItemsList",memberFileItemsList);
			request.setAttribute("createdOnList",createdOnList);
		}
		
//		if(request.getParameter("profile")!=null){
//			request.setAttribute("profile","0");
//			Member me=new Member();
//			me=memberDelegate.find(member.getId());
//			if(me!=null){
//				PasswordEncryptor encryptor=new PasswordEncryptor();
//				//me.setPassword(encryptor.decrypt(me.getPassword()).trim());<<--Not working well on live.
//				request.setAttribute("updateOk", "true");
//				me.setPassword("");
//				request.setAttribute("member_1",me);
//			}
//		}else{
//			member.setPassword("");
//			request.setAttribute("updateOk", "true");
//			request.setAttribute("member_1",member);
//		}
			
		return Action.SUCCESS;
	}

	private boolean isImageFile(String contentType) {
		return StringUtils.contains(contentType, "image");
	}
	
	private String decodeKey(String key) {
		return firstLetterWordUpperCase(key.replace("_", " "));
	}	
	
	private String firstLetterWordUpperCase(String s) {
		char[] chars = s.trim().toLowerCase().toCharArray();
		boolean found = false;

		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i])) {
				found = false;
			}
		}

		return String.valueOf(chars);
	}	
	
	private String getRealPath() {
		ServletContext servCont = ServletActionContext.getServletContext();
		return servCont.getRealPath(getCompanyImageFolder()) + File.separator;
	}
	
	private String getCompanyImageFolder() {
		return "/companies/" + company.getName();
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

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getFreight() {
		return freight;
	}

	public void setMember2(Member member2) {
		this.member2 = member2;
	}

	public Member getMember2() {
		return member2;
	}
	
}
