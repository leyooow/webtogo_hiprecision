/**
 *
 */
package com.ivant.cms.action.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.Order;

import com.ivant.cms.action.PageDispatcherAction;
import com.ivant.cms.beans.KuysenClientBean;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemPackage;
import com.ivant.cms.entity.IPackage;
import com.ivant.cms.interfaces.KuysenClientAware;
import com.ivant.cms.interfaces.PageDispatcherAware;

/**
 * @author Edgar S. Dacpano, Daniel B. Sario
 */
public class KuysenDispatcherAction
		extends PageDispatcherAction
		implements PageDispatcherAware
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * K: {@link CategoryItem#getId()} V: Set of {@link IPackage} that contains this K.
	 */
	private Map<String, Set<IPackage>> packageMap;
	private Set<IPackage> packages;
	
	private KuysenClientBean kuysenClientBean;
	
	@Override
	public void prepare() throws Exception
	{
		super.prepare();
	}
	
	@Override
	public String execute() throws Exception
	{
		final String result = super.execute();
		
		final Object obj = request.getAttribute("category");
		if(obj != null)
		{
			if(obj instanceof Category)
			{
				//final Category category = (Category) obj;
				Category category = categoryDelegate.find(((Category) obj).getId());
				
				if(request.getParameter("searchkeyword") != null && request.getParameter("in") != null) {
					category = (Category) request.getAttribute("category");
				}
				
				preparePackageMap(category);
				preparePackage();
			}
		}
		
		return result;
	}
	
	private void preparePackageMap(Category category)
	{
		final Map<String, Set<IPackage>> packageMap = new HashMap<String, Set<IPackage>>();
		
		if(category != null)
		{
			final List<Category> children = categoryDelegate.findAllChildrenOfChildrenCategory(company, category, -1, -1, Order.asc("id")).getList();
			children.add(category);
			
			final Set<Long> itemSets = new HashSet<Long>();
			
			for(Category c : children)
			{
				final List<CategoryItem> items = categoryItemDelegate.findAllWithPaging(company, category.getParentGroup(), c, -1, -1, true, Order.asc("name")).getList();
				if(CollectionUtils.isNotEmpty(items))
				{
					for(CategoryItem i : items)
					{
						itemSets.add(i.getId());
					}
				}
			}
			
			if(CollectionUtils.isNotEmpty(itemSets))
			{
				final List<CategoryItemPackage> itemPackages = categoryItemPackageDelegate.findAllWithPaging(company, new ArrayList<Long>(itemSets), -1, -1, Order.asc("id")).getList();
				if(CollectionUtils.isNotEmpty(itemPackages))
				{
					for(CategoryItemPackage cip : itemPackages)
					{
						final String ciId = cip.getiPackage().getSku();
						final Set<IPackage> val = packageMap.get(ciId) == null
							? new HashSet<IPackage>()
							: packageMap.get(ciId);
						
						val.add(cip.getiPackage());
						
						packageMap.put(ciId, val);
					}
				}
			}
		}
		
		setPackageMap(packageMap);
	}
	
	private void preparePackage()
	{
		if(MapUtils.isNotEmpty(getPackageMap()))
		{
			setPackages(new HashSet<IPackage>());
			for(Entry<String, Set<IPackage>> entry : getPackageMap().entrySet())
			{
				getPackages().addAll(entry.getValue());
			}
		}
	}
	
	public Map<String, Set<IPackage>> getPackageMap()
	{
		return packageMap;
	}
	
	public Set<IPackage> getPackages()
	{
		return packages;
	}
	
	public void setPackageMap(Map<String, Set<IPackage>> packageMap)
	{
		this.packageMap = packageMap;
	}
	
	public void setPackages(Set<IPackage> packages)
	{
		this.packages = packages;
	}
}
