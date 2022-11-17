package com.ivant.cms.action.company.json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cart.action.*;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberMessageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberMessage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.MessageRemarks;
import com.ivant.cms.enums.MessageStatus;
import com.ivant.cms.enums.MessageType;



/*
 * 
 * @author: Josephine Noreen B. Lazo
 * 
 */
public class RockwellJSONAction extends AbstractBaseAction {

	private static final long serialVersionUID = 0L;
	

	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final MemberMessageDelegate memberMessageDelegate = MemberMessageDelegate.getInstance();
	private final UserDelegate userDelegate = UserDelegate.getInstance();
	private String successUrl;
	private String errorUrl;
	
	private String senderEmail;
	private String senderSession;
	
	private String result;
	
	
	Member tempMember = new Member();

	private InputStream inputStream;


	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}
	
	@SuppressWarnings({ "unchecked", "null" })
	public String chatForm() throws Exception{
		
		//ROCKWELL - Register chat members. Use session ID for unique user per session.
			try{
				logger.debug("REACH REACH REACH reach initrockwell");
				String sessionId = request.getParameter("senderSessionId");
				String email = request.getParameter("senderEmail");
				String content = request.getParameter("senderContent");
				// create sessionid_email
				Member member = null;
				String username = email + "_" + sessionId;
				
				// create member if not exist
				member = memberDelegate.findByUsername(company, username);
				if(member == null){
					member = new Member();
					member.setUsername(username);
					member.setEmail(email);
					member.setCompany(company);
					member.setPassword("");
					member.setVerified(true);
					member.setActivated(true);
					member.setNewsletter(false);
				
					Long id = memberDelegate.insert(member);
					session.put("selectedMemberRockwell", id);
					logger.debug(member.getId());
				}
				else{
					Long id = member.getId();
					session.put("selectedMemberRockwell", id);
				}
				
				User receiverUser = new User();
				receiverUser = userDelegate.find(13L);
				
				MemberMessage memberMessage = new MemberMessage();
				memberMessage.setSenderMember(member);
				memberMessage.setCompany(company);
				////// fill other details
				memberMessage.setContent(content);
				/////
				memberMessage.setMessageRemarks(MessageRemarks.UNSEEN);
				memberMessage.setMessageType(MessageType.CHAT);
				memberMessage.setReceiverMember(member);
				memberMessage.setReceiverUser(receiverUser);
				memberMessage.setReceiverMessageStatus(MessageStatus.NEW);
				//memberMessage.setSenderMember(null);
				memberMessage.setSenderMessageStatus(MessageStatus.NEW);
				
				
				memberMessage = memberMessageDelegate.find(memberMessageDelegate.insert(memberMessage));
				
				
				JSONObject obj = new JSONObject();
				obj.put("success", true);
				obj.put("memberMessage", memberMessage);
				obj.put("dateCreated", memberMessage);
				
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			}
			
			catch(Exception e){
				e.printStackTrace();
				logger.error(" ROCKWELL ERROR: " , e);
				
			}
	
		return SUCCESS;
	}
	@SuppressWarnings("all")
	public String showAllLatestChatRockwell(){
		getLatestChatRockwell();
		
		return SUCCESS;
	}
	
	@SuppressWarnings("all")
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
		

		Long memberId = (Long) request.getSession().getAttribute("selectedMemberRockwell");
		JSONObject main = new JSONObject();
		
		main.put("selectedMember" , memberId);
		main.put("messages", latestChats);
		
		setInputStream(new ByteArrayInputStream(main.toJSONString().getBytes()));
		
		result = main.toString();
		
		return latestChats;
		
	}

	
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
