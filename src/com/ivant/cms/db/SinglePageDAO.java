package com.ivant.cms.db;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;

public class SinglePageDAO extends AbstractBaseDAO<SinglePage>{

	public SinglePageDAO() {
		super();
	}
	
	public SinglePage find(Company company, String name, MultiPage parent) {
		SinglePage singlePage = null;
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
		
		List<SinglePage> singlePageList = findAllByCriterion(null, null, null, null, null,	null, junc).getList();
		if(singlePageList.size() > 0) 		{
			singlePage = singlePageList.get(0);
		}
		return singlePage;
	}
	
	public SinglePage findJsp(Company company, String jsp) {
		SinglePage singlePage = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("jsp", jsp));
		junc.add(Restrictions.isNull("parent"));
		
		List<SinglePage> singlePageList = findAllByCriterion(null, null, null, null, null,	null, junc).getList();
		if(singlePageList.size() > 0) 		{
			singlePage = singlePageList.get(0);
		}
		return singlePage;
	}
	
	public ObjectList<SinglePage> findAll(Company company, MultiPage multiPage, Order... order)
	{
		final Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		if(multiPage != null)
		{
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else
		{
			junc.add(Restrictions.isNull("parent"));
		}
		
		return findAllByCriterion(null, null, null, order, junc);
	}
	
	public ObjectList<SinglePage> findByKeyword(Company company, MultiPage multipage,String keyword){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		if(multipage != null) {
			junc.add(Restrictions.eq("parent", multipage));
		}
		/*	
	 * else {
			junc.add(Restrictions.isNull("parent"));
		} */
		
		Disjunction disjunc = Restrictions.disjunction();
		
		disjunc.add(Restrictions.ilike("content", keyword, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.ilike("name", keyword, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.ilike("title", keyword, MatchMode.ANYWHERE));
		
		junc.add(disjunc);

		return findAllByCriterion(null, null, null,	null, junc);
	}
	
	public ObjectList<SinglePage> findAllByParentAndKeywords(Company company, MultiPage multipage, List<String> keywords){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		if(multipage != null) {
			junc.add(Restrictions.eq("parent", multipage));
		}
		/*	
	 * else {
			junc.add(Restrictions.isNull("parent"));
		} */
		
		Disjunction disjunc = Restrictions.disjunction();
		
		if(keywords.size() > 0){
			   for(String keyword : keywords){
				   
				   disjunc.add(Restrictions.ilike("name", keyword, MatchMode.ANYWHERE));
			       disjunc.add(Restrictions.ilike("title", keyword, MatchMode.ANYWHERE));
			       disjunc.add(Restrictions.ilike("content", keyword, MatchMode.ANYWHERE));
			       junc.add(disjunc);
			   }
			   
			}

		return findAllByCriterion(null, null, null,	null, junc);
	}
		
	public ObjectList<SinglePage> findAllPublished(Company company, MultiPage multiPage, Order... order) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("hidden", Boolean.FALSE));
//		junc.add(Restrictions.le("validTo", new Date()));
		
		Disjunction disjunc = Restrictions.disjunction();
		//System.out.println("ALL PUBLISHED : "+(multiPage!=null && multiPage.getId() != 892L)+"    "+multiPage.getId());
		if(multiPage!=null && (multiPage.getId() != 892L && multiPage.getId() != 1732)){
			disjunc.add(Restrictions.ge("validTo", new Date()));
			disjunc.add(Restrictions.isNull("validTo"));
		}
		
		
		
		junc.add(disjunc);

		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		
		return findAllByCriterion(null, null, null, order, junc);
	}
	
	public ObjectList<SinglePage> findAllPublishedWithExpired(Company company, MultiPage multiPage, Order... order) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("hidden", Boolean.FALSE));
		
		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		
		return findAllByCriterion(null, null, null, order, junc);
	}	
	
	public ObjectList<SinglePage> findAllPublishedFeatured(Company company, MultiPage multiPage, Order... order) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("hidden", Boolean.FALSE));
		junc.add(Restrictions.eq("isSingleFeatured", Boolean.TRUE));
		
		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		
		return findAllByCriterion(null, null, null, order, junc);
	}	
	
	public ObjectList<SinglePage> findAllPublishedWithPaging(Company company, MultiPage multiPage, int resultPerPage, int pageNumber, Order... order) {			
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("hidden", Boolean.FALSE));
//		junc.add(Restrictions.le("validTo", new Date()));
		
		Disjunction disjunc = Restrictions.disjunction();
		disjunc.add(Restrictions.ge("validTo", new Date()));
		disjunc.add(Restrictions.isNull("validTo"));
		
		junc.add(disjunc);

		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				order, junc);
	}	
	
	public ObjectList<SinglePage> findAllArchiveWithPaging(Company company, MultiPage multiPage, int resultPerPage, int pageNumber, Order... order) {			
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("hidden", Boolean.FALSE));
		
		Disjunction disjunc = Restrictions.disjunction();
		disjunc.add(Restrictions.lt("validTo", new Date()));
		disjunc.add(Restrictions.isNull("validTo"));
		
		junc.add(disjunc);

		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				order, junc);
	}	
	
	public ObjectList<SinglePage> findAllWithPaging(Company company, MultiPage multiPage, int resultPerPage, int pageNumber, Order... order) {			
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				order, junc);
	}	
	
	public ObjectList<SinglePage> findAllForms(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("isForm", Boolean.TRUE));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	
//------------------	
	
	public ObjectList<SinglePage> findAllCurrent(Company company, MultiPage multiPage, int nodays, Order... order) {	
		
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(GregorianCalendar.DAY_OF_YEAR, (-1) * nodays);
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.ge("createdOn", rightNow.getTime()));
		
		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		return findAllByCriterion(null, null, null, 
				order, junc);
	}
	
	
	public ObjectList<SinglePage> findAllPast(Company company, MultiPage multiPage, int nodays, Order... order) {	
		
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(GregorianCalendar.DAY_OF_YEAR, (-1) * nodays);
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.lt("createdOn", rightNow.getTime()));
	
		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		return findAllByCriterion(null, null, null, 
				order, junc);
	}
	
	
	
	
	
//------------------	
	
	
	
	public ObjectList<SinglePage> findDescendingItems(Company company, MultiPage multiPage, Order... order) {	
		
		Calendar rightNow = Calendar.getInstance();
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		return findAllByCriterion(null, null, null, 
				order, junc);
	}
	
	
	
	
	public ObjectList<SinglePage> findYearItems(Company company, MultiPage multiPage, int year, Order... order) {	
		
		Calendar calendarFrom = GregorianCalendar.getInstance();
		calendarFrom.set(year,0,1);
		Date date1 = calendarFrom.getTime();
		Calendar calendarTo = GregorianCalendar.getInstance();
		calendarTo.set(year,11,31);
		Date date2 = calendarTo.getTime();
		
		//System.out.println("dateeeeeeeeee  " +  date1 + "   " +  date2);
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.between("createdOn", date1, date2));
		
		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		return findAllByCriterion(null, null, null, 
				order, junc);
	}

	public ObjectList<SinglePage> findAllFeatured(Company company) {		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("isSingleFeatured", Boolean.TRUE));
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	

	
	public int findMaxYear(Company company, MultiPage multiPage) {	
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parent", multiPage));
		
		Order order[] = {Order.desc("createdOn")};
		
		ObjectList<SinglePage> list = findAllByCriterion(null, null, null, order, junc);
		
		if(list != null && list.getSize() > 0) return list.getList().get(0).getCreatedOn().getYear()+1900;
		else return new Date().getYear()+1900;
	}
	
	public int findMinYear(Company company, MultiPage multiPage) {	
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("parent", multiPage));
		
		Order order[] = {Order.asc("createdOn")};
		
		ObjectList<SinglePage> list = findAllByCriterion(null, null, null, order, junc);
		
		if(list != null && list.getSize() > 0) return list.getList().get(0).getCreatedOn().getYear()+1900;
		else return new Date().getYear()+1900;
	}
	
	public ObjectList<SinglePage> findMonthYearItems(Company company, MultiPage multiPage, int year, int month) {
		Calendar calendarFrom = GregorianCalendar.getInstance();
		calendarFrom.set(year,month-1,1);
		Date date1 = calendarFrom.getTime();
		Calendar calendarTo = GregorianCalendar.getInstance();
		calendarTo.set(year,month-1,31);
		Date date2 = calendarTo.getTime();
		
		//System.out.println("dateeeeeeeeee  " +  date1 + "   " +  date2);
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.between("createdOn", date1, date2));
		
		if(multiPage != null) {
			junc.add(Restrictions.eq("parent", multiPage));
		}
		else {
			junc.add(Restrictions.isNull("parent"));
		}
		
		Order order[] = {Order.desc("createdOn")};
		return findAllByCriterion(null, null, null, order, junc);
	
	}

	@SuppressWarnings("unchecked")
	public List<SinglePage> findAllByImageKeyword(Company company, String keyword)
	{
		final Criteria criteria = getSession().createCriteria(SinglePage.class);
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
	
	
	
}
