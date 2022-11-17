package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.LogDAO;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;

public class LogDelegate extends AbstractBaseDelegate<Log, LogDAO> {
	
	private static final LogDelegate instance = new LogDelegate();
	
	public static LogDelegate getInstance() {
		return instance;
	}

	private LogDelegate() {
		super(new LogDAO());
	}
	
	public ObjectList<Log> findLastWeek(Company company) {
		return dao.getLogsLastWeek(company);
	}
	
	public ObjectList<Log> findAll(Company company,int pageNumber, int maxResult) {
		return dao.getAllLogs(company,pageNumber,maxResult);
	}
	
	public ObjectList<Log> findAll(User user,int pageNumber, int maxResult) {
		return dao.getAllLogs(user,pageNumber,maxResult);
	}
	
	public ObjectList<Log> findAll(User user,int pageNumber, int maxResult, String startDate, String endDate) {
		return dao.getAllLogs(user,pageNumber,maxResult, startDate, endDate);
	}

//	public ObjectList<Log> findAll(SinglePage singlePage) {
//		return dao.getAllLogs(singlePage);
//	}

//	public ObjectList<Log> findAll(CategoryItem item) {
//		return dao.getAllLogs(item);
//	}
	
	public ObjectList<Log> findAll(Long entityid, EntityLogEnum type) {
		return dao.getAllLogs(entityid, type);
	}

//	public ObjectList<Log> findAll(MultiPage multiPage) {
//		return dao.getAllLogs(multiPage);
//	}
//
//	public ObjectList<Log> findAll(Category category) {
//		return dao.getAllLogs(category);
//	}
	
	public List<Log> findAllLogs(Company company, Long entityid, EntityLogEnum entityEnum, String[] transType) {
		return dao.findAllLogs(company, entityid, entityEnum, transType);
	}
	
	public ObjectList<Log> findCategoryItemLogsWithFilter(Company company, String filter, EntityLogEnum entityEnum, int pageNumber, int maxResult) {
		return dao.findCategoryItemLogsWithFilter(company, filter, entityEnum, pageNumber, maxResult);
	}
}
