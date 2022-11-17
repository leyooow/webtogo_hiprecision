package com.ivant.cms.action.admin;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.BrandDelegate;
import com.ivant.cms.delegate.BrandImageDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.BrandImage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.GroupAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class BrandAction extends ActionSupport implements Preparable, ServletRequestAware, ServletContextAware,
					UserAware, CompanyAware, GroupAware {

	private static final long serialVersionUID = -6643627984835985732L;
	private static final Logger logger = Logger.getLogger(BrandAction.class);
	private static final BrandDelegate brandDelegate = BrandDelegate.getInstance();
	private static final BrandImageDelegate brandImageDelegate = BrandImageDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
		
	private HttpServletRequest request;
	private ServletContext servletContext;
	private User user;
	private Company company;
	private Group group;
	private CompanySettings companySettings;
	
	private Brand brand;
	private BrandImage image;
	private String evt;
	
	private File[] files;
	private String[] contentTypes;
	private String[] filenames; 
	
	private String errorType;
	
	private List<Brand> allBrands; 
	private Long parentBrandId;
	
	public void prepare() throws Exception {
		companySettings = company.getCompanySettings();
		try {
			long brandId = Long.parseLong(request.getParameter("brand_id"));
			brand = brandDelegate.find(brandId);
		}
		catch(Exception e) {
			brand = new Brand();
		}
		
		try {
			long imageId = Long.parseLong(request.getParameter("image_id"));
			image = brandImageDelegate.find(imageId);
		}
		catch(Exception e) {
			logger.debug("brand does not have image...");
		}
	}

	public String save() throws Exception {
		if(!commonParamsValid()) {
			return ERROR;
		}
		 
		brand.setGroup(group);
		brand.setCompany(company);
		
		if(parentBrandId!=null){
			Brand parentBrand = brandDelegate.find(parentBrandId);
			brand.setParentBrand(parentBrand);
		}
		
		if(brandDelegate.find(company, group, brand.getName()) == null) {
			brandDelegate.insert(brand);
		}
		else {
			if(brand.getId() == null) {
				errorType = "brandexist";
				return ERROR;
			}
			else {
				brandDelegate.update(brand);
			}
		}
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if(!commonParamsValid()) {
			return ERROR;
		}
		
		if(!brand.getCompany().equals(company)) {
			return ERROR;
		}
			
		brandDelegate.delete(brand);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return SUCCESS;
	}
	
	public String edit() throws Exception {
		if(!commonParamsValid()) {
			return ERROR;
		} 
		
		allBrands = brandDelegate.findAll(company, group,Order.asc("name")).getList();
		
		return SUCCESS;
	}
	
	
	public String uploadImage() { 
		if(!commonParamsValid()) {
			return ERROR;
		} 
						
		if(!brand.getCompany().equals(company)) {
			return ERROR;
		}
		
		if(!brand.getGroup().equals(group)) {
			return ERROR;
		}
		
		saveImages(files, filenames, contentTypes);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	private boolean commonParamsValid() {
		if(company == null) {
			return false;
		}
		
		if(group == null) {
			return false;
		}
		
		return true;
	}
	
	private void saveImages(File[] files, String[] filenames, String[] contentTypes) {
		CompanySettings companySettings = company.getCompanySettings();
			
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "brands";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				
		// create the orig, image1, image2, image3 and thumbnail folders
		if(company.getName().equalsIgnoreCase(CompanyConstants.KUYSEN_FURNITURES) || company.getName().equalsIgnoreCase(CompanyConstants.MOBLER_ENTERPRISE) || company.getName().equalsIgnoreCase(CompanyConstants.SANITEC)) {
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
		}
//		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "orig"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image1"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image2"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image3"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "thumbnail"));
		
		destinationPath = servletContext.getRealPath(destinationPath);
		
		Long companyId = company.getId();
		
		Integer sortCount = 0;
		if(company.getName().equalsIgnoreCase(CompanyConstants.KUYSEN_FURNITURES) || company.getName().equalsIgnoreCase(CompanyConstants.MOBLER_ENTERPRISE) || company.getName().equalsIgnoreCase(CompanyConstants.SANITEC)) {
			if(brand.getImages() != null) {
				sortCount = brand.getImages().size();
			}
		}
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
				String source = files[i].getAbsolutePath();
				
				String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"), 
						String.valueOf(new Date().getTime()));	
				
				if(company.getName().equalsIgnoreCase(CompanyConstants.KUYSEN_FURNITURES) || company.getName().equalsIgnoreCase(CompanyConstants.MOBLER_ENTERPRISE) || company.getName().equalsIgnoreCase(CompanyConstants.SANITEC)) {
					if (FileUtil.getExtension(filename).equalsIgnoreCase("png")) {
						filename = FileUtil.replaceExtension(filename, "png"); 
						File originalFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
						FileUtil.copyFile(files[i], originalFile);
					} else {
						filename = FileUtil.replaceExtension(filename, "jpg"); 
						File originalFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
						FileUtil.copyFile(files[i], originalFile);
					}
				} else {
					filename = FileUtil.replaceExtension(filename, "jpg");
				}
				
//				File origFile = new File(destinationPath + File.separator + "orig" + File.separator + filenames[i]);
				
				// save the original files 
//				FileUtil.copyFile(files[i], origFile);
				
				// instead of creating directly from the temporary file, create a file from the orignial file
				
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
				BrandImage brandImage = new BrandImage();
				brandImage.setFilename(filenames[i]);
				brandImage.setBrand(brand); 
				brandImage.setCaption(brand.getName());
				brandImage.setImage1("image1/" + filename);
				brandImage.setImage2("image2/" + filename);
				brandImage.setImage3("image3/" + filename);
				brandImage.setThumbnail("thumbnail/" + filename);
				sortCount++;
				if(company.getName().equalsIgnoreCase(CompanyConstants.KUYSEN_FURNITURES) || company.getName().equalsIgnoreCase(CompanyConstants.MOBLER_ENTERPRISE) || company.getName().equalsIgnoreCase(CompanyConstants.SANITEC)) {
					brandImage.setOriginal("original/" + filename);
					brandImage.setSortOrder(sortCount);
				}
				
				brandImageDelegate.insert(brandImage);
			}
		}
	}
	
	public String deleteImage() {
		if(company == null) {
			return Action.ERROR;
		}
		
		if( !brand.getCompany().equals(company) ) {
			return ERROR;
		}

		if(!image.getBrand().getCompany().equals(company)) {
			return Action.ERROR;
		}

		String destinationPath = servletContext.getRealPath("companies" + 
				File.separator + company.getName() +
				File.separator + "images" +
				File.separator + "brands");
			
		// delete image1
		FileUtil.deleteFile(destinationPath + File.separator + image.getImage1());
		// delete image2
		FileUtil.deleteFile(destinationPath + File.separator + image.getImage2());
		// delete image2
		FileUtil.deleteFile(destinationPath + File.separator + image.getImage3());
		// delete thumbnail
		FileUtil.deleteFile(destinationPath + File.separator + image.getThumbnail());

		brandImageDelegate.delete(image);
				
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Brand getBrand() {
		return brand;
	}
	
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	
	public void setBrand(Brand brand) {
		this.brand = brand;
	}	
	
	public String getEvt() {
		return evt;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public CompanySettings getCompanySettings() {
		return companySettings;
	}
	
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}
	
	public void setUpload(File[] files) {
		this.files = files;
	}
	
	public void setUploadContentType(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}
	
	public void setUploadFileName(String[] filenames) {
		this.filenames = filenames;
	}
	
	public String getErrorType() {
		return errorType;
	}

	public void setParentBrandId(Long parentBrandId) {
		this.parentBrandId = parentBrandId;
	}

	public List<Brand> getAllBrands() {
		return allBrands;
	}

}
