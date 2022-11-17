/**
 *
 */
package com.ivant.cms.action.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.action.PageDispatcherAction;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.RegistrationItemOtherFieldDelegate;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.interfaces.PageDispatcherAware;
import com.ivant.constants.MyHomeTherapistConstants;

/**
 * Dispatcher action for My Home Therapist.
 * 
 * @author Edgar S. Dacpano
 *
 */
public class MyHomeTherapistDispatcherAction 
	extends PageDispatcherAction
		implements PageDispatcherAware
{
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	
	private final Set<String> professions = new TreeSet<String>(Arrays.asList(MyHomeTherapistConstants.PROFESSIONS));
	private final Set<String> schedules = new TreeSet<String>(Arrays.asList(MyHomeTherapistConstants.SCHEDULES));
	private final Set<String> locations = new HashSet<String>(Arrays.asList(MyHomeTherapistConstants.LOCATIONS));
	
	private final Map<String, String> registrationItemOtherFieldsMap = new HashMap<String, String>();
	private final RegistrationItemOtherFieldDelegate registrationItemOtherFieldDelegate = RegistrationItemOtherFieldDelegate.getInstance();
	
	/**
	 * Serialization purpose 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void prepare() throws Exception
	{
		super.prepare();
		loadQuickSearch();
	}
	
	public String searchTherapist() throws Exception
	{
		final String province = StringUtils.trimToNull(request.getParameter("loc"));
		final String city = StringUtils.trimToNull(request.getParameter("city"));
		final String schedule = StringUtils.trimToNull(request.getParameter("sc"));
		final String profession = StringUtils.trimToNull(request.getParameter("prof"));
		
		final Map<String, Object> filters = new HashMap<String, Object>();
		
		filters.put("verified", Boolean.TRUE);
		
		if(province != null)
		{
			filters.put("province", province);
		}
		if(city != null)
		{
			filters.put("city", city);
		}
		if(schedule != null)
		{
			filters.put("info1", schedule);
		}
		if(profession != null)
		{
			filters.put("info2", profession);
		}
		
		final ObjectList<Member> searchResults = memberDelegate.findAllByPropertyName(company, Restrictions.conjunction(), filters);
		request.setAttribute("searchResults", searchResults.getList());
		
		final String results = StringUtils.trimToEmpty(profession) + " " + StringUtils.trimToEmpty(province) + " " + StringUtils.trimToEmpty(schedule);
		request.setAttribute("results", 
			StringUtils.isNotBlank(results)
			? results
			: "All");
		
		return SUCCESS;
	}
	
	private void loadQuickSearch()
	{
		final Map<String, Integer> locationCounts = new LinkedHashMap<String, Integer>();
		for(String province : locations)
		{
			final Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("verified", Boolean.TRUE);
			filters.put("province", province);
			final List<Member> membersByLocation = memberDelegate.findAllByPropertyName(company, Restrictions.conjunction(), filters).getList();
			locationCounts.put(province, 
				membersByLocation != null && !membersByLocation.isEmpty()
				? membersByLocation.size()
				: 0); 
		}
		request.setAttribute("locationCounts", locationCounts);
	}
	
	public String viewTherapist() throws Exception
	{
		
		final String username = StringUtils.trimToNull(request.getParameter("username"));
		
		final Member therapist = memberDelegate.findMember(company, username);
		request.setAttribute("therapist", therapist);
		loadMemberRegistrationItemOtherFields(therapist);
		
		return SUCCESS;
	}
	
	private void loadMemberRegistrationItemOtherFields(Member member)
	{
		
		try
		{
			registrationItemOtherFieldsMap.clear();
			final List<RegistrationItemOtherField> riofList = registrationItemOtherFieldDelegate.findAll(company, member).getList();
			if(riofList != null && !riofList.isEmpty())
			{
				for(final RegistrationItemOtherField riof : riofList)
				{
					final OtherField parent = riof.getOtherField();
					if(parent != null)
					{
						final Long otherFieldId = parent.getId();
						final OtherField otherField = otherFieldDelegate.find(otherFieldId);
						if(otherField != null)
						{
							final String otherFieldName = otherField.getName();
							final String riofContent = riof.getContent();
							if(StringUtils.isNotBlank(otherFieldName))
							{
								final String otherFieldNameToTitleCaseSeparatedBySpace = StringUtils.capitalize(otherFieldName).replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");
								registrationItemOtherFieldsMap.put(otherFieldNameToTitleCaseSeparatedBySpace, riofContent);
							}
						}
					}
				}
			}
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the specializations
	 */
	public Set<String> getProfessions()
	{
		return professions;
	}

	/**
	 * @return the schedules
	 */
	public Set<String> getSchedules()
	{
		return schedules;
	}
	
	public Set<String> getLocations()
	{
		return locations;
	}
	
	/**
	 * @return the registrationItemOtherFieldsMap
	 */
	public Map<String, String> getRegistrationItemOtherFieldsMap()
	{
		return registrationItemOtherFieldsMap;
	}

}
