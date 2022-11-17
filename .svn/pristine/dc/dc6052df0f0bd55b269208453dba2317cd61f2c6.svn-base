/*
 * Created on Jun 30, 2005
 *
 */
package com.ivant.cms.entity.baseobjects;

import java.io.Serializable;
import java.util.Date;
import java.util.StringTokenizer;

import javax.persistence.*;

import com.ivant.cms.interfaces.BaseID;

/**
 * The super class of the entity. A good subclass must implement {@link #hashCode()} and 
 * {@link #equals(Object)} method.
 * The class must also implement {@link Serializable} and generate serialVersionUID
 * @author erwin
 * @author Vincent
 */
@MappedSuperclass
public class BaseObject implements BaseID<Long> {
	private Long id;
	private boolean isValid;
	private Date updatedOn;
	private Date createdOn;
	
	public BaseObject() {
		isValid = true;
		updatedOn = new Date();
		createdOn = new Date();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable=false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Basic
	@Column(name="valid", nullable=false)
	public Boolean getIsValid()	{
		return Boolean.valueOf(isValid);
	}
	
	public void setIsValid(Boolean isValid) {
		this.isValid = (isValid != null) ? isValid.booleanValue() : false;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name="updated_on", nullable=false)
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name="created_on", nullable=false)
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj){
			return true;
		}
		
		if (obj instanceof BaseObject) 	{
			if (getId() == null)
				return false;
			
			BaseObject bobj = (BaseObject) obj;
			Long id2 = bobj.getId();
			
			return (getId().equals(id2));
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		if (getId() != null){
			hash = getId().hashCode();
		}
//		if (getUpdatedOn() != null)
//		{
//			hash ^= getUpdatedOn().hashCode();
//		}
		return hash;
	}
	
	
	protected static String makeShorterString(String str, int numOfWord, boolean removeHTMLTags) {
		String ret = "";
		
		if(str != null) {
			String nstr;
			
			if (removeHTMLTags)
				nstr = str.replaceAll("<(\"[^\"]*\"|'[^']*'|[^'\">])*>", "");
			else
				nstr = str;
			
			StringTokenizer tk = new StringTokenizer(nstr);
			
			if (tk.countTokens() > numOfWord) {
				StringBuilder sb = new StringBuilder(160);
				for (int i = 1; i < numOfWord && tk.hasMoreTokens(); i++)
				{
					sb.append(tk.nextToken()).append(' ');
				}
				sb.append("...");
				ret = sb.toString();
			} else {
				ret = nstr;
			}
		}
		
		return ret;
	}
}
