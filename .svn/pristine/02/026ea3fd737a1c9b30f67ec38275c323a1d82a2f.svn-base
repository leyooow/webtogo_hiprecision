package com.ivant.cms.delegate;

import com.ivant.cms.db.UserDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.UserType;
import com.ivant.utils.Encryption;

import java.util.List;

public class UserDelegate extends AbstractBaseDelegate<User, UserDAO>{

	private static UserDelegate instance = new UserDelegate();
	
	public static UserDelegate getInstance() {
		return instance;
	}
	
	private UserDelegate() {
		super(new UserDAO());
	}
	
	public User findAccount(String username, String password, Company company, boolean isSuperUser) {
		User user = null;
		ObjectList<User> users = dao.find(username, Encryption.hash(password), company, isSuperUser);
		if(users.getSize() > 0) { 
			user = users.getList().get(0);
		}
		return user;
	}
	
	public User findAccount(String username, String password, Company company, boolean isSuperUser, boolean isWTGAdmin) {
		User user = null;
		ObjectList<User> users = dao.find(username, Encryption.hash(password), company, isSuperUser, isWTGAdmin);
		if(users.getSize() > 0) { 
			user = users.getList().get(0);
		}
		return user;
	}
	
	public User findAccount(String username, String password, Company company) {
		User user = null;
		ObjectList<User> users = dao.find(username, Encryption.hash(password), company, false);
		if(users.getSize() > 0) { 
			user = users.getList().get(0);
		}
		return user;
	}
	
	
	public List<User> findByType(UserType userType) {
		ObjectList<User> users = dao.findByType(userType);
		return users.getList();
	}
	
	
	public User findEmail(Company company, String email) {
		return dao.findEmail(company, email);
	}
	
	public ObjectList<User> findAllWithPaging(int resultPerPage, int pageNumber) {
		return dao.findAllWithPaging(resultPerPage, pageNumber);
	}
	
	public ObjectList<User> findAllWithPaging(int resultPerPage, int pageNumber, String[] orderBy) {
		return dao.findAllWithPaging(resultPerPage, pageNumber, orderBy);
	}
	
	public List<User> findAllByCompany(Company company) {			
		return dao.findAllByCompany(company);
	}
	
	public ObjectList<User> findAllByCompanyAgian(int resultPerPage, int pageNumber, String[] orderBy, Company company ) {			
		return dao.findAllByCompanyAgian(resultPerPage, pageNumber, orderBy, company);
	}
	
	public List<User> findAllByLikeUsername(Company company, String username){
		return dao.findAllByLikeUsername(company, username);
	}
	
	/*find by company*/	
	public ObjectList<User> findByCompanyWithPaging(int resultPerPage, int pageNumber, String[] orderBy, Company company ) {
		return dao.findByCompanyWithPaging(resultPerPage, pageNumber, orderBy, company);
	}	
}
