package com.ivant.cms.db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.WishlistType;
import com.ivant.cms.entity.list.ObjectList;

public class WishlistTypeDAO extends AbstractBaseDAO<WishlistType> {

	public WishlistType findByName(Company company, Member member, String name){
		final Junction junc = Restrictions.conjunction();
		Order[] orders = {Order.asc("id")};
		List<WishlistType> res = new ArrayList<WishlistType>();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		junc.add(Restrictions.eq("name", name));
		res = findAllByCriterion(-1, -1, null, null, null, orders, junc).getList();
		if(res != null){
			if(res.size() > 0){
				return res.get(0);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	public ObjectList<WishlistType> findAll(Company company, Member member){
		final Junction junc = Restrictions.conjunction();
		Order[] orders = {Order.asc("name")};
		List<WishlistType> res = new ArrayList<WishlistType>();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		return findAllByCriterion(-1, -1, null, null, null, orders, junc);
		
	}
	
	public ObjectList<WishlistType> findByIdList(Company company, Member member, List<Long> idList, Boolean isEqualToCollection){
		final Junction junc = Restrictions.conjunction();
		Order[] orders = {Order.asc("name")};
		List<WishlistType> res = new ArrayList<WishlistType>();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		if(isEqualToCollection){
			junc.add(Restrictions.in("id", idList));
		}
		else{
			junc.add(Restrictions.not( Restrictions.in("id", idList)) );
		}
		
		
		return findAllByCriterion(-1, -1, null, null, null, orders, junc);
	}
}
