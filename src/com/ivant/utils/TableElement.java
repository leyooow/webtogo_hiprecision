package com.ivant.utils;

import java.awt.Color;
import java.security.InvalidParameterException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class TableElement extends ITextPdfElement {
	
	public static final Integer CELL_CONTENT_ALIGN_CENTER = Element.ALIGN_CENTER;
	public static final Integer CELL_CONTENT_ALIGN_LEFT = Element.ALIGN_LEFT;
	public static final Integer CELL_CONTENT_ALIGN_JUSTIFY = Element.ALIGN_JUSTIFIED;
	public static final Integer CELL_CONTENT_ALIGN_RIGHT = Element.ALIGN_RIGHT;
	
	private PdfPTable table;
	private Integer columnCount;
	private Integer columnCounter = 0;
	
	public TableElement(Integer columnCount, Integer tableWidth, Boolean strechTableOnOverFlow, Integer x, Integer y) {
		table = new PdfPTable(columnCount);
		table.setTotalWidth(tableWidth);
		table.setLockedWidth(strechTableOnOverFlow);
		this.x = x;
		this.y = y;
		this.columnCount = columnCount;
	}
	
	public Boolean setColumnWidths(float[] columnWidths) {
		if(columnWidths.length > columnCount || columnWidths.length < columnCount) {
			throw new InvalidParameterException("Length of your array must be equal to the number of column you define in the contructor");
		}
		
		try {
			table.setWidths(columnWidths);
			return Boolean.TRUE;
		} catch(DocumentException a) {return Boolean.FALSE;}
	}
	
	public void addCell(TableCellElement cell, Integer colspan) {
		table.addCell(cell.getCell());
		
		columnCounter += colspan;
		
		if(columnCount.equals(columnCounter)) {
			table.completeRow();
			columnCounter = 0;
		}
	}
	
	public PdfPTable getTable() {
		return table;
	}
}
