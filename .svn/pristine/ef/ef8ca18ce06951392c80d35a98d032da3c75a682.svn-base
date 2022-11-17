package com.ivant.cms.action.admin;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.FormPageLanguageDelegate;
import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.PageImageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.FormPageLanguage;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class FormPageAction extends ActionSupport implements Preparable, ServletContextAware,
							ServletRequestAware, UserAware, CompanyAware {

	private static final long serialVersionUID = 5826051458201320336L;
	private static final Logger logger = Logger.getLogger(FormPageAction.class);
	private static final FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private static final PageImageDelegate pageImageDelegate = PageImageDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private LanguageDelegate languageDelegate = LanguageDelegate.getInstance();
	private FormPageLanguageDelegate formPageLanguageDelegate = FormPageLanguageDelegate.getInstance();
	
	private User user;
	private Company company;
	private CompanySettings companySettings;
	private HttpServletRequest request;
	private ServletContext servletContext;
	
	private FormPage formPage;
	private PageImage image;
	
	private File[] files;
	private String[] contentTypes;
	private String[] filenames;
	
	private List<Language> languages;
	
	public void prepare() throws Exception {
		setLanguages(company.getLanguages());
		companySettings = company.getCompanySettings();
		try {
			long formPageId = Long.parseLong(request.getParameter("form_id"));
			formPage = formPageDelegate.find(formPageId);
		}
		catch(Exception e) {
			formPage = new FormPage();
			formPage.setCompany(company);
			
			User persistedUser = userDelegate.load(user.getId());
			formPage.setCreatedBy(persistedUser);
		}
		
		try {
			long imageId = Long.parseLong(request.getParameter("image_id"));
			image = pageImageDelegate.find(imageId);
		}
		catch(Exception e) {}
		
		if(request.getParameter("language")!=null && formPage!=null){
			formPage.setLanguage(languageDelegate.find(request.getParameter("language"),company));
		}	
	}
	
	public String save() {
				
		if (!commonParamsValid()) {
			return Action.ERROR;
		}
		if (request.getParameter("language") == null
				|| request.getParameter("language").isEmpty()) {
			try {
				if (formPage.getCreatedBy() == null)
					formPage.setCreatedBy(userDelegate.load(user.getId()));
				if (formPage.getTopContent() == null)
					formPage.setTopContent("");
				if (formPage.getBottomContent() == null)
					formPage.setBottomContent("");
				if (formPage.getName() == null)
					formPage.setName("");
			} catch (Exception e) {
			}

			if (formPageDelegate.find(company, formPage.getName()) == null) {
				formPageDelegate.insert(formPage);
			} else {
				formPageDelegate.update(formPage);
			}

			lastUpdatedDelegate.saveLastUpdated(company);
		}else{
			//if category doesnt exist(insert)
			FormPageLanguage formPageLanguage = formPage.getFormPageLanguage();
			
			if(formPageLanguage==null){
				formPage.setLanguage(null);
				formPageLanguage = new FormPageLanguage();
				formPageLanguage.cloneOf(formPage);
				formPageLanguage.setLanguage(languageDelegate.find(request.getParameter("language"),company));
				formPageDelegate.refresh(formPage);
				formPageLanguage.setDefaultFormPage(formPage);
				formPageLanguageDelegate.insert(formPageLanguage);
			}
			//if page exist (update)
			else{				
				formPageLanguage.cloneOf(formPage);
				formPageDelegate.refresh(formPage);
				formPageLanguage.setDefaultFormPage(formPage);
				formPageLanguageDelegate.update(formPageLanguage);				
			}
		}
		
		
		return Action.SUCCESS;
	}
	
	public String delete() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!formPage.getCompany().equals(company)) {
			return Action.ERROR;
		}
		
		formPageDelegate.delete(formPage);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public String edit() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!formPage.getCompany().equals(company)) {
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	
//	public String deleteImage() {
//		if(company == null) {
//			return Action.ERROR;
//		}
//		if( !company.equals(singlePage.getCompany()) )  {	
//			return Action.ERROR;
//		}  
//		if(!singlePage.providePageType().equals(image.getPage().providePageType())) {
//			return Action.ERROR;
//		}
//
//		String destinationPath = servletContext.getRealPath("companies" + 
//				File.separator + company.getName() +
//				File.separator + "images" +
//				File.separator + "pages");
//			
//		// delete image1
//		FileUtil.deleteFile(destinationPath + File.separator + image.getImage1());
//		// delete image2
//		FileUtil.deleteFile(destinationPath + File.separator + image.getImage2());
//		// delete image2
//		FileUtil.deleteFile(destinationPath + File.separator + image.getImage3());
//		// delete thumbnail
//		FileUtil.deleteFile(destinationPath + File.separator + image.getThumbnail());
//
//		pageImageDelegate.delete(image);
//		
//		return Action.SUCCESS;
//	}
		
	public String uploadImage() { 
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!company.equals(formPage.getCompany())) {
			return Action.ERROR;
		}
		
		saveImages(files, filenames, contentTypes);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	private void saveImages(File[] files, String[] filenames, String[] contentTypes) {
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "pages";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				
		// create the image1, image2, image3 and thumbnail folders
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
				
				String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"), 
						String.valueOf(new Date().getTime()));	
				filename = FileUtil.replaceExtension(filename, "jpg"); 
				
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
				 
				// create the page image
				PageImage pageImage = new PageImage(); 
				pageImage.setFilename(filenames[i]);
				pageImage.setPage(formPage);
				pageImage.setPageType(formPage.providePageType());
//				pageImage.setUrl(UrlUtil.generateImageUploadUrl(company) + "pages/");
				pageImage.setOriginal("original/" + filename);
				pageImage.setImage1("image1/" + filename);
				pageImage.setImage2("image2/" + filename);
				pageImage.setImage3("image3/" + filename);
				pageImage.setThumbnail("thumbnail/" + filename);
				
				pageImageDelegate.insert(pageImage);
			}
		}
	}
	
	public String deleteImage() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!company.equals(formPage.getCompany())) {
			return Action.ERROR;
		}

		String destinationPath = servletContext.getRealPath("companies" + 
				File.separator + company.getName() +
				File.separator + "images" +
				File.separator + "pages");
			
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
	
	private boolean commonParamsValid() {
		if(company == null) {
			return false;
		}
		if(formPage == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public String execute() throws Exception {
		
		return Action.NONE;
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

	public FormPage getFormPage() {
		return formPage;
	}

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}

	// upload setters
	
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

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	
}
