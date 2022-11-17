package com.ivant.cms.action.admin;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.ReferralDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.Referral;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.ReferralEnum;
import com.ivant.cms.enums.ReferralStatus;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CategoryAware;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.GroupAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.EmailUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ReferralAction extends ActionSupport implements Action,  Preparable, ServletRequestAware, 
UserAware, PagingAware,  CompanyAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6076537228718308644L;
	private Logger logger = Logger.getLogger(getClass());
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private UserDelegate userDelegate = UserDelegate.getInstance();
	
	private ReferralDelegate referralDelegate =  ReferralDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private EmailSenderAction emailSender=new EmailSenderAction();
	private FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	
	
	private User user;
	private Member member;
	
	private int page=0;
	private int totalItems;
	private int itemsPerPage;
	private Company company;
	private CompanySettings companySettings;
	private List<Company>  companies;
	private String memSort = "username";
	
	private ServletRequest request;
	private List<Member> memList;
	
	private List<Member> members;
	private List<Referral> referralList = new ArrayList<Referral>();
	
	private Long referralIds[];
	
	
	private ReferralStatus status;
	
	private String actionMode;
	
	private String firstName;
	
	private String lastName;
	
	private String filterByReferrer;
	
	private Long referrerId;
	
	private Member currentReferrer;
	
	private Integer filterByReferralStatus;
	
	private String filterByStatusString;
	
	private Long requestId;
	
	
	@Override
	public void prepare() throws Exception {
		int year = new DateTime().getYear();
		request.setAttribute("year", year);
		
		Order[] orders = {Order.desc("id")};
		
		setReferralList(referralDelegate.findAll(company));
		
		//referralDelegate.findAllWithPaging(company, user.getItemsPerPage(), page, new String[]{"id"});
		
	}

	public String execute() {
		
		//System.out.println("The Company is--->"+(company==null));
		
		
		setCompanySettings(company.getCompanySettings());
		if(user.getCompany() == null || companySettings.getHasReferrals() == false) {
			return Action.ERROR;
		}
		
		String[] order = {"date_created"};
		
		/*
		setReferralList(referralDelegate.findAllWithPaging(user.getCompany(),itemsPerPage, page, order));	
		try {
			memSort = request.getParameter("memSort");
			if (memSort.trim().length()!=0) {
				order[0] = memSort;
				setReferralList(referralDelegate.findAllWithPaging(user.getCompany(),itemsPerPage, page, order));
			}
			else {
				setReferralList(referralDelegate.findAllWithPaging(user.getCompany(),itemsPerPage, page, order));
			}
			
		} catch (Exception e) {
			
		}
		*/
		
		//System.out.println("filterByReferralStatus--------->"+filterByReferralStatus);
		
		if(filterByReferrer!=null&&filterByReferralStatus==null){
			//System.out.println("FNAME IS : "+firstName);
			//System.out.println("LNAME IS : "+lastName);
			setReferralList(referralDelegate.findAllReferrer(firstName,lastName,company));
		}else{
		
			ReferralStatus refStatus = null;
			
			if(filterByReferralStatus!=null){
				
				if(filterByReferralStatus>=0)
					refStatus = ReferralStatus.REFERRAL_STATUSES[filterByReferralStatus-1];
				
				// dao.findByStatus(company,referralStatus,referrer)
			}
			
			setReferralList(referralDelegate.findByStatus(company,refStatus,getCurrentReferrer()));
		
		}
		/*
		if(referrerId!=null){
			setReferralList(referralDelegate.findAllReferrer(getCurrentReferrer(),company));
		}*/
		
		
		
		String[] orderBy = {"nameEditable"};
		setCompanies(companyDelegate.findAll(orderBy).getList());
		
		return Action.SUCCESS;
	}
	
	
	public String updateStatus(){
		
		List<Referral> refs = new ArrayList<Referral>();
		referralList = referralDelegate.findAllByIds(referralIds,company);
		//System.out.println(111111);
		for(int i=0;i<referralList.size();i++){
			referralList.get(i).setStatus(getStatus());
			referralList.get(i).setDateApproved(new Date());
			
		}
		if(referralList.size()!=0){
			if(getStatus().equals(ReferralStatus.APPROVED)||getStatus().equals(ReferralStatus.REJECTED)){
				List<Referral> tempRefList = new ArrayList<Referral>();
				int indexCount = 0;
				for(Referral ref:referralList){
					if(indexCount>0){
						if(ref.getReferredBy() != referralList.get(indexCount-1).getReferredBy()){
							//System.out.println(55555);
							sendEmail(tempRefList);
							tempRefList = new ArrayList<Referral>();
							tempRefList.add(ref);
							indexCount++;
							continue;
						}
					}
					tempRefList.add(ref);
					indexCount++;
				}
				if(tempRefList.size()!=0)
					sendEmail(tempRefList);
				
			}else{
				sendEmail(referralList);
			}
			//System.out.println(88888888);
			referralDelegate.batchUpdate(referralList);
		}
		return Action.SUCCESS;
	}
	
	

	public String  updateRequestedStatus(){
		referralList = referralDelegate.findAllRequestId(requestId,company);
		for(int i=0;i<referralList.size();i++){
			referralList.get(i).setStatus(getStatus());
			referralList.get(i).setDateApproved(new Date());
		}
		if(referralList.size()!=0){
			sendEmail(referralList);
			referralDelegate.batchUpdate(referralList);
		}
		return Action.SUCCESS;
	}
	
	
	private String sendEmail(List<Referral> referrals){
		
		//referralList = referralDelegate.findAllRequestId(requestId,company);
		
		
		try {
			String emailTitle = "Referral Update from "+company.getNameEditable() ;
			
				EmailUtil.connect("smtp.gmail.com", 25);
				StringBuffer content = new StringBuffer();
				
				content.append("Hi <strong>" + referrals.get(0).getReferredBy().getFullName() +"</strong>,");
				
				String message = "";
				message += "<br><br>We reviewed your referral.<br><br>";
				
				message += ("<table width='40%'>");
				message += ("<tr><td colspan=3 rownspan=2 style='width:10px'>");
				message += "Referral Status: <strong>"+status+"</strong><br><br>";
				message +=("</td></tr>");
				for(Referral referral:referrals){
						message += ("<tr><td style='width:10px'></td><td>Name</td><td>:</td><td>");
						message +=(referral.getFullname() );
						message +=("</td></tr>");
						message +=("<tr><td style='width:10px'></td><td>Email</td><td>:</td><td>");
						message +=(referral.getEmail() );
						message +=("</td></tr>");
						message +=("<tr><td style='width:10px'></td><td>Contact Number</td><td>:</td><td>");
						message +=(referral.getContactNumber());
						message +=("</td></tr>");
						if(referral.getStatus().equals(ReferralStatus.REQUESTED)){
							message +=("<tr><td style='width:10px'></td></td>Requested Reward(s)<td>");
							message +=(referral.getReward());
							message +=("</td></tr>");
						}
						message+= ("<tr><td colspan='3'></td></tr>");
						message+= ("<tr><td colspan='3'></td></tr>");
						message+= ("<tr><td colspan='3'></td></tr>");
				}
				
				message+=("</table>");
				message+= ("<br><br>Thank you.<br><br><br>All the Best, <br><br>");
				message+= ("The "+company.getNameEditable()+" Team");
				message+= ("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");
				
				content.append(message);
				
				if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", referrals.get(0).getReferredBy().getEmail(), 
						emailTitle
						, content.toString(),null)){
					return Action.SUCCESS;
				}
			
			return Action.ERROR; 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	
	
	public String getPromoCode(){
	
	
	
	SinglePage promoCodeSetting = singlePageDelegate.find(company, ReferralEnum.PROMOCODE_SETTINGPAGE.getValue());
	
	String currentPromocode = promoCodeSetting.getContent().replaceAll("<p>", "");
	
		if(promoCodeSetting!=null){
			currentPromocode  = currentPromocode.trim();
			return currentPromocode;
		}
		
		return "";
	
	}
	
	
	

	public List<Member> getMemList() {
		return memList;
	}

	public void setMemList(List<Member> memList) {
		this.memList = memList;
	}

	public String getMemSort() {
		return memSort;
	}

	public void setMemSort(String memSort) {
		this.memSort = memSort;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
	public void setMember(Member member) {
		this.member = member;
	}
	
	public int getPage() {
		return page;
	} 

	public void setPage(int page) {
		this.page = page;
	}
	
	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems() {
		totalItems = referralDelegate.findAll().size();
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public List<Member> getMembers() {
		
		return	memberDelegate.findAll(company).getList();
		
		
	}

	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}

	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	@Override
	public void setCompany(Company company) {
		this.company = company;
	}
	
	protected boolean isNull(Object param) {
		return null == param;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setReferralList(List<Referral> referralList) {
		this.referralList = referralList;
	}

	public List<Referral> getReferralList() {
		return referralList;
	}

	
	public ReferralStatus[] getReferralStatuses(){
		
		if(company.getCompanySettings().getHasReferrals()){
			return ReferralStatus.REFERRAL_STATUSES;
		}
		
		return null;
	}

	public void setStatus(ReferralStatus status) {
		this.status = status;
	}

	public ReferralStatus getStatus() {
		
		if(actionMode!=null && status == null){
			
			if(actionMode.equalsIgnoreCase("approve"))
				setStatus(ReferralStatus.APPROVED);
			
			
			if(actionMode.equalsIgnoreCase("reject"))
				setStatus(ReferralStatus.REJECTED);
			
			
			if(actionMode.equalsIgnoreCase("redeemed"))
				setStatus(ReferralStatus.REDEEMED);
			
			
		}
		
		return status;
	}


	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	public String getActionMode() {
		return actionMode;
	}

	public void setReferralIds(Long referralIds[]) {
		this.referralIds = referralIds;
	}

	public Long[] getReferralIds() {
		return referralIds;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFilterByReferrer(String filterByReferrer) {
		this.filterByReferrer = filterByReferrer;
	}

	public String getFilterByReferrer() {
		return filterByReferrer;
	}

	public void setReferrerId(Long referrerId) {
		this.referrerId = referrerId;
	}

	public Long getReferrerId() {
		return referrerId;
	}

	public void setCurrentReferrer(Member currentReferrer) {
		this.currentReferrer = currentReferrer;
	}

	public Member getCurrentReferrer() {
		if(currentReferrer!=null)
			return currentReferrer;
		
		return memberDelegate.find(referrerId);
	}

	public void setFilterByReferralStatus(Integer filterByReferralStatus) {
		this.filterByReferralStatus = filterByReferralStatus;
	}

	public Integer getFilterByReferralStatus() {
		return filterByReferralStatus;
	}

	public String getFilterByStatusString() {
		
		if(filterByReferralStatus != null && filterByReferralStatus>0)
			return ReferralStatus.REFERRAL_STATUSES[filterByReferralStatus-1].getValue();
		return filterByStatusString;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getRequestId() {
		return requestId;
	}


}
