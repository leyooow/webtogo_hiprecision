package com.ivant.cms.delegate;

import org.joda.time.DateTime;

import com.ivant.cms.db.NwdiPatientDAO;
import com.ivant.cms.entity.NwdiPatient;

/**
 * This delegate uses MS SQL Server for NWDI 
 * @author Eric John Apondar
 * @since Mar 14, 2017
 */
public class NwdiPatientDelegate extends AbstractDelegate<NwdiPatient, Long, NwdiPatientDAO> {

	private static NwdiPatientDelegate instance = new NwdiPatientDelegate();
	
	public static NwdiPatientDelegate getInstance() {
		return NwdiPatientDelegate.instance;
	}
		
	public NwdiPatientDelegate() {
		super(new NwdiPatientDAO());
	}
	
	public NwdiPatient find(String patientNo, DateTime birthDate){
		return dao.find(patientNo, birthDate);
	}
}
