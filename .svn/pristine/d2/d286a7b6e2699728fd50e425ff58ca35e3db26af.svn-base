package com.ivant.cms.delegate;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.CategoryDAO;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class CategoryDelegate extends AbstractBaseDelegate<Category, CategoryDAO>{

	private static CategoryDelegate instance = new CategoryDelegate();
	
	public static CategoryDelegate getInstance() {
		return instance;
	}
	
	public CategoryDelegate() {
		super(new CategoryDAO());
	}
	
	public Category find(Company company, String name, Group parentGroup) {
		return dao.find(company, name, null, parentGroup);
	}
	
	public Category findContainsName(Company company, String name, Group parentGroup) {
		return dao.findContainsName(company, name, parentGroup);
	}
	
	public Category find(Company company, String name, Category parentCategory, Group parentGroup) {
		return dao.find(company, name, parentCategory, parentGroup);
	}
	
	public ObjectList<Category> findAll(Company company, Group parentGroup) {
		return dao.findAll(company, null, parentGroup, true);
	}
	
	public ObjectList<Category> findAll(Company company, Group parentGroup, boolean showAll) {
		return dao.findAll(company, null, parentGroup, showAll);
	}

	public ObjectList<Category> findAll(Company company, Category parentCategory, Group parentGroup) {
		return dao.findAll(company, parentCategory, parentGroup, true);
	}
	
	public ObjectList<Category> findAll(Company company, Category parentCategory, Group parentGroup, boolean showAll) {
		return dao.findAll(company, parentCategory, parentGroup, showAll);
	}
	
	public ObjectList<Category> findAll(Company company, Category parentCategory, Group parentGroup, boolean showAll, boolean hidden) {
		return dao.findAll(company, parentCategory, parentGroup, showAll, hidden);
	}
	
	public ObjectList<Category> findAllWithKeyword(Company company, Category parentCategory, Group parentGroup, String keyword, boolean showAll, boolean hidden) {
		return dao.findAllWithKeyword(company, parentCategory, parentGroup, keyword, showAll, hidden);
	}
	
	public ObjectList<Category> findAllPublished(Company company, Category parentCategory, Group parentGroup) {
		return dao.findAllPublished(company, parentCategory, parentGroup, true);
	}
	
	public ObjectList<Category> findAllPublished(Company company, Category parentCategory, Group parentGroup, boolean showAll) {
		return dao.findAllPublished(company, parentCategory, parentGroup, showAll);
	}
	
	public ObjectList<Category> findAllPublished(Company company, Category parentCategory, Group parentGroup, boolean showAll, Order...orders) {
		return dao.findAllPublished(company, parentCategory, parentGroup, showAll, orders);
	}
	
	public ObjectList<Category> findAllWithPaging(Company company, Group parentGroup,int resultPerPage, int pageNumber, boolean showAll) {
		return dao.findAllWithPaging(company, null, parentGroup, resultPerPage, pageNumber, null, showAll);
	}
	
	public ObjectList<Category> findAllWithPaging(Company company, Category parentCategory, Group parentGroup, int resultPerPage, int pageNumber, boolean showAll) {
		return dao.findAllWithPaging(company, parentCategory, parentGroup, resultPerPage, pageNumber, null, showAll);
	}
	
	public ObjectList<Category> findAllWithPaging(Company company, Category parentCategory, Group parentGroup, int resultPerPage, int pageNumber, Order[] orders, boolean showAll) {
		return dao.findAllWithPaging(company, parentCategory, parentGroup, resultPerPage, pageNumber, orders, showAll);
	} 
	
	public ObjectList<Category> findAllRootCategories(Company company, boolean showAll, Order...orders)
	{
		//System.out.println("categoryDelegate size: " + dao.findAllRootCategories(company).getSize() );
		return dao.findAllRootCategories(company, showAll, orders);
	}
	
	public ObjectList<Category> findAllRootCategories(Company company, Group parentGroup, boolean showAll, Order...orders)
	{
		//System.out.println("categoryDelegate size: " + dao.findAllRootCategories(company).getSize() );
		return dao.findAllRootCategories(company, parentGroup, showAll, orders);
	}
	
	public ObjectList<Category> findAllChildrenOfChildrenCategory(Company company, Category parentCat, int resultPerPage, int pageNumber, Order...orders)
	{
		return dao.findAllChildrenOfChildrenCategory(company, parentCat, resultPerPage, pageNumber, orders);
	}

	public List<Category> findAllChildrenByParentCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}
	public ObjectList<Category> findAll(Company company){
		return dao.findAll(company);
	}
	public ObjectList<Category> findAll(Company company, boolean hidden){
		return dao.findAll(company, hidden);
	}
	
	public Boolean hasNewUpdate(Company company, Group group, Date date, Long pid, Category category){
		return dao.hasNewUpdate(company, group, date, pid, category);
	}
	
	public Boolean hasNewSelfUpdate(Company company, Group group, Date date, Long pid, Category category){
		return dao.hasNewSelfUpdate(company, group, date, pid, category);
	}
}
