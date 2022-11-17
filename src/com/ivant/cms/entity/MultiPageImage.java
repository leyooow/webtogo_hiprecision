package com.ivant.cms.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity(name = "MultiPageImage")
@Table(name = "multipage_images")
public class MultiPageImage extends BaseImage {

	private MultiPage multipage;

	public void setMultipage(MultiPage multipage) {
		this.multipage = multipage;
	}

	@ManyToOne(targetEntity = MultiPage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "multipage_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public MultiPage getMultipage() {
		return multipage;
	}
}