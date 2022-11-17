package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.CategoryItemOtherFieldDAO;
import com.ivant.cms.db.RegistrationItemOtherFieldDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.entity.list.ObjectList;

public class RegistrationItemOtherFieldDelegate
		extends AbstractBaseDelegate<RegistrationItemOtherField, RegistrationItemOtherFieldDAO>
{
	private static RegistrationItemOtherFieldDelegate instance = new RegistrationItemOtherFieldDelegate();
	
	public static RegistrationItemOtherFieldDelegate getInstance() {
		return instance;
	}
	
	public ObjectList<RegistrationItemOtherField> findAllWithIndexing(Company company, Member member,String note){
		return dao.findAllWithIndexing(company, member, note);
	}
	
	public ObjectList<RegistrationItemOtherField> findAllYearWithIndexing(Company company, String note){
	    return dao.findAllYearWithIndexing(company, note);
    }
	
	public int findTotalHours(Company company){
	    return dao.findTotalHours(company);
    }

	public ObjectList<RegistrationItemOtherField> findAllWithNote(Company company, Member member,String note){
		return dao.findAllWithNote(company, member, note);
	}
	
	public RegistrationItemOtherField findByName(Company company, String name, Long member, String note, Integer indexing){
		return dao.findByName(company, name, member, note, indexing);
	}
	
	public ObjectList<RegistrationItemOtherField> findAll(Company company, Member member,String note, Integer indexing){
		return dao.findAll(company, member, note, indexing);
	}
	
	public RegistrationItemOtherFieldDelegate() {
		super(new RegistrationItemOtherFieldDAO());
	}	
	
	public RegistrationItemOtherField findByCategoryItemOtherField(Company company, Member member, OtherField otherField)
	{
		return dao.findByCategoryItemOtherField(company, member, otherField);
	}

	public ObjectList<RegistrationItemOtherField> findAll(Company company, Member member) {
		return dao.findAll(company, member);
	}

	public ObjectList<RegistrationItemOtherField> findByCategoryItem(Company company, Member member) {
		return dao.findByCategoryItem(company, member);
	}
	
	public RegistrationItemOtherField findByName(Company company, String name, Long member) {
		return dao.findByName(company, name,member);
	}
}
