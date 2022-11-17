/**
 *
 */
package com.ivant.cms.ws.rest.client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @author Edgar S. Dacpano
 *
 */
public class ClientConfigurationServlet
		extends HttpServlet
{

	private static ClientConfigurationServlet instance;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** For Wendys */
	private String wendysUri1;
	private String wendysUri2;
	private String wendysPublicKey1;
	private String wendysPublicKey2;
	private String wendysPrivateKey1;
	private String wendysPrivateKey2;
	private String wendysHeaderNamePublicKey1;
	private String wendysHeaderNamePublicKey2;
	private String wendysHeaderNameSharedSecret1;
	private String wendysHeaderNameSharedSecret2;
	private String wendysParameterNameDateRequest1;
	private String wendysParameterNameDateRequest2;
	public static final String WENDYS_PARAM_NAME_URI_1 = "wendysUri1";
	public static final String WENDYS_PARAM_NAME_URI_2 = "wendysUri2";
	public static final String WENDYS_PARAM_NAME_PUBLIC_KEY_1 = "wendysPublicKey1";
	public static final String WENDYS_PARAM_NAME_PUBLIC_KEY_2 = "wendysPublicKey2";
	public static final String WENDYS_PARAM_NAME_PRIVATE_KEY_1 = "wendysPrivateKey1";
	public static final String WENDYS_PARAM_NAME_PRIVATE_KEY_2 = "wendysPrivateKey2";
	public static final String WENDYS_PARAM_NAME_HEADER_NAME_PUBLIC_KEY_1 = "wendysHeaderNamePublicKey1";
	public static final String WENDYS_PARAM_NAME_HEADER_NAME_PUBLIC_KEY_2 = "wendysHeaderNamePublicKey2";
	public static final String WENDYS_PARAM_NAME_HEADER_NAME_SHARED_SECRET_1 = "wendysHeaderNameSharedSecret1";
	public static final String WENDYS_PARAM_NAME_HEADER_NAME_SHARED_SECRET_2 = "wendysHeaderNameSharedSecret2";
	public static final String WENDYS_PARAM_NAME_PARAMETER_NAME_DATE_REQUEST_1 = "wendysParameterNameDateRequest1";
	public static final String WENDYS_PARAM_NAME_PARAMETER_NAME_DATE_REQUEST_2 = "wendysParameterNameDateRequest2";
	
	/** other companies... */

	
	/**
	 * Do not instantiate, use {@link #getInstance()} instead.
	 */
	public ClientConfigurationServlet()
	{
		super();
	}
	
	@Override
	public void init() throws ServletException
	{
		ClientConfigurationServlet.instance = this;
		
		setWendysUri1(getInitParameter(WENDYS_PARAM_NAME_URI_1));
		setWendysUri2(getInitParameter(WENDYS_PARAM_NAME_URI_2));
		setWendysPublicKey1(getInitParameter(WENDYS_PARAM_NAME_PUBLIC_KEY_1));
		setWendysPublicKey2(getInitParameter(WENDYS_PARAM_NAME_PUBLIC_KEY_2));
		setWendysPrivateKey1(getInitParameter(WENDYS_PARAM_NAME_PRIVATE_KEY_1));
		setWendysPrivateKey2(getInitParameter(WENDYS_PARAM_NAME_PRIVATE_KEY_2));
		setWendysHeaderNamePublicKey1(getInitParameter(WENDYS_PARAM_NAME_HEADER_NAME_PUBLIC_KEY_1));
		setWendysHeaderNamePublicKey2(getInitParameter(WENDYS_PARAM_NAME_HEADER_NAME_PUBLIC_KEY_2));
		setWendysHeaderNameSharedSecret1(getInitParameter(WENDYS_PARAM_NAME_HEADER_NAME_SHARED_SECRET_1));
		setWendysHeaderNameSharedSecret2(getInitParameter(WENDYS_PARAM_NAME_HEADER_NAME_SHARED_SECRET_2));
		setWendysParameterNameDateRequest1(getInitParameter(WENDYS_PARAM_NAME_PARAMETER_NAME_DATE_REQUEST_1));
		setWendysParameterNameDateRequest2(getInitParameter(WENDYS_PARAM_NAME_PARAMETER_NAME_DATE_REQUEST_2));
	}
	
	// GETTERS
	
	public static final ClientConfigurationServlet getInstance()
	{
		return instance;
	}

	public String getWendysUri1()
	{
		return wendysUri1;
	}

	public String getWendysUri2()
	{
		return wendysUri2;
	}

	public String getWendysPublicKey1()
	{
		return wendysPublicKey1;
	}

	public String getWendysPublicKey2()
	{
		return wendysPublicKey2;
	}

	public String getWendysPrivateKey1()
	{
		return wendysPrivateKey1;
	}

	public String getWendysPrivateKey2()
	{
		return wendysPrivateKey2;
	}

	public String getWendysHeaderNamePublicKey1()
	{
		return wendysHeaderNamePublicKey1;
	}

	public String getWendysHeaderNamePublicKey2()
	{
		return wendysHeaderNamePublicKey2;
	}

	public String getWendysHeaderNameSharedSecret1()
	{
		return wendysHeaderNameSharedSecret1;
	}

	public String getWendysHeaderNameSharedSecret2()
	{
		return wendysHeaderNameSharedSecret2;
	}

	public String getWendysParameterNameDateRequest1()
	{
		return wendysParameterNameDateRequest1;
	}

	public String getWendysParameterNameDateRequest2()
	{
		return wendysParameterNameDateRequest2;
	}

	// SETTERS
	
	public void setWendysUri1(String wendysUri1)
	{
		this.wendysUri1 = wendysUri1;
	}

	public void setWendysUri2(String wendysUri2)
	{
		this.wendysUri2 = wendysUri2;
	}

	public void setWendysPublicKey1(String wendysPublicKey1)
	{
		this.wendysPublicKey1 = wendysPublicKey1;
	}

	public void setWendysPublicKey2(String wendysPublicKey2)
	{
		this.wendysPublicKey2 = wendysPublicKey2;
	}

	public void setWendysPrivateKey1(String wendysPrivateKey1)
	{
		this.wendysPrivateKey1 = wendysPrivateKey1;
	}

	public void setWendysPrivateKey2(String wendysPrivateKey2)
	{
		this.wendysPrivateKey2 = wendysPrivateKey2;
	}

	public void setWendysHeaderNamePublicKey1(String wendysHeaderNamePublicKey1)
	{
		this.wendysHeaderNamePublicKey1 = wendysHeaderNamePublicKey1;
	}

	public void setWendysHeaderNamePublicKey2(String wendysHeaderNamePublicKey2)
	{
		this.wendysHeaderNamePublicKey2 = wendysHeaderNamePublicKey2;
	}

	public void setWendysHeaderNameSharedSecret1(String wendysHeaderNameSharedSecret1)
	{
		this.wendysHeaderNameSharedSecret1 = wendysHeaderNameSharedSecret1;
	}

	public void setWendysHeaderNameSharedSecret2(String wendysHeaderNameSharedSecret2)
	{
		this.wendysHeaderNameSharedSecret2 = wendysHeaderNameSharedSecret2;
	}

	public void setWendysParameterNameDateRequest1(String wendysParameterNameDateRequest1)
	{
		this.wendysParameterNameDateRequest1 = wendysParameterNameDateRequest1;
	}

	public void setWendysParameterNameDateRequest2(String wendysParameterNameDateRequest2)
	{
		this.wendysParameterNameDateRequest2 = wendysParameterNameDateRequest2;
	}

}
