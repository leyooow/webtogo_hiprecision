package com.ivant.cms.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

@Entity(name = "MultiPageFile")
@Table(name = "multipage_files")
public class MultiPageFile extends AbstractFile {

	private MultiPage multipage;
	private SinglePage singlepage;
	
	private List<MemberPageFile> memberPageFiles;

	public void setMultipage(MultiPage multipage) {
		this.multipage = multipage;
	}

	@ManyToOne(targetEntity = MultiPage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "multipage_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public MultiPage getMultipage() {
		return multipage;
	}

	@ManyToOne(targetEntity = SinglePage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "single_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public SinglePage getSinglepage() {
		return singlepage;
	}

	public void setSinglepage(SinglePage singlepage) {
		this.singlepage = singlepage;
	}

	@OneToMany(targetEntity = MemberPageFile.class, mappedBy = "multiPageFile", fetch = FetchType.LAZY)
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
		Map<Long, Boolean> map = new HashMap<Long, Boolean>();
		if(list != null) {
			
			
			for(MemberPageFile memberPageFile : list) {
				map.put(memberPageFile.getMemberType().getId(), Boolean.TRUE);
			}
			
			
		}
		
		return map;
	}
}