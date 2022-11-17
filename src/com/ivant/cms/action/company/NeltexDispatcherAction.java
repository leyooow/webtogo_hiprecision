/**
 *
 */
package com.ivant.cms.action.company;

import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Order;

import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.action.PageDispatcherAction;
import com.ivant.cms.beans.KuysenClientBean;
import com.ivant.cms.dataSource.PDFDataSource;
import com.ivant.cms.dataSource.PDFItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemPackage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.IPackage;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interfaces.PDFGenerator;
import com.ivant.cms.interfaces.PageDispatcherAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.PDFCreatorTool;
import com.ivant.utils.PDFUtil;

/**
 * @author Edgar S. Dacpano
 *
 */
public class NeltexDispatcherAction
		extends PageDispatcherAction
		implements PageDispatcherAware
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * K: {@link CategoryItem#getId()} V: Set of {@link IPackage} that contains this K.
	 */
	private Map<String, Set<IPackage>> packageMap;
	private Set<IPackage> packages;
	
	private PDFDataSource datasource;
	
	private PDFCreatorTool PDFCreator = new PDFCreatorTool();
	private PDFDataSource quoteDatasource;
	private List<PDFItem> quoteItems = new ArrayList<PDFItem>();
	private String templateName = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	private Calendar date = Calendar.getInstance();
	
	private static final String ATTACHMENT_FOLDER = "message_attachments";
	
	private String successUrl;
	private String errorUrl;
	
	@Override
	public void prepare() throws Exception
	{
		super.prepare();
	}
	
	@Override
	public String execute() throws Exception
	{
		final String result = super.execute();
		
		final Object obj = request.getAttribute("category");
		if(obj != null)
		{
			if(obj instanceof Category)
			{
				final Category category = (Category) obj;
				preparePackageMap(category);
				preparePackage();
			}
		}
		
		return result;
	}
	
	private void preparePackageMap(Category category)
	{
		final Map<String, Set<IPackage>> packageMap = new HashMap<String, Set<IPackage>>();
		
		if(category != null)
		{
			final List<Category> children = categoryDelegate.findAllChildrenOfChildrenCategory(company, category, -1, -1, Order.asc("id")).getList();
			children.add(category);
			
			final Set<Long> itemSets = new HashSet<Long>();
			
			for(Category c : children)
			{
				final List<CategoryItem> items = categoryItemDelegate.findAllWithPaging(company, category.getParentGroup(), c, -1, -1, true, Order.asc("name")).getList();
				if(CollectionUtils.isNotEmpty(items))
				{
					for(CategoryItem i : items)
					{
						itemSets.add(i.getId());
					}
				}
			}
			
			if(CollectionUtils.isNotEmpty(itemSets))
			{
				final List<CategoryItemPackage> itemPackages = categoryItemPackageDelegate.findAllWithPaging(company, new ArrayList<Long>(itemSets), -1, -1, Order.asc("id")).getList();
				if(CollectionUtils.isNotEmpty(itemPackages))
				{
					for(CategoryItemPackage cip : itemPackages)
					{
						final String ciId = cip.getiPackage().getSku();
						final Set<IPackage> val = packageMap.get(ciId) == null
							? new HashSet<IPackage>()
							: packageMap.get(ciId);
						
						val.add(cip.getiPackage());
						
						packageMap.put(ciId, val);
					}
				}
			}
		}
		
		setPackageMap(packageMap);
	}
	
	private void preparePackage()
	{
		if(MapUtils.isNotEmpty(getPackageMap()))
		{
			setPackages(new HashSet<IPackage>());
			for(Entry<String, Set<IPackage>> entry : getPackageMap().entrySet())
			{
				getPackages().addAll(entry.getValue());
			}
		}
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public String sendEQuote() throws Exception {
		
		String logo = PDFCreator.getLogo();

		
		if(company.getLogo()==null)
			logo="";
		try{
		}catch(Exception e){
		}
		
		//get passed template id
		templateName="";
		try{
			templateName = request.getParameter("template_name");
		}catch(Exception e){
		}
		if(templateName==null || templateName.equals("")){
			templateName="hpds_quote_template.jrxml";
		}
		
		try {
			
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			
			//String subject = request.getParameter("subject")/* + " - " + company.getNameEditable()*/;
			String companyName = request.getParameter("company_name");
			String companyAddress = request.getParameter("company_address");
			String projectName = request.getParameter("project_name");
			String telephone = request.getParameter("telephone");
			String fax = request.getParameter("fax");
			String name = request.getParameter("name");
			String position = request.getParameter("position");
			String contactNumber = request.getParameter("contact_number");
			String senderEmail = request.getParameter("email_address");
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("company_name", companyName);
			parameters.put("company_address", companyAddress);
			parameters.put("project_name", projectName);
			parameters.put("telephone", telephone);
			parameters.put("fax", fax);
			parameters.put("sender_name", name);
			parameters.put("position", position);
			parameters.put("contact_number", contactNumber);
			parameters.put("sender_email", senderEmail);
			parameters.put("image", servletContext.getRealPath("companies/neltex/images/neltex.jpg"));
			parameters.put("address_image", servletContext.getRealPath("companies/neltex/images/address.jpg"));
			parameters.put("tel_image", servletContext.getRealPath("companies/neltex/images/telephone.png"));
			parameters.put("fax_image", servletContext.getRealPath("companies/neltex/images/fax.png"));
			
			//get date
			DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
			Date date2 = new Date();
			String billingDate = dateFormat.format(date2);
			parameters.put("date", billingDate);
			
			quoteItems = new ArrayList<PDFItem>();
			
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			List<CategoryItem> catItems = (List<CategoryItem>) request.getSession().getAttribute("noLoginCartItems");
			
			Collections.sort(catItems, new Comparator<CategoryItem>() {
	    	    public int compare(CategoryItem m1, CategoryItem m2) {
		    	        return m1.getParent().getName().compareTo(m2.getParent().getName());
		    	    }
		    	});
			
			Integer count = 1;
			String defaultCategory = "";
			double total = 0.0;
			for(CategoryItem categoryItem : catItems) {
				
				double subTotal = categoryItem.getPrice()*categoryItem.getOrderQuantity();
				total += subTotal;
				
				String categoryName = categoryItem.getParent().getName();
				if(defaultCategory.equalsIgnoreCase(categoryName)) {
					quoteItems.add(new PDFItem(
							categoryItem.getName(),//f1
							"Pcs.",//f2
							""+categoryItem.getOrderQuantity(),//f3
							numberFormatter.format(categoryItem.getPrice()),//f4
							numberFormatter.format(categoryItem.getPrice()*categoryItem.getOrderQuantity()),//f5 
							logo, //f6
							categoryName,//f7 
							""+company.getAddress(),//f8 
							company.getNameEditable(),//f9
							count.toString(), //f10
							contactNumber, //phone f11
							senderEmail, //order email address f12
							position, //f13
							name, //f14
							companyAddress, //cart order address f15 
							companyName, //f16
							""+billingDate, //f17 
							"" //f18
							));
				} else {
					defaultCategory = categoryName;
					PDFItem pdfItem = new PDFItem();
					pdfItem.setF1(defaultCategory);
					pdfItem.setF2("");
					pdfItem.setF7(defaultCategory);
					quoteItems.add(pdfItem);
					
					quoteItems.add(new PDFItem(
							categoryItem.getName(),//f1
							"Pcs.",//f2
							""+categoryItem.getOrderQuantity(),//f3
							numberFormatter.format(categoryItem.getPrice()),//f4
							numberFormatter.format((categoryItem.getPrice()*categoryItem.getOrderQuantity())),//f5 
							logo, //f6
							categoryName,//f7 
							""+company.getAddress(),//f8 
							company.getNameEditable(),//f9
							count.toString(), //f10
							contactNumber, //phone f11
							senderEmail, //order email address f12
							position, //f13
							name, //f14
							companyAddress, //cart order address f15 
							companyName, //f16
							""+billingDate, //f17 
							"" //f18
							));
				}
				count++;
			}
			
			PDFItem pdfItem = new PDFItem();
			pdfItem.setF1("TOTAL");
			pdfItem.setF2("");
			pdfItem.setF5(numberFormatter.format(total));
			quoteItems.add(pdfItem);
			
			//Collections.reverse(quoteItems);
			
			setQuoteDatasource(new PDFDataSource(quoteItems));
			String fileName = company.getName()+"_Product Quotation_"+sdf.format(date.getTime())+".pdf";
			
			String path = ATTACHMENT_FOLDER + File.separator;
			
			File file = PDFUtil.createPDF(quoteDatasource, parameters, templateName, fileName, path);
			
			String subject = "Neltex Product Quotation";
			StringBuffer content = new StringBuffer();
			
			final EmailSenderAction emailSenderAction = new EmailSenderAction();
			emailSenderAction.sendQuotationEmail(company, senderEmail, subject, content, file.getAbsolutePath());
			
			return SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		
	}
	
	public void setQuoteDatasource(PDFDataSource quoteDatasource) {
		this.quoteDatasource = quoteDatasource;
	}
	public PDFDataSource getQuoteDatasource() {
		return quoteDatasource;
	}
	
	public Map<String, Set<IPackage>> getPackageMap()
	{
		return packageMap;
	}
	
	public Set<IPackage> getPackages()
	{
		return packages;
	}
	
	public void setPackageMap(Map<String, Set<IPackage>> packageMap)
	{
		this.packageMap = packageMap;
	}
	
	public void setPackages(Set<IPackage> packages)
	{
		this.packages = packages;
	}

	public PDFDataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(PDFDataSource datasource) {
		this.datasource = datasource;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

}
