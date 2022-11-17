package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import com.ivant.cms.entity.NwdiPatient;

/**
 * This dao uses MS SQL Server datasource for NWDI
 * 
 * @author Eric John Apondar
 * @since Mar 14, 2017
 */
public class NwdiPatientDAO extends AbstractNwdiDAO<NwdiPatient, Long> {

	public NwdiPatientDAO() {
		super();
	}
	
	public NwdiPatient find(String patientNo, DateTime birthDate){
		NwdiPatient patient = null;
		
		if(patientNo != null && birthDate != null){
			Conjunction conj = Restrictions.conjunction();
			conj.add(Restrictions.eq("patientNo", patientNo));
			conj.add(Restrictions.eq("birthDate", birthDate));
			
			List<NwdiPatient> results = findAllByCriterion(null, null, null, null, conj).getList();
			if(results != null && !results.isEmpty()){
				patient = results.get(0);
			}
		}
		
		return patient;
	}
}
