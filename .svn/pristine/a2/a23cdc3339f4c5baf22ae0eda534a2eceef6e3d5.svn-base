package com.ivant.cms.action;
import java.io.File;
import java.text.SimpleDateFormat;
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

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.MultiPageFileDelegate;
import com.ivant.cms.delegate.MultiPageLanguageDelegate;
import com.ivant.cms.delegate.PageFileDelegate;
import com.ivant.cms.delegate.PageImageDelegate;
import com.ivant.cms.delegate.ReceiptImageDelegate;
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
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.PageFile;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.Receipts;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.SinglePageLanguage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.ApplicationConstants;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class UploadImageReceipt extends AbstractBaseAction {
	
	private static final long serialVersionUID = -244217747366834302L;
	private static final Logger logger = Logger.getLogger(UploadImageReceipt.class);
	private static SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private static final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private static final PageImageDelegate pageImageDelegate = PageImageDelegate.getInstance();
	private static final MultiPageFileDelegate multiPageFileDelegate = MultiPageFileDelegate.getInstance();	
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private static final PageFileDelegate pageFileDelegate = PageFileDelegate.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final LogDelegate logDelegate = LogDelegate.getInstance();
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private LanguageDelegate languageDelegate = LanguageDelegate.getInstance();
	private SinglePageLanguageDelegate singlePageLanguageDelegate = SinglePageLanguageDelegate.getInstance();
	private MultiPageLanguageDelegate multiPageLanguageDelegate = MultiPageLanguageDelegate.getInstance();
	private ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private static final ReceiptImageDelegate receiptImageDelegate = ReceiptImageDelegate.getInstance();
	private List<Receipts> receiptList;
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

	
	///
	
	private List<Log> logList;
	
	public List<Log> getLogList(){
		return logList;
	}
	
	public void prepare() throws Exception {
		//setLanguages(company.getLanguages());
		//companySettings = company.getCompanySettings();	
	}
	
	
	@Override
	public String execute() throws Exception {
		return Action.NONE;
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
		//System.out.println("THE FILES IS "+files);
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
				/*
				// generate image 1
				ImageUtil.generateJpegImage(source, destinationPath + File.separator + "image1" + File.separator + filename,
				companySettings.getImage1Width(), companySettings.getImage1Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
		
				// generate image 2
				ImageUtil.generateJpegImage(source, destinationPath + File.separator + "image2" + File.separator + filename,
				companySettings.getImage2Width(), companySettings.getImage2Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
		
				// generate image 3
				ImageUtil.generateJpegImage(source, destinationPath + File.separator + "image3" + File.separator + filename,
				companySettings.getImage3Width(), companySettings.getImage3Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
		
				// generate thumbnail
				ImageUtil.generateThumbnailImage(source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
				*/
			}
			
			// create the page image
			// create the page image
			Receipts receiptImage = new Receipts();
			receiptImage.setFilename(filenames[i]);
			// pageImage.setUrl(UrlUtil.generateImageUploadUrl(company) + "pages/");
			if(member == null){
				Member member1=new Member();
				member1.setId(Long.parseLong(request.getParameter("member_id")));
				receiptImage.setMember(member1);
			}
		
			receiptImage.setCompany(company);
			receiptImage.setPoints(0.0);
			if(request.getParameter("points")!=null)
				receiptImage.setPoints(Double.parseDouble(request.getParameter("points")));
			receiptImage.setOriginal("original/" + filename);
			receiptImage.setImage1("image1/" + filename);
			receiptImage.setImage2("image2/" + filename);
			receiptImage.setImage3("image3/" + filename);
			receiptImage.setThumbnail("thumbnail/" + filename);
			
			receiptImageDelegate.insert(receiptImage);
			request.setAttribute("receiptImage",receiptImage);
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
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "uploads"));
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
				File file = files[i];
				String filename = filenames[i];
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
	
	public String showMemberReceipt(){
	//	member2.setId(Long.parseLong("1055"));
		receiptList=receiptImageDelegate.findAll(company, member);
		if(receiptList == null){
			return Action.ERROR;
		}
		request.setAttribute("receiptList",receiptList);
		return Action.SUCCESS;
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
	
}
