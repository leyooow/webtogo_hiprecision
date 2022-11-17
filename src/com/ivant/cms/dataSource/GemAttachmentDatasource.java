package com.ivant.cms.dataSource;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class GemAttachmentDatasource implements JRDataSource{
	
	private List<String> elems;
	private Iterator<String> iter;
	private String item;
	
	public GemAttachmentDatasource(List<String> elems) {
		this.elems = elems;
		iter = elems.iterator();
	}
	
	@Override
	public boolean next() throws JRException {
		final boolean hasNext = iter.hasNext();
		if (hasNext)
		{
			item = iter.next();
		}
		return hasNext;
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {		
		return null;
	}

}
