package com.ivant.cms.delegate;

import com.ivant.cms.db.MemberShippingInfoDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberShippingInfo;

/**
 * Runtime Data Access implementation class for {@link MemberShippingInfo}.
 * 
 * @author Mark Kenneth M. Rañosa
 *
 */
public class MemberShippingInfoDelegate extends AbstractBaseDelegate<MemberShippingInfo, MemberShippingInfoDAO> {
	
	/** Thread safe {@code MemberShippingInfoDelegate} class instance*/
	private volatile static MemberShippingInfoDelegate instance;
	
	/**
	 * Creates a new instance of class {@code MemberShippingInfoDelegate}.
	 */
	protected MemberShippingInfoDelegate(MemberShippingInfoDAO dao) {
		super(new MemberShippingInfoDAO());
	}

	/**
	 * Returns {@code MemberShippingInfoDelegate} class instance.
	 * 
	 * @return - {@code MemberShippingInfoDelegate} class instance
	 */
	public static MemberShippingInfoDelegate getInstance(){
		//generate a new instance, initial instance is null
		if(instance == null){
			//ensure thread safety
			synchronized (MemberShippingInfoDelegate.class) {
				if(instance == null)
					instance = new MemberShippingInfoDelegate(new MemberShippingInfoDAO());
			}
		}
		return instance;
	}
	
	/**
	 * Returns the {@link MemberShippingInfo} item based on the specified parameters.
	 * 
	 * @param company
	 * 			- the company that the member belongs to
	 * @param member
	 * 			- company client/buyer
	 * 
	 * @return - the {@link MemberShippingInfo} item based on the specified parameters
	 */
	public MemberShippingInfo find(Company company, Member member) {
		return dao.find(company, member);
	}

}
