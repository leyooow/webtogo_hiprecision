package com.ivant.cms.delegate;

import com.ivant.cms.db.GroupDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class GroupDelegate extends AbstractBaseDelegate<Group, GroupDAO>{

	private static GroupDelegate instance = new GroupDelegate();
	
	public static GroupDelegate getInstance() {
		return instance;
	}
	
	public GroupDelegate() {
		super(new GroupDAO());
	}
	
	public Group find(Company company, String name) {
		return dao.find(company, name, false);
	}
	
	public Group find(Company company, Long gid) {
		return dao.find(company, gid);
	}
	
	public Group findByKeyword(Company company, String keyword)
	{
		return dao.find(company, keyword, true);
	}
	
	public ObjectList<Group> findAll(Company company) {
		return dao.findAll(company, null);
	}
	
	public ObjectList<Group> findAll(Company company, String[] order) {
		return dao.findAll(company, order);
	}
	
	public ObjectList<Group> findAllFeatured(Company company) {
		return dao.findAllFeatured(company);
	}
	
	public ObjectList<Group> findAllWithPaging(Company company, int resultPerPage, int pageNumber, String[] order) {
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, order);
	}
	
	public ObjectList<Group> findAllWithPaging(Company company, int resultPerPage, int pageNumber) {
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, null);
	}
	
	public ObjectList<Group> findAllWithPagingAndUserRights(Company company, Long[] forbiddenGroupIds, int resultPerPage, int pageNumber) {
		return dao.findAllWithPagingAndUserRights(company, forbiddenGroupIds, resultPerPage, pageNumber, null);
	}
	
}
