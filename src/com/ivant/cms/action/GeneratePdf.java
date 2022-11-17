package com.ivant.cms.action;


import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.ivant.cms.dataSource.PDFDataSource;
import com.ivant.cms.dataSource.PDFItem;
import com.ivant.cms.delegate.ActivityDelegate;
import com.ivant.cms.delegate.BillingDelegate;
import com.ivant.cms.delegate.BrandDelegate;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.EventDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.entity.Activity;
import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.BillingStatusEnum;
import com.ivant.cms.enums.BillingTypeEnum;
import com.ivant.cms.enums.CompanyStatusEnum;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.enums.ShippingType;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interfaces.PDFGenerator;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.CartOrderUtil;
import com.ivant.utils.CategoryItemUtil;
import com.ivant.utils.PDFCreatorTool;

/*
 * @author Glenn Allen B. Sapla
 * @version 1.0, May 2009
 * This controls the flows of the PDF creation
 */
public class GeneratePdf implements PDFGenerator{

	private static final long serialVersionUID = -6076537228718308644L;
	private static final String NEW = "NEW";
	private static final String DISCOUNT = "Discount";
	private static final String SHIPPING = "Shipping Cost";
	private static final String NEGATIVE = "- ";
	private static final String DIY = "DIY";
	private static final String FACE = "Face";
	private static final String STRAP = "Strap";
	private static final String SINGLE_SPACE = " ";
	private static final String TRIPLE_SPACE = "   ";
	private static final String UPLOADED_PHOTO = "Uploaded Photo";
    private static final String PNG_EXTENSION = ".png";
    private static final String ZERO = "0.00";
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private BrandDelegate brandDelegate = BrandDelegate.getInstance();
	private CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private EventDelegate eventDelegate = EventDelegate.getInstance();
	private ActivityDelegate activityDelegate = ActivityDelegate.getInstance();
	private CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	private CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	private CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private List<PDFItem> items3;
	private PDFDataSource datasource;
	
	
	private HttpServletRequest request;
	private ServletContext servletContext;
	private boolean local;
	private Map<String, String> contextParams;
	private Company company;
	private String httpServer = "";
	private String category_id;
	private String templateName = "";
	private Double totalPrice = 0d;
	private Double totalDiscount = 0d;

	private PDFCreatorTool PDFCreator = new PDFCreatorTool();

	private PDFDataSource orderDatasource;
	private List<PDFItem> orderItems = new ArrayList<PDFItem>();
	private String cartOrderID;

	private PDFDataSource billingDatasource;
	private List<PDFItem> billingItems = new ArrayList<PDFItem>();
	private Logger logger = Logger.getLogger(getClass());
	private BillingDelegate billingDelegate = BillingDelegate.getInstance();

	private Billing billing;
	private User user;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	private Calendar date = Calendar.getInstance();
	private final String MONEY_FORMAT = "##,###,##0.00";

	private List<Billing> billings;
	private List<BillingStatusEnum> billingStatuses;
	private List<BillingTypeEnum> billingTypes;

	private PDFDataSource lastPaymentDatasource;
	private List<PDFItem> lastPaymentItems = new ArrayList<PDFItem>();

	private PDFDataSource pendingDatasource;
	private List<PDFItem> pendingItems = new ArrayList<PDFItem>();
	
	private PDFDataSource nissanDatasource;
	private List<PDFItem> nissanItems = new ArrayList<PDFItem>();
	
	private PDFDataSource elcDatasource;
	private List<PDFItem> elcItems = new ArrayList<PDFItem>();

	private PDFDataSource directoryDatasource;
	private List<PDFItem> directoryItems = new ArrayList<PDFItem>();
	
	private String notificationMessage;

	private CompanyStatusEnum[] status =
	{ 
		CompanyStatusEnum.ONGOING, CompanyStatusEnum.ACTIVE,
		CompanyStatusEnum.ACTIVE_NO_HOSTING 
	};

	//Generates the report Price List
	public void generatePriceListReport(){
		String name="all";
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
		String logo = PDFCreator.getLogo();
		
		if(company.getLogo()==null)
			logo="";
		/*****************************************************************************/		
		//get passed category id
		category_id="";
		try{
			category_id = request.getParameter("category_id");
		}catch(Exception e){
		}
		
		//get passed template id
		templateName="";
		try{
			templateName = request.getParameter("template_name");
		}catch(Exception e){
		}
		
		
		//Try to create a report file
		try {
			//if no template id passed
			if(templateName==null || templateName.equals("")){
				templateName="default_template.jrxml";
			}

			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			/* compile the report */
		
			//gather items to be listed in report
			final List<PDFItem> items2 = new ArrayList<PDFItem>();
			items3 = new ArrayList<PDFItem>();
			List<CategoryItem> tempListCategory = new ArrayList<CategoryItem>();
			List<CategoryItem> temp;
			String catName;

			
			//if no category id is passed download whole price list
			if(category_id==null || category_id.equals("")){
				//get all items of a company
				temp = categoryItemDelegate .findAll(company).getList();	
				/************ sort items by category  *************/
				Long tempId=null;
				for(int i=0; i<temp.size(); i++){
					tempId=temp.get(i).getId();		
					for(int j=0; j<temp.size(); j++){
						if(tempId==temp.get(j).getId()){
							tempListCategory.add(temp.get(j));
							temp.remove(j);
							j--;
						}
					}
				}
				/********************************************/
				Category rootCatObject;
				//Collect all data to be passed to the PriceListDataSource object
				for(int j=0; j<tempListCategory.size() ; j++ ){
					CategoryItem tempItem = tempListCategory.get(j);
					catName = tempItem.getParent().getName().toUpperCase();
					//Make a chain of string from root to item (eg: products->hair color->permanent->brand ....)
					Category loop =tempItem.getParent();
					rootCatObject=loop.findRootCategory2(loop);
					String rootCat = rootCatObject.getName();
					items2.add(new PDFItem(tempItem.getName(), tempItem.getSku(), "1", numberFormatter.format(tempItem.getPrice()), logo, catName, ""+company.getAddress()+"  "+company.getEmail(), company.getNameEditable(),(tempItem.getShortDescription()).replaceAll("\\<.*?\\>", ""), 
							rootCat, ""+rootCatObject.getId(),null,null,null,null,null,null,null));
				}
					
				/************ sort items by root category  *************/
				tempId=null;
				for(int i=0; i<items2.size(); i++){
					tempId=Long.parseLong(items2.get(i).getF11());			
					for(int j=0; j<items2.size(); j++){
						if(tempId==Long.parseLong(items2.get(j).getF11())){
							items3.add(items2.get(j));
							items2.remove(j);
							j--;
						}
					}
				}
				/********************************************/
			}
			//if a root category has been chosen (category_id has a value)
			else{
				name = categoryDelegate.find(Long.parseLong(category_id)).getName();
				//get all items of a company
				temp = categoryItemDelegate.findAll(company).getList();			
				/************ sort items by category  *************/
				Long tempId=null;
				for(int i=0; i<temp.size(); i++){
					tempId=temp.get(i).getId();		
					for(int j=0; j<temp.size(); j++){
						if(tempId==temp.get(j).getId()){
							tempListCategory.add(temp.get(j));
							temp.remove(j);
							j--;
						}
					}
				}
				/********************************************/
				Category rootCatObject;
				//Collect all data to be passed to the PriceListDataSource object
				for(int j=0; j<tempListCategory.size() ; j++ ){
					CategoryItem tempItem = tempListCategory.get(j);
					catName = tempItem.getParent().getName().toUpperCase();
					//Make a chain of string from root to item (eg: products->hair color->permanent->brand ....)
					Category loop =tempItem.getParent();
					while(loop.getParentCategory()!=null){
						//catName = loop.getParentCategory().getName()+ " >> " + catName;
						loop= loop.getParentCategory();
					}
					rootCatObject = loop;
					String rootCat = rootCatObject.getName();
					items2.add(new PDFItem(tempItem.getName(), tempItem.getSku(), "3", numberFormatter.format(tempItem.getPrice()), logo, catName, ""+company.getAddress()+"  "+company.getEmail(), company.getNameEditable(),(tempItem.getShortDescription()).replaceAll("\\<.*?\\>", ""), rootCat,""+ rootCatObject.getId(),null,null,null,null,null,null,null));
				}
				/************ get items on specified root category  *************/	
				for(int i=0; i<items2.size(); i++){
					for(int j=0; j<items2.size(); j++){
						if(category_id.equals(items2.get(j).getF11())){
							items3.add(items2.get(j));
							items2.remove(j);
							j--;
						}
					}
				}
				
				/********************************************/
			}
				
		//place all collected data to the datasource to be used by the jasper as a "datasource"
		setDatasource(new PDFDataSource(items3));	
		PDFCreator.outputPDF(datasource,templateName,""+company.getName()+"_"+name.replace(" ", "_")+"_PriceList_"+sdf.format(date.getTime())+".pdf");	
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void setDatasource(PDFDataSource datasource) {
		this.datasource = datasource;
	}
	public PDFDataSource getDatasource() {
		return datasource;
	}
	
	private InputStream fInstream;
	
	public void setFInStream(InputStream fInstream) {
		this.fInstream = fInstream;
	}
	public InputStream getFInStream() {
		return fInstream;
	}
	
	//Generates the report Quote List
	public void generateQuoteReport(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
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
		
		//Try to create a report file
		try {
			
			//get date
			DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
			Date date2 = new Date();
			String billingDate2 = dateFormat.format(date2);
			
			orderItems = new ArrayList<PDFItem>();
			cartOrderID = request.getParameter("cart_order_id");
			
			//lines or rows per page in the PDF
			int lines = 22;
			Long orderID = Long.parseLong(cartOrderID);
			CartOrder cartOrder = cartOrderDelegate.find(orderID);
			int count=0;
			List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
				
			//generate html string containing list
			

			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			for(CartOrderItem currentItem : itemList){
				if(currentItem.getStatus()==null){
					count++;
					orderItems.add(new PDFItem(currentItem.getItemDetail().getSku()+" - "+currentItem.getItemDetail().getName(), "SKU", ""+currentItem.getQuantity(),numberFormatter.format(currentItem.getItemDetail().getPrice()),numberFormatter.format((currentItem.getItemDetail().getPrice()*currentItem.getQuantity())), logo, "Category name", ""+company.getAddress()+"  "+company.getEmail(), company.getNameEditable(),"10", cartOrder.getPhoneNumber(), cartOrder.getEmailAddress(),null,cartOrder.getName(),cartOrder.getAddress1(),cartOrder.getComments(), "Quote No. "+cartOrder.getId(),""+billingDate2));
				}
				else if(currentItem.getStatus().equalsIgnoreCase("OK")){
					count++;
					orderItems.add(new PDFItem(currentItem.getItemDetail().getSku()+" - "+currentItem.getItemDetail().getName(), "SKU", ""+currentItem.getQuantity(),numberFormatter.format(currentItem.getItemDetail().getPrice()),numberFormatter.format((currentItem.getItemDetail().getPrice()*currentItem.getQuantity())), logo, "Category name", ""+company.getAddress()+"  "+company.getEmail(), company.getNameEditable(),"10", cartOrder.getPhoneNumber(), cartOrder.getEmailAddress(),null,cartOrder.getName(),cartOrder.getAddress1(),cartOrder.getComments(), "Quote No. "+cartOrder.getId(),""+billingDate2));
				}
			}
			
			for(int x=0; x<lines-(count%lines); x++){
				if(x==lines-(count%lines)-1){
					orderItems.add(new PDFItem("FINAL PRICE", null, null,null,numberFormatter.format(cartOrder.getTotalPriceOk()), null, null, ""+company.getAddress()+"  "+company.getEmail(), null,null,null, null,company.getStatement(),null,null,null,null,null));
				}
				else
					orderItems.add(new PDFItem(null, null,null, null,null, null, null, ""+company.getAddress()+"  "+company.getEmail(), null,null,null, null,null,null,null,null,null,null));
			}
					
			//place all collected data to the datasource to be used by the jasper as a "datasource"
			setOrderDatasource(new PDFDataSource(orderItems));
			PDFCreator.outputPDF(orderDatasource,templateName,""+company.getName()+"_OrderList_ID_"+cartOrderID+"_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	//Generate Wendys Delivery Tracking Report
		public void printWendysDeliveryTrackingReport(){
			
			request = ServletActionContext.getRequest();
			company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			
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
				templateName="wendyssummary.jrxml";
			}
			
			//Try to create a report file
			try {
				
				CategoryItem itemStore_ = new CategoryItem();
				//get date
				orderItems = new ArrayList<PDFItem>();

				String orderStatus = "";
				String paymentStatus = "";
				String shippingType = "";
				String branch = "";
				String branch_location = "comments";
				Boolean isForInfo1 = false;
				String branch_comments = "";
				String branch_info1 = "";
				
				if(request.getParameter("orderstatus")!=null){
					orderStatus = request.getParameter("orderstatus");
				}
				else{
					orderStatus = "Completed";
				}
				if(request.getParameter("paymentstatus")!=null){
					paymentStatus = request.getParameter("paymentstatus");
				}
				else{
					paymentStatus = "";
				}
				if(request.getParameter("ordertype")!=null){
					shippingType = request.getParameter("ordertype");
				}
				
				
				if(request.getParameter("branch")!=null && request.getParameter("branch")!=""){
					branch = request.getParameter("branch");
					branch_info1 = String.valueOf(categoryItemDelegate.findByName(company, branch).getId());
					branch_comments = "Prefferred Store: " + branch;
					itemStore_ = categoryItemDelegate.findByName(company, branch);
				}
				
				String str_fromDate = request.getParameter("fromDate");
				String str_toDate = request.getParameter("toDate");
		        DateFormat formatter ; 	    
		        Calendar c1 = Calendar.getInstance();
		        Calendar c2 = Calendar.getInstance();
		        formatter = new SimpleDateFormat("MM-dd-yyyy");
		        formatter.parse(str_toDate);
		        c1.setTime(formatter.parse(str_fromDate));
		        //this line of code is use to add days on the range of report date
		        c1.add(Calendar.DATE, 0);
		        c2.setTime(formatter.parse(str_toDate));
		        c2.add(Calendar.DATE, 1);
		        str_fromDate = formatter.format(c1.getTime());
		        str_toDate = formatter.format(c2.getTime());
		        Date fromDate = (Date)formatter.parse(str_fromDate);
		        Date toDate = (Date)formatter.parse(str_toDate); 
				
				List<CartOrder> listCartOrder = cartOrderDelegate.listAllPaidOrdersByDate(null, fromDate, toDate, company, -1, -1,orderStatus, paymentStatus, shippingType, branch_comments,branch_info1);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Long totalNewToInTransitMilliSecond = 0L;
				Long totalInTransitToDeliveredMilliSecond = 0L;
				Long totalNewToDeliveredMilliSecond = 0L;
				Long counterOrder = 0L;
				for(CartOrder order : listCartOrder) {
					if(order.getInfo2() != null && order.getInfo3() != null){
						if(order.getInfo2().length() > 10 && order.getInfo3().length() > 10){
							Duration durationFromNewToInTransit = new Duration(new DateTime(simpleDateFormat.parse(String.valueOf(order.getCreatedOn()))),new DateTime(simpleDateFormat.parse(order.getInfo2())));
							Duration durationFromInTransitToDelivered = new Duration(new DateTime(simpleDateFormat.parse(String.valueOf(order.getInfo2()))),new DateTime(simpleDateFormat.parse(order.getInfo3())));
							Duration durationFromNewToDelivered = new Duration(new DateTime(simpleDateFormat.parse(String.valueOf(order.getCreatedOn()))),new DateTime(simpleDateFormat.parse(order.getInfo3())));
							totalNewToInTransitMilliSecond = totalNewToInTransitMilliSecond + durationFromNewToInTransit.getMillis();
							totalInTransitToDeliveredMilliSecond = totalInTransitToDeliveredMilliSecond + durationFromInTransitToDelivered.getMillis();
							totalNewToDeliveredMilliSecond = totalNewToDeliveredMilliSecond + durationFromNewToDelivered.getMillis();
							
							orderItems.add(new PDFItem(
									itemStore_.getName() != null ? itemStore_.getName() : "",//f1 - store name
									"",//itemStore_.getCategoryItemOtherFieldMap().get("Email").getContent() ,//f2 - store email address
									itemStore_.getSku() !=null ? itemStore_.getSku() : "",//f3 - store adddress
									String.valueOf(order.getId()),//f4 - order No.
									order.getName(),//f5 - Customer name
									simpleDateFormat.format(order.getCreatedOn()),//f6 - Ordered DateTime
									order.getInfo2(),//f7 - change to in-transit
									String.valueOf((durationFromNewToInTransit.getMillis()/1000) / 60)+ "min. " + String.valueOf((durationFromNewToInTransit.getMillis()/1000) % 60)+ "sec.",//String.valueOf(Minutes.minutesBetween(new DateTime(simpleDateFormat.parse(String.valueOf(order.getCreatedOn()))), new DateTime(simpleDateFormat.parse(order.getInfo2()))).getMinutes()) ,//f8 - new to in-transit
									order.getInfo3(),//f9 - change to delivered
									String.valueOf((durationFromInTransitToDelivered.getMillis()/1000) / 60)+ "min. " + String.valueOf((durationFromInTransitToDelivered.getMillis()/1000) % 60)+ "sec.",//f10 - in transit to delivered
									String.valueOf((durationFromNewToDelivered.getMillis()/1000) / 60)+ "min. " + String.valueOf((durationFromNewToDelivered.getMillis()/1000) % 60)+ "sec.",//f11 - total time in minutes
									logo,//f12 - logo
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									""));
								counterOrder++;
							}
						}
				}
				
				if(counterOrder > 0){
				orderItems.add(new PDFItem(
						itemStore_.getName() != null ? itemStore_.getName() : "",//f1 - store name
						"",//itemStore_.getCategoryItemOtherFieldMap().get("Email").getContent() ,//f2 - store email address
						itemStore_.getSku() !=null ? itemStore_.getSku() : "",//f3 - store adddress
						"",//f4 - order No.
						"",//f5 - Customer name
						"",//f6 - Ordered DateTime
						"",//f7 - change to in-transit
						"",//String.valueOf(Minutes.minutesBetween(new DateTime(simpleDateFormat.parse(String.valueOf(order.getCreatedOn()))), new DateTime(simpleDateFormat.parse(order.getInfo2()))).getMinutes()) ,//f8 - new to in-transit
						"",//f9 - change to delivered
						"",//f10 - in transit to delivered
						"",//f11 - total time in minutes
						"",//f12 - logo
						String.valueOf(((totalNewToInTransitMilliSecond/counterOrder)/1000) / 60)+ "min. " + String.valueOf(((totalNewToInTransitMilliSecond/counterOrder)/1000) % 60)+ "sec.",
						String.valueOf(((totalInTransitToDeliveredMilliSecond/counterOrder)/1000) / 60)+ "min. " + String.valueOf(((totalInTransitToDeliveredMilliSecond/counterOrder)/1000) % 60)+ "sec.",
						String.valueOf(((totalNewToDeliveredMilliSecond/counterOrder)/1000) / 60)+ "min. " + String.valueOf(((totalNewToDeliveredMilliSecond/counterOrder)/1000) % 60)+ "sec.",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						""));
				}
				//place all collected data to the datasource to be used by the jasper as a "datasource"
				setOrderDatasource(new PDFDataSource(orderItems));
				PDFCreator.outputPDF(orderDatasource,templateName,""+company.getName()+"_OrderList_ID_"+cartOrderID+"_"+sdf.format(date.getTime())+".pdf");	
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	
		//Generate Wendys Delivery Tracking Report
				public void printWendysTopCustomer(){
					
					request = ServletActionContext.getRequest();
					company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
					
					String logo = PDFCreator.getLogo();
					Integer counterRank = 1;
					
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
						templateName="wendys_top_customer.jrxml";
					}
					
					//Try to create a report file
					try {
						
						Map<Long, Integer> topMemberMap = new HashMap<Long, Integer>();
						List<Member> tempMemberList = memberDelegate.findAll(company).getList();
						
						
						String str_fromDate = request.getParameter("fromDate2");
						String str_toDate = request.getParameter("toDate2");
						DateFormat formatter ; 	    
				        Calendar c1 = Calendar.getInstance();
				        Calendar c2 = Calendar.getInstance();
				        formatter = new SimpleDateFormat("MM-dd-yyyy");
				        formatter.parse(str_toDate);
				        c1.setTime(formatter.parse(str_fromDate));
				        //this line of code is use to add days on the range of report date
				        c1.add(Calendar.DATE, 0);
				        c2.setTime(formatter.parse(str_toDate));
				        c2.add(Calendar.DATE, 1);
				        str_fromDate = formatter.format(c1.getTime());
				        str_toDate = formatter.format(c2.getTime());
				        Date fromDate = (Date)formatter.parse(str_fromDate);
				        Date toDate = (Date)formatter.parse(str_toDate); 	
				        
						
						
						for(Member member : tempMemberList){
							List<CartOrder> listCartOrder = cartOrderDelegate.findAllByMemberCompleted(company, member, fromDate, toDate).getList();
							topMemberMap.put(member.getId(), listCartOrder.size());
						}
						
						System.out.println("################ Map of top Member ######################");
						System.out.println(topMemberMap);
						System.out.println("################ ------------------ ######################");
						
						
						
						
						
						List list = new LinkedList(topMemberMap.entrySet());
						 
						Collections.sort(list, new Comparator() {
							public int compare(Object o1, Object o2) {
								return ((Comparable) ((Map.Entry) (o2)).getValue())
											.compareTo(((Map.Entry) (o1)).getValue());
							}
						});
					 
						Map sortedMap = new LinkedHashMap();
						for (Iterator it = list.iterator(); it.hasNext();) {
							Map.Entry entry = (Map.Entry) it.next();
							sortedMap.put(entry.getKey(), entry.getValue());
						}
						topMemberMap = sortedMap;
						
						
						System.out.println("################ Sorted Map here ######################");
						System.out.println(topMemberMap);
						System.out.println("################ ------------------ ######################");
						for(Long memberid : topMemberMap.keySet()){
							Member member1 = memberDelegate.find(memberid);
							double totalOrderPrice_ = 0.0;
							List<CartOrder> listCartOrder = cartOrderDelegate.findAllByMemberCompleted(company, member1, fromDate, toDate).getList();
							for(CartOrder tempCartOrder : listCartOrder){
								List<CartOrderItem> listCartOrderItem = cartOrderItemDelegate.findAll(tempCartOrder).getList();
								for(CartOrderItem tempCartOrderItem : listCartOrderItem){
									totalOrderPrice_= totalOrderPrice_ + tempCartOrderItem.getItemDetail().getPrice();
								}
							}
							
							DecimalFormat formatterdec = new DecimalFormat("#,##0.00");
							if(topMemberMap.get(member1.getId()) > 0){
								orderItems.add(new PDFItem(
										str_fromDate,//f1
										str_toDate,//f2
										counterRank.toString(),//f3
										member1.getFullName(),//f4 - 
										member1.getEmail(),//f5 - 
										String.valueOf(topMemberMap.get(member1.getId())),//f6 - 
										formatterdec.format(totalOrderPrice_),//f7 - 
										member1.getCity(),
										"",
										"",
										"",
										logo,//f12 - logo
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										""));
								
									counterRank++;
								}
							}
							
							
							
							
						
						
						//place all collected data to the datasource to be used by the jasper as a "datasource"
						setOrderDatasource(new PDFDataSource(orderItems));
						PDFCreator.outputPDF(orderDatasource,templateName,""+company.getName()+"_Top_CUSTOMER_"+"_"+sdf.format(date.getTime())+".pdf");	
				
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
	
				//Generate Wendys Delivery Tracking Report
				public void printWendysTopProducts(){
					
					request = ServletActionContext.getRequest();
					company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
					
					String logo = PDFCreator.getLogo();
					Integer counterRank = 1;
					
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
						templateName="wendys_top_products.jrxml";
					}
					
					//Try to create a report file
					try {
						
						
						String str_fromDate = request.getParameter("fromDate3");
						String str_toDate = request.getParameter("toDate3");
						DateFormat formatter ; 	    
				        Calendar c1 = Calendar.getInstance();
				        Calendar c2 = Calendar.getInstance();
				        formatter = new SimpleDateFormat("MM-dd-yyyy");
				        formatter.parse(str_toDate);
				        c1.setTime(formatter.parse(str_fromDate));
				        //this line of code is use to add days on the range of report date
				        c1.add(Calendar.DATE, 0);
				        c2.setTime(formatter.parse(str_toDate));
				        c2.add(Calendar.DATE, 1);
				        str_fromDate = formatter.format(c1.getTime());
				        str_toDate = formatter.format(c2.getTime());
				        Date fromDate = (Date)formatter.parse(str_fromDate);
				        Date toDate = (Date)formatter.parse(str_toDate); 	
				        
						
				        Map<String, Integer> topProductMap = new HashMap<String, Integer>();
						Group group = GroupDelegate.getInstance().find(company, 475L);
						
						List<CategoryItem> listProductName = categoryItemDelegate.findAllByGroup(company, group).getList();
						ItemDetail itemDetail;
						for(CategoryItem productItem : listProductName){
							List<CartOrderItem> listCartOrderItem = cartOrderItemDelegate.findAllByNameCompleted(company, productItem.getName(), fromDate, toDate);
							if(listCartOrderItem != null){
								for(CartOrderItem cItem : listCartOrderItem){
									
									ItemDetail idl = cItem.getItemDetail();
									CartOrder co_ = cItem.getOrder();
									
									logger.info("########## Order Status :::: "+idl.getName()+"="+cItem.getOrder().getStatus()+"##########################");
								}
							}
							
							Integer listSize = 0;
							if(listCartOrderItem != null){
								for(CartOrderItem cItem : listCartOrderItem){
									if(cItem.getOrder().getStatus() == OrderStatus.COMPLETED){
										listSize++;
									}
								}
								//listSize = listCartOrderItem.size();
							}
							topProductMap.put(productItem.getName() , listSize);
							
						}
				        
						System.out.println("################ Map of top Member ######################");
						System.out.println(topProductMap);
						System.out.println("################ ------------------ ######################");
						
						
						
						
						
						List list = new LinkedList(topProductMap.entrySet());
						 
						Collections.sort(list, new Comparator() {
							public int compare(Object o1, Object o2) {
								return ((Comparable) ((Map.Entry) (o2)).getValue())
											.compareTo(((Map.Entry) (o1)).getValue());
							}
						});
					 
						Map sortedMap = new LinkedHashMap();
						for (Iterator it = list.iterator(); it.hasNext();) {
							Map.Entry entry = (Map.Entry) it.next();
							sortedMap.put(entry.getKey(), entry.getValue());
						}
						topProductMap = sortedMap;
						
						
						System.out.println("################ Sorted Map here ######################");
						System.out.println(topProductMap);
						System.out.println("################ ------------------ ######################");
				        
						for(String productneym : topProductMap.keySet()){
							
							
							if(topProductMap.get(productneym) > 0){
								orderItems.add(new PDFItem(
										str_fromDate,//f1
										str_toDate,//f2
										counterRank.toString(),//f3
										productneym,//f4 - 
										String.valueOf(topProductMap.get(productneym)),//f5 - 
										"",//f6 - 
										"",//f7 - 
										"",
										"",
										"",
										"",
										logo,//f12 - logo
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										""));
									counterRank++;
								
								}
							
							
							
						}
				        
				        
				        
				        
				        
				        
				        
				        
				        
				        
				        
				        
				        
				        
				        
							
						
						
						//place all collected data to the datasource to be used by the jasper as a "datasource"
						setOrderDatasource(new PDFDataSource(orderItems));
						PDFCreator.outputPDF(orderDatasource,templateName,""+company.getName()+"_Top_PRODUCTS_"+"_"+sdf.format(date.getTime())+".pdf");	
				
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
				
	//Generate Wendys Delivery Order Report
	public void printWendysDeliveryOrder(){
		
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
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
			templateName="wendysdelivery_order_template.jrxml";
		}
		
		//Try to create a report file
		try {
			
			//get date
			orderItems = new ArrayList<PDFItem>();
			cartOrderID = request.getParameter("cart_order_id");
			//lines or rows per page in the PDF
			int lines = 25;
			final Long orderID = Long.parseLong(cartOrderID);
			final CartOrder cartOrder = cartOrderDelegate.find(orderID);
			final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
			Date date2 = 
				(cartOrder.getCreatedOn() != null)
					? cartOrder.getCreatedOn()
					: new Date();
			String billingDate2 = dateFormat.format(date2);
			final List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
			int count=0;
			//generate html string containing list
			
			String cartOrderName = ""; 
			String cartOrderAddress1 = "";
			String cartOrderEmail = "";
			String cartOrderID = "";
			String receiverDetails = null;
			
			String currentItemName = "";
			String f2 = "SKU";
			String f7 = "Category name";
			String f10 = "10";
			String f11 = "root cat";
			String f12 = null;
			String f13 = null;
			
			Double discountCost = 0d;
			Double shippingCost = 0d;
			
			if(cartOrder != null)
			{
				if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
				{
					
				}
				else
				{
					cartOrderName = cartOrder.getName();
					cartOrderAddress1 = cartOrder.getAddress1();
					cartOrderEmail = cartOrder.getEmailAddress();
					cartOrderID = cartOrder.getId().toString();
				}
			}
			
			final String currency = "";
			final NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			final String displayCurrency = StringUtils.isNotBlank(currency) ? currency + " " : "";
			for (CartOrderItem currentItem : itemList)
			{
				final ItemDetail itemDetail = currentItem.getItemDetail();
				final Double itemPrice = itemDetail.getPrice();
				final Integer qty = currentItem.getQuantity();
				String formattedPrice = CartOrderUtil.isDiscountItem(currentItem) ? "(" + numberFormatter.format(itemDetail.getDiscount()) + ")" : numberFormatter.format(itemPrice);
				String subTotalFormatted = CartOrderUtil.isDiscountItem(currentItem) ? "(" + numberFormatter.format(itemDetail.getDiscount()) + ")" :numberFormatter.format((itemPrice * qty)); 
				
				currentItemName = currentItem.getItemDetail().getName();
				
				if (currentItem.getStatus() == null || currentItem.getStatus().equals(NEW))
				{
					count++;
					orderItems.add(new PDFItem(
						currentItemName, 
						f2, 
						"" + qty,
						displayCurrency + formattedPrice, 
						displayCurrency + subTotalFormatted, 
						logo,
						f7, 
						"", 
						company.getNameEditable(), 
						f10, 
						f11, 
						f12,
						f13, 
						cartOrderName, 
						cartOrderAddress1,
						cartOrderEmail,
						"" + cartOrderID, 
						"" + billingDate2,
						cartOrder.getCity(),
						cartOrder.getState(),
						cartOrder.getCountry(),
						cartOrder.getPhoneNumber(),
						cartOrder.getPaymentType() == PaymentType.COD ? "COD" : "Credit Card",
						String.valueOf(cartOrder.getStatus()),
						String.valueOf(cartOrder.getPaymentStatus()),
						String.valueOf(cartOrder.getShippingStatus()),
						"",
						"",
						"",
						"",
						"",
						""));
				}
				else if (currentItem.getStatus().equalsIgnoreCase("OK"))
				{
					count++;
					orderItems
						.add(new PDFItem(
							currentItemName,
							f2,
							"" + (qty > 0 ? qty : ""),
							displayCurrency + formattedPrice,
							displayCurrency + subTotalFormatted, 
							logo,
							f7, 
							"", 
							company.getNameEditable(), 
							f10,
							f11, 
							f12, 
							f13, 
							cartOrderName, 
							cartOrderAddress1, 
							cartOrderEmail, 
							"" + cartOrderID, 
							"" + billingDate2,
							cartOrder.getCity(),
							cartOrder.getState(),
							cartOrder.getCountry(),
							cartOrder.getPhoneNumber(),
							cartOrder.getPaymentType() == PaymentType.COD ? "COD" : "Credit Card",
							String.valueOf(cartOrder.getStatus()),
							String.valueOf(cartOrder.getPaymentStatus()),
							String.valueOf(cartOrder.getShippingStatus()),
							"",
							"",
							"",
							"",
							"",
							""));
				}
			}
			
			
			for (int x = 0; x < lines - (count % lines); x++)
			{
				if (x == lines - (count % lines) - 1) {
					if(company.getId() == CompanyConstants.TOMATO || company.getId() == CompanyConstants.SWAPCANADA) {
						
					}
					else {
						orderItems
							.add(new PDFItem(
								"TOTAL PRICE", 
								null, 
								null, 
								null,
								displayCurrency + numberFormatter.format((cartOrder.getTotalPriceOk() - cartOrder.getTotalDiscount())), 
								null, 
								null, 
								"", 
								null, 
								null,
								null, 
								null, 
								company.getStatement(),
								null,
								null, 
								null, 
								null, 
								null,
								cartOrder.getCity(),
								cartOrder.getState(),
								cartOrder.getCountry(),
								cartOrder.getPhoneNumber(),
								cartOrder.getPaymentType() == PaymentType.COD ? "COD" : "Credit Card",
								String.valueOf(cartOrder.getStatus()),
								String.valueOf(cartOrder.getPaymentStatus()),
								String.valueOf(cartOrder.getShippingStatus()),
								"",
								"",
								"",
								"",
								"",
								""));
					}
				}
				else
				{
				}
			}
					
			//place all collected data to the datasource to be used by the jasper as a "datasource"
			setOrderDatasource(new PDFDataSource(orderItems));
			PDFCreator.outputPDF(orderDatasource,templateName,""+company.getName()+"_OrderList_ID_"+cartOrderID+"_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	//Generate Wendys PICKUP Order Report
		public void printWendysPickupOrder(){

			request = ServletActionContext.getRequest();
			company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			
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
				templateName="wendyspickup_order_template.jrxml";
			}
			
			//Try to create a report file
			try {
				
				//get date
				orderItems = new ArrayList<PDFItem>();
				cartOrderID = request.getParameter("cart_order_id");
				//lines or rows per page in the PDF
				int lines = 25;
				final Long orderID = Long.parseLong(cartOrderID);
				final CartOrder cartOrder = cartOrderDelegate.find(orderID);
				final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
				Date date2 = 
					(cartOrder.getCreatedOn() != null)
						? cartOrder.getCreatedOn()
						: new Date();
				String billingDate2 = dateFormat.format(date2);
				final List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
				int count=0;
				//generate html string containing list
				String strReceiver = cartOrder.getComments();
				String strLimitter1 = "Prefferred Store: ";
				String strLimitter2 = "Preffered Date: ";
				String strLimitter3 = "Preffered Time: ";
				
				strLimitter1 = strReceiver.substring(strReceiver.indexOf(strLimitter1)+(strLimitter1.length()-1),strReceiver.indexOf(strLimitter2)).trim();
				strLimitter2 = strReceiver.substring(strReceiver.indexOf(strLimitter2)+(strLimitter2.length()-1),strReceiver.indexOf(strLimitter3)).trim();
				strLimitter3 = strReceiver.substring(strReceiver.indexOf(strLimitter3)+(strLimitter3.length()-1),strReceiver.length()).trim();
				
				String cartOrderName = ""; 
				String cartOrderAddress1 = "";
				String cartOrderEmail = "";
				String cartOrderID = "";
				String receiverDetails = null;
				
				String currentItemName = "";
				String f2 = "SKU";
				String f7 = "Category name";
				String f10 = "10";
				String f11 = "root cat";
				String f12 = null;
				String f13 = null;
				
				Double discountCost = 0d;
				Double shippingCost = 0d;
				
				if(cartOrder != null)
				{
					if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
					{
						
					}
					else
					{
						cartOrderName = cartOrder.getName();
						cartOrderAddress1 = cartOrder.getAddress1();
						cartOrderEmail = cartOrder.getEmailAddress();
						cartOrderID = cartOrder.getId().toString();
					}
				}
				
				final String currency = "";
				final NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
				numberFormatter.setMinimumFractionDigits(2);
				
				final String displayCurrency = StringUtils.isNotBlank(currency) ? currency + " " : "";
				for (CartOrderItem currentItem : itemList)
				{
					final ItemDetail itemDetail = currentItem.getItemDetail();
					final Double itemPrice = itemDetail.getPrice();
					final Integer qty = currentItem.getQuantity();
					String formattedPrice = CartOrderUtil.isDiscountItem(currentItem) ? "(" + numberFormatter.format(itemDetail.getDiscount()) + ")" : numberFormatter.format(itemPrice);
					String subTotalFormatted = CartOrderUtil.isDiscountItem(currentItem) ? "(" + numberFormatter.format(itemDetail.getDiscount()) + ")" :numberFormatter.format((itemPrice * qty)); 
					
					currentItemName = currentItem.getItemDetail().getName();
					
					if (currentItem.getStatus() == null || currentItem.getStatus().equals(NEW))
					{
						count++;
						orderItems.add(new PDFItem(
							currentItemName, 
							f2, 
							"" + qty,
							displayCurrency + formattedPrice, 
							displayCurrency + subTotalFormatted, 
							logo,
							f7, 
							"", 
							company.getNameEditable(), 
							f10, 
							f11, 
							f12,
							f13, 
							cartOrderName, 
							"",
							cartOrderEmail,
							"" + cartOrderID, 
							"" + billingDate2,
							strLimitter1,
							strLimitter2,
							strLimitter3,
							cartOrder.getPhoneNumber(),
							cartOrder.getPaymentType() == PaymentType.COD ? "COD" : "Credit Card",
							String.valueOf(cartOrder.getStatus()),
							String.valueOf(cartOrder.getPaymentStatus()),
							"",
							"",
							"",
							"",
							"",
							"",
							""));
					}
					else if (currentItem.getStatus().equalsIgnoreCase("OK"))
					{
						count++;
						orderItems
							.add(new PDFItem(
								currentItemName,
								f2,
								"" + (qty > 0 ? qty : ""),
								displayCurrency + formattedPrice,
								displayCurrency + subTotalFormatted, 
								logo,
								f7, 
								"", 
								company.getNameEditable(), 
								f10,
								f11, 
								f12, 
								f13, 
								cartOrderName, 
								"", 
								cartOrderEmail, 
								"" + cartOrderID, 
								"" + billingDate2,
								strLimitter1,
								strLimitter2,
								strLimitter3,
								cartOrder.getPhoneNumber(),
								cartOrder.getPaymentType() == PaymentType.COD ? "COD" : "Credit Card",
								String.valueOf(cartOrder.getStatus()),
								String.valueOf(cartOrder.getPaymentStatus()),
								"",
								"",
								"",
								"",
								"",
								"",
								""));
					}
				}
				
				
				for (int x = 0; x < lines - (count % lines); x++)
				{
					if (x == lines - (count % lines) - 1) {
						if(company.getId() == CompanyConstants.TOMATO || company.getId() == CompanyConstants.SWAPCANADA) {
							
						}
						else {
							orderItems
								.add(new PDFItem(
									"TOTAL PRICE", 
									null, 
									null, 
									null,
									displayCurrency + numberFormatter.format((cartOrder.getTotalPriceOk() - cartOrder.getTotalDiscount())), 
									null, 
									null, 
									"", 
									null, 
									null,
									null, 
									null, 
									company.getStatement(),
									null,
									null, 
									null, 
									null, 
									null,
									strLimitter1,
									strLimitter2,
									strLimitter3,
									cartOrder.getPhoneNumber(),
									cartOrder.getPaymentType() == PaymentType.COD ? "COD" : "Credit Card",
									String.valueOf(cartOrder.getStatus()),
									String.valueOf(cartOrder.getPaymentStatus()),
									"",
									"",
									"",
									"",
									"",
									"",
									""));
						}
					}
					else
					{

					}
				}
						
				//place all collected data to the datasource to be used by the jasper as a "datasource"
				setOrderDatasource(new PDFDataSource(orderItems));
				PDFCreator.outputPDF(orderDatasource,templateName,""+company.getName()+"_OrderList_ID_"+cartOrderID+"_"+sdf.format(date.getTime())+".pdf");	
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
	//Generates the report Order List
	public void generateOrderReport(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
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
			templateName="default_order_template.jrxml";
		}
		
		//Try to create a report file
		try {
			//get date
			orderItems = new ArrayList<PDFItem>();
			cartOrderID = request.getParameter("cart_order_id");
			//lines or rows per page in the PDF
			int lines = 25;
			final Long orderID = Long.parseLong(cartOrderID);
			final CartOrder cartOrder = cartOrderDelegate.find(orderID);
			final DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
			Date date2 = 
				(cartOrder.getCreatedOn() != null)
					? cartOrder.getCreatedOn()
					: new Date();
			String billingDate2 = dateFormat.format(date2);
			final List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
			int count=0;
			//generate html string containing list
			
			String cartOrderName = ""; 
			String cartOrderAddress1 = "";
			String cartOrderEmail = "";
			String cartOrderID = "";
			String receiverDetails = null;
			
			String currentItemName = "";
			String f2 = "SKU";
			String f7 = "Category name";
			String f10 = "10";
			String f11 = "root cat";
			String f12 = null;
			String f13 = null;
			
			Double discountCost = 0d;
			Double shippingCost = 0d;
			
			if(cartOrder != null)
			{
				if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
				{
					templateName = "gurkka_order_template.jrxml";
					lines = 2;
					
					Member member = cartOrder.getMember();
					if(member != null)
					{
						member = memberDelegate.find(member.getId()); // reload member info
						//cartOrderName = cartOrder.getName() + " (" + GurkkaMemberUtil.getMemberCode(company, member) + ")";
						cartOrderAddress1 = member.getAddress1() +  " " + StringUtils.trimToEmpty(member.getCity()) + " " + StringUtils.trimToEmpty(member.getProvince());;
						cartOrderEmail = member.getEmail();
						cartOrderID = !StringUtils.isEmpty(cartOrder.getTransactionNumber())
							? cartOrder.getTransactionNumber()
							: "";
						
						{
							if ((!StringUtils.isEmpty(cartOrder.getAddress2())
									|| !StringUtils.isEmpty(cartOrder.getAddress1())
									|| !StringUtils.isEmpty(cartOrder.getPhoneNumber())
									|| !StringUtils.isEmpty(cartOrder.getState())
									|| !StringUtils.isEmpty(cartOrder.getCity())) 
								&& !StringUtils.containsIgnoreCase(cartOrder.getAddress1(), company.getAddress()))
							{
								final StringBuilder stringBuilder = new StringBuilder();
								stringBuilder.append(
										"Receiver's Details: " + "\n");
								stringBuilder.append(cartOrder.getAddress2() != null 
										? "Name: " + cartOrder.getAddress2() + "\n" : "");
								stringBuilder.append(cartOrder.getAddress1() != null 
										? "Address: " + cartOrder.getAddress1() + "\n" : "");
								
								//stringBuilder.append(cartOrder.getPhoneNumber() != null 
								//		? "Contact No.: " + cartOrder.getPhoneNumber() + "\n" : "");
								stringBuilder.append(cartOrder.getPhoneNumber() != null 
										? "Telephone No.: " + cartOrder.getPhoneNumber() + "\n" : "");
								stringBuilder.append(cartOrder.getInfo2() != null 
										? "Mobile No.: " + cartOrder.getInfo2() + "\n" : "");
								
								stringBuilder.append(cartOrder.getState() != null 
										? "Area: " + cartOrder.getState() + "\n" : "");
								stringBuilder.append(cartOrder.getCity() != null 
										? "Location: " + cartOrder.getCity() + "\n" : "");
								receiverDetails = stringBuilder.toString();
							}
						}
							
						/** revert - date of transaction not on the above condition */
						date2 = cartOrder.getCreatedOn();
						billingDate2 = dateFormat.format(date2);
					}
					final String contactNumber = 
							StringUtils.isNotEmpty(cartOrder.getPhoneNumber()) 
							? cartOrder.getPhoneNumber() 
							: member == null ? "" : member.getMobile();
					
					f11 = StringUtils.trimToNull(contactNumber);
					f12 = StringUtils.trimToNull(
						cartOrder.getPaymentType() != null
						? cartOrder.getPaymentType().getValue()
						: "");
					f13 = StringUtils.trimToNull(
						cartOrder.getShippingType() != null
						? cartOrder.getShippingType().getValue()
						: "");
				}
				else
				{
					cartOrderName = cartOrder.getName();
					cartOrderAddress1 = cartOrder.getAddress1();
					cartOrderEmail = cartOrder.getEmailAddress();
					cartOrderID = cartOrder.getId().toString();
				}
			}
			
			final String currency = company.getId() == CompanyConstants.GURKKA ? "P" : "";
			final NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			final String displayCurrency = StringUtils.isNotBlank(currency) ? currency + " " : "";
			for (CartOrderItem currentItem : itemList)
			{
				
				final ItemDetail itemDetail = currentItem.getItemDetail();
				final Double itemPrice = itemDetail.getPrice();
				final Integer qty = (company.getName().equals("mraircon") && currentItem.getQuantity() == null ? 1 : currentItem.getQuantity());
				String formattedPrice = CartOrderUtil.isDiscountItem(currentItem) ? "(" + numberFormatter.format(itemDetail.getDiscount()) + ")" : numberFormatter.format(itemPrice);
				
				if(company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE)) {
					formattedPrice = numberFormatter.format(currentItem.getCategoryItem().getPrice()) + "\n (Less " + itemDetail.getDiscount() + "%)";
				}
				
				String subTotalFormatted = CartOrderUtil.isDiscountItem(currentItem) ? "(" + numberFormatter.format(itemDetail.getDiscount()) + ")" :numberFormatter.format((itemPrice * qty)); 
				
				if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
				{
					final CategoryItem item = CategoryItemUtil.getItemFromCartOrder(currentItem);
					if(item != null)
					{
						final String itemName = currentItem.getItemDetail().getName();
						final CategoryItemOtherField ciof = categoryItemOtherFieldDelegate.findByOtherFieldKeyword(company, item, "size");
						final String productSize = (ciof != null)
							? ciof.getContent()
							: null;
						final Brand brand = (item.getBrand() != null)
							? brandDelegate.find(item.getBrand().getId()) 
							: null;
						final String brandName = (brand != null)
							? brand.getName()
							: null;
						
						final StringBuilder stringBuilder = new StringBuilder();	
						if(item.getItemDetail()!=null && item.getItemDetail().getSku()!=null&&item.getItemDetail().getSku().equalsIgnoreCase("")) {
							stringBuilder.append(item.getName());
						} else {
							stringBuilder.append(itemName);
							totalPrice += currentItem.getCategoryItem().getPrice() * currentItem.getQuantity();
						}
						if(productSize != null || brandName != null)
						{
							stringBuilder.append(" (");
							if(productSize != null)
							{
								stringBuilder.append(productSize);
							}
							if(productSize != null && brandName != null)
							{
								stringBuilder.append(", ");
							}
							if(brandName != null)
							{
								stringBuilder.append(brandName);
							}
							stringBuilder.append(")");
						}
						
						currentItemName = stringBuilder.toString();
					}
					else
					{
						currentItemName = currentItem.getItemDetail().getName();
					}
					lines++;
				}
				else if(company.getId() == CompanyConstants.TOMATO) 
				{
					currentItemName = currentItem.getItemDetail().getName();
					
					if(currentItemName.equals(DISCOUNT)) {
						discountCost = itemPrice;
					}
					else if(currentItemName.equals(SHIPPING)) {
						shippingCost = itemPrice;
					}
					else {
						currentItemName = currentItem.getItemDetail().getName() + SINGLE_SPACE + STRAP;
						formattedPrice = numberFormatter.format(itemPrice);
						subTotalFormatted = numberFormatter.format((itemPrice) * qty); 
					}
				}
				else if(company.getId() == CompanyConstants.SWAPCANADA)
				{
					currentItemName = currentItem.getItemDetail().getName();
					
					if(currentItemName.equals(DISCOUNT)) {
						discountCost = itemPrice;
					}
					else if(currentItemName.equals(SHIPPING)) {
						shippingCost = itemPrice;
					}
					else {
						currentItemName = currentItem.getItemDetail().getName() + SINGLE_SPACE + STRAP;
						formattedPrice = numberFormatter.format(itemPrice);
						subTotalFormatted = numberFormatter.format((itemPrice) * qty); 
					}
				}
				else
				{
					currentItemName = currentItem.getItemDetail().getName();
					if(company.getName().equals("mraircon")) {
						if(currentItem.getItemDetail().getRealID() != null) {
							CategoryItem categoryItem = categoryItemDelegate.find(currentItem.getItemDetail().getRealID());
							if(categoryItem != null) {
								String desc1 = "";
								String desc2 = "";
								String outdoorModelNo = "";
								String indoorModelNo = "";
								desc1 = categoryItem.getCategoryItemOtherFieldMap().get("Description Line 1").getContent();
								desc2 = categoryItem.getCategoryItemOtherFieldMap().get("Description Line 2").getContent();
								indoorModelNo = (categoryItem.getCategoryItemOtherFieldMap().get("Indoor Model No.").getContent() != null
										? categoryItem.getCategoryItemOtherFieldMap().get("Indoor Model No.").getContent() : "");
								outdoorModelNo = "(" + (categoryItem.getCategoryItemOtherFieldMap().get("Outdoor Model No.").getContent() != null && !categoryItem.getCategoryItemOtherFieldMap().get("Outdoor Model No.").getContent().equals("") 
										? categoryItem.getCategoryItemOtherFieldMap().get("Outdoor Model No.").getContent() : "") + ")";
								currentItemName += "\n"+desc1+"\n"+desc2+"\n"+indoorModelNo+""+outdoorModelNo;
							}
						}
					}
				}
				
				if (currentItem.getStatus() == null || currentItem.getStatus().equals(NEW))
				{
					if(company.getId() == CompanyConstants.TOMATO  
							&& !currentItem.getItemDetail().getName().equals(DISCOUNT) 
							&& !currentItem.getItemDetail().getName().equals(SHIPPING)) {
						count++;

						String currentFaceName = "";
						Double facePrice = currentItem.getOtherDetail().getFace().getPrice();
						String formattedFacePrice = numberFormatter.format(facePrice);
						String formattedFaceSubtotal = numberFormatter.format(facePrice * qty);
						totalPrice += facePrice * qty;
						totalPrice += itemPrice * qty;
						
						if(currentItem.getOtherDetail() != null)
							currentFaceName = !currentItem.getOtherDetail().getFace().getName().equals(UPLOADED_PHOTO) ? currentItem.getOtherDetail().getFace().getName() : DIY;
							
						orderItems.add(new PDFItem(
							currentFaceName + SINGLE_SPACE + FACE, 
							f2, 
							"" + qty,
							displayCurrency + formattedFacePrice, 
							displayCurrency + formattedFaceSubtotal, 
							logo,
							f7, 
							"" + company.getAddress() + "  " + company.getEmail(), 
							company.getNameEditable(), 
							f10, 
							f11, 
							f12,
							f13, 
							cartOrderName, 
							cartOrderAddress1,
							cartOrderEmail,
							"" + cartOrderID, 
							"" + billingDate2));	
						
						count++;
						orderItems.add(new PDFItem(
							currentItemName, 
							f2, 
							"" + qty,
							displayCurrency + formattedPrice, 
							displayCurrency + subTotalFormatted, 
							logo,
							f7, 
							"" + company.getAddress() + "  " + company.getEmail(), 
							company.getNameEditable(), 
							f10, 
							f11, 
							f12,
							f13, 
							cartOrderName, 
							cartOrderAddress1,
							cartOrderEmail,
							"" + cartOrderID, 
							"" + billingDate2));					
					}
					else if(company.getId() == CompanyConstants.SWAPCANADA  
							&& !currentItem.getItemDetail().getName().equals(DISCOUNT) 
							&& !currentItem.getItemDetail().getName().equals(SHIPPING)) {
						if(StringUtils.contains(currentItem.getItemDetail().getImage(), PNG_EXTENSION)) {
							count++;

							String currentFaceName = "";
							Double facePrice = currentItem.getOtherDetail().getFace().getPrice();
							String formattedFacePrice = numberFormatter.format(facePrice);
							String formattedFaceSubtotal = numberFormatter.format(facePrice * qty);
							totalPrice += facePrice * qty;
							totalPrice += itemPrice * qty;
							
							if(currentItem.getOtherDetail() != null)
								currentFaceName = !currentItem.getOtherDetail().getFace().getName().equals(UPLOADED_PHOTO) ? currentItem.getOtherDetail().getFace().getName() : DIY;
								
							orderItems.add(new PDFItem(
								currentFaceName + SINGLE_SPACE + FACE, 
								f2, 
								"" + qty,
								displayCurrency + formattedFacePrice, 
								displayCurrency + formattedFaceSubtotal, 
								logo,
								f7, 
								"" + company.getAddress() + "  " + company.getEmail(), 
								company.getNameEditable(), 
								f10, 
								f11, 
								f12,
								f13, 
								cartOrderName, 
								cartOrderAddress1,
								cartOrderEmail,
								"" + cartOrderID, 
								"" + billingDate2));	
							
							count++;
							orderItems.add(new PDFItem(
								currentItemName, 
								f2, 
								"" + qty,
								displayCurrency + formattedPrice, 
								displayCurrency + subTotalFormatted, 
								logo,
								f7, 
								"" + company.getAddress() + "  " + company.getEmail(), 
								company.getNameEditable(), 
								f10, 
								f11, 
								f12,
								f13, 
								cartOrderName, 
								cartOrderAddress1,
								cartOrderEmail,
								"" + cartOrderID, 
								"" + billingDate2));
						}
						else {
							count++;
							String image = currentItem.getItemDetail().getImage();
							String[] color = image != null ? image.split(",") : null;
							CategoryItem colorItem = null; 
							totalPrice += itemPrice * qty;
							
							orderItems.add(new PDFItem(
								currentItem.getItemDetail().getName(), 
								f2, 
								"" + qty,
								displayCurrency + formattedPrice, 
								displayCurrency + subTotalFormatted, 
								logo,
								f7, 
								"" + company.getAddress() + "  " + company.getEmail(), 
								company.getNameEditable(), 
								f10, 
								f11, 
								f12,
								f13, 
								cartOrderName, 
								cartOrderAddress1,
								cartOrderEmail,
								"" + cartOrderID, 
								"" + billingDate2));
							
							if(color != null) {
								if(color.length > 0) {
									count++;
									colorItem = categoryItemDelegate.findSKU(company, color[0]); 
									
									orderItems.add(new PDFItem(
										TRIPLE_SPACE + colorItem.getName() + SINGLE_SPACE + STRAP, 
										f2, 
										"" + qty,
										displayCurrency + ZERO, 
										displayCurrency + ZERO, 
										logo,
										f7, 
										"" + company.getAddress() + "  " + company.getEmail(), 
										company.getNameEditable(), 
										f10, 
										f11, 
										f12,
										f13, 
										cartOrderName, 
										cartOrderAddress1,
										cartOrderEmail,
										"" + cartOrderID, 
										"" + billingDate2));
								}
								
								if(color.length > 1) {
									count++;
									colorItem = categoryItemDelegate.findSKU(company, color[1]); 
									
									orderItems.add(new PDFItem(
										TRIPLE_SPACE + colorItem.getName() + SINGLE_SPACE + STRAP, 
										f2, 
										"" + qty,
										displayCurrency + ZERO, 
										displayCurrency + ZERO, 
										logo,
										f7, 
										"" + company.getAddress() + "  " + company.getEmail(), 
										company.getNameEditable(), 
										f10, 
										f11, 
										f12,
										f13, 
										cartOrderName, 
										cartOrderAddress1,
										cartOrderEmail,
										"" + cartOrderID, 
										"" + billingDate2));
								}
							}
						}
					}
					else if(company.getId() != CompanyConstants.TOMATO && company.getId() != CompanyConstants.SWAPCANADA) {
						count++;
						orderItems.add(new PDFItem(
							currentItemName, 
							f2, 
							"" + qty,
							displayCurrency + formattedPrice, 
							displayCurrency + subTotalFormatted, 
							logo,
							f7, 
							"" + company.getAddress() + "  " + company.getEmail(), 
							company.getNameEditable(), 
							f10, 
							f11, 
							f12,
							f13, 
							cartOrderName, 
							cartOrderAddress1,
							cartOrderEmail,
							"" + cartOrderID, 
							"" + billingDate2));
						
						
						double subTotalPrice = CartOrderUtil.isDiscountItem(currentItem) ? itemDetail.getDiscount() : itemPrice * qty;
						
						totalPrice += subTotalPrice;
					}
				} 
				else if (currentItem.getStatus().equalsIgnoreCase("OK"))
				{
					count++;
					orderItems
						.add(new PDFItem(
							currentItemName,
							f2,
							"" + (qty > 0 ? qty : ""),
							displayCurrency + formattedPrice,
							displayCurrency + subTotalFormatted, 
							logo,
							f7, 
							"" + company.getAddress() + "  " + company.getEmail(), 
							company.getNameEditable(), 
							f10,
							f11, 
							f12, 
							f13, 
							cartOrderName, 
							cartOrderAddress1, 
							cartOrderEmail, 
							"" + cartOrderID, 
							"" + billingDate2));
				}
			}
			
			if(company.getId() == CompanyConstants.TOMATO || company.getId() == CompanyConstants.SWAPCANADA) {
				count++;
				orderItems.add(new PDFItem(
					SHIPPING, 
					f2, 
					"1",
					displayCurrency + numberFormatter.format(shippingCost), 
					displayCurrency + numberFormatter.format(shippingCost), 
					logo,
					f7, 
					"" + company.getAddress() + "  " + company.getEmail(), 
					company.getNameEditable(), 
					f10, 
					f11, 
					f12,
					f13, 
					cartOrderName, 
					cartOrderAddress1,
					cartOrderEmail,
					"" + cartOrderID, 
					"" + billingDate2));
				
				if(discountCost > 0) {
					count++;
					orderItems.add(new PDFItem(
						DISCOUNT, 
						f2, 
						"1",
						displayCurrency + NEGATIVE + numberFormatter.format(discountCost), 
						displayCurrency + NEGATIVE + numberFormatter.format(discountCost), 
						logo,
						f7, 
						"" + company.getAddress() + "  " + company.getEmail(), 
						company.getNameEditable(), 
						f10, 
						f11, 
						f12,
						f13, 
						cartOrderName, 
						cartOrderAddress1,
						cartOrderEmail,
						"" + cartOrderID, 
						"" + billingDate2));
				}
			}
			
			for (int x = 0; x < lines - (count % lines); x++)
			{
				if (x == lines - (count % lines) - 1) {
					if(company.getId() == CompanyConstants.TOMATO || company.getId() == CompanyConstants.SWAPCANADA) {
						orderItems
							.add(new PDFItem(
								"TOTAL PRICE", 
								null, 
								null, 
								null,
								displayCurrency + numberFormatter.format(totalPrice - discountCost + shippingCost), 
								null, 
								null, 
								"" + company.getAddress() + "  " + company.getEmail(), 
								null, 
								null,
								null, 
								null, 
								(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST
										? StringUtils.trimToNull(receiverDetails)
										: company.getStatement()),
								(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST
										? StringUtils.trimToNull(cartOrder.getComments()) 
										: null),
								null, 
								null, 
								null, 
								null));
					}
					else {
						String displayTotalPrice = "";
						if(company.getName().equals("mraircon")) {
							displayTotalPrice = numberFormatter.format(totalPrice - cartOrder.getTotalDiscount());
						} else if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST) {
							boolean isPickup = cartOrder.getShippingType().equals(ShippingType.PICKUP);
							if(isPickup)
							{
								totalDiscount = totalPrice * 0.015;
								orderItems
								.add(new PDFItem(
									"Discount(1.5%)", 
									null, 
									null, 
									null,
									displayCurrency + totalDiscount, 
									null, 
									null, 
									"" + company.getAddress() + "  " + company.getEmail(), 
									null, 
									null,
									null, 
									null, 
									(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST
											? StringUtils.trimToNull(receiverDetails)
											: company.getStatement()),
									(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST
											? StringUtils.trimToNull(cartOrder.getComments()) 
											: null),
									null, 
									null, 
									null, 
									null));
								totalPrice = totalPrice - totalDiscount;
							}
							displayTotalPrice = String.valueOf(totalPrice);
						} else if (company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE)) {
							displayTotalPrice = cartOrder.getTotalPriceOkFormatted();
						} else {
							displayTotalPrice = numberFormatter.format((cartOrder.getTotalPriceOk() - cartOrder.getTotalDiscount()));
						}
						orderItems
							.add(new PDFItem(
								"TOTAL PRICE", 
								null, 
								null, 
								null,
								displayCurrency + displayTotalPrice, 
								null, 
								null, 
								"" + company.getAddress() + "  " + company.getEmail(), 
								null, 
								null,
								null, 
								null, 
								(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST
										? StringUtils.trimToNull(receiverDetails)
										: company.getStatement()),
								(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST
										? StringUtils.trimToNull(cartOrder.getComments()) 
										: null),
								null, 
								null, 
								null, 
								null));
					}
				}
				else
				{
					if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
					{
						orderItems
						.add(new PDFItem(
							null,
							null, 
							null, 
							null,
							null,
							null, 
							null, 
							null,
							null, 
							null,
							null, 
							null, 
							StringUtils.trimToNull(receiverDetails),
							null,
							null, 
							null, 
							null, 
							null));
					}
					orderItems
						.add(new PDFItem(
							null, 
							null, 
							null, 
							null, 
							null,
							null, 
							null, 
							"" + company.getAddress() + "  " + company.getEmail(), 
							null, 
							null, 
							null,
							null, 
							null, 
							null, 
							null, 
							null, 
							null, 
							null));
				}
			}
					
			//place all collected data to the datasource to be used by the jasper as a "datasource"
			setOrderDatasource(new PDFDataSource(orderItems));
			PDFCreator.outputPDF(orderDatasource,templateName,""+company.getName()+"_OrderList_ID_"+cartOrderID+"_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	

	//Generates the report Order List
	public void generateGiftcardOrderReport(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
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
			templateName="giftcard_order_template.jrxml";
		}
		
		//Try to create a report file
		try {
			//get date
			DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
			
			orderItems = new ArrayList<PDFItem>();
			
			cartOrderID = request.getParameter("cart_order_id");
			
			//lines or rows per page in the PDF
			int lines=23;
			Long orderID = Long.parseLong(cartOrderID);
			CartOrder cartOrder = cartOrderDelegate.find(orderID);
			String billingDate2 = dateFormat.format(cartOrder.getCreatedOn());
			int count=0;
			String address = ""; 
			
			if(cartOrder.getShippingType().toString().equals("DELIVERY"))
			{
				address = "Address 1:          " + cartOrder.getAddress1() + "\n";
			
				if(cartOrder.getAddress2() != null)
					address = address + "Address 2:          " + cartOrder.getAddress2() + "\n"; 
			
				address = address + "City:                    " + cartOrder.getCity() + "\n" + "Zip code:            " + cartOrder.getZipCode();
			}

			String paymentType = "";
			String shippingType = "";
			
			if(cartOrder.getPaymentType() == null)
				paymentType = "";
			else
				paymentType = cartOrder.getPaymentType().getValue();
			
			if(cartOrder.getShippingType() == null)
				shippingType = "";
			else
				shippingType = cartOrder.getShippingType().getValue();
			
			List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
			
			//generate html string containing list
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			for(CartOrderItem currentItem : itemList){
				if(currentItem.getStatus()==null){
					count++;
					orderItems.add(new PDFItem(currentItem.getItemDetail().getName(), "SKU", ""+currentItem.getQuantity(), numberFormatter.format(currentItem.getItemDetail().getPrice()), numberFormatter.format((currentItem.getItemDetail().getPrice()*currentItem.getQuantity())), logo, "Category name", ""+company.getAddress()+"  "+company.getEmail(), company.getNameEditable(), paymentType, address, cartOrder.getPhoneNumber(), null, cartOrder.getName(), shippingType, cartOrder.getEmailAddress(), ""+cartOrder.getId(),""+billingDate2));
				}
				else if(currentItem.getStatus().equalsIgnoreCase("OK")){
					count++;
					orderItems.add(new PDFItem(currentItem.getItemDetail().getName(), "SKU", ""+currentItem.getQuantity(), numberFormatter.format(currentItem.getItemDetail().getPrice()), numberFormatter.format((currentItem.getItemDetail().getPrice()*currentItem.getQuantity())), logo, "Category name", ""+company.getAddress()+"  "+company.getEmail(), company.getNameEditable(), paymentType, address, cartOrder.getPhoneNumber(), null, cartOrder.getName(), shippingType, cartOrder.getEmailAddress(), ""+cartOrder.getId(),""+billingDate2));
				}
			}
			
			if(cartOrder.getTotalShippingPrice2() > 0)
			{
				count++;
				orderItems.add(new PDFItem("Shipping Price", "SKU", "0", numberFormatter.format(cartOrder.getTotalShippingPrice2()), numberFormatter.format(cartOrder.getTotalShippingPrice2()), logo, "Category name", ""+company.getAddress()+"  "+company.getEmail(), company.getNameEditable(), paymentType, address, cartOrder.getPhoneNumber(), null, cartOrder.getName(), shippingType, cartOrder.getEmailAddress(), ""+cartOrder.getId(), ""+billingDate2));
			}
			
			for(int x=0; x<lines-(count%lines); x++){
				if(x==lines-(count%lines)-1)
				{
					if(cartOrder.getTotalShippingPrice2() > 0)
						orderItems.add(new PDFItem("TOTAL PRICE", null, null,null,numberFormatter.format(cartOrder.getTotalPriceOk()+cartOrder.getTotalShippingPrice2()), null, null, ""+company.getAddress()+"  "+company.getEmail(), null,null,null, null,company.getStatement(),null,null,null,null,null));
					else	
						orderItems.add(new PDFItem("TOTAL PRICE", null, null,null,numberFormatter.format(cartOrder.getTotalPriceOk()), null, null, ""+company.getAddress()+"  "+company.getEmail(), null,null,null, null,company.getStatement(),null,null,null,null,null));
				}
				else
					orderItems.add(new PDFItem(null, null,null, null,null, null, null, ""+company.getAddress()+"  "+company.getEmail(), null,null,null, null,null,null,null,null,null,null));
			}
					
			//place all collected data to the datasource to be used by the jasper as a "datasource"
			setOrderDatasource(new PDFDataSource(orderItems));
			PDFCreator.outputPDF(orderDatasource,templateName,""+company.getName()+"_OrderList_ID_"+cartOrderID+"_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}	
	
	//Generates the report Order List
	public void generateEcommerceOrderReport(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
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
			templateName="ecommerce_order_template.jrxml";
		}
		
		//Try to create a report file
		try {
			//get date
			DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
			
			orderItems = new ArrayList<PDFItem>();
			
			cartOrderID = request.getParameter("cart_order_id");
			
			//lines or rows per page in the PDF
			int lines=23;
			Long orderID = Long.parseLong(cartOrderID);
			CartOrder cartOrder = cartOrderDelegate.find(orderID);
			String billingDate2 = dateFormat.format(cartOrder.getCreatedOn());
			int count=0;
			String address = ""; 
			
			address = "Address 1:          " + cartOrder.getAddress1() + "\n";
			
			if(cartOrder.getAddress2() != null)
				address = address + "Address 2:          " + cartOrder.getAddress2() + "\n"; 
			
			address = address + "City:                    " + cartOrder.getCity() + "\n" + "Zip code:            " + cartOrder.getZipCode();
		
			List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
			
			//generate html string containing list
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			for(CartOrderItem currentItem : itemList){
				if(currentItem.getStatus()==null){
					count++;
					orderItems.add(new PDFItem(currentItem.getItemDetail().getName(), "SKU", ""+currentItem.getQuantity(), numberFormatter.format(currentItem.getItemDetail().getPrice()), numberFormatter.format((currentItem.getItemDetail().getPrice()*currentItem.getQuantity())), logo, "Category name", ""+company.getAddress()+"  "+company.getEmail(), company.getNameEditable(), "", address, cartOrder.getPhoneNumber(), null, cartOrder.getName(), "", cartOrder.getEmailAddress(), ""+cartOrder.getId(), ""+billingDate2));
				}
				else if(currentItem.getStatus().equalsIgnoreCase("OK")){
					count++;
					orderItems.add(new PDFItem(currentItem.getItemDetail().getName(), "SKU", ""+currentItem.getQuantity(), numberFormatter.format(currentItem.getItemDetail().getPrice()), numberFormatter.format((currentItem.getItemDetail().getPrice()*currentItem.getQuantity())), logo, "Category name", ""+company.getAddress()+"  "+company.getEmail(), company.getNameEditable(), "", address, cartOrder.getPhoneNumber(), null, cartOrder.getName(), "", cartOrder.getEmailAddress(), ""+cartOrder.getId(), ""+billingDate2));
				}
			}
			
			if(cartOrder.getTotalShippingPrice2() > 0)
			{
				count++;
				orderItems.add(new PDFItem("Shipping Price", "SKU", "0", numberFormatter.format(cartOrder.getTotalShippingPrice2()), numberFormatter.format(cartOrder.getTotalShippingPrice2()), logo, "Category name", ""+company.getAddress()+"  "+company.getEmail(), company.getNameEditable(), "", address, cartOrder.getPhoneNumber(), null, cartOrder.getName(), "", cartOrder.getEmailAddress(), ""+cartOrder.getId(), ""+billingDate2));
			}
			
			for(int x=0; x<lines-(count%lines); x++){
				if(x==lines-(count%lines)-1)
				{
					if(cartOrder.getTotalShippingPrice2() > 0)
						orderItems.add(new PDFItem("TOTAL PRICE", null, null,null,numberFormatter.format(cartOrder.getTotalPriceOk()+cartOrder.getTotalShippingPrice2()), null, null, ""+company.getAddress()+"  "+company.getEmail(), null,null,null, null,company.getStatement(),null,null,null,null,null));
					else	
						orderItems.add(new PDFItem("TOTAL PRICE", null, null,null,numberFormatter.format(cartOrder.getTotalPriceOk()), null, null, ""+company.getAddress()+"  "+company.getEmail(), null,null,null, null,company.getStatement(),null,null,null,null,null));
				}
				else
					orderItems.add(new PDFItem(null, null,null, null,null, null, null, ""+company.getAddress()+"  "+company.getEmail(), null,null,null, null,null,null,null,null,null,null));
			}
					
			//place all collected data to the datasource to be used by the jasper as a "datasource"
			setOrderDatasource(new PDFDataSource(orderItems));
			PDFCreator.outputPDF(orderDatasource,templateName,""+company.getName()+"_OrderList_ID_"+cartOrderID+"_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}	

	public void setOrderDatasource(PDFDataSource orderDatasource) {
		this.orderDatasource = orderDatasource;
	}
	public PDFDataSource getOrderDatasource() {
		return orderDatasource;
	}
	
	//Generates the report Order List
	public void generateBillingsSummaryReport(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
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
			templateName="all_billings_summary_template.jrxml";
		}
		
		//Try to create a report file
		billingItems = new ArrayList<PDFItem>();
		try {
			request = ServletActionContext.getRequest();
			company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			List<Company> companies = companyDelegate.findAll();
			//Long comapny_id=companies.get(0).getId();
			List<Billing> bills = billingDelegate.findAll(company).getList();
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			/************SORT BY ID************/
			Billing temp;
		    for (int i=1; i < bills.size(); i++) { 
		        for (int j=0; j < bills.size()-i; j++) {
		            if (bills.get(j).getId() < bills.get(j+1).getId()) {
		            	temp = bills.get(j);
		            	bills.set(j, bills.get(j+1));
		            	bills.set(j+1, temp);
		            }
		        }
		    }			

		    /************SORT BY ID************/
			for(Billing bill : bills){
				if(bill.getPayment().size()>0)
					billingItems.add(new PDFItem(""+bill.getId(), ""+bill.getType(),""+sdf.format(bill.getFromDate()), ""+ sdf.format(bill.getToDate()).toString(),numberFormatter.format(Double.parseDouble(""+bill.getAmountDue())), ""+bill.getStatus(),""+company.getAddress()+"  "+company.getEmail(), ""+bill.getPayment().get(0).getId(), ""+bill.getPayment().get(0).getType(), ""+sdf.format(bill.getPayment().get(0).getPaymentDate()),""+bill.getPayment().get(0).getOrNumber(), numberFormatter.format(Double.parseDouble(""+bill.getPayment().get(0).getPaidAmount())),null,null,null,null,logo,company.getNameEditable()));
				else
					billingItems.add(new PDFItem(""+bill.getId(), ""+bill.getType(),""+sdf.format(bill.getFromDate()), ""+sdf.format(bill.getToDate()).toString(),numberFormatter.format(Double.parseDouble(""+bill.getAmountDue())), ""+bill.getStatus(),""+company.getAddress()+"  "+company.getEmail(),"", "","","","",null,null,null,null,logo,company.getNameEditable()));
			}

			setBillingDatasource(new PDFDataSource(billingItems));
			PDFCreator.outputPDF(billingDatasource,templateName,company.getNameEditable().replaceAll(" ","_")+"_Billing_Summary_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	
	
	public void generateBillingLastPayment(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
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
			templateName="latest_billings_template.jrxml";
		}
		
		//Try to create a report file
		billingItems = new ArrayList<PDFItem>();
		try {
			request = ServletActionContext.getRequest();
			company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			List<Company> companies = companyDelegate.filterByStatus(status);
			//Long comapny_id=companies.get(0).getId();
			List<Billing> bills = billingDelegate.findAll(company).getList();
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			
			companies = sortList(companies);
			
			for(Company company : companies){
				if(company.getCompanySettings()!=null)
				if( company.getCompanySettings().getDoNotGenerateBilling()==null || company.getCompanySettings().getDoNotGenerateBilling()!=true){
					bills = billingDelegate.findAll(company).getList();
					if(bills.size()>0){
						int x=bills.size()-1;
						Billing bill;
						do{		
							bill=bills.get(x);
							
							x--;		
						}while(!(""+bill.getStatus()).equals("PAID") && x>=0);
						if((""+bill.getStatus()).equals("PAID"))
							lastPaymentItems.add(new PDFItem(""+bill.getId(), ""+bill.getType(),""+sdf.format(bill.getFromDate()), ""+sdf.format(bill.getToDate()),numberFormatter.format(Double.parseDouble(""+bill.getAmountDue())), ""+bill.getStatus(),""+company.getAddress()+"  "+company.getEmail(), null, null, null,null, null,null,null,null,null,logo,company.getNameEditable()));
					}	
				}
			}
			

			setLastPaymentDatasource(new PDFDataSource(lastPaymentItems));
			PDFCreator.outputPDF(lastPaymentDatasource,templateName,"Latest_Billing_Payments_Summary_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void generateBillingPending(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
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
			templateName="pending_billings_template.jrxml";
		}
		
		//Try to create a report file
		billingItems = new ArrayList<PDFItem>();
		try {
			request = ServletActionContext.getRequest();
			company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			List<Company> companies = companyDelegate.filterByStatus(status);
			//Long comapny_id=companies.get(0).getId();
			List<Billing> bills = billingDelegate.findAll(company).getList();
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			companies = sortList(companies);
			for(Company company : companies){
				int count=0;
				if(company.getCompanySettings()!=null)
				if( company.getCompanySettings().getDoNotGenerateBilling()==null || company.getCompanySettings().getDoNotGenerateBilling()!=true){				
					bills = billingDelegate.findAll(company).getList();
					for(Billing bill : bills){
							if((""+bill.getStatus()).equals("PENDING")){
								pendingItems.add(new PDFItem(""+bill.getId(), ""+bill.getType(),""+sdf.format(bill.getFromDate()), ""+sdf.format(bill.getToDate()),numberFormatter.format(Double.parseDouble(""+bill.getAmountDue())), ""+bill.getStatus(),""+company.getAddress()+"  "+company.getEmail(), null, null, null,null, null,null,null,null,null,logo,company.getNameEditable()));
								count++;
							}
					}
				}
			}
			

			setPendingDatasource(new PDFDataSource(pendingItems));
			PDFCreator.outputPDF(pendingDatasource,templateName,"Pending_Billing_Payments_Summary_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void generateBillingCancelled(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
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
			templateName="cancelled_billings_template.jrxml";
		}
		
		//Try to create a report file
		billingItems = new ArrayList<PDFItem>();
		try {
			request = ServletActionContext.getRequest();
			company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			List<Company> companies = companyDelegate.filterByStatus(status);
			List<Billing> bills = billingDelegate.findAll(company).getList();
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			companies = sortList(companies);
			pendingItems = new ArrayList<PDFItem>();
			for(Company company : companies){
				int count=0;
				if(company.getCompanySettings()!=null)
				if( company.getCompanySettings().getDoNotGenerateBilling()==null || company.getCompanySettings().getDoNotGenerateBilling()!=true){				
					bills = billingDelegate.findAll(company).getList();
					for(Billing bill : bills){
							if((""+bill.getStatus()).equals("CANCELLED")){
								pendingItems.add(new PDFItem(""+bill.getId(), ""+bill.getType(),""+sdf.format(bill.getFromDate()), ""+sdf.format(bill.getToDate()),numberFormatter.format(Double.parseDouble(""+bill.getAmountDue())), ""+bill.getStatus(),""+company.getAddress()+"  "+company.getEmail(), null, null, null,null, null,null,null,null,null,logo,company.getNameEditable()));
								count++;
							}
					}
				}
			}
			

			setPendingDatasource(new PDFDataSource(pendingItems));
			PDFCreator.outputPDF(pendingDatasource,templateName,"Cancelled_Billing_Payments_Summary_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void generateCompaniesDirectory(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
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
			templateName="company_directory_template.jrxml";
		}
		
		//Try to create a report file
		billingItems = new ArrayList<PDFItem>();
		try {
			request = ServletActionContext.getRequest();
			company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			List<Company> companies = companyDelegate.filterByStatus(status);
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			companies = sortList(companies);
			for(Company company : companies){
				if(company.getCompanySettings()!=null)			
					directoryItems.add(new PDFItem(company.getAddress(), company.getPhone(),company.getContactPerson(), company.getServerName(), numberFormatter.format(Double.parseDouble(""+company.getCompanySettings().getMonthlyCharge())), null, null, null,null, null,null,null,null,null,null,company.getCompanySettings().getCompanyStatus().name(),logo,company.getNameEditable())); 
			}
			

			setDirectoryDatasource(new PDFDataSource(directoryItems));
			PDFCreator.outputPDF(directoryDatasource,templateName,"Companies_Directory_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void generateRequestForm(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
		
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
			templateName="website_service_request_form_template.jrxml";
		}
		
		//Try to create a report file
		billingItems = new ArrayList<PDFItem>();
		try {
			request = ServletActionContext.getRequest();
			company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			List<Company> companies = companyDelegate.findAll();
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			companies = sortList(companies);
			directoryItems = new ArrayList<PDFItem>();
			
			List<Activity> activities = new ArrayList<Activity>();;
			if (request.getParameter("filter").equals("all")){
				activities = activityDelegate.findAll(company).getList();
			}
			else{
				// gets parameters submitted from activities.jsp
				List<Activity> activities2 = activityDelegate.findAll(company).getList();
				String fromDay = request.getParameter("byDay");
				String fromMonth = request.getParameter("byMonth");
				String fromYear = request.getParameter("byYear");
				String endDay = ""+(Integer.parseInt(request.getParameter("endDay")));
				String endMonth = request.getParameter("endMonth");
				String endYear = request.getParameter("endYear");
				Calendar calendar = Calendar.getInstance();
		        calendar.set(1, Integer.parseInt(endMonth), Integer.parseInt(endYear));

		        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		        if(Integer.parseInt(endDay)>maxDay)
		        	endDay=""+maxDay;
		        
		        //this is to fix the b
				if(Integer.parseInt(endMonth)==12 && Integer.parseInt(endDay)>=maxDay){
					endMonth = "0"+1;
					endDay = "0"+1;
					endYear = ""+(Integer.parseInt(endYear) +1);
				}       
				else
		        endDay = ""+(Integer.parseInt(endDay+1)%maxDay);    
		        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy"); 
		        String endDate = endMonth+"-"+endDay+"-"+endYear;
				String fromDate = fromMonth+"-"+fromDay+"-"+fromYear;
		        Date fDate = (Date)df.parse(fromDate);
		        Date tDate = (Date)df.parse(endDate);
		        
			        for(Activity activity : activities2){
			        	if(fDate.equals((Date)df.parse(activity.getRequestDate())) || (((Date) df.parse(activity.getRequestDate())).after(fDate) && ((Date) df.parse(activity.getRequestDate())).before(tDate)))
			        		activities.add(activity);
			        }
		   		}
			
			Activity temp;
			/************SORT BY ID************/
		    for (int i=1; i < activities.size(); i++) { 
		        for (int j=0; j < activities.size()-i; j++) {
		            if (activities.get(j).getId() < activities.get(j+1).getId()) {
		            	temp = activities.get(j);
		            	activities.set(j, activities.get(j+1));
		            	activities.set(j+1, temp);
		            }
		        }
		    }			
			/************SORT BY ID************/
			
			
			
			for(Activity activity : activities){
				directoryItems.add(new PDFItem(company.getNameEditable(), company.getContactPerson(),company.getPhone(), company.getDomainName(), company.getContactPersonDesignation(), company.getEmail(), ""+activity.getId(), ""+activity.getRequestDetails(),""+activity.getRemarks(), ""+activity.getActionTaken(),""+activity.getRequestDate(),""+activity.getDescription(),""+activity.getDesignIteration(),""+activity.getStatus(),activity.getType(),null,logo,null));
			}

			setDirectoryDatasource(new PDFDataSource(directoryItems));
			PDFCreator.outputPDF(directoryDatasource,templateName,company.getNameEditable().replace(" ", "_")+"_Website_Service_Request_Fomr_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void generateCertificate(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
		
		String logo = PDFCreator.getCertificateLogo();

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
			templateName="certificate.jrxml";
		}
		
		//Try to create a report file
		billingItems = new ArrayList<PDFItem>();
		try {
			request = ServletActionContext.getRequest();
			company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			List<Company> companies = companyDelegate.findAll();
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormatter.setMinimumFractionDigits(2);
			companies = sortList(companies);
			directoryItems = new ArrayList<PDFItem>();

			Member member = memberDelegate.find(Long.parseLong(request.getParameter("id")));
			//PCOMember pcoMember = pcoMemberDelegate.find(request.getParameter("id").toString());
			Event event = eventDelegate.find(Long.parseLong(request.getParameter("event_id")));
			String firstName = member.getFirstname().trim().substring(0,1).toUpperCase() + member.getFirstname().trim().substring(1).toLowerCase();
			String lastName = member.getLastname().trim().substring(0,1).toUpperCase() + member.getLastname().trim().substring(1).toLowerCase(); 
			String name = ""; //firstName + " " + pcoMember.getMiddlename().toUpperCase().charAt(0) + ". " + lastName + ", " + pcoMember.getSuffix().toUpperCase();
			Calendar eventDate = Calendar.getInstance();
			eventDate.setTime(event.getEventDate());
			String strEventDate = "Given this " + numberSuffix(eventDate.get(Calendar.DAY_OF_MONTH)) + " day of " + new SimpleDateFormat("MMMM").format(event.getEventDate());
			String strYear = "in the year of our Lord Two Thousand and " + convert(eventDate.get(Calendar.YEAR)-2000);
			directoryItems.add(new PDFItem(name, event.getTitle(),strEventDate, strYear, "", "", "", "","", "","","","","","","",logo,""));
			
			setDirectoryDatasource(new PDFDataSource(directoryItems));
			PDFCreator.outputPDF(directoryDatasource,templateName,company.getNameEditable().replace(" ", "_")+"_Website_Service_Request_Fomr_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings("rawtypes")
	public void generateSalesKit(){
		request = ServletActionContext.getRequest();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
		String categoryId = request.getParameter("category");
		String rootCategory = request.getParameter("root");
		String logo = PDFCreator.getSpherePDFLogo(rootCategory);

		DecimalFormat df = new DecimalFormat("###,###.00");
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
			templateName="sphere_sales_kit.jrxml";
		}
		
		//Try to create a report file
		billingItems = new ArrayList<PDFItem>();
		try {
			request = ServletActionContext.getRequest();
			company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			List<Company> companies = companyDelegate.findAll();
			
			directoryItems = new ArrayList<PDFItem>();
			GroupDelegate groupDelegate = new GroupDelegate();
			final String GROUP1 = "Product Group 1";
			final String GROUP2 = "Product Group 2";
			String productImage1 = "";
			String productName1 = "";
			//String productCode1;
			String productPrice1 = "";
			
			String productImage2 = "";
			String productName2 = "";
			//String productCode2;
			String productPrice2 = "";
			
			String productImage3 = "";
			String productName3 = "";
			//String productCode3;
			String productPrice3 = "";
			
			String productImage4 = "";
			String productName4 = "";
			//String productCode3;
			String productPrice4 = "";
			
			String categoryName;
			for(Company company: companies){
				if(company.getId() == CompanyConstants.SPHERE){
					List<Group> tempGroup = groupDelegate.findAll(company).getList();
					for(Group group : tempGroup){
						if(group.getName().equals(GROUP1) || group.getName().equals(GROUP2)){
							for(Category category : group.getCategories()) {
								for(Category childCategory : category.getChildrenCategory()){
									if(categoryId != null){
										if(childCategory.getId().toString().equals(categoryId)){
											for(int i = 0 ; i < childCategory.getItems().size() ; i++){
												CategoryItem item = new CategoryItem();
												
												productImage1 = "";productName1 = "";productPrice1 = "";
												productImage2 = "";productName2 = "";productPrice2 = "";
												productImage3 = "";productName3 = "";productPrice3 = "";
												productImage4 = "";productName4 = "";productPrice4 = "";
												
												if(i % 4 == 0){
													item = childCategory.getItems().get(i);
													productImage1 = PDFCreator.getCompanyItemImage(item.getId(), "thumbnail");
													productName1 = item.getName();
													//productCode1 = item.getSku();
													productPrice1 = df.format(item.getPrice());
													
													if((i + 1) < childCategory.getItems().size()){
														item = childCategory.getItems().get(i + 1);
														productImage2 = PDFCreator.getCompanyItemImage(item.getId(), "thumbnail");
														productName2 = item.getName();
														//productCode2 = item.getSku();
														productPrice2 = df.format(item.getPrice());
													}
													if((i + 2) < childCategory.getItems().size()){
														item = childCategory.getItems().get(i + 2);
														productImage3 = PDFCreator.getCompanyItemImage(item.getId(), "thumbnail");
														productName3 = item.getName();
														//productCode3 = item.getSku();
														productPrice3 = df.format(item.getPrice());
													}
													if((i + 3) < childCategory.getItems().size()){
														item = childCategory.getItems().get(i + 3);
														productImage4 = PDFCreator.getCompanyItemImage(item.getId(), "thumbnail");
														productName4 = item.getName();
														//productCode3 = item.getSku();
														productPrice4 = df.format(item.getPrice());
													}
													
													categoryName = childCategory.getName() + " WATCHES";
													directoryItems.add(new PDFItem(productImage1, "", productName1, "", productPrice1, logo, categoryName.toUpperCase(), productImage2, productName2, productPrice2, productImage3, productName3, productPrice3, productImage4, productName4, productPrice4, "", ""));
												}
												
											}
										}
									}else{
										for(int i = 0 ; i < childCategory.getItems().size() ; i++){
											CategoryItem item = new CategoryItem();
											
											productImage1 = "";productName1 = "";productPrice1 = "";
											productImage2 = "";productName2 = "";productPrice2 = "";
											productImage3 = "";productName3 = "";productPrice3 = "";
											productImage4 = "";productName4 = "";productPrice4 = "";
											
											if(i % 3 == 0){
												item = childCategory.getItems().get(i);
												productImage1 = PDFCreator.getCompanyItemImage(item.getId(), "thumbnail");
												productName1 = item.getName();
												//productCode1 = item.getSku();
												productPrice1 = df.format(item.getPrice());
												
												if((i + 1) < childCategory.getItems().size()){
													item = childCategory.getItems().get(i + 1);
													productImage2 = PDFCreator.getCompanyItemImage(item.getId(), "thumbnail");
													productName2 = item.getName();
													//productCode2 = item.getSku();
													productPrice2 = df.format(item.getPrice());
												}
												if((i + 2) < childCategory.getItems().size()){
													item = childCategory.getItems().get(i + 2);
													productImage3 = PDFCreator.getCompanyItemImage(item.getId(), "thumbnail");
													productName3 = item.getName();
													//productCode3 = item.getSku();
													productPrice3 = df.format(item.getPrice());
												}
												if((i + 3) < childCategory.getItems().size()){
													item = childCategory.getItems().get(i + 3);
													productImage4 = PDFCreator.getCompanyItemImage(item.getId(), "thumbnail");
													productName4 = item.getName();
													//productCode3 = item.getSku();
													productPrice4 = df.format(item.getPrice());
												}
												
												directoryItems.add(new PDFItem(productImage1, "", productName1, "", productPrice1, logo, "ALL PRODUCT SALES KIT", productImage2, productName2, productPrice2, productImage3, productName3, productPrice3, productImage4, productName4, productPrice4, "", ""));
											}
										}
									}
									
								}
								
							}
						}
					}
				}
			}
			
			setDirectoryDatasource(new PDFDataSource(directoryItems));
			PDFCreator.outputPDF(directoryDatasource,templateName,company.getNameEditable().replace(" ", "_")+"_Sales_Kit_"+sdf.format(date.getTime())+".pdf");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String numberSuffix(int num)
	{
		int numLength = String.valueOf(num).trim().length(); 
		
		if(numLength == 1)
		{
			switch(num)
			{
			case 1: return num + "st"; 
			case 2: return num + "nd"; 
			case 3: return num + "rd"; 
			case 4: case 5: case 6: case 7: case 8: case 9: return num + "th"; 
			}
		}
		else if(numLength == 2)
		{
			Character numChar1 = String.valueOf(num).trim().charAt(0);
			Character numChar2 = String.valueOf(num).trim().charAt(1);
			if(numChar1 == '1')
			{
				return num + "th";
			}
			else
			{
				switch(Integer.parseInt(numChar2.toString()))
				{
				case 1: return num + "st"; 
				case 2: return num + "nd"; 
				case 3: return num + "rd"; 
				case 4: case 5: case 6: case 7: case 8: case 9: case 0: return num + "th"; 
				}
			}
		}
		return "";
	}
	
	final private String[] units = {"Zero","One","Two","Three","Four",
		"Five","Six","Seven","Eight","Nine","Ten",
		"Eleven","Twelve","Thirteen","Fourteen","Fifteen",
		"Sixteen","Seventeen","Eighteen","Nineteen"};
	final private String[] tens = {"","","Twenty","Thirty","Forty","Fifty",
		"Sixty","Seventy","Eighty","Ninety"};

	public String convert(Integer i) {
		//
		if( i < 20)  return units[i];
		if( i < 100) return tens[i/10] + ((i % 10 > 0)? " " + convert(i % 10):"");
		if( i < 1000) return units[i/100] + " Hundred" + ((i % 100 > 0)?" and " + convert(i % 100):"");
		if( i < 1000000) return convert(i / 1000) + " Thousand " + ((i % 1000 > 0)? " " + convert(i % 1000):"") ;
		return convert(i / 1000000) + " Million " + ((i % 1000000 > 0)? " " + convert(i % 1000000):"") ;
		}
	
	/* 	
	 * returns an alphabetically-sorted list of the categoriesList
	 * uses the Bubble Sort Implementation
	*/
	public List<Company> sortList(List<Company> toBeSorted) {
		Company temp;
	    for (int i=1; i < toBeSorted.size(); i++) { 
	        for (int j=0; j < toBeSorted.size()-i; j++) {
	            if (0 < (""+toBeSorted.get(j).getNameEditable()).compareTo(""+toBeSorted.get(j+1).getNameEditable())) {
	            	temp = toBeSorted.get(j);
	            	toBeSorted.set(j, toBeSorted.get(j+1));
	            	toBeSorted.set(j+1, temp);
	            }
	        }
	    }
		return toBeSorted;
	}
	
	public void setBillingDatasource(PDFDataSource billingDatasource) {
		this.billingDatasource = billingDatasource;
	}
	
	public void setNissanDatasource(PDFDataSource nissanDatasource) {
		this.nissanDatasource = nissanDatasource;
	}
	
	public void setElcDatasource(PDFDataSource elcDatasource) {
		this.elcDatasource = elcDatasource;
	}

	public PDFDataSource getBillingDatasource() {
		return billingDatasource;
	}

	public void setLastPaymentDatasource(PDFDataSource lastPaymentDatasource) {
		this.lastPaymentDatasource = lastPaymentDatasource;
	}

	public PDFDataSource getLastPaymentDatasource() {
		return lastPaymentDatasource;
	}

	public void setPendingDatasource(PDFDataSource pendingDatasource) {
		this.pendingDatasource = pendingDatasource;
	}

	public PDFDataSource getPendingDatasource() {
		return pendingDatasource;
	}

	public void setLastPaymentItems(List<PDFItem> lastPaymentItems) {
		this.lastPaymentItems = lastPaymentItems;
	}

	public List<PDFItem> getLastPaymentItems() {
		return lastPaymentItems;
	}

	public void setPendingItems(List<PDFItem> pendingItems) {
		this.pendingItems = pendingItems;
	}

	public List<PDFItem> getPendingItems() {
		return pendingItems;
	}

	public void setDirectoryDatasource(PDFDataSource directoryDatasource) {
		this.directoryDatasource = directoryDatasource;
	}

	public PDFDataSource getDirectoryDatasource() {
		return directoryDatasource;
	}

	public void setDirectoryItems(List<PDFItem> directoryItems) {
		this.directoryItems = directoryItems;
	}

	public List<PDFItem> getDirectoryItems() {
		return directoryItems;
	}
}
