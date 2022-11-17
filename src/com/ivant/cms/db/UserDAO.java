package com.ivant.cms.db;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.UserType;

public class UserDAO extends AbstractBaseDAO<User>{

	//private Logger logger = Logger.getLogger(UserDAO.class);
	
	public UserDAO() {
		super();
	}
	
	
	
public ObjectList<User> find(String username, String password, Company company, boolean isSuperUser) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("username", username));
		conj.add(Restrictions.eq("password", password));
		
		if(isSuperUser) {
			conj.add(Restrictions.eq("userType", UserType.SUPER_USER));
		}
		else {
			if(company != null) {
				conj.add(Restrictions.eq("company", company));
			}
			else {
				conj.add(Restrictions.eq("userType", UserType.SUPER_USER));
			}
		}
		
		return findAllByCriterion(null, null, null, null, conj);
	}
	

public ObjectList<User> find(String username, String password, Company company, boolean isSuperUser, boolean isWTGAdmin) {			
	Junction conj = Restrictions.conjunction();
	conj.add(Restrictions.eq("isValid", Boolean.TRUE));
	conj.add(Restrictions.eq("username", username));
	conj.add(Restrictions.eq("password", password));
	
	if(isWTGAdmin) {
		conj.add(Restrictions.eq("userType", UserType.WEBTOGO_ADMINISTRATOR));
	}
	else {
		if(company != null) {
			conj.add(Restrictions.eq("company", company));
		}
		else {
			conj.add(Restrictions.eq("userType", UserType.WEBTOGO_ADMINISTRATOR));
		}
	}
	
	return findAllByCriterion(null, null, null, null, conj);
}
	
	
	public User findEmail(Company company, String email) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("email", email));
		conj.add(Restrictions.eq("company", company));
		
		ObjectList<User> users =  findAllByCriterion(null, null, null, null, conj);
		if(users.getSize() > 0) {
			return users.getList().get(0);
		}
		
		return null;
	}
	
	public ObjectList<User> findAllWithPaging(int resultPerPage, int pageNumber) {	
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				null, 
				Restrictions.eq("isValid", Boolean.TRUE));
	}
	
	public ObjectList<User> findAllWithPaging(int resultPerPage, int pageNumber, String[] orderBy) {	
		
		Order[] orders = null;
		
		if(orderBy.length > 0) {
			orders = new Order[orderBy.length];
			for(int i = 0; i < orderBy.length; i++) {
				orders[i] = Order.asc(orderBy[i]);
			}
		}
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, 
				Restrictions.eq("isValid", Boolean.TRUE));
	}
	
	public ObjectList<User> findByCompanyWithPaging(int resultPerPage, int pageNumber, String[] orderBy, Company company) {	
		
		Order[] orders = null;
		
		if(orderBy.length > 0) {
			orders = new Order[orderBy.length];
			for(int i = 0; i < orderBy.length; i++) {
				orders[i] = Order.asc(orderBy[i]);
			}
		}

		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company",company));
		
		Disjunction disjunc = Restrictions.disjunction();
		disjunc.add(Restrictions.eq("userType", UserType.COMPANY_ADMINISTRATOR));
		disjunc.add(Restrictions.eq("userType", UserType.COMPANY_STAFF));
		disjunc.add(Restrictions.eq("userType", UserType.NORMAL_USER));
		
		if(company.getName().equalsIgnoreCase("agian")){
			disjunc.add(Restrictions.eq("userType", UserType.SYSTEM_ADMINISTRATOR));
			disjunc.add(Restrictions.eq("userType", UserType.USER_GROUP_ADMINISTRATOR));
			disjunc.add(Restrictions.eq("userType", UserType.EVENTS_ADMINISTRATOR));
			disjunc.add(Restrictions.eq("userType", UserType.CONTENT_ADMINISTRATOR));
			disjunc.add(Restrictions.eq("userType", UserType.PORTAL_ADMINISTRATOR));
		}
		
		conj.add(disjunc);
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, conj);
	}	
	
	public List<User> findAllByCompany(Company company) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));		
		
		return findAllByCriterion(null, null, null, null, conj).getList();		
	}	
	
	public ObjectList<User> findAllByCompanyAgian(int resultPerPage, int pageNumber, String[] orderBy, Company company) {	
		Order[] orders = null;
		
		if(orderBy.length > 0) {
			orders = new Order[orderBy.length];
			for(int i = 0; i < orderBy.length; i++) {
				orders[i] = Order.asc(orderBy[i]);
			}
		}
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));	
		conj.add(Restrictions.not(Restrictions.eq("userType", UserType.SUPER_USER)));
		
		Disjunction disjunc = Restrictions.disjunction();
		disjunc.add(Restrictions.eq("userType", UserType.COMPANY_ADMINISTRATOR));
		disjunc.add(Restrictions.eq("userType", UserType.COMPANY_STAFF));
		disjunc.add(Restrictions.eq("userType", UserType.NORMAL_USER));
		disjunc.add(Restrictions.eq("userType", UserType.SYSTEM_ADMINISTRATOR));
		disjunc.add(Restrictions.eq("userType", UserType.USER_GROUP_ADMINISTRATOR));
		disjunc.add(Restrictions.eq("userType", UserType.EVENTS_ADMINISTRATOR));
		disjunc.add(Restrictions.eq("userType", UserType.CONTENT_ADMINISTRATOR));
		
		conj.add(disjunc);
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, conj);
	}	
	
	public List<User> findAllByLikeUsername(Company company, String username) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.ilike("username", username, MatchMode.ANYWHERE));
		
		return findAllByCriterion(null, null, null, null, conj).getList();
		
	}
	
	public ObjectList<User> findByType(UserType userType) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("userType", UserType.WEBTOGO_ADMINISTRATOR));

	
		return findAllByCriterion(null, null, null, null, conj);		
		
	}
	
	
	
	
}
