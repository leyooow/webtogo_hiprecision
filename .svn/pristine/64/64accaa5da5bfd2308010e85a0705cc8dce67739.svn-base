/**
 *
 */
package com.ivant.cms.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.enums.CompanyStatusEnum;

/**
 * @author Edgar S. Dacpano
 */
@Entity
@Table(name = "company_settings")
public class CompanySettings
		extends CompanyBaseObject
{
	public CompanySettings()
	{
		super();
	}
	
	private Boolean DoNotGenerateBilling;
	
	private Boolean hasWishlist;
	private Boolean canBatchUpdateExcelFiles;
	private Boolean hasOrder;
	private Boolean hasPalAccount;
	private Boolean manyAttachments;
	
	// setting for product inventory
	private Boolean hasProductInventory;
	
	// setting for HTML Meta Setting for pages
	private Boolean hasMetaPage;
	
	// setting for HTML Meta Setting for products & Category
	private Boolean hasMetaProduct;
	
	// setting for client chatting
	private Boolean hasClientChat;
	
	// setting for member files
	private Boolean hasMemberFiles;
	
	// setting for message board feature
	private Boolean hasBulletinFeature;
	
	// settings related to featured pages
	private Boolean hasFeaturedSinglePages;
	private Integer maxFeaturedSinglePages;
	
	// settings related to featured pages
	private Boolean hasFeaturedPages;
	private Integer maxFeaturedPages;
	
	// settings related to featured products
	private Boolean hasFeaturedProducts;
	private Integer maxFeaturedProducts;
	
	// settings related to products
	private Boolean hasProducts;
	private Integer productsPerPage;
	
	// settings related to packages
	private Boolean hasPackages;
	private Integer packagesPerPage;
	
	// settings related to other fields
	private Boolean hasOtherFields;
	private Integer maxOtherFields;
	
	// settings related to categoryMenu
	private Boolean hasCategoryMenu;
	private Integer maxCategoryMenu;
	
	// settings related to images
	private Integer image1Width;
	private Integer image1Heigth;
	private Integer image2Width;
	private Integer image2Heigth;
	private Integer image3Width;
	private Integer image3Heigth;
	
	// settings whether the image will be resized to the given dimension by force
	private Boolean image1Forced;
	private Boolean image2Forced;
	private Boolean image3Forced;
	
	// email setting
	private String emailUserName;
	private String emailPassword;
	private String smtp;
	private String portNumber;
	private Boolean autoAnswer;
	private String subject;
	private String message;
	
	private Integer maxAllowedImages;
	private Integer maxAllowedFiles;
	private Long maxFileSize;
	private Double monthlyCharge;
	private Date onlineDate;
	private Boolean willExpire;
	private Integer expiryDate;
	private Boolean limitDownloads;
	private Integer downloads;
	private CompanyStatusEnum companyStatus;
	
	// setting to show the brand or not
	private Boolean showBrands;
	
	private Boolean hasEventCalendar;
	private Boolean hasRepeatingEvent;
	
	private Boolean suspended;
	
	private Boolean hasMemberFeature;
	
	private String shippingType;
	
	private Boolean hasUserRights;
	
	private Boolean hasComments;
	
	private Boolean hasReferrals;
	
	private Boolean hasPageFileRights;
	
	private Boolean hasTestimonials;
	
	private Boolean hasBestSellers;
	private Integer maxBestSellers;

	private Boolean hasPromoCode;
	private String promoCode;

	private Boolean hasMobileSite;
	
	private String accountExpiryDate;
	
	// GETTERS
	
	@Basic
	@Column(name = "_do_not_generate_billing")
	public Boolean getDoNotGenerateBilling()
	{
		if(DoNotGenerateBilling == null)
		{
			this.DoNotGenerateBilling = Boolean.FALSE;
		}
		return DoNotGenerateBilling;
	}
	
	@Basic
	@Column(name = "has_wishlist")
	public Boolean getHasWishlist()
	{
		if(hasWishlist == null)
		{
			this.hasWishlist = Boolean.FALSE;
		}
		return hasWishlist;
	}
	
	@Basic
	@Column(name = "can_batch_update_excel_files")
	public Boolean getCanBatchUpdateExcelFiles()
	{
		if(canBatchUpdateExcelFiles == null)
		{
			this.canBatchUpdateExcelFiles = Boolean.FALSE;
		}
		return canBatchUpdateExcelFiles;
	}
	
	@Basic
	@Column(name = "has_order")
	public Boolean getHasOrder()
	{
		if(hasOrder == null)
		{
			this.hasOrder = Boolean.FALSE;
		}
		return hasOrder;
	}
	
	@Basic
	@Column(name = "has_pal_account")
	public Boolean getHasPalAccount()
	{
		if(hasPalAccount == null)
		{
			this.hasPalAccount = Boolean.FALSE;
		}
		return hasPalAccount;
	}
	
	@Basic
	@Column(name = "many_attachments")
	public Boolean getManyAttachments()
	{
		if(manyAttachments == null)
		{
			this.manyAttachments = Boolean.FALSE;
		}
		return manyAttachments;
	}
	
	@Basic
	@Column(name = "has_product_inventory")
	public Boolean getHasProductInventory()
	{
		if(hasProductInventory == null)
		{
			this.hasProductInventory = Boolean.FALSE;
		}
		return hasProductInventory;
	}
	
	@Basic
	@Column(name = "has_meta_page")
	public Boolean getHasMetaPage()
	{
		if(hasMetaPage == null)
		{
			this.hasMetaPage = Boolean.FALSE;
		}
		return hasMetaPage;
	}
	
	@Basic
	@Column(name = "has_meta_product")
	public Boolean getHasMetaProduct()
	{
		if(hasMetaProduct == null)
		{
			this.hasMetaProduct = Boolean.FALSE;
		}
		return hasMetaProduct;
	}
	
	@Basic
	@Column(name = "has_client_chat")
	public Boolean getHasClientChat()
	{
		if(hasClientChat == null)
		{
			this.hasClientChat = Boolean.FALSE;
		}
		return hasClientChat;
	}
	
	@Basic
	@Column(name = "has_member_files")
	public Boolean getHasMemberFiles()
	{
		if(hasMemberFiles == null)
		{
			this.hasMemberFiles = Boolean.FALSE;
		}
		return hasMemberFiles;
	}
	
	@Basic
	@Column(name = "has_bulletin_feature")
	public Boolean getHasBulletinFeature()
	{
		if(hasBulletinFeature == null)
		{
			this.hasBulletinFeature = Boolean.FALSE;
		}
		return hasBulletinFeature;
	}
	
	@Basic
	@Column(name = "has_featured_single_pages")
	public Boolean getHasFeaturedSinglePages()
	{
		if(hasFeaturedSinglePages == null)
		{
			this.hasFeaturedSinglePages = Boolean.FALSE;
		}
		return hasFeaturedSinglePages;
	}
	
	@Basic
	@Column(name = "max_featured_single_pages")
	public Integer getMaxFeaturedSinglePages()
	{
		if(maxFeaturedSinglePages == null)
		{
			this.maxFeaturedSinglePages = 0;
		}
		return maxFeaturedSinglePages;
	}
	
	@Basic
	@Column(name = "has_featured_pages")
	public Boolean getHasFeaturedPages()
	{
		if(hasFeaturedPages == null)
		{
			this.hasFeaturedPages = Boolean.FALSE;
		}
		return hasFeaturedPages;
	}
	
	@Basic
	@Column(name = "max_featured_pages")
	public Integer getMaxFeaturedPages()
	{
		if(maxFeaturedPages == null)
		{
			this.maxFeaturedPages = 3;
		}
		return maxFeaturedPages;
	}
	
	@Basic
	@Column(name = "has_featured_products")
	public Boolean getHasFeaturedProducts()
	{
		if(hasFeaturedProducts == null)
		{
			this.hasFeaturedProducts = Boolean.FALSE;
		}
		return hasFeaturedProducts;
	}
	
	@Basic
	@Column(name = "max_featured_products")
	public Integer getMaxFeaturedProducts()
	{
		if(maxFeaturedProducts == null)
		{
			this.maxFeaturedProducts = 3;
		}
		return maxFeaturedProducts;
	}
	
	@Basic
	@Column(name = "has_products")
	public Boolean getHasProducts()
	{
		if(hasProducts == null)
		{
			this.hasProducts = Boolean.FALSE;
		}
		return hasProducts;
	}
	
	@Basic
	@Column(name = "products_per_page")
	public Integer getProductsPerPage()
	{
		if(productsPerPage == null)
		{
			this.productsPerPage = 10;
		}
		return productsPerPage;
	}
	
	@Basic
	@Column(name = "has_packages")
	public Boolean getHasPackages()
	{
		if(hasPackages == null)
		{
			this.hasPackages = Boolean.FALSE;
		}
		return hasPackages;
	}
	
	@Basic
	@Column(name = "packages_per_page")
	public Integer getPackagesPerPage()
	{
		if(packagesPerPage == null)
		{
			this.packagesPerPage = 10;
		}
		return packagesPerPage;
	}
	
	@Basic
	@Column(name = "has_other_fields")
	public Boolean getHasOtherFields()
	{
		if(hasOtherFields == null)
		{
			this.hasOtherFields = Boolean.FALSE;
		}
		return hasOtherFields;
	}
	
	@Basic
	@Column(name = "max_other_fields")
	public Integer getMaxOtherFields()
	{
		if(maxOtherFields == null)
		{
			this.maxOtherFields = 10;
		}
		return maxOtherFields;
	}
	
	@Basic
	@Column(name = "has_category_menu")
	public Boolean getHasCategoryMenu()
	{
		if(hasCategoryMenu == null)
		{
			this.hasCategoryMenu = Boolean.FALSE;
		}
		return hasCategoryMenu;
	}
	
	@Basic
	@Column(name = "max_category_menu")
	public Integer getMaxCategoryMenu()
	{
		if(maxCategoryMenu == null)
		{
			this.maxCategoryMenu = 10;
		}
		return maxCategoryMenu;
	}
	
	@Basic
	@Column(name = "image1_width")
	public Integer getImage1Width()
	{
		if(image1Width == null)
		{
			this.image1Width = 500;
		}
		return image1Width;
	}
	
	@Basic
	@Column(name = "image1_heigth")
	public Integer getImage1Heigth()
	{
		if(image1Heigth == null)
		{
			this.image1Heigth = 500;
		}
		return image1Heigth;
	}
	
	@Basic
	@Column(name = "image2_width")
	public Integer getImage2Width()
	{
		if(image2Width == null)
		{
			this.image2Width = 300;
		}
		return image2Width;
	}
	
	@Basic
	@Column(name = "image2_heigth")
	public Integer getImage2Heigth()
	{
		if(image2Heigth == null)
		{
			this.image2Heigth = 300;
		}
		return image2Heigth;
	}
	
	@Basic
	@Column(name = "image3_width")
	public Integer getImage3Width()
	{
		if(image3Width == null)
		{
			this.image3Width = 160;
		}
		return image3Width;
	}
	
	@Basic
	@Column(name = "image3_heigth")
	public Integer getImage3Heigth()
	{
		if(image3Heigth == null)
		{
			this.image3Heigth = 160;
		}
		return image3Heigth;
	}
	
	@Basic
	@Column(name = "image1_forced")
	public Boolean getImage1Forced()
	{
		if(image1Forced == null)
		{
			this.image1Forced = Boolean.FALSE;
		}
		return image1Forced;
	}
	
	@Basic
	@Column(name = "image2_forced")
	public Boolean getImage2Forced()
	{
		if(image2Forced == null)
		{
			this.image2Forced = Boolean.FALSE;
		}
		return image2Forced;
	}
	
	@Basic
	@Column(name = "image3_forced")
	public Boolean getImage3Forced()
	{
		if(image3Forced == null)
		{
			this.image3Forced = Boolean.FALSE;
		}
		return image3Forced;
	}
	
	@Basic
	@Column(name = "email_user_name")
	public String getEmailUserName()
	{
		return emailUserName;
	}
	
	@Basic
	@Column(name = "email_password")
	public String getEmailPassword()
	{
		return emailPassword;
	}
	
	@Basic
	@Column(name = "smtp")
	public String getSmtp()
	{
		return smtp;
	}
	
	@Basic
	@Column(name = "port_number")
	public String getPortNumber()
	{
		return portNumber;
	}
	
	@Basic
	@Column(name = "auto_answer")
	public Boolean getAutoAnswer()
	{
		if(autoAnswer == null)
		{
			this.autoAnswer = Boolean.FALSE;
		}
		return autoAnswer;
	}
	
	@Basic
	@Column(name = "subject")
	public String getSubject()
	{
		return subject;
	}
	
	@Basic
	@Column(name = "message")
	public String getMessage()
	{
		return message;
	}
	
	@Basic
	@Column(name = "max_allowed_images")
	public Integer getMaxAllowedImages()
	{
		if(maxAllowedImages == null)
		{
			this.maxAllowedImages = 5;
		}
		return maxAllowedImages;
	}
	
	@Basic
	@Column(name = "max_allowed_files")
	public Integer getMaxAllowedFiles()
	{
		if(maxAllowedFiles == null)
		{
			this.maxAllowedFiles = 5;
		}
		return maxAllowedFiles;
	}
	
	@Basic
	@Column(name = "max_file_size")
	public Long getMaxFileSize()
	{
		if(maxFileSize == null)
		{
			this.maxFileSize = 5L; // 5 Mb
		}
		return maxFileSize;
	}
	
	@Basic
	@Column(name = "monthly_charge")
	public Double getMonthlyCharge()
	{
		if(monthlyCharge == null)
		{
			this.monthlyCharge = 0.0D;
		}
		return monthlyCharge;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name = "online_date")
	public Date getOnlineDate()
	{
		if(onlineDate == null)
		{
			this.onlineDate = new Date();
		}
		return onlineDate;
	}
	
	@Basic
	@Column(name = "will_expire")
	public Boolean getWillExpire()
	{
		if(willExpire == null)
		{
			this.willExpire = Boolean.FALSE;
		}
		return willExpire;
	}
	
	@Basic
	@Column(name = "expiry_date")
	public Integer getExpiryDate()
	{
		if(expiryDate == null)
		{
			this.expiryDate = 7;
		}
		return expiryDate;
	}
	
	@Basic
	@Column(name = "limit_downloads")
	public Boolean getLimitDownloads()
	{
		if(limitDownloads == null)
		{
			this.limitDownloads = Boolean.FALSE;
		}
		return limitDownloads;
	}
	
	@Basic
	@Column(name = "downloads")
	public Integer getDownloads()
	{
		if(downloads == null)
		{
			this.downloads = 1;
		}
		return downloads;
	}
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="company_status")
	public CompanyStatusEnum getCompanyStatus()
	{
		if(companyStatus == null)
		{
			this.companyStatus = CompanyStatusEnum.ONGOING;
		}
		return companyStatus;
	}
	
	@Basic
	@Column(name = "show_brands")
	public Boolean getShowBrands()
	{
		if(showBrands == null)
		{
			this.showBrands = Boolean.FALSE;
		}
		return showBrands;
	}
	
	@Basic
	@Column(name = "has_event_calendar")
	public Boolean getHasEventCalendar()
	{
		if(hasEventCalendar == null)
		{
			this.hasEventCalendar = Boolean.FALSE;
		}
		return hasEventCalendar;
	}
	
	@Basic
	@Column(name = "has_reapeating_event")
	public Boolean getHasRepeatingEvent()
	{
		if(hasRepeatingEvent == null)
		{
			this.hasRepeatingEvent = Boolean.FALSE;
		}
		return hasRepeatingEvent;
	}
	
	@Basic
	@Column(name = "suspended")
	public Boolean getSuspended()
	{
		if(suspended == null)
		{
			this.suspended = Boolean.FALSE;
		}
		return suspended;
	}
	
	@Basic
	@Column(name = "has_member_feature")
	public Boolean getHasMemberFeature()
	{
		if(hasMemberFeature == null)
		{
			this.hasMemberFeature = Boolean.FALSE;
		}
		return hasMemberFeature;
	}
	
	@Basic
	@Column(name = "shipping_type")
	public String getShippingType()
	{
		return shippingType;
	}
	
	@Basic
	@Column(name = "has_user_rights")
	public Boolean getHasUserRights()
	{
		if(hasUserRights == null)
		{
			this.hasUserRights = Boolean.FALSE;
		}
		return hasUserRights;
	}
	
	@Basic
	@Column(name = "has_comments")
	public Boolean getHasComments()
	{
		if(hasComments == null)
		{
			this.hasComments = Boolean.FALSE;
		}
		return hasComments;
	}
	
	@Basic
	@Column(name = "has_referrals")
	public Boolean getHasReferrals()
	{
		if(hasReferrals == null)
		{
			this.hasReferrals = Boolean.FALSE;
		}
		return hasReferrals;
	}
	
	@Basic
	@Column(name = "has_page_file_rights")
	public Boolean getHasPageFileRights()
	{
		if(hasPageFileRights == null)
		{
			this.hasPageFileRights = Boolean.FALSE;
		}
		return hasPageFileRights;
	}
	
	@Basic
	@Column(name = "has_testimonials")
	public Boolean getHasTestimonials()
	{
		if(hasTestimonials == null)
		{
			this.hasTestimonials = Boolean.FALSE;
		}
		return hasTestimonials;
	}
	
	@Basic
	@Column(name = "has_best_sellers")
	public Boolean getHasBestSellers()
	{
		if(hasBestSellers == null)
		{
			this.hasBestSellers = Boolean.FALSE;
		}
		return hasBestSellers;
	}
	
	@Basic
	@Column(name = "max_best_sellers")
	public Integer getMaxBestSellers()
	{
		if(maxBestSellers == null)
		{
			this.maxBestSellers = 0;
		}
		return maxBestSellers;
	}
	
	@Basic
	@Column(name = "has_promo_code")
	public Boolean getHasPromoCode()
	{
		if(hasPromoCode == null)
		{
			this.hasPromoCode = Boolean.FALSE;
		}
		return hasPromoCode;
	}
	
	@Basic
	@Column(name = "promo_code")
	public String getPromoCode()
	{
		return promoCode;
	}
	
	@Basic
	@Column(name = "has_mobile_site")
	public Boolean getHasMobileSite()
	{
		if(hasMobileSite == null)
		{
			this.hasMobileSite = Boolean.FALSE;
		}
		return hasMobileSite;
	}
	
	@Basic
	@Column(name = "account_expiry_date")
	public String getAccountExpiryDate()
	{
		return accountExpiryDate;
	}
	
	// SETTERS
	
	public void setAccountExpiryDate(String accountExpiryDate)
	{
		this.accountExpiryDate = accountExpiryDate;
	}
	
	public void setDoNotGenerateBilling(Boolean DoNotGenerateBilling)
	{
		this.DoNotGenerateBilling = DoNotGenerateBilling;
	}
	
	public void setHasWishlist(Boolean hasWishlist)
	{
		this.hasWishlist = hasWishlist;
	}
	
	public void setCanBatchUpdateExcelFiles(Boolean canBatchUpdateExcelFiles)
	{
		this.canBatchUpdateExcelFiles = canBatchUpdateExcelFiles;
	}
	
	public void setHasOrder(Boolean hasOrder)
	{
		this.hasOrder = hasOrder;
	}
	
	public void setHasPalAccount(Boolean hasPalAccount)
	{
		this.hasPalAccount = hasPalAccount;
	}
	
	public void setManyAttachments(Boolean manyAttachments)
	{
		this.manyAttachments = manyAttachments;
	}
	
	public void setHasProductInventory(Boolean hasProductInventory)
	{
		this.hasProductInventory = hasProductInventory;
	}
	
	public void setHasMetaPage(Boolean hasMetaPage)
	{
		this.hasMetaPage = hasMetaPage;
	}
	
	public void setHasMetaProduct(Boolean hasMetaProduct)
	{
		this.hasMetaProduct = hasMetaProduct;
	}
	
	public void setHasClientChat(Boolean hasClientChat)
	{
		this.hasClientChat = hasClientChat;
	}
	
	public void setHasMemberFiles(Boolean hasMemberFiles)
	{
		this.hasMemberFiles = hasMemberFiles;
	}
	
	public void setHasBulletinFeature(Boolean hasBulletinFeature)
	{
		this.hasBulletinFeature = hasBulletinFeature;
	}
	
	public void setHasFeaturedSinglePages(Boolean hasFeaturedSinglePages)
	{
		this.hasFeaturedSinglePages = hasFeaturedSinglePages;
	}
	
	public void setMaxFeaturedSinglePages(Integer maxFeaturedSinglePages)
	{
		this.maxFeaturedSinglePages = maxFeaturedSinglePages;
	}
	
	public void setHasFeaturedPages(Boolean hasFeaturedPages)
	{
		this.hasFeaturedPages = hasFeaturedPages;
	}
	
	public void setMaxFeaturedPages(Integer maxFeaturedPages)
	{
		this.maxFeaturedPages = maxFeaturedPages;
	}
	
	public void setHasFeaturedProducts(Boolean hasFeaturedProducts)
	{
		this.hasFeaturedProducts = hasFeaturedProducts;
	}
	
	public void setMaxFeaturedProducts(Integer maxFeaturedProducts)
	{
		this.maxFeaturedProducts = maxFeaturedProducts;
	}
	
	public void setHasProducts(Boolean hasProducts)
	{
		this.hasProducts = hasProducts;
	}
	
	public void setProductsPerPage(Integer productsPerPage)
	{
		this.productsPerPage = productsPerPage;
	}
	
	public void setHasPackages(Boolean hasPackages)
	{
		this.hasPackages = hasPackages;
	}
	
	public void setPackagesPerPage(Integer packagesPerPage)
	{
		this.packagesPerPage = packagesPerPage;
	}
	
	public void setHasOtherFields(Boolean hasOtherFields)
	{
		this.hasOtherFields = hasOtherFields;
	}
	
	public void setMaxOtherFields(Integer maxOtherFields)
	{
		this.maxOtherFields = maxOtherFields;
	}
	
	public void setHasCategoryMenu(Boolean hasCategoryMenu)
	{
		this.hasCategoryMenu = hasCategoryMenu;
	}
	
	public void setMaxCategoryMenu(Integer maxCategoryMenu)
	{
		this.maxCategoryMenu = maxCategoryMenu;
	}
	
	public void setImage1Width(Integer image1Width)
	{
		this.image1Width = image1Width;
	}
	
	public void setImage1Heigth(Integer image1Heigth)
	{
		this.image1Heigth = image1Heigth;
	}
	
	public void setImage2Width(Integer image2Width)
	{
		this.image2Width = image2Width;
	}
	
	public void setImage2Heigth(Integer image2Heigth)
	{
		this.image2Heigth = image2Heigth;
	}
	
	public void setImage3Width(Integer image3Width)
	{
		this.image3Width = image3Width;
	}
	
	public void setImage3Heigth(Integer image3Heigth)
	{
		this.image3Heigth = image3Heigth;
	}
	
	public void setImage1Forced(Boolean image1Forced)
	{
		this.image1Forced = image1Forced;
	}
	
	public void setImage2Forced(Boolean image2Forced)
	{
		this.image2Forced = image2Forced;
	}
	
	public void setImage3Forced(Boolean image3Forced)
	{
		this.image3Forced = image3Forced;
	}
	
	public void setEmailUserName(String emailUserName)
	{
		this.emailUserName = emailUserName;
	}
	
	public void setEmailPassword(String emailPassword)
	{
		this.emailPassword = emailPassword;
	}
	
	public void setSmtp(String smtp)
	{
		this.smtp = smtp;
	}
	
	public void setPortNumber(String portNumber)
	{
		this.portNumber = portNumber;
	}
	
	public void setAutoAnswer(Boolean autoAnswer)
	{
		this.autoAnswer = autoAnswer;
	}
	
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public void setMaxAllowedImages(Integer maxAllowedImages)
	{
		this.maxAllowedImages = maxAllowedImages;
	}
	
	public void setMaxAllowedFiles(Integer maxAllowedFiles)
	{
		this.maxAllowedFiles = maxAllowedFiles;
	}
	
	public void setMaxFileSize(Long maxFileSize)
	{
		this.maxFileSize = maxFileSize;
	}
	
	public void setMonthlyCharge(Double monthlyCharge)
	{
		this.monthlyCharge = monthlyCharge;
	}
	
	public void setOnlineDate(Date onlineDate)
	{
		this.onlineDate = onlineDate;
	}
	
	public void setWillExpire(Boolean willExpire)
	{
		this.willExpire = willExpire;
	}
	
	public void setExpiryDate(Integer expiryDate)
	{
		this.expiryDate = expiryDate;
	}
	
	public void setLimitDownloads(Boolean limitDownloads)
	{
		this.limitDownloads = limitDownloads;
	}
	
	public void setDownloads(Integer downloads)
	{
		this.downloads = downloads;
	}
	
	public void setCompanyStatus(CompanyStatusEnum companyStatus)
	{
		this.companyStatus = companyStatus;
	}
	
	public void setShowBrands(Boolean showBrands)
	{
		this.showBrands = showBrands;
	}
	
	public void setHasEventCalendar(Boolean hasEventCalendar)
	{
		this.hasEventCalendar = hasEventCalendar;
	}
	
	public void setSuspended(Boolean suspended)
	{
		this.suspended = suspended;
	}
	
	public void setHasMemberFeature(Boolean hasMemberFeature)
	{
		this.hasMemberFeature = hasMemberFeature;
	}
	
	public void setShippingType(String shippingType)
	{
		this.shippingType = shippingType;
	}
	
	public void setHasUserRights(Boolean hasUserRights)
	{
		this.hasUserRights = hasUserRights;
	}
	
	public void setHasComments(Boolean hasComments)
	{
		this.hasComments = hasComments;
	}
	
	public void setHasReferrals(Boolean hasReferrals)
	{
		this.hasReferrals = hasReferrals;
	}
	
	public void setHasPageFileRights(Boolean hasPageFileRights)
	{
		this.hasPageFileRights = hasPageFileRights;
	}
	
	public void setHasTestimonials(Boolean hasTestimonials)
	{
		this.hasTestimonials = hasTestimonials;
	}
	
	public void setHasBestSellers(Boolean hasBestSellers)
	{
		this.hasBestSellers = hasBestSellers;
	}
	
	public void setMaxBestSellers(Integer maxBestSellers)
	{
		this.maxBestSellers = maxBestSellers;
	}
	
	public void setHasPromoCode(Boolean hasPromoCode)
	{
		this.hasPromoCode = hasPromoCode;
	}
	
	public void setPromoCode(String promoCode)
	{
		this.promoCode = promoCode;
	}
	
	public void setHasMobileSite(Boolean hasMobileSite)
	{
		this.hasMobileSite = hasMobileSite;
	}
	public void setHasRepeatingEvent(Boolean hasRepeatingEvent)
	{
		this.hasRepeatingEvent = hasRepeatingEvent;
	}
	
}
