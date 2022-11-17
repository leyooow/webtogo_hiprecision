package com.ivant.cms.delegate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.db.MemberDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;

public class MemberDelegate extends AbstractBaseDelegate<Member, MemberDAO>{

	private static MemberDelegate instance = new MemberDelegate();
	
	public static MemberDelegate getInstance() {
		return instance;
	}
	
	public MemberDelegate() {
		super(new MemberDAO());
	}
	
	public ObjectList<Member> findAllWithPagingWithPurpose(String purpose,Company company, int pageNumber, int itemPerPage, Order...orders) {
		return dao.findAllWithPagingWithPurpose( purpose,company, pageNumber, itemPerPage, orders);
	}
	
	public Member findAccount(String username, String password, Company company) {
		Member member = null;
		ObjectList<Member> members = dao.find(username, password, company);
		if(members.getSize() > 0) { 
			member = members.getList().get(0);
		}
		return member;
	}
	
	public Member findAccountByEmail (String email, String password, Company company) {
		Member member = null;
		ObjectList<Member> members = dao.findByEmail(email, password, company);
		if(members.getSize() > 0) { 
			member = members.getList().get(0);
		}
		return member;
	}
	
	public Member findAccount(String username, Company company) {
		Member member = null;
		ObjectList<Member> members = dao.find(username, company);
		if(members.getSize() > 0) { 
			member = members.getList().get(0);
		}
		return member;
	}
	
	public Member findEmail(Company company, String email) {
		return dao.findEmail(company, email);
	}
	
	public Member findIaveMember(Company company){
		return dao.findIaveMember(company);
	}
	
	public List<Member> findPurpose(Company company, String purpose) {
		return dao.findPurpose(company, purpose);
	}
	
	public List<Member> findPurposeByDate(Company company, String purpose, Date fromDate, Date toDate) {
		return dao.findPurposeByDate(company, purpose,fromDate,toDate);
	}
	
	public Member findMember(Member member) {
		return dao.findMember(member);
	}
	
	public Member findMember(String activationKey){
		return dao.findMember(activationKey);
	}
	
	public Member findMember(Company company, String username){
		return dao.findMember(company,username);
	}
	
	public Member findActivationKey(String activationKey){
		return dao.findActivationKey(activationKey);	
	}
	
	public ObjectList<Member> findAll(Company company) {
		return dao.findAll(company);
	}
	
	public ObjectList<Member> findAllByName(Company company) {
		return dao.findAllByName(company);
	}

	public ObjectList<Member> findAllWithPaging(Company company,int resultPerPage, int pageNumber, String[] orderBy) {
		if(company.getId() == 404L){//WENDYS
			return dao.findAllWithPaging(company,resultPerPage, pageNumber, orderBy, false);
		}
		else{
			return dao.findAllWithPaging(company,resultPerPage, pageNumber, orderBy, true);
		}
		
	}
	
	public ObjectList<Member> findAllWithPaging(Company company,int resultPerPage, int pageNumber, String[] orderBy, boolean isAscending) {
		return dao.findAllWithPaging(company,resultPerPage, pageNumber, orderBy, isAscending);
	}

	public Member findByUsername(Company company, String username) {
		return dao.findByUsername(company, username);
	}
	
	public Member findByUserId(Company company, String userid) {
		return dao.findByUserId(company, userid);
	}
	
	public ObjectList<Member> findAll(Company company, Boolean isActive) {
		return dao.findAll(company, isActive);
	}
	
	public ObjectList<Member> findAllNissanUser(Company company, String username, String outlet, long userType) {
		return dao.findAllNissanUser(company, username, outlet, userType);
	}
	
	public Member findByPageModuleAndPagemoduleUsername(String pageModule, String pageModuleUsername, String password, Company company) {
		return dao.findByPageModuleAndPagemoduleUsername(pageModule, pageModuleUsername, password, company);
	}
	
	public ObjectList<Member> findAllByPageModule(String pageModule, Company company) {
		return dao.findAllByPageModule(pageModule, company);
	}
	
	public Member findAllByEmailModule(String pageModule, String email, Company company) {
		return dao.findAllByEmailModule(pageModule, email, company);
	}
	
	/**
	 * Find all member which matches the value of the given property name.
	 * @param company
	 * @param junction - the junction operation("or" or "and"), use {@link Restrictions#conjunction()} or {@link Restrictions#disjunction()}
	 * @param filters
	 * 		K: propertyName,
	 * 		V: the value to be filtered <br>
	 * 		i.e: <br>
	 * 			search for username, map.put("username", "search string") <br>
	 *			search if activated, map.put("activated", Boolean.TRUE) <br>
	 * @return
	 */
	public ObjectList<Member> findAllByPropertyName(Company company, Junction junction, Map<String, Object> filters)
	{
		return dao.findAllByPropertyName(company, junction, filters);
	}
	
	public ObjectList<Member> findAllByLikeUsername(Company company, String username){
		return dao.findAllByLikeUsername(company, username);
	}
}
