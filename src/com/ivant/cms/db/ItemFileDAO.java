package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.entity.list.ObjectList;

public class ItemFileDAO extends AbstractBaseDAO<ItemFile> {

	public ItemFileDAO() {
		super();
	}
	
	public ItemFile findFileID(Company company, long cartOrderItemID)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("item.id", cartOrderItemID));
		
		return (findAllByCriterion(null, null, null, null, junc)).getList().get(0);
	}
	
	public List<ItemFile> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, null, junc).getList();
	}
	
	public ItemFile findItemFileID(Company company, long realID)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("item.id", realID));
		List<ItemFile> list = (findAllByCriterion(null, null, null, null, junc)).getList();
		if((list != null) ? list.size() > 0 :  false )
			return list.get(0);
		else
			return null;
	}
	
	public ObjectList<ItemFile> findAll(Company company, CategoryItem categoryItem)
	{
		final Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("item", categoryItem));
		return findAllByCriterion(null, null, null, null, junc);
	}
	
}
