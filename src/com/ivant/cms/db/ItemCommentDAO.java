package com.ivant.cms.db;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;

public class ItemCommentDAO extends AbstractBaseDAO<ItemComment>{
	public ItemCommentDAO() {
		super();
	}
	
	public Double getAverageValue(Company company, CategoryItem item) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT SUM(value)/(SELECT COUNT(*) FROM item_comment WHERE company_id = :currentCompanyID and item_id = :currentItemID and valid is true) FROM item_comment WHERE company_id = :currentCompanyID and item_id = :currentItemID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setLong("currentItemID", item.getId());
		
		return (Double) q.uniqueResult();
	}
	
	public Double getValuePercentage(Company company, CategoryItem item, Double[] value) {
		StringBuilder query = new StringBuilder();
		String appendWhereClause = "";
		String strsql = "";
		
		for(int i = 0; i < value.length; i++){
			if(i==0){
				appendWhereClause += " and (";
			}
			
			appendWhereClause += "  value = :currentValue" + String.valueOf(i) + " ";
			if(i == value.length - 1){
				appendWhereClause += " )";
			}
			else{
				appendWhereClause += " or ";
			}
		}
		strsql = "SELECT COUNT(*) FROM item_comment WHERE company_id = :currentCompanyID and item_id = :currentItemID and valid is true "+appendWhereClause;
		
		query.append(strsql);
		
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setLong("currentItemID", item.getId());
		for(int i = 0; i < value.length; i++){
			q.setDouble("currentValue" + String.valueOf(i), value[i]);
		}
		return Double.parseDouble(String.valueOf(q.uniqueResult()));
	}
	
	public BigInteger getItemCommentCount(Company currentCompany) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM item_comment WHERE company_id = :currentCompanyID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", currentCompany.getId());
		return (BigInteger) q.uniqueResult();
	}
	
	public ObjectList<ItemComment> findCommentsByValueAndContent(Company company, CategoryItem item, double value, String comment, Member member){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("item", item));
		conj.add(Restrictions.eq("value", value));
		conj.add(Restrictions.eq("content", comment));
		conj.add(Restrictions.eq("member", member));
		ObjectList<ItemComment> tempItemComment = this.findAllByCriterion(null, null, null, null, conj);
		
		if(tempItemComment != null){
			return tempItemComment;
		}
		return null;
	}
	
	public ObjectList<ItemComment> findCommentsByContent(Company company, SinglePage singlePage, String comment, Member member) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("page", singlePage));
		conj.add(Restrictions.eq("content", comment));
		conj.add(Restrictions.eq("member", member));
		ObjectList<ItemComment> tempItemComment = this.findAllByCriterion(null, null, null, null, conj);
		if(tempItemComment != null) {
			return tempItemComment;
		}
		return null;
	}
	
	public ObjectList<ItemComment> findChildCommentsByValueAndContent(Company company, CategoryItem item, ItemComment itemComment, double value, String comment, Member member){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("item", item));
		conj.add(Restrictions.eq("parentItemComment", itemComment));
		conj.add(Restrictions.eq("value", value));
		conj.add(Restrictions.eq("content", comment));
		conj.add(Restrictions.eq("member", member));
		ObjectList<ItemComment> tempItemComment = this.findAllByCriterion(null, null, null, null, conj);
		if(tempItemComment != null){
			return tempItemComment;
		}
		return null;
		
	}
	
	public ObjectList<ItemComment> findChildCommentsByContent(Company company, SinglePage singlePage, ItemComment itemComment, String comment, Member member) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("page", singlePage));
		conj.add(Restrictions.eq("parentItemComment", itemComment));
		conj.add(Restrictions.eq("content", comment));
		conj.add(Restrictions.eq("member", member));
		ObjectList<ItemComment> tempItemComment = this.findAllByCriterion(null, null, null, null, conj);
		if(tempItemComment != null){
			return tempItemComment;
		}
		return null;
	}
	
	public ObjectList<ItemComment> findAllByMemberAndItemContent(Company company, CategoryItem categoryItem, Member member, String content ){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("item", categoryItem));
		conj.add(Restrictions.eq("member", member));
		conj.add(Restrictions.eq("content", content));
		
		ObjectList<ItemComment> tempItemComment = this.findAllByCriterion(null, null, null, null, conj);
		if(tempItemComment != null){
			if(tempItemComment.getList() != null){
				try{
					if(tempItemComment.getList().size() > 0){
						return tempItemComment;
					}
				}catch(Exception e){
					return tempItemComment;
				}
			}
		}
		return tempItemComment;
	}
	
	public ObjectList<ItemComment> getCommentsByItem(CategoryItem item){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("item", item));
		return this.findAllByCriterion(null, null, null, null, conj);
	}
	
	
	public ObjectList<ItemComment> getCommentsByPage(SinglePage page, Order...orders){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("page", page));
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", page.getCompany()));
		return this.findAllByCriterion(null, null, null, orders, conj);
	}
	
	public ObjectList<ItemComment> getCommentsByItem(CategoryItem item, Order...orders){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("item", item));
		conj.add(Restrictions.eq("company", item.getCompany()));
		return this.findAllByCriterion(null, null, null, orders, conj);
	}
	
	public ObjectList<ItemComment> findAllParentCommentsByItem(CategoryItem item, Order...orders){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("item", item));
		conj.add(Restrictions.eq("company", item.getCompany()));
		conj.add(Restrictions.isNull("parentItemComment"));
		return this.findAllByCriterion(null, null, null, orders, conj);
	}
	
	public ObjectList<ItemComment> findAllParentCommentsBySinglePage(SinglePage singlePage, Order...orders){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("page", singlePage));
		conj.add(Restrictions.eq("company", singlePage.getCompany()));
		conj.add(Restrictions.isNull("parentItemComment"));
		return this.findAllByCriterion(null, null, null, orders, conj);
		
	}
	
	public ObjectList<ItemComment> findAllParentCommentsBySinglePagePublished(SinglePage singlePage, Order...orders){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("page", singlePage));
		conj.add(Restrictions.eq("company", singlePage.getCompany()));
		conj.add(Restrictions.eq("published", Boolean.TRUE));
		conj.add(Restrictions.isNull("parentItemComment"));
		return this.findAllByCriterion(null, null, null, orders, conj);
		
	}
	
	public Integer findAllParentCommentsByItemSize(CategoryItem item, Order...orders){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("item", item));
		conj.add(Restrictions.eq("company", item.getCompany()));
		conj.add(Restrictions.isNull("parentItemComment"));
		List<ItemComment> tempList = findAllByCriterion(null, null, null, orders, conj).getList();
		if(tempList != null){
			return tempList.size();
		}
		return 0;
	}
	
	public ObjectList<ItemComment> getCommentsByItemWithPaging(CategoryItem item, int resultPerPage, int pageNumber, Order...orders) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("item", item));
		conj.add(Restrictions.eq("company", item.getCompany()));
		return this.findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, conj); 
	}
	
	public ObjectList<ItemComment> getCommentsByCompanyWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));

		return this.findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, conj); 
	}
	
	public ObjectList<ItemComment> getCommentsByType(Company company, String type, Order...orders) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.isNotNull(type));		

		return this.findAllByCriterion(null, null, null, orders, conj); 
	}
	
	public ObjectList<ItemComment> findAllLatestCommentByGivenId(Company company, CategoryItem categoryItem, Long latestCommentId, Order...orders){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("item", categoryItem));
		conj.add(Restrictions.gt("id", latestCommentId));
		
		return this.findAllByCriterion(null, null, null, orders, conj);
		
	}
	
	public ObjectList<ItemComment> getCommentsByCompany(Company company){
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM item_comment WHERE company_id = :currentCompanyID and valid is true ORDER BY created_on ASC");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		return (ObjectList<ItemComment>) q.uniqueResult();
	}
	
	public ObjectList<ItemComment> getTruecareComments(Company company, Order...orders){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));	
		
		return this.findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<ItemComment> findLatestComments(Company company, int days, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);
		Date last2days = new Date(calendar.getTimeInMillis());
		
		junc.add(Restrictions.ge("createdOn", last2days));
		
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<ItemComment> findAllValid(Company company,  Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));	
		
		return this.findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<ItemComment> findAllValidByCategoryItem(Company company, CategoryItem item, Order...orders){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("item", item));

		return this.findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<ItemComment> findAllValidBySinglePage(Company company, SinglePage singlePage, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("page", singlePage));
		
		return this.findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<ItemComment> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));	
		
		return this.findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<ItemComment> findLatestCommentsWithPaging(Company company, int days,  int resultPerPage, int pageNumber, Order...orders) 
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));		
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);
		Date last30days = new Date(calendar.getTimeInMillis());
		
		junc.add(Restrictions.ge("createdOn", last30days));
		
		return this.findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}
	
	public Integer findSinglePageCommentCount(Company company, SinglePage singlePage) {
		/*
		 
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM cart_order WHERE company_id = :currentCompanyID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		return (BigInteger) q.uniqueResult();
		 
		 */
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(id) FROM item_comment WHERE company_id = :currentCompanyID and page_id = :currentPageID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		//q.setLong("currentMemberID", member.getId());
		q.setLong("currentPageID", singlePage.getId());
		try{
			return ((BigInteger) q.uniqueResult()).intValue();
		}catch(Exception e){
			return 0;
		}
		
		
	}
	
}
