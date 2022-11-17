package com.ivant.cms.db;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.constants.CompanyConstants;

public class SavedEmailDAO extends AbstractBaseDAO<SavedEmail> {

	public ObjectList<SavedEmail> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(-1, 1000, null, null, null, null, junc);
	}
	
	/*bluewarner*/
	
	
	public ObjectList<SavedEmail> findAllBlue(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(-1, 1000000, null, null, null, null, junc);
	}
	
	
	/*bluewarner*/
	
	/*panasonic*/
	public ObjectList<SavedEmail> findAllPanasonic(Company company, Order...orders){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));	
		
		return this.findAllByCriterion(null, null, null, orders, junc);
	}
	/*panasonic*/
	
	public SavedEmail findByTruecareEmail(Company company, String email, String formName) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("email", email));
		junc.add(Restrictions.eq("formName", formName));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc).uniqueResult();
	}
	
	public SavedEmail findByFormNameAndEmail(Company company, String email, String formName) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("email", email));
		junc.add(Restrictions.eq("formName", formName));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc).uniqueResult();
	}
	
	public ObjectList<SavedEmail> findAllWithPaging(Company company, int pageNumber, int itemPerPage, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		if(company.getId() == CompanyConstants.SKECHERS) {
			junc.add(Restrictions.ne("formName", "Raffle entry form"));
		}
		
		return findAllByCriterion(pageNumber, itemPerPage, null, null, null, orders, junc);
	}
	
	public ObjectList<SavedEmail> findLatestEmail(Company company, int days) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);
		Date last3days = new Date(calendar.getTimeInMillis());
		
		junc.add(Restrictions.ge("createdOn", last3days));
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	public ObjectList<SavedEmail> findEmailByDate(Company company, Date fromDate, Date toDate) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));	
		junc.add(Restrictions.ge("createdOn",fromDate));
		//System.out.println(toDate);
		junc.add(Restrictions.le("createdOn",toDate));
				
		return findAllByCriterion(null, null, null, null, junc);
	}	
	
	public ObjectList<SavedEmail> findEmailByDateAndFormName(Company company, Date fromDate, Date toDate,String formName) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));	
		junc.add(Restrictions.ge("createdOn",fromDate));
		//System.out.println(toDate);
		junc.add(Restrictions.le("createdOn",toDate));
		junc.add(Restrictions.eq("formName", formName));
				
		return findAllByCriterion(null, null, null, null, junc);
	}	
	
	public ObjectList<SavedEmail> findEmailByEmailDateValidatedAndFormName(Company company, Date fromDate, Date toDate,String formName) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));	
		junc.add(Restrictions.ge("emailDateValidated",fromDate));
		//System.out.println(toDate);
		junc.add(Restrictions.le("emailDateValidated",toDate));
		junc.add(Restrictions.eq("formName", formName));
				
		return findAllByCriterion(null, null, null, null, junc);
	}	
	
	public ObjectList<SavedEmail> findEmailByDate(Date fromDate, Date toDate) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));	
		junc.add(Restrictions.ge("createdOn",fromDate));
		//System.out.println(toDate);
		junc.add(Restrictions.le("createdOn",toDate));
				
		return findAllByCriterion(null, null, null, null, junc);
	}

	public ObjectList<SavedEmail> findByTestimonial(Company company,
			Date date, String testimonial) {
		// TODO Auto-generated method stub
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("testimonial", testimonial));
		junc.add(Restrictions.ge("createdOn", date));
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public SavedEmail findByDownloadLink(String downloadLink ,Company company){
		SavedEmail savedEmail = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("downloadLink", downloadLink));
		
		try {
			savedEmail = findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return savedEmail;
	}

	public ObjectList<SavedEmail> findEmailByDateAndFormNameWithPaging(
			Company company, DateTime fromDate, DateTime toDate,
			String formName, int pageNumber, int itemPerPage, Order[] orders) {

		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		if(fromDate != null && toDate != null) {
			junc.add(Restrictions.between("createdOn", fromDate.toDate(), toDate.toDate()));
		}
		
		junc.add(Restrictions.eq("company", company));	
//		junc.add(Restrictions.ge("createdOn",fromDate.toDate()));
		//System.out.println(toDate);
//		junc.add(Restrictions.le("createdOn",toDate.toDate()));
		junc.add(Restrictions.eq("formName", formName));
				
		return findAllByCriterion(pageNumber, itemPerPage, null, null, null, orders, junc);

	}

	public ObjectList<SavedEmail> findByFormName(Company company,
			String formName, Order... orders) {
		// TODO Auto-generated method stub
		
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("formName", formName));

		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<SavedEmail> findEmailByFormNameWithPaging(Company company, String formName, int pageNumber, int itemPerPage, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("formName", formName));
		
		if(company.getId() == CompanyConstants.SKECHERS) {
			junc.add(Restrictions.ne("formName", "Raffle entry form"));
		}
		
		return findAllByCriterion(pageNumber, itemPerPage, null, null, null, orders, junc);
	}
	
	public Boolean isValidBlueWarnerPromoCode(String code){
		Session session = getSession();
		Query query = session.createSQLQuery("");
		return false;
	}
	
}
