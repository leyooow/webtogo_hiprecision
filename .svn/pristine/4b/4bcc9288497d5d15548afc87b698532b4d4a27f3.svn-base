package com.ivant.cms.server.listener;

/**
 * author Niño Eclarin and Marck Mateo
 * OJT
 * May 23, 2012
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.User;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.action.admin.ChatAction;
import com.ivant.utils.FileUtil;


public class SessionListener implements HttpSessionListener,
		ServletContextListener, HttpSessionAttributeListener {
	private ServletContext servletContext;
	private HttpServletRequest request;

	private String destinationPath;
	private BufferedReader br;
	private BufferedWriter bw;
	private UserDelegate userDelegate = UserDelegate.getInstance();
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	
	@Override
	public void contextInitialized(ServletContextEvent event) {

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {

	}

	public void sessionCreated(HttpSessionEvent se) {
		/*System.out
				.println("HAHAHAHA=============================created..!!!!!!!!!!");*/
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		long u_id = -1;
		boolean hasChat = false;
		String companyName = null;
		String frontCompany = null;
		String userName = null;
		final HttpSession session = se.getSession();
		
		if(session.getAttribute("user_id")!= null)
			u_id = Integer.parseInt(session.getAttribute("user_id").toString().trim());
		
		if(session.getAttribute("userName")!=null)
			userName = session.getAttribute("userName").toString().trim();
			
		if(session.getAttribute("hasChat")!=null)
			hasChat = (boolean) session.getAttribute("hasChat").toString().trim().equalsIgnoreCase("true");
		
		if(session.getAttribute("frontCompany")!=null && session.getAttribute("company") != null)
			frontCompany =  session.getAttribute("company").toString().trim();
		
		if(session.getAttribute("companyName")!=null)
			companyName = session.getAttribute("companyName").toString().trim();
		
		if(companyName != null && u_id != -1 && hasChat)
			redistAdmin(String.valueOf(u_id)+"-"+userName,companyName,session);
		/*System.out.println("HAHAHAHA=============================destryed..!~!..!!!!!!!!!!"+u_id+"==="+companyName+"======"+hasChat+"======"+frontCompany);*/
	}

	private final class RemoveUserRunnable implements Runnable {
		private final String sessionId;

		private final Long loggedUserId;

		private final boolean unassignJobs;

		/**
		 * @param session
		 * @param loggedUser
		 */
		private RemoveUserRunnable(HttpSession session, User loggedUser,
				boolean unassignJobs) {
			this.sessionId = session.getId();
			this.loggedUserId = loggedUser.getId();
			this.unassignJobs = unassignJobs;
		}

		@Override
		public void run() {
			
		}
	}
	
	private void checkFiles(String comp, HttpSession session) throws IOException {
			
			servletContext = session.getServletContext();
	
			destinationPath = "companies";
	
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			destinationPath += File.separator + comp;
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			destinationPath += File.separator + "files";
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			destinationPath += File.separator + "chat";
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
	
			destinationPath = servletContext.getRealPath(destinationPath);
	
			if (!new File(destinationPath + File.separator + "admin_status.txt").exists()) {
				new File(destinationPath + File.separator + "admin_status.txt").createNewFile();
				bw = new BufferedWriter(new FileWriter(new File(destinationPath + File.separator + "admin_status.txt")));
				bw.write("0");
				bw.close();
			}
			if (!new File(destinationPath + File.separator + "id.txt").exists()) {
				new File(destinationPath + File.separator + "id.txt").createNewFile();
				bw = new BufferedWriter(new FileWriter(new File(destinationPath + File.separator + "id.txt")));
				bw.write("0");
				bw.close();
			}
	}
	
	public void redistAdmin(String staff_id, String companyName, HttpSession session) {
		int admin_count = 0, i;
		String dist = "";
		ArrayList<String> arlist = new ArrayList<String>();
		//System.out.println(staff_id+"----------==============-------");
		try {
			String sr;
			StringTokenizer token;
	
			checkFiles(companyName,session);
			//System.out.println("dpath====---------"+destinationPath);
			br = new BufferedReader(new FileReader(destinationPath + File.separator + "admin_status.txt"));
	
			sr = br.readLine();
			admin_count = Integer.parseInt(sr.trim());
	
			if (admin_count > 0)
				for (i = 0; i < admin_count; i++)
					arlist.add(br.readLine().trim());
			br.close();
	
			bw = new BufferedWriter(new FileWriter(destinationPath + File.separator + "admin_status.txt"));
	
			for (i = 0; i < arlist.size(); i++) {
				token = new StringTokenizer(arlist.get(i), "|");
				//System.out.println(arlist.get(i));
				if (token.nextElement().toString().trim().equals(staff_id)) {
					dist = arlist.get(i).trim();
					arlist.remove(i);
					admin_count--;
					//System.out.println("Admin count:" + admin_count);
					break;
				}
			}
	
			token = new StringTokenizer(dist, "|");
	
			if (token.countTokens() > 1 && admin_count > 0) {
				int tk = token.countTokens();
				String str = token.nextElement().toString().trim();
				for (i = 0; i < tk - 1; i++) {
					str = arlist.get(i % arlist.size()).trim();
					str = str + "|" + token.nextElement().toString().trim();
					/*System.out.println("nagana pa d2==" + staff_id + "--" + tk
							+ "----" + i + "===" + str);*/
					arlist.set(i % arlist.size(), str);
				}
			}
			//System.out.println("abot d2");
	
			bw.write((admin_count) + "");
			bw.newLine();
			for (i = 0; i < admin_count; i++) {
				bw.write(arlist.get(i).trim());
				bw.newLine();
			}
			bw.close();
	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
