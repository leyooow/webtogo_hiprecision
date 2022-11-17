package com.ivant.cms.delegate;

import com.ivant.cms.db.BatchUpdateExcelFileDAO;
import com.ivant.cms.entity.BatchUpdateExcelFile;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class BatchUpdateExcelFileDelegate extends AbstractBaseDelegate<BatchUpdateExcelFile, BatchUpdateExcelFileDAO> {
	private static BatchUpdateExcelFileDelegate instance = new BatchUpdateExcelFileDelegate();
	
	public static BatchUpdateExcelFileDelegate getInstance() {
		return instance;
	}
	
	public BatchUpdateExcelFileDelegate() {
		super(new BatchUpdateExcelFileDAO());
	}

	public boolean isInTheDatabase(Company company, Group group, String absolutePath) {
		return dao.isInTheDatabase(company, group, absolutePath);
	}

	public ObjectList<BatchUpdateExcelFile> findAll(Company company) {
		return dao.findAll(company);
	}

	public ObjectList<BatchUpdateExcelFile> findAll(Company company, Group group) {
		return dao.findAll(company, group);
	}

	public ObjectList<BatchUpdateExcelFile> findByPath(Company company, Group group, String absolutePath) {
		return dao.findByPath(company, group, absolutePath);
	}
}
