package com.ivant.cms.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.ServletActionContext;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.enums.MonthEnum;
import com.ivant.cms.enums.PageType;
import com.ivant.cms.interceptors.FrontCompanyInterceptor;
import com.ivant.cms.interceptors.UserInterceptor;
import com.ivant.cms.wrapper.Archive;
import com.ivant.cms.wrapper.ArchiveMonth;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.HTMLTagStripper;

@Entity(name = "MultiPage")
@Table(name = "multi_page")
public class MultiPage extends AbstractPage {
 
	private String description;
	private List<SinglePage> items;
	private List<SinglePage> itemsFeatured;
	private List<SinglePage> sortedItems;
	private List<SinglePage> descendingItems;
	private boolean featured;
	private boolean hasFile = false;
	private List<PageFile> files;
	private int itemsPerPage;
	private MultiPage parentMultiPage;
	private List<MultiPage> childrenMultiPage;
	private List<SinglePage> yearList;
	private List<MultiPageImage> images;

	private  List<MultiPageLanguage> multiPageLanguageList;
	private List<MultiPageFile> multiPageFiles;
	private transient MemberType memberType;
	private transient List<Archive> archives;
	
	private Boolean hasPublicationDate = false;

	@Transient
	public List<Archive> getArchives() {

	//ADDED-START-ARCHIVE
	archives = new ArrayList<Archive>();

	int maxYear = 0;
	int minYear = 9999;

	for(SinglePage s: getItems()) {
	int year = s.getCreatedOn().getYear()+1900;

	if(year > maxYear) maxYear = year;
	else if (year < minYear) minYear = year;

	}

	for(int i = maxYear;i>=minYear;i--) {
	Archive a = new Archive();

	a.setYear(i);

	ArrayList<ArchiveMonth> monthArchives = new ArrayList<ArchiveMonth>();

	for(MonthEnum month: MonthEnum.values()) {
	ArchiveMonth am = new ArchiveMonth();


	ArrayList<SinglePage> pageMonthList = new ArrayList<SinglePage>();

	for(SinglePage s: getItems()) {
	if(s.getCreatedOn().getYear()+1900 == i && s.getCreatedOn().getMonth() == month.getMonthNum()-1)
	pageMonthList.add(s);
	}

	if(pageMonthList.size() > 0) {
	am.setMonthName(month.getMonthName());
	am.setPages(pageMonthList);
	monthArchives.add(am);
	}


	}
	a.setMonths(monthArchives);
	if(monthArchives.size() > 0) {
	archives.add(a);

	}
	}

	return archives;

	}

	@Transient
	public String getTitle() {
		if(getMultiPageLanguageList()!=null && language!=null)
			for(MultiPageLanguage temp :getMultiPageLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp.getTitle();
			}
		return title;
	}


	 
	@Transient
	public String getName() {
		if(getMultiPageLanguageList()!=null && language!=null)
			for(MultiPageLanguage temp :getMultiPageLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp.getName();
			}
		return name;
	}
	
	@Transient
	public MultiPageLanguage getMultiPageLanguage(){
		if(getMultiPageLanguageList()!=null && language!=null)
			for(MultiPageLanguage temp :getMultiPageLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp;
			}
		return null;
	}
	

	
	@OneToMany(targetEntity = MultiPageLanguage.class, mappedBy = "defaultPage", fetch = FetchType.LAZY)
	public List<MultiPageLanguage> getMultiPageLanguageList(){
		return multiPageLanguageList;
	}

	public void setMultiPageLanguageList(List<MultiPageLanguage> multiPageLanguageList){
		this.multiPageLanguageList = multiPageLanguageList;
	}
	

	

	
	@Transient
	public List<SinglePage> getYearList() {
		return yearList;
	}

	public void setYearList(List<SinglePage> yearList) {
		this.yearList = yearList;
	}
	

	//settings related to news/archive
	private boolean hasArchive;
	private int archiveDays;

	
	@Basic
	@Column(name="has_archive")
	public boolean getHasArchive() {
		return hasArchive;
	}

	public void setHasArchive(boolean hasArchive) {
		this.hasArchive = hasArchive;
	}

	@Basic
	@Column(name="archive_days")
	public int getArchiveDays() {
		return archiveDays;
	}

	public void setArchiveDays(int archiveDays) {
		this.archiveDays = archiveDays;
	}

	public MultiPage() {
		itemsPerPage = 10;
		featured = false;
	}
	
	@ManyToOne(targetEntity = MultiPage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_multipage")
	@NotFound(action = NotFoundAction.IGNORE) 
	public MultiPage getParentMultiPage() {
		return parentMultiPage;
	}

	public void setParentMultiPage(MultiPage parent) {
		this.parentMultiPage = parent;
	}
	
	@OneToMany(targetEntity = MultiPage.class, mappedBy = "childrenMultiPage", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	public List<MultiPage> getChildrenMultiPage(){
	return childrenMultiPage;
	}

	public void setChildrenMultiPage(List<MultiPage> c){
		childrenMultiPage = c;
	}

	@Basic
	@Column(name="has_file")
	public boolean getHasFile() {
		return hasFile;
	}

	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}

	@OneToMany(targetEntity = PageFile.class, mappedBy = "page", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")
	public List<PageFile> getFiles() { 
		if(Boolean.TRUE.equals(getCompany().getCompanySettings().getHasPageFileRights()) && memberType != null){
			List<PageFile> fileResult = new ArrayList<PageFile>();
			
			for(PageFile pageFile : files) {
				if(Boolean.TRUE.equals(pageFile.getMemberTypeAccess().get(memberType.getId()))) {
					fileResult.add(pageFile);
				}
			}
			
			return fileResult;
		} 
		return files;
	}

	public void setFiles(List<PageFile> files) {
		this.files = files;
	}
	@Basic
	@Column(name="featured", nullable=false)
	public boolean getFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
	
	@OneToMany(targetEntity = MultiPageFile.class, mappedBy = "multipage", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")
	public List<MultiPageFile> getMultiPageFiles() {
		if(Boolean.TRUE.equals(getCompany().getCompanySettings().getHasPageFileRights()) && memberType != null){
			List<MultiPageFile> fileResult = new ArrayList<MultiPageFile>();
			
			for(MultiPageFile pageFile : multiPageFiles) {
				if(Boolean.TRUE.equals(pageFile.getMemberTypeAccess().get(memberType.getId()))) {
					fileResult.add(pageFile);
				}
			}
			
			return fileResult;
		} 
		return multiPageFiles;
	}
	
	public void setMultiPageFiles(List<MultiPageFile> multiPageFiles) {
		this.multiPageFiles = multiPageFiles;
	}
	 
	@Transient
	public List<SinglePage> getItems() { 
//		if(items == null) {
			User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
			Company company = null;
			
			if(user == null) {
				company = this.getCompany();
			}
			else {
				company = user.getCompany();
			}
			
			SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
			if(company.getId() == CompanyConstants.NKTI)
			{
				//return singlePageDelegate.findAll(company, this, Order.desc("itemDate")).getList();
				return singlePageDelegate.findAllPublished(company, this, new Order[] {Order.desc("id")}).getList();
			}
			return singlePageDelegate.findAllPublished(company, this, Order.asc("sortOrder")).getList();
//		}
//		else {
//			return items;
//		} 
	}
	
	@Transient
	public List<SinglePage> getItemsWithExpired() { 
		User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = null;
			
		if(user == null) {
			company = this.getCompany();
		}
		else {
			company = user.getCompany();
		}
	
		SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	
		return singlePageDelegate.findAllPublishedWithExpired(company, this, Order.asc("sortOrder")).getList();
	}	
	
	@Transient
	public List<SinglePage> getItemsFeatured() { 
		User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = null;
			
		if(user == null) {
			company = this.getCompany();
		}
		else {
			company = user.getCompany();
		}
	
		SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();

		return singlePageDelegate.findAllPublishedFeatured(company, this, Order.asc("sortOrder")).getList();
	}	
	 
	@Transient 
	public List<SinglePage> findSortedItems() { 

			User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
			Company company = null;
			
			if(user == null) {
				company = this.getCompany();
			}
			else {
				company = user.getCompany();
			}
	
			SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
		
			return singlePageDelegate.findAllPublished(company, this, Order.asc("sortOrder")).getList();
		

	}
	
	
	public void setItems(List<SinglePage> items) {
		this.items = items;
	}
	
	public void setItemsFeatured(List<SinglePage> itemsFeatured) {
		this.itemsFeatured = itemsFeatured;
	}	
	
	@Transient
	public List<SinglePage> getActiveItems() {
		return null;
	}
	
	@Transient
	public List<SinglePage> getLatestItems() {
		User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = null;
		
		if(user == null) {
			company = (Company) ServletActionContext.getRequest().getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY);
		}
		else {
			company = user.getCompany();
		}
		
		CompanySettings companySettings = company.getCompanySettings();
		
		SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
		return singlePageDelegate.findAllPublishedWithPaging(company, this, companySettings.getMaxFeaturedPages(), 0, Order.desc("createdOn")).getList();
	}
	
		
	@Basic
	@Column(name = "description", length=5000)
	public String getDescription() {
		if(getMultiPageLanguageList()!=null && language!=null)
			for(MultiPageLanguage temp :getMultiPageLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp.getDescription();
			}
		
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Basic
	@Column(name="items_per_page", nullable=false)
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	
	
	@Transient
	public List<SinglePage> getAllItems() {
		return this.items;
	}
	
	
	@Transient
	public List<SinglePage> getArchiveItems() {
		User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = null;
		
		if(user == null) {
			company = (Company) ServletActionContext.getRequest().getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY);
		}
		else {
			company = user.getCompany();
		}
		
		CompanySettings companySettings = company.getCompanySettings();
		
		SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
		return singlePageDelegate.findAllArchiveWithPaging(company, this, companySettings.getMaxFeaturedPages(), 0, Order.desc("updatedOn")).getList();
	}
	
	@Override 
	public String toString() {
		return  "id: " + getId() + "\n" +
				"name: " +getName() + "\n" +
				"company: " + ((getCompany() != null) ? getCompany().getName() : "null") + "\n" +
				"created by: " + ((getCreatedBy() != null) ? getCreatedBy().getUsername() : "null") + "\n" +
				"valid from: " + getValidFrom() + "\n" +
				"valid to: " + getValidTo() + "\n";
	}	
	
//-----------------------------------------------------------------	
	@Transient
	public void getCurrentItems(int nodays) {
		User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = null;
		
		if(user == null) {
			company = (Company) ServletActionContext.getRequest().getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY);
		}
		else {
			company = user.getCompany();
		}
		
		CompanySettings companySettings = company.getCompanySettings();
		
		SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
		List<SinglePage> singlePageList = singlePageDelegate.findAllCurrent(company, this, nodays, Order.desc("createdOn")).getList();
		this.setItems(singlePageList);
		//System.out.println("this.sizemethod: " + this.getItems().size());
		
	}
	
	
	@Transient
	public void getPastItems(int nodays) {
		User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = null;
		
		if(user == null) {
			company = (Company) ServletActionContext.getRequest().getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY);
		}
		else {
			company = user.getCompany();
		}
		
		CompanySettings companySettings = company.getCompanySettings();
		
		SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
		List<SinglePage> singlePageList = singlePageDelegate.findAllPast(company, this, nodays, Order.desc("createdOn")).getList();
		this.setItems(singlePageList);		
		
		//return singlePageDelegate.findAllPast(company, this, nodays, Order.desc("updatedOn")).getList();
	}
	
//-----------------------------------------------------------------
	
	@Transient
	public List<SinglePage> findDescendingItems() {
		User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = null;
		
		if(user == null) {
			company = (Company) ServletActionContext.getRequest().getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY);
		}
		else {
			company = user.getCompany();
		}
		
		CompanySettings companySettings = company.getCompanySettings();
		
		SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
		List<SinglePage> singlePageList = singlePageDelegate.findDescendingItems(company, this, Order.desc("createdOn")).getList();
		return singlePageList;
		//this.setItems(singlePageList);
		
	}
//----------------------------------------------------------------
	
	@Transient
	public List<SinglePage> getSortOrderItems() {
		User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = null;
		
		if(user == null) {
			company = (Company) ServletActionContext.getRequest().getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY);
		}
		else {
			company = user.getCompany();
		}
		
		CompanySettings companySettings = company.getCompanySettings();
		
		SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
		List<SinglePage> singlePageList = singlePageDelegate.findDescendingItems(company, this, Order.asc("sortOrder")).getList();
		return singlePageList;
		//this.setItems(singlePageList);
		
	}
	
	
	@Transient
	public void getYearItems(int year) {
		User user = (User) ServletActionContext.getRequest().getAttribute(UserInterceptor.USER_REQUEST_KEY);
		Company company = null;
		
		if(user == null) {
			company = (Company) ServletActionContext.getRequest().getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY);
		}
		else {
			company = user.getCompany();
		}
		
		CompanySettings companySettings = company.getCompanySettings();
		
		SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
		List<SinglePage> singlePageList = singlePageDelegate.findYearItems(company, this, year, Order.desc("createdOn")).getList();
		this.setYearList(singlePageList);
		return;
		//System.out.println("this.sizemethod: " + this.getItems().size());
		
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
//-----------------------------------------------------------------	
	
	
	public MultiPage findRootMultiPage(MultiPage multiPage)
	{
		if (multiPage.getParentMultiPage()==null)
				return multiPage;
		else
				return findRootMultiPage(multiPage.getParentMultiPage());
		
	}
	
	

	@Transient
	public List<MultiPage> getParentMultiPages(MultiPage multiPage)
	{   
		
		List<MultiPage> list = new LinkedList<MultiPage>();
		while (multiPage.getParentMultiPage()!=null) {
				multiPage = multiPage.getParentMultiPage();
				list.add(multiPage);
		}
		Collections.reverse(list);
		return list;
	}
	
	@Transient
	public List<MultiPage> getDesMultiPages()
	{   
		List<MultiPage> children = getDesMultiPages();
		if(children == null) {
			return null;
		}
		
		List<MultiPage> allPages = new LinkedList<MultiPage>();
		for (MultiPage child : children) {
			List<MultiPage> c = child.getDesMultiPages();
			if (c!=null)
				allPages.addAll(c);
		}
		
		return allPages;
	}

	@Transient
	public List<SinglePage> getDescendingItems() {
		descendingItems= findDescendingItems();
		return descendingItems;
	}

	public void setDescendingItems(List<SinglePage> descendingItems) {
		this.descendingItems = descendingItems;
	}
	
	@Transient
	public List<SinglePage> getSortedItems() {
		sortedItems= findSortedItems();
		return sortedItems;
	}

	public void setSortedItems(List<SinglePage> sortedItems) {
		this.sortedItems = sortedItems;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<MultiPageImage> images) {
		this.images = images;
	}

	@OneToMany(targetEntity = MultiPageImage.class, mappedBy = "multipage", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")
	public List<MultiPageImage> getImages() {
		return images;
	}

	@Transient
	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

	@Transient
	public MemberType getMemberType() {
		return memberType;
	}
	
	@Transient
	@Override
	public PageType getPageType()
	{
		return PageType.MULTIPAGE;
	}
	
	@Transient
	public List<SinglePage> getItemsInRandom()
	{
		List<SinglePage> items = getItems();
		Collections.shuffle(items);
		return items;
	}
	
	@Transient
	public String getDescriptionWithoutTags()
	{
		return HTMLTagStripper.stripTags(getDescription()); 
	}

	public void setHasPublicationDate(Boolean hasPublicationDate) {
		this.hasPublicationDate = hasPublicationDate;
	}

	@Basic 
	@Column(name = "has_publication_date")
	public Boolean getHasPublicationDate() {
		if(this.hasPublicationDate == null) {
			hasPublicationDate = false;
		}
		return hasPublicationDate;
	}

	
}
