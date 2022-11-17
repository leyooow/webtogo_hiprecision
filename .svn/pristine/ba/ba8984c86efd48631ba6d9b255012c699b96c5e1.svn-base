package com.ivant.cms.action.admin;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryFileDelegate;
import com.ivant.cms.delegate.CategoryImageDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.CategoryLanguageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryFile;
import com.ivant.cms.entity.CategoryImage;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.CategoryLanguage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.GroupAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CategoryAction extends ActionSupport implements Preparable, ServletRequestAware, 
ServletContextAware, UserAware, CompanyAware, GroupAware {

	private static final long serialVersionUID = -889165494539713940L;
	private static final Logger logger = Logger.getLogger(CategoryAction.class);
	private static final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private static final CategoryImageDelegate categoryImageDelegate = CategoryImageDelegate.getInstance();
	private static final CategoryFileDelegate categoryFileDelegate = CategoryFileDelegate.getInstance();
	private static final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private static final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final LogDelegate logDelegate = LogDelegate.getInstance();	
	private LanguageDelegate languageDelegate = LanguageDelegate.getInstance();
	private CategoryLanguageDelegate categoryLanguageDelegate = CategoryLanguageDelegate.getInstance();
	
	private static final CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	private static final OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	
	private User user;
	private Company company;
	private HttpServletRequest request;
	private ServletContext servletContext;
	
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	private Category category;
	private List<Category> categories;
	private Group group;
	private String errorType;
	private String name;
	private String description;
	private List<CategoryImage> images;
	private List<CategoryFile> categoryFiles;
	private CategoryFile categoryFile;
	private String categoryparent_id;
	private CompanySettings companySettings;
	private CategoryImage categoryImage;
	private String image_id;
	private String file_id;
	private String group_id;
	private String shortDescription;
	
	private List<Log> logList;
	
	private List<Language> languages;
	
	public List<Log> getLogList(){
		return logList;
	}
	
	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	private File[] files;
	private String[] contentTypes;
	private String[] filenames;
	
	private CategoryItem item;
	
	
	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}

	public String getCategoryparent_id() {
		return categoryparent_id;
	}

	public void setCategoryparent_id(String categoryparent_id) {
		this.categoryparent_id = categoryparent_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void prepare() throws Exception {
		setLanguages(company.getLanguages());
		companySettings = company.getCompanySettings();

		// group = groupDelegate.find(new Long(group_id));
		try {
			long categoryId = Long.parseLong(request
					.getParameter("category_id"));
			category = categoryDelegate.find(categoryId);
			if (category.getCreatedBy() == null) {
				User persistedUser = userDelegate.load(user.getId());
				category.setCreatedBy(persistedUser);
			}
		} catch (Exception e) {
			category = new Category();
			User persistedUser = userDelegate.load(user.getId());

			category.setName(name);
			category.setDescription(description);
			category.setCreatedBy(persistedUser);
			category.setCompany(company);
			category.setParentGroup(group);
			category.setImages(images);
			category.setFiles(categoryFiles);
		}
		
		//setting up language
		if(request.getParameter("language")!=null && category!=null){
			category.setLanguage(languageDelegate.find(request.getParameter("language"),company));
		}			
		

		try {
			Long parentId = Long.parseLong(request.getParameter("parent"));
			Category parentCategory = categoryDelegate.find(parentId);
			if (parentCategory.getCompany().equals(company)) {
				category.setParentCategory(parentCategory);
			}
		} catch (Exception e) {
		}

		if (logger.isDebugEnabled())
			logger.debug("***************category: " + category);
		logList = logDelegate.findAll(category.getId(), EntityLogEnum.CATEGORY).getList();
		String temp_abc = "survey1<<->>branches here1<<->>question<<:>>3<<:>>the<<o>>Quick<<o>>Brown<<o>><<:>><<;>>question2<<:>>2<<:>>jumps<<o>>over<<o>>the<<o>><<:>><<;>>question3<<:>>1<<:>>lazy<<o>>dog<<o>><<:>><<;>><<->>";
		int counter_detail1 = 0;
		int counter_detail2 = 0;
		int counter_detail3 = 0;
		int counter_detail4 = 0;
		System.out.println("######################    1   #######################################");
		for(String s1:temp_abc.split("<<->>")){//divide the string per question
			System.out.println("######################    2   #######################################");
			System.out.println("##S1#"+s1);
			if(s1.trim().length()>0){
				System.out.println("Detail : " + s1);
				if(counter_detail1 == 2){
					//this is intended for all the question
					
					
					
					for(String questItem : s1.split("<<;>>")){
					
					
						if(questItem.trim().length()>0){
					
					
							counter_detail2 = 0;
							for(String s2 : questItem.split("<<:>>")){
								System.out.println("##S2#"+s2);
								System.out.println("######################    3   #######################################");
								if(s2.trim().length() > 0){
									if(counter_detail2 == 2){
										//this is intended for the given choices of each question
										counter_detail3 = 0;
											for(String s3 : s2.split("<<o>>")){
												System.out.println("##S3#"+s3);
												if(s3.trim().length()>0){
													System.out.println("Option : " + s3);
											}
										}
									}
									counter_detail2++;
									
								}
								
							}
						
						}
					}
					
					
					
					
					
					
					
					
					
					
					
				}
				counter_detail1++;
				
			}
			
		}
	}

	@Override
	public String execute() throws Exception {
		return Action.NONE;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String save() {
		if(!commonParamsValid()) {
			return ERROR;
		}
		
		if(group == null) {
			return ERROR;
		}
		
		if(request.getParameter("language")==null || request.getParameter("language").isEmpty()){
			Category oldCategory = (Category) request.getSession().getAttribute("oldCategory");
			Log log = new Log();
			
			if(categoryDelegate.find(company, category.getName(), category.getParentCategory(), group) == null ) {
				final ObjectList<Category> categories = categoryDelegate.findAllWithPaging(company, group, user.getItemsPerPage(), page, true);  //.findAll(company, group);
				if(CompanyConstants.UNIORIENT_AGENTS.equalsIgnoreCase(company.getName()) ? true : categories.getSize() < group.getMaxCategories()) {
					category.setParentCategory(categoryDelegate.find(new Long(categoryparent_id)));
					
					Double point_ = 0D;
					Double price_ = 0D;
					try
					{
						point_ = Double.parseDouble(request.getParameter("points"));
					}
					catch(Exception e){
						point_ = 0D;
					}
					
					try
					{
						price_ = Double.parseDouble(request.getParameter("price"));
					}
					catch(Exception e){
						price_ = 0D;
					}
					category.setPoints(String.valueOf(point_));
					category.setPrice(String.valueOf(price_));
					
					long categoryId = categoryDelegate.insert(category);
					category.setId(categoryId);
					//for wendys only
					System.out.println("-------Dumaan here ------");
					if(company.getName().equalsIgnoreCase("wendys") && group.getName().equalsIgnoreCase("GC")){
						point_ = 0D;
						price_ = 0D;
						try
						{
							point_ = Double.parseDouble(request.getParameter("points"));
						}
						catch(Exception e){
							point_ = 0D;
						}
						
						try
						{
							price_ = Double.parseDouble(request.getParameter("price"));
						}
						catch(Exception e){
							price_ = 0D;
						}
						category.setPoints(String.valueOf(point_));
						category.setPrice(String.valueOf(price_));
					}
					
					if(company.getName().equalsIgnoreCase("wendys") && group.getName().equalsIgnoreCase("survey")){
						System.out.println(request.getParameter("abc_anjerico"));
						
						String temp_abc = request.getParameter("abc_anjerico");
						int counter_detail1 = 0;
						int counter_detail2 = 0;
						int counter_detail3 = 0;
						int counter_detail4 = 0;
						System.out.println("######################    1   #######################################");
						for(String s1:temp_abc.split("<<->>")){//divide the string per question
							System.out.println("######################    2   #######################################");
							System.out.println("##S1#"+s1);
							if(s1.trim().length()>0){
								System.out.println("Detail : " + s1);
								if(counter_detail1 == 0){
									//category.setName(s1.trim());
								}
								else if(counter_detail1 == 1){
									//category.setInfo1(s2.trim());
								}
								else if(counter_detail1 == 2){
									//this is intended for all the question
									
									
									
									for(String questItem : s1.split("<<;>>")){
									
									
										if(questItem.trim().length()>0){
									
											item = new CategoryItem();
											item.setCompany(company);
											item.setCreatedOn(new Date());
											item.setUpdatedOn(new Date());
											item.setParentGroup(group);
											item.setParent(category);
											counter_detail2 = 0;
											for(String s2 : questItem.split("<<:>>")){
												System.out.println("##S2#"+s2);
												System.out.println("######################    3   #######################################");
												if(s2.trim().length() > 0){
													if(counter_detail2 == 0) {
														item.setName(s2.trim());
														item = categoryItemDelegate.find(categoryItemDelegate.insert(item));
													}
													else if(counter_detail2 == 1){
														//save maximum answer here
														CategoryItemOtherField maxAnswerField = new CategoryItemOtherField();
														OtherField maxOF = otherFieldDelegate.find("Maximum Answer Count", company);
														
														maxAnswerField.setContent(s2.trim());
														maxAnswerField.setCategoryItem(item);
														maxAnswerField.setOtherField(maxOF);
														maxAnswerField.setCompany(company);
														maxAnswerField.setIsValid(Boolean.TRUE);
														categoryItemOtherFieldDelegate.insert(maxAnswerField);
														
													}
													else if(counter_detail2 == 2){
														//this is intended for the given choices of each question
														counter_detail3 = 0;
														int optionCounter_1 = 1;	
														for(String s3 : s2.split("<<o>>")){
																System.out.println("##S3#"+s3);
																if(s3.trim().length()>0){
																	System.out.println("Option : " + s3);
																	CategoryItemOtherField newOtherField = new CategoryItemOtherField();//categoryItemOtherFieldDelegate.findByOtherFieldName(company, item, "Option "+String.valueOf(optionCounter_1));
																	OtherField of = otherFieldDelegate.find("Option "+String.valueOf(optionCounter_1), company);
																	newOtherField.setContent(s3);
																	newOtherField.setCategoryItem(item);
																	newOtherField.setOtherField(of);
																	newOtherField.setCompany(company);
																	newOtherField.setIsValid(Boolean.TRUE);
																	categoryItemOtherFieldDelegate.insert(newOtherField);
																	optionCounter_1++;
															}
														}
													}
													counter_detail2++;
													
												}
												
											}
										
										}
									}
									
								}
								counter_detail1++;
								
							}
							
						}
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
					}
					
					//insert log here
					log.setRemarks("Added a new category " + "\"" + category.getName() + "\"");
					//add action log
					log.setEntityType(EntityLogEnum.CATEGORY);
					log.setEntityID(category.getId());
					log.setCompany(company);
					log.setUpdatedByUser(user);
					logDelegate.insert(log);
				}
				else {
					errorType = "maxcategories";
					return ERROR;
				}
			}  
			else {
				if(category.getId() == null) {
					errorType = "categoryexist";
					return ERROR;
				}
				else {
					if (new Long(categoryparent_id)==0L)  
						category.setParentCategory(null);
					else
						category.setParentCategory(categoryDelegate.find(new Long(categoryparent_id)));
					
					if(categoryDelegate.update(category)){
						
						
						
						
						
						Double point_ = 0D;
						Double price_ = 0D;
						try
						{
							point_ = Double.parseDouble(request.getParameter("points"));
						}
						catch(Exception e){
							point_ = 0D;
						}
						
						try
						{
							price_ = Double.parseDouble(request.getParameter("price"));
						}
						catch(Exception e){
							price_ = 0D;
						}
						category.setPoints(String.valueOf(point_));
						category.setPrice(String.valueOf(price_));
						
						
						
						
						
						
						
						System.out.println("-------Dumaan here 2------");
						if(company.getName().equalsIgnoreCase("wendys") && group.getName().equalsIgnoreCase("GC")){
							point_ = 0D;
							price_ = 0D;
							try
							{
								point_ = Double.parseDouble(request.getParameter("points"));
							}
							catch(Exception e){
								point_ = 0D;
							}
							
							try
							{
								price_ = Double.parseDouble(request.getParameter("price"));
							}
							catch(Exception e){
								price_ = 0D;
							}
							category.setPoints(String.valueOf(point_));
							category.setPrice(String.valueOf(price_));
						}
						
						
						
						//for wendys only
						if(company.getName().equalsIgnoreCase("wendys") && group.getName().equalsIgnoreCase("survey")){
							System.out.println(request.getParameter("abc_anjerico"));
							
							String temp_abc = request.getParameter("abc_anjerico");
							int counter_detail1 = 0;
							int counter_detail2 = 0;
							int counter_detail3 = 0;
							int counter_detail4 = 0;
							System.out.println("######################    1   #######################################");
							for(String s1:temp_abc.split("<<->>")){//divide the string per question
								System.out.println("######################    2   #######################################");
								System.out.println("##S1#"+s1);
								if(s1.trim().length()>0){
									System.out.println("Detail : " + s1);
									if(counter_detail1 == 0){
										//category.setName(s1.trim());
									}
									else if(counter_detail1 == 1){
										//category.setInfo1(s2.trim());
									}
									else if(counter_detail1 == 2){
										//this is intended for all the question
										
										
										
										for(String questItem : s1.split("<<;>>")){
										
										
											if(questItem.trim().length()>0){
										
												
												counter_detail2 = 0;
												for(String s2 : questItem.split("<<:>>")){
													System.out.println("##S2#"+s2);
													System.out.println("######################    3   #######################################");
													if(s2.trim().length() > 0){
														Boolean isNewItem = true;
														if(counter_detail2 == 0) {
															if(categoryItemDelegate.find(Long.parseLong(s2.trim())) != null){
																item = categoryItemDelegate.find(Long.parseLong(s2.trim()));
																isNewItem = false;
															}
															else{
																item = new CategoryItem();
																
																item.setCompany(company);
																item.setCreatedOn(new Date());
																item.setUpdatedOn(new Date());
																item.setParentGroup(group);
																item.setParent(category);
															}
															
															item.setCompany(company);
															//item.setCreatedOn(new Date());
															item.setUpdatedOn(new Date());
															//item.setParentGroup(group);
															//item.setParent(category);
															
															
															
															
															
															
															
															
														}
														else if(counter_detail2 == 1){
															//save maximum answer here
															item.setName(s2.trim());
															if(isNewItem){
																item = categoryItemDelegate.find(categoryItemDelegate.insert(item));
															}
															else{
																categoryItemDelegate.update(item);
															}
															
															
															
															
															
														}
														else if(counter_detail2 == 2){
															Boolean isNewMaxAnswer = true;
															CategoryItemOtherField maxAnswerField = new CategoryItemOtherField();
															if(categoryItemOtherFieldDelegate.findByOtherFieldName(company, item, "Maximum Answer Count") != null){
																maxAnswerField = categoryItemOtherFieldDelegate.findByOtherFieldName(company, item, "Maximum Answer Count");
																isNewMaxAnswer = false;
															}
															
															OtherField maxOF = otherFieldDelegate.find("Maximum Answer Count", company);
															
															maxAnswerField.setContent(s2.trim());
															maxAnswerField.setCategoryItem(item);
															maxAnswerField.setOtherField(maxOF);
															maxAnswerField.setCompany(company);
															//maxAnswerField.setIsValid(Boolean.TRUE);
															if(isNewMaxAnswer){
																categoryItemOtherFieldDelegate.insert(maxAnswerField);
															}
															else{
																categoryItemOtherFieldDelegate.update(maxAnswerField);
															}
															
														}
														else if(counter_detail2 == 3){
															Boolean isNewOtherF = true;
															//this is intended for the given choices of each question
															counter_detail3 = 0;
															int optionCounter_1 = 1;
															
															for(String s3 : s2.split("<<o>>")){
																	
																	System.out.println("##S3#"+s3);
																	if(s3.trim().length()>0){
																		
																		System.out.println("Option : " + s3);
																		CategoryItemOtherField updateOtherField = new CategoryItemOtherField();
																		if(categoryItemOtherFieldDelegate.findByOtherFieldName(company, item, "Option "+String.valueOf(optionCounter_1)) != null){
																			updateOtherField = categoryItemOtherFieldDelegate.findByOtherFieldName(company, item, "Option "+String.valueOf(optionCounter_1));
																			isNewOtherF = false;
																		}
																		else{
																			updateOtherField = new CategoryItemOtherField();
																			isNewOtherF = true;
																		}
																		OtherField of = otherFieldDelegate.find("Option "+String.valueOf(optionCounter_1), company);
																		updateOtherField.setContent(s3);
																		updateOtherField.setCategoryItem(item);
																		updateOtherField.setOtherField(of);
																		updateOtherField.setCompany(company);
																		updateOtherField.setIsValid(Boolean.TRUE);
																		if(isNewOtherF){
																			categoryItemOtherFieldDelegate.insert(updateOtherField);
																		}
																		else{
																			categoryItemOtherFieldDelegate.update(updateOtherField);
																		}
																		
																		
																		optionCounter_1++;
																}
															}
															if(optionCounter_1 < 11){
																//clear the content of other field
																CategoryItemOtherField updateOtherField = new CategoryItemOtherField();
																if(categoryItemOtherFieldDelegate.findByOtherFieldName(company, item, "Option "+String.valueOf(optionCounter_1)) != null){
																	updateOtherField = categoryItemOtherFieldDelegate.findByOtherFieldName(company, item, "Option "+String.valueOf(optionCounter_1));
																	isNewOtherF = false;
																}
																else{
																	updateOtherField = new CategoryItemOtherField();
																	isNewOtherF = true;
																}
																
																OtherField of = otherFieldDelegate.find("Option "+String.valueOf(optionCounter_1), company);
																updateOtherField.setContent("");
																updateOtherField.setCategoryItem(item);
																updateOtherField.setOtherField(of);
																updateOtherField.setCompany(company);
																updateOtherField.setIsValid(Boolean.TRUE);
																if(isNewOtherF){
																	categoryItemOtherFieldDelegate.insert(updateOtherField);
																}
																else{
																	categoryItemOtherFieldDelegate.update(updateOtherField);
																}
																
																optionCounter_1++;
															}
															
														}
														
														
														counter_detail2++;
														
													}
													
												}
											
											}
										}
										
									}
									counter_detail1++;
								}
							}
						}
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						//insert update log here
						String remark = "Updated the category "+ "\"" + category.getName() + "\"";
						String[] diff = LogHelper.getDifference(oldCategory, category);
						
						if(diff.length>0){
							log.setRemarks(remark + "~" + LogHelper.join(diff,"~"));
							//update action log
							log.setEntityType(EntityLogEnum.CATEGORY);
							log.setEntityID(category.getId());
							log.setCompany(company);
							log.setUpdatedByUser(user);
							logDelegate.insert(log);
						}
					}
				}
			}
			
			lastUpdatedDelegate.saveLastUpdated(company);
		}else{
			//if category doesnt exist(insert)
			CategoryLanguage categoryLanguage = category.getCategoryLanguage();			
			if(categoryLanguage==null){
				category.setLanguage(null);
				categoryLanguage = new CategoryLanguage();
				categoryLanguage.cloneOf(category);
				categoryLanguage.setLanguage(languageDelegate.find(request.getParameter("language"),company));
				categoryDelegate.refresh(category);
				categoryLanguage.setDefaultCategory(category);
				categoryLanguageDelegate.insert(categoryLanguage);
			}
			//if page exist (update)
			else{				
				categoryLanguage.cloneOf(category);
				categoryDelegate.refresh(category);
				categoryLanguage.setDefaultCategory(category);
				categoryLanguageDelegate.update(categoryLanguage);
				
			}
			
		}
		
		if(company.getName().equals("neltex") && files != null) {
			uploadImage();
		}
		
		return Action.SUCCESS;
	}

	public String update() {

		categoryDelegate.update(category);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}	
	
	public String delete() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		if(!category.getCompany().equals(company)) {
			return Action.ERROR;
		}
						
		categoryDelegate.delete(category);
		//insert delete log here 
		Log log = new Log();
		log.setEntityType(EntityLogEnum.CATEGORY);
		log.setRemarks("Deleted the category " +"\""+ category.getName()+"\"");
		log.setEntityID(category.getId());
		log.setCompany(company);
		log.setUpdatedByUser(user);
		logDelegate.insert(log);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public String edit() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		if(!category.getCompany().equals(company)) {
			return Action.ERROR;
		}
		
		//request.getSession().setAttribute("oldCategory",this.category);
		request.getSession().setAttribute("oldCategory",category.clone());
		
		Order[] orders = {Order.asc("parentCategory")};
		if (category.getParentCategory()!=null){
		categoryparent_id = category.getParentCategory().getId().toString();
		}
		categories = categoryDelegate.findAll(company, group).getList();
		
		return Action.SUCCESS;
	}
	
	public User getUser() {
		return user;
	}

	private boolean commonParamsValid() {
		if(company == null) {
			return false;
		}		
		
		if(category == null) {
			return false;
		}
		
		return true;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String getErrorType() {
		return errorType;
	}
	
	public List<Category> findAllRootCategories()
	{
		return categoryDelegate.findAllRootCategories(user.getCompany(),true).getList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	
	
	
	public String uploadImage() { 
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		if( !company.equals(category.getCompany()) )  {	
			return Action.ERROR;
		}
		
		saveImages(files, filenames, contentTypes);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public String deleteImage() {
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		if( !company.equals(category.getCompany()) )  {	
			return Action.ERROR;
		}

		String destinationPath = servletContext.getRealPath("companies" + 
				File.separator + company.getName() +
				File.separator + "images" +
				File.separator + "items");
			
		
		//logger.info("CategoryImage " + category.getImages()getcategoryImage);
		logger.info("-->category::::: " + category);
		categoryImage = categoryImageDelegate.find(new Long(image_id));
		if (categoryImage!=null)
		{
		logger.info("categoryImage1: " + categoryImage.getImage1());
		logger.info("categoryImage2: " + categoryImage.getImage2());
		logger.info("categoryImage3: " + categoryImage.getImage3());
		
		// delete image1
		FileUtil.deleteFile(destinationPath + File.separator + categoryImage.getImage1());
		// delete image2
		FileUtil.deleteFile(destinationPath + File.separator + categoryImage.getImage2());
		// delete image2
		FileUtil.deleteFile(destinationPath + File.separator + categoryImage.getImage3());
		// delete thumbnail
		FileUtil.deleteFile(destinationPath + File.separator +categoryImage.getThumbnail());

		categoryImageDelegate.delete(categoryImage);
		}
		
		company = category.getCompany();
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	public List<CategoryImage> getImages() {
		return images;
	}

	public void setImages(List<CategoryImage> images) {
		this.images = images;
	}

	public CategoryImage getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(CategoryImage categoryImage) {
		this.categoryImage = categoryImage;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public String[] getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}

	public String[] getFilenames() {
		return filenames;
	}

	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	private void saveImages(File[] files, String[] filenames, String[] contentTypes) {
		
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "items";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));

		
		logger.info("files length----------------- " + files.length + "    " + files[0].getName());
		logger.info("filenames length----------------- " + filenames.length  + "    " + filenames[0].toString());
		

		
		// create the image1, image2, image3 and thumbnail folders
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image1"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image2"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image3"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "thumbnail"));
		
		destinationPath = servletContext.getRealPath(destinationPath);
				
		Long companyId = company.getId();
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
				String source = files[i].getAbsolutePath();
				
				String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"), 
						String.valueOf(new Date().getTime()));	
				filename = FileUtil.replaceExtension(filename, "jpg"); 
				
				
				
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);

				// save the original files
				FileUtil.copyFile(files[i], origFile);
				 
				// generate image 1
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename, 
						companySettings.getImage1Width(), companySettings.getImage1Heigth(), 
						ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
				
				// generate image 2
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename, 
						companySettings.getImage2Width(), companySettings.getImage2Heigth(), 
						ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
				
				// generate image 3
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename, 
						companySettings.getImage3Width(), companySettings.getImage3Heigth(), 
						ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
				
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
				
				
				logger.info("setFilename: " + filenames[i] + " === " + category);
				
				CategoryImage categoryImage = new CategoryImage();
				categoryImage.setFilename(filenames[i]);
				categoryImage.setCategory(category);
//				categoryImage.setUrl(UrlUtil.generateImageUploadUrl(company) + "items/");
				categoryImage.setOriginal("original/" + filename);
				categoryImage.setImage1("image1/" + filename);
				categoryImage.setImage2("image2/" + filename);
				categoryImage.setImage3("image3/" + filename);
				categoryImage.setThumbnail("thumbnail/" + filename);
				categoryImageDelegate.insert(categoryImage);
			}
		}
	}
	
	public String uploadFile() { 
		
		if(!commonParamsValid()) {
			return Action.ERROR;
		}
		
		if( !company.equals(category.getCompany()) )  {	
			return Action.ERROR;
		}
		
		saveFiles(files, filenames, contentTypes);
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return Action.SUCCESS;
	}
	
	private void saveFiles(File[] files, String[] filenames, String[] contentTypes) {
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		// save new image in boysennewdesign folder
		if(company.getName().equalsIgnoreCase("boysen"))
			destinationPath += File.separator + "boysennewdesign";
		else
			destinationPath += File.separator + company.getName();
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator  + "images" + File.separator + "items"    + File.separator + "uploads"));
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
				File file = files[i];
				String filename = filenames[i];
				String contentType = contentTypes[i];
				String destination0 = servletContext.getRealPath(destinationPath + File.separator   + "images" + File.separator + "items");
				String destination = servletContext.getRealPath(destinationPath + File.separator   + "images" + File.separator + "items"    + File.separator + "uploads");
				////System.out.println("DESTINATION:  " + destination);
				new File(destination0).mkdir();
				new File(destination).mkdir();
				logger.info("UPloaddddd : "  +  destination + File.separator + filename  );
				File destFile = new File(destination + File.separator + filename);
				
				FileUtil.copyFile(file, destFile); 
				
				CategoryFile categoryFile = new CategoryFile();
				categoryFile.setCompany(company);
				categoryFile.setFileName(filename);
				categoryFile.setFilePath("uploads" + "/" + filename);
				categoryFile.setFileType(contentType);
				categoryFile.setCategory(category);
				categoryFile.setFileSize(file.length()/1000);
				categoryFileDelegate.insert(categoryFile);
			} 
		}
	}
	
	public String deleteFile() {
		if(company == null) {
			return Action.ERROR;
		}
		if( !company.equals(category.getCompany()) )  {	
			return Action.ERROR;
		}  
		
		categoryFile = categoryFileDelegate.find(new Long(file_id));
		if (categoryFile!=null)
		{
		
		String destinationPath = servletContext.getRealPath("companies" + 
				File.separator + company.getName() +
				File.separator + "images" + File.separator  + "items"  + File.separator + "uploads");
	
		FileUtil.deleteFile(destinationPath + File.separator + categoryFile.getFilePath());
	
		
		
		
		try {
			logger.info("FILE: " + destinationPath + File.separator + "-m-" + File.separator +  categoryFile.getFilePath());
		boolean success = (new File(destinationPath + File.separator + categoryFile.getFilePath())).delete();
	    if (!success) {
	        // Deletion failed
	    }		
		}catch (Exception e) {
	    	e.getStackTrace();
	    }
	    categoryFileDelegate.delete(categoryFile);
		}
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return SUCCESS;
	}
	

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public static CategoryDelegate getCategoryDelegate() {
		return categoryDelegate;
	}

	public static CategoryImageDelegate getCategoryImageDelegate() {
		return categoryImageDelegate;
	}

	public static GroupDelegate getGroupDelegate() {
		return groupDelegate;
	}

	public static LastUpdatedDelegate getLastUpdatedDelegate() {
		return lastUpdatedDelegate;
	}

	public static UserDelegate getUserDelegate() {
		return userDelegate;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
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

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	/**
	 * @return the categoryFiles
	 */
	public List<CategoryFile> getCategoryFiles() {
		return categoryFiles;
	}

	/**
	 * @param categoryFiles the categoryFiles to set
	 */
	public void setCategoryFiles(List<CategoryFile> categoryFiles) {
		this.categoryFiles = categoryFiles;
	}

	public static CategoryFileDelegate getCategoryfiledelegate() {
		return categoryFileDelegate;
	}

	/**
	 * @return the categoryFile
	 */
	public CategoryFile getCategoryFile() {
		return categoryFile;
	}

	/**
	 * @param categoryFile the categoryFile to set
	 */
	public void setCategoryFile(CategoryFile categoryFile) {
		this.categoryFile = categoryFile;
	}
	
	public CategoryItem getItem() {
		return item;
	}

	public void setItem(CategoryItem item) {
		this.item = item;
	}
	
	public Map<String,Group> getGroupMap()
	{
		// get the groups
		final List<Group> groupList = groupDelegate.findAll(company).getList();
		final Map<String, Group> groupMap = new HashMap<String, Group>();
		final Map<Long, Group> groupIdMap = new HashMap<Long, Group>();
		for(final Group group : groupList)
		{
			final String jspName = group.getName().toLowerCase();
			//group.setLanguage(language);
			//final Menu menu = new Menu(group, httpServer + "/" + jspName + ".do");
			//menuList.put(jspName, menu);
			
			groupMap.put(group.getName(), group);
			groupMap.put(jspName, group);
			groupIdMap.put(group.getId(), group);
		}
		request.setAttribute("groupList", groupList);
		request.setAttribute("groupMap", groupMap);
		request.setAttribute("groupIdMap", groupIdMap);
		return groupMap;
	}
	
	
}
