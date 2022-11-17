/**
 *
 */
package com.ivant.cms.entity;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interfaces.CompanyAware;

/**
 * @author Edgar S. Dacpano
 *
 */
public class MemberPromoCode
		extends CompanyBaseObject
		implements Cloneable, CompanyAware
{
	
	/** The owner/member associated with this promo code.*/
	private Member member;
	
	/** The code for this promo code. */
	private String code;
	
	/** If the promo code is disabled, useful if the company wants to halt/freeze the code. */
	private Boolean isDisabled;
	
	/** Points for this promo code, {@link BigDecimal} for precision */
	private BigDecimal points;
	
	/** The starting date of validity of this promo code */
	private DateTime validFrom;
	
	/** The expiration(valid until) date of this promo code */
	private DateTime validUntil;
	
	public MemberPromoCode()
	{
		super();
	}
	
}
