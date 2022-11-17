package com.ivant.cms.delegate;

import java.util.Date;

import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import com.ivant.cms.db.SavedEmailDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.list.ObjectList;

public class SavedEmailDelegate extends AbstractBaseDelegate<SavedEmail, SavedEmailDAO>{

	private static SavedEmailDelegate instance = new SavedEmailDelegate();
	
	public static SavedEmailDelegate getInstance() {
		return instance;
	}
	
	public SavedEmailDelegate() {
		super(new SavedEmailDAO());
	}
	
	public ObjectList<SavedEmail> findAll(Company company) {
		return dao.findAll(company);
	}
	
	public ObjectList<SavedEmail> findAllPanasonic(Company company, Order...orders) {
		return dao.findAllPanasonic(company, orders);
	}
	
	public ObjectList<SavedEmail> findAllBlue(Company company) {
		return dao.findAllBlue(company);
	}
	
	public SavedEmail findByTruecareEmail(Company company, String email, String formName) 
	{
		return dao.findByTruecareEmail(company, email, formName);
	}
	
	public ObjectList<SavedEmail> findAllWithPaging(Company company, int pageNumber, int itemPerPage, Order...orders) {	
		return dao.findAllWithPaging(company, pageNumber, itemPerPage, orders);
	}
	
	public ObjectList<SavedEmail> findLatestEmail(Company company, int days) {
		return dao.findLatestEmail(company, days);
	}
	public ObjectList<SavedEmail> findEmailByDate(Company company, Date fromDate, Date toDate) {
		return dao.findEmailByDate(company, fromDate, toDate);
	}
	public ObjectList<SavedEmail> findEmailByDateAndFormName(Company company, Date fromDate, Date toDate, String formName) {
		return dao.findEmailByDateAndFormName(company, fromDate, toDate, formName);
	}
	public ObjectList<SavedEmail> findEmailByEmailDateValidatedAndFormName(Company company, Date fromDate, Date toDate, String formName) {
		return dao.findEmailByEmailDateValidatedAndFormName(company, fromDate, toDate, formName);
	}
	public ObjectList<SavedEmail> findEmailByDate(Date fromDate, Date toDate) {
		return dao.findEmailByDate(fromDate, toDate);
	}

	public ObjectList<SavedEmail> findByTestimonial(Company company, Date date, String testimonial) {
		// TODO Auto-generated method stub
		return dao.findByTestimonial(company, date, testimonial);
	}
	
	public SavedEmail findByDownloadLink(String downloadLink ,Company company){
		return dao.findByDownloadLink(downloadLink, company);
	}
	
	public ObjectList<SavedEmail> findEmailByDateAndFormNameWithPaging(Company company, DateTime fromDate, DateTime toDate, String formName, int pageNumber, int itemPerPage, Order...orders) {
		return dao.findEmailByDateAndFormNameWithPaging(company, fromDate, toDate, formName, pageNumber, itemPerPage, orders);
	}

	public ObjectList<SavedEmail> findEmailByFormName(Company company, String formName, Order orders) {
		// TODO Auto-generated method stub
		return dao.findByFormName(company, formName, orders);
	}
	
	public SavedEmail findByFormNameAndEmail(Company company, String email, String formName) 
	{
		return dao.findByFormNameAndEmail(company, email, formName);
	}

	public ObjectList<SavedEmail> findEmailByFormNameWithPaging(
			Company company, String formName, int pageNumber, int itemPerPage,
			Order...orders) {
		// TODO Auto-generated method stub
		return dao.findEmailByFormNameWithPaging(company, formName, pageNumber, itemPerPage, orders);
	}
}
