package com.ivant.utils;

import java.awt.Color;

import com.itextpdf.text.pdf.BaseFont;

public class TextElement extends ITextPdfElement {
	
	private final Integer DEFAULT_FONT_SIZE = 8;
	private final Color DEFAULT_FONT_COLOR = Color.BLACK;
	
	private String text;
	private BaseFont font;
	private Color color;
	private Integer size;
	private boolean bold = false;
	
	public TextElement(String text, BaseFont font, Color color, Integer size, Integer x, Integer y) {
		this.text = text;
		this.font = font;
		this.color = color;
		this.size = size;
		this.x = x;
		this.y = y;
	}
	
	public TextElement(String text, Color color, Integer size, Integer x, Integer y) {
		this.text = text;
		this.color = color;
		this.size = size;
		this.x = x;
		this.y = y;
	}
	
	public TextElement(String text, Integer size, Integer x, Integer y) {
		this.text = text;
		this.size = size;
		this.x = x;
		this.y = y;
	}
	
	public TextElement(String text, Integer x, Integer y) {
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	public TextElement(String text, Integer x, Integer y, boolean bold) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.bold = bold;
	}
	
	public TextElement(String text, Color color, Integer x, Integer y) {
		this.text = text;
		this.color = color;
		this.x = x;
		this.y = y;
	}
	
	public String getText() {
		return text;
	}

	public BaseFont getFont() {
		if(font == null) {
			if(!bold) {
				try {
					return BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
				} catch(Exception a) {
					a.printStackTrace();
					return null;
				}
			}else {
				try {
					return BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
				} catch(Exception a) {
					a.printStackTrace();
					return null;
				}
			}
		} else {
			return font;
		}
	}

	public Color getColor() {
		if(color == null) {
			return DEFAULT_FONT_COLOR;
		} else {
			return color;
		}
	}

	public Integer getSize() {
		if(size == null) {
			return DEFAULT_FONT_SIZE;
		} else {
			return size;
		}
	}
	
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	
}
