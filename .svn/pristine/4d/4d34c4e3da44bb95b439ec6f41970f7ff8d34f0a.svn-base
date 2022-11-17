package com.ivant.cms.interfaces;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;

public interface CompanyRegistration {

	public void prepare(List<String> listOfFieldsRepeating,List<String> listOfFields,List<String> listOfFiles,HttpServletRequest request, Company company, Member member
			, Map<String, File> mapFile,Map<String, String> mapFileName,ServletContext servletContext,String status);
	public void initializeCompany();
	
	public Long executeRegistration();
	
}
