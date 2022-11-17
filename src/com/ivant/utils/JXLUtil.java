package com.ivant.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Blank;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.IOUtils;

/**
 * @author Kevin Roy K. Chua
 *
 * @version 1.0, Aug 17, 2010
 * @since 1.0, Aug 17, 2010
 *
 */
public class JXLUtil
{
	public static final WritableFont.FontName DEFAULT_FONT_NAME = WritableFont.TIMES; 
	public static final Integer DEFAULT_FONT_SIZE = 10;
	public static final Colour DEFAULT_COLOR = Colour.BLACK;
	
	public static void main(String [] args) throws Exception
	{
		File writeDir = new File("C:/Users/Vincent/Desktop");
		WritableWorkbook writableWorkbook = JXLUtil.createWritableWorkbook(writeDir, "test2.xls");									
		WritableSheet writableSheet = JXLUtil.createWritableSheet(writableWorkbook, "WebToGo", 0);
				
		JXLUtil.addImage(writableSheet, 0, 0, 5, 5, "C:/Users/Vincent/Desktop/Photo0003.jpg");		
		JXLUtil.addLabelCell(writableSheet, 0, 6, "TULOG", getWritableCellFormat(WritableFont.COURIER, 12));
		
		writableWorkbook.write();
		writableWorkbook.close();
		
		//System.out.println("Excel creation completed.");
	}
	
	/**
	 * 
	 * @param directory the directory where to place the file
	 * @param fileName the file name
	 * 
	 * @return the writable workbook
	 * @throws Exception
	 */
	public static final WritableWorkbook createWritableWorkbook(String directory, String fileName) throws Exception
	{
		return createWritableWorkbook(new File(directory), fileName);
	}
	
	/**
	 * 
	 * @param directory the directory where to place the file
	 * @param fileName the file name
	 * 
	 * @return the writable workbook
	 * @throws Exception
	 */
	public static final WritableWorkbook createWritableWorkbook(File directory, String fileName) throws Exception
	{
		return Workbook.createWorkbook(new File(directory, fileName));
	}
	
	/**
	 * 
	 * @param writableWorkbook the workbook
	 * @param name the name of the sheet
	 * @param index the index for the sheet
	 * 
	 * @return the writable sheet
	 * @throws Exception
	 */
	public static final WritableSheet createWritableSheet(WritableWorkbook writableWorkbook, String name, Integer index) throws Exception
	{
		return writableWorkbook.createSheet(name, index);
	}
	
	/**
	 * 
	 * @param writableSheet the sheet where to write
	 * @param column the column number
	 * @param row the row number
	 * @param width the number of columns which the image spans
	 * @param height the number of rows which the image spans
	 * @param image the path to the image
	 * 
	 * @throws Exception
	 */
	public static final void addImage(WritableSheet writableSheet, Integer column, Integer row, Integer width, Integer height, File image) throws Exception
	{
		InputStream inputStream = new FileInputStream(image);
		byte [] data = IOUtils.toByteArray(inputStream);		
		writableSheet.addImage(new WritableImage(column, row, width, height, data));
	}
	
	/**
	 * 
	 * @param writableSheet the sheet where to write
	 * @param column the column number
	 * @param row the row number
	 * @param width the number of columns which the image spans
	 * @param height the number of rows which the image spans
	 * @param image the path to the image
	 * 
	 * @throws Exception
	 */
	public static final void addImage(WritableSheet writableSheet, Integer column, Integer row, Integer width, Integer height, String image) throws Exception
	{
		addImage(writableSheet, column, row, width, height, new File(image));
	}
	
	/**
	 * 
	 * @param fontName the font name(Can be accessed through WritableFont enum)
	 * @param fontSize the font size
	 * @param isBold switch if bold
	 * @param isItalic switch if italic
	 * @param isUnderline switch if underline
	 * @param color the color(Can be accessed through Colour enum)
	 * 
	 * @return the writable cell format
	 * @throws Exception
	 */
	public static final WritableCellFormat getWritableCellFormat(WritableFont.FontName fontName, Integer fontSize, Boolean isBold, Boolean isItalic, Boolean isUnderline, Colour color) throws Exception
	{		
		if(isBold && isUnderline)
		{
			return new WritableCellFormat(new WritableFont(fontName, fontSize, WritableFont.BOLD, isItalic, UnderlineStyle.SINGLE, color));	
		}
		else if(isBold)
		{
			return new WritableCellFormat(new WritableFont(fontName, fontSize, WritableFont.BOLD, isItalic, UnderlineStyle.NO_UNDERLINE, color));
		}
		else if(isUnderline)
		{
			return new WritableCellFormat(new WritableFont(fontName, fontSize, WritableFont.NO_BOLD, isItalic, UnderlineStyle.SINGLE, color));
		}
		else //default
		{
			return new WritableCellFormat(new WritableFont(fontName, fontSize, WritableFont.NO_BOLD, isItalic, UnderlineStyle.NO_UNDERLINE, color));
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static final WritableCellFormat getWritableCellFormat() throws Exception
	{
		return getWritableCellFormat(DEFAULT_FONT_NAME, DEFAULT_FONT_SIZE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DEFAULT_COLOR);
	}
	
	/**
	 * 
	 * @param fontName the font name(Can be accessed through WritableFont enum)
	 * 
	 * @return the writable cell format
	 * @throws Exception
	 */
	public static final WritableCellFormat getWritableCellFormat(WritableFont.FontName fontName) throws Exception
	{
		return getWritableCellFormat(fontName, DEFAULT_FONT_SIZE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DEFAULT_COLOR);
	}
	
	/**
	 * 
	 * @param fontName the font name(Can be accessed through WritableFont enum)
	 * @param fontSize the font size
	 * 
	 * @return the writable cell format
	 * @throws Exception
	 */
	public static final WritableCellFormat getWritableCellFormat(WritableFont.FontName fontName, Integer fontSize) throws Exception
	{
		return getWritableCellFormat(fontName, fontSize, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DEFAULT_COLOR);
	}
	
	/**
	 * 
	 * @param fontName the font name(Can be accessed through WritableFont enum)
	 * @param fontSize the font size
	 * @param isBold switch if bold
	 * 
	 * @return the writable cell format
	 * @throws Exception
	 */
	public static final WritableCellFormat getWritableCellFormat(WritableFont.FontName fontName, Integer fontSize, Boolean isBold) throws Exception
	{
		return getWritableCellFormat(fontName, fontSize, isBold, Boolean.FALSE, Boolean.FALSE, DEFAULT_COLOR);
	}
	
	/**
	 * 
	 * @param fontName the font name(Can be accessed through WritableFont enum)
	 * @param fontSize the font size
	 * @param isBold switch if bold
	 * @param isItalic switch if italic
	 * 
	 * @return the writable cell format
	 * @throws Exception
	 */
	public static final WritableCellFormat getWritableCellFormat(WritableFont.FontName fontName, Integer fontSize, Boolean isBold, Boolean isItalic) throws Exception
	{
		return getWritableCellFormat(fontName, fontSize, isBold, isItalic, Boolean.FALSE, DEFAULT_COLOR);
	}
	
	/**
	 * 
	 * @param fontName the font name(Can be accessed through WritableFont enum)
	 * @param fontSize the font size
	 * @param isBold switch if bold
	 * @param isItalic switch if italic
	 * @param isUnderline switch if underline
	 * 
	 * @return the writable cell format
	 * @throws Exception
	 */
	public static final WritableCellFormat getWritableCellFormat(WritableFont.FontName fontName, Integer fontSize, Boolean isBold, Boolean isItalic, Boolean isUnderline) throws Exception
	{
		return getWritableCellFormat(fontName, fontSize, isBold, isItalic, isUnderline, DEFAULT_COLOR);
	}	
	
	/**
	 *  
	 * @param writableSheet the sheet where to add the blank cell
	 * @param column the column number 
	 * @param row the row number
	 * @param writableCellFormat the cell format
	 * 
	 * @throws Exception
	 */
	public static final void addBlankCell(WritableSheet writableSheet, Integer column, Integer row, WritableCellFormat writableCellFormat) throws Exception
	{
		writableSheet.addCell(new Blank(column, row, writableCellFormat));
	}
	
	/**
	 * 
	 * @param writableSheet the sheet where to add the blank cell
	 * @param column the column number 
	 * @param row the row number
	 * 
	 * @throws Exception
	 */
	public static final void addBlankCell(WritableSheet writableSheet, Integer column, Integer row) throws Exception
	{
		addBlankCell(writableSheet, column, row, null);
	}
	
	/**
	 * 
	 * @param writableSheet the sheet where to add the date cell
	 * @param column the column number 
	 * @param row the row number
	 * @param date the date
	 * @param writableCellFormat the cell format
	 * 
	 * @throws Exception
	 */
	public static final void addDateCell(WritableSheet writableSheet, Integer column, Integer row, Date date, WritableCellFormat writableCellFormat) throws Exception
	{
		writableSheet.addCell(new DateTime(column, row, date, writableCellFormat));
	}
	
	/**
	 * 
	 * @param writableSheet the sheet where to add the date cell
	 * @param column the column number 
	 * @param row the row number
	 * @param date the date
	 * 
	 * @throws Exception
	 */
	public static final void addDateCell(WritableSheet writableSheet, Integer column, Integer row, Date date) throws Exception
	{
		addDateCell(writableSheet, column, row, date, null);
	}
	
	/**
	 * 
	 * @param writableSheet the sheet where to add the label cell
	 * @param column the column number 
	 * @param row the row number
	 * @param content the content of the cell
	 * @param writableCellFormat the cell format
	 * 
	 * @throws Exception
	 */
	public static final void addLabelCell(WritableSheet writableSheet, Integer column, Integer row, String content, WritableCellFormat writableCellFormat) throws Exception
	{
		if(writableCellFormat == null)
		{
			
			writableSheet.addCell(new Label(column, row, content));
		}
		else
		{
			writableSheet.addCell(new Label(column, row, content, writableCellFormat));
		}
	}
	
	/**
	 * 
	 * @param writableSheet the sheet where to add the label cell
	 * @param column the column number 
	 * @param row the row number
	 * @param content the content of the cell
	 * 
	 * @throws Exception
	 */
	public static final void addLabelCell(WritableSheet writableSheet, Integer column, Integer row, String content) throws Exception
	{
		addLabelCell(writableSheet, column, row, content, null);
	}
	
	/**
	 * 
	 * @param writableSheet the sheet where to add the number cell
	 * @param column the column number 
	 * @param row the row number
	 * @param value the value
	 * @param writableCellFormat the cell format
	 * 
	 * @throws Exception
	 */
	public static final void addNumberCell(WritableSheet writableSheet, Integer column, Integer row, Double value, WritableCellFormat writableCellFormat) throws Exception
	{
		writableSheet.addCell(new Number(column, row, value, writableCellFormat));
	}
	
	/**
	 * 
	 * @param writableSheet the sheet where to add the number cell
	 * @param column the column number 
	 * @param row the row number
	 * @param value the value
	 * 
	 * @throws Exception
	 */
	public static final void addNumberCell(WritableSheet writableSheet, Integer column, Integer row, Double value) throws Exception
	{
		addNumberCell(writableSheet, column, row, value, null);
	}
	
	/**
	 * 
	 * @param writableSheet the writable sheet
	 * @param column the column number
	 * @param width the width
	 * 
	 * @throws Exception
	 */
	public static final void setColumnView(WritableSheet writableSheet, Integer column, Integer width) throws Exception
	{
		writableSheet.setColumnView(column, width);
	}
	
	/**
	 * 
	 * @param writableSheet
	 * @param row
	 * @param height
	 * @throws Exception
	 */
	public static final void setRowView(WritableSheet writableSheet, Integer row, Integer height) throws Exception
	{
		writableSheet.setRowView(row, height);
	}
}