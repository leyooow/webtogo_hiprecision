package com.ivant.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.utils.SiteMapGenerator;

/**
 * Loads the Menu
 * @author Angel
 *
 */
public class MenuLoaderAction extends BaseAction {
	private static final long serialVersionUID = -4707179782774190947L;
	private static final List<String> ALLOWED_PAGES;

	static {
		ALLOWED_PAGES = new ArrayList<String>();
		ALLOWED_PAGES.add("sitemap");
		ALLOWED_PAGES.add("printerfriendly");
		ALLOWED_PAGES.add("brands");
		ALLOWED_PAGES.add("calendarevents");
		ALLOWED_PAGES.add("cart");
	}

	private Logger logger = Logger.getLogger(getClass());
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate
			.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate
			.getInstance();
	private FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private String navMenu;

	protected void loadSiteMenu() {
		createSitemapGenerator();
		loadMenu();
	}

	private void createSitemapGenerator() {
		logger.debug("createSitemapGenerator");
		request.setAttribute("sitemapGenerator", new SiteMapGenerator(
				currentCompany));
	}

	private void loadMenu() {
		try {
			Map<String, Menu> menuList = new HashMap<String, Menu>();

			// get the single pages
			List<SinglePage> singlePageList = singlePageDelegate.findAll(
					currentCompany).getList();
			for (SinglePage singlePage : singlePageList) {
				String jspName = singlePage.getJsp();
				Menu menu = new Menu(singlePage.getName(), httpServer + "/"
						+ jspName + ".do");
				menuList.put(singlePage.getJsp(), menu);
			}

			// get the multi pages
			List<MultiPage> multiPageList = multiPageDelegate.findAll(
					currentCompany).getList();
			for (MultiPage multiPage : multiPageList) {
				String jspName = multiPage.getJsp();
				Menu menu = new Menu(multiPage.getName(), httpServer + "/"
						+ jspName + ".do");
				menuList.put(multiPage.getJsp(), menu);
			}

			// get the form Pages
			List<FormPage> formPageList = formPageDelegate.findAll(
					currentCompany).getList();
			for (FormPage formPage : formPageList) {
				String jspName = formPage.getJsp();
				Menu menu = new Menu(formPage.getName(), httpServer + "/"
						+ jspName + ".do");
				menuList.put(formPage.getJsp(), menu);
			}

			// get the groups
			List<Group> groupList = groupDelegate.findAll(currentCompany)
					.getList();
			request.setAttribute("groupList", groupList);
			for (Group group : groupList) {
				String jspName = group.getName().toLowerCase();
				Menu menu = new Menu(group.getName(), httpServer + "/"
						+ jspName + ".do");
				menuList.put(jspName, menu);
			}

			// get the link to the allowed pages
			for (String s : ALLOWED_PAGES) {
				String jspName = s.toLowerCase();
				Menu menu = new Menu(jspName, httpServer + "/" + jspName
						+ ".do");
				menuList.put(jspName, menu);
			}

			request.setAttribute("menu", menuList);
		} catch (Exception e) {
			logger
					.fatal("problem intercepting action in FrontMenuInterceptor. "
							+ e);
		}
	}

	/**
	 * @return the navMenu
	 */
	public String getNavMenu() {
		return navMenu;
	}

	/**
	 * @param navMenu
	 *            the navMenu to set
	 */
	public void setNavMenu(String navMenu) {
		this.navMenu = navMenu;
	}

}
