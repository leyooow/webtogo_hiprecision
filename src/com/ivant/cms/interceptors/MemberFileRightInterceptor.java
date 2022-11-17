package com.ivant.cms.interceptors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.MemberAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class MemberFileRightInterceptor implements Interceptor{

	private static final long serialVersionUID = 8150137228884840948L;
	
	private static final Logger logger = Logger.getLogger(MemberFileRightInterceptor.class);
	
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	
	@Override
	public void destroy() {
		logger.debug("DESTROYING MEMBER FILE RIGHT INTERCEPTOR...");
		
	}

	@Override
	public void init() {
		logger.debug("INITIALIZING MEMBER FILE RIGHT INTERCEPTOR...");
		
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		try {
			Object action = invocation.getAction();
			
			long companyId = 0;
			Company company = null;
			if(request.getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY) != null) {
				companyId = ((Company)request.getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY)).getId();
				company = companyDelegate.find(companyId);
			}
			
			//System.out.println("COMPANY: "+company);
			//System.out.println("MEMBER AWARE? "+(action instanceof MemberAware));
			//System.out.println("HAS FILE RIGHTS? "+company.getCompanySettings().getHasPageFileRights());
			
			if(company != null && action instanceof MemberAware && Boolean.TRUE.equals(company.getCompanySettings().getHasPageFileRights())){
				List<MultiPage> multiPageList = multiPageDelegate.findAll(company).getList();
				
				List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
				
				Long memberId = (Long)session.getAttribute(MemberInterceptor.MEMBER_SESSION_KEY);
				
				Member member = memberDelegate.find(memberId);
				
				Map<String, Integer> singlePageFileCount = new HashMap<String, Integer>();
				Map<String, Integer> multiPageFileCount = new HashMap<String, Integer>();
				
				List<MultiPage> memberMultiPageList = new ArrayList<MultiPage>();
				List<SinglePage> memberSinglePageList = new ArrayList<SinglePage>();
				//System.out.println("MEMBER: "+member);
				if(member != null && member.getMemberType() != null) {
					MemberType memberType = member.getMemberType();
					
					for(MultiPage multiPage : multiPageList) {
						//System.out.println("MULTIPAGE: "+multiPage.getName());
						if(Boolean.TRUE.equals(multiPage.getLoginRequired())) {
							
							multiPage.setMemberType(memberType);
							
							if(multiPage.getMultiPageFiles() != null) {
								memberMultiPageList.add(multiPage);
								multiPageFileCount.put(multiPage.getName(), multiPage.getMultiPageFiles().size());
							}
						}
					}
					
					for(SinglePage singlePage : singlePageList) {
						
						if(Boolean.TRUE.equals(singlePage.getLoginRequired())) {
							
							singlePage.setMemberType(memberType);
							
							if(singlePage.getFiles() != null) {
								memberSinglePageList.add(singlePage);
								singlePageFileCount.put(singlePage.getName(), singlePage.getFiles().size());
							}
						}
					}
					
					request.setAttribute("memberSinglePageList", memberSinglePageList);
					request.setAttribute("memberMultiPageList", memberMultiPageList);
					request.setAttribute("multiPageFileCount", multiPageFileCount);
					request.setAttribute("singlePageFileCount", singlePageFileCount);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return invocation.invoke(); 
	}

}
