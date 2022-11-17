package com.ivant.cms.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.json.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.ivant.cms.enums.PageType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.wrapper.TagCloudGenerator;
import com.ivant.utils.HTMLTagStripper;



@Entity(name = "SinglePage")
@Table(name = "single_page")
@PrimaryKeyJoinColumn(name = "page_id")
public class SinglePage extends BasePage implements Cloneable, CompanyAware, JSONAware {

	private String subTitle;
	private String content;
	private MultiPage parent;
	private boolean isHome = false;
	private boolean isForm = false;
	private boolean isSingleFeatured = false;
	private boolean hasFile = false;
	private List<PageFile> files;
	private List<MultiPageFile> multiPageFiles;
	private Date itemDate;
	private transient List<MultiPageFile> mySortedFiles;
	private  List<MultiPageFile> multiPageFiles2;
	private  List<SinglePageLanguage> singlePageLanguageList;
	private String startTime;
	private String endTime;
	private String daysAvailable;
	private String docRoom;
	private transient int imageCount;
	private transient int fileCount;
	private transient MemberType memberType;
	private transient ArrayList<Map.Entry<String,Integer>> tagCloud;
	
	private String previewContent;
	private Date datePublished;
	
	private Boolean flag1;
	private Boolean flag2;

	public String getPreviewContent() {
		return previewContent;
	}

	public void setPreviewContent(String previewContent) {
		this.previewContent = previewContent;
	}

	@Transient
	public ArrayList<Map.Entry<String,Integer>> getTagCloud() {
		TagCloudGenerator gen = new TagCloudGenerator(getContent(),10,20,12);
		
		tagCloud = gen.returnTagCloud();
		return tagCloud;
	}
	
	@Transient
	public int getImageCount() {
		imageCount = 0;
				if(getImages() != null)
				{
					imageCount += getImages().size();
				}
			
		return imageCount;
	}
	private String docSchool;

	@Transient
	public int getFileCount() {
		fileCount = 0;
		
				if(getFiles() != null)
				{
					fileCount += getFiles().size();
				}
			
		
		return fileCount;
	}


	@OneToMany(targetEntity = SinglePageLanguage.class, mappedBy = "defaultPage", fetch = FetchType.LAZY)
	public List<SinglePageLanguage> getSinglePageLanguageList(){
		return singlePageLanguageList;
	}

	public void setSinglePageLanguageList(List<SinglePageLanguage> singlePageLanguageList){
		this.singlePageLanguageList = singlePageLanguageList;
	}
	
	//TODO: asdasd
	@Transient
	public SinglePageLanguage getSinglePageLanguage(){
		if(getSinglePageLanguageList()!=null && language!=null)
			for(SinglePageLanguage temp :getSinglePageLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp;
			}
		return null;
	}
	
	//TODO: asdasd
	@Transient
	public String getTitle() {
		if(getSinglePageLanguageList()!=null && language!=null)
			for(SinglePageLanguage temp :getSinglePageLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp.getTitle();
			}
		return title;
	}

	//TODO: asdasd
	@Transient
	public String getName() {
		if(getSinglePageLanguageList()!=null && language!=null)
			for(SinglePageLanguage temp :getSinglePageLanguageList()){
				if(temp.getLanguage().equals(this.language))
					return temp.getName();
			}
		return name;
	}
	

	
	
//	@Basic
//	@Column(name = "meta_description", nullable = true)
//	public String getmeta_description() {
//		return meta_description;
//	}
//	
//	public void setmeta_description(String metaDescription) {
//		this.meta_description = meta_description;
//	}
//	
//	@Basic
//	@Column(name = "meta_keywords", nullable = true)
//	public String getmeta_keywords() {
//		return meta_keywords;
//	}
//
//	public void setmeta_keywords(String keywords) {
//		this.meta_keywords = meta_keywords;
//	}
//
//	@Basic
//	@Column(name = "meta_title", length = 50, nullable = true)
//	public String getmeta_title() {
//		return meta_title;
//	}
//
//	public void setmeta_title(String title) {
//		this.meta_title = meta_title;
//	}
	

	

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
	
	@OneToMany(targetEntity = MultiPageFile.class, mappedBy = "singlepage", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")
	public List<MultiPageFile> getMultiPageFiles() {
		if(Boolean.TRUE.equals(getCompany().getCompanySettings().getHasPageFileRights()) && memberType != null){
			List<MultiPageFile> fileResult = new ArrayList<MultiPageFile>();
			//System.out.println("GETTING FILES....");
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

	@OneToMany(targetEntity = MultiPageFile.class, mappedBy = "singlepage", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("createdOn desc")
	public List<MultiPageFile> getMultiPageFiles2() {
		if(Boolean.TRUE.equals(getCompany().getCompanySettings().getHasPageFileRights()) && memberType != null){
			List<MultiPageFile> fileResult = new ArrayList<MultiPageFile>();
			//System.out.println("GETTING FILES....");
			for(MultiPageFile pageFile : multiPageFiles2) {
				if(Boolean.TRUE.equals(pageFile.getMemberTypeAccess().get(memberType.getId()))) {
					fileResult.add(pageFile);
				}
			}
			
			return fileResult;
		} 
		return multiPageFiles2;
	}
	
	public void setMultiPageFiles2(List<MultiPageFile> multiPageFiles) {
		this.multiPageFiles2 = multiPageFiles;
	}
//	
	//TODO: asdasd
	@Basic
	@Column(name="subtitle", nullable=true)
	public String getSubTitle() {
		//return other language
		if(getSinglePageLanguageList()!=null && language!=null)
		for(SinglePageLanguage temp :getSinglePageLanguageList()){
			if(temp.getLanguage().equals(this.language))
				return temp.getSubTitle();
		}
		
		return subTitle;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	//TODO: asdasd
	@Basic
	@Column(name="content", length=2147483647, nullable=false)
	public String getContent() {
		//return other language
		if(getSinglePageLanguageList()!=null && language!=null)
		for(SinglePageLanguage temp :getSinglePageLanguageList()){
			if(temp.getLanguage().equals(this.language))
				return temp.getContent();
		}
		
		//return default content (english)
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
		
	@ManyToOne(targetEntity = MultiPage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent", insertable=true, updatable=true, nullable=true)
	@NotFound(action = NotFoundAction.IGNORE) 
	public MultiPage getParent() {
		return parent;
	}
	
	public void setParent(MultiPage parent) {
		this.parent = parent;
	}
	
	@Basic
	@Column(name="is_home", nullable=false)
	public boolean getIsHome() {
		return isHome;
	}

	public void setIsHome(boolean isHome) {
		this.isHome = isHome;
	}
		
	@Override
	@Transient
	public String providePageType() {
		return "sp";
	}
	
	@Override
	@Transient
	public PageType getPageType()
	{
		return PageType.SINGLEPAGE;
	}
	
	@Transient
	public String getContentWithoutTags()
	{
		return HTMLTagStripper.stripTags(getContent()); 
	}
		
	@Basic
	@Column(name="is_form")
	public boolean getIsForm() {
		return isForm;
	}
	
	public void setIsForm(boolean isForm) {
		this.isForm = isForm;
	}
	
	@Override
	public SinglePage clone() throws RuntimeException {
		SinglePage singlePage = null;
		try {
			singlePage = (SinglePage)super.clone();
		}
		catch(CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
		return singlePage;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SinglePage) {
			SinglePage sp = (SinglePage) obj;	
			
			if(this.getId() == sp.getId()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override 
	public String toString() {
		return  "id: " + getId() + "\n" +
				"name: " +getName() + "\n" +
				"jsp: " +getJsp()+ "\n" +
				"title: " + getTitle() + "\n" +
				"sub title: " + getSubTitle() + "\n" +
				"content: " + getContent() + "\n" +
				"image: " + getImages() + "\n" +
				"is home: " + getIsHome() + "\n" +
				"company: " + ((getCompany() != null) ? getCompany().getName() : "null") + "\n" +
				"created by: " + ((getCreatedBy() != null) ? getCreatedBy().getUsername() : "null") + "\n" +
				"valid from: " + getValidFrom() + "\n" +
				"valid to: " + getValidTo() + "\n";
	}
	
	
	public void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="item_date_singlepage", nullable=true)
	public Date getItemDate() {
		return itemDate;
		
	}
	
	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_published", nullable=true)
	public Date getDatePublished() {
		return datePublished;
		
	}
	
	@Transient
	public Boolean getExpired(){
		if(itemDate==null){
			return false;
		}
		return itemDate.before(new Date());
	}

	public void setIsSingleFeatured(boolean isSingleFeatured) {
		this.isSingleFeatured = isSingleFeatured;
	}
	
	@Basic
	@Column(name="startTime", nullable=true)
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Basic
	@Column(name="endTime", nullable=true)
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Basic
	@Column(name="dayAvailability", nullable=true)
	public String getDaysAvailable() {
		return daysAvailable;
	}

	public void setDaysAvailable(String daysAvailable) {
		this.daysAvailable = daysAvailable;
	}
	
	@Basic
	@Column(name="docRoom", nullable=true)
	public String getDocRoom() {
		return docRoom;
	}

	public void setDocRoom(String docRoom) {
		this.docRoom = docRoom;
	}
	
	@Basic
	@Column(name="docSchool", nullable=true)
	public String getDocSchool() {
		return docSchool;
	}

	public void setDocSchool(String docSchool) {
		this.docSchool = docSchool;
	}


	
	
	@Basic
	@Column(name="isSingleFeatured", nullable=false)
	public boolean getIsSingleFeatured() {
		return isSingleFeatured;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transient
	public List<MultiPageFile> getSortedDateFiles()
	{
		int x=1;
		int y=0;
		List<MultiPageFile> lizt = this.getMultiPageFiles();
		for (MultiPageFile c: lizt){ 
			if(c.getCreatedOn() == null){
			//System.out.println("-----------------null" + x++);
			}
			else if(c.getCreatedOn() != null) {
				//System.out.println(c.getCreatedOn());
			}
		}
		
		//System.out.println("-------------------------------" + y);
		Collections.sort(lizt, new DateComparator());
		//for (CategoryItem c: enabledItems)  System.out.println(c.getItemDate());
		return lizt;
	}

	
	@Transient
	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}



	class DateComparator implements Comparator{

		public int compare(Object cat1, Object cat2){

		//parameter are of type Object, so we have to downcast it to MultiPageFile objects

		
			
		Date catt1 = ( (MultiPageFile) cat1).getCreatedOn();
		//System.out.println(catt1);
		if (catt1 == null)
			catt1 = new Date(1L);
		
		Date catt2 = ( (MultiPageFile) cat2).getCreatedOn();
		//System.out.println(catt2);
		if (catt2 == null)
			catt2 = new Date(1L);
		
		if( catt1.after(catt2) )

		return -1;

		else if( catt1.before(catt2) )

		return 1;

		else

		return 0;

		}
	}
	
	@Basic
	@Column(name = "flag_one")
	public Boolean getFlag1()
	{
		return flag1;
	}
	
	@Basic
	@Column(name = "flag_two")
	public Boolean getFlag2()
	{
		return flag2;
	}
	
	public void setFlag1(Boolean flag1)
	{
		this.flag1 = flag1;
	}
	
	public void setFlag2(Boolean flag2)
	{
		this.flag2 = flag2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		ArrayList<String> imgs = new ArrayList<String>();
		for(int x = 0; x < getImages().size(); x++){
			imgs.add(getImages().get(x).getOriginal().toString());
		}
		
		ArrayList<String> files = new ArrayList<String>();
		for(int y = 0; y < getMultiPageFiles().size(); y++){
			files.add(getMultiPageFiles().get(y).getFilePath().toString());
		}
		JSONObject json = new JSONObject();
		
		json.put("id", getId());
		json.put("name", getName());
		json.put("images", new JSONArray(imgs));
		json.put("files", new JSONArray(files));
		return json.toJSONString();
	}
	
}
