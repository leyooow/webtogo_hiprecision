/*
 * Created on Aug 10, 2005
 *
 */
package com.ivant.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ivant.cms.delegate.CompanySettingsDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;

/**
 * @author erwin
 * 
 */
public final class GurkkaEmailUtil {

	private final Logger logger = Logger.getLogger(GurkkaEmailUtil.class);
	private static Session session = null;

	private static String mailerUserName;
	private static String mailerPassword;

	private static final String defaultUsername = EmailUtil.DEFAULT_USERNAME;
	private static final String defaultPassword = EmailUtil.DEFAULT_PASSWORD;
	private static final String defaultSmtpHost = "smtp.gmail.com";
	private static final int defaultSmtpPort = 25;
	
	public static boolean hasHTMLContent = false;

	public static boolean send(String from, String to, String subject,
			String content) {
		return send(from, to, subject, content, null);
	}
	public static void connect(String smtpHost, int smtpPort){
		//this will reset to default
		connect(smtpHost,smtpPort,defaultUsername,defaultPassword);
	}

	public static void connectViaCompanySettings(Company company)
	{
		if(company != null)
		{
			final CompanySettings companySettings = company.getCompanySettings();// CompanySettingsDelegate.getInstance().find(company);
			String emailUsername = null;
			String emailPassword = null;
			String smtpHost = null;
			int smtpPort = 25;
			if(companySettings != null)
			{
				emailUsername = companySettings.getEmailUserName();
				emailPassword = companySettings.getEmailPassword();
				smtpHost = companySettings.getSmtp();
				try
				{
					smtpPort = Integer.parseInt(companySettings.getPortNumber());
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			if(!StringUtils.isEmpty(emailUsername) && !StringUtils.isEmpty(emailPassword) && !StringUtils.isEmpty(smtpHost))
			{
				connect(smtpHost, smtpPort, emailUsername, emailPassword);
			}
			else
			{
				connect(defaultSmtpHost, defaultSmtpPort);
			}
		}
		else
		{
			connect(defaultSmtpHost, defaultSmtpPort);
		}
	}
	
	public static void connectViaGurkka()
	{
		try{
			connect("smtp.gmail.com", 587, "noreply@gurkka.com", "gnic1288");
		}catch(Exception e){
			e.printStackTrace();
			connect(defaultSmtpHost, defaultSmtpPort);
		}
	}
	
	public static void connect(String smtpHost, int smtpPort, String username,
			String password) {

		setMailerUserName(username);
		setMailerPassword(password);
		//System.out.println("=====================================--++");
		//System.out.println(mailerUserName);
		//System.out.println(mailerPassword);
		//System.out.println(smtpHost);
		//System.out.println(smtpPort);
		//System.out.println("=====================================--++");

		/*
		 * =================----READ---------------===========================
		 * Nino Eclarin OJT
		 * 
		 * set mailerUserName and mailerPassword to your smtp username and password
		 * set smtpHost and smtpPort to your mail server and the portnumber
		 * 
		 * 
		 * -------------------------====================-------------------------
		 */

		Authenticator auth = new PopupAuthenticator();

		// Create a mail session
		// java.util.Properties props = new java.util.Properties();
		// props.put("mail.smtp.host", smtpHost);
		// props.put("mail.smtp.port", ""+smtpPort);
		// props.put("mail.debug", "true");
		// props.put("mail.smtp.auth", "true");
		// props.put("mail.smtp.starttls.enable","true");

		java.util.Properties props = new java.util.Properties();
//		java.util.Properties props = System.getProperties();
		
		//props.setProperty("mail.transport.protocol", "smtps");
		//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		if(smtpHost.equalsIgnoreCase("mail.uniorient.com")){
			props.put("mail.smtp.starttls.enable", "false");
		} else if(smtpHost.equalsIgnoreCase("smtp.1and1.com")){
			props.put("mail.smtp.starttls.enable", "false");
		} else if(smtpHost.equalsIgnoreCase("smtp.zoho.com")){
			props.put("mail.smtp.starttls.enable", "true");
		} else{
			props.put("mail.smtp.starttls.enable", "true");
		}
		
		props.put("mail.smtp.ssl.enable", true);
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "" + smtpPort);
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		
		session = Session.getInstance(props, auth);
	}

	public static boolean sendWithHTMLFormat(String from, String to,
			String subject, String content, String attachment) {
		hasHTMLContent = true;
		return send(from, to, subject, content, attachment);
	}
	
	public static boolean sendWithHTMLFormatWithCC(String from, String to,
			String subject, String content, String attachment) {
		hasHTMLContent = true;
		return sendWithCC(from, to, subject, content, attachment);
	}
	
	public static boolean sendWithHTMLFormatWithCC(String from, String[] toEmails,
			String subject, String content, String attachment) {
		hasHTMLContent = true;
		return sendWithCC(from, toEmails, subject, content, attachment);
	}
	
	public static boolean sendWithHTMLFormatWithBCC(String from, String to,
			String subject, String content, String attachment, String[] toEmailsBCC) {
		hasHTMLContent = true;
		return sendWithBCC(from, to, subject, content, attachment, toEmailsBCC);
	}
	
	public static boolean sendWithHTMLFormatManyAttachments(String from, String to,
			String subject, String content, String[] attachment) {
		hasHTMLContent = true;
		return sendManyAttachments(from, to, subject, content, attachment);
	}

	public static boolean send(String from, String to, String subject,
			String content, String attachment) {
		boolean flag = true;
		
		MimeMessage msg = new MimeMessage(session);
		Logger logger = Logger.getLogger(GurkkaEmailUtil.class);
		StringWriter errors = new StringWriter();
		logger.info("WENDYS INSIDE SEND..." + to);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(subject, "UTF-8");

			if (hasHTMLContent)
				//msg.setDataHandler(new DataHandler(new HTMLDataSource(content)));
				msg.setContent(content, "text/html; charset=utf-8");
			else
				msg.setText(content);

			if (attachment != null)
				setFileAsAttachment(msg, attachment, content);
			Transport.send(msg);

		} catch (AddressException e) {
			flag = false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		} catch (Exception e) {
			flag =false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		}
		
		logger.info("sending email..." + flag);
		
		return flag;
	}
	
	public static boolean sendWithCC(String from, String to, String subject,
			String content, String attachment) {
		boolean flag = true;
		
		MimeMessage msg = new MimeMessage(session);
		Logger logger = Logger.getLogger(GurkkaEmailUtil.class);
		StringWriter errors = new StringWriter();
		logger.info("NISSAN SEND..." + to);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.addRecipients(Message.RecipientType.CC, new InternetAddress[]{new InternetAddress("reservations@nissanrentacar.com")});
			msg.setSubject(subject, "UTF-8");

			if (hasHTMLContent)
				//msg.setDataHandler(new DataHandler(new HTMLDataSource(content)));
				msg.setContent(content, "text/html; charset=utf-8");
			else
				msg.setText(content);

			if (attachment != null)
				setFileAsAttachment(msg, attachment, content);
			Transport.send(msg);

		} catch (AddressException e) {
			flag = false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		} catch (Exception e) {
			flag =false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		}
		
		logger.info("sending email..." + flag);
		
		return flag;
	}
	
	/**
	 * Send email with carbon copy
	 *
	 * @param from the sender
	 * @param toEmails the recipient(s), first email to be the direct recipient
	 * @param subject the subject
	 * @param content the message
	 * @param attachment attachment path if any, otherwise null
	 */
	public static boolean sendWithCC(String from, String[] toEmails, String subject,
			String content, String attachment) {
		boolean flag = true;
		
		MimeMessage msg = new MimeMessage(session);
		Logger logger = Logger.getLogger(GurkkaEmailUtil.class);
		StringWriter errors = new StringWriter();
		//logger.info("\n\n---->>Sending with carbon copy " + toEmails.toString());
		try {
			
			if(from.equalsIgnoreCase("info@lifeyogacenter.com")) {
				logger.info("LiFE ----- ");
				msg.setFrom(new InternetAddress(from, "LiFE"));
			} else {
				msg.setFrom(new InternetAddress(from));
			}
			
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[0]));
			if(toEmails.length > 1){
				//javax.mail.internet.InternetAddress[] ccTo = new javax.mail.internet.InternetAddress[toEmails.length];
				for (int i = 1; i < toEmails.length; i++){
				   // ccTo[i] = new javax.mail.internet.InternetAddress(toEmails[i]);
					logger.info("## Recepient : "+ toEmails[i]);
					msg.addRecipients(Message.RecipientType.CC, toEmails[i]); 
				}
				//msg.addRecipients(Message.RecipientType.CC, ccTo); 
			}
			msg.setSubject(subject, "UTF-8");
			if (hasHTMLContent){
				//msg.setDataHandler(new DataHandler(new HTMLDataSource(content)));
				msg.setContent(content, "text/html; charset=utf-8");
			}
			else{
				msg.setText(content);
			}
			if (attachment != null){
				setFileAsAttachment(msg, attachment, content);
			}
			logger.info("### username : " + msg.getSubject());
			
			Transport.send(msg);

		} catch (AddressException e) {
			flag = false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		} catch (Exception e) {
			flag =false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		}
		
		logger.info("sending email..." + flag);
		
		return flag;
	}
	
	/**
	 * Send email with blind carbon copy (BCC)
	 * @author Eric John
	 * @param from the sender
	 * @param to the recipient
	 * @param subject the subject
	 * @param content the message
	 * @param attachment attachment path if any, otherwise null
	 * @param toEmailsBCC BCC recipient(s)
	 */
	public static boolean sendWithBCC(String from, String to, String subject,
			String content, String attachment, String[] toEmailsBCC) {
		boolean flag = true;
		
		MimeMessage msg = new MimeMessage(session);
		Logger logger = Logger.getLogger(GurkkaEmailUtil.class);
		StringWriter errors = new StringWriter();
		//logger.info("\n\n---->>Sending with carbon copy " + toEmails.toString());
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			if(toEmailsBCC != null){
				if(toEmailsBCC.length > 0){
					//javax.mail.internet.InternetAddress[] ccTo = new javax.mail.internet.InternetAddress[toEmails.length];
					for (int i = 0; i < toEmailsBCC.length; i++){
					   // ccTo[i] = new javax.mail.internet.InternetAddress(toEmails[i]);
						msg.addRecipients(Message.RecipientType.BCC, toEmailsBCC[i]); 
					}
					//msg.addRecipients(Message.RecipientType.CC, ccTo); 
				}
			}
			
			msg.setSubject(subject, "UTF-8");
			if (hasHTMLContent)
				msg.setDataHandler(new DataHandler(new HTMLDataSource(content)));
				//msg.setContent(content, "text/html; charset=utf-8");
			else
				msg.setText(content);

			if (attachment != null)
				setFileAsAttachment(msg, attachment, content);
			Transport.send(msg);

		} catch (AddressException e) {
			flag = false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		} catch (Exception e) {
			flag =false;
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			logger.info("failed sending email..." + errors.toString());
		}
		
		logger.info("sending email..." + flag);
		
		return flag;
	}

	public static boolean sendManyAttachments(String from, String to, String subject,
			String content, String[] attachment) {
		boolean flag = true;
		
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(subject, "UTF-8");

			if (hasHTMLContent)
				msg.setContent(content, "text/html; charset=utf-8");
			else
				msg.setText(content);

			if (attachment != null) 
				setFilesAsAttachment(msg, attachment, content);
			
			Transport.send(msg);

		} catch (AddressException e) {
			flag = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
		} catch (Exception e) {
			flag =false;
			e.printStackTrace();
		}
		
		return flag;
	}	
	
	public static boolean sendWithManyAttachments(String from, String to,
			String subject, String content, String[] attachment) {
		boolean flag = true;
		// connect();

		// Construct the message
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(subject);
			msg.setText(content);
			if (attachment != null)
				setFilesAsAttachment(msg, attachment, content);

			Transport.send(msg);
		} catch (AddressException e) {
			flag = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean sendWithManyAttachments(String from, String[] to,
			String subject, String content, String[] attachment) {
		boolean flag = true;
		// connect();

		// Construct the message
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));

			if (to.length > 0) {
				for (String s : to) {
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(s.trim()));
				}
			}

			msg.setSubject(subject);
			msg.setText(content);
			if (attachment != null)
				setFilesAsAttachment(msg, attachment, content);

			Transport.send(msg);
		} catch (AddressException e) {
			flag = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean sendWithHTMLFormat(String from, String[] to,
			String subject, String content, String attachment) {
		hasHTMLContent = true;
		boolean flag = true;
		if (to != null)
			for (String toCompanyEmail : to) {
				// individual Email
				flag = sendWithHTMLFormat(from, toCompanyEmail, subject, content,
						attachment);
			}
		return flag;
	}
	
	public static boolean sendWithHTMLFormatToMany(String from, String[] to,
			String subject, String content, String attachment) {
		hasHTMLContent = true;
		if (to != null){
				sendSSL(from, to, subject, content, attachment);
			}
		return true;
	}

	public static boolean send(String from, String[] to, String subject,
			String content, String attachment) {
		boolean flag = true;
		// connect();

		// Construct the message
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));

			/*
			 * 
			 * msg.setFrom(new InternetAddress(from));
			 * msg.addRecipient(Message.RecipientType.TO, new
			 * InternetAddress(to)); msg.setSubject(subject);
			 * 
			 * if(hasHTMLContent) msg.setDataHandler(new DataHandler(new
			 * HTMLDataSource(content))); else msg.setText(content);
			 * 
			 * if(attachment!=null) setFileAsAttachment(msg, attachment,
			 * content); Transport.send(msg);
			 */
			if (to.length > 0) {
				for (String s : to) {
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(s.trim()));
				}
			}

			msg.setSubject(subject);

			// msg.setDataHandler(arg0)
			if (hasHTMLContent)
				msg.setDataHandler(new DataHandler(new HTMLDataSource(content)));
			else
				msg.setText(content);

			if (attachment != null)
				setFileAsAttachment(msg, attachment, content);
			else {
				// Create and fill first part
				MimeBodyPart p1 = new MimeBodyPart();
				p1.setText(content);

				// Create the Multipart. Add BodyParts to it.
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(p1);

				// Set Multipart as the message's content
				msg.setContent(mp);
			}

			Transport.send(msg);
		} catch (AddressException e) {
			flag = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	public static boolean sendSSL(String from, String[] to, String subject,
			String content, String attachment) {
		boolean flag = true;

		// Construct the message
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
			if (to.length > 0) {
				for (String s : to) {
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(s.trim()));
				}
			}

			msg.setSubject(subject);

			// msg.setDataHandler(arg0)
			if (hasHTMLContent)
				msg.setDataHandler(new DataHandler(new HTMLDataSource(content)));
			else
				msg.setText(content);

			if (attachment != null){
				setFileAsAttachment(msg, attachment, content);
			}
			
			Transport transport = session.getTransport("smtps");
			transport.connect(defaultSmtpHost , 465, mailerUserName, mailerPassword );
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		} catch (AddressException e) {
			flag = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	// Set a file as an attachment. Uses JAF FileDataSource.
	private static void setFileAsAttachment(Message msg, String filename,
			String content) throws MessagingException {

		if (hasHTMLContent)
			msg.setDataHandler(new DataHandler(new HTMLDataSource(content)));
		else
			msg.setText(content);
		
		// Create and fill first part
		MimeBodyPart p1 = new MimeBodyPart();
		p1.setText(content);
		
		if (hasHTMLContent)
			p1.setContent(content, "text/html");

		// Create second part
		MimeBodyPart p2 = new MimeBodyPart();

		// Put a file in the second part
		FileDataSource fds = new FileDataSource(filename);
		p2.setDataHandler(new DataHandler(fds));
		p2.setFileName(fds.getName());

		// Create the Multipart. Add BodyParts to it.
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(p1);
		mp.addBodyPart(p2);

		// Set Multipart as the message's content
		msg.setContent(mp);
	}

	static class HTMLDataSource implements DataSource {
		private String html;

		public HTMLDataSource(String htmlString) {
			html = htmlString;
		}

		// Return html string in an InputStream.
		// A new stream must be returned each time.
		public InputStream getInputStream() throws IOException {
			if (html == null)
				throw new IOException("Null HTML");
			return new ByteArrayInputStream(html.getBytes());
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("This DataHandler cannot write HTML");
		}

		public String getContentType() {
			return "text/html";
		}

		public String getName() {
			return "JAF text/html dataSource to send e-mail only";
		}
	}

	// email verification
	public static ArrayList<String> getMX(String hostName)
			throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put("java.naming.factory.initial",
				"com.sun.jndi.dns.DnsContextFactory");
		DirContext ictx = new InitialDirContext(env);
		Attributes attrs = ictx.getAttributes(hostName, new String[] { "MX" });
		Attribute attr = attrs.get("MX");
		if ((attr == null) || (attr.size() == 0)) {
			attrs = ictx.getAttributes(hostName, new String[] { "A" });
			attr = attrs.get("A");
			if (attr == null)
				throw new NamingException("No match for name '" + hostName
						+ "'");
		}
		ArrayList<String> res = new ArrayList<String>();
		NamingEnumeration<?> en = attr.getAll();
		while (en.hasMore()) {
			String mailhost;
			String x = (String) en.next();
			String f[] = x.split(" ");
			if (f.length == 1)
				mailhost = f[0];
			else if (f[1].endsWith("."))
				mailhost = f[1].substring(0, (f[1].length() - 1));
			else
				mailhost = f[1];
			res.add(mailhost);
		}
		return res;
	}

	public static boolean isAddressValid(String address) {
		int pos = address.indexOf('@');
		if (pos == -1)
			return false;
		String domain = address.substring(++pos);
		ArrayList<?> mxList = null;
		try {
			mxList = getMX(domain);
		} catch (NamingException ex) {
			return false;
		}
		if (mxList.size() == 0)
			return false;
		for (int mx = 0; mx < mxList.size(); mx++) {
			boolean valid = false;
			try {
				int res;
				Socket skt = new Socket((String) mxList.get(mx), defaultSmtpPort);
				skt.setSoTimeout(60000);

				BufferedReader rdr = new BufferedReader(new InputStreamReader(
						skt.getInputStream()));
				BufferedWriter wtr = new BufferedWriter(new OutputStreamWriter(
						skt.getOutputStream()));

				res = hear(rdr);
				if (res != 220)
					throw new Exception("Invalid Header");
				say(wtr, "EHLO gmail.com");
				res = hear(rdr);
				if (res != 250)
					throw new Exception("Not ESMTP");
				say(wtr, "MAIL FROM: <amitt800@gmail.com>");
				res = hear(rdr);
				if (res != 250)
					throw new Exception("Sender Rejected!");
				say(wtr, "RCPT TO: <" + address + ">");
				res = hear(rdr);
				say(wtr, "RSET");
				hear(rdr);
				say(wtr, "QUIT");
				hear(rdr);
				if (res != 250)
					throw new Exception("Address is Not Valid!!!");
				valid = true;
				rdr.close();
				wtr.close();
				skt.close();
			} catch (Exception ex) {
				valid = false;
			} finally {
				if (valid)
					return true;
			}
		}
		return false;
	}

	public static int hear(BufferedReader in) throws IOException {
		String line = null;
		int res = 0;
		while ((line = in.readLine()) != null) {
			String pfx = line.substring(0, 3);
			try {
				res = Integer.parseInt(pfx);

			} catch (Exception ex) {
				res = -1;
			}
			if (line.charAt(3) != '-')
				break;
		}

		return res;
	}

	public static void say(BufferedWriter wr, String text) throws IOException {
		wr.write(text + "\r\n");
		wr.flush();

	}

	// ----------
	// Set a file as an attachment. Uses JAF FileDataSource.
	private static void setFilesAsAttachment(Message msg, String[] filenames,
			String content) throws MessagingException {

		// Create and fill first part
		MimeBodyPart p1 = new MimeBodyPart();
		// p1.setText(content);
		// p1.setDataHandler(dh)
		p1.setDataHandler(new DataHandler(new HTMLDataSource(content)));

		// Create second part
		MimeBodyPart p2 = new MimeBodyPart();
		FileDataSource fds;
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(p1);
		
		for (String temp : filenames) {
			if(temp != null) {
				// Put a file in the second part
				fds = new FileDataSource(temp);
				p2 = new MimeBodyPart();
				p2.setDataHandler(new DataHandler(fds));
				p2.setFileName(fds.getName());
				mp.addBodyPart(p2);
			}
		}
		// Create the Multipart. Add BodyParts to it.
		// Set Multipart as the message's content
		msg.setContent(mp);
	}

	static class PopupAuthenticator extends Authenticator {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			// return new PasswordAuthentication("noreply@ivant.com",
			// "noreplywtg2009");
			// return new PasswordAuthentication("webtogo@ivant.com",
			// "web2go;1vant!");
			return new PasswordAuthentication(mailerUserName, mailerPassword);
			// return new PasswordAuthentication("system@webtogo.com.ph",
			// "ivanttechnologies2009");
		}
	}
	
	/**
	 * Checks of ALL emails are valid.
	 * @param emails
	 * @return
	 */
	public static final boolean allEmailsValid(String[] emails)
	{
		boolean result = true;
		if(emails != null && emails.length > 0)
		{
			for(String email : emails)
			{
				if(!isEmailValid(email))
				{
					result = false;
					break;
				}
			}
		}
		else
		{
			result = false;
		}
		return result;
	}
	
	/**
	 * Checks if SOME emails are valid.
	 * @param emails
	 * @return
	 */
	public static final boolean hasValidEmails(String[] emails)
	{
		boolean result = false;
		if(emails != null && emails.length > 0)
		{
			for(String email : emails)
			{
				if(isEmailValid(email))
				{
					result = true;
				}
			}
		}
		return result;
	}
	
	/**
	 * Checks if the email is valid
	 * @param email
	 * @return
	 */
	public static final boolean isEmailValid(String email)
	{
		return Pattern.compile
				(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" 
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
				)
				.matcher
					(
						email
					)
				.matches();
	}

	public static void setMailerUserName(String mailerUserName) {
		GurkkaEmailUtil.mailerUserName = mailerUserName;
	}

	public static void setMailerPassword(String mailerPassword) {
		GurkkaEmailUtil.mailerPassword = mailerPassword;
	}

}