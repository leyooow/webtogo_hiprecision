package com.ivant.cms.action.admin;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.db.MixAndMatchDAO;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemComponentDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.ComponentCategoryDelegate;
import com.ivant.cms.delegate.ComponentDelegate;
import com.ivant.cms.delegate.ItemImageDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemComponent;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Component;
import com.ivant.cms.entity.ComponentCategory;
import com.ivant.cms.entity.ItemImage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.wrapper.PaintCategoryWrapper;
import com.ivant.cms.wrapper.PaintWrapper;
import com.ivant.constants.BoysenConstants;
import com.ivant.constants.CompanyConstants;
import com.ivant.constants.DutchboyConstants;
import com.ivant.utils.ReflectionUtil;
import com.ivant.utils.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class DatabaseAction
		extends ActionSupport
		implements CompanyAware, ServletContextAware
{
	private static final long serialVersionUID = 1L;
	
	private static final Long BOYSEN_FAQ_MULTIPAGE_ID = 1686L;
	
	// Dutchboy multipage id added by cas
	private static final Long DUTCHBOY_FAQ_MULTIPAGE_ID = 2426L;
	private static final Long DUTCHBOY_PAINTING_101_MULTIPAGE_ID = 2425L;
	private static final Long DUTCHBOY_NEWS_MULTIPAGE_ID = 2414L; 
	private static final Long DUTCHBOY_HOWS_TO_MULTIPAGE_ID = 2413L; 
	
	private static final String OPENING_STRING = "\"(";
	private static final String CLOSING_STRING = ");\",";
	private static final String COMMA = ",";
	private static final String NEW_LINE = "\n";
	
	private final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private final ItemImageDelegate itemImageDelegate = ItemImageDelegate.getInstance();
	private final ComponentCategoryDelegate componentCategoryDelegate = ComponentCategoryDelegate.getInstance();
	private final ComponentDelegate componentDelegate = ComponentDelegate.getInstance();
	private final CategoryItemComponentDelegate categoryItemComponentDelegate = 
			CategoryItemComponentDelegate.getInstance();
	private final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private final MixAndMatchDAO mixAndMatchDAO = new MixAndMatchDAO(BoysenConstants.DB_HOST_NEW);
	
	private Company company;
	
	private ServletContext servletContext;
	private InputStream inputStream;
	private String fileName;
	private Long contentLength;
	
	public String execute() throws IOException
	{
		return SUCCESS;
	}
	
	public String createBoysenAppDB() throws IOException
	{
		if(company != null && (CompanyConstants.BOYSEN == company.getId() || 
				CompanyConstants.BOYSEN_NEW == company.getId()))
		{
			createDatabaseData();
		}
		
		return SUCCESS;
	}
	
	public String downloadBoysenAppDB() throws IOException
	{
		if(company != null && (CompanyConstants.BOYSEN == company.getId() || 
				CompanyConstants.BOYSEN_NEW == company.getId()))
		{
			File file = createDatabaseData();
			inputStream = new FileInputStream(file);
			fileName = generateFileName();
			contentLength = file.length();
		}
		
		return SUCCESS;
	}
	
	public String downloadDutchboyAppDB() throws  IOException {
		// added by cas
		if(company != null && (CompanyConstants.DUTCHBOY == company.getId())) {
			File file = createDutchboyDatabaseData();
			inputStream = new FileInputStream(file);
			fileName = generateDutchboyFileName();
			contentLength = file.length();
		}
		
		return SUCCESS;
	}
	
	private File createDatabaseData() throws IOException 
	{
		List<Category> categoryList = categoryDelegate.findAll(company).getList();
		List<CategoryItem> categoryItemList = categoryItemDelegate.findAll(company).getList();
		//item image
		List<ItemImage> itemImageList = itemImageDelegate.findAllByItems(categoryItemList);
		List<CategoryItemComponent> categoryItemComponentList = categoryItemComponentDelegate.findAll();
		List<Component> componentList = componentDelegate.findAll(company).getList();
		List<ComponentCategory> componentCategoryList = componentCategoryDelegate
				.findAll(company, Order.asc("id")).getList();
		List<SinglePage> faqList = singlePageDelegate.findAll(company, 
				multiPageDelegate.find(BOYSEN_FAQ_MULTIPAGE_ID)).getList();
		List<PaintCategoryWrapper> paintCategoryList = mixAndMatchDAO.getAllPaintCategory();
		List<PaintWrapper> paintList = mixAndMatchDAO.getAllPaint();
		
		File categoryFile = writeToFile(categoryList, BoysenConstants.FIELDS_CATEGORY, 
				BoysenConstants.FILENAME_CATEGORY);
		File categoryItemFile = writeToFile(categoryItemList, BoysenConstants.FIELDS_CATEGORY_ITEM, 
				BoysenConstants.FILENAME_CATEGORY_ITEM);
		File itemImageFile = writeToFile(itemImageList, BoysenConstants.FIELDS_ITEM_IMAGES,
				BoysenConstants.FILENAME_ITEM_IMAGES);
		File categoryItemComponentFile = writeToFile(categoryItemComponentList, 
				BoysenConstants.FIELDS_CATEGORY_ITEM_COMPONENT, BoysenConstants.FILENAME_CATEGORY_ITEM_COMPONENT);
		File componentFile = writeToFile(componentList, BoysenConstants.FIELDS_COMPONENT, 
				BoysenConstants.FILENAME_COMPONENT);
		File componentCategoryFile = writeToFile(componentCategoryList, BoysenConstants.FIELDS_COMPONENT_CATEGORY, 
				BoysenConstants.FILENAME_COMPONENT_CATEGORY);
		File faqFile = writeToFile(faqList, BoysenConstants.FIELDS_FAQ, BoysenConstants.FILENAME_FAQ);
		File paintCategoryFile = writeToFile(paintCategoryList, BoysenConstants.FIELDS_PAINT_CATEGORY,
				BoysenConstants.FILENAME_PAINT_CATEGORY);
		File paintFile = writeToFile(paintList, BoysenConstants.FIELDS_PAINT, BoysenConstants.FILENAME_PAINT);
	
		File destinationFile = new File(getDir(), generateFileName());
		zipFiles(destinationFile, new File[] { 
				categoryFile, categoryItemFile, itemImageFile, categoryItemComponentFile, 
				componentFile, componentCategoryFile, faqFile, paintCategoryFile, paintFile});
		
		return destinationFile;
	}
	
	public File createDutchboyDatabaseData() throws IOException {
		// added by cas
		List<Category> categoryList = categoryDelegate.findAll(company, false).getList();
		List<CategoryItem> categoryItemList = categoryItemDelegate.findAllEnabled(company).getList();
		//item image
		List<ItemImage> itemImageList = itemImageDelegate.findAllByItems(categoryItemList);
		List<CategoryItemComponent> categoryItemComponentList = categoryItemComponentDelegate.findAll();
		List<Component> componentList = componentDelegate.findAll(company).getList();
		List<ComponentCategory> componentCategoryList = componentCategoryDelegate
				.findAll(company, Order.asc("id")).getList();
		// Dutchboy multipage items
		List<SinglePage> faqList = singlePageDelegate.findAll(company, 
				multiPageDelegate.find(DUTCHBOY_FAQ_MULTIPAGE_ID)).getList();
		List<SinglePage> painting101List = singlePageDelegate.findAll(company, 
				multiPageDelegate.find(DUTCHBOY_PAINTING_101_MULTIPAGE_ID)).getList();
		List<SinglePage> newsList = singlePageDelegate.findAll(company, 
				multiPageDelegate.find(DUTCHBOY_NEWS_MULTIPAGE_ID)).getList();
		List<SinglePage> howTosList = singlePageDelegate.findAll(company, 
				multiPageDelegate.find(DUTCHBOY_HOWS_TO_MULTIPAGE_ID)).getList();
		Map<String, CategoryItemComponent> componentsMap = new HashMap<String, CategoryItemComponent>();
		List<CategoryItemComponent> newCategoryItemComponentList = new ArrayList<CategoryItemComponent>();
		for(CategoryItemComponent catItemComp : categoryItemComponentList) {
			for(CategoryItem categoryItem : categoryItemList) {
				List<CategoryItemComponent> components = categoryItem.getCategoryItemComponentList();
				for(CategoryItemComponent component : components) {
					if(catItemComp.getId() == component.getId()) {
						if(componentsMap.get(catItemComp.getId().toString()) != null) {
						} else {
							componentsMap.put(catItemComp.getId().toString(), component);
							newCategoryItemComponentList.add(catItemComp);
						}
					}
				}
			}
		}
		
		File categoryFile = writeToFile(categoryList, DutchboyConstants.FIELDS_CATEGORY, 
				DutchboyConstants.FILENAME_CATEGORY);
		File categoryItemFile = writeToFile(categoryItemList, DutchboyConstants.FIELDS_CATEGORY_ITEM, 
				DutchboyConstants.FILENAME_CATEGORY_ITEM);
		File itemImageFile = writeToFile(itemImageList, DutchboyConstants.FIELDS_ITEM_IMAGES,
				DutchboyConstants.FILENAME_ITEM_IMAGES);
		File categoryItemComponentFile = writeToFile(newCategoryItemComponentList, 
				DutchboyConstants.FIELDS_CATEGORY_ITEM_COMPONENT, DutchboyConstants.FILENAME_CATEGORY_ITEM_COMPONENT);
		File componentFile = writeToFile(componentList, DutchboyConstants.FIELDS_COMPONENT, 
				DutchboyConstants.FILENAME_COMPONENT);
		File componentCategoryFile = writeToFile(componentCategoryList, DutchboyConstants.FIELDS_COMPONENT_CATEGORY, 
				DutchboyConstants.FILENAME_COMPONENT_CATEGORY);
		File faqFile = writeToFile(faqList, DutchboyConstants.FIELDS_MULTIPAGE_ITEM, 
				DutchboyConstants.FILENAME_FAQ);
		File painting101File = writeToFile(painting101List, DutchboyConstants.FIELDS_MULTIPAGE_ITEM, 
				DutchboyConstants.FILENAME_PAINTING101);
		File newsFile = writeToFile(newsList, DutchboyConstants.FIELDS_MULTIPAGE_ITEM, 
				DutchboyConstants.FILENAME_NEWS);
		File howsToFile = writeToFile(howTosList, DutchboyConstants.FIELDS_MULTIPAGE_ITEM, 
				DutchboyConstants.FILENAME_HOWSTO);
		
		
		File destinationFile = new File(getDir(), generateDutchboyFileName());
		zipFiles(destinationFile, new File[] { 
				categoryFile, categoryItemFile, itemImageFile,categoryItemComponentFile, 
				componentFile, componentCategoryFile, faqFile, painting101File, newsFile, howsToFile});
		
		return destinationFile;
	}
	
	public void zipFiles(File destinationFile, File[] files) throws IOException
	{
		
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destinationFile));
		
		byte bytes[] = new byte[2048];
		
		for(File file : files)
		{
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			zos.putNextEntry(new ZipEntry(file.getName()));
			
			int bytesRead;
			while((bytesRead = bis.read(bytes)) != -1) {
				zos.write(bytes, 0, bytesRead);
			}
			zos.closeEntry();
			bis.close();
			fis.close();
		}
		
		zos.flush();
		zos.close();
	}
	
	public byte[] zipFilesToByteArray(File[] files) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		byte bytes[] = new byte[2048];
		
		for(File file : files)
		{
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			zos.putNextEntry(new ZipEntry(file.getName()));
			
			int bytesRead;
			while((bytesRead = bis.read(bytes)) != -1) {
				zos.write(bytes, 0, bytesRead);
			}
			zos.closeEntry();
			bis.close();
			fis.close();
		}
		
		zos.flush();
		baos.flush();
		zos.close();
		baos.close();
		
		return baos.toByteArray();
	}
	
	@SuppressWarnings("unchecked")
	private <T extends BaseObject> File writeToFile(List<T> list, String[] columns, String fileName)
	{
		PrintWriter pw = null;
		File file = new File(getDir(), fileName);
		StringBuilder sb_final = new StringBuilder("");
		try {
			//pw = new PrintWriter(new FileWriter(file));
			
			for(T data : list)
			{
				//pw.print(OPENING_STRING);
				sb_final.append(OPENING_STRING);
				
				StringBuffer sb = new StringBuffer();
				
				for(String columnName: columns)
				{
					
					Object fieldObject = ReflectionUtil.getValueByMethodName(data, columnName);
					
					String value = StringUtil.fixDisplay(getObjectValueToString(fieldObject), columnName);
					/**
					 * modified by john paul cas
					 */
					if(columnName.equals("getCategoryItemOtherFieldMap")) {
						try {
							value = ((Map<String, CategoryItemOtherField>) fieldObject).get("Background").getContent();
						} catch (Exception e) {
							value = null;
						}	
					} 
					
					if(value != null)
						sb.append(StringUtil.encloseWithDoubleQuote(value));
					else
						sb.append(value);
					
					sb.append(COMMA);
				}
				
				//pw.print(StringUtil.removeLastComma(sb.toString()));
				sb_final.append(StringUtil.removeLastComma(sb.toString()));
				//pw.print(CLOSING_STRING);
				sb_final.append(CLOSING_STRING);
				//pw.print(NEW_LINE);
				sb_final.append(NEW_LINE);
			}
			
			//pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//if(pw != null) pw.close();
		}
		
		System.out.println(file.getAbsolutePath() + " done!");
		try {
			FileUtils.writeStringToFile(file, sb_final.toString(), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	private String getObjectValueToString(Object fieldObject)
	{
		if(fieldObject == null)
			return null;
		
		if(fieldObject instanceof String)
			return (String) fieldObject;
		else if(fieldObject instanceof Long)
			return String.valueOf((Long) fieldObject);
		else if(fieldObject instanceof Integer)
			return String.valueOf((Integer) fieldObject);
		else if(fieldObject instanceof Date)
			return ((Date) fieldObject).toString();
		else if(fieldObject instanceof Boolean)
			return ((Boolean) fieldObject).toString();
		else if(fieldObject instanceof Double)
			return String.valueOf((Double) fieldObject);
		else if(fieldObject instanceof BaseObject)
			return ((BaseObject) fieldObject).getId().toString();
		
		return "CLASSNAME : " + fieldObject.getClass().getName();
	}
	
	private String generateFileName()
	{
		return "boysen_db_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".zip";
	}
	
	private String generateDutchboyFileName() {
		return "dutchboy_db_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".zip";
	}
	
	private String getDir() 
	{
		String directory = servletContext.getRealPath("companies" + 
				File.separator + (company.getName().equalsIgnoreCase("boysen") 
						?  "boysennewdesign" : company.getName()) +
				File.separator + "images" +
				File.separator + "database");
		
		File dir = new File(directory);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		
		return directory;
	}

	@Override
	public void setCompany(Company company) 
	{
		this.company = company;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Long getContentLength() {
		return contentLength;
	}

	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}
}
