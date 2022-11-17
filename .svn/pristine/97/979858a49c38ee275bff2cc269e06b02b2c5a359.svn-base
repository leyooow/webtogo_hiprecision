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

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.hibernate.criterion.Order;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cms.beans.AgianEventsBean;
import com.ivant.cms.delegate.BrandDelegate;
import com.ivant.cms.delegate.CommentFileDelegate;
import com.ivant.cms.delegate.CommentLikeDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberMessageDelegate;
import com.ivant.cms.delegate.NotificationDelegate;
import com.ivant.cms.delegate.PageImageDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.AbstractFile;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Comment;
import com.ivant.cms.entity.CommentFile;
import com.ivant.cms.entity.CommentLike;
import com.ivant.cms.entity.Company;
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

public class KuysenCmsDispatcherAction extends PageDispatcherAction {
	
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private BrandDelegate brandDelegate = BrandDelegate.getInstance();
	
	//private HttpSession session = request.getSession();
	
	@Override
	public String execute() throws Exception
	{
		
		return super.execute();
	}
	
	public String changeSelectedCompany(){
		
		long selectedcompany_id = Long.parseLong(request.getParameter("selectedCompany"));
		
		Company selectedCompany = companyDelegate.find(selectedcompany_id);
		
		session.put("selectedKuysenCompany", selectedCompany.getId());

		return SUCCESS;
	}
	
}


