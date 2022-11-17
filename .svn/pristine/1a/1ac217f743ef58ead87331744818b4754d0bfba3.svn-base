package com.ivant.cms.company.utils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ivant.cms.entity.Member;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.PasswordEncryptor;

/**
 * 
 * @author John Paul Cas
 *
 */
public class AgianUtilities {
	
	// Again Email Provided
	private static final String SMTP = "smtp.office365.com";
	private static final int SMTP_PORT = 587;
	
	private static final String EMAIL_USERNAME = "admin_agian@ayala.com.ph";
	private static final String EMAIL_PASSWORD = "Zs9DLQye";
	
	
	/**
	 * Generate random character from 0-z character
	 * 
	 * @param size number of character to be generated
	 * @return random 
	 * @see {@link SecureRamdom}
	 */
	public static String generatePassword(int size) {
		String charSquence = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom ramdomazer = new SecureRandom();
		
		StringBuilder passwordChar = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			passwordChar.append(charSquence.charAt(ramdomazer.nextInt(charSquence.length()-1)));
		}
		
		return passwordChar.toString();
	}
	
	/**
	 * <p>Remove characters begin with dot(.) symbol <br />
	 * 	1000 out 1000 <br/>
	 *  10.0 out 10 <br/>
	 *  10.01 out 10 <br/>
	 * </p>
	 * 
	 * @param num
	 * @return
	 */
	public static String trimZeroStringNumeric(String num) {
		return num.indexOf(".") == -1 ? num : num.substring(0, num.indexOf("."));
	}
	
	/**
	 * Format the month, day and year specified into MM/dd/yyyy
	 * 
	 * @param month
	 * @param day
	 * @param year
	 * @return formatted date
	 */
	public static String getFormattedDate(String month, String day, String year) {
		final String SPACE = " ";
		String dateFormat = "";
		
		StringBuilder dateBuilder = new StringBuilder()
				.append(month)
				.append(SPACE)
				.append(trimZeroStringNumeric(day))
				.append(SPACE)
				.append(trimZeroStringNumeric(year));
		
		try {
			Date date = new SimpleDateFormat("MMMM dd yyyy").parse(dateBuilder.toString());
			dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
		} catch (ParseException e) {
			dateFormat = "";
		}
		
		return dateFormat;
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param attachment
	 * @return
	 */
	public static boolean sendMemberAccountDetails(String from, String to, String subject, String content, String attachment) {
		EmailUtil.connect(SMTP, SMTP_PORT, EMAIL_USERNAME, EMAIL_PASSWORD);
		return EmailUtil.sendWithHTMLFormat(from, to, subject, content, attachment);
	}
	
}
