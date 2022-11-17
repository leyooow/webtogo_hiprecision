package com.ivant.cms.action.company;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.action.PageDispatcherAction;
import com.ivant.cms.dataSource.PDFDataSource;
import com.ivant.cms.dataSource.PDFItem;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.interfaces.PageDispatcherAware;
import com.ivant.utils.PDFCreatorTool;
import com.ivant.utils.PDFUtil;

public class AyrosoHardwareDispatcherAction extends PageDispatcherAction implements PageDispatcherAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PDFDataSource datasource;
	
	private PDFCreatorTool PDFCreator = new PDFCreatorTool();
	private PDFDataSource quoteDatasource;
	private List<PDFItem> quoteItems = new ArrayList<PDFItem>();
	private String templateName = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy-h:mm a");
	private Calendar date = Calendar.getInstance();
	
	private static final String ATTACHMENT_FOLDER = "message_attachments";
	
	private String successUrl;
	private String errorUrl;
	
	public void prepare() throws Exception
	{
		super.prepare();
	}
	
	@Override
	public String execute() throws Exception
	{
		final String result = super.execute();
		
		return result;
	}
	
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
		
			if(templateName==null || templateName.equals("")){
				templateName="hpds_quote_template.jrxml";
			}
			
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			
			String name = request.getParameter("name");
			String address1 = request.getParameter("address1");
			String address2 = request.getParameter("address2");
			String mobile = request.getParameter("mobile_number");
			String phone = request.getParameter("phone_number");
			String senderEmail = request.getParameter("email_address");
			
			String[] quantity = request.getParameterValues("cartItem");
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("sender_name", name);
			parameters.put("address", address1);
			parameters.put("mobile", mobile);
			parameters.put("phone", phone);
			parameters.put("sender_email", senderEmail);
			
			//get date
			DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
			Date date2 = new Date();
			String billingDate = dateFormat.format(date2);
			parameters.put("date", billingDate);
			
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			quoteItems = new ArrayList<PDFItem>();
			
			shoppingCart = cartDelegate.find(company, member);
			
			List<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>();
			
			Integer count = 1;
			double total = 0.0;
			if(member != null) {
				
				final CartOrder cartOrder = new CartOrder();
				cartOrder.setAddress1(member.getAddress1());
				cartOrder.setStatus(OrderStatus.IN_PROCESS);
				cartOrder.setCompany(company);
				cartOrder.setName(name);
				cartOrder.setEmailAddress(senderEmail);
				cartOrder.setPhoneNumber(mobile);
				cartOrder.setPaymentStatus(PaymentStatus.PENDING);
				cartOrder.setMember(member);
				
				final List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
				
				int counter = 0;
				for(ShoppingCartItem item : shoppingCart.getItems()) {
					
					CategoryItem categoryItem = categoryItemDelegate.find(item.getItemDetail().getRealID());
					
					item.setQuantity(Integer.parseInt(quantity[counter]));
					
					double subTotal = item.getItemDetail().getPrice()*item.getQuantity();
					total += subTotal;
					
					quoteItems.add(new PDFItem(
							item.getItemDetail().getName(),//f1
							"",//f2
							""+item.getQuantity(),//f3
							numberFormatter.format(item.getItemDetail().getPrice()),//f4
							numberFormatter.format(item.getItemDetail().getPrice()*item.getQuantity()),//f5 
							logo, //f6
							""+item.getItemDetail().getName(),//f7 
							""+company.getAddress(),//f8 
							company.getNameEditable(),//f9
							count.toString(), //f10
							phone, //phone f11
							senderEmail, //order email address f12
							"", //f13
							name, //f14
							"", //cart order address f15 
							"", //f16
							"", //f17 
							"" //f18
							));
					
					final CartOrderItem cartOrderItem = new CartOrderItem();
					cartOrderItem.setCompany(company);
					cartOrderItem.setItemDetail(item.getItemDetail());
					cartOrderItem.setQuantity(item.getQuantity());
					cartOrderItem.setOrder(cartOrder);
					cartOrderItems.add(cartOrderItem);
					
					counter++;
					
					shoppingCartItems.add(item);
					
				}
				
				cartOrder.setTotalPrice(total);
				cartOrder.setItems(cartOrderItems);
				
				final long orderId = cartOrderDelegate.insert(cartOrder);
				cartOrderItemDelegate.batchInsert(cartOrderItems);
				
			} else {
				
				final CartOrder cartOrder = new CartOrder();
				
				final List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
				
				cartOrder.setAddress1(address1);
				cartOrder.setStatus(OrderStatus.IN_PROCESS);
				cartOrder.setCompany(company);
				cartOrder.setName(name);
				cartOrder.setEmailAddress(senderEmail);
				cartOrder.setPhoneNumber(mobile);
				cartOrder.setPaymentStatus(PaymentStatus.PENDING);
				cartOrder.setMember(null);
				
				List<CategoryItem> list = (List<CategoryItem>) request.getSession().getAttribute("noLogInCartItems");
				
				int counter = 0;
				for(CategoryItem item : list) {
					
					item.setOrderQuantity(Integer.parseInt(quantity[counter]));
					
					double subTotal = item.getPrice()*item.getOrderQuantity();
					total += subTotal;
					
					quoteItems.add(new PDFItem(
							item.getName(),//f1
							"",//f2
							""+item.getOrderQuantity(),//f3
							numberFormatter.format(item.getPrice()),//f4
							numberFormatter.format(item.getPrice()*item.getOrderQuantity()),//f5 
							logo, //f6
							""+item.getParent().getName(),//f7 
							""+company.getAddress(),//f8 
							company.getNameEditable(),//f9
							count.toString(), //f10
							phone, //phone f11
							senderEmail, //order email address f12
							"", //f13
							name, //f14
							"", //cart order address f15 
							"", //f16
							"", //f17 
							"" //f18
							));
					
					final CartOrderItem cartOrderItem = new CartOrderItem();
					cartOrderItem.setCompany(company);
					cartOrderItem.setItemDetail(item.getItemDetail());
					cartOrderItem.setQuantity(item.getOrderQuantity());
					cartOrderItem.setOrder(cartOrder);
					cartOrderItems.add(cartOrderItem);
					
					counter++;
				}
				
				cartOrder.setTotalPrice(total);
				cartOrder.setItems(cartOrderItems);
				
				final long orderId = cartOrderDelegate.insert(cartOrder);
				cartOrderItemDelegate.batchInsert(cartOrderItems);
				
			}
			
			PDFItem pdfItem = new PDFItem();
			pdfItem.setF1("TOTAL");
			pdfItem.setF2("");
			pdfItem.setF5(numberFormatter.format(total));
			quoteItems.add(pdfItem);
			
			//Collections.reverse(quoteItems);
			
			setQuoteDatasource(new PDFDataSource(quoteItems));
			String fileName = company.getName()+" Product Quotation_"+sdf.format(date.getTime())+".pdf";
			
			String path = ATTACHMENT_FOLDER + File.separator;
			
			File file = PDFUtil.createPDF(quoteDatasource, parameters, templateName, fileName, path);
			
			String subject = "Ayroso Hardware Product Quotation";
			StringBuffer content = new StringBuffer();
			
			final EmailSenderAction emailSenderAction = new EmailSenderAction();
			boolean emailsent = emailSenderAction.sendQuotationEmail(company, senderEmail, subject, content, file.getAbsolutePath());
			if(!emailsent) {
				return ERROR;
			}
			
			//REMOVING CART ITEMS
			if(member != null) {
				for(ShoppingCartItem item : shoppingCartItems) {
					shoppingCartItemDelegate.delete(item);
				}
			} else {
				request.getSession().removeAttribute("noLogInCartItems");
			}
			
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public String downloadQuoteHistory() throws Exception {
		
		try {
			
			String logo = PDFCreator.getLogo();
	
			
			if(company.getLogo()==null)
				logo="";
			try{
			}catch(Exception e){
			}
			
			templateName = request.getParameter("template_name");
			
			quoteItems = new ArrayList<PDFItem>();
			
			Long id = new Long(request.getParameter("quoteId"));
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("sender_name", member.getFullName());
			parameters.put("address", member.getAddress1());
			parameters.put("mobile", member.getMobile());
			parameters.put("phone", member.getMobile());
			parameters.put("sender_email", member.getEmail());
			
			List<CartOrderItem> items = cartOrderDelegate.find(id).getItems();
			
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			Integer count = 1;
			double total = 0.0;
			
			for(CartOrderItem item : items) {
	
				double subTotal = item.getItemDetail().getPrice()*item.getQuantity();
				total += subTotal;
				
				quoteItems.add(new PDFItem(
						item.getItemDetail().getName(),//f1
						"",//f2
						""+item.getQuantity(),//f3
						numberFormatter.format(item.getItemDetail().getPrice()),//f4
						numberFormatter.format(item.getItemDetail().getPrice()*item.getQuantity()),//f5 
						logo, //f6
						""+item.getItemDetail().getName(),//f7 
						""+company.getAddress(),//f8 
						company.getNameEditable(),//f9
						count.toString(), //f10
						member.getMobile(), //phone f11
						member.getEmail(), //order email address f12
						"", //f13
						member.getFullName(), //f14
						"", //cart order address f15 
						"", //f16
						"", //f17 
						"" //f18
						));
				
			}
			
			PDFItem pdfItem = new PDFItem();
			pdfItem.setF1("TOTAL");
			pdfItem.setF2("");
			pdfItem.setF5(numberFormatter.format(total));
			quoteItems.add(pdfItem);
			
			//Collections.reverse(quoteItems);
			
			setQuoteDatasource(new PDFDataSource(quoteItems));
			fileName = company.getName()+" Product Quotation_"+sdf.format(date.getTime())+".pdf";
			
			String path = ATTACHMENT_FOLDER + File.separator;
			
			File file = PDFUtil.createPDF(quoteDatasource, parameters, templateName, fileName, path);
			
			fileInputStream = new FileInputStream(file);
			contentLength = file.length();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	/**
	 * @return the datasource
	 */
	public PDFDataSource getDatasource() {
		return datasource;
	}

	/**
	 * @param datasource the datasource to set
	 */
	public void setDatasource(PDFDataSource datasource) {
		this.datasource = datasource;
	}

	/**
	 * @return the quoteDatasource
	 */
	public PDFDataSource getQuoteDatasource() {
		return quoteDatasource;
	}

	/**
	 * @param quoteDatasource the quoteDatasource to set
	 */
	public void setQuoteDatasource(PDFDataSource quoteDatasource) {
		this.quoteDatasource = quoteDatasource;
	}

	/**
	 * @return the successUrl
	 */
	public String getSuccessUrl() {
		return successUrl;
	}

	/**
	 * @param successUrl the successUrl to set
	 */
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	/**
	 * @return the errorUrl
	 */
	public String getErrorUrl() {
		return errorUrl;
	}

	/**
	 * @param errorUrl the errorUrl to set
	 */
	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

}
