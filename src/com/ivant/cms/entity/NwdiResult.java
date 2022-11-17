package com.ivant.cms.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.ivant.cms.interfaces.BaseID;

/**
 *  NOTE: Not all table columns were mapped in this entity. Only the needed as to date.
 * @author Eric John Apondar
 * @since Mar 14, 2017
 */
@Entity(name="NwdiResult")
@Table(name=NwdiResult.TABLE_NAME)
@Transactional(readOnly=true)
public class NwdiResult implements BaseID<Long>, JSONAware, Serializable{
	private static final long serialVersionUID = -8038963960638923099L;

	public static final String TABLE_NAME = "RESULTS";
	
	private Long id;
	
	private String patientNo;
	private DateTime resultDate;
	private String path;
	
	
	@Id
	@Column( name="ID" )
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column(name="PATIENTNO")
	public String getPatientNo() {
		return patientNo;
	}
	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}
	
	
	
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(name="RESULTDATE")
	public DateTime getResultDate() {
		return resultDate;
	}
	public void setResultDate(DateTime resultDate) {
		this.resultDate = resultDate;
	}
	
	
	@Basic
	@Column(name="PATH")
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("patientno", patientNo);
		
		return json.toJSONString();
	}
	
	@Override
	public String toString() {
		return toJSONString();
	}
	
}
