package com.ivant.cms.action.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.EventDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
import com.ivant.cms.delegate.MemberTypeDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.interfaces.CategoryAware;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.GroupAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.EmailUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ListMemberAction
		extends ActionSupport
		implements Action, Preparable, ServletRequestAware, UserAware, PagingAware, GroupAware, CompanyAware, CategoryAware
{
	private static final long serialVersionUID = -6076537228718308644L;
	private final Logger logger = Logger.getLogger(getClass());
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final MemberTypeDelegate memberTypeDelegate = MemberTypeDelegate.getInstance();
	private final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private final UserDelegate userDelegate = UserDelegate.getInstance();
	private final EventDelegate eventDelegate = EventDelegate.getInstance();
	private final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private final MemberFileDelegate memberFileDelegate = MemberFileDelegate.getInstance();
	private final MemberFileItemDelegate memberFileItemDelegate = MemberFileItemDelegate.getInstance();
	private final DateTimeFormatter parser = DateTimeFormat.forPattern("yyyy-mm-dd");

	private User user;
	private List<Event> events;
	private List<MemberFileItems> memberFileItemsList;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	private Company company;
	private CompanySettings companySettings;
	private List<Company> companies;
	private String memSort = "username";
	private Boolean memOrder;

	private ServletRequest request;
	private List<Member> memList;
	private List<Member> memListCount;
	private List<Member> members;
	private Group group;

	private String x, y;
	private boolean boolSearch;
	private boolean boolNull = true;
	private String customFieldDownload;
	private String searchStatus;

	private String startDate;
	private String endDate;

	private int points;

	private Integer totalMembers;
	private Integer totalActiveMembers;
	private Integer totalInactiveMembers;
	private Integer totalNotVerifiedMembers;
	private Integer totalVerifiedMembers;
	
	private Long memberId;

	public String getCustomFieldDownload()
	{
		return customFieldDownload;
	}

	public void setCustomFieldDownload(String customFieldDownload)
	{
		this.customFieldDownload = customFieldDownload;
	}

	public boolean isBoolSearch()
	{
		return boolSearch;
	}

	public void setBoolSearch(boolean boolSearch)
	{
		this.boolSearch = boolSearch;
	}

	public boolean isBoolNull()
	{
		return boolNull;
	}

	public void setBoolNull(boolean boolNull)
	{
		this.boolNull = boolNull;
	}

	public String getX()
	{
		return x;
	}

	public void setX(String x)
	{
		this.x = x;
	}

	public String getY()
	{
		return y;
	}

	public void setY(String y)
	{
		this.y = y;
	}

	@Override
	public void prepare() throws Exception
	{
		final int year = new DateTime().getYear();
		request.setAttribute("year", year);
		// System.out.println("PREPARE::::::::::::::::::::::::::");
	}

	@Override
	public String execute()
	{
		ObjectList<Member> objectList = null;

		if(user.getUserType().getValue().equals("User Sub Group Administrator") || user.getUserType().getValue().equals("User Group Administrator")){
			if(company.getName().equalsIgnoreCase("agian")){
				memList = memberDelegate.findAll(company).getList();
				members = memberDelegate.findAll(company).getList();
				memListCount = memberDelegate.findAll(company).getList();
			}
		}else{
			memListCount = memberDelegate.findAll(company).getList();
			if(request.getParameter("which") != null)
			{
				setBoolSearch(request.getParameter("which").toString().equals("search"));
				boolNull = false;
			}
			else
			{
				boolNull = true;
			}

			setCompanySettings(company.getCompanySettings());
			if(user.getCompany() == null || companySettings.getHasMemberFeature() == false)
			{
				return Action.ERROR;
			}
			
			
			String[] order = { "username" };
			
			String[] wendysMemberOrder = {"createdOn"};
			
			if(company.getName().equalsIgnoreCase(CompanyConstants.CANCUN)/* || company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST*/)
			{
				order = null;
			}
			else if(company.getId()==CompanyConstants.WENDYS){
				order = wendysMemberOrder;
			}

			objectList = memberDelegate.findAllWithPaging(user.getCompany(), itemsPerPage, page, order);
			try
			{
				memSort = request.getParameter("memSort");
				memOrder = Boolean.valueOf(request.getParameter("memOrder"));
				if(memSort != null && memSort.trim().length() != 0)
				{
					if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
					{
						if(StringUtils.equalsIgnoreCase(memSort, "username"))
						{
							this.memSort = "email";
						}
					}
					order[0] = memSort;
					objectList = memberDelegate.findAllWithPaging(user.getCompany(), itemsPerPage, page, order, memOrder);
				}
				else
				{
					objectList = memberDelegate.findAllWithPaging(user.getCompany(), itemsPerPage, page, order);
				}

			}
			catch(final Exception e)
			{
				e.printStackTrace();
			}
			setMembersStatus();
			members = objectList.getList();
			
			memList = objectList.getList();
			
			final String[] orderBy = { "nameEditable" };
			companies = companyDelegate.findAll(orderBy).getList();

			if(boolSearch)
			{

				memList = memberDelegate.findAll(company).getList();
				searchBy();
			}
			else
			{
				memList = objectList.getList();
			}

			if(company.getName().equalsIgnoreCase("pco"))
			{
				events = eventDelegate.findAll(company);
				request.setAttribute("events", events);
			}

			if(company.getName().equalsIgnoreCase("apc") || company.getName().equalsIgnoreCase("westerndigital"))
			{
				group = groupDelegate.find(company, Long.parseLong("" + 212));
				final List<CategoryItem> listOfItems = categoryItemDelegate.findAllByGroup(company, group).getList();
				request.setAttribute("listOfItems", listOfItems);
			}
			if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
			{
				initGurkkaMemberCodes(memList);
			}
		}
		

		return Action.SUCCESS;
	}

	public String inactiveMembers()
	{
		setMembersStatus();
		final List<Member> inactives = memberDelegate.findAll(company, Boolean.FALSE).getList();
		this.members = inactives;
		
		final List<Member> memListCounts = memberDelegate.findAll(company).getList();
		List<Member> memListCountss = new ArrayList<Member>();
		if(company.getName().equalsIgnoreCase("agian")){
			DateTime today = new DateTime();
			DateTime today90 = today.minusDays(90);
			
			for(final Member member : memListCounts){
				if(!isNull(member.getLastLogin())){
					Long d = today.toDate().getTime();
					Long a = (member.getLastLogin()).getTime();
					Long d90 = today90.toDate().getTime();
					if(d > a && a > d90){
						
					}else{
						memListCountss.add(member);
					}
				}
			}
			this.members = memListCountss;
			memListCount = memberDelegate.findAll(company).getList();
		}
		return SUCCESS;
	}
	
	public String activeMembers()
	{
		setMembersStatus();
		final List<Member> actives = memberDelegate.findAll(company, Boolean.TRUE).getList();
		this.members = actives;
		
		final List<Member> memListCounts = memberDelegate.findAll(company).getList();
		List<Member> memListCountss = new ArrayList<Member>();
		if(company.getName().equalsIgnoreCase("agian")){
			DateTime today = new DateTime();
			DateTime today90 = today.minusDays(90);
			
			for(final Member member : memListCounts){
				if(!isNull(member.getLastLogin())){
					Long d = today.toDate().getTime();
					Long a = (member.getLastLogin()).getTime();
					Long d90 = today90.toDate().getTime();
					if(d > a && a > d90){
						memListCountss.add(member);
					}else{
						
					}
				}
			}
			this.members = memListCountss;
			memListCount = memberDelegate.findAll(company).getList();
		}
		return SUCCESS;
	}
	
	public String notVerifiedMembers()
	{
		setMembersStatus();
		Junction junc = Restrictions.conjunction();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("verified", Boolean.FALSE);
		final List<Member> notverified = memberDelegate.findAllByPropertyName(company, junc, filter).getList();
		this.members = notverified;
		memListCount = memberDelegate.findAll(company).getList();
		return SUCCESS;
	}
	
	public String verifiedMembers()
	{
		setMembersStatus();
		Junction junc = Restrictions.conjunction();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("verified", Boolean.TRUE);
		final List<Member> verified = memberDelegate.findAllByPropertyName(company, junc, filter).getList();
		this.members = verified;
		memListCount = memberDelegate.findAll(company).getList();
		return SUCCESS;
	}
	
	public void setMembersStatus()
	{
		totalMembers = memberDelegate.findAll(user.getCompany()).getSize();
		totalActiveMembers = memberDelegate.findAll(user.getCompany(), Boolean.TRUE).getSize();
		totalInactiveMembers = memberDelegate.findAll(user.getCompany(), Boolean.FALSE).getSize();
		
		Junction junc = Restrictions.conjunction();
		Junction junc2 = Restrictions.conjunction();
		Map<String, Object> filter = new HashMap<String, Object>();
		
		filter.put("verified", Boolean.TRUE);
		totalVerifiedMembers = memberDelegate.findAllByPropertyName(company, junc, filter).getSize();
		
		filter.put("verified", Boolean.FALSE);
		totalNotVerifiedMembers = memberDelegate.findAllByPropertyName(company, junc2, filter).getSize();
		
		
	}

	public String resendActivation()
	{
		final String memberId = request.getParameter("member_id");
		if(StringUtils.isNotEmpty(memberId))
		{
			try
			{
				
				final Long id = Long.parseLong(memberId);
				final Member member = memberDelegate.find(id);
				if(member != null)
				{
					setMemberId(member.getId());
					switch(company.getId().intValue())
					{
						case CompanyConstants.GURKKA:
						case CompanyConstants.GURKKA_TEST:
							break;
						case CompanyConstants.MY_HOME_THERAPIST:
							EmailUtil.connectViaCompanySettings(company);
							final String to = member.getEmail();
							final String subject = "My Home Therapist Member Registration";
							final String memberName = member.getFirstname() + " " + member.getLastname();
							final String nextLine = " \r\n\r\n";
							
							final StringBuffer content = new StringBuffer();
							content.append("Hi " + memberName + "," + nextLine);
							content.append("<br/><br/> Welcome to " + company.getNameEditable() + "!,");
							content.append("<br/> Please click on the link below to activate your account.");
							content.append("http://" + company.getServerName() + "/activate.do?activation=" + member.getActivationKey());
							content.append("<br/><br/> Thank you for registering.");
							content.append("<br/> All the Best,");
							content.append("<br/> The " + company.getNameEditable() + " Team");
							
							if(!StringUtils.isEmpty(content.toString()))
							{
								EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, subject, content.toString(), null);
							}
							break;
							
						default:
							break;
					}
					
					final List<Member> inactives = memberDelegate.findAll(company, Boolean.FALSE).getList();
					this.members = inactives;
					request.setAttribute("resendActivationMessage", "Activation email has been sent.");
					setMembersStatus();
				}
				
			}
			catch(final Exception e)
			{
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String watsonMembers()
	{

		DateTime dateFrom = new DateTime(new DateTime().getYear(), new DateTime().getMonthOfYear(), new DateTime().getDayOfMonth(), 0, 0, 0, 0);
		DateTime dateTo = new DateTime(new DateTime().getYear(), new DateTime().getMonthOfYear(), new DateTime().getDayOfMonth(), 23, 59, 59, 999);

		if(!StringUtils.isEmpty(request.getParameter("startDate")) && !StringUtils.isEmpty(request.getParameter("endDate")))
		{
			final String[] start = request.getParameter("startDate").split("-");
			final String[] end = request.getParameter("endDate").split("-");

			dateFrom = new DateTime(new Integer(start[2]).intValue(), new Integer(start[0]).intValue(), new Integer(start[1]).intValue(), 0, 0, 0, 0);
			dateTo = new DateTime(new Integer(end[2]).intValue(), new Integer(end[0]).intValue(), new Integer(end[1]).intValue(), 23, 59, 59, 999);
		}

		final String[] sDate = dateFrom.toString().substring(0, 10).split("-");
		final String[] eDate = dateTo.toString().substring(0, 10).split("-");

		startDate = sDate[1] + "-" + sDate[2] + "-" + sDate[0];
		endDate = eDate[1] + "-" + eDate[2] + "-" + eDate[0];


		return SUCCESS;
	}






	/**
	 * gets the list of all member file by the use of member file items
	 *
	 * @author Mohaimen S. Mutalib
	 */
	public String memberFiles()
	{
		memberFileItemsList = new ArrayList<MemberFileItems>();
		List<MemberFile> memberFiles = new ArrayList<MemberFile>();
		members = memberDelegate.findAll(company).getList();

		// System.out.println("STATUS LMA == "+searchStatus);

		for(final Member member : members)
		{
			memberFiles = memberFileDelegate.findAll(member);
			Collections.reverse(memberFiles);

			if(searchStatus != null && !StringUtils.isEmpty(searchStatus))
			{
				for(final MemberFile memFile : memberFiles)
				{
					if(searchStatus.equalsIgnoreCase(memFile.getStatus()))
					{
						final MemberFileItems fileInfo = memberFileItemDelegate.findMemberFileItem(company, memFile.getId());
						memberFileItemsList.add(fileInfo);
					}
				}
			}
			else
			{
				for(final MemberFile memFile : memberFiles)
				{
					final MemberFileItems fileInfo = memberFileItemDelegate.findMemberFileItem(company, memFile.getId());
					memberFileItemsList.add(fileInfo);
				}
			}
		}

		if(memberFileItemsList != null)
		{
			// System.out.println("THE TOTAL NUMBER OF ITEMS FOUND IS/ARE "+memberFileItemsList.size());
			Collections.reverse(memberFileItemsList);
			request.setAttribute("memberFileItemsList", memberFileItemsList);
		}
		return Action.SUCCESS;
	}

	/**
	 * gets the list of members with the selected criteria matching the input string
	 *
	 * @author Samiel Gerard C. Santos
	 */
	private void searchBy()
	{

		if(request.getParameter("searchBy") != null && request.getParameter("str") != null)
		{
			x = request.getParameter("searchBy").toString();
			y = request.getParameter("str").toString();

			// Pattern regex = Pattern.compile(y);

			if(x.equalsIgnoreCase("u"))
			{
				for(int index = 0; index < memList.size(); index++)
				{
					final Member mem = memList.get(index);
					// System.out.println("OOOOO" + mem.getUsername().matches(regex.pattern()));
					if(!(mem.getUsername().toLowerCase().contains(y.toLowerCase())))
					{
						memList.remove(index);
						index--;
					}
				}
			}
			else if(x.equalsIgnoreCase("f"))
			{
				for(int index = 0; index < memList.size(); index++)
				{
					final Member mem = memList.get(index);
					if(!(mem.getFirstname().toLowerCase().contains(y.toLowerCase())))
					{
						memList.remove(index);
						index--;
					}
				}
			}
			else if(x.equalsIgnoreCase("l"))
			{
				for(int index = 0; index < memList.size(); index++)
				{
					final Member mem = memList.get(index);
					if(!(mem.getLastname().toLowerCase().contains(y.toLowerCase())))
					{
						memList.remove(index);
						index--;
					}
				}
			}
			else if(x.equalsIgnoreCase("e"))
			{
				for(int index = 0; index < memList.size(); index++)
				{
					final Member mem = memList.get(index);
					if(!(mem.getEmail().toLowerCase().contains(y.toLowerCase())))
					{
						memList.remove(index);
						index--;
					}
				}
			}
		}
		// else memList = null;
	}

	private void initGurkkaMemberCodes(List<Member> memList)
	{
		try
		{
			if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
			{
				final Map<Long, String> memberCodes = new HashMap<Long, String>();
				for(final Member member : memList)
				{
				}
				request.setAttribute("memberCode", memberCodes);
			}
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Map<String, List<CategoryItem>> getListCategoryItemMap(){
		Map<String, List<CategoryItem>> categoryItemMap = new HashMap<String, List<CategoryItem>>();
		final List<Group> listCategories = groupDelegate.findAll(company).getList();
		for(Group g: listCategories){
			categoryItemMap.put(g.getName(), categoryItemDelegate.findAllEnabledWithPaging(company, g, -1, -1, null).getList());
		}
		return categoryItemMap;
	}
	

	public List<Member> getMemList()
	{
		return memList;
	}

	public void setMemList(List<Member> memList)
	{
		this.memList = memList;
	}

	public String getMemSort()
	{
		return memSort;
	}

	public void setMemSort(String memSort)
	{
		this.memSort = memSort;
	}
	
	public Boolean getMemOrder()
	{
		return memOrder;
	}

	public void getMemOrder(Boolean memOrder)
	{
		this.memOrder = memOrder;
	}

	@Override
	public void setUser(User user)
	{
		this.user = user;
	}

	public void setMember(Member member)
	{
	}

	@Override
	public int getPage()
	{
		return page;
	}

	@Override
	public void setPage(int page)
	{
		this.page = page;
	}

	@Override
	public int getTotalItems()
	{
		return totalItems;
	}

	@Override
	public void setTotalItems()
	{
		/* changed 7/4/2012 */
		totalItems = memberDelegate.findAll(company, Boolean.TRUE).getSize();
	}

	@Override
	public void setItemsPerPage(int itemsPerPage)
	{
		this.itemsPerPage = itemsPerPage;
	}

	public List<Member> getMembers()
	{
		return members;
	}
	
	public List<Member> getMemListCount()
	{
		return memListCount;
	}

	public void setCompanySettings(CompanySettings companySettings)
	{
		this.companySettings = companySettings;
	}

	public CompanySettings getCompanySettings()
	{
		return companySettings;
	}

	@Override
	public void setCompany(Company company)
	{
		this.company = company;
	}

	protected boolean isNull(Object param)
	{
		return null == param;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;

	}

	@Override
	public void setGroup(Group group)
	{
		this.group = group;

	}

	@Override
	public void setCategory(Category category)
	{

	}

	public List<Company> getCompanies()
	{
		return companies;
	}

	public void setCompanies(List<Company> companies)
	{
		this.companies = companies;
	}

	public void setMemberFileItemsList(List<MemberFileItems> memberFileItemsList)
	{
		this.memberFileItemsList = memberFileItemsList;
	}

	public List<MemberFileItems> getMemberFileItemsList()
	{
		return memberFileItemsList;
	}

	public void setSearchStatus(String status)
	{
		this.searchStatus = status;
	}

	public String getSearchStatus()
	{
		return searchStatus;
	}

	public List<MemberType> getMemberTypes()
	{

		return memberTypeDelegate.findAllWithPaging(company, -1, -1, null).getList();
	}

	public void setUseBillingNumberAsUserName(boolean useBillingNumberAsUserName)
	{
	}

	public boolean isUseBillingNumberAsUserName()
	{
		if(company.getName().equalsIgnoreCase("cancun"))
		{
			return true;
		}
		return false;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public Integer getTotalMembers()
	{
		return totalMembers;
	}

	public Integer getTotalActiveMembers()
	{
		return totalActiveMembers;
	}

	public Integer getTotalInactiveMembers()
	{
		return totalInactiveMembers;
	}

	public void setTotalNotVerifiedMembers(Integer totalNotVerifiedMembers) {
		this.totalNotVerifiedMembers = totalNotVerifiedMembers;
	}

	public Integer getTotalNotVerifiedMembers() {
		return totalNotVerifiedMembers;
	}

	public void setTotalVerifiedMembers(Integer totalVerifiedMembers) {
		this.totalVerifiedMembers = totalVerifiedMembers;
	}

	public Integer getTotalVerifiedMembers() {
		return totalVerifiedMembers;
	}

	public Long getMemberId() {
		return memberId;
	}
	
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	
}
