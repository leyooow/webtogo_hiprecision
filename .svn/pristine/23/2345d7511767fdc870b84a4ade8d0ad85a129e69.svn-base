package com.ivant.cms.db;

import java.util.Date;

import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ShippingTable;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.UserType;

public class ShippingTableDAO extends AbstractBaseDAO<ShippingTable>{

	//private Logger logger = Logger.getLogger(UserDAO.class);
	
	public ShippingTableDAO() {
		super();
	}
	
	
	
public ObjectList<ShippingTable> find(String username, String password, Company company, boolean isSuperUser) {			
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
	

public ObjectList<ShippingTable> find(String username, String password, Company company, boolean isSuperUser, boolean isWTGAdmin) {			
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
	
	
	public ShippingTable findEmail(Company company, String email) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("email", email));
		conj.add(Restrictions.eq("company", company));
		
		ObjectList<ShippingTable> users =  findAllByCriterion(null, null, null, null, conj);
		if(users.getSize() > 0) {
			return users.getList().get(0);
		}
		
		return null;
	}
	
	public ObjectList<ShippingTable> findAllWithPaging(int resultPerPage, int pageNumber) {	
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				null, 
				Restrictions.eq("isValid", Boolean.TRUE));
	}
	
	public ObjectList<ShippingTable> findAllWithPaging(int resultPerPage, int pageNumber, String[] orderBy) {	
		
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
	
	public ObjectList<ShippingTable> findByCompanyWithPaging(int resultPerPage, int pageNumber, String[] orderBy, Company company) {	
		
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
		
		Disjunction disjunc = Restrictions.disjunction();
		disjunc.add(Restrictions.eq("userType", UserType.COMPANY_ADMINISTRATOR));
		disjunc.add(Restrictions.eq("userType", UserType.COMPANY_STAFF));
		
		conj.add(disjunc);
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders,	conj);
	}	
	
	public ObjectList<ShippingTable> findByType(UserType userType) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("userType", UserType.WEBTOGO_ADMINISTRATOR));

	
		return findAllByCriterion(null, null, null, null, conj);		
		
	}
	
	
	
	
}
