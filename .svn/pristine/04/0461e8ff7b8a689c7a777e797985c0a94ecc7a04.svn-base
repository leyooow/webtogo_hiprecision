package com.ivant.cms.action.admin.dwr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;

import com.ivant.cms.delegate.BrandDelegate;
import com.ivant.cms.delegate.BrandImageDelegate;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryFileDelegate;
import com.ivant.cms.delegate.CategoryImageDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.GroupImageDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.delegate.ItemFileDelegate;
import com.ivant.cms.delegate.ItemImageDelegate;
import com.ivant.cms.delegate.ItemVariationDelegate;
import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
import com.ivant.cms.delegate.MemberLogDelegate;
import com.ivant.cms.delegate.MessageDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.MultiPageImageDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.PageImageDelegate;
import com.ivant.cms.delegate.PreOrderDelegate;
import com.ivant.cms.delegate.PreOrderItemDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.delegate.VariationDelegate;
import com.ivant.cms.delegate.VariationGroupDelegate;
import com.ivant.cms.delegate.WishlistDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.User;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interceptors.UserInterceptor;

public class AbstractDWRAction {

	protected static final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	protected static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	protected static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	protected static final MessageDelegate messageDelegate = MessageDelegate.getInstance();
	protected static final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	protected static final UserDelegate userDelegate = UserDelegate.getInstance();
	protected static final PageImageDelegate pageImageDelegate = PageImageDelegate.getInstance();
	protected static final ItemImageDelegate itemImageDelegate = ItemImageDelegate.getInstance();
	protected static final ItemFileDelegate itemFileDelegate = ItemFileDelegate.getInstance();
	protected static final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	protected static final BrandDelegate brandDelegate = BrandDelegate.getInstance();
	protected static final CategoryImageDelegate categoryImageDelegate = CategoryImageDelegate.getInstance();	
	protected static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	protected static final VariationGroupDelegate variationGroupDelegate = VariationGroupDelegate.getInstance();
	protected static final VariationDelegate varationDelegate = VariationDelegate.getInstance();
	protected static final ItemVariationDelegate itemVariationDelegate = ItemVariationDelegate.getInstance();
	protected static final ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();
	protected static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	protected static final ShoppingCartDelegate shoppingCartDelegate = ShoppingCartDelegate.getInstance();
	protected static final CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	protected static final CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	protected static final WishlistDelegate wishlistDelegate = WishlistDelegate.getInstance();
	protected static final MultiPageImageDelegate multiPageImageDelegate = MultiPageImageDelegate.getInstance();
	protected static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	protected static final PreOrderDelegate preOrderDelegate = PreOrderDelegate.getInstance();
	protected static final PreOrderItemDelegate preOrderItemDelegate = PreOrderItemDelegate.getInstance();
	protected static final MemberFileDelegate memberFileDelegate=new MemberFileDelegate(); //for apc
	protected static final LanguageDelegate languageDelegate = LanguageDelegate.getInstance();
	protected static final ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate.getInstance();
	protected static final GroupImageDelegate groupImageDelegate = GroupImageDelegate.getInstance();
	protected static final CategoryFileDelegate categoryFileDelegate = CategoryFileDelegate.getInstance();
	protected static final MemberLogDelegate memberLogDelegate = MemberLogDelegate.getInstance();
	protected static final MemberFileItemDelegate memberFileItemDelegate = MemberFileItemDelegate.getInstance();
	protected static final PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	protected static final OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	protected static final CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	protected static final BrandImageDelegate brandImageDelegate = BrandImageDelegate.getInstance();
	
	protected HttpSession session;
	protected HttpServletRequest request;
	protected User user;
	protected Company company;
	protected Member member;
	
	public AbstractDWRAction() {
		request = WebContextFactory.get().getHttpServletRequest();
		session = request.getSession();
		user = getUser();
		company = getCompany();
	}
	
	public User getUser() {
		Object sessUserId = session.getAttribute(UserInterceptor.USER_SESSION_KEY);
		if(sessUserId != null) {
			long userId = (Long)sessUserId;
			return userDelegate.find(userId);
		}
		
		return null;
	}
	
	public Company getCompany() {
		Object sessCompanyId = session.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		if(sessCompanyId != null) {
			long companyId = (Long) sessCompanyId;
			Company company = companyDelegate.find(companyId);
			return company;
		}
		
		return null;
	}
	
	public Member getMember() {
		Object sessMemberId = session.getAttribute(MemberInterceptor.MEMBER_REQUEST_KEY);
		if(sessMemberId != null) {
			long memberId = (Long) sessMemberId;
			Member member = memberDelegate.find(memberId);			
			return member;
		}
		
		return null;
	}
}
