package com.ivant.cms.action.admin;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.RatesDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Rates;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.helper.DateHelper;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import com.opensymphony.xwork2.Preparable;

public class TrustProductStatisticsAction extends ActionSupport 
implements Preparable, ServletRequestAware, CompanyAware, UserAware { 
	
	private static Logger logger = LoggerFactory.getLogger(TrustProductStatisticsAction.class);
	private static final long serialVersionUID = 3617798953897716137L;

	private final static int ITEMS_PER_PAGE = 20;
	
	/** Object responsible for rates for Robinsonsbank */
	private RatesDelegate ratesDelegate = RatesDelegate.getInstance();
	private UserDelegate userDelegate = UserDelegate.getInstance();	
	
	/** Current webtogo admin user, must not be null*/
	@SuppressWarnings("unused")
	private User user;
	
	/** Data that is passed through the session */
	private HttpServletRequest request;
	
	/** Current session webtogo company, must not be null*/
	private Company company;
	
	
	/** Current page number for paging, can be null*/
	private Integer pageNumber;
	
	/** List of rates items by the company, can be 0 or more*/
	private List<Rates> rates;
	
	private Rates rate;
	private String itemDate;
	private Date iDate;
	
	public String blueFundXML = "";
	public String greenFundXML = "";
	public String balancedFundXML = "";
	public String moneyMarketFundXML = "";
	public String buyXML = "";
	public String sellXML = "";
	public String strXML = "";
	
	private String[] currencies = {"USD", "CNY", "EUR", "GBP", "JPY", "SGD", "HKD"};
	
	@Override
	public void prepare() throws Exception {
		//initialize paging
		//setPageNumber();
		
		//setPaging(itemCount.intValue(), ITEMS_PER_PAGE);
		
		setBlueFundXML();
		setGreenFundXML();
		setBalancedFundXML();
		setMoneyMarketFundXML();
		setBuyXML();
		setSellXML();
	}
	
	@Override
	public String execute() throws Exception {
		logger.info("Executing TrustProductStatisticsAction .......");
		rates = ratesDelegate.getParentFund(company).getList();

		setBlueFundXML();
		request.setAttribute("blueFundXML", blueFundXML);
		setGreenFundXML();
		request.setAttribute("greenFundXML", greenFundXML);
		setBalancedFundXML();
		request.setAttribute("balancedFundXML", balancedFundXML);
		setMoneyMarketFundXML();
		request.setAttribute("moneyMarketFundXML", moneyMarketFundXML);
		setBuyXML();
		request.setAttribute("buyXML", buyXML);
		setSellXML();
		request.setAttribute("sellXML", sellXML);
		
		
		if(request.getParameter("id") != null){
			String ratename = request.getParameter("id");
			
			if(ratename.equalsIgnoreCase("1")){
				Rates blue = ratesDelegate.getParentById(Long.parseLong("1"));
				Rates green = ratesDelegate.getParentById(Long.parseLong("2"));
				Rates balanced = ratesDelegate.getParentById(Long.parseLong("3"));
				Rates moneyMarket = ratesDelegate.getParentById(Long.parseLong("12939"));
				
				List<Rates> parents = new ArrayList<Rates>();
				parents.add(blue);
				parents.add(green);
				parents.add(balanced);
				parents.add(moneyMarket);
				
				Order[] order = new Order[2];
				order[0] = Order.desc("createdOn");
				order[1] = Order.asc("parent");
				
				List<Rates> s = ratesDelegate.getMultipleParentRates(parents, order).getList();
				
				request.setAttribute("parentStatistics", "Trust Products");
				request.setAttribute("statistics", s);
			}
			else if(ratename.equalsIgnoreCase("2"))
			{		
				Rates buy = ratesDelegate.getParentById(Long.parseLong("4"));
				Rates sell = ratesDelegate.getParentById(Long.parseLong("5"));
				
				List<Rates> buyUSD = ratesDelegate.getRatesByName("USD", buy);
				List<Rates> sellUSD = ratesDelegate.getRatesByName("USD", sell);
				List<Rates> buyCNY = ratesDelegate.getRatesByName("CNY", buy);
				List<Rates> sellCNY = ratesDelegate.getRatesByName("CNY", sell);
				List<Rates> buyEUR = ratesDelegate.getRatesByName("EUR", buy);
				List<Rates> sellEUR = ratesDelegate.getRatesByName("EUR", sell);
				List<Rates> buyGBP = ratesDelegate.getRatesByName("GBP", buy);
				List<Rates> sellGBP = ratesDelegate.getRatesByName("GBP", sell);
				List<Rates> buyJPY = ratesDelegate.getRatesByName("JPY", buy);
				List<Rates> sellJPY = ratesDelegate.getRatesByName("JPY", sell);
				List<Rates> buySGD = ratesDelegate.getRatesByName("SGD", buy);
				List<Rates> sellSGD = ratesDelegate.getRatesByName("SGD", sell);
				List<Rates> buyHKD = ratesDelegate.getRatesByName("HKD", buy);
				List<Rates> sellHKD = ratesDelegate.getRatesByName("HKD", sell);
				
				List<Object> buyValues = new ArrayList<Object>();
				List<Object> sellValues = new ArrayList<Object>();
				
				List<Double> latestBuyValues = new ArrayList<Double>();
				List<Double> latestSellValues = new ArrayList<Double>();
				List<Rates> rates;
								
				for(int i=0; i<buyUSD.size() && i<sellUSD.size(); i++) 
				{
					rates = new ArrayList<Rates>();					
					rates.add((buyUSD.get(i)==null)? null: buyUSD.get(i));
					
					if(i<buyCNY.size()) {
						rates.add((buyCNY.get(i)==null)? null: buyCNY.get(i));
						rates.add((buyEUR.get(i)==null)? null: buyEUR.get(i));
						rates.add((buyGBP.get(i)==null)? null: buyGBP.get(i));
						rates.add((buyJPY.get(i)==null)? null: buyJPY.get(i));
						rates.add((buySGD.get(i)==null)? null: buySGD.get(i));
						rates.add((buyHKD.get(i)==null)? null: buyHKD.get(i));	
					}
					buyValues.add(rates);
					
					rates = new ArrayList<Rates>();					
					rates.add((sellUSD.get(i)==null)? null: sellUSD.get(i));
					
					if(i<sellCNY.size()) {
						rates.add((sellCNY.get(i)==null)? null: sellCNY.get(i));
						rates.add((sellEUR.get(i)==null)? null: sellEUR.get(i));
						rates.add((sellGBP.get(i)==null)? null: sellGBP.get(i));
						rates.add((sellJPY.get(i)==null)? null: sellJPY.get(i));
						rates.add((sellSGD.get(i)==null)? null: sellSGD.get(i));
						rates.add((sellHKD.get(i)==null)? null: sellHKD.get(i));	
					}
					sellValues.add(rates);							
				}
				
				if(buyCNY.size()>0) {
					latestBuyValues.add((buyUSD.get(0)==null)? null: buyUSD.get(0).getValue());
					latestBuyValues.add((buyCNY.get(0)==null)? null: buyCNY.get(0).getValue());
					latestBuyValues.add((buyEUR.get(0)==null)? null: buyEUR.get(0).getValue());
					latestBuyValues.add((buyGBP.get(0)==null)? null: buyGBP.get(0).getValue());
					latestBuyValues.add((buyJPY.get(0)==null)? null: buyJPY.get(0).getValue());
					latestBuyValues.add((buySGD.get(0)==null)? null: buySGD.get(0).getValue());
					latestBuyValues.add((buyHKD.get(0)==null)? null: buyHKD.get(0).getValue());
					
					latestSellValues.add((sellUSD.get(0)==null)? null: sellUSD.get(0).getValue());
					latestSellValues.add((sellCNY.get(0)==null)? null: sellCNY.get(0).getValue());
					latestSellValues.add((sellEUR.get(0)==null)? null: sellEUR.get(0).getValue());
					latestSellValues.add((sellGBP.get(0)==null)? null: sellGBP.get(0).getValue());
					latestSellValues.add((sellJPY.get(0)==null)? null: sellJPY.get(0).getValue());
					latestSellValues.add((sellSGD.get(0)==null)? null: sellSGD.get(0).getValue());
					latestSellValues.add((sellHKD.get(0)==null)? null: sellHKD.get(0).getValue());	
				}
				
				request.setAttribute("latestBuyValues", latestBuyValues);
				request.setAttribute("latestSellValues", latestSellValues);
				
				request.setAttribute("buyValues", buyValues);
				request.setAttribute("sellValues", sellValues);
				
				request.setAttribute("parentStatistics", "Foreign Exchange");
				request.setAttribute("statistics", buyUSD);
			}
		}
		
		return SUCCESS;
	}
	
	public void setXML(long id){
		strXML = "";
		
		if(id == 1)
			strXML = blueFundXML;
		else if(id == 2)
			strXML = greenFundXML;
		else if(id == 3)
			strXML = balancedFundXML;
		else if(id == 4)
			strXML = buyXML;
		else if(id == 5)
			strXML = sellXML;
		else if(id == 12939)
			strXML = moneyMarketFundXML;
		request.setAttribute("strXML", strXML);
	}
	
	/**
	 * Parses specified request specified page number to local variable {@code pageNumber}.
	 * Returns the parsed specified page number if successful, otherwise 1.
	 * 
	 * @return - the parsed specified page number if successful, otherwise 1.
	 */
	public int getPageNumber() {
		//get the specified page number
		String page = request.getParameter("page");
 		try{
 			//parse specified page
			pageNumber = Integer.parseInt(page);
		} catch(Exception e){
			//log unparsed value and return to 1st page
			logger.info("Cannot parse {} as page number" , page);
			pageNumber = 1;
			return pageNumber;
		}
		return pageNumber;
	}
	
	/**
	 * Loads the items based on the specified items per page.
	 * 
	 * @param itemSize 
	 * 			- total number of items
	 * @param itemsPerPage 
	 * 			- number of items shown per page
	 */
	public void setPaging(int itemSize, int itemsPerPage) {
		request.setAttribute("pagingUtil", new PagingUtil(itemSize, itemsPerPage, pageNumber, (itemSize/itemsPerPage)));
	}
	
	/**
	 * Returns the request property value.
	 *
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Sets the request property value with the specified request.
	 *
	 * @param request the request to set
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Sets the user property value with the specified user.
	 *
	 * @param user the user to set
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Sets the company property value with the specified company.
	 *
	 * @param company the company to set
	 */
	@Override
	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Rates> getRates() {
		return rates;
	}
	
	public String delete() {
		if(company == null) {
			return Action.ERROR;
		}
		
		if(request.getParameter("r1") != null){
			int count=1;		
			while(request.getParameter("r"+count) != null) {
				long statID = Long.parseLong(request.getParameter("r"+count));
				Rates deleteRate = ratesDelegate.getRateById(statID);			
				ratesDelegate.delete(deleteRate);
				count++;
			}
			return Action.SUCCESS;		
		}
		
		if(request.getParameter("rate_id3") != null){
			long statID = Long.parseLong(request.getParameter("rate_id3"));
			Rates deleteRate = ratesDelegate.getRateById(statID);
			
			ratesDelegate.delete(deleteRate);
		}
		
		if(request.getParameter("rate_id2") != null){
			long statID = Long.parseLong(request.getParameter("rate_id2"));
			Rates deleteRate = ratesDelegate.getRateById(statID);
			
			ratesDelegate.delete(deleteRate);
			
		}
		
		if(request.getParameter("rate_id") != null){
			long statID = Long.parseLong(request.getParameter("rate_id"));
			Rates deleteRate = ratesDelegate.getRateById(statID);
			
			logger.debug(""+deleteRate.getParent().getName()+" - "+deleteRate.getValue());
			
			ratesDelegate.delete(deleteRate);
			return Action.SUCCESS;		
		}
		
		return Action.ERROR;			
	}
	
	public String save() {
		if(company == null) {
		return Action.ERROR;
		}
		String ratename = (String) request.getParameter("statname");

		if(ratename != null){
			logger.debug("\n\n"+ratename);
			if(ratesDelegate.find(company, ratename) == null) {
				rate = new Rates();
				rate.setName(ratename);
				rate.setCompany(company);
				rate.setCreatedOn(new Date());
				logger.debug("\n\n\n\nDATE CREATED: "+rate.getCreatedOn());
				rate.setIsValid(true);
				rate.setUpdatedOn(new Date());
				rate.setValue(0);
		
				ratesDelegate.insert(rate);
			}
		}

		return Action.SUCCESS;
	}
	
	private boolean sendEmail(String emailcontent, String subject) {
		boolean emailSent = false;
		StringBuffer content = new StringBuffer();

		try {			
			//String subject = request.getParameter("subject");
			String to = request.getParameter("to");
			String from = request.getParameter("from");
			String title = request.getParameter("title");

			content = content.append("\n" + title + "\n");
			content = content
					.append("--------------------------------------------------\n\n");

			content.append(emailcontent);
			
			EmailUtil.connectViaCompanySettings(company);
			
			logger.debug("\n\n\nCONTENT: "+content.toString()+"\n\n");
			emailSent = EmailUtil.send(from, to.split(","), subject, content.toString(), null);
			
		} catch (Exception e) {
			logger.debug("failed to send email in " + company.getName()
					+ "... " + e);
		}

		return emailSent;
	}
	
	public String addTrustProducts(){
		double bluestatvalue;
		double greenstatvalue;
		double balancedstatvalue;
		double moneymarketstatvalue;
		String subject = "NAVPU Updates";
		StringBuffer content = new StringBuffer();
		
		if(company == null) {
			return Action.ERROR;
		}
		
		Date dateCreated = new Date();
		
		content = content.append("\nTrust Products");
		
		
		if(request.getParameter("bluestatvalue") != null){
			bluestatvalue = Double.parseDouble(request.getParameter("bluestatvalue"));
			rate = new Rates();
			rate.setCompany(company);
			rate.setCreatedOn(dateCreated);
			rate.setIsValid(true);
			rate.setUpdatedOn(dateCreated);
			rate.setUser(user);
			rate.setValue(bluestatvalue);
			add(rate, (long) 1);
			
			//content = content.append("\nBlue Fund: "+rate.getValue());
		}
		
		if(request.getParameter("greenstatvalue") != null){
			greenstatvalue = Double.parseDouble(request.getParameter("greenstatvalue"));
			rate = new Rates();
			rate.setCompany(company);
			rate.setCreatedOn(dateCreated);
			rate.setIsValid(true);
			rate.setUpdatedOn(dateCreated);
			rate.setUser(user);
			rate.setValue(greenstatvalue);
			add(rate, (long) 2);
			
			//content = content.append("\nGreen Fund: "+rate.getValue());
		}
		
		if(request.getParameter("balancedstatvalue") != null){
			balancedstatvalue = Double.parseDouble(request.getParameter("balancedstatvalue"));
			rate = new Rates();
			rate.setCompany(company);
			rate.setCreatedOn(dateCreated);
			rate.setIsValid(true);
			rate.setUpdatedOn(dateCreated);
			rate.setUser(user);
			rate.setValue(balancedstatvalue);
			add(rate, (long) 3);
			
			content = content.append("\nBalanced Fund: "+rate.getValue());
			
		}
		
		if(request.getParameter("moneymarketstatvalue") != null){
			moneymarketstatvalue = Double.parseDouble(request.getParameter("moneymarketstatvalue"));
			rate = new Rates();
			rate.setCompany(company);
			rate.setCreatedOn(dateCreated);
			rate.setIsValid(true);
			rate.setUpdatedOn(dateCreated);
			rate.setUser(user);
			rate.setValue(moneymarketstatvalue);
			add(rate, (long) 12939);
			
			content = content.append("\nMoney Market Fund: "+rate.getValue());
			

			content = content.append("\n\nUpdated By: "+rate.getUser().getFirstname()+" "+rate.getUser().getLastname());
			content = content.append("\nDate Created: "+rate.getCreatedOn());
		}
		
		sendEmail(content.toString(), subject);
		
		return Action.SUCCESS;
	}
	
	public String addForeignExchange()
	{
		Double buystatvalue;
		Double sellstatvalue;
		String subject = "Foreign Exchange Rate";
		StringBuffer content = new StringBuffer();
		
		if(company == null) {
			return Action.ERROR;
		}
		
		Date dateCreated = new Date();
		
		for(int i=0; i<currencies.length; i++) 
		{
			content = content.append(currencies[i]+" Buy and Sell");
			
			//BUY
			if(request.getParameter("buy"+currencies[i]) != null){
				buystatvalue = Double.parseDouble(request.getParameter("buy"+currencies[i]));
				rate = new Rates();
				rate.setName(currencies[i]);
				rate.setCompany(company);
				rate.setDate(new Date());
				rate.setCreatedOn(dateCreated);
				rate.setIsValid(true);
				rate.setUpdatedOn(dateCreated);
				rate.setUser(user);
				rate.setValue(buystatvalue);
				add(rate, (long) 4);
				
				content = content.append("\nBuy: "+rate.getValue());
			}
			
			//SALE
			if(request.getParameter("sell"+currencies[i]) != null){
				sellstatvalue = Double.parseDouble(request.getParameter("sell"+currencies[i]));
				rate = new Rates();
				rate.setName(currencies[i]);
				rate.setCompany(company);
				rate.setDate(new Date());
				rate.setCreatedOn(dateCreated);
				rate.setIsValid(true);
				rate.setUpdatedOn(dateCreated);
				rate.setUser(user);
				rate.setValue(sellstatvalue);
				add(rate, (long) 5);
				
				content = content.append("\nSell: "+rate.getValue()+"\n\n");				
			}
		}
		
		content = content.append("\nUpdated By: "+rate.getUser().getFirstname()+" "+rate.getUser().getLastname());		
		content = content.append("\nDate Created: "+rate.getCreatedOn());		
		
		sendEmail(content.toString(), subject);
		
		return Action.SUCCESS;
	}
	
	
	public void add(Rates rate, Long parentID) {
		Rates parent = ratesDelegate.getParentById(parentID);
		
		rate.setParent(parent);		
		Long newid = ratesDelegate.insert(rate);

		logger.debug("\nINSERTED: ID = "+newid+" NAME = "+rate.getName()+"\n");
	}
	
	public void setXML(int id){
		String strXML = "";
		
		switch(id){
			case 1: strXML = blueFundXML; break;
			case 2: strXML = greenFundXML; break;
			case 3: strXML = balancedFundXML; break;
			case 12939: strXML = moneyMarketFundXML; break;
		}
			
		request.setAttribute("strXML", strXML);
	}
	
	public String setBlueFundXML(){
		
		long statID = 1;
		
		Rates parent = ratesDelegate.getParentById(statID);
		List<Rates> bf = ratesDelegate.getRatesByParentWithPaging(parent, 30, 0, Order.desc("createdOn")).getList();
		double min = 0, max = 0;
		blueFundXML = "";
		//blueFundXML = "<chart caption='Trust Product' subCaption='Blue Fund' numdivlines='9' lineThickness='2' showValues='1' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1'><categories>";
		String chartDesc = "<chart caption='RobinsonsBank Trust Product' decimals='2' numdivlines='9' lineThickness='2' showValues='0' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1' ";
				
		//populate categories
		for(int i=bf.size()-1; i >= 0 ; i--){
			//blueFundXML = blueFundXML + "<category label='"+bf.get(i).getDate().toString().substring(0, 11)+"'/>";
			blueFundXML = blueFundXML + "<category label='"+bf.get(i).getCreatedOn().toString().substring(0, 11)+"'/>";
		}
		
		blueFundXML = blueFundXML + "</categories><dataset seriesName='Blue Fund NAVPU' color='0080C0' anchorBorderColor='0080C0'>";
		//populate values
		min = bf.get(0).getValue();
		max = bf.get(0).getValue();
		for(int i=bf.size()-1; i >= 0 ; i--){
			blueFundXML = blueFundXML + "<set value='"+bf.get(i).getValue()+"'/>";
			if(min > bf.get(i).getValue())
				min = bf.get(i).getValue();
			else if(max < bf.get(i).getValue())
				max = bf.get(i).getValue();	
		}
		
		chartDesc = chartDesc + "yAxisMaxValue='"+ max +"' yAxisMinValue='"+min+"' ><categories>";
		blueFundXML = chartDesc + blueFundXML + "</dataset></chart>";
		
		return blueFundXML;
		
	}
	
	public String setGreenFundXML(){

		long statID = 2;
		
		Rates parent = ratesDelegate.getParentById(statID);
		List<Rates> bf = ratesDelegate.getRatesByParentWithPaging(parent, 30, 0, Order.desc("createdOn")).getList();
		double min = 0, max = 0;
		greenFundXML = "";
		//greenFundXML = "<chart caption='Trust Product' subCaption='Green Fund' numdivlines='9' lineThickness='2' showValues='1' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1'><categories>";
		String chartDesc = "<chart caption='RobinsonsBank Trust Product' decimals='2' numdivlines='9' lineThickness='2' showValues='0' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1' ";
		
		//populate categories
		min = bf.get(0).getValue();
		max = bf.get(0).getValue();
		for(int i=bf.size()-1; i >= 0 ; i--){
			//greenFundXML = greenFundXML + "<category label='"+bf.get(i).getDate().toString().substring(0, 11)+"'/>";
			greenFundXML = greenFundXML + "<category label='"+bf.get(i).getCreatedOn().toString().substring(0, 11)+"'/>";
		}
		
		greenFundXML = greenFundXML + "</categories><dataset seriesName='Green Fund NAVPU' color='008040' anchorBorderColor='008040'>";
		//populate values
		for(int i=bf.size()-1; i >= 0 ; i--){
			greenFundXML = greenFundXML + "<set value='"+bf.get(i).getValue()+"'/>";
			if(min > bf.get(i).getValue())
				min = bf.get(i).getValue();
			else if(max < bf.get(i).getValue())
				max = bf.get(i).getValue();	
		}
		
		chartDesc = chartDesc + "yAxisMaxValue='"+ max +"' yAxisMinValue='"+min+"' ><categories>";
		greenFundXML = chartDesc + greenFundXML + "</dataset></chart>";	
		
		
		return greenFundXML;
	}
	
	public String setBalancedFundXML(){

		long statID = 3;
		
		Rates parent = ratesDelegate.getParentById(statID);
		List<Rates> bf = ratesDelegate.getRatesByParentWithPaging(parent, 30, 0, Order.desc("createdOn")).getList();
		double min = 0, max = 0;
		balancedFundXML = "";
		//balancedFundXML = "<chart caption='Trust Product' subCaption='Balanced Fund' numdivlines='9' lineThickness='2' showValues='1' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1'><categories>";
		String chartDesc = "<chart caption='RobinsonsBank Trust Product' decimals='2' numdivlines='9' lineThickness='2' showValues='0' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1' ";
		
		//populate categories
		min = bf.get(0).getValue();
		max = bf.get(0).getValue();
		for(int i=bf.size()-1; i >= 0 ; i--){
			//balancedFundXML = balancedFundXML + "<category label='"+bf.get(i).getDate().toString().substring(0, 11)+"'/>";
			balancedFundXML = balancedFundXML + "<category label='"+bf.get(i).getCreatedOn().toString().substring(0, 11)+"'/>";
		}
		
		balancedFundXML = balancedFundXML + "</categories><dataset seriesName='Balanced Fund NAVPU' color='#000' anchorBorderColor='#000'>";
		//populate values
		for(int i=bf.size()-1; i >= 0 ; i--){
			balancedFundXML = balancedFundXML + "<set value='"+bf.get(i).getValue()+"'/>";
			if(min > bf.get(i).getValue())
				min = bf.get(i).getValue();
			else if(max < bf.get(i).getValue())
				max = bf.get(i).getValue();	
		}
		
		chartDesc = chartDesc + "yAxisMaxValue='"+ max +"' yAxisMinValue='"+min+"' ><categories>";
		balancedFundXML = chartDesc + balancedFundXML + "</dataset></chart>";

		
		return balancedFundXML;
	}
	
	public String setMoneyMarketFundXML(){

		long statID = 12939;
		
		Rates parent = ratesDelegate.getParentById(statID);
		List<Rates> bf = ratesDelegate.getRatesByParentWithPaging(parent, 30, 0, Order.desc("createdOn")).getList();
		if(bf.size() > 0) {
			double min = 0, max = 0;
			moneyMarketFundXML = "";
			//balancedFundXML = "<chart caption='Trust Product' subCaption='Balanced Fund' numdivlines='9' lineThickness='2' showValues='1' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1'><categories>";
			String chartDesc = "<chart caption='RobinsonsBank Trust Product' decimals='2' numdivlines='9' lineThickness='2' showValues='0' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1' ";
			
			//populate categories
			min = bf.get(0).getValue();
			max = bf.get(0).getValue();
			for(int i=bf.size()-1; i >= 0 ; i--){
				//balancedFundXML = balancedFundXML + "<category label='"+bf.get(i).getDate().toString().substring(0, 11)+"'/>";
				moneyMarketFundXML = moneyMarketFundXML + "<category label='"+bf.get(i).getCreatedOn().toString().substring(0, 11)+"'/>";
			}
			
			moneyMarketFundXML = moneyMarketFundXML + "</categories><dataset seriesName='Money Market Fund NAVPU' color='#FDD017' anchorBorderColor='#FDD017'>";
			//populate values
			for(int i=bf.size()-1; i >= 0 ; i--){
				moneyMarketFundXML = moneyMarketFundXML + "<set value='"+bf.get(i).getValue()+"'/>";
				if(min > bf.get(i).getValue())
					min = bf.get(i).getValue();
				else if(max < bf.get(i).getValue())
					max = bf.get(i).getValue();	
			}
			
			chartDesc = chartDesc + "yAxisMaxValue='"+ max +"' yAxisMinValue='"+min+"' ><categories>";
			moneyMarketFundXML = chartDesc + moneyMarketFundXML + "</dataset></chart>";

		}
		
		return moneyMarketFundXML;
	}
	
	public String setBuyXML(){
		int pageNumber = this.getPageNumber();
		long statID = 4;
		
		Rates parent = ratesDelegate.getParentById(statID);
		List<Rates> bf = ratesDelegate.getRatesByParentWithPaging(parent, 30, pageNumber-1, Order.desc("createdOn")).getList();
		
		buyXML = "";
		buyXML = "<chart caption='Trust Product' subCaption='Buy' numdivlines='9' lineThickness='2' showValues='1' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1'><categories>";

		//populate categories
		for(int i=bf.size()-1; i > 0 ; i--){
			//buyXML = buyXML + "<category label='"+bf.get(i).getDate().toString().substring(0, 11)+"'/>";
			buyXML = buyXML + "<category label='"+bf.get(i).getCreatedOn().toString().substring(0, 11)+"'/>";
		}
		
		buyXML = buyXML + "</categories><dataset seriesName='Buy' color='FFFF00' anchorBorderColor='FFFF00'>";
		//populate values
		for(int i=0; i < bf.size(); i++){
			buyXML = buyXML + "<set value='"+bf.get(i).getValue()+"'/>";
		}
		
		buyXML = buyXML + "</dataset></chart>";

		
		return buyXML;
	}
	
	public String setSellXML(){
		int pageNumber = this.getPageNumber();
		long statID = 5;
		
		Rates parent = ratesDelegate.getParentById(statID);
		List<Rates> bf = ratesDelegate.getRatesByParentWithPaging(parent, 30, pageNumber-1, Order.desc("createdOn")).getList();
		
		sellXML = "";
		sellXML = "<chart caption='Trust Product' subCaption='Sell' numdivlines='9' lineThickness='2' showValues='1' numVDivLines='22' formatNumberScale='1' labelDisplay='ROTATE' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1'><categories>";

		//populate categories
		for(int i=bf.size()-1; i > 0 ; i--){
			//sellXML = sellXML + "<category label='"+bf.get(i).getDate().toString().substring(0, 11)+"'/>";
			sellXML = sellXML + "<category label='"+bf.get(i).getCreatedOn().toString().substring(0, 11)+"'/>";
		}
		
		sellXML = sellXML + "</categories><dataset seriesName='Sell' color='FF0080' anchorBorderColor='FF0080'>";
		//populate values
		for(int i=0; i < bf.size(); i++){
			balancedFundXML = balancedFundXML + "<set value='"+bf.get(i).getValue()+"'/>";
		}
		
		sellXML = sellXML + "</dataset></chart>";
		
		return sellXML;
	}
}
