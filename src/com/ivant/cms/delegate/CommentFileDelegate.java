package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.CommentFileDAO;
import com.ivant.cms.entity.CommentFile;
import com.ivant.cms.entity.Company;

public class CommentFileDelegate extends AbstractBaseDelegate<CommentFile, CommentFileDAO> {

	private static CommentFileDelegate instance = new CommentFileDelegate();
	
	public static CommentFileDelegate getInstance() {
		return CommentFileDelegate.instance;
	}
	
	public CommentFileDelegate() {
		super(new CommentFileDAO());
	}
	
	public CommentFile find(Company company, Long id)
	{
		return dao.find(company, id);
	}

	public List<CommentFile> findAll(Company company)
	{
		return dao.findAll(company);
	}
	
}
