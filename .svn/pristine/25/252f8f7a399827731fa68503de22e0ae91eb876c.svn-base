package com.ivant.cms.action.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

import com.ivant.cms.action.PageDispatcherAction;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberPollDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.interfaces.PageDispatcherAware;
import com.ivant.utils.PagingUtil;

public class SpcmcDispatcherAction extends PageDispatcherAction implements PageDispatcherAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 488441403688083861L;
	
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private MemberPollDelegate memberPollDelegate = MemberPollDelegate.getInstance();
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	
	private String successUrl;
	private String errorUrl;
	private String notificationMessage;
	private Integer commentPageNumber;
	private Long item_id;
	private CategoryItem categoryItem;
	
	@Override
	public void prepare() throws Exception {
		if(item_id != null){
			categoryItem = categoryItemDelegate.find(item_id);
		}
		super.prepare();
	}
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}
	
	public List<ItemComment> getSpcmcItemComment() {
		List<ItemComment> tempItemComment = new ArrayList<ItemComment>();
		if(categoryItem != null){
			Order[] orders = { Order.desc("createdOn") };
			int maxResultTemp = 5;
			if(commentPageNumber == null){
				commentPageNumber = 1;
			}
			tempItemComment = itemCommentDelegate.getCommentsByItemWithPaging(categoryItem, maxResultTemp, commentPageNumber-1, orders).getList();
		}
		return tempItemComment;
	}
	
	public PagingUtil getSpcmcItemCommentPagingUtil() {
		PagingUtil tempPagingUtil = new PagingUtil();
		if(categoryItem != null){
			List<ItemComment> tempItemComment = new ArrayList<ItemComment>();
			int totalItemCount = 0;
			Order[] orders = { Order.desc("createdOn") };
			int maxResultTemp = 5;
			if(commentPageNumber == null){
				commentPageNumber = 1;
			}
			tempItemComment = itemCommentDelegate.getCommentsByItemWithPaging(categoryItem, -1, -1, orders).getList();
			totalItemCount = tempItemComment.size();
			
			
			
			return new PagingUtil(totalItemCount, maxResultTemp, commentPageNumber,
					(totalItemCount / maxResultTemp));
		}
		
		return tempPagingUtil;
	}

	public Map<String, Map<String, List<CategoryItem>>> getBlogArchive() {
		Map<String, Map<String, List<CategoryItem>>> blogArchive = new HashMap<String, Map<String, List<CategoryItem>>>();
		Map<String, List<CategoryItem>> contentBlog = new HashMap<String, List<CategoryItem>>();
		SimpleDateFormat fmtYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat fmtMonth = new SimpleDateFormat("MMMM");
		String strYear = "";
		String strMonth = "";
		
		String strPrevYear = "";
		String strPrevMonth = "";
		List<CategoryItem> tempListCategoryItem = new ArrayList<CategoryItem>();
		List<CategoryItem> listCategoryItem = new ArrayList<CategoryItem>();
		Order[] orders = {Order.desc("itemDate")};
		Group blogGroup = groupDelegate.find(company, "Blogs");
		listCategoryItem = categoryItemDelegate.findAllByGroupWithPaging(company, blogGroup, -1, -1, orders).getList();
		if(listCategoryItem != null){
			if(listCategoryItem.size() > 0){
				
				for(CategoryItem itm : listCategoryItem){
					if(itm.getItemDate() != null){
						strYear = fmtYear.format(itm.getItemDate());
						strMonth = fmtMonth.format(itm.getItemDate());
						
						contentBlog = blogArchive.get(strYear);
						if(contentBlog==null){
							contentBlog = new HashMap<String, List<CategoryItem>>();
							tempListCategoryItem = new ArrayList<CategoryItem>();
							tempListCategoryItem.add(itm);
							contentBlog.put(strMonth, tempListCategoryItem);
							blogArchive.put(strYear, contentBlog);
						}
						else{
							tempListCategoryItem = contentBlog.get(strMonth);
							
							if(tempListCategoryItem == null){
								tempListCategoryItem = new ArrayList<CategoryItem>();
								tempListCategoryItem.add(itm);
								contentBlog.put(strMonth, tempListCategoryItem);
								blogArchive.put(strYear, contentBlog);
							}
							else{
								tempListCategoryItem.add(itm);
								contentBlog.put(strMonth, tempListCategoryItem);
								blogArchive.put(strYear, contentBlog);
							}
						}
					}
				}
				
			}
		}
		System.out.println("blogArchive content : " + blogArchive);
		return blogArchive;
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

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public Integer getCommentPageNumber() {
		return commentPageNumber;
	}

	public void setCommentPageNumber(Integer commentPageNumber) {
		this.commentPageNumber = commentPageNumber;
	}

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public CategoryItem getCategoryItem() {
		return categoryItem;
	}

	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	
}
