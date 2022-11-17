package com.ivant.cms.helper;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.ivant.utils.Encryption;

public class UserHelper {

	public static User createUser(String username, String password, String email,
					String firstname, String lastname, UserType userType, Company company) {
		 
		User user = new User();
		user.setUsername(username);
		user.setPassword(Encryption.hash(password));
		user.setEmail(email);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setIsValid(true);
		//user.setCreatedOn(new Date());
		//user.setUpdatedOn(new Date());
		user.setUserType(userType);
		user.setCompany(company);
		
		return user;
	}
	
}
