package com.ivant.cms.dataSource;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class CalculatorDataSource 
	implements JRDataSource{

	private final Iterator<CalculatorItem> iter;
	
	private CalculatorItem item;
	
	private String NAME = "name";
	private String VALUE = "value";
	
	public CalculatorDataSource(List<CalculatorItem> calculatorItemList) {
		iter = calculatorItemList.iterator();
	}
	
	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		if(NAME.equals(jrField.getName())) {
			return item.getName();
		} if(VALUE.equals(jrField.getName())) {
			return item.getValue();
		}
		return null;
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

}
