package com.ivant.cms.action.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.utils.HTMLTagStripper;

@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class MobileAPIAction extends AbstractBaseAction 
		implements ServletResponseAware
{
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(MobileAPIAction.class);
	
	private final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private final CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	
	private static final String URL_HI_PRECISION = "www.hi-precision.com.ph";
	private static final String DATA = "data";
	private static final String ERRORS = "errors";
	private static final String MESSAGE = "message";
	private static final long HIPRECISION_COMPANY_ID = 66;
	
	private static final String HEALTH_WATCH_PAGE_NAME = "health watch";
	
	private static final Map<String, String> CONTENT_MAP = new HashMap<String, String>();
	private static final String CONTENT_VALUE_DELIMITER = "/";
	private static final String CONTENT_DELIMITER = ":";
	private static final String LINE_BREAK_KEYWORD = "brKwrd";
	
	private static final String CONTENT_ADDRESS_KEY = "Address".concat(CONTENT_DELIMITER);
	private static final String CONTENT_TELNO_KEY = "Tel. No".concat(CONTENT_DELIMITER);
	private static final String CONTENT_CELLPHONE_KEY = "Cellphone".concat(CONTENT_DELIMITER);
	private static final String CONTENT_LOCATION_KEY = "Location".concat(CONTENT_DELIMITER); 
	private static final String CONTENT_OFFICEHOURS_KEY = "Office Hours".concat(CONTENT_DELIMITER);
	private static final String CONTENT_SERVICES_KEY = "Services Offered".concat(CONTENT_DELIMITER);
	private static final Set<String> CONTENT_KEYSETS = new HashSet<String>();
	{
		CONTENT_KEYSETS.add(CONTENT_ADDRESS_KEY);
		CONTENT_KEYSETS.add(CONTENT_TELNO_KEY);
		CONTENT_KEYSETS.add(CONTENT_CELLPHONE_KEY);
		CONTENT_KEYSETS.add(CONTENT_LOCATION_KEY);
		CONTENT_KEYSETS.add(CONTENT_OFFICEHOURS_KEY);
		CONTENT_KEYSETS.add(CONTENT_SERVICES_KEY);
	}
	private static final String TEST_PROCEDURES_GROUP_NAME = "Test Procedures";
	
	// keys
	private static final String PURPOSE_KEY = "PURPOSE";
	private static final String INTENDED_USE_KEY = "INTENDED USE";
	
	private static final String PATIENT_PREPARATION_KEY = "SPECIAL INSTRUCTIONS";
	private static final String SPECIAL_INSTRUCTIONS_KEY = "SPECIAL INSTRUCTIONS/PATIENT PREPARATIONS";
	
	private static final String SECTION_KEY = "SECTION";
	private static final String LABORATORY_SECTION_KEY = "LABORATORY SECTION";
	
	private static final String METHODOLOGY_KEY = "METHODOLOGY";

	private static final String VOLUME_REQ_KEY = "VOLUME REQUIREMENT";
	private static final String SPECIMEN_AND_VOLUME_REQUIREMENT_KEY = "SPECIMEN AND VOLUME REQUIREMENT";
	
	private static final String SPECIMEN_CONTAINER_KEY = "SPECIMEN CONTAINER";
	private static final String COLLECTION_SAMPLE_CONTAINER_KEY = "COLLECTION/SAMPLE CONTAINER"; 
	
	private static final String SPECIMEN_REJECTION_KEY = "REJECTION CRITERIA";
	
	private static final String STORAGE_AND_TRANSPORT_KEY = "TRANSPORT TEMPERATURE";
	private static final String RUNNING_DAY_KEY = "RUNNING DAY";
	private static final String CUT_OFF_TIME_KEY = "CUT OFF TIME";
	
	private static final String TIME_OF_RELEASE_KEY = "TIME OF RELEASE";
	private static final String TAT_RELEASING_OF_RESULTS_KEY = "TAT/RELEASING OF RESULTS";
	
	private static final String ROOM_TEMPERATURE_KEY = "ROOM TEMPERATURE (15-25°C)";
	private static final String REFRIGERATED_TEMPERATURE_KEY = "REFRIGERATED TEMPERATURE (2-8°C)";	
	private static final String FREEZER_TEMPERATURE_KEY = "FREEZER TEMPERATURE (-20°C)";
	
	private static final String OTHER_TEST_REQUEST_NAME_KEY = "OTHER TEST REQUEST NAME";
	private static final String ALTERNATIVE_SPECIMEN_AND_VOLUME_REQUIREMENT_KEY = "ALTERNATIVE SPECIMEN AND VOLUME REQUIREMENT";
	private static final String SPECIMEN_STABILITY_KEY = "SPECIMEN STABILITY";
	private static final String REFERENCE_INTERVAL_KEY = "REFERENCE INTERVAL/RESULT INTERPRETATION";
	private static final String LIMITATIONS_INTERFERENCES_KEY = "LIMITATIONS/INTERFERENCES";
	private static final String FREQUENTLY_ASKED_QUESTIONS_KEY = "FREQUENTLY ASKED QUESTIONS (FAQS)";
	private static final String RELATED_WORDS_TEST_KEY = "RELATED WORDS";	
	
	// -keys
	
	private Company company;
	private Category category;
	private Group group;
	
	private Map session;
	private Long groupId;
	private Long categoryId;
	private Long subCategoryId;
	private Integer pageNumber;
	private Integer resultsPerPage;
	private String searchKeyword;
	private String pageName;
	private InputStream inputStream;
	private HttpServletResponse response;
	
	private JSONObject json;
	private JSONArray dataArray;
	private JSONArray errorsArray;
	
	@Override
	public void prepare() throws Exception
	{
		json = new JSONObject();
		dataArray = new JSONArray();
		errorsArray = new JSONArray();
	}
	
	public String getCategories()
	{
		logger.info("getCategories invoked...");
		
		if(company != null && groupId != null)
		{
			group = groupDelegate.find(groupId);
			
			List<Category> categoryList = categoryDelegate.findAll(company, checkCategory(), group, 
					false, false).getList();
			
			if(categoryList.isEmpty())
				addJSONError("No data found");
			
			for(Category category: categoryList)
			{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", category.getId());
				jsonObject.put("name", StringUtils.trimToEmpty(category.getName()));
				
				dataArray.add(jsonObject);
			}
		}
		else
		{
			if(company == null)
				addJSONError("No company found");
				
			if(groupId == null)
				addJSONError("groupId is null");
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes());
		
		return SUCCESS;
	}
	
	public String getCategoryItems()
	{
		logger.info("getCategoryItems invoked...");
		
		if(company != null && categoryId != null)
		{
			category = categoryDelegate.find(categoryId);
			List<CategoryItem> catItemList = categoryItemDelegate.findAll(company, category, 
					new Order[] { Order.asc("name")}, false, false).getList();
			
			if(catItemList.isEmpty())
				addJSONError("No data found");
			
			for(CategoryItem item: catItemList)
			{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", item.getId());
				jsonObject.put("name", StringUtils.trimToEmpty(item.getName()));
				jsonObject.put("shortDescription", item.getShortDescription());
				jsonObject.put("description", item.getDescription());
				
				if(StringUtils.isNotEmpty(item.getItemSchedule()))
					jsonObject.put("schedule", item.getItemSchedule());
				
				dataArray.add(jsonObject);
			}
		}
		else
		{
			if(company == null)
				addJSONError("company is null");
			
			if(categoryId == null)
				addJSONError("categoryId is null");
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes());
		
		return SUCCESS;
	}

	/**
	 * Exclusive for Hi-Precision website only 
	 * */
	public String getHPDCategories()
	{
		logger.info("getHPDCategories invoked...");
		
		if(company != null && categoryId != null && company.getId() == HIPRECISION_COMPANY_ID)
		{
			category = categoryDelegate.find(categoryId);
			final MultiPage multiPage = multiPageDelegate.find(categoryId);
			
			List<SinglePage> items = multiPage != null ? multiPage.getItems() : new ArrayList<SinglePage>();
			
			if(items.isEmpty())
			{
				addJSONError("No data found");
			}
			
			final boolean isHealthWatch = multiPage != null
				? StringUtils.containsIgnoreCase(multiPage.getName(), HEALTH_WATCH_PAGE_NAME)
				: false;
			
			for(SinglePage item : items)
			{
				final JSONObject jsonObject = new JSONObject();
				
				jsonObject.put("id", item.getId());
				jsonObject.put("name", StringUtils.trimToEmpty(item.getName()));
				jsonObject.put("subtitle", item.getSubTitle());
				
				if(isHealthWatch)
				{
					jsonObject.put("content", item.getContent());
				}
				else
				{
					jsonObject.put("imagesUrl", imagesUrlToArray(item.getImages(), request));
					putOtherDetails(jsonObject, item);
					
				}
				
				dataArray.add(jsonObject);
			}
		}
		else
		{
			if(company == null)
				addJSONError("company is null");
			else if(company.getId() != HIPRECISION_COMPANY_ID)
				addJSONError("invalid company");
			
			if(categoryId == null)
				addJSONError("categoryId is null");
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes());
		
		return SUCCESS;
	}
	
	/**
	 * Exclusive for Hi-Precision website only 
	 * */
	public String getHPDCategories2()
	{
		logger.info("getHPDCategories invoked...");
		
		if(company != null && categoryId != null && company.getId() == HIPRECISION_COMPANY_ID)
		{
			category = categoryDelegate.find(categoryId);
			final MultiPage multiPage = multiPageDelegate.find(categoryId);
			
			List<SinglePage> items = multiPage != null ? multiPage.getItems() : new ArrayList<SinglePage>();
			
			if(items.isEmpty())
			{
				addJSONError("No data found");
			}
			
			final boolean isHealthWatch = multiPage != null
				? StringUtils.containsIgnoreCase(multiPage.getName(), HEALTH_WATCH_PAGE_NAME)
				: false;
			
			for(SinglePage item : items)
			{
				final JSONObject jsonObject = new JSONObject();
				
				jsonObject.put("id", item.getId());
				jsonObject.put("name", StringUtils.trimToEmpty(item.getName()));
				jsonObject.put("subtitle", item.getSubTitle());
				
				if(isHealthWatch)
				{
					jsonObject.put("content", item.getContent());
				}
				else
				{
					jsonObject.put("imagesUrl", imagesUrlToArray(item.getImages(), request));
					putOtherDetails2(jsonObject, item);
					
				}
				
				dataArray.add(jsonObject);
			}
		}
		else
		{
			if(company == null)
				addJSONError("company is null");
			else if(company.getId() != HIPRECISION_COMPANY_ID)
				addJSONError("invalid company");
			
			if(categoryId == null)
				addJSONError("categoryId is null");
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes());
		
		return SUCCESS;
	}
	
	private List<String> imagesUrlToArray(List<PageImage> pageImages, HttpServletRequest request)
	{
		List<String> urlList = new ArrayList<String>();
		
		if(pageImages == null)
			return urlList;
		
		for(PageImage pageImage : pageImages)
		{
			final String serverName = request.getServerName();
			final String baseUrl = serverName.contains("localhost")
				? URL_HI_PRECISION
				: serverName;
			
			urlList.add(baseUrl + "/images/pages/" + pageImage.getImage1());
		}
		
		return urlList;
	}

	private void putOtherDetails(JSONObject jsonObject, SinglePage item)
	{
		if(item != null && jsonObject != null)
		{
			final String content = item.getContent();
			if(StringUtils.isNotBlank(content))
			{
				String parsedContent = HTMLTagStripper.stripTags(content.replaceAll("(?i)<br[^>]*>", LINE_BREAK_KEYWORD));
				if(StringUtils.isNotEmpty(parsedContent))
				{
					for(String key : CONTENT_KEYSETS)
					{
						parsedContent = parsedContent.replaceAll("(?i)".concat(key), LINE_BREAK_KEYWORD.concat(key));
					}
					
					final String parseLines = parsedContent.replaceAll(LINE_BREAK_KEYWORD, "\n");
					final String[] lines = parseLines.split("\n");
					
					final JSONObject contactsArray = new JSONObject();
					final JSONObject locationArray = new JSONObject();
					final JSONArray officeHoursArray = new JSONArray();
					for(String key : CONTENT_KEYSETS)
					{
						for(String line : lines)
						{
							if(StringUtils.containsIgnoreCase(line, key))
							{
								if(CONTENT_TELNO_KEY.equalsIgnoreCase(key) || CONTENT_CELLPHONE_KEY.equalsIgnoreCase(key))
								{
									final String value = StringUtils.remove(line, key);
									if(StringUtils.isNotEmpty(value))
									{
										final String[] values = value.split(CONTENT_VALUE_DELIMITER);
										if(values.length > 0)
										{
											final JSONArray itemArray = new JSONArray();
											for(String val : values)
											{
												itemArray.add(trimToEmptyWithNBSP(val));
											}
											contactsArray.put(key, itemArray);
										}
										else
										{
											contactsArray.put(key, trimToEmptyWithNBSP(value));
										}
									}
								}
								else if(CONTENT_LOCATION_KEY.equalsIgnoreCase(key))
								{
									final String value = StringUtils.remove(line, key);
									if(StringUtils.isNotEmpty(value))
									{
										final String[] values = value.split(CONTENT_VALUE_DELIMITER);
										if(values.length == 2)
										{
											locationArray.put("longitude", trimToEmptyWithNBSP(values[0]));
											locationArray.put("latitude", trimToEmptyWithNBSP(values[1]));
										}
										else
										{
											locationArray.put(key, trimToEmptyWithNBSP(value));
										}
									}
								}
								else if(CONTENT_OFFICEHOURS_KEY.equalsIgnoreCase(key))
								{
									final String value = StringUtils.remove(line, key);
									if(StringUtils.isNotEmpty(value))
									{
										final String[] values = value.split(CONTENT_VALUE_DELIMITER);
										if(values.length > 0)
										{
											for(String val : values)
											{
												officeHoursArray.add(trimToEmptyWithNBSP(val));
											}
										}
										else
										{
											officeHoursArray.add(trimToEmptyWithNBSP(value));
										}
									}
								}
								else
								{
									// replace the value having the keyword to "", then trimToEmptyWithNBSP
									CONTENT_MAP.put(key, trimToEmptyWithNBSP(line.replaceAll("(?i)".concat(key), "")));
								}
								break;
							}
						}
					}
					
					jsonObject.put("address", trimToEmptyWithNBSP(CONTENT_MAP.get(CONTENT_ADDRESS_KEY)));
					jsonObject.put("contacts", MapUtils.isNotEmpty(contactsArray) ? contactsArray : "");
					jsonObject.put("location", MapUtils.isNotEmpty(locationArray) ? locationArray : "");
					jsonObject.put("officeHours", CollectionUtils.isNotEmpty(officeHoursArray) ? officeHoursArray : "");
					jsonObject.put("servicesOffered", trimToEmptyWithNBSP(CONTENT_MAP.get(CONTENT_SERVICES_KEY)));
					
					if(getIsLocal())
					{
						printMap(CONTENT_MAP);
					}
					
					CONTENT_MAP.clear();
				}
			}
		}
	}
	
	private void putOtherDetails2(JSONObject jsonObject, SinglePage item)
	{
		if(item != null && jsonObject != null)
		{
			final String content = item.getContent();
			if(StringUtils.isNotBlank(content))
			{
				String parsedContent = HTMLTagStripper.stripTags(content.replaceAll("(?i)<br[^>]*>", LINE_BREAK_KEYWORD));
				if(StringUtils.isNotEmpty(parsedContent))
				{
					for(String key : CONTENT_KEYSETS)
					{
						parsedContent = parsedContent.replaceAll("(?i)".concat(key), LINE_BREAK_KEYWORD.concat(key));
					}
					
					final String parseLines = parsedContent.replaceAll(LINE_BREAK_KEYWORD, "\n");
					final String[] lines = parseLines.split("\n");

					final JSONObject locationArray = new JSONObject();
					
					final JSONObject contactsArray = new JSONObject();
					final JSONArray telNoArray = new JSONArray();
					final JSONArray cellphoneArray = new JSONArray();
					
					final JSONArray officeHoursArray = new JSONArray();
					
					for(String key : CONTENT_KEYSETS)
					{
						for(String line : lines)
						{
							if(StringUtils.containsIgnoreCase(line, key))
							{
								if(CONTENT_TELNO_KEY.equalsIgnoreCase(key) || CONTENT_CELLPHONE_KEY.equalsIgnoreCase(key))
								{
									final String value = StringUtils.remove(line, key);
									if(StringUtils.isNotEmpty(value))
									{
										final Object objValue;
										final String[] values = value.split(CONTENT_VALUE_DELIMITER);
										if(values.length > 0)
										{
											final JSONArray itemArray = new JSONArray();
											for(String val : values)
											{
												itemArray.add(trimToEmptyWithNBSP(val));
											}
											objValue = itemArray;
										}
										else
										{
											objValue = trimToEmptyWithNBSP(value);
										}
										
										contactsArray.put(key, objValue);
										
										if(CONTENT_TELNO_KEY.equalsIgnoreCase(key))
										{
											if(objValue instanceof JSONArray)
											{
												telNoArray.addAll((JSONArray) objValue);
											}
											else
											{
												telNoArray.add(objValue);
											}
										}
										else if(CONTENT_CELLPHONE_KEY.equalsIgnoreCase(key))
										{
											if(objValue instanceof JSONArray)
											{
												cellphoneArray.addAll((JSONArray) objValue);
											}
											else
											{
												cellphoneArray.add(objValue);
											}
										}
									}
								}
								else if(CONTENT_LOCATION_KEY.equalsIgnoreCase(key))
								{
									final String value = StringUtils.remove(line, key);
									if(StringUtils.isNotEmpty(value))
									{
										final String[] values = value.split(CONTENT_VALUE_DELIMITER);
										if(values.length == 2)
										{
											locationArray.put("longitude", trimToEmptyWithNBSP(values[0]));
											locationArray.put("latitude", trimToEmptyWithNBSP(values[1]));
										}
										else
										{
											locationArray.put(key, trimToEmptyWithNBSP(value));
										}
									}
								}
								else if(CONTENT_OFFICEHOURS_KEY.equalsIgnoreCase(key))
								{
									final String value = StringUtils.remove(line, key);
									if(StringUtils.isNotEmpty(value))
									{
										final String[] values = value.split(CONTENT_VALUE_DELIMITER);
										if(values.length > 0)
										{
											for(String val : values)
											{
												officeHoursArray.add(trimToEmptyWithNBSP(val));
											}
										}
										else
										{
											officeHoursArray.add(trimToEmptyWithNBSP(value));
										}
									}
								}
								else
								{
									// replace the value having the keyword to "", then trimToEmptyWithNBSP
									CONTENT_MAP.put(key, trimToEmptyWithNBSP(line.replaceAll("(?i)".concat(key), "")));
								}
								break;
							}
						}
					}
					
					jsonObject.put("address", trimToEmptyWithNBSP(CONTENT_MAP.get(CONTENT_ADDRESS_KEY)));
					jsonObject.put("location", MapUtils.isNotEmpty(locationArray) ? locationArray : "");

					jsonObject.put("contacts", MapUtils.isNotEmpty(contactsArray) ? contactsArray : "");
					jsonObject.put("telNo", CollectionUtils.isNotEmpty(telNoArray) ? telNoArray : "");
					jsonObject.put("cellphone", CollectionUtils.isNotEmpty(cellphoneArray) ? cellphoneArray : "");
					
					jsonObject.put("officeHours", CollectionUtils.isNotEmpty(officeHoursArray) ? officeHoursArray : "");
					jsonObject.put("servicesOffered", trimToEmptyWithNBSP(CONTENT_MAP.get(CONTENT_SERVICES_KEY)));
					
					if(getIsLocal())
					{
						printMap(CONTENT_MAP);
					}
					
					CONTENT_MAP.clear();
				}
			}
		}
	}
	
	private String trimToEmptyWithNBSP(String str)
	{
		str = StringUtils.trimToEmpty(str);
		str = str.replace(String.valueOf((char) 160), " "); // replace &nbsp; to " "
		return StringUtils.trimToEmpty(str);
	}
	
	private void printMap(Map<String, String> contentMap)
	{
		for(Entry<String, String> e : contentMap.entrySet())
		{
			if(e.getValue() != null)
			{
				logger.info("Content: {[" + e.getKey() + "], [" + StringUtils.trimToEmpty(e.getValue()) + "]}");
			}
		}
	}

	public String getAllCategoryWithItems()
	{
		logger.info("getAllCategoryWithItems invoked...");
		
		if(company != null && groupId != null)
		{
			group = groupDelegate.find(groupId);
			
			List<Category> categoryList = categoryDelegate.findAllWithKeyword(company, checkCategory(), group, 
					searchKeyword, false, false).getList();
			
			if(categoryList.isEmpty())
				addJSONError("No data found");
			
			for(Category category: categoryList)
			{
				JSONObject jsonObject = new JSONObject();
				JSONArray itemsArray = new JSONArray();
				
				jsonObject.put("id", category.getId());
				jsonObject.put("name", StringUtils.trimToEmpty(category.getName()));
				
				for(CategoryItem item : category.getItems3())
				{
					JSONObject jsonItem = new JSONObject();
					
					jsonItem.put("id", item.getId());
					jsonItem.put("name", StringUtils.trimToEmpty(item.getName()));
					jsonItem.put("shortDescription", item.getShortDescription());
					jsonItem.put("description", item.getDescription());
					
					itemsArray.add(jsonItem);
				}
				
				jsonObject.put("items", itemsArray);
				dataArray.add(jsonObject);
			}
		}
		else
		{
			if(company == null)
				addJSONError("No company found");
				
			if(groupId == null)
				addJSONError("groupId is null");
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes());
		
		return SUCCESS;
	}
	
	/**
	 * Exclusive for Hi-Precision website only 
	 * */
	public String getHPDDoctorsDirectory()
	{
		logger.info("getHPDDoctorsDirectory invoked...");
		
		if(company != null && groupId != null && groupId == 241)
		{
			group = groupDelegate.find(groupId);
			List<CategoryItem> catItemList = categoryItemDelegate.findAllByGroupNameAsc(company, group).getList();
			
			if(catItemList.isEmpty())
				addJSONError("No data found");
			
			for(CategoryItem item: catItemList)
			{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", item.getId());
				jsonObject.put("name", StringUtils.trimToEmpty(item.getName()));
				jsonObject.put("details", item.getDescription());
				jsonObject.put("schedule", item.getItemSchedule());
				
				final Category category = item.getParent();
				if(category != null)
				{
					if(category.getParentCategory() == null)
						continue;
					
					jsonObject.put("specializationId", category.getId());
					jsonObject.put("specialization", category.getName());
					jsonObject.put("branchId", category.getParentCategory().getName());
					jsonObject.put("branch", category.getParentCategory().getName());
				}
				else
					continue;
					
				dataArray.add(jsonObject);
			}
		}
		else
		{
			if(company == null) addJSONError("company is null");
			
			if(groupId == null) addJSONError("groupId is null");
			else if(groupId != 241) addJSONError("groupId is invalid");
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes());
		
		return SUCCESS;
	}
	
	public String getSinglePage()
	{
		logger.info("getSinglePage invoked...");
		
		if(company != null && StringUtils.isNotEmpty(pageName))
		{
			SinglePage singlePage = singlePageDelegate.findJsp(company, pageName);
			
			if(singlePage == null)
				addJSONError("page not found");
			else
			{
				//paging info
				JSONObject obj = new JSONObject();
				obj.put("title", singlePage.getTitle());
				obj.put("content", singlePage.getContent());
				obj.put("contentWithoutTags", singlePage.getContentWithoutTags());
				
				dataArray.add(obj);
			}
		}
		else
		{
			if(company == null)
				addJSONError("company is null");
			
			if(StringUtils.isEmpty(pageName))
				addJSONError("pageName is null");
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes());
		
		return SUCCESS;
	}
	
	public String getHPDSLabTestPreparation() throws Exception
	{
		final int DEFAULT_RESULTS_PER_PAGE = 15;
		int resultsperpage = resultsPerPage != null && resultsPerPage > 0? resultsPerPage : DEFAULT_RESULTS_PER_PAGE;
		int pageNo = pageNumber != null  && pageNumber > 0 ? pageNumber : 1;
		
		Group group = groupDelegate.find(company, TEST_PROCEDURES_GROUP_NAME);
		ObjectList<CategoryItem> itemList = categoryItemDelegate.findAllByGroupAndKeywordWithPaging(
				pageNo-1, (resultsperpage * 2), searchKeyword != null ? searchKeyword : "", company, true, group, "Section", "IMAGING", "ne");
		
		if(itemList.getSize() == 0)
		{
			JSONObject msgJson = new JSONObject();
			msgJson.put("message", "No tests found.");
			
			dataArray.add(msgJson);
		}
		else
		{
			//add paging info
			json.put("resultsPerPage", resultsperpage);
			json.put("pageNumber", pageNumber != null && pageNumber > 0 ? pageNumber : 1);
			json.put("totalPages", (int) Math.ceil((double) itemList.getTotal() / resultsperpage / 2));
		}
		
		for(CategoryItem item : itemList.getList())
		{
			JSONObject itemJson = new JSONObject();
			itemJson.put("name", item.getName());
			
			getItemOtherDetails(item, itemJson);
			
			dataArray.add(itemJson);
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes("UTF-8"));
		
		return SUCCESS; 
	}
	
	public String getHPDSLabTestPreparationSorted() throws Exception
	{
		
		
		final int max = company.getCompanySettings().getProductsPerPage();
		final int DEFAULT_RESULTS_PER_PAGE = 15;
		int resultsperpage = resultsPerPage != null && resultsPerPage > 0? resultsPerPage : DEFAULT_RESULTS_PER_PAGE;
		int pageNo = pageNumber != null  && pageNumber > 0 ? pageNumber : 1;
		
		String otherFieldName = "TEST";
		String otherFieldValue = "IMAGING";
		String otherFieldCompator = "ne";
		Group group = groupDelegate.find(company, TEST_PROCEDURES_GROUP_NAME);
		ObjectList<CategoryItem> allItems = new ObjectList<>();
		if(searchKeyword != null){
			allItems = categoryItemDelegate.findAllByGroupAndKeywordWithPaging(
					pageNo-1,(resultsperpage * 2), searchKeyword != null ? searchKeyword : "", company, true, group, otherFieldName, otherFieldValue, otherFieldCompator);	
		}
		else{
			List<Long> allIds = categoryItemDelegate.searchAllIdsByGroupAndOtherFieldWithPaging(-1, -1, searchKeyword, company, group, otherFieldName, otherFieldValue, otherFieldCompator);
			logger.info(allIds);
			
			List<Long> createdIds = categoryItemDelegate.searchHipreCreatedItems(-1, -1, searchKeyword, company, group, otherFieldName, otherFieldValue, otherFieldCompator);
			List<Long> updatedIds = categoryItemDelegate.searchHipreUpdatedItems(-1, -1, searchKeyword, company, group, otherFieldName, otherFieldValue, otherFieldCompator);
			List<Long> finalUpdatedIds = new ArrayList<Long>();
			
			logger.info(updatedIds);
			
			List<Long> masterList = new ArrayList<Long>();
			
			if(createdIds != null && !createdIds.isEmpty()){
				masterList.addAll(createdIds);
			}
			if(updatedIds != null && !updatedIds.isEmpty()){
				
				/*for(Long list1: updatedIds){
					int i = 1;
					for(Long list2: createdIds){
						if(list1 == list2){
							break;
						}
						else if(i++ == createdIds.size()){
							finalUpdatedIds.add(list1);
						}
					}
				}*/
				
				for(Long list : updatedIds){
					
					if(!createdIds.contains(list)){
						finalUpdatedIds.add(list);
					}
				}
				masterList.addAll(finalUpdatedIds);
			}
			
			logger.info("createdIds: " + createdIds);
			logger.info("updatedIds: " + updatedIds);
			logger.info("finalUpdatedIds: " + finalUpdatedIds);
			masterList.addAll(allIds);
			
			//masterList.size()/max;
			
			int pageNumberIndex = pageNo - 1;
			
			/*int pageCount = 0;
			int itemRemainder = masterList.size() % max;
			
			if (masterList.size() % max == 0){
				pageCount = masterList.size() / max;
			}
			else{
				pageCount = masterList.size()/ max + 1;
			}*/
			
			int toIndex =(max*pageNo) - 1;
			
			int fromIndex = toIndex - max + 1;
			List<Long> selectedItemsList = new ArrayList<Long>();
			selectedItemsList = masterList.subList(fromIndex, toIndex);

			logger.info("selectedItemsList size: " + selectedItemsList.size());
			
			List<CategoryItem> allItemsTempList = new ArrayList<CategoryItem>();
			allItemsTempList = categoryItemDelegate.findNoLoop(selectedItemsList);
			/*for(Long item: selectedItemsList){
				CategoryItem categoryItem = categoryItemDelegate.find(item);
				allItemsTempList.add(categoryItem);
			}*/
			Map <Long, CategoryItem> map = new HashMap<Long, CategoryItem>();
			for(CategoryItem tempItem: allItemsTempList){
				map.put(tempItem.getId(), tempItem);
			}
			
			List<CategoryItem> allItemsFinalList = new ArrayList<CategoryItem>();
			
			for(Long id : selectedItemsList){
				allItemsFinalList.add(map.get(id));
			}
			
			allItems.setList(allItemsFinalList);
			allItems.setTotal(masterList.size());
		}
		
		if(allItems.getSize() == 0)
		{
			JSONObject msgJson = new JSONObject();
			msgJson.put("message", "No tests found.");
			
			dataArray.add(msgJson);
		}
		else
		{
			//add paging info
			json.put("resultsPerPage", resultsperpage);
			json.put("pageNumber", pageNumber != null && pageNumber > 0 ? pageNumber : 1);
			json.put("totalPages", (int) Math.ceil((double) allItems.getTotal() / resultsperpage / 2));
		}
		
		Date dateToday = new Date();
		for(CategoryItem item : allItems.getList())
		{
			
			int notifDuration = (company.getNotifDuration() != null ? company.getNotifDuration() : 0);
			JSONObject itemJson = new JSONObject();
			
			Calendar createdDateCalendar = Calendar.getInstance();
			createdDateCalendar.setTime(item.getCreatedOn());
			createdDateCalendar.add(Calendar.DAY_OF_YEAR, notifDuration);
			Date createdDate = createdDateCalendar.getTime();
			
			Calendar updatedDateCalendar= Calendar.getInstance();
			updatedDateCalendar.setTime(item.getUpdatedOn());
			updatedDateCalendar.add(Calendar.DAY_OF_YEAR, notifDuration);
			Date updatedDate = updatedDateCalendar.getTime();
			
			
			if(createdDate.after(dateToday)){
				System.out.println(item.getName() + " --- NEW ("+createdDate+ " >= "+dateToday+")");
				itemJson.put("itemStatus", "New");
			}
			
			else if(updatedDate.after(dateToday)){
				System.out.println(item.getName() + " --- UPDATED ("+updatedDate+ " >= "+dateToday+")");
				itemJson.put("itemStatus", "Updated");
			}
			else{
				System.out.println(item.getName() + " --- SORTED");
				itemJson.put("itemStatus", "");
			}
				
			itemJson.put("name", item.getName());
			
			getItemOtherDetails(item, itemJson);
			
			dataArray.add(itemJson);
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes("UTF-8"));
		
		return SUCCESS; 
	}
	
	private void getItemOtherDetails(CategoryItem categoryItem, JSONObject itemJson)
	{
		List<CategoryItemOtherField> list = categoryItemOtherFieldDelegate.findAll(company, categoryItem).getList();
		
		String purpose=null, 
				patientPreparation=null, 
				section=null, 
				methodology=null, 
				volumeReq=null, 
				specimentContainer=null, 
				specimenRejection=null, 
				storageAndTransport=null, 
				runningDay=null, 
				cutOffTime=null, 
				timeOfRelease=null,
				roomTemperature=null,
				refrigeratedTemperature=null,
				freezerTemperature=null,
				otherTestRequestName=null,
				alternativeSpecimenAndVolumeRequirement=null,
				specimenStability = null,
				referenceInterval=null,
				limitationsInterference=null,
				frequentlyAskedQuestions=null,
				relatedWordsTest=null;
		
		for(CategoryItemOtherField otherField : list)
		{
			String fieldName = otherField.getOtherField().getName();
			System.out.println("Field Name: " + fieldName);
			
			if(PURPOSE_KEY.equalsIgnoreCase(fieldName) || INTENDED_USE_KEY.equalsIgnoreCase(fieldName))
			{
				purpose = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(PATIENT_PREPARATION_KEY.equalsIgnoreCase(fieldName) || SPECIAL_INSTRUCTIONS_KEY.equalsIgnoreCase(fieldName))
			{
				patientPreparation = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(SECTION_KEY.equalsIgnoreCase(fieldName) || LABORATORY_SECTION_KEY.equalsIgnoreCase(fieldName))
			{
				section = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(METHODOLOGY_KEY.equalsIgnoreCase(fieldName))
			{
				methodology = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(VOLUME_REQ_KEY.equalsIgnoreCase(fieldName) || SPECIMEN_AND_VOLUME_REQUIREMENT_KEY.equalsIgnoreCase(fieldName))
			{
				volumeReq = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(SPECIMEN_CONTAINER_KEY.equalsIgnoreCase(fieldName) || COLLECTION_SAMPLE_CONTAINER_KEY.equalsIgnoreCase(fieldName))
			{
				specimentContainer = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(SPECIMEN_REJECTION_KEY.equalsIgnoreCase(fieldName))
			{
				specimenRejection = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(STORAGE_AND_TRANSPORT_KEY.equalsIgnoreCase(fieldName))
			{
				storageAndTransport = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(RUNNING_DAY_KEY.equalsIgnoreCase(fieldName))
			{
				runningDay = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(CUT_OFF_TIME_KEY.equalsIgnoreCase(fieldName))
			{
				cutOffTime = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(TIME_OF_RELEASE_KEY.equalsIgnoreCase(fieldName) || TAT_RELEASING_OF_RESULTS_KEY.equalsIgnoreCase(fieldName))
			{
				timeOfRelease = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(ROOM_TEMPERATURE_KEY.equalsIgnoreCase(fieldName)){
				roomTemperature = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(REFRIGERATED_TEMPERATURE_KEY.equalsIgnoreCase(fieldName)){
				refrigeratedTemperature = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(FREEZER_TEMPERATURE_KEY.equalsIgnoreCase(fieldName)){
				freezerTemperature = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(OTHER_TEST_REQUEST_NAME_KEY.equalsIgnoreCase(fieldName)){
				otherTestRequestName = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(ALTERNATIVE_SPECIMEN_AND_VOLUME_REQUIREMENT_KEY.equalsIgnoreCase(fieldName)){
				alternativeSpecimenAndVolumeRequirement = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(SPECIMEN_STABILITY_KEY.equalsIgnoreCase(fieldName)){
				specimenStability = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(REFERENCE_INTERVAL_KEY.equalsIgnoreCase(fieldName)){
				referenceInterval = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(LIMITATIONS_INTERFERENCES_KEY.equalsIgnoreCase(fieldName)){
				limitationsInterference = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(FREQUENTLY_ASKED_QUESTIONS_KEY.equalsIgnoreCase(fieldName)){
				frequentlyAskedQuestions = HTMLTagStripper.stripTags(otherField.getContent());
			}
			else if(RELATED_WORDS_TEST_KEY.equalsIgnoreCase(fieldName)){
				relatedWordsTest = HTMLTagStripper.stripTags(otherField.getContent());
			}
		}
		
		itemJson.put("purpose", StringUtils.trimToEmpty(purpose));
		itemJson.put("patientPreparation", StringUtils.trimToEmpty(patientPreparation));
		itemJson.put("section", StringUtils.trimToEmpty(section));
		itemJson.put("methodology", StringUtils.trimToEmpty(methodology));
		itemJson.put("volumeReq", StringUtils.trimToEmpty(volumeReq));
		itemJson.put("specimenContainer", StringUtils.trimToEmpty(specimentContainer));
		itemJson.put("specimenRejection", StringUtils.trimToEmpty(specimenRejection));
		itemJson.put("storageAndTransport", StringUtils.trimToEmpty(storageAndTransport));
		itemJson.put("runningDay", StringUtils.trimToEmpty(runningDay));
		itemJson.put("cutoffTime", StringUtils.trimToEmpty(cutOffTime));
		itemJson.put("timeOfRelease", StringUtils.trimToEmpty(timeOfRelease));
		itemJson.put("roomTemperature", StringUtils.trimToEmpty(roomTemperature));
		itemJson.put("refrigeratedTemperature", StringUtils.trimToEmpty(refrigeratedTemperature));
		itemJson.put("freezerTemperature", StringUtils.trimToEmpty(freezerTemperature));
		
		itemJson.put("otherTestRequestName", StringUtils.trimToEmpty(otherTestRequestName));
		itemJson.put("alternativeSpecimenAndVolumeRequirement", StringUtils.trimToEmpty(alternativeSpecimenAndVolumeRequirement));
		itemJson.put("specimenRejection", StringUtils.trimToEmpty(specimenRejection));
		itemJson.put("specimenStability", StringUtils.trimToEmpty(specimenStability));
		itemJson.put("referenceInterval", StringUtils.trimToEmpty(referenceInterval));
		itemJson.put("limitationsInterference", StringUtils.trimToEmpty(limitationsInterference));
		itemJson.put("frequentlyAskedQuestions", StringUtils.trimToEmpty(frequentlyAskedQuestions));
		itemJson.put("relatedWordsTest", StringUtils.trimToEmpty(relatedWordsTest));
		
	}
	
	public String getHPDSLabTestPreparation2() throws Exception
	{
		final int DEFAULT_RESULTS_PER_PAGE = 15;
		int resultsperpage = resultsPerPage != null && resultsPerPage > 0? resultsPerPage : DEFAULT_RESULTS_PER_PAGE;
		int pageNo = pageNumber != null  && pageNumber > 0 ? pageNumber : 1;
		
		Group group = groupDelegate.find(company, TEST_PROCEDURES_GROUP_NAME);
		ObjectList<CategoryItem> itemList = categoryItemDelegate.findAllByGroupAndKeywordWithPaging(
				pageNo-1, (resultsperpage * 3), searchKeyword != null ? searchKeyword : "", company, true, group, "Section", "IMAGING", "ne");
		
		if(itemList.getSize() == 0)
		{
//			addJSONError("groupId is null");
			JSONObject msgJson = new JSONObject();
			msgJson.put("message", "No tests found.");
			dataArray.add(msgJson);
		}
		else
		{
			//add paging info
			json.put("resultsPerPage", resultsperpage);
			json.put("pageNumber", pageNumber != null && pageNumber > 0 ? pageNumber : 1);
			json.put("totalPages", (int) Math.ceil((double) itemList.getTotal() / resultsperpage / 3));
		}
		
		for(CategoryItem item : itemList.getList())
		{
			JSONObject itemJson = new JSONObject();
			itemJson.put("name", item.getName());
			
			getItemOtherDetails2(item, itemJson);
			
			dataArray.add(itemJson);
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes("UTF-8"));
		
		return SUCCESS; 
	}
	
	private void getItemOtherDetails2(CategoryItem categoryItem, JSONObject itemJson)
	{
		final List<CategoryItemOtherField> list = categoryItemOtherFieldDelegate.findAll(company, categoryItem).getList();
		
		final TreeMap<Integer, CategoryItemOtherField> map = new TreeMap<Integer, CategoryItemOtherField>(); 
		for(CategoryItemOtherField otherField : list)
		{
			final Integer sortOrder = otherField.getOtherField().getSortOrder();
			if(sortOrder != null && sortOrder >= 0)
			{
				map.put(sortOrder, otherField);				
			}
		}
		
		for(Entry<Integer, CategoryItemOtherField> e : map.entrySet())
		{
			final CategoryItemOtherField otherField = e.getValue();
			final String fieldName = otherField.getOtherField().getName();
			
			final StringBuilder test = new StringBuilder("");
			
			final String replace = fieldName.replaceAll("[^A-Za-z ]", "");
			final String[] space = replace.split(" ");
			if(space.length > 1)
			{
				for(int i = 0; i < space.length; i++)
				{
					String sp = space[i];
					if(sp != null)
					{
						sp = sp.toLowerCase();
						if(!sp.equalsIgnoreCase("c"))
						{
							if(i > 0)
							{
								test.append(sp.substring(0, 1).toUpperCase());
								test.append(sp.substring(1));
							}
							else
							{
								test.append(sp);
							}							
						}
					}
				}			
			}
			else
			{
				test.append(replace.toLowerCase());
			}

			if(!test.toString().isEmpty())
			{
				itemJson.put(test.toString(), StringUtils.trimToEmpty(HTMLTagStripper.stripTags(otherField.getContent())));
			}
		}
		
	}

	public String getMultiPage()
	{
		logger.info("getMultiPage invoked...");
		
		if(company != null && StringUtils.isNotEmpty(pageName))
		{
			MultiPage multiPage = multiPageDelegate.findJsp(company, pageName);
			
			if(multiPage == null)
			{
				addJSONError("page not found");
			}
			else
			{
				final boolean isNews = StringUtils.containsIgnoreCase(multiPage.getName(), "news");
				final DateTime newsDateRange = isNews ? new DateTime().minusMonths(12) : null;
				
				final JSONArray itemsArray = new JSONArray();
				for(SinglePage singlePage : multiPage.getItems())
				{
					final JSONObject jsonItem = new JSONObject();
					
					if(isNews)
					{
						final DateTime dt = new DateTime(singlePage.getCreatedOn());
						if(dt != null && newsDateRange != null && dt.isAfter(newsDateRange))
						{
							putNewsPageDetails(singlePage, jsonItem);
						}
						else
						{
							continue;
						}
					}
					else
					{
						jsonItem.put("title", singlePage.getTitle());
						jsonItem.put("content", singlePage.getContent());
						jsonItem.put("contentWithoutTags", singlePage.getContentWithoutTags());
					}
					
					itemsArray.add(jsonItem);
				}
				
				final JSONObject obj = new JSONObject();
				obj.put("title", multiPage.getTitle());
				obj.put("items", itemsArray);
				
				dataArray.add(obj);
			}
		}
		else
		{
			if(company == null)
				addJSONError("company is null");
			
			if(StringUtils.isEmpty(pageName))
				addJSONError("pageName is null");
		}
		
		finalizeJSON();
		
		inputStream = new ByteArrayInputStream(json.toJSONString().getBytes());
		
		return SUCCESS;
	}
	
	private void finalizeJSON()
	{
		if(errorsArray.size() > 0)
		{
			json.put(ERRORS, errorsArray);
		}
		else
		{
			json.put(DATA, dataArray);
		}
	}

	private void addJSONError(String message)
	{
		JSONObject error = new JSONObject();
		error.put(MESSAGE, message);
		
		errorsArray.add(error);
	}
	
	private Category checkCategory()
	{
		if(categoryId == null)
			return null;
		
		Category category = categoryDelegate.find(categoryId);
		
		if(category == null)
			addJSONError("category not found");
		
		return category;
	}
	
	private void putNewsPageDetails(SinglePage sp, JSONObject item)
	{
		if(sp != null && item != null)
		{
			final String name = StringUtils.trimToEmpty(sp.getName());
			
			item.put("id", sp.getId());
			item.put("title", StringUtils.isEmpty(sp.getTitle()) ? name : StringUtils.trimToEmpty(sp.getTitle()));
			item.put("subtitle", StringUtils.isEmpty(sp.getSubTitle()) ? name : StringUtils.trimToEmpty(sp.getSubTitle()));
			item.put("content", sp.getContent());
			item.put("date_published", sp.getDatePublished() != null ? sp.getDatePublished().toString() : "");
			item.put("date_created", sp.getCreatedOn() != null ? sp.getCreatedOn().toString() : "");
			
			if(CollectionUtils.isNotEmpty(sp.getImages()))
			{
				final StringBuilder sb = new StringBuilder();

				sb.append("https://");
				sb.append(company.getServerName());
				sb.append("/");
				sb.append("images");
				sb.append("/");
				sb.append("pages");
				sb.append("/");
				sb.append(sp.getImages().get(0).getOriginal());
				
				item.put("imageUrl", sb.toString());
				
			}
			else
			{
				item.put("imageUrl", "No image found.");
			}
		}
	}
	
	@Override
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public void setResultsPerPage(Integer resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
	
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public void setSession(Map session) {
		this.session = session;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response)
	{
		this.response = response;
	}
}
