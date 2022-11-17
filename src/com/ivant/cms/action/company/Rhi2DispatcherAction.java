package com.ivant.cms.action.company;

import java.util.List;

import com.ivant.cms.action.PageDispatcherAction;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.interfaces.PageDispatcherAware;

public class Rhi2DispatcherAction extends PageDispatcherAction implements PageDispatcherAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void prepare() throws Exception
	{
		super.prepare();
	}
	
	@Override
	public String execute() throws Exception
	{
		return super.execute();
	}
	
	public String doSearch(){
		
		
//		groupDelegate.find(Long.parseLong("364"));
		final List<Category> categoriesList = categoryDelegate.findAll(company).getList();
		request.setAttribute("categoriesList", categoriesList);
			
		final List<ItemFile> itemFile = itemFileDelegate.findAll(company);
		request.setAttribute("itemFilesList", itemFile);
		return SUCCESS;
	}
}
