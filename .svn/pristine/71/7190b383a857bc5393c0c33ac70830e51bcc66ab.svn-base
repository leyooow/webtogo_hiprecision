package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.ItemCommentDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;

/**
 * 
 * @author Irish
 * 
 *         Manages database transactions with the ItemComment Entity
 */
public class ItemCommentDelegate extends AbstractBaseDelegate<ItemComment, ItemCommentDAO> {

	private static ItemCommentDelegate instance;

	protected ItemCommentDelegate() {
		super(new ItemCommentDAO());
	}

	/**
	 * 
	 * @return MessageDelegate Instance
	 */
	public static ItemCommentDelegate getInstance() {
		if (instance == null) {
			return new ItemCommentDelegate();
		}
		return instance;
	}
	
	public ObjectList<ItemComment> findCommentsByValueAndContent(Company company, CategoryItem item, double value, String comment, Member member){
		return dao.findCommentsByValueAndContent(company, item, value, comment, member);
	}
	
	public ObjectList<ItemComment> findCommentsByContent(Company company, SinglePage singlePage, String comment, Member member){
		return dao.findCommentsByContent(company, singlePage, comment, member);
	}
	
	public ObjectList<ItemComment> findChildCommentsByValueAndContent(Company company, CategoryItem item, ItemComment itemComment, double value, String comment, Member member){
		return dao.findChildCommentsByValueAndContent(company, item, itemComment, value, comment, member);
	}
	
	public ObjectList<ItemComment> findChildCommentsByContent(Company company, SinglePage singlePage, ItemComment itemComment, String comment, Member member){
		return dao.findChildCommentsByContent(company, singlePage, itemComment, comment, member);
	}
	
	public ObjectList<ItemComment> findAllByMemberAndItemContent(Company company, CategoryItem categoryItem, Member member, String content ){
		return dao.findAllByMemberAndItemContent(company, categoryItem, member, content);
	}
	
	public ObjectList<ItemComment> getCommentsByItem(CategoryItem item){
		return dao.getCommentsByItem(item);
	}
	
	
	public ObjectList<ItemComment> getCommentsByItem(CategoryItem item, Order...orders){
		return dao.getCommentsByItem(item, orders);
	}
	
	public ObjectList<ItemComment> findAllParentCommentsByItem(CategoryItem item, Order...orders){
		return dao.findAllParentCommentsByItem(item, orders);
	}
	
	public ObjectList<ItemComment> findAllParentCommentsBySinglePage(SinglePage singlePage, Order...orders){
		return dao.findAllParentCommentsBySinglePage(singlePage, orders);
	}
	
	public ObjectList<ItemComment> findAllParentCommentsBySinglePagePublished(SinglePage singlePage, Order...orders){
		return dao.findAllParentCommentsBySinglePagePublished(singlePage, orders);
	}
	
	public Integer findAllParentCommentsByItemSize(CategoryItem item, Order...orders){
		return dao.findAllParentCommentsByItemSize(item, orders);
	}
	
	public ObjectList<ItemComment> getCommentsByPage(SinglePage page, Order...orders){
		return dao.getCommentsByPage(page, orders);
	}
	
	public ObjectList<ItemComment> getCommentsByCompany(Company company){
		return dao.getCommentsByCompany(company);
	}
	
	public ObjectList<ItemComment> getTruecareComments(Company company, Order...orders){
		return dao.getTruecareComments(company, orders);
	}
	
	public ObjectList<ItemComment> findLatestComments(Company company, int days, Order...orders){ 
		return dao.findLatestComments(company, days, orders);
	}
	
	public ObjectList<ItemComment> findAllValid(Company company,  Order...orders) {
		return dao.findAllValid(company, orders);
	}
	
	public ObjectList<ItemComment> findAllValidByCategoryItem(Company company, CategoryItem item, Order...orders){
		return dao.findAllValidByCategoryItem(company, item, orders);
	}
	
	public ObjectList<ItemComment> findAllValidBySinglePage(Company company, SinglePage singlePage, Order...orders) {
		return dao.findAllValidBySinglePage(company, singlePage, orders);
	}
	
	public ObjectList<ItemComment> findAll(Company company) {
		return dao.findAll(company);
	}
	
	public ObjectList<ItemComment> findLatestCommentsWithPaging(Company company, int days,  int resultPerPage, int pageNumber, Order...orders) {
		return dao.findLatestCommentsWithPaging(company, days, resultPerPage, pageNumber, orders);
	}
	
	public ObjectList<ItemComment> getCommentsByItemWithPaging(CategoryItem item, int resultPerPage, int pageNumber, Order...orders) {
		return dao.getCommentsByItemWithPaging(item, resultPerPage, pageNumber, orders);
	}
	
	public ObjectList<ItemComment> getCommentsByCompanyWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
		return dao.getCommentsByCompanyWithPaging(company, resultPerPage, pageNumber, orders);
	}
	
	public ObjectList<ItemComment> getCommentsByType(Company company, String type, Order...orders) {
		return dao.getCommentsByType(company, type, orders);
	}
	
	public ObjectList<ItemComment> findAllLatestCommentByGivenId(Company company, CategoryItem categoryItem, Long latestCommentId, Order...orders){
		return dao.findAllLatestCommentByGivenId(company, categoryItem, latestCommentId, orders);
	}
	
	public Double getAverageValue(Company company, CategoryItem item) {
		return dao.getAverageValue(company, item);
	}
	
	public Double getValuePercentage(Company company, CategoryItem item, Double[] value){
		return dao.getValuePercentage(company, item, value);
	}
	
	public Integer findSinglePageCommentCount(Company company, SinglePage singlePage) {
		return dao.findSinglePageCommentCount(company, singlePage);
	}
}