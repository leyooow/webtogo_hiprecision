package com.ivant.cms.ws.rest.resource;

import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;

import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.utils.hibernate.HibernateUtil;

/**
 * 
 * @author Kent
 *
 */
public abstract class AbstractResource
		implements ServletContextAware
{
	protected ServletContext servletContext;
	
	protected static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	protected static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	protected static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	protected static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	protected static final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	protected static final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	protected static final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	protected static final ShoppingCartDelegate shoppingCartDelegate = ShoppingCartDelegate.getInstance();
	protected static final ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();
	protected static final CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	protected static final CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	
	protected static final MemberFileDelegate memberFileDelegate = MemberFileDelegate.getInstance();
	
	
	public static final String HOST_HEADER = "host";
	public static final String COMPANY_ID_HEADER_NAME = "companyId";
	public static final String COMPANY_KEY_HEADER_NAME = "companyKey";
	
	protected Company company;
	
	protected Company getCompany(HttpHeaders headers)
	{
		Company company = null;
		try 
		{
			String companyId = headers.getRequestHeader(COMPANY_ID_HEADER_NAME).get(0);
			company = companyDelegate.find(Long.parseLong(companyId));
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return company;
	}
	
	protected void openSession()
	{
		if(!HibernateUtil.hasSessionCreated()) HibernateUtil.createSession();
	}
	
	protected void closeSession()
	{
		if(HibernateUtil.hasSessionCreated()) HibernateUtil.destroySession();
	}
	
	protected void ThrowNoDataException()
	{
		throwWebApplicationException(Status.NO_CONTENT);
	}
	
	protected Status throwWebApplicationException(Status status)
	{
		throw new WebApplicationException(status);
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) 
	{
		this.servletContext = servletContext;
	}

}
