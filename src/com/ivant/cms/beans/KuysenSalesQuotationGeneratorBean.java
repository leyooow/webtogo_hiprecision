package com.ivant.cms.beans;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.ivant.cms.entity.Member;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.constants.KuysenSalesConstants;
import com.ivant.utils.DocumentElement;
import com.ivant.utils.ITextPdfUtil;
import com.ivant.utils.ImageElement;
import com.ivant.utils.LineSeparatorElement;
import com.ivant.utils.TableCellElement;
import com.ivant.utils.TableElement;
import com.ivant.utils.TextElement;
import com.lowagie.text.pdf.PdfCell;

public class KuysenSalesQuotationGeneratorBean extends DisplayProcessor {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	private static final int LINE_HEIGHT = 10;
	private static final int FONT_SIZE = 8;
	private static final float BORDER_WIDTH = .5F;
	private static final float CELL_HEIGHT = 13f;
	private static final float[] HEADER_WIDTHS = new float[]{1f,4f};
	private static final float[] COLUMN_HEADER_WIDTHS = new float[]{3f,3f,6f,4f,2f,2f, 3f,3f};
	private static final float[] ITEM_WIDTHS = new float[]{2.1f,4.1f,2.7f,1.3f,1.4f, 2f, 2f};
	private static final float[] TERMS_AND_CONDITION_WIDTHS = new float[]{.5f,20f};
	private static final int PAGINATION_X = 530;
	private static final int PAGINATION_Y = 820;
	
	private final String fileFullPath;
	private final KuysenSalesTransactionBean kuysenSalesTransactionBean;
	private final Member member;
	private final String signaturePath;
	private List<TextElement> paginations = new ArrayList<TextElement>();
	
	public KuysenSalesQuotationGeneratorBean(String fileFullPath, String signaturePath, KuysenSalesTransactionBean kuysenSalesTransactionBean, Member member) {
		this.fileFullPath = fileFullPath;
		this.kuysenSalesTransactionBean = kuysenSalesTransactionBean;
		this.member = member;
		this.signaturePath = signaturePath;
	}
	
	public void generateQuote() {
		
		ITextPdfUtil pdf = ITextPdfUtil.getInstance();

		pdf.setMargin(10, 10);
		
		generateQuoteHeader(pdf);
		generateColumnHeader(pdf, 195); 
		Integer current_y = 210;
		
		TextElement firstpage = new TextElement("", PAGINATION_X, PAGINATION_Y);
		paginations.add(firstpage);
		pdf.addElement(firstpage);
		
		int areaPrefix = 65;
		
		for(String area : kuysenSalesTransactionBean.getOrders().keySet()) {
			String prefix = Character.toString((char) areaPrefix++);
			current_y = generateQuoteProductItems(pdf, prefix, area, current_y);
		}

		current_y = generateQuoteSummary(pdf, current_y);
		
		generateTermAndConditions(pdf, current_y);
		
		for(int index = 0; index < paginations.size();index++) {
			TextElement page = paginations.get(index);
			page.setText("Page ".concat(Integer.toString(index + 1)).concat("/").concat(Integer.toString(paginations.size())));
		}
		
		pdf.createDocument(fileFullPath);
	}
	
	private int generateQuoteSummary(ITextPdfUtil pdf, Integer current_y) {
		TableElement table = null;
		Double grand_total = 0.0;
		
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		
		int areaPrefix = 65;
		
		for(KuysenSalesAreaBean area : kuysenSalesTransactionBean.getOrders().values()) {
			String firstColumnValue = areaPrefix == 65 ? "QUOTATION SUMMARY :" : "";
			String prefix = Character.toString((char) areaPrefix++);
			current_y = displayGrandSummaryBreakDown(firstColumnValue, prefix.concat(". ").concat(area.getArea().toUpperCase()), formatter.format(area.getSubTotal()), current_y, pdf);

			if(current_y > 810) {
				pdf.addElement(new DocumentElement(Boolean.TRUE));
				TextElement page = new TextElement("", PAGINATION_X, PAGINATION_Y);
				paginations.add(page);
				pdf.addElement(page);
				current_y = 0;
			}
			
			grand_total += area.getSubTotal();
		}
		
		table = getGrandSummaryTable(current_y);
		table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_LEFT, com.itextpdf.text.Font.NORMAL, 1).setNoBorder(), 1);
		table.addCell(new TableCellElement("Total Amount", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_LEFT, com.itextpdf.text.Font.NORMAL, 1).setNoBorder().setBorderBottom(.5F).setBorderTop(.5F).setCellHeight(15f).setBorderColor(Color.BLACK).setBorderLeft(.5F), 1);
		table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, com.itextpdf.text.Font.NORMAL, 1).setNoBorder().setNoBorder().setBorderBottom(.5F).setBorderTop(.5F).setCellHeight(15f).setBorderColor(Color.BLACK), 1);
		table.addCell(new TableCellElement(formatter.format(grand_total), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, com.itextpdf.text.Font.NORMAL, 1).setNoBorder().setBorderBottom(.5F).setBorderTop(.5F).setCellHeight(15f).setBorderColor(Color.BLACK).setBorderRight(.5F), 1);
		table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_LEFT, com.itextpdf.text.Font.NORMAL, 1).setNoBorder(), 1);
		pdf.addElement(table);
		
		current_y += 17;
		
		if(current_y > 810) {
			pdf.addElement(new DocumentElement(Boolean.TRUE));
			TextElement page = new TextElement("", PAGINATION_X, PAGINATION_Y);
			paginations.add(page);
			pdf.addElement(page);
			current_y = 0;
		}
		
		if(kuysenSalesTransactionBean.getTotalDiscount() > 0) {
			current_y = displayGrandSummaryBreakDown("", "Additional Adjustment", formatter.format(kuysenSalesTransactionBean.getTotalDiscount()), current_y, pdf);
		}
		
		if(current_y > 810) {
			pdf.addElement(new DocumentElement(Boolean.TRUE));
			TextElement page = new TextElement("", PAGINATION_X, PAGINATION_Y);
			paginations.add(page);
			pdf.addElement(page);
			current_y = 0;
		}
		
		//Total Net Amount
		if(kuysenSalesTransactionBean.getTotalDiscount() > 0) {
			table = getGrandSummaryTable(current_y);
			table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_LEFT, com.itextpdf.text.Font.NORMAL, 1).setNoBorder(), 1);
			table.addCell(new TableCellElement("Total Net Amount", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_LEFT, com.itextpdf.text.Font.NORMAL, 1).setNoBorder().setBorderBottom(.5F).setBorderTop(.5F).setCellHeight(15f).setBorderColor(Color.BLACK).setBorderLeft(.5F), 1);
			table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, com.itextpdf.text.Font.NORMAL, 1).setNoBorder().setNoBorder().setBorderBottom(.5F).setBorderTop(.5F).setCellHeight(15f).setBorderColor(Color.BLACK), 1);
			table.addCell(new TableCellElement(formatter.format(kuysenSalesTransactionBean.getDiscountedTotal()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, com.itextpdf.text.Font.NORMAL, 1).setNoBorder().setBorderBottom(.5F).setBorderTop(.5F).setCellHeight(15f).setBorderColor(Color.BLACK).setBorderRight(.5F), 1);
			table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_LEFT, com.itextpdf.text.Font.NORMAL, 1).setNoBorder(), 1);
			pdf.addElement(table);
		}
		
		current_y += 17;
		
		if(current_y > 810) {
			pdf.addElement(new DocumentElement(Boolean.TRUE));
			TextElement page = new TextElement("", PAGINATION_X, PAGINATION_Y);
			paginations.add(page);
			pdf.addElement(page);
			current_y = 0;
		}
		
		return current_y;
	}
	
	private Integer displayGrandSummaryBreakDown(String firstColumnValue, String caption, String value, Integer current_y, ITextPdfUtil pdf) {
		TableElement table = getGrandSummaryTable(current_y);
		table.addCell(new TableCellElement(firstColumnValue, Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_LEFT, com.itextpdf.text.Font.NORMAL, 1), 1);
		table.addCell(new TableCellElement(caption, Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_LEFT, com.itextpdf.text.Font.NORMAL, 1), 1);
		table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, com.itextpdf.text.Font.NORMAL, 1), 1);
		table.addCell(new TableCellElement(value, Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, com.itextpdf.text.Font.NORMAL, 1), 1);
		table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, com.itextpdf.text.Font.NORMAL, 1), 1);
		pdf.addElement(table);
		current_y += 17;
		return current_y;
	}
	
	private TableElement getGrandSummaryTable(Integer current_y) {
		TableElement table =  new TableElement(5, 560, true, 10, current_y); 
		table.setColumnWidths(new float[]{2f,2f,1f,2f,2f});
		return table;
	}
	
	private void generateQuoteHeader(ITextPdfUtil pdf) {
		
		pdf.addElement(new ImageElement("http://www.kuysensales.webtogo.com.ph/images/KuysenLogo4.png", 150F, 50F, ImageElement.FLOAT_LEFT, 0));
		//pdf.addElement(new ImageElement("http://www.kuysensales.webtogo.com.ph/images/sanitec.png", 150F, 43F, ImageElement.FLOAT_RIGHT, 10));
		
		pdf.addElement(new TextElement("www.kuysen.com", Color.BLACK, 7, 503, 35));
		
		/*LEFT*/
		{
			int y = 65;
			int x = 10;
			int interval = 18;
			createHeaderRow(pdf, "Date :", kuysenSalesTransactionBean.getClient().getDate().toString("EEEE, MMMM dd, yyyy"), x, y, false, false);
			createHeaderRow(pdf, "To :", kuysenSalesTransactionBean.getClient().getClientName(), x, y += interval, false, false);
			createHeaderRow(pdf, "Company :", kuysenSalesTransactionBean.getClient().getClientCompany(), x, y += interval, false, false);
			createHeaderRow(pdf, "Address :", kuysenSalesTransactionBean.getClient().getClientAddress(), x, y += interval, false, false);
		}
		
		/*REF*/
		{
			int y = 40;
			int x = 290;
			createHeaderRow(pdf, "REF# :", kuysenSalesTransactionBean.getClient().getRef(), x, y, false, false);
		}
		
		/*RIGHT*/
		{
			int y = 65;
			int x = 290;
			int interval = 18;
			createHeaderRow(pdf, "Tel :", kuysenSalesTransactionBean.getClient().getClientTelephone(), x, y, false, false);
			createHeaderRow(pdf, "Fax :", kuysenSalesTransactionBean.getClient().getClientFax(), x, y += interval, false, false);
			createHeaderRow(pdf, "Landline :", kuysenSalesTransactionBean.getClient().getClientTelephone(), x, y += interval, false, false);
			createHeaderRow(pdf, "Mobile :", kuysenSalesTransactionBean.getClient().getClientMobile(), x, y += interval, false, false);
			createHeaderRow(pdf, "Email :", kuysenSalesTransactionBean.getClient().getClientEmail(), x, y += interval, false, false);
		}
		
		pdf.addElement(new TextElement("Dear Sir/Madam:", 10, 175, false));
		pdf.addElement(new TextElement("Our Company is pleased to qoute you the following items for your project requirements.", 10, 190));
				
	}
	
	private void createHeaderRow(ITextPdfUtil pdf, String fieldCaption, String value, int x, int y, boolean isCaptionBold, boolean isValueBold) {
		TableElement table = new TableElement(2, 270, true, x, y);
		table.setColumnWidths(HEADER_WIDTHS);
		
		int captionFont = isCaptionBold ? Font.BOLD : Font.NORMAL;
		int valueFont = isValueBold ? Font.BOLD : Font.NORMAL;
		
		table.addCell(new TableCellElement(fieldCaption, 1, TableCellElement.CELL_CONTENT_ALIGN_LEFT, FONT_SIZE, captionFont, Color.BLACK),1);
		table.addCell(new TableCellElement(value, 1, TableCellElement.CELL_CONTENT_ALIGN_LEFT, FONT_SIZE, valueFont, Color.BLACK).setAllBorder(Color.LIGHT_GRAY),1);
		
		pdf.addElement(table);
	}
	
	private void generateColumnHeader(ITextPdfUtil pdf, int y) {
		TableElement table = new TableElement(8, 560, true, 10, y);
		table.setColumnWidths(COLUMN_HEADER_WIDTHS);
		table.addCell(new TableCellElement("Item", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.LIGHT_GRAY, BORDER_WIDTH, CELL_HEIGHT, 1, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setAllBorderExceptRight(), 1);
		table.addCell(new TableCellElement("Art. No.", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.LIGHT_GRAY, BORDER_WIDTH, CELL_HEIGHT, 1, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setAllBorderExceptRight(),1);
		table.addCell(new TableCellElement("Description", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.LIGHT_GRAY, BORDER_WIDTH, CELL_HEIGHT, 1, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setAllBorderExceptRight(),1);
		table.addCell(new TableCellElement("Unit Price", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.LIGHT_GRAY, BORDER_WIDTH, CELL_HEIGHT, 1, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setAllBorderExceptRight(),1);
		table.addCell(new TableCellElement("Qty.", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.LIGHT_GRAY, BORDER_WIDTH, CELL_HEIGHT, 1, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setAllBorderExceptRight(),1);
		table.addCell(new TableCellElement("Less %", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.LIGHT_GRAY, BORDER_WIDTH, CELL_HEIGHT, 1, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setAllBorderExceptRight(),1);
		table.addCell(new TableCellElement("Net Price", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.LIGHT_GRAY, BORDER_WIDTH, CELL_HEIGHT, 1, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setAllBorderExceptRight(),1);
		table.addCell(new TableCellElement("Total", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.LIGHT_GRAY, BORDER_WIDTH, CELL_HEIGHT, 1, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setAllBorder(),1);
		pdf.addElement(table);
	}
	
	private Integer generateQuoteProductItems(ITextPdfUtil pdf, String prefix, String area, Integer current_y) {
		TableElement table = null;
		Integer item_no = 1;
		String SortNoCaption = "";
		
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		
		for(KuysenSalesParentOrderSetBean orderset : sortParentOrderItems(kuysenSalesTransactionBean.getOrders().get(area).getOrders())) {
			
			if(createNewDocumentPage(orderset,current_y)) {
				pdf.addElement(new DocumentElement(Boolean.TRUE));
				TextElement page = new TextElement("", PAGINATION_X, PAGINATION_Y);
				paginations.add(page);
				pdf.addElement(page);
				generateColumnHeader(pdf, 0);
				current_y = 15;
			}
			
			SortNoCaption = orderset.getItem().getDescriptionWithoutTags();
			
			if(orderset.getIsPackage()) {
				SortNoCaption += " SET"; 
			}
			
			table = new TableElement(8, 560, true, 10, current_y);
			table.setColumnWidths(COLUMN_HEADER_WIDTHS);
			
			if(item_no == 1) {
				table.addCell(new TableCellElement(prefix.concat(". ").concat(area.toUpperCase()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, com.itextpdf.text.Font.NORMAL, 1).setNoBorder(),1);
			} else {
				table.addCell(new TableCellElement(" ", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER).setNoBorder(),1);
			}
			
			table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER).setNoBorder(), 1);
			table.addCell(new TableCellElement(""/*orderset.getItem().getName().toUpperCase()*/, Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER, com.itextpdf.text.Font.BOLD, 1).setNoBorder(), 1);
			table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER).setNoBorder(), 1);
			table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER).setNoBorder(), 1);
			table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER).setNoBorder(), 1);
			table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER).setNoBorder(), 1);
			table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER).setNoBorder(), 1);
			
			table.addCell(new TableCellElement("http://www.kuysen.com" + KuysenSalesConstants.CATEGORY_IMAGES_DIRECTORY + orderset.getItem().getImages().get(0).getImage3(), Boolean.TRUE, Boolean.TRUE, Color.BLUE, 1f, 1, TableCellElement.CELL_CONTENT_ALIGN_CENTER, 60f),1);
			table.addCell(new TableCellElement(/*SortNoCaption*/"", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER), 1);
			table.addCell(new TableCellElement(/*orderset.getItem().getDescription() == null ? "" : orderset.getItem().getDescription()*/"", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_LEFT), 1);
			table.addCell(new TableCellElement(/*formatter.format(orderset.getItem().getPrice())*/"", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER) , 1);
			table.addCell(new TableCellElement(/*(orderset.getDiscount() != 0.0 ? formatter.format(orderset.getDiscount() * 100) : "0") + " %" + (orderset.getSubDiscount() != 0.0 ? "\n" + formatter.format(orderset.getSubDiscount() * 100) + " %" : "")*/"", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER), 1);
			table.addCell(new TableCellElement(/*orderset.getQuantity().toString()*/"", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER), 1);
			table.addCell(new TableCellElement(/*formatter.format(orderset.getTotal())*/"", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER), 1);
			table.addCell(new TableCellElement(/*formatter.format(orderset.getTotal())*/"", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER), 1);
			pdf.addElement(table);
			
			int base_y_added = current_y + 60;

			current_y += 10;
			Integer spec_no = 1;
			
			if(orderset.getIsPackage()) {
				
				table = new TableElement(7, 495, true, 75, current_y);
				table.setColumnWidths(ITEM_WIDTHS);
				//Boolean isBGPink = Boolean.TRUE;
				Color bgcolor = Color.WHITE;
				
				//table.addCell(new TableCellElement("Item consisted of : ", new Color(31,167,214), 4, TableCellElement.CELL_CONTENT_ALIGN_LEFT, 10, Color.LIGHT_GRAY, 1, 25f), 4);
					for(KuysenSalesOrderSetBean os : orderset.getSpecifications()) {
						if(os.getIsIncluded()) {
							
							/*if(isBGPink) {
								bgcolor = new Color(255,241,223);
							} else {
								bgcolor = Color.WHITE;
							}*/
							
							String description = formatDisplayText(os.getItem().getBrand().getParentBrand() != null ? os.getItem().getBrand().getParentBrand().getName() + "\n" + os.getItem().getSku().trim() : os.getItem().getBrand() != null ? os.getItem().getBrand().getName() + "\n" + os.getItem().getSku().trim() : os.getItem().getSku().trim(), 30);
							int lines = countLines(description);
							
							table.addCell(new TableCellElement(os.getItem().getDescriptionWithoutTags().trim(), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor), 1);
							
							table.addCell(new TableCellElement(
													description
													,Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_LEFT, bgcolor), 1);
							table.addCell(new TableCellElement(formatter.format(os.getItem().getPrice()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
							table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_LEFT, bgcolor), 1);
							table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_LEFT, bgcolor), 1);
							table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_LEFT, bgcolor), 1);
							table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_LEFT, bgcolor), 1);
							
							current_y += lines * LINE_HEIGHT;
							spec_no++;
							
							//isBGPink = !isBGPink;
						}
					}
					
				table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_LEFT, bgcolor), 1);
				table.addCell(new TableCellElement("", Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_LEFT, bgcolor), 1);
				table.addCell(new TableCellElement(formatter.format(orderset.getItem().getPrice()), Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setNoBorder().setBorderTop().setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
				table.addCell(new TableCellElement(orderset.getQuantity().toString(), Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
					if(orderset.getDiscount().doubleValue() > 0.0 && orderset.getSubDiscount().doubleValue() > 0.0) {
						table.addCell(new TableCellElement(new Integer(new Double(orderset.getDiscount() * 100).intValue()).toString() + "%, " + 
								   						   new Integer(new Double(orderset.getSubDiscount() * 100).intValue()).toString() + "% ", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
					} else if(orderset.getDiscount().doubleValue() > 0.0) {
						table.addCell(new TableCellElement(new Integer(new Double(orderset.getDiscount() * 100).intValue()).toString() + "%", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
					} else {
						table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
					}
				table.addCell(new TableCellElement(formatter.format(orderset.pdfNetPrice()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
				table.addCell(new TableCellElement(formatter.format(orderset.mainTotal()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
				
				if(orderset.getOptionals().size() > 0) {
					for(KuysenSalesOptionalSetBean optional : orderset.getOptionals()) {
						if(optional.getTotal() > 0) {
							
							String description = formatDisplayText(optional.getItem().getBrand().getParentBrand() != null ? optional.getItem().getBrand().getParentBrand().getName() + "\n" + optional.getItem().getSku().trim() : optional.getItem().getBrand() != null ? optional.getItem().getBrand().getName() + "\n" + optional.getItem().getSku().trim() : optional.getItem().getSku().trim(), 30);
							int lines = countLines(description);
							
							table.addCell(new TableCellElement(optional.getItem().getDescriptionWithoutTags().trim(), Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor), 1);
							table.addCell(new TableCellElement(description, Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_LEFT, bgcolor), 1);
							table.addCell(new TableCellElement(formatter.format(optional.getItem().getPrice()), Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setNoBorder().setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
							table.addCell(new TableCellElement(optional.getQuantity().toString(), Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
							table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
							table.addCell(new TableCellElement(formatter.format(optional.getItem().getPrice()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
							table.addCell(new TableCellElement(formatter.format(optional.getTotal()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
						
							current_y += lines * LINE_HEIGHT;
						}
					}
				}
				
				pdf.addElement(table);
			} else {
				Color bgcolor = Color.WHITE;
				
				table = new TableElement(7, 495, true, 75, current_y);
				table.setColumnWidths(ITEM_WIDTHS);
				
				String description = formatDisplayText(orderset.getItem().getBrand().getParentBrand() != null ? orderset.getItem().getBrand().getParentBrand().getName() + "\n" + orderset.getItem().getSku().trim() : orderset.getItem().getBrand() != null ? orderset.getItem().getBrand().getName() + "\n" + orderset.getItem().getSku().trim() : orderset.getItem().getSku().trim(), 30);
				int lines = countLines(description);
				
				table.addCell(new TableCellElement(orderset.getItem().getDescriptionWithoutTags().trim(), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor), 1);
				table.addCell(new TableCellElement(
													description
													,Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_LEFT, bgcolor), 1);
				table.addCell(new TableCellElement(formatter.format(orderset.getItem().getPrice()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
				table.addCell(new TableCellElement(orderset.getQuantity().toString(), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
					if(orderset.getDiscount().doubleValue() > 0.0 && orderset.getSubDiscount().doubleValue() > 0.0) {
						table.addCell(new TableCellElement(new Integer(new Double(orderset.getDiscount() * 100).intValue()).toString() + "%, " + 
								   						   new Integer(new Double(orderset.getSubDiscount() * 100).intValue()).toString() + "% ", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
					} else if(orderset.getDiscount().doubleValue() > 0.0) {
						table.addCell(new TableCellElement(new Integer(new Double(orderset.getDiscount() * 100).intValue()).toString() + "%", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
					} else {
						table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
					}
				table.addCell(new TableCellElement(formatter.format(orderset.pdfNetPrice()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
				table.addCell(new TableCellElement(formatter.format(orderset.mainTotal()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
				
				if(orderset.getOptionals().size() > 0) {
					for(KuysenSalesOptionalSetBean optional : orderset.getOptionals()) {
						if(optional.getTotal() > 0) {
							
							description = formatDisplayText(optional.getItem().getBrand().getParentBrand() != null ? optional.getItem().getBrand().getParentBrand().getName() + "\n" + optional.getItem().getSku().trim() : optional.getItem().getBrand() != null ? optional.getItem().getBrand().getName() + "\n" + optional.getItem().getSku().trim() : optional.getItem().getSku().trim(), 30);
							lines = countLines(description);
							
							table.addCell(new TableCellElement(optional.getItem().getDescriptionWithoutTags().trim(), Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor), 1);
							table.addCell(new TableCellElement(description, Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_LEFT, bgcolor), 1);
							table.addCell(new TableCellElement(formatter.format(optional.getItem().getPrice()), Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setNoBorder().setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
							table.addCell(new TableCellElement(optional.getQuantity().toString(), Color.BLACK, 8, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
							table.addCell(new TableCellElement("", Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_CENTER, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
							table.addCell(new TableCellElement(formatter.format(optional.getItem().getPrice()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
							table.addCell(new TableCellElement(formatter.format(optional.getTotal()), Color.BLACK, FONT_SIZE, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, bgcolor).setVerticalAlignment(TableCellElement.CELL_CONTENT_ALIGN_BOTTOM), 1);
						
							current_y += lines * LINE_HEIGHT;
						}
					}
				}
				
				pdf.addElement(table);
				
				current_y += lines + LINE_HEIGHT;
			}
			
			if(current_y < base_y_added) {
				current_y = base_y_added;
			}
			
			current_y += 25;
			
			item_no++;
		}
		
		if((current_y + 50) > 810) {
			pdf.addElement(new DocumentElement(Boolean.TRUE));
			TextElement page = new TextElement("", PAGINATION_X, PAGINATION_Y);
			paginations.add(page);
			pdf.addElement(page);
			current_y = 0;
		}
		
		generateSubGroupSummary(pdf, area, current_y);
		
		current_y += 50;
		
		return current_y;
	}
	
	private void generateSubGroupSummary(ITextPdfUtil pdf, String area, int y) {
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		
		TableElement table = new TableElement(7, 560, true, 10, y);
		table.setColumnWidths(new float[]{3f,3f,6f,4f,4f,2f,3f});
		
		if(kuysenSalesTransactionBean.getOrders().get(area).getTotalDiscount() > 0) {
			table.addCell(new TableCellElement("", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 3, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setNoBorder().setBorderLeft().setBorderTop(), 3);
			table.addCell(new TableCellElement("Total for ".concat(area), TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 2, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, Color.WHITE).setNoBorder().setBorderTop(),2);
			table.addCell(new TableCellElement(formatter.format(kuysenSalesTransactionBean.getOrders().get(area).getNetTotal()), TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 2, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, Color.WHITE).setNoBorder().setBorderTop().setBorderRight(),2);
			
			table.addCell(new TableCellElement("", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 3, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setNoBorder().setBorderLeft(), 3);
			table.addCell(new TableCellElement("Additional Adjustment", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 2, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, Color.WHITE).setNoBorder(),2);
			table.addCell(new TableCellElement("(" + formatter.format(kuysenSalesTransactionBean.getOrders().get(area).getTotalDiscount()) + ")", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 2, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, Color.WHITE).setNoBorder().setBorderRight().setBorderBottom(Color.BLACK),2);
			
			table.addCell(new TableCellElement("", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 3, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setNoBorder().setBorderLeft().setBorderBottom(), 3);
			table.addCell(new TableCellElement("Net Total", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 2, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, Color.WHITE).setNoBorder().setBorderBottom(),2);
			table.addCell(new TableCellElement(formatter.format(kuysenSalesTransactionBean.getOrders().get(area).getSubTotal()), TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 2, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, Color.WHITE).setNoBorder().setBorderBottom().setBorderRight(),2);
		} else {
			table.addCell(new TableCellElement("", TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 3, TableCellElement.CELL_CONTENT_ALIGN_CENTER, Color.WHITE).setNoBorder().setBorderLeft().setBorderTop().setBorderBottom(), 3);
			table.addCell(new TableCellElement("Total for ".concat(area), TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 2, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, Color.WHITE).setNoBorder().setBorderTop().setBorderBottom(),2);
			table.addCell(new TableCellElement(formatter.format(kuysenSalesTransactionBean.getOrders().get(area).getNetTotal()), TableCellElement.baseFont, Color.BLACK, FONT_SIZE, Font.NORMAL, Color.GRAY, .5f, 15f, 2, TableCellElement.CELL_CONTENT_ALIGN_RIGHT, Color.WHITE).setNoBorder().setBorderTop().setBorderRight().setBorderBottom(),2);
		}
		
		pdf.addElement(table);
	}
	
	private void generateTermAndConditions(ITextPdfUtil pdf, int current_y) {
		
		current_y += 20;
		
		if(current_y > 450) {
			pdf.addElement(new DocumentElement(Boolean.TRUE));
			TextElement page = new TextElement("", PAGINATION_X, PAGINATION_Y);
			paginations.add(page);
			pdf.addElement(page);
			current_y = 20;
		}
		
		TableElement table = new TableElement(2, 560, true, 10, current_y);
		table.setColumnWidths(TERMS_AND_CONDITION_WIDTHS);
		
		table.addCell(new TableCellElement(new Phrase(new Chunk("TERMS AND CONDITIONS", new Font(FontFamily.HELVETICA, 7F, Font.BOLD))), 2).setBorderLeft(Color.LIGHT_GRAY, .5F).setBorderTop(Color.LIGHT_GRAY, .5F).setBorderRight(Color.LIGHT_GRAY, .5F), 2);
		
		generateTermsAndConditionsItems(table, "1.", generateChunk(Color.BLACK, "Prices indicated in this qoutation are inclusive of all Philippiine Governament prescribed duties and taxes, cost of goods \n" +
																				"devery to the jobsite within Metro-Manila, ground floor level only"));
		generateTermsAndConditionsItems(table, "2.", generateChunk(Color.BLACK, "A "), generateChunk(Color.BLACK, "minimum down payment of Fifty (50%) Percent is required payable upon reciept of \n" + 
																				"signed confirmation of order before stocks can be reserved\n"),
																				generateChunk(Color.BLACK, "Balance is payable Cash-Before-Delivery(CBD), progress delivery, progress payment allowed."));
		generateTermsAndConditionsItems(table, "3.", generateChunk(Color.BLACK, "Product stocks move on continious basis. The customer's payment, therefore, is not to be considered as an affirmation of the sale \n" +
																				"Kuysen Enterprise Inc. (KEI) & Sanitec Immport Ventures, Inc. (SIVI) have one working day to determine with finality the stock availability\n" +
																				"of the products detailed in the confirmation.\n" +
																				"A) In case it is confirmed that the ordered stocks are no longer available and the customer is not willing to exchange out of stocks items\n" +
																				"    with another item, KEI/SIVI will reimburse a check down payment immediately upon clearing\n" + 
																				"    These check refunds will be issued under the name appearing on the official reciept issued by KEI/SIVI.\n" +
																				"B) Credit card payments will be voided by KEI/SIVI with the credit card company and proof of cancellation of \n" +
																				"    the transaction will be presented to the customer upon receipt of the same by KEI/SIVI"));
		generateTermsAndConditionsItems(table, "4.", generateChunk(Color.BLACK, "Clients will be invoiced either by Kuysen Enterprise Inc. of Sanitec Import Ventures Inc. \n" +
																				"depending on the product delivered. Both by Kuysen and Sanitec are owned and" +
																				"operated by one the same management group and collection documents from either of both of these companies\n" +
																				"will be accepted to the client. Checks will be made out to the company invoicing the delivery. \n"),
																				generateChunk(Color.BLACK, "Check payments must be cleared prior to delivery. A period of Three(3) banking days is required for this purpose."));
		generateTermsAndConditionsItems(table, "5.", generateChunk(Color.BLACK,   "On stock items will be delivered within 4 to 5 days upon clearing the payment."), generateChunk(Color.BLACK, "Indent orders are to be delivered within\n" + 
																				"a period of 120 to 150 days from the date of the full payment. For indent orders, once the order have been processed\n" + 
																				"no cancellation of change in quantity shall be accepted by Kuysen and Sanitec"));
		generateTermsAndConditionsItems(table, "6.", generateChunk(Color.BLACK, "All prices indicated do not include installation. \n" +
																				"Should there be more queries please feel free to call us at (411-9571-75/740-7509 Loc.104) or look for the undersigned\n" +
																				"We hope to serve ypur requirements very soon."));
		
		table.addCell(new TableCellElement(new Phrase(), 2).setBorderLeft(Color.LIGHT_GRAY, .5F).setBorderBottom(Color.LIGHT_GRAY, .5F).setBorderRight(Color.LIGHT_GRAY, .5F), 2);
		
		current_y += 220;
		
		pdf.addElement(new TextElement("We hope to serve your requirements soon", Color.BLACK, FONT_SIZE, 10, current_y));
		
		current_y += 30;
		
		pdf.addElement(table);
		
		table = new TableElement(1, 100, true, 10, current_y);
		table.setColumnWidths(new float[]{1f});

		table.addCell(new TableCellElement(new Phrase(new Chunk("Very truly yours,", new com.itextpdf.text.Font(FontFamily.HELVETICA, FONT_SIZE, Font.NORMAL)))), 1);
		table.addCell(new TableCellElement(signaturePath, true, true, Color.WHITE, 0f, 1, TableCellElement.CELL_CONTENT_ALIGN_LEFT, 40f), 1);
		table.addCell(new TableCellElement(new Phrase(new Chunk(member.getFirstname() + " " + member.getLastname(), new Font(FontFamily.HELVETICA, FONT_SIZE, Font.NORMAL)))).setNoBorder().setBorderTop(), 1);
		table.addCell(new TableCellElement(new Phrase(new Chunk(member.getEmail() == null ? "" : member.getEmail(), new Font(FontFamily.HELVETICA, FONT_SIZE, Font.NORMAL)))).setNoBorder(), 1);
		table.addCell(new TableCellElement(new Phrase(new Chunk(member.getInfo1() == null ? "" : member.getInfo1(), new Font(FontFamily.HELVETICA, FONT_SIZE, Font.NORMAL)))).setNoBorder(), 1);
		table.addCell(new TableCellElement(new Phrase(new Chunk(member.getMobile() == null ? "" : member.getMobile(), new Font(FontFamily.HELVETICA, FONT_SIZE, Font.NORMAL)))).setNoBorder(), 1);
		
		pdf.addElement(table);
		
		table = new TableElement(1, 150, true, 400, current_y);
		table.setColumnWidths(new float[]{1f});

		table.addCell(new TableCellElement(new Phrase(new Chunk("CONFORME", new Font(FontFamily.HELVETICA, FONT_SIZE, Font.NORMAL)))), 1);
		table.addCell(new TableCellElement(new Phrase(new Chunk("", new Font(FontFamily.HELVETICA, FONT_SIZE, Font.NORMAL)))).setFixedHeight(40f), 1);
		table.addCell(new TableCellElement(new Phrase(new Chunk("(SIGNATURE OVER PRINTED NAME)", new Font(FontFamily.HELVETICA, FONT_SIZE, Font.NORMAL)))).setNoBorder().setBorderTop(), 1);
		table.addCell(new TableCellElement(new Phrase(new Chunk("", new Font(FontFamily.HELVETICA, FONT_SIZE, Font.NORMAL)))).setFixedHeight(20f), 1);
		table.addCell(new TableCellElement(new Phrase(new Chunk("(DATE)", new Font(FontFamily.HELVETICA, FONT_SIZE, Font.NORMAL)))).setNoBorder().setBorderTop(), 1);
		
		pdf.addElement(table);
	}
	
	private void generateTermsAndConditionsItems(TableElement table, String itemNo, Chunk... chunks) {
		Phrase content = new Phrase();
		for(Chunk chunk : chunks) {
			content.add(chunk);
		}
		
		TableCellElement col1 = null;
		TableCellElement col2 = null;
		
		table.addCell(col1 = new TableCellElement(new Phrase(generateChunk(Color.BLACK, itemNo))).setBorderLeft(Color.LIGHT_GRAY, .5F), 1);
		table.addCell(col2 = new TableCellElement(content).setBorderRight(Color.LIGHT_GRAY, .5F), 1);
		
		col1.getCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
		col2.getCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
	}
	
	private Chunk generateChunk(Color color, String content) {
		return new Chunk(content, new Font(FontFamily.HELVETICA, 7f, Font.NORMAL, new BaseColor(color.getRGB())));
	}
	
	
	
	private Boolean createNewDocumentPage(KuysenSalesParentOrderSetBean parent , Integer currentY) {
		final Integer PAGE_HEIGHT = 810;
		final Integer INITIAL_CONSUMED_SPACE = 70;
		final Integer TABLE_ROW_CONSUMED_SPACE = LINE_HEIGHT;
		
		Integer currentComsumedSpace = INITIAL_CONSUMED_SPACE + currentY;
		
		for(KuysenSalesOrderSetBean os : parent.getSpecifications()) {
			String description = formatDisplayText(os.getItem().getBrand().getParentBrand() != null ? os.getItem().getBrand().getParentBrand().getName() + "\n" + os.getItem().getSku().trim() : os.getItem().getBrand() != null ? os.getItem().getBrand().getName() + "\n" + os.getItem().getSku().trim() : os.getItem().getSku().trim(), 30);
			int lines = countLines(description);
			currentComsumedSpace += TABLE_ROW_CONSUMED_SPACE * lines;
		}
		
		for(KuysenSalesOptionalSetBean os : parent.getOptionals()) {
			String description = formatDisplayText(os.getItem().getBrand().getParentBrand() != null ? os.getItem().getBrand().getParentBrand().getName() + "\n" + os.getItem().getSku().trim() : os.getItem().getBrand() != null ? os.getItem().getBrand().getName() + "\n" + os.getItem().getSku().trim() : os.getItem().getSku().trim(), 30);
			int lines = countLines(description);
			currentComsumedSpace += TABLE_ROW_CONSUMED_SPACE * lines;
		}
		
		if(currentComsumedSpace >= PAGE_HEIGHT) {
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	private List<KuysenSalesParentOrderSetBean> sortParentOrderItems(List<KuysenSalesParentOrderSetBean> all) {
		List<KuysenSalesParentOrderSetBean> newAll = new ArrayList<KuysenSalesParentOrderSetBean>();
		
		for(KuysenSalesParentOrderSetBean parent : all) {
			if(parent.getIsPackage()) {
				newAll.add(parent);
			}
		}
		
		for(KuysenSalesParentOrderSetBean parent : all) {
			if(!parent.getIsPackage()) {
				newAll.add(parent);
			}
		}
		return newAll;
	}
}
