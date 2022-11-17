package com.ivant.cms.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class CategoryDAO extends AbstractBaseDAO<Category>{

	public CategoryDAO() {
		super();
	}
	
	public Category find(Company company, String name, Category parentCategory, Group parentGroup) {
		Category category = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("name", name));
		junc.add(Restrictions.eq("parentGroup", parentGroup));
		
		
		if(parentCategory != null) {
			junc.add(Restrictions.eq("parentCategory", parentCategory));
		} 
		else {
			junc.add(Restrictions.isNull("parentCategory"));
		}
		
		try {
			category = findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return category;
	}
	
	public Category findContainsName(Company company, String name, Group parentGroup) {
		Category category = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		junc.add(Restrictions.eq("parentGroup", parentGroup));
		
		
		
		try {
			category = findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return category;
	}
	
	public ObjectList<Category> findAll(Company company, Category parentCategory, Group parentGroup, boolean showAll) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", parentGroup));
		
		if(parentCategory == null) {
			if(!showAll) {
				junc.add(Restrictions.isNull("parentCategory"));
			}
		}
		else { 
			junc.add(Restrictions.eq("parentCategory", parentCategory));
		}
		
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}
	
	public ObjectList<Category> findAll(Company company, Category parentCategory, Group parentGroup, boolean showAll, boolean hidden) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", parentGroup));
		junc.add(Restrictions.eq("hidden", hidden));
		
		if(parentCategory == null) {
			if(!showAll) {
				junc.add(Restrictions.isNull("parentCategory"));
			}
		}
		else { 
			junc.add(Restrictions.eq("parentCategory", parentCategory));
		}
		
		final Order[] orders = new Order[] {Order.asc("sortOrder")};
		
		return findAllByCriterion(null, null, null,orders, junc);
	}
	
	public ObjectList<Category> findAllWithKeyword(Company company, Category parentCategory, Group parentGroup, 
			String keyword, boolean showAll, boolean hidden)
	{
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", parentGroup));
		junc.add(Restrictions.eq("hidden", hidden));
		
		if(parentCategory == null) {
			if(!showAll) {
				junc.add(Restrictions.isNull("parentCategory"));
			}
		}
		else { 
			junc.add(Restrictions.eq("parentCategory", parentCategory));
		}
		
		if(StringUtils.isNotEmpty(keyword))
		{
			Disjunction disj = Restrictions.disjunction();
			
			disj.add(Restrictions.ilike("name", keyword, MatchMode.ANYWHERE));
			disj.add(Restrictions.ilike("item.name", keyword, MatchMode.ANYWHERE));
			disj.add(Restrictions.ilike("item.description", keyword, MatchMode.ANYWHERE));
			disj.add(Restrictions.ilike("item.shortDescription", keyword, MatchMode.ANYWHERE));
			
			junc.add(disj);
			
			return findAllByCriterion(new String[] { "items" }, new String[] { "item" }, new int[] { Criteria.INNER_JOIN }, 
					null, null,	null, junc);
		}
		
		return findAllByCriterion(null,null,null, null, junc);
	}
	
	public ObjectList<Category> findAllPublished(Company company, Category parentCategory, Group parentGroup, boolean showAll, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("hidden", Boolean.FALSE));
		
		if(parentGroup != null) {
			junc.add(Restrictions.eq("parentGroup", parentGroup));
		}
		
		if(parentCategory == null) {
			if(!showAll) {
				junc.add(Restrictions.isNull("parentCategory"));
			}
		}
		else { 
			junc.add(Restrictions.eq("parentCategory", parentCategory));
		}
		
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<Category> findAllWithPaging(Company company, Category parentCategory, Group parentGroup, int resultPerPage, int pageNumber, Order[] orders, boolean showAll) {				
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", parentGroup));
		
		if(parentCategory == null) {
			if(!showAll) {
				junc.add(Restrictions.isNull("parentCategory"));
			}
		}
		else { 
			junc.add(Restrictions.eq("parentCategory", parentCategory));
		}
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, junc);
	}	
	
	
	public ObjectList<Category> findAllRootCategories (Company company, boolean showAll, Order...orders)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.isNull("parentCategory"));
		junc.add(Restrictions.eq("hidden", Boolean.FALSE));
		return findAllByCriterion(null, null, null, orders, junc);
		
	}	
	
	public ObjectList<Category> findAllRootCategories (Company company, Group parentGroup, boolean showAll, Order...orders)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parentGroup", parentGroup));
		junc.add(Restrictions.isNull("parentCategory"));
		junc.add(Restrictions.eq("hidden", Boolean.FALSE));
		return findAllByCriterion(null, null, null, orders, junc);
		
	}
	
	public ObjectList<Category> findAllChildrenOfChildrenCategory(Company company, Category parentCat, int resultPerPage, int pageNumber, Order...orders)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("hidden", Boolean.FALSE));
		
		Junction disjunc = Restrictions.disjunction();
		if(parentCat.getChildrenCategory() != null)
		{
			for(Category ct:parentCat.getChildrenCategory())
			{
				disjunc.add(Restrictions.eq("parentCategory", ct));
			}
			
			junc.add(disjunc);
		}
		
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}

	public ObjectList<Category> findAll(Company company){
		Order[] orders = {Order.asc("sortOrder")};
		Junction junc=Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null,orders, junc);
	}
	
	public ObjectList<Category> findAll(Company company, boolean hidden){
		Order[] orders = {Order.asc("sortOrder")};
		Junction junc=Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("hidden", hidden));
		return findAllByCriterion(null, null, null,orders, junc);
	}
	
	public Boolean hasNewUpdate(Company company, Group group, Date date, Long pid, Category category){
		List<Category> listOfNewUpdates = new ArrayList<Category>();
		Junction junc = Restrictions.conjunction();
		Order[] order = {Order.asc("createdOn")};
		
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.ge("updatedOn", date));
		if(pid > 0L){
			
			junc.add(Restrictions.eq("parentCategory", category));
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
	
	public Boolean hasNewSelfUpdate(Company company, Group group, Date date, Long pid, Category category){
		List<Category> listOfNewUpdates = new ArrayList<Category>();
		Junction junc = Restrictions.conjunction();
		Order[] order = {Order.asc("createdOn")};
		
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("parentGroup", group));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.ge("updatedOn", date));
		if(pid > 0L){
			
			junc.add(Restrictions.eq("id", category.getId()));
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
	
}
