package com.ivant.cms.action.boysen;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.joda.time.DateTime;

import com.ivant.cms.dataSource.CalculatorDataSource;
import com.ivant.cms.dataSource.CalculatorItem;
import com.ivant.cms.dataSource.PDFDataSource;
import com.ivant.cms.delegate.ComponentDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Component;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.PDFUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CalculatorReportAction
		extends ActionSupport
		implements Preparable, ServletRequestAware, ServletContextAware, CompanyAware
{
	
	private static final long serialVersionUID = 4502714073524860595L;
	
	private Company company;
	
	private ServletContext servletContext;
	
	private HttpServletRequest request;
	
	private PDFDataSource datasource;
	
	private ComponentDelegate componentDelegate = ComponentDelegate.getInstance();
	
	private String fileName;
	
	private InputStream inputStream;
	
	private long contentLength;
	
	private Long[] componentIdList;
	private Double[] valueList;
	
	private Integer area;
	private String productName;
	
	private String email;
	private String name;
	
	private Long itemid;
	private String actionName;
	private String isEmail;
	
	@Override
	public void prepare() throws Exception
	{
		// TODO Auto-generated method stub
		
	}
	
	public String generatePDF() throws Exception
	{
		
		if(componentIdList != null)
		{
			
			List<CalculatorItem> items = new ArrayList<CalculatorItem>();
			
			for(int i = 0; i < componentIdList.length; i++)
			{
				Component component = componentDelegate.find(componentIdList[i]);
				CalculatorItem item = new CalculatorItem(component.getName(), valueList[i]);
				item.setName(component.getCategory().getName() + "\n(" + component.getName() + ")");
				items.add(item);
				
			}
			productName = productName.replace("<sup>", "").replace("</sup>", "");
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("AREA", (area == null
				? "0"
				: area.toString()) + " m\u00B2");
			parameters.put("PRODUCT_NAME", productName);
			parameters.put("image", servletContext.getRealPath("companies/boysen/images/boysenlogo2.png"));
			CalculatorDataSource dataSource = new CalculatorDataSource(items);
			
			String directory = servletContext.getRealPath("calculatorpdf");
			fileName = "Boysen Paints Calculator-" + (new DateTime()).getMillis() + ".pdf";
			// System.out.println("BEFORE CALCULATOR");
			
			File file = PDFUtil.createPDF(dataSource, parameters, "CalculatorReport.jrxml", fileName, directory);
			if(StringUtils.isEmpty(email))
			{
				byte[] pdfByteArray = PDFUtil.getBytes(file);
				// System.out.println("FILE: "+file.getAbsolutePath());
				contentLength = pdfByteArray.length;
				
				inputStream = new ByteArrayInputStream(pdfByteArray);
			}
			else
			{
				sendEmail(file, email);
				return "email";
			}
			file.delete();
		}
		
		return SUCCESS;
	}
	
	private void sendEmail(File attachment, String to)
	{
		EmailUtil.connectViaCompanySettings(company);
		StringBuffer message = new StringBuffer();
		
		message.append("Thank you for using the Boysen Paints online calculator.")
		.append("<br/> Please find the attached PDF for your records.")
		.append("<br/><br/> regards,")
		.append("<br/> the Boysen Team.");
		
		// EmailUtil.sendWithHTMLFormat("test@ivant.com", to, "Boysen Calculator for Boysen® KNOxOUT™ Air Cleaning Paint "+productName, message.toString(), attachment.getAbsolutePath());
		String subject = ("Boysen Calculator for Boysen\u00AE KNOxOUT\u2122 Air Cleaning Paint " + productName);
		EmailUtil.sendWithHTMLFormat("inquiry@boysen.com.ph", to, subject, message.toString(), attachment.getAbsolutePath());
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public InputStream getInputStream()
	{
		return inputStream;
	}
	
	public long getContentLength()
	{
		return contentLength;
	}
	
	public void setArea(Integer area)
	{
		this.area = area;
	}
	
	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	
	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	
	public void setValueList(Double[] valueList)
	{
		this.valueList = valueList;
	}
	
	public void setComponentIdList(Long[] componentIdList)
	{
		this.componentIdList = componentIdList;
	}
	
	@Override
	public void setServletContext(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Long getItemid()
	{
		return itemid;
	}
	
	public void setItemid(Long itemid)
	{
		this.itemid = itemid;
	}
	
	public String getActionName()
	{
		return actionName;
	}
	
	public void setActionName(String actionName)
	{
		this.actionName = actionName;
	}
	
	public void setIsEmail(String isEmail)
	{
		this.isEmail = isEmail;
	}
	
	public String getIsEmail()
	{
		return isEmail;
	}
	
	public Company getCompany()
	{
		return this.company;
	}
	
	@Override
	public void setCompany(Company company)
	{
		this.company = company;
	}
	
}
