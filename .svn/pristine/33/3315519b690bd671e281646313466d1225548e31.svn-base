package com.ivant.cms.delegate;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.RatesDAO;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Rates;

import com.ivant.cms.entity.list.ObjectList;

/**
 * 
 * @author Irish
 * 
 *  
 */
public class RatesDelegate extends AbstractBaseDelegate<Rates, RatesDAO> {

	private static RatesDelegate instance;

	protected RatesDelegate() {
		super(new RatesDAO());
	}

	/**
	 * 
	 * @return MessageDelegate Instance
	 */
	public static RatesDelegate getInstance() {
		if (instance == null) {
			return new RatesDelegate();
		}
		return instance;
	}
	
	
	public ObjectList<Rates> getParentFund(Company company){
		return dao.getParentFund(company);
	}
	
	public ObjectList<Rates> getRatesByFund(Long fundID){
		return dao.getRatesByFund(fundID);
	}
	
	public ObjectList<Rates> getAll(Company company){
		return dao.getAll(company);
	}
	
	public ObjectList<Rates> getRatesByCompany(Company company){
		return dao.getRatesByCompany(company);
	}
	
	public Rates getParentById(Long statID){
		return dao.getParentById(statID);
	}
	
	public Rates getRateById(Long statID){
		return dao.getRateById(statID);
	}
	
	public List<Rates> getRatesByName(String name, Rates parent) {
		return dao.getRatesByName(name, parent);
	}
	
	public ObjectList<Rates> getRatesByParent(Rates parent){
		return dao.getRatesByParent(parent);
	}
	
	public ObjectList<Rates> getRatesByParent(Rates parent, Order...orders){
		return dao.getRatesByParent(parent, orders);
	}
	
	public Rates getLatestRates(Rates parent, String name) {
		return dao.getLatestRates(parent, name);
	}
	
	public Rates getLatestByParent(Rates parent, Order...orders){
		return dao.getLatestByParent(parent, orders);
	}
	
	public ObjectList<Rates> getMultipleParentRates(List<Rates> parentList, Order...orders){
		return dao.getMultipleParentRates(parentList, orders);
	}
	
	public ObjectList<Rates> getBuyAndSellRates(Company company){
		return dao.getBuyAndSellRates(company);
	}
	
	public ObjectList<Rates> getTrustProductsRates(Company company){
		return dao.getTrustProductsRates(company);
	}
	
	public Rates find(Company company, String name){
		return dao.find(company, name);
	}
	
	public ObjectList<Rates> getRatesByParentWithPaging(Rates parent, int resultPerPage,int pageNumber){
		return dao.getRatesByParentWithPaging(parent, resultPerPage, pageNumber);
	}
	
	public ObjectList<Rates> getRatesByParentWithPaging(Rates parent, int resultPerPage,int pageNumber, Order...orders){
		return dao.getRatesByParentWithPaging(parent, resultPerPage, pageNumber, orders);
	}
	
	public ObjectList<Rates> getLatestDate(Company company){
		return dao.getLatestDate(company);
	}
	
	public int getLatestDateCount(Company company){
		return dao.getLatestDate(company).getSize();
	}
}