package com.ivant.cms.db;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.joda.time.DateTime;

import com.ivant.cms.beans.CountBean;
import com.ivant.cms.beans.NameBean;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;
import com.ivant.cms.helper.ClassHelper;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.CategoryItemPackageWrapper;
import com.ivant.utils.hibernate.HibernateUtil;

public class CategoryItemDAO extends AbstractBaseDAO<CategoryItem> {
	
	CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private static final Logger logger = Logger.getLogger(CategoryItemDAO.class);
	
	public CategoryItemDAO() {
		super();
	} 
	
	public CategoryItem find(Boolean isValid, Long id)
	{
		try
		{
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", isValid));
			junc.add(Restrictions.eq("id", id));
			return findAllByCriterion(null, null, null, null, null, null, junc).getList().get(0);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public ObjectList<CategoryItem> findAll(int page, int maxResults, Order[] orders, Group parentGroup, Company company ) {
		ObjectList<CategoryItem> items = null;
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
//		if(parentGroup != null)
			junc.add(Restrictions.eq("parentGroup", parentGroup));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		try {
			items = findAllByCriterion(page, maxResults, null, null, null, orders, junc);
		}
		catch(Exception e) {}
		
		return items;
	}
	
	public ObjectList<CategoryItem> findDescItems(Company company, List<Category> parentList, int resultPerPage, int pageNumber, Order...orders) {
		ObjectList<CategoryItem> items = null;
		
		//System.out.println("dao itemsPerPage: " + resultPerPage);
		//System.out.println("dao pageNumber: " + pageNumber);
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		if(parentList != null && !parentList.isEmpty()){
			junc.add(Restrictions.in("parent", parentList));
		}

		try {
			items = findAllByCriterion(null, null, null, orders, junc);
		}
		catch(Exception e) {}
		
		return items;
	}
	
	public int findDescItemsSize(Company company, List<Category> parentList, int resultPerPage, int pageNumber, Order...orders) {
		ObjectList<CategoryItem> items = null;
		
		//System.out.println("dao itemsPerPage: " + resultPerPage);
		//System.out.println("dao pageNumber: " + pageNumber);
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		if(parentList != null && !parentList.isEmpty()){
			junc.add(Restrictions.in("parent", parentList));
		}

		try {
			items = findAllByCriterion(null, null, null, orders, junc);
		}
		catch(Exception e) {}
		
		return items.getSize();
	}
	
	public ObjectList<CategoryItem> findDescItemsWithPaging(Company company, List<Category> parentList, int resultPerPage, int pageNumber, Order...orders) {
		ObjectList<CategoryItem> items = null;
		
		//System.out.println("dao itemsPerPage: " + resultPerPage);
		//System.out.println("dao pageNumber: " + pageNumber);
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		if(parentList != null && !parentList.isEmpty()){
			junc.add(Restrictions.in("parent", parentList));
		}
		
		//HBC filtering out of stock items
		if(company.getId() == 104 || company.getId()==CompanyConstants.DRUGASIA) 
			junc.add(Restrictions.eq("isOutOfStock", Boolean.FALSE));

		try {
			items = findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
		}
		catch(Exception e) {}
		
		return items;
	}
	
	public CategoryItem findByGroupAndName(Company company, Group parentGroup, String name){
		CategoryItem item = new CategoryItem();
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", parentGroup));
		junc.add(Restrictions.eq("name", name));
		try{
			item = findAllByCriterion(null, null, null, null, null, null, junc).getList().get(0);
		}
		catch(Exception e){}
		return item;
	}
	
	public CategoryItem find(Company company, Category parent, String name) {
		CategoryItem item = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("name", name));
		if(parent == null) {
			junc.add(Restrictions.isNull("parent"));
		}
		else {
			junc.add(Restrictions.eq("parent", parent));
		}
		
		try {
			item = findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return item;
	}
	
	public CategoryItem findDuplicate(Company company, String name, String sku, Category parent, Group group){
		CategoryItem item = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("name", name));
		junc.add(Restrictions.eq("sku", sku));
		
		junc.add(Restrictions.eq("parentGroup", group));
		
		if(parent == null) {
			junc.add(Restrictions.isNull("parent"));
		}
		else {
			junc.add(Restrictions.eq("parent", parent));
		}
		
		try {
			item = findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return item;
	}
	
	public CategoryItem findContainsName(Company company, String name) {
		CategoryItem item = null;
		List<CategoryItem> itemList = new ArrayList<CategoryItem>();
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		//junc.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		junc.add(Restrictions.eq("name", name));
		
		
		return findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		
		//return item;
	}
	
	public CategoryItem findSKU(Company company, String sku) {
		CategoryItem item = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("sku", sku));
		if(company.getName().equals("pocketpons")){
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		}
		try {
			item = findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return item;
	}
	
	public CategoryItem findByName(Company company, String name) {
		CategoryItem item = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("name", name));

		try {
			item = findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return item;
	}
	
	public CategoryItem findByArticle(Company company, String article) {
		CategoryItem item = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("description", article));

		try {
			item = findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return item;
	}
	
	public ObjectList<CategoryItem> findAll(Company company, Category parent, Order[] order, boolean showAll) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		if(parent != null) {
			junc.add(Restrictions.eq("parent", parent));
		}
		else {
			if(!showAll) {
				junc.add(Restrictions.isNull("parent"));
			}
		}
		
		return findAllByCriterion(null, null, null, order, junc);
	}
	
	public ObjectList<CategoryItem> findAll(Company company, Category parent, Order[] order, boolean showAll, boolean disabled) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", disabled));
		if(parent != null) {
			junc.add(Restrictions.eq("parent", parent));
		}
		else {
			if(!showAll) {
				junc.add(Restrictions.isNull("parent"));
			}
		}
		
		return findAllByCriterion(null, null, null, order, junc);
	}
	
	public ObjectList<CategoryItem> findAllByTags(Company company, String tag) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.like("searchTags", tag,MatchMode.ANYWHERE));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<CategoryItem> findTagItemsWithPaging(Company company, String tag, int resultPerPage, int pageNumber, Order...orders) {
		ObjectList<CategoryItem> items = null;
		
		//System.out.println("dao itemsPerPage: " + resultPerPage);
		//System.out.println("dao pageNumber: " + pageNumber);
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.like("searchTags", tag,MatchMode.ANYWHERE));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		try {
			items = findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
		}
		catch(Exception e) {}
		
		return items;
	}
	
	public ObjectList<CategoryItem> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		/*if(company.getId()==152){		//hard code for hi precision
			GroupDelegate groupDelegate = GroupDelegate.getInstance();
			junc.add(Restrictions.eq("parentGroup", groupDelegate.find((long) 160)));
		}*/
		
		Order[] orders = {Order.asc("name")};
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<CategoryItem> findAllEnabled(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		Order[] orders = {Order.asc("name")};
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<CategoryItem> findAllSortedByDate(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		/*if(company.getId()==152){		//hard code for hi precision
			GroupDelegate groupDelegate = GroupDelegate.getInstance();
			junc.add(Restrictions.eq("parentGroup", groupDelegate.find((long) 160)));
		}*/
		
		Order[] orders = {Order.asc("createdOn")};
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<CategoryItem> findAllEnabledWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}
	
	public ObjectList<CategoryItem> findAllWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}
	
	public ObjectList<CategoryItem> findAllEnabledNotFreebiesWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}
	
	public ObjectList<CategoryItem> findAllEnabledFreebiesWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}
	
	public ObjectList<CategoryItem> findAllDisabledWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.TRUE));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, null, junc);
	}
	
	public CategoryItem findBySearchTagsContains(Company company, Group group, String searchTagContains) {
		CategoryItem item = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		junc.add(Restrictions.ilike("searchTags", searchTagContains, MatchMode.ANYWHERE));

		try {
			item = findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return item;
	}
	
	public ObjectList<CategoryItem> findByNameContainsOrSearchTagsContains(Company company, Group group, String sku, String keyword) {
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		junc.add(Restrictions.or(
				Restrictions.ilike("name", keyword, MatchMode.ANYWHERE),
				Restrictions.ilike("searchTags", keyword, MatchMode.ANYWHERE)
		));
		
		if(sku != null){
			junc.add(Restrictions.eq("sku", sku));
		}

		try {
			return findAllByCriterion(null, null, null, null, null,	null, junc);
		}
		catch(Exception e) {}
		
		return null;
	}
	
	public ObjectList<CategoryItem> findByNameContainsOrSearchTagsContains(Company company, String keyword) {
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		junc.add(Restrictions.or(
				Restrictions.ilike("name", keyword, MatchMode.ANYWHERE),
				Restrictions.ilike("searchTags", keyword, MatchMode.ANYWHERE)
		));
		
		try {
			return findAllByCriterion(null, null, null, null, null,	null, junc);
		}
		catch(Exception e) {}
		
		return null;
	}
	
    public ObjectList<CategoryItem> findAllByGroupAndKeywords(Company company, Group group, List<String> keywords) {
		
		Junction junc = Restrictions.conjunction();
		Disjunction disjunc = Restrictions.disjunction();
		Disjunction disjunc2 = Restrictions.disjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		if(keywords.size() > 0){
		   for(String keyword : keywords){
			   
			   disjunc.add(Restrictions.ilike("name", keyword, MatchMode.ANYWHERE));
		       //disjunc.add(Restrictions.ilike("searchTags", keyword, MatchMode.ANYWHERE));
		       disjunc.add(Restrictions.ilike("shortDescription", keyword, MatchMode.ANYWHERE));
		       disjunc.add(Restrictions.ilike("description", keyword, MatchMode.ANYWHERE));
		       disjunc.add(Restrictions.ilike("otherDetails", keyword, MatchMode.ANYWHERE));
		       junc.add(disjunc);
		   }
		   
		}
		

		try {
			return findAllByCriterion(null, null, null, null, null,	null, junc);
		}
		catch(Exception e) {}
		
		return null;
	}
	
    public ObjectList<CategoryItem> findByFullContentContains(Company company, Group group, String sku, String keyword) {
		
		Junction junc = Restrictions.conjunction();
		
		Disjunction disjunc = Restrictions.disjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		/*
		junc.add(Restrictions.or(
				Restrictions.ilike("name", keyword, MatchMode.ANYWHERE),
				Restrictions.ilike("searchTags", keyword, MatchMode.ANYWHERE)
		));
		*/
		if(sku != null){
			junc.add(Restrictions.eq("sku", sku));
		}
		
		disjunc.add(Restrictions.ilike("name", keyword, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.ilike("searchTags", keyword, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.ilike("shortDescription", keyword, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.ilike("description", keyword, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.ilike("otherDetails", keyword, MatchMode.ANYWHERE));
		
		junc.add(disjunc);
		
		try {
			return findAllByCriterion(null, null, null, null, null,	null, junc);
		}
		catch(Exception e) {}
		
		return null;
	}
	
	public ObjectList<CategoryItem> findByFullContentContains(Company company, String keyword) {
		
		Junction junc = Restrictions.conjunction();
		
		Disjunction disjunc = Restrictions.disjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		/*
		junc.add(Restrictions.or(
				Restrictions.ilike("name", keyword, MatchMode.ANYWHERE),
				Restrictions.ilike("searchTags", keyword, MatchMode.ANYWHERE)
		));
		*/
		disjunc.add(Restrictions.ilike("name", keyword, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.ilike("searchTags", keyword, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.ilike("shortDescription", keyword, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.ilike("description", keyword, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.ilike("otherDetails", keyword, MatchMode.ANYWHERE));
		
		junc.add(disjunc);
		
		try {
			return findAllByCriterion(null, null, null, null, null,	null, junc);
		}
		catch(Exception e) {}
		
		return null;
	}
	
	public ObjectList<CategoryItem> findAllBillsWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		//junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, null, junc);
	}
	 
	public ObjectList<CategoryItem> findAllFeaturedProductsWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
		final String[] aliases = {"parentGroup"};
		final int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup.featured", Boolean.TRUE));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		junc.add(Restrictions.eq("featured", Boolean.TRUE));
		return findAllByCriterion(pageNumber, resultPerPage, aliases, null, joinType, orders, junc); 
	}
	
	public ObjectList<CategoryItem> findAllBestSellersWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
		final String[] aliases = {"parentGroup"};
		final int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		junc.add(Restrictions.eq("bestSeller", Boolean.TRUE));
		return findAllByCriterion(pageNumber, resultPerPage, aliases, null, joinType, orders, junc); 
	}
	
	public ObjectList<CategoryItem> findAllFeaturedByGroup(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		final String[] aliases = {"parentGroup"};
		final int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		junc.add(Restrictions.eq("featured", Boolean.TRUE));
		return findAllByCriterion(0, 100, aliases,null, joinType, orders, junc); 
	}
	
	public ObjectList<CategoryItem> findAllFeaturedByGroup(Company company, Group group, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		junc.add(Restrictions.eq("featured", Boolean.TRUE));
		return this.findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<CategoryItem> findAllFeaturedByGroupWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		final String[] aliases = {"parentGroup"};
		final int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		junc.add(Restrictions.eq("featured", Boolean.TRUE));
		
		//HBC filtering out of stock items
		if(company.getId() == 104) 
			junc.add(Restrictions.eq("isOutOfStock", Boolean.FALSE));
		
		return findAllByCriterion(pageNumber, resultPerPage, aliases, null, joinType, orders, junc); 
	}
		
	public ObjectList<CategoryItem> findAllActiveItemsWithPaging(Company company, Category parent, int resultPerPage, int pageNumber, Order[] order, boolean showAll) {			
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", false));
		if(parent == null) {
			if(!showAll) {
				junc.add(Restrictions.isNull("parent"));
			}
		}
		else {
			junc.add(Restrictions.eq("parent", parent));
		}
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				order, junc);
	}	
	
	public ObjectList<CategoryItem> findAllWithPaging(Company company, Category parent, int resultPerPage, int pageNumber, Order[] order, boolean showAll) {			
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		if(parent == null) {
			if(!showAll) {
				junc.add(Restrictions.isNull("parent"));
			}
		}
		else {
			junc.add(Restrictions.eq("parent", parent));
		}
		
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				order, junc);
	}	
	
	public ObjectList<CategoryItem> findAllWithPaging(Company company, Group group, Category parent, int resultPerPage, int pageNumber, Order[] order, boolean showAll)
	{
		Junction junc = Restrictions.conjunction();
		Junction disjunction = Restrictions.disjunction();
		/*if(company.getName().equals("pocketpons")){
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		}*/
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("company", company));
		
		if(parent == null)
		{
			if(!showAll)
			{
				junc.add(Restrictions.isNull("parent"));
			}
		}
		else
		{
			String[] paths = { "parent", "parent.parentCategory" };
			String[] aliases = { "parent", "parentCategory1" };
			int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
			
			disjunction.add(Restrictions.eq("parent", parent));
			disjunction.add(Restrictions.eq("parent.parentCategory", parent));
			junc.add(disjunction);
			return findAllByCriterion(pageNumber, resultPerPage, paths, aliases, joinTypes, order, junc);
		}
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, junc);
		
	}
	
	// HAS USER RIGHTS
	public ObjectList<CategoryItem> findAllWithPaging(Company company, Group group, Category parent, int resultPerPage, int pageNumber, Order[] order, boolean showAll, boolean hasUserRights, List<Category> forbiddenCategories)
	{
		Junction junc = Restrictions.conjunction();
		Junction disjunction = Restrictions.disjunction();
		/*if(company.getName().equals("pocketpons")){
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		}*/
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("company", company));
		
		if(hasUserRights && (forbiddenCategories != null && forbiddenCategories.size() > 0)){
			junc.add(Restrictions.not(Restrictions.in("parent", forbiddenCategories)));
		}
		if(parent == null)
		{
			if(!showAll)
			{
				junc.add(Restrictions.isNull("parent"));
			}
		}
		else
		{
			String[] paths = { "parent", "parent.parentCategory" };
			String[] aliases = { "parent", "parentCategory1" };
			int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
			
			disjunction.add(Restrictions.eq("parent", parent));
			disjunction.add(Restrictions.eq("parent.parentCategory", parent));
			junc.add(disjunction);
			return findAllByCriterion(pageNumber, resultPerPage, paths, aliases, joinTypes, order, junc);
		}
		
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, junc);
		
	}
	
	//findAllFeaturedWithPaging
	public ObjectList<CategoryItem> findAllFeaturedWithPaging(Company company, Group group, Category parent, int resultPerPage, int pageNumber, Order[] order, boolean showAll, boolean isFeatured)
	{
		Junction junc = Restrictions.conjunction();
		Junction disjunction = Restrictions.disjunction();
		if(company.getName().equals("pocketpons")){
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		}
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("featured", isFeatured));
		if(parent == null)
		{
			if(!showAll)
			{
				junc.add(Restrictions.isNull("parent"));
			}
		}
		else
		{
			String[] paths = { "parent", "parent.parentCategory" };
			String[] aliases = { "parent", "parentCategory1" };
			int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
			
			disjunction.add(Restrictions.eq("parent", parent));
			disjunction.add(Restrictions.eq("parent.parentCategory", parent));
			junc.add(disjunction);
			return findAllByCriterion(pageNumber, resultPerPage, paths, aliases, joinTypes, order, junc);
		}
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, junc);
	}
	
	
	public Integer countAll(Company company, Group group, Category parent,  boolean showAll)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("company", company));
		if(parent == null) {
			if(!showAll) {
				junc.add(Restrictions.isNull("parent"));
			}
		}
		else {
			junc.add(Restrictions.eq("parent", parent));
		}
		
		
		return (Integer)this.findAllByCriterionProjection(Projections.count("id"), junc).get(0);
	}
	
	public Integer countAll(Company company, Group group, Category parent, boolean showAll, boolean hasUserRights, List<Category> forbiddenCategories)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("company", company));
		if(parent == null) {
			if(!showAll) {
				junc.add(Restrictions.isNull("parent"));
			}
		}
		else {
			junc.add(Restrictions.eq("parent", parent));
		}
		
		if(hasUserRights && (forbiddenCategories != null && forbiddenCategories.size() > 0)){
			junc.add(Restrictions.not(Restrictions.in("parent", forbiddenCategories)));
		}
		
		
		return (Integer)this.findAllByCriterionProjection(Projections.count("id"), junc).get(0);
	}
	
	public CategoryItem find(Company company, Long id) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("id", id));
		if(company.getName().equals("pocketpons")){
			conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		}
		conj.add(Restrictions.eq("company.id", company.getId()));
		
		return findAllByCriterion(null, null, null, null, 
				conj).uniqueResult();
	}
	
//	public ObjectList<CategoryItem> searchWithPaging(String keyword, int resultPerPage, int pageNumber, Order[] order, boolean showAll) {			
//		Junction junc = Restrictions.conjunction();
//		junc.add(Restrictions.eq("isValid", Boolean.TRUE));


		public ObjectList<CategoryItem> search(String keyword, Company company) {			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			if(company.getName().equals("pocketpons")){
				junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			}
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
		return findAllByCriterion(0, 0, null, null, null, 
				null, junc);
		}
		
		public ObjectList<CategoryItem> searchByCatSubCatClass(String keyword, Company company) {
			String [] paths = {"parent",  "parent.parentCategory", "categoryItemOtherFields"};
			String [] aliases = {"parent",  "parentCategory", "otherFields"};
			int [] joinTypes = {  CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };

			Junction junc = Restrictions.conjunction();
			Junction djunc = Restrictions.disjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			
			djunc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
			djunc.add(Restrictions.like("parent.name", keyword, MatchMode.ANYWHERE));
			djunc.add(Restrictions.like("parentCategory.name", keyword, MatchMode.ANYWHERE));
			djunc.add(Restrictions.like("otherFields.content", keyword, MatchMode.ANYWHERE));
			junc.add(djunc);

			return findAllByCriterion(0, 0, paths, aliases, joinTypes, null, junc, djunc);
		}
		
		public ObjectList<CategoryItem> searchByBrand(String keyword, Company company) {
			String [] paths = {"brand"};
			String [] aliases = {"brand"};
			int [] joinTypes = {CriteriaSpecification.LEFT_JOIN};

			Junction junc = Restrictions.conjunction();
			Junction djunc = Restrictions.disjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			
			djunc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
			djunc.add(Restrictions.like("brand.name", keyword, MatchMode.ANYWHERE));
			junc.add(djunc);

			return findAllByCriterion(0, 0, paths, aliases, joinTypes, null, junc, djunc);
		}		
		
		public ObjectList<CategoryItem> search (Company company, Category category, List<String> keywords){			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("parent", category));
			junc.add(Restrictions.eq("company", company));
			for(String key: keywords)
			{
				junc.add(Restrictions.like("searchTags", key, MatchMode.ANYWHERE));
			}
				
			return findAllByCriterion(0, 0, null, null, null, 
				null, junc);
		}
		
		public ObjectList<CategoryItem> searchByTagsAndName(String keyword, String searchTag, Company company) {			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
			junc.add(Restrictions.like("searchTags", searchTag, MatchMode.ANYWHERE));
		return findAllByCriterion(0, 0, null, null, null, 
				null, junc);
		}
		
		public ObjectList<CategoryItem> searchByTagAndName(int pageNumber, int resultPerPage, String keyword, String searchTag, Company company, boolean isInDescription) {
			Junction junc = Restrictions.conjunction();
			
			Junction disj = Restrictions.disjunction();
			
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			disj.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
			junc.add(Restrictions.like("searchTags", searchTag, MatchMode.ANYWHERE));
			disj.add(Restrictions.like("sku", keyword, MatchMode.ANYWHERE));
			if(isInDescription)
			{
				disj.add(Restrictions.like("description", keyword, MatchMode.ANYWHERE));
			}
			junc.add(disj);
			
			return findAllByCriterion(0, 0, null, null, null, 
				null, junc);
		}
		
		public ObjectList<CategoryItem> searchbySearchTags(String searchTag, Company company) {			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.like("searchTags", searchTag, MatchMode.EXACT));
		return findAllByCriterion(0, 0, null, null, null, 
				null, junc);
		}
		public ObjectList<CategoryItem> searchByName(String keyname, Company company) {			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.like("name", keyname, MatchMode.ANYWHERE));
		return findAllByCriterion(0, 0, null, null, null, 
				null, junc);
		}
	
		public ObjectList<CategoryItem> searchByGroup(String keyword, Company company, Group group) {
			final String[] aliases = {"parentGroup"};
			final int[] joinType = {CriteriaSpecification.LEFT_JOIN};
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			
			
			Junction djunc = Restrictions.disjunction();
			djunc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
			djunc.add(Restrictions.like("description", keyword, MatchMode.ANYWHERE));
			djunc.add(Restrictions.like("shortDescription", keyword, MatchMode.ANYWHERE));
			
			return findAllByCriterion(0, 0, aliases,null, joinType, null, junc, djunc);
	 
		}
		
	public ObjectList<CategoryItem> searchByGroupAndOtherFieldWithPaging(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		//final Criteria totalCriteria = getSession().createCriteria(clazz, "this");
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		if(group != null)
		{
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = { "TEST", "RELATED WORDS" , /*"TEST CODE(S)",*/ "OTHER TEST REQUEST NAME", "ANALYTE", "SAMPLE" };
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
			
			/*if(otherFieldCompator.equals("ne"))
			{
				Junction disj = Restrictions.disjunction();
				disj.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
				disj.add(Restrictions.like("searchTags", keyword, MatchMode.ANYWHERE));
				
				conj.add(disj);
			}*/
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		//totalCriteria.add(conj);
		
		for(int i = 0; i < paths.length; i++)
		{
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			//totalCriteria.createAlias(paths[i], aliases[i], joinTypes[i]);
		}
		
		final ObjectList<CategoryItem> ret = new ObjectList<CategoryItem>();
		//addPagingToCriteria(totalCriteria, pageNumber, maxResult);
		/*totalCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		totalCriteria.setProjection(Projections.countDistinct("id"));
		Integer totalCount = (Integer)totalCriteria.uniqueResult();*/
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] { Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		// criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<CategoryItem> list = criteria.list();
		if(list != null && !list.isEmpty())
		{
			final LinkedHashSet<CategoryItem> sets = new LinkedHashSet<CategoryItem>(list);
			list = new LinkedList<CategoryItem>(sets);
			ret.setList(list);
			/*final int a = (pageNumber+1) * maxResult;
			final int total = ret.getTotal();
			if(a / total > 0)
			{
				final List<CategoryItem> end = new ArrayList<CategoryItem>();
				final int div = (pageNumber * maxResult);
				if(div <= 0)
				{
					ret.setList(list);
				}
				else
				{
					final int rem = total % div;
					for(int i = 0; i < rem; i++)
					{
						end.add(list.get(i));
					}
					ret.setList(end);
				}
			}
			else
			{
				ret.setList(list);
			}*/
		}
		
		return ret;
	}
	
	public ObjectList<CategoryItem> findAllAnalytesByGroupAndKeywordByMachineWithPaging(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		
		if(group != null){
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = { "ANALYTE- ANALYTE", "ANALYTE- SAMPLE", "ANALYTE- MACHINES" };
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword)){
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++){
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}
		
		final ObjectList<CategoryItem> ret = new ObjectList<CategoryItem>();
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] {  Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		
		List<CategoryItem> list = criteria.list();
		if(list != null && !list.isEmpty())
		{
			final LinkedHashSet<CategoryItem> sets = new LinkedHashSet<CategoryItem>(list);
			list = new LinkedList<CategoryItem>(sets);
			ret.setList(list);
	
		}
		
		return ret;
	}
	
	
	public ObjectList<CategoryItem> searchLabFileByGroupAndOtherFieldWithPaging(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		
		if(group != null){
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = { "LAB FILE- ANALYTE", "LAB FILE- TEST CODE"};
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword)){
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++){
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}
		
		final ObjectList<CategoryItem> ret = new ObjectList<CategoryItem>();
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] {  Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		
		List<CategoryItem> list = criteria.list();
		if(list != null && !list.isEmpty())
		{
			final LinkedHashSet<CategoryItem> sets = new LinkedHashSet<CategoryItem>(list);
			list = new LinkedList<CategoryItem>(sets);
			ret.setList(list);
	
		}
		
		return ret;
	}
	
	
	public List<Long> searchAllIdsByGroupAndOtherFieldWithPaging(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		if(group != null)
		{
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = { "TEST", "RELATED WORDS" , /*"TEST CODE(S)",*/ "OTHER TEST REQUEST NAME" };
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
			
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++)
		{
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]);
		}
		
		final ObjectList<Long> ret = new ObjectList<Long>();
		
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] { Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		//ProjectionList pList = Projections.projectionList().add(Projections.property("id")).add(Projections.distinct("id"));
		List<Long> list = criteria.list();
		
		/*if(list != null && !list.isEmpty()){
			
		}
		*/
		return list;
	}
	
	public List<Long> searchAllAnalytesIdsByGroupAndOtherFieldWithPaging(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		System.out.println("searchAllAnalytesIdsByGroupAndOtherFieldWithPaging INVOKED!");
		
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		if(group != null)
		{
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = { "ANALYTE- ANALYTE", "ANALYTE- SAMPLE", "ANALYTE- MACHINES" };
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
			
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++)
		{
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]);
		}
		
		final ObjectList<Long> ret = new ObjectList<Long>();
		
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] { Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		List<Long> list = criteria.list();
		
		return list;
	}
	
	
	public List<Long> searchAllLabFileIdsByGroupAndOtherFieldWithPaging(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		System.out.println("searchAllLabFileIdsByGroupAndOtherFieldWithPaging INVOKED!");
		
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		if(group != null)
		{
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = { "LAB FILE- TEST", "LAB FILE- TEST CODE" };
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
			
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++)
		{
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]);
		}
		
		final ObjectList<Long> ret = new ObjectList<Long>();
		
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] { Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		List<Long> list = criteria.list();
		
		return list;
	}
	
	
	public List<Long> searchHipreUpdatedItems(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		DateTime fromDate = new DateTime().minusDays(company.getNotifDuration());
		//Disjunction disj = Restrictions.disjunction();
	
		//disj.add(Restrictions.ge("createdOn", fromDate.toDate()));
		//disj.add(Restrictions.ge("updatedOn", fromDate.toDate()));
		//conj.add(disj);
		conj.add(Restrictions.ge("updatedOn", fromDate.toDate()));
		
		
		
		if(group != null)
		{
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = { "TEST", "RELATED WORDS" , /*"TEST CODE(S)",*/ "OTHER TEST REQUEST NAME" };
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
			
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++)
		{
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]);
		}
		
		final ObjectList<Long> ret = new ObjectList<Long>();
		
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] { Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		
		List<Long> list = criteria.list();
		
		/*if(list != null && !list.isEmpty()){
			
		}
		*/
		return list;
	}
	
	public List<Long> searchHipreAnalytesUpdatedItems(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		DateTime fromDate = new DateTime().minusDays(company.getNotifDuration());

		conj.add(Restrictions.ge("updatedOn", fromDate.toDate()));
		
		if(group != null)
		{
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = {"ANALYTE- ANALYTE", "ANALYTE- SAMPLE", "ANALYTE- MACHINES"};
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
			
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++)
		{
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]);
		}
		
		final ObjectList<Long> ret = new ObjectList<Long>();
		
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] { Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		
		List<Long> list = criteria.list();
		
		return list;
	}
	
	
	public List<Long> searchHipreLabFileUpdatedItems(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		DateTime fromDate = new DateTime().minusDays(company.getNotifDuration());

		conj.add(Restrictions.ge("updatedOn", fromDate.toDate()));
		
		if(group != null)
		{
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = {"LAB FILE- TEST", "LAB FILE- TEST CODE"  };
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
			
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++)
		{
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]);
		}
		
		final ObjectList<Long> ret = new ObjectList<Long>();
		
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] { Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		
		List<Long> list = criteria.list();
		
		return list;
	}
	
	public List<Long> searchHipreCreatedItems(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		DateTime fromDate = new DateTime().minusDays(company.getNotifDuration());
		//Disjunction disj = Restrictions.disjunction();
	
		//disj.add(Restrictions.ge("createdOn", fromDate.toDate()));
		//disj.add(Restrictions.ge("updatedOn", fromDate.toDate()));
		//conj.add(disj);
		conj.add(Restrictions.ge("createdOn", fromDate.toDate()));
		
		
		
		if(group != null)
		{
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = { "TEST", "RELATED WORDS" , /*"TEST CODE(S)",*/ "OTHER TEST REQUEST NAME" };
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
			
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++)
		{
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]);
		}
		
		final ObjectList<Long> ret = new ObjectList<Long>();
		
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] { Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		
		List<Long> list = criteria.list();
		
		/*if(list != null && !list.isEmpty()){
			
		}
		*/
		return list;
	}
	
	public List<Long> searchHipreaAnalytesCreatedItems(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		DateTime fromDate = new DateTime().minusDays(company.getNotifDuration());
		conj.add(Restrictions.ge("createdOn", fromDate.toDate()));

		if(group != null)
		{
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = { "ANALYTE- ANALYTE", "ANALYTE- SAMPLE", "ANALYTE- MACHINES" };
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
			
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++)
		{
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]);
		}
		
		final ObjectList<Long> ret = new ObjectList<Long>();
		
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] { Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		
		List<Long> list = criteria.list();

		return list;
	}
	
	
	public List<Long> searchHipreaLabFileCreatedItems(int pageNumber, int maxResult, String keyword, Company company, Group group, String otherFieldName, String otherFieldValue,
			String otherFieldCompator)
	{
		logger.info("keyword: "+  keyword);
		String[] paths = { "categoryItemOtherFields", "otherFields.otherField" };
		String[] aliases = { "otherFields", "otherField" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		final Criteria criteria = getSession().createCriteria(clazz, "this");
		
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("disabled", Boolean.FALSE));
		DateTime fromDate = new DateTime().minusDays(company.getNotifDuration());
		conj.add(Restrictions.ge("createdOn", fromDate.toDate()));

		if(group != null)
		{
			conj.add(Restrictions.eq("parentGroup", group));
		}
		
		final Disjunction disjOtherFields = Restrictions.disjunction();
		final String[] otherFields = {"LAB FILE- TEST", "LAB FILE- TEST CODE"};
		for(String of : otherFields)
		{
			logger.info("OtherField: "+  of);
			disjOtherFields.add(Restrictions.eq("otherField.name", of));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			final Disjunction disjContents = Restrictions.disjunction();
			disjContents.add(Restrictions.ilike("otherFields.content", keyword, MatchMode.ANYWHERE));
			conj.add(disjContents);
			
		}
		
		conj.add(disjOtherFields);
		
		criteria.add(conj);
		
		for(int i = 0; i < paths.length; i++)
		{
			criteria.createAlias(paths[i], aliases[i], joinTypes[i]);
		}
		
		final ObjectList<Long> ret = new ObjectList<Long>();
		
		ret.setTotal(getTotalItems(criteria));
		
		addOrderToCriteria(criteria, new Order[] { Order.asc("name") }, true);
		
		addPagingToCriteria(criteria, pageNumber, maxResult);
		
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		
		List<Long> list = criteria.list();

		return list;
	}
		
		
		public ObjectList<CategoryItem> search(String keyword, Company company, boolean isInDescription) {		
			
			String [] paths = {"parent","parentGroup"};
			String [] aliases = {"category","parentGroup"};
			int [] joinTypes = {  CriteriaSpecification.LEFT_JOIN,CriteriaSpecification.LEFT_JOIN};
			
			Junction mJunc = Restrictions.disjunction();
			
			//System.out.println("keyword    " + keyword);
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("company", company));
			if (isInDescription) { 
				mJunc.add(Restrictions.like("description", keyword, MatchMode.ANYWHERE));
				//mJunc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
				if(company.getName().equalsIgnoreCase("knoxout2") || company.getName().equalsIgnoreCase("hiprecisiononlinestore")) {
					mJunc.add(Restrictions.like("category.name", keyword, MatchMode.ANYWHERE));
					mJunc.add(Restrictions.like("parentGroup.name", keyword, MatchMode.ANYWHERE));
					
					if (company.getName().equalsIgnoreCase("hiprecisiononlinestore")) {
						mJunc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
						mJunc.add(Restrictions.like("searchTags", keyword, MatchMode.ANYWHERE));
						return findAllByCriterion(0, 0, paths, aliases, joinTypes, 
								null, junc, mJunc);
					}
					
					return findAllByCriterion(0, 0, paths, aliases, joinTypes, 
							null, junc, mJunc);
				}
				
				if (company.getName().equalsIgnoreCase("hiprecisiononlinestore")) {
					mJunc.add(Restrictions.like("category.name", keyword, MatchMode.ANYWHERE));
					mJunc.add(Restrictions.like("parentGroup.name", keyword, MatchMode.ANYWHERE));
					mJunc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
					mJunc.add(Restrictions.like("searchTags", keyword, MatchMode.ANYWHERE));
					return findAllByCriterion(0, 0, paths, aliases, joinTypes, 
							null, junc, mJunc);
				}
				
			} else{
				mJunc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
				mJunc.add(Restrictions.like("searchTags", keyword, MatchMode.ANYWHERE));
				if(company.getId() == 125 || company.getId() == 162)
					mJunc.add(Restrictions.like("sku", keyword, MatchMode.ANYWHERE));
				if(company.getName().equalsIgnoreCase("purpletag2")) {
					mJunc.add(Restrictions.like("category.name", keyword, MatchMode.ANYWHERE));
					mJunc.add(Restrictions.like("parentGroup.name", keyword, MatchMode.ANYWHERE));
					return findAllByCriterion(0, 0, paths, aliases, joinTypes, 
							null, junc, mJunc);
				}
			}
				
		return findAllByCriterion(0, 0, null, null, null, 
				null, junc, mJunc);
	}
		
		public ObjectList<CategoryItem> searchWithPaging(int pageNumber, int resultPerPage, 
				String keyword, Company company, boolean isInDescription) {		
			
			Junction mJunc = Restrictions.disjunction();
			
			//System.out.println("keyword    " + keyword);
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			//junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("company", company));
			if (isInDescription) { 
				mJunc.add(Restrictions.like("description", keyword, MatchMode.ANYWHERE));
				//mJunc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
			} else{
				mJunc.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
				mJunc.add(Restrictions.like("searchTags", keyword, MatchMode.ANYWHERE));
				if(company.getId() == 125 || company.getId() == 162)
					mJunc.add(Restrictions.like("sku", keyword, MatchMode.ANYWHERE));
			}
				
			return findAllByCriterion(pageNumber, resultPerPage, null, null, null, null, junc, mJunc);
		}
		
		public ObjectList<CategoryItem> searchClass(String keyword, Company company) {		
			String [] paths = {"parent",  "parent.parentCategory", "categoryItemOtherFields"};
			String [] aliases = {"parent",  "parentCategory", "otherFields"};
			int [] joinTypes = {  CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
			
			Junction mJunc = Restrictions.disjunction();
			
			Junction junc = Restrictions.conjunction();
			Junction djunc = Restrictions.disjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("company", company));
			djunc.add(Restrictions.like("parent.name", keyword, MatchMode.ANYWHERE));
			djunc.add(Restrictions.like("parentCategory.name", keyword, MatchMode.ANYWHERE));
			djunc.add(Restrictions.like("otherFields.content", keyword, MatchMode.ANYWHERE));
			junc.add(djunc);
			
			return findAllByCriterion(paths, aliases, joinTypes, null, junc);
		}			
		
		public ObjectList<CategoryItem> searchClassWithPaging(int pageNumber, int resultPerPage, 
				String keyword, Company company, boolean isInDescription) {		
			String [] paths = {"parent",  "parent.parentCategory", "categoryItemOtherFields"};
			String [] aliases = {"parent",  "parentCategory", "otherFields"};
			int [] joinTypes = {  CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
			
			Junction mJunc = Restrictions.disjunction();
			
			Junction junc = Restrictions.conjunction();
			Junction djunc = Restrictions.disjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("company", company));
			djunc.add(Restrictions.like("parent.name", keyword, MatchMode.ANYWHERE));
			djunc.add(Restrictions.like("parentCategory.name", keyword, MatchMode.ANYWHERE));
			djunc.add(Restrictions.like("otherFields.content", keyword, MatchMode.ANYWHERE));
			junc.add(djunc);
			
			return findAllByCriterion(pageNumber, resultPerPage, paths, aliases, joinTypes, null, junc, mJunc);
		}		
		
		public List<CategoryItemPackageWrapper> searchIncludePackageWithPaging(int pageNumber, int resultPerPage, 
				String keyword, Company company, boolean isInDescription) {					
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, sku, parentGroupId from (");
			sql.append("select id, name, sku, parent_group as parentGroupId from category_item where (name like '%").append(keyword).append("%' or sku like '%").append(keyword).append("%') and company_id=").append(company.getId());
			sql.append(" union ");
			sql.append("select id, name, sku, parent_group as parentGroupId from packages where (name like '%").append(keyword).append("%' or sku like '%").append(keyword).append("%') and company_id=").append(company.getId());
			sql.append(") as DerivedTable order by name limit ");
			sql.append(pageNumber * resultPerPage).append(", ").append(resultPerPage).append(";");
						
			final Session session = getSession();
			final SQLQuery sqlQuery = session.createSQLQuery(sql.toString());

			sqlQuery.setResultTransformer(Transformers.aliasToBean(CategoryItemPackageWrapper.class));
			
			@SuppressWarnings("unchecked")
			List<CategoryItemPackageWrapper> list = sqlQuery.list();
			return list;						
		}
		
		public Integer countSearchIncludePackage(String keyword, Company company, boolean isInDescription)
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select count(id) from (");
			sql.append("select id from category_item where name like '%").append(keyword).append("%' or sku like '%").append(keyword).append("%'");
			sql.append(" union ");
			sql.append("select id from packages where name like '%").append(keyword).append("%' or sku like '%").append(keyword).append("%'");
			sql.append(") as DerivedTable;");
						
			final Session session = getSession();
			final SQLQuery sqlQuery = session.createSQLQuery(sql.toString());			
			return ((BigInteger) sqlQuery.uniqueResult()).intValue();		
		}

		public ObjectList<CategoryItem> findAllByGroupWithPaging(Company company,Group group, int resultPerPage,int pageNumber, Order[] orders) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));		
			return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
					orders, junc);
		}
		
		public ObjectList<CategoryItem> findAllEnabledByGroupWithPaging(Company company,Group group, int resultPerPage,int pageNumber, Order[] orders) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));	
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));		
			return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
					orders, junc);
		}
		
		public ObjectList<CategoryItem> findAllEnabledByGroupAndParentWithPaging(Company company,Group group,int pageNumber, int resultPerPage, List<Long> idList, Order[] orders) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));	
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			//if(category != null){
			//    junc.add(Restrictions.eq("parent", category));
			//}
			if(idList != null){
				if(idList.size() > 0){
					junc.add(Restrictions.in("id", idList));
				}
			}
			return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
					orders, junc);
		}
		
		public ObjectList<CategoryItem> findAllByGroupAndByCategoryWithPaging(Company company, Group group, Category category, int resultsPerPage, int pageNumber, Order[] orders) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("parent", category));
			
			return findAllByCriterion(pageNumber, resultsPerPage, null, null, null, orders, junc);
		}
		
		public ObjectList<CategoryItem> findFirstTenItemsByGroup(Company company,Group group) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			
			Order[] orders = {Order.asc("parent"),Order.asc("sortOrder"),Order.asc("name")};
			
			return findAllByCriterion(0, 10, null, null, null, orders, junc);
		}

		public ObjectList<CategoryItem> getAllItems(Company company, Group group, int resultPerPage, int pageNumber, Order[] orders) {			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.like("parentGroup", group));
			junc.add(Restrictions.isNotNull("name"));
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, junc);
		}
		
		public ObjectList<CategoryItem> getAllCatItems(Company company, Category parent, int resultPerPage, int pageNumber, Order[] orders) {			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("parent", parent));
			
			//HBC filtering out of stock items
			if(company.getId() == 104) 
				junc.add(Restrictions.eq("isOutOfStock", Boolean.FALSE));
			
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, junc);
		}
		
		public ObjectList<CategoryItem> getAllBrandItems(Company company, Brand brand, int resultPerPage, int pageNumber, Order[] orders) {			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("brand", brand));
			
		return findAllByCriterion(null, null, null, 
				orders, junc);
		}

		public ObjectList<CategoryItem> getAllCatItemsNoPaging(Company company, Category parent, Order[] orders) {			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("parent", parent));
			
			return findAllByCriterion(null, null, null, orders, junc);
		}

		public ObjectList<CategoryItem> getAllBrandItemsWithPaging(Company company, int resultPerPage, int pageNumber, Brand brand, Order[] orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		junc.add(Restrictions.eq("brand", brand));
		
		//HBC filtering out of stock items
		if(company.getId() == 104) 
			junc.add(Restrictions.eq("isOutOfStock", Boolean.FALSE));

		return findAllByCriterion(pageNumber, resultPerPage, null, null, null,
				orders, junc);
		}
		
		
		public ObjectList<CategoryItem> getLatestMonthProducts(Company company, int resultPerPage, int pageNumber, Order...orders) {
			ObjectList<CategoryItem> items = null;
			
			DateTime currentDate = new DateTime();
			DateTime startDate = new DateTime(currentDate.getYear(), currentDate.getMonthOfYear(), 1, 0, 0, 0, 0);
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.between("createdOn", startDate.toDate(), new Date()));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));

			try {
				items = findAllByCriterion(0, 50, null,null, null, orders, junc);
			}
			catch(Exception e) {}
			
			return items;
		
		}
	
		public ObjectList<CategoryItem> getLatestMonthProductsWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
			ObjectList<CategoryItem> items = null;
			
			DateTime currentDate = new DateTime();
			DateTime startDate = new DateTime(currentDate.getYear(), currentDate.getMonthOfYear(), 1, 0, 0, 0, 0);
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.between("createdOn", startDate.toDate(), new Date()));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));

			try {

				items = findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
			}
			catch(Exception e) {}
			
			return items;
		
		}
		
		public ObjectList<CategoryItem> getLatestProducts(Company company, int resultPerPage, int pageNumber, Order...orders) {
			ObjectList<CategoryItem> items = null;
	
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));


			try {
				items = findAllByCriterion(0, 50, null,null, null, orders, junc);
			}
			catch(Exception e) {}
			
			return items;
		
		}
	
		public ObjectList<CategoryItem> getLatestProductsWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
			ObjectList<CategoryItem> items = null;
			
			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));

			try {
				items = findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
				
			}
			catch(Exception e) {}
			
			return items;
		
		}
		
		public ObjectList<CategoryItem> getLatestProductsWithPaging(Company company, Group productgroup, int resultPerPage, int pageNumber, Order...orders) {
			ObjectList<CategoryItem> items = null;
			
			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("parentGroup", productgroup));
			
			//HBC filtering out of stock items
			if(company.getId() == 104) 
				junc.add(Restrictions.eq("isOutOfStock", Boolean.FALSE));

			try {
				items = findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
				
			}
			catch(Exception e) {}
			
			return items;
		
		}
		
		public ObjectList<CategoryItem> findAll(Company company, Group group, String sku) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("sku", sku));
			
			return this.findAllByCriterion(null, null, null, null, junc);
		}
		
		public ObjectList<CategoryItem> findAllByGroup(Company company, Group group, Boolean disabled) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			
			if(disabled != null)
			{
				junc.add(Restrictions.eq("disabled", disabled));	
			}
			
			Order[] orders = {Order.asc("parent"),Order.asc("sortOrder"),Order.asc("name")};
			
			return this.findAllByCriterion(null, null, null, orders, junc);
		}

		public ObjectList<CategoryItem> findAllByGroupAndParent(Company company, Group group, Category parent) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("parent", parent));
			
			return this.findAllByCriterion(null, null, null, null, junc);
		}


		public ObjectList<CategoryItem> findAllByGroupNameAsc(Company company, Group group) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			
			Order[] orders = {Order.asc("name")};
			
			return this.findAllByCriterion(null, null, null, orders, junc);
		}
		
		
		



		public List<CategoryItem> search(Company company,
				Category parentCategory, Category itemParentCategory,
				String searchItemKeyword) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.ilike("name", searchItemKeyword,MatchMode.ANYWHERE));

			if(parentCategory!=null && itemParentCategory!=null){
				junc.add(Restrictions.eq("parent", itemParentCategory));
				junc.add(Restrictions.eq("parent.parentCategory", parentCategory));
			}
			else{
				if(parentCategory!=null)
					junc.add(Restrictions.eq("parent.parentCategory", parentCategory));
				
				if(itemParentCategory!=null)
					junc.add(Restrictions.eq("parent", itemParentCategory));
				
			}
			
			Order[] orders = {Order.asc("parentCategory.name"),Order.asc("parent"),Order.asc("parent.parentCategory"),Order.asc("name")};

			String [] paths = {"parent",  "parent.parentCategory" };
			String [] aliases = {"parent",  "parentCategory"};
			int [] joinTypes = { CriteriaSpecification.INNER_JOIN,  CriteriaSpecification.INNER_JOIN};
			//System.out.println(findAllByCriterion(paths, aliases, joinTypes, orders, junc).t)
			return this.findAllByCriterion(paths, aliases, joinTypes, orders, junc).getList();
		}



		public List<CategoryItem> searchByParentCategoryBrand(Company company, Brand brand, String searchItemKeyword) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			junc.add(Restrictions.ilike("name", searchItemKeyword,MatchMode.ANYWHERE));
	
			if(brand!=null)
				junc.add(Restrictions.eq("brand", brand));
			
			Order[] orders = {Order.asc("parentCategory.name"),Order.asc("parent.parentCategory"),Order.asc("parent"),Order.asc("name")};

			String [] paths = {"parent",  "parent.parentCategory" };
			String [] aliases = {"parent",  "parentCategory"};
			int [] joinTypes = { CriteriaSpecification.INNER_JOIN,  CriteriaSpecification.INNER_JOIN};
			return this.findAllByCriterion(paths, aliases, joinTypes, orders, junc).getList();
		}
		
		public List<CategoryItem> searchByCategoryAndTag(Company company, Category parent, Category parentCategory, Group group, String searchTag){
			final Junction junc = Restrictions.conjunction();
			final Junction disj = Restrictions.disjunction();
			final Order[] orders = {Order.asc("name")};
			
			String [] paths;
			String [] aliases;
			int [] joinTypes;
			
			//FIXME , update - fixed na ata
			//
			/* No category item result when parentCategory is not null
			 * or the assigned variables (paths, aliases, joinTypes) are wrong?
			 * */
			/*if(parentCategory != null) { 
				paths = new String[3];
				aliases = new String[3];
				joinTypes = new int[3];
				
				paths[0] = "parent";
			    paths[1] = "parent.parentCategory";
		        paths[2] = "parent.parentGroup";
		        
		        aliases[0] = "parent";
                aliases[1] = "parentCategory";
                aliases[2] = "parentGroup";
                
                joinTypes[0] = CriteriaSpecification.INNER_JOIN;
                joinTypes[1] = CriteriaSpecification.INNER_JOIN;  
                joinTypes[2] = CriteriaSpecification.INNER_JOIN;    
                
				junc.add(Restrictions.eq("parent.parentCategory", parentCategory));
				junc.add(Restrictions.eq("parent", parent));
			} else */
			if(parent != null) {
				paths = new String[2];
				aliases = new String[2];
				joinTypes = new int[2];
				
				paths[0] = "parent";
		        paths[1] = "parent.parentGroup";
		        
		        aliases[0] = "parent";
                aliases[1] = "parentGroup";
                
                joinTypes[0] = CriteriaSpecification.INNER_JOIN;
                joinTypes[1] = CriteriaSpecification.INNER_JOIN;  
				
                if(parentCategory != null) {
                	disj.add(Restrictions.eq("parent", parentCategory));
                } else {
                	try {
                		for (Category child : parent.getChildrenCategory()) {
                    		disj.add(Restrictions.eq("parent", child));
    					}
					} catch (Exception e) {}
                }
			} else {
				joinTypes = null;
				paths = null;
				aliases  = null;
			}
			
			if(searchTag.length() > 1){
				final String[] tags = searchTag.split("\\s+");
				for (String string : tags) {
					if(string != null || string != "") {
						disj.add(Restrictions.like("searchTags", string, MatchMode.ANYWHERE));
					} else{
						continue;
					}
				}
			}
			junc.add(disj);
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));

			return this.findAllByCriterion(paths, aliases, joinTypes, orders, junc).getList();
		}
		
		public List<CategoryItem> searchOtherFields(Company company, Category parent, Category parentCategory, Group group, String[] fields, String query) {
			final Junction junc = Restrictions.conjunction();
			final Junction disj = Restrictions.disjunction();
			final Order[] orders = {Order.asc("name")};
			
			String [] paths = new String[4];
			String [] aliases = new String[4];
			int [] joinTypes = new int[4];
			
			paths[0] = "categoryItemOtherFields";
			aliases[0] = "categoryItemOtherFields";
			joinTypes[0] = CriteriaSpecification.INNER_JOIN;
				
			paths[1] = "categoryItemOtherFields.otherField";
			aliases[1] = "otherField";
			joinTypes[1] = CriteriaSpecification.INNER_JOIN;
				
			paths[2] = "parent";
		    paths[3] = "parent.parentGroup";
		        
		    aliases[2] = "parent";
            aliases[3] = "parentGroup";
             
            joinTypes[2] = CriteriaSpecification.INNER_JOIN;
            joinTypes[3] = CriteriaSpecification.INNER_JOIN;  
						
			if(parent != null) {
				
				if(parentCategory != null) {
                	disj.add(Restrictions.eq("parent", parentCategory));
                } else {
                	try {
                		if(parent.getChildrenCategory() != null) {
	                		for (Category child : parent.getChildrenCategory()) {
	                			if(child != null)
	                				disj.add(Restrictions.eq("parent", child));
	    					}
                		} 
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
			} 
			
			for(int i=0; i< paths.length; i++) {
				//System.out.println(paths[i]);
				//System.out.println(aliases[i]);
				//System.out.println(joinTypes[i]);
			}
			
			
			
			if(fields != null) {
				junc.add(Restrictions.eq("categoryItemOtherFields.content", query));
				for(int i=0; i<fields.length; i++)
					junc.add(Restrictions.eq("otherField.name", fields[i]));
				
			}
			junc.add(disj);
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			
			
			
			return new ArrayList<CategoryItem>(new HashSet<CategoryItem>(this.findAllByCriterion(paths, aliases, joinTypes, orders, junc).getList()));
		}
		
		public List<CategoryItem> searchOtherFieldsWithPaging(Company company, Category parent, Category parentCategory, Group group, String[] fields, String query, int resultsPerPage, int maxResults) {
			final Junction junc = Restrictions.conjunction();
			final Junction disj = Restrictions.disjunction();
			final Order[] orders = {Order.asc("name")};
			
			String [] paths = new String[4];
			String [] aliases = new String[4];
			int [] joinTypes = new int[4];
			
			paths[0] = "categoryItemOtherFields";
			aliases[0] = "categoryItemOtherFields";
			joinTypes[0] = CriteriaSpecification.INNER_JOIN;
				
			paths[1] = "categoryItemOtherFields.otherField";
			aliases[1] = "otherField";
			joinTypes[1] = CriteriaSpecification.INNER_JOIN;
				
			paths[2] = "parent";
		    paths[3] = "parent.parentGroup";
		        
		    aliases[2] = "parent";
            aliases[3] = "parentGroup";
             
            joinTypes[2] = CriteriaSpecification.INNER_JOIN;
            joinTypes[3] = CriteriaSpecification.INNER_JOIN;  
						
			if(parent != null) {
				
				if(parentCategory != null) {
                	disj.add(Restrictions.eq("parent", parentCategory));
                } else {
                	try {
                		if(parent.getChildrenCategory() != null) {
	                		for (Category child : parent.getChildrenCategory()) {
	                			if(child != null)
	                				disj.add(Restrictions.eq("parent", child));
	    					}
                		} 
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
			} 
			
			for(int i=0; i< paths.length; i++) {
				//System.out.println(paths[i]);
				//System.out.println(aliases[i]);
				//System.out.println(joinTypes[i]);
			}
			
			
			
			if(fields != null) {
				junc.add(Restrictions.eq("categoryItemOtherFields.content", query));
				for(int i=0; i<fields.length; i++)
					junc.add(Restrictions.eq("otherField.name", fields[i]));
				
			}
			junc.add(disj);
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			
			
			
			return new ArrayList<CategoryItem>(new HashSet<CategoryItem>(this.findAllByCriterion(resultsPerPage, maxResults, paths, aliases, joinTypes, orders, junc).getList()));
		}

		public List<CategoryItem> searchOtherFieldsByBetweenValue(Company company, Category parent, Category parentCategory, Group group, String[] fields, String lowerLimit, String higherLimit) {
			final Junction junc = Restrictions.conjunction();
			final Junction disj = Restrictions.disjunction();
			final Order[] orders = {Order.asc("name")};
			
			String [] paths = new String[4];
			String [] aliases = new String[4];
			int [] joinTypes = new int[4];
			
			paths[0] = "categoryItemOtherFields";
			aliases[0] = "categoryItemOtherFields";
			joinTypes[0] = CriteriaSpecification.INNER_JOIN;
				
			paths[1] = "categoryItemOtherFields.otherField";
			aliases[1] = "otherField";
			joinTypes[1] = CriteriaSpecification.INNER_JOIN;
				
			paths[2] = "parent";
		    paths[3] = "parent.parentGroup";
		        
		    aliases[2] = "parent";
            aliases[3] = "parentGroup";
             
            joinTypes[2] = CriteriaSpecification.INNER_JOIN;
            joinTypes[3] = CriteriaSpecification.INNER_JOIN;  
						
			if(parent != null) {
				
				if(parentCategory != null) {
                	disj.add(Restrictions.eq("parent", parentCategory));
                } else {
                	try {
                		if(parent.getChildrenCategory() != null) {
	                		for (Category child : parent.getChildrenCategory()) {
	                			if(child != null)
	                				disj.add(Restrictions.eq("parent", child));
	    					}
                		} 
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
			} 
			
			for(int i=0; i< paths.length; i++) {
				//System.out.println(paths[i]);
				//System.out.println(aliases[i]);
				//System.out.println(joinTypes[i]);
			}
			
			
			
			if(fields != null) {
				junc.add(Restrictions.between("categoryItemOtherFields.content", lowerLimit, higherLimit));
				for(int i=0; i<fields.length; i++)
					junc.add(Restrictions.eq("otherField.name", fields[i]));
				
			}
			junc.add(disj);
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			
			
			
			return new ArrayList<CategoryItem>(new HashSet<CategoryItem>(this.findAllByCriterion(paths, aliases, joinTypes, orders, junc).getList()));
		}
		
		public List<CategoryItem> searchOtherFieldsByBetweenValueWithPaging(Company company, Category parent, Category parentCategory, Group group, String[] fields, String lowerLimit, String higherLimit, int resultsPerPage, int maxResults) {
			final Junction junc = Restrictions.conjunction();
			final Junction disj = Restrictions.disjunction();
			final Order[] orders = {Order.asc("name")};
			
			String [] paths = new String[4];
			String [] aliases = new String[4];
			int [] joinTypes = new int[4];
			
			paths[0] = "categoryItemOtherFields";
			aliases[0] = "categoryItemOtherFields";
			joinTypes[0] = CriteriaSpecification.INNER_JOIN;
				
			paths[1] = "categoryItemOtherFields.otherField";
			aliases[1] = "otherField";
			joinTypes[1] = CriteriaSpecification.INNER_JOIN;
				
			paths[2] = "parent";
		    paths[3] = "parent.parentGroup";
		        
		    aliases[2] = "parent";
            aliases[3] = "parentGroup";
             
            joinTypes[2] = CriteriaSpecification.INNER_JOIN;
            joinTypes[3] = CriteriaSpecification.INNER_JOIN;  
						
			if(parent != null) {
				
				if(parentCategory != null) {
                	disj.add(Restrictions.eq("parent", parentCategory));
                } else {
                	try {
                		if(parent.getChildrenCategory() != null) {
	                		for (Category child : parent.getChildrenCategory()) {
	                			if(child != null)
	                				disj.add(Restrictions.eq("parent", child));
	    					}
                		} 
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
			} 
			
			for(int i=0; i< paths.length; i++) {
				//System.out.println(paths[i]);
				//System.out.println(aliases[i]);
				//System.out.println(joinTypes[i]);
			}
			
			
			
			if(fields != null) {
				junc.add(Restrictions.between("categoryItemOtherFields.content", lowerLimit, higherLimit));
				for(int i=0; i<fields.length; i++)
					junc.add(Restrictions.eq("otherField.name", fields[i]));
				
			}
			junc.add(disj);
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			
			
			
			return new ArrayList<CategoryItem>(new HashSet<CategoryItem>(this.findAllByCriterion(resultsPerPage, maxResults, paths, aliases, joinTypes, orders, junc).getList()));
		}
		
		public List<CategoryItem> searchOtherFieldsByContainsContent(Company company, Category parent, Category parentCategory, Group group, String[] fields, String contentValue) {
			final Junction junc = Restrictions.conjunction();
			final Junction disj = Restrictions.disjunction();
			final Order[] orders = {Order.asc("name")};
			
			String [] paths = new String[4];
			String [] aliases = new String[4];
			int [] joinTypes = new int[4];
			
			paths[0] = "categoryItemOtherFields";
			aliases[0] = "categoryItemOtherFields";
			joinTypes[0] = CriteriaSpecification.INNER_JOIN;
				
			paths[1] = "categoryItemOtherFields.otherField";
			aliases[1] = "otherField";
			joinTypes[1] = CriteriaSpecification.INNER_JOIN;
				
			paths[2] = "parent";
		    paths[3] = "parent.parentGroup";
		        
		    aliases[2] = "parent";
            aliases[3] = "parentGroup";
             
            joinTypes[2] = CriteriaSpecification.INNER_JOIN;
            joinTypes[3] = CriteriaSpecification.INNER_JOIN;  
						
			if(parent != null) {
				
				if(parentCategory != null) {
                	disj.add(Restrictions.eq("parent", parentCategory));
                } else {
                	try {
                		if(parent.getChildrenCategory() != null) {
	                		for (Category child : parent.getChildrenCategory()) {
	                			if(child != null)
	                				disj.add(Restrictions.eq("parent", child));
	    					}
                		} 
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
			} 
			
			for(int i=0; i< paths.length; i++) {
				//System.out.println(paths[i]);
				//System.out.println(aliases[i]);
				//System.out.println(joinTypes[i]);
			}
			
			
			
			if(fields != null) {
				junc.add(Restrictions.like("categoryItemOtherFields.content", MatchMode.ANYWHERE));
				for(int i=0; i<fields.length; i++)
					junc.add(Restrictions.eq("otherField.name", fields[i]));
				
			}
			junc.add(disj);
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			
			
			
			return new ArrayList<CategoryItem>(new HashSet<CategoryItem>(this.findAllByCriterion(paths, aliases, joinTypes, orders, junc).getList()));
		}
		
		public List<CategoryItem> searchOtherFieldsByContainsContentWithPaging(Company company, Category parent, Category parentCategory, Group group, String[] fields, String contentValue, int resultsPerPage, int maxResults) {
			final Junction junc = Restrictions.conjunction();
			final Junction disj = Restrictions.disjunction();
			final Order[] orders = {Order.asc("name")};
			
			String [] paths = new String[4];
			String [] aliases = new String[4];
			int [] joinTypes = new int[4];
			
			paths[0] = "categoryItemOtherFields";
			aliases[0] = "categoryItemOtherFields";
			joinTypes[0] = CriteriaSpecification.INNER_JOIN;
				
			paths[1] = "categoryItemOtherFields.otherField";
			aliases[1] = "otherField";
			joinTypes[1] = CriteriaSpecification.INNER_JOIN;
				
			paths[2] = "parent";
		    paths[3] = "parent.parentGroup";
		        
		    aliases[2] = "parent";
            aliases[3] = "parentGroup";
             
            joinTypes[2] = CriteriaSpecification.INNER_JOIN;
            joinTypes[3] = CriteriaSpecification.INNER_JOIN;  
						
			if(parent != null) {
				
				if(parentCategory != null) {
                	disj.add(Restrictions.eq("parent", parentCategory));
                } else {
                	try {
                		if(parent.getChildrenCategory() != null) {
	                		for (Category child : parent.getChildrenCategory()) {
	                			if(child != null)
	                				disj.add(Restrictions.eq("parent", child));
	    					}
                		} 
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
			} 
			
			for(int i=0; i< paths.length; i++) {
				//System.out.println(paths[i]);
				//System.out.println(aliases[i]);
				//System.out.println(joinTypes[i]);
			}
			
			
			
			if(fields != null) {
				junc.add(Restrictions.like("categoryItemOtherFields.content", MatchMode.ANYWHERE));
				for(int i=0; i<fields.length; i++)
					junc.add(Restrictions.eq("otherField.name", fields[i]));
				
			}
			junc.add(disj);
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			
			
			
			return new ArrayList<CategoryItem>(new HashSet<CategoryItem>(this.findAllByCriterion(resultsPerPage, maxResults, paths, aliases, joinTypes, orders, junc).getList()));
		}
		
		public List<CategoryItem> findAllByBrand(Company company, Brand brand) {
			String [] paths = {"brand"};
			String [] aliases = {"brand"};
			int [] joinTypes = {CriteriaSpecification.LEFT_JOIN};

			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("brand", brand));

			return findAllByCriterion(0, 0, paths, aliases, joinTypes, null, junc).getList();
		}
		public List<CategoryItem> findAllByBrandSortByOrder(Company company, Brand brand) {
			String [] paths = {"brand"};
			String [] aliases = {"brand"};
			int [] joinTypes = {CriteriaSpecification.LEFT_JOIN};
			
			Order[] orders = {Order.asc("parent"),Order.asc("sortOrder"),Order.asc("name")};
			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("brand", brand));

			return findAllByCriterion(0, 0, paths, aliases, joinTypes, orders, junc).getList();
		}
		
		public List<CategoryItem> findAllByBrandSortByNameWithPaging(Company company, Brand brand, int page, int maxResults) {
			String [] paths = {"brand"};
			String [] aliases = {"brand"};
			int [] joinTypes = {CriteriaSpecification.LEFT_JOIN};
			
			Order[] orders = {Order.asc("name")};
			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("brand", brand));
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));

			return findAllByCriterion(page, maxResults, paths, aliases, joinTypes, orders, junc).getList();
		}
		
		public List<CategoryItem> findAllByBrandSortByNameIsFreebiesWithPaging(Company company, Group group, Brand brand, Boolean isFreebies, Order[] orders, int page, int maxResults, ArrayList<Long> idList) {
			//String [] paths = {"brand","categoryItemOtherFields","categoryItemOtherFields.otherField","comments"};
			//String [] aliases = {"brand","categoryItemOtherFields","otherField","comments"};
			//int [] joinTypes = {CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.INNER_JOIN,CriteriaSpecification.INNER_JOIN,CriteriaSpecification.INNER_JOIN};
			int arrayLength = 0;
			if(isFreebies) {
				arrayLength = 1;
			}
			else{
				arrayLength = 1;
			}
			String [] paths = new String[arrayLength];
			String [] aliases = new String[arrayLength];
			int [] joinTypes = new int[arrayLength];
			if(isFreebies){
				paths[0] = "brand";
				aliases[0] = "brand";
				joinTypes[0] = CriteriaSpecification.LEFT_JOIN;
			}
			else{
				paths[0] = "brand";
				aliases[0] = "brand";
				joinTypes[0] = CriteriaSpecification.LEFT_JOIN;
				
				/*
				paths[1] = "categoryItemOtherFields";
				aliases[1] = "categoryItemOtherFields";
				joinTypes[1] = CriteriaSpecification.INNER_JOIN;
				
				paths[2] = "categoryItemOtherFields.otherField";
				aliases[2] = "otherField";
				joinTypes[2] = CriteriaSpecification.INNER_JOIN;
				
				//paths[3] = "comments";
				//aliases[3] = "comments";
				//joinTypes[3] = CriteriaSpecification.INNER_JOIN;
				*/
			}
			
			//Order[] orders = {Order.asc("name")};
			
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("parentGroup", group));
			if(brand != null){
				junc.add(Restrictions.eq("brand", brand));
			}
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
			//for GurkkaTest
			/*
			if(company.getId() ==  CompanyConstants.GURKKA_TEST && !isFreebies){
			
				if(brand.getName().equalsIgnoreCase(GurkkaConstants.GUEST_BRAND_NAME))
				{
					//for Guest Price
					if(request.getParameter(GurkkaConstants.PARAM_NAME_GE_PRICE_GUEST_NAME) != null){
						Double priceVal = 0.0;
						try{
							priceVal = Double.parseDouble(request.getParameter(GurkkaConstants.PARAM_NAME_GE_PRICE_GUEST_NAME));
						}catch(Exception e){}
						junc.add(Restrictions.ge("price", priceVal));
					}
					if(request.getParameter(GurkkaConstants.PARAM_NAME_GT_PRICE_GUEST_NAME) != null){
						Double priceVal = 0.0;
						try{
							priceVal = Double.parseDouble(request.getParameter(GurkkaConstants.PARAM_NAME_GT_PRICE_GUEST_NAME));
						}catch(Exception e){}
						junc.add(Restrictions.gt("price", priceVal));
					}
					if(request.getParameter(GurkkaConstants.PARAM_NAME_LE_PRICE_GUEST_NAME) != null){
						Double priceVal = 0.0;
						try{
							priceVal = Double.parseDouble(request.getParameter(GurkkaConstants.PARAM_NAME_LE_PRICE_GUEST_NAME));
						}catch(Exception e){}
						junc.add(Restrictions.le("price", priceVal));
					}
					if(request.getParameter(GurkkaConstants.PARAM_NAME_LT_PRICE_GUEST_NAME) != null){
						Double priceVal = 0.0;
						try{
							priceVal = Double.parseDouble(request.getParameter(GurkkaConstants.PARAM_NAME_LT_PRICE_GUEST_NAME));
						}catch(Exception e){}
						junc.add(Restrictions.lt("price", priceVal));
					}
					
					if(request.getParameter(GurkkaConstants.PARAM_NAME_ORIGIN_GUEST_NAME) != null){
						junc.add(Restrictions.eq("categoryItemOtherFields.content", request.getParameter(GurkkaConstants.PARAM_NAME_ORIGIN_GUEST_NAME)));
						junc.add(Restrictions.eq("otherField.name", GurkkaConstants.PRODUCTS_OTHERFIELD_ORIGIN_NAME));
						System.out.println("###### inside  origin ######");
					}
					
					if(request.getParameter(GurkkaConstants.PARAM_NAME_CATEG_ID_GUEST_NAME) != null){
						Long parentOfParentCategoryId = 0L;
						try{
							parentOfParentCategoryId = Long.parseLong(request.getParameter(GurkkaConstants.PARAM_NAME_CATEG_ID_GUEST_NAME));
						}catch(Exception e){}
						Category parentOfParentCategory = categoryDelegate.find(parentOfParentCategoryId);
						if(parentOfParentCategory != null){
							junc.add(Restrictions.eq("parent.parentCategory", parentOfParentCategory));
						}
					}
					
					if(request.getParameter(GurkkaConstants.PARAM_NAME_PRODUCT_TYPE_GUEST_NAME) != null){
						junc.add(Restrictions.eq("categoryItemOtherFields.content", request.getParameter(GurkkaConstants.PARAM_NAME_PRODUCT_TYPE_GUEST_NAME)));
						junc.add(Restrictions.eq("otherField.name", GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME));
						System.out.println("###### inside  product type ######");
					}
					
					
				}
				
				if(brand.getName().equalsIgnoreCase(GurkkaConstants.MAIN_BRAND_NAME))
				{
					//for main PRICE
					if(request.getParameter(GurkkaConstants.PARAM_NAME_GE_PRICE_MAIN_NAME) != null){
						Double priceVal = 0.0;
						try{
							priceVal = Double.parseDouble(request.getParameter(GurkkaConstants.PARAM_NAME_GE_PRICE_MAIN_NAME));
						}catch(Exception e){}
						junc.add(Restrictions.ge("price", priceVal));
					}
					if(request.getParameter(GurkkaConstants.PARAM_NAME_GT_PRICE_MAIN_NAME) != null){
						Double priceVal = 0.0;
						try{
							priceVal = Double.parseDouble(request.getParameter(GurkkaConstants.PARAM_NAME_GT_PRICE_MAIN_NAME));
						}catch(Exception e){}
						junc.add(Restrictions.gt("price", priceVal));
					}
					if(request.getParameter(GurkkaConstants.PARAM_NAME_LE_PRICE_MAIN_NAME) != null){
						Double priceVal = 0.0;
						try{
							priceVal = Double.parseDouble(request.getParameter(GurkkaConstants.PARAM_NAME_LE_PRICE_MAIN_NAME));
						}catch(Exception e){}
						junc.add(Restrictions.le("price", priceVal));
					}
					if(request.getParameter(GurkkaConstants.PARAM_NAME_LT_PRICE_MAIN_NAME) != null){
						Double priceVal = 0.0;
						try{
							priceVal = Double.parseDouble(request.getParameter(GurkkaConstants.PARAM_NAME_LT_PRICE_MAIN_NAME));
						}catch(Exception e){}
						junc.add(Restrictions.lt("price", priceVal));
					}
					
					if(request.getParameter(GurkkaConstants.PARAM_NAME_ORIGIN_MAIN_NAME) != null){
						junc.add(Restrictions.eq("categoryItemOtherFields.content", request.getParameter(GurkkaConstants.PARAM_NAME_ORIGIN_MAIN_NAME)));
						junc.add(Restrictions.eq("otherField.name", GurkkaConstants.PRODUCTS_OTHERFIELD_ORIGIN_NAME));
						System.out.println("###### inside  origin 2 ######");
					}
					
					System.out.println("### 1 ###");
					if(request.getParameter(GurkkaConstants.PARAM_NAME_CATEG_ID_MAIN_NAME) != null){
						Long parentOfParentCategoryId = 0L;
						try{
							System.out.println("### 2 ###");
							parentOfParentCategoryId = Long.parseLong(request.getParameter(GurkkaConstants.PARAM_NAME_CATEG_ID_MAIN_NAME));
							System.out.println("###########-- Category ID of Parent Parent Category ::: " + request.getParameter(GurkkaConstants.PARAM_NAME_CATEG_ID_MAIN_NAME) + " ############");
						}catch(Exception e){System.out.println("### 3 ###");}
						Category parentOfParentCategory = categoryDelegate.find(parentOfParentCategoryId);
						System.out.println("##### Category Name :: " + parentOfParentCategory.getName());
						System.out.println("### 4 ###");
						if(parentOfParentCategory != null){
							System.out.println("### 5###");
							junc.add(Restrictions.eq("parent.getParentCategory()", parentOfParentCategory));
						}
					}
					System.out.println("### 6 ###");
					
					if(request.getParameter(GurkkaConstants.PARAM_NAME_PRODUCT_TYPE_MAIN_NAME) != null){
						junc.add(Restrictions.eq("categoryItemOtherFields.content", request.getParameter(GurkkaConstants.PARAM_NAME_PRODUCT_TYPE_MAIN_NAME)));
						junc.add(Restrictions.eq("otherField.name", GurkkaConstants.PRODUCTS_OTHERFIELD_PRODUCT_TYPE_NAME));
						System.out.println("###### inside  product type 2 ######");
					}
					
				}
			}
			
			*/
			
			if(isFreebies){
				//FOR FREEBIES
				
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar now = Calendar.getInstance();
			        now.set(Calendar.HOUR, 0);
			        now.set(Calendar.MINUTE, 0);
			        now.set(Calendar.SECOND, 0);
			        //System.out.println(sdf.format(now.getTime()));
			        now.set(Calendar.HOUR_OF_DAY, 0);
			        //System.out.println(sdf.format(now.getTime()));
					
					
					String str_fromDate = String.valueOf(sdf.format(now.getTime()));
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c1 = Calendar.getInstance();
					c1.setTime(formatter.parse(str_fromDate));
					str_fromDate = formatter.format(c1.getTime());
					Date fromDate = (Date)formatter.parse(str_fromDate);
					//before creating a criterion, update the validity date of freebies
					
					
					//Criterion criterionRefreshable = Restrictions.le("itemDate", fromDate);
					
					
					
				}
				catch(Exception e){
				
				}
				
			}
			else{
				//FOR MAIN PRODUCTS
				//junc.add(Restrictions.ne("sku", GurkkaConstants.FREEBIES_SKU));
			}
			if(idList != null){
				if(idList.size() > 0){
					junc.add(Restrictions.in("id", idList));
				}
			}
			if(brand == null){
				return findAllByCriterion(page, maxResults, null, null, null, orders, junc).getList();
			}
			return findAllByCriterion(page, maxResults, paths, aliases, joinTypes, orders, junc).getList();
		}
		
		public List<CategoryItem> findAllEnabledByGroupAndItemDate(Company company, Group group, Order[] orders, int page, int maxResults) {
			//String [] paths = {"brand"};
			//String [] aliases = {"brand"};
			//int [] joinTypes = {CriteriaSpecification.LEFT_JOIN};
			
			//Order[] orders = {Order.asc("name")};
			
			Junction junc = Restrictions.conjunction();
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar now = Calendar.getInstance();
		        now.set(Calendar.HOUR, 0);
		        now.set(Calendar.MINUTE, 0);
		        now.set(Calendar.SECOND, 0);
		        //System.out.println(sdf.format(now.getTime()));
		        now.set(Calendar.HOUR_OF_DAY, 0);
		        //System.out.println(sdf.format(now.getTime()));
				
				
				String str_fromDate = String.valueOf(sdf.format(now.getTime()));
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c1 = Calendar.getInstance();
				c1.setTime(formatter.parse(str_fromDate));
				str_fromDate = formatter.format(c1.getTime());
				Date fromDate = (Date)formatter.parse(str_fromDate);
				
				
				junc.add(Restrictions.eq("isValid", Boolean.TRUE));
				junc.add(Restrictions.eq("company", company));
				junc.add(Restrictions.eq("parentGroup", group));
				junc.add(Restrictions.eq("disabled", Boolean.FALSE));
				junc.add(Restrictions.ge("itemDate", fromDate));
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return findAllByCriterion(page, maxResults, null, null, null, orders, junc).getList();
		}

		public List<CategoryItem> findAllByName(Company company, String name) {
			// TODO Auto-generated method stub
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.like("name", name));

			return findAllByCriterion(null, null, null, null, null,	null, junc).getList();
		}
		
		public List<CategoryItem> findAllByKeyWordName(Company company, Group group, String keyWordName, MatchMode matchMode, Boolean isDisabled, Order[] orders) {
			Junction junc = Restrictions.conjunction();
			junc.add(Restrictions.eq("isValid", Boolean.TRUE));
			junc.add(Restrictions.eq("company", company));
			junc.add(Restrictions.eq("disabled", isDisabled));
			junc.add(Restrictions.eq("parentGroup", group));
			junc.add(Restrictions.ilike("name", keyWordName, MatchMode.START));
			
			return findAllByCriterion(-1, -1, null, null, null, orders, junc).getList();
		}
		
	public ObjectList<CategoryItem> findByCriteria(int resultPerPage, int pageNumber, Company company, 
			Group group, Category parent, Date dateFrom, Date dateTo, Order[] order, boolean showAll)
	{
		Junction junc = Restrictions.conjunction();
		Junction disjunction = Restrictions.disjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("company", company));
		if(company.getName().equals("pocketpons")){
			junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		}
		
		if(parent == null)
		{
			if(!showAll)
			{
				junc.add(Restrictions.isNull("parent"));
			}
		}
		else
		{
			String[] paths = { "parent", "parent.parentCategory" };
			String[] aliases = { "parent", "parentCategory1" };
			int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
			
			disjunction.add(Restrictions.eq("parent", parent));
			disjunction.add(Restrictions.eq("parent.parentCategory", parent));
			junc.add(disjunction);
			return findAllByCriterion(pageNumber, resultPerPage, paths, aliases, joinTypes, order, junc);
		}
		
		if(dateFrom != null)
		{
			junc.add(Restrictions.ge("itemDate", dateFrom));
		}
		
		if(dateTo != null)
		{
			junc.add(Restrictions.le("itemDate", dateTo));
		}
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, order, junc);
	}
	
	public BigInteger getFreebiesRemainingDaysCount(CategoryItem categoryItem){
		StringBuilder query = new StringBuilder();
		query.append("select (case when item_date >= NOW() then DATEDIFF(NOW(), item_date) when NOW() > item_date then DATEDIFF(NOW(), DATE_ADD(item_date, INTERVAL (((DATEDIFF(item_date, NOW()) DIV 7)+1)*7) DAY) ) END) from category_item where id = :currentCategoryItemID and sku = 'Freebies' and item_date not null");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCategoryItemID", categoryItem.getId());
		
		return (BigInteger) q.uniqueResult();
	}
	
	public Boolean hasNewUpdate(Company company, Group group, Date date, Long pid, Category category){
		List<CategoryItem> listOfNewUpdates = new ArrayList<CategoryItem>();
		Junction junc = Restrictions.conjunction();
		Order[] order = {Order.asc("createdOn")};
		
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.ge("updatedOn", date));
		if(pid > 0L){
			
			junc.add(Restrictions.eq("parent", category));
		}
		System.out.println("##id value : "+pid);
		
		listOfNewUpdates = findAllByCriterion(-1, -1, null, null, null, order, junc).getList();
		if(listOfNewUpdates.size() > 0){
			return Boolean.TRUE;
		}
		else{
			return Boolean.FALSE;
		}
		
	}
	
	public Boolean hasNewGrandChildUpdate(Company company, Group group, Date date, Long pid, Category category){
		List<CategoryItem> listOfNewUpdates = new ArrayList<CategoryItem>();
		Junction junc = Restrictions.conjunction();
		Junction disjunction = Restrictions.disjunction();
		Order[] order = {Order.asc("createdOn")};
		String[] paths = { "parent", "parent.parentCategory" };
		String[] aliases = { "parent", "parentCategory1" };
		int[] joinTypes = { CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.ge("updatedOn", date));
		if(pid > 0L){
			
			disjunction.add(Restrictions.eq("parent", category));
			disjunction.add(Restrictions.eq("parent.parentCategory", category));
			junc.add(disjunction);
			
			listOfNewUpdates = findAllByCriterion(-1, -1,  paths, aliases, joinTypes, order, junc).getList();
		}
		else{
			System.out.println("##id value : "+pid);
			
			listOfNewUpdates = findAllByCriterion(-1, -1, null, null, null, order, junc).getList();
		}
		if(listOfNewUpdates.size() > 0){
			return Boolean.TRUE;
		}
		else{
			return Boolean.FALSE;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CategoryItem> findAllByImageKeyword(Company company, String keyword)
	{
		final Criteria criteria = getSession().createCriteria(CategoryItem.class);
		final Junction conj = Restrictions.conjunction();
		final Junction disj = Restrictions.disjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		
		disj.add(Restrictions.ilike("images.title", keyword, MatchMode.ANYWHERE));
		disj.add(Restrictions.ilike("images.caption", keyword, MatchMode.ANYWHERE));
		disj.add(Restrictions.ilike("images.description", keyword, MatchMode.ANYWHERE));
		
		conj.add(disj);
		
		criteria.add(conj)
			.createAlias("images", "images")
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}

	public ObjectList<CategoryItem> findAll(Company company, String sku, String name, Boolean valid)
	{
		final Junction junc = Restrictions.conjunction();
		
		if(valid != null)
		{
			junc.add(Restrictions.eq("isValid", valid));	
		}
		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.like("name", name));
		junc.add(Restrictions.like("sku", sku));
		
		return findAllByCriterion(null, null, null, null, null, null, junc);
	}
				
	
	public ObjectList<NameBean> findAllNameBeanByGroupNameAsc(Company company, Group group) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		Order[] orders = {Order.asc("name")};
		Projection proj = Projections.projectionList()
				.add(Projections.property("id"), "id")
				.add(Projections.property("name"), "name");;
		return findAllByCriterionProjection(NameBean.class, -1, -1, null, null, null, orders, proj, Transformers.aliasToBean(NameBean.class), junc);
	}
	
	public List<CountBean> findCountPerCategory(Company company, Group group){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		
		String[] paths = new String[]{"parent"};
		String[] aliases = new String[]{"parent"};
		int[] joins = {CriteriaSpecification.LEFT_JOIN};
		
		Order[] orders = new Order[]{Order.asc("parent.name")};
		
		Projection proj = Projections.projectionList()
				.add(Projections.property("parent.id"), "id")
				.add(Projections.property("parent.name"), "name")
				.add(Projections.rowCount(), "count")
				.add(Projections.groupProperty("parent"));
		
		return findAllByCriterionProjection(CountBean.class, -1, -1, paths, aliases, joins, orders, proj, Transformers.aliasToBean(CountBean.class), junc).getList();
	}
	
	
	public ObjectList<CategoryItem> findAllByPriceWithpaging(int page, int maxResults, Company company, Group group, Category parent,  Double minPrice, Double maxPrice, String bagType){
		
		String[] paths = null;
		String[] aliases = null;
		int[] joins = null;
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));			
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", group));
		
		if(parent != null){
			junc.add(Restrictions.eq("parent", parent));
		}
		
		if(minPrice != null){
			junc.add(Restrictions.ge("price", minPrice));
		}
		
		if(maxPrice != null){
			junc.add(Restrictions.le("price", maxPrice));
		}
		
		if(StringUtils.isNotBlank(bagType)){
			paths = new String[]{"categoryItemOtherFields"};
			aliases = new String[]{"categoryItemOtherField"};
			joins = new int[]{CriteriaSpecification.LEFT_JOIN};
			
			junc.add(Restrictions.eq("categoryItemOtherField.content", bagType));
		}
		
		return findAllByCriterion(page, maxResults, paths, aliases, joins, new Order[]{Order.asc("name")}, junc);
	}
	
	@SuppressWarnings("unchecked")
	public List<CategoryItem> findNoLoop(List<Long> ids){

		final Criteria criteria = getSession().createCriteria(clazz, "this");

		/*final Disjunction disj = Restrictions.disjunction();
		
		for(Long id: ids){
			disj.add(Restrictions.eq("id", id));	
		}*/
		String idsString = "";
		int ctr = 0;
		for(Long id: ids){
			idsString += id;	
			if(ctr++ != ids.size() - 1){
				idsString += ", ";
			}
		}
		
		final Conjunction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.sqlRestriction(" id in ("+ idsString +") "));
		
		Projection proj = Projections.projectionList()
				.add(Projections.property("id"), "id")
				.add(Projections.property("name"), "name" )
				.add(Projections.property("updatedOn"), "updatedOn")
				.add(Projections.property("createdOn"), "createdOn");
		
		criteria.add(conj);
		criteria.setProjection(proj);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(CategoryItem.class));
		
		List<CategoryItem> list = criteria.list();
		return list;
	}
	
	public CategoryItem findWithNewSession(Long id, Boolean openNewSession){
		if(openNewSession){
			if(id == null)
				return null;
			
			CategoryItem ret = null;
			Session session = null;
			
			try{
				session = HibernateUtil.getSessionFactory().openSession();
				ret = ClassHelper.cast(session.get(CategoryItem.class, id));
				
				Hibernate.initialize(ret.getCategoryItemOtherFields());
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				session.close();
			}
			return ret;
		}
		else{
			return find(id);
		}
	}
	
	
	
		
}

