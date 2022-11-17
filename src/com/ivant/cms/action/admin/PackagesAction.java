package com.ivant.cms.action.admin;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.CategoryItemPackageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.PackageDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.CategoryItemPackage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.IPackage;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("unused")
public class PackagesAction
		implements Action, Preparable, ServletRequestAware, ServletContextAware, UserAware, PagingAware, CompanyAware
{
	
	private final Logger logger = Logger.getLogger(getClass());
	private final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private final PackageDelegate packageDelegate = PackageDelegate.getInstance();
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private final CategoryItemPackageDelegate categoryItemPackageDelegate = CategoryItemPackageDelegate.getInstance();
	private final CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	
	private Company company;
	
	private User user;
	private ServletRequest request;
	private ServletContext servletContext;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	private int pageNumber;
	
	private List<Group> groups;
	private List<IPackage> packages;
	private List<CategoryItem> catItems = null;
	private IPackage packageObj = null;
	private boolean hasPackages = false;
	
	/** CategoryItemPackage ID */
	private Long cipId;
	/** CategoryItem ID */
	private Long ciId;
	/** IPackage ID */
	private Long packageId;
	
	private String message;
	private String newFilename;
	
	@Override
	public String execute() throws Exception
	{
		
		if(user.getCompany() == null)
		{
			return Action.ERROR;
		}
		
		final PagingUtil pagingUtil = new PagingUtil(totalItems, user.getItemsPerPage(), pageNumber, 5);
		// System.out.println("Total Item Count: "+ totalItems);
		// System.out.println("Page Count: " + pagingUtil.getTotalPages());
		request.setAttribute("pagingUtil", pagingUtil);
		
		return Action.SUCCESS;
	}
	
	@Override
	public void prepare() throws Exception
	{
		hasPackages = user.getCompany().getCompanySettings().getHasPackages();
		final Order[] order = { Order.asc("name") };
		
		if(request.getParameter("pageNumber") == null)
		{
			pageNumber = 0;
		}
		else
		{
			pageNumber = Integer.parseInt(request.getParameter("pageNumber")) - 1;
		}
		
		groups = groupDelegate.findAll(user.getCompany()).getList();
		packages = packageDelegate.findAll(user.getCompany(), user.getItemsPerPage(), pageNumber).getList();
		catItems = categoryItemDelegate.findAll(user.getCompany()).getList();
		
		final String sid = request.getParameter("package_id");
		if(sid != null)
		{
			final long id = Long.parseLong(sid);
			packageObj = packageDelegate.find(id);
		}
	}
	
	public String save()
	{
		final String name = request.getParameter("package_name");
		final String sku = request.getParameter("sku");
		final String parentGroup = request.getParameter("parentGroup");
		final String description = request.getParameter("description");
		
		
		if(StringUtils.isEmpty(name) || !hasPackages)
		{
			return Action.ERROR;
		}
		
		packageObj = new IPackage();
		
		if(StringUtils.isNotEmpty(parentGroup))
		{
			final Group g = groupDelegate.find(Long.parseLong(parentGroup));
			packageObj.setParentGroup(g);
		}
		
		if(company.getName().equals(CompanyConstants.WILCON)){
			final Double price = Double.parseDouble(request.getParameter("price")); 
			final int duration = Integer.parseInt(request.getParameter("duration"));
			final int stock = Integer.parseInt(request.getParameter("stock"));
			CompanySettings companySettings = company.getCompanySettings();
			MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
			File[] file = r.getFiles("image");
			String[] filenames = r.getFileNames("image");
			String[] contenttype = r.getContentTypes("image");
			
			if(file!=null){
				logger.info("UPLOADING..............");
				
				String destinationPath = "companies";
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				
				destinationPath += File.separator + company.getName();
				
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				destinationPath += File.separator + "images";
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				destinationPath += File.separator + "packages";
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
						
				// create the image1, image2, image3 and thumbnail folders
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
				
				destinationPath = servletContext.getRealPath(destinationPath);
				
				if(file[0].exists()) {
					String source = file[0].getAbsolutePath();
					
					String filename = FileUtil.insertPostfix(filenames[0].replace(" ", "_"), 
							String.valueOf(new Date().getTime()));	
					System.out.println(filename);
					newFilename = filename;
					/*
					//I removed this code because it intercept the gif / png upload, and change the file to JPG format
					filename = FileUtil.replaceExtension(filename, "jpg"); 
					*/
					
					// generate original image
					File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
					FileUtil.copyFile(file[0], origFile);
					
					Long companyId = company.getId();
					
				}
				
				
				logger.info("UPLOADING SUCCESS !!!!!!!!!!!!!!!!!!!!!!!!!");
				
			}
			
			packageObj.setPrice(price);
			packageObj.setDuration(duration);
			packageObj.setImage(newFilename);
			packageObj.setStock(stock);
		}
		
		packageObj.setCompany(user.getCompany());
		packageObj.setName(name);
		packageObj.setSku(sku);
		packageObj.setDescription(description);
		packageObj.setIsValid(true);
		
		final long id = packageDelegate.insert(packageObj);
		
		if(company.getName().equals("wilcon")){
			packageObj = packageDelegate.find(id);
			
			if(packageObj != null){
				CategoryItem newPackage = new CategoryItem();
				newPackage.setName(packageObj.getName());
				newPackage.setPrice(packageObj.getPrice());
				newPackage.setInfo1(String.format("%s", packageObj.getId()));
				newPackage.setUnitOfMeasure(newFilename);
				newPackage.setCompany(company);
				/*Category packCat = categoryDelegate.find(Long.parseLong("17735"));*/
				Category packCat = categoryDelegate.find(Long.parseLong("17804"));
				newPackage.setParent(packCat);
				newPackage.setParentGroup(packCat.getParentGroup());
				
				long idd = categoryItemDelegate.insert(newPackage);
				CategoryItem ccc = categoryItemDelegate.find(idd);
				
				CategoryItemOtherField ciof = new CategoryItemOtherField();
				ciof.setCompany(company);
				ciof.setCategoryItem(ccc);
				ciof.setOtherField(packCat.getParentGroup().getOtherFields().get(4));
				ciof.setContent(String.format("%s", packageObj.getStock()));
				categoryItemOtherFieldDelegate.insert(ciof);
				
				packageObj.setInfo1(String.format("%s", idd));
				packageDelegate.update(packageObj);
				
			}
		}else{
			packageObj.setId(id);
		}
		
		
		return Action.SUCCESS;
	}
	
	public String delete()
	{
		final String package_id = request.getParameter("package_id");
		
		if(package_id == null || !hasPackages)
		{
			return Action.ERROR;
		}
		
		packageObj = packageDelegate.find(Long.parseLong(package_id));
		
		if(packageObj == null)
		{
			// System.out.println("Package is null. ID is " + package_id);
			return Action.ERROR;
		}
		
		if(packageDelegate.deletePackage(packageObj))
		{
			return Action.SUCCESS;
		}
		
		// System.out.println("failed to delete");
		
		return Action.ERROR;
	}
	
	public String update()
	{
		final String packageId = request.getParameter("package_id");
		final String packageName = request.getParameter("package_name");
		final String sku = request.getParameter("sku");
		final String description = request.getParameter("description");
		
		if(packageId == null || packageName == null || sku == null || !hasPackages)
		{
			return Action.ERROR;
		}
		
		
		packageObj = packageDelegate.find(Long.parseLong(packageId));
		
		
		if(company.getName().equals(CompanyConstants.WILCON)){
			final Double price = Double.parseDouble(request.getParameter("price")); 
			final int duration = Integer.parseInt(request.getParameter("duration"));
			final int stock = Integer.parseInt(request.getParameter("stock"));
			CompanySettings companySettings = company.getCompanySettings();
			MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
			File[] file = r.getFiles("image");
			String[] filenames = r.getFileNames("image");
			String[] contenttype = r.getContentTypes("image");
			
			if(file!=null){
				logger.info("UPLOADING..............");
				
				String destinationPath = "companies";
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				
				destinationPath += File.separator + company.getName();
				
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				destinationPath += File.separator + "images";
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
				destinationPath += File.separator + "packages";
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
						
				// create the image1, image2, image3 and thumbnail folders
				FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
				
				destinationPath = servletContext.getRealPath(destinationPath);
				
				if(file[0].exists()) {
					String source = file[0].getAbsolutePath();
					
					String filename = FileUtil.insertPostfix(filenames[0].replace(" ", "_"), 
							String.valueOf(new Date().getTime()));	
					System.out.println(filename);
					newFilename = filename;
					/*
					//I removed this code because it intercept the gif / png upload, and change the file to JPG format
					filename = FileUtil.replaceExtension(filename, "jpg"); 
					*/
					
					// generate original image
					File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
					FileUtil.copyFile(file[0], origFile);
					
					Long companyId = company.getId();
					
				}
				
				packageObj.setImage(newFilename);
				logger.info("UPLOADING SUCCESS !!!!!!!!!!!!!!!!!!!!!!!!!");
				
				packageObj.setStock(stock);
			}
			
			packageObj.setPrice(price);
			packageObj.setDuration(duration);
			
			String id = packageObj.getName();
			
			CategoryItem newPackage = categoryItemDelegate.findByName(company, id);
				
			if(newPackage != null){
				newPackage.setName(packageObj.getName());
				newPackage.setPrice(packageObj.getPrice());
				newPackage.setInfo1(String.format("%s", packageObj.getId()));
				newPackage.setUnitOfMeasure(newFilename);
				newPackage.setCompany(company);
				/*Category packCat = categoryDelegate.find(Long.parseLong("17735"));*/
				Category packCat = categoryDelegate.find(Long.parseLong("17804"));
				newPackage.setParent(packCat);
				newPackage.setParentGroup(packCat.getParentGroup());
				newPackage.getCategoryItemOtherFieldMap().get("Stock").setContent(String.format("%s", stock));
				categoryItemDelegate.update(newPackage);
				
			}
			
		}
		
		packageObj.setName(packageName);
		packageObj.setSku(sku);
		packageObj.setDescription(description);
		
		if(!packageDelegate.update(packageObj))
		{
			return Action.ERROR;
		}
		
		return Action.SUCCESS;
	}
	
	public String edit()
	{
		final String package_id = request.getParameter("package_id");
		
		if(package_id == null || !hasPackages)
		{
			return Action.ERROR;
		}
		
		packageObj = packageDelegate.find(Long.parseLong(package_id));
		return Action.SUCCESS;
	}
	
	public String deleteitem()
	{
		final boolean success;
		
		if(getCipId() != null)
		{
			final CategoryItemPackage cip = categoryItemPackageDelegate.find(getCipId());
			if(cip == null)
			{
				success = false;
			}
			else
			{
				success = categoryItemPackageDelegate.delete(cip);
			}
		}
		else
		{
			setMessage("Could not delete item.");
			success = false;
		}
		
		setMessage(success
			? "Item deleted."
			: "Could not delete item.");
		
		return success ? SUCCESS : ERROR;
	}
	
	public String additem()
	{
		final String message;
		final boolean success;
		
		if(getPackageId() != null && getCiId() != null)
		{
			final IPackage pakeyds = packageDelegate.find(getPackageId());
			final CategoryItem item = categoryItemDelegate.find(company, getCiId());
			
			if(pakeyds == null)
			{
				message = "Packages does not exist.";
				success = false;
			}
			else if(pakeyds != null && !pakeyds.getCompany().equals(company))
			{
				message = "Packages does not exist.";
				success = false;
			}
			else if(item == null)
			{
				message = "Item not found.";
				success = false;
			}
			else
			{
				final CategoryItemPackage cip = new CategoryItemPackage();
				
				cip.setCategoryItem(item);
				cip.setCompany(company);
				cip.setiPackage(pakeyds);
				
				success = categoryItemPackageDelegate.insert(cip) != null;
				message = success
					? "Item added."
					: "Problem adding item.";
			}
		}
		else
		{
			message = "Package / Item not found.";
			success = false;
		}
		
		setMessage(message);
		
		return success
			? SUCCESS
			: ERROR;
	}
	
	public IPackage getPackageObj()
	{
		return packageObj;
	}
	
	public List<CategoryItem> getCatItems()
	{
		return catItems;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	
	@Override
	public void setUser(User user)
	{
		this.user = user;
	}
	
	@Override
	public int getPage()
	{
		return page;
	}
	
	@Override
	public void setPage(int page)
	{
		this.page = page;
	}
	
	@Override
	public int getTotalItems()
	{
		return totalItems;
	}
	
	@Override
	public void setTotalItems()
	{
		totalItems = packageDelegate.findAll(user.getCompany()).getTotal();
	}
	
	@Override
	public void setItemsPerPage(int itemsPerPage)
	{
		this.itemsPerPage = itemsPerPage;
	}
	
	public int getItemsPerPage()
	{
		return itemsPerPage;
	}
	
	public List<Group> getGroups()
	{
		return groups;
	}
	
	public void setGroups(List<Group> groups)
	{
		this.groups = groups;
	}
	
	public List<IPackage> getPackages()
	{
		return packages;
	}
	
	public void setPackages(List<IPackage> packages)
	{
		this.packages = packages;
	}
	
	public Long getCiId()
	{
		return ciId;
	}
	
	public Long getCipId()
	{
		return cipId;
	}
	
	public void setCipId(Long cipId)
	{
		this.cipId = cipId;
	}
	
	public Long getPackageId()
	{
		return packageId;
	}
	
	public void setCiId(Long ciId)
	{
		this.ciId = ciId;
	}
	
	public void setPackageId(Long packageId)
	{
		this.packageId = packageId;
	}
	
	public Company getCompany()
	{
		return company;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	@Override
	public void setCompany(Company company)
	{
		this.company = company;
	}
	
	public List<CategoryItem> getAllCategoryItems()
	{
		return company == null
			? null
			: categoryItemDelegate.findAll(company).getList();
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
}
