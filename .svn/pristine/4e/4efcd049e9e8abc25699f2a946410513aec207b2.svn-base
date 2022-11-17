package com.ivant.cms.ws.rest.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.ivant.cms.delegate.PageImageDelegate;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.PageFile;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.SinglePageLanguage;

/**
 * 
 * @author Anjerico D. Gutierrez
 * @since July 2015
 */
@XmlRootElement(name = "MultiPage")
public class SinglePageModel extends AbstractModel{
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
	private List<MultiPageFile> multiPageFiles2;
	private List<SinglePageLanguage> singlePageLanguageList;
	private String startTime;
	private String endTime;
	private String daysAvailable;
	private String docRoom;
	private transient int imageCount;
	private transient int fileCount;
	private transient MemberType memberType;
	private transient ArrayList<Map.Entry<String, Integer>> tagCloud;
	
	private String previewContent;
	private Date datePublished;
	
	private Boolean flag1;
	private Boolean flag2;
	
	private List<String> imagesUrlImage1;
	private List<String> imagesUrlImage2;
	private List<String> imagesUrlImage3;
	private List<String> imagesUrlThumbnail;
	private List<String> imagesUrlOriginal;
	
	public SinglePageModel(SinglePage singlePage) {
		if(singlePage == null){
			return;
		}
		setSubTitle(singlePage.getSubTitle());
		setContent(singlePage.getContent());
		setParent(singlePage.getParent());
		setHome(singlePage.getIsHome());
		setForm(singlePage.getIsForm());
		setSingleFeatured(singlePage.getIsSingleFeatured());
		setHasFile(singlePage.getHasFile());
		setFiles(singlePage.getFiles());
		setMultiPageFiles(singlePage.getMultiPageFiles());
		setItemDate(singlePage.getItemDate());
		//setMySortedFiles(singlePage.getSo)
		setMultiPageFiles2(singlePage.getMultiPageFiles2());
		setStartTime(singlePage.getStartTime());
		setEndTime(singlePage.getEndTime());
		setDaysAvailable(singlePage.getDaysAvailable());
		setDocRoom(singlePage.getDocRoom());
		setImageCount(singlePage.getImageCount());
		//setFileCount(singlePage.setFileCount());
		//setMemberType(singlePage.getMemberType());
		setTagCloud(singlePage.getTagCloud());
		setPreviewContent(singlePage.getPreviewContent());
		setDatePublished(singlePage.getDatePublished());
		setFlag1(singlePage.getFlag1());
		setFlag2(singlePage.getFlag2());
		
		/** Page Images **/
		imagesUrlImage1 = new ArrayList<String>();
		imagesUrlImage2 = new ArrayList<String>();
		imagesUrlImage3 = new ArrayList<String>();
		imagesUrlThumbnail = new ArrayList<String>();
		imagesUrlOriginal = new ArrayList<String>();
		List<PageImage> singlePageImages = singlePage.getImages();
		if(singlePageImages != null){
			for(PageImage singlePageImage : singlePageImages){
				imagesUrlImage1.add(singlePageImage.getImage1());
				imagesUrlImage2.add(singlePageImage.getImage2());
				imagesUrlImage3.add(singlePageImage.getImage3());
				imagesUrlThumbnail.add(singlePageImage.getThumbnail());
				imagesUrlOriginal.add(singlePageImage.getOriginal());
			}
		}
		
	}
	
	
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MultiPage getParent() {
		return parent;
	}
	public void setParent(MultiPage parent) {
		this.parent = parent;
	}
	public boolean isHome() {
		return isHome;
	}
	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}
	public boolean isForm() {
		return isForm;
	}
	public void setForm(boolean isForm) {
		this.isForm = isForm;
	}
	public boolean isSingleFeatured() {
		return isSingleFeatured;
	}
	public void setSingleFeatured(boolean isSingleFeatured) {
		this.isSingleFeatured = isSingleFeatured;
	}
	public boolean isHasFile() {
		return hasFile;
	}
	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}
	public List<PageFile> getFiles() {
		return files;
	}
	public void setFiles(List<PageFile> files) {
		this.files = files;
	}
	public List<MultiPageFile> getMultiPageFiles() {
		return multiPageFiles;
	}
	public void setMultiPageFiles(List<MultiPageFile> multiPageFiles) {
		this.multiPageFiles = multiPageFiles;
	}
	public Date getItemDate() {
		return itemDate;
	}
	public void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}
	public List<MultiPageFile> getMySortedFiles() {
		return mySortedFiles;
	}
	public void setMySortedFiles(List<MultiPageFile> mySortedFiles) {
		this.mySortedFiles = mySortedFiles;
	}
	public List<MultiPageFile> getMultiPageFiles2() {
		return multiPageFiles2;
	}
	public void setMultiPageFiles2(List<MultiPageFile> multiPageFiles2) {
		this.multiPageFiles2 = multiPageFiles2;
	}
	public List<SinglePageLanguage> getSinglePageLanguageList() {
		return singlePageLanguageList;
	}
	public void setSinglePageLanguageList(
			List<SinglePageLanguage> singlePageLanguageList) {
		this.singlePageLanguageList = singlePageLanguageList;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDaysAvailable() {
		return daysAvailable;
	}
	public void setDaysAvailable(String daysAvailable) {
		this.daysAvailable = daysAvailable;
	}
	public String getDocRoom() {
		return docRoom;
	}
	public void setDocRoom(String docRoom) {
		this.docRoom = docRoom;
	}
	public int getImageCount() {
		return imageCount;
	}
	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}
	public int getFileCount() {
		return fileCount;
	}
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	public MemberType getMemberType() {
		return memberType;
	}
	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}
	public ArrayList<Map.Entry<String, Integer>> getTagCloud() {
		return tagCloud;
	}
	public void setTagCloud(ArrayList<Map.Entry<String, Integer>> tagCloud) {
		this.tagCloud = tagCloud;
	}
	public String getPreviewContent() {
		return previewContent;
	}
	public void setPreviewContent(String previewContent) {
		this.previewContent = previewContent;
	}
	public Date getDatePublished() {
		return datePublished;
	}
	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}
	public Boolean getFlag1() {
		return flag1;
	}
	public void setFlag1(Boolean flag1) {
		this.flag1 = flag1;
	}
	public Boolean getFlag2() {
		return flag2;
	}
	public void setFlag2(Boolean flag2) {
		this.flag2 = flag2;
	}

	public List<String> getImagesUrlImage1() {
		return imagesUrlImage1;
	}


	public void setImagesUrlImage1(List<String> imagesUrlImage1) {
		this.imagesUrlImage1 = imagesUrlImage1;
	}


	public List<String> getImagesUrlImage2() {
		return imagesUrlImage2;
	}


	public void setImagesUrlImage2(List<String> imagesUrlImage2) {
		this.imagesUrlImage2 = imagesUrlImage2;
	}


	public List<String> getImagesUrlImage3() {
		return imagesUrlImage3;
	}


	public void setImagesUrlImage3(List<String> imagesUrlImage3) {
		this.imagesUrlImage3 = imagesUrlImage3;
	}


	public List<String> getImagesUrlThumbnail() {
		return imagesUrlThumbnail;
	}


	public void setImagesUrlThumbnail(List<String> imagesUrlThumbnail) {
		this.imagesUrlThumbnail = imagesUrlThumbnail;
	}


	public List<String> getImagesUrlOriginal() {
		return imagesUrlOriginal;
	}


	public void setImagesUrlOriginal(List<String> imagesUrlOriginal) {
		this.imagesUrlOriginal = imagesUrlOriginal;
	}

	
	
}
