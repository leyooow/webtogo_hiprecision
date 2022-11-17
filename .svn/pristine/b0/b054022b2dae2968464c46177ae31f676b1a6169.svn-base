package com.ivant.cms.action.admin;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.RandomAccessFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.interceptors.FrontCompanyInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.FileUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

public class ChatAction implements Action, SessionAware, Preparable,
		ServletRequestAware, UserAware, CompanyAware {

	private static final Logger logger = Logger.getLogger(ChatAction.class);

	private static final CompanyDelegate companyDelegate = CompanyDelegate
			.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();

	private ServletContext servletContext;

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private Map session;
	private HttpServletRequest request;

	private List<User> companyUsers;
	private User user;

	private Company comp;
	private long companyId;

	private String staff_id;

	/* All return string types for chat */
	private String buffer = "";
	private String userList = "";
	private InputStream fileInputStream;

	/* For file writing in chat */
	BufferedReader br = null;
	BufferedWriter bw = null;
	StringTokenizer token;
	private String destinationPath;

	@Override
	public void prepare() throws Exception {
		try {
			companyId = Long.parseLong(request.getParameter("company"));
			comp = companyDelegate.find(companyId);
			companyUsers = userDelegate.findAllByCompany(comp);
		} catch (Exception e) {
			logger.debug("cannot find company");
		}
	}

	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		if (comp != null) {
			session.put(FrontCompanyInterceptor.COMPANY_REQUEST_KEY, comp.getId());
			return SUCCESS;
		} else {
			//System.out.println("MMMM " + comp);
		}
		return Action.ERROR;
	}

	@Override
	public void setSession(Map session) {
		this.session = session;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setCompany(Company company) {
		this.comp = company;
	}

	public Company getCompany() {
		return comp;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getCompanyId() {
		return companyId;
	}

	/**
	 * The adminLog() writes to the 'admin_status.txt' the admin's
	 * id to prompt the user that there is an admin online.
	 * @return
	 */
	public String adminLog() {	
		String[] admin_map;
		int admin_count = 0, i;
		boolean new_admin = true;

		try {
			String str; 
			checkFiles();
			String masterlog = destinationPath + File.separator + "masterlog_" 
				+ request.getParameter("staff_id").split("-")[0] + ".txt";

			if (!new File(masterlog).exists()) {
				new File(masterlog).createNewFile();
				bw = new BufferedWriter(new FileWriter(new File(masterlog)));
				bw.write("<div class='log'>Mater Log " + request.getParameter("staff_id").trim()
						+"</div>");
				bw.newLine();
				bw.close();
			}

			String side = request.getParameter("admin_status").trim();

			staff_id = request.getParameter("staff_id");
			br = new BufferedReader(new FileReader(destinationPath
					+ File.separator + "/admin_status.txt"));

			str = br.readLine();
			admin_count = Integer.parseInt(str.trim());
			admin_map = new String[admin_count];

			if (admin_count > 0)
				for (i = 0; i < admin_count; i++) {
					admin_map[i] = br.readLine().trim();

					if (admin_map[i].startsWith(staff_id.split("-")[0]) && !side.equals("user_side"))
						new_admin = false;
				}
			br.close();

			bw = new BufferedWriter(new FileWriter(destinationPath + File.separator + "admin_status.txt"));

			if (new_admin && !side.equals("user_side")){
				bw.write(String.valueOf(admin_count + 1));
			}
			else{
				bw.write(String.valueOf(admin_count));
			}
			bw.newLine();
			for (i = 0; i < admin_map.length; i++) {
				bw.write(admin_map[i]);
				bw.newLine();
			}
			if (new_admin && side != "user_side")
				bw.write(staff_id);
			bw.close();
			//user.setActive(true);
			//userDelegate.update(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * redistAdmin() is responsible for redistributing the users to
	 * an available admin with the least assigned clients.
	 * 
	 * this method is called when the admin unloads the pop up window.
	 * @return
	 */
	public String redistAdmin() {
		//user.setActive(false);
		//userDelegate.update(user);
		int admin_count = 0, i;// index = 0;
		 staff_id = "";
		// String status = "";
		String dist = "";
		ArrayList<String> arlist = new ArrayList<String>();

		try {
			String sr;
			StringTokenizer token;

			checkFiles();

			staff_id = request.getParameter("staff_id").split("-")[0];
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
				if (token.nextElement().toString().split("-")[0].trim().equals(staff_id)) {
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
				/*	System.out.println("nagana pa d2==" + staff_id + "--" + tk
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
		return SUCCESS;
	}

	/**
	 * like the receiver from the user side, the front end,
	 * it is responsible for updating the chat log viewed 
	 * from the interface.
	 * @return
	 * @throws Exception
	 */
	public String receiver() throws Exception {
		String fname = "";
		try {
			String str = request.getParameter("u_id");
			buffer = "";//
			checkFiles();
			str = str.trim();
			fname = destinationPath + File.separator + "out_" + str + ".txt";
			br = new BufferedReader(new FileReader(fname));//
			while((str = br.readLine())!= null)//
				buffer+=str;//
			br.close();//
			fileInputStream = new ByteArrayInputStream(buffer.getBytes());
			//fileInputStream = new FileInputStream(fname);
		} catch (IOException e) {
			fileInputStream = new ByteArrayInputStream("0".getBytes());
		}
		return SUCCESS;
	}

	/**
	 * This checks the admin's chat queue if a user sends a message.
	 * @return
	 */
	public String queueReader() {
		try {

			checkFiles();
			String fname = destinationPath + File.separator + "chat_queue_"
					+ request.getParameter("staff_id").split("-")[0] + ".txt";

			if (!new File(fname).exists()) {
				new File(fname).createNewFile();
			}

			br = new BufferedReader(new FileReader(fname));
			String str;
			while ((str = br.readLine()) != null) {
				//System.out.print(str.trim() + "------");
				buffer += str.trim();
			}
			//System.out.println(buffer);
			br.close();
			// if(fileInputStream!=null)
			// fileInputStream.close();

			fileInputStream = new ByteArrayInputStream(buffer.getBytes());
			
			// can be implemented na hindi na binubura ung mga past log, pero
			// mas matagal iprocess..
			// if implemented using delete previous, mahirap i backtrack ung
			// kung sino ung sender unless magsend ulit sya ng bagong message
			// masasave naman ung log nya as long as hindi sya nacclose ng
			// browser
			bw = new BufferedWriter(new FileWriter(fname));
			bw.write("");
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	/**
	 * Responsible for replying.
	 * @return
	 */
	public String senderAdmin() {
		
		try {
			String userid = request.getParameter("user_name");

			checkFiles();

			bw = new BufferedWriter(new FileWriter(destinationPath
					+ File.separator + "out_" + request.getParameter("user_id")
					+ ".txt", true));
			String writeinfile = "<div class='chat_item'><p class='server'><em class='server'>" + userid 
			+ ":</em>  " + request.getParameter("message") + "</p>" 
			+ "<h6>" + request.getParameter("date") + "</h></div>" 
			+ "\n";
			bw.write(writeinfile);
			bw.newLine();
			bw.flush();
			bw.close();

			bw = new BufferedWriter(new FileWriter(destinationPath + File.separator + "masterlog_" 
					+ request.getParameter("staff_id").split("-")[0] + ".txt", true));
			bw.write(writeinfile);
			bw.newLine();
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * Returns all the employees of the company.
	 * @return
	 */
	public String getAllUser() {
		int i;
		String info = "";
		User tmp;
		
		companyUsers = userDelegate.findAllByCompany(comp);
		
		if (companyUsers.size() != 0) {
			tmp = companyUsers.get(0);
			userList = "";
			info = tmp.getId().toString() + "=" + tmp.getFirstname() + " "
					+ tmp.getLastname() + "=" + tmp.getUserType()/* + "=" +tmp.getActive()*/;
			userList = userList + info;
		}
		for (i = 1; i < companyUsers.size(); i++) {
			tmp = companyUsers.get(i);

			info = tmp.getId().toString() + "=" + tmp.getFirstname() + " " + tmp.getLastname() + "=" + tmp.getUserType()/* + "=" + tmp.getActive()*/;
			userList = userList + "," + info;
		}
		fileInputStream = new ByteArrayInputStream(
				userList.getBytes());
		return SUCCESS;
	}

	public String logoutChat() {
		try
		{
			checkFiles();
			br = new BufferedReader(new FileReader(destinationPath + File.separator + "id.txt"));
			String sr = br.readLine();
			int last_id = Integer.parseInt(sr.trim());
			br.close();
			File filetobedeleted;
			for(int i = 0 ; i < last_id ; i++)
			{
				filetobedeleted = new File(destinationPath + File.separator + "out_chat_user"+String.valueOf(i)+".txt");
				if(filetobedeleted.exists())
					filetobedeleted.delete();
			}
			if(new File(destinationPath + File.separator + "id.txt").exists())
				new File(destinationPath + File.separator + "id.txt").delete();
			if(new File(destinationPath + File.separator + "admin_status.txt").exists())
				new File(destinationPath + File.separator + "admin_status.txt").delete();
		}			
		catch(Exception ex)
		{ex.printStackTrace();}
		return logoutAdmin();
	}

	public String logoutAdmin() {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("STAFID")) {
					logoutRedistAdmin(cookies[i].getValue().replace("%20", " "));
					//System.out.println("--------------------------"+cookies[i].getValue());
					cookies[i].setMaxAge(0);
					break;
				}
			}
		} else{
			System.out.println("there are no cookies");
		}
			
		return SUCCESS;
	}
	
	public String logoutRedistAdmin(String staff_id) {
		int admin_count = 0, i;
		String dist = "";
		ArrayList<String> arlist = new ArrayList<String>();

		try {
			checkFiles();
			String sr;
			
			StringTokenizer token;

			
			
			br = new BufferedReader(new FileReader(destinationPath + File.separator + "admin_status.txt"));

			sr = br.readLine();
			admin_count = Integer.parseInt(sr.trim());

			if (admin_count > 0) {
				for (i = 0; i < admin_count; i++) {
					arlist.add(br.readLine().trim());
				}
			}
			br.close();

			bw = new BufferedWriter(new FileWriter(destinationPath + File.separator + "admin_status.txt"));

			for (i = 0; i < arlist.size(); i++) {
				token = new StringTokenizer(arlist.get(i), "|");
				//System.out.println(arlist.get(i));
				if (token.nextElement().toString().split("-")[0].trim().equals(staff_id)) {
					dist = arlist.get(i).trim();
					arlist.remove(i);
					admin_count--;
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
					arlist.set(i % arlist.size(), str);
				}
			}

			bw.write((admin_count) + "");
			bw.newLine();
			for (i = 0; i < admin_count; i++) {
				bw.write(arlist.get(i).trim());
				bw.newLine();
			}

			bw.close();
			
			bw = new BufferedWriter(new FileWriter(destinationPath + File.separator + "masterlog_" 
					+ staff_id.split("-")[0] + ".txt", true));
			bw.write("<div class='log'>Logout at: "+getTimeStamp()+"</div>");
			bw.flush();
			bw.close();
			//System.out.println("---------------------------ako ay nag out!!!!!!!!!!");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String resetChat(){
		try{
			checkFiles();
			File[]files = new File(destinationPath).listFiles();
			for(int i=0;i<files.length;i++)
				files[i].delete();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String adminWriteClients(){
		//System.out.println("==================================adminWriteClients");
		String[]clients = request.getParameter("clients").split(",");
		String staff_id = request.getParameter("staff_id");
		String admin_map[];
		
		try{
			checkFiles();
			RandomAccessFile raf = new RandomAccessFile(destinationPath + File.separator + "admin_status.txt","rw");
			String str = raf.readLine();
			String pointer = str + System.getProperty("line.separator");
			admin_map = new String[Integer.parseInt(str.trim())];
			for(int i = 0 ; i < admin_map.length ; i++)
			{
				admin_map[i] = raf.readLine();
				if(admin_map[i].length()>=staff_id.length())
				{
				if(admin_map[i].substring(0, staff_id.length()).equals(staff_id))
				{
					admin_map[i] = staff_id;
					for(int j = 0; j < clients.length ; j++)
						admin_map[i]+="|"+clients[j];
					raf.seek(pointer.length());
					raf.writeBytes(admin_map[i]);
					break;
				}
				else
					pointer+=admin_map[i]+System.getProperty("line.separator");
				}
			}
			raf.close();
		}
		
		
		catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewLog() {		
		try {
			String str = request.getParameter("staff_id").trim();
			checkFiles();
			String fname = destinationPath + File.separator + "masterlog_" + str.split("-")[0] + ".txt";; 
			if(!new File(fname).exists()){
				new File(fname).createNewFile();
				bw = new BufferedWriter(new FileWriter(new File(fname)));
				bw.write("<div class='log'>Mater Log " + str.trim()+"</div>");
				bw.newLine();
				bw.close();
			}
			fileInputStream = new FileInputStream(fname);
		} catch (IOException e) {
			e.printStackTrace();
			fileInputStream = new ByteArrayInputStream("0".getBytes());
		}
		return SUCCESS;
	}

	private void checkFiles() throws IOException {
		
		servletContext = request.getSession().getServletContext();

		destinationPath = "companies";

		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + comp.getName();
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

	public String getTimeStamp() {
		String[]monthName = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		Calendar calendar = new GregorianCalendar();
		String am_pm;
		String hr = String.valueOf(calendar.get(Calendar.HOUR));
		String min = String.valueOf(calendar.get(Calendar.MINUTE));
		String sec = String.valueOf(calendar.get(Calendar.SECOND));
		String mm = monthName[calendar.get(Calendar.MONTH)];
		String dd = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String yy = String.valueOf(calendar.get(Calendar.YEAR));

		if (calendar.get(Calendar.AM_PM) == 0)
			am_pm = "AM";
		else
			am_pm = "PM";

		String time = mm + ". "
				+ (dd.length() == 1 ? "0" + dd : dd) + ", "
				+ (yy.length() == 1 ? "0" + yy : yy) + " "
				+ (hr.length() == 1 ? "0" + hr : hr) + ":"
				+ (min.length() == 1 ? "0" + min : min) + ":"
				+ (sec.length() == 1 ? "0" + sec : sec) + " " + am_pm;

		fileInputStream = new ByteArrayInputStream(time.getBytes());

		return time;
	}
	
	public String initialQueueReader() {
		// reads the queue and gets called outside the chat module to notify
		// admin that there are chat messages
		try {

		checkFiles();
			String fname = destinationPath + File.separator + "chat_queue_"
					+ request.getParameter("staff_id").split("-")[0] + ".txt";

			if (!new File(fname).exists()) {
				new File(fname).createNewFile();
			}

			br = new BufferedReader(new FileReader(fname));
			String str;
			while ((str = br.readLine()) != null)
				buffer += str.trim();
			
			br.close();

			fileInputStream = new ByteArrayInputStream(buffer.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
}
