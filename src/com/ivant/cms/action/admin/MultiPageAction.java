package com.ivant.cms.action.admin;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.MultiPageImageDelegate;
import com.ivant.cms.delegate.MultiPageLanguageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageImage;
import com.ivant.cms.entity.MultiPageLanguage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.EntityLogEnum;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

// TODO - Fix the functionality when the multi page properties are updated

public class MultiPageAction extends ActionSupport implements Preparable, ServletContextAware, 
				ServletRequestAware, UserAware, PagingAware, CompanyAware {
	
	private static final long serialVersionUID = 4137618690542101030L;
	private static final Logger logger = Logger.getLogger(MultiPageAction.class);
	private static final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private static final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private static final MultiPageImageDelegate multiPageImageDelegate = MultiPageImageDelegate.getInstance();	
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final LogDelegate logDelegate = LogDelegate.getInstance();
	private LanguageDelegate languageDelegate = LanguageDelegate.getInstance();
	private MultiPageLanguageDelegate multiPageLanguageDelegate = MultiPageLanguageDelegate.getInstance();
	private static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	
	private User user; 
	private HttpServletRequest request;
	private ServletContext servletContext;
	private Company company;
	private CompanySettings companySettings;
	private List<Category> categoriesList;
	private List<Category> sortedCategoriesList;
	
	private int page;
	private int totalItems;
	private int itemsPerPage;
	private String image_id;
	private File[] files;
	private String[] contentTypes;
	private String[] filenames;
	private List<Category> categories;
	private List<CategoryItem> items;
	private Category category;
	
	private MultiPage multiPage;
	private MultiPageImage multiPageImage;
	private List<SinglePage> multiPageItems;
	private List<Log> logList;
	private List<Language> languages;
	
	private Group group;
	
	public List<Log> getLogList(){
		return logList;
	}
	
	/*
			try {
			long categoryId = Long.parseLong(request.getParameter("category_id"));
			if(categoryId == 0) {
				returnValue = Action.ERROR;
			}
			category = categoryDelegate.find(categoryId);
			showAll = false;
		}
		catch(Exception e) { 
			logger.debug("no category created.");
		}
	
	
	*/
	public void prepare() throws Exception {
		setLanguages(company.getLanguages());
		setCompanySettings(company.getCompanySettings());
		try {
			long multiPageId = Long.parseLong(request.getParameter("multipage_id"));
			multiPage = multiPageDelegate.find(multiPageId);
			
			if(request.getParameter("language")!=null && multiPage!=null){
				multiPage.setLanguage(languageDelegate.find(request.getParameter("language"),company));
			}
			
			logList = logDelegate.findAll(multiPage.getId(), EntityLogEnum.MULTI_PAGE).getList();
			//System.out.println("Log list length: " + logList.size());
			//THIS PART IS FOR HPDSKED ONLY
			if(multiPageId==1111){
				long categoryId = Long.parseLong("205");//request.getParameter("category_id")
				category = categoryDelegate.find(categoryId);
			}
			
		}
		catch(Exception e) {
			multiPage = new MultiPage();
			User persistedUser = userDelegate.load(user.getId());
			
			multiPage.setCreatedBy(persistedUser);
			multiPage.setCompany(user.getCompany());

		}	
	}
	
	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}
	
	public String save() {
		/*User persistedUser = userDelegate.load(user.getId());
		System.out.println("Debug User");
		multiPage.setCreatedBy(persistedUser);*/
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		try{
			if(multiPage.getCreatedBy() == null) multiPage.setCreatedBy(userDelegate.load(user.getId()));
			if(multiPage.getName() == null) multiPage.setName("");
		}catch(Exception e){}
		
		//System.out.println("Request language: "+request.getParameter("language"));
		if(StringUtils.isEmpty(request.getParameter("language"))){
			MultiPage oldMultiPage = (MultiPage) request.getSession().getAttribute("oldMultiPage");
			
			Log log = new Log();
			
			if(multiPageDelegate.find(user.getCompany(), multiPage.getName()) == null) {
				
				long id = multiPageDelegate.insert(multiPage);
				//insert log here
				log.setRemarks("Added a new multi page " + "\"" + multiPage.getName() + "\"");
				log.setEntityType(EntityLogEnum.MULTI_PAGE);
				log.setEntityID(id);
				log.setCompany(company);
				
				log.setUpdatedByUser(user);
				logDelegate.insert(log);
				
			} 
			else {
				if(multiPage.getId() == null) {
					return ERROR;
				}
	
				if(multiPageDelegate.update(multiPage)){
					log.setRemarks("Updated the multipage " + multiPage.getName());
					//update action log
					log.setEntityType(EntityLogEnum.MULTI_PAGE);
					log.setEntityID(multiPage.getId());
					log.setCompany(company);
					log.setUpdatedByUser(user);
					logDelegate.insert(log);
				}
			}
		}
		else
		{
			MultiPageLanguage multiPageLanguage = multiPage.getMultiPageLanguage();
			
			if(multiPageLanguage == null)
			{
				multiPage.setLanguage(null);
				//System.out.println("USER "+user.getFullName());
				multiPage.setCreatedBy(user);
				MultiPageLanguage mpLanguage = new MultiPageLanguage();
				mpLanguage.cloneOf(multiPage);
			
				mpLanguage.setLanguage(languageDelegate.find(request.getParameter("language"),company));
				multiPageDelegate.refresh(multiPage);
				mpLanguage.setDefaultPage(multiPage);
				multiPageLanguageDelegate.insert(mpLanguage);
				
			}
			else
			{
				multiPageLanguage.cloneOf(multiPage);
				multiPageDelegate.refresh(multiPage);
				multiPageLanguage.setDefaultPage(multiPage);
				multiPageLanguageDelegate.update(multiPageLanguage);
			}
		}
		
		if(company.getName().equals("neltex") && files != null) {
			uploadImage();
		}
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public String delete() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return ERROR;
		}
		if( !user.getCompany().equals(multiPage.getCompany()) ) {
			return Action.ERROR;
		}
		
		multiPageDelegate.delete(multiPage);
		//insert log here
		Log log = new Log();
		log.setEntityType(EntityLogEnum.MULTI_PAGE);
		log.setRemarks("Deleted the multi page "+ "\"" + multiPage.getName() + "\"");
		log.setEntityID(multiPage.getId());
		log.setCompany(company);
		log.setUpdatedByUser(user);
		logDelegate.insert(log);
		
		lastUpdatedDelegate.saveLastUpdated(company);		
		
		return Action.SUCCESS;
	}
	
	public String edit() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		if( !user.getCompany().equals(multiPage.getCompany()) ) {
			return Action.ERROR;
		}
		
		return Action.SUCCESS;
	}
	
	public String editChild() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		} 
		if( !user.getCompany().equals(multiPage.getCompany()) ) {
			return Action.ERROR;
		}		
		if(company.getName().equalsIgnoreCase("HPDSKED"))
			if (group == null){
				Order[] orders = {Order.asc("parentCategory"),Order.asc("sortOrder"),Order.asc("name")};
				group = groupDelegate.find(Long.parseLong("205"));//request.getParameter("group_id")==205
				categories = categoryDelegate.findAllWithPaging(user.getCompany(), null, group, user.getItemsPerPage(), page, orders, true).getList();
				Order[] ordersItem = {Order.asc("parent"),Order.asc("sortOrder"),Order.asc("name")};
				categoriesList = categoryDelegate.findAll(user.getCompany(), group).getList();
				sortedCategoriesList = sortList(categoriesList);//sort categoriesList
				items = categoryItemDelegate.findAllWithPaging(user.getCompany(), group, category, 1000, page, true, ordersItem).getList();
				request.setAttribute("allItems",items);
				request.setAttribute("allCategories",categories);
				
				//System.out.println("****************************************");
				//System.out.println("****************************************");
				//System.out.println("********Item size = "+items.size()+"***********");
				//System.out.println("****************************************");
				//System.out.println("****************************************");
			}
		
		
		String searchByKeyword=request.getParameter("searchByKeyword");
		if(searchByKeyword!=null){
			multiPageItems = singlePageDelegate.findByKeyword(company, multiPage, searchByKeyword).getList();
			request.setAttribute("searchByKeyword", searchByKeyword);
		}else{		
//		List<SinglePage> multiPageItems = multiPage.getItems();		
			Order order_ = Order.asc("sortOrder");
			multiPageItems = singlePageDelegate.findAll(company, multiPage, order_).getList();
		}
		if(company.getName().equalsIgnoreCase("hpdsked")&&(multiPage.getName().equalsIgnoreCase("events")||multiPage.getName().equalsIgnoreCase("retiroBranch"))){
			return "hpdsked-schedule";
		}
		 
		return Action.SUCCESS;
	}
	
	/* 	
	 * returns an alphabetically-sorted list of the categoriesList
	 * uses the Bubble Sort Implementation
	*/
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
	
	public String uploadImage() { 
		if(company == null) {
			return Action.ERROR;
		}
		if( !company.equals(multiPage.getCompany()) )  {	
			return Action.ERROR;
		}
		
//		System.out.println(files.getClass());
//		System.out.println(filenames);
//		System.out.println(contentTypes);
		saveImages(files, filenames, contentTypes);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	private boolean saveImages(File[] files, String[] filenames, String[] contentTypes) {
		
		CompanySettings companySettings = company.getCompanySettings();
		
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));		
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
		
		Long companyId = company.getId();
		
		//System.out.println("SAVING "+files.length +"file.");
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

			// create the page image
			MultiPageImage multipageImage = new MultiPageImage();
			multipageImage.setFilename(filenames[i]);
			multipageImage.setMultipage(multiPage);
			//multipageImage.setPageType(multiPage.providePageType());
			// pageImage.setUrl(UrlUtil.generateImageUploadUrl(company) + "pages/");
			multipageImage.setOriginal("original/" + filename);
			multipageImage.setImage1("image1/" + filename);
			multipageImage.setImage2("image2/" + filename);
			multipageImage.setImage3("image3/" + filename);
			multipageImage.setThumbnail("thumbnail/" + filename);
	
			multiPageImageDelegate.insert(multipageImage);
		}
		}
		return true;
	}
	
	public String deleteImage() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		if( !company.equals(multiPage.getCompany()) )  {	
			return Action.ERROR;
		}

		String destinationPath = servletContext.getRealPath("companies" + 
				File.separator + company.getName() +
				File.separator + "images" +
				File.separator + "items");
			
		
		//logger.info("CategoryImage " + category.getImages()getcategoryImage);
		logger.info("-->multipage::::: " + multiPage);
		
		multiPageImage = multiPageImageDelegate.find(new Long(image_id));
		if (multiPageImage!=null)
		{
		logger.info("categoryImage1: " + multiPageImage.getImage1());
		logger.info("categoryImage2: " + multiPageImage.getImage2());
		logger.info("categoryImage3: " + multiPageImage.getImage3());
		
		// delete image1
		FileUtil.deleteFile(destinationPath + File.separator + multiPageImage.getImage1());
		// delete image2
		FileUtil.deleteFile(destinationPath + File.separator + multiPageImage.getImage2());
		// delete image2
		FileUtil.deleteFile(destinationPath + File.separator + multiPageImage.getImage3());
		// delete thumbnail
		FileUtil.deleteFile(destinationPath + File.separator +multiPageImage.getThumbnail());

		multiPageImageDelegate.delete(multiPageImage);
		}
		
		company = multiPage.getCompany();
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	
	private boolean commonParamsValid() {
		if(user.getCompany() == null) {
			return false;
		}		
		return true;
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
		try {
			totalItems = singlePageDelegate.findAll(user.getCompany(), multiPage).getTotal();
		}
		catch(Exception e) {
			logger.debug("failed to set total items. " + e);
		}
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	public MultiPage getMultiPage() {
		return multiPage;
	}
	
	public void setMultiPage(MultiPage multiPage) {
		this.multiPage = multiPage;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}

	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	public User getUser() {
		return user;
	}
	
	public List<SinglePage> getMultiPageItems() {
		return multiPageItems;
	}
	
	

	public List<MultiPage> getParentMultiPages(MultiPage multiPage)
	{   
		
		LinkedList list;
		list = new LinkedList<MultiPage>();
		while (multiPage.getParentMultiPage()!=null) {
				multiPage = multiPage.getParentMultiPage();
				list.add(multiPage);
		}
		Collections.reverse(list);
		return list;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}	
	
	public void setUpload(File[] files) {
		this.files = files;
	}
	
	public File[] getUpload() {
		return files;
	}
	
	public void setUploadContentType(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}
	
	public void setUploadFileName(String[] filenames) {
		this.filenames = filenames;
	}
	
	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}
	
	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public List<Language> getLanguages() {
		return languages;
	}
}
