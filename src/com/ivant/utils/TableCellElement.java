package com.ivant.utils;

import java.awt.Color;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;

/**
 * @author Daniel B. Sario
 *
 * @version 1.0, Feb 17, 2015
 * @since 1.0, Feb 17, 2015
 */

public class TableCellElement extends ITextPdfElement {
	
	public static final Integer CELL_CONTENT_ALIGN_CENTER = Element.ALIGN_CENTER;
	public static final Integer CELL_CONTENT_ALIGN_LEFT = Element.ALIGN_LEFT;
	public static final Integer CELL_CONTENT_ALIGN_JUSTIFY = Element.ALIGN_JUSTIFIED;
	public static final Integer CELL_CONTENT_ALIGN_RIGHT = Element.ALIGN_RIGHT;
	
	public static final Integer CELL_CONTENT_ALIGN_BOTTOM = Element.ALIGN_BOTTOM;
	
	private Boolean isLocalResource = Boolean.TRUE;
	
	private PdfPCell cell;
	
	private String text;
	private Image image;
	
	public static BaseFont baseFont = getFont();
	private Color fontColor = Color.BLACK;
	private Integer fontSize = 10;
	private Integer fontStyle = Font.NORMAL;
	
	private Color bgcolor = Color.WHITE;
	private Color borderColor = Color.BLACK;
	private Float borderWidth = 1f;
	
	private Float fixedHeight;
	
	private Integer colspan = 1;
	private Integer alignment = TableCellElement.CELL_CONTENT_ALIGN_CENTER;
	
	public TableCellElement(Phrase phrase) {
		cell = new PdfPCell(phrase);
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(TableCellElement.CELL_CONTENT_ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
	}
	
	public TableCellElement(Phrase phrase, int colspan) {
		cell = new PdfPCell(phrase);
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(TableCellElement.CELL_CONTENT_ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
	}
	
	public TableCellElement(String text, Color fontColor){
		Font font = new Font(baseFont, fontSize, fontStyle);
		font.setColor(new BaseColor(fontColor.getRGB()));
		
		cell = new PdfPCell(new Phrase(text, font));
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(Rectangle.NO_BORDER);
	}
	
	public TableCellElement(String text, Color fontColor, Integer colspan, Integer alignment, Integer fontSize){
		Font font = new Font(baseFont, fontSize, fontStyle);
		font.setColor(new BaseColor(fontColor.getRGB()));
		
		cell = new PdfPCell(new Phrase(text, font));
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(Rectangle.NO_BORDER);
	}
	
	public TableCellElement(String text, Integer colspan, Integer alignment, Integer fontSize, Integer fontStyle, Color fontColor){
		Font font = new Font(baseFont, fontSize, fontStyle);
		font.setColor(new BaseColor(fontColor.getRGB()));
		
		cell = new PdfPCell(new Phrase(text, font));
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(Rectangle.NO_BORDER);
	}
	
	public TableCellElement(String text, Color fontColor, Integer colspan, Integer alignment, Integer fontSize, Color bottomborder, Integer bottomborderwidth, Float cellheight){
		Font font = new Font(baseFont, fontSize, fontStyle);
		font.setColor(new BaseColor(fontColor.getRGB()));
		
		cell = new PdfPCell(new Phrase(text, font));
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setBorderColorBottom(new BaseColor(bottomborder.getRGB()));
		cell.setBorderWidthBottom(bottomborderwidth);
		cell.setFixedHeight(cellheight);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setBackgroundColor(new BaseColor(Color.WHITE.getRGB()));
	}
	
	public TableCellElement(String text, Color fontColor, Integer fontSize, Integer alignment){
		Font font = new Font(baseFont, fontSize, fontStyle);
		font.setColor(new BaseColor(fontColor.getRGB()));
		
		cell = new PdfPCell(new Phrase(text, font));
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(Rectangle.NO_BORDER);
	}
	
	public TableCellElement(String text, Color fontColor, Integer fontSize, Integer alignment, Integer fontStyle, Integer colspan){
		Font font = new Font(baseFont, fontSize, fontStyle);
		font.setColor(new BaseColor(fontColor.getRGB()));
		
		cell = new PdfPCell(new Phrase(text, font));
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(Rectangle.NO_BORDER);
	}
	
	public TableCellElement(String text, Color fontColor, Integer fontSize, Integer alignment, Color bgcolor){
		Font font = new Font(baseFont, fontSize, fontStyle);
		font.setColor(new BaseColor(fontColor.getRGB()));
		
		cell = new PdfPCell(new Phrase(text, font));
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setBackgroundColor(new BaseColor(bgcolor.getRGB()));
	}
	
	public TableCellElement(String text, 
					Color fontColor,
					Color borderColor,
					Float borderWidth,
					Float fixedHeight,
					Integer colspan,
					Integer alignment,
					Color bgcolor) {
		
		Font font = new Font(baseFont, fontSize, fontStyle);
		font.setColor(new BaseColor(fontColor.getRGB()));
		
		cell = new PdfPCell(new Phrase(text, font));
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setFixedHeight(fixedHeight);
		cell.setBackgroundColor(new BaseColor(bgcolor.getRGB()));
		cell.setBorderColor(new BaseColor(borderColor.getRGB()));
		cell.setBorderWidth(borderWidth);
	}
	
	
	public TableCellElement(String text, 
							BaseFont baseFont,
							Color fontColor,
							Integer fontSize,
							Integer fontStyle,
							Color borderColor,
							Float borderWidth,
							Float fixedHeight,
							Integer colspan,
							Integer alignment,
							Color bgcolor) {
		
		Font font = new Font(baseFont, fontSize, fontStyle);
		font.setColor(new BaseColor(fontColor.getRGB()));
		
		cell = new PdfPCell(new Phrase(text, font));
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setFixedHeight(fixedHeight);
		cell.setBackgroundColor(new BaseColor(bgcolor.getRGB()));
		cell.setBorderColor(new BaseColor(borderColor.getRGB()));
		cell.setBorderWidth(borderWidth);
	}
	
	public TableCellElement(String file_path,
							Boolean isLocalResource,
							Boolean fitToCell,
							Color bordercolor, 
							Float borderWidth,
							Integer colspan,
							Integer alignment,
							Float fixedHeight) {
		
		cell = new PdfPCell(getImage(file_path, isLocalResource), fitToCell);
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setFixedHeight(fixedHeight);
		cell.setBorderColor(new BaseColor(bordercolor.getRGB()));
		cell.setBorderWidth(borderWidth);
		cell.setPadding(2F);
	}
	
	public PdfPCell getCell() {return cell;}
	
	private static BaseFont getFont() {
		if(baseFont == null) {
			try {
				return BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
			} catch(Exception a) {
				a.printStackTrace();
				return null;
			}
		} else {
			return baseFont;
		}
	}
	
	private Image getImage(String file_path, Boolean isLocalResource) {
		Image image = null;
		
		try {
			if(isLocalResource) {
				image = Image.getInstance(file_path);
			} else {
				image = Image.getInstance(new URL(file_path));
			}
			
		} catch(BadElementException a) {
			a.printStackTrace();
		} catch(MalformedURLException b) {
			b.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public TableCellElement setAllBorder() {
		cell.setBorder(Rectangle.BOX);
		cell.setBorderWidth(1f);
		return this;
	}
	
	public TableCellElement setAllBorder(Color borderColor) {
		cell.setBorder(Rectangle.BOX);
		cell.setBorderWidth(1f);
		cell.setBorderColor(new BaseColor(borderColor.getRGB()));
		return this;
	}
	
	public TableCellElement setAllBorderExceptRight() {
		cell.setBorder(Rectangle.BOX);
		cell.setBorderWidth(1f);
		cell.setBorderColorRight(new BaseColor(Color.white.getRGB()));
		return this;
	}
	
	public TableCellElement setNoBorder() {
		cell.setBorder(Rectangle.NO_BORDER);
		return this;
	}
	
	public TableCellElement setBorderTop() {
		cell.setBorderWidthTop(1f);
		return this;
	}
	
	public TableCellElement setBorderTop(float width) {
		cell.setBorderWidthTop(width);
		return this;
	}
	
	public TableCellElement setBorderTop(Color color, float width) {
		cell.setBorderWidthTop(width);
		cell.setBorderColor(new BaseColor(color.getRGB()));
		return this;
	}
	
	public TableCellElement setBorderLeft() {
		cell.setBorderWidthLeft(1f);
		return this;
	}
	
	public TableCellElement setBorderLeft(Color color, float width) {
		cell.setBorderWidthLeft(width);
		cell.setBorderColor(new BaseColor(color.getRGB()));
		return this;
	}
	
	public TableCellElement setBorderLeft(float width) {
		cell.setBorderWidthLeft(width);
		return this;
	}
	
	
	public TableCellElement setBorderRight() {
		cell.setBorderWidthRight(1f);
		return this;
	}
	
	public TableCellElement setBorderRight(float width) {
		cell.setBorderWidthRight(width);
		return this;
	}
	
	public TableCellElement setBorderRight(Color color, float width) {
		cell.setBorderWidthRight(width);
		cell.setBorderColor(new BaseColor(color.getRGB()));
		return this;
	}
	
	public TableCellElement setBorderBottom() {
		cell.setBorderWidthBottom(1f);
		return this;
	}
	
	public TableCellElement setBorderBottom(Color color) {
		cell.setBorderColorBottom(new BaseColor(color.getRGB()));
		cell.setBorderWidthBottom(1f);
		return this;
	}
	
	public TableCellElement setBorderBottom(Color color, float width) {
		cell.setBorderColorBottom(new BaseColor(color.getRGB()));
		cell.setBorderWidthBottom(width);
		return this;
	}
	
	public TableCellElement setBorderBottom(float width) {
		cell.setBorderWidthBottom(width);
		return this;
	}
	
	public TableCellElement setCellHeight(float height) {
		cell.setFixedHeight(height);
		return this;
	}
	
	public TableCellElement setRowspan(int rowspan) {
		cell.setRowspan(rowspan);
		return this;
	}
	
	public TableCellElement setFixedHeight(float height) {
		cell.setFixedHeight(height);
		return this;
	}
	
	public TableCellElement setBorderColor(Color color) {
		cell.setBorderColor(new BaseColor(color.getRGB()));
		return this;
	}
	
	public TableCellElement setVerticalAlignment(int valign) {
		cell.setVerticalAlignment(valign);
		return this;
	}
	
}
