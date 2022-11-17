package com.ivant.cms.action.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JProgressBar;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.JSONException;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.beans.KuysenClientBean;
import com.ivant.cms.beans.KuysenSalesInquiriesInfoBean;
import com.ivant.cms.beans.KuysenSalesQuotationGeneratorBean;
import com.ivant.cms.beans.KuysenSalesTransactionBean;
import com.ivant.cms.beans.transformers.KuysenSalesTransactionTransformer;
import com.ivant.cms.dataSource.KuysenSalesQuotationDataSource;
import com.ivant.cms.delegate.KuysenSalesTransactionDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.KuysenSalesTransaction;
import com.ivant.cms.entity.Member;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.KuysenClientAware;
import com.ivant.cms.interfaces.KuysenSalesTransactionAware;
import com.ivant.cms.interfaces.MemberAware;
import com.ivant.constants.KuysenSalesConstants;
import com.opensymphony.xwork2.Action;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class KuysenSalesReportAction extends AbstractBaseAction 
									 implements Action, ServletRequestAware, 
									 			CompanyAware, KuysenSalesTransactionAware, 
									 			MemberAware {
	
	private final Logger logger = Logger.getLogger(getClass());

	private final KuysenSalesTransactionDelegate kuysenSalesTransactionDelegate = KuysenSalesTransactionDelegate.getInstance();
	
	private KuysenSalesTransactionBean kuysenSalesTransactionBean;
	private KuysenClientBean kuysenClientBean;
	private HttpServletRequest request;
	private Company company;

	private InputStream inputStream;

	private String data;
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy");
	
	private String filename;
	
	private String fileSize;
	
	public String saveQuotation() {

		kuysenSalesTransactionBean.setMember(member);
		KuysenSalesTransaction transactionEntity = (KuysenSalesTransaction) KuysenSalesConstants.TRANSFORMERS.get(KuysenSalesTransactionTransformer.class).insertBeanAsEntity(kuysenSalesTransactionBean, null);
		kuysenSalesTransactionBean.setId(transactionEntity.getId());
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION,kuysenSalesTransactionBean);
		
		JSONObject request_status = new JSONObject();
		request_status.put("Ok", true);
		inputStream = new StringBufferInputStream(request_status.toJSONString());
		
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
		
		String signaturePath = servletContext.getRealPath("companies") + "/kuysensales/members/signatures/" + member.getInfo2();
		
		KuysenSalesQuotationGeneratorBean quotationGenerator = new KuysenSalesQuotationGeneratorBean(filePath, signaturePath, kuysenSalesTransactionBean, member);
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

	@Override
	public void prepare() throws Exception {
		logger.info("KUYSEN REPORT ACTION IS PREPARING");
	}

	@Override
	public void setCompany(Company company) {
		this.company = company;

	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public void setKuysenSalesTransactionBean(KuysenSalesTransactionBean kuysenSalesTransactionBean) {
		this.kuysenSalesTransactionBean = kuysenSalesTransactionBean;
	}
}
