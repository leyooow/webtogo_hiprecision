/**
 *
 */
package com.ivant.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.BrandDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.CategoryItemPriceDelegate;
import com.ivant.cms.delegate.CategoryItemPriceNameDelegate;
import com.ivant.cms.delegate.CompanySettingsDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.CategoryItemPrice;
import com.ivant.cms.entity.CategoryItemPriceName;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.enums.PageType;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.hibernate.HibernateUtil;

/** 
 * @author Edgar S. Dacpano
 */
public class CompanyUtil
{
	private static final Logger logger = Logger.getLogger(CompanyUtil.class);
	
	/** Cannot be instantiated */
	private CompanyUtil()
	{
	}
	
	public static final List<String> ALLOWED_PAGES;
	static
	{
		ALLOWED_PAGES = new ArrayList<String>();
		ALLOWED_PAGES.add("sitemap");
		ALLOWED_PAGES.add("printerfriendly");
		ALLOWED_PAGES.add("brands");
		ALLOWED_PAGES.add("calendarevents");
		ALLOWED_PAGES.add("cart");
	}
	
	public static final int[] WITH_MOBILE_SITES = 
	{ 
		//CompanyConstants.BOYSEN, 
		CompanyConstants.HIPRECISION_ID,
		CompanyConstants.PILIPINAS_BRONZE, 
		CompanyConstants.FROSCH, 
		CompanyConstants.VIRTUOSO,
		CompanyConstants.HEALTHY_HOME_PAINTS, 
		CompanyConstants.MR_AIRCON_ID, 
		CompanyConstants.FRIDAYS_BORACAY, 
		CompanyConstants.HIPRECISION_ID_NEW, 
		CompanyConstants.SMART_DOCS,
		CompanyConstants.GURKKA,
		CompanyConstants.MY_DEALS,
		CompanyConstants.FRUITMAGIC,
		CompanyConstants.PRGAZ,
		CompanyConstants.TOMATO,
		CompanyConstants.HEALTHONLINEASIA
	};
	
	private static final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private static final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private static final FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private static final BrandDelegate brandDelegate = BrandDelegate.getInstance();
	private static final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private static final OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	private static final CategoryItemPriceNameDelegate categoryItemPriceNameDelegate = CategoryItemPriceNameDelegate.getInstance();
	private static final CategoryItemPriceDelegate categoryItemPriceDelegate = CategoryItemPriceDelegate.getInstance();
	private static final CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	private static final CompanySettingsDelegate companySettingsDelegate = CompanySettingsDelegate.getInstance();
	
	public static final boolean hasMobileSite(Company company)
	{
		boolean hasMobileSite = false;
		try
		{
			if(company != null)
			{
				final Long companyId = company.getId();
				
				if(company.getCompanySettings() != null)
				{
					final CompanySettings companySettings = companySettingsDelegate.find(company.getCompanySettings().getId()); 
					hasMobileSite = companySettings == null ? Boolean.FALSE : companySettings.getHasMobileSite();
				}
				
				if(!hasMobileSite) // if still false, check WITH_MOBILE_SITES
				{
					for(final int i : WITH_MOBILE_SITES)
					{
						if(i == companyId)
						{
							hasMobileSite = true;
						}
					}
				}
			}
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
		return hasMobileSite;
	}
	
	public static void loadMenu(Company company, String httpServer, HttpServletRequest request, Language language, Logger logger, ServletContext servletContext, boolean isLocal)
	{
		try
		{
			httpServer = (isLocal)
				? ("http://" + request.getServerName() + ":" + request.getServerPort() + "/" + servletContext.getServletContextName() + "/companies/" + company.getDomainName())
				: ("http://" + request.getServerName());
			
			final Map<String, Menu> menuList = new HashMap<String, Menu>();
			
			// get the single pages
			final List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
			for(final SinglePage singlePage : singlePageList)
			{
				if(language != null)
				{
					singlePage.setLanguage(language);
				}
				final String jspName = singlePage.getJsp();
				final Menu menu = new Menu(singlePage.getName(), PageType.SINGLEPAGE.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(singlePage.getJsp(), menu);
			}
			
			// get the multi pages
			final List<MultiPage> multiPageList = multiPageDelegate.findAll(company).getList();
			request.setAttribute("multiPageList", multiPageList);
			
			for(final MultiPage multiPage : multiPageList)
			{
				if(language != null)
				{
					multiPage.setLanguage(language);
				}
				final String jspName = multiPage.getJsp();
				final Menu menu = new Menu(multiPage.getName(), PageType.MULTIPAGE.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(multiPage.getJsp(), menu);
			}
			
			// get the form Pages
			final List<FormPage> formPageList = formPageDelegate.findAll(company).getList();
			for(final FormPage formPage : formPageList)
			{
				final String jspName = formPage.getJsp();
				final Menu menu = new Menu(formPage.getName(), PageType.FORMPAGE.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(formPage.getJsp(), menu);
			}
			
			// get the groups
			final List<Group> groupList = groupDelegate.findAll(company).getList();
			request.setAttribute("groupList", groupList);
			for(final Group group : groupList)
			{
				final String jspName = group.getName().toLowerCase();
				final Menu menu = new Menu(group.getName(), PageType.GROUP.getName(), httpServer + "/" + jspName + ".do");
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
			if(logger != null)
			{
				logger.fatal("problem intercepting action in " + logger.getName() + ". " + e);
			}
			else
			{
				CompanyUtil.logger.fatal("problem intercepting action");
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * Copy the pages & groups to another company, use with caution.
	 * 
	 * @param fromCompany - source
	 * @param toCompany - destination
	 * @throws Exception
	 */
	public static final synchronized boolean copyCompanyPagesAndGroups(final Company fromCompany, final Company toCompany)
	{
		try
		{
			/**
			 * keywords: 'from*' - objects from 'fromCompany'(source)
			 * 'to*' - objects from 'toCompany'(destination)
			 */
			
			/** Default order */
			final Order[] orders = new Order[] { Order.asc("id") };
			
			/** Copy multipages */
			final List<MultiPage> multiPages = multiPageDelegate.findAll(fromCompany).getList();
			if(multiPages != null && !multiPages.isEmpty())
			{
				final Iterator<MultiPage> i = multiPages.iterator();
				while(i.hasNext())
				{
					final MultiPage fromMultiPage = (MultiPage) i.next();
					final Long fromMultiPageId = fromMultiPage.getId();
					
					MultiPage toMultiPage = multiPageDelegate.find(toCompany, fromMultiPage.getName());
					if(toMultiPage == null)
					{
						HibernateUtil.currentSession().clear();
						fromMultiPage.setCompany(toCompany);
						setListToNull(fromMultiPage);
						toMultiPage = multiPageDelegate.find(multiPageDelegate.insert(fromMultiPage));
					}
					
					if(toMultiPage != null)
					{
						/** Copy children (single pages) */
						final List<SinglePage> singlePages = singlePageDelegate.findAllWithPaging(fromCompany, multiPageDelegate.find(fromMultiPageId), -1, -1).getList();
						if(singlePages != null && !singlePages.isEmpty())
						{
							for(SinglePage fromSinglePage : singlePages)
							{
								HibernateUtil.currentSession().clear();
								fromSinglePage.setCompany(toCompany);
								fromSinglePage.setParent(toMultiPage);
								fromSinglePage.setImages(null);
								setListToNull(fromSinglePage);
								singlePageDelegate.insert(fromSinglePage);
							}
						}
					}
					
				}
			}
			
			/** Copy single pages (no parent multi page) */
			final List<SinglePage> singlePages = singlePageDelegate.findAll(fromCompany, null, orders).getList();
			if(singlePages != null && !singlePages.isEmpty())
			{
				final Iterator<SinglePage> i = singlePages.iterator();
				while(i.hasNext())
				{
					final SinglePage fromSinglePage = (SinglePage) i.next();
					if(singlePageDelegate.find(toCompany, fromSinglePage.getName()) == null)
					{
						HibernateUtil.currentSession().clear();
						fromSinglePage.setCompany(toCompany);
						fromSinglePage.setImages(null);
						setListToNull(fromSinglePage);
						singlePageDelegate.insert(fromSinglePage);
					}
					
				}
			}
			
			/** Copy form pages */
			final List<FormPage> formPages = formPageDelegate.findAll(fromCompany).getList();
			if(formPages != null && !formPages.isEmpty())
			{
				final Iterator<FormPage> i = formPages.iterator();
				while(i.hasNext())
				{
					final FormPage fromFormPage = (FormPage) i.next();
					if(formPageDelegate.find(toCompany, fromFormPage.getName()) == null)
					{
						HibernateUtil.currentSession().clear();
						fromFormPage.setCompany(toCompany);
						setListToNull(fromFormPage);
						formPageDelegate.insert(fromFormPage);
					}
					
				}
			}
			
			/** Copy groups */
			final List<Group> groups = groupDelegate.findAll(fromCompany).getList();
			if(groups != null && !groups.isEmpty())
			{
				final Iterator<Group> i = groups.iterator();
				while(i.hasNext())
				{
					final Group fromGroup = (Group) i.next();
					final Long groupId = fromGroup.getId();
					final List<Category> categories = categoryDelegate.findAllWithPaging(fromCompany, fromGroup, -1, -1, true).getList();
					final List<Brand> brands = brandDelegate.findAll(fromCompany, fromGroup).getList();
					
					Group toGroup = groupDelegate.find(toCompany, fromGroup.getName());
					if(toGroup == null)
					{
						HibernateUtil.currentSession().clear();
						fromGroup.setCompany(toCompany);
						setListToNull(fromGroup);
						toGroup = groupDelegate.find(groupDelegate.insert(fromGroup));
					}
					
					if(toGroup != null)
					{
						/** Copy category item price names */
						final List<CategoryItemPriceName> priceNames = categoryItemPriceNameDelegate.findByGroup(groupDelegate.find(groupId));
						if(priceNames != null && !priceNames.isEmpty())
						{
							for(CategoryItemPriceName fromCipn : priceNames)
							{
								CategoryItemPriceName toCipn = categoryItemPriceNameDelegate.findByName(fromCipn.getName(), toGroup);
								if(toCipn == null)
								{
									HibernateUtil.currentSession().clear();
									fromCipn.setCompany(toCompany);
									fromCipn.setGroup(toGroup);
									setListToNull(fromCipn);
									categoryItemPriceNameDelegate.insert(fromCipn);
									// toCipn = categoryItemPriceNameDelegate.find(categoryItemPriceNameDelegate.insert(fromCipn));
								}
							}
						}
						
						/** Copy other fields */
						final List<OtherField> otherFields = otherFieldDelegate.findByGroup(fromCompany, groupDelegate.find(groupId));
						if(otherFields != null && !otherFields.isEmpty())
						{
							for(OtherField fromOtherField : otherFields)
							{
								OtherField toOtherField = otherFieldDelegate.find(fromOtherField.getName(), toCompany);
								if(toOtherField == null)
								{
									HibernateUtil.currentSession().clear();
									fromOtherField.setCompany(toCompany);
									fromOtherField.setGroup(toGroup);
									setListToNull(fromOtherField);
									otherFieldDelegate.insert(fromOtherField);
								}
							}
						}
						
						/** Copy brands from this group */
						if(brands != null && !brands.isEmpty())
						{
							for(Brand fromBrand : brands)
							{
								final Brand toBrand = brandDelegate.find(toCompany, toGroup, fromBrand.getName());
								if(toBrand == null)
								{
									HibernateUtil.currentSession().clear();
									fromBrand.setCompany(toCompany);
									fromBrand.setGroup(toGroup);
									setListToNull(fromBrand);
									brandDelegate.insert(fromBrand);
								}
							}
						}
						
						/** Copy categories from this group */
						if(categories != null && !categories.isEmpty())
						{
							for(Category fromCategory : categories)
							{
								final Long categoryId = fromCategory.getId();
								final Long parentCategoryId = fromCategory.getParentCategory() == null
									? null
									: fromCategory.getParentCategory().getId();
								final Category fromParentCategory = categoryDelegate.find(parentCategoryId);
								final Category toParentCategory = fromParentCategory == null
									? null
									: categoryDelegate.find(toCompany, fromParentCategory.getName(), toGroup);
								
								Category toCategory = categoryDelegate.find(toCompany, fromCategory.getName(), toParentCategory, toGroup);
								if(toCategory == null)
								{
									HibernateUtil.currentSession().clear();
									fromCategory.setCompany(toCompany);
									fromCategory.setParentGroup(toGroup);
									setListToNull(fromCategory);
									toCategory = categoryDelegate.find(categoryDelegate.insert(fromCategory));
								}
								
								if(toCategory != null)
								{
									Category newParent = null;
									if(parentCategoryId != null)
									{
										newParent = categoryDelegate.find(toCompany, fromParentCategory.getName(), toGroup);
										if(newParent == null)
										{
											HibernateUtil.currentSession().clear();
											fromParentCategory.setCompany(toCompany);
											fromParentCategory.setParentGroup(toGroup);
											setListToNull(fromParentCategory);
											newParent = categoryDelegate.find(categoryDelegate.insert(fromParentCategory));
										}
									}
									if(newParent != null)
									{
										toCategory.setParentCategory(newParent);
										categoryDelegate.update(toCategory);
										categoryDelegate.refresh(toCategory);
									}
									copyCategoryItems(fromCompany, toCompany, groupDelegate.find(groupId), toGroup, categoryDelegate.find(categoryId), toCategory);
								}
							}
						}
					}
				}
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Copy the items from 'fromCategory'(source) to 'toCategory'
	 * 
	 * @param fromCompany
	 * @param toCompany
	 * @param fromGroup
	 * @param toGroup
	 * @param fromCategory
	 * @param toCategory
	 */
	private static void copyCategoryItems(Company fromCompany, Company toCompany, Group fromGroup, Group toGroup, Category fromCategory, Category toCategory)
	{
		final List<CategoryItem> fromItems = categoryItemDelegate.findAll(fromCompany, fromCategory, false).getList();
		for(CategoryItem fromItem : fromItems)
		{
			final Long fromItemId = fromItem.getId();
			final Brand brand = fromItem.getBrand() == null
				? null
				: brandDelegate.find(fromItem.getBrand().getId());
			final String brandName = brand == null
				? null
				: brand.getName();
			
			CategoryItem toItem = categoryItemDelegate.find(toCompany, toCategory, fromItem.getName());
			if(toItem == null)
			{
				HibernateUtil.currentSession().clear();
				fromItem.setCompany(toCompany);
				fromItem.setParent(toCategory);
				fromItem.setParentGroup(toGroup);
				if(StringUtils.isNotEmpty(brandName))
				{
					fromItem.setBrand(brandDelegate.find(toCompany, toGroup, brandName));
				}
				setListToNull(fromItem);
				toItem = categoryItemDelegate.find(categoryItemDelegate.insert(fromItem));
			}
			
			if(toItem != null && fromItemId != null)
			{
				fromItem = categoryItemDelegate.find(fromItemId);
				
				/** Copy prices */
				final List<CategoryItemPriceName> priceNames = categoryItemPriceNameDelegate.findByGroup(fromGroup);
				if(priceNames != null && !priceNames.isEmpty())
				{
					for(CategoryItemPriceName fromCipn : priceNames)
					{
						final String fromCipnName = fromCipn.getName();
						
						final List<CategoryItemPrice> prices = categoryItemPriceDelegate.findAllByCategoryItem(fromCompany, fromItem).getList();
						if(prices != null && !prices.isEmpty())
						{
							for(CategoryItemPrice fromCip : prices)
							{
								final CategoryItemPriceName toCipn = categoryItemPriceNameDelegate.findByName(fromCipnName, toGroup);
								if(toCipn != null)
								{
									HibernateUtil.currentSession().clear();
									fromCip.setCategoryItemPriceName(toCipn);
									fromCip.setCategoryItem(toItem);
									fromCip.setCompany(toCompany);
									setListToNull(fromCip);
									categoryItemPriceDelegate.insert(fromCip);
								}
							}
						}
					}
				}
				
				/** Copy other fields */
				final List<CategoryItemOtherField> ciof = categoryItemOtherFieldDelegate.findAll(fromCompany, fromItem).getList();
				if(ciof != null)
				{
					for(CategoryItemOtherField fromCiof : ciof)
					{
						final OtherField fromOtherField = otherFieldDelegate.find(fromCiof.getOtherField().getId());
						if(fromOtherField != null)
						{
							final String otherFieldName = fromOtherField.getName();
							final OtherField toOtherField = otherFieldDelegate.find(otherFieldName, toCompany);
							if(toOtherField != null)
							{
								CategoryItemOtherField toCiof = categoryItemOtherFieldDelegate.findByOtherFieldName(toCompany, toItem, otherFieldName);
								if(toCiof == null)
								{
									HibernateUtil.currentSession().clear();
									fromCiof.setCategoryItem(toItem);
									fromCiof.setCompany(toCompany);
									fromCiof.setOtherField(toOtherField);
									setListToNull(fromCiof);
									categoryItemOtherFieldDelegate.insert(fromCiof);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private static <E> void setListToNull(E entry)
	{
		final Method[] methods = entry.getClass().getDeclaredMethods();
		if(methods != null && methods.length > 0)
		{
			for(Method method : methods)
			{
				if(method.getName().contains("set"))
				{
					final Class<?>[] clazzes = method.getParameterTypes();
					naTayo: for(Class<?> clazz : clazzes)
					{
						if(clazz.getName().contains("List") || clazz.equals(List.class))
						{
							try
							{
								final Object objs = null;
								method.invoke(entry, objs);
								break naTayo;
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	
}
