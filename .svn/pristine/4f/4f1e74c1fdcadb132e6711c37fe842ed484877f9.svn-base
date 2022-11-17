package com.ivant.cms.action.company;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Member;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.MemberAware;

public class SpcmcUtilAction extends AbstractBaseAction implements MemberAware, CompanyAware {

	private InputStream inputStream;
	private ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private Long item_id;
	private Long latestCommentId;
	private String content;
	
	@Override
	public void prepare() throws Exception {
		
	}

	@SuppressWarnings("unchecked")
	public String submitComment() throws Exception, IOException {
		logger.info("submitComment method invoked...");
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		Order[] order = {Order.desc("createdOn")};
		CategoryItem categoryItem = new CategoryItem();
		List<ItemComment> newItemComment = new ArrayList<ItemComment>();
		try{
			categoryItem = categoryItemDelegate.find(item_id);
			if(getMember() == null){
				obj.put("errorMessage", "Session was expired. Please log-in to continue your action...");
				objList.add(obj);
				obj2.put("listSubmitComment", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				return SUCCESS;
			}
			
			if(categoryItem == null){
				obj.put("errorMessage", "Error encountered. Try to refresh this page...");
				objList.add(obj);
				obj2.put("listSubmitComment", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				return SUCCESS;
			}
			ItemComment itemComment = new ItemComment();
			List<ItemComment> listMemberItemComment = new ArrayList<ItemComment>();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			int similarContentCount = 0;
			listMemberItemComment = itemCommentDelegate.findAllByMemberAndItemContent(company, categoryItem, member, content).getList();
			for(ItemComment comm : listMemberItemComment){
				if(fmt.format(comm.getCreatedOn()).equals(fmt.format(new Date()))){
					similarContentCount++;
					break;
				}
			}
			if(similarContentCount > 0){
				obj.put("errorMessage", "Similar comment was already sent!");
				objList.add(obj);
				obj2.put("listSubmitComment", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				return SUCCESS;
			}
			else{
				itemComment.setCompany(company);
				itemComment.setMember(member);
				itemComment.setContent(content);
				itemComment.setItem(categoryItem);
				itemComment.setValue(0D);
				itemComment.setCreatedOn(new Date());
				itemComment.setUpdatedOn(new Date());
				Long tempId = 0L;
				try{
					tempId= itemCommentDelegate.insert(itemComment);
					if(tempId == null){
						tempId = 0L;
					}
				}catch(Exception e1){}
				if(!(tempId > 0L)){
					obj.put("errorMessage", "Comment was not sent!");
					objList.add(obj);
					obj2.put("listSubmitComment", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
					return SUCCESS;
				}
				
				if(latestCommentId == null){
					latestCommentId = 0L;
				}
				
				newItemComment = itemCommentDelegate.findAllLatestCommentByGivenId(company, categoryItem, latestCommentId, order).getList();
				if(newItemComment != null){
					if(newItemComment.size() > 0){
						Format formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm:ss a");
						String deyt = "";
						for(ItemComment ic : newItemComment){
							deyt = formatter.format(ic.getCreatedOn());
							obj = new JSONObject();
							obj.put("memberName", ic.getMember().getFirstname() + " " + ic.getMember().getLastname());
							obj.put("commentDate", deyt);
							obj.put("commentContent", ic.getContent());
							obj.put("commentId", ic.getId());
							objList.add(obj);
						}
						obj2.put("listSubmitComment", objList);
						setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
						logger.info("inputStream value : "+ inputStream);
						logger.info("obj2 value : "+ obj2.toJSONString());
						return SUCCESS;
					}
					else{
						obj.put("errorMessage", "Comment was not sent!");
						objList.add(obj);
						obj2.put("listSubmitComment", objList);
						setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
						return SUCCESS;
					}
				}else{
					obj.put("errorMessage", "Comment was not sent!");
					objList.add(obj);
					obj2.put("listSubmitComment", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
					return SUCCESS;
				}
			}
			
		}catch(Exception e){
			logger.info("Error encountered inside submitComment method...");
		}
		
		return SUCCESS;
	}

	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getLatestCommentId() {
		return latestCommentId;
	}

	public void setLatestCommentId(Long latestCommentId) {
		this.latestCommentId = latestCommentId;
	}

	public Member getMember()
	{
		return this.member;
	}
	
	@Override
	public void setMember(Member member){
		this.member = member;
	}
	
	

}
