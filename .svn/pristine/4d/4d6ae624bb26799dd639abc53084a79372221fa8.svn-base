package com.ivant.cart.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompareDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Compare;
import com.ivant.cms.entity.Member;
import com.ivant.constants.CompanyConstants;



public class CompareAction extends AbstractBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6175561435493521309L;
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private UserDelegate userDelegate = UserDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private CompareDelegate compareDelegate = CompareDelegate.getInstance();
	
	private String successUrl;
	private String errorUrl;
	private InputStream inputStream;
	private String notificationMessage;

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
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

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	
	
	@SuppressWarnings("unchecked")
	public String savecompare() {
		logger.info("#######################compare action      1      ########################");
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		Integer totalCompareItemCount = 0;
		String requestSItemId = request.getParameter("item_id");
		Long requestLItemId = 0L;
		Compare compare = new Compare();
		
		try{
			requestLItemId = Long.parseLong(requestSItemId);
		}
		catch(Exception e){
			
		}
		logger.info("#######################compare action      2      ########################");
		if(member != null){
			logger.info("#######################compare action      3      ########################");
			totalCompareItemCount = Integer.parseInt(String.valueOf(compareDelegate.findCompareCountByMember(company, member)));
			
			
			CategoryItem item = categoryItemDelegate.find(requestLItemId);
			if(item != null){
				logger.info("#######################compare action      6      ########################");
				compare = compareDelegate.find(company, member, item);
				if(compare == null){
					//do not add when the total number of items reach the maximum allowed count
					if(CompanyConstants.GURKKA_TEST == Integer.parseInt(String.valueOf(company.getId()))){
						logger.info("#######################compare action      4      ########################");
					}
					logger.info("#######################compare action      7      ########################");
					compare = new Compare();
					compare.setCompany(company);
					compare.setMember(member);
					compare.setCategoryItem(item);
					compareDelegate.insert(compare);
					
					obj.put("success", true);
					obj.put("isAddedToCompare", true);
					obj.put("compareContent", countCompareWithFormat(company, member));
					objList.add(obj);
					obj2.put("listAddToCompareDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				}
				else{
					//delete this item to compare (only  when it is an existing item)
					compareDelegate.delete(compare);
					
					obj.put("success", true);
					obj.put("isAddedToCompare", false);
					obj.put("compareContent", countCompareWithFormat(company, member));
					objList.add(obj);
					obj2.put("listAddToCompareDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				}
				
				
				
			}
			else{
				logger.info("#######################compare action    8        ########################");
				logger.info("CategoryItem is null");
				obj.put("errorMessage", "Invalid Item! This item was not available in the database!");
				obj.put("isAddedToCompare", false);
				obj.put("compareContent", countCompareWithFormat(company, member));
				objList.add(obj);
				obj2.put("listAddToCompareDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
			}
		}
		else{
			logger.info("#######################compare action     9       ########################");
			logger.info("Member is null!");
			obj.put("errorMessage", "You are not a recognized user! Please log-in first before adding this item to compare!");
			obj.put("isAddedToCompare", false);
			obj.put("compareContent", countCompareWithFormat(company, member));
			objList.add(obj);
			obj2.put("listAddToCompareDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
		}
		logger.info("#######################compare action      10      ########################");
		return SUCCESS;
	}
	
	public String countCompareWithFormat(Company company, Member member){
		Integer totalCount = 0;
		DecimalFormat myFormatter = new DecimalFormat("###,###");
		totalCount = Integer.parseInt(String.valueOf(compareDelegate.findCompareCountByMember(company, member)));
		return myFormatter.format(totalCount);
	}
}
