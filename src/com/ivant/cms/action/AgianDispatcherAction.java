package com.ivant.cms.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.hibernate.criterion.Order;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cms.beans.AgianEventsBean;
import com.ivant.cms.delegate.CommentFileDelegate;
import com.ivant.cms.delegate.CommentLikeDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberMessageDelegate;
import com.ivant.cms.delegate.NotificationDelegate;
import com.ivant.cms.delegate.PageImageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.AbstractFile;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Comment;
import com.ivant.cms.entity.CommentFile;
import com.ivant.cms.entity.CommentLike;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.entity.MemberMessage;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.Notification;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.PortalActivityLog;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.MessageRemarks;
import com.ivant.cms.enums.MessageStatus;
import com.ivant.cms.enums.MessageType;
import com.ivant.constants.ApplicationConstants;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.ivant.utils.PasswordEncryptor;
import com.ivant.utils.ReflectionUtil;

public class AgianDispatcherAction extends PageDispatcherAction {

	private String title;
	private String content;
	private Long parentId;
	private InputStream inputStream;
	private PasswordEncryptor encryptor;
	private CommentFileDelegate commentFileDelegate = CommentFileDelegate.getInstance();
	private CommentLikeDelegate commentLikeDelegate = CommentLikeDelegate.getInstance();
	private List<File> files;
	private List<String> filesFileName;
	private List<String> filesContentType;
	private String successUrl;
	private String errorUrl;
	private String result;
	private String status, redirection, notifType;
	private Long singlepage, multiPageId;
	private Integer invalidFiles;
	
	private UserDelegate userDelegate = UserDelegate.getInstance();
	private long commentID;
	private MultiPage multiPage;
	
	private MemberMessageDelegate memberMessageDelegate = MemberMessageDelegate.getInstance();
	private NotificationDelegate notificationDelegate = NotificationDelegate.getInstance();

	@Override
	public String execute() throws Exception
	{
		setFileSearchList("itemFileList");
		return super.execute();
	}
	
	private void setFileSearchList(String attribute)
	{
		final String q = request.getParameter("q");
		if(q != null && !q.isEmpty())
		{
			final List<com.ivant.cms.entity.interfaces.File> itemFileList = new ArrayList<com.ivant.cms.entity.interfaces.File>();
			
			final String basePath = "companies" + File.separator 
					+ company.getName() + File.separator ;
			
			final Group group = groupDelegate.find(company, 611L);
			if(group != null)
			{
				final List<CategoryItem> items = categoryItemDelegate.findAllByGroup(company, group).getList();
				if(items != null && !items.isEmpty())
				{
					for(CategoryItem item : items)
					{
						final List<ItemFile> files = itemFileDelegate.findAll(company, item).getList();
						if(files != null && !files.isEmpty())
						{
							final String itemPath = "images" + File.separator
									+ "items" + File.separator;
							for(ItemFile itemFile : files)
							{
								boolean addFile = false;
								if(StringUtils.containsIgnoreCase(itemFile.getFileName(), q))
								{
									addFile = true;
								}
								else
								{
									final String filePath = (basePath + itemPath + itemFile.getFilePath()); 
									final String path = servletContext.getRealPath(filePath);
									final File file = new File(path);
									if(FileUtil.searchText(file, q, true))
									{
										addFile = true;
									}
								}
								
								if(addFile)
								{
									itemFile.setFilePath(itemPath + itemFile.getFilePath());
									itemFileList.add(itemFile);
								}
							}
						}
					}
				}
			}
			
			final Group group1 = groupDelegate.find(company, 614L);
			if(group1 != null)
			{
				final List<CategoryItem> items = categoryItemDelegate.findAllByGroup(company, group1).getList();
				if(items != null && !items.isEmpty())
				{
					for(CategoryItem item : items)
					{
						final List<ItemFile> files = itemFileDelegate.findAll(company, item).getList();
						if(files != null && !files.isEmpty())
						{
							final String itemPath = "images" + File.separator
									+ "items" + File.separator;
							for(ItemFile itemFile : files)
							{
								boolean addFile = false;
								if(StringUtils.containsIgnoreCase(itemFile.getFileName(), q))
								{
									addFile = true;
								}
								else
								{
									final String filePath = (basePath + itemPath + itemFile.getFilePath()); 
									final String path = servletContext.getRealPath(filePath);
									final File file = new File(path);
									if(FileUtil.searchText(file, q, true))
									{
										addFile = true;
									}
								}
								
								if(addFile)
								{
									itemFile.setFilePath(itemPath + itemFile.getFilePath());
									itemFileList.add(itemFile);
								}
							}
						}
					}
				}
			}
			
			
			
			final List<CommentFile> commentFiles = commentFileDelegate.findAll(company);
			if(commentFiles != null && !commentFiles.isEmpty())
			{
				for(CommentFile commentFile : commentFiles)
				{
					boolean addFile = false;
					if(StringUtils.containsIgnoreCase(commentFile.getFileName(), q))
					{
						addFile = true;
					}
					else
					{
						final String filePath = (basePath + commentFile.getFilePath());
						final String path = servletContext.getRealPath(filePath);
						final File file = new File(path);
						if(FileUtil.searchText(file, q, true))
						{
							addFile = true;
						}
					}
					
					if(addFile)
					{
						commentFile.setFilePath(commentFile.getFilePath());
						itemFileList.add(commentFile);
					}
				}
			}
			
			request.setAttribute(attribute, itemFileList);
		}
	}
	
	public String saveFeed(){
		SinglePage singlePage = new SinglePage();
		User persistedUser = userDelegate.load(Long.parseLong("1"));
		
		singlePage.setCreatedBy(persistedUser);
		singlePage.setCompany(company);
		
		multiPageId = Long.parseLong(request.getParameter("multipage_id"));
		multiPage = multiPageDelegate.find(multiPageId);
		singlePage.setParent(multiPage);
		singlePage.setDaysAvailable(request.getParameter("singlePage.prev"));
		singlePage.setContent(request.getParameter("singlePage.content"));
		singlePage.setName(request.getParameter("singlePage.name"));
		singlePage.setTitle(request.getParameter("singlePage.title"));
		singlePage.setSubTitle(request.getParameter("singlePage.subTitle"));
		singlePage.setDatePublished(new Date());
		singlePage.setDatePosted(new Date());
		singlePage.setSortOrder(1);
		saveNewOrder();
		singlepage = singlePageDelegate.insert(singlePage);
		
		final MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
		final File[] files = r.getFiles("files");
		final String[] filenames = r.getFileNames("files");
		final String[] contentTypes = r.getContentTypes("files");
		
		if(!isNull(files)){
			saveImages(files,filenames,contentTypes);
		}
		
		final MultiPartRequestWrapper rVid = (MultiPartRequestWrapper) request;
		final File[] filesVid = rVid.getFiles("filesVid");
		final String[] filenamesVid = rVid.getFileNames("filesVid");
		final String[] contentTypesVid = rVid.getContentTypes("filesVid");
		
		if(!isNull(filesVid)){
			saveVideos(filesVid,filenamesVid,contentTypesVid);
		}
		
		Notification notif = new Notification();
		notif.setCompany(company);
		notif.setTitle("1 news update");
		notif.setBy(request.getParameter("singlePage.posted"));
		notif.setType("feed new");
		notif.setContent(content);
		notif.setNotifOne("news.do?type=feed&id="+singlepage);
		notificationDelegate.insert(notif);
		
		/*FOR PORTAL ACTIVITY LOG
		 * UNCOMMENT IF REQUESTED
		 * 
		 * PortalActivityLog portalActivityLog = new PortalActivityLog();
		portalActivityLog.setCompany(company);
		portalActivityLog.setMember(member);
		portalActivityLog.setSection("News Feed");
		portalActivityLog.setTopic(singlePage.getName());
		portalActivityLog.setMemberCompany(member.getReg_companyName());
		portalActivityLog.setMemberParentCompany(member.getInfo7());
		portalActivityLog.setRemarks("Posted New News Feed");
		portalActivityLogDelegate.insert(portalActivityLog);*/
		
		return SUCCESS;
	}
	
	public String editFeed(){
		SinglePage singlePage = singlePageDelegate.find(Long.parseLong(request.getParameter("singlepage_id")));
		User persistedUser = userDelegate.load(Long.parseLong("1"));
		
		singlePage.setCreatedBy(persistedUser);
		singlePage.setCompany(company);
		
		multiPageId = Long.parseLong(request.getParameter("multipage_id"));
		multiPage = multiPageDelegate.find(multiPageId);
		singlePage.setParent(multiPage);
		singlePage.setDaysAvailable(request.getParameter("singlePage.prev"));
		singlePage.setContent(request.getParameter("singlePage.content"));
		singlePage.setName(request.getParameter("singlePage.name"));
		singlePage.setTitle(request.getParameter("singlePage.title"));
		singlePage.setSubTitle(request.getParameter("singlePage.subTitle"));
		singlePage.setDatePublished(new Date());
		singlePage.setDatePosted(new Date());
		singlePage.setSortOrder(1);
		saveNewOrder();
		if(singlePageDelegate.update(singlePage)){
			Notification notif = new Notification();
			notif.setCompany(company);
			notif.setTitle("1 news edited");
			notif.setBy(request.getParameter("singlePage.posted"));
			notif.setType("feed edit");
			notif.setContent(content);
			notif.setNotifOne("news.do?type=feed&id="+request.getParameter("singlepage_id"));
			notificationDelegate.insert(notif);
			
			/*FOR PORTAL ACTIVITY LOG
			 * UNCOMMENT IF REQUESTED
			 * 
			 * PortalActivityLog portalActivityLog = new PortalActivityLog();
			portalActivityLog.setCompany(company);
			portalActivityLog.setMember(member);
			portalActivityLog.setSection("News Feed");
			portalActivityLog.setTopic(singlePage.getName());
			portalActivityLog.setMemberCompany(member.getReg_companyName());
			portalActivityLog.setMemberParentCompany(member.getInfo7());
			portalActivityLog.setRemarks("Edited");
			portalActivityLogDelegate.insert(portalActivityLog);*/
		}
		singlepage = singlePage.getId();
		final MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
		final File[] files = r.getFiles("files");
		final String[] filenames = r.getFileNames("files");
		final String[] contentTypes = r.getContentTypes("files");
		
		if(!isNull(files)){
			saveImages(files,filenames,contentTypes);
		}
		
		final MultiPartRequestWrapper rVid = (MultiPartRequestWrapper) request;
		final File[] filesVid = rVid.getFiles("filesVid");
		final String[] filenamesVid = rVid.getFileNames("filesVid");
		final String[] contentTypesVid = rVid.getContentTypes("filesVid");
		
		if(!isNull(filesVid)){
			saveVideos(filesVid,filenamesVid,contentTypesVid);
		}
		
		if(!request.getParameter("deleteVideos").equals("")){
			String imgs[] = request.getParameter("deleteVideos").split(",");
			for(int x = 0; x < imgs.length; x++){
				if(imgs[x] != ""){
					MultiPageFile pageFile = multiPageFileDelegate.find(Long.parseLong(imgs[x]));
					multiPageFileDelegate.delete(pageFile);
				}
			}

		}
		
		if(!request.getParameter("deleteImages").equals("")){
			String imgs[] = request.getParameter("deleteImages").split(",");
			for(int x = 0; x < imgs.length; x++){
				if(imgs[x] != ""){
					PageImage pageImage = pageImageDelegate.find(Long.parseLong(imgs[x]));
					pageImageDelegate.delete(pageImage);
				}
			}

		}
		return SUCCESS;
	}
	
	public String deleteFeed(){
		SinglePage singlePage = singlePageDelegate.find(Long.parseLong(request.getParameter("singlepage_id")));
		if(singlePageDelegate.delete(singlePage)){
			/*FOR PORTAL ACTIVITY LOG
			 * UNCOMMENT IF REQUESTED
			 * 
			 * PortalActivityLog portalActivityLog = new PortalActivityLog();
			portalActivityLog.setCompany(company);
			portalActivityLog.setMember(member);
			portalActivityLog.setSection("News Feed");
			portalActivityLog.setTopic(singlePage.getName());
			portalActivityLog.setMemberCompany(member.getReg_companyName());
			portalActivityLog.setMemberParentCompany(member.getInfo7());
			portalActivityLog.setRemarks("Deleted");
			portalActivityLogDelegate.insert(portalActivityLog);*/
		}
		return SUCCESS;
	}
	
	private void saveNewOrder() {
		// TODO Auto-generated method stub
		List<SinglePage> singlePages = new ArrayList<SinglePage>();
		int count = 2;
		for(SinglePage sp : multiPage.getItems()) {
			sp.setSortOrder(count++);
			singlePages.add(sp);
		}
//		singlePageDelegate.batchUpdate(singlePages); 
	}
	
	public String saveComment(){
		
		Comment comment = new Comment();
		
		comment.setCompany(company);
		comment.setCreatedBy(member);
		comment.setContent(content);
		comment.setLikeCount(0);
		comment.setTitle(title);
		
		
		
		if(parentId != null){
			comment.setParentComment(commentDelegate.find(parentId));
		}else{
		}
			
		final MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
		final File[] files = r.getFiles("files");
		final String[] filenames = r.getFileNames("files");
		final String[] contentTypes = r.getContentTypes("files");
		
		if(!isNull(files)){
			saveFiles(files,filenames,contentTypes,commentDelegate.find(commentDelegate.insert(comment)));
		}else{
			
			commentID = commentDelegate.insert(comment);
			
		}
		
		if(parentId != null){
			status = member.getFirstname() + " replied on a topic";
			redirection = "forum.do?id="+parentId;
			notifType = "comment reply";
			
			/*FOR PORTAL ACTIVITY LOG
			 * UNCOMMENT IF REQUESTED
			 * 
			 * PortalActivityLog portalActivityLog = new PortalActivityLog();
			portalActivityLog.setCompany(company);
			portalActivityLog.setMember(member);
			portalActivityLog.setSection("Members Forum");
			portalActivityLog.setTopic(comment.getTitle());
			portalActivityLog.setMemberCompany(member.getReg_companyName());
			portalActivityLog.setMemberParentCompany(member.getInfo7());
			portalActivityLog.setRemarks("Replied");
			portalActivityLogDelegate.insert(portalActivityLog);*/
		}else{
			status = member.getFirstname() + " posted a new topic";
			redirection = "forum.do?id="+commentID;
			notifType = "comment new";
			/*FOR PORTAL ACTIVITY LOG
			 * UNCOMMENT IF REQUESTED
			 * 
			 * PortalActivityLog portalActivityLog = new PortalActivityLog();
			portalActivityLog.setCompany(company);
			portalActivityLog.setMember(member);
			portalActivityLog.setSection("Members Forum");
			portalActivityLog.setTopic(comment.getTitle());
			portalActivityLog.setMemberCompany(member.getReg_companyName());
			portalActivityLog.setMemberParentCompany(member.getInfo7());
			portalActivityLog.setRemarks("Posted New Topic");
			portalActivityLogDelegate.insert(portalActivityLog);*/
		} 
		Notification notif = new Notification();
		notif.setCompany(company);
		notif.setTitle(status);
		notif.setBy(String.format("%s", member.getFirstname()));
		notif.setType(notifType);
		notif.setContent(content);
		notif.setNotifOne(redirection);
		notificationDelegate.insert(notif);
		return SUCCESS;
	}
	
	public String deleteComment(){
		
		Long commentId = Long.parseLong(request.getParameter("commentId"));
		Comment parentComment = commentDelegate.find(commentId);
		boolean error = false;
		

			if(!isNull(parentComment)){

				if(!isNull(parentComment.getReplies())){
					for(Comment layer1 : parentComment.getReplies()){
						
						if(!isNull(layer1.getReplies())){

							for(Comment layer2 : layer1.getReplies()){
								
									deleteCommentLikes(layer2);
									if(commentDelegate.delete(layer2)){
										/*FOR PORTAL ACTIVITY LOG
										 * UNCOMMENT IF REQUESTED
										 * 
										 * PortalActivityLog portalActivityLog = new PortalActivityLog();
										portalActivityLog.setCompany(company);
										portalActivityLog.setMember(member);
										portalActivityLog.setSection("Members Forum");
										portalActivityLog.setTopic(layer2.getTitle() + " > " + layer2.getContent());
										portalActivityLog.setMemberCompany(member.getReg_companyName());
										portalActivityLog.setMemberParentCompany(member.getInfo7());
										portalActivityLog.setRemarks("Deleted Comment");
										portalActivityLogDelegate.insert(portalActivityLog);*/
									}
							}
							
						}
						
	
						deleteCommentLikes(layer1);
						if(commentDelegate.delete(layer1)){
							/*FOR PORTAL ACTIVITY LOG
							 * UNCOMMENT IF REQUESTED
							 * 
							 * PortalActivityLog portalActivityLog = new PortalActivityLog();
							portalActivityLog.setCompany(company);
							portalActivityLog.setMember(member);
							portalActivityLog.setSection("Members Forum");
							portalActivityLog.setTopic(layer1.getTitle() + " > " + layer1.getContent());
							portalActivityLog.setMemberCompany(member.getReg_companyName());
							portalActivityLog.setMemberParentCompany(member.getInfo7());
							portalActivityLog.setRemarks("Deleted Comment");
							portalActivityLogDelegate.insert(portalActivityLog);*/
						}

					}			
				}
				
				if(!isNull(request.getParameter("parentId"))){
					Long parentId = Long.parseLong(request.getParameter("parentId"));
					successUrl = "../../forum.do?id=" + parentId;
				}else{
					successUrl = "../../forum.do";
				}
				

			}else{
				successUrl = "../../forum.do";
			}
			
			
			deleteCommentLikes(parentComment);
			if(commentDelegate.delete(parentComment)){
				/*FOR PORTAL ACTIVITY LOG
				 * UNCOMMENT IF REQUESTED
				 * 
				 * PortalActivityLog portalActivityLog = new PortalActivityLog();
				portalActivityLog.setCompany(company);
				portalActivityLog.setMember(member);
				portalActivityLog.setSection("Members Forum");
				portalActivityLog.setTopic(parentComment.getTitle());
				portalActivityLog.setMemberCompany(member.getReg_companyName());
				portalActivityLog.setMemberParentCompany(member.getInfo7());
				portalActivityLog.setRemarks("Deleted Topic");
				portalActivityLogDelegate.insert(portalActivityLog);*/
			}

		return SUCCESS;

	}
	
	public Boolean deleteCommentLikes(Comment comment){
		
		List<CommentLike> commentLikes = comment.getCommentLikes();
		
		if(!isNull(commentLikes)){
			for(CommentLike commentLike : commentLikes){
				commentLikeDelegate.delete(commentLike);
			}	
		}else{
			return false;
		}
		
		return true;
	}
	
	public String deleteFileMethod(){
		
		Long deleteFile = Long.parseLong(request.getParameter("deleteFile"));

		CommentFile commentFile = commentFileDelegate.find(deleteFile);
		deleteFile(commentFile);
		return SUCCESS;
	}
	
	public boolean deleteFile(CommentFile file){	
		successUrl = request.getParameter("successUrl");
		errorUrl = successUrl;
		
		String fileToDelete = servletContext.getRealPath("/companies/agian/") + File.separator + file.getFilePath();
		if(FileUtils.deleteQuietly(new File(fileToDelete))){
			commentFileDelegate.delete(file);
		}else{
			
			return false;
		}
		
		return true;

	}
	
	public String editComment(){
		Long commentId = Long.parseLong(request.getParameter("commentId"));
		Comment comment = commentDelegate.find(commentId);
		comment.setContent(content);
		comment.setTitle(title);

		final MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
		final File[] files = r.getFiles("files");
		final String[] filenames = r.getFileNames("files");
		final String[] contentTypes = r.getContentTypes("files");
		
		if(!isNull(files)){
			saveFiles(files,filenames,contentTypes,comment);
		}
		
		if(commentDelegate.update(comment)){
			/*FOR PORTAL ACTIVITY LOG
			 * UNCOMMENT IF REQUESTED
			 * 
			 * PortalActivityLog portalActivityLog = new PortalActivityLog();
			portalActivityLog.setCompany(company);
			portalActivityLog.setMember(member);
			portalActivityLog.setSection("Members Forum");
			portalActivityLog.setTopic(comment.getTitle());
			portalActivityLog.setMemberCompany(member.getReg_companyName());
			portalActivityLog.setMemberParentCompany(member.getInfo7());
			portalActivityLog.setRemarks("Edited");
			portalActivityLogDelegate.insert(portalActivityLog);*/
		}
		
		return SUCCESS;
	} 
	

	private void saveFiles(File[] files, String[] filenames, String[] contentTypes, Comment comment) {
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		
		destinationPath += File.separator + company.getName();
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "uploads"));
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
				File file = files[i];
				String originalFileName = filenames[i];
				//String filename = filenames[i];
				
				String filename = // replace extension to lowercase, some browser cannot open the file properly if the extension is in uppercase
					FileUtil.replaceExtension(
						System.currentTimeMillis() + filenames[i], 
						FileUtil.getExtension(filenames[i]).toLowerCase());
				
				
				String contentType = contentTypes[i];
				String destination = servletContext.getRealPath(destinationPath + File.separator + "uploads");
				
				File destFile = new File(destination + File.separator + filename);
				
				FileUtil.copyFile(file, destFile); 
				
				CommentFile commentFile = new CommentFile();
				commentFile.setCompany(company);
				commentFile.setFileName(filename);
				commentFile.setFilePath("uploads" + "/" + filename);
				commentFile.setFileType(contentType);
				commentFile.setComment(comment);
				commentFile.setOriginalFileName(originalFileName);
				commentFileDelegate.insert(commentFile);
				commentID = comment.getId();
				if(contentType.contains("image")){
					status = member.getFirstname() + " uploaded new image/s";
					redirection = "gallery.do?gallery=gal3";
					notifType = "gallery image";
				}else if(contentType.contains("video")){
					status = member.getFirstname() + " uploaded new video";
					redirection = "gallery.do?gallery=gal2";
					notifType = "gallery video";
				}else{
					status = member.getFirstname() + " uploaded new file/s";
					redirection = "knowledge-database.do?db=1";
					notifType = "database";
				}
			} 
		}
		Notification notif = new Notification();
		notif.setCompany(company);
		notif.setTitle(status);
		notif.setBy(String.format("%s", member.getFirstname()));
		notif.setType(notifType);
		notif.setNotifOne(redirection);
		notificationDelegate.insert(notif);
	}

	
	public String changePassword(){
		successUrl =  request.getParameter("successUrl");
		errorUrl =  request.getParameter("errorUrl");
		String username = request.getParameter("member_user");
		//String new_username = request.getParameter("username");
		System.out.println(request.getParameter("current_password"));
		encryptor = new PasswordEncryptor();
		String password = request.getParameter("current_password");
		String cpassword = encryptor.encrypt(password);
		String new_password = encryptor.encrypt(request.getParameter("retype_password"));
		Member check = memberDelegate.findAccount(username, cpassword, company);
		
		if(check != null){
			//check.setUsername(new_username);
			check.setPassword(new_password);
			memberDelegate.update(check);
			
		}else{
			errorUrl += "invalid";
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String pinNewsFeed(){
		String status = "", statusLog = "";
		String stat = "";
		long id = Long.parseLong(request.getParameter("pinned_id"));
		Boolean flg = Boolean.parseBoolean(request.getParameter("pin_flag"));
		if(!request.getParameter("pinned_id").equals("")){
			status = " pinned a news update";
			statusLog = "Pinned";
			stat = " pin";
		}
		SinglePage like = singlePageDelegate.find(id);
		if(like != null){
			if(!request.getParameter("pinned_id").equals("")){
				like.setFlag1(flg);
				if(flg == true){
					status = " pinned a news update";
					statusLog = "Pinned";
					
				}else{
					status = " unpinned a news update";
					statusLog = "Unpinned";
					
				}
				singlePageDelegate.update(like);
			}
			
			member = memberDelegate.find(Long.parseLong(request.getParameter("users_id")));
			if(status != "unlike"){
				Notification notif = new Notification();
				notif.setCompany(company);
				notif.setTitle(member.getFirstname() + status);
				notif.setBy(String.format("%s", member.getFirstname()));
				notif.setType("feed" + stat);
				notif.setContent(content);
				notif.setNotifOne("news.do?type=feed&id="+id);
				notificationDelegate.insert(notif);
				
				PortalActivityLog portalActivityLog = new PortalActivityLog();
				portalActivityLog.setCompany(company);
				portalActivityLog.setMember(member);
				portalActivityLog.setSection("News Feed");
				portalActivityLog.setTopic(like.getName());
				portalActivityLog.setMemberCompany(member.getReg_companyName());
				portalActivityLog.setMemberParentCompany(member.getInfo7());
				portalActivityLog.setRemarks(statusLog);
				portalActivityLogDelegate.insert(portalActivityLog);
			}
		}
		return SUCCESS;
	}
	
	public String likeNewsFeed() {
		String status = "", statusLog = "";
		String stat = "";
		long id;
		Boolean flg = Boolean.parseBoolean(request.getParameter("pin_flag"));
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		if(!request.getParameter("pinned_id").equals("")){
			id = Long.parseLong(request.getParameter("pinned_id"));
			status = " pinned a news update";
			statusLog = "Pinned";
			stat = " pin";
		}else if(request.getParameter("unliked_id").equals("")){
			id = Long.parseLong(request.getParameter("liked_id"));
			status = " liked a news update";
			statusLog = "Liked";
			stat = " like";
		}else{
			id = Long.parseLong(request.getParameter("unliked_id"));
			status = "unlike";
			statusLog = "Unliked";
		}
		SinglePage like = singlePageDelegate.find(id);
		if(like != null){
			String likes = "";
			if(!request.getParameter("pinned_id").equals("")){
				like.setFlag1(flg);
				if(flg == true){
					status = " pinned a news update";
					statusLog = "Pinned";
				}else{
					status = " unpinned a news update";
					statusLog = "Unpinned";
				}
				singlePageDelegate.update(like);
			}else if(request.getParameter("unliked_id").equals("")){
				likes = request.getParameter("liked_count");
				like.setDocSchool(likes);
				String members_like = like.getDocRoom();
				if(members_like == null || members_like == "null") members_like = "";
				like.setDocRoom(members_like + request.getParameter("users_id") + ",");
				singlePageDelegate.update(like);
			}else{
				likes = request.getParameter("liked_count");
				like.setDocSchool(likes);
				String new_members_like = "";
				String members_like = like.getDocRoom();
				String usr = request.getParameter("users_id");
				String[] mem= members_like.split(",");
				content = like.getContent();
				for(int x = 0; x< mem.length; x++){
					if(mem[x].equals(usr)){
						
					}else{
						new_members_like += mem[x] + ",";
					}
				}
				like.setDocRoom(new_members_like);
				singlePageDelegate.update(like);
			}
			
			member = memberDelegate.find(Long.parseLong(request.getParameter("users_id")));
			if(status != "unlike"){
				Notification notif = new Notification();
				notif.setCompany(company);
				notif.setTitle(member.getFirstname() + status);
				notif.setBy(String.format("%s", member.getFirstname()));
				notif.setType("feed" + stat);
				notif.setContent(content);
				notif.setNotifOne("news.do?type=feed&id="+id);
				notificationDelegate.insert(notif);
			}
			PortalActivityLog portalActivityLog = new PortalActivityLog();
			portalActivityLog.setCompany(company);
			portalActivityLog.setMember(member);
			portalActivityLog.setSection("News Feed");
			portalActivityLog.setTopic(like.getName());
			portalActivityLog.setMemberCompany(member.getReg_companyName());
			portalActivityLog.setMemberParentCompany(member.getInfo7());
			portalActivityLog.setRemarks(statusLog);
			portalActivityLogDelegate.insert(portalActivityLog);
		}
		setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		return SUCCESS;
	}
	
	public String likeComment(){
		
		String result = "LIKE";
		
		Long commentId = Long.parseLong(request.getParameter("commentId"));
		String isLiked = request.getParameter("likeAction");
		
		
		CommentLike commentLike = new CommentLike();
		
		Comment comment = commentDelegate.find(commentId);
		
		commentLike.setMember(member);
		commentLike.setComment(comment);
		commentLike.setCompany(company);
		
		if(isLiked.equals("true")){
			if(!isNull(commentLikeDelegate.insert(commentLike))){
				PortalActivityLog portalActivityLog = new PortalActivityLog();
				portalActivityLog.setCompany(company);
				portalActivityLog.setMember(member);
				portalActivityLog.setSection("Members Forum");
				if(!isNull(comment.getParentComment())){
					portalActivityLog.setTopic(comment.getTitle() + " > " + comment.getContent());
				}else{
					portalActivityLog.setTopic(comment.getTitle());
				}
				portalActivityLog.setMemberCompany(member.getReg_companyName());
				portalActivityLog.setMemberParentCompany(member.getInfo7());
				portalActivityLog.setRemarks("Liked");
				portalActivityLogDelegate.insert(portalActivityLog);
			}
			
			result = "LIKE";
		}else{
			commentLike = commentLikeDelegate.findByCommentByMember(comment, member);
			if(commentLikeDelegate.delete(commentLike)){
				PortalActivityLog portalActivityLog = new PortalActivityLog();
				portalActivityLog.setCompany(company);
				portalActivityLog.setMember(member);
				portalActivityLog.setSection("Members Forum");
				if(!isNull(comment.getParentComment())){
					portalActivityLog.setTopic(comment.getTitle() + " > " + comment.getContent());
				}else{
					portalActivityLog.setTopic(comment.getTitle());
				}
				portalActivityLog.setMemberCompany(member.getReg_companyName());
				portalActivityLog.setMemberParentCompany(member.getInfo7());
				portalActivityLog.setRemarks("Unliked");
				portalActivityLogDelegate.insert(portalActivityLog);
			}
			result = "UNLIKE";
		}
		
		setInputStream(new ByteArrayInputStream(result.getBytes()));
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String findAllMembers(){
		JSONArray jsonArr = new JSONArray();
		
		List<Member> members = memberDelegate.findAllByName(company).getList();
		if(!isNull(members)){
			//request.setAttribute("agianMembers", members);
			jsonArr.addAll(members);
		}
		System.out.println(members);
		
		//setInputStream(new ByteArrayInputStream(jsonArr.toJSONString().getBytes()));
		setInputStream(new ByteArrayInputStream("[{fullname:'Angeles, Cedrick'}]".getBytes()));
		
		
		return SUCCESS;
		
	}
	
	@SuppressWarnings("unchecked")
	public String getGalleryItems(){
		JSONArray jsonArr = new JSONArray();
		String jsonString;
		Group group = groupDelegate.find(612L);
		List<CategoryItem> gal = categoryItemDelegate.findAllByGroup(company, group, false).getList();
		if(!isNull(gal)){
			//request.setAttribute("agianMembers", members);
			jsonArr.addAll(gal);
		}
		
		jsonString = jsonArr.toJSONString().replace("\'", "\\'"); 
		return jsonString;
		
	}
	
	@SuppressWarnings("unchecked")
	public String getEventItems(){
		JSONArray jsonArr = new JSONArray();
		String jsonString;
		Group group = groupDelegate.find(614L);
		List<CategoryItem> evt = categoryItemDelegate.findAllByGroup(company, group, false).getList();
		if(!isNull(evt)){
			//request.setAttribute("agianMembers", members);
			jsonArr.addAll(evt);
		}
		
		jsonString = jsonArr.toJSONString().replace("\'", "\\'"); 
		return jsonString;
		
	}
	
	@SuppressWarnings("unchecked")
	public String getFeedItems(){
		JSONArray jsonArr = new JSONArray();
		String jsonString;
		MultiPage multiPage = multiPageDelegate.find(2626L);
		List<SinglePage> feed = singlePageDelegate.findAll(company, multiPage).getList();
		if(!isNull(feed)){
			//request.setAttribute("agianMembers", members);
			jsonArr.addAll(feed);
		}
		
		jsonString = jsonArr.toJSONString().replace("\'", "\\'"); 
		return jsonString;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public String getCommentItems(){
		JSONArray jsonArr = new JSONArray();
		String jsonString;
		List<CommentFile> comm = commentFileDelegate.findAll(company);
		if(!isNull(comm)){
			//request.setAttribute("agianMembers", members);
			jsonArr.addAll(comm);
		}
		
		jsonString = jsonArr.toJSONString().replace("\'", "\\'"); 
		return jsonString;
		
	}
	
	@SuppressWarnings("unchecked")
	public String getAllMembers(){
		JSONArray jsonArr = new JSONArray();
		String jsonString;

		List<Member> members = memberDelegate.findAllByName(company).getList();
		if(!isNull(members)){
			//request.setAttribute("agianMembers", members);
			jsonArr.addAll(members);
		}
		
		jsonString = jsonArr.toJSONString().replace("\'", "\\'"); 
		return jsonString;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CommentFile> getAllForumFiles(){
	
		List<CommentFile> forumFiles = commentFileDelegate.findAll(company);

		return forumFiles;
		
	}
		
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	
	public List<Comment> getCommentList(){
		return commentDelegate.findAllTopics(company).getList();
		
	}
	
	
	public Comment getSelectedComment(){
		Long id = null;
		try{
			id = Long.parseLong(request.getParameter("id"))  ;

		}catch(Exception e){
			
		}
		
		if(id == null){
			return null;
		}else{
			return commentDelegate.find(id);
		}
	}
	
	public Boolean getCommentLiked(){
		
		Long commentId = Long.parseLong(request.getParameter("commentId"));
		
		Comment comment = commentDelegate.find(commentId);
		CommentLike commentLike = commentLikeDelegate.findByCommentByMember(comment, member);
		
		if(commentLike == null){
			return false;
		}else{
			return true;
		}
		
	}
	
	private boolean saveImages(File[] files, String[] filenames, String[] contentTypes) {
		
		CompanySettings companySettings = company.getCompanySettings();
		
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));	
		
		destinationPath += File.separator + company.getName();
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "pages";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));

		// create the orig, image1, image2, image3 and thumbnail folders
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image1"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image2"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image3"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "thumbnail"));

		destinationPath = servletContext.getRealPath(destinationPath);
		
		//System.out.println("SAVING "+files.length +"file.");
		
		Long companyId = company.getId();
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
			String source = files[i].getAbsolutePath();
	
			String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"),
			String.valueOf(new Date().getTime()));
	
			if(FileUtil.getExtension(filename).equalsIgnoreCase("png")) {
				filename = FileUtil.replaceExtension(filename, "png");
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				FileUtil.copyFile(files[i], origFile);				
				// generate image 1
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename,
				companySettings.getImage1Width(), companySettings.getImage1Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
				// generate image 2
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename,
				companySettings.getImage2Width(), companySettings.getImage2Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
				// generate image 3
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename,
				companySettings.getImage3Width(), companySettings.getImage3Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
			}
	
			else if (!FileUtil.getExtension(filename).equalsIgnoreCase("jpg") && !FileUtil.getExtension(filename).equalsIgnoreCase("jpeg") &&
			!FileUtil.getExtension(filename).equalsIgnoreCase("gif") )
			{
				return false;
			}

			//logger.debug("after the if=================================================");
		
		
			if (FileUtil.getExtension(filename).equalsIgnoreCase("gif")) {

//				File origFile = new File(destinationPath + File.separator + "image1" + File.separator + filename);
	
				// save the original files
//				FileUtil.copyFile(files[i], origFile);
				
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				File origFile2 = new File(destinationPath + File.separator + "image1" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile2);
			
				// generate thumbnail
				ImageUtil.generateGifThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);				
		
				// instead of creating directly from the temporary file, create a file from the original file
			}
			
			
			if (FileUtil.getExtension(filename).endsWith("JPG")) {

//				File origFile = new File(destinationPath + File.separator + "image1" + File.separator + filename);
	
				// save the original files
//				FileUtil.copyFile(files[i], origFile);
				
				filename = FileUtil.replaceExtension(filename, "jpg");
				FileUtil.getExtension(filename).toLowerCase();
				
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				File origFile2 = new File(destinationPath + File.separator + "image1" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile2);
			
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);				
		
				// instead of creating directly from the temporary file, create a file from the original file
			}
			
			if (FileUtil.getExtension(filename).endsWith("GIF")) {

//				File origFile = new File(destinationPath + File.separator + "image1" + File.separator + filename);
	
				// save the original files
//				FileUtil.copyFile(files[i], origFile);
				
				filename = FileUtil.replaceExtension(filename, "gif");
				FileUtil.getExtension(filename).toLowerCase();
				
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				File origFile2 = new File(destinationPath + File.separator + "image1" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile2);
			
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);				
		
				// instead of creating directly from the temporary file, create a file from the original file
			}
	
			//System.out.println("----------------------------------" + FileUtil.getExtension(filename));	
			
			if (FileUtil.getExtension(filename).equalsIgnoreCase("jpg") || FileUtil.getExtension(filename).equalsIgnoreCase("jpeg") || FileUtil.getExtension(filename).endsWith("JPG") )
			{
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename,
				companySettings.getImage1Width(), companySettings.getImage1Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
		
				// generate image 2
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename,
				companySettings.getImage2Width(), companySettings.getImage2Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
		
				// generate image 3
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename,
				companySettings.getImage3Width(), companySettings.getImage3Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
		
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
			}
			SinglePage find;
			if(request.getParameter("feed_type").equals("edit")){
				find = singlePageDelegate.find(Long.parseLong(request.getParameter("singlepage_id")));
			}else{
				find = singlePageDelegate.find(singlepage);
			}
			// create the page image
			PageImage pageImage = new PageImage();
			pageImage.setFilename(filenames[i]);
			pageImage.setPage(find);
			pageImage.setPageType(find.providePageType());
			// pageImage.setUrl(UrlUtil.generateImageUploadUrl(company) + "pages/");
			pageImage.setOriginal("original/" + filename);
			pageImage.setImage1("image1/" + filename);
			pageImage.setImage2("image2/" + filename);
			pageImage.setImage3("image3/" + filename);
			pageImage.setThumbnail("thumbnail/" + filename);
			
			pageImageDelegate.insert(pageImage);
		}
		}
		return true;
	}
	
	public Map<String, Long> getAgianCompaniesMap(){
		
		Group group = groupDelegate.find(company, "Companies");
		List<CategoryItem> categories = categoryItemDelegate.findAllByGroup(company, group).getList();
		Map<String, Long> companies = new HashMap<String, Long>();
		
		for(CategoryItem item : categories){
			companies.put(item.getName(), item.getId());
		}
		
		return companies;
		
	}
	
	private void saveVideos(File[] files, String[] filenames, String[] contentTypes) {
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		// save new image in boysennewdesign folder
		if(company.getName().equalsIgnoreCase("boysen"))
			destinationPath += File.separator + "boysennewdesign";
		else
			destinationPath += File.separator + company.getName();
		
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		String dir = ApplicationConstants.MULTIPAGE_FILES_DIRECTORY_NAME + "/"+ multiPageId + "/" + singlepage;
		File fileDirectory = new File(servletContext.getRealPath(destinationPath + File.separator + dir));
		fileDirectory.mkdirs();
		//System.out.println(1);
		invalidFiles = 0;
		for(int i = 0; i < files.length; i++) {
			if(files[i].exists()) {
				//System.out.println(2);
				File file = files[i];
				String filename = filenames[i];
				String contentType = contentTypes[i];
				if(isImageFile(contentType)){
					//System.out.println(3);
					invalidFiles++;
					continue;
				}

				String destination = servletContext.getRealPath(destinationPath + File.separator + dir);
				File destFile = new File(destination + File.separator + filename);
				
				if(destFile.length()/1048576 > companySettings.getMaxFileSize()){
					//System.out.println(4);
					invalidFiles++;
					continue;
				}
				//System.out.println(5);
				FileUtil.copyFile(file, destFile); 
				
				SinglePage singlePage = singlePageDelegate.find(singlepage); 
				MultiPage multiPage = multiPageDelegate.find(multiPageId);
				MultiPageFile pageFile = new MultiPageFile();
				pageFile.setCompany(company);
				pageFile.setFileName(filename);
				pageFile.setFilePath(dir + "/" + filename);
				pageFile.setFileType(contentType);
				pageFile.setFileSize(destFile.length());
				pageFile.setMultipage(multiPage);
				pageFile.setSinglepage(singlePage);
				multiPageFileDelegate.insert(pageFile); 
			} 
		}
	}
	
	private boolean isImageFile(String contentType) {
		return StringUtils.contains(contentType, "image");
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<String> getFilesFileName() {
		return filesFileName;
	}

	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}

	public List<String> getFilesContentType() {
		return filesContentType;
	}

	public void setFilesContentType(List<String> filesContentType) {
		this.filesContentType = filesContentType;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}
	
	public Map<String, List<AgianEventsBean>> getSortedAgianEvents(){
		
		Group group = groupDelegate.find(company, "Events and Activities");
		Map<String, List<AgianEventsBean>> sortedEvents = new HashMap<String, List<AgianEventsBean>>();
		
		
		if(!isNull(group)){
			for(Category category : group.getCategories()){
				if(!isNull(category)){
					List<AgianEventsBean> events = new ArrayList<AgianEventsBean>();
					
					for(CategoryItem item : category.getItems()){
						try{
							AgianEventsBean eventBean = new AgianEventsBean();
							String eventDateString = item.getCategoryItemOtherFieldMap().get("Date").getContent();
							
							SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd,yyyy", Locale.ENGLISH);
							Date eventDate = sdf.parse(eventDateString);
						
							String display = item.getName();
							
							eventBean.setDate(eventDate);
							eventBean.setDisplay(display);
							eventBean.setDay(eventDate.getDay());
							eventBean.setEventType("event");
							eventBean.setIsEvent(true);
							events.add(eventBean);
									
						}catch(Exception e){
							
						}
						
					}
					Comparator<AgianEventsBean> comparator = new BeanComparator("date");
					Collections.sort(events, comparator);
					sortedEvents.put(category.getName(), events);
				}
			}
		}
		
		
		
		return sortedEvents;
	}
	
	public List<AgianEventsBean> getAgianEvents(){
		List<AgianEventsBean> events = new ArrayList<AgianEventsBean>();
		ObjectList<Member> members = memberDelegate.findAllByName(company);
		
		if(!isNull(members)){
			for(Member member : members){
				try{
					AgianEventsBean eventBean = new AgianEventsBean();
					String birthdayString = member.getInfo3();
					
					Date birthday = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(birthdayString);
					birthday.setYear(0000);
					
					String memberName = member.getFirstname() + " " + member.getLastname() + "'s Birthday!";
					eventBean.setDate(birthday);
					eventBean.setDisplay(memberName);
					eventBean.setMemberId(member.getId());
					eventBean.setDay(birthday.getDate());
					eventBean.setEventType("birthday");
					eventBean.setIsEvent(false);
					events.add(eventBean);
							
				}catch(Exception e){
					
				}
				
			}
		}
		
		Comparator<AgianEventsBean> comparator = new BeanComparator("date");
		Collections.sort(events, comparator);
		
		
		return events;
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}


