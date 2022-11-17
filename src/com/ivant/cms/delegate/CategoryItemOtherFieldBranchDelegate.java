package com.ivant.cms.delegate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.beans.CountBean;
import com.ivant.cms.db.CategoryItemOtherFieldBranchDAO;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherFieldBranch;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;

public class CategoryItemOtherFieldBranchDelegate extends
		AbstractBaseDelegate<CategoryItemOtherFieldBranch, CategoryItemOtherFieldBranchDAO>
{
	private static CategoryItemOtherFieldBranchDelegate instance = new CategoryItemOtherFieldBranchDelegate();

	public static CategoryItemOtherFieldBranchDelegate getInstance()
	{
		return instance;
	}

	public CategoryItemOtherFieldBranchDelegate()
	{
		super(new CategoryItemOtherFieldBranchDAO());
	}

	public CategoryItemOtherFieldBranch findByCategoryItemOtherFieldBranch(Company company,
			CategoryItem categoryItem, OtherField otherField)
	{
		return dao.findByCategoryItemOtherFieldBranch(company, categoryItem,
				otherField);
	}

	public ObjectList<CategoryItemOtherFieldBranch> findAll(Company company,
			CategoryItem item)
	{
		return dao.findAll(company, item);
	}

	/* added for category and model filtering */
	public ObjectList<CategoryItemOtherFieldBranch> findAll(Company company)
	{
		return dao.findAll(company);
	}

	/* added for category and model filtering */
	// public ObjectList<CategoryItemOtherFieldBranch> findAllBySearch(Company
	// company,Brand brand, List<OtherField> otherFields, List<String>
	// otherFieldValues, int resultPerPage, int pageNumber, Order...orders) {
	// return
	// dao.findDescItemsWithPaging(company,brand,otherFields,otherFieldValues,resultPerPage,
	// pageNumber,orders);
	// }
	public ObjectList<CategoryItemOtherFieldBranch> findAllBySearch(Company company,
			Brand brand, List<OtherField> otherFields,
			List<String> otherFieldValues, int resultPerPage, int pageNumber,
			Order... orders)
	{
		return dao.findDescItemsWithPaging(company, brand, otherFields,
				otherFieldValues, resultPerPage, pageNumber, orders);
	}

	public ObjectList<CategoryItemOtherFieldBranch> findByCategoryItem(Company company, CategoryItem item){
		return dao.findByCategoryItem(company, item);
	}
	
	public CategoryItemOtherFieldBranch findByOtherFieldName(Company company, CategoryItem categoryItem, String name)
	{
		return dao.findByOtherFieldKeyword(company, categoryItem, name, false);
	}
	
	public CategoryItemOtherFieldBranch findByOtherFieldKeyword(Company company, CategoryItem categoryItem, String keyword)
	{
		return dao.findByOtherFieldKeyword(company, categoryItem, keyword, true);
	}
	
	public ObjectList<CategoryItemOtherFieldBranch> findByNameContains(Company company, String nameContent){
		return dao.findByNameContains(company, nameContent);
	}
	
	public ObjectList<CategoryItemOtherFieldBranch> findByContent(Company company, String keyWord){
		return dao.findByContent(company, keyWord);
	}
	
	public List<String> findAllByContentAndOtherFieldList(Company company, String content, List<OtherField> ofList){
		return dao.findAllByContentAndOtherFieldList(company, content, ofList);
	}
	
	public List<CategoryItemOtherFieldBranch> findAllByCategoryItemOtherFieldBranch(Company company, CategoryItem categoryItem, OtherField otherField)
	{
		return dao.findAllByCategoryItemOtherFieldBranch(company, categoryItem, otherField);
	}
	
	public List<CountBean> findItemCountPerOtherFieldValue(Company company, Group group, OtherField otherField){
		return dao.findItemCountPerOtherFieldValue(company, group, otherField);
	}
	
	public void update(CategoryItem item, CategoryItemOtherFieldBranch otherField, Company company, User user, List<Log> updateLogList){
		final CategoryItemOtherFieldBranch oldOtherField = dao.findWithNewSession(otherField.getId(), true);
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
