package com.ivant.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.interceptors.CompanyInterceptor;

/*
 * @author Glenn Allen B. Sapla
 * @version 1.0, May 2009
 */
public class PDFCreatorTool{

	private static final long serialVersionUID = -6076537228718308644L;
		
	HttpServletRequest request;
	ServletContext servletContext;
	boolean local;
	Map<String, String> contextParams;
	Company company;
	String httpServer = "";
	String cartOrderID;
	String templateName="";

	//accepts DataSource, Template File name (.jrxml), and the PDF output File Name
	public void outputPDF(JRDataSource data, String templateName2, String fileName)throws Exception{
		JasperReport jr = JasperCompileManager.compileReport(JRLoader.getResourceInputStream(templateName2));	

		/* The parameters. */
		Map<String, Object> parameters = new HashMap<String, Object>();
		/* Fill Report */
		JasperPrint print = JasperFillManager.fillReport(jr, parameters, data);
		
//		if(company.getName().equalsIgnoreCase("wendys")){
			//for(JRPrintPage page_1 : print.getPages()){
				
			//}
//		}
		
		
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream out;	
		byte[] pdfByteArray = JasperExportManager.exportReportToPdf( print );
		out = response.getOutputStream();
		
		/* PDF Stream date (response to be made) */
		response.setContentType("application/pdf");
		//generates the PDF name this is the default value
		response.setHeader("Content-Disposition","fileName="+fileName);
			
		response.setContentLength(pdfByteArray.length);
		
		/*Execute or in other means create pdf and put it in outputstream*/
		out.write(pdfByteArray, 0,pdfByteArray.length);
		out.flush();
		out.close();
	}
	
	public void outputPDFWithParameters(JRDataSource data, Map<String, Object> parameters, String templateName, String fileName)throws Exception{
		JasperReport jr = JasperCompileManager.compileReport(JRLoader.getResourceInputStream(templateName));	

		/* Fill Report */
		JasperPrint print = JasperFillManager.fillReport(jr, parameters, data);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream out;	
		byte[] pdfByteArray = JasperExportManager.exportReportToPdf( print );
		out = response.getOutputStream();
		
		/* PDF Stream date (response to be made) */
		response.setContentType("application/pdf");
		//generates the PDF name this is the default value
		response.setHeader("Content-Disposition","fileName="+fileName);
			
		response.setContentLength(pdfByteArray.length);
		
		/*Execute or in other means create pdf and put it in outputstream*/
		out.write(pdfByteArray, 0,pdfByteArray.length);
		out.flush();
		out.close();
	}
	
	//algorithm to get Logo
	public String getLogo(){
		request = ServletActionContext.getRequest();
		servletContext = ServletActionContext.getServletContext();
		local = (request.getServerName().equals("localhost")) ? true : false;
		contextParams = new HashMap<String, String>();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		httpServer = "";
		if(local) {
			httpServer = "http://"+ request.getServerName() +":"+ request.getServerPort() + "/" + servletContext.getServletContextName();
		}
		else {
			String companyServerName = (company != null) ? company.getServerName() : "webtogo.com.ph";
			httpServer = "http://" + companyServerName;
		}				
		contextParams.put("HTTP_SERVER", httpServer);
		String imagePath = "";
		if(local) {
			imagePath = (company != null) ? (httpServer + "/companies/" + company.getName()) :
											(httpServer + "/admin"); 
		}
		else {
			imagePath = httpServer;
		}
		contextParams.put("IMAGE_PATH", imagePath);
		contextParams.put("FILE_PATH", imagePath);
		request.setAttribute("contextParams", contextParams);
		
		return imagePath+"/images/"+company.getLogo();
	}
	
	//algorithm to get Logo
	public String getCertificateLogo(){
		request = ServletActionContext.getRequest();
		servletContext = ServletActionContext.getServletContext();
		local = (request.getServerName().equals("localhost")) ? true : false;
		contextParams = new HashMap<String, String>();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		httpServer = "";
		if(local) {
			httpServer = "http://"+ request.getServerName() +":"+ request.getServerPort() + "/" + servletContext.getServletContextName();
		}
		else {
			String companyServerName = (company != null) ? company.getServerName() : "webtogo.com.ph";
			httpServer = "http://" + companyServerName;
		}				
		contextParams.put("HTTP_SERVER", httpServer);
		String imagePath = "";
		if(local) {
			imagePath = (company != null) ? (httpServer + "/companies/" + company.getName()) :
											(httpServer + "/admin"); 
		}
		else {
			imagePath = httpServer;
		}
		contextParams.put("IMAGE_PATH", imagePath);
		contextParams.put("FILE_PATH", imagePath);
		request.setAttribute("contextParams", contextParams);
		
		return imagePath+"/images/certificateLogo.jpg";
	}
	
	public String getSpherePDFLogo(String rootCategory){
		request = ServletActionContext.getRequest();
		servletContext = ServletActionContext.getServletContext();
		local = (request.getServerName().equals("localhost")) ? true : false;
		contextParams = new HashMap<String, String>();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		httpServer = "";
		if(local) {
			httpServer = "http://"+ request.getServerName() +":"+ request.getServerPort() + "/" + servletContext.getServletContextName();
		}
		else {
			String companyServerName = (company != null) ? company.getServerName() : "webtogo.com.ph";
			httpServer = "http://" + companyServerName;
		}				
		contextParams.put("HTTP_SERVER", httpServer);
		String imagePath = "";
		if(local) {
			imagePath = (company != null) ? (httpServer + "/companies/" + company.getName()) :
											(httpServer + "/admin"); 
		}
		else {
			imagePath = httpServer;
		}
		contextParams.put("IMAGE_PATH", imagePath);
		contextParams.put("FILE_PATH", imagePath);
		request.setAttribute("contextParams", contextParams);
		
		if(rootCategory == null){rootCategory = ""; }
		if(rootCategory.equals("Tomato Time")){
			imagePath = imagePath+"/images/tomato-time-logo.jpg";
		}else if(rootCategory.equals("SWAP")){
			imagePath = imagePath+"/images/swap-logo.jpg";
		}else{
			imagePath = imagePath+"/images/tomato-time-logo.jpg";
		}
		return imagePath;
	}
	
	public String getCompanyItemImage(Long itemID, String size){
		request = ServletActionContext.getRequest();
		servletContext = ServletActionContext.getServletContext();
		local = (request.getServerName().equals("localhost")) ? true : false;
		contextParams = new HashMap<String, String>();
		company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		httpServer = "";
		if(local) {
			httpServer = "http://"+ request.getServerName() +":"+ request.getServerPort() + "/" + servletContext.getServletContextName();
		}
		else {
			String companyServerName = (company != null) ? company.getServerName() : "webtogo.com.ph";
			httpServer = "http://" + companyServerName;
		}				
		contextParams.put("HTTP_SERVER", httpServer);
		String imagePath = "";
		if(local) {
			imagePath = (company != null) ? (httpServer + "/companies/" + company.getName()) :
											(httpServer + "/admin"); 
		}
		else {
			imagePath = httpServer;
		}
		
		String imageName = "";
		CategoryItemDelegate categoryItemDelegate = new CategoryItemDelegate();
		List<CategoryItem> tempCatItem = categoryItemDelegate.findAll(company).getList();
		for(CategoryItem item : tempCatItem){
			if(item.getId() == itemID){
				if(!item.getImages().isEmpty()){
					imageName = item.getImages().get(0).getThumbnail();
				}else{
					continue;
				}
				
			}
		}
		
		contextParams.put("IMAGE_PATH", imagePath);
		contextParams.put("FILE_PATH", imagePath);
		request.setAttribute("contextParams", contextParams);
		
		if(imageName == ""){
			return "";
		}else{
			System.out.println(imagePath+"/images/items/" + imageName+"<<<<<<<<<<<<<");
			return imagePath+"/images/items/" + imageName;
		}
		
	}


}
