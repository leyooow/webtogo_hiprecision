package com.ivant.cms.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberFileAction 
		extends ActionSupport
		implements Preparable, ServletRequestAware, UserAware, CompanyAware
			{
	
	private Company company;
	private User user;
	private HttpServletRequest request;
	
	protected MemberFileDelegate memberFileDelegate = MemberFileDelegate.getInstance();
	protected MemberDelegate memberDelegate = MemberDelegate.getInstance();
	
	protected GroupDelegate groupDelegate = GroupDelegate.getInstance();
	protected CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	protected CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	
	private String successUrl;
	private String errorUrl;
	
	private Member member;
	private MemberFile memberFile;
	private String member_id;
	
	public String notificationMessage;
	
	@Override
	public void setCompany(Company company) {
		// TODO Auto-generated method stub
		this.company = company;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user = user;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String getSuccessUrl()
	{
		return successUrl;
	}
	
	public void setSuccessUrl(String successUrl)
	{
		this.successUrl = successUrl;
	}
	
	public String getErrorUrl()
	{
		return errorUrl;
	}
	
	public void setErrorUrl(String errorUrl)
	{
		this.errorUrl = errorUrl;
	}
	
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public MemberFile getMemberFile() {
		return memberFile;
	}

	public void setMemberFile(MemberFile memberFile) {
		this.memberFile = memberFile;
	}
	
	
	public List<MemberFile> getAllMemberFile(){
		return memberFileDelegate.findAllWithPaging(company, -1, -1, Order.desc("createdOn"));
	}
	
	public List<MemberFile> getMemberFileById(){
		try{
			if(request.getParameter("member_id") != null){
				member = memberDelegate.find(Long.parseLong(request.getParameter("member_id")));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return memberFileDelegate.findAll(member);
	}
	
	public MemberFile getMemberFileByMemberFileId(){
		try{
			if(request.getParameter("memberfile_id") != null){
				return memberFileDelegate.find(Long.parseLong(request.getParameter("memberfile_id")));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String saveGC() {
		
		try{
			
					Long cat_id = 0L;
					Boolean hasValidMemberFileId = false;
					try{
						cat_id = Long.parseLong(request.getParameter("memberfile_id"));
						hasValidMemberFileId = true;
					}
					catch(Exception e){
						cat_id = 0L;
						e.printStackTrace();
					}
					Category category = CategoryDelegate.getInstance().find(Long.parseLong(request.getParameter("parent")));
					
					MemberFile memberFile = request.getParameter("memberfile_id") != null && hasValidMemberFileId ? memberFileDelegate.find(cat_id) : new  MemberFile();
					
					if(request.getParameter("memberfile_id") != null && hasValidMemberFileId){
						
						
						memberFile.setRemarks(request.getParameter("remarks"));
						
						
						
						memberFile.setCategory(category);
						memberFileDelegate.update(memberFile);
						notificationMessage = "Reward successfully updated!";
						
					}
					else{
						
						memberFile.setApprovedDate(new Date());
						memberFile.setRemarks(request.getParameter("remarks"));
						memberFile.setStatus("Not yet Redeemed");
						
						memberFile.setCompany(company);
						memberFile.setCategory(category);
						memberFileDelegate.insert(memberFile);
						notificationMessage = "New Reward successfully created!";
						
					}
					
					
					
			
		}
		catch(Exception e){
			e.printStackTrace();
			notificationMessage = "Error encountered while creating new Reward!";
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String deleteGC() {
		
		try{
			
			MemberFile memberFile = memberFileDelegate.find(Long.parseLong(request.getParameter("memberfile_id")));
			if(memberFile !=null){
				memberFileDelegate.delete(memberFile);
				notificationMessage = "Reward successfully deleted!";
			}
			else{
				
			}
			
			
			
		}
		catch(Exception e){
			e.printStackTrace();
			notificationMessage = "Error encountered while creating new Reward!";
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String redeemReward() {
		
		
		
		
		if(request.getParameter("member_file_id") != null) {
			MemberFile memberFile = memberFileDelegate.find(Long.parseLong(request.getParameter("member_file_id")));
			
				try{
					if(memberFile != null){
					memberFile.setStatus("Already Redeemed");
					memberFileDelegate.update(memberFile);
					member_id = request.getParameter("member_id");
					notificationMessage = "Reward successfully redeemed!";
					}
					else
					{
						member_id = request.getParameter("member_id");
						notificationMessage = "Reward was not redeemed!";
						return ERROR;
					}
				}
				catch(Exception e){
					e.printStackTrace();
					member_id = request.getParameter("member_id");
					notificationMessage = "Reward was not redeemed!";
					return ERROR;
				}
				
				
			}
				
		return SUCCESS;
	}
	
	public String assignPoints(){
		
		return SUCCESS;
	}

/* Get  Categories Map */
public Map<String, List<Category>> getListCategoryMap()
{
	Map<String, List<Category>> categoryMap = new HashMap<String, List<Category>>();
	final List<Group> listCategories = groupDelegate.findAll(company).getList();
	for(Group g : listCategories){
		categoryMap.put(g.getName(),categoryDelegate.findAll(company, g).getList());
	}
	return categoryMap;
}
	
public Map<String,Group> getGroupMap()
{
	// get the groups
	final List<Group> groupList = groupDelegate.findAll(company).getList();
	final Map<String, Group> groupMap = new HashMap<String, Group>();
	final Map<Long, Group> groupIdMap = new HashMap<Long, Group>();
	for(final Group group : groupList)
	{
		final String jspName = group.getName().toLowerCase();
		//group.setLanguage(language);
		//final Menu menu = new Menu(group, httpServer + "/" + jspName + ".do");
		//menuList.put(jspName, menu);
		
		groupMap.put(group.getName(), group);
		groupMap.put(jspName, group);
		groupIdMap.put(group.getId(), group);
	}
	request.setAttribute("groupList", groupList);
	request.setAttribute("groupMap", groupMap);
	request.setAttribute("groupIdMap", groupIdMap);
	return groupMap;
}
	
public String getNotificationMessage() {
	return notificationMessage;
}

public void setNotificationMessage(String notificationMessage) {
	this.notificationMessage = notificationMessage;
}

public String getMember_id() {
	return member_id;
}

public void setMember_id(String member_id) {
	this.member_id = member_id;
}
	
}
