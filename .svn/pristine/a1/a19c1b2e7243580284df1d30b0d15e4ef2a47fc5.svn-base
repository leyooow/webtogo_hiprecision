package com.ivant.utils;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class ITextPdfUtil {
	private static final ITextPdfUtil instance = new ITextPdfUtil(new Integer(0), new Integer(0));
	
	private Document document;
	private PdfWriter writer;
	
	private Integer pageWidth;
	private Integer pageHeight;
	
	private Integer LEFT_MARGIN;
	
	private Integer TOP_MARGIN;
	
	private static List<ITextPdfElement> elements;
		
	public static ITextPdfUtil getInstance() {
		elements = new ArrayList<ITextPdfElement>();
		return ITextPdfUtil.instance;
	}
	
	public ITextPdfUtil(Integer LEFT_MARGIN, Integer TOP_MARGIN) {
		this.LEFT_MARGIN = LEFT_MARGIN;
		this.TOP_MARGIN = TOP_MARGIN;
	}
	
	public void createDocument(String path) {
		
		try {
			document = new Document();
			
			pageWidth = Math.round(document.getPageSize().getWidth());
			pageHeight = Math.round(document.getPageSize().getHeight());
			
			System.out.println(pageHeight + " : PAGE HEIGHT");
			
			File file = new File(path);
			file.createNewFile();
			
			writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			
			document.open();
			
				for(ITextPdfElement element : elements) {
					if(element instanceof TextElement) {
						putText((TextElement) element);
					} else if(element instanceof ImageElement) {
						putImage((ImageElement) element);
					} else if(element instanceof DocumentElement) {
						inserNewDocumentPage((DocumentElement) element);
					} else if(element instanceof TableElement){
						putTable((TableElement) element);
					} else if(element instanceof LineSeparatorElement){
						putLineSeparator((LineSeparatorElement) element);
					}
				}

			document.close();
			
		} catch(Exception a) {
			a.printStackTrace();
		}
	}
	
	private void putText(TextElement text) {
		
		try {
				PdfContentByte content = writer.getDirectContent();
	
				Point point = calibrateLocation(text.getX(), text.getY());
				
				content.beginText();
				content.moveText(Math.round(point.getX()), Math.round(point.getY()));
				content.setFontAndSize(text.getFont(), text.getSize());
				content.setRGBColorFill(text.getColor().getRed(), text.getColor().getGreen(), text.getColor().getBlue());
				content.showText(text.getText());
				content.endText();

		} catch(Exception a) {
			a.printStackTrace();
		}
		
	}
	
	private void putImage(ImageElement image) {
		try {
				Point point = null;
	
				Image img = image.getImage();
				
				if (image.getX() == null) {
					if (image.getFloatTo().equals(ImageElement.FLOAT_LEFT)) {
						point = calibrateLocation(0, image.getY());
						img.setAbsolutePosition(Math.round(point.getX()+ 10), Math.round(point.getY()));
					} else if(image.getFloatTo().equals(ImageElement.FLOAT_RIGHT)) {
						point = calibrateLocation((int) (pageWidth - (image.getWidth() + 20)), image.getY());
						img.setAbsolutePosition(Math.round(point.getX()), Math.round(point.getY()));
					}
				} else {
					point = calibrateLocation(image.getX(), image.getY());
					img.setAbsolutePosition(Math.round(point.getX()), Math.round(point.getY()));
				}
				

				document.add(img);

		} catch(Exception a) {
			a.printStackTrace();
		}
	}
	
	private void putLineSeparator(LineSeparatorElement line) {
		Point point = calibrateLocation(line.getX(), line.getY());
		line.getLineSeparator().drawLine(writer.getDirectContent(), (float)point.getX(), line.getWidth(), (float)point.getY());
	}
	
	private void putTable(TableElement table) {
		PdfContentByte content = writer.getDirectContent();
		Point point = calibrateLocation(table.getX(), table.getY());
		table.getTable().writeSelectedRows(0, -1, (float)point.getX(), (float)point.getY(), content);
	}
	
	private void inserNewDocumentPage(DocumentElement page) {
		if(page.getCreateNew()) {
			document.newPage();
		}
	}
	
	public void addElement(ITextPdfElement element) {
		elements.add(element);
	}
	
	public void setMargin(Integer horizontal, Integer vertical) {
		LEFT_MARGIN = horizontal;
		TOP_MARGIN = vertical;
	}
	
	private Point calibrateLocation(Integer x, Integer y) {
		return new Point(x + LEFT_MARGIN, pageHeight - (y + TOP_MARGIN));
	}
	
	public String newLineByMaxCharacters(String value, Integer max) {
		String toReturn = "";
		
		for(int ctr = 0;ctr < value.length();ctr++) {
			if((ctr + 1) % max == 0) {
				toReturn += "\n";
			}
			toReturn += value.substring(ctr, ctr + 1);
		}
		
		return toReturn;
	}
	
	
}
