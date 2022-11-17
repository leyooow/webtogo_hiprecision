package com.ivant.cms.action.admin;

import java.util.List;

import com.ivant.cms.delegate.CategoryItemComponentDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.ComponentCategoryDelegate;
import com.ivant.cms.delegate.ComponentDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemComponent;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Component;
import com.ivant.cms.entity.ComponentCategory;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ComponentAction 
	extends ActionSupport implements UserAware, CompanyAware, Preparable{

	private static final long serialVersionUID = 6744707787449738468L;
	
	private CategoryItemComponentDelegate categoryItemComponentDelegate = CategoryItemComponentDelegate.getInstance();
	private ComponentDelegate componentDelegate = ComponentDelegate.getInstance();
	private ComponentCategoryDelegate componentCategoryDelegate = ComponentCategoryDelegate.getInstance();
	
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	
	private User user;
	private Company company;
	
	private Component component;
	private ComponentCategory componentCategory;
	
	private Long componentId;
	private Long componentCategoryId;
	
	private Long itemId;
	private Long groupId;
	
	private Long itemComponentId;
	
	private CategoryItem item;
	private Group group;
	
	private List<Component> componentList;
	
	private Long[] componentIdList;
	private Double[] valueList;
	private String[] variableList;
	private String[] equationList;

	private PagingUtil pagingUtil;
	
	private int page;
	
	@Override
	public void prepare() throws Exception {
		
		if(componentId == null) {
			component = new Component();
		} else {
			component = componentDelegate.find(componentId);
		}
		
		if(componentCategoryId != null)
			componentCategory = componentCategoryDelegate.find(componentCategoryId);
		
		if(itemId != null)
			item = categoryItemDelegate.find(itemId);
		
		if(groupId != null)
			group = groupDelegate.find(groupId);
	}
	
	public String execute() {
		if(componentId == null) {
			int itemsPerPage = user.getItemsPerPage() != null ? user.getItemsPerPage() : 10;
			ObjectList<Component> list = componentDelegate.findAll(page, itemsPerPage, null, company);
			pagingUtil = new PagingUtil(list.getTotal(), itemsPerPage, page < 1 ? 1 : page);
			componentList = list.getList();
		}
		return SUCCESS;
	}
	
	public String save() {
		
		component.setCompany(company);
		component.setCategory(componentCategory);
		
		if(component.getId() == null) {
			componentDelegate.insert(component);
		} else {
			componentDelegate.update(component);
		}
		
		return SUCCESS;
	}
	
	public String delete() {
		
		componentDelegate.delete(component);
		
		return SUCCESS;
	}
	
	public String saveItemComponent() {
		
		if(componentIdList != null) {
			for(int i=0; i<componentIdList.length; i++) {
				
				Component component = componentDelegate.find(componentIdList[i]);
				
				CategoryItemComponent itemComponent = new CategoryItemComponent();
				
				itemComponent.setCategoryItem(item);
				itemComponent.setComponent(component);
				itemComponent.setVariable("ITEM"+(i+1));
				itemComponent.setEquation(equationList[i]);
				categoryItemComponentDelegate.insert(itemComponent);
			}
		}
		
		item = categoryItemDelegate.find(item.getId());
		
		List<CategoryItemComponent> list = item.getCategoryItemComponentList();
		
		if(list != null) {
			int i = 1;
			for(CategoryItemComponent componentItem : list) {
				componentItem.setVariable("ITEM"+i);
				categoryItemComponentDelegate.update(componentItem);
				i++;
			}
		}
		
		return SUCCESS;
	}
	
	public String deleteItemComponent() {
		
		if(itemComponentId != null) {
			CategoryItemComponent item = categoryItemComponentDelegate.find(itemComponentId);
			
			categoryItemComponentDelegate.delete(item);
			
			CategoryItem catItem = item.getCategoryItem();
			
			List<CategoryItemComponent> list = catItem.getCategoryItemComponentList();
			
			if(list != null) {
				int i = 1;
				for(CategoryItemComponent componentItem : list) {
					componentItem.setVariable("ITEM"+i);
					categoryItemComponentDelegate.update(componentItem);
					i++;
				}
			}
		}
		
		
		return SUCCESS;
	}
	
	public Component getComponent() {
		return component;
	}

	public List<Component> getComponentList() {
		return componentList;
	}
	
	public List<ComponentCategory> getComponentCategoryList() {
		return componentCategoryDelegate.findAll(company).getList();
	}
	
	public PagingUtil getPagingUtil() {
		return pagingUtil;
	}

	public CategoryItem getItem() {
		return item;
	}

	public Group getGroup() {
		return group;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public void setComponentCategoryId(Long componentCategoryId) {
		this.componentCategoryId = componentCategoryId;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public void setItemComponentId(Long itemComponentId) {
		this.itemComponentId = itemComponentId;
	}

	public void setComponentIdList(Long[] componentIdList) {
		this.componentIdList = componentIdList;
	}

	public void setValueList(Double[] valueList) {
		this.valueList = valueList;
	}

	public void setVariableList(String[] variableList) {
		this.variableList = variableList;
	}

	public void setEquationList(String[] equationList) {
		this.equationList = equationList;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setCompany(Company company) {
		this.company = company;
	}


	

}
