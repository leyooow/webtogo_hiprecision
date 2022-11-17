package com.ivant.cms.delegate;

import com.ivant.cms.db.MemberPollDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberPoll;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;

public class MemberPollDelegate extends AbstractBaseDelegate<MemberPoll, MemberPollDAO> {

	private static MemberPollDelegate instance = new MemberPollDelegate();
	
	public static MemberPollDelegate getInstance() {
		return instance;
	}
	
	public MemberPollDelegate() {
		super(new MemberPollDAO());
	}
	
	public MemberPoll find(Company company, Member member, String pollType, String remarks, ItemComment itemComment){
		return dao.find(company, member, pollType, remarks, itemComment);
	}
	
	public MemberPoll find(Company company, Member member, String pollType, String remarks, CategoryItem item) {
		return dao.find(company, member, pollType, remarks, item);
	}
	
	public MemberPoll find(Company company, Member member, String pollType, String remarks, SinglePage singlePage){
		return dao.find(company, member, pollType, remarks, singlePage);
	}
	
	public ObjectList<MemberPoll> findAll(Company company, String pollType, String remarks, SinglePage singlePage){
		return dao.findAll(company, pollType, remarks, singlePage);
	}
	
	public Integer findCount(Company company, String pollType, String remarks, ItemComment itemComment){
		return dao.findCount(company, pollType, remarks, itemComment);
	}
	
	public Integer findCount(Company company, String pollType, String remarks, CategoryItem item){
		return dao.findCount(company, pollType, remarks, item);
	}
	
	public Integer findCount(Company company, String pollType, String remarks, SinglePage singlePage) {
		return dao.findCount(company, pollType, remarks, singlePage);
	}
	
	public ObjectList<MemberPoll> find(Member member, String pollType)
	{
		return dao.find(member, pollType);
	}
	
	public ObjectList<MemberPoll> findSupporter(CategoryItem categoryItem, String pollType)
	{
		return dao.findSupporter(categoryItem, pollType);
	}
	
	public ObjectList<MemberPoll> findByPollType(String pollType)
	{
		return dao.findByPollType(pollType);
	}
	
	public MemberPoll find(Member member, CategoryItem categoryItem, String pollType)
	{
		return dao.find(member, categoryItem, pollType);
	}
	
	public MemberPoll findByCategoryItem(CategoryItem categoryItem)
	{
		return dao.findByCategoryItem(categoryItem);
	}
	
	public int findSupportCount(CategoryItem categoryItem, String pollType)
	{
		return dao.findSupportCount(categoryItem, pollType);
	}
	
	public int findVoteCount(CategoryItem categoryItem, String pollType, String remarks)
	{
		return dao.findVoteCount(categoryItem, pollType, remarks);
	}
	
	public int findRemarksIndexVoteCount(CategoryItem categoryItem, String pollType, String remarks)
	{
		return dao.findRemarksIndexVoteCount(categoryItem, pollType, remarks);
	}
	
	public Integer findBumpPollCount(ItemComment itemComment)
	{
		return dao.findBumpPollCount(itemComment);
	}
	
	public MemberPoll find(Member member, ItemComment itemComment)
	{
		return dao.find(member, itemComment);
	}
	
	public ObjectList<MemberPoll> findCommentPoll(ItemComment itemComment)
	{
		return dao.findCommentPoll(itemComment);
	}
	
	public ObjectList<MemberPoll> findAllSingleByPollType(Company company, Member member, String pollType){
		return dao.findAllSingleByPollType(company, member, pollType);
	}
	
	public ObjectList<MemberPoll> findAllCategoryItemByPollType(Company company, Member member, String pollType){
		return dao.findAllCategoryItemByPollType(company, member, pollType);
	}
	
	public Integer findSinglePagePollCount(Company company, SinglePage singlePage, String pollType) {
		return dao.findSinglePagePollCount(company, singlePage, pollType);
	}
	
	/*-------------------------------------------------------------------------------*/
	/*--------------------------------------------------------------------------------*/
	
}
