package com.ivant.cms.delegate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.beans.CountBean;
import com.ivant.cms.db.CategoryItemOtherFieldDAO;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;

public class CategoryItemOtherFieldDelegate extends
		AbstractBaseDelegate<CategoryItemOtherField, CategoryItemOtherFieldDAO>
{
	private static CategoryItemOtherFieldDelegate instance = new CategoryItemOtherFieldDelegate();

	public static CategoryItemOtherFieldDelegate getInstance()
	{
		return instance;
	}

	public CategoryItemOtherFieldDelegate()
	{
		super(new CategoryItemOtherFieldDAO());
	}

	public CategoryItemOtherField findByCategoryItemOtherField(Company company,
			CategoryItem categoryItem, OtherField otherField)
	{
		return dao.findByCategoryItemOtherField(company, categoryItem,
				otherField);
	}

	public ObjectList<CategoryItemOtherField> findAll(Company company,
			CategoryItem item)
	{
		return dao.findAll(company, item);
	}

	/* added for category and model filtering */
	public ObjectList<CategoryItemOtherField> findAll(Company company)
	{
		return dao.findAll(company);
	}

	/* added for category and model filtering */
	// public ObjectList<CategoryItemOtherField> findAllBySearch(Company
	// company,Brand brand, List<OtherField> otherFields, List<String>
	// otherFieldValues, int resultPerPage, int pageNumber, Order...orders) {
	// return
	// dao.findDescItemsWithPaging(company,brand,otherFields,otherFieldValues,resultPerPage,
	// pageNumber,orders);
	// }
	public ObjectList<CategoryItemOtherField> findAllBySearch(Company company,
			Brand brand, List<OtherField> otherFields,
			List<String> otherFieldValues, int resultPerPage, int pageNumber,
			Order... orders)
	{
		return dao.findDescItemsWithPaging(company, brand, otherFields,
				otherFieldValues, resultPerPage, pageNumber, orders);
	}

	public ObjectList<CategoryItemOtherField> findByCategoryItem(
			Company company, CategoryItem item)
	{
		return dao.findByCategoryItem(company, item);
	}
	
	public CategoryItemOtherField findByOtherFieldName(Company company, CategoryItem categoryItem, String name)
	{
		return dao.findByOtherFieldKeyword(company, categoryItem, name, false);
	}
	
	public CategoryItemOtherField findByOtherFieldKeyword(Company company, CategoryItem categoryItem, String keyword)
	{
		return dao.findByOtherFieldKeyword(company, categoryItem, keyword, true);
	}
	
	public ObjectList<CategoryItemOtherField> findByNameContains(Company company, String nameContent){
		return dao.findByNameContains(company, nameContent);
	}
	
	public ObjectList<CategoryItemOtherField> findByContent(Company company, String keyWord){
		return dao.findByContent(company, keyWord);
	}
	
	public List<String> findAllByContentAndOtherFieldList(Company company, String content, List<OtherField> ofList){
		return dao.findAllByContentAndOtherFieldList(company, content, ofList);
	}
	
	public List<CategoryItemOtherField> findAllByCategoryItemOtherField(Company company, CategoryItem categoryItem, OtherField otherField)
	{
		return dao.findAllByCategoryItemOtherField(company, categoryItem, otherField);
	}
	
	public List<CountBean> findItemCountPerOtherFieldValue(Company company, Group group, OtherField otherField){
		return dao.findItemCountPerOtherFieldValue(company, group, otherField);
	}
	
	public void update(CategoryItem item, CategoryItemOtherField otherField, Company company, User user, List<Log> updateLogList){
		final CategoryItemOtherField oldOtherField = dao.findWithNewSession(otherField.getId(), true);
		boolean updateSuccess = dao.update(otherField);
		if(updateSuccess){
			Log log = new Log();

			log.setEntityType(EntityLogEnum.CATEGORY_ITEM);
			log.setEntityID(item.getId());
			log.setCompany(company);
			log.setUpdatedByUser(user);
			
			if(!otherField.getContent().equals(oldOtherField.getContent())){
				log.setRemarks("Change " + otherField.getOtherField().getName() + " from " + oldOtherField.getContent() + " to " + otherField.getContent());
				updateLogList.add(log);
			}			
		}
		

	}
}
