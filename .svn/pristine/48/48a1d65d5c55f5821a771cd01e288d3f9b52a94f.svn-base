package com.ivant.cms.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.PromoDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.Promo;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class PromoAction extends ActionSupport implements Preparable, ServletRequestAware, UserAware, CompanyAware {

	private static final long serialVersionUID = 4048368734861819318L;
	private Logger logger = Logger.getLogger(getClass());
	private PromoDelegate promoDelegate = PromoDelegate.getInstance();
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private Promo promo;
	private Company company;
	private User user;
	private HttpServletRequest request;
	private Long id;
	private List<Promo> promos;
	private List<String> promoItems;
	private List<SinglePage> singlePages;
	private Group group;
	
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		try {
			
			id = Long.parseLong(request.getParameter("promoId"));
			promo = promoDelegate.find(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String execute() throws Exception {
		return Action.NONE;
	}
	
	private boolean commonParamsValid() {
		if(user.getCompany() == null) {
			return false;
		}		
	
		return true;
	}
	
	public String editPromo() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}

		if(!promo.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}

		promo = promoDelegate.find(id);
		
		if(CompanyConstants.LIFE == company.getId()) {
			if(promo.getName().equalsIgnoreCase("Events")) {
				MultiPage multiPage = multiPageDelegate.findJsp(company, "events");
				singlePages = multiPage.getItems();
			}
			if(promo.getName().equalsIgnoreCase("Packages")) {
				setGroup(groupDelegate.find(company, "Packages"));
			}
		}

		return Action.SUCCESS;
	}
	
	public String savePromo() {
		
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		try {
			
			promo.setChecked(new Boolean(request.getParameter("checked")));
			promo.setContent(request.getParameter("content"));
			promoDelegate.update(promo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
		
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public Promo getPromo() {
		return promo;
	}
	public void setPromo(Promo promo) {
		this.promo = promo;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Promo> getPromos() {
		return promos;
	}
	public void setPromos(List<Promo> promos) {
		this.promos = promos;
	}

	public List<String> getPromoItems() {
		return promoItems;
	}

	public void setPromoItems(List<String> promoItems) {
		this.promoItems = promoItems;
	}

	public List<SinglePage> getSinglePages() {
		return singlePages;
	}

	public void setSinglePages(List<SinglePage> singlePages) {
		this.singlePages = singlePages;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
}
