package com.ivant.cms.action.admin;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.ActivityDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Activity;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ivant.cms.enums.ActivityStatusEnum;
import com.ivant.cms.enums.BillingTypeEnum;
import com.ivant.cms.enums.UserType;




public class ActivityAction extends ActionSupport implements Preparable, ServletRequestAware, 
UserAware, CompanyAware, PagingAware {

	
	private static final long serialVersionUID = 4048368734861819318L;
	private static final Logger logger = Logger.getLogger(GroupAction.class);
	private static final ActivityDelegate activityDelegate = ActivityDelegate.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	
	
	private User user;
	private HttpServletRequest request;
	private Company company;
	
	private String description;
	private ActivityStatusEnum status;
	private User createdBy;
	private User updatedBy;
	private List<User> wtgUsers;
	private List<Activity> activities;
	private List<ActivityStatusEnum> statusTypes;
	
	private String requestDetails;
	private String remarks;
	private String actionTaken;
	private String requestDate;
	private String designIteration;
	private String type;
	
	private int page;
	private int totalItems;
	private int itemsPerPage;

	
	private Activity activity;
	
	private String activityId;
	
	private String errorType;

	public List<ActivityStatusEnum> getStatusTypes() {
		return statusTypes;
	}
	public void setStatusTypes(List<ActivityStatusEnum> statusTypes) {
		this.statusTypes = statusTypes;
	}
	public List<User> getWtgUsers() {
		return wtgUsers;
	}
	public void setWtgUsers(List<User> wtgUsers) {
		this.wtgUsers = wtgUsers;
	}
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	
	
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	@Override
	public String execute() throws Exception {
		//System.out.println("executing.......");
				String[] order = {"id"};
		
		//Object objectList = activityDelegate.findAllWithPaging(user.getCompany(),itemsPerPage, page, order);
		//this.activities = activityDelegate.findAllWithPaging(user.getCompany(),itemsPerPage, page, order).getList();
		//		activities = activityDelegate.findAllWithPaging(user.getCompany(),itemsPerPage, page, order).getList();
				activities = activityDelegate.findAllWithPaging(user.getCompany(),user.getItemsPerPage(), page, order).getList();

		return SUCCESS;
	}
	
	public void prepare() throws Exception {
		try {
			long activityId = Long.parseLong(request.getParameter("activityId"));
			activity = activityDelegate.find(activityId);
		}
		catch(Exception e) {
			activity = new Activity();
			activity.setCompany(user.getCompany());
			activity.setCreatedBy(user);
		}
		String[] orderBy = {"id"};
		wtgUsers = userDelegate.findByType(UserType.WEBTOGO_ADMINISTRATOR);
		//activities = activityDelegate.findAll();
		//activities = activityDelegate.findAllWithPaging(user.getCompany(),user.getItemsPerPage(), page, orderBy).getList();

		//activities = activityDelegate.findAll(company).getList();
		statusTypes = Arrays.asList(ActivityStatusEnum.values());
		//System.out.println("888f8df8df8d8fd8   " + activities.size());
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ActivityStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ActivityStatusEnum status) {
		this.status = status;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public User getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	
	private boolean commonParamsValid() {
		if(user.getUserType() != UserType.SUPER_USER   &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return false;
		} 
//		if(user.getCompany() == null) {
//			return false;
//		}		
//		if(activity == null) {
//			return false;
//		}
		
		return true;
	}
	
	public String edit() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!activity.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}
		
		return Action.SUCCESS;
	}
	
	public String save() {
		
		//System.out.println("Saving activity....");
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		 	
		
		if(activityId  != null) {
			if (activityDelegate.find(new Long(activityId))!= null) {
				//System.out.println("activity name: " + activity.getDescription());
				activity.setUpdatedBy(user);
				Integer stat;
				if (request.getParameter("status") != null ) {
					stat = Integer.parseInt(request.getParameter("status"));
					for (ActivityStatusEnum ac: ActivityStatusEnum.values())
					{
						if (stat == ac.getStatusCode())
							activity.setStatus(ac);
					}
		}
				activityDelegate.update(activity);
			}
		}
		else {
//			if(activity.getId() == null) {
//				errorType = "activityexist";
//				return ERROR;
//			}
			//System.out.println("adding new activity..........");
//			activity.setDescription(description);
//			activity.setStatus(status);
			//System.out.println("status................" + activity.getStatus());
			//System.out.println("status2................" + status);		
			Integer stat;
			if (request.getParameter("status") != null ) {
				stat = Integer.parseInt(request.getParameter("status"));
				for (ActivityStatusEnum ac: ActivityStatusEnum.values())
				{
					if (stat == ac.getStatusCode())
						activity.setStatus(ac);
				}
	}
			activityDelegate.insert(activity);
		}
		
		//lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public String delete() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!activity.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}
		
		activityDelegate.delete(activity);
		
		//lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	public void setRequestDetails(String requestDetails) {
		this.requestDetails = requestDetails;
	}
	public String getRequestDetails() {
		return requestDetails;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
	public String getActionTaken() {
		return actionTaken;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setDesignIteration(String designIteration) {
		this.designIteration = designIteration;
	}
	public String getDesignIteration() {
		return designIteration;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPage() {
		return page;
	}

	public int getTotalItems() {
		return totalItems;
	}
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	@Override
	public void setTotalItems() {
		totalItems = activityDelegate.findAll(company).getList().size();
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	
}
