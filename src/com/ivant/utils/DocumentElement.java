package com.ivant.utils;

public class DocumentElement extends ITextPdfElement{
	
	private Boolean createNew;
	
	public DocumentElement(Boolean createNew) {
		this.createNew = createNew;
	}
	
	public Boolean getCreateNew() {
		return createNew;
	}
}
