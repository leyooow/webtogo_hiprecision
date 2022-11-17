package com.ivant.cms.delegate;

import com.ivant.cms.db.ShippingTableDAO;
import com.ivant.cms.db.UserDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ShippingTable;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.UserType;
import com.ivant.utils.Encryption;

import java.util.List;

public class ShippingTableDelegate extends AbstractBaseDelegate<ShippingTable, ShippingTableDAO>{

	private static ShippingTableDelegate instance = new ShippingTableDelegate();
	
	public static ShippingTableDelegate getInstance() {
		return instance;
	}
	
	private ShippingTableDelegate() {
		super(new ShippingTableDAO());
	}
	
	public ShippingTable findAccount(String username, String password, Company company, boolean isSuperUser) {
		ShippingTable user = null;
		ObjectList<ShippingTable> users = dao.find(username, Encryption.hash(password), company, isSuperUser);
		if(users.getSize() > 0) { 
			user = users.getList().get(0);
		}
		return user;
	}
	
	public ShippingTable findAccount(String username, String password, Company company, boolean isSuperUser, boolean isWTGAdmin) {
		ShippingTable user = null;
		ObjectList<ShippingTable> users = dao.find(username, Encryption.hash(password), company, isSuperUser, isWTGAdmin);
		if(users.getSize() > 0) { 
			user = users.getList().get(0);
		}
		return user;
	}
	
	public ShippingTable findAccount(String username, String password, Company company) {
		ShippingTable user = null;
		ObjectList<ShippingTable> users = dao.find(username, Encryption.hash(password), company, false);
		if(users.getSize() > 0) { 
			user = users.getList().get(0);
		}
		return user;
	}
	
	
	public List<ShippingTable> findByType(UserType userType) {
		ObjectList<ShippingTable> users = dao.findByType(userType);
		return users.getList();
	}
	
	public ShippingTable findEmail(Company company, String email) {
		return dao.findEmail(company, email);
	}
	
	public ObjectList<ShippingTable> findAllWithPaging(int resultPerPage, int pageNumber) {
		return dao.findAllWithPaging(resultPerPage, pageNumber);
	}
	
	public ObjectList<ShippingTable> findAllWithPaging(int resultPerPage, int pageNumber, String[] orderBy) {
		return dao.findAllWithPaging(resultPerPage, pageNumber, orderBy);
	}
	
	/*find by company*/	
	public ObjectList<ShippingTable> findByCompanyWithPaging(int resultPerPage, int pageNumber, String[] orderBy, Company company ) {
		return dao.findByCompanyWithPaging(resultPerPage, pageNumber, orderBy, company);
	}	
}
