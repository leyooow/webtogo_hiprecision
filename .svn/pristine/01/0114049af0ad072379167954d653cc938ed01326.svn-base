package com.ivant.cms.db;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.ivant.cms.beans.CountBean;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.helper.ClassHelper;
import com.ivant.utils.hibernate.HibernateUtil;

public class CategoryItemOtherFieldDAO
		extends AbstractBaseDAO<CategoryItemOtherField>
{
	public CategoryItemOtherFieldDAO() {
		super();
	} 
	
	public CategoryItemOtherField findByCategoryItemOtherField(Company company, CategoryItem categoryItem, OtherField otherField)
	{
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("otherField", otherField));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("categoryItem", categoryItem));
		
		List<CategoryItemOtherField> temp = this.findAllByCriterion(null, null, null, null, junc).getList();
		
		if(temp != null)
		{
			if(temp.size() > 0)
				return temp.get(0);
			else
				return null;
		}
		else
			return null;
	}
	
	public List<CategoryItemOtherField> findAllByCategoryItemOtherField(Company company, CategoryItem categoryItem, OtherField otherField)
	{
		final Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("otherField", otherField));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("categoryItem", categoryItem));
		
		return findAllByCriterion(null, null, null, null, junc).getList();
	}
	
	/*for category item*/
	public ObjectList<CategoryItemOtherField> findAll(Company company){
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		String[] path = {"categoryItem"};
		Order[] orders = {Order.asc("categoryItem.name")};
		int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		
		return findAllByCriterion(path, null, joinType, orders, junc);
	}
	
	
	
	
	/*for category item*/
	public ObjectList<CategoryItemOtherField> findDescItemsWithPaging(Company company,Brand brand, List<OtherField> otherFields,List<String> otherFieldValues, int resultPerPage, int pageNumber, Order...orders) {
		ObjectList<CategoryItemOtherField> items = null;
		//list dapat ung ipapasa na otherField pati ung values nya
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));

		Junction junc1 = Restrictions.disjunction();
		for(OtherField otherfield: otherFields)
		junc1.add(Restrictions.eq("otherField", otherfield));

		junc.add(junc1);
		
		Junction junc2 = Restrictions.disjunction();
		for(String otherFieldValue : otherFieldValues)
		junc2.add(Restrictions.eq("content", otherFieldValue));

		junc.add(junc2);

		try {
		items = findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
		}
		catch(Exception e) {}

		return items;
		}

	
	
	public ObjectList<CategoryItemOtherField> findAll(Company company, CategoryItem categoryItem){
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("categoryItem", categoryItem));
		
		String[] path = {"categoryItem"};
		Order[] orders = {Order.asc("categoryItem.name")};
		int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		
		return findAllByCriterion(path, null, joinType, orders, junc);
	}
	/*added for category item other field mapping*/
	public ObjectList<CategoryItemOtherField> findAllByOtherFieldId(Company company, OtherField otherField){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("other_field",otherField));
		return findAllByCriterion(null,null,null,null,junc);
	}
	
	public ObjectList<CategoryItemOtherField> findByCategoryItem(Company company, CategoryItem item) {
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("categoryItem", item));
		
		String[] path = {"otherField"};
		Order[] orders = {Order.asc("otherField.sortOrder")};
		int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		
		return findAllByCriterion(path, path, joinType, orders, junc);
	}
	
	public CategoryItemOtherField findByOtherFieldKeyword(Company company, CategoryItem categoryItem, String otherFieldName, boolean isKeyword)
	{
		final Junction conj = Restrictions.conjunction();
		final String[] path = {"otherField"};
		int[] joinTypes = {CriteriaSpecification.LEFT_JOIN};
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("categoryItem", categoryItem));
		if(isKeyword)
		{
			conj.add(Restrictions.like("otherField.name", otherFieldName, MatchMode.ANYWHERE));
		}
		else
		{
			conj.add(Restrictions.eq("otherField.name", otherFieldName));	
		}
		
		return findAllByCriterion(path, path, joinTypes, null, conj).uniqueResult();
		
	}
	
	public ObjectList<CategoryItemOtherField> findByNameContains(Company company, String nameContent){
		String nameContent2 = nameContent;
		if(company.getName().equalsIgnoreCase("wendys")){
			nameContent2 = ";" + nameContent + ";";
		}
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.ilike("name", nameContent2, MatchMode.ANYWHERE));
		
		String[] path = {"categoryItem"};
		Order[] orders = {Order.asc("categoryItem.name")};
		int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		return findAllByCriterion(path, null, joinType, orders, junc);
	}
	
	public ObjectList<CategoryItemOtherField> findByContent(Company company, String keyWord){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("content", keyWord));
		
		String[] path = {"categoryItem"};
		//Order[] orders = {Order.asc("categoryItem.name")};
		//int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		
		return findAllByCriterion(path, null, null, null, junc);
	}
	
	public List<String> findAllByContentAndOtherFieldList(Company company, String content, List<OtherField> ofList){
		StringBuilder query = new StringBuilder();
		String otherFieldListId = "(";
		String appender = ",";
		if(ofList != null){
			try{
				for(int i = 0; i < ofList.size(); i++){
					if(i == (ofList.size() -1)){
						appender = ")";
					}
					otherFieldListId += String.valueOf(ofList.get(i).getId()) + appender;
				}
			}catch(Exception e){}
		}
		query.append("SELECT distinct category_item_id FROM category_item_other_field WHERE company_id = :currentCompanyID and other_field_id in (900,901,902,903,904,905,906,907,908,909) and content = :catId");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		//q.setLong("currentMemberID", member.getId());
		//q.setString("otherFieldID", otherFieldListId);
		q.setString("catId", content);
		try{
			return ((List<String>) q.list());
		}catch(Exception e){
			System.out.println("ERROR IN SELECT SQL : " + e.toString());
			return new ArrayList<String>();
		}
	}
	
	
	public List<CountBean> findItemCountPerOtherFieldValue(Company company, Group group, OtherField otherField){
		String[] paths = {"categoryItem"};
		String[] aliases = {"categoryItem"};
		int[] joins = {CriteriaSpecification.LEFT_JOIN};
		Order[] orders = {Order.asc("content")};
		
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("categoryItem.parentGroup", group));
		junc.add(Restrictions.eq("otherField", otherField));
		
		ProjectionList projections = Projections.projectionList()
				.add(Projections.groupProperty("content"))
				.add(Projections.rowCount(), "count")
				.add(Projections.alias(Projections.property("content"), "name"));
		
		return findAllByCriterionProjection(CountBean.class, -1, -1, paths, aliases, joins, orders, projections, Transformers.aliasToBean(CountBean.class), junc).getList();
	}

	public CategoryItemOtherField findWithNewSession(Long id, boolean openNewSession) {	
		if(openNewSession){
			if(id == null)
				return null;
			
			CategoryItemOtherField ret = null;
			Session session = null;
			
			try{
				session = HibernateUtil.getSessionFactory().openSession();
				ret = ClassHelper.cast(session.get(CategoryItemOtherField.class, id));
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
