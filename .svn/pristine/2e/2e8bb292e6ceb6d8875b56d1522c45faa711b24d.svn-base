package com.ivant.cms.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.entity.list.ObjectList;

public class RegistrationItemOtherFieldDAO
		extends AbstractBaseDAO<RegistrationItemOtherField>
{
	public RegistrationItemOtherFieldDAO() {
		super();
	} 
	
	public RegistrationItemOtherField findByCategoryItemOtherField(Company company, Member member, OtherField otherField)
	{
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("otherField", otherField));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		List<RegistrationItemOtherField> temp = this.findAllByCriterion(null, null, null, null, junc).getList();
		
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
	
	public RegistrationItemOtherField findByName(Company company, String name, Long member) {
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member.id", member));
		junc.add(Restrictions.eq("otherField.name", name.trim()));
		
		String[] path = {"otherField"};
		Order[] orders = {Order.asc("otherField.sortOrder")};
		int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		
		return findAllByCriterion(path, path, joinType, orders, junc).uniqueResult();
	}
	
	public RegistrationItemOtherField findByName(Company company, String name, Long member, String note, Integer indexing) {
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member.id", member));
		junc.add(Restrictions.eq("otherField.name", name.trim()));
		if(note!=null){
			junc.add(Restrictions.eq("note", note));
		}
		if(indexing!=null){
			junc.add(Restrictions.eq("index", indexing));
		}
		
		String[] path = {"otherField"};
		Order[] orders = {Order.asc("otherField.sortOrder")};
		int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		
		return findAllByCriterion(path, path, joinType, orders, junc).uniqueResult();
	}
	
	
	public ObjectList<RegistrationItemOtherField> findAll(Company company, Member member){
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		String[] path = {"member"};
		Order[] orders = null; //{Order.asc("member.lastName")}; ?!?!?!?!?!
		int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		
		return findAllByCriterion(path, null, joinType, orders, junc);
	}
	
	public ObjectList<RegistrationItemOtherField> findAll(Company company, Member member,String note, Integer indexing){
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		if(note!=null){
			junc.add(Restrictions.eq("note", note));
		}
		if(indexing!=null){
			junc.add(Restrictions.eq("index", indexing));
		}

		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<RegistrationItemOtherField> findAllWithIndexing(Company company, Member member,String note){
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		junc.add(Restrictions.isNotNull("index"));
		
		if(note!=null){
			junc.add(Restrictions.eq("note", note));
		}
		
		Order[] orders = {Order.asc("index")};

		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<RegistrationItemOtherField> findAllYearWithIndexing(Company company, String note){	
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member.isValid", Boolean.TRUE));
		junc.add(Restrictions.isNotNull("index"));
		
		if(note==null){
			junc.add(Restrictions.in("note", new String[] { "2007", "2008", "2009", "2010", "2011" })); 
		}
		
		String[] path = {"member"};
		Order[] orders = {Order.asc("note"), Order.asc("index")};
		int[] joinType = {CriteriaSpecification.LEFT_JOIN};

		return findAllByCriterion(path, null, joinType, orders, junc);
	}

	public int findTotalHours(Company company){	
		Criteria criteria = getSession().createCriteria(RegistrationItemOtherField.class);
		criteria.createAlias("member", "member");
		criteria.createAlias("otherField", "otherField");
		criteria.add(Restrictions.eq("company", company));
  		criteria.add(Restrictions.eq("otherField.name", "Grand Total Number of Hours"));
		criteria.add(Restrictions.eq("member.isValid", Boolean.TRUE));
		criteria.add(Restrictions.eq("isValid", Boolean.TRUE));
		criteria.setProjection(Projections.sum("content"));
		
		//System.out.println("CRITERIA RESULT ... : " + criteria.uniqueResult());
		
		return criteria.uniqueResult() != null ? Integer.parseInt(criteria.uniqueResult().toString()) : 0;
		
		//return Integer.parseInt();
	}	

	public ObjectList<RegistrationItemOtherField> findAllWithNote(Company company, Member member,String note){
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		if(note==null)
			junc.add(Restrictions.isNull("note"));
		else
			junc.add(Restrictions.eq("note", note));
		Order[] orders = {Order.asc("index")};

		return findAllByCriterion(null, null, null, orders, junc);
	}

	public ObjectList<RegistrationItemOtherField> findByCategoryItem(Company company, Member member) {
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		String[] path = {"otherField"};
		Order[] orders = {Order.asc("otherField.sortOrder")};
		int[] joinType = {CriteriaSpecification.LEFT_JOIN};
		
		return findAllByCriterion(path, path, joinType, orders, junc);
	}
}
