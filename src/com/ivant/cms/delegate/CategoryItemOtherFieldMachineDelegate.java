package com.ivant.cms.delegate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.beans.CountBean;
import com.ivant.cms.db.CategoryItemOtherFieldMachineDAO;
import com.ivant.cms.db.CategoryItemOtherFieldMachineDAO;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherFieldMachine;
import com.ivant.cms.entity.CategoryItemOtherFieldMachine;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;

public class CategoryItemOtherFieldMachineDelegate extends
		AbstractBaseDelegate<CategoryItemOtherFieldMachine, CategoryItemOtherFieldMachineDAO>
{
	private static CategoryItemOtherFieldMachineDelegate instance = new CategoryItemOtherFieldMachineDelegate();

	public static CategoryItemOtherFieldMachineDelegate getInstance()
	{
		return instance;
	}

	public CategoryItemOtherFieldMachineDelegate()
	{
		super(new CategoryItemOtherFieldMachineDAO());
	}

	public CategoryItemOtherFieldMachine findByCategoryItemOtherFieldMachine(Company company,
			CategoryItem categoryItem, OtherField otherField)
	{
		return dao.findByCategoryItemOtherFieldMachine(company, categoryItem,
				otherField);
	}

	public ObjectList<CategoryItemOtherFieldMachine> findAll(Company company,
			CategoryItem item)
	{
		return dao.findAll(company, item);
	}

	/* added for category and model filtering */
	public ObjectList<CategoryItemOtherFieldMachine> findAll(Company company)
	{
		return dao.findAll(company);
	}

	/* added for category and model filtering */
	// public ObjectList<CategoryItemOtherFieldMachine> findAllBySearch(Company
	// company,Brand brand, List<OtherField> otherFields, List<String>
	// otherFieldValues, int resultPerPage, int pageNumber, Order...orders) {
	// return
	// dao.findDescItemsWithPaging(company,brand,otherFields,otherFieldValues,resultPerPage,
	// pageNumber,orders);
	// }
	public ObjectList<CategoryItemOtherFieldMachine> findAllBySearch(Company company,
			Brand brand, List<OtherField> otherFields,
			List<String> otherFieldValues, int resultPerPage, int pageNumber,
			Order... orders)
	{
		return dao.findDescItemsWithPaging(company, brand, otherFields,
				otherFieldValues, resultPerPage, pageNumber, orders);
	}

	public ObjectList<CategoryItemOtherFieldMachine> findByCategoryItem(Company company, CategoryItem item){
		return dao.findByCategoryItem(company, item);
	}
	
	public CategoryItemOtherFieldMachine findByOtherFieldName(Company company, CategoryItem categoryItem, String name)
	{
		return dao.findByOtherFieldKeyword(company, categoryItem, name, false);
	}
	
	public CategoryItemOtherFieldMachine findByOtherFieldKeyword(Company company, CategoryItem categoryItem, String keyword)
	{
		return dao.findByOtherFieldKeyword(company, categoryItem, keyword, true);
	}
	
	public ObjectList<CategoryItemOtherFieldMachine> findByNameContains(Company company, String nameContent){
		return dao.findByNameContains(company, nameContent);
	}
	
	public ObjectList<CategoryItemOtherFieldMachine> findByContent(Company company, String keyWord){
		return dao.findByContent(company, keyWord);
	}
	
	public List<String> findAllByContentAndOtherFieldList(Company company, String content, List<OtherField> ofList){
		return dao.findAllByContentAndOtherFieldList(company, content, ofList);
	}
	
	public List<CategoryItemOtherFieldMachine> findAllByCategoryItemOtherFieldMachine(Company company, CategoryItem categoryItem, OtherField otherField)
	{
		return dao.findAllByCategoryItemOtherFieldMachine(company, categoryItem, otherField);
	}
	
	public List<CountBean> findItemCountPerOtherFieldValue(Company company, Group group, OtherField otherField){
		return dao.findItemCountPerOtherFieldValue(company, group, otherField);
	}
	
	public void update(CategoryItem item, CategoryItemOtherFieldMachine otherField, Company company, User user, List<Log> updateLogList){
		final CategoryItemOtherFieldMachine oldOtherField = dao.findWithNewSession(otherField.getId(), true);
		boolean updateSuccess = dao.update(otherField);
		if(updateSuccess){
			Log log = new Log();

			log.setEntityType(EntityLogEnum.CATEGORY_ITEM);
			log.setEntityID(item.getId());
			log.setCompany(company);
			log.setUpdatedByUser(user);
			
			if(!otherField.getContent().equals(oldOtherField.getContent())){
				//log.setRemarks("Change " + otherField.getOtherField().getName() + " from " + oldOtherField.getContent() + " to " + otherField.getContent());
				updateLogList.add(log);
			}			
		}
		

	}
}
