package com.ivant.cms.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.utils.PagingUtil;

public class PageBaseAction extends MenuLoaderAction {
	private Logger logger = LoggerFactory.getLogger(PageBaseAction.class);
	private static final long serialVersionUID = 6922051398556345531L;
	protected Integer pageNumber;

	@Override
	public void prepare() throws Exception {
		super.prepare();
		setPageNumber();
	}
	
	public void setPaging(int itemSize, int itemsPerPage) {
		request.setAttribute("pagingUtil", new PagingUtil(itemSize, itemsPerPage, pageNumber, (itemSize/itemsPerPage)));
	}

	/**
	 * @return the pageNumber
	 */
	public int setPageNumber() {
		String page = request.getParameter("page");
 		try{
			pageNumber = Integer.parseInt(page);
		} catch(Exception e){
			logger.info("Cannot parse {} as page number" , page);
			pageNumber = 1;
			return pageNumber;
		}
		return pageNumber;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}
}
