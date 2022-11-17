package com.ivant.utils;

import java.awt.Color;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class LineSeparatorElement extends ITextPdfElement {
	private final LineSeparator line = new LineSeparator();
	private final Float lineWidth;
	
	public LineSeparatorElement(Color lineColor, Float lineWidth, Integer x, Integer y) {
		this.lineWidth = lineWidth;
		this.x = x;
		this.y = y;
		
		line.setLineColor(new BaseColor(lineColor.getRGB()));
	}
	
	public Float getWidth() {return this.lineWidth;}
	
	public LineSeparator getLineSeparator() {return line;}
}
