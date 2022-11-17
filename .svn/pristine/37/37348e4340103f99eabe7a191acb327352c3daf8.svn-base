package com.ivant.cms.db;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.constants.CompanyConstants;

public class MemberDAO extends AbstractBaseDAO<Member>{

	//private Logger logger = Logger.getLogger(UserDAO.class);
	
	public MemberDAO() {
		super();
	}
	
	
	
	public ObjectList<Member> find(String username, String password, Company company) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("username", username));
		conj.add(Restrictions.eq("password", password));
		conj.add(Restrictions.eq("company", company));
			
		return findAllByCriterion(null, null, null, null, conj);
	}
	
	public ObjectList<Member> findByEmail(String email, String password, Company company) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("email", email));
		conj.add(Restrictions.eq("password", password));
		conj.add(Restrictions.eq("company", company));
			
		return findAllByCriterion(null, null, null, null, conj);
	}
	
	public ObjectList<Member> findAllWithPagingWithPurpose(String purpose,Company company, int pageNumber, int itemPerPage, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("purpose", purpose));
		return findAllByCriterion(pageNumber, itemPerPage, null, null, null, orders, junc);
	}
	
	public ObjectList<Member> find(String username, Company company) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("username", username));
		conj.add(Restrictions.eq("company", company));
			
		return findAllByCriterion(null, null, null, null, conj);
	}
	

	public Member findEmail(Company company, String email) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("email", email));
		conj.add(Restrictions.eq("company", company));
			
		ObjectList<Member> members =  findAllByCriterion(null, null, null, null, conj);
		if(members.getSize() > 0) {
			return members.getList().get(0);
		}
		return null;
	}

	public Member findIaveMember(Company company) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
			
		ObjectList<Member> members =  findAllByCriterion(null, null, null, null, conj);
		if(members.getSize() > 0) {
			return members.getList().get(0);
		}
		return null;
	}
	
	public List<Member> findPurpose(Company company, String purpose) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("purpose", purpose));
		conj.add(Restrictions.eq("company", company));
			
		ObjectList<Member> members =  findAllByCriterion(null, null, null, null, conj);
		return members.getList();
	}
	
	public List<Member> findPurposeByDate(Company company, String purpose, Date fromDate, Date toDate) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("purpose", purpose));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.ge("createdOn",fromDate));
		conj.add(Restrictions.le("createdOn",toDate));
			
		ObjectList<Member> members =  findAllByCriterion(null, null, null, null, conj);
		return members.getList();
	}
	
	public Member findMember(Member member) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("username", member.getUsername()));
		conj.add(Restrictions.eq("email", member.getEmail()));
		conj.add(Restrictions.eq("company", member.getCompany()));
			
		ObjectList<Member> members =  findAllByCriterion(null, null, null, null, conj);
		if(members.getSize() > 0) {
			return members.getList().get(0);
		}
		return null;
	}
	
	public Member findMember(String activationKey){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("activationKey", activationKey));
			
		ObjectList<Member> members =  findAllByCriterion(null, null, null, null, conj);
		if(members.getSize() > 0) {
			return members.getList().get(0);
		}
		return null;
	}
	
	public Member findMember(Company company, String username){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("username", username));
		conj.add(Restrictions.eq("company", company));
		ObjectList<Member> members =  findAllByCriterion(null, null, null, null, conj);
		if(members.getSize() > 0) {
			return members.getList().get(0);
		}
		return null;
	}
	public Member findActivationKey(String activationKey){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("activationKey", activationKey));
			
		ObjectList<Member> members =  findAllByCriterion(null, null, null, null, conj);
		if(members.getSize() > 0) {
			return members.getList().get(0);
		}
		return null;
	}
	
	public ObjectList<Member> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		Order[] orders = null;
		
		
		if(company!=null&&company.getName().equalsIgnoreCase(CompanyConstants.CANCUN))
			orders = new Order[]{Order.desc("id")};
		
		
		return findAllByCriterion(null, null, null, orders, null,	null, junc);
	}

	public ObjectList<Member> findAllByName(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		Order[] orders = {Order.asc("lastname")};
		return findAllByCriterion(null, null, null, orders, junc);
	}	
		
	public ObjectList<Member> findAllWithPaging(Company company,int resultPerPage, int pageNumber, String[] orderBy, boolean isAscending) {
		final Junction junc = Restrictions.conjunction();
		Order[] orders = null;
		
		if(orderBy == null && company != null && company.getName().equalsIgnoreCase(CompanyConstants.CANCUN))
		{
			orders = new Order[] { Order.desc("id") };
		}
		else if(orderBy == null && company != null && company.getId() == CompanyConstants.GURKKA)
		{
			junc.add(Restrictions.isNotNull("dateJoined"));
			orders = new Order[] { Order.desc("dateJoined") };
		}
		else
		{
			try
			{
				if(orderBy.length > 0)
				{
					orders = new Order[orderBy.length];
					for(int i = 0; i < orderBy.length; i++)
					{
						if(isAscending){
							orders[i] = Order.asc(orderBy[i]);
						}else{
							orders[i] = Order.desc(orderBy[i]);
						}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("activated", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, junc);
	}

	public Member findByUsername(Company company, String username) {
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("username", username));
		
		ObjectList<Member> members =  findAllByCriterion(null, null, null, null, junc);
		
		if(members.getSize() > 0) {
			return members.getList().get(0);
		}
		
		return null;
	}
	
	/* for watson only */
	public Member findByUserId(Company company, String userid) {
		Junction junc = Restrictions.conjunction();
		
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("password", userid));
		
		ObjectList<Member> members =  findAllByCriterion(null, null, null, null, junc);
		
		if(members.getSize() > 0) {
			return members.getList().get(0);
		}
		
		return null;		
	}
	
	public ObjectList<Member> findAll(Company company, Boolean isActive) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("activated", isActive));
				
		return findAllByCriterion(null, null, null, null, null,	null, junc);
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
		final Junction junc = Restrictions.conjunction();

		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("activated", Boolean.TRUE));
		
		if(filters != null && !filters.isEmpty())
		{
			/* 
			 * Using reflection to check if the property name is valid
			 * as it may throw: org.hibernate.QueryException: could not resolve property ...
			 */
		    final Field[] allFields = Member.class.getDeclaredFields();
		    final Set<String> fields = new HashSet<String>();
		    for(Field field : allFields)
			{
				fields.add(field.getName());
			}
			for(Entry<String, Object> entry : filters.entrySet())
			{
				final String key = entry.getKey();
				/*
				 * Only valid property names are added to the queries.
				 */
				if(fields.contains(key))
				{
					final Object value = entry.getValue();
					junction.add(Restrictions.eq(key, value));
				}
			}
			junc.add(junction);
		}
		
		return findAllByCriterion(null, null, null, null, null, null, junc);
	}
	
	public ObjectList<Member> findAllByLikeUsername(Company company, String username) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.ilike("username", username, MatchMode.ANYWHERE));
		
		Order[] orders = null;
		orders = new Order[]{Order.desc("username")};
		
		return findAllByCriterion(null, null, null, orders, null,	null, conj);
		
	}
	
	public ObjectList<Member> findAllNissanUser(Company company, String username, String outlet, long userType) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		if(username != null && !username.equals(""))
			conj.add(Restrictions.ilike("username", username, MatchMode.ANYWHERE));
		if(outlet != null && !outlet.equals(""))
			conj.add(Restrictions.eq("servicingOutlet", outlet));
		if(String.valueOf(userType) != null && !String.valueOf(userType).equals("") && userType != 0)
			conj.add(Restrictions.eq("memberType.id", userType));
		
		Order[] orders = null;
		orders = new Order[]{Order.desc("username")};
		
		return findAllByCriterion(null, null, null, orders, null,	null, conj);
		
	}
	
	public Member findByPageModuleAndPagemoduleUsername(String pageModule, String pageModuleUsername, String password, Company company) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("pageModule", pageModule));
		conj.add(Restrictions.eq("pageModuleUsername", pageModuleUsername));
		conj.add(Restrictions.eq("password", password));
		conj.add(Restrictions.eq("company", company));
			
		ObjectList<Member> members = findAllByCriterion(null, null, null, null, conj);
		if(members.getSize() > 0) {
			return members.getList().get(0);
		}
		return null;
	}



	public ObjectList<Member> findAllByPageModule(String pageModule,
			Company company) {
		// TODO Auto-generated method stub
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("pageModule", pageModule));
		junc.add(Restrictions.eq("company", company));
				
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}



	public Member findAllByEmailModule(String pageModule, String email,
			Company company) {
		// TODO Auto-generated method stub
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("pageModule", pageModule));
		conj.add(Restrictions.eq("email", email));
		conj.add(Restrictions.eq("company", company));
			
		ObjectList<Member> members = findAllByCriterion(null, null, null, null, conj);
		if(members.getSize() > 0) {
			return members.getList().get(0);
		}
		return null;
	}
	
}
