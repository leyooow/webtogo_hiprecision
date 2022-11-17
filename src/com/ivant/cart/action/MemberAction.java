package com.ivant.cart.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompanySettingsDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberShippingInfo;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.ShippingStatus;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.PasswordEncryptor;

/**
 * Action for company member's create, update, and delete.
 *
 * @author Mark Kenneth M. Ra�osa
 */
public class MemberAction
		extends AbstractBaseAction
{
	private final Logger logger = LoggerFactory.getLogger(MemberAction.class);
	private static final long serialVersionUID = 1375473430583518274L;

	CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private final ShoppingCartItemDelegate cartItemDelegate = ShoppingCartItemDelegate.getInstance();
	private final ShoppingCartDelegate cartDelegate = ShoppingCartDelegate.getInstance();
	private final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private final FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private final CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	private final CompanySettingsDelegate companySettingsDelegate = CompanySettingsDelegate.getInstance();
	
	protected CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();

	private static final List<String> ALLOWED_PAGES;
	private String notificationMessage;

	static
	{
		ALLOWED_PAGES = new ArrayList<String>();
		ALLOWED_PAGES.add("sitemap");
		ALLOWED_PAGES.add("printerfriendly");
		ALLOWED_PAGES.add("brand");
		ALLOWED_PAGES.add("calendarevents");
		ALLOWED_PAGES.add("cart");
	}

	private ShoppingCart shoppingCart;

	private CompanySettings companySettings;

	/** Object responsible for member CRUD tasks */
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();

	private final MemberShippingInfoDelegate shippingInfoDelegate = MemberShippingInfoDelegate.getInstance();
	private MemberShippingInfo shippingInfo;
	private ShippingInfo info;

	private PasswordEncryptor encryptor;

	private String actionType;

	@Override
	public void prepare() throws Exception
	{
		logger.debug("member : " + member);
		companySettings = company.getCompanySettings();
		// check if action member action is save or edit
		actionType = request.getParameter("action");
		if(isNull(actionType))
		{
			actionType = LOAD;
		}
		logger.debug("current action type : " + actionType);

		loadAllRootCategories();
		getCartSize();

		// populate server URL to be redirected to
		initHttpServerUrl();

		locateShoppingInfo();

		loadFeaturedPages(companySettings.getMaxFeaturedPages());

		loadMenu();
	}

	@Override
	public String execute() throws Exception
	{
		// validate current user, must not be empty
		if(isNull(member))
		{
			return INPUT;
		}

		logger.debug("[execute] actionType : " + actionType);

		// update values
		if(SAVE.equals(actionType))
		{
			member.setFirstname(request.getParameter("firstname"));
			member.setLastname(request.getParameter("lastname"));
			member.setEmail(request.getParameter("email"));
			member.setUpdatedOn(new Date());

			addActionMessage("Account information changed.");
			memberDelegate.update(member);
			actionType = LOAD;
		}
		request.setAttribute("editCheck", 0);
		return SUCCESS;
	}

	/**
	 * Returns {@code SUCCESS} if password was successfully changed, {@code INPUT} if member is null, and {@code ERROR} if an error was encountered.
	 *
	 * @return - {@code SUCCESS} if password was successfully changed, {@code INPUT} if member is null, and {@code ERROR} if an error was
	 *         encountered
	 */
	public String editPassword()
	{
		// validate current user, must not be empty
		if(isNull(member))
		{
			if(request.getParameter("member_id") != null){
				try{
					member = memberDelegate.find(Long.parseLong(request.getParameter("member_id")));
					if(isNull(member))
					{
						return INPUT;
					}
				}
				catch(Exception e){return INPUT;}
			}
			else{
				return INPUT;
			}
			
		}

		final String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		final String confirmPassword = request.getParameter("confirmPassword");

		encryptor = new PasswordEncryptor();
		final String tempCurrentPassword = encryptor.encrypt(currentPassword);
		final String oldPassword = encryptor.decrypt(member.getPassword());

		// validate current passwords
		if(!(encryptor.decrypt(tempCurrentPassword).equals(oldPassword)))
		{
			logger.debug("Current Password does not match.");
			addActionError("Current Password does not match.");
			return ERROR;
		}

		// validate new passwords
		if(!newPassword.equals(confirmPassword))
		{
			logger.debug("New passwords dont match.");
			addActionError("New passwords dont match.");
			return ERROR;
		}

		// encrypt new password
		newPassword = encryptor.encrypt(newPassword);
		// update new member password
		member.setPassword(newPassword);

		// update new member
		member.setUpdatedOn(new Date());
		memberDelegate.update(member);

		logger.debug("newPassword : " + newPassword);

		addActionMessage("Password successfully changed.");
		if(company.getId() == CompanyConstants.POCKETPONS){
			request.setAttribute("notificationMessage","Password successfully changed");
		}
		return SUCCESS;
	}

	public String editSecret()
	{
		final String securityInfo[] = member.getInfo1().split("\\|");
		final String securityQuestion = StringUtils.trimToNull(securityInfo[0]);
		

		request.setAttribute("question", securityQuestion);
		request.setAttribute("answer", securityInfo[1]);
		return SUCCESS;
	}

	public String swapSecret()
	{
		if(isNull(member))
		{
			return INPUT;
		}
		// System.out.println("1st going");
		String newQuestion = request.getParameter("securityquestion");
		final String newAnswer = request.getParameter("answer");
		// System.out.println("Parameter: " + newQuestion);
		if(newQuestion.equals("1"))
		{
			newQuestion = "What was your childhood nickname?";
		}
		else if(newQuestion.equals("2"))
		{
			newQuestion = "What is the name of your favorite childhood friend?";
		}
		else if(newQuestion.equals("3"))
		{
			newQuestion = "What was your dream job as a child?";
		}
		else if(newQuestion.equals("4"))
		{
			newQuestion = "What is your preferred musical genre?";
		}
		else if(newQuestion.equals("5"))
		{
			newQuestion = "Who was your childhood hero?";
		}
		else if(newQuestion.equals("6"))
		{
			newQuestion = "What is the name of the first school you attended?";
		}
		else if(newQuestion.equals("7"))
		{
			newQuestion = "What was your favorite place to visit as a child?";
		}
		else if(newQuestion.equals("8"))
		{
			newQuestion = "In what city or town was your first job?";
		}
		else if(newQuestion.equals("0"))
		{
			newQuestion = request.getParameter("question");
		}
		// System.out.println("Parameter: " + newQuestion);
		final String newSecurity = newQuestion + "|" + newAnswer;
		// update new member password
		member.setInfo1(newSecurity);
		// System.out.println("2nd going");

		// update new member
		member.setUpdatedOn(new Date());
		memberDelegate.update(member);
		// System.out.println("3rd going");

		logger.debug("newSecurity : " + newSecurity);
		// System.out.println("4th going");

		addActionMessage("Secret Q & A successfully changed!");

		// System.out.println("5th going");
		return SUCCESS;

	}

	/**
	 * Returns action type property value. Action type can be edit, save or
	 * load.
	 *
	 * @return - action type property value. Action type can be edit, save or
	 *         load
	 */
	public String getActionType()
	{
		return actionType;
	}

	public void getCartSize()
	{
		ObjectList<ShoppingCartItem> tempCartItems;
		try
		{
			shoppingCart = cartDelegate.find(company, member);
			tempCartItems = cartItemDelegate.findAll(shoppingCart);
			final int cartSize = tempCartItems.getList().size();
			request.setAttribute("cartSize", cartSize);
		}
		catch(final Exception e)
		{
			logger.debug("Shopping cart is currently empty.");
		}

	}

	private void loadAllRootCategories()
	{
		final Order[] orders = { Order.asc("sortOrder") };
		final List<Category> rootCategories = categoryDelegate.findAllRootCategories(company, true, orders).getList();
		request.setAttribute("rootCategories", rootCategories);
	}

	private void locateShoppingInfo()
	{

		try
		{
			shippingInfo = shippingInfoDelegate.find(company, member);

			if(!isNull(shippingInfo) && shippingInfo.getShippingInfo() != null)
			{
				info = shippingInfo.getShippingInfo();
			}
			else
			{
				info = null;
			}
		}
		catch(final Exception e)
		{
			logger.debug("No shipping info found.");
		}
		request.setAttribute("info", info);
	}

	private void loadMenu()
	{
		try
		{
			final Map<String, Menu> menuList = new HashMap<String, Menu>();

			// get the single pages
			final List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
			for(final SinglePage singlePage : singlePageList)
			{
				final String jspName = singlePage.getJsp();
				final Menu menu = new Menu(singlePage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(singlePage.getJsp(), menu);
			}

			// get the multi pages
			final List<MultiPage> multiPageList = multiPageDelegate.findAll(company).getList();
			for(final MultiPage multiPage : multiPageList)
			{
				final String jspName = multiPage.getJsp();
				final Menu menu = new Menu(multiPage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(multiPage.getJsp(), menu);
			}

			// get the form page
			final List<FormPage> formPageList = formPageDelegate.findAll(company).getList();
			for(final FormPage formPage : formPageList)
			{
				final String jspName = formPage.getJsp();
				final Menu menu = new Menu(formPage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(formPage.getJsp(), menu);
			}

			// get the groups
			final List<Group> groupList = groupDelegate.findAll(company).getList();
			for(final Group group : groupList)
			{
				final String jspName = group.getName().toLowerCase();
				final Menu menu = new Menu(group.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(jspName, menu);
			}

			// get the link to the allowed pages
			for(final String s : ALLOWED_PAGES)
			{
				final String jspName = s.toLowerCase();
				final Menu menu = new Menu(jspName, httpServer + "/" + jspName + ".do");
				menuList.put(jspName, menu);
			}
			request.setAttribute("menu", menuList);
		}
		catch(final Exception e)
		{
			/* logger.fatal("Problem interception action in FrontMenuInterceptor. " + e); */
			System.out.println("Problem interception action in FrontMenuInterceptor. " + e);
		}
	}

	private void loadFeaturedPages(int max)
	{
		final Map<String, Object> featuredPages = new HashMap<String, Object>();
		final List<MultiPage> featuredMultiPage = multiPageDelegate.findAllFeatured(company).getList();

		for(final MultiPage mp : featuredMultiPage)
		{
			if(!mp.getHidden())
			{
				featuredPages.put(mp.getName(), mp);
			}
		}
		// System.out.println("loadFeaturedPages PMC size: " + featuredMultiPage.size());
		request.setAttribute("featuredPages", featuredPages);
	}

	public String changePassword()
	{
		// validate current user, must not be empty
		if(isNull(member))
		{
			return INPUT;
		}

		String newPassword = request.getParameter("newPassword");
		final String confirmPassword = request.getParameter("confirmPassword");

		encryptor = new PasswordEncryptor();
		encryptor.decrypt(member.getPassword());

		// validate new passwords
		if(!newPassword.equals(confirmPassword))
		{
			logger.debug("New passwords dont match.");
			addActionError("New passwords dont match.");
			// System.out.println("passwords does not match");
			return ERROR;
		}

		// encrypt new password
		newPassword = encryptor.encrypt(newPassword);
		// update new member password
		member.setPassword(newPassword);

		// update new member
		member.setUpdatedOn(new Date());
		memberDelegate.update(member);

		logger.debug("newPassword : " + newPassword);

		addActionMessage("Password successfully changed.");
		return SUCCESS;
	}

	public String loadMemberCartOrders()
	{
		if(member == null)
		{
			return INPUT;
		}
		final List<CartOrder> cartOrders = cartOrderDelegate.findAll(company, member);

		if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
		{}

		request.setAttribute("cartOrders", cartOrders);
		
		return SUCCESS;
	}

	public String sendEmailToFriend()
	{
		final String kaptchaReceived = request.getParameter("kaptcha");
		if(StringUtils.isNotEmpty(kaptchaReceived))
		{
			final String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if(kaptchaReceived.equalsIgnoreCase(kaptchaExpected))
			{
				final String emails = StringUtils.trimToNull(request.getParameter("emails"));
				final String message = StringUtils.trimToNull(request.getParameter("message"));
				if(emails != null && message != null)
				{
					final String[] emailArray = emails.split(",");
					if(EmailUtil.hasValidEmails(emailArray))
					{
						final String memberName = StringUtils.isNotEmpty(member.getReg_companyName())
							? member.getReg_companyName()
							: member.getFirstname() + " " + member.getLastname();
						final String companyServerName = company.getServerName();
						final CompanySettings companySettings = companySettingsDelegate.find(company);
						String emailUsername = null;
						String emailPassword = null;
						String smtpHost = null;
						int smtpPort = 25;
						if(companySettings != null)
						{
							emailUsername = companySettings.getEmailUserName();
							emailPassword = companySettings.getEmailPassword();
							smtpHost = companySettings.getSmtp();
							try
							{
								smtpPort = Integer.parseInt(companySettings.getPortNumber());
							}
							catch(final Exception e)
							{
								e.printStackTrace();
							}
						}
						if(!StringUtils.isEmpty(emailUsername) && !StringUtils.isEmpty(emailPassword) && !StringUtils.isEmpty(smtpHost))
						{
							logger.info("Connecting by Email Setting");
							EmailUtil.connect(smtpHost, smtpPort, emailUsername, emailPassword);
						}
						else
						{
							// EmailUtil.connect("smtp.gmail.com", 587, "system@ivant.com","ivanttechnologies2009"); // LOCAL TEST
							logger.info("Connecting by WebToGo Default Setting");
							EmailUtil.connect("smtp.gmail.com", 25);
						}

						final Thread thread = new Thread(new Runnable()
						{
							@Override
							public void run()
							{
								for(final String email : emailArray)
								{
									if(EmailUtil.isEmailValid(email))
									{
										final StringBuilder content = new StringBuilder();

										content.append("Greetings!<br><br>");

										if(!StringUtils.isEmpty(member.getInfo2()))
										{
											content.append("Your friend " + memberName + " has invited you to join Gurkka. ");
										}
										else
										{
											content.append(memberName + " has invited you to join Gurkka. ");
										}
										content.append("To join Gurkka, please visit our <a href=\"http://" + companyServerName + "\"> Gurkka Website</a>. <br><br>");

										content.append(memberName + "'s" + " personal message: <br>");
										content.append(message);
										content.append("<br><br>");

										content.append("All the Best,<br><br>");
										content.append("The " + company.getNameEditable() + " Team");

										logger.info("Sending an Email to: " + email + " ...");
										EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", email, "Gurkka Invitation", content.toString(), null);
									}
								}
							}
						});

						thread.start();

						setNotificationMessage("Your invitation(s) message has been sent.");
						return SUCCESS;
					}
					else
					{
						setNotificationMessage("Email(s) inputted is/are invalid.");
						return ERROR;
					}
				}
			}
		}
		else
		{
			setNotificationMessage("Invalid characters in image.");
		}
		return ERROR;
	}

	public String loadGurkkaRewardItems()
	{
		if(member == null)
		{
			return INPUT;
		}

		try
		{
			// Get all reward items
			final Group rewardItemsGroup = groupDelegate.findByKeyword(company, "Reward");
			if(rewardItemsGroup != null)
			{
				final List<CategoryItem> rewardItems = categoryItemDelegate.findAllByGroup(company, rewardItemsGroup).getList();
				request.setAttribute("rewardItems", rewardItems);
			}
			request.setAttribute("rewardItemsGroup", rewardItemsGroup);


			final HttpSession httpSession = request.getSession();
			final String code = StringUtils.trimToNull(request.getParameter("code"));
				httpSession.removeAttribute("previousMemberType");
				httpSession.removeAttribute("previousMemberTypeName");

			return SUCCESS;
		}
		catch(final Exception e)
		{
			logger.debug("Error setting other fields", e);
		}

		return ERROR;
	}

	public String getNotificationMessage()
	{
		return notificationMessage;
	}

	@Override
	public void setNotificationMessage(String notificationMessage)
	{
		this.notificationMessage = notificationMessage;
	}
	
	public Map<Long, Double> getMemberPointsMap() {
		System.out.println("######################  memberpoints  1   ###################");
		Map<Long, Double> memberPoints = new HashMap<Long, Double>();
		List<CartOrder> listMemberOrder = new ArrayList<CartOrder>();
		listMemberOrder = cartOrderDelegate.findAll(company, member);
		System.out.println("######################  memberpoints  2   ###################");
		if(listMemberOrder != null && listMemberOrder.size() > 0){
			for(CartOrder co : listMemberOrder){
				if(co.getPaymentStatus()==PaymentStatus.PAID && co.getShippingStatus()==ShippingStatus.DELIVERED && co.getStatus() == OrderStatus.COMPLETED){
					memberPoints.put(co.getId(), computeMemberPoints(co, member));
				}
				else{
					memberPoints.put(co.getId(), 0.0);
				}
			}
		}
		System.out.println("###################### Member Points ###########################");
		System.out.println(memberPoints);
		System.out.println("#################################################");
		return memberPoints;
	}
	
	public Double getTotalMemberPoints() {
		Double memberPoints = 0.0;
		List<CartOrder> listMemberOrder = new ArrayList<CartOrder>();
		listMemberOrder = cartOrderDelegate.findAll(company, member);
		
		if(listMemberOrder != null && listMemberOrder.size() > 0){
			for(CartOrder co : listMemberOrder){
				if(co.getPaymentStatus()==PaymentStatus.PAID && co.getShippingStatus()==ShippingStatus.DELIVERED && co.getStatus() == OrderStatus.COMPLETED){
					memberPoints += computeMemberPoints(co, member);
				}
				
			}
		}
		
		return memberPoints;
	}
	
	public static Double computeMemberPoints(CartOrder memberOrder, Member member){
		
		//CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
		CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
		
		Double totalPoints = 0.0;
		Boolean isFirstPurchase = false;
		isFirstPurchase = memberOrder.getFlag1() == null ? false :  memberOrder.getFlag1();
		
		List<CartOrderItem> listCOI = new ArrayList<CartOrderItem>();
		listCOI = cartOrderItemDelegate.findAll(memberOrder).getList();
		if(listCOI != null && listCOI.size() > 0){
			
			// sort the list of cart order items so that main product should be evaluated first
			Collections.sort(listCOI, new Comparator<CartOrderItem>() {
				@Override
				public int compare(CartOrderItem lhs, CartOrderItem rhs){
					try{
						return 1;
					}catch(NullPointerException npe){
						return 1;
					}
					//return 0;
				}
			});
			
			Double totalSoldPrice = 0D;
			
			for(CartOrderItem coi : listCOI){}
				
		}
		
		
		return totalPoints;
	}
	
	public Map<String, Group> getGroupMap() {
		Map<String, Group> groupMap = new HashMap<String, Group>();
		List<Group> listGroup = new ArrayList<Group>();
		listGroup = groupDelegate.findAll(company).getList();
		if(listGroup!=null & listGroup.size()>0){
			for(Group g : listGroup){
				if(g!=null){
					groupMap.put(g.getName(), g);
				}
			}
		}
		return groupMap;
	}

	public Map<Long, CartOrder> getCartOrderMap() {
		Map<Long, CartOrder> cartOrderMap = new HashMap<Long, CartOrder>();
		List<CartOrder> listMemberCartOrder = cartOrderDelegate.findAll(company, member);
		if(listMemberCartOrder.size() > 0){
			for(CartOrder co : listMemberCartOrder){
				cartOrderMap.put(co.getId(), co);
			}
		}
		return cartOrderMap;
	}
}
