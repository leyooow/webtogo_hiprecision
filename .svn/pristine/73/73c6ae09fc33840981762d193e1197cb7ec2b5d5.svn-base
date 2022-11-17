package com.ivant.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

public class JSWriter {
	
	public void createJSFile(String company, String url) throws IOException
	{
		File file;
		Writer output;
		
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat defaultDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
		
		HtmlContent htmlContent = new HtmlContent();
		//String tradeDate = htmlContent.getHtmlContent(url, "lastTradedDate", "headerTotalValue");		
		
		file = new File((ServletActionContext.getServletContext().getRealPath("")+ File.separator+"companies"+File.separator+"new_webtogo"+File.separator+"scripts"+File.separator+company+"Date.js"));
		output = new BufferedWriter(new FileWriter(file));
		
		//try {
			//Date date = format.parse(tradeDate);
			Date date = new Date();
			output.write("document.write('"+defaultDf.format(date)+"');");		
			output.close();
		//} catch (ParseException e) {
		//    e.printStackTrace();
		//}
		file = new File((ServletActionContext.getServletContext().getRealPath("")+File.separator+"companies"+File.separator+"new_webtogo"+File.separator+"scripts"+File.separator+company+".js"));
		output = new BufferedWriter(new FileWriter(file));
		String sqPrevious = "";
		int tries = 0;
		do {
			sqPrevious = htmlContent.getHtmlContent(url, "headerSqPrevious", "securitySymbol");
			System.out.println(sqPrevious + "<<trial" +(++tries) +" getting sqPrevious");
			
			if(StringUtils.isBlank(sqPrevious)){
				if(tries>=100){
					sqPrevious = "0";
				}
				try { Thread.currentThread().sleep(1000); }
				catch ( Exception e ) { }
			}
			
		} while (StringUtils.isBlank(sqPrevious));
		BigDecimal sqPreviousDecimal = BigDecimal.ZERO;
		if(!StringUtils.isBlank(sqPrevious)){
			sqPreviousDecimal = new BigDecimal(sqPrevious);
		}
		sqPrevious = sqPreviousDecimal.setScale(3,BigDecimal.ROUND_UP).toString();
		System.out.println("sqPrevious >>>>>>>>>>>" + sqPrevious);
		if(company.equals("bhi"))
			output.write("document.write('Last Sale: "+sqPrevious+"');");
		else if(company.equals("roxas"))
			output.write("document.write('Prev Close: "+sqPrevious+"');");
		else if(company.equals("cadp"))
			output.write("document.write('Prev Close: "+sqPrevious+"');");		
		
		output.close();
				
		//System.out.println("Directory: "+file.getAbsolutePath());
		//System.out.println("File written at "+new Date()); 		
	}

	/*
	 * OLD CODE
	 * 
	public void createJSFile(String filename, String url) throws IOException{
		File file;
		
		HtmlContent htmlContent = new HtmlContent();
		ArrayList jsString = htmlContent.getHtmlContent(url, "lastTradedDate", "<!-- Put Free Float Level Here -->");
		System.out.println("JSFile "+jsString);
		file = new File((ServletActionContext.getServletContext().getRealPath("")+File.separator+"companies"+File.separator+"new_webtogo"+File.separator+"scripts"+File.separator+filename+".js"));
		Writer output = new BufferedWriter(new FileWriter(file));
		
		if(!jsString.get(0).equals("null")){
			if(!jsString.get(24).equals("0.00"))
				output.write("document.write('Last Sale: "+jsString.get(24)+"');");
			else
				output.write("document.write('Prev Close: "+jsString.get(31)+" ("+jsString.get(30)+") ');");
		}else{
			if(!jsString.get(19).equals("0.00"))
				output.write("document.write('Last Sale: "+jsString.get(19)+"');");
			else
				output.write("document.write('Prev Close: "+jsString.get(26)+"');");
		}
		output.close();
		
		file = new File((ServletActionContext.getServletContext().getRealPath("")+ File.separator+"companies"+File.separator+"new_webtogo"+File.separator+"scripts"+File.separator+filename+"Date.js"));
		output = new BufferedWriter(new FileWriter(file));
		//jsString = htmlContent.getHtmlContent(url, "As of ", "</span>");
//		if(!jsString.get(0).equals("null")){
//			if(jsString.get(24).equals("0.00")){
//				DateTime temp = new DateTime();
//				int month = temp.getMonthOfYear();
//			    int year = temp.getYear();
//			    int day = temp.getDayOfMonth();
//			    output.write("document.write('"+(month)+"-"+day+"-"+year+"');");
////				output.write("document.write('"+jsString.get(30)+"');");
//			}else{
////				Calendar cal = new GregorianCalendar();
//				DateTime temp = new DateTime();
//				temp = temp.minusDays(1);
//				int month = temp.getMonthOfYear();
//			    int year = temp.getYear();
//			    int day = temp.getDayOfMonth();
//			    output.write("document.write('"+(month)+"-"+day+"-"+year+"');");
//			}
//		}else{
			 Calendar cal = new GregorianCalendar();
			    int month = cal.get(Calendar.MONTH);
			    int year = cal.get(Calendar.YEAR);
			    int day = cal.get(Calendar.DAY_OF_MONTH);
			output.write("document.write('"+(month+1)+"-"+day+"-"+year+"');");
//		}
			
		output.close();
		
		System.out.println("Directory: "+file.getAbsolutePath());
		System.out.println("File written at "+new Date()); 
		
	}
	*/
}
