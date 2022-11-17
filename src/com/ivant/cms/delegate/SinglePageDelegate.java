package com.ivant.cms.delegate;

import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.SinglePageDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;

public class SinglePageDelegate extends AbstractBaseDelegate<SinglePage, SinglePageDAO>{

	private static SinglePageDelegate instance = new SinglePageDelegate();
		
	public static SinglePageDelegate getInstance() {
		return instance;
	}
	
	public SinglePageDelegate() {
		super(new SinglePageDAO());
	}
	
	public SinglePage find(Company company, String name) {
		return dao.find(company, name, null);
	}
	
	public SinglePage find(Company company, String name, MultiPage parent) {
		return dao.find(company, name, parent);
	}
	
	public SinglePage findJsp(Company company, String jsp) {
		return dao.findJsp(company,jsp);
	}
	
	public ObjectList<SinglePage> findAll(Company company) {
		return dao.findAll(company, null);
	}
	
	public ObjectList<SinglePage> findAll(Company company, Order... order) {
		return dao.findAll(company, null, order);
	}
		
	public ObjectList<SinglePage> findAll(Company company, MultiPage multiPage) {		
		return dao.findAll(company, multiPage);
	}
	
	public ObjectList<SinglePage> findByKeyword(Company company,MultiPage multipage,String keyword){		
		return dao.findByKeyword(company, multipage, keyword);
	}
	
	public ObjectList<SinglePage> findAllByParentAndKeywords(Company company,MultiPage multipage, List<String> keywords){		
		return dao.findAllByParentAndKeywords(company, multipage, keywords);
	}
	
	public ObjectList<SinglePage> findAll(Company company, MultiPage multiPage, Order... order) {		
		return dao.findAll(company, multiPage, order);
	}
	
	public ObjectList<SinglePage> findAllForms(Company company) {
		return dao.findAllForms(company);
	}
	
	public ObjectList<SinglePage> findAllPublished(Company company, MultiPage multiPage, Order... order) {		
		return dao.findAllPublished(company, multiPage, order);
	}
	
	public ObjectList<SinglePage> findAllPublishedWithExpired(Company company, MultiPage multiPage, Order... order) {		
		return dao.findAllPublishedWithExpired(company, multiPage, order);
	}
	
	public ObjectList<SinglePage> findAllPublishedFeatured(Company company, MultiPage multiPage, Order... order) {		
		return dao.findAllPublishedFeatured(company, multiPage, order);
	}	
			
	public ObjectList<SinglePage> findAllPublishedWithPaging(Company company, MultiPage multiPage, int resultPerPage, int pageNumber, Order... order) {
		return dao.findAllPublishedWithPaging(company, multiPage, resultPerPage, pageNumber, order);
	} 
		
	public ObjectList<SinglePage> findAllWithPaging(Company company, int resultPerPage, int pageNumber) {
		return dao.findAllWithPaging(company, null, resultPerPage, pageNumber);
	}
	 
	public ObjectList<SinglePage> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Order... order) {
		return dao.findAllWithPaging(company, null, resultPerPage, pageNumber, order);
	}
	
	public ObjectList<SinglePage> findAllWithPaging(Company company, MultiPage multiPage, int resultPerPage, int pageNumber) {
		return dao.findAllWithPaging(company, multiPage, resultPerPage, pageNumber);
	}
	 
	public ObjectList<SinglePage> findAllWithPaging(Company company, MultiPage multiPage, int resultPerPage, int pageNumber, Order... order) {
		return dao.findAllWithPaging(company, multiPage, resultPerPage, pageNumber, order);
	}
	
	public void disableAll(Company company) {
		List<SinglePage> singlePages = findAll(company).getList();
		try {
			for(SinglePage singlePage : singlePages) {
				singlePage.setIsHome(false);
			}
		}
		catch(Exception e) {}
	}
	
	public ObjectList<SinglePage> findAllArchiveWithPaging(Company company, MultiPage multiPage, int resultPerPage, int pageNumber, Order... order) {			
		return dao.findAllArchiveWithPaging(company, multiPage, resultPerPage, pageNumber, order);
	}
	
//-------------
	
	public ObjectList<SinglePage> findAllCurrent(Company company, MultiPage multiPage, int nodays, Order... order ) {
		return dao.findAllCurrent(company, multiPage, nodays, order);
	}
	
	public ObjectList<SinglePage> findAllPast(Company company, MultiPage multiPage, int nodays, Order... order ) {		
		return dao.findAllPast(company, multiPage, nodays, order);
	}
	
	
//-------------	
	
	
	public ObjectList<SinglePage> findYearItems(Company company, MultiPage multiPage, int year, Order... order ) {
		return dao.findYearItems(company, multiPage, year, order);
	
	}
	
	
	public ObjectList<SinglePage> findDescendingItems(Company company, MultiPage multiPage, Order... order ) {
		return dao.findDescendingItems(company, multiPage, order);
	}

	public ObjectList<SinglePage> findAllFeatured(Company company) {
		return dao.findAllFeatured(company);
	}

	public int findMaxYear(Company company, MultiPage multiPage) {	
		return dao.findMaxYear(company,multiPage);
	}
	
	public int findMinYear(Company company, MultiPage multiPage) {	
		return dao.findMinYear(company,multiPage);
	}
	
	public ObjectList<SinglePage> findMonthYearItems(Company company, MultiPage multiPage, int year, int month) {
		return dao.findMonthYearItems(company, multiPage, year, month);
	
	}
	
	public List<SinglePage> findAllByImageKeyword(Company company, String keyword)
	{
		return dao.findAllByImageKeyword(company, keyword);
	}
	
}
