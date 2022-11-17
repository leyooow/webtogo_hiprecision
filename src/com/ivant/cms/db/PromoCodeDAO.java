package com.ivant.cms.db;

import java.text.ParseException;
import java.util.Date;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.list.ObjectList;

public class PromoCodeDAO extends AbstractBaseDAO<PromoCode>
{

	public PromoCodeDAO() {
		super();
	}
	
	public PromoCode findByItems(Company company, String items) { 
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.like("items", items, MatchMode.ANYWHERE));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc).uniqueResult();
	}
	
	public PromoCode findByCode(Company company, String code) { 
		Junction junc = Restrictions.conjunction();
		//junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("code", code));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc).uniqueResult();
	}
	
	public PromoCode findByNote(Company company, String note) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("note", note));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc).uniqueResult();
	}

	public ObjectList<PromoCode> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}
	
	public ObjectList<PromoCode> findAllWithinExpiryDate(Company company) throws ParseException {
		DateTime dateTime = new DateTime();
		DateTimeFormatter df = DateTimeFormat.forPattern("MM-dd-yyyy");
		String text = dateTime.toString(df);
		DateTime dt = df.parseDateTime(text);
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.le("fromDate", dt));
		junc.add(Restrictions.ge("toDate", dt));
		junc.add(Restrictions.eq("isDisabled", Boolean.FALSE));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}

	public ObjectList<PromoCode> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Date fromDate, Date toDate, String note) {		
	
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		if (fromDate != null) {
			junc.add(Restrictions.eq("fromDate", fromDate));
		}
		if (fromDate != null && toDate != null) {
			junc.add(Restrictions.ge("fromDate", toDate));
			junc.add(Restrictions.le("toDate", toDate));
		}
		if (note != null)
			junc.add(Restrictions.eq("note", note));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, new Order[]{Order.desc("id")}, junc);
	}	
}
