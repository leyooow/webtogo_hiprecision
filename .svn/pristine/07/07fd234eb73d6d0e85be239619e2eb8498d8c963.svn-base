package com.ivant.cms.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

@Entity(name = "PageFiles")
@Table(name = "page_files")
public class PageFile extends AbstractFile {

	private BasePage page; 
	private boolean disabled = false;
	private List<MemberPageFile> memberPageFiles;
 
	@ManyToOne(targetEntity = BasePage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "page_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public BasePage getPage() {
		return page;
	}

	public void setPage(BasePage page) {
		this.page = page;
	}

	@Basic
	@Column(name="is_disabled")
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@OneToMany(targetEntity = MemberPageFile.class, mappedBy = "pageFile", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	public List<MemberPageFile> getMemberPageFiles() {
		return memberPageFiles;
	}

	public void setMemberPageFiles(List<MemberPageFile> memberPageFiles) {
		this.memberPageFiles = memberPageFiles;
	}
	
	@Transient
	public Map<Long, Boolean> getMemberTypeAccess() {
		List<MemberPageFile> list = getMemberPageFiles();
		
		if(list != null) {
			Map<Long, Boolean> map = new HashMap<Long, Boolean>();
			
			for(MemberPageFile memberPageFile : list) {
				map.put(memberPageFile.getMemberType().getId(), Boolean.TRUE);
			}
			
			return map;
		}
		
		return null;
	}
	
	
}
