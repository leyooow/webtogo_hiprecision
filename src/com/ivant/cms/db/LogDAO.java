package com.ivant.cms.db;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.joda.time.DateTime;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;
//import com.mysql.jdbc.StringUtils;

public class LogDAO extends AbstractBaseDAO<Log> {
	public LogDAO() {
		super();
	}
	
	public ObjectList<Log> getLogsLastWeek(Company company){
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("company", company));
		DateTime dateToday = new DateTime();
		junc.add(Restrictions.ge("createdOn", dateToday.minusDays(7).toDate()));
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<Log> getAllLogs(Company company, int pageNumber, int maxResult){
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("company", company));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(pageNumber, maxResult, null, null, null, orders, junc);
	}
	
	public ObjectList<Log> getAllLogs(User user, int pageNumber, int maxResult){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("updatedByUser", user));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(pageNumber, maxResult, null, null, null, orders, junc);
	}
	
	@SuppressWarnings("deprecation")
	public ObjectList<Log> getAllLogs(User user, int pageNumber, int maxResult, String startDate, String endDate){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("updatedByUser", user));
		
		String[] start = startDate.split("-");
		String[] end = endDate.split("-");
		
		Calendar calStart = Calendar.getInstance(); 
		calStart.set(
				Integer.parseInt(start[0]), 
				Integer.parseInt(start[1]), 
				Integer.parseInt(start[2])
			);
		
		Calendar calEnd = Calendar.getInstance(); 
		calEnd.set(
				Integer.parseInt(end[0]), 
				Integer.parseInt(end[1]), 
				Integer.parseInt(end[2])
			);
		
		Date dateS = new DateTime().toDate();
		Date dateE = new DateTime().toDate();
		dateS.setYear(Integer.parseInt(start[0])-1900);
		dateS.setMonth(Integer.parseInt(start[1])-1);
		dateS.setDate(Integer.parseInt(start[2]));
		dateS.setHours(0);
		dateS.setMinutes(0);
		dateS.setSeconds(0);
		
		dateE.setYear(Integer.parseInt(end[0])-1900);
		dateE.setMonth(Integer.parseInt(end[1])-1);
		dateE.setDate(Integer.parseInt(end[2]));
		dateE.setHours(23);
		dateE.setMinutes(59);
		dateE.setSeconds(59);
		
		//System.out.println(dateS);
		//System.out.println(dateE);
		
		junc.add(Restrictions.ge("createdOn", dateS));
		junc.add(Restrictions.le("createdOn", dateE));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(pageNumber, maxResult, null, null, null, orders, junc);
	}

	public ObjectList<Log> getAllLogs(SinglePage singlePage) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("entityID", singlePage.getId()));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(null, null, null, orders, junc);
	}

	public ObjectList<Log> getAllLogs(CategoryItem item) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("entityID", item.getId()));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<Log> getAllLogs(Long entityid, EntityLogEnum type) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("entityID", entityid));
		junc.add(Restrictions.eq("entityType", type));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(null, null, null, orders, junc);
	}

	public ObjectList<Log> getAllLogs(MultiPage multiPage) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("entityID", multiPage.getId()));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(null, null, null, orders, junc);
	}

	public ObjectList<Log> getAllLogs(Category category) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("entityID", category.getId()));
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public List<Log> findAllLogs(Company company, Long entityid, EntityLogEnum entityEnum, String[] transType) {
		Junction junc = Restrictions.conjunction();
		Junction junc2 = Restrictions.disjunction();
		
		junc.add(Restrictions.eq("entityID", entityid));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("entityType", entityEnum));
		
		for(int i=0; i<transType.length; i++)		
			junc2.add(Restrictions.eq("transactionType", transType[i]));
		
		junc.add(junc2);
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(null, null, null, orders, junc).getList();
	}

	public ObjectList<Log> findCategoryItemLogsWithFilter(Company company, String filter, EntityLogEnum entityEnum, int pageNumber, int maxResult) {
		Junction junc = Restrictions.conjunction();
						
		String[] aliases = null;
		String[] paths = null;
		int[] joinTypes = null;

		
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("entityType", entityEnum));
		
				
		if(StringUtils.isNotEmpty(filter)){
			
			aliases = new String[] {"categoryItem", "categoryItemOtherFields", "otherField"};
			paths = new String[] {"categoryItem", "categoryItem.categoryItemOtherFields", "categoryItem.categoryItemOtherFields.otherField"};
			joinTypes = new int[] {CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN, CriteriaSpecification.LEFT_JOIN };
			
			Junction junc2 = Restrictions.conjunction();
			junc2.add(Restrictions.eq("otherField.name", "TEST CODE(S)"));
			junc2.add(Restrictions.eq("categoryItemOtherFields.content", filter));
			
			Junction disjunc = Restrictions.disjunction();
			
			disjunc.add(Restrictions.eq("categoryItem.name", filter));
			disjunc.add(junc2);

			junc.add(disjunc);
			
//			projection = Projections.projectionList()
//					.add(Projections.property("categoryItem.id"), "categoryItem.id")
//					.add(Projections.property("createdOn"), "createdOn")
//					.add(Projections.property("remarks"), "remarks")
//					.add(Projections.property("categoryItem.name"), "categoryItem.name")
//					.add(Projections.property("categoryItemOtherFields.content"), "categoryItemOtherFields.content")
//					.add(Projections.property("updatedByUser.username"), "updatedByUser.username")
//					.add(Projections.groupProperty("categoryItem.id"));
//			
//			resultTransformer = Transformers.aliasToBean(Log.class);

		}				
		
		
		
		Order[] orders = {Order.desc("createdOn")};
		return findAllByCriterion(pageNumber, maxResult, paths, aliases, joinTypes, orders, junc);	
	}
}
