/**
 *  @author Administrator created on @dJul 3, 2004
 */
package com.ivant.cms.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateHelper {

	private static String dateParserFormat = "MM/dd/yyyy";

	// private static DateHelper instance = new DateHelper();
	private static Date lastProductUpdate;

	private static Date lastPackageUpdate;

	private static final TimeZone manilaTimeZone = TimeZone.getTimeZone("GMT+8");

	private static final TimeZone serverTimeZone = TimeZone.getDefault();

	/**
	 * Displays a string from a given Date object
	 * 
	 * @param date
	 *            The date object for us to format
	 * @return
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(dateParserFormat);
		return df.format(date);
	}
	
	/**
	 * Displays a string from a given Date object in a given format/pattern
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format)
	{
		if(format == null)
		{
			return formatDate(date);
		}
		
		final SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * Parses a String into a Date object using our application's format
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(dateParserFormat);
			df.setLenient(false);
			return df.parse(date);
		} catch (ParseException e) {
			System.out.println("Format does not follow structure!");
		}
		return null;
	}

	/**
	 * Create a date after a given number of months. The hours, minute and
	 * seconds are reset to zero.
	 * 
	 * @param originalDate
	 *            The date object with the original date.
	 * @param noOfMonths
	 *            The number of months to be added to the given date object.
	 * 
	 * @return the Date object that reflect the correct date after the given
	 *         number of months
	 */
	public static Date getDateAfterNumberOfMonths(Date originalDate, int noOfMonths) {
		return getDateAfterNumberOfMonths(originalDate, noOfMonths, true);
	}

	/**
	 * Create a date after a given number of months.
	 * 
	 * @param originalDate
	 *            The date object with the original date.
	 * @param noOfMonths
	 *            The number of months to be added to the given date object.
	 * @param resetHourMinSec
	 *            Specify if we need to set the hour, minute, and seconds to
	 *            zero, the default is true
	 * @return the Date object that reflect the correct date after the given
	 *         number of months
	 */
	public static Date getDateAfterNumberOfMonths(Date originalDate, int noOfMonths,
			boolean resetHourMinSec) {

		if (originalDate == null)
			return null;

		if (noOfMonths < 1)
			return originalDate;

		// compute the last payment date
		Calendar origdatecal = Calendar.getInstance();
		origdatecal.setTime(originalDate);

		Calendar laterdatecal = Calendar.getInstance();
		int lastyear = origdatecal.get(Calendar.YEAR) + (noOfMonths / 12);
		int lastmonth = origdatecal.get(Calendar.MONTH) + (noOfMonths % 12) - 1;
		int lastday = origdatecal.get(Calendar.DAY_OF_MONTH);
		laterdatecal.set(Calendar.YEAR, lastyear);
		laterdatecal.set(Calendar.MONTH, lastmonth);
		laterdatecal.set(Calendar.DAY_OF_MONTH, lastday);

		if (resetHourMinSec) {
			laterdatecal.set(Calendar.HOUR, 0);
			laterdatecal.set(Calendar.MINUTE, 0);
			laterdatecal.set(Calendar.SECOND, 0);
		}

		return laterdatecal.getTime();
	}

	/**
	 * 
	 * @param originalDate
	 * @param noOfDays
	 * @return
	 */
	public static Date getDateAfterNumberOfDays(Date originalDate, int noOfDays) {

		return getDateAfterNumberOfDays(originalDate, noOfDays, true);
	}

	/**
	 * 
	 * @param originalDate
	 * @param noOfDays
	 * @param resetHourMinSec
	 * @return
	 */
	public static Date getDateAfterNumberOfDays(Date originalDate, int noOfDays,
			boolean resetHourMinSec) {

		if (originalDate == null)
			return null;

		Calendar origdatecal = Calendar.getInstance();
		origdatecal.setTime(originalDate);

		origdatecal.add(Calendar.DAY_OF_MONTH, noOfDays);

		if (resetHourMinSec) {
			origdatecal.set(Calendar.HOUR, 0);
			origdatecal.set(Calendar.MINUTE, 0);
			origdatecal.set(Calendar.SECOND, 0);
		}

		return origdatecal.getTime();
	}

	/**
	 * retrieves a nicely formated date time description.
	 * 
	 * @param date
	 *            the date to be formatted
	 */
	public static String getFormattedDateTime(Date date) {
		// needs to find out why date is null
		String formattedDate = "";
		Calendar cal = Calendar.getInstance();

		if (date != null) {
			cal.setTime(date);
			cal = setToClientTime(cal, date);

			Date newDate = cal.getTime();

			// ateFormat dateFormat = DateFormat.getDateTimeInstance();
			formattedDate = formatDate(newDate);
		}
		return formattedDate;
	}

	public static String getLastProductUpdate() {

		int result = 0;

		if (lastProductUpdate == null) {
			// result = resetLastUpdatedDate();

			if (result < 1)
				System.out.println("Unable to get the last update time.");
		}

		return getFormattedDateTime(lastProductUpdate);
	}

	public static String getLastPackageUpdate() {

		int result = 0;

		if (lastPackageUpdate == null) {
			// result = resetLastPackageUpdatedDate();

			if (result < 1)
				System.out.println("Unable to get the last update time.");
		}

		return getFormattedDateTime(lastPackageUpdate);
	}

	public static Calendar setToClientTime(Calendar calendar) {
		Date date = new Date(System.currentTimeMillis());
		return setToClientTime(calendar, date);
	}

	public static Calendar setToClientTime(Calendar calendar, Date date) {

		calendar.setTime(new Date(date.getTime() + manilaTimeZone.getRawOffset()
				- serverTimeZone.getRawOffset() - serverTimeZone.getDSTSavings()));

		return calendar;
	}
}
