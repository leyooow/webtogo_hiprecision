package com.ivant.cms.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.BatchUpdateExcelFileDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.CategoryItemPriceDelegate;
import com.ivant.cms.delegate.CategoryItemPriceNameDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.GroupImageDelegate;
import com.ivant.cms.delegate.GroupLanguageDelegate;
import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.OtherFieldValueDelegate;
import com.ivant.cms.entity.BatchUpdateExcelFile;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.CategoryItemPrice;
import com.ivant.cms.entity.CategoryItemPriceName;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.GroupImage;
import com.ivant.cms.entity.GroupLanguage;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.OtherFieldValue;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.ApplicationConstants;
import com.ivant.utils.FileUtil;
import com.ivant.utils.HTMLTagStripper;
import com.ivant.utils.ImageUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class GroupAction extends ActionSupport
		implements Preparable, ServletRequestAware, UserAware, ServletContextAware, CompanyAware {

	private static final long serialVersionUID = 4048368734861819318L;
	private static final Logger logger = Logger.getLogger(GroupAction.class);
	private static final CategoryItemPriceNameDelegate categoryItemPriceNameDelegate = CategoryItemPriceNameDelegate
			.getInstance();
	private static final OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	private static final OtherFieldValueDelegate otherFieldValueDelegate = OtherFieldValueDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private static final GroupImageDelegate groupImageDelegate = GroupImageDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private static final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private static final BatchUpdateExcelFileDelegate excelFileDelegate = BatchUpdateExcelFileDelegate.getInstance();
	private static final CategoryItemPriceDelegate categoryItemPriceDelegate = CategoryItemPriceDelegate.getInstance();
	private static final CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate
			.getInstance();
	private LanguageDelegate languageDelegate = LanguageDelegate.getInstance();
	private GroupLanguageDelegate groupLanguageDelegate = GroupLanguageDelegate.getInstance();

	private User user;
	private HttpServletRequest request;
	private Company company;
	private CompanySettings companySettings;

	private ServletContext servletContext;

	private Group group;
	// start: group image add-ons

	private InputStream fInStream;
	private InputStream inputStream;

	private File[] files;
	private String[] contentTypes;
	private String[] filenames;

	private GroupImage groupImage;
	private long contentLength;
	private String fileName;
	private String contentType;
	private String image_id;

	// private String group_id;
	String shortDescription;
	// end
	private String errorType;

	// for the batch update
	private String[] itemSKUs = new String[0];
	private String[] itemNames = new String[0];
	private Double[][] itemPrices = null;
	private String[] itemIds = new String[0];
	private String[] itemActions = new String[0];
	private String[] itemOtherActions = new String[0];
	private String[] itemShortDescription = new String[0];
	private String[] itemDescription = new String[0];
	private String[] itemOtherDetails = new String[0];
	private List<CategoryItemPriceName> categoryItemPriceNames = null;
	// end of fields for batch update

	List<BatchUpdateExcelFile> excelFiles;

	private List<Language> languages;

	public List<BatchUpdateExcelFile> getExcelFiles() {
		return excelFiles;
	}

	public String[] getItemIds() {
		return itemIds;
	}

	public String[] getItemSKUs() {
		return itemSKUs;
	}

	public String[] getItemNames() {
		return itemNames;
	}

	public Double[][] getItemPrices() {
		return itemPrices;
	}

	public List<CategoryItemPriceName> getCategoryItemPriceNames() {
		return categoryItemPriceNames;
	}

	public String[] getItemActions() {
		return itemActions;
	}

	@Override
	public String execute() throws Exception {
		return Action.NONE;
	}

	public void prepare() throws Exception {
		setLanguages(company.getLanguages());
		companySettings = company.getCompanySettings();
		try {
			long groupId = Long.parseLong(request.getParameter("group_id"));
			group = groupDelegate.find(groupId);
		} catch (Exception e) {
			group = new Group();
			group.setCompany(user.getCompany());
		}
		if (request.getParameter("language") != null && group != null) {
			group.setLanguage(languageDelegate.find(request.getParameter("language"), company));
		}

	}

	// for hpds only
	public String downloadGroupDataAsExcel() {
		group = groupDelegate.find(Long.parseLong(request.getParameter("group_id")));
		short col = 0;

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("RUNNING DAY");

		// STYLES

		// Default style
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName(HSSFFont.FONT_ARIAL);
		style.setWrapText(true);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(font);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBottomBorderColor(HSSFColor.BLACK.index);

		// test code and section style
		HSSFCellStyle testCodeStyle = wb.createCellStyle();
		HSSFFont testCodeFont = wb.createFont();
		testCodeFont.setFontHeightInPoints((short) 10);
		testCodeFont.setFontName(HSSFFont.FONT_ARIAL);
		testCodeStyle.setWrapText(true);
		testCodeStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		testCodeStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		testCodeStyle.setFont(testCodeFont);
		testCodeStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		testCodeStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		testCodeStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		testCodeStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		testCodeStyle.setRightBorderColor(HSSFColor.BLACK.index);
		testCodeStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		testCodeStyle.setTopBorderColor(HSSFColor.BLACK.index);
		testCodeStyle.setBottomBorderColor(HSSFColor.BLACK.index);

		// title style
		HSSFCellStyle titleStyle = wb.createCellStyle();
		HSSFFont titleFont = wb.createFont();
		titleFont.setColor(HSSFColor.BLACK.index);
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setFontName(HSSFFont.FONT_ARIAL);
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setWrapText(true);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleStyle.setFont(titleFont);

		// title style1
		HSSFCellStyle titleStyle1 = wb.createCellStyle();
		HSSFFont titleFont1 = wb.createFont();
		titleFont1.setColor(HSSFColor.BLACK.index);
		titleFont1.setFontHeightInPoints((short) 16);
		titleFont1.setFontName(HSSFFont.FONT_ARIAL);
		titleFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle1.setWrapText(true);
		titleStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleStyle1.setFont(titleFont1);
		titleStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle1.setBottomBorderColor(HSSFColor.BLACK.index);

		// title style2
		HSSFCellStyle titleStyle2 = wb.createCellStyle();
		HSSFFont titleFont2 = wb.createFont();
		titleFont2.setColor(HSSFColor.BLACK.index);
		titleFont2.setFontHeightInPoints((short) 10);
		titleFont2.setFontName(HSSFFont.FONT_ARIAL);
		titleFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle2.setWrapText(true);
		titleStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleStyle2.setFont(titleFont2);

		// title style3
		HSSFCellStyle titleStyle3 = wb.createCellStyle();
		HSSFFont titleFont3 = wb.createFont();
		titleFont3.setColor(HSSFColor.RED.index);
		titleFont3.setFontHeightInPoints((short) 15);
		titleFont3.setFontName(HSSFFont.FONT_ARIAL);
		titleFont3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle3.setWrapText(true);
		titleStyle3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		titleStyle3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleStyle3.setFont(titleFont3);

		// footer style
		HSSFCellStyle footerStyle = wb.createCellStyle();
		HSSFFont footerFont = wb.createFont();
		footerFont.setColor(HSSFColor.BLACK.index);
		footerFont.setFontHeightInPoints((short) 12);
		footerFont.setFontName(HSSFFont.FONT_ARIAL);
		footerStyle.setWrapText(true);
		footerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		footerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		footerStyle.setFont(footerFont);

		// header style
		HSSFCellStyle headerStyle = wb.createCellStyle();
		HSSFFont headerFont = wb.createFont();
		headerFont.setColor(HSSFColor.LIGHT_BLUE.index);
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setFontName("Verdana");
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setWrapText(true);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headerStyle.setFont(headerFont);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setRightBorderColor(HSSFColor.BLACK.index);
		headerStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		headerStyle.setTopBorderColor(HSSFColor.BLACK.index);
		headerStyle.setBottomBorderColor(HSSFColor.BLACK.index);

		HSSFCellStyle headerStyle1 = wb.createCellStyle();
		HSSFFont headerFont1 = wb.createFont();
		headerFont1.setColor(HSSFColor.BLACK.index);
		headerFont1.setFontHeightInPoints((short) 10);
		headerFont1.setFontName(HSSFFont.FONT_ARIAL);
		headerStyle1.setWrapText(true);
		headerStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headerStyle1.setFont(headerFont1);
		headerStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle1.setRightBorderColor(HSSFColor.BLACK.index);
		headerStyle1.setLeftBorderColor(HSSFColor.BLACK.index);
		headerStyle1.setTopBorderColor(HSSFColor.BLACK.index);
		headerStyle1.setBottomBorderColor(HSSFColor.BLACK.index);

		// floor(actual excel width * 275)
		sheet.setColumnWidth((short) 0, (short) 10370.25);
		sheet.setColumnWidth((short) 1, (short) 6718.25);
		sheet.setColumnWidth((short) 2, (short) 4518.25);
		sheet.setColumnWidth((short) 3, (short) 5068.25);
		sheet.setColumnWidth((short) 4, (short) 7463.5);
		sheet.setColumnWidth((short) 5, (short) 11943.25);
		sheet.setColumnWidth((short) 6, (short) 4479.75);
		sheet.setColumnWidth((short) 7, (short) 4163.5);
		sheet.setColumnWidth((short) 8, (short) 1650);
		sheet.setColumnWidth((short) 9, (short) 1336.5);
		sheet.setColumnWidth((short) 10, (short) 3456.75);
		sheet.setColumnWidth((short) 11, (short) 4163.5);
		sheet.setColumnWidth((short) 12, (short) 16145.25);
		sheet.setColumnWidth((short) 13, (short) 3770.25);
		sheet.setColumnWidth((short) 14, (short) 2788.5);
		sheet.setColumnWidth((short) 15, (short) 3770.25);

		HSSFRow headerRowTitle = sheet.createRow((short) 0);
		HSSFRow headerRowTitle1 = sheet.createRow((short) 1);
		HSSFRow headerRowTitle2 = sheet.createRow((short) 3);
		HSSFRow headerRowTitle3 = sheet.createRow((short) 4);
		headerRowTitle.createCell((short) 0).setCellStyle(titleStyle);
		headerRowTitle1.createCell((short) 5).setCellStyle(titleStyle2);
		headerRowTitle2.createCell((short) 0).setCellStyle(titleStyle1);
		headerRowTitle3.createCell((short) 0).setCellStyle(titleStyle3);

		headerRowTitle.setHeightInPoints((short) 26.25);
		headerRowTitle1.setHeightInPoints((short) 26.25);
		sheet.createRow((short) 2).setHeightInPoints((short) 26.25);
		headerRowTitle2.setHeightInPoints((short) 20.25);
		headerRowTitle3.setHeightInPoints((short) 19.5);

		Region titleRegion = new Region();
		titleRegion.setColumnFrom((short) 0);
		titleRegion.setColumnTo((short) 15);
		titleRegion.setRowFrom((short) 0);
		titleRegion.setRowTo((short) 0);
		sheet.addMergedRegion(titleRegion);
		headerRowTitle.getCell((short) 0).setCellValue("HI-PRECISION DIAGNOSTICS");

		Region titleRegion2 = new Region();
		titleRegion2.setColumnFrom((short) 5);
		titleRegion2.setColumnTo((short) 7);
		titleRegion2.setRowFrom((short) 1);
		titleRegion2.setRowTo((short) 1);
		sheet.addMergedRegion(titleRegion2);
		headerRowTitle1.getCell((short) 5)
				.setCellValue("TEL.NO. (02) 413-79-50 TO 51                    MOBILE NO.: 0922-890- 6673");

		Region titleRegion1 = new Region();
		titleRegion1.setColumnFrom((short) 0);
		titleRegion1.setColumnTo((short) 15);
		titleRegion1.setRowFrom((short) 3);
		titleRegion1.setRowTo((short) 3);
		sheet.addMergedRegion(titleRegion1);
		headerRowTitle2.getCell((short) 0).setCellValue("LABORATORY TEST INFORMATION");

		Region titleRegion3 = new Region();
		titleRegion3.setColumnFrom((short) 0);
		titleRegion3.setColumnTo((short) 1);
		titleRegion3.setRowFrom((short) 4);
		titleRegion3.setRowTo((short) 4);
		sheet.addMergedRegion(titleRegion3);
		headerRowTitle3.getCell((short) 0).setCellValue("");

		// header creation
		HSSFRow headerRow = sheet.createRow((short) 5);
		// HSSFCell headerCell;
		HSSFCell headerCell1 = headerRow.createCell(col++);
		headerCell1.setCellValue("TEST PROCEDURE");
		headerCell1.setCellStyle(headerStyle);
		HSSFCell headerCell2 = headerRow.createCell(col++);
		headerCell2.setCellValue("TEST CODE");
		headerCell2.setCellStyle(headerStyle);
		headerRow.createCell(col++).setCellStyle(headerStyle);
		if (company.getCompanySettings().getHasOtherFields()) {
			List<OtherField> otherFields = group.getOtherFields();
			for (int i = 0; i < otherFields.size(); i++) {
				HSSFCell headerCell3 = headerRow.createCell(col++);
				headerCell3.setCellValue(otherFields.get(i).getName());
				headerCell3.setCellStyle(headerStyle);
			}
		}

		Region region = new Region();
		region.setColumnFrom((short) 8);
		region.setColumnTo((short) 10);
		region.setRowFrom((short) 5);
		region.setRowTo((short) 5);
		sheet.addMergedRegion(region);
		headerRow.getCell((short) 8).setCellValue("SPECIMEN STABILITY");

		headerRow = sheet.createRow((short) 6);
		headerRow.createCell((short) 0).setCellStyle(headerStyle1);
		headerRow.createCell((short) 1).setCellStyle(headerStyle1);
		headerRow.createCell((short) 2).setCellStyle(headerStyle1);
		headerRow.createCell((short) 3).setCellStyle(headerStyle1);
		headerRow.createCell((short) 4).setCellStyle(headerStyle1);
		headerRow.createCell((short) 5).setCellStyle(headerStyle1);
		headerRow.createCell((short) 6).setCellStyle(headerStyle1);
		headerRow.createCell((short) 7).setCellStyle(headerStyle1);
		HSSFCell headerCell4 = headerRow.createCell((short) 8);
		headerCell4.setCellStyle(headerStyle1);
		headerCell4.setCellValue("20-25 C");
		HSSFCell headerCell5 = headerRow.createCell((short) 9);
		headerCell5.setCellStyle(headerStyle1);
		headerCell5.setCellValue("4-8 C");
		HSSFCell headerCell6 = headerRow.createCell((short) 10);
		headerCell6.setCellStyle(headerStyle1);
		headerCell6.setCellValue("-20 C");
		headerRow.createCell((short) 11).setCellStyle(headerStyle1);
		headerRow.createCell((short) 12).setCellStyle(headerStyle1);
		headerRow.createCell((short) 13).setCellStyle(headerStyle1);
		headerRow.createCell((short) 14).setCellStyle(headerStyle1);
		headerRow.createCell((short) 15).setCellStyle(headerStyle1);

		sheet.createFreezePane(0, 7);

		// data
		List<CategoryItem> items = categoryItemDelegate.findAllByGroupNameAsc(company, group).getList();
		for (int i = 0; i < items.size(); i++) {
			CategoryItem item = items.get(i);
			HSSFRow row = sheet.createRow((short) (i + 7));
			col = 0;

			HSSFCell cell = row.createCell(col++);
			cell.setCellValue(HTMLTagStripper.stripTags2(item.getName()));
			cell.setCellStyle(style);

			if (item.getSku() != null) {
				cell = row.createCell(col++);
				cell.setCellValue(HTMLTagStripper.stripTags2(item.getSku()));
				cell.setCellStyle(testCodeStyle);
			} else {
				cell = row.createCell(col++);
				cell.setCellValue("");
				cell.setCellStyle(testCodeStyle);
			}
			row.createCell(col++).setCellStyle(testCodeStyle);
			if (company.getCompanySettings().getHasOtherFields()) {
				int total = item.getParentGroup().getOtherFieldCount();
				List<CategoryItemOtherField> otherFieldContentList = categoryItemOtherFieldDelegate
						.findByCategoryItem(company, item).getList();

				for (int j = 0; j < total; j++) {
					if (j >= otherFieldContentList.size()) {
						cell = row.createCell(col++);
						cell.setCellValue("");
						cell.setCellStyle(style);
					} else {
						CategoryItemOtherField otherFieldContent = otherFieldContentList.get(j);

						if (otherFieldContent != null && otherFieldContent.getContent() != null) {
							cell = row.createCell(col++);
							cell.setCellValue(HTMLTagStripper.stripTags2(StringUtils.substring(otherFieldContent.getContent(), 0, 32767)));
							if (otherFieldContent.getOtherField().getName().compareToIgnoreCase("SECTION") == 0) {
								cell.setCellStyle(testCodeStyle);
							} else {
								cell.setCellStyle(style);
							}
						} else {
							cell = row.createCell(col++);
							cell.setCellValue("");
							cell.setCellStyle(style);
						}
					}
				}
			}
		}

		HSSFRow headerRowFooter = sheet.createRow((short) items.size() + 7);
		headerRowFooter.createCell((short) 0).setCellStyle(footerStyle);

		HSSFRow headerRowFooter1 = sheet.createRow((short) items.size() + 8);
		headerRowFooter1.createCell((short) 0).setCellStyle(footerStyle);

		HSSFRow headerRowFooter2 = sheet.createRow((short) items.size() + 9);
		headerRowFooter2.createCell((short) 0).setCellStyle(footerStyle);

		Region footerRegion = new Region();
		footerRegion.setColumnFrom((short) 0);
		footerRegion.setColumnTo((short) 6);
		footerRegion.setRowFrom((short) items.size() + 7);
		footerRegion.setRowTo((short) items.size() + 7);
		sheet.addMergedRegion(footerRegion);
		headerRowFooter.getCell((short) 0).setCellValue(
				"NOTE: ALL TESTS ARE SUBJECT TO CHANGE FOR REFERENCE RANGES AND RUNNING DAYS WITHOUT PRIOR NOTICE. PLEASE BE GUIDED ACCORDINGLY");

		Region footerRegion1 = new Region();
		footerRegion1.setColumnFrom((short) 0);
		footerRegion1.setColumnTo((short) 6);
		footerRegion1.setRowFrom((short) items.size() + 8);
		footerRegion1.setRowTo((short) items.size() + 8);
		sheet.addMergedRegion(footerRegion1);
		headerRowFooter1.getCell((short) 0).setCellValue(
				"               FOR SPECIAL TESTS: A. PLEASE CALL HPD MAIN LABORATORY FOR THE CONFIRMATION OF THE PROCEDURE");

		Region footerRegion2 = new Region();
		footerRegion2.setColumnFrom((short) 0);
		footerRegion2.setColumnTo((short) 6);
		footerRegion2.setRowFrom((short) items.size() + 9);
		footerRegion2.setRowTo((short) items.size() + 9);
		sheet.addMergedRegion(footerRegion2);
		headerRowFooter2.getCell((short) 0).setCellValue(
				"                                                        B. ALL RESULTS ARE PROCESSED ONLY ON WEEKDAYS. SATURDAYS AND SUNDAYS NOT INCLUDED.");

		// file writing and streaming from here
		File tempFile = null;
		try {
			tempFile = File.createTempFile("downloadGroupAsExcel", "xls");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		FileOutputStream fOutStream = null;
		try {
			fOutStream = new FileOutputStream(tempFile); // dapat
															// fileinputstream
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Action.ERROR;
		}

		try {
			wb.write(fOutStream);
		} catch (IOException e) {
			e.printStackTrace();
			return Action.ERROR;
		}

		try {
			fOutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fInStream = new FileInputStream(tempFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		contentLength = tempFile.length();
		fileName = company.getName() + "_" + group.getName().replace(" ", "_").toLowerCase() + ".xls";
		tempFile.deleteOnExit();

		return Action.SUCCESS;
	}

	/* batch update */
	public String displayUploadedExcels() {
		group = groupDelegate.find(Long.parseLong(request.getParameter("group_id")));
		excelFiles = excelFileDelegate.findAll(company, group).getList();
		return Action.SUCCESS;
	}

	public String downloadExcelFile() {
		Long id = Long.parseLong(request.getParameter("excel_id"));
		BatchUpdateExcelFile excelItem = excelFileDelegate.find(id);

		File file = new File(excelItem.getFileLocation());
		try {
			fInStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		contentLength = file.length();
		fileName = excelItem.getFileName();
		contentType = excelItem.getContentType();

		return Action.SUCCESS;
	}

	public String batchUpdate() {
		group = groupDelegate.find(Long.parseLong(request.getParameter("group_id")));
		// System.out.println("Batch Update Heeerree: "+group.getName());
		// System.out.println("Length.........");
		// System.out.println("Length......... "+itemOtherDetails.length);
		// System.out.println("Length......... "+itemShortDescription.length);
		// System.out.println("Length......... "+itemDescription.length);
		// System.out.println("itemDescription[0].........
		// "+itemDescription[0]);
		// System.out.println("itemDescription[1]........ "+itemDescription[1]);
		File excelFile = (File) servletContext.getAttribute("com.ivant.cms.action.admin.GroupAction.excelFile");
		String excelName = (String) servletContext.getAttribute("com.ivant.cms.action.admin.GroupAction.excelName");
		String excelContentType = (String) servletContext
				.getAttribute("com.ivant.cms.action.admin.GroupAction.excelContentType");

		if (excelFile == null) {
			logger.info("\tbatchUpdate() excelFile is null");
			return Action.SUCCESS;
		}

		itemIds = request.getParameterValues("id");
		itemNames = request.getParameterValues("productName");
		// System.out.println("Item ids "+itemIds.length);

		itemPrices = (Double[][]) servletContext.getAttribute("com.ivant.cms.action.admin.GroupAction.itemPrices");
		servletContext.removeAttribute("com.ivant.cms.action.admin.GroupAction.itemPrices");

		// update happens here
		for (int i = 0; i < itemIds.length; i++) {
			Long id = Long.parseLong(itemIds[i]);

			if (id > 0) { // if the item needs a a change
				CategoryItem item = categoryItemDelegate.find(id);

				if (item == null) {
					continue;
				}
				logger.debug("\n\nCurrent item: " + item.getName());
				List<CategoryItemPriceName> names = item.getParentGroup().getCategoryItemPriceNames();
				item.setName(itemNames[i]);

				/*
				 * if(itemShortDescription!=null &&
				 * i<itemShortDescription.length){
				 * item.setShortDescription(itemShortDescription[i]);
				 * logger.debug("prev item short desc "
				 * +item.getShortDescription()+" to "+itemShortDescription[i]);
				 * } if(itemDescription!= null && i<itemDescription.length){
				 * item.setDescription(itemDescription[i]); logger.debug(
				 * "prev item desc "+item.getDescription()+" to "
				 * +itemDescription[i]); } if(itemOtherDetails !=null &&
				 * i<itemOtherDetails.length){
				 * item.setOtherDetails(itemOtherDetails[i]); logger.debug(
				 * "prev item other "+item.getOtherDetails()+" to "
				 * +itemOtherDetails[i]); }
				 */

				String shortDesc = " ";

				if (i < itemShortDescription.length) {
					if (item.getShortDescription() != null) {

						shortDesc = item.getShortDescription();
					}
					// System.out.println("item.getShortDescription()
					// "+shortDesc);
					if (itemShortDescription[i] != null) {
						if (itemShortDescription[i].compareTo(shortDesc) != 0) {
							item.setShortDescription(itemShortDescription[i]);
						}

					}

				}

				String descStr = " ";
				if (i < itemDescription.length) {
					if (item.getDescription() != null) {
						descStr = item.getDescription();
					}
					if (itemDescription[i] != null) {
						if (itemDescription[i].compareTo(descStr) != 0) {
							item.setDescription(itemDescription[i]);
						}
					}
				}
				String otherStr = " ";
				if (i < itemOtherDetails.length) {
					if (item.getOtherDetails() != null) {
						otherStr = item.getOtherDetails();
					}
					if (itemOtherDetails[i] != null) {
						if (itemOtherDetails[i].compareTo(otherStr) != 0) {
							item.setOtherDetails(itemOtherDetails[i]);
						}
					}
				}

				Long[] pricesId = new Long[1];
				pricesId = getPricesId(item).toArray(pricesId);

				for (int j = 0; j < pricesId.length; j++) {
					if (pricesId[j] == 0) { // update the default price
						item.setPrice(new Double(itemPrices[j][i] + ""));
						boolean result = categoryItemDelegate.update(item);

						logger.debug("1 Update default price: " + item.getName() + " Result: " + result + "\tnew price"
								+ new Double(itemPrices[j][i] + ""));
					} else if (pricesId[j] < 0) { // the other price is not in
													// the database. insert it
						CategoryItemPrice price = categoryItemPriceDelegate.findByCategoryItemPriceName(company, item,
								categoryItemPriceNameDelegate.find(pricesId[j] * -1l));

						if (price == null) { // check first if the price does
												// not exist yet
							price = new CategoryItemPrice();
							price.setCompany(company);
							price.setCategoryItem(item);
							price.setCategoryItemPriceName(names.get(j));
							price.setName(names.get(j).getName());
							price.setAmount(itemPrices[j][i]);
							Long priceId = categoryItemPriceDelegate.insert(price);
							price.setId(priceId);
						} else {
							price.setAmount(itemPrices[j][i]);
							boolean result = categoryItemPriceDelegate.update(price);
						}
					} else { // the update
						CategoryItemPrice price = categoryItemPriceDelegate.find(pricesId[j]);
						if (price != null) {
							price.setAmount(itemPrices[j][i]);
							boolean result = categoryItemPriceDelegate.update(price);

						} else
							logger.debug("Item price not found!");
					}
				}
			}
		}

		// copy the excel file to the server
		String path = servletContext.getRealPath("") + File.separator + "companies" + File.separator + company.getName()
				+ File.separator + "batchupdate_excelfiles";
		File parent = new File(path);
		parent.mkdir();
		File file = new File(parent, excelName);

		try {
			excelFile.createNewFile();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(excelFile);
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return Action.SUCCESS;
		}

		// copy byte per byte
		byte[] buf = new byte[1024];
		int len;
		try {
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("batchUpdate() File saved at: " + file.getAbsolutePath());

		// save the new excel file
		if (!excelFileDelegate.isInTheDatabase(company, group, file.getAbsolutePath())) {
			BatchUpdateExcelFile excelFileItem = new BatchUpdateExcelFile();
			excelFileItem.setCompany(company);
			excelFileItem.setFileLocation(file.getAbsolutePath());
			excelFileItem.setContentType(excelContentType);
			excelFileItem.setFileName(excelName);
			excelFileItem.setGroup(group);
			excelFileItem.setUser(user);
			Long id = excelFileDelegate.insert(excelFileItem);
			excelFileItem.setId(id);
		} else { // do something to update the 'updated_by' field of the excel
					// file
			List<BatchUpdateExcelFile> excelFileItems = excelFileDelegate
					.findByPath(company, group, file.getAbsolutePath()).getList();

			if (excelFileItems.size() == 1) {
				BatchUpdateExcelFile excelFileItem = excelFileItems.get(0);
				excelFileItem.setFileName("template.xls");
				excelFileDelegate.update(excelFileItem);
				excelFileItem.setFileName(excelName);
				excelFileDelegate.update(excelFileItem);
			}
		}

		servletContext.removeAttribute("com.ivant.cms.action.admin.GroupAction.excelFile");
		servletContext.removeAttribute("com.ivant.cms.action.admin.GroupAction.excelName");
		servletContext.removeAttribute("com.ivant.cms.action.admin.GroupAction.excelContentType");
		return Action.SUCCESS;
	}

	public String previewBatchUpdate() {
		group = groupDelegate.find(Long.parseLong(request.getParameter("groupId")));
		categoryItemPriceNames = group.getCategoryItemPriceNames();
		int numPrice = (group.getItemHasPrice() == null) ? 1 : group.getCategoryItemPriceNames().size();
		boolean hasHeader = false;

		if (!commonParamsValid()) {
			return Action.ERROR;
		}

		if (contentTypes[0].compareTo("application/vnd.ms-excel") != 0) {
			return Action.ERROR;
		}

		// read the uploaded file
		InputStream inp = null;
		try {
			inp = new FileInputStream(files[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> itemSKUsList = new ArrayList<String>();
		List<String> itemNamesList = new ArrayList<String>();
		List<Double>[] itemPricesList = new ArrayList[numPrice]; // each list
																	// contains
																	// a single
																	// price
																	// column of
																	// a all the
																	// items
		List<String> itemShortDescriptionList = new ArrayList<String>();
		List<String> itemDescriptionList = new ArrayList<String>();
		List<String> itemOtherDetailsList = new ArrayList<String>();
		for (int i = 0; i < numPrice; i++) {
			itemPricesList[i] = new ArrayList<Double>(); // instantiate
		}
		Map<Integer, String> hm = new HashMap<Integer, String>();
		Map<Integer, String> hmDesc = new HashMap<Integer, String>();
		Map<Integer, String> hmOther = new HashMap<Integer, String>();
		// read the values from the file
		int itemsRead = 0;
		for (int index = 0; index < wb.getNumberOfSheets(); index++) { // sheet
																		// iteration
			HSSFSheet sheet = wb.getSheetAt(index);
			for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext()
					&& itemsRead < ApplicationConstants.MAX_BATCH_UPDATE_ITEMS; itemsRead++) { // row
																								// iteration
				Row row = rit.next();
				for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) { // cell
																				// iteration

					Cell cell = cit.next();
					if (cell.getColumnIndex() == 0) { // first column
						String value = "";
						try {
							value = cell.getStringCellValue();
						} catch (NumberFormatException e) {
							try {
								value = cell.getNumericCellValue() + "";
							} catch (Exception ex) {
								value = "";
							}
						}

						if (row.getRowNum() == 0 && value.toLowerCase().contains("sku")) {
							hasHeader = true;
							break; // ignore header row
						}

						if (value.trim().length() == 0) {
							break; // ignore blank row
						}

						itemSKUsList.add(value);
					} else if (cell.getColumnIndex() == 1) { // second column
						String value = "";
						try {
							value = cell.getStringCellValue();
						} catch (NumberFormatException e) {
							try {
								value = cell.getNumericCellValue() + "";
							} catch (Exception ex) {
								value = "";
							}
						}
						itemNamesList.add(value);
					} else if (cell.getColumnIndex() == 2) { // third column
						String value = "";
						try {
							value = cell.getStringCellValue();
						} catch (NumberFormatException e) {
							try {
								value = cell.getNumericCellValue() + "";
							} catch (Exception ex) {
								value = "";
							}
						}
						// System.out.println("VALUE: "+value);
						itemShortDescriptionList.add(value);
						hm.put(itemsRead, value);

					} else if (cell.getColumnIndex() == 3) { // fourth column
						String value = "";
						try {
							value = cell.getStringCellValue();
						} catch (NumberFormatException e) {
							try {
								value = cell.getNumericCellValue() + "";
							} catch (Exception ex) {
								value = " ";
							}
						}

						itemDescriptionList.add(value);
						hmDesc.put(itemsRead, value);
					} else if (cell.getColumnIndex() == 4) { // fifth column
						String value = "";
						try {
							value = cell.getStringCellValue();
						} catch (NumberFormatException e) {
							try {
								value = cell.getNumericCellValue() + "";
							} catch (Exception ex) {
								value = " ";
							}
						}
						itemOtherDetailsList.add(value);
						hmOther.put(itemsRead, value);
					} else if (cell.getColumnIndex() >= 5 && (cell.getColumnIndex() - 4 <= numPrice)) { // >=sixth
																										// column
																										// (prices),
																										// do
																										// not
																										// read
																										// more
																										// than
																										// the
																										// number
																										// of
																										// prices
																										// in
																										// the
																										// database
						// all excel cells that belong to the prices must have a
						// value. or else, the update will be erroneous
						try {
							Double num = cell.getNumericCellValue();
							itemPricesList[cell.getColumnIndex() - 5].add(num);
						} catch (NumberFormatException e) {
							itemPricesList[cell.getColumnIndex() - 5].add(Double.NaN);
						}
					}
				}
			}
		}

		// transfer values to arrays
		itemSKUs = itemSKUsList.toArray(itemSKUs);
		itemNames = itemNamesList.toArray(itemNames);
		itemPrices = new Double[numPrice][itemsRead];
		itemShortDescription = new String[itemsRead];
		itemDescription = new String[itemsRead];
		itemOtherDetails = new String[itemsRead];
		/*
		 * itemShortDescription =
		 * itemShortDescriptionList.toArray(itemShortDescription);
		 * itemDescription = itemDescriptionList.toArray(itemDescription);
		 * itemOtherDetails = itemOtherDetailsList.toArray(itemOtherDetails);
		 */
		// Get Map in Set interface to get key and value
		Set s = hm.entrySet();
		Set sDesc = hmDesc.entrySet();
		Set sOther = hmOther.entrySet();

		// Move next key and value of Map by iterator
		Iterator it = s.iterator();
		Iterator itDesc = sDesc.iterator();
		Iterator itOther = sOther.iterator();

		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			// System.out.println(pairs.getKey() + " = " + pairs.getValue());
			for (int k = 1; k <= itemShortDescriptionList.size(); k++) {
				if (pairs.getValue().toString().contains(itemShortDescriptionList.get(k - 1))) {
					itemShortDescription[Integer.parseInt(pairs.getKey().toString())] = pairs.getValue().toString();
				}
			}

		}
		while (itDesc.hasNext()) {
			Map.Entry pairs = (Map.Entry) itDesc.next();
			// System.out.println(pairs.getKey() + " = " + pairs.getValue());
			for (int k = 1; k <= itemDescriptionList.size(); k++) {
				if (pairs.getValue().toString().contains(itemDescriptionList.get(k - 1))) {
					itemDescription[Integer.parseInt(pairs.getKey().toString())] = pairs.getValue().toString();
				}
			}

		}
		while (itOther.hasNext()) {
			Map.Entry pairs = (Map.Entry) itOther.next();
			// System.out.println(pairs.getKey() + " = " + pairs.getValue());
			for (int k = 1; k <= itemOtherDetailsList.size(); k++) {
				if (pairs.getValue().toString().contains(itemOtherDetailsList.get(k - 1))) {
					itemOtherDetails[Integer.parseInt(pairs.getKey().toString())] = pairs.getValue().toString();
				}
			}

		}

		Double[] temp;
		// System.out.println("SIZE DECRIPTION "+itemDescription.length+ "
		// itemDescriptionList " +itemDescriptionList.size());
		for (int j = 1; j < itemShortDescription.length; j++) {
			// System.out.println("idx "+j+" itemShortDescription[i]
			// "+itemShortDescription[j]);
		}
		for (int i = 0; i < numPrice; i++) {
			temp = new Double[itemsRead];
			itemPrices[i] = itemPricesList[i].toArray(temp);
		}

		itemIds = new String[itemSKUs.length];
		itemActions = new String[itemSKUs.length];
		boolean saveTheExcelFile = false;

		// check for changes per item
		for (int i = 0; i < itemSKUs.length; i++) {
			List<CategoryItem> items = categoryItemDelegate.findAll(company, group, itemSKUs[i]).getList();

			if (items.size() == 1) { // found in the database
				CategoryItem item = items.get(0);
				boolean hasChanges = false;

				itemIds[i] = item.getId() + "";
				itemActions[i] = "";

				// check for errors
				if (itemNames[i].length() == 0) {
					itemIds[i] = "-3";
					itemActions[i] = "Error: Name is required.";
					continue;
				}

				// check for change of name
				if (itemNames[i].compareTo(item.getName()) != 0) {
					itemActions[i] = "Update item name from \"" + item.getName() + "\". ";
					saveTheExcelFile = true;
					hasChanges = true;
					logger.debug("Name is changed");
				}

				String shortDesc = " ";

				if (i < itemShortDescription.length) {
					if (item.getShortDescription() != null) {

						shortDesc = item.getShortDescription();
					}
					// System.out.println("item.getShortDescription()
					// "+shortDesc);
					if (itemShortDescription[i + 1] != null) {
						if (itemShortDescription[i + 1].compareTo(shortDesc) != 0) {
							itemActions[i] = itemActions[i] + " Update item ShortDescription from \"" + shortDesc
									+ "\". ";
							// System.out.println("Action taken in short
							// description:" +itemActions[i]);
							saveTheExcelFile = true;
							hasChanges = true;
							logger.debug("ShortDescription is changed");
						}

					}

				}

				String descStr = " ";
				if (i < itemDescription.length) {
					if (item.getDescription() != null) {
						descStr = item.getDescription();
					}
					if (itemDescription[i + 1] != null) {
						if (itemDescription[i + 1].compareTo(descStr) != 0) {
							itemActions[i] = itemActions[i] + " Update item Description from \"" + descStr + "\". ";
							saveTheExcelFile = true;
							hasChanges = true;
							logger.debug("Description is changed");
						}
					}
				}
				String otherStr = " ";
				if (i < itemOtherDetails.length) {
					if (item.getOtherDetails() != null) {
						otherStr = item.getOtherDetails();
					}
					if (itemOtherDetails[i + 1] != null) {
						if (itemOtherDetails[i + 1].compareTo(otherStr) != 0) {
							itemActions[i] = itemActions[i] + " Update item other details from \"" + otherStr + "\". ";
							// System.out.println("Action taken in other
							// details:" +itemActions[i]);
							saveTheExcelFile = true;
							hasChanges = true;
							logger.debug("Other Details changed");
						}
					}
				}

				Double[] pricesMap = new Double[1];
				pricesMap = getPrices(item).values().toArray(pricesMap);

				// check for price changes
				logger.debug("numPrice:" + numPrice + " pricesMap.length: " + pricesMap.length);
				for (int j = 0; j < numPrice && j < pricesMap.length; j++) {

					if (itemPrices == null || itemPrices[j][i] == null) {
						itemIds[i] = "-4";
						itemActions[i] = "Error: Price is not a number";
						hasChanges = true;
						break;
					}

					if (Double.isNaN(itemPrices[j][i])) {
						itemIds[i] = "-4";
						itemActions[i] = "Error: Price is not a number";
						hasChanges = true;
						break;
					}

					logger.debug("Compare db price " + pricesMap[j] + " with excel price " + itemPrices[j][i]);
					if (itemPrices[j][i].compareTo(pricesMap[j]) != 0) { // check
																			// for
																			// change
																			// of
																			// prices
						try {
							itemActions[i] = itemActions[i] + "Update item price \""
									+ categoryItemPriceNames.get(j).getName() + "\" from " + pricesMap[j] + " to "
									+ itemPrices[j][i] + ". ";
						} catch (Exception e) {
							itemActions[i] = itemActions[i] + "Update item price from " + pricesMap[j] + " to "
									+ itemPrices[j][i] + ". ";
						}
						saveTheExcelFile = true;
						hasChanges = true;
					}
				}

				if (!hasChanges) {
					itemIds[i] = "-5";
					itemActions[i] = "No changes.";
				}
			} else if (items.size() == 0) {
				itemIds[i] = "-2";
				itemActions[i] = "Error: SKU not found.";
			} else {
				itemActions[i] = "Error: More than 1 item matches the given SKU.";
				itemIds[i] = "-1";
			}
		}

		servletContext.setAttribute("com.ivant.cms.action.admin.GroupAction.itemPrices", itemPrices);
		if (saveTheExcelFile) {
			servletContext.setAttribute("com.ivant.cms.action.admin.GroupAction.excelFile", files[0]);
			servletContext.setAttribute("com.ivant.cms.action.admin.GroupAction.excelName", filenames[0]);
			servletContext.setAttribute("com.ivant.cms.action.admin.GroupAction.excelContentType", contentTypes[0]);
		}

		return Action.SUCCESS;
	}

	private HashMap<Long, Double> getPrices(CategoryItem item) {
		List<CategoryItemPriceName> names = item.getParentGroup().getCategoryItemPriceNames();
		HashMap<Long, Double> prices = new HashMap<Long, Double>();

		prices.put(0l, item.getPrice()); // default price
		if (names != null && names.size() > 1) {
			for (int i = 1; i < names.size(); i++) {
				CategoryItemPrice price = categoryItemPriceDelegate.findByCategoryItemPriceName(company, item,
						names.get(i));
				if (price != null) {
					prices.put(price.getId(), price.getAmount());
				} else {
					prices.put((long) (names.get(i).getId() * -1), 0.0); // the
																			// price
																			// is
																			// not
																			// in
																			// the
																			// database.
																			// insert
																			// later
					logger.info("getPrices() the price " + names.get(i).getName() + " for " + item.getSku()
							+ " is not in the database.");
				}
			}
		}
		logger.debug("Price map: " + prices.toString());
		return prices;
	}

	private List<Long> getPricesId(CategoryItem item) {
		List<CategoryItemPriceName> names = item.getParentGroup().getCategoryItemPriceNames();
		List<Long> ids = new ArrayList<Long>();

		ids.add(0l);
		if (names != null && names.size() > 1) {
			for (int i = 1; i < names.size(); i++) {
				CategoryItemPrice price = categoryItemPriceDelegate.findByCategoryItemPriceName(company, item,
						names.get(i));
				if (price != null) {
					ids.add(price.getId());
				} else {
					ids.add((long) (names.get(i).getId() * -1));
				}
			}
		}

		logger.debug("Price Id map: " + ids.toString());
		return ids;
	}

	public String sampleExcelFile() {
		group = groupDelegate.find(Long.parseLong(request.getParameter("group_id")));
		List<CategoryItem> items = categoryItemDelegate.findFirstTenItemsByGroup(company, group).getList();
		List<CategoryItemPriceName> names = group.getCategoryItemPriceNames();
		int numPrices = (names.size() > 0) ? names.size() : 1;

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(group.getName());
		HSSFRow row = sheet.createRow((short) 0);

		row.createCell((short) 0).setCellValue("SKU");
		row.createCell((short) 1).setCellValue("Name");
		row.createCell((short) 2).setCellValue("Short Description");
		row.createCell((short) 3).setCellValue("Description");
		row.createCell((short) 4).setCellValue("Other Details");
		if (names.size() == 0) {
			row.createCell((short) 5).setCellValue("Price");
		} else {
			for (int i = 0; i < names.size(); i++) {
				row.createCell((short) (i + 5)).setCellValue(names.get(i).getName());
			}
		}

		for (int i = 0; i < items.size(); i++) {
			CategoryItem item = items.get(i);
			row = sheet.createRow((short) (i + 1));
			row.createCell((short) 0).setCellValue(item.getSku());
			row.createCell((short) 1).setCellValue(item.getName());

			if (item.getShortDescription() != null) {
				row.createCell((short) 2).setCellValue(item.getShortDescription().replaceAll("<(.*?)>", "\n"));
				// System.out.println("HEHEHE
				// "+item.getShortDescription().replaceAll("<(.*?)>","\n"));
			}
			if (item.getDescription() != null) {
				row.createCell((short) 3).setCellValue(item.getDescription().replaceAll("<(.*?)>", "\n"));

			}
			if (item.getOtherDetails() != null) {
				row.createCell((short) 4).setCellValue(item.getOtherDetails().replaceAll("<(.*?)>", "\n"));

			} /*
				 * else{ row.createCell((short) 2).setCellValue(" ");
				 * row.createCell((short) 3).setCellValue(" ");
				 * row.createCell((short) 4).setCellValue(" ");
				 * 
				 * }
				 */
			Double[] pricesMap = new Double[1];
			pricesMap = getPrices(item).values().toArray(pricesMap);

			for (int j = 0; j < names.size(); j++) {
				HSSFCell cell = row.createCell((short) (j + 5));
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				try {
					cell.setCellValue(pricesMap[j].doubleValue());
				} catch (ArrayIndexOutOfBoundsException e) {
					cell.setCellValue(0);
				}
			}
		}

		File tempFile = null;
		try {
			tempFile = File.createTempFile("sampleExcelFileBatchUpdate", "xls");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		FileOutputStream fOutStream = null;
		try {
			fOutStream = new FileOutputStream(tempFile); // dapat
															// fileinputstream
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Action.ERROR;
		}

		try {
			wb.write(fOutStream);
		} catch (IOException e) {
			e.printStackTrace();
			return Action.ERROR;
		}

		try {
			fOutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fInStream = new FileInputStream(tempFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		contentLength = tempFile.length();
		fileName = company.getName() + "_excel_template.xls";
		tempFile.deleteOnExit();

		return Action.SUCCESS;
	}

	/* end of batch update */

	private void saveCategoryItemPriceNames(Group group) {
		List<CategoryItemPriceName> priceNames = group.getCategoryItemPriceNames();
		if (priceNames != null) {
			for (CategoryItemPriceName priceName : priceNames) {
				String tempName = request.getParameter("n" + priceName.getId());
				if (tempName != null) {
					priceName.setName(tempName);
					categoryItemPriceNameDelegate.update(priceName);
				}
			}
		}

		int counter = 1;
		while (counter < 11) {
			if (StringUtils.isNotEmpty(request.getParameter("pn" + counter))) {
				CategoryItemPriceName priceName = new CategoryItemPriceName();
				priceName.setCompany(company);
				priceName.setGroup(group);
				priceName.setName(request.getParameter("pn" + counter));
				priceName.setIsValid(Boolean.TRUE);

				categoryItemPriceNameDelegate.insert(priceName);
			}
			counter++;
		}
	}

	private void saveOtherFields(Group group) {
		List<OtherField> otherFields = group.getOtherFields();
		if (otherFields != null) {
			for (OtherField otherField : otherFields) {

				String tempName = request.getParameter("o" + otherField.getId());
				if (tempName != null) {
					otherField.setName(tempName);
					otherFieldDelegate.update(otherField);

					saveOtherFieldValues(otherField, otherField.getId(), Boolean.FALSE);
				}
			}
		}

		int counter = 1;
		while (counter <= company.getCompanySettings().getMaxOtherFields()) {
			if (StringUtils.isNotEmpty(request.getParameter("on" + counter))) {
				OtherField otherField = new OtherField();
				otherField.setCompany(company);
				otherField.setGroup(group);
				otherField.setName(request.getParameter("on" + counter));
				otherField.setIsValid(Boolean.TRUE);

				otherField = otherFieldDelegate.find(otherFieldDelegate.insert(otherField));

				saveOtherFieldValues(otherField, new Long(counter), Boolean.TRUE);
			}
			counter++;
		}
	}

	private void saveOtherFieldValues(OtherField otherField, Long identifier, Boolean isNew) {

		String prefix = (isNew) ? "onv" : "ov";

		if (!isNew && otherField.getOtherFieldValueList() != null) {

			for (OtherFieldValue value : otherField.getOtherFieldValueList()) {
				otherFieldValueDelegate.delete(value);
			}

		}
		// System.out.println("IDENTIFIER: "+prefix+identifier);
		String[] values = (String[]) request.getParameterValues(prefix + identifier);
		// System.out.println(request.getParameterValues(prefix+identifier));
		if (values != null) {
			for (String value : values) {
				OtherFieldValue newFieldValue = new OtherFieldValue();
				newFieldValue.setValue(value);
				newFieldValue.setOtherField(otherField);

				otherFieldValueDelegate.insert(newFieldValue);
			}
		}
	}

	public String save() {
		if (request.getParameter("language") == null || request.getParameter("language").isEmpty()) {
			if (StringUtils.isEmpty(request.getParameter("itemHasPrice")))
				group.setItemHasPrice(false);
			else
				group.setItemHasPrice(Boolean.valueOf(request.getParameter("itemHasPrice")));
			if (!commonParamsValid()) {
				return Action.ERROR;
			}

			if (groupDelegate.find(user.getCompany(), group.getName()) == null) {
				// System.out.println("Has item price:
				// "+group.getItemHasPrice());
				group = groupDelegate.find(groupDelegate.insert(group));
			} else {
				if (group.getId() == null) {
					errorType = "groupexist";
					return ERROR;
				}
				// System.out.println("Has item price:
				// "+group.getItemHasPrice());
				groupDelegate.update(group);
			}
			if ((group.getItemHasPrice() != null) ? group.getItemHasPrice() : false)
				saveCategoryItemPriceNames(group);

			if (company.getCompanySettings().getHasOtherFields()) {
				saveOtherFields(group);
			}

			lastUpdatedDelegate.saveLastUpdated(company);
		} else {
			// if category doesnt exist(insert)
			GroupLanguage groupLanguage = group.getGroupLanguage();

			if (groupLanguage == null) {
				group.setLanguage(null);
				groupLanguage = new GroupLanguage();
				groupLanguage.cloneOf(group);
				groupLanguage.setLanguage(languageDelegate.find(request.getParameter("language"), company));
				groupDelegate.refresh(group);
				groupLanguage.setDefaultGroup(group);
				groupLanguageDelegate.insert(groupLanguage);
			}
			// if page exist (update)
			else {
				groupLanguage.cloneOf(group);
				groupDelegate.refresh(group);
				groupLanguage.setDefaultGroup(group);
				groupLanguageDelegate.update(groupLanguage);

			}
		}

		if (company.getName().equals("neltex") && files != null) {
			uploadImage();
		}

		return Action.SUCCESS;
	}

	public String delete() {
		if (!commonParamsValid()) {
			return Action.ERROR;
		}
		if (!group.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}

		groupDelegate.delete(group);

		lastUpdatedDelegate.saveLastUpdated(company);

		return Action.SUCCESS;
	}

	public String edit() {
		if (!commonParamsValid()) {
			return Action.ERROR;
		}
		if (!group.getCompany().equals(user.getCompany())) {
			return Action.ERROR;
		}

		return Action.SUCCESS;
	}

	private boolean commonParamsValid() {
		if (user.getUserType() != UserType.SUPER_USER && user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			// System.out.println("here1");
			return false;
		}
		if (user.getCompany() == null) {
			// System.out.println("here2");
			return false;
		}
		if (group == null) {
			// System.out.println("here3");
			return false;
		}
		// System.out.println("here=========================================");
		return true;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getErrorType() {
		return errorType;
	}

	/**
	 * @param filenames
	 *            the filenames to set
	 */
	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}

	/**
	 * @return the filenames
	 */
	public String[] getFilenames() {
		return filenames;
	}

	/**
	 * @param contentTypes
	 *            the contentTypes to set
	 */
	public void setContentTypes(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}

	/**
	 * @return the contentTypes
	 */
	public String[] getContentTypes() {
		return contentTypes;
	}

	/**
	 * @param contentLength
	 *            the contentLength to set
	 */
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	/**
	 * @return the contentLength
	 */
	public long getContentLength() {
		return contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String uploadImage() {
		if (!commonParamsValid()) {
			// System.out.println("11111111111");
			return Action.ERROR;
		}

		if (!company.equals(group.getCompany())) {
			// System.out.println("22222222222222222");
			return Action.ERROR;
		}
		// System.out.println(files);
		// System.out.println(filenames);
		// System.out.println(contentTypes);

		saveImages(files, filenames, contentTypes);

		lastUpdatedDelegate.saveLastUpdated(company);

		return Action.SUCCESS;
	}

	public String deleteImage() {
		if (!commonParamsValid()) {
			return Action.ERROR;
		}

		if (!company.equals(group.getCompany())) {
			// System.out.println("hereNOOOOOOOOO");
			return Action.ERROR;
		}

		String destinationPath = servletContext.getRealPath("companies" + File.separator + company.getName()
				+ File.separator + "images" + File.separator + "items");

		// logger.info("CategoryImage " + category.getImages()getcategoryImage);
		logger.info("-->group::::: " + group);
		// System.out.println("------------------------------------" +
		// image_id);
		groupImage = groupImageDelegate.find(new Long(image_id));
		if (groupImage != null) {
			logger.info("groupImage1: " + groupImage.getImage1());
			logger.info("groupImage2: " + groupImage.getImage2());
			logger.info("groupImage3: " + groupImage.getImage3());

			// delete original
			FileUtil.deleteFile(destinationPath + File.separator + groupImage.getOriginal());
			// delete image1
			FileUtil.deleteFile(destinationPath + File.separator + groupImage.getImage1());
			// delete image2
			FileUtil.deleteFile(destinationPath + File.separator + groupImage.getImage2());
			// delete image2
			FileUtil.deleteFile(destinationPath + File.separator + groupImage.getImage3());
			// delete thumbnail
			FileUtil.deleteFile(destinationPath + File.separator + groupImage.getThumbnail());

			groupImageDelegate.delete(groupImage);
		}
		company = group.getCompany();
		lastUpdatedDelegate.saveLastUpdated(company);

		return Action.SUCCESS;
	}

	private void saveImages(File[] files, String[] filenames, String[] contentTypes) {
		// System.out.println("Save images..." + files);
		// System.out.println(filenames.length + " - " + files.length);
		// System.out.println("filenames.size" + filenames.length);
		// System.out.println("files.size" + files.length);
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "items";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));

		// create the image1, image2, image3 and thumbnail folders
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image1"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image2"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image3"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "thumbnail"));

		destinationPath = servletContext.getRealPath(destinationPath);

		Long companyId = company.getId();

		for (int i = 0; i < files.length; i++) {
			if (files[i].exists()) {
				String source = files[i].getAbsolutePath();

				String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"),
						String.valueOf(new Date().getTime()));
				filename = FileUtil.replaceExtension(filename, "jpg");
				// generate original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				FileUtil.copyFile(files[i], origFile);

				// generate image 1
				ImageUtil.generateJpegImage(companyId, source,
						destinationPath + File.separator + "image1" + File.separator + filename,
						companySettings.getImage1Width(), companySettings.getImage1Heigth(),
						ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());

				// generate image 2
				ImageUtil.generateJpegImage(companyId, source,
						destinationPath + File.separator + "image2" + File.separator + filename,
						companySettings.getImage2Width(), companySettings.getImage2Heigth(),
						ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());

				// generate image 3
				ImageUtil.generateJpegImage(companyId, source,
						destinationPath + File.separator + "image3" + File.separator + filename,
						companySettings.getImage3Width(), companySettings.getImage3Heigth(),
						ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());

				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source,
						destinationPath + File.separator + "thumbnail" + File.separator + filename);

				GroupImage groupImage = new GroupImage();
				groupImage.setFilename(filenames[i]);
				groupImage.setGroup(group);
				// itemImage.setUrl(UrlUtil.generateImageUploadUrl(company) +
				// "items/");
				groupImage.setOriginal("original/" + filename);
				groupImage.setImage1("image1/" + filename);
				groupImage.setImage2("image2/" + filename);
				groupImage.setImage3("image3/" + filename);
				groupImage.setThumbnail("thumbnail/" + filename);

				groupImageDelegate.insert(groupImage);
			}
		}
	}

	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}

	public GroupImage getGroupImage() {
		return groupImage;
	}

	public void setGroupImage(GroupImage groupImage) {
		this.groupImage = groupImage;
	}

	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	// upload methods

	public void setUpload(File[] files) {
		this.files = files;
	}

	public void setUploadContentType(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}

	public void setUploadFileName(String[] filenames) {
		this.filenames = filenames;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String[] getItemShortDescription() {
		return itemShortDescription;
	}

	public void setItemShortDescription(String[] itemShortDescription) {
		this.itemShortDescription = itemShortDescription;
	}

	public String[] getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String[] itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String[] getItemOtherDetails() {
		return itemOtherDetails;
	}

	public void setItemOtherDetails(String[] itemOtherDetails) {
		this.itemOtherDetails = itemOtherDetails;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public InputStream getfInStream() {
		return fInStream;
	}

	public void setfInStream(InputStream fInStream) {
		this.fInStream = fInStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
