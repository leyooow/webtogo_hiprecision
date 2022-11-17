package com.ivant.cms.action.dutchboy;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.ServletContextAware;
import org.joda.time.DateTime;

import com.ivant.cms.dataSource.CalculatorDataSource;
import com.ivant.cms.dataSource.CalculatorItem;
import com.ivant.cms.delegate.ComponentDelegate;
import com.ivant.cms.entity.Component;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.HTMLTagStripper;
import com.ivant.utils.PDFUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CalculatorReportAction
		extends ActionSupport
		implements Preparable, ServletContextAware
{
	
	private static final long serialVersionUID = 4502714073524860595L;

	private ServletContext servletContext;
	
	private ComponentDelegate componentDelegate = ComponentDelegate.getInstance();
	
	private String fileName;
	
	private InputStream inputStream;
	
	private long contentLength;
	
	private Long[] componentIdList;
	
	private Double[] valueList;
	
	private Integer area;
	
	private String productName;
	
	private String email;
	
	private Long itemid;
	
	private String actionName;
	
	private String name;
	
	
	@Override
	public void prepare() throws Exception
	{
		
	}
	
	public String generatePDF() throws Exception
	{
		
			final List<CalculatorItem> items = new ArrayList<CalculatorItem>();
			
			if(componentIdList != null) {
				for(int i = 0; i < componentIdList.length; i++)
				{
					final Component component = componentDelegate.find(componentIdList[i]);
					final CalculatorItem item = new CalculatorItem(component.getName(), valueList[i]);
					items.add(item);
				}
			} else {
				CalculatorItem item = new CalculatorItem("", null);
				items.add(item);
			}
			
			final Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("AREA", area == null
				? ""
				: area.toString());
			parameters.put("PRODUCT_NAME", HTMLTagStripper.stripTags(productName));
			parameters.put("image", servletContext.getRealPath("companies/dutchboy2/images/dutchboy2_paint_cal.jpg"));
			
			final String directory = servletContext.getRealPath("calculatorpdf");
			fileName = "Dutch Boy Paint Calculator-" + (new DateTime()).getMillis() + ".pdf";
			
			final CalculatorDataSource dataSource = new CalculatorDataSource(items);
			final File file = PDFUtil.createPDF((items.size()>0 ? dataSource : null), parameters, "CalculatorReport.jrxml", fileName, directory);
			
			try
			{
				if(StringUtils.isEmpty(email))
				{
					final byte[] pdfByteArray = PDFUtil.getBytes(file);
					contentLength = pdfByteArray.length;
					inputStream = new ByteArrayInputStream(pdfByteArray);
				}
				else
				{
					sendEmail(file, email, getName());
					return "email";
				}
			}
			finally
			{
				file.delete();
			}
		
		return SUCCESS;
	}
	
	private void sendEmail(File attachment, String to, String name)
	{
		EmailUtil.connect("smtp.gmail.com", 587);
		
		final StringBuilder message = new StringBuilder();
		if(StringUtils.isNotBlank(name))
		{
			message.append("Hi " + name + ",");
			message.append("<br/> ");
		}
		message.append("Thank you for using the Dutch Boy Paints online calculator. Please see the attached PDF for your records.");
		message.append("<br/> regards,");
		message.append("<br/> the Dutch Boy Team.");
		
		EmailUtil.sendWithHTMLFormat("test@ivant.com", to, "Dutch Boy Calculator", message.toString(), attachment.getAbsolutePath());
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
