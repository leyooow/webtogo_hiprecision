
package com.ivant.cms.delegate;

import com.ivant.cms.db.ContactUsDAO;
import com.ivant.cms.entity.ContactUs;

public class ContactUsDelegate extends AbstractBaseDelegate<ContactUs, ContactUsDAO> {

	private static ContactUsDelegate instance = new ContactUsDelegate();
	private static ContactUsDAO dao = new ContactUsDAO();
	
	public static ContactUsDelegate getInstance() {
		return ContactUsDelegate.instance;
	}
		
	public ContactUsDelegate() {
		super(new ContactUsDAO());
	}
	
	@Override
	public ContactUs find(Long companyId) {
		return dao.find(companyId);
	}
	
	@Override
	public boolean update(ContactUs cu)
	{
		return dao.update(cu);
	}
	
	public ContactUs findContactUsByCompany(Long companyid)
	{
		return dao.findContactUsByCompany(companyid);
	}
	
}
