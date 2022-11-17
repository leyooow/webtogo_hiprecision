package com.ivant.cms.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.db.MemberDAO;
import com.ivant.cms.delegate.AbstractDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.GroupMessageDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberMessageDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.GroupMessage;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberMessage;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.MessageRemarks;
import com.ivant.cms.enums.MessageStatus;
import com.ivant.cms.enums.MessageType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.LanguageAware;
import com.ivant.cms.interfaces.MemberAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Lloyd Cedrick Angeles
 *
 */

public class AgianChatAction extends AbstractBaseAction 
			implements Action, Preparable, ServletRequestAware, ServletResponseAware, ServletContextAware, CompanyAware, MemberAware, SessionAware, LanguageAware
{
	private HttpServletResponse response;
	private Language language;
	private List<MemberMessage> allChat;
	private String result;
	private MemberMessageDelegate memberMessageDelegate = MemberMessageDelegate.getInstance();
	private GroupMessageDelegate groupMessageDelegate = GroupMessageDelegate.getInstance();
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String sendChat(){
		
		String message = request.getParameter("chat_content");
		
		MemberMessage memberMessage = new MemberMessage();
		Member userMember = new Member();
		Long idd = Long.parseLong(request.getParameter("chat_to"));
		userMember = memberDelegate.find(idd);
		
		memberMessage.setCompany(company);
		memberMessage.setContent(request.getParameter("chat_content"));
		memberMessage.setMessageRemarks(MessageRemarks.UNSEEN);
		memberMessage.setMessageType(MessageType.CHAT);
		memberMessage.setReceiverMember(userMember);
		//memberMessage.setReceiverUser(userMember);
		memberMessage.setReceiverMessageStatus(MessageStatus.NEW);
		memberMessage.setSenderMember(member);
		memberMessage.setSenderMessageStatus(MessageStatus.NEW);
		//memberMessage.setSenderUser(null);
		memberMessageDelegate.insert(memberMessage);
		System.out.println("SAVED CHAT MESSAGE");
		
		getAllChat();
//		setInputStream(new ByteArrayInputStream(result.getBytes()));
		
		
		return SUCCESS;
	}
	
	public String sendGroupMessage(){
		
		String message = request.getParameter("chat_content");
		Long group = Long.parseLong(request.getParameter("chat_to"));
		
		
		GroupMessage groupMessage = new GroupMessage();

		groupMessage.setCompany(company);
		groupMessage.setSender(member);
		groupMessage.setContent(message);
		groupMessage.setGroupId(group);
		groupMessage.setGroupName(member.getReg_companyName());

		groupMessageDelegate.insert(groupMessage);
		System.out.println("SAVED GROUP MESSAGE");
		

		return SUCCESS;
	}
	
	public List<GroupMessage> getAllGroupMessages(){
		
		JSONArray jsonArr = new JSONArray();
		String group = request.getParameter("group_chat");
		
		
		Order[] orders = new Order[]{
				Order.asc("id")
		};
		
		ObjectList<GroupMessage> messages = groupMessageDelegate.findAllByGroup(company, group);
		
		if(!isNull(messages)){
			//request.setAttribute("agianMembers", members);
			jsonArr.addAll(messages.getList());
		}
		
		//setInputStream(new ByteArrayInputStream(jsonArr.toJSONString().getBytes()));

		
		result = jsonArr.toJSONString();
		
		return messages.getList();
	}
	
	public String showChat(){
		

		if(!isNull(request.getParameter("dateAcquired"))){
			String dateString = request.getParameter("dateAcquired");
			
			DateTime dateAcquired = new DateTime(dateString);
			getUpdatedChat(dateAcquired);
		}else{
			getAllChat();
		}
		
		
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public List<MemberMessage> getAllChat() {
		JSONArray mesArr = new JSONArray();
		JSONArray grpArray = new JSONArray();
		JSONArray contactArray = new JSONArray();
		Order[] orders = new Order[]{
				Order.asc("id")
		};
		member.setLastLogin(new Date());
		memberDelegate.update(member);
		ObjectList<MemberMessage> messages = memberMessageDelegate.findAll(-1, -1, company, member, orders);
		ObjectList<GroupMessage> groupMessages = groupMessageDelegate.findAllByGroup(company, member.getReg_companyName());
		ObjectList<Member> contacts = memberDelegate.findAll(company);
		JSONObject main = new JSONObject();
		main.put("time", new DateTime().toString());
		
		
		
		if(!isNull(messages)){
			//request.setAttribute("agianMembers", members);
			for(MemberMessage message : messages){
				if(!isNull(message.getSenderMember()) && !isNull(message.getReceiverMember())){
					mesArr.add(message);
				}
			}

			main.put("messages", mesArr.toJSONString());
		}else{
			main.put("messages", "[]");
		}
		
		if(!isNull(groupMessages)){
			for(GroupMessage groupMessage : groupMessages){
				if(!isNull(groupMessage.getSender())){
					grpArray.add(groupMessage);
				}
			}
			main.put("groupMessages", grpArray.toJSONString());
		}else{
			main.put("groupMessages", "[]");
		}
		
		
		if(!isNull(contacts)){
			for(Member contact : contacts){
				JSONObject obj = new JSONObject();
				obj.put("contactid", contact.getId());
				if(!isNull(contact.getLastLogin()) && ((new Date()).getTime()-contact.getLastLogin().getTime()) < (1L*60*1000)){
					obj.put("loginstatus", "online");
				}else{
					obj.put("loginstatus", "offline");
				}
				contactArray.add(obj);
				
			}
			
			main.put("contacts", contactArray.toJSONString());
			
		}else{
			main.put("contacts", "[]");
		}

		result = main.toJSONString();

		return messages.getList();

	}
	
	public List<MemberMessage> getUpdatedChat(DateTime dateAcquired){
		JSONArray mesArr = new JSONArray();
		JSONArray grpArray = new JSONArray();
		JSONArray contactArray = new JSONArray();
		
		Order[] orders = new Order[]{
				Order.asc("id")
		};
		
		member.setLastLogin(new Date());
		memberDelegate.update(member);
		ObjectList<MemberMessage> messages = memberMessageDelegate.findAllByMemberAndTime(-1, -1, company, member, dateAcquired.toDate(), orders);
		ObjectList<GroupMessage> groupMessages = groupMessageDelegate.findAllByGroupAndTime(-1, -1, company, member.getReg_companyName(), dateAcquired.toDate(), orders);
		ObjectList<Member> contacts = memberDelegate.findAll(company);
		
		if(!isNull(contacts)){
			for(Member contact : contacts){
				JSONObject obj = new JSONObject();
				obj.put("contactid", contact.getId());
				if(!isNull(contact.getLastLogin()) && ((new Date()).getTime()-contact.getLastLogin().getTime()) < (1L*60*1000)){
					obj.put("loginstatus", "online");
				}else{
					obj.put("loginstatus", "offline");
				}
				contactArray.add(obj);
				
			}
			
		}
		
		if(!isNull(messages)){
			//request.setAttribute("agianMembers", members);
			mesArr.addAll(messages.getList());
		}
		
		if(!isNull(groupMessages)){
			//request.setAttribute("agianMembers", members);
			grpArray.addAll(groupMessages.getList());
		}
		//setInputStream(new ByteArrayInputStream(jsonArr.toJSONString().getBytes()));
		
		JSONObject main = new JSONObject();
		main.put("time", new DateTime().toString());
		main.put("messages", mesArr.toJSONString());
		main.put("groupMessages", grpArray.toJSONString());
		main.put("contacts", contactArray.toJSONString());
		
		result = main.toJSONString();
		
		return messages.getList();
	}
	
	public String showAllLatestChat(){
		getLatestChat();
		
		return SUCCESS;
	}
	
	public String showAllLatestChatRockwell(){
		getLatestChatRockwell();
		
		return SUCCESS;
	}
	
	public List<MemberMessage> getLatestChat(){
		JSONArray jsonArr = new JSONArray();
		Order[] orders = new Order[]{
				Order.asc("id")
		};
		
		ObjectList<Member> membersList = memberDelegate.findAll(company);
		List<MemberMessage> latestChats = new ArrayList<MemberMessage>();
		
		
		for(Member mem : membersList){
			ObjectList<MemberMessage> messages = memberMessageDelegate.findAllBySenderAndReceiver(-1, -1, company, member, mem, orders);
			
			if(!isNull(messages) && !messages.getList().isEmpty()){
				latestChats.add(messages.getList().get(messages.getList().size() - 1));
			}
		}

		jsonArr.addAll(latestChats);
		
		result = jsonArr.toJSONString();
		
		
		return latestChats;
		
	}
	
	public List<MemberMessage> getLatestChatRockwell(){
		JSONArray jsonArr = new JSONArray();
		Order[] orders = new Order[]{
				Order.asc("id")
		};
		Object ob = session.get("selectedMemberRockwell");
		Long lastMemberId = Long.parseLong( ob.toString());
		member = memberDelegate.find(lastMemberId);
		
		List<MemberMessage> latestChats = new ArrayList<MemberMessage>();
		
		
		ObjectList<MemberMessage> messages = memberMessageDelegate.findAll(-1, -1, company, member, orders);
			
		if(!isNull(messages) && !messages.getList().isEmpty()){
				latestChats.add(messages.getList().get(messages.getList().size() - 1));
		}
		

		jsonArr.addAll(latestChats);
		
		result = jsonArr.toJSONString();
		
		
		return latestChats;
		
	}

	public void setAllChat(List<MemberMessage> allChat) {
		this.allChat = allChat;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Language getLanguage() {
		return language;
	}
	
	@Override
	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}

	public HttpServletResponse getServletResponse() {
		return response;
	}
	
	
	
}
