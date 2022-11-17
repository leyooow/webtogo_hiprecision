package com.ivant.cms.action.admin;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;

public class ItemCommentAction extends ActionSupport 
implements UserAware, CompanyAware, PagingAware, ServletContextAware, ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3402053542293202484L;	
	
	private static final ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate.getInstance();
	private static final Logger logger = Logger.getLogger(ItemCommentAction.class);
	private Company company;
	private User user;
	
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	private List<ItemComment> itemComments;	
	
	
	private ServletContext servletContext;
	private HttpServletRequest request;	
	
	private int filterDays;
	private String commentType;	
	
	@Override
	public String execute() throws Exception {
		
		if(filterDays > 0)
		{
			itemComments = itemCommentDelegate.findLatestComments(company, filterDays, Order.desc("updatedOn")).getList(); 
		}
		else
		{
			//System.out.println("Comment type: "+commentType+" "+filterDays);
			if(commentType != null && StringUtils.isNotEmpty(commentType))
			{
				if(commentType.equals("ITEM"))
					itemComments = itemCommentDelegate.getCommentsByType(company, "item", Order.desc("updatedOn")).getList();
				else if(commentType.equals("PAGE"))
					itemComments = itemCommentDelegate.getCommentsByType(company, "page", Order.desc("updatedOn")).getList();
				else
					itemComments = itemCommentDelegate.findAllValid(company,  Order.desc("updatedOn")).getList();
				
			}
			else{
				
				if(company.getName().equalsIgnoreCase("truecare")){
					itemComments = itemCommentDelegate.getTruecareComments(company, Order.desc("updatedOn")).getList();
				}else{
					itemComments = itemCommentDelegate.findAllValid(company,  Order.desc("updatedOn")).getList();
				}
			}
		}	
		//itemComments = itemCommentDelegate.findLatestCommentsWithPaging(company, 30, itemsPerPage, page, Order.desc("createdOn")).getList();
				
//		hasRegistrants=false;
//		List<Member> members = memberDelegate.findPurpose(company,"Registration");
//		if(members!=null){
//			hasRegistrants=true;
//		}
//		
//		if(filterDays > 0) {
//			emails = savedEmailDelegate.findLatestEmail(company, 3).getList();
//		}
//		else {
//			emails = savedEmailDelegate.findAllWithPaging(company, page, itemsPerPage, Order.desc("createdOn")).getList();
//		}
//		int year = new DateTime().getYear();
//		request.setAttribute("year", year);
		return super.execute();
	}
	
	public String delete()
	{
		String id = request.getParameter("id");
		
		ItemComment item = itemCommentDelegate.find(Long.parseLong(id));
		
		if(item != null){
			itemCommentDelegate.delete(item);
			return SUCCESS;
		}
		else return ERROR;		
	}
	
	public String deleteMultiple()
	{
		try{
			String selectedValues = request.getParameter("selectedValues");
			
			//System.out.println(selectedValues); //testing
			StringTokenizer st = new StringTokenizer(selectedValues,"_");
						
			while (st.hasMoreTokens()) {
				ItemComment comment = itemCommentDelegate.find(Long.parseLong(st.nextToken()));
				
				if(comment.getCompany().equals(company)) {
					itemCommentDelegate.delete(comment);
				}		
			}
			
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action.", e);
			return ERROR;
		}
		return SUCCESS; 
	}
	
	public void setFilterDays(int filterDays) {
		this.filterDays = filterDays;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user = user;
	}

	@Override
	public void setCompany(Company company) {
		// TODO Auto-generated method stub
		this.company = company;
	}

	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return page;
	}

	@Override
	public int getTotalItems() {
		// TODO Auto-generated method stub
		return totalItems;
	}

	@Override
	public void setItemsPerPage(int itemsPerPage) {
		// TODO Auto-generated method stub
		this.itemsPerPage = itemsPerPage;
	}

	@Override
	public void setPage(int page) {
		// TODO Auto-generated method stub
		this.page = page;
	}

	@Override
	public void setTotalItems() {
		// TODO Auto-generated method stub
		this.totalItems = totalItems;
	}

	@Override
	public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	/**
	 * @param itemComments the itemComments to set
	 */
	public void setItemComments(List<ItemComment> itemComments) {
		this.itemComments = itemComments;
	}

	/**
	 * @return the itemComments
	 */
	public List<ItemComment> getItemComments() {
		return itemComments;
	}

	/**
	 * @param commentType the commentType to set
	 */
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	/**
	 * @return the commentType
	 */
	public String getCommentType() {
		return commentType;
	}
	

}
