package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.WishlistTypeDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.WishlistType;
import com.ivant.cms.entity.list.ObjectList;

public class WishlistTypeDelegate extends AbstractBaseDelegate<WishlistType, WishlistTypeDAO> {
	
	private static WishlistTypeDelegate instance = new WishlistTypeDelegate();
	
	public static WishlistTypeDelegate getInstance() {
		return instance;
	}
	
	public WishlistTypeDelegate() {
		super(new WishlistTypeDAO());
	}
	
	public WishlistType findByName(Company company, Member member, String name){
		return dao.findByName(company, member, name.replace("'", "&rsquo;").replace("’", "&rsquo;"));
	}
	
	public ObjectList<WishlistType> findAll(Company company, Member member){
		return dao.findAll(company, member);
	}
	
	public ObjectList<WishlistType> findByIdList(Company company, Member member, List<Long> idList, Boolean isEqualToCollection){
		return dao.findByIdList(company, member, idList, isEqualToCollection);
	}
	
}
