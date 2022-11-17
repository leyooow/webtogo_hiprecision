package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ivant.cms.enums.PageType;

@Entity(name = "FormPage")
@Table(name = "form_page")
@PrimaryKeyJoinColumn(name = "page_id")
public class FormPage extends BasePage {

	private String topContent;
	private String bottomContent;
	private boolean fileUploadAllowed;
	
	private  List<FormPageLanguage> formPageLanguageList;
	
	public FormPage() {
		fileUploadAllowed = false;
	}
	
	@Basic
	@Column(name="top_content", length=65536, nullable=false)
	public String getTopContent() {
		if (getFormPageLanguageList() != null && language != null) {
			for (FormPageLanguage temp : getFormPageLanguageList()) {
				if (temp.getLanguage().equals(this.language))
					return temp.getTopContent();
			}
		}
		return topContent;
	}

	public void setTopContent(String topContent) {
		this.topContent = topContent;
	}

	@Basic
	@Column(name="bottom_content", length=65536, nullable=false)
	public String getBottomContent() {
		if (getFormPageLanguageList() != null && language != null) {
			for (FormPageLanguage temp : getFormPageLanguageList()) {
				if (temp.getLanguage().equals(this.language))
					return temp.getBottomContent();
			}
		}
		return bottomContent;
	}

	public void setBottomContent(String bottomContent) {
		this.bottomContent = bottomContent;
	}
	
	@Override
	@Transient
	public String providePageType() {
		return "fp";
	}
	
	@Basic
	@Column(name="file_upload_allowed", nullable=false)
	public boolean isFileUploadAllowed() {
		return fileUploadAllowed;
	}
	
	public void setFileUploadAllowed(boolean fileUploadAllowed) {
		this.fileUploadAllowed = fileUploadAllowed;
	}
	
	@Override
	public String toString() {
		return "id : " + getId() + "\n" +
				"title : " + getTitle() + "\n" +
				"jsp : " + getJsp() + "\n";
	}
	
	
	@OneToMany(targetEntity = FormPageLanguage.class, mappedBy = "defaultFormPage", fetch = FetchType.LAZY)
	public List<FormPageLanguage> getFormPageLanguageList() {
		return formPageLanguageList;
	}

	public void setFormPageLanguageList(List<FormPageLanguage> formPageLanguageList) {
		this.formPageLanguageList = formPageLanguageList;
	}

	@Transient
	public String getName() {
		if (getFormPageLanguageList() != null && language != null) {
			for (FormPageLanguage temp : getFormPageLanguageList()) {
				if (temp.getLanguage().equals(this.language))
					return temp.getName();
			}
		}
		return name;
	}
	
	@Transient
	public String getTitle() {
		if (getFormPageLanguageList() != null && language != null) {
			for (FormPageLanguage temp : getFormPageLanguageList()) {
				if (temp.getLanguage().equals(this.language))
					return temp.getTitle();
			}
		}
		return title;
	}
	
	@Transient
	public FormPageLanguage getFormPageLanguage() {
		if (getFormPageLanguageList() != null && language != null) {
			for (FormPageLanguage temp : getFormPageLanguageList()) {
				if (temp.getLanguage().equals(this.language))
					return temp;
			}
		}
		return null;
	}
	
	@Override
	@Transient
	public PageType getPageType()
	{
		return PageType.FORMPAGE;
	}
	
}
