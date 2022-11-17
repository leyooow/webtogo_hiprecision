package com.ivant.cms.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.beans.KuysenSalesQuotationGeneratorBean;
import com.ivant.cms.beans.KuysenSalesTransactionBean;
import com.ivant.cms.beans.transformers.KuysenSalesTransactionTransformer;
import com.ivant.cms.db.AbstractDAO;
import com.ivant.cms.delegate.KuysenSalesAreaDelegate;
import com.ivant.cms.delegate.KuysenSalesClientDelegate;
import com.ivant.cms.delegate.KuysenSalesOptionalSetDelegate;
import com.ivant.cms.delegate.KuysenSalesOrderSetDelegate;
import com.ivant.cms.delegate.KuysenSalesParentOrderSetDelegate;
import com.ivant.cms.delegate.KuysenSalesTransactionDelegate;
import com.ivant.cms.entity.KuysenSalesArea;
import com.ivant.cms.entity.KuysenSalesOptionalSet;
import com.ivant.cms.entity.KuysenSalesOrderSet;
import com.ivant.cms.entity.KuysenSalesParentOrderSet;
import com.ivant.cms.entity.KuysenSalesTransaction;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.User;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.KuysenSalesConstants;
import com.opensymphony.xwork2.Action;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesQuotationAction extends AbstractBaseAction 
										implements Action, 
												   PagingAware, 
												   ServletRequestAware {
	
	private static final Logger logger = Logger.getLogger(KuysenSalesQuotationAction.class);
	
	private KuysenSalesTransactionDelegate kuysenSalesTransactionDelegate = KuysenSalesTransactionDelegate.getInstance();
	private KuysenSalesAreaDelegate kuysenSalesAreaDelegate = KuysenSalesAreaDelegate.getInstance();
	private KuysenSalesParentOrderSetDelegate kuysenSalesParentOrderSetDelegate = KuysenSalesParentOrderSetDelegate.getInstance();
	private KuysenSalesOrderSetDelegate kuysenSalesOrderSetDelegate = KuysenSalesOrderSetDelegate.getInstance();
	private KuysenSalesOptionalSetDelegate kuysenSalesOptionalSetDelegate = KuysenSalesOptionalSetDelegate.getInstance();
	private KuysenSalesClientDelegate kuysenSalesClientDelegate = KuysenSalesClientDelegate.getInstance();
	
	private HttpServletRequest request;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	private Long id;
	
	private List<KuysenSalesTransaction> transactions;
	
	private String filename;
	
	private String fileSize;
	
	private InputStream inputStream;

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public void setTotalItems() {
		totalItems = kuysenSalesTransactionDelegate.findAll().size();
	}

	@Override
	public int getTotalItems() {
		return totalItems;
	}

	@Override
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	@Override
	public String execute() throws Exception {
		itemsPerPage = 100;
		
		Order[] orders = {Order.desc("createdOn")};
		transactions = kuysenSalesTransactionDelegate.findAllWithPaging(itemsPerPage, page, orders).getList();

		return SUCCESS;
	}
	
	public String generateQuote() {
		
		DateTime dateTime = new DateTime();
		File pdfFolderDirectory = new File(ServletActionContext.getServletContext().getRealPath(""), KuysenSalesConstants.QUOTATION_PDF_FOLDER);
		if(!pdfFolderDirectory.exists()) 
		{
			System.out.println("TARGET PATH DOES NOT EXIST! " + pdfFolderDirectory.getAbsolutePath());
			pdfFolderDirectory.mkdirs();
		}
		
		KuysenSalesTransaction transaction = kuysenSalesTransactionDelegate.find(id);
		KuysenSalesTransactionBean kuysenSalesTransactionBean = (KuysenSalesTransactionBean) KuysenSalesConstants.TRANSFORMERS.get(KuysenSalesTransactionTransformer.class).transform(transaction);

		StringBuilder sb = new StringBuilder();
		sb.append(KuysenSalesConstants.QUOTATION_PDF_FILENAME_PREFIX)
									  .append(" - ")
									  .append(kuysenSalesTransactionBean.getClient().getClientName())
									  .append(" - ")
									  .append(kuysenSalesTransactionBean.getClient().getDate().toString("MM-dd-yyyy"))
									  .append(".pdf");
		
		setFilename(sb.toString());
		String filePath = pdfFolderDirectory.getAbsolutePath().concat("\\").concat(getFilename());
		
		File filepdf = new File(filePath);
		if(filepdf.exists()) {
			filepdf.delete();
		}
		
		String imagepath = servletContext.getRealPath("companies") + "/kuysensales/members/signatures/" + transaction.getMember().getInfo2();
		
		KuysenSalesQuotationGeneratorBean quotationGenerator = new KuysenSalesQuotationGeneratorBean(filePath, imagepath,kuysenSalesTransactionBean, transaction.getMember());
		quotationGenerator.generateQuote();
		
		File pdf_file = new File(filePath);
		fileSize = Long.toString(pdf_file.length());
		
		try {
			inputStream = new FileInputStream(pdf_file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String deleteQuote() {
		
		KuysenSalesTransaction transaction = kuysenSalesTransactionDelegate.find(id);
		
		try {
			for(KuysenSalesArea area : transaction.getOrders().values()) {
				for(KuysenSalesParentOrderSet parentOrderSet : area.getKuysenSalesParentOrderSets()) {
					for(KuysenSalesOrderSet orderSet : parentOrderSet.getSpecifications()) {
						kuysenSalesOrderSetDelegate.delete(orderSet);
					}
					
					for(KuysenSalesOptionalSet optionalSet : parentOrderSet.getOptionals()) {
						kuysenSalesOptionalSetDelegate.delete(optionalSet);
					}
					kuysenSalesParentOrderSetDelegate.delete(parentOrderSet);
				}
				kuysenSalesAreaDelegate.delete(area);
			}
			
			transaction.setParentOrderIdList(new ArrayList<String>());
			kuysenSalesTransactionDelegate.update(transaction);
			
			kuysenSalesTransactionDelegate.delete(transaction);
			
			kuysenSalesClientDelegate.delete(transaction.getClient());
			
		} catch(Exception a) {
			return ERROR;
		}
			
		return SUCCESS;
	}

	public List<KuysenSalesTransaction> getTransactions() {
		return transactions;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
